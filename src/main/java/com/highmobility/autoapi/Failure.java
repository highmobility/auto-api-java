package com.highmobility.autoapi;

import com.highmobility.autoapi.property.FailureReason;
import com.highmobility.autoapi.property.Property;

/**
 * For different reasons a command sent to the car might not be successful. When this happens, a
 * failure message is sent to the smart device with details about what went wrong.
 */
public class Failure extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.FAILURE, 0x01);

    private Type failedType;
    private FailureReason failureReason;

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
    public FailureReason getFailureReason() {
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
                    failureReason = FailureReason.fromByte(property.getValueByte());
                    break;
            }
        }
    }

    private Failure(Builder builder) {
        super(TYPE, builder.getProperties());
        failureReason = builder.failureReason;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private FailureReason failureReason;
        private Type failedType;

        public Builder() {
            super(TYPE);
        }

        public Builder setFailedType(Type type) {
            this.failedType = type;
            addProperty(new Property((byte) 0x01, type.getIdentifierAndType()));
            return this;
        }

        public Builder setFailureReason(FailureReason failureReason) {
            this.failureReason = failureReason;
            addProperty(failureReason);
            return this;
        }


        public Failure build() {
            return new Failure(this);
        }
    }
}