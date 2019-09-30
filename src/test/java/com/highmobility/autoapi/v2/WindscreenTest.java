package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.Zone;
import com.highmobility.autoapitest.TestUtils;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WindscreenTest extends BaseTest {
    Bytes bytes = new Bytes(
            "004201" +
                    "01000401000102" +
                    "02000401000103" +
                    "03000401000101" +
                    "0400050100020403" +
                    "0500050100020102" +
                    "06000401000102" +
                    "07000B0100083FEE666666666666" +
                    "08000B010008000001598938E788"

    );

    @Test
    public void state() {
        testState((WindscreenState) CommandResolver.resolve(bytes));
    }

    private void testState(WindscreenState state) {
        assertTrue(state.getWipers().getValue() == WindscreenState.Wipers.AUTOMATIC);
        assertTrue(state.getWipersIntensity().getValue() == WindscreenState.WipersIntensity.LEVEL_3);
        assertTrue(state.getWindscreenDamage().getValue() == WindscreenState.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED);

        Zone matrix = state.getWindscreenZoneMatrix().getValue();
        assertTrue(matrix.getHorizontal() == 4);
        assertTrue(matrix.getVertical() == 3);

        Zone zone = state.getWindscreenDamageZone().getValue();
        assertTrue(zone.getHorizontal() == 1);
        assertTrue(zone.getVertical() == 2);

        assertTrue(state.getWindscreenNeedsReplacement().getValue() == WindscreenState.WindscreenNeedsReplacement.REPLACEMENT_NEEDED);

        assertTrue(state.getWindscreenDamageConfidence().getValue() == .95d);

        Calendar c = state.getWindscreenDamageDetectionTime().getValue();
        assertTrue(TestUtils.dateIsSame(c, "2017-01-10T16:32:05"));
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        WindscreenState.Builder builder = new WindscreenState.Builder();

        builder.setWipers(new Property(WindscreenState.Wipers.AUTOMATIC));
        builder.setWipersIntensity(new Property(WindscreenState.WipersIntensity.LEVEL_3));
        builder.setWindscreenDamage(new Property(WindscreenState.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED));
        Zone matrix = new Zone(4, 3);
        builder.setWindscreenZoneMatrix(new Property(matrix));
        Zone zone = new Zone(1, 2);
        builder.setWindscreenDamageZone(new Property(zone));
        builder.setWindscreenNeedsReplacement(new Property(WindscreenState.WindscreenNeedsReplacement.REPLACEMENT_NEEDED));
        builder.setWindscreenDamageConfidence(new Property(.95d));
        Calendar date = TestUtils.getCalendar("2017-01-10T16:32:05");
        builder.setWindscreenDamageDetectionTime(new Property(date));
        WindscreenState command = builder.build();
        testState(command);
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004200");
        byte[] bytes = new GetWindscreenState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void setNoDamage() {
        Bytes waitingForBytes = new Bytes("004201" +
                "03000401000101");

        Bytes bytes =
                new SetWindscreenDamage(WindscreenState.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED,
                        null);
        assertTrue(bytesTheSame(waitingForBytes, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetWindscreenDamage command =
                (SetWindscreenDamage) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getWindscreenDamage().getValue() == WindscreenState.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED);
        assertTrue(command.getWindscreenDamageZone().getValue() == null);
    }

    @Test public void setDamage() {
        Bytes bytes = new Bytes("004201" +
                "03000401000101" +
                "0500050100020203");

        WindscreenState.WindscreenDamage damage =
                WindscreenState.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED;
        Zone zone = new Zone(2, 3);

        Bytes createdBytes = new SetWindscreenDamage(damage, zone);
        assertTrue(bytesTheSame(createdBytes, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetWindscreenDamage resolvedBytes = (SetWindscreenDamage) CommandResolver.resolve(bytes);
        assertTrue(resolvedBytes.getWindscreenDamage().getValue() == WindscreenState.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED);
        assertTrue(resolvedBytes.getWindscreenDamageZone().getValue().getHorizontal() == 2);
        assertTrue(resolvedBytes.getWindscreenDamageZone().getValue().getVertical() == 3);
    }

    @Test public void setReplacementNeeded() {
        Bytes waitingForBytes = new Bytes("004201" +
                "06000401000101");

        Bytes bytes = new SetWindscreenReplacementNeeded(WindscreenState.WindscreenNeedsReplacement
                .NO_REPLACEMENT_NEEDED);
        assertTrue(bytesTheSame(waitingForBytes, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetWindscreenReplacementNeeded command =
                (SetWindscreenReplacementNeeded) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getWindscreenNeedsReplacement().getValue() == WindscreenState.WindscreenNeedsReplacement.NO_REPLACEMENT_NEEDED);
    }

    @Test public void controlWipersTest() {
        Bytes bytes = new Bytes("004201" +
                "01000401000101" +
                "02000401000102");

        ControlWipers create = new ControlWipers(WindscreenState.Wipers.ACTIVE,
                WindscreenState.WipersIntensity.LEVEL_2);
        assertTrue(create.getWipersIntensity().getValue() == WindscreenState.WipersIntensity.LEVEL_2);
        assertTrue(create.getWipers().getValue() == WindscreenState.Wipers.ACTIVE);
        assertTrue(bytesTheSame(create, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ControlWipers resolve = (ControlWipers) CommandResolver.resolve(bytes);
        assertTrue(resolve.getWipers().getValue() == WindscreenState.Wipers.ACTIVE);
        assertTrue(resolve.getWipersIntensity().getValue() == WindscreenState.WipersIntensity.LEVEL_2);
    }
}
