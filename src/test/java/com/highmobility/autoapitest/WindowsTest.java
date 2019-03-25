package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlWindows;
import com.highmobility.autoapi.GetWindowsState;
import com.highmobility.autoapi.WindowsState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.windows.WindowOpenPercentage;
import com.highmobility.autoapi.value.windows.WindowPosition;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WindowsTest {
    Bytes bytes = new Bytes("004501" +
            "02000C010009023FE1EB851EB851EC" +
            "02000C010009033FC70A3D70A3D70A" +
            "0300050100020201" +
            "0300050100020300"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(WindowsState.TYPE));
        WindowsState state = (WindowsState) command;
        assertTrue(state.getWindowOpenPercentages().length == 2);
        assertTrue(state.getWindowPositions().length == 2);

        assertTrue(state.getWindowOpenPercentage(Location.REAR_RIGHT).getValue().getOpenPercentage()
                == .56d);
        assertTrue(state.getWindowOpenPercentage(Location.REAR_LEFT).getValue().getOpenPercentage() ==
                .18d);

        assertTrue(state.getWindowPosition(Location.REAR_RIGHT).getValue().getPosition() == Position.OPEN);
        assertTrue(state.getWindowPosition(Location.REAR_LEFT).getValue().getPosition() == Position.CLOSED);
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004500");
        byte[] bytes = new GetWindowsState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void control() {
        Bytes waitingForBytes = new Bytes("004512" +
                "0200050100020001" +
                "0200050100020101"
        );

        WindowPosition[] windowsStates = new WindowPosition[2];
        windowsStates[0] = new WindowPosition(Location.FRONT_LEFT, Position.OPEN);
        windowsStates[1] = new WindowPosition(Location.FRONT_RIGHT, Position.OPEN);

        Bytes bytes = new ControlWindows(windowsStates);
        assertTrue(TestUtils.bytesTheSame(bytes, waitingForBytes));

        ControlWindows command = (ControlWindows) CommandResolver.resolve(waitingForBytes);
        Property<WindowPosition>[] states = command.getWindowPositions();
        assertTrue(states.length == 2);
        assertTrue(command.getWindowPosition(Location.FRONT_LEFT).getValue().getPosition() ==
                Position.OPEN);
        assertTrue(command.getWindowPosition(Location.FRONT_RIGHT).getValue().getPosition() ==
                Position.OPEN);
    }

    @Test public void control0Properties() {
        Bytes waitingForBytes = new Bytes("004512");

        WindowPosition[] windowsStates = new WindowPosition[0];
        Bytes bytes = new ControlWindows(windowsStates);
        assertTrue(TestUtils.bytesTheSame(bytes, waitingForBytes));

        ControlWindows command = (ControlWindows) CommandResolver.resolve(waitingForBytes);
        Property<WindowPosition>[] states = command.getWindowPositions();
        assertTrue(states.length == 0);
    }

    @Test public void build() {
        WindowsState.Builder builder = new WindowsState.Builder();
        builder.addWindowOpenPercentage(new Property(new WindowOpenPercentage(Location.REAR_RIGHT,
                .56d)));
        builder.addWindowOpenPercentage(new Property(new WindowOpenPercentage(Location.REAR_LEFT,
                .18d)));
        builder.addWindowPosition(new Property(new WindowPosition(Location.REAR_RIGHT,
                Position.OPEN)));
        builder.addWindowPosition(new Property(new WindowPosition(Location.REAR_LEFT,
                Position.CLOSED)));
        WindowsState state = builder.build();

        assertTrue(state.getWindowOpenPercentages().length == 2);
        assertTrue(state.getWindowPositions().length == 2);

        assertTrue(TestUtils.bytesTheSame(builder.build(), bytes));
    }
}
