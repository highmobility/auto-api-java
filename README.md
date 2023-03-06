# HMKit Auto API

This repository contains the java parsers for Auto API.

### Dependencies

* hmkit-utils


### Install

Releases are pushed to mavenCentral. To include hmkit-auto-api in your project, add to build.gradle:

```
repositories {
  mavenCentral()
}

dependencies {
  implementation('com.highmobility:hmkit-auto-api:{version}')
}
```

SLF4J is used for logging. Add slf4j binding to see the logs, for example:

```
implementation 'org.slf4j:slf4j-simple:1.8.0-beta1'
```

Find the latest version name in [mavenCentral](https://search.maven.org/search?q=g:com.high-mobility)

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
Bytes commandBytes = new LockUnlockDoors(Lock.LOCKED).getBytes();
sendCommand(commandBytes)
```

### Get a capability
```java
Bytes commandBytes = new GetCapability(SendHeartRate.TYPE).getBytes();
sendCommand(commandBytes)
```

### Check for the failed command's type
```java
Failure failure;
if (command instanceof Failure) {
    failure = (Failure)command;
    if (failure.getFailedType != null && failure.getFailedType.equals(LockUnlockDoors.TYPE) {
        // the lock unlock command failed
    }
}
```

### Builders for bigger commands(states)

Builder pattern is used to build commands with more properties, for example Vehicle status:

```java
// create the builder
VehicleStatus.Builder builder = new VehicleStatus.Builder();
// add known properties as simple values 
builder.setVin(new Property("JF2SHBDC7CH451869"));
builder.setPowerTrain(new Property(PowerTrain.ALLELECTRIC));;
builder.setModelYear(new Property(2017));
//        builder.setPower(220);
// can also add unknown properties
builder.addProperty(new Property(220));
// can chain the properties adding
builder.setNumberOfDoors(new Property(5)).setNumberOfSeats(new Property(5));

// use builders from other commands to append them to vehicle status
TrunkState.Builder trunkState = new TrunkState.Builder();
trunkState.setLockState(new Property(Lock.UNLOCKED));
trunkState.setPosition(new Property(Position.OPEN));
builder.addProperty(new Property(trunkState.build()));

ControlMode.Builder controlCommand = new ControlMode.Builder();
controlCommand.setMode(new Property(ControlModeValue.STARTED));
builder.addProperty(new Property(controlCommand.build()));

// build the actual vehicleStatus command
VehicleStatus status = builder.build();
// get the raw bytes of the vehicle status
byte[] command = status.getByteArray();
```

A signature and a nonce can be added to any of the command's builders:

```java
VehicleStatus.Builder builder = getVehicleStatusBuilderWithoutSignature();
// set the nonce
builder.setNonce(new Bytes("324244433743483436"));
// get the temporary data that needs to be signed
Bytes bytesToBeSigned = builder.build().getSignedBytes();
// sign it
Bytes sig = Crypto.sign(bytesToBeSigned, privateKey);
// add the signature property
builder.setSignature(sig);
// get the final bytes with signature
Bytes command = builder.build();
```

### Adding a new Capability

* Add the Identifier in Identifier.java
* Follow, for instance, Fueling.java to add the capability-s skeleton commands.   
* Create the tests. Copy this from previous FuelingTest.
* Implement the new Capability commands.


## OEM

Some commands are not available if using AutoAPI on the vehicle (OEM) side. For them to work, the
environment needs to be set to `VEHICLE` in `CommandResolver`:

```java
CommandResolver.setEnvironment(CommandResolver.Environment.VEHICLE);
```