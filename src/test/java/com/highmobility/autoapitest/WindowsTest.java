package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DoorLockState;
import com.highmobility.autoapi.GetLockState;
import com.highmobility.autoapi.GetWindowsState;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.OpenCloseWindows;
import com.highmobility.autoapi.WindowsState;
import com.highmobility.autoapi.property.WindowState;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class WindowsTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "0045010100020001010002010001000202000100020300");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.is(WindowsState.TYPE));
        WindowsState state = (WindowsState) command;
        assertTrue(state.getWindowStates().length == 4);


        assertTrue(state.getWindowState(WindowState.Position.FRONT_LEFT) != null);
        assertTrue(state.getWindowState(WindowState.Position.FRONT_LEFT).getState() == WindowState.State.OPEN);

        assertTrue(state.getWindowState(WindowState.Position.FRONT_RIGHT) != null);
        assertTrue(state.getWindowState(WindowState.Position.FRONT_RIGHT).getState() == WindowState.State.CLOSED);

        assertTrue(state.getWindowState(WindowState.Position.REAR_RIGHT) != null);
        assertTrue(state.getWindowState(WindowState.Position.REAR_RIGHT).getState() == WindowState.State.CLOSED);

        assertTrue(state.getWindowState(WindowState.Position.REAR_LEFT) != null);
        assertTrue(state.getWindowState(WindowState.Position.REAR_LEFT).getState() == WindowState.State.CLOSED);
    }

    @Test public void get() {
        byte[] waitingForBytes = Bytes.bytesFromHex("004500");
        byte[] bytes = new GetWindowsState().getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void openClose() throws CommandParseException {
        byte[] waitingForBytes = Bytes.bytesFromHex("00450201000200010100020101");
        WindowState[] windowsStates = new WindowState[2];
        windowsStates[0] = new WindowState(WindowState.Position.FRONT_LEFT, WindowState.State.OPEN);
        windowsStates[1] = new WindowState(WindowState.Position.FRONT_RIGHT, WindowState.State.OPEN);

        byte[] bytes = new OpenCloseWindows(windowsStates).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }
}
