package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.WindowLocation;
import com.highmobility.autoapi.value.WindowOpenPercentage;
import com.highmobility.autoapi.value.WindowPosition;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WindowsTest extends BaseTest {
    Bytes bytes = new Bytes("004501" +
            "02000C010009023FE1EB851EB851EC" +
            "02000C010009033FC70A3D70A3D70A" +
            "0300050100020201" +
            "0300050100020300"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        Windows.State state = (Windows.State) command;
        testState(state);
    }

    private void testState(Windows.State state) {
        assertTrue(state.getOpenPercentages().length == 2);
        assertTrue(state.getPositions().length == 2);

        assertTrue(state.getOpenPercentage(WindowLocation.REAR_RIGHT).getValue().getPercentage()
                == .56d);
        assertTrue(state.getOpenPercentage(WindowLocation.REAR_LEFT).getValue().getPercentage() ==
                .18d);

        assertTrue(state.getPosition(WindowLocation.REAR_RIGHT).getValue().getPosition() == WindowPosition.Position.OPEN);
        assertTrue(state.getPosition(WindowLocation.REAR_LEFT).getValue().getPosition() == WindowPosition.Position.CLOSED);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004500");
        byte[] bytes = new Windows.GetWindows().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void control() {
        Bytes waitingForBytes = new Bytes("004501" +
                "0300050100020001" +
                "0300050100020101"
        );

        WindowPosition[] windowsPositions = new WindowPosition[2];
        windowsPositions[0] = new WindowPosition(WindowLocation.FRONT_LEFT,
                WindowPosition.Position.OPEN);
        windowsPositions[1] = new WindowPosition(WindowLocation.FRONT_RIGHT,
                WindowPosition.Position.OPEN);

        Bytes bytes = new Windows.ControlWindows(null, windowsPositions);
        assertTrue(bytesTheSame(bytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Windows.ControlWindows command = (Windows.ControlWindows) CommandResolver.resolve(waitingForBytes);
        Property<WindowPosition>[] states = command.getPositions();
        assertTrue(states.length == 2);
    }

    @Test public void noPropertiesThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            WindowPosition[] windowsStates = new WindowPosition[0];
            Bytes bytes = new Windows.ControlWindows(null, windowsStates);
        });
    }

    @Test public void build() {
        Windows.State.Builder builder = new Windows.State.Builder();
        builder.addOpenPercentage(new Property(new WindowOpenPercentage(WindowLocation.REAR_RIGHT,
                .56d)));
        builder.addOpenPercentage(new Property(new WindowOpenPercentage(WindowLocation.REAR_LEFT,
                .18d)));
        builder.addPosition(new Property(new WindowPosition(WindowLocation.REAR_RIGHT,
                WindowPosition.Position.OPEN)));
        builder.addPosition(new Property(new WindowPosition(WindowLocation.REAR_LEFT,
                WindowPosition.Position.CLOSED)));
        Windows.State state = builder.build();
        testState(state);
    }
}
