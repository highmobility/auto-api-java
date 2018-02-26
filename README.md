# HMKit Auto API

This repository contains the java parsers for Auto API.

### Dependencies

* hmkit-utils


### Install

Releases are pushed to jcenter. To include hmkit-auto-api in your project, add to build.gradle:

```
repositories {
  jcenter()
}

dependencies {
  implementation('com.highmobility:hmkit-auto-api:1.6.0')
}
```

SLF4J is used for logging. Add slf4j binding to see the logs, for example:

```
implementation 'org.slf4j:slf4j-simple:1.8.0-beta1'
```

Find the latest version name in https://bintray.com/high-mobility/maven/hmkit-auto-api

## How to create/parse commands

Find the command name in auto api doc, then locate a class in com.highmobility.autoapi package with
the same name. Every command has a designated class and it is used for all of the common use cases:

### Parse the received command's bytes
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

### Get a specific state from the vehicle status
```java
LockState state = vehicleStatus.getState(LockState.TYPE);
if (state != null) {
    ...
}
```

### Inspect whether the capability is supported for the vehicle
```java
if (capabilities.isSupported(LockState.TYPE)) {
    ...
}
```

### Send a command
```java
byte[] commandBytes = new LockUnlockDoors(DoorLockProperty.LockState.LOCKED).getBytes();
sendCommand(commandBytes)
```

### Get a capability
```java
byte[] commandBytes = new GetCapability(SendHeartRate.TYPE).getBytes();
sendCommand(commandBytes)
```

### Check for the failed command's type
```java
Failure failure;
if (command instanceof Failure) {
    failure = (Failure)command;
    if (failure.getFailedType.equals(LockUnlockDoors.TYPE) {
        // the lock unlock command failed
    }
}
```

### Builders for bigger commands(states)

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
builder.setSignature(sig);
// get the final bytes with signature
byte[] command = builder.build().getBytes();
```

Currently supported commands with the builder pattern:

* VehicleStatus
* DoorLocks
* Diagnostics
* Failure
* Capabilities
* TrunkState
* RemoteControl
* LightsState
* WindowsState
* ParkingBrakeState
* ChargeState
* RooftopState