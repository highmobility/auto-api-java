package com.highmobility.autoapi;

import com.highmobility.autoapi.incoming.ChargeState;
import com.highmobility.autoapi.incoming.ClimateState;
import com.highmobility.autoapi.incoming.ControlMode;
import com.highmobility.autoapi.incoming.DeliveredParcels;
import com.highmobility.autoapi.incoming.DiagnosticsState;
import com.highmobility.autoapi.incoming.DriverFatigue;
import com.highmobility.autoapi.incoming.Failure;
import com.highmobility.autoapi.incoming.IgnitionState;
import com.highmobility.autoapi.incoming.KeyfobPosition;
import com.highmobility.autoapi.incoming.LightsState;
import com.highmobility.autoapi.incoming.LockState;
import com.highmobility.autoapi.incoming.Maintenance;
import com.highmobility.autoapi.incoming.Notification;
import com.highmobility.autoapi.incoming.NotificationAction;
import com.highmobility.autoapi.incoming.ParkingTicket;
import com.highmobility.autoapi.incoming.RooftopState;
import com.highmobility.autoapi.incoming.SendMessage;
import com.highmobility.autoapi.incoming.TheftAlarmState;
import com.highmobility.autoapi.incoming.TrunkState;
import com.highmobility.autoapi.incoming.ValetMode;
import com.highmobility.autoapi.incoming.VehicleLocation;
import com.highmobility.autoapi.incoming.VehicleTime;
import com.highmobility.autoapi.incoming.WindowsState;
import com.highmobility.autoapi.incoming.WindscreenState;
import com.highmobility.utils.Bytes;

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
 * Created by ttiganik on 15/09/16.
 */
