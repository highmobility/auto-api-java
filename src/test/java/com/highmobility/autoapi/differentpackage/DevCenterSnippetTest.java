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
package com.highmobility.autoapi.differentpackage;

import com.highmobility.autoapi.*;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.value.SupportedCapability;
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.value.Bytes;

@SuppressWarnings({"UnusedAssignment", "StatementWithEmptyBody", "ResultOfMethodCallIgnored"})
public class DevCenterSnippetTest {

    // 3 files have snippets:
    // auto api tutorial
    // android tutorial
    // android bluetooth tutorial

    // if test does not compile, a snippet needs to be updated somewhere in docs.
    // these tests are not supposed to pass as tests
    Bytes bytes = null;

    void lockCommand() {
        // tabs/resources/documentation/mobile-sdks/android/auto-api.html
        // tabs/resources/tutorials/sdk/android/android-bluetooth.html
        // tabs/resources/tutorials/sdk/android/android-tutorial.html
        // tabs/resources/samples/android/scaffold.html
        Command command = new Doors.LockUnlockDoors(LockState.UNLOCKED);
    }

    void lockState() {
        // tabs/resources/tutorials/sdk/android/android-bluetooth.html
        // tabs/resources/tutorials/sdk/android/android-tutorial.html
        // tabs/resources/documentation/mobile-sdks/android/auto-api.html
        // tabs/resources/tutorials/for-carmakers/cloud-sdk/cloud-tutorial.html
        Command command = CommandResolver.resolve(bytes);

        if (command instanceof Doors.State) {
            Doors.State doorsState = (Doors.State) command;
            // access the doors state object
        }
    }

    // auto-api tutorial

    void getCommands() {
        new VehicleStatus.GetVehicleStatus();
        new Capabilities.GetCapabilities();
        new VehicleLocation.GetVehicleLocation();
    }

    void vehicleStatus() {
        // tabs/resources/documentation/mobile-sdks/android/auto-api.html

        Command command = CommandResolver.resolve(bytes);

        if (command instanceof VehicleInformation.State) {
            VehicleInformation.State vehicleInfo = (VehicleInformation.State) command;
            // Now you can inspect the Vehicle Information, for example

            // Get the VIN number
            vehicleInfo.getPower().getValue().getValue();

            // Check the power train type
            if (vehicleInfo.getPowertrain().getValue() == VehicleInformation.Powertrain.ALL_ELECTRIC) {
                // vehicle has all electric power train
            }
        }
    }

    void capabilites() {
        // tabs/resources/documentation/mobile-sdks/android/auto-api.html
        Command command = CommandResolver.resolve(bytes);

        if (command instanceof Capabilities.State) {
            Capabilities.State capabilities = (Capabilities.State) command;
            // you can now inspect which capabilities are supported, for example:

            if (capabilities.getSupported(Doors.IDENTIFIER,
                    Doors.PROPERTY_LOCKS_STATE)) {
                // Vehicle supports the doors inside locks property. You can query/set the inside
                // locks state with LockUnlockDoors.
            }

            if (capabilities.getSupported(Trunk.IDENTIFIER,
                    Trunk.PROPERTY_LOCK)) {
                // Vehicle supports the trunk position property. You can query/set the trunk
                // position with the ControlTrunk command.
            }
        }
    }

    void vehicleLocation() {
        // tabs/resources/documentation/mobile-sdks/android/auto-api.html
        Command command = CommandResolver.resolve(bytes);

        if (command instanceof VehicleLocation.State) {
            VehicleLocation.State location = (VehicleLocation.State) command;
            // vehicle location testState can now be accessed:

            // coordinates
            location.getCoordinates().getValue().getLatitude();
            location.getCoordinates().getValue().getLongitude();

            // heading
            location.getHeading().getValue();
        }
    }

    void lockStateLong() {
        // tabs/resources/documentation/mobile-sdks/android/auto-api.html

        Command command = CommandResolver.resolve(bytes);

        if (command instanceof Doors.State) {
            Doors.State state = (Doors.State) command;
            // vehicle lock state testState can now be accessed:

            // lock state for a specific door
            state.getLock(Location.FRONT_LEFT).getValue();

            // lock states for all of the doors available
            LockState left = null, right = null, rearRight = null, rearLeft = null;
            for (Property<Lock> lockState : state.getLocks()) {
                if (lockState.getValue() == null) continue;
                switch (lockState.getValue().getLocation()) {
                    case FRONT_LEFT:
                        left = lockState.getValue().getLockState();
                        break;
                    case FRONT_RIGHT:
                        right = lockState.getValue().getLockState();
                        break;
                    case REAR_RIGHT:
                        rearRight = lockState.getValue().getLockState();
                        break;
                    case REAR_LEFT:
                        rearLeft = lockState.getValue().getLockState();
                        break;
                }
            }
        }
    }

    void failure() {
        // tabs/resources/documentation/mobile-sdks/android/auto-api.html
        Command command = CommandResolver.resolve(bytes);

        if (command instanceof FailureMessage.State) {
            FailureMessage.State failure = (FailureMessage.State) command;
            if (failure.getFailedMessageID().getValue() == Identifier.VEHICLE_STATUS &&
                    failure.getFailedMessageType().getValue() == Type.GET) {
                // The Get Vehicle Status command failed.
                if (failure.getFailureReason().getValue() == FailureMessage.FailureReason.UNAUTHORISED) {
                    // The command failed because the vehicle is not authorized. Try to connect
                    // to vehicle again
                }
            }
        }
    }

    // oem

    void oem() {
        Lock frontLeftState = new Lock(
                Location.FRONT_LEFT,
                LockState.LOCKED
        );

        Lock frontRightState = new Lock(
                Location.FRONT_RIGHT,
                LockState.UNLOCKED
        );

        Doors.State hmLockState = new Doors.State.Builder()
                .addLock(new Property(frontLeftState))
                .addLock(new Property(frontRightState))
                .build();
    }

    // CommandQueuetest and README
    void bleCommandQueue() {
// readme
        new VehicleStatus.GetVehicleStatus();
        Class cls = VehicleStatus.State.class;
        // send OpenGasFlap and only wait for the ack, not the GasFlapState response.
        new Fueling.ControlGasFlap(LockState.LOCKED, Position.CLOSED);
        // send HonkAndFlash straight after the OpenGasFlap ack.
        new HonkHornFlashLights.HonkFlash(3, new Duration(3d, Duration.Unit.SECONDS));

// test
        Command command = new Doors.LockUnlockDoors(LockState.LOCKED);
        Command response = new Doors.State.Builder().addInsideLock(new Property(new Lock
                (Location.FRONT_LEFT, LockState.LOCKED))).build();

        Integer id = Doors.IDENTIFIER;
        Integer type = Type.SET;

        // error ctor

        FailureMessage.State firstResponse =
                new FailureMessage.State.Builder()
                        .setFailedMessageType(new Property(Type.SET))
                        .setFailedMessageID(new Property(Doors.IDENTIFIER))
                        .setFailedPropertyIDs(new Property(new Bytes(new byte[]{Doors.PROPERTY_LOCKS_STATE})))
                        .setFailureReason(new Property(FailureMessage.FailureReason.UNSUPPORTED_CAPABILITY)).build();

        SupportedCapability capability = new SupportedCapability(Doors.IDENTIFIER,
                new Bytes(new byte[]{Doors.PROPERTY_LOCKS_STATE}));

        Capabilities.State capas = new Capabilities.State.Builder().addCapability(new
                Property(capability)).build();
    }
}