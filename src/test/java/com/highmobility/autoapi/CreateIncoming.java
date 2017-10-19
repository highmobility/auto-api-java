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
import com.highmobility.autoapi.incoming.Failure;
import com.highmobility.utils.Bytes;

import org.junit.Test;

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
}
