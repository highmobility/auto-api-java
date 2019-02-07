package com.highmobility.autoapitest;

import com.highmobility.autoapi.ChassisSettings;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetChassisSettings;
import com.highmobility.autoapi.SetChassisPosition;
import com.highmobility.autoapi.SetDrivingMode;
import com.highmobility.autoapi.SetSpringRate;
import com.highmobility.autoapi.StartStopSportChrono;
import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.SpringRate;
import com.highmobility.autoapi.property.value.Axle;
import com.highmobility.autoapi.property.value.StartStop;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ChassisSettingsTest {
    Bytes bytes = new Bytes
            ("005301010001010200010105000200150500020117060002002506000201270700020010070002011208000119090001370A0001E4");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        testState((ChassisSettings) command);
    }

    void testState(ChassisSettings state) {
        assertTrue(state.getDrivingMode().getValue() == DrivingMode.ECO);
        assertTrue(state.isSportChronoActive().getValue() == true);

        assertTrue(state.getCurrentSpringRate(Axle.FRONT).getValue().getSpringRate() == 21);
        assertTrue(state.getCurrentSpringRate(Axle.REAR).getValue().getSpringRate() == 23);

        assertTrue(state.getMaximumSpringRate(Axle.FRONT).getValue().getSpringRate() == 37);
        assertTrue(state.getMaximumSpringRate(Axle.REAR).getValue().getSpringRate() == 39);

        assertTrue(state.getMinimumSpringRate(Axle.FRONT).getValue().getSpringRate() == 16);
        assertTrue(state.getMinimumSpringRate(Axle.REAR).getValue().getSpringRate() == 18);

        assertTrue(state.getCurrentChassisPosition().getValue() == 25);
        assertTrue(state.getMaximumChassisPosition().getValue() == 55);
        assertTrue(state.getMinimumChassisPosition().getValue() == -28);

    }

    @Test public void stateWithTimestamp() {
        Bytes timestampBytes = bytes.concat(new Bytes("A4000911010A112200000002"));
        ChassisSettings command = (ChassisSettings) CommandResolver.resolve(timestampBytes);
        assertTrue(command.isSportChronoActive().getTimestamp() != null);
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
        String commandBytes = ByteUtils.hexFromBytes(new StartStopSportChrono(StartStop.START)
                .getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopSportChrono command = (StartStopSportChrono) CommandResolver.resolve(ByteUtils
                .bytesFromHex(waitingForBytes));
        assertTrue(command.getStartStop() == StartStop.START);
    }

    @Test public void setSpringRate() {
        Bytes waitingForBytes = new Bytes("005314" +
                "0100020119");

        SpringRate prop = new SpringRate(Axle.REAR, 25);
        SpringRate[] props = new SpringRate[1];
        props[0] = prop;
        Bytes commandBytes = new SetSpringRate(props);
        assertTrue(waitingForBytes.equals(commandBytes));

        SetSpringRate command = (SetSpringRate) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getSpringRates().length == 1);
        assertTrue(command.getSpringRate(Axle.REAR).getSpringRate() == 25);
    }

    @Test public void setSpringRate0Properties() {
        Bytes waitingForBytes = new Bytes("005314");

        SpringRate[] props = new SpringRate[0];
        Bytes commandBytes = new SetSpringRate(props);
        assertTrue(waitingForBytes.equals(commandBytes));

        SetSpringRate command = (SetSpringRate) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getSpringRates().length == 0);

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
        Bytes waitingForBytes = new Bytes("005315010001E4");
        Command state = new SetChassisPosition(-28);
        assertTrue(TestUtils.bytesTheSame(state, waitingForBytes));
        SetChassisPosition command = (SetChassisPosition) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getPosition() == -28);
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("005301");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((ChassisSettings) state).getMinimumSpringRate(Axle.REAR) == null);
    }

    @Test public void build() {
        ChassisSettings.Builder builder = new ChassisSettings.Builder();

        builder.setDrivingMode(new ObjectProperty<>(DrivingMode.ECO));
        builder.setSportChronoActive(new ObjectProperty<>(true));

        builder.addCurrentSpringRate(new ObjectProperty<>(new SpringRate(Axle.FRONT, 21)));
        builder.addCurrentSpringRate(new ObjectProperty<>(new SpringRate(Axle.REAR, 23)));

        builder.addMaximumSpringRate(new ObjectProperty<>(new SpringRate(Axle.FRONT, 37)));
        builder.addMaximumSpringRate(new ObjectProperty<>(new SpringRate(Axle.REAR, 39)));

        builder.addMinimumSpringRate(new ObjectProperty<>(new SpringRate(Axle.FRONT, 16)));
        builder.addMinimumSpringRate(new ObjectProperty<>(new SpringRate(Axle.REAR, 18)));

        builder.setCurrentChassisPosition(new ObjectPropertyInteger(25));
        builder.setMaximumChassisPosition(new ObjectPropertyInteger(55));
        builder.setMinimumChassisPosition(new ObjectPropertyInteger(-28));

        ChassisSettings state = builder.build();
        assertTrue(state.equals(bytes));
        assertTrue(state.getType() == ChassisSettings.TYPE);
        testState(state);
    }
}