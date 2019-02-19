package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.CommandWithProperties;
import com.highmobility.autoapi.GasFlapState;
import com.highmobility.autoapi.ParkingBrakeState;
import com.highmobility.autoapi.RooftopState;
import com.highmobility.autoapi.SeatsState;
import com.highmobility.autoapi.property.DashboardLight;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyTimestamp;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

public class CommandTest {
    String parkingBrakeCommand = "00580101000401000101";

    @Test public void failureProperty() {
        // want to test parsing into the command. values are tested in property test.
        Bytes bytes = new Bytes("002501" +
                "01000401000164" +
                /*"02000100"*/  // << this failed
                /*"03000101" +*/ // << this failed
                "04000401000102" +
                "A5001001000D02000A54727920696e20343073" +
                "A5001001000D03000A54727920696e20343073");

        CommandWithProperties command = (CommandWithProperties) CommandResolver.resolve(bytes);
        assertTrue(command.getPropertyFailures().length == 2);
        assertTrue(command.getPropertyFailure((byte) 0x02) != null);
        assertTrue(command.getPropertyFailure((byte) 0x03) != null);
        assertTrue(command.getPropertyFailure((byte) 0x04) == null);

        // TBODO:
    }

    @Test
    public void testAddPropertyTimestamp() throws ParseException {
        // size 9 + full prop size
        String parkingStateProperty = "01000401000101";
        String propertyTimestamp = "A4001301001011010A112200000001" +
                parkingStateProperty;
        Bytes bytes = new Bytes(parkingBrakeCommand + propertyTimestamp);

        String expectedDate = "2017-01-10T17:34:00";
        Calendar calendar = TestUtils.getUTCCalendar(expectedDate);

        // TBODO:
        /*ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setIsActive(true, calendar);

        ParkingBrakeState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));*/
    }

    @Test public void timestampInTheMiddle() throws ParseException {
        Bytes bytes = new Bytes("002501" +
                "01000401000164" +
                "02000401000100" + "A4001301001011010A11220000000202000401000100" +
                "03000401000101" +
                "04000401000102");
        String expectedDate = "2017-01-10T17:34:00+0000";
        Calendar calendar = TestUtils.getUTCCalendar(expectedDate);

        RooftopState state = (RooftopState) CommandResolver.resolve(bytes);
        Calendar timestamp = state.getPropertyTimestamp(state.getOpenPercentage()).getTimestamp();
        assertTrue(TestUtils.dateIsSame(timestamp, expectedDate));

        // TBODO:
        /*RooftopState.Builder builder = new RooftopState.Builder();
        builder.setDimmingPercentage(1f, null);
        builder.setOpenPercentage(0f, calendar);
        builder.setConvertibleRoofState(ConvertibleRoofState.OPEN, null);
        builder.setSunroofTiltState(SunroofTiltState.HALF_TILTED, null);

        assertTrue(builder.build().equals(bytes));*/
    }

    @Test
    public void testPropertyTimestampParsed() throws ParseException {
        String parkingStateProperty = "01000401000101";
        Bytes bytes =
                new Bytes(parkingBrakeCommand + "A4001301001011010A112200000001" + parkingStateProperty);
        String expectedDate = "2017-01-10T17:34:00+0000";
        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);

