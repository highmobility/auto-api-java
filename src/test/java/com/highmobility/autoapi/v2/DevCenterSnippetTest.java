package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.Lock;
import com.highmobility.autoapi.v2.value.LockState;
import com.highmobility.value.Bytes;

public class DevCenterSnippetTest {

    // 3 files have snippets:
    // auto api tutorial
    // android tutorial
    // android bluetooth tutorial

    // if test does not compile, a snippet needs to be updated somewhere in docs.
    // these tests are not supposed to pass as tests
    Bytes incomingBytes = null;

    void lockState() {
        // developer-center/tabs/resources/tutorials/sdk/android/android-bluetooth.html
        Command command = CommandResolver.resolve(incomingBytes);

        if (command instanceof DoorsState) {
            DoorsState lockState = (DoorsState) command;
            // access the doors state object
        }
    }

    void lockCommand() {
        // developer-center/tabs/resources/documentation/mobile-sdks/android/auto-api.html
        // developer-center/tabs/resources/tutorials/sdk/android/android-bluetooth.html
        // developer-center/tabs/resources/tutorials/sdk/android/android-tutorial.html
        Command command = new LockUnlockDoors(LockState.UNLOCKED);
    }

    void vehicleStatus() {
        // developer-center/tabs/resources/documentation/mobile-sdks/android/auto-api.html

        Command command = CommandResolver.resolve(incomingBytes);

        if (command instanceof VehicleStatusState) {
            VehicleStatusState vehicleStatus = (VehicleStatusState) command;
            // now you can inspect the Vehicle Status testState, for example

            // get the VIN number
            vehicleStatus.getVin().getValue();

            // check the power train type
            if (vehicleStatus.getPowertrain().getValue() == VehicleStatusState.Powertrain.ALL_ELECTRIC) {
                // vehicle has all electric power train
            }

            // find the trunk state
            for (Property<Command> state : vehicleStatus.getStates()) {
                if (state.getValue().getIdentifier() == Identifier.TRUNK) {
                    TrunkState trunkState = (TrunkState) state.getValue();
                    if (trunkState.getLock().getValue() == LockState.UNLOCKED) {
                        // trunk is unlocked
                    }
                }
            }
        }
    }

    void capabilites() {
        // developer-center/tabs/resources/documentation/mobile-sdks/android/auto-api.html
        Command command = CommandResolver.resolve(incomingBytes);

        if (command instanceof CapabilitiesState) {
            CapabilitiesState capabilities = (CapabilitiesState) command;
            // you can now inspect which capabilities are supported, for example:

            if (capabilities.getSupported(Identifier.DOORS, (byte) 0x05) == true) {
                // Vehicle supports the doors inside locks property. You can query/set the inside
                // locks state with LockUnlockDoors.
            }

            if (capabilities.getSupported(Identifier.TRUNK, (byte) 0x02) == true) {
                // Vehicle supports the trunk position property. You can query/set the trunk
                // position with the ControlTrunk command.
            }
        }
    }

    void vehicleLocation() {
        // developer-center/tabs/resources/documentation/mobile-sdks/android/auto-api.html
        Command command = CommandResolver.resolve(incomingBytes);

        if (command instanceof VehicleLocationState) {
            VehicleLocationState location = (VehicleLocationState) command;
            // vehicle location testState can now be accessed:

            // coordinates
            location.getCoordinates().getValue().getLatitude();
            location.getCoordinates().getValue().getLongitude();

            // heading
            location.getHeading().getValue();
        }
    }

    void lockStateLong() {
        // developer-center/tabs/resources/documentation/mobile-sdks/android/auto-api.html

        Command command = CommandResolver.resolve(incomingBytes);

        if (command instanceof DoorsState) {
            DoorsState state = (DoorsState) command;
            // vehicle lock state testState can now be accessed:

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
        // developer-center/tabs/resources/documentation/mobile-sdks/android/auto-api.html
        Command command = CommandResolver.resolve(incomingBytes);

        if (command instanceof FailureMessageState) {
            FailureMessageState failure = (FailureMessageState) command;
            if (failure.getFailedMessageID().getValue() == Identifier.VEHICLE_STATUS.asInt() &&
                    failure.getFailedMessageType().getValue() == Type.GET.asInt()) {
                // The Get Vehicle Status command failed.
                if (failure.getFailureReason().getValue() == FailureMessageState.FailureReason.UNAUTHORISED) {
                    // The command failed because the vehicle is not authorized. Try to connect
                    // to vehicle again
                }
            }
        }
    }
}