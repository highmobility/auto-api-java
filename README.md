# What is this repository for? #

This repository is for building the jar that has the parsers for Auto API.

# How to use create/parse commands #

Find the command name in auto api doc, then locate a class in com.highmobility.autoapi package with
the same name. Every command has a designated class and it is used for all of the common use cases:

1. Parse the received command's bytes
```java
byte[] bytes = ...
Command command = CommandResolver.resolve(bytes);

VehicleStatus vehicleStatus;
Capabilities capabilities;

if (command instanceof VehicleStatus) {
    vehicleStatus = (VehicleStatus) command;
}
else if (command instanceof Capabilities) {
    capabilities = (Capabilities) command;
}
```

2. Get a specific state from the vehicle status
```java
LockState state = vehicleStatus.getState(LockState.TYPE);
if (state != null) {

}
```

3. Inspect whether the capability is supported for the vehicle
```java
if (capabilities.isSupported(LockState.TYPE)) {
    ...
}
```

4. send a command
```java
byte[] commandBytes = new LockUnlockDoors(DoorLockProperty.LockState.LOCKED).getBytes();
sendCommand(commandBytes)
```

5. get a capability
```java
byte[] commandBytes = new GetCapability(SendHeartRate.TYPE).getBytes();
sendCommand(commandBytes)
```

6. check for the failed command's type
```java
Failure failure;
if (failure.getFailedType.equals(LockUnlockDoors.TYPE) {

}
```

Builder pattern is used to build commands with more properties, for instance Vehicle status:

```java
// create the builder
VehicleStatus.Builder builder = new VehicleStatus.Builder();
// add known properties as simple values 
builder.setVin("JF2SHBDC7CH451869");
builder.setPowerTrain(PowerTrain.ALLELECTRIC);
builder.setModelName("Type X");
builder.setName("My Car");
builder.setLicensePlate("ABC123");
builder.setSalesDesignation("Package+");
builder.setModelYear(2017);
builder.setColor("Estoril Blau");
//        builder.setPower(220);
// can also add unknown properties
builder.addProperty(new IntProperty((byte) 0x09, 220, 2));
// can chain the properties adding
builder.setNumberOfDoors(5).setNumberOfSeats(5);

// use builders from other commands to append them to vehicle status
TrunkState.Builder trunkState = new TrunkState.Builder();
trunkState.setLockState(TrunkState.LockState.UNLOCKED);
trunkState.setPosition(TrunkState.Position.OPEN);
builder.addProperty(new CommandProperty(trunkState.build()));

ControlMode.Builder controlCommand = new ControlMode.Builder();
controlCommand.setMode(ControlMode.Mode.STARTED);
builder.addProperty(new CommandProperty(controlCommand.build()));

// build the actual vehicleStatus command
VehicleStatus status = builder.build();
// get the bytes that can now be forwarded to the device
byte[] command = status.getBytes();
```

A signature and a nonce can be added to any of the command's builders:

```java
VehicleStatus.Builder builder = getVehicleStatusBuilderWithoutSignature();
// set the nonce
builder.setNonce(Bytes.bytesFromHex("324244433743483436"));
// get the temporary data that needs to be signed
byte[] bytesToBeSigned = builder.build().getSignedBytes();
// sign it
byte[] sig = Crypto.sign(bytesToBeSigned, privateKey);
// add the signature property
byte[] command = builder.build().getBytes();
```

Currently supported commands with the builder pattern:
- [x] vehicleStatus
- [x] doorLocks
- [x] diagnostics
- [x] failure
- [x] capabilities
- [x] trunk state
- [x] remote control

# Dependencies #

* hmkit-utils.jar

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

