package com.highmobility.autoapitest;

import com.highmobility.autoapi.ClimateState;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetClimateState;
import com.highmobility.autoapi.SetClimateProfile;
import com.highmobility.autoapi.StartStopDefogging;
import com.highmobility.autoapi.StartStopDefrosting;
import com.highmobility.autoapi.StartStopHvac;
import com.highmobility.autoapi.StartStopIonising;
import com.highmobility.autoapi.property.AutoHvacProperty;
import com.highmobility.autoapi.property.AutoHvacState;
import com.highmobility.utils.Base64;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ClimateTest {
    @Test
    public void state() {
        Bytes bytes = new Bytes(
                "002401010004419800000200044140000003000441ac000004000441ac00000500010106000100070001000800010009000441ac00000A000F6000000000000000000000071E071E");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
        if (command == null) fail();

        assertTrue(command.is(ClimateState.TYPE));
        ClimateState state = (ClimateState) command;

        assertTrue(command.getClass() == ClimateState.class);
        assertTrue(state.getInsideTemperature() == 19f);
        assertTrue(state.getOutsideTemperature() == 12f);
        assertTrue(state.getDriverTemperatureSetting() == 21.5f);
        assertTrue(state.getPassengerTemperatureSetting() == 21.5f);

        assertTrue(state.isHvacActive() == true);
        assertTrue(state.isDefoggingActive() == false);
        assertTrue(state.isDefrostingActive() == false);
        assertTrue(state.isIonisingActive() == false);
        assertTrue(state.getDefrostingTemperature() == 21.5f);

        assertTrue(state.isAutoHvacConstant() == false);
        AutoHvacState[] autoHvacStates = state.getAutoHvacStates();
        assertTrue(autoHvacStates != null);
        assertTrue(autoHvacStates.length == 7);

        assertTrue(state.getAutoHvacState().isConstant() == false);
        AutoHvacProperty.WeekdayState[] weekdayWeekdayStates = state.getAutoHvacState().getStates();
        assertTrue(weekdayWeekdayStates != null);
        assertTrue(weekdayWeekdayStates.length == 7);

        assertTrue(autoHvacStates[0].isActive() == false);
        assertTrue(autoHvacStates[5].isActive() == true);
        assertTrue(autoHvacStates[5].getDay() == 5);
        assertTrue(autoHvacStates[5].getStartHour() == 7);
        assertTrue(autoHvacStates[5].getStartMinute() == 30);
        assertTrue(autoHvacStates[6].isActive() == true);
        assertTrue(autoHvacStates[6].getDay() == 6);
        assertTrue(autoHvacStates[6].getStartHour() == 7);
        assertTrue(autoHvacStates[6].getStartMinute() == 30);

        assertTrue(weekdayWeekdayStates[0].isActive() == false);
        assertTrue(weekdayWeekdayStates[5].isActive() == true);
        assertTrue(weekdayWeekdayStates[5].getDay() == 5);
        assertTrue(weekdayWeekdayStates[5].getStartHour() == 7);
        assertTrue(weekdayWeekdayStates[5].getStartMinute() == 30);
        assertTrue(weekdayWeekdayStates[6].isActive() == true);
        assertTrue(weekdayWeekdayStates[6].getDay() == 6);
        assertTrue(weekdayWeekdayStates[6].getStartHour() == 7);
        assertTrue(weekdayWeekdayStates[6].getStartMinute() == 30);
    }

    @Test public void get() {
        String waitingForBytes = "002400";
        String commandBytes = ByteUtils.hexFromBytes(new GetClimateState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void startStopDefogging() {
        Bytes waitingForBytes = new Bytes("00240401");
        String commandBytes = ByteUtils.hexFromBytes(new StartStopDefogging(true).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopDefogging command = (StartStopDefogging) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.start() == true);
    }

    @Test public void startStopDefrosting() {
        Bytes waitingForBytes = new Bytes("00240501");
        String commandBytes = ByteUtils.hexFromBytes(new StartStopDefrosting(true).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopDefrosting command = (StartStopDefrosting) CommandResolver.resolve
                (waitingForBytes);
        assertTrue(command.start() == true);
    }

    @Test public void startStopHvac() {
        Bytes waitingForBytes = new Bytes("00240300");
        String commandBytes = ByteUtils.hexFromBytes(new StartStopHvac(false).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopHvac command = (StartStopHvac) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.start() == false);
    }

    @Test public void StartStopIonising() {
        Bytes waitingForBytes = new Bytes("00240600");
        String commandBytes = ByteUtils.hexFromBytes(new StartStopIonising(false).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopIonising command = (StartStopIonising) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.start() == false);
    }

    @Test public void setClimateProfile() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(
                "00240201000F6000000000000000000000071E071E02000441ac000003000441ac0000");

        AutoHvacProperty.WeekdayState[] weekdayWeekdayStates = new AutoHvacProperty.WeekdayState[7];
        for (int i = 0; i < 7; i++) {
            AutoHvacProperty.WeekdayState weekdayState;
            if (i < 5) {
                weekdayState = new AutoHvacProperty.WeekdayState(false, i, 0, 0);
            } else {
                weekdayState = new AutoHvacProperty.WeekdayState(true, i, 7, 30);
            }

            weekdayWeekdayStates[i] = weekdayState;
        }
        AutoHvacProperty autoHvac = new AutoHvacProperty(weekdayWeekdayStates, false);

        float driverTemp = 21.5f;
        float passengerTemp = 21.5f;

        byte[] commandBytes = new SetClimateProfile(
                autoHvac,
                driverTemp,
                passengerTemp).getByteArray();

        assertTrue(Arrays.equals(commandBytes, waitingForBytes));

        SetClimateProfile profile = (SetClimateProfile) CommandResolver.resolveBase64(Base64.encode(waitingForBytes));
        assertTrue(profile.getDriverTemperature() == 21.5f);
        assertTrue(profile.getPassengerTemperature() == 21.5f);

        assertTrue(profile.getAutoHvacState().isConstant() == false);
        weekdayWeekdayStates = profile.getAutoHvacState().getStates();
        assertTrue(weekdayWeekdayStates != null);
        assertTrue(weekdayWeekdayStates.length == 7);
        assertTrue(weekdayWeekdayStates[0].isActive() == false);
        assertTrue(weekdayWeekdayStates[5].isActive() == true);
        assertTrue(weekdayWeekdayStates[5].getDay() == 5);
        assertTrue(weekdayWeekdayStates[5].getStartHour() == 7);
        assertTrue(weekdayWeekdayStates[5].getStartMinute() == 30);
        assertTrue(weekdayWeekdayStates[6].isActive() == true);
        assertTrue(weekdayWeekdayStates[6].getDay() == 6);
        assertTrue(weekdayWeekdayStates[6].getStartHour() == 7);
        assertTrue(weekdayWeekdayStates[6].getStartMinute() == 30);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002401");
        ClimateState state = (ClimateState) CommandResolver.resolve(bytes);
        assertTrue(state.getAutoHvacState() == null);
    }

    @Test public void build() {
        byte[] expectedBytes = ByteUtils.bytesFromHex(
                "002401010004419800000200044140000003000441ac000004000441ac00000500010106000100070001000800010009000441ac00000A000F6000000000000000000000071E071E");
        ClimateState.Builder builder = new ClimateState.Builder();

        builder.setInsideTemperature(19f);

        builder.setOutsideTemperature(12f);
        builder.setDriverTemperatureSetting(21.5f);
        builder.setPassengerTemperatureSetting(21.5f);

        builder.setHvacActive(true);
        builder.setDefoggingActive(false);
        builder.setDefrostingActive(false);
        builder.setIonisingActive(false);
        builder.setDefrostingTemperature(21.5f);

        AutoHvacProperty.WeekdayState[] weekdayWeekdayStates = new AutoHvacProperty.WeekdayState[7];
        for (int i = 0; i < 7; i++) {
            AutoHvacProperty.WeekdayState weekdayState;
            if (i < 5) {
                weekdayState = new AutoHvacProperty.WeekdayState(false, i, 0, 0);
            } else {
                weekdayState = new AutoHvacProperty.WeekdayState(true, i, 7, 30);
            }

            weekdayWeekdayStates[i] = weekdayState;
        }
        AutoHvacProperty autoHvac = new AutoHvacProperty(weekdayWeekdayStates, false);
        builder.setAutoHvacState(autoHvac);

        ClimateState command = builder.build();
        byte[] bytes = command.getByteArray();
        assertTrue(Arrays.equals(bytes, expectedBytes));
    }
}
