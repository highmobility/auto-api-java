package com.highmobility.autoapi;

import com.highmobility.autoapi.Command.Identifier;
import com.highmobility.autoapi.incoming.*;
import com.highmobility.autoapi.incoming.IncomingCommand;
import com.highmobility.autoapi.vehiclestatus.*;
import com.highmobility.autoapi.vehiclestatus.Maintenance;
import com.highmobility.autoapi.vehiclestatus.ParkingTicket;
import com.highmobility.autoapi.vehiclestatus.RooftopState;
import com.highmobility.autoapi.vehiclestatus.ValetMode;
import com.highmobility.autoapi.vehiclestatus.VehicleLocation;
import com.highmobility.autoapi.vehiclestatus.VehicleTime;
import com.highmobility.utils.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 13/12/2016.
 */

public class VehicleStatus {
    com.highmobility.autoapi.incoming.VehicleStatus vehicleStatus;

    @Before
    public void setup() {
        String vehicleStatusHexString =
                "0011" + // MSB, LSB Message Identifier for Vehicle Status
                        "01"       + // Message Type for Vehicle Status
                        "4a46325348424443374348343531383639" + // VIN
                        "01"           + // All-electric powertrain
                        "06"           + // Model name is 6 bytes
                        "547970652058" + // "Type X"
                        "06"           + // Car name is 6 bytes
                        "4d7920436172" + // "My Car"
                        "06"           + // License plate is 6 bytes
                        "414243313233" + // "ABC123"
                        "0F" +      // length
                        "00200D04000100010000020101030001" + // door locks
                        "0021020001" +
                        "0023080200FF32bf19999a" +
                        "002410419800004140000001000041ac000060" + // climate
                        "0025020135" + // rooftop state
                        "00270102" +
                        "00280101" + // valet mode
                        "00300842561eb941567ab1" + // location 53.530003 13.404954; // 8 feature states
                        "00330B0249F00063003C09C45A01" +
                        "00340501F5000E61" +
                        "00350100" +
                        "003603020100" +
                        "00460102" +
                        "004720010e4265726c696e205061726b696e670363054F11010a11220A000000000000" +
                        "00500811010a10200a0078";

        byte[] bytes = Bytes.bytesFromHex(vehicleStatusHexString);

        try {
            IncomingCommand command = IncomingCommand.create(bytes);
            assertTrue(command.getClass() == com.highmobility.autoapi.incoming.VehicleStatus.class);
            vehicleStatus = (com.highmobility.autoapi.incoming.VehicleStatus)command;
        } catch (CommandParseException e) {
            e.printStackTrace();
            fail("init failed");
        }
    }

    @Test
    public void states_size() {
        assertTrue(vehicleStatus.getFeatureStates().length == 15);
    }

    @Test
    public void vin() {
        assertTrue(vehicleStatus.getVin().equals("JF2SHBDC7CH451869"));
    }

    @Test
    public void power_train() {
        assertTrue(vehicleStatus.getPowerTrain() == com.highmobility.autoapi.incoming.VehicleStatus.PowerTrain.ALLELECTRIC);
    }

    @Test
    public void model_name() {
        assertTrue(vehicleStatus.getModelName().equals("Type X"));
    }

    @Test
    public void car_name() {
        assertTrue(vehicleStatus.getName().equals("My Car"));
    }

    @Test
    public void license_plate() {
        assertTrue(vehicleStatus.getLicensePlate().equals("ABC123"));
    }

