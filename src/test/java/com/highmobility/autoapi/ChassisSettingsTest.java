package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Axle;
import com.highmobility.autoapi.value.DrivingMode;
import com.highmobility.autoapi.value.SpringRate;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ChassisSettingsTest extends BaseTest {
    Bytes bytes = new Bytes
            (COMMAND_HEADER + "005301" +
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
        testState((ChassisSettingsState) command);
    }

    void testState(ChassisSettingsState state) {
        assertTrue(state.getDrivingMode().getValue() == DrivingMode.ECO);
        assertTrue(state.getSportChrono().getValue() == ChassisSettingsState.SportChrono.ACTIVE);

        assertTrue(getSpringRate(state.currentSpringRates, Axle.FRONT).getSpringRate() == 21);
        assertTrue(getSpringRate(state.currentSpringRates, Axle.REAR).getSpringRate() == 23);

        assertTrue(getSpringRate(state.maximumSpringRates, Axle.FRONT).getSpringRate() == 37);
        assertTrue(getSpringRate(state.maximumSpringRates, Axle.REAR).getSpringRate() == 39);

        assertTrue(getSpringRate(state.minimumSpringRates, Axle.FRONT).getSpringRate() == 16);
        assertTrue(getSpringRate(state.minimumSpringRates, Axle.REAR).getSpringRate() == 18);

        assertTrue(state.getCurrentChassisPosition().getValue() == 25);
        assertTrue(state.getMaximumChassisPosition().getValue() == 55);
        assertTrue(state.getMinimumChassisPosition().getValue() == -28);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    SpringRate getSpringRate(Property<SpringRate>[] springRates, Axle axle) {
        for (Property<SpringRate> springRate : springRates) {
            if (springRate.getValue().getAxle() == axle) {
                return springRate.getValue();
            }
        }

        return null;
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "005300";
        String commandBytes = ByteUtils.hexFromBytes(new GetChassisSettings().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setDrivingMode() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "00530101000401000103");
        Bytes commandBytes = new SetDrivingMode(DrivingMode.SPORT_PLUS);
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetDrivingMode drivingMode = (SetDrivingMode) CommandResolver.resolve(waitingForBytes);
        assertTrue(drivingMode.getDrivingMode().getValue() == DrivingMode.SPORT_PLUS);
    }

    @Test public void startChrono() {
        String waitingForBytes = COMMAND_HEADER + "00530102000401000101";
        String commandBytes =
                ByteUtils.hexFromBytes(new StartStopSportsChrono(ChassisSettingsState.SportChrono.ACTIVE)
                .getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        StartStopSportsChrono command = (StartStopSportsChrono) CommandResolver.resolve(ByteUtils
                .bytesFromHex(waitingForBytes));
        assertTrue(command.getSportChrono().getValue() == ChassisSettingsState.SportChrono.ACTIVE);
    }

    @Test public void setSpringRate() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "005301" +
                "0500050100020119");

        SpringRate prop = new SpringRate(Axle.REAR, 25);
        SpringRate[] props = new SpringRate[]{prop};
        Bytes commandBytes = new SetSpringRates(props);
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetSpringRates command = (SetSpringRates) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getCurrentSpringRates().length == 1);
        assertTrue(getSpringRate(command.getCurrentSpringRates(), Axle.REAR).getSpringRate() == 25);
    }

    @Test public void setChassisPosition() {
        String waitingForBytes = COMMAND_HEADER + "00530108000401000132";
        String commandBytes = ByteUtils.hexFromBytes(new SetChassisPosition(50).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetChassisPosition command = (SetChassisPosition) CommandResolver.resolve(ByteUtils
                .bytesFromHex(waitingForBytes));
        assertTrue(command.getCurrentChassisPosition().getValue() == 50);
    }

    @Test public void setNegativeChassisPosition() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "005301" +
                "080004010001E4");
        Command state = new SetChassisPosition(-28);
        assertTrue(TestUtils.bytesTheSame(state, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetChassisPosition command = (SetChassisPosition) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getCurrentChassisPosition().getValue() == -28);
    }

    @Test public void build() {
        ChassisSettingsState.Builder builder = new ChassisSettingsState.Builder();

        builder.setDrivingMode(new Property(DrivingMode.ECO));
        builder.setSportChrono(new Property(ChassisSettingsState.SportChrono.ACTIVE));

        builder.addCurrentSpringRate(new Property(new SpringRate(Axle.FRONT, 21)));
        builder.addCurrentSpringRate(new Property(new SpringRate(Axle.REAR, 23)));

        builder.addMaximumSpringRate(new Property(new SpringRate(Axle.FRONT, 37)));
        builder.addMaximumSpringRate(new Property(new SpringRate(Axle.REAR, 39)));

        builder.addMinimumSpringRate(new Property(new SpringRate(Axle.FRONT, 16)));
        builder.addMinimumSpringRate(new Property(new SpringRate(Axle.REAR, 18)));

        builder.setCurrentChassisPosition(new Property(25));
        builder.setMaximumChassisPosition(new Property(55));
        builder.setMinimumChassisPosition(new Property(-28));

        ChassisSettingsState state = builder.build();
        assertTrue(state.equals(bytes));
        testState(state);
    }
}