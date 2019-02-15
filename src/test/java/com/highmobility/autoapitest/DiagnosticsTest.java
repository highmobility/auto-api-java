package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DiagnosticsState;
import com.highmobility.autoapi.GetDiagnosticsState;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.ObjectPropertyPercentage;
import com.highmobility.autoapi.property.diagnostics.BrakeFluidLevel;
import com.highmobility.autoapi.property.diagnostics.CheckControlMessage;
import com.highmobility.autoapi.property.diagnostics.DiagnosticsTroubleCode;
import com.highmobility.autoapi.property.diagnostics.TirePressure;
import com.highmobility.autoapi.property.diagnostics.TireTemperature;
import com.highmobility.autoapi.property.diagnostics.WasherFluidLevel;
import com.highmobility.autoapi.property.diagnostics.WheelRpm;
import com.highmobility.autoapi.property.value.TireLocation;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class DiagnosticsTest {
    Bytes bytes = new Bytes(
            "0033010100030249F00200020063030002003C04000209C40500015A0600020109090001010B0004414000000C00043F0000000D000205DC0E0002000A" +
                    "0F0004420E0000" +
                    /*level7*/
                    "10000101110002001412000444bb94cd13000446d7860014000100150001141600010A1700020041" +
                    /*level8*/"18000138" +
                    "19001A000100019C78000C436865636B20656E67696E6505416C657274" + // check control
                    // message 1
                    "19001B000100019C78000C436865636B20656E67696E6506416C65727474" + // check
                    // control message 2
                    "1A0005004013d70a1A0005014013d70a1A0005024013d70a1A0005034013d70a" + // tire
                    // pressure
                    "1B000500422000001B000501422000001B000502422000001B00050342200000" + // tire
                    // temp
                    "1C00030002EA1C00030102EA1C00030202EA1C00030302EA" + // wheel rpms
                    "1D001B020743313131364641095244555F32313246520750454E44494E47" +
                    "1D0018020743313633414641064454523231320750454E44494E47" +
                    "1E0004000249f0"); // TT-171

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
        assertTrue(state.getFuelLevel().getValue() == 90);
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
        assertTrue(state.getEngineTorque().getValue() == 20);
        assertTrue(state.getEngineLoad().getValue() == 10);
        assertTrue(state.getWheelBasedSpeed().getValue() == 65);

        // level 8
        assertTrue(state.getBatteryLevel().getValue() == 56);

        int propertyCount = 0;

        for (ObjectProperty<CheckControlMessage> checkControlMessage :
                state.getCheckControlMessages()) {
            if (checkControlMessage.getValue().getId() == 1 &&
                    checkControlMessage.getValue().getText().equals("Check engine") &&
                    checkControlMessage.getValue().getStatus().equals("Alert") &&
                    checkControlMessage.getValue().getRemainingTime() == 105592) {
                propertyCount++;
            }

            if (checkControlMessage.getValue().getId() == 1 &&
                    checkControlMessage.getValue().getText().equals("Check engine") &&
                    checkControlMessage.getValue().getStatus().equals("Alertt") &&
                    checkControlMessage.getValue().getRemainingTime() == 105592) {
                propertyCount++;
            }
        }

        assertTrue(propertyCount == 2);

        propertyCount = 0;
        for (ObjectProperty<TirePressure> pressure : state.getTirePressures()) {
            if (pressure.getValue().getPressure() != 2.31f) fail();

            if (pressure.getValue().getTireLocation() == TireLocation.FRONT_LEFT) propertyCount++;
            if (pressure.getValue().getTireLocation() == TireLocation.FRONT_RIGHT) propertyCount++;
            if (pressure.getValue().getTireLocation() == TireLocation.REAR_RIGHT) propertyCount++;
            if (pressure.getValue().getTireLocation() == TireLocation.REAR_LEFT) propertyCount++;
        }

        assertTrue(propertyCount == 4);
        propertyCount = 0;

        for (ObjectProperty<TireTemperature> tireTemperature : state.getTireTemperatures()) {
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

        for (ObjectProperty<WheelRpm> wheelRpm : state.getWheelRpms()) {
            if (wheelRpm.getValue().getRpm() != 746) fail();

            if (wheelRpm.getValue().getTireLocation() == TireLocation.FRONT_LEFT) propertyCount++;
            if (wheelRpm.getValue().getTireLocation() == TireLocation.FRONT_RIGHT) propertyCount++;
            if (wheelRpm.getValue().getTireLocation() == TireLocation.REAR_RIGHT) propertyCount++;
            if (wheelRpm.getValue().getTireLocation() == TireLocation.REAR_LEFT) propertyCount++;
        }

        assertTrue(propertyCount == 4);
        propertyCount = 0;

        for (ObjectProperty<DiagnosticsTroubleCode> diagnosticsTroubleCode :
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
    }

    @Test public void stateWithTimestamp() {
        Bytes timestampBytes = bytes.concat(new Bytes("A4000911010A112200000002"));
        DiagnosticsState command = (DiagnosticsState) CommandResolver.resolve(timestampBytes);
        assertTrue(command.getOilTemperature().getTimestamp() != null);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("003300");
        String commandBytes = ByteUtils.hexFromBytes(new GetDiagnosticsState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof GetDiagnosticsState);
    }

    @Test public void build() {
        DiagnosticsState.Builder builder = new DiagnosticsState.Builder();
        builder.setMileage(new ObjectPropertyInteger(150000));
        builder.setOilTemperature(new ObjectPropertyInteger(99));
        builder.setSpeed(new ObjectPropertyInteger(60));
        builder.setRpm(new ObjectPropertyInteger(2500));
        builder.setFuelLevel(new ObjectPropertyPercentage(90));
        builder.setRange(new ObjectPropertyInteger(265));
        builder.setWasherFluidLevel(new ObjectProperty<>(WasherFluidLevel.FULL));

        builder.setBatteryVoltage(new ObjectProperty<Float>(12f));
        builder.setAdBlueLevel(new ObjectProperty<Float>(.5f));
        builder.setDistanceDrivenSinceReset(new ObjectPropertyInteger(1500));
        builder.setDistanceDrivenSinceEngineStart(new ObjectPropertyInteger(10));
        builder.setFuelVolume(new ObjectProperty<Float>(35.5f));

        builder.setAntiLockBrakingActive(new ObjectProperty<>(true));
        builder.setEngineCoolantTemperature(new ObjectPropertyInteger(20));
        builder.setEngineTotalOperatingHours(new ObjectProperty<>(1500.65f));
        builder.setEngineTotalFuelConsumption(new ObjectProperty<>(27587.0f));
        builder.setBrakeFluidLevel(new ObjectProperty<>(BrakeFluidLevel.LOW));
        builder.setEngineTorque(new ObjectPropertyPercentage(20));
        builder.setEngineLoad(new ObjectPropertyPercentage(10));
        builder.setWheelBasedSpeed(new ObjectPropertyInteger(65));

        // level8
        builder.setBatteryLevel(new ObjectPropertyPercentage(56));

        ObjectProperty msg1 = new ObjectProperty<>(new CheckControlMessage(1, 105592, "Check " +
                "engine", "Alert"));
        ObjectProperty msg2 = new ObjectProperty<>(new CheckControlMessage(1, 105592, "Check " +
                "engine", "Alertt"));
        builder.addCheckControlMessage(msg1);
        builder.addCheckControlMessage(msg2);

        builder.addTirePressure(new ObjectProperty<>(new TirePressure(TireLocation.FRONT_LEFT,
                2.31f)));
        builder.addTirePressure(new ObjectProperty<>(new TirePressure(TireLocation.FRONT_RIGHT,
                2.31f)));
        builder.addTirePressure(new ObjectProperty<>(new TirePressure(TireLocation.REAR_RIGHT,
                2.31f)));
        builder.addTirePressure(new ObjectProperty<>(new TirePressure(TireLocation.REAR_LEFT,
                2.31f)));

        builder.addTireTemperature(new ObjectProperty<>(new TireTemperature(TireLocation.FRONT_LEFT, 40f)));
        builder.addTireTemperature(new ObjectProperty<>(new TireTemperature(TireLocation.FRONT_RIGHT, 40f)));
        builder.addTireTemperature(new ObjectProperty<>(new TireTemperature(TireLocation.REAR_RIGHT, 40f)));
        builder.addTireTemperature(new ObjectProperty<>(new TireTemperature(TireLocation.REAR_LEFT, 40f)));

        builder.addWheelRpm(new ObjectProperty<>(new WheelRpm(TireLocation.FRONT_LEFT, 746)));
        builder.addWheelRpm(new ObjectProperty<>(new WheelRpm(TireLocation.FRONT_RIGHT, 746)));
        builder.addWheelRpm(new ObjectProperty<>(new WheelRpm(TireLocation.REAR_RIGHT, 746)));
        builder.addWheelRpm(new ObjectProperty<>(new WheelRpm(TireLocation.REAR_LEFT, 746)));

        ObjectProperty code1 = new ObjectProperty<>(new DiagnosticsTroubleCode(2, "C1116FA",
                "RDU_212FR",
                "PENDING"));
        ObjectProperty code2 = new ObjectProperty<>(new DiagnosticsTroubleCode(2, "C163AFA",
                "DTR212",
                "PENDING"));
        builder.addTroubleCode(code1);
        builder.addTroubleCode(code2);
        builder.setMileageMeters(new ObjectPropertyInteger(150000));
        DiagnosticsState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        testState(state);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("003301");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((DiagnosticsState) state).getBatteryVoltage().getValue() == null);
    }
}
