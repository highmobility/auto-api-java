package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlMode;
import com.highmobility.autoapi.GetVehicleStatus;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.utils.Bytes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleStatusTest {
    byte[] bytes = Bytes.bytesFromHex(
            "0011010100114a46325348424443374348343531383639020001010300065479706520580400064d79204361720500064142433132330600085061636B6167652B07000207E108000C4573746f72696c20426c617509000200DC0A0001050B00010599000B002101010001000200010199000700270101000102"
    );

    com.highmobility.autoapi.VehicleStatus vehicleStatus;

    @Before
    public void setup() {
        try {
            Command command = CommandResolver.resolve(bytes);
            if (command.is(com.highmobility.autoapi.VehicleStatus.TYPE)) {
                vehicleStatus = (com.highmobility.autoapi.VehicleStatus) command;
            }
            else {
                fail();
            }
        } catch (CommandParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void states_size() {
        assertTrue(vehicleStatus.getStates().length == 2);
    }

    @Test
    public void properties() {
        assertTrue(vehicleStatus.getVin().equals("JF2SHBDC7CH451869"));
        assertTrue(vehicleStatus.getPowerTrain() == com.highmobility.autoapi.VehicleStatus.PowerTrain.ALLELECTRIC);
        assertTrue(vehicleStatus.getModelName().equals("Type X"));
        assertTrue(vehicleStatus.getName().equals("My Car"));
        assertTrue(vehicleStatus.getLicensePlate().equals("ABC123"));

        assertTrue(vehicleStatus.getSalesDesignation().equals("Package+"));
        assertTrue(vehicleStatus.getModelYear() == 2017);
        assertTrue(vehicleStatus.getColorName().equals("Estoril Blau"));
        assertTrue(vehicleStatus.getPower() == 220);
        assertTrue(vehicleStatus.getNumberOfDoors() == 5);
        assertTrue(vehicleStatus.getNumberOfSeats() == 5);
    }

    @Test public void get() {
        String waitingForBytes = "001100";
        String commandBytes = Bytes.hexFromBytes(new GetVehicleStatus().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void trunkState() {
        Command command = getState(TrunkState.class);
        if (command == null) fail();
        if (command.is(TrunkState.TYPE) == false) fail();
        TrunkState trunkState = (TrunkState) command;
        assertTrue(trunkState.getLockState() == TrunkState.LockState.UNLOCKED);
        assertTrue(trunkState.getPosition() == TrunkState.Position.OPEN);
    }

    @Test public void controlMode() {
        Command command = getState(ControlMode.class);
        if (command == null) fail();
        if (command.is(ControlMode.TYPE) == false) fail();
        ControlMode state = (ControlMode) command;
        assertTrue(state.getMode() == ControlMode.Mode.STARTED);
    }

    Command getState(Class forClass) {
        for (int i = 0; i < vehicleStatus.getStates().length; i++) {
            Command command = vehicleStatus.getStates()[i];
            if (command.getClass().equals(forClass)) return command;
        }

        return null;
    }
}
