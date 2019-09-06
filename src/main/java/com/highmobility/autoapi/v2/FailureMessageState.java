// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyInteger;

public class FailureMessageState extends Command {
    PropertyInteger failedMessageID = new PropertyInteger(0x01, false);
    PropertyInteger failedMessageType = new PropertyInteger(0x02, false);
    Property<FailureReason> failureReason = new Property(FailureReason.class, 0x03);
    Property<String> failureDescription = new Property(String.class, 0x04);

    /**
     * @return Capability identifier of the failed message
     */
    public PropertyInteger getFailedMessageID() {
        return failedMessageID;
    }

    /**
     * @return Message type of the failed message
     */
    public PropertyInteger getFailedMessageType() {
        return failedMessageType;
    }

    /**
     * @return The failure reason
     */
    public Property<FailureReason> getFailureReason() {
        return failureReason;
    }

    /**
     * @return Failure description bytes formatted in UTF-8
     */
    public Property<String> getFailureDescription() {
        return failureDescription;
    }

    FailureMessageState(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return failedMessageID.update(p);
                    case 0x02: return failedMessageType.update(p);
                    case 0x03: return failureReason.update(p);
                    case 0x04: return failureDescription.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private FailureMessageState(Builder builder) {
        super(builder);

        failedMessageID = builder.failedMessageID;
        failedMessageType = builder.failedMessageType;
        failureReason = builder.failureReason;
        failureDescription = builder.failureDescription;
    }

    public static final class Builder extends Command.Builder {
        private PropertyInteger failedMessageID;
        private PropertyInteger failedMessageType;
        private Property<FailureReason> failureReason;
        private Property<String> failureDescription;

        public Builder() {
            super(Identifier.FAILURE_MESSAGE);
        }

        public FailureMessageState build() {
            return new FailureMessageState(this);
        }

        /**
         * @param failedMessageID Capability identifier of the failed message
         * @return The builder
         */
        public Builder setFailedMessageID(PropertyInteger failedMessageID) {
            this.failedMessageID = new PropertyInteger(0x01, false, 2, failedMessageID);
            addProperty(failedMessageID);
            return this;
        }
        
        /**
         * @param failedMessageType Message type of the failed message
         * @return The builder
         */
        public Builder setFailedMessageType(PropertyInteger failedMessageType) {
            this.failedMessageType = new PropertyInteger(0x02, false, 1, failedMessageType);
            addProperty(failedMessageType);
            return this;
        }
        
        /**
         * @param failureReason The failure reason
         * @return The builder
         */
        public Builder setFailureReason(Property<FailureReason> failureReason) {
            this.failureReason = failureReason.setIdentifier(0x03);
            addProperty(failureReason);
            return this;
        }
        
        /**
         * @param failureDescription Failure description bytes formatted in UTF-8
         * @return The builder
         */
        public Builder setFailureDescription(Property<String> failureDescription) {
            this.failureDescription = failureDescription.setIdentifier(0x04);
            addProperty(failureDescription);
            return this;
        }
    }

    public enum FailureReason {
        UNSUPPORTED_CAPABILITY((byte)0x00),
        UNAUTHORISED((byte)0x01),
        INCORRECT_STATE((byte)0x02),
        EXECUTION_TIMEOUT((byte)0x03),
        VEHICLE_ASLEEP((byte)0x04),
        INVALID_COMMAND((byte)0x05),
        PENDING((byte)0x06),
        RATE_LIMIT((byte)0x07);
    
        public static FailureReason fromByte(byte byteValue) throws CommandParseException {
            FailureReason[] values = FailureReason.values();
    
            for (int i = 0; i < values.length; i++) {
                FailureReason state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        FailureReason(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }
}