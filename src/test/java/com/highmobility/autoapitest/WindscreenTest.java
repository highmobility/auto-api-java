package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlWipers;
import com.highmobility.autoapi.GetWindscreenState;
import com.highmobility.autoapi.SetWindscreenDamage;
import com.highmobility.autoapi.SetWindscreenReplacementNeeded;
import com.highmobility.autoapi.WindscreenState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.windscreen.WindscreenDamage;
import com.highmobility.autoapi.value.windscreen.WindscreenDamageZone;
import com.highmobility.autoapi.value.windscreen.WindscreenDamageZoneMatrix;
import com.highmobility.autoapi.value.windscreen.WindscreenReplacementState;
import com.highmobility.autoapi.value.windscreen.WiperIntensity;
import com.highmobility.autoapi.value.windscreen.WiperState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WindscreenTest {
    Bytes bytes = new Bytes(
            "004201" +
                    "01000401000102" +
                    "02000401000103" +
                    "03000401000101" +
                    "04000401000143" +
                    "05000401000112" +
                    "06000401000102" +
                    "07000B0100083FEE666666666666" +
                    "08000B010008000001598938E788"

    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(WindscreenState.TYPE) && command.getClass().equals(WindscreenState
                .class));
        WindscreenState state = (WindscreenState) command;
        testState(state);
    }

    private void testState(WindscreenState state) {
        assertTrue(state.getWiperState().getValue() == WiperState.AUTOMATIC);
        assertTrue(state.getWiperIntensity().getValue() == WiperIntensity.LEVEL_3);
        assertTrue(state.getWindscreenDamage().getValue() == WindscreenDamage.IMPACT_NO_DAMAGE);

        WindscreenDamageZoneMatrix matrix = state.getWindscreenDamageZoneMatrix().getValue();
        assertTrue(matrix.getWindscreenSizeHorizontal() == 4);
        assertTrue(matrix.getWindscreenSizeVertical() == 3);

        WindscreenDamageZone zone = state.getWindscreenDamageZone().getValue();
        assertTrue(zone.getDamageZoneX() == 1);
        assertTrue(zone.getDamageZoneY() == 2);

        assertTrue(state.getWindscreenReplacementState().getValue() == WindscreenReplacementState
                .REPLACEMENT_NEEDED);

        assertTrue(state.getDamageConfidence().getValue() == .95d);

        Calendar c = state.getDamageDetectionTime().getValue();
        assertTrue(TestUtils.dateIsSame(c, "2017-01-10T16:32:05"));
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        WindscreenState.Builder builder = new WindscreenState.Builder();

        builder.setWiperState(new Property(WiperState.AUTOMATIC));
        builder.setWiperIntensity(new Property(WiperIntensity.LEVEL_3));
        builder.setWindscreenDamage(new Property(WindscreenDamage.IMPACT_NO_DAMAGE));
        WindscreenDamageZoneMatrix matrix = new WindscreenDamageZoneMatrix(4, 3);
        builder.setWindscreenDamageZoneMatrix(new Property(matrix));
        WindscreenDamageZone zone = new WindscreenDamageZone(1, 2);
        builder.setWindscreenDamageZone(new Property(zone));
        builder.setWindscreenReplacementState(new Property(WindscreenReplacementState.REPLACEMENT_NEEDED));
        builder.setDamageConfidence(new Property(.95d));
        Calendar date = TestUtils.getCalendar("2017-01-10T16:32:05");
        builder.setDamageDetectionTime(new Property(date));
        WindscreenState command = builder.build();
        testState(command);
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004200");
        byte[] bytes = new GetWindscreenState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void setNoDamage() {
        Bytes waitingForBytes = new Bytes("004212" +
                "03000401000101");

        byte[] bytes = new SetWindscreenDamage(WindscreenDamage.IMPACT_NO_DAMAGE,
                null).getByteArray();
        assertTrue(waitingForBytes.equals(bytes));

        SetWindscreenDamage command = (SetWindscreenDamage) CommandResolver.resolve
                (waitingForBytes);
        assertTrue(command.getDamage().getValue() == WindscreenDamage.IMPACT_NO_DAMAGE);
        assertTrue(command.getZone().getValue() == null);
    }

    @Test public void setDamage() {
        Bytes bytes = new Bytes("004212" +
                "03000401000101" +
                "05000401000123");

        WindscreenDamage damage = WindscreenDamage.IMPACT_NO_DAMAGE;
        WindscreenDamageZone zone = new WindscreenDamageZone(2, 3);

        Bytes createdBytes = new SetWindscreenDamage(damage, zone);

        assertTrue(TestUtils.bytesTheSame(createdBytes, bytes));

        SetWindscreenDamage resolvedBytes = (SetWindscreenDamage) CommandResolver.resolve
                (bytes);
        assertTrue(resolvedBytes.getDamage().getValue() == WindscreenDamage.IMPACT_NO_DAMAGE);
        assertTrue(resolvedBytes.getZone().getValue().getDamageZoneX() == 2);
        assertTrue(resolvedBytes.getZone().getValue().getDamageZoneY() == 3);
    }

    @Test public void setReplacementNeeded() {
        Bytes waitingForBytes = new Bytes("004213" +
                "01000401000101");

        Bytes bytes = new SetWindscreenReplacementNeeded(WindscreenReplacementState
                .REPLACEMENT_NOT_NEEDED);

        assertTrue(TestUtils.bytesTheSame(waitingForBytes, bytes));

        SetWindscreenReplacementNeeded command = (SetWindscreenReplacementNeeded) CommandResolver
                .resolve
                        (waitingForBytes);
        assertTrue(command.getState().getValue() == WindscreenReplacementState.REPLACEMENT_NOT_NEEDED);
    }

    @Test public void controlWipersTest() {
        Bytes bytes = new Bytes("004214" +
                "01000401000101" +
                "02000401000102");

        ControlWipers create = new ControlWipers(WiperState.ACTIVE, WiperIntensity.LEVEL_2);
        assertTrue(create.getIntensity().getValue() == WiperIntensity.LEVEL_2);
        assertTrue(create.getState().getValue() == WiperState.ACTIVE);
        assertTrue(create.equals(bytes));

        ControlWipers resolve = (ControlWipers) CommandResolver.resolve(bytes);
        assertTrue(resolve.getState().getValue() == WiperState.ACTIVE);
        assertTrue(resolve.getIntensity().getValue() == WiperIntensity.LEVEL_2);
    }

    @Test public void failsWherePropertiesMandatory() {
        assertTrue(CommandResolver.resolve(SetWindscreenDamage.TYPE.getIdentifierAndType()).getClass() == Command.class);
        assertTrue(CommandResolver.resolve(SetWindscreenReplacementNeeded.TYPE.getIdentifierAndType()).getClass() == Command.class);
        assertTrue(CommandResolver.resolve(ControlWipers.TYPE.getIdentifierAndType()).getClass() == Command.class);
    }
}
