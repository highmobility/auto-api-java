/*
 * The MIT License
 *
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleInformationTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "001101" +
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
                    "0F000401000100" + // display unit km
                    "10000401000100" + // driver seat left
                    "11001201000F5061726B696E672073656E736F7273" + // Parking sensors
                    "1100130100104175746F6D6174696320776970657273" + // Automatic wipers
                    // l9
                    "12000B0100084D65726365646573"
    );

    // TODO: 2/6/20 test the properties
    @Test public void state() {
        testState((VehicleInformation.State) CommandResolver.resolve(bytes));
    }

    private void testState(VehicleInformation.State state) {
        assertTrue(state.getVin().getValue().equals("JF2SHBDC7CH451869"));
        assertTrue(state.getPowertrain().getValue() == VehicleInformation.Powertrain.ALL_ELECTRIC);
        assertTrue(state.getModelName().getValue().equals("Type X"));
        assertTrue(state.getName().getValue().equals("My Car"));
        assertTrue(state.getLicensePlate().getValue().equals("ABC123"));

        assertTrue(state.getSalesDesignation().getValue().equals("Package+"));
        assertTrue(state.getModelYear().getValue() == 2017);
        assertTrue(state.getColourName().getValue().equals("Estoril Blau"));
        assertTrue(state.getPowerInKW().getValue() == 220);
        assertTrue(state.getNumberOfDoors().getValue() == 5);
        assertTrue(state.getNumberOfSeats().getValue() == 5);

        assertTrue(state.getEngineVolume().getValue() == 2.5f);
        assertTrue(state.getEngineMaxTorque().getValue() == 245);
        assertTrue(state.getGearbox().getValue() == VehicleInformation.Gearbox.AUTOMATIC);

        assertTrue(state.getDisplayUnit().getValue() == VehicleInformation.DisplayUnit.KM);
        assertTrue(state.getDriverSeatLocation().getValue() == VehicleInformation.DriverSeatLocation.LEFT);
        assertTrue(state.getEquipments().length == 2);

        int count = 0;
        for (Property<String> s : state.getEquipments()) {
            if (s.getValue().equals("Parking sensors") || s.getValue().equals("Automatic wipers"))
                count++;
        }
        assertTrue(count == 2);
        assertTrue(state.getBrand().getValue().equals("Mercedes"));

        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() throws CommandParseException {
        VehicleInformation.State status = getVehicleStatusStateBuilderWithoutSignature().build();
        testState(status);
    }

    @Test public void get() {
        Bytes bytes = new Bytes(COMMAND_HEADER + "001100");
        Bytes commandBytes = new VehicleInformation.GetVehicleInformation();
        assertTrue(bytes.equals(commandBytes));

        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof VehicleInformation.GetVehicleInformation);
    }

    VehicleInformation.State.Builder getVehicleStatusStateBuilderWithoutSignature() throws CommandParseException {
        VehicleInformation.State.Builder builder = new VehicleInformation.State.Builder();
        builder.setVin(new Property("JF2SHBDC7CH451869"));
        builder.setPowertrain(new Property(VehicleInformation.Powertrain.ALL_ELECTRIC));
        builder.setModelName(new Property("Type X"));
        builder.setName(new Property("My Car"));
        builder.setLicensePlate(new Property("ABC123"));
        builder.setSalesDesignation(new Property("Package+"));
        builder.setModelYear(new Property(2017));

        builder.setColourName(new Property("Estoril Blau"));
        builder.setPowerInKW(new Property(220));
        builder.setNumberOfDoors(new Property(5)).setNumberOfSeats(new Property(5));

        // l7
        builder.setEngineVolume(new Property(2.5f));
        builder.setEngineMaxTorque(new Property(245));
        builder.setGearbox(new Property(VehicleInformation.Gearbox.AUTOMATIC));

        // l8
        builder.setDisplayUnit(new Property(VehicleInformation.DisplayUnit.KM));
        builder.setDriverSeatLocation(new Property(VehicleInformation.DriverSeatLocation.LEFT));
        builder.addEquipment(new Property("Parking sensors"));
        builder.addEquipment(new Property("Automatic wipers"));

        // l9
        builder.setBrand(new Property("Mercedes"));

        return builder;
    }

    @Test public void createWithSignature() throws CommandParseException {
        VehicleInformation.State.Builder builder = getVehicleStatusStateBuilderWithoutSignature();
        Bytes nonce = new Bytes("324244433743483436");
        builder.setNonce(nonce);
        Bytes signature = new Bytes
                ("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6");
        builder.setSignature(signature);

        VehicleInformation.State status = builder.build();
        byte[] command = status.getByteArray();
        assertTrue(Arrays.equals(command, command));
        assertTrue(status.getNonce().equals(nonce));
        assertTrue(status.getSignature().equals(signature));
    }

    @Test public void zeroProperties() {
        VehicleInformation.State.Builder builder = new VehicleInformation.State.Builder();
        VehicleInformation.State vs = builder.build();

        assertTrue(vs.getNumberOfDoors() == null);
        assertTrue(vs.getByteArray().length == Command.HEADER_LENGTH + 3);

        Bytes bytes = new Bytes(COMMAND_HEADER + "00110100");
        vs = (VehicleInformation.State) CommandResolver.resolve(bytes);

        assertTrue(vs.getNumberOfDoors().getValue() == null);
    }
}
