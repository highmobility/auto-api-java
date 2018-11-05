package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DiagnosticsState;
import com.highmobility.autoapi.GetDiagnosticsState;
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
                    /*level7*/"10000101110002001412000444bb94cd13000446d7860014000100150001141600010A1700020041" +
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
                    "1D0018020743313633414641064454523231320750454E44494E47"
    );

    @Test public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == DiagnosticsState.class);
        DiagnosticsState state = (DiagnosticsState) command;

        assertTrue(state.getMileage() == 150000);
        assertTrue(state.getOilTemperature() == 99);
        assertTrue(state.getSpeed() == 60);
        assertTrue(state.getRpm() == 2500);
        assertTrue(state.getRange() == 265);
        assertTrue(state.getFuelLevel() == .9f);
        assertTrue(state.getWasherFluidLevel() == WasherFluidLevel.FULL);
        assertTrue(state.getFuelVolume() == 35.5f);

        assertTrue(state.getNonce() == null);
        assertTrue(state.getSignature() == null);

        assertTrue(state.getBatteryVoltage() == 12f);
        assertTrue(state.getAdBlueLevel() == .5f);
        assertTrue(state.getDistanceDrivenSinceReset() == 1500);
        assertTrue(state.getDistanceDrivenSinceEngineStart() == 10);

        assertTrue(state.isAntiLockBrakingActive());
        assertTrue(state.getEngineCoolantTemperature() == 20);
        assertTrue(state.getEngineTotalOperatingHours() == 1500.65f);
        assertTrue(state.getEngineTotalFuelConsumption() == 27587.0f);
        assertTrue(state.getBrakeFluidLevel() == BrakeFluidLevel.LOW);
        assertTrue(state.getEngineTorque() == .2f);
        assertTrue(state.getEngineLoad() == .1f);
        assertTrue(state.getWheelBasedSpeed() == 65);

        // level 8
        assertTrue(state.getBatteryLevel() == .56f);

        int propertyCount = 0;

        for (CheckControlMessage checkControlMessage : state.getCheckControlMessages()) {
            if (checkControlMessage.getId() == 1 &&
                    checkControlMessage.getText().equals("Check engine") &&
                    checkControlMessage.getStatus().equals("Alert") &&
                    checkControlMessage.getRemainingTime() == 105592) {
                propertyCount++;
            }

            if (checkControlMessage.getId() == 1 &&
                    checkControlMessage.getText().equals("Check engine") &&
                    checkControlMessage.getStatus().equals("Alertt") &&
                    checkControlMessage.getRemainingTime() == 105592) {
                propertyCount++;
            }
        }

        assertTrue(propertyCount == 2);

        propertyCount = 0;
        for (TirePressure pressure : state.getTirePressures()) {
            if (pressure.getPressure() != 2.31f) fail();

            if (pressure.getTireLocation() == TireLocation.FRONT_LEFT) propertyCount++;
            if (pressure.getTireLocation() == TireLocation.FRONT_RIGHT) propertyCount++;
            if (pressure.getTireLocation() == TireLocation.REAR_RIGHT) propertyCount++;
            if (pressure.getTireLocation() == TireLocation.REAR_LEFT) propertyCount++;
        }

        assertTrue(propertyCount == 4);
        propertyCount = 0;

        for (TireTemperature tireTemperature : state.getTireTemperatures()) {
            if (tireTemperature.getTemperature() != 40f) fail();

            if (tireTemperature.getTireLocation() == TireLocation.FRONT_LEFT) propertyCount++;
            if (tireTemperature.getTireLocation() == TireLocation.FRONT_RIGHT) propertyCount++;
            if (tireTemperature.getTireLocation() == TireLocation.REAR_RIGHT) propertyCount++;
            if (tireTemperature.getTireLocation() == TireLocation.REAR_LEFT) propertyCount++;
        }

        assertTrue(propertyCount == 4);
        propertyCount = 0;

        for (WheelRpm wheelRpm : state.getWheelRpms()) {
            if (wheelRpm.getRpm() != 746) fail();

            if (wheelRpm.getTireLocation() == TireLocation.FRONT_LEFT) propertyCount++;
            if (wheelRpm.getTireLocation() == TireLocation.FRONT_RIGHT) propertyCount++;
            if (wheelRpm.getTireLocation() == TireLocation.REAR_RIGHT) propertyCount++;
            if (wheelRpm.getTireLocation() == TireLocation.REAR_LEFT) propertyCount++;
        }

        assertTrue(propertyCount == 4);
        propertyCount = 0;

        for (DiagnosticsTroubleCode diagnosticsTroubleCode : state.getTroubleCodes()) {
            if (diagnosticsTroubleCode.getId().equals("C1116FA") &&
                    diagnosticsTroubleCode.getEcuId().equals("RDU_212FR") &&
                    diagnosticsTroubleCode.getStatus().equals("PENDING") &&
                    diagnosticsTroubleCode.getNumberOfOccurences() == 2) {
                propertyCount++;
            } else if (diagnosticsTroubleCode.getId().equals("C163AFA") &&
                    diagnosticsTroubleCode.getEcuId().equals("DTR212") &&
                    diagnosticsTroubleCode.getStatus().equals("PENDING") &&
                    diagnosticsTroubleCode.getNumberOfOccurences() == 2) {
                propertyCount++;
            }
        }

        assertTrue(propertyCount == 2);
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
        builder.setMileage(150000);
        builder.setOilTemperature(99);
        builder.setSpeed(60);
        builder.setRpm(2500);
        builder.setFuelLevel(.9f);
        builder.setRange(265);
        builder.setWasherFluidLevel(WasherFluidLevel.FULL);

        builder.setBatteryVoltage(12f);
        builder.setAdBlueLevel(.5f);
        builder.setDistanceDrivenSinceReset(1500);
        builder.setDistanceDrivenSinceEngineStart(10);
        builder.setFuelVolume(35.5f);

        builder.setAntiLockBrakingActive(true);
        builder.setEngineCoolantTemperature(20);
        builder.setEngineTotalOperatingHours(1500.65f);
        builder.setEngineTotalFuelConsumption(27587.0f);
        builder.setBrakeFluidLevel(BrakeFluidLevel.LOW);
        builder.setEngineTorque(.2f);
        builder.setEngineLoad(.1f);
        builder.setWheelBasedSpeed(65);

        // level8
        builder.setBatteryLevel(.56f);

        CheckControlMessage msg1 = new CheckControlMessage(1, 105592, "Check engine", "Alert");
        CheckControlMessage msg2 = new CheckControlMessage(1, 105592, "Check engine", "Alertt");
        builder.addCheckControlMessage(msg1);
        builder.addCheckControlMessage(msg2);

        builder.addTirePressure(new TirePressure(TireLocation.FRONT_LEFT, 2.31f));
        builder.addTirePressure(new TirePressure(TireLocation.FRONT_RIGHT, 2.31f));
        builder.addTirePressure(new TirePressure(TireLocation.REAR_RIGHT, 2.31f));
        builder.addTirePressure(new TirePressure(TireLocation.REAR_LEFT, 2.31f));

        builder.addTireTemperature(new TireTemperature(TireLocation.FRONT_LEFT, 40f));
        builder.addTireTemperature(new TireTemperature(TireLocation.FRONT_RIGHT, 40f));
        builder.addTireTemperature(new TireTemperature(TireLocation.REAR_RIGHT, 40f));
        builder.addTireTemperature(new TireTemperature(TireLocation.REAR_LEFT, 40f));

        builder.addWheelRpm(new WheelRpm(TireLocation.FRONT_LEFT, 746));
        builder.addWheelRpm(new WheelRpm(TireLocation.FRONT_RIGHT, 746));
        builder.addWheelRpm(new WheelRpm(TireLocation.REAR_RIGHT, 746));
        builder.addWheelRpm(new WheelRpm(TireLocation.REAR_LEFT, 746));

        DiagnosticsTroubleCode code1 = new DiagnosticsTroubleCode(2, "C1116FA", "RDU_212FR",
                "PENDING");
        DiagnosticsTroubleCode code2 = new DiagnosticsTroubleCode(2, "C163AFA", "DTR212",
                "PENDING");
        builder.addTroubleCode(code1);
        builder.addTroubleCode(code2);

        assertTrue(TestUtils.bytesTheSame(builder.build(), bytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("003301");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((DiagnosticsState) state).getBatteryVoltage() == null);
    }
}
