package com.highmobility.autoapitest;

import com.highmobility.autoapi.ChassisSettings;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetChassisSettings;
import com.highmobility.autoapi.SetChassisPosition;
import com.highmobility.autoapi.SetDrivingMode;
import com.highmobility.autoapi.SetSpringRate;
import com.highmobility.autoapi.StartStopSportChrono;
import com.highmobility.autoapi.property.Axle;
import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ChassisSettingsTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "00530101000101020001010300040015251503000401171F110400031937E4");

        com.highmobility.autoapi.Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == ChassisSettings.class);
        ChassisSettings state = (ChassisSettings) command;

        assertTrue(state.isSportChronoActive() == true);
        assertTrue(state.getDrivingMode() == DrivingMode.ECO);

        assertTrue(state.getSpringRate(Axle.FRONT).getSpringRate() == 21);
        assertTrue(state.getSpringRate(Axle.FRONT).getMaximumPossibleRate() == 37);
        assertTrue(state.getSpringRate(Axle.FRONT).getMinimumPossibleRate() == 21);

        assertTrue(state.getSpringRate(Axle.REAR).getSpringRate() == 23);
        assertTrue(state.getSpringRate(Axle.REAR).getMaximumPossibleRate() == 31);
        assertTrue(state.getSpringRate(Axle.REAR).getMinimumPossibleRate() == 17);

        assertTrue(state.getChassisPosition().getPosition() == 25);
        assertTrue(state.getChassisPosition().getMaximumPossibleValue() == 55);
        assertTrue(state.getChassisPosition().getMinimumPossibleValue() == -28);

    }

    @Test public void get() {
        String waitingForBytes = "005300";
        String commandBytes = Bytes.hexFromBytes(new GetChassisSettings().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setDrivingMode() {
        String waitingForBytes = "00530203";
        String commandBytes = Bytes.hexFromBytes(new SetDrivingMode(DrivingMode.SPORT_PLUS).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void startChrono() {
        String waitingForBytes = "00530300";
        String commandBytes = Bytes.hexFromBytes(new StartStopSportChrono(StartStopSportChrono
                .Command.START).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setSpringRate() {
        String waitingForBytes = "0053040119";
        String commandBytes = Bytes.hexFromBytes(new SetSpringRate(Axle.REAR, 25).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setChassisPosition() {
        String waitingForBytes = "00530532";
        String commandBytes = Bytes.hexFromBytes(new SetChassisPosition(50).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}