    @Test
    public void unknown_state() {
        byte[] bytes = Bytes.bytesFromHex("0011014a463253484244433743483435313836390106547970652058064d7920436172064142433132330300590101002102000100270102");
        try {
            vehicleStatus = new com.highmobility.autoapi.incoming.VehicleStatus(bytes);
        } catch (CommandParseException e) {
            e.printStackTrace();
            fail("init failed");
        }

        assertTrue(vehicleStatus.getFeatureStates().length == 2);

        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            assertTrue(vehicleStatus.getFeatureStates()[i] != null);
        }
    }

    @Test
    public void door_locks() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.DOOR_LOCKS) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == DoorLocks.class);

        assertTrue(((DoorLocks)state).getFrontLeft() != null);
        assertTrue(((DoorLocks)state).getFrontRight() != null);
        assertTrue(((DoorLocks)state).getRearLeft() != null);
        assertTrue(((DoorLocks)state).getRearRight() != null);

        assertTrue(((DoorLocks)state).getFrontLeft().getPosition() == DoorLockState.DoorPosition.OPEN);
        assertTrue(((DoorLocks)state).getFrontLeft().getLockState() == DoorLockState.LockState.UNLOCKED);

        assertTrue(((DoorLocks)state).getFrontRight().getPosition() == DoorLockState.DoorPosition.CLOSED);
        assertTrue(((DoorLocks)state).getFrontRight().getLockState() == DoorLockState.LockState.UNLOCKED);

        assertTrue(((DoorLocks)state).getRearRight().getPosition() == DoorLockState.DoorPosition.OPEN);
        assertTrue(((DoorLocks)state).getRearRight().getLockState() == DoorLockState.LockState.LOCKED);

        assertTrue(((DoorLocks)state).getRearLeft().getPosition() == DoorLockState.DoorPosition.CLOSED);
        assertTrue(((DoorLocks)state).getRearLeft().getLockState() == DoorLockState.LockState.LOCKED);
    }

    @Test
    public void trunk_access() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.TRUNK_ACCESS) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == TrunkAccess.class);

        if (state.getClass() == TrunkAccess.class) {
            assertTrue(((TrunkAccess)state).getLockState() == TrunkState.LockState.UNLOCKED);
            assertTrue(((TrunkAccess)state).getPosition() == TrunkState.Position.OPEN);
        }
    }

    @Test
    public void remote_control() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.REMOTE_CONTROL) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == RemoteControl.class);
        assertTrue(((RemoteControl)state).getState() == RemoteControl.State.STARTED);
    }

    @Test
    public void charging() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.CHARGING) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == Charging.class);
        assertTrue(((Charging)state).getChargingState() == ChargeState.ChargingState.CHARGING);
        assertTrue(((Charging)state).getEstimatedRange() == 255f);
        assertTrue(((Charging)state).getBatteryLevel() == .5f);
        assertTrue(((Charging)state).getBatteryCurrent() == -.6f);
    }

    @Test
    public void climate() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.CLIMATE) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == Climate.class);

        assertTrue(((Climate)state).getInsideTemperature() == 19f);
        assertTrue(((Climate)state).getOutsideTemperature() == 12f);

        assertTrue(((Climate)state).isHvacActive() == true);
        assertTrue(((Climate)state).isDefoggingActive() == false);
        assertTrue(((Climate)state).isDefrostingActive() == false);
        assertTrue(((Climate)state).getDefrostingTemperature() == 21.5f);
        assertTrue(((Climate)state).isAutoHvacConstant() == false);

        boolean[] autoHvacStates = ((Climate)state).getHvacActiveOnDays();
        assertTrue(autoHvacStates != null);
        assertTrue(autoHvacStates.length == 7);

        assertTrue(autoHvacStates[0] == false);
        assertTrue(autoHvacStates[5] == true);
        assertTrue(autoHvacStates[6] == true);
    }

    @Test
    public void valetMode() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.VALET_MODE) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == ValetMode.class);
        assertTrue(((ValetMode)state).isActive() == true);
    }

    @Test
    public void vehicleLocation() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.VEHICLE_LOCATION) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == VehicleLocation.class);
        assertTrue(((VehicleLocation)state).getLatitude() == 53.530003f);
        assertTrue(((VehicleLocation)state).getLongitude() == 13.404954f);
    }

    @Test
    public void rooftopState() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.ROOFTOP) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == RooftopState.class);
        assertTrue(((RooftopState)state).getDimmingPercentage() == .01f);
        assertTrue(((RooftopState)state).getOpenPercentage() == .53f);
    }

    @Test
    public void diagnostics() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.DIAGNOSTICS) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == Diagnostics.class);
        assertTrue(((Diagnostics)state).getMileage() == 150000);
        assertTrue(((Diagnostics)state).getOilTemperature() == 99);
        assertTrue(((Diagnostics)state).getSpeed() == 60);
        assertTrue(((Diagnostics)state).getRpm() == 2500);
        assertTrue(((Diagnostics)state).getFuelLevel() == .9f);
        assertTrue(((Diagnostics)state).getWasherFluidLevel() == DiagnosticsState.WasherFluidLevel.FULL);
    }

    @Test
    public void maintenance() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.MAINTENANCE) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == Maintenance.class);
        assertTrue(((Maintenance)state).getDaysToNextService() == 501);
        assertTrue(((Maintenance)state).getKilometersToNextService() == 3681);
    }

    @Test
    public void engine() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.ENGINE) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == Engine.class);
        assertTrue(((Engine)state).isOn() == false);
    }

    @Test
    public void lights() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.LIGHTS) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == Lights.class);
        assertTrue(((Lights)state).getFrontExteriorLightState() == LightsState.FrontExteriorLightState.ACTIVE_WITH_FULL_BEAM);
        assertTrue(((Lights)state).isRearExteriorLightActive() == true);
        assertTrue(((Lights)state).isInteriorLightActive() == false);
    }

    @Test
    public void theftAlarm() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.THEFT_ALARM) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == TheftAlarm.class);
        assertTrue(((TheftAlarm)state).getState() == TheftAlarmState.State.TRIGGERED);
    }

    @Test
    public void parking() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.PARKING_TICKET) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == ParkingTicket.class);
        assertTrue(((ParkingTicket)state).getState() == com.highmobility.autoapi.incoming.ParkingTicket.State.STARTED);
        assertTrue(((ParkingTicket)state).getOperatorName().equals("Berlin Parking"));
        assertTrue(((ParkingTicket)state).getOperatorTicketId() == 6489423);
        assertTrue(((ParkingTicket)state).getTicketEndDate() == null);

        String string = "2017-01-10T17:34:10";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = format.parse(string);

            Date commandDate = ((ParkingTicket)state).getTicketStartDate();
            assertTrue((format.format(commandDate).equals(format.format(date))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void vehicleTime() {
        FeatureState state = null;
        for (int i = 0; i < vehicleStatus.getFeatureStates().length; i++) {
            FeatureState iteratingState = vehicleStatus.getFeatureStates()[i];
            if (iteratingState.getFeature() == Identifier.VEHICLE_TIME) {
                state = iteratingState;
                break;
            }
        }

        assertTrue(state != null);
        assertTrue(state.getClass() == VehicleTime.class);

        Calendar c = ((VehicleTime)state).getVehicleTime();

        float rawOffset = c.getTimeZone().getRawOffset();
        float expectedRawOffset = 120 * 60 * 1000;
        assertTrue(rawOffset == expectedRawOffset);
        Date commandDate = c.getTime();

        String string = "2017-01-10T14:32:10"; // hour is 16 - 2 = 14 because timezone is UTC+2
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date date = format.parse(string);
            assertTrue((format.format(commandDate).equals(format.format(date))));
            // msSince1970 are random
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
