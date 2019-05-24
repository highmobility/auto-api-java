package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlMode;
import com.highmobility.autoapi.GetVehicleStatus;
import com.highmobility.autoapi.TheftAlarmState;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.autoapi.VehicleStatus;
import com.highmobility.autoapi.WindowsState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.DisplayUnit;
import com.highmobility.autoapi.value.DriverSeatLocation;
import com.highmobility.autoapi.value.Gearbox;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.value.PowerTrain;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
                    "9900140100110021010100040100010002000401000101" + // Trunk open
                    "99000D01000A00270101000401000102" + // Remote Control Started

                    // l8
                    "0F000401000100" + // display unit km
                    "10000401000100" + // driver seat left
                    "11001201000F5061726B696E672073656E736F7273" + // Parking sensors
                    "1100130100104175746F6D6174696320776970657273" + // Automatic wipers
                    // l9
                    "12000B0100084D65726365646573"
    );

    @Test public void state() {
        testState((VehicleStatus) CommandResolver.resolve(bytes));
    }

    @Test public void asd() {
        Bytes failed = new Bytes(
                "00110101001401001131484D34363133383148364535334430320200040100010003000301000004000B0100086D6178206361706105000C010009422D484D2D3334363107000501000207E108000301000009000501000200000A0004010001040B0004010001040C0007010004000000000D000501000200000E0004010001000F0004010001001000040100010011000301000012000A010007506F72736368659901A70101A400230102000E010002001E020006016A7CF2AEAB0300140100083FE999999999999A020006016A7CF2AEAB040010010004BF19999A020006016A7CF2AEAB050010010004BF19999A020006016A7CF2AEAB06001001000400000000020006016A7CF2AEAB07001001000400000000020006016A7CF2AEAB0800140100083FF0000000000000020006016A7CF2AEAB09000E0100020000020006016A7CF2AEAB0A001001000400000000020006016A7CF2AEAB0B000D01000100020006016A7CF2AEAB0C000D01000100020006016A7CF2AEAB0E001001000441C80000020006016A7CF2AEAB0F000D01000101020006016A7CF2AEAB10000D01000100020006016A7CF2AEAB11000F010003001107020006016A7CF2AEAB13000F010003001107020006016A7CF2AEAB1400100100044219999A020006016A7CF2AEAB15001501000900000001669BAF11A9020006016A7CF2AEAB15001501000901000001669BAF11A9020006016A7CF2AEAB15001501000902000001669BAF11A9020006016A7CF2AEAB16000D01000100020006016A7CF2AEAB17000D01000100020006016A7CF2AEAB9900BC0100B900530101000D01000101020006016A7CF2AEAC02000D01000100020006016A7CF2AEAC05000E0100020015020006016A7CF2AEAC05000E0100020115020006016A7CF2AEAC06000E0100020025020006016A7CF2AEAC06000E0100020125020006016A7CF2AEAC07000E0100020011020006016A7CF2AEAC07000E0100020111020006016A7CF2AEAC08000D01000119020006016A7CF2AEAC09000D01000137020006016A7CF2AEAC0A000D010001E4020006016A7CF2AEAC9900DC0100D900240101001001000441B80000020006016A7CF2AEAC02001001000441900000020006016A7CF2AEAC03001001000441B80000020006016A7CF2AEAC04001001000441B00000020006016A7CF2AEAC06000D01000100020006016A7CF2AEAC07000D01000100020006016A7CF2AEAC08000D01000100020006016A7CF2AEAC09001001000441B80000020006016A7CF2AEAC05000D01000100020006016A7CF2AEAC0B000F010003050800020006016A7CF2AEAC0B000F010003060800020006016A7CF2AEAC0C001001000441B00000020006016A7CF2AEAC99005801005500620101000D01000100020006016A7CF2AEAE02000D01000100020006016A7CF2AEAE03000E0100020000020006016A7CF2AEAE04000D01000100020006016A7CF2AEAE05000E0100020000020006016A7CF2AEAE99025901025600610101000E0100020000020006016A7CF2AEAA01000E0100020100020006016A7CF2AEAA01000E0100020200020006016A7CF2AEAA01000E0100020300020006016A7CF2AEAA01000E0100020400020006016A7CF2AEAA01000E0100020500020006016A7CF2AEAA01000E0100020600020006016A7CF2AEAA01000E0100020700020006016A7CF2AEAA01000E0100020800020006016A7CF2AEAA01000E0100020900020006016A7CF2AEAA01000E0100020A00020006016A7CF2AEAA01000E0100020B00020006016A7CF2AEAA01000E0100020C00020006016A7CF2AEAA01000E0100020D00020006016A7CF2AEAA01000E0100020E00020006016A7CF2AEAA01000E0100020F00020006016A7CF2AEAA01000E0100021000020006016A7CF2AEAA01000E0100021100020006016A7CF2AEAA01000E0100021200020006016A7CF2AEAA01000E0100021300020006016A7CF2AEAA01000E0100021400020006016A7CF2AEAA01000E0100021500020006016A7CF2AEAA01000E0100021600020006016A7CF2AEAA01000E0100021700020006016A7CF2AEAA01000E0100021800020006016A7CF2AEAA01000E0100021900020006016A7CF2AEAA01000E0100021A00020006016A7CF2AEAA01000E0100021B00020006016A7CF2AEAA01000E0100021C00020006016A7CF2AEAA01000E0100021D00020006016A7CF2AEAA01000E0100021E00020006016A7CF2AEAA01000E0100021F00020006016A7CF2AEAA01000E0100022000020006016A7CF2AEAA01000E0100022100020006016A7CF2AEAA01000E0100022200020006016A7CF2AEAA9902B10102AE00330101000F010003000BB8020006016A7CF2AEAD02000E0100020012020006016A7CF2AEAD03000E0100020000020006016A7CF2AEAD04000E0100020000020006016A7CF2AEAD0500140100083FE999999999999A020006016A7CF2AEAD06000E01000200C8020006016A7CF2AEAD09000D01000100020006016A7CF2AEAD0B001001000441400000020006016A7CF2AEAD0C001001000400000000020006016A7CF2AEAD0D000E0100020000020006016A7CF2AEAD0E000E0100020000020006016A7CF2AEAD0F001001000400000000020006016A7CF2AEAD10000D01000100020006016A7CF2AEAD11000E0100020017020006016A7CF2AEAD12001001000441C00000020006016A7CF2AEAD13001001000444160000020006016A7CF2AEAD14000D01000100020006016A7CF2AEAD");
        VehicleStatus status = (VehicleStatus) CommandResolver.resolve(failed);
    }


    private void testState(VehicleStatus state) {
        assertTrue(state.getStates().length == 2);
        assertTrue(state.getVin().getValue().equals("JF2SHBDC7CH451869"));
        assertTrue(state.getPowerTrain().getValue() == PowerTrain.ALLELECTRIC);
        assertTrue(state.getModelName().getValue().equals("Type X"));
        assertTrue(state.getName().getValue().equals("My Car"));
        assertTrue(state.getLicensePlate().getValue().equals("ABC123"));

        assertTrue(state.getSalesDesignation().getValue().equals("Package+"));
        assertTrue(state.getModelYear().getValue() == 2017);
        assertTrue(state.getColorName().getValue().equals("Estoril Blau"));
        assertTrue(state.getPower().getValue() == 220);
        assertTrue(state.getNumberOfDoors().getValue() == 5);
        assertTrue(state.getNumberOfSeats().getValue() == 5);

        assertTrue(state.getState(TrunkState.TYPE).getValue() != null);

        assertTrue(state.getEngineVolume().getValue() == 2.5f);
        assertTrue(state.getMaxTorque().getValue() == 245);
        assertTrue(state.getGearBox().getValue() == Gearbox.AUTOMATIC);

        assertTrue(state.getDisplayUnit().getValue() == DisplayUnit.KM);
        assertTrue(state.getDriverSeatLocation().getValue() == DriverSeatLocation.LEFT);
        assertTrue(state.getEquipments().length == 2);

        int count = 0;
        for (Property<String> s : state.getEquipments()) {
            if (s.getValue().equals("Parking sensors") || s.getValue().equals("Automatic wipers"))
                count++;
        }
        assertTrue(count == 2);
        assertTrue(state.getBrand().getValue().equals("Mercedes"));

        Command command = getState(ControlMode.class, state);
        if (command == null) fail();
        if (command.is(ControlMode.TYPE) == false) fail();
        ControlMode controlMode = (ControlMode) command;
        assertTrue(controlMode.getMode().getValue() == ControlMode.Value.STARTED);

        command = getState(TrunkState.class, state);
        if (command == null) fail();
        if (command.is(TrunkState.TYPE) == false) fail();
        TrunkState trunkState = (TrunkState) command;
        assertTrue(trunkState.getLockState().getValue() == Lock.UNLOCKED);
        assertTrue(trunkState.getPosition().getValue() == Position.OPEN);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        VehicleStatus status = getVehicleStatusBuilderWithoutSignature().build();
        testState(status);
    }

    @Test public void get() {
        Bytes bytes = new Bytes("001100");
        Bytes commandBytes = new GetVehicleStatus();
        assertTrue(bytes.equals(commandBytes));

        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof GetVehicleStatus);
    }

    Command getState(Class forClass, VehicleStatus command) {
        for (int i = 0; i < command.getStates().length; i++) {
            Property<Command> state = command.getStates()[i];
            if (state != null && state.getValue().getClass().equals(forClass))
                return state.getValue();
        }

        return null;
    }

    VehicleStatus.Builder getVehicleStatusBuilderWithoutSignature() {
        VehicleStatus.Builder builder = new VehicleStatus.Builder();
        builder.setVin(new Property("JF2SHBDC7CH451869"));
        builder.setPowerTrain(new Property(PowerTrain.ALLELECTRIC));
        builder.setModelName(new Property("Type X"));
        builder.setName(new Property("My Car"));
        builder.setLicensePlate(new Property("ABC123"));
        builder.setSalesDesignation(new Property("Package+"));
        builder.setModelYear(new Property(2017));

        builder.setColorName(new Property("Estoril Blau"));
        builder.setPower(new Property(220));
        builder.setNumberOfDoors(new Property(5)).setNumberOfSeats(new Property(5));

        // l7
        builder.setEngineVolume(new Property(2.5f));
        builder.setMaxTorque(new Property(245));
        builder.setGearBox(new Property(Gearbox.AUTOMATIC));

        TrunkState.Builder trunkState = new TrunkState.Builder();
        trunkState.setLockState(new Property(Lock.UNLOCKED));
        trunkState.setPosition(new Property(Position.OPEN));
        builder.addState(new Property(trunkState.build()));

        ControlMode controlMode = new ControlMode(new Bytes("00270101000401000102").getByteArray());
        builder.addState(new Property(controlMode));

        // l8
        builder.setDisplayUnit(new Property(DisplayUnit.KM));
        builder.setDriverSeatLocation(new Property(DriverSeatLocation.LEFT));
        builder.addEquipment(new Property("Parking sensors"));
        builder.addEquipment(new Property("Automatic wipers"));

        // l9
        builder.setBrand(new Property("Mercedes"));

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

    @Test public void maiduTestTwo() {
        Bytes bytes = new Bytes
                ("00110131484D31363241363748333232393645460208757573206175746F21452D436C61737320436F6D666F7274202620506572736F6E616C69736174696F6E000400230800001C50BF19999A00330B000BB8001200000000500000200D04000001010001020001030001003008425210E741561BEA");
        Command command = CommandResolver.resolve(bytes);
        assertTrue(((Command) command).getProperties().length != 0);
    }

    @Test public void testInvalidProperty() {
        Bytes bytes = new Bytes
                ("001101" +
                        "0100140100094a46325348424443374348343531383639" +
                        "99002E01002B" + // 2b // 43
                        "004501" + // windows
                        "0200050100020238" + // invalid
                        "0200050100020312" + // invalid
                        "0300050100020201" +
                        "0300050100020300" +
                        "03000501000201FF" + // invalid Window Position FF
                        "99000D01000A00270101000401000102"); // control mode command
        TestUtils.errorLogExpected(3, () -> {
            Command command = CommandResolver.resolve(bytes);
            VehicleStatus vs = (VehicleStatus) command;
            // one window property will fail to parse
            WindowsState ws = (WindowsState) vs.getState(WindowsState.TYPE).getValue();
            assertTrue(ws.getProperties().length == 5);
            assertTrue(ws.getWindowPositions().length == 3);
        });
    }

    @Test public void zeroProperties() {
        VehicleStatus.Builder builder = new VehicleStatus.Builder();
        VehicleStatus vs = builder.build();

        assertTrue(vs.getStates().length == 0);
        assertTrue(vs.getNumberOfDoors() == null);
        assertTrue(vs.getState(TheftAlarmState.TYPE) == null);
        assertTrue(vs.getByteArray().length == 3);

        Bytes bytes = new Bytes("00110100");
        vs = (VehicleStatus) CommandResolver.resolve(bytes);
        assertTrue(vs.getStates().length == 0);
        assertTrue(vs.getNumberOfDoors().getValue() == null);
        assertTrue(vs.getState(TheftAlarmState.TYPE) == null);
    }

    @Test public void testOneInvalidVsStateDoesNotMatter() {
        Bytes bytes = new Bytes
                ("001101" +
                        "9900140100110021010100040100010002000401000101" +
                        "99000D01000A00270101000401000115"); //invalid control mode
        TestUtils.errorLogExpected(() -> {
            VehicleStatus command = (VehicleStatus) CommandResolver.resolve(bytes);
            assertTrue(command.getStates().length == 2); // invalid command is added as a base
            // command class
        });
    }
}
