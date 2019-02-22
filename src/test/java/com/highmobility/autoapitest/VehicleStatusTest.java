package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.CommandWithProperties;
import com.highmobility.autoapi.ControlMode;
import com.highmobility.autoapi.GetVehicleStatus;
import com.highmobility.autoapi.TheftAlarmState;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.autoapi.VehicleStatus;
import com.highmobility.autoapi.WindowsState;
import com.highmobility.autoapi.property.CommandProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PowerTrain;
import com.highmobility.autoapi.property.value.DisplayUnit;
import com.highmobility.autoapi.property.value.DriverSeatLocation;
import com.highmobility.autoapi.property.value.Gearbox;
import com.highmobility.autoapi.property.value.Lock;
import com.highmobility.autoapi.property.value.Position;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static com.highmobility.autoapi.property.ControlModeValue.STARTED;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleStatusTest {
    Bytes bytes = new Bytes(
            "001101" +
                    "0100140100114a46325348424443374348343531383639" +
                    "02000401000101" +
                    "030009010006547970652058" +
                    "0400090100064d7920436172" +
                    "050009010006414243313233" +
                    "06000B0100085061636B6167652B" +
                    "07000501000207E1" +
                    "08000F01000C4573746f72696c20426c6175" +
                    "09000501000200DC" +
                    "0A000401000105" +
                    "0B000401000105" +
                    "0C000701000440200000" +
                    "0D000501000200F5" +
                    "0E000401000101" +
                    "9900140100110021010100040100010002000401000101" +
                    "99000D01000A00270101000401000102" +
                    // l8
                    "0F000401000100" + // display unit km
                    "10000401000100" + // driver seat left
                    "11001201000F5061726B696E672073656E736F7273" + // Parking sensors
                    "1100130100104175746F6D6174696320776970657273" + // Automatic wipers
                    // l9
                    "12000B0100084D65726365646573"
    );

    private void testState(VehicleStatus vs) {
        assertTrue(vs.getStates().length == 2);
        assertTrue(vs.getVin().equals("JF2SHBDC7CH451869"));
        assertTrue(vs.getPowerTrain() == PowerTrain.ALLELECTRIC);
        assertTrue(vs.getModelName().equals("Type X"));
        assertTrue(vs.getName().equals("My Car"));
        assertTrue(vs.getLicensePlate().equals("ABC123"));

        assertTrue(vs.getSalesDesignation().equals("Package+"));
        assertTrue(vs.getModelYear() == 2017);
        assertTrue(vs.getColorName().equals("Estoril Blau"));
        assertTrue(vs.getPower() == 220);
        assertTrue(vs.getNumberOfDoors() == 5);
        assertTrue(vs.getNumberOfSeats() == 5);

        assertTrue(vs.getState(TrunkState.TYPE) != null);

        assertTrue(vs.getEngineVolume() == 2.5f);
        assertTrue(vs.getMaxTorque() == 245);
        assertTrue(vs.getGearBox() == Gearbox.AUTOMATIC);

        assertTrue(vs.getDisplayUnit() == DisplayUnit.KM);
        assertTrue(vs.getDriverSeatLocation() == DriverSeatLocation.LEFT);
        assertTrue(vs.getEquipments().length == 2);
        int count = 0;
        for (String s : vs.getEquipments()) {
            if (s.equals("Parking sensors") || s.equals("Automatic wipers")) count++;
        }
        assertTrue(count == 2);
        assertTrue(vs.getBrand().equals("Mercedes"));

        Command command = getState(ControlMode.class, vs);
        if (command == null) fail();
        if (command.is(ControlMode.TYPE) == false) fail();
        ControlMode state = (ControlMode) command;
        assertTrue(state.getMode() == STARTED);

        command = getState(TrunkState.class, vs);
        if (command == null) fail();
        if (command.is(TrunkState.TYPE) == false) fail();
        TrunkState trunkState = (TrunkState) command;
        assertTrue(trunkState.getLockState() == Lock.UNLOCKED);
        assertTrue(trunkState.getPosition() == Position.OPEN);
    }

    @Test public void build() {
        VehicleStatus status = getVehicleStatusBuilderWithoutSignature().build();
        assertTrue(TestUtils.bytesTheSame(status, bytes));
    }

    @Test public void get() {
        Bytes bytes = new Bytes("001100");
        Bytes commandBytes = new GetVehicleStatus();
        assertTrue(bytes.equals(commandBytes));

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof GetVehicleStatus);
    }

    Command getState(Class forClass, VehicleStatus command) {
        for (int i = 0; i < command.getStates().length; i++) {
            Command state = command.getStates()[i];
            if (state.getClass().equals(forClass)) return state;
        }

        return null;
    }

    VehicleStatus.Builder getVehicleStatusBuilderWithoutSignature() {
        VehicleStatus.Builder builder = new VehicleStatus.Builder();
        builder.setVin("JF2SHBDC7CH451869");
        builder.setPowerTrain(PowerTrain.ALLELECTRIC);
        builder.setModelName("Type X");
        builder.setName("My Car");
        builder.setLicensePlate("ABC123");
        builder.setSalesDesignation("Package+");
        builder.setModelYear(2017);

        builder.setColorName("Estoril Blau");
//        build.setPower(220);
        // add an unknown property (power)
        builder.addProperty(new IntegerProperty((byte) 0x09, 220, 2));
        builder.setNumberOfDoors(5).setNumberOfSeats(5);

        // l7
        builder.setEngineVolume(2.5f);
        builder.setMaxTorque(245);
        builder.setGearBox(Gearbox.AUTOMATIC);

        TrunkState.Builder trunkState = new TrunkState.Builder();
        trunkState.setLockState(Lock.UNLOCKED);
        trunkState.setPosition(Position.OPEN);
        builder.addProperty(new CommandProperty(trunkState.build()));

        ControlMode.Builder controlCommand = new ControlMode.Builder();
        controlCommand.setMode(STARTED);
        builder.addProperty(new CommandProperty(controlCommand.build()));

        // l8
        builder.setDisplayUnit(DisplayUnit.KM);
        builder.setDriverSeatLocation(DriverSeatLocation.LEFT);
        builder.addEquipment("Parking sensors");
        builder.addEquipment("Automatic wipers");

        // l9
        builder.setBrand("Mercedes");

        return builder;
    }

    @Test public void createWithSignature() {
        VehicleStatus.Builder builder = getVehicleStatusBuilderWithoutSignature();
        Bytes nonce = new Bytes("324244433743483436");
        builder.setNonce(nonce);
        Bytes signature = new Bytes
                ("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6");
        builder.setSignature(signature);

        VehicleStatus status = builder.build();
        byte[] command = status.getByteArray();
        assertTrue(Arrays.equals(command, command));
        assertTrue(status.getNonce().equals(nonce));
        assertTrue(status.getSignature().equals(signature));
    }

    @Test public void maiduTest() {
        Bytes bytes = new Bytes
                ("0011010A000105991B002001010003000100010003010000010003020001010003030001");
        Command command = CommandResolver.resolve(bytes);
    }

    @Test public void maiduTestTwo() {
        Bytes bytes = new Bytes
                ("00110131484D31363241363748333232393645460208757573206175746F21452D436C61737320436F6D666F7274202620506572736F6E616C69736174696F6E000400230800001C50BF19999A00330B000BB8001200000000500000200D04000001010001020001030001003008425210E741561BEA");
        Command command = CommandResolver.resolve(bytes);
        assertTrue(((CommandWithProperties) command).getProperties().length != 0);
    }

    @Test public void testInvalidProperty() {
        Bytes bytes = new Bytes
                ("001101" +
                        "0100140100094a46325348424443374348343531383639" +
                        "99002E002B01" + // 2b // 43
                        "004501" + // windows
                        "0200050100020238" +
                        "0200050100020312" +
                        "0300050100020201" +
                        "0300050100020300" +
                        "03000501000201FF" + // invalid Window Position FF

                        "99000D01000700270101000401000102");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
        VehicleStatus vs = (VehicleStatus) command;
        // one window property will fail to parse
        WindowsState ws = (WindowsState) vs.getState(WindowsState.TYPE);
        assertTrue(ws.getProperties().length == 5);
        assertTrue(ws.getWindowPositions().length == 2);
    }

    @Test public void zeroProperties() {
        VehicleStatus.Builder builder = new VehicleStatus.Builder();
        VehicleStatus vs = builder.build();
        testEmptyCommand(vs);
        assertTrue(vs.getByteArray().length == 3);
        assertTrue(vs.getByteArray().length == 3);

        Bytes bytes = new Bytes("00110100");
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
        testEmptyCommand((VehicleStatus) command);
    }

    void testEmptyCommand(VehicleStatus vs) {
        assertTrue(vs.getStates().length == 0);
        assertTrue(vs.getNumberOfDoors() == null);
        assertTrue(vs.getState(TheftAlarmState.TYPE) == null);
    }

    @Test public void testOneInvalidVsStateDoesNotMatter() {
        Bytes bytes = new Bytes
                ("001101" +
                        "9900140100110021010100040100010002000401000101" +
                        "99000D01000A00270101000401000115"); //invalid control mode
// 0102000401000103
        VehicleStatus command = (VehicleStatus) CommandResolver.resolve(bytes);
        assertTrue(command.getStates().length == 2); // invalid command is added as a base
        // command class
    }
}
