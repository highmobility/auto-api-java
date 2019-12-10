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
            COMMAND_HEADER + "001301" +
                    "02000E01000B" + COMMAND_HEADER + "00200105000401000101" +
                    "02000E01000B" + COMMAND_HEADER + "00460101000401000101");

    Bytes stateBytes = new Bytes(
            COMMAND_HEADER + "001301" +
                    "01004E01004B" + // door state header
                    COMMAND_HEADER + "002001" +// door state
                    "0200050100020000" +
                    "0200050100020100" +
                    "0300050100020001" +
                    "0300050100020101" +
                    "0400050100020001" +
                    "0400050100020100" +
                    "0400050100020200" +
                    "0400050100020300" +
                    "06000401000101" +
                    "01000E01000B" + // trunk state header
                    COMMAND_HEADER + "00460101000401000101"); // trunk state

    @Test
    public void commandIncoming() {
        setRuntime(CommandResolver.RunTime.JAVA);
        Command command = CommandResolver.resolve(commandBytes);
        MultiCommand state = (MultiCommand) command;
        assertTrue(state.getMultiCommands().length == 2);

        int testCount = 0;
        for (Property<Command> multiCommand : state.getMultiCommands()) {
            if (multiCommand.getValue() instanceof LockUnlockDoors) {
                assertTrue(((LockUnlockDoors) multiCommand.getValue()).getInsideLocksState().getValue() == LockState.LOCKED);
                testCount++;
            }

            if (multiCommand.getValue() instanceof SetTheftAlarm) {
                assertTrue(((SetTheftAlarm) multiCommand.getValue()).getStatus().getValue() == TheftAlarmState.Status.ARMED);
                testCount++;
            }
        }

        assertTrue(testCount == 2);
    }

    @Test
    public void commandBuild() {
        Command[] commands = new Command[2];
        commands[0] = new LockUnlockDoors(LockState.LOCKED);
        commands[1] = new SetTheftAlarm(TheftAlarmState.Status.ARMED);
        MultiCommand command = new MultiCommand(commands);
        assertTrue(bytesTheSame(command, commandBytes));
    }

    @Test
    public void commandBuildWithZeroCommands() {
        Command[] commands = new Command[0];
        new MultiCommand(commands);
    }

    @Test
    public void stateIncoming() {
        MultiCommandState command = (MultiCommandState) CommandResolver.resolve(stateBytes);
        testState(command);
    }

    private void testState(MultiCommandState state) {
        assertTrue(state.getMultiStates().length == 2);

        int testCount = 0;
        for (Property<Command> multiCommand : state.getMultiStates()) {
            if (multiCommand.getValue() instanceof DoorsState) {
                DoorsState command = (DoorsState) multiCommand.getValue();
                assertTrue(command.getLocksState().getValue() == LockState.LOCKED);
                testCount++;
            }

            if (multiCommand.getValue() instanceof TheftAlarmState) {
                TheftAlarmState command = (TheftAlarmState) multiCommand.getValue();
                assertTrue(command.getStatus().getValue() == TheftAlarmState.Status.ARMED);
                testCount++;
            }
        }

        assertTrue(testCount == 2);
    }

    @Test
    public void stateBuild() {
        Property[] commands = new Property[2];

        DoorsState.Builder builder = new DoorsState.Builder();

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

        DoorsState lockState = builder.build();

        TheftAlarmState.Builder tBuilder = new TheftAlarmState.Builder();
        tBuilder.setStatus(new Property(TheftAlarmState.Status.ARMED));
        TheftAlarmState theftAlarmState = tBuilder.build();

        commands[0] = new Property(lockState);
        commands[1] = new Property(theftAlarmState);

        MultiCommandState.Builder multiStateBuilder = new MultiCommandState.Builder();
        multiStateBuilder.setMultiStates(commands);
        testState(multiStateBuilder.build());
    }

}