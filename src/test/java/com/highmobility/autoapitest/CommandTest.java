package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.CommandWithProperties;
import com.highmobility.autoapi.GasFlapState;
import com.highmobility.autoapi.ParkingBrakeState;
import com.highmobility.autoapi.RooftopState;
import com.highmobility.autoapi.property.DashboardLight;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;

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
    String parkingBrakeCommand = "00580101000401000101";

    @Test public void propertiesSortedWithUniversalPropertiesLast() {
        // TODO: 2019-02-28 this is not necessary. remove the sorting from code
        
        /*Bytes bytes = new Bytes("002501" +
                "A4000D11010A11220000000202000100" + // timestamp as first property
                "01000164" +
                "A5000D02000A54727920696e20343073" +
                "02000201"
        );

        CommandWithProperties state = (CommandWithProperties) CommandResolver.resolve(bytes);
        assertTrue(state.getProperties()[0].getPropertyIdentifier() < 3 &&
                state.getProperties()[1].getPropertyIdentifier() < 3);

        assertTrue(state.getProperties()[2].getPropertyIdentifier() < 0 &&
                state.getProperties()[3].getPropertyIdentifier() < 0);*/
    }

    // MARK: PropertyFailure

    @Test public void propertyFailureForSingleIdentifier() {
        // TODO: 2019-02-28 write a new test for property components

/*        // want to test parsing into the command. values are tested in property test.
        Bytes bytes = new Bytes("002501" +
                "01000401000164" +
                *//*"02000100"*//*  // << this failed
                *//*"03000101" +*//* // << this failed
                "04000401000102" +
                "A5001001000D02000A54727920696e20343073" +
                "A5001001000D03000A54727920696e20343073");

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
        builder.setDimmingPercentage(new ObjectProperty(100));
        builder.setSunroofTiltState(new SunroofTiltState(SunroofTiltState.Value.HALF_TILTED));
        builder.setOpenPercentage(new ObjectProperty(null, null,
                new PropertyFailure(PropertyFailure.Reason.RATE_LIMIT, "Try in 40s")));
        builder.setConvertibleRoofState(new ConvertibleRoofState(null, null,
                new PropertyFailure(PropertyFailure.Reason.EXECUTION_TIMEOUT, "Try " +
                        "in 40s")));

        command = builder.build();
        assertTrue(TestUtils.bytesTheSame(command, bytes));*/
    }

    @Test public void propertyFailureForMultipleIdentifiers() {
        // TODO: 2019-02-28 write a new test for property components
        // Cannot parse this to a property, because it will not exist. We dont create empty
        // objects for all of the possible values.
        /*Bytes bytes = new Bytes("005601" +
                *//*"0300020201" +
                "0300020300" +*//*
                "A5000D02000A54727920696e20343073"); // this is a failure for seatbelt fastened.

        SeatsState command = (SeatsState) CommandResolver.resolve(bytes);
        assertTrue(command.getSeatBeltFastened(SeatLocation.REAR_LEFT) == null);
        assertTrue(command.getPropertyFailure((byte) 0x02) != null);*/
    }

    // MARK: PropertyTimestamp

    @Test
    public void propertyTimestampParsedIntoArrayAndProperty() throws ParseException {
        // TODO: 2019-02-28 write a new test for property components
        /*// size 9 + full prop size
        String parkingStateProperty = "01000401000101";
        String propertyTimestamp = "A4001301001011010A112200000001" +
                parkingStateProperty;
        Bytes bytes = new Bytes(parkingBrakeCommand + propertyTimestamp);

        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);

        String expectedDate = "2017-01-10T17:34:00";
        Calendar calendar = TestUtils.getUTCCalendar(expectedDate);
        assertTrue(TestUtils.dateIsSameIgnoreTimezone(command.isActive().getTimestamp(), calendar));

        // TBODO:
        *//*ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setIsActive(true, calendar);

        ParkingBrakeState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));*/
    }

    @Test public void testPropertyTimestampParsed() throws ParseException {

        // TODO: 2019-02-28 write a new test for property components
        /*String parkingStateProperty = "01000401000101";
        Bytes bytes =
                new Bytes(parkingBrakeCommand + "A4001301001000000160E156084001" + parkingStateProperty);
        String expectedDate = "2018-01-10T18:30:00";
        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);

        assertTrue(command.isActive().getTimestamp() != null);
        assertTrue(TestUtils.dateIsSame(command.isActive().getTimestamp(), expectedDate));*/
    }

    @Test public void propertyTimestampWithField() throws ParseException {
        // TODO: 2019-02-28 write a new test for property components

       /* String parkingStateProperty = "01000401000101";
        Bytes bytes = new Bytes(parkingBrakeCommand + "A4001301001000000160E156084001" +
                parkingStateProperty);
        String expectedDate = "2018-01-10T18:30:00";
        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);

        Calendar timestamp = command.isActive().getTimestamp();
        assertTrue(TestUtils.dateIsSame(timestamp, expectedDate));

        assertTrue(command.getPropertyTimestamps((byte) 0x01)[0].getAdditionalData().equals(parkingStateProperty));*/
    }

    @Test public void propertyTimestampWithObjectFromArray() throws ParseException {
        // TODO: 2019-02-28 write a new test for property components
        /*Bytes bytes = new Bytes("005601" +
                "0200050100020201" +
                "0200050100020300" + "A4001401001100000160E0EA138802" + "0200050100020300" +
                "0300050100020201" +
                "0300050100020300");
        SeatsState command = (SeatsState) CommandResolver.resolve(bytes);
        String expectedDate = "2018-01-10T16:32:05";

        PropertyTimestamp timestamp = command.getPropertyTimestamps((byte) 0x02)[0];
        assertTrue(TestUtils.dateIsSame(timestamp.getCalendar(), expectedDate));
        assertTrue(timestamp.getAdditionalData().equals("0200050100020300"));

        Calendar propTimestamp = command.getPersonDetection(SeatLocation.REAR_LEFT).getTimestamp();
        assertTrue(TestUtils.dateIsSame(propTimestamp, expectedDate));

        // TBODO:*/
    }

    // TODO: 2019-03-01 verify universal command timestamp works

    // MARK: Universal Properties

    @Test public void invalidProperty() {
        // test that invalid gasflapstate just sets the property to null and keeps the base property
        Bytes bytes = new Bytes("00400102000401000103"); // 3 is invalid gasflap lock state
        GasFlapState state = (GasFlapState) CommandResolver.resolve(bytes);

        assertTrue(state.getLock().getValue() == null);
        assertTrue(state.getProperty((byte) 0x02) != null);
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
            if (property instanceof ObjectProperty) {
                if (((ObjectProperty) property).getValueClass() == DashboardLight.class) {
                    found = true;
                }
            }
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
        builder.setIsActive(new ObjectProperty<>(true));
        builder.setNonce(new Bytes("324244433743483436"));
        builder.setSignature(new Bytes
                ("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6"));
        ParkingBrakeState state = builder.build();
        assertTrue(state.equals(command));
    }

    @Test public void timestamp() throws ParseException {
        // TODO: 2019-02-28 there is no universal timestamp
        /*Bytes bytes = new Bytes(parkingBrakeCommand + "A2000B01000800000160E0EA1388");
        String expectedDate = "2018-01-10T16:32:05";
        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);
        assertTrue(TestUtils.dateIsSame(command.getTimestamp(), expectedDate));

        Calendar calendar = TestUtils.getUTCCalendar(expectedDate);
        ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setIsActive(new ObjectProperty<>(true));
        builder.setTimestamp(calendar);
        ParkingBrakeState state = builder.build();
        assertTrue(state.equals(bytes));*/
    }

    @Test public void propertyTimestampNoData() throws ParseException {
        // TODO: 2019-02-28 write a new test for property components
        /*Bytes bytes = new Bytes(parkingBrakeCommand + "A4000C01000900000160E0EA138804");
        String expectedDate = "2018-01-10T16:32:05";
        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);

        PropertyTimestamp timestamp = command.getPropertyTimestamps((byte) 0x04)[0];
        assertTrue(TestUtils.dateIsSame(timestamp.getTimestamp(), expectedDate));
        assertTrue(timestamp.getAdditionalData().getLength() == 0);*/
    }

    @Test public void signedBytes() {
        CommandWithProperties command = getCommandWithSignature();
        Bytes signedBytes = command.getSignedBytes();
        assertTrue(signedBytes.equals(new Bytes(parkingBrakeCommand +
                "A0000C010009324244433743483436")));
    }

    CommandWithProperties getCommandWithSignature() {
        Bytes bytes = new Bytes
                (parkingBrakeCommand +
                        "A0000C010009324244433743483436" +
                        "A100430100404D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6");
        try {
            Command command = CommandResolver.resolve(bytes);

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
                        "01000B0100083FF0000000000000" +
                        "1A000401000135");

        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(RooftopState.TYPE));

        assertTrue(command.getClass() == RooftopState.class);
        RooftopState state = (RooftopState) command;

        assertTrue(state.getDimmingPercentage().getValue() == 1d);
        assertTrue(state.getOpenPercentage().getValue() == null);

        assertTrue(state.getProperties().length == 2);

        boolean foundUnknownProperty = false;
        boolean foundDimmingProperty = false;

        for (int i = 0; i < state.getProperties().length; i++) {
            Property property = state.getProperties()[i];

            if (property.getPropertyIdentifier() == 0x1A) {
                assertTrue(property.getValueLength() == 1);

                assertTrue(Arrays.equals(property.getValueBytes().getByteArray(), new byte[]{0x35}));
                assertTrue(Arrays.equals(property.getByteArray(), ByteUtils.bytesFromHex
                        ("1A000401000135")));
                foundUnknownProperty = true;
            } else if (property.getPropertyIdentifier() == 0x01) {
                assertTrue(property.getValueLength() == 8);

                assertTrue(Arrays.equals(property.getByteArray(), ByteUtils.bytesFromHex
                        ("01000B0100083FF0000000000000")));
                foundDimmingProperty = true;
            }
        }

        assertTrue(foundDimmingProperty == true);
        assertTrue(foundUnknownProperty == true);
    }
}
