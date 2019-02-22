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
import com.highmobility.autoapi.property.FailureReason;
import com.highmobility.autoapi.property.PowerTrain;
import com.highmobility.autoapi.property.doors.DoorLockState;
import com.highmobility.autoapi.property.value.Location;
import com.highmobility.autoapi.property.value.Lock;
import com.highmobility.value.Bytes;

public class DevCenterSnippetTest {

    // if test does not compile, a snippet needs to be updated somewhere in docs.
    // these tests are not supposed to pass as tests

    void testSnippets() {
        //
        Bytes bytes = new Bytes("");
        Command command = CommandResolver.resolve(bytes);

        if (command instanceof LockState) {
            LockState lockState = (LockState) command;
            // access the lock state object
        }

        //
        new LockUnlockDoors(Lock.UNLOCKED);

        //
        Bytes incomingBytes = new Bytes("");
        command = CommandResolver.resolve(incomingBytes);

        if (command instanceof VehicleStatus) {
            VehicleStatus vehicleStatus = (VehicleStatus) command;
            // now you can inspect the Vehicle Status testState, for example

            // get VIN number
            vehicleStatus.getVin();

            // check the power train
            if (vehicleStatus.getPowerTrain() == PowerTrain.ALLELECTRIC) {
                // vehicle has all electric power train
            }

            // check the trunk state, if exists
            Command subState = vehicleStatus.getState(TrunkState.TYPE);
            if (subState != null) {
                TrunkState trunkState = (TrunkState) subState;
                if (trunkState.getLockState() == Lock.UNLOCKED) {
                    // trunk is unlocked
                }
            }
        }

        //

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

        //

        if (command instanceof VehicleLocation) {
            VehicleLocation location = (VehicleLocation) command;
            // vehicle location testState can now be accessed:

            // coordinates
            location.getCoordinates().getLatitude();
            location.getCoordinates().getLongitude();

            // heading
            location.getHeading();
        }

        //
        command = CommandResolver.resolve(incomingBytes);

        if (command instanceof LockState) {
            LockState state = (LockState) command;
            // vehicle lock state testState can now be accessed:

            // lock state for a specific door
            state.getOutsideLock(Location.FRONT_LEFT);

            // lock states for all of the doors available
            DoorLockState left = null, right = null, rearRight = null, rearLeft = null;
            for (DoorLockState lockState : state.getOutsideLocks()) {
                switch (lockState.getLocation()) {
                    case FRONT_LEFT:
                        left = lockState;
                        break;
                    case FRONT_RIGHT:
                        right = lockState;
                        break;
                    case REAR_RIGHT:
                        rearRight = lockState;
                        break;
                    case REAR_LEFT:
                        rearLeft = lockState;
                        break;
                }
            }
        }

        if (command instanceof Failure) {
            Failure failure = (Failure) command;
            if (failure.getFailedType().equals(GetVehicleStatus.TYPE)) {
                // The Get Vehicle Status command failed.
                if (failure.getFailureReason() == FailureReason.UNAUTHORISED) {
                    // The command failed because the vehicle is not authorized. Try to connect to vehicle again
                }
            }
        }
    }
}