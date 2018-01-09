package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateEmergencyFlasher;
import com.highmobility.autoapi.ActivateDeactivateValetMode;
import com.highmobility.autoapi.Capabilities;
import com.highmobility.autoapi.Capability;
import com.highmobility.autoapi.ChargeState;
import com.highmobility.autoapi.ClimateState;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.ControlCommand;
import com.highmobility.autoapi.ControlMode;
import com.highmobility.autoapi.ControlRooftop;
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
import com.highmobility.autoapi.GetTrunkState;
import com.highmobility.autoapi.GetValetMode;
import com.highmobility.autoapi.GetVehicleLocation;
import com.highmobility.autoapi.HonkAndFlash;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.NaviDestination;
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
import com.highmobility.autoapi.StartStopIonizing;
import com.highmobility.autoapi.StopControlMode;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.autoapi.ValetMode;
import com.highmobility.autoapi.VehicleLocation;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CapabilitiesTest {
    @Test
    public void capabilities() {
        byte[] bytes = Bytes.bytesFromHex("001001010005002000010201000500210001020100060023000102030100090024000102030405060100050025000102010006002600010203010007002700010203040100050028000102010003002902010004003000010100050031000102");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.is(Capabilities.TYPE));
        Capabilities capabilities = (Capabilities)command;

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
        assertTrue(capabilities.isSupported(StartStopIonizing.TYPE));

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
        byte[] unknownCapabilitiesBytes = Bytes.bytesFromHex("00100101000500AB00010201000500210001020100060023000102030100090024000102030405060100050025000102010006002600010203010007002700010203040100050028000102010003002902010004003000010100050031000102");
        Capabilities unknownCapabilities= null;

        try {
            unknownCapabilities = new Capabilities(unknownCapabilitiesBytes);
        } catch (CommandParseException e) {
            fail("unknowncapabilities init failed");
        }

        assertTrue(unknownCapabilities.getCapabilities().length == 11); // unknown capa still added to array

        for (int i = 0; i < unknownCapabilities.getCapabilities().length; i++) {
            assertTrue(unknownCapabilities.getCapabilities()[i] != null);
        }
    }

    // single capabilities

    @Test
    public void climateCapability() {
        byte[] message = Bytes.bytesFromHex("001004010009002400010203040506");
        Capability capability = null;
        try {
            capability = new Capability(message);
        } catch (CommandParseException e) {
            fail("climate capability init failed");
            e.printStackTrace();
        }

        assertTrue(capability.getCapability().isSupported(GetClimateState.TYPE));
        assertTrue(capability.getCapability().isSupported(ClimateState.TYPE));
        assertTrue(capability.getCapability().isSupported(StartStopHvac.TYPE));
        assertTrue(capability.getCapability().isSupported(StartStopDefogging.TYPE));
        assertTrue(capability.getCapability().isSupported(StartStopDefrosting.TYPE));
        assertTrue(capability.getCapability().isSupported(StartStopIonizing.TYPE));
    }

    @Test
    public void heartRateCapability() {
        byte[] message = Bytes.bytesFromHex("001004010003002902");
        Capability capability = null;
        try {
            capability = new Capability(message);
        }
        catch (CommandParseException e) {
            fail("climate capability init failed");
            e.printStackTrace();
        }

        assertTrue(capability.getCapability().isSupported(SendHeartRate.TYPE));
    }

    @Test public void getCapabilities() {
        byte[] waitingForBytes = Bytes.bytesFromHex("001000");
        byte[] commandBytes = new GetCapabilities().getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void getCapability() {
        byte[] waitingForBytes = Bytes.bytesFromHex("0010030029");
        byte[] commandBytes = new GetCapability(SendHeartRate.TYPE).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }
}
