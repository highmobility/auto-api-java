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
public class VehicleStatusTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "001101" +
                    "990015010012" + COMMAND_HEADER + "0021010100040100010002000401000101" + //
                    // Trunk open
                    "99000E01000B" + COMMAND_HEADER + "00270101000401000102" // Remote Control
            // Started
    );

    @Test public void state() {
        testState((VehicleStatus.State) CommandResolver.resolve(bytes));
    }

    private void testState(VehicleStatus.State state) {
        assertTrue(state.getStates().length == 2);

        assertTrue(state.getState(Identifier.TRUNK).getValue() != null);

        Command command = getState(RemoteControl.State.class, state);
        RemoteControl.State controlMode = (RemoteControl.State) command;
        assertTrue(controlMode.getControlMode().getValue() == RemoteControl.ControlMode.STARTED);

        command = getState(Trunk.State.class, state);
        Trunk.State trunkState = (Trunk.State) command;
        assertTrue(trunkState.getLock().getValue() == LockState.UNLOCKED);
        assertTrue(trunkState.getPosition().getValue() == Position.OPEN);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() throws CommandParseException {
        VehicleStatus.State status = getVehicleStatusStateBuilderWithoutSignature().build();
        testState(status);
    }

    @Test public void get() {
        Bytes bytes = new Bytes(COMMAND_HEADER + "001100");
        Bytes commandBytes = new VehicleStatus.GetVehicleStatus();
        assertTrue(bytes.equals(commandBytes));

        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof VehicleStatus.GetVehicleStatus);
    }

    Command getState(Class forClass, VehicleStatus.State command) {
        for (int i = 0; i < command.getStates().length; i++) {
            Property<Command> state = command.getStates()[i];
            if (state != null && state.getValue().getClass().equals(forClass))
                return state.getValue();
        }

        return null;
    }

    VehicleStatus.State.Builder getVehicleStatusStateBuilderWithoutSignature() throws CommandParseException {
        VehicleStatus.State.Builder builder = new VehicleStatus.State.Builder();

        Trunk.State.Builder trunkState = new Trunk.State.Builder();
        trunkState.setLock(new Property(LockState.UNLOCKED));
        trunkState.setPosition(new Property(Position.OPEN));
        builder.addState(new Property(trunkState.build()));

        RemoteControl.State controlMode =
                new RemoteControl.State(new Bytes(COMMAND_HEADER + "00270101000401000102").getByteArray());
        builder.addState(new Property(controlMode));

        return builder;
    }

    @Test public void createWithSignature() throws CommandParseException {
        VehicleStatus.State.Builder builder = getVehicleStatusStateBuilderWithoutSignature();
        Bytes nonce = new Bytes("324244433743483436");
        builder.setNonce(nonce);
        Bytes signature = new Bytes
                ("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6");
        builder.setSignature(signature);

        VehicleStatus.State status = builder.build();
        byte[] command = status.getByteArray();
        assertTrue(Arrays.equals(command, command));
        assertTrue(status.getNonce().equals(nonce));
        assertTrue(status.getSignature().equals(signature));
    }

    @Test public void testInvalidProperty() {
        Bytes bytes = new Bytes
                (COMMAND_HEADER + "001101" +
                        "0100140100094a46325348424443374348343531383639" +
                        "99003001002D" + //
                        COMMAND_HEADER + "004501" + // windows
                        "0200050100020238" + // invalid
                        "0200050100020312" + // invalid
                        "0300050100020201" +
                        "0300050100020300" +
                        "03000501000201FF" + // invalid Window Position FF
                        "99000F01000C" + COMMAND_HEADER + "00270101000401000102"); // control mode
        // command
        TestUtils.errorLogExpected(3, () -> {
            Command command = CommandResolver.resolve(bytes);
            VehicleStatus.State vs = (VehicleStatus.State) command;
            // one window property will fail to parse
            Windows.State ws = (Windows.State) vs.getState(Identifier.WINDOWS).getValue();
            assertTrue(ws.getProperties().length == 5);
            assertTrue(ws.getPositions().length == 3);
        });
    }

    @Test public void zeroProperties() {
        VehicleStatus.State.Builder builder = new VehicleStatus.State.Builder();
        VehicleStatus.State vs = builder.build();

        assertTrue(vs.getStates().length == 0);
        assertTrue(vs.getState(Identifier.THEFT_ALARM) == null);
        assertTrue(vs.getByteArray().length == Command.HEADER_LENGTH + 3);

        Bytes bytes = new Bytes(COMMAND_HEADER + "00110100");
        vs = (VehicleStatus.State) CommandResolver.resolve(bytes);

        assertTrue(vs.getStates().length == 0);
        assertTrue(vs.getState(Identifier.THEFT_ALARM) == null);
    }

    @Test public void testOneInvalidVsStateDoesNotMatter() {
        Bytes bytes = new Bytes
                (COMMAND_HEADER + "001101" +
                        "990015010012" + COMMAND_HEADER + "0021010100040100010002000401000101" +
                        "99000E01000B" + COMMAND_HEADER + "00270101000401000115"); //invalid
        // control mode
        TestUtils.errorLogExpected(() -> {
            VehicleStatus.State command = (VehicleStatus.State) CommandResolver.resolve(bytes);
            assertTrue(command.getStates().length == 2); // invalid command is added as a base
            // command class
        });
    }
}
