package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetWindscreenState;
import com.highmobility.autoapi.SetWindscreenDamage;
import com.highmobility.autoapi.WindscreenState;
import com.highmobility.autoapi.property.WindscreenDamage;
import com.highmobility.autoapi.property.WindscreenDamageZone;
import com.highmobility.autoapi.property.WindscreenDamageZoneMatrix;
import com.highmobility.autoapi.property.WindscreenReplacementState;
import com.highmobility.autoapi.property.WiperIntensity;
import com.highmobility.autoapi.property.WiperState;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class WindscreenTest {
    @Test
    public void state() {
        byte[] bytes = ByteUtils.bytesFromHex(
                "0042010100010202000103030001020400014305000112060001020700015f08000811010a1020050000");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

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

        assertTrue(state.getDamageConfidence() == .95f);

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
        WindscreenState.Builder builder = new WindscreenState.Builder();

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
        Calendar date = TestUtils.getCalendar("2017-01-10T16:32:05");
        builder.setDamageDetectionTime(date);

        WindscreenState command = builder.build();
        assertTrue(Arrays.equals(command.getByteArray(), ByteUtils.bytesFromHex
                ("0042010100010202000103030001020400014305000112060001020700015f08000811010a1020050000")));

    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004200");
        byte[] bytes = new GetWindscreenState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void setNoDamage() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("00420203000101");

        byte[] bytes = new SetWindscreenDamage(WindscreenDamage.IMPACT_NO_DAMAGE,
                null, null).getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));

        SetWindscreenDamage command = (SetWindscreenDamage) CommandResolver.resolve
                (waitingForBytes);
        assertTrue(command.getDamage() == WindscreenDamage.IMPACT_NO_DAMAGE);
        assertTrue(command.getZone() == null);
        assertTrue(command.getReplacementState() == null);
    }

    @Test public void setDamage() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004202030001010500012306000101");

        WindscreenDamage damage = WindscreenDamage.IMPACT_NO_DAMAGE;
        WindscreenDamageZone zone = new WindscreenDamageZone(2, 3);
        WindscreenReplacementState replacementState = WindscreenReplacementState
                .REPLACEMENT_NOT_NEEDED;
        byte[] bytes = new SetWindscreenDamage(damage, zone, replacementState).getByteArray();

        assertTrue(Arrays.equals(waitingForBytes, bytes));

        SetWindscreenDamage command = (SetWindscreenDamage) CommandResolver.resolve
                (waitingForBytes);
        assertTrue(command.getDamage() == WindscreenDamage.IMPACT_NO_DAMAGE);
        assertTrue(command.getZone().getDamageZoneX() == 2);
        assertTrue(command.getZone().getDamageZoneY() == 3);
        assertTrue(command.getReplacementState() == WindscreenReplacementState
                .REPLACEMENT_NOT_NEEDED);
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("004201");
        WindscreenState state = (WindscreenState) CommandResolver.resolve(bytes);
        assertTrue(state.getDamageConfidence() == null);
    }
}
