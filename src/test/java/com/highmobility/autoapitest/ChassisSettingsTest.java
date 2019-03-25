package com.highmobility.autoapitest;

import com.highmobility.autoapi.ChassisSettings;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetChassisSettings;
import com.highmobility.autoapi.SetChassisPosition;
import com.highmobility.autoapi.SetDrivingMode;
import com.highmobility.autoapi.SetSpringRate;
import com.highmobility.autoapi.StartStopSportChrono;
import com.highmobility.autoapi.value.DrivingMode;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.SpringRate;
import com.highmobility.autoapi.value.Axle;
import com.highmobility.autoapi.value.StartStop;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ChassisSettingsTest {
    Bytes bytes = new Bytes
            ("005301" +
                    "01000401000101" +
                    "02000401000101" +
                    "0500050100020015" +
                    "0500050100020117" +
                    "0600050100020025" +
                    "0600050100020127" +
                    "0700050100020010" +
                    "0700050100020112" +
                    "08000401000119" +
                    "09000401000137" +
                    "0A0004010001E4");

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
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "005300";
        String commandBytes = ByteUtils.hexFromBytes(new GetChassisSettings().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setDrivingMode() {
        Bytes waitingForBytes = new Bytes("00531201000401000103");
        Bytes commandBytes = new SetDrivingMode(DrivingMode.SPORT_PLUS);
        assertTrue(waitingForBytes.equals(commandBytes));

        SetDrivingMode drivingMode = (SetDrivingMode) CommandResolver.resolve(waitingForBytes);
        assertTrue(drivingMode.getDrivingMode().getValue() == DrivingMode.SPORT_PLUS);
    }

    @Test public void startChrono() {
        String waitingForBytes = "00531301000401000100";
        String commandBytes = ByteUtils.hexFromBytes(new StartStopSportChrono(StartStop.START)
                .getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        StartStopSportChrono command = (StartStopSportChrono) CommandResolver.resolve(ByteUtils
                .bytesFromHex(waitingForBytes));
        assertTrue(command.getStartStop().getValue() == StartStop.START);
    }

    @Test public void setSpringRate() {
        Bytes waitingForBytes = new Bytes("005314" +
                "0100050100020119");

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
        String waitingForBytes = "00531501000401000132";
        String commandBytes = ByteUtils.hexFromBytes(new SetChassisPosition(50).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        SetChassisPosition command = (SetChassisPosition) CommandResolver.resolve(ByteUtils
                .bytesFromHex(waitingForBytes));
        assertTrue(command.getPosition().getValue() == 50);
    }

    @Test public void setNegativeChassisPosition() {
        Bytes waitingForBytes = new Bytes("005315" +
                "010004010001E4");
        Command state = new SetChassisPosition(-28);
        assertTrue(TestUtils.bytesTheSame(state, waitingForBytes));
        SetChassisPosition command = (SetChassisPosition) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getPosition().getValue() == -28);
    }

    @Test public void build() {
        ChassisSettings.Builder builder = new ChassisSettings.Builder();

        builder.setDrivingMode(new Property(DrivingMode.ECO));
        builder.setSportChronoActive(new Property(true));

        builder.addCurrentSpringRate(new Property(new SpringRate(Axle.FRONT, 21)));
        builder.addCurrentSpringRate(new Property(new SpringRate(Axle.REAR, 23)));

        builder.addMaximumSpringRate(new Property(new SpringRate(Axle.FRONT, 37)));
        builder.addMaximumSpringRate(new Property(new SpringRate(Axle.REAR, 39)));

        builder.addMinimumSpringRate(new Property(new SpringRate(Axle.FRONT, 16)));
        builder.addMinimumSpringRate(new Property(new SpringRate(Axle.REAR, 18)));

        builder.setCurrentChassisPosition(new Property(25));
        builder.setMaximumChassisPosition(new Property(55));
        builder.setMinimumChassisPosition(new Property(-28));

        ChassisSettings state = builder.build();
        assertTrue(state.equals(bytes));
        assertTrue(state.getType() == ChassisSettings.TYPE);
        testState(state);
    }

    @Test public void failsWherePropertiesMandatory() {
        assertTrue(CommandResolver.resolve(SetDrivingMode.TYPE.getIdentifierAndType()).getClass() == Command.class);
        assertTrue(CommandResolver.resolve(StartStopSportChrono.TYPE.getIdentifierAndType()).getClass() == Command.class);
        assertTrue(CommandResolver.resolve(SetSpringRate.TYPE.getIdentifierAndType()).getClass() == Command.class);
    }
}