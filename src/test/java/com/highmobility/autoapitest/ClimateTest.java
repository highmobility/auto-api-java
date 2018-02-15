package com.highmobility.autoapitest;

import com.highmobility.autoapi.ClimateState;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetClimateState;
import com.highmobility.autoapi.SetClimateProfile;
import com.highmobility.autoapi.StartStopDefogging;
import com.highmobility.autoapi.StartStopDefrosting;
import com.highmobility.autoapi.StartStopHvac;
import com.highmobility.autoapi.StartStopIonizing;
import com.highmobility.autoapi.property.AutoHvacState;
import com.highmobility.utils.Bytes;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ClimateTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "002401010004419800000200044140000003000441ac000004000441ac00000500010106000100070001000800010009000441ac00000A000F6000000000000000000000071E071E");


        Command command = CommandResolver.resolve(bytes);
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
        assertTrue(state.getDefrostingTemperature() == 21.5f);

        assertTrue(state.isAutoHvacConstant() == false);
        AutoHvacState[] autoHvacStates = state.getAutoHvacStates();
        assertTrue(autoHvacStates != null);
        assertTrue(autoHvacStates.length == 7);

        assertTrue(autoHvacStates[0].isActive() == false);

        assertTrue(autoHvacStates[5].isActive() == true);
        assertTrue(autoHvacStates[5].getDay() == 5);
        assertTrue(autoHvacStates[5].getStartHour() == 7);
        assertTrue(autoHvacStates[5].getStartMinute() == 30);

        assertTrue(autoHvacStates[6].isActive() == true);
        assertTrue(autoHvacStates[6].getDay() == 6);
        assertTrue(autoHvacStates[6].getStartHour() == 7);
        assertTrue(autoHvacStates[6].getStartMinute() == 30);
    }

    @Test public void get() {
        String waitingForBytes = "002400";
        String commandBytes = Bytes.hexFromBytes(new GetClimateState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void startStopDefogging() {
        String waitingForBytes = "00240401";
        String commandBytes = Bytes.hexFromBytes(new StartStopDefogging(true).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void startStopDefrosting() {
        String waitingForBytes = "00240501";
        String commandBytes = Bytes.hexFromBytes(new StartStopDefrosting(true).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void startStopHvac() {
        String waitingForBytes = "00240300";
        String commandBytes = Bytes.hexFromBytes(new StartStopHvac(false).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void startStopIonizing() {
        String waitingForBytes = "00240600";
        String commandBytes = Bytes.hexFromBytes(new StartStopIonizing(false).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setClimateProfile() throws CommandParseException {
        byte[] waitingForBytes = Bytes.bytesFromHex(
                "00240201000F6000000000000000000000071E071E02000441ac000003000441ac0000");

        AutoHvacState[] states = new AutoHvacState[7];
        for (int i = 0; i < 7; i++) {
            AutoHvacState state;
            if (i < 5)
                state = new AutoHvacState(false, i, 0, 0);
            else
                state = new AutoHvacState(true, i, 7, 30);

            states[i] = state;
        }

        float driverTemp = 21.5f;
        float passengerTemp = 21.5f;

        byte[] commandBytes = new SetClimateProfile(
                states,
                driverTemp,
                passengerTemp).getBytes();

        assertTrue(Arrays.equals(commandBytes, waitingForBytes));
    }
}