public class IncomingCommand {
    @Test
    public void deliveredParcels_init() {
        /*
        0x00, 0x32, # MSB, LSB Message Identifier for Delivered Parcels
        0x01,       # Message Type for Delivered Parcels
        0x02,       # Two parcels in the car
        0x4B87EFA8B4A6EC08, # Tracking number for first parcel
        0x4B87EFA8B4A6EC09  # Tracking number for second parcel
         */
        byte[] bytes = Bytes.bytesFromHex("003201024B87EFA8B4A6EC084B87EFA8B4A6EC09");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == DeliveredParcels.class);
        assertTrue(((DeliveredParcels)command).getDeliveredParcels().length == 2);
        assertTrue(((DeliveredParcels)command).getDeliveredParcels()[0].equals("4B87EFA8B4A6EC08"));
        assertTrue(((DeliveredParcels)command).getDeliveredParcels()[1].equals("4B87EFA8B4A6EC09"));
    }

    @Test
    public void failure_wakeup() {
        byte[] bytes = Bytes.bytesFromHex("00020100210001");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == Failure.class);
        assertTrue(((Failure)command).getFailedType() == Command.TrunkAccess.GET_TRUNK_STATE);
        assertTrue(((Failure)command).getFailureReason() == Failure.Reason.UNAUTHORIZED);
    }

    @Test
    public void failure_vs() {
        byte[] bytes = Bytes.bytesFromHex("00020100100101");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == Failure.class);
        assertTrue(command.is(Command.FailureMessage.FAILURE_MESSAGE));
        assertTrue(((Failure)command).getFailedType() == Command.Capabilities.CAPABILITIES);
        assertTrue(((Failure)command).getFailureReason() == Failure.Reason.UNAUTHORIZED);
    }

    @Test
    public void controlMode_init() {
        byte[] bytes = Bytes.bytesFromHex("002701020032");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == ControlMode.class);
        assertTrue(((ControlMode)command).getMode() == ControlMode.Mode.STARTED);
        assertTrue(((ControlMode)command).getAngle() == 50);
    }

    @Test
    public void lockstate_init() {
        byte[] bytes = Bytes.bytesFromHex("00200104000100010000020001030101");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == LockState.class);

        assertTrue(((LockState)command).getFrontLeft() != null);
        assertTrue(((LockState)command).getFrontRight() != null);
        assertTrue(((LockState)command).getRearLeft() != null);
        assertTrue(((LockState)command).getRearRight() != null);

        assertTrue(((LockState)command).getFrontLeft().getPosition() == DoorLockState.DoorPosition.OPEN);
        assertTrue(((LockState)command).getFrontLeft().getLockState() == DoorLockState.LockState.UNLOCKED);

        assertTrue(((LockState)command).getFrontRight().getPosition() == DoorLockState.DoorPosition.CLOSED);
        assertTrue(((LockState)command).getFrontRight().getLockState() == DoorLockState.LockState.UNLOCKED);

        assertTrue(((LockState)command).getRearRight().getPosition() == DoorLockState.DoorPosition.CLOSED);
        assertTrue(((LockState)command).getRearRight().getLockState() == DoorLockState.LockState.LOCKED);

        assertTrue(((LockState)command).getRearLeft().getPosition() == DoorLockState.DoorPosition.OPEN);
        assertTrue(((LockState)command).getRearLeft().getLockState() == DoorLockState.LockState.LOCKED);

    }

    @Test
    public void lockstate_two_front_doors() {
        byte[] bytes = Bytes.bytesFromHex("00200102000100010000");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == LockState.class);

        assertTrue(((LockState)command).getFrontLeft() != null);
        assertTrue(((LockState)command).getFrontRight() != null);
        assertTrue(((LockState)command).getRearLeft() == null);
        assertTrue(((LockState)command).getRearRight() == null);

        assertTrue(((LockState)command).getFrontLeft().getPosition() == DoorLockState.DoorPosition.OPEN);
        assertTrue(((LockState)command).getFrontLeft().getLockState() == DoorLockState.LockState.UNLOCKED);

        assertTrue(((LockState)command).getFrontRight().getPosition() == DoorLockState.DoorPosition.CLOSED);
        assertTrue(((LockState)command).getFrontRight().getLockState() == DoorLockState.LockState.UNLOCKED);
    }

    @Test
    public void rooftopState_init_random() {
        byte[] bytes = Bytes.bytesFromHex("0025010135");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == RooftopState.class);
        assertTrue(((RooftopState)command).getDimmingPercentage() == .01f);
        assertTrue(((RooftopState)command).getOpenPercentage() == .53f);
    }

    @Test
    public void rooftopState_init_opaque() {
        byte[] bytes = Bytes.bytesFromHex("0025016400");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == RooftopState.class);
        assertTrue(((RooftopState)command).getDimmingPercentage() == 1f);
        assertTrue(((RooftopState)command).getOpenPercentage() == 0f);
    }

    @Test
    public void trunkState_init() {
        byte[] bytes = Bytes.bytesFromHex("0021010001");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == TrunkState.class);
        assertTrue(((TrunkState)command).getLockState() == TrunkState.LockState.UNLOCKED);
        assertTrue(((TrunkState)command).getPosition() == TrunkState.Position.OPEN);
    }

    @Test
    public void charging_init() {
        byte[] bytes = Bytes.bytesFromHex("0023010200FF32BF19999A01905A003C3F5EB85201");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == ChargeState.class);
        assertTrue(((ChargeState)command).getChargingState() == ChargeState.ChargingState.CHARGING);
        assertTrue(((ChargeState)command).getEstimatedRange() == 255f);
        assertTrue(((ChargeState)command).getBatteryLevel() == .5f);
        assertTrue(((ChargeState)command).getChargerVoltage() == 400f);
        assertTrue(((ChargeState)command).getChargeLimit() == .9f);
        assertTrue(((ChargeState)command).getTimeToCompleteCharge() == 60f);
        assertTrue(((ChargeState)command).getChargingRate() == .87f);
        assertTrue(((ChargeState)command).getBatteryCurrent() == -.6f);
        assertTrue(((ChargeState)command).getChargePortState() == ChargeState.PortState.OPEN);
    }

    @Test
    public void climate() {
        byte[] bytes = Bytes.bytesFromHex("002401419800004140000041ac000041ac000001000041ac00006000000000000000000000071E071F");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == ClimateState.class);
        assertTrue(((ClimateState)command).getInsideTemperature() == 19f);
        assertTrue(((ClimateState)command).getOutsideTemperature() == 12f);
        assertTrue(((ClimateState)command).getDriverTemperatureSetting() == 21.5f);
        assertTrue(((ClimateState)command).getPassengerTemperatureSetting() == 21.5f);

        assertTrue(((ClimateState)command).isHvacActive() == true);
        assertTrue(((ClimateState)command).isDefoggingActive() == false);
        assertTrue(((ClimateState)command).isDefrostingActive() == false);
        assertTrue(((ClimateState)command).getDefrostingTemperature() == 21.5f);

        assertTrue(((ClimateState)command).isAutoHvacConstant() == false);
        AutoHvacState[] autoHvacStates = ((ClimateState)command).getAutoHvacStates();
        assertTrue(autoHvacStates != null);
        assertTrue(autoHvacStates.length == 7);

        assertTrue(autoHvacStates[0].isActive() == false);

        assertTrue(autoHvacStates[5].isActive() == true);
        assertTrue(autoHvacStates[5].getDay() == 5);
        assertTrue(autoHvacStates[5].getStartHour() == 7);
        assertTrue(autoHvacStates[5].getStartMinute() == 30);

        assertTrue(autoHvacStates[6].isActive() == true);
        assertTrue(autoHvacStates[6].getDay() == 6);
        assertTrue(autoHvacStates[6].getStartHour() == 7);
        assertTrue(autoHvacStates[6].getStartMinute() == 31);
    }

    @Test
    public void valetMode() {
        byte[] bytes = Bytes.bytesFromHex("00280101");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == ValetMode.class);
        assertTrue(((ValetMode)command).isActive() == true);
    }

    @Test
    public void location() {
        byte[] bytes = Bytes.bytesFromHex("0030014252147d41567ab1");
        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == VehicleLocation.class);
        assertTrue(((VehicleLocation)command).getLatitude() == 52.520008f);
        assertTrue(((VehicleLocation)command).getLongitude() == 13.404954f);
    }

    @Test
    public void diagnostics() {
        byte[] bytes = Bytes.bytesFromHex("0033010249F00063003C09C45A0104004013d70a01401666660240166666034013d70a");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == DiagnosticsState.class);
        assertTrue(((DiagnosticsState)command).getMileage() == 150000);
        assertTrue(((DiagnosticsState)command).getOilTemperature() == 99);
        assertTrue(((DiagnosticsState)command).getSpeed() == 60);
        assertTrue(((DiagnosticsState)command).getRpm() == 2500);
        assertTrue(((DiagnosticsState)command).getFuelLevel() == .9f);
        assertTrue(((DiagnosticsState)command).getWasherFluidLevel() == DiagnosticsState.WasherFluidLevel.FULL);
        assertTrue(((DiagnosticsState)command).getFrontLeftTirePressure() == 2.31f);
        assertTrue(((DiagnosticsState)command).getFrontRightTirePressure() == 2.35f);
        assertTrue(((DiagnosticsState)command).getRearRightTirePressure() == 2.35f);
        assertTrue(((DiagnosticsState)command).getRearLeftTirePressure() == 2.31f);
    }

    @Test
    public void maintenance() {
        byte[] bytes = Bytes.bytesFromHex("00340101F5000E61");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == Maintenance.class);
        assertTrue(((Maintenance)command).getDaysToNextService() == 501);
        assertTrue(((Maintenance)command).getKilometersToNextService() == 3681);
    }

    @Test
    public void engine() {
        byte[] bytes = Bytes.bytesFromHex("00350101");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == IgnitionState.class);
        assertTrue(((IgnitionState)command).isOn() == true);
    }

    @Test
    public void lights() {
        byte[] bytes = Bytes.bytesFromHex("003601020100FF0000");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == LightsState.class);
        assertTrue(((LightsState)command).getFrontExteriorLightState() == LightsState.FrontExteriorLightState.ACTIVE_WITH_FULL_BEAM);
        assertTrue(((LightsState)command).isRearExteriorLightActive() == true);
        assertTrue(((LightsState)command).isInteriorLightActive() == false);
