package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * For different reasons a command sent to the car might not be successful. When this happens, a
 * failure message is sent to the smart device with details about what went wrong.
 */
public class Failure extends Command {
    public static final Type TYPE = new Type(Identifier.FAILURE, 0x01);

    public enum Reason {
        UNSUPPORTED_CAPABILITY((byte) 0x00),
        UNAUTHORIZED((byte) 0x01),
        INCORRECT_STATE((byte) 0x02),
        EXECUTION_TIMEOUT((byte) 0x03),
        VEHICLE_ASLEEP((byte) 0x04),
        INVALID_COMMAND((byte) 0x05);

        public byte getByte() {
            return reasonByte;
        }

        public static Reason fromByte(byte reasonByte) throws CommandParseException {
            Reason[] allValues = Reason.values();

            for (int i = 0; i < allValues.length; i++) {
                Reason reason = allValues[i];

                if (reason.getByte() == reasonByte) {
                    return reason;
                }
            }

            throw new CommandParseException();
        }

        Reason(byte reason) {
            this.reasonByte = reason;
        }

        private byte reasonByte;
    }

    private Type failedType;
    private Reason failureReason;

    /**
     * @return The type of the command that failed. Do not mistake this for this(Failure) command's
     * identifier {@link #getType }
     */
    public Type getFailedType() {
        return failedType;
    }

    /**
     * @return The failure reason
     */
    public Reason getFailureReason() {
        return failureReason;
    }

    public Failure(byte[] bytes) throws CommandParseException {
        super(bytes);
        
        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    // failed message
                    byte[] value = property.getValueBytes();
                    failedType = new Type(value[0], value[1], value[2]);
                    break;
                case 0x02:
                    // failure reason
                    failureReason = Reason.fromByte(property.getValueByte());
                    break;
            }
        }
    }
}