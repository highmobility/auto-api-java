package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlWindows;
import com.highmobility.autoapi.GetWindowsState;
import com.highmobility.autoapi.WindowsState;
import com.highmobility.autoapi.property.value.Position;
import com.highmobility.autoapi.property.windows.WindowLocation;
import com.highmobility.autoapi.property.windows.WindowOpenPercentage;
import com.highmobility.autoapi.property.windows.WindowPosition;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class WindowsTest {
    Bytes bytes = new Bytes("004501" +
            "0200020238" +
            "0200020312" +
            "0300020201" +
            "0300020300"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(WindowsState.TYPE));
        WindowsState state = (WindowsState) command;
        assertTrue(state.getWindowOpenPercentages().length == 2);
        assertTrue(state.getWindowPositions().length == 2);

        assertTrue(state.getWindowOpenPercentage(WindowLocation.REAR_RIGHT).getOpenPercentage()
                == .56f);
        assertTrue(state.getWindowOpenPercentage(WindowLocation.REAR_LEFT).getOpenPercentage() ==
                .18f);

        assertTrue(state.getWindowPosition(WindowLocation.REAR_RIGHT).getPosition()
                == Position.OPEN);
        assertTrue(state.getWindowPosition(WindowLocation.REAR_LEFT).getPosition() ==
                Position.CLOSED);
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004500");
        byte[] bytes = new GetWindowsState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void control() {
        Bytes waitingForBytes = new Bytes("004512" +
                "0200020001" +
                "0200020101"
        );

        WindowPosition[] windowsStates = new WindowPosition[2];
        windowsStates[0] = new WindowPosition(WindowLocation.FRONT_LEFT, Position.OPEN);
        windowsStates[1] = new WindowPosition(WindowLocation.FRONT_RIGHT, Position.OPEN);

        Bytes bytes = new ControlWindows(windowsStates);
        assertTrue(TestUtils.bytesTheSame(bytes, waitingForBytes));

        ControlWindows command = (ControlWindows) CommandResolver.resolve(waitingForBytes);
        WindowPosition[] states = command.getWindowPositions();
        assertTrue(states.length == 2);
        assertTrue(command.getWindowPosition(WindowLocation.FRONT_LEFT).getPosition() ==
                Position.OPEN);
        assertTrue(command.getWindowPosition(WindowLocation.FRONT_RIGHT).getPosition() ==
                Position.OPEN);
    }

    @Test public void build() {
        WindowsState.Builder builder = new WindowsState.Builder();
        builder.addWindowOpenPercentage(new WindowOpenPercentage(WindowLocation.REAR_RIGHT,
                .56f));
        builder.addWindowOpenPercentage(new WindowOpenPercentage(WindowLocation.REAR_LEFT,
                .18f));
        builder.addWindowPosition(new WindowPosition(WindowLocation.REAR_RIGHT,
                Position.OPEN));
        builder.addWindowPosition(new WindowPosition(WindowLocation.REAR_LEFT,
                Position.CLOSED));
        WindowsState state = builder.build();

        assertTrue(state.getWindowOpenPercentages().length == 2);
        assertTrue(state.getWindowPositions().length == 2);

        assertTrue(TestUtils.bytesTheSame(builder.build(), bytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("004501");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((WindowsState) state).getProperties().length == 0);
    }
}
