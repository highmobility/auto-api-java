package com.highmobility.autoapi;

import com.highmobility.autoapi.capability.AvailableCapability;
import com.highmobility.autoapi.capability.AvailableGetStateCapability;
import com.highmobility.autoapi.capability.ClimateCapability;
import com.highmobility.autoapi.capability.FeatureCapability;
import com.highmobility.autoapi.capability.HonkFlashCapability;
import com.highmobility.autoapi.capability.LightsCapability;
import com.highmobility.autoapi.capability.MessagingCapability;
import com.highmobility.autoapi.capability.NotificationsCapability;
import com.highmobility.autoapi.capability.RooftopCapability;
import com.highmobility.autoapi.capability.TrunkAccessCapability;
import com.highmobility.autoapi.capability.WindscreenCapability;
import com.highmobility.autoapi.incoming.Capabilities;
import com.highmobility.autoapi.incoming.ChargeState;
import com.highmobility.autoapi.incoming.DiagnosticsState;
import com.highmobility.autoapi.incoming.Failure;
import com.highmobility.autoapi.incoming.LightsState;
import com.highmobility.autoapi.incoming.TrunkState;
import com.highmobility.autoapi.incoming.VehicleStatus;
import com.highmobility.autoapi.incoming.WindscreenState;
import com.highmobility.autoapi.vehiclestatus.Charging;
import com.highmobility.autoapi.vehiclestatus.Climate;
import com.highmobility.autoapi.vehiclestatus.Diagnostics;
import com.highmobility.autoapi.vehiclestatus.DoorLocks;
import com.highmobility.autoapi.vehiclestatus.Engine;
import com.highmobility.autoapi.vehiclestatus.FeatureState;
import com.highmobility.autoapi.vehiclestatus.Lights;
import com.highmobility.autoapi.vehiclestatus.Maintenance;
import com.highmobility.autoapi.vehiclestatus.RemoteControl;
import com.highmobility.autoapi.vehiclestatus.RooftopState;
import com.highmobility.autoapi.vehiclestatus.TrunkAccess;
import com.highmobility.autoapi.vehiclestatus.ValetMode;
import com.highmobility.autoapi.vehiclestatus.Windscreen;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class CreateIncoming {
    @Test public void failure() {
        byte[] expectedBytes = Bytes.bytesFromHex("00020100210001");
        byte[] bytes = Failure.getCommandBytes(Command.TrunkAccess.GET_TRUNK_STATE, Failure.Reason.UNAUTHORIZED);
        assertTrue(Arrays.equals(expectedBytes, bytes));
    }

    @Test public void capabilities() throws CommandParseException {
        byte[] expectedBytes = Bytes.bytesFromHex("00100112" +
                "00200101" +
                "0021020300" +
                "00220100" +
                "00230100" +
                "0024020100" +
                "0025020101" +
                "002603010101" +
                "00270101" +
                "00280101" +
                "00290100" +
                "00300101" +
                "00310101" +
                "00320101" +
                "00400100" +
                "003603000101" +
                "0037020000" +
                "0038020100" +
                "0042020002"
                );

        FeatureCapability[] capabilities = new FeatureCapability[18];

        AvailableGetStateCapability doorLocksCapability = new AvailableGetStateCapability(
                Command.Identifier.DOOR_LOCKS,
                AvailableGetStateCapability.Capability.AVAILABLE);
        capabilities[0] = doorLocksCapability;

        TrunkAccessCapability trunkAccessCapability = new TrunkAccessCapability(
                TrunkAccessCapability.LockCapability.GET_STATE_UNLOCK_AVAILABLE,
                TrunkAccessCapability.PositionCapability.UNAVAILABLE);
        capabilities[1] = trunkAccessCapability;

        AvailableCapability wakeUpCapability = new AvailableCapability(
                Command.Identifier.WAKE_UP,
                AvailableCapability.Capability.UNAVAILABLE);
        capabilities[2] = wakeUpCapability;

        AvailableGetStateCapability chargingCapability = new AvailableGetStateCapability(
                Command.Identifier.CHARGING,
                AvailableGetStateCapability.Capability.UNAVAILABLE);
        capabilities[3] = chargingCapability;

        ClimateCapability climateCapability = new ClimateCapability(
                AvailableGetStateCapability.Capability.AVAILABLE,
                ClimateCapability.ProfileCapability.UNAVAILABLE);
        capabilities[4] = climateCapability;

        RooftopCapability rooftopCapability = new RooftopCapability(
            RooftopCapability.DimmingCapability.AVAILABLE,
            RooftopCapability.OpenCloseCapability.AVAILABLE
        );
        capabilities[5] = rooftopCapability;

        HonkFlashCapability honkFlashCapability = new HonkFlashCapability(
                AvailableCapability.Capability.AVAILABLE,
                AvailableCapability.Capability.AVAILABLE,
                AvailableCapability.Capability.AVAILABLE
        );
        capabilities[6] = honkFlashCapability;

        AvailableCapability remoteControlCapability = new AvailableCapability(
                Command.Identifier.REMOTE_CONTROL,
                AvailableCapability.Capability.AVAILABLE);
        capabilities[7] = remoteControlCapability;

        AvailableGetStateCapability valetCapability = new AvailableGetStateCapability(
                Command.Identifier.VALET_MODE,
                AvailableGetStateCapability.Capability.AVAILABLE);
        capabilities[8] = valetCapability;

        AvailableCapability heartRateCapability = new AvailableCapability(
                Command.Identifier.HEART_RATE,
                AvailableCapability.Capability.UNAVAILABLE);
        capabilities[9] = heartRateCapability;

        AvailableCapability vehicleLocationCapability = new AvailableCapability(
                Command.Identifier.VEHICLE_LOCATION,
                AvailableCapability.Capability.AVAILABLE);
        capabilities[10] = vehicleLocationCapability;

        AvailableCapability naviCapability = new AvailableCapability(
                Command.Identifier.NAVI_DESTINATION,
                AvailableCapability.Capability.AVAILABLE);
        capabilities[11] = naviCapability;

        AvailableCapability deliveredParcelsCapability = new AvailableCapability(
                Command.Identifier.DELIVERED_PARCELS,
                AvailableCapability.Capability.AVAILABLE);
        capabilities[12] = deliveredParcelsCapability;

        AvailableCapability fuelingCapability = new AvailableCapability(
                Command.Identifier.FUELING,
                AvailableCapability.Capability.UNAVAILABLE);
        capabilities[13] = fuelingCapability;

        LightsCapability lightsCapability = new LightsCapability(
                AvailableGetStateCapability.Capability.UNAVAILABLE,
                AvailableGetStateCapability.Capability.AVAILABLE,
                AvailableCapability.Capability.AVAILABLE);
        capabilities[14] = lightsCapability;

        MessagingCapability messagingCapability = new MessagingCapability(
                AvailableCapability.Capability.UNAVAILABLE,
                AvailableCapability.Capability.UNAVAILABLE
        );

        capabilities[15] = messagingCapability;

        NotificationsCapability notificationsCapability = new NotificationsCapability(
                AvailableCapability.Capability.AVAILABLE,
                AvailableCapability.Capability.UNAVAILABLE
        );

        capabilities[16] = notificationsCapability;

        WindscreenCapability windscreenCapability = new WindscreenCapability(
                AvailableCapability.Capability.UNAVAILABLE,
                AvailableGetStateCapability.Capability.GET_STATE_AVAILABLE
        );

        capabilities[17] = windscreenCapability;

        byte[] bytes = Capabilities.getCommandBytes(capabilities);
        assertTrue(Arrays.equals(expectedBytes, bytes));
    }

    @Test
    public void vehicleStatus() throws UnsupportedEncodingException {
        byte[] expectedBytes = Bytes.bytesFromHex(
        "001101" +
            "4a46325348424443374348343531383639" + // VIN
            "01" +           // All-electric powertrain
            "06" +           // Model name is 6 bytes
            "547970652058" + // "Type X"
            "06" +           // Car name is 6 bytes
            "4d7920436172" + // "My Car"
            "06" +           // License plate is 6 bytes
            "414243313233" + // "ABC123"
            "11" +       // 17 vehicle states in the message
            "0021020001" + // Trunk Access Identifier
            "00270102" + // Remote Control Identifier
            "0023080200FF32bf19999a" + // charging
            "002410419800004140000001000041ac000060" + // climate
            "003603020100" + // lights
            "00200D04000100010000020001030001" + // door locks
            "0025026400" + // rooftop
            "00280101" + // valetmode
            "00330B0249F00063003C09C45A01" + // diagnostics
            "00340501F5000E61" + // maintenance
            "00350101" + // engine
            "0042010203024312025f11010a102005" + // windscreen
            "00460101" + // theft alarm
            "004701010e4265726c696e205061726b696e670363054F11010a11220000000000" + // parking ticket
            "00500111010a1020010078" + // vehicle time
            "004501040001010002000301" // windows
        );

        FeatureState[] featureStates = new FeatureState[17];

        TrunkAccess trunkAccess = new TrunkAccess(TrunkState.LockState.UNLOCKED, TrunkState.Position.OPEN);
        featureStates[0] = trunkAccess;
        RemoteControl remoteControl = new RemoteControl(RemoteControl.State.STARTED);
        featureStates[1] = remoteControl;

        featureStates[2] = getCharging();
        featureStates[3] = getClimate();
        featureStates[4] = getLights();
        featureStates[5] = getDoorLocks();
        featureStates[6] = getRooftop();
        featureStates[7] = getValetMode();
        featureStates[8] = getDiagnostics();
        featureStates[9] = getMaintenance();
        featureStates[10] = getEngine();
        featureStates[11] = getWindscreen();

        byte[] bytes = VehicleStatus.getCommandBytes(
                "4a46325348424443374348343531383639",
                VehicleStatus.PowerTrain.ALLELECTRIC,
                "547970652058",
                "4d7920436172",
                "414243313233",
                featureStates);

        assertTrue(Arrays.equals(bytes, expectedBytes));
    }

    @Test public void windscreen() throws Exception {
        String expectedResult = "00420D0203024312025f11010a102005";
        assertTrue(Arrays.equals(Bytes.bytesFromHex(
                expectedResult),
                getEngine().getBytes()));
    }

    private Windscreen getWindscreen() {
//        Windscreen windscreen = new Windscreen(
//                WindscreenState.WiperState.AUTOMATIC,
//                WindscreenState.WiperIntensity.LEVEL_3,
//                WindscreenState.WindscreenDamage.DAMAGE_SMALLER_THAN_1
//
//        );
//        return windscreen;
        return null; // TODO:
    }

    @Test public void engine() throws Exception {
        String expectedResult = "00350101";
        assertTrue(Arrays.equals(Bytes.bytesFromHex(
                expectedResult),
                getEngine().getBytes()));
    }

    private Engine getEngine() {
        Engine maintenance = new Engine(true);
        return maintenance;
    }

    @Test public void maintenance() throws Exception {
        String expectedResult = "00340501F5000E61";
        assertTrue(Arrays.equals(Bytes.bytesFromHex(
                expectedResult),
                getMaintenance().getBytes()));
    }

    private Maintenance getMaintenance() {
        Maintenance maintenance = new Maintenance(
            501, 3681
        );

        return maintenance;
    }

    @Test public void diagnostics() throws Exception {
        String expectedResult = "00330B0249F00063003C09C45A01";

        assertTrue(Arrays.equals(Bytes.bytesFromHex(
                expectedResult),
                getDiagnostics().getBytes()));
    }

    private Diagnostics getDiagnostics() {
        Diagnostics diagnostics = new Diagnostics(
                150000,
                99,
                60,
                2500,
                .9f,
                DiagnosticsState.WasherFluidLevel.FULL
        );

        return diagnostics;
    }

    @Test public void valetMode() throws Exception {
        String expectedResult = "00280101";
        assertTrue(Arrays.equals(Bytes.bytesFromHex(
                expectedResult),
                getValetMode().getBytes()));
    }

    private ValetMode getValetMode() {
        ValetMode valetMode = new ValetMode(true);
        return valetMode;
    }

    @Test public void rooftop() throws Exception {
        String expectedResult = "0025026400";
        assertTrue(Arrays.equals(Bytes.bytesFromHex(
                expectedResult),
                getValetMode().getBytes()));
    }

    RooftopState getRooftop() {
        RooftopState rooftopState = new RooftopState(
            1f, 0f
        );

        return rooftopState;
    }

    @Test public void doorLocks() throws Exception {
        String expectedResult = "00200D04000100010000020001030001";
        assertTrue(Arrays.equals(Bytes.bytesFromHex(
                expectedResult),
                getDoorLocks().getBytes()));
    }

    DoorLocks getDoorLocks() {
        DoorLocks doorLocks = new DoorLocks(
            new DoorLockState(DoorLockState.Location.FRONT_LEFT, DoorLockState.Position.OPEN, DoorLockState.LockState.UNLOCKED),
            new DoorLockState(DoorLockState.Location.FRONT_RIGHT, DoorLockState.Position.CLOSED, DoorLockState.LockState.UNLOCKED),
            new DoorLockState(DoorLockState.Location.REAR_RIGHT, DoorLockState.Position.CLOSED, DoorLockState.LockState.LOCKED),
            new DoorLockState(DoorLockState.Location.REAR_LEFT, DoorLockState.Position.CLOSED, DoorLockState.LockState.LOCKED)
        );

        return doorLocks;
    }

    @Test public void lights() throws Exception {
        String expectedResult = "003603020100";

        assertTrue(Arrays.equals(Bytes.bytesFromHex(
                expectedResult),
                getLights().getBytes()));
    }

    Lights getLights() {
        Lights lights = new Lights(
                LightsState.FrontExteriorLightState.ACTIVE_WITH_FULL_BEAM,
                true,
                false);
        return lights;
    }

    @Test public void climate() throws Exception {
        String expectedResult = "002410419800004140000001000041ac000060";

        assertTrue(Arrays.equals(Bytes.bytesFromHex(
                expectedResult),
                getClimate().getBytes()));
    }

    Climate getClimate() {
        boolean[] autoHvacStates = new boolean[] { false, false, false, false, false, true, true };

        Climate climate = new Climate(19f,
                12f,
                true,
                false,
                false,
                21.5f,
                autoHvacStates,
                false);
        return climate;
    }

    @Test public void remoteControl() {
        RemoteControl remoteControl = new RemoteControl(RemoteControl.State.STARTED);
        assertTrue(Arrays.equals(Bytes.bytesFromHex("00270102"), remoteControl.getBytes()));
    }

    @Test public void charging() throws Exception {
        assertTrue(Arrays.equals(Bytes.bytesFromHex("0023080200FF32bf19999a"), getCharging().getBytes()));
    }

    Charging getCharging() {
        Charging charging = new Charging(
                ChargeState.ChargingState.CHARGING,
                255,
                .5f,
                -.6f);
        return charging;
    }

    @Test public void trunkAccess() throws Exception {
        TrunkAccess trunkAccess = new TrunkAccess(TrunkState.LockState.UNLOCKED, TrunkState.Position.OPEN);
        assertTrue(Arrays.equals(Bytes.bytesFromHex("0021020001"), trunkAccess.getBytes()));
    }
}
