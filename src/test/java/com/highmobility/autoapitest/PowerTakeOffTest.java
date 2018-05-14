package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivatePowerTakeoff;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetPowerTakeOffState;
import com.highmobility.autoapi.PowerTakeOffState;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class PowerTakeOffTest {
    byte[] bytes = Bytes.bytesFromHex(
            "0065010100010102000101"
    );

    @Test
    public void state() {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(PowerTakeOffState.TYPE));
        PowerTakeOffState state = (PowerTakeOffState) command;
        assertTrue(state.isActive() == true);
        assertTrue(state.isEngaged() == true);
    }

    @Test public void build() {
        PowerTakeOffState.Builder builder = new PowerTakeOffState.Builder();
        builder.setIsActive(true);
        builder.setIsEngaged(true);
        assertTrue(Arrays.equals(builder.build().getBytes(), bytes));
    }

    @Test public void get() {
        String waitingForBytes = "006500";
        String commandBytes = Bytes.hexFromBytes(new GetPowerTakeOffState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activateDeactivate() {
        byte[] waitingForBytes = Bytes.bytesFromHex("00650201000101");
        byte[] commandBytes = new ActivateDeactivatePowerTakeoff(true).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
        ActivateDeactivatePowerTakeoff command = (ActivateDeactivatePowerTakeoff) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.activate() == true);
    }
}
