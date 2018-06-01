package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateEmergencyFlasher;
import com.highmobility.autoapi.ActivateDeactivateValetMode;
import com.highmobility.autoapi.Capabilities;
import com.highmobility.autoapi.ChargeState;
import com.highmobility.autoapi.ClimateState;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlCommand;
import com.highmobility.autoapi.ControlMode;
import com.highmobility.autoapi.ControlRooftop;
import com.highmobility.autoapi.EnableDisableWifi;
import com.highmobility.autoapi.FlashersState;
import com.highmobility.autoapi.GetCapabilities;
import com.highmobility.autoapi.GetCapability;
import com.highmobility.autoapi.GetChargeState;
import com.highmobility.autoapi.GetClimateState;
import com.highmobility.autoapi.GetControlMode;
import com.highmobility.autoapi.GetFlashersState;
import com.highmobility.autoapi.GetLockState;
import com.highmobility.autoapi.GetNaviDestination;
import com.highmobility.autoapi.GetRooftopState;
import com.highmobility.autoapi.GetTheftAlarmState;
import com.highmobility.autoapi.GetTrunkState;
import com.highmobility.autoapi.GetValetMode;
import com.highmobility.autoapi.GetVehicleLocation;
import com.highmobility.autoapi.HonkAndFlash;
import com.highmobility.autoapi.Identifier;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.NaviDestination;
import com.highmobility.autoapi.Notification;
import com.highmobility.autoapi.OpenCloseTrunk;
import com.highmobility.autoapi.RooftopState;
import com.highmobility.autoapi.SendHeartRate;
import com.highmobility.autoapi.SetChargeLimit;
import com.highmobility.autoapi.SetClimateProfile;
import com.highmobility.autoapi.StartControlMode;
import com.highmobility.autoapi.StartStopCharging;
import com.highmobility.autoapi.StartStopDefogging;
import com.highmobility.autoapi.StartStopDefrosting;
import com.highmobility.autoapi.StartStopHvac;
import com.highmobility.autoapi.StartStopIonising;
import com.highmobility.autoapi.StopControlMode;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.autoapi.Type;
import com.highmobility.autoapi.ValetMode;
import com.highmobility.autoapi.VehicleLocation;
import com.highmobility.autoapi.property.CapabilityProperty;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CapabilitiesTest {
    @Test
    public void capabilities() {
        byte[] bytes = ByteUtils.bytesFromHex
                ("001001010005002000010201000500210001020100060023000102030100090024000102030405060100050025000102010006002600010203010007002700010203040100050028000102010003002902010004003000010100050031000102");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(Capabilities.TYPE));
        Capabilities capabilities = (Capabilities) command;

        assertTrue(capabilities.isSupported(GetLockState.TYPE));
        assertTrue(capabilities.isSupported(LockState.TYPE));
        assertTrue(capabilities.isSupported(LockUnlockDoors.TYPE));

        assertTrue(capabilities.isSupported(GetTrunkState.TYPE));
        assertTrue(capabilities.isSupported(OpenCloseTrunk.TYPE));
        assertTrue(capabilities.isSupported(TrunkState.TYPE));

        assertTrue(capabilities.isSupported(GetChargeState.TYPE));
        assertTrue(capabilities.isSupported(ChargeState.TYPE));
        assertTrue(capabilities.isSupported(StartStopCharging.TYPE));
        assertTrue(capabilities.isSupported(SetChargeLimit.TYPE));

        assertTrue(capabilities.isSupported(GetClimateState.TYPE));
        assertTrue(capabilities.isSupported(ClimateState.TYPE));
        assertTrue(capabilities.isSupported(SetClimateProfile.TYPE));
        assertTrue(capabilities.isSupported(StartStopHvac.TYPE));
        assertTrue(capabilities.isSupported(StartStopDefogging.TYPE));
        assertTrue(capabilities.isSupported(StartStopDefrosting.TYPE));
        assertTrue(capabilities.isSupported(StartStopIonising.TYPE));

        assertTrue(capabilities.isSupported(GetRooftopState.TYPE));
        assertTrue(capabilities.isSupported(RooftopState.TYPE));
        assertTrue(capabilities.isSupported(ControlRooftop.TYPE));

        assertTrue(capabilities.isSupported(GetFlashersState.TYPE));
        assertTrue(capabilities.isSupported(FlashersState.TYPE));
        assertTrue(capabilities.isSupported(HonkAndFlash.TYPE));
        assertTrue(capabilities.isSupported(ActivateDeactivateEmergencyFlasher.TYPE));

        assertTrue(capabilities.isSupported(GetControlMode.TYPE));
        assertTrue(capabilities.isSupported(ControlMode.TYPE));
        assertTrue(capabilities.isSupported(StartControlMode.TYPE));
        assertTrue(capabilities.isSupported(StopControlMode.TYPE));
        assertTrue(capabilities.isSupported(ControlCommand.TYPE));

        assertTrue(capabilities.isSupported(GetValetMode.TYPE));
        assertTrue(capabilities.isSupported(ValetMode.TYPE));
        assertTrue(capabilities.isSupported(ActivateDeactivateValetMode.TYPE));

        assertTrue(capabilities.isSupported(SendHeartRate.TYPE));

        assertTrue(capabilities.isSupported(GetVehicleLocation.TYPE));
        assertTrue(capabilities.isSupported(VehicleLocation.TYPE));

        assertTrue(capabilities.isSupported(GetNaviDestination.TYPE));
        assertTrue(capabilities.isSupported(NaviDestination.TYPE));
    }

    @Test
    public void oneUnknownCapability() {
        // 00 AB unknown
        byte[] unknownCapabilitiesBytes = ByteUtils.bytesFromHex
                ("00100101000500AB00010201000500210001020100060023000102030100090024000102030405060100050025000102010006002600010203010007002700010203040100050028000102010003002902010004003000010100050031000102");
        Capabilities unknownCapabilities = null;
        unknownCapabilities = new Capabilities(unknownCapabilitiesBytes);
        assertTrue(unknownCapabilities.getCapabilities().length == 11); // unknown capa still
        // added to array

        for (int i = 0; i < unknownCapabilities.getCapabilities().length; i++) {
            assertTrue(unknownCapabilities.getCapabilities()[i] != null);
        }
    }

    // single capabilities

    @Test
    public void climateCapability() {
        byte[] message = ByteUtils.bytesFromHex("001001010009002400010203040506");
        Capabilities capability = null;
        capability = (Capabilities) CommandResolver.resolve(message);
        if (capability == null) fail();
        assertTrue(capability.getCapability(GetClimateState.TYPE) != null);
        assertTrue(capability.isSupported(GetClimateState.TYPE));
        assertTrue(capability.isSupported(ClimateState.TYPE));
        assertTrue(capability.isSupported(StartStopHvac.TYPE));
        assertTrue(capability.isSupported(StartStopDefogging.TYPE));
        assertTrue(capability.isSupported(StartStopDefrosting.TYPE));
        assertTrue(capability.isSupported(StartStopIonising.TYPE));
    }

    @Test
    public void heartRateCapability() {
        byte[] message = ByteUtils.bytesFromHex("001001010003002902");
        Capabilities capability = null;
        capability = (Capabilities) CommandResolver.resolve(message);
        if (capability == null) fail();

        assertTrue(capability.isSupported(SendHeartRate.TYPE));
    }

    @Test public void getCapabilities() {
        byte[] bytes = ByteUtils.bytesFromHex("001000");
        byte[] commandBytes = new GetCapabilities().getByteArray();
        assertTrue(Arrays.equals(bytes, commandBytes));

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof GetCapabilities);
    }

    @Test public void getCapability() {
        byte[] bytes = ByteUtils.bytesFromHex("0010020029");
        byte[] commandBytes = new GetCapability(SendHeartRate.TYPE).getByteArray();
        assertTrue(Arrays.equals(bytes, commandBytes));

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof GetCapability);
        GetCapability get = (GetCapability) command;
        assertTrue(get.getCapabilityIdentifier() == Identifier.HEART_RATE);
    }

    @Test public void buildClimate() {
        Capabilities.Builder builder = new Capabilities.Builder();

        Type[] supportedTypes = new Type[]{
                GetClimateState.TYPE,
                ClimateState.TYPE,
                SetClimateProfile.TYPE,
                StartStopHvac.TYPE,
                StartStopDefogging.TYPE,
                StartStopDefrosting.TYPE,
                StartStopIonising.TYPE
        };

        CapabilityProperty property = new CapabilityProperty(Identifier.CLIMATE, supportedTypes);
        builder.addCapability(property);
        Capabilities capabilities = builder.build();
        byte[] message = capabilities.getByteArray();
        assertTrue(Arrays.equals(message, ByteUtils.bytesFromHex("001001010009002400010203040506")));
        assertTrue(capabilities.getCapability(GetClimateState.TYPE) != null);
        assertTrue(capabilities.getCapability(EnableDisableWifi.TYPE) == null);
        assertTrue(capabilities.getCapabilities().length == 1);

    }

    @Test public void buildClimateAndRemoteControl() throws IllegalArgumentException {
        Capabilities.Builder builder = new Capabilities.Builder();

        Type[] climateSupportedTypes = new Type[]{
                GetClimateState.TYPE,
                ClimateState.TYPE,
                SetClimateProfile.TYPE,
                StartStopHvac.TYPE,
                StartStopDefogging.TYPE,
                StartStopDefrosting.TYPE,
                StartStopIonising.TYPE
        };

        CapabilityProperty property = new CapabilityProperty(Identifier.CLIMATE,
                climateSupportedTypes);
        builder.addCapability(property);

        Type[] remoteControlTypes = new Type[]{
                GetControlMode.TYPE,
                ControlMode.TYPE,
                StartControlMode.TYPE,
                StopControlMode.TYPE,
                ControlCommand.TYPE
        };

        CapabilityProperty property2 = new CapabilityProperty(Identifier.REMOTE_CONTROL,
                remoteControlTypes);
        builder.addCapability(property2);

        byte[] message = builder.build().getByteArray();
        assertTrue(Arrays.equals(message, ByteUtils.bytesFromHex
                ("00100101000900240001020304050601000700270001020304")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildTypesFromDifferentCategories() {
        Capabilities.Builder builder = new Capabilities.Builder();

        Type[] supportedTypes = new Type[]{
                GetClimateState.TYPE,
                ClimateState.TYPE,
                SetClimateProfile.TYPE,
                StartStopHvac.TYPE,
                StartStopDefogging.TYPE,
                StartStopDefrosting.TYPE,
                GetTrunkState.TYPE
        };

        CapabilityProperty property = new CapabilityProperty(Identifier.CLIMATE, supportedTypes);

        builder.addCapability(property);
        builder.build().getByteArray();
    }

    @Test public void buildAddStateAutomaticallyWithGet() {
        Capabilities.Builder builder = new Capabilities.Builder();

        Type[] supportedTypes = new Type[]{
                GetClimateState.TYPE,
        };

        CapabilityProperty property = new CapabilityProperty(Identifier.CLIMATE, supportedTypes);
        builder.addCapability(property);
        Capabilities capabilities = builder.build();
        assertTrue(capabilities.isSupported(ClimateState.TYPE));

        byte[] bytes = ByteUtils.bytesFromHex("00100101000400240001");
        assertTrue(Arrays.equals(capabilities.getByteArray(), bytes));
    }

    @Test public void buildDontAddStateAutomaticallyWhenStateAlreadyExists() {
        Capabilities.Builder builder = new Capabilities.Builder();

        Type[] supportedTypes = new Type[]{
                GetClimateState.TYPE,
                ClimateState.TYPE
        };

        CapabilityProperty property = new CapabilityProperty(Identifier.CLIMATE, supportedTypes);
        builder.addCapability(property);
        Capabilities capabilities = builder.build();
        assertTrue(capabilities.getCapability(GetClimateState.TYPE).getTypes().length == 2);
    }

    @Test public void buildDontAddStateAutomaticallyWithNonGetStateType() {
        Capabilities.Builder builder = new Capabilities.Builder();

        Type[] supportedTypes = new Type[]{
                Notification.TYPE,
        };

        CapabilityProperty property = new CapabilityProperty(Identifier.NOTIFICATIONS, supportedTypes);
        builder.addCapability(property);
        Capabilities capabilities = builder.build();
        assertTrue(capabilities.getCapability(Notification.TYPE).getTypes().length == 1);
    }

    @Test public void handleUnknownCapability() {
        Capabilities.Builder builder = new Capabilities.Builder();

        Type[] supportedTypes = new Type[]{
                GetClimateState.TYPE,
                ClimateState.TYPE,
                new Type(Identifier.CLIMATE.getBytes(), 0x22)
        };

        CapabilityProperty property = new CapabilityProperty(Identifier.CLIMATE, supportedTypes);
        builder.addCapability(property);
        Capabilities capabilities = builder.build();
        assertTrue(capabilities.getCapability(GetClimateState.TYPE).getTypes().length == 3);
    }

    @Test public void zeroProperties() {
        Capabilities.Builder builder = new Capabilities.Builder();
        Capabilities capabilities = builder.build();
        testEmptyCommand(capabilities);
        assertTrue(capabilities.getByteArray().length == 3);

        byte[] bytes = ByteUtils.bytesFromHex("00100100");
        Command command = CommandResolver.resolve(bytes);
        testEmptyCommand((Capabilities) command);
    }

    void testEmptyCommand(Capabilities capabilities) {
        assertTrue(capabilities.getCapabilities().length == 0);
        assertTrue(capabilities.getCapability(GetTheftAlarmState.TYPE) == null);
    }
}