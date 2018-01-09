package com.highmobility.autoapi;

public class DoorLockState {
    /**
     * The door location
     */
    public enum Location {
        FRONT_LEFT((byte)0x00),
        FRONT_RIGHT((byte)0x01),
        REAR_RIGHT((byte)0x02),
        REAR_LEFT((byte)0x03);

        public static Location fromByte(byte value) throws CommandParseException {
            Location[] allValues = Location.values();

            for (int i = 0; i < allValues.length; i++) {
                Location value1 = allValues[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Location(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }

    /**
     * The possible positions of a car door
     */
    public enum Position {
        CLOSED((byte)0x00),
        OPEN((byte)0x01);

        public static Position fromByte(byte value) throws CommandParseException {
            Position[] capabilities = Position.values();

            for (int i = 0; i < capabilities.length; i++) {
                Position capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        Position(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    /**
     * The possible states of the car lock.
     */
    public enum LockState {
        UNLOCKED((byte)0x00),
        LOCKED((byte)0x01);

        public static LockState fromByte(byte value) throws CommandParseException {
            LockState[] capabilities = LockState.values();

            for (int i = 0; i < capabilities.length; i++) {
                LockState capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        LockState(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    Location location;
    Position position;
    LockState lockState;

    /**
     *
     * @return The door location
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @return The door position
     */
    public Position getPosition() {
        return position;
    }

    /**
     *
     * @return The lock state
     */
    public LockState getLockState() {
        return lockState;
    }

    public DoorLockState(byte location, byte position, byte lockState) throws CommandParseException {
        this.location = Location.fromByte(location);
        this.position = Position.fromByte(position);
        this.lockState = LockState.fromByte(lockState);
    }

    public DoorLockState(Location location, Position position, LockState lockState) {
        this.location = location;
        this.position = position;
        this.lockState = lockState;
    }

    DoorLockState(byte[] bytes, int startingFrom) throws CommandParseException {
        this(bytes[startingFrom], bytes[startingFrom + 1], bytes[startingFrom + 2]);
    }
}