package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateCruiseControl;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.CruiseControlState;
import com.highmobility.autoapi.GetCruiseControlState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class CruiseControlTest {
    Bytes bytes = new Bytes("006201" +
            "01000401000101" +
            "02000401000101" +
            "030005010002003D" +
            "04000401000100" +
            "050005010002003C"
    );

    @Test
    public void state() {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(CruiseControlState.TYPE));
        CruiseControlState state = (CruiseControlState) command;
        assertTrue(state.isActive() == true);
        assertTrue(state.getLimiter() == CruiseControlState.Limiter.HIGHER_SPEED_REQUESTED);
        assertTrue(state.getTargetSpeed() == 61);
        assertTrue(state.isAdaptiveActive() == false);
        assertTrue(state.getAdaptiveTargetSpeed() == 60);
    }

    @Test public void get() {
        String waitingForBytes = "006200";
        String commandBytes = ByteUtils.hexFromBytes(new GetCruiseControlState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activateDeactivate() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("006212" +
                "01000401000101" +
                "020005010002003C");
        byte[] commandBytes = new ActivateDeactivateCruiseControl(true, 60)
                .getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void deactivate() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("006212" +
                "01000401000100");
        byte[] commandBytes = new ActivateDeactivateCruiseControl(false, null)
                .getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }
}