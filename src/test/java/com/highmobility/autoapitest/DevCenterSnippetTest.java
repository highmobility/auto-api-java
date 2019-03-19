package com.highmobility.autoapitest;

import com.highmobility.autoapi.Capabilities;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.Failure;
import com.highmobility.autoapi.GetLockState;
import com.highmobility.autoapi.GetVehicleLocation;
import com.highmobility.autoapi.GetVehicleStatus;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.autoapi.VehicleLocation;
import com.highmobility.autoapi.VehicleStatus;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.FailureReason;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.autoapi.value.PowerTrain;
import com.highmobility.autoapi.value.doors.DoorLockState;
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

        if (command instanceof LockState) {
            LockState lockState = (LockState) command;
            // access the lock state object
        }
    }

    void lockCommand() {
        // developer-center/tabs/resources/documentation/mobile-sdks/android/auto-api.html
        // developer-center/tabs/resources/tutorials/sdk/android/android-bluetooth.html
        // developer-center/tabs/resources/tutorials/sdk/android/android-tutorial.html
        Command command = new LockUnlockDoors(Lock.UNLOCKED);
    }

    void vehicleStatus() {
        // developer-center/tabs/resources/documentation/mobile-sdks/android/auto-api.html

        Command command = CommandResolver.resolve(incomingBytes);

        if (command instanceof VehicleStatus) {
            VehicleStatus vehicleStatus = (VehicleStatus) command;
            // now you can inspect the Vehicle Status testState, for example

            // get the VIN number
            vehicleStatus.getVin().getValue();

            // check the power train type
            if (vehicleStatus.getPowerTrain().getValue() == PowerTrain.ALLELECTRIC) {
                // vehicle has all electric power train
            }

            // check the trunk state, if exists
            Command subState = vehicleStatus.getState(TrunkState.TYPE).getValue();
            if (subState != null) {
                TrunkState trunkState = (TrunkState) subState;
                if (trunkState.getLockState().getValue() == Lock.UNLOCKED) {
                    // trunk is unlocked
                }
            }
        }
    }

    void capabilites() {
        // developer-center/tabs/resources/documentation/mobile-sdks/android/auto-api.html
        Command command = CommandResolver.resolve(incomingBytes);

        if (command instanceof Capabilities) {
            Capabilities capabilities = (Capabilities) command;
            // you can now inspect which capabilities are supported, for example:
            if (capabilities.isSupported(GetLockState.TYPE) == true) {
                // Vehicle supports queries for its lock state
                // You can now be sure that it responds to the GetLockState command
            }

            if (capabilities.isSupported(GetVehicleLocation.TYPE) == false) {
                // Vehicle does not support queries for its location
            }
        }
    }

    void vehicleLocation() {
        // developer-center/tabs/resources/documentation/mobile-sdks/android/auto-api.html
        Command command = CommandResolver.resolve(incomingBytes);

        if (command instanceof VehicleLocation) {
            VehicleLocation location = (VehicleLocation) command;
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

        if (command instanceof LockState) {
            LockState state = (LockState) command;
            // vehicle lock state testState can now be accessed:

            // lock state for a specific door
            state.getOutsideLock(Location.FRONT_LEFT).getValue();

            // lock states for all of the doors available
            DoorLockState left = null, right = null, rearRight = null, rearLeft = null;
            for (Property<DoorLockState> lockState : state.getOutsideLocks()) {
                if (lockState.getValue() == null) continue;
                switch (lockState.getValue().getLocation()) {
                    case FRONT_LEFT:
                        left = lockState.getValue();
                        break;
                    case FRONT_RIGHT:
                        right = lockState.getValue();
                        break;
                    case REAR_RIGHT:
                        rearRight = lockState.getValue();
                        break;
                    case REAR_LEFT:
                        rearLeft = lockState.getValue();
                        break;
                }
            }
        }
    }

    void failure() {
        // developer-center/tabs/resources/documentation/mobile-sdks/android/auto-api.html
        Command command = CommandResolver.resolve(incomingBytes);

        if (command instanceof Failure) {
            Failure failure = (Failure) command;
            if (failure.getFailedType() != null && failure.getFailedType().equals(GetVehicleStatus.TYPE)) {
                // The Get Vehicle Status command failed.
                if (failure.getFailureReason().getValue() != null &&
                        failure.getFailureReason().getValue() == FailureReason.UNAUTHORISED) {
                    // The command failed because the vehicle is not authorized. Try to connect
                    // to vehicle again
                }
            }
        }
    }
}