package com.highmobility.v2.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DiagnosticsState;
import com.highmobility.autoapi.GetDiagnosticsState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.TireLocation;
import com.highmobility.autoapi.value.diagnostics.BrakeFluidLevel;
import com.highmobility.autoapi.value.diagnostics.CheckControlMessage;
import com.highmobility.autoapi.value.diagnostics.DiagnosticsTroubleCode;
import com.highmobility.autoapi.value.diagnostics.TirePressure;
import com.highmobility.autoapi.value.diagnostics.TireTemperature;
import com.highmobility.autoapi.value.diagnostics.WasherFluidLevel;
import com.highmobility.autoapi.value.diagnostics.WheelRpm;
import com.highmobility.autoapitest.TestUtils;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class DiagnosticsTest {
    Bytes bytes = new Bytes(
            "003301" +
                    "010007010004000249F0" +
                    "0200050100020063" +
                    "030005010002003C" +
                    "04000501000209C4" +
                    "05000B0100083FECCCCCCCCCCCCD" +
                    "0600050100020109" +
                    "09000401000101" +
                    "0B000701000441400000" +
                    "0C00070100043F000000" +
                    "0D000501000205DC" +
                    "0E0005010002000A" +
                    "0F0007010004420E0000" +
                    "10000401000101" +
                    "1100050100020014" +
                    "12000701000444BB94CD" +
                    "13000701000446D78600" +
                    "14000401000100" +
                    "15000B0100083FC999999999999A" +
                    "16000B0100083FB999999999999A" +
                    "1700050100020041" +
                    "18000B0100083FE1EB851EB851EC" +
                    "19001E01001B000100019C78000C436865636B20656E67696E650005416C657274" +
                    "19001F01001C000100019C78000C436865636B20656E67696E650006416C65727474" +
                    "1A0008010005004013D70A" +
                    "1A0008010005014013D70A" +
                    "1A0008010005024013D70A" +
                    "1A0008010005034013D70A" +
                    "1B00080100050042200000" +
                    "1B00080100050142200000" +
                    "1B00080100050242200000" +
                    "1B00080100050342200000" +
                    "1C00060100030002EA" +
                    "1C00060100030102EA" +
                    "1C00060100030202EA" +
                    "1C00060100030302EA" +
                    "1D001E01001B020743313131364641095244555F32313246520750454E44494E47" +
                    "1D001B010018020743313633414641064454523231320750454E44494E47" +
                    "1E0007010004000249F0"
    );

    @Test public void state() {
        DiagnosticsState state = (DiagnosticsState) CommandResolver.resolve(bytes);
        testState(state);
    }

    private void testState(DiagnosticsState state) {
        assertTrue(state.getNonce() == null);
        assertTrue(state.getSignature() == null);

        assertTrue(state.getMileage().getValue() == 150000);
        assertTrue(state.getOilTemperature().getValue() == 99);
        assertTrue(state.getSpeed().getValue() == 60);
        assertTrue(state.getRpm().getValue() == 2500);
        assertTrue(state.getRange().getValue() == 265);
        assertTrue(state.getFuelLevel().getValue() == .9d);
        assertTrue(state.getWasherFluidLevel().getValue() == WasherFluidLevel.FULL);
        assertTrue(state.getFuelVolume().getValue() == 35.5f);

        assertTrue(state.getBatteryVoltage().getValue() == 12f);
        assertTrue(state.getAdBlueLevel().getValue() == .5f);
        assertTrue(state.getDistanceDrivenSinceReset().getValue() == 1500);
        assertTrue(state.getDistanceDrivenSinceEngineStart().getValue() == 10);

        assertTrue(state.isAntiLockBrakingActive().getValue());
        assertTrue(state.getEngineCoolantTemperature().getValue() == 20);
        assertTrue(state.getEngineTotalOperatingHours().getValue() == 1500.65f);
        assertTrue(state.getEngineTotalFuelConsumption().getValue() == 27587.0f);
        assertTrue(state.getBrakeFluidLevel().getValue() == BrakeFluidLevel.LOW);
        assertTrue(state.getEngineTorque().getValue() == .2d);
        assertTrue(state.getEngineLoad().getValue() == .1d);
        assertTrue(state.getWheelBasedSpeed().getValue() == 65d);

        // level 8
        assertTrue(state.getBatteryLevel().getValue() == .56d);

        int propertyCount = 0;

        for (Property<CheckControlMessage> checkControlMessage : state.getCheckControlMessages()) {
            if (checkControlMessage.getValue().getID() == 1 &&
                    checkControlMessage.getValue().getText().equals("Check engine") &&
                    checkControlMessage.getValue().getStatus().equals("Alert") &&
                    checkControlMessage.getValue().getRemainingMinutes() == 105592) {
                propertyCount++;
            }

            if (checkControlMessage.getValue().getID() == 1 &&
                    checkControlMessage.getValue().getText().equals("Check engine") &&
                    checkControlMessage.getValue().getStatus().equals("Alertt") &&
                    checkControlMessage.getValue().getRemainingMinutes() == 105592) {
                propertyCount++;
            }
        }

        assertTrue(propertyCount == 2);

        propertyCount = 0;
        for (Property<TirePressure> pressure : state.getTirePressures()) {
            if (pressure.getValue().getPressure() != 2.31f) fail();

            if (pressure.getValue().getTireLocation() == TireLocation.FRONT_LEFT) propertyCount++;
            if (pressure.getValue().getTireLocation() == TireLocation.FRONT_RIGHT) propertyCount++;
            if (pressure.getValue().getTireLocation() == TireLocation.REAR_RIGHT) propertyCount++;
            if (pressure.getValue().getTireLocation() == TireLocation.REAR_LEFT) propertyCount++;
        }

        assertTrue(propertyCount == 4);
        propertyCount = 0;

        for (Property<TireTemperature> tireTemperature : state.getTireTemperatures()) {
            if (tireTemperature.getValue().getTemperature() != 40f) fail();

            if (tireTemperature.getValue().getTireLocation() == TireLocation.FRONT_LEFT)
                propertyCount++;
            if (tireTemperature.getValue().getTireLocation() == TireLocation.FRONT_RIGHT)
                propertyCount++;
            if (tireTemperature.getValue().getTireLocation() == TireLocation.REAR_RIGHT)
                propertyCount++;
            if (tireTemperature.getValue().getTireLocation() == TireLocation.REAR_LEFT)
                propertyCount++;
        }

        assertTrue(propertyCount == 4);
        propertyCount = 0;

        for (Property<WheelRpm> wheelRpm : state.getWheelRpms()) {
            if (wheelRpm.getValue().getRpm() != 746) fail();

            if (wheelRpm.getValue().getTireLocation() == TireLocation.FRONT_LEFT) propertyCount++;
            if (wheelRpm.getValue().getTireLocation() == TireLocation.FRONT_RIGHT) propertyCount++;
            if (wheelRpm.getValue().getTireLocation() == TireLocation.REAR_RIGHT) propertyCount++;
            if (wheelRpm.getValue().getTireLocation() == TireLocation.REAR_LEFT) propertyCount++;
        }

        assertTrue(propertyCount == 4);
        propertyCount = 0;

        for (Property<DiagnosticsTroubleCode> diagnosticsTroubleCode :
                state.getTroubleCodes()) {
            if (diagnosticsTroubleCode.getValue().getId().equals("C1116FA") &&
                    diagnosticsTroubleCode.getValue().getEcuId().equals("RDU_212FR") &&
                    diagnosticsTroubleCode.getValue().getStatus().equals("PENDING") &&
                    diagnosticsTroubleCode.getValue().getNumberOfOccurences() == 2) {
                propertyCount++;
            } else if (diagnosticsTroubleCode.getValue().getId().equals("C163AFA") &&
                    diagnosticsTroubleCode.getValue().getEcuId().equals("DTR212") &&
                    diagnosticsTroubleCode.getValue().getStatus().equals("PENDING") &&
                    diagnosticsTroubleCode.getValue().getNumberOfOccurences() == 2) {
                propertyCount++;
            }
        }

        assertTrue(propertyCount == 2);
        assertTrue(state.getMileageMeters().getValue() == 150000);

        assertTrue(TestUtils.bytesTheSame(bytes, state));
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("003300");
        String commandBytes = ByteUtils.hexFromBytes(new GetDiagnosticsState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof GetDiagnosticsState);
    }

    @Test public void troubleCodeWithZeroValues() throws CommandParseException {
        Property prop =
                new Property(new Bytes("1D001001000400000000020006016A7CF2AEAD").getByteArray());
        Property<DiagnosticsTroubleCode> troubleCode =
                new Property(DiagnosticsTroubleCode.class, prop);
        assertTrue(troubleCode.getValue().getStatus().equals(""));
    }

    @Test public void build() {
        DiagnosticsState.Builder builder = new DiagnosticsState.Builder();

        builder.setMileage(new Property(150000));
        builder.setOilTemperature(new Property(99));
        builder.setSpeed(new Property(60));
        builder.setRpm(new Property(2500));
        builder.setFuelLevel(new Property(.9d));
        builder.setRange(new Property(265));
        builder.setWasherFluidLevel(new Property(WasherFluidLevel.FULL));

        builder.setBatteryVoltage(new Property(12f));
        builder.setAdBlueLevel(new Property(.5f));
        builder.setDistanceDrivenSinceReset(new Property(1500));
        builder.setDistanceDrivenSinceEngineStart(new Property(10));
        builder.setFuelVolume(new Property(35.5f));

        builder.setAntiLockBrakingActive(new Property(true));
        builder.setEngineCoolantTemperature(new Property(20));
        builder.setEngineTotalOperatingHours(new Property(1500.65f));
        builder.setEngineTotalFuelConsumption(new Property(27587.0f));
        builder.setBrakeFluidLevel(new Property(BrakeFluidLevel.LOW));
        builder.setEngineTorque(new Property(.2d));
        builder.setEngineLoad(new Property(.1d));
        builder.setWheelBasedSpeed(new Property(65));

        // level8
        builder.setBatteryLevel(new Property(.56d));

        Property msg1 = new Property(new CheckControlMessage(1, 105592, "Check " +
                "engine", "Alert"));
        Property msg2 = new Property(new CheckControlMessage(1, 105592, "Check " +
                "engine", "Alertt"));
        builder.addCheckControlMessage(msg1);
        builder.addCheckControlMessage(msg2);

        builder.addTirePressure(new Property(new TirePressure(TireLocation.FRONT_LEFT,
                2.31f)));
        builder.addTirePressure(new Property(new TirePressure(TireLocation.FRONT_RIGHT,
                2.31f)));
        builder.addTirePressure(new Property(new TirePressure(TireLocation.REAR_RIGHT,
                2.31f)));
        builder.addTirePressure(new Property(new TirePressure(TireLocation.REAR_LEFT,
                2.31f)));

        builder.addTireTemperature(new Property(new TireTemperature(TireLocation.FRONT_LEFT, 40f)));
        builder.addTireTemperature(new Property(new TireTemperature(TireLocation.FRONT_RIGHT,
                40f)));
        builder.addTireTemperature(new Property(new TireTemperature(TireLocation.REAR_RIGHT, 40f)));
        builder.addTireTemperature(new Property(new TireTemperature(TireLocation.REAR_LEFT, 40f)));

        builder.addWheelRpm(new Property(new WheelRpm(TireLocation.FRONT_LEFT, 746)));
        builder.addWheelRpm(new Property(new WheelRpm(TireLocation.FRONT_RIGHT, 746)));
        builder.addWheelRpm(new Property(new WheelRpm(TireLocation.REAR_RIGHT, 746)));
        builder.addWheelRpm(new Property(new WheelRpm(TireLocation.REAR_LEFT, 746)));

        Property code1 = new Property(new DiagnosticsTroubleCode(2, "C1116FA",
                "RDU_212FR",
                "PENDING"));
        Property code2 = new Property(new DiagnosticsTroubleCode(2, "C163AFA",
                "DTR212",
                "PENDING"));
        builder.addTroubleCode(code1);
        builder.addTroubleCode(code2);
        builder.setMileageMeters(new Property(150000));
        DiagnosticsState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        testState(state);
    }
}
