# What is this repository for? #

This repository is for building the jar that has the parsers for Auto API.

# How to use create/parse commands #

The main classes are IncomingCommand and Command.

## Command ##
Command class is used to create commands that are sent from Mobile Device to vehicle. 

For example to create a lock doors command.

```java
byte[] command = Command.DoorLocks.lockDoors(true);
```

## IncomingCommand ##

IncomingCommand subclasses are used to parse commands coming from the vehicle in a Mobile Device.
They are also used to create these commands in the cloud.

for example to parse an incoming command on a Mobile device:

```java
try {
    IncomingCommand command = IncomingCommand.create(bytes);

    if (command.is(Command.VehicleStatus.VEHICLE_STATUS)) {
        view.onVehicleStatusUpdate(vehicleStatus);
    }
} catch (CommandParseException e) {
    e.printStackTrace();
}
```

To create an Incoming Command, call the static method `getCommandBytes()` in that class. For example to create Capabilities command:

```java
public static byte[] getCommandBytes(FeatureCapability[] capabilities)
```

# Dependencies #

* hmkit-utils.jar