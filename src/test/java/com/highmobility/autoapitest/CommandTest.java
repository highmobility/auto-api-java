package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.CommandWithProperties;
import com.highmobility.autoapi.GasFlapState;
import com.highmobility.autoapi.ParkingBrakeState;
import com.highmobility.autoapi.RooftopState;
import com.highmobility.autoapi.SeatsState;
import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.ConvertibleRoofState;
import com.highmobility.autoapi.property.DashboardLight;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyTimestamp;
import com.highmobility.autoapi.property.SunroofTiltState;
import com.highmobility.autoapi.property.seats.SeatLocation;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

/*
 * Timestamp + failure properties:
 *
 * property updated without failure or timestamp - every other test tests that.
 * timestamp added to the parsed property.
 * failure added to the base empty property. Base property bytes are not set.
 * For multiple properties with same identifier, timestamps only added if additional data same.
 * For single property for an identifier, can add without checking additional data.
 */
public class CommandTest {
    String parkingBrakeCommand = "00580101000101";

    @Test public void propertiesSortedWithUniversalPropertiesLast() {
        Bytes bytes = new Bytes("002501" +
                "A4000D11010A11220000000202000100" + // timestamp as first property
                "01000164" +
                "A5000D02000A54727920696e20343073" +
                "02000201"
        );

        CommandWithProperties state = (CommandWithProperties) CommandResolver.resolve(bytes);
        assertTrue(state.getProperties()[0].getPropertyIdentifier() < 3 &&
                state.getProperties()[1].getPropertyIdentifier() < 3);

        assertTrue(state.getProperties()[2].getPropertyIdentifier() < 0 &&
                state.getProperties()[3].getPropertyIdentifier() < 0);
    }

    // MARK: PropertyFailure

    @Test public void propertyFailureForSingleIdentifier() {
        // want to test parsing into the command. values are tested in property test.
        Bytes bytes = new Bytes("002501" +
                "01000164" +
                /*"02000100"*/  // << open failed
                /*"03000101" +*/ // << convertible roof failed
                "04000102" +
                "A5000D02000A54727920696e20343073" +
                "A5000D03010A54727920696e20343073");

        RooftopState command = (RooftopState) CommandResolver.resolve(bytes);

        // failure in array
        assertTrue(command.getPropertyFailures().length == 2);
        assertTrue(command.getPropertyFailure((byte) 0x02) != null);
        assertTrue(command.getPropertyFailure((byte) 0x03) != null);
        assertTrue(command.getPropertyFailure((byte) 0x04) == null);

        // failure in property
        assertTrue(command.getOpenPercentage().getFailure().getFailureReason() == PropertyFailure.Reason.RATE_LIMIT);
        assertTrue(command.getOpenPercentage().getValue() == null);
        assertTrue(command.getConvertibleRoofState().getFailure().getFailureReason() == PropertyFailure.Reason.EXECUTION_TIMEOUT);
        assertTrue(command.getConvertibleRoofState().getValue() == null);

        // TBODO:

        RooftopState.Builder builder = new RooftopState.Builder();
        builder.setDimmingPercentage(new PercentageProperty(1f));
        builder.setSunroofTiltState(new SunroofTiltState(SunroofTiltState.Value.HALF_TILTED));
        builder.setOpenPercentage(new PercentageProperty(null, null,
                new PropertyFailure(PropertyFailure.Reason.RATE_LIMIT, "Try in 40s")));
        builder.setConvertibleRoofState(new ConvertibleRoofState(null, null,
                new PropertyFailure(PropertyFailure.Reason.EXECUTION_TIMEOUT, "Try " +
                        "in 40s")));

        command = builder.build();
        assertTrue(TestUtils.bytesTheSame(command, bytes));
    }

    @Test public void propertyFailureForMultipleIdentifiers() {
        // Cannot parse this to a property, because it will not exist. We dont create empty
        // objects for all of the possible values.
        Bytes bytes = new Bytes("005601" +
                /*"0300020201" +
                "0300020300" +*/
                "A5000D02000A54727920696e20343073"); // this is a failure for seatbelt fastened.

        SeatsState command = (SeatsState) CommandResolver.resolve(bytes);
        assertTrue(command.getSeatBeltFastened(SeatLocation.REAR_LEFT) == null);
        assertTrue(command.getPropertyFailure((byte) 0x02) != null);
    }

    // MARK: PropertyTimestamp

    @Test
    public void propertyTimestampParsedIntoArrayAndProperty() throws ParseException {
        // size 9 + full prop size
        String parkingStateProperty = "01000101";
        String propertyTimestamp = "A4000D11010A112200000001" +
                parkingStateProperty;
        Bytes bytes = new Bytes(parkingBrakeCommand + propertyTimestamp);

        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);

        String expectedDate = "2017-01-10T17:34:00";
        Calendar calendar = TestUtils.getUTCCalendar(expectedDate);
        assertTrue(TestUtils.dateIsSameIgnoreTimezone(command.isActive().getTimestamp(), calendar));

