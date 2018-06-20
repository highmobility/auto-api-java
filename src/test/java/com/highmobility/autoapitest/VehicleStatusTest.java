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
import com.highmobility.autoapi.property.TrunkLockState;
import com.highmobility.autoapi.property.TrunkPosition;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Before;
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
            "0011010100114a46325348424443374348343531383639020001010300065479706520580400064d79204361720500064142433132330600085061636B6167652B07000207E108000C4573746f72696c20426c617509000200DC0A0001050B00010599000B002101010001000200010199000700270101000102" +
                    "0C0004402000000D000200F50E000101" // l7
    );

    com.highmobility.autoapi.VehicleStatus vehicleStatus;

    @Before
    public void setup() {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
        if (command != null && command instanceof VehicleStatus) {
            vehicleStatus = (VehicleStatus) command;
        } else {
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

        assertTrue(vehicleStatus.getState(TrunkState.TYPE) != null);

        assertTrue(vehicleStatus.getEngineVolume() == 2.5f);
        assertTrue(vehicleStatus.getMaxTorque() == 245);
        assertTrue(vehicleStatus.getGearBox() == VehicleStatus.Gearbox.AUTOMATIC);
    }

    @Test public void build() {
        VehicleStatus status = getVehicleStatusBuilderWithoutSignature().build();
        assertTrue(status.equals(bytes));
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

        TrunkState.Builder trunkState = new TrunkState.Builder();
        trunkState.setLockState(TrunkLockState.UNLOCKED);
        trunkState.setPosition(TrunkPosition.OPEN);
        builder.addProperty(new CommandProperty(trunkState.build()));

        ControlMode.Builder controlCommand = new ControlMode.Builder();
        controlCommand.setMode(STARTED);
        builder.addProperty(new CommandProperty(controlCommand.build()));

        // l7
        builder.setEngineVolume(2.5f);
        builder.setMaxTorque(245);
        builder.setGearBox(VehicleStatus.Gearbox.AUTOMATIC);

        return builder;
    }

    @Test public void createWithSignature() {
        VehicleStatus.Builder builder = getVehicleStatusBuilderWithoutSignature();
        byte[] nonce = ByteUtils.bytesFromHex("324244433743483436");
        builder.setNonce(nonce);
        byte[] signature = ByteUtils.bytesFromHex
                ("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6");
        builder.setSignature(signature);

        VehicleStatus status = builder.build();
        byte[] command = status.getByteArray();
        assertTrue(Arrays.equals(command, command));
        assertTrue(Arrays.equals(status.getNonce(), nonce));
        assertTrue(Arrays.equals(status.getSignature(), signature));
    }

    @Test public void maiduTest() {
        Bytes bytes = new Bytes
                ("0011010A000105991B002001010003000100010003010000010003020001010003030001");
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
    }

    @Test public void maiduTestTwo() {
        Bytes bytes = new Bytes
                ("00110131484D31363241363748333232393645460208757573206175746F21452D436C61737320436F6D666F7274202620506572736F6E616C69736174696F6E000400230800001C50BF19999A00330B000BB8001200000000500000200D04000001010001020001030001003008425210E741561BEA");
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(((CommandWithProperties) command).getProperties().length != 0);
    }

    @Test public void testFloatOverFlow() {
        Bytes bytes = new Bytes
                ("00110101001131484D43393342343348454232413433430200010003000661206E616D6504001047656E6572616C20456D756C61746F72050009422D484D2D3637323799003C00230101000100020002001E03000150040004BF19999A050004BF19999A060002000007000200000800016409000200000A0004000000000B00010099001F0053010100010102000100030004001525150300040115251504000319370099004800240101000441B800000200044190000003000441B8000004000441B0000006000100070001000800010009000441B80000050001000A000FF80800080008000800080008000800990083003301010003000BB80200020012030002000004000200000500015006000200C8070004410C000008000440C66666090001000A000B00401333334220000000000A000B01401333334220000000000A000B02401333334220000000000A000B03401333334220000000000B0004414000000C0004000000000D000200000E0002000099001B00200101000300000001000301000001000302000001000303000099000700410101000100990007003501010001009900070040010100010099000700480101000100990011005401010004461C4000020004447A00009900150036010100010002000100030001000400030000FF99000E00340101000201900200030075309900360031010100084252167241569C87020025416C6578616E646572706C61747A2C203130313738204265726C696E2C204765726D616E7999000C005201010002000002000100990007005801010001009900230047010100010002000003000004000812020E12332101A405000812020E12332101A499004B0057010100050000000000010005010000000002000100030001000400010005000100060004000000000700040000000008000100090001000A000200000A000201000B0001000C00010099000C00270101000101020002000099000B00250101000100020001009900210056010100030000000100030100000100030200000100030300000100030400009900070046010100010099000E0050010100081108120F043A01A499000B0021010100010102000100990015003001010008425210E741561BEA0200044252147D9900070055010100010099001C0045010100020000010002010001000203000100020400010002050099002A0042010100010002000100030001000400013205000122060001000700010008000800010107000001A4");
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
        assertTrue(ws.getWindowProperties().length == 4);
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

    @Test public void anotherTest() {
        Bytes bytes = new Bytes
                ("00110101001131484D393634363634483442424646343902000101030009546F6E697320636172040012556E6976657273616C2054657374204361720500094F5054494D4953543699005000230101000100020002001E03000150040004BF19999A050004BF19999A06000400000000070004000000000800016409000200000A0004000000000B0001000C0001000D000900120215120D3B01A499001F0053010100010102000100030004001525150300040115251504000319370099004800240101000441B800000200044190000003000441B8000004000441B0000006000100070001000800010009000441B80000050001000A000F600800080008000800080008000800990083003301010003000BB80200020012030002000004000200000500015006000200C8070004410C000008000440C66666090001000A000B00401333334220000000000A000B01401333334220000000000A000B02401333334220000000000A000B03401333334220000000000B0004414000000C0004000000000D000200000E0002000099001B0020010100030000000100030100000100030200000100030300009900070041010100010099000700350101000100990007004001010001009900070026010100010099000700480101000100990011005401010004461C4000020004447A00009900150036010100010002000100030001000400030000FF99000E00340101000201900200030075309900360031010100084252167241569C87020025416C6578616E646572706C61747A2C203130313738204265726C696E2C204765726D616E7999000C0052010100020000020001009900070058010100010099002300470101000100020000030000040008120215120D3B01A4050008120215120D3B01A499005F005701010005000000000001000501000000000100050200000000010005030000000002000100030001000400010005000100060004000000000700040000000008000100090001000A000200000A000201000B0001000C0001000D00010099000C00270101000101020002000099000B00250101000100020001009900210056010100030000000100030100000100030200000100030300000100030400009900070046010100010099000E005001010008120215120D3B01A499000B002101010001010200010099000700280101000100990015003001010008425210E741561BEA0200044252147D9900070055010100010099001200590101000100020001000300000400010099001C0045010100020000010002010001000202000100020300010002040099002A0042010100010002000100030001000400013205000122060001000700010008000800010107000001A4");
        try {
            CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
    }

    @Test public void testOneInvalidVsStateDoesNotMatter() {
        Bytes bytes = new Bytes
                ("00110101001131484D393634363634483442424646343902000101030009546F6E697320636172040012556E6976657273616C2054657374204361720500094F5054494D49535436"
                        + "99000400400100" // invalid gasflap state
                        + "99000700580101000101"); // valid parking brake state

        VehicleStatus command = (VehicleStatus) CommandResolver.resolve(bytes);
        assertTrue(command.getStates().length == 2); // invalid command is added as a base
        // command class
    }
}