        PropertyTimestamp timestamp = command.getPropertyTimestamps((byte) 0x01)[0];
        assertTrue(TestUtils.dateIsSame(timestamp.getTimestamp(), expectedDate));
        assertTrue(timestamp.getAdditionalData().equals(parkingStateProperty));
    }

    @Test public void propertyTimestampWithField() throws ParseException {
        /*
        String parkingStateProperty = "01000401000101";
        Bytes bytes =
                new Bytes(parkingBrakeCommand + "A4001301001011010A112200000001" + parkingStateProperty);

         */

        String parkingStateProperty = "01000401000101";
        Bytes bytes = new Bytes(parkingBrakeCommand + "A4001301001011010A112200000001" +
                parkingStateProperty);
        String expectedDate = "2017-01-10T17:34:00+0000";
        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);

        PropertyTimestamp timestamp = command.getPropertyTimestamp(command.isActive());
        assertTrue(TestUtils.dateIsSame(timestamp.getTimestamp(), expectedDate));
        assertTrue(timestamp.getAdditionalData().equals(parkingStateProperty));
    }

    @Test public void propertyTimestampWithObjectFromArray() throws ParseException {
        Bytes bytes = new Bytes("005601" +
                "0200050100020201" +
                "0200050100020300" + "A4001401001111010A112200000002" + "0200050100020300" +
                "0300050100020201" +
                "0300050100020300");
        SeatsState command = (SeatsState) CommandResolver.resolve(bytes);
        String expectedDate = "2017-01-10T17:34:00+0000";

        PropertyTimestamp timestamp = command.getPropertyTimestamp(command.getPersonsDetected()[1]);
        assertTrue(TestUtils.dateIsSame(timestamp.getTimestamp(), expectedDate));
    }

    @Test public void invalidProperty() {
        // test that invalid gasflapstate just sets the property to null and keeps the base property
        Bytes bytes = new Bytes("00400102000103"); // 3 is invalid gasflap lock state
        GasFlapState state = (GasFlapState) CommandResolver.resolve(bytes);

        assertTrue(state.getLock() == null);
        assertTrue(state.getProperty((byte) 0x02) != null);
    }

    @Test public void propertyTimestampWithObjectFromArrayBaseType() {
        // there is no such array that only holds primitive types..
    }

    @Test public void basePropertiesArrayObjectReplaced() {
        Bytes bytes = new Bytes("006101" +
                "0100050100020000" +
                "0100050100020201" +
                "0100050100020F03" +
                "0100050100021500");
        CommandWithProperties command = (CommandWithProperties) CommandResolver.resolve(bytes);

        boolean found = false;
        for (Property property : command.getProperties()) {
            if (property instanceof DashboardLight) found = true;
            break;
        }

        assertTrue(found);
    }

    @Test public void nonce() {
        CommandWithProperties command = getCommandWithSignature();
        Bytes nonce = command.getNonce();
        assertTrue(nonce.equals("324244433743483436"));

        // build with #signature()
    }

    @Test public void signature() {
        CommandWithProperties command = getCommandWithSignature();
        assertTrue(command.getSignature().equals
                ("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6"));

        ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setIsActive(true);
        builder.setNonce(new Bytes("324244433743483436"));
        builder.setSignature(new Bytes
                ("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6"));
        ParkingBrakeState state = builder.build();
        assertTrue(state.equals(command));
    }

    @Test public void timestamp() throws ParseException {
        Bytes bytes = new Bytes(parkingBrakeCommand + "A2000B01000811010A1122000000");
        String expectedDate = "2017-01-10T17:34:00";
        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);
        assertTrue(TestUtils.dateIsSameUTC(command.getTimestamp(), expectedDate));

        Calendar calendar = TestUtils.getUTCCalendar(expectedDate);
        ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setIsActive(true);
        builder.setTimestamp(calendar);
        ParkingBrakeState state = builder.build();
        assertTrue(state.equals(bytes));
    }

    @Test public void propertyTimestamp() throws ParseException {
        // size 9 + full prop size
        String parkingStateProperty = "01000401000101";
        Bytes bytes = new Bytes(parkingBrakeCommand + "A4001301001011010A112200000004" +
                parkingStateProperty);
        String expectedDate = "2017-01-10T17:34:00+0000";

        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);

        PropertyTimestamp timestamp = command.getPropertyTimestamps((byte) 0x04)[0];
        assertTrue(TestUtils.dateIsSame(timestamp.getTimestamp(), expectedDate));
        assertTrue(timestamp.getAdditionalData().equals(parkingStateProperty));
    }

    @Test public void propertyTimestampNoData() throws ParseException {
        Bytes bytes = new Bytes(parkingBrakeCommand + "A4000C01000911010A112200000004");
        String expectedDate = "2017-01-10T17:34:00+0000";
        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);

        PropertyTimestamp timestamp = command.getPropertyTimestamps((byte) 0x04)[0];
        assertTrue(TestUtils.dateIsSame(timestamp.getTimestamp(), expectedDate));
        assertTrue(timestamp.getAdditionalData().getLength() == 0);
    }

    @Test public void signedBytes() {
        CommandWithProperties command = getCommandWithSignature();
        Bytes signedBytes = command.getSignedBytes();
        assertTrue(signedBytes.equals(new Bytes(parkingBrakeCommand + "A0000C010009324244433743483436")));
    }

    CommandWithProperties getCommandWithSignature() {
        Bytes bytes = new Bytes
                (parkingBrakeCommand +
                        "A0000C010009324244433743483436" +
                        "A100430100404D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6");
        try {
            Command command = null;
            try {
                command = CommandResolver.resolve(bytes);
            } catch (Exception e) {
                fail();
            }

            if (command instanceof CommandWithProperties) {
                return (CommandWithProperties) command;
            }

            throw new CommandParseException();
        } catch (CommandParseException e) {
            fail();
            return null;
        }
    }

    @Test public void unknownProperty() throws CommandParseException {
        Bytes bytes = new Bytes(
                "002501" +
                        "01000401000101" +
                        "1A000401000135");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(RooftopState.TYPE));

        assertTrue(command.getClass() == RooftopState.class);
        RooftopState state = (RooftopState) command;
        assertTrue(state.getDimmingPercentage() == .01f);
        assertTrue(state.getOpenPercentage() == null);
        assertTrue(state.getProperties().length == 2);

        boolean foundUnknownProperty = false;
        boolean foundDimmingProperty = false;

        for (int i = 0; i < state.getProperties().length; i++) {
            Property property = state.getProperties()[i];
            if (property.getPropertyIdentifier() == 0x1A) {
                assertTrue(property.getValueLength() == 1);
                assertTrue(Arrays.equals(property.getValueBytes(), new byte[]{0x35}));
                assertTrue(Arrays.equals(property.getPropertyBytes(), ByteUtils.bytesFromHex
                        ("1A000401000135")));
                foundUnknownProperty = true;
            } else if (property.getPropertyIdentifier() == 0x01) {
                assertTrue(property.getValueLength() == 1);
                assertTrue(Arrays.equals(property.getValueBytes(), new byte[]{0x01}));
                assertTrue(Arrays.equals(property.getPropertyBytes(), ByteUtils.bytesFromHex
                        ("01000401000101")));
                foundDimmingProperty = true;
            }
        }

        assertTrue(foundDimmingProperty == true);
        assertTrue(foundUnknownProperty == true);
    }
}
