package com.highmobility.autoapitest;

import com.highmobility.autoapi.ChassisSettings;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetChassisSettings;
import com.highmobility.autoapi.SetChassisPosition;
import com.highmobility.autoapi.SetDrivingMode;
import com.highmobility.autoapi.SetSpringRate;
import com.highmobility.autoapi.StartStopSportChrono;
import com.highmobility.autoapi.property.Axle;
import com.highmobility.autoapi.property.ChassisPositionProperty;
import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.autoapi.property.SpringRateProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ChassisSettingsTest {
    @Test
    public void state() {
        byte[] bytes = ByteUtils.bytesFromHex(
                "00530101000101020001010300040015251503000401171F110400031937E4");

        com.highmobility.autoapi.Command command = null;

        try {
            command = CommandResolver.resolveBytes(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == ChassisSettings.class);
        ChassisSettings state = (ChassisSettings) command;

        assertTrue(state.getDrivingMode() == DrivingMode.ECO);
        assertTrue(state.isSportChronoActive() == true);

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
        String commandBytes = ByteUtils.hexFromBytes(new GetChassisSettings().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setDrivingMode() {
        String waitingForBytes = "00530203";
        String commandBytes = ByteUtils.hexFromBytes(new SetDrivingMode(DrivingMode.SPORT_PLUS).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        SetDrivingMode drivingMode = (SetDrivingMode) CommandResolver.resolveBytes(ByteUtils.bytesFromHex(waitingForBytes));
        assertTrue( drivingMode.getDrivingMode() == DrivingMode.SPORT_PLUS);
    }

    @Test public void startChrono() {
        String waitingForBytes = "00530300";
        String commandBytes = ByteUtils.hexFromBytes(new StartStopSportChrono(StartStopSportChrono
                .Command.START).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopSportChrono command = (StartStopSportChrono) CommandResolver.resolveBytes(ByteUtils.bytesFromHex(waitingForBytes));
        assertTrue(command.getCommand() == StartStopSportChrono.Command.START);
    }

    @Test public void setSpringRate() {
        String waitingForBytes = "0053040119";
        String commandBytes = ByteUtils.hexFromBytes(new SetSpringRate(Axle.REAR, 25).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        SetSpringRate command = (SetSpringRate) CommandResolver.resolveBytes(ByteUtils.bytesFromHex(waitingForBytes));
        assertTrue(command.getAxle() == Axle.REAR);
        assertTrue(command.getSpringRate() == 25);
    }

    @Test public void setChassisPosition() {
        String waitingForBytes = "00530532";
        String commandBytes = ByteUtils.hexFromBytes(new SetChassisPosition(50).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        SetChassisPosition command = (SetChassisPosition) CommandResolver.resolveBytes(ByteUtils.bytesFromHex(waitingForBytes));
        assertTrue(command.getPosition() == 50);
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("005301");
        Command state = CommandResolver.resolveBytes(bytes);
        assertTrue(((ChassisSettings) state).getChassisPosition() == null);
    }

    @Test public void build() {
        byte[] bytes = ByteUtils.bytesFromHex
                ("00530101000101020001010300040015251503000401171F110400031937E4");

        ChassisSettings.Builder builder = new ChassisSettings.Builder();

        builder.setDrivingMode(DrivingMode.ECO);
        builder.setSportChronoActive(true);

        SpringRateProperty frontSpringRate = new SpringRateProperty(Axle.FRONT, 21, 37, 21);
        SpringRateProperty rearSpringRate = new SpringRateProperty(Axle.REAR, 23, 31, 17);
        builder.addSpringRate(frontSpringRate).addSpringRate(rearSpringRate);

        ChassisPositionProperty position = new ChassisPositionProperty(25, 55, -28);
        builder.setChassisPosition(position);

        ChassisSettings state = builder.build();
        byte[] builtBytes = state.getByteArray();
        assertTrue(Arrays.equals(builtBytes, bytes));

        assertTrue(state.getType() == ChassisSettings.TYPE);
    }
}