        // TBODO:
        /*ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setIsActive(true, calendar);

        ParkingBrakeState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));*/
    }

    @Test public void propertyTimestampInTheMiddleOfProperties() {
        Bytes bytes = new Bytes("002501" +
                "01000164" +
                "02000100" + "A4000D11010A11220000000202000100" + // with additional data
                "03000101" + "A4000911010A112200000003" + // without additional data
                "04000102");

        // timestamp in array
        RooftopState state = (RooftopState) CommandResolver.resolve(bytes);

        // timestamp in property
        assertTrue(state.getOpenPercentage().getTimestamp() != null);
        assertTrue(state.getConvertibleRoofState().getTimestamp() != null);

        // TBODO:
        /*RooftopState.Builder builder = new RooftopState.Builder();
        builder.setDimmingPercentage(1f, null);
        builder.setOpenPercentage(0f, calendar);
        builder.setConvertibleRoofState(ConvertibleRoofState.OPEN, null);
        builder.setSunroofTiltState(SunroofTiltState.HALF_TILTED, null);

        assertTrue(builder.build().equals(bytes));*/
    }

    @Test
    public void propertyTimestampAdditionalDataUsedIfMultipleProperties() throws ParseException {
        // Only the property with same additional data should have the timestamp

        Bytes bytes = new Bytes("005601" +
                "0200020201" +
                "0200020300" + "A4000E11010A112200000002" + "0200020300" +
                "0300020201" +
                "0300020300");

    /*
    REAR_RIGHT((byte) 0x02),
    REAR_LEFT((byte) 0x03),
    */
        SeatsState command = (SeatsState) CommandResolver.resolve(bytes);
        assertTrue(command.getPersonDetection(SeatLocation.REAR_LEFT).getTimestamp() != null);
        assertTrue(command.getPersonDetection(SeatLocation.REAR_RIGHT).getTimestamp() == null);
    }

    @Test public void propertyTimestamp() throws ParseException {
        // size 9 + full prop size
        String parkingStateProperty = "01000101";

        Bytes bytes = new Bytes(parkingBrakeCommand + "A4000D11010A112200000001" +
                parkingStateProperty);
        String expectedDate = "2017-01-10T17:34:00+0000";
        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);

        Calendar timestamp = command.isActive().getTimestamp();
        assertTrue(TestUtils.dateIsSame(timestamp, expectedDate));

        assertTrue(command.getPropertyTimestamps((byte) 0x01)[0].getAdditionalData().equals(parkingStateProperty));
    }

    @Test public void propertyTimestampNoData() throws ParseException {
        Bytes bytes = new Bytes(parkingBrakeCommand + "A4000911010A112200000004");
        String expectedDate = "2017-01-10T17:34:00+0000";
        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);

        PropertyTimestamp timestamp = command.getPropertyTimestamps((byte) 0x04)[0];
        assertTrue(TestUtils.dateIsSame(timestamp.getCalendar(), expectedDate));
        assertTrue(timestamp.getAdditionalData().getLength() == 0);
    }

    // MARK: Universal Properties

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
        Bytes bytes = new Bytes("006101010002000001000202010100020F030100021500");
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
        builder.setIsActive(new BooleanProperty(true));
        builder.setNonce(new Bytes("324244433743483436"));
        builder.setSignature(new Bytes
                ("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6"));
        ParkingBrakeState state = builder.build();
        assertTrue(state.equals(command));
    }

    @Test public void timestamp() throws ParseException {
        Bytes bytes = new Bytes(parkingBrakeCommand + "A2000811010A1122000000");
        String expectedDate = "2017-01-10T17:34:00";
        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);
        assertTrue(TestUtils.dateIsSameUTC(command.getTimestamp(), expectedDate));

        Calendar calendar = TestUtils.getUTCCalendar(expectedDate);
        ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setIsActive(new BooleanProperty(true));
        builder.setTimestamp(calendar);
        ParkingBrakeState state = builder.build();
        assertTrue(state.equals(bytes));
    }

    @Test public void signedBytes() {
        CommandWithProperties command = getCommandWithSignature();
        Bytes signedBytes = command.getSignedBytes();
        assertTrue(signedBytes.equals(new Bytes(parkingBrakeCommand + "A00009324244433743483436")));
    }

    CommandWithProperties getCommandWithSignature() {
        Bytes bytes = new Bytes
                (parkingBrakeCommand +
                        "A00009324244433743483436A100404D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6");
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

    @Test public void unknownProperty() {
        Bytes bytes = new Bytes(
                "002501" +
                        "01000101" +
                        "1A000135");

        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(RooftopState.TYPE));

        assertTrue(command.getClass() == RooftopState.class);
        RooftopState state = (RooftopState) command;
        assertTrue(state.getDimmingPercentage().getValue() == .01f);
        assertTrue(state.getOpenPercentage().getValue() == null);
        assertTrue(state.getProperties().length == 2);

        boolean foundUnknownProperty = false;
        boolean foundDimmingProperty = false;

        for (int i = 0; i < state.getProperties().length; i++) {
            Property property = state.getProperties()[i];
            if (property.getPropertyIdentifier() == 0x1A) {
                assertTrue(property.getValueLength() == 1);
                assertTrue(Arrays.equals(property.getValueBytes(), new byte[]{0x35}));
                assertTrue(Arrays.equals(property.getByteArray(), ByteUtils.bytesFromHex
                        ("1A000135")));
                foundUnknownProperty = true;
            } else if (property.getPropertyIdentifier() == 0x01) {
                assertTrue(property.getValueLength() == 1);
                assertTrue(Arrays.equals(property.getValueBytes(), new byte[]{0x01}));
                assertTrue(Arrays.equals(property.getByteArray(), ByteUtils.bytesFromHex
                        ("01000101")));
                foundDimmingProperty = true;
            }
        }

        assertTrue(foundDimmingProperty == true);
        assertTrue(foundUnknownProperty == true);
    }
}
