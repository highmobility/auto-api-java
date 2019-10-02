package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RooftopTest extends BaseTest {
    Bytes bytes = new Bytes("002501" +
            "01000B0100084059000000000000" +
            "02000B010008404A800000000000" +
            "03000401000101" +
            "04000401000102" +
            "05000401000101"
    );

    @Test
    public void state() {
        RooftopControlState command = (RooftopControlState) CommandResolver.resolve(bytes);
        testState(command);
    }

    @Test public void get() {
        String waitingForBytes = "002500";
        String commandBytes = ByteUtils.hexFromBytes(new GetRooftopState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void controlRooftop() {
        Bytes waitingForBytes = new Bytes("002501" +
                "01000B0100080000000000000000" +
                "02000B0100080000000000000000" +
                "03000401000100" +
                "04000401000102" +
                "05000401000101");

        Bytes commandBytes = new ControlRooftop(0d, 0d,
                RooftopControlState.ConvertibleRoofState.CLOSED,
                RooftopControlState.SunroofTiltState.HALF_TILTED,
                RooftopControlState.SunroofState.OPEN);
        assertTrue(bytesTheSame(commandBytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ControlRooftop command = (ControlRooftop) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getDimming().getValue() == 0d);
        assertTrue(command.getPosition().getValue() == 0d);
        assertTrue(command.getConvertibleRoofState().getValue() == RooftopControlState.ConvertibleRoofState.CLOSED);
        assertTrue(command.getSunroofTiltState().getValue() == RooftopControlState.SunroofTiltState.HALF_TILTED);
        assertTrue(command.getSunroofState().getValue() == RooftopControlState.SunroofState.OPEN);
    }

    @Test public void stateBuilder() {
        RooftopControlState.Builder builder = new RooftopControlState.Builder();

        builder.setDimming(new Property(100d));
        builder.setPosition(new Property(53d));
        builder.setConvertibleRoofState(new Property(RooftopControlState.ConvertibleRoofState.OPEN));
        builder.setSunroofTiltState(new Property(RooftopControlState.SunroofTiltState.HALF_TILTED));
        builder.setSunroofState(new Property(RooftopControlState.SunroofState.OPEN));

        RooftopControlState state = builder.build();
        testState(state);
    }

    private void testState(RooftopControlState state) {
        assertTrue(bytesTheSame(state, bytes));
        assertTrue(state.getDimming().getValue() == 100d);
        assertTrue(state.getPosition().getValue() == 53d);
        assertTrue(state.getConvertibleRoofState().getValue() == RooftopControlState.ConvertibleRoofState.OPEN);
        assertTrue(state.getSunroofTiltState().getValue() == RooftopControlState.SunroofTiltState.HALF_TILTED);
        assertTrue(state.getSunroofState().getValue() == RooftopControlState.SunroofState.OPEN);
        assertTrue(bytesTheSame(state, bytes));
    }
}
