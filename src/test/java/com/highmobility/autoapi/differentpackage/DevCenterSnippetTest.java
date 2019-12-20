package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.SupportedCapability;
import com.highmobility.value.Bytes;

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
        new GetVehicleStatus();
        new GetCapabilities();
        new GetVehicleLocation();
        new LockUnlockDoors(LockState.UNLOCKED);
    }

    void vehicleStatus() {
        // tabs/resources/documentation/mobile-sdks/android/auto-api.html

        Command command = CommandResolver.resolve(bytes);

        if (command instanceof VehicleStatusState) {
            VehicleStatusState vehicleStatus = (VehicleStatusState) command;
            // Now you can inspect the Vehicle Status testState, for example

            // Get the VIN number
            vehicleStatus.getVin().getValue();

            // Check the power train type
            if (vehicleStatus.getPowertrain().getValue() == VehicleStatusState.Powertrain.ALL_ELECTRIC) {
                // vehicle has all electric power train
            }

            // Check the trunk state, if exists
            Command subState = vehicleStatus.getState(Identifier.TRUNK).getValue();
            if (subState != null) {
                TrunkState trunkState = (TrunkState) subState;
                if (trunkState.getLock().getValue() == LockState.UNLOCKED) {
                    // Trunk is unlocked
                }
            }
        }
    }

    void capabilites() {
        // tabs/resources/documentation/mobile-sdks/android/auto-api.html
        Command command = CommandResolver.resolve(bytes);

        if (command instanceof CapabilitiesState) {
            CapabilitiesState capabilities = (CapabilitiesState) command;
            // you can now inspect which capabilities are supported, for example:

            if (capabilities.getSupported(LockUnlockDoors.IDENTIFIER,
                    LockUnlockDoors.IDENTIFIER_INSIDE_LOCKS_STATE)) {
                // Vehicle supports the doors inside locks property. You can query/set the inside
                // locks state with LockUnlockDoors.
            }

            if (capabilities.getSupported(ControlTrunk.IDENTIFIER,
                    ControlTrunk.IDENTIFIER_LOCK)) {
                // Vehicle supports the trunk position property. You can query/set the trunk
                // position with the ControlTrunk command.
            }
        }
    }

    void vehicleLocation() {
        // tabs/resources/documentation/mobile-sdks/android/auto-api.html
        Command command = CommandResolver.resolve(bytes);

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
        // tabs/resources/documentation/mobile-sdks/android/auto-api.html

        Command command = CommandResolver.resolve(bytes);

        if (command instanceof DoorsState) {
            DoorsState state = (DoorsState) command;
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

        if (command instanceof FailureMessageState) {
            FailureMessageState failure = (FailureMessageState) command;

            if (failure.getFailedMessageID().getValue() == Identifier.VEHICLE_STATUS &&
                    failure.getFailedMessageType().getValue() == Type.GET) {
                // The Get Vehicle Status command failed.
                if (failure.getFailureReason().getValue() == FailureMessageState.FailureReason.UNAUTHORISED) {
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

        DoorsState hmLockState = new DoorsState.Builder()
                .addLock(new Property(frontLeftState))
                .addLock(new Property(frontRightState))
                .build();
    }

    // BleCommandQueue in sandBoxUi

    void bleCommandQueue() {
        Command command = new LockUnlockDoors(LockState.LOCKED);
        Command response = new DoorsState.Builder().addInsideLock(new Property(new Lock
                (Location.FRONT_LEFT, LockState.LOCKED))).build();

        Integer id = DoorsState.IDENTIFIER;
        Integer type = Type.SET;

        // error ctor

        FailureMessageState firstResponse =
                new FailureMessageState.Builder()
                        .setFailedMessageType(new Property(Type.SET))
                        .setFailedMessageID(new Property(LockUnlockDoors.IDENTIFIER))
                        .setFailedPropertyIDs(new Property(new Bytes(new byte[]{LockUnlockDoors.IDENTIFIER_INSIDE_LOCKS_STATE})))
                        .setFailureReason(new Property(FailureMessageState.FailureReason.UNSUPPORTED_CAPABILITY)).build();

        SupportedCapability capability = new SupportedCapability(LockUnlockDoors.IDENTIFIER,
                new Bytes(new byte[]{LockUnlockDoors.IDENTIFIER_INSIDE_LOCKS_STATE}));

        CapabilitiesState capas = new CapabilitiesState.Builder().addCapability(new
                Property(capability)).build();
    }
}