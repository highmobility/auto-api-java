package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlWipers;
import com.highmobility.autoapi.GetWindscreenState;
import com.highmobility.autoapi.SetWindscreenDamage;
import com.highmobility.autoapi.SetWindscreenReplacementNeeded;
import com.highmobility.autoapi.WindscreenState;
import com.highmobility.autoapi.property.WindscreenDamage;
import com.highmobility.autoapi.property.WindscreenDamageZone;
import com.highmobility.autoapi.property.WindscreenDamageZoneMatrix;
import com.highmobility.autoapi.property.WindscreenReplacementState;
import com.highmobility.autoapi.property.WiperIntensity;
import com.highmobility.autoapi.property.WiperState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertTrue;

public class WindscreenTest {
    @Test
    public void state() {
        Bytes bytes = new Bytes(
                "0042010100010202000103030001020400014305000112060001020700015f08000811010a1020050000");
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(WindscreenState.TYPE) && command.getClass().equals(WindscreenState
                .class));
        WindscreenState state = (WindscreenState) command;

        assertTrue(state.getWiperState() == WiperState.AUTOMATIC);
        assertTrue(state.getWiperIntensity() == WiperIntensity.LEVEL_3);
        assertTrue(state.getWindscreenDamage() == WindscreenDamage.DAMAGE_SMALLER_THAN_1);
        assertTrue(state.getWindscreenReplacementState() == WindscreenReplacementState
                .REPLACEMENT_NEEDED);

        WindscreenDamageZone zone = state.getWindscreenDamageZone();
        assertTrue(zone.getDamageZoneX() == 1);
        assertTrue(zone.getDamageZoneY() == 2);

        WindscreenDamageZoneMatrix matrix = state.getWindscreenDamageZoneMatrix();
        assertTrue(matrix.getWindscreenSizeHorizontal() == 4);
        assertTrue(matrix.getWindscreenSizeVertical() == 3);

        assertTrue(state.getDamageConfidence().getValue() == 95);

        Calendar c = state.getDamageDetectionTime();

        float rawOffset = c.getTimeZone().getRawOffset();
        float expectedRawOffset = 0;
        assertTrue(rawOffset == expectedRawOffset);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date commandDate = c.getTime();
            Date expectedDate = format.parse("2017-01-10T16:32:05");
            assertTrue((format.format(commandDate).equals(format.format(expectedDate))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test public void build() throws ParseException {
        // TBODO:
        /*WindscreenState.Builder builder = new WindscreenState.Builder();

        builder.setWiperState(WiperState.AUTOMATIC);
        builder.setWiperIntensity(WiperIntensity.LEVEL_3);
        builder.setWindscreenDamage(WindscreenDamage.DAMAGE_SMALLER_THAN_1);
        WindscreenDamageZoneMatrix matrix = new WindscreenDamageZoneMatrix(4, 3);
        builder.setWindscreenDamageZoneMatrix(matrix);
        WindscreenDamageZone zone = new WindscreenDamageZone(1, 2);
        builder.setWindscreenDamageZone(zone);
        builder.setWindscreenReplacementState(WindscreenReplacementState.REPLACEMENT_NEEDED);

        builder.setDamageConfidence(.95f);

        //2017-07-29T14:09:31+00:00
        Calendar date = TestUtils.getUTCCalendar("2017-01-10T16:32:05");
        builder.setDamageDetectionTime(date);

        WindscreenState command = builder.build();
        assertTrue(Arrays.equals(command.getByteArray(), ByteUtils.bytesFromHex
                ("0042010100010202000103030001020400014305000112060001020700015f08000811010a1020050000")));
*/
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004200");
        byte[] bytes = new GetWindscreenState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void setNoDamage() {
        Bytes waitingForBytes = new Bytes("00421203000101");

        byte[] bytes = new SetWindscreenDamage(WindscreenDamage.IMPACT_NO_DAMAGE,
                null).getByteArray();
        assertTrue(waitingForBytes.equals(bytes));

        SetWindscreenDamage command = (SetWindscreenDamage) CommandResolver.resolve
                (waitingForBytes);
        assertTrue(command.getDamage() == WindscreenDamage.IMPACT_NO_DAMAGE);
        assertTrue(command.getZone() == null);
    }

    @Test public void setDamage() {
        Bytes bytes = new Bytes("0042120300010105000123");

        WindscreenDamage damage = WindscreenDamage.IMPACT_NO_DAMAGE;
        WindscreenDamageZone zone = new WindscreenDamageZone(2, 3);

        Bytes createdBytes = new SetWindscreenDamage(damage, zone);

        assertTrue(TestUtils.bytesTheSame(createdBytes, bytes));

        SetWindscreenDamage resolvedBytes = (SetWindscreenDamage) CommandResolver.resolve
                (bytes);
        assertTrue(resolvedBytes.getDamage() == WindscreenDamage.IMPACT_NO_DAMAGE);
        assertTrue(resolvedBytes.getZone().getDamageZoneX() == 2);
        assertTrue(resolvedBytes.getZone().getDamageZoneY() == 3);
    }

    @Test public void setReplacementNeeded() {
        Bytes waitingForBytes = new Bytes("00421301000101");

        Bytes bytes = new SetWindscreenReplacementNeeded(WindscreenReplacementState
                .REPLACEMENT_NOT_NEEDED);

        assertTrue(TestUtils.bytesTheSame(waitingForBytes, bytes));

        SetWindscreenReplacementNeeded command = (SetWindscreenReplacementNeeded) CommandResolver
                .resolve
                        (waitingForBytes);
        assertTrue(command.getState() == WindscreenReplacementState.REPLACEMENT_NOT_NEEDED);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("004201");
        WindscreenState state = (WindscreenState) CommandResolver.resolve(bytes);
        assertTrue(state.getDamageConfidence().getValue() == null);
    }

    @Test public void controlWipersTest() {
        Bytes bytes = new Bytes("0042140100010102000102");

        ControlWipers create = new ControlWipers(WiperState.ACTIVE, WiperIntensity.LEVEL_2);
        assertTrue(create.getIntensity() == WiperIntensity.LEVEL_2);
        assertTrue(create.getState() == WiperState.ACTIVE);
        assertTrue(create.equals(bytes));

        ControlWipers resolve = (ControlWipers) CommandResolver.resolve(bytes);
        assertTrue(resolve.getState() == WiperState.ACTIVE);
        assertTrue(resolve.getIntensity() == WiperIntensity.LEVEL_2);
    }
}