//        assertTrue(((Lights)command).getAmbientColor() == Color.RED); // Color is not mocked
    }

    @Test
    public void sendMessage() {
        byte[] bytes = Bytes.bytesFromHex("0037010e2b31203535352d3535352d35353501D554696d6520746f2067657420737461727465642e2057697468207468652053444b7320697420697320706f737369626c6520746f206275696c64206e65772074797065206f662061707073206f7220696e74656772617465206578697374696e6720617070732077697468206361722066756e6374696f6e732e2054686520415049732068617665206265656e2064657369676e656420746f2070726f7669646520612073696d706c6520696e7465726661636520746f20746865206361722c207468617420697320626f74682075736566756c20616e642073696d706c6520746f20657874656e642e2054696d6520746f2067657420737461727465642e2057697468207468652053444b7320697420697320706f737369626c6520746f206275696c64206e65772074797065206f662061707073206f7220696e74656772617465206578697374696e6720617070732077697468206361722066756e6374696f6e732e2054686520415049732068617665206265656e2064657369676e656420746f2070726f7669646520612073696d706c6520696e7465726661636520746f20746865206361722c207468617420697320626f74682075736566756c20616e642073696d706c6520746f20657874656e642e");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == SendMessage.class);
        assertTrue(((SendMessage)command).getRecipientHandle().equals("+1 555-555-555"));
        assertTrue(((SendMessage)command).getText().equals("Time to get started. With the SDKs it is possible to build new type of apps or integrate existing apps with car functions. The APIs have been designed to provide a simple interface to the car, that is both useful and simple to extend. Time to get started. With the SDKs it is possible to build new type of apps or integrate existing apps with car functions. The APIs have been designed to provide a simple interface to the car, that is both useful and simple to extend."));
    }

    @Test
    public void notificationAction() {
        byte[] bytes = Bytes.bytesFromHex("003801FE");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == NotificationAction.class);
        assertTrue(((NotificationAction)command).getActionIdentifier() == -2);
    }

    @Test
    public void notification() {
        byte[] bytes = Bytes.bytesFromHex("00380000115374617274206e617669676174696f6e3f0200024e6f0103596573");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == Notification.class);
        assertTrue(((Notification)command).getText().equals("Start navigation?"));
        assertTrue(((Notification)command).getNotificationActions().length == 2);
        assertTrue(((Notification)command).getNotificationActions()[0].getIdentifier() == 0);
        assertTrue(((Notification)command).getNotificationActions()[0].getText().equals("No"));
        assertTrue(((Notification)command).getNotificationActions()[1].getIdentifier() == 1);
        assertTrue(((Notification)command).getNotificationActions()[1].getText().equals("Yes"));
    }

    @Test
    public void windscreenState() {
        byte[] bytes = Bytes.bytesFromHex("0042010203024312025f11010a102005");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == WindscreenState.class);
        assertTrue(((WindscreenState)command).getWiperState() == WindscreenState.WiperState.AUTOMATIC);
        assertTrue(((WindscreenState)command).getWiperIntensity() == WindscreenState.WiperIntensity.LEVEL_3);
        assertTrue(((WindscreenState)command).getWindscreenDamage() == WindscreenState.WindscreenDamage.DAMAGE_SMALLER_THAN_1);
        assertTrue(((WindscreenState)command).getWindscreenReplacementState() == WindscreenState.WindscreenReplacementState.REPLACEMENT_NEEDED);
        WindscreenDamagePosition position = ((WindscreenState)command).getWindscreenDamagePosition();

        assertTrue(position.getWindscreenSizeHorizontal() == 4);
        assertTrue(position.getWindscreenSizeVertical() == 3);

        assertTrue(position.getDamagePositionX() == 1);
        assertTrue(position.getDamagePositionY() == 2);

        assertTrue(((WindscreenState)command).getDamageConfidence() == .95f);
        //2017-07-29T14:09:31+00:00
        String string = "2017-01-10T16:32:05";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = format.parse(string);
            Date commandDate = ((WindscreenState)command).getDamageDetectionTime();
            assertTrue((format.format(commandDate).equals(format.format(date))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fatigueLevel() {
        byte[] bytes = Bytes.bytesFromHex("00410101");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == DriverFatigue.class);
        assertTrue(((DriverFatigue)command).getFatigueLevel() == DriverFatigue.FatigueLevel.PAUSE_RECOMMENDED);
    }

    @Test
    public void theftAlarmState() {
        byte[] bytes = Bytes.bytesFromHex("00460101");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == TheftAlarmState.class);
        assertTrue(((TheftAlarmState)command).getState() == TheftAlarmState.State.ARMED);
    }

    @Test
    public void parkingTicket() {
        byte[] bytes = Bytes.bytesFromHex("004701010e4265726c696e205061726b696e670363054F11010a11220000000000");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == ParkingTicket.class);
        assertTrue(((ParkingTicket)command).getState() == ParkingTicket.State.STARTED);
        assertTrue(((ParkingTicket)command).getOperatorName().equals("Berlin Parking"));
        assertTrue(((ParkingTicket)command).getOperatorTicketId() == 6489423);
        assertTrue(((ParkingTicket)command).getTicketEndDate() == null);


        String string = "2017-01-10T17:34:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = format.parse(string);
            Date commandDate = ((ParkingTicket)command).getTicketStartDate();
            assertTrue((format.format(commandDate).equals(format.format(date))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void keyfobPosition() {
        byte[] bytes = Bytes.bytesFromHex("00480105");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == KeyfobPosition.class);
        assertTrue(((KeyfobPosition) command).getPosition() == KeyfobPosition.Position.INSIDE_CAR);
    }

    @Test
    public void vehicleTime() {
        /*
            0x11, # 2017
            0x01, # January
            0x0a, # the 10th
            0x10, # 16h
            0x20, # 32min

            0x0078 # +120 min UTC time offset
        */

        byte[] bytes = Bytes.bytesFromHex("00500111010a1020010078");
        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == VehicleTime.class);
        Calendar c = ((VehicleTime)command).getVehicleTime();
        float rawOffset = c.getTimeZone().getRawOffset();
        float expectedRawOffset = 120 * 60 * 1000;
        assertTrue(rawOffset == expectedRawOffset);
        Date commandDate = c.getTime();

        String string = "2017-01-10T14:32:01"; // hour is 16 - 2 = 14 because timezone is UTC+2
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

    @Test
    public void windows() {
        byte[] bytes = Bytes.bytesFromHex("004501040001010002000301");
        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }
        assertTrue(command.getClass() == WindowsState.class);

        WindowState[] states = ((WindowsState)command).getWindowStates();

        assertTrue(states.length == 4);

        for (int i = 0; i < states.length; i++) {
            WindowState state = states[i];

            switch (state.location) {
                case FRONT_LEFT:
                    assertTrue(state.getPosition() == WindowState.Position.OPEN);
                    break;
                case FRONT_RIGHT:
                    assertTrue(state.getPosition() == WindowState.Position.CLOSED);
                    break;
                case REAR_RIGHT:
                    assertTrue(state.getPosition() == WindowState.Position.CLOSED);
                    break;
                case REAR_LEFT:
                    assertTrue(state.getPosition() == WindowState.Position.OPEN);
                    break;
            }
        }

    }
}
