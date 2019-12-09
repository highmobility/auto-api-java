package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.DoorPosition;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MultiCommandTest extends BaseTest {
    Bytes commandBytes = new Bytes(
            "001301" +
                    "02000D01000A00200105000401000101" +
                    "02000D01000A00460101000401000101");

    Bytes stateBytes = new Bytes(
            "001301" +
                    "01004D01004A" + // door state header
                    "002001" +// door state
                    "0200050100020000" +
                    "0200050100020100" +
                    "0300050100020001" +
                    "0300050100020101" +
                    "0400050100020001" +
                    "0400050100020100" +
                    "0400050100020200" +
                    "0400050100020300" +
                    "06000401000101" +
                    "01000D01000A" + // trunk state header
                    "00460101000401000101"); // trunk state

    @Test
    public void commandIncoming() {
        setRuntime(CommandResolver.RunTime.JAVA);
        Command command = CommandResolver.resolve(commandBytes);
        MultiCommand.MultiCommandCommand state = (MultiCommand.MultiCommandCommand) command;
        assertTrue(state.getMultiCommands().length == 2);

        int testCount = 0;
        for (Property<Command> multiCommand : state.getMultiCommands()) {
            if (multiCommand.getValue() instanceof Doors.LockUnlockDoors) {
                assertTrue(((Doors.LockUnlockDoors) multiCommand.getValue()).getInsideLocksState().getValue() == LockState.LOCKED);
                testCount++;
            }

            if (multiCommand.getValue() instanceof TheftAlarm.SetTheftAlarm) {
                assertTrue(((TheftAlarm.SetTheftAlarm) multiCommand.getValue()).getStatus().getValue() == TheftAlarm.Status.ARMED);
                testCount++;
            }
        }

        assertTrue(testCount == 2);
    }

    @Test
    public void commandBuild() {
        Command[] commands = new Command[2];
        commands[0] = new Doors.LockUnlockDoors(LockState.LOCKED);
        commands[1] = new TheftAlarm.SetTheftAlarm(TheftAlarm.Status.ARMED);
        MultiCommand.MultiCommandCommand command = new MultiCommand.MultiCommandCommand(commands);
        assertTrue(bytesTheSame(command, commandBytes));
    }

    @Test
    public void commandBuildWithZeroCommands() {
        Command[] commands = new Command[0];
        new MultiCommand.MultiCommandCommand(commands);
    }

    @Test
    public void stateIncoming() {
        MultiCommand.State command = (MultiCommand.State) CommandResolver.resolve(stateBytes);
        testState(command);
    }

    private void testState(MultiCommand.State state) {
        assertTrue(state.getMultiStates().length == 2);

        int testCount = 0;
        for (Property<Command> multiCommand : state.getMultiStates()) {
            if (multiCommand.getValue() instanceof Doors.State) {
                Doors.State command = (Doors.State) multiCommand.getValue();
                assertTrue(command.getLocksState().getValue() == LockState.LOCKED);
                testCount++;
            }

            if (multiCommand.getValue() instanceof TheftAlarm.State) {
                TheftAlarm.State command = (TheftAlarm.State) multiCommand.getValue();
                assertTrue(command.getStatus().getValue() == TheftAlarm.Status.ARMED);
                testCount++;
            }
        }

        assertTrue(testCount == 2);
    }

    @Test
    public void stateBuild() {
        Property[] commands = new Property[2];

        Doors.State.Builder builder = new Doors.State.Builder();

        builder.addInsideLock(new Property(new Lock(Location.FRONT_LEFT, LockState.UNLOCKED)));
        builder.addInsideLock(new Property(new Lock(Location.FRONT_RIGHT, LockState.UNLOCKED)));

        builder.addLock(new Property(new Lock(Location.FRONT_LEFT, LockState.LOCKED)));
        builder.addLock(new Property(new Lock(Location.FRONT_RIGHT, LockState.LOCKED)));

        builder.addPosition(new Property(new DoorPosition(DoorPosition.Location.FRONT_LEFT,
                Position.OPEN)));
        builder.addPosition(new Property(new DoorPosition(DoorPosition.Location.FRONT_RIGHT,
                Position.CLOSED)));
        builder.addPosition(new Property(new DoorPosition(DoorPosition.Location.REAR_RIGHT,
                Position.CLOSED)));
        builder.addPosition(new Property(new DoorPosition(DoorPosition.Location.REAR_LEFT,
                Position.CLOSED)));

        builder.setLocksState(new Property(LockState.LOCKED));

        Doors.State lockState = builder.build();

        TheftAlarm.State.Builder tBuilder = new TheftAlarm.State.Builder();
        tBuilder.setStatus(new Property(TheftAlarm.Status.ARMED));
        TheftAlarm.State theftAlarmState = tBuilder.build();

        commands[0] = new Property(lockState);
        commands[1] = new Property(theftAlarmState);

        MultiCommand.State.Builder multiStateBuilder = new MultiCommand.State.Builder();
        multiStateBuilder.setMultiStates(commands);
        testState(multiStateBuilder.build());
    }

}