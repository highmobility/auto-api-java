package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Zone;
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
        testState((Windscreen.State) CommandResolver.resolve(bytes));
    }

    private void testState(Windscreen.State state) {
        assertTrue(state.getWipersStatus().getValue() == Windscreen.WipersStatus.AUTOMATIC);
        assertTrue(state.getWipersIntensity().getValue() == Windscreen.WipersIntensity.LEVEL_3);
        assertTrue(state.getWindscreenDamage().getValue() == Windscreen.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED);

        Zone matrix = state.getWindscreenZoneMatrix().getValue();
        assertTrue(matrix.getHorizontal() == 4);
        assertTrue(matrix.getVertical() == 3);

        Zone zone = state.getWindscreenDamageZone().getValue();
        assertTrue(zone.getHorizontal() == 1);
        assertTrue(zone.getVertical() == 2);

        assertTrue(state.getWindscreenNeedsReplacement().getValue() == Windscreen.WindscreenNeedsReplacement.REPLACEMENT_NEEDED);

        assertTrue(state.getWindscreenDamageConfidence().getValue() == .95d);

        Calendar c = state.getWindscreenDamageDetectionTime().getValue();
        assertTrue(TestUtils.dateIsSame(c, "2017-01-10T16:32:05"));
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        Windscreen.State.Builder builder = new Windscreen.State.Builder();

        builder.setWipersStatus(new Property(Windscreen.WipersStatus.AUTOMATIC));
        builder.setWipersIntensity(new Property(Windscreen.WipersIntensity.LEVEL_3));
        builder.setWindscreenDamage(new Property(Windscreen.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED));
        Zone matrix = new Zone(4, 3);
        builder.setWindscreenZoneMatrix(new Property(matrix));
        Zone zone = new Zone(1, 2);
        builder.setWindscreenDamageZone(new Property(zone));
        builder.setWindscreenNeedsReplacement(new Property(Windscreen.WindscreenNeedsReplacement.REPLACEMENT_NEEDED));
        builder.setWindscreenDamageConfidence(new Property(.95d));
        Calendar date = TestUtils.getCalendar("2017-01-10T16:32:05");
        builder.setWindscreenDamageDetectionTime(new Property(date));
        Windscreen.State command = builder.build();
        testState(command);
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004200");
        byte[] bytes = new Windscreen.GetState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void setNoDamage() {
        Bytes waitingForBytes = new Bytes("004201" +
                "03000401000101");

        Bytes bytes =
                new Windscreen.SetWindscreenDamage(Windscreen.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED,
                        null);
        assertTrue(bytesTheSame(waitingForBytes, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Windscreen.SetWindscreenDamage command =
                (Windscreen.SetWindscreenDamage) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getWindscreenDamage().getValue() == Windscreen.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED);
        assertTrue(command.getWindscreenDamageZone().getValue() == null);
    }

    @Test public void setDamage() {
        Bytes bytes = new Bytes("004201" +
                "03000401000101" +
                "0500050100020203");

        Windscreen.WindscreenDamage damage =
                Windscreen.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED;
        Zone zone = new Zone(2, 3);

        Bytes createdBytes = new Windscreen.SetWindscreenDamage(damage, zone);
        assertTrue(bytesTheSame(createdBytes, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Windscreen.SetWindscreenDamage resolvedBytes = (Windscreen.SetWindscreenDamage) CommandResolver.resolve(bytes);
        assertTrue(resolvedBytes.getWindscreenDamage().getValue() == Windscreen.WindscreenDamage.IMPACT_BUT_NO_DAMAGE_DETECTED);
        assertTrue(resolvedBytes.getWindscreenDamageZone().getValue().getHorizontal() == 2);
        assertTrue(resolvedBytes.getWindscreenDamageZone().getValue().getVertical() == 3);
    }

    @Test public void setReplacementNeeded() {
        Bytes waitingForBytes = new Bytes("004201" +
                "06000401000101");

        Bytes bytes = new Windscreen.SetWindscreenReplacementNeeded(Windscreen.WindscreenNeedsReplacement
                .NO_REPLACEMENT_NEEDED);
        assertTrue(bytesTheSame(waitingForBytes, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Windscreen.SetWindscreenReplacementNeeded command =
                (Windscreen.SetWindscreenReplacementNeeded) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getWindscreenNeedsReplacement().getValue() == Windscreen.WindscreenNeedsReplacement.NO_REPLACEMENT_NEEDED);
    }

    @Test public void controlWipersTest() {
        Bytes bytes = new Bytes("004201" +
                "01000401000101" +
                "02000401000102");

        Windscreen.ControlWipers create = new Windscreen.ControlWipers(Windscreen.WipersStatus.ACTIVE,
                Windscreen.WipersIntensity.LEVEL_2);
        assertTrue(create.getWipersIntensity().getValue() == Windscreen.WipersIntensity.LEVEL_2);
        assertTrue(create.getWipersStatus().getValue() == Windscreen.WipersStatus.ACTIVE);
        assertTrue(bytesTheSame(create, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Windscreen.ControlWipers resolve = (Windscreen.ControlWipers) CommandResolver.resolve(bytes);
        assertTrue(resolve.getWipersStatus().getValue() == Windscreen.WipersStatus.ACTIVE);
        assertTrue(resolve.getWipersIntensity().getValue() == Windscreen.WipersIntensity.LEVEL_2);
    }
}
