package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.value.ActiveState;
import com.highmobility.autoapitest.TestUtils;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CruiseControlTest extends BaseTest {
    Bytes bytes = new Bytes("006201" +
            "01000401000101" +
            "02000401000101" +
            "030005010002003D" +
            "04000401000100" +
            "050005010002003C"
    );

    @Test
    public void state() {
        CruiseControlState state = (CruiseControlState) CommandResolver.resolve(bytes);

        assertTrue(state.getCruiseControl().getValue() == ActiveState.ACTIVE);
        assertTrue(state.getLimiter().getValue() == CruiseControlState.Limiter.HIGHER_SPEED_REQUESTED);

        assertTrue(state.getTargetSpeed().getValue() == 61);
        assertTrue(state.getAdaptiveCruiseControl().getValue() == ActiveState.INACTIVE);
        assertTrue(state.getAccTargetSpeed().getValue() == 60);
    }

    @Test public void get() {
        String waitingForBytes = "006200";
        String commandBytes = ByteUtils.hexFromBytes(new GetCruiseControlState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activateDeactivate() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("006201" +
                "01000401000101" +
                "030005010002003C");
        byte[] commandBytes = new ActivateDeactivateCruiseControl(ActiveState.ACTIVE, 60)
                .getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void deactivate() {
        Bytes waitingForBytes = new Bytes("006201" +
                "01000401000100");
        Command commandBytes = new ActivateDeactivateCruiseControl(ActiveState.INACTIVE, null);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));
    }
}