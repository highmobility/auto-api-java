package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivatePowerTakeoff;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetPowerTakeOffState;
import com.highmobility.autoapi.PowerTakeOffState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class PowerTakeOffTest {
    Bytes bytes = new Bytes(
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
        assertTrue(builder.build().equals(bytes));
    }

    @Test public void get() {
        String waitingForBytes = "006500";
        String commandBytes = ByteUtils.hexFromBytes(new GetPowerTakeOffState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activateDeactivate() {
        Bytes waitingForBytes = new Bytes("00650201000101");
        byte[] commandBytes = new ActivateDeactivatePowerTakeoff(true).getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));
        ActivateDeactivatePowerTakeoff command = (ActivateDeactivatePowerTakeoff) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.activate() == true);
    }
}
