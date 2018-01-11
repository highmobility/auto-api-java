package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlMode;
import com.highmobility.autoapi.GetVehicleStatus;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.autoapi.VehicleStatus;
import com.highmobility.autoapi.property.CommandProperty;
import com.highmobility.autoapi.property.IntProperty;
import com.highmobility.autoapi.property.PowerTrain;
import com.highmobility.autoapi.property.TrunkLockState;
import com.highmobility.autoapi.property.TrunkPosition;
import com.highmobility.utils.Bytes;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static com.highmobility.autoapi.property.ControlMode.STARTED;
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
        assertTrue(vehicleStatus.getPowerTrain() == PowerTrain.ALLELECTRIC);
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
        assertTrue(trunkState.getLockState() == TrunkLockState.UNLOCKED);
        assertTrue(trunkState.getPosition() == TrunkPosition.OPEN);
    }

    @Test public void controlMode() {
        Command command = getState(ControlMode.class);
        if (command == null) fail();
        if (command.is(ControlMode.TYPE) == false) fail();
        ControlMode state = (ControlMode) command;
        assertTrue(state.getMode() == STARTED);
    }

    Command getState(Class forClass) {
        for (int i = 0; i < vehicleStatus.getStates().length; i++) {
            Command command = vehicleStatus.getStates()[i];
            if (command.getClass().equals(forClass)) return command;
        }

        return null;
    }

    VehicleStatus.Builder getVehicleStatusBuilderWithoutSignature() throws UnsupportedEncodingException {
        VehicleStatus.Builder builder = new VehicleStatus.Builder();
        builder.setVin("JF2SHBDC7CH451869");
        builder.setPowerTrain(PowerTrain.ALLELECTRIC);
        builder.setModelName("Type X");
        builder.setName("My Car");
        builder.setLicensePlate("ABC123");
        builder.setSalesDesignation("Package+");
        builder.setModelYear(2017);
        builder.setColor("Estoril Blau");
//        builder.setPower(220);
        // add an unknown property (power)
        builder.addProperty(new IntProperty((byte) 0x09, 220, 2));
        builder.setNumberOfDoors(5).setNumberOfSeats(5);

        TrunkState.Builder trunkState = new TrunkState.Builder();
        trunkState.setLockState(TrunkLockState.UNLOCKED);
        trunkState.setPosition(TrunkPosition.OPEN);
        builder.addProperty(new CommandProperty(trunkState.build()));

        ControlMode.Builder controlCommand = new ControlMode.Builder();
        controlCommand.setMode(STARTED);
        builder.addProperty(new CommandProperty(controlCommand.build()));
        return builder;
    }

    @Test public void create() throws UnsupportedEncodingException {
        VehicleStatus status = getVehicleStatusBuilderWithoutSignature().build();
        byte[] command = status.getBytes();
        assertTrue(Arrays.equals(command, Bytes.bytesFromHex("0011010100114a46325348424443374348343531383639020001010300065479706520580400064d79204361720500064142433132330600085061636B6167652B07000207E108000C4573746f72696c20426c617509000200DC0A0001050B00010599000B002101010001000200010199000700270101000102")));
    }

    @Test public void createWithSignature() throws UnsupportedEncodingException {
        VehicleStatus.Builder builder = getVehicleStatusBuilderWithoutSignature();
        builder.setNonce(Bytes.bytesFromHex("324244433743483436"));
        builder.setSignature(Bytes.bytesFromHex
                ("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6"));
        byte[] command = builder.build().getBytes();
        assertTrue(Arrays.equals(command, Bytes.bytesFromHex
                ("0011010100114a46325348424443374348343531383639020001010300065479706520580400064d79204361720500064142433132330600085061636B6167652B07000207E108000C4573746f72696c20426c617509000200DC0A0001050B00010599000B002101010001000200010199000700270101000102A00009324244433743483436A100404D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6")));
    }
}
