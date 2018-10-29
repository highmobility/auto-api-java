package com.highmobility.autoapitest;

import com.highmobility.autoapi.ChassisSettings;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetChassisSettings;
import com.highmobility.autoapi.SetChassisPosition;
import com.highmobility.autoapi.SetDrivingMode;
import com.highmobility.autoapi.SetSpringRate;
import com.highmobility.autoapi.StartStopSportChrono;
import com.highmobility.autoapi.property.value.Axle;
import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.autoapi.property.SpringRateProperty;
import com.highmobility.autoapi.property.value.StartStop;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ChassisSettingsTest {
    Bytes bytes = new Bytes
            ("005301010001010200010105000200150500020117060002002506000201270700020010070002011208000119090001370A0001E4");

    @Test
    public void state() {

        com.highmobility.autoapi.Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == ChassisSettings.class);
        ChassisSettings state = (ChassisSettings) command;

        assertTrue(state.getDrivingMode() == DrivingMode.ECO);
        assertTrue(state.isSportChronoActive() == true);

        assertTrue(state.getCurrentSpringRate(Axle.FRONT).getSpringRate() == 21);
        assertTrue(state.getCurrentSpringRate(Axle.REAR).getSpringRate() == 23);

        assertTrue(state.getMaximumSpringRate(Axle.FRONT).getSpringRate() == 37);
        assertTrue(state.getMaximumSpringRate(Axle.REAR).getSpringRate() == 39);

        assertTrue(state.getMinimumSpringRate(Axle.FRONT).getSpringRate() == 16);
        assertTrue(state.getMinimumSpringRate(Axle.REAR).getSpringRate() == 18);

        assertTrue(state.getCurrentChassisPosition() == 25);
        assertTrue(state.getMaximumChassisPosition() == 55);
        assertTrue(state.getMinimumChassisPosition() == -28);
    }

    @Test public void get() {
        String waitingForBytes = "005300";
        String commandBytes = ByteUtils.hexFromBytes(new GetChassisSettings().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setDrivingMode() {
        Bytes waitingForBytes = new Bytes("00531201000103");
        Bytes commandBytes = new SetDrivingMode(DrivingMode.SPORT_PLUS);
        assertTrue(waitingForBytes.equals(commandBytes));

        SetDrivingMode drivingMode = (SetDrivingMode) CommandResolver.resolve(waitingForBytes);
        assertTrue(drivingMode.getDrivingMode() == DrivingMode.SPORT_PLUS);
    }

    @Test public void startChrono() {
        String waitingForBytes = "00531301000100";
        String commandBytes = ByteUtils.hexFromBytes(new StartStopSportChrono(StartStop.START).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopSportChrono command = (StartStopSportChrono) CommandResolver.resolve(ByteUtils
                .bytesFromHex(waitingForBytes));
        assertTrue(command.getStartStop() == StartStop.START);
    }

    @Test public void setSpringRate() {
        String waitingForBytes = "0053140100020119";
        String commandBytes = ByteUtils.hexFromBytes(new SetSpringRate(Axle.REAR, 25)
                .getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        SetSpringRate command = (SetSpringRate) CommandResolver.resolve(ByteUtils.bytesFromHex
                (waitingForBytes));
        assertTrue(command.getAxle() == Axle.REAR);
        assertTrue(command.getSpringRate() == 25);
    }

    @Test public void setChassisPosition() {
        String waitingForBytes = "00531501000132";
        String commandBytes = ByteUtils.hexFromBytes(new SetChassisPosition(50).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        SetChassisPosition command = (SetChassisPosition) CommandResolver.resolve(ByteUtils
                .bytesFromHex(waitingForBytes));
        assertTrue(command.getPosition() == 50);
    }

    @Test public void setNegativeChassisPosition() {
        String waitingForBytes = "005315010001E4";
        String commandBytes = ByteUtils.hexFromBytes(new SetChassisPosition(-28).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        SetChassisPosition command = (SetChassisPosition) CommandResolver.resolve(ByteUtils
                .bytesFromHex(waitingForBytes));
        assertTrue(command.getPosition() == -28);
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("005301");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((ChassisSettings) state).getMinimumSpringRate(Axle.REAR) == null);
    }

    @Test public void build() {
        ChassisSettings.Builder builder = new ChassisSettings.Builder();

        builder.setDrivingMode(DrivingMode.ECO);
        builder.setSportChronoActive(true);

        builder.addCurrentSpringRate(new SpringRateProperty(Axle.FRONT, 21));
        builder.addCurrentSpringRate(new SpringRateProperty(Axle.REAR, 23));

        builder.addMaximumSpringRate(new SpringRateProperty(Axle.FRONT, 37));
        builder.addMaximumSpringRate(new SpringRateProperty(Axle.REAR, 39));

        builder.addMinimumSpringRate(new SpringRateProperty(Axle.FRONT, 16));
        builder.addMinimumSpringRate(new SpringRateProperty(Axle.REAR, 18));

        builder.setCurrentChassisPosition(25);
        builder.setMaximumChassisPosition(55);
        builder.setMinimumChassisPosition(-28);

        ChassisSettings state = builder.build();
        assertTrue(state.equals(bytes));
        assertTrue(state.getType() == ChassisSettings.TYPE);
    }
}