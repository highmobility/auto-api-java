package com.highmobility.autoapi.property;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.CommandWithProperties;
import com.highmobility.autoapi.GasFlapState;
import com.highmobility.autoapi.ParkingBrakeState;
import com.highmobility.autoapi.RooftopState;
import com.highmobility.autoapi.value.DashboardLight;
import com.highmobility.autoapitest.TestUtils;
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
 * property updated without failure or universalTimestamp - every other test tests that.
 * universalTimestamp added to the parsed property.
 * failure added to the base empty property. Base property bytes are not set.
 * For multiple properties with same identifier, timestamps only added if additional data same.
 * For single property for an identifier, can add without checking additional data.
 */
public class CommandTest {
    String parkingBrakeCommand = "00580101000401000101";

    // MARK: Timestamp

    @Test public void universalTimestamp() throws ParseException {
        Bytes bytes = new Bytes(parkingBrakeCommand + "A2000B01000800000160E0EA1388");
        String expectedDate = "2018-01-10T16:32:05";
        ParkingBrakeState command = (ParkingBrakeState) CommandResolver.resolve(bytes);
        assertTrue(TestUtils.dateIsSame(command.getTimestamp(), expectedDate));

        Calendar calendar = TestUtils.getUTCCalendar(expectedDate);
        ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setIsActive(new Property(true));
        builder.setTimestamp(calendar);
        command = builder.build();
        assertTrue(command.equals(bytes));
        assertTrue(TestUtils.dateIsSame(command.getTimestamp(), expectedDate));
    }

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
            if (property instanceof Property) {
                if (property.getValueClass() == DashboardLight.class) {
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
        builder.setIsActive(new Property(true));
        builder.setNonce(new Bytes("324244433743483436"));
        builder.setSignature(new Bytes
                ("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6"));
        ParkingBrakeState state = builder.build();
        assertTrue(state.equals(command));
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
                assertTrue(property.getValueComponent().getValueBytes().getLength() == 1);

                assertTrue(Arrays.equals(property.getValueComponent().getValueBytes().getByteArray(), new byte[]{0x35}));
                assertTrue(Arrays.equals(property.getByteArray(), ByteUtils.bytesFromHex
                        ("1A000401000135")));
                foundUnknownProperty = true;
            } else if (property.getPropertyIdentifier() == 0x01) {
                assertTrue(property.getValueComponent().getValueBytes().getLength() == 8);

                assertTrue(Arrays.equals(property.getByteArray(), ByteUtils.bytesFromHex
                        ("01000B0100083FF0000000000000")));
                foundDimmingProperty = true;
            }
        }

        assertTrue(foundDimmingProperty == true);
        assertTrue(foundUnknownProperty == true);
    }
}
