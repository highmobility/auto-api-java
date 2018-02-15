package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetWindowsState;
import com.highmobility.autoapi.OpenCloseWindows;
import com.highmobility.autoapi.WindowsState;
import com.highmobility.autoapi.property.WindowProperty;
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



        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(WindowsState.TYPE));
        WindowsState state = (WindowsState) command;
        assertTrue(state.getWindowProperties().length == 4);


        assertTrue(state.getWindowProperty(WindowProperty.Position.FRONT_LEFT) != null);
        assertTrue(state.getWindowProperty(WindowProperty.Position.FRONT_LEFT).getState() == WindowProperty.State.OPEN);

        assertTrue(state.getWindowProperty(WindowProperty.Position.FRONT_RIGHT) != null);
        assertTrue(state.getWindowProperty(WindowProperty.Position.FRONT_RIGHT).getState() == WindowProperty.State.CLOSED);

        assertTrue(state.getWindowProperty(WindowProperty.Position.REAR_RIGHT) != null);
        assertTrue(state.getWindowProperty(WindowProperty.Position.REAR_RIGHT).getState() == WindowProperty.State.CLOSED);

        assertTrue(state.getWindowProperty(WindowProperty.Position.REAR_LEFT) != null);
        assertTrue(state.getWindowProperty(WindowProperty.Position.REAR_LEFT).getState() == WindowProperty.State.CLOSED);
    }

    @Test public void get() {
        byte[] waitingForBytes = Bytes.bytesFromHex("004500");
        byte[] bytes = new GetWindowsState().getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void openClose() throws CommandParseException {
        byte[] waitingForBytes = Bytes.bytesFromHex("00450201000200010100020101");
        WindowProperty[] windowsStates = new WindowProperty[2];
        windowsStates[0] = new WindowProperty(WindowProperty.Position.FRONT_LEFT, WindowProperty.State.OPEN);
        windowsStates[1] = new WindowProperty(WindowProperty.Position.FRONT_RIGHT, WindowProperty.State.OPEN);

        byte[] bytes = new OpenCloseWindows(windowsStates).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void build() {
        byte[] expectedBytes = Bytes.bytesFromHex(
                "0045010100020001010002010001000202000100020300");

        WindowsState.Builder builder = new WindowsState.Builder();
        builder.addWindowProperty(new WindowProperty(WindowProperty.Position.FRONT_LEFT, WindowProperty.State.OPEN));
        builder.addWindowProperty(new WindowProperty(WindowProperty.Position.FRONT_RIGHT, WindowProperty.State.CLOSED));
        builder.addWindowProperty(new WindowProperty(WindowProperty.Position.REAR_RIGHT, WindowProperty.State.CLOSED));
        builder.addWindowProperty(new WindowProperty(WindowProperty.Position.REAR_LEFT, WindowProperty.State.CLOSED));

        assertTrue(Arrays.equals(builder.build().getBytes(), expectedBytes));
    }
}
