package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivatePowerTakeoff;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetPowerTakeOffState;
import com.highmobility.autoapi.PowerTakeOffState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PowerTakeOffTest {
    Bytes bytes = new Bytes(
            "006501" +
                    "01000401000101" +
                    "02000401000101"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(PowerTakeOffState.TYPE));
        PowerTakeOffState state = (PowerTakeOffState) command;
        assertTrue(state.isActive().getValue() == true);
        assertTrue(state.isEngaged().getValue() == true);
    }

    @Test public void build() {
        PowerTakeOffState.Builder builder = new PowerTakeOffState.Builder();
        builder.setIsActive(new Property(true));
        builder.setIsEngaged(new Property(true));
        assertTrue(builder.build().equals(bytes));
    }

    @Test public void get() {
        String waitingForBytes = "006500";
        String commandBytes = ByteUtils.hexFromBytes(new GetPowerTakeOffState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activateDeactivate() {
        Bytes waitingForBytes = new Bytes("006502" +
                "01000401000101");
        byte[] commandBytes = new ActivateDeactivatePowerTakeoff(true).getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));
        ActivateDeactivatePowerTakeoff command = (ActivateDeactivatePowerTakeoff) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.activate().getValue() == true);
    }

    @Test public void failsWherePropertiesMandatory() {
        assertTrue(CommandResolver.resolve(ActivateDeactivatePowerTakeoff.TYPE.getIdentifierAndType()).getClass() == Command.class);
    }
}
