/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.value.Bytes;

/**
 * The failure message state
 */
public class FailureMessageState extends SetCommand {
    public static final Identifier identifier = Identifier.FAILURE_MESSAGE;

    PropertyInteger failedMessageID = new PropertyInteger(0x01, false);
    PropertyInteger failedMessageType = new PropertyInteger(0x02, false);
    Property<FailureReason> failureReason = new Property(FailureReason.class, 0x03);
    Property<String> failureDescription = new Property(String.class, 0x04);
    Property<Bytes> failedPropertyIDs = new Property(Bytes.class, 0x05);

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
     * @return Failure description
     */
    public Property<String> getFailureDescription() {
        return failureDescription;
    }

    /**
     * @return Array of failed property identifiers
     */
    public Property<Bytes> getFailedPropertyIDs() {
        return failedPropertyIDs;
    }

    /**
     * Understand whether a specific message failed
     *
     * @param capabilityIdentifier The command capability identifier
     * @param propertyIdentifier   The property identifier
     * @return Boolean indicating whether the queried message failed.
     */
    public boolean getPropertyFailed(Identifier capabilityIdentifier, byte propertyIdentifier) {
        if ((getFailedMessageID().getValue() != null && getFailedPropertyIDs().getValue() != null) &&
                capabilityIdentifier.equals(getFailedMessageID().getValue())) {
            Bytes failedIds = getFailedPropertyIDs().getValue();

            for (Byte failedId : failedIds) {
                if (failedId == propertyIdentifier) return true;
            }
        }
        
        return false;
    }

    FailureMessageState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return failedMessageID.update(p);
                    case 0x02: return failedMessageType.update(p);
                    case 0x03: return failureReason.update(p);
                    case 0x04: return failureDescription.update(p);
                    case 0x05: return failedPropertyIDs.update(p);
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
        failedPropertyIDs = builder.failedPropertyIDs;
    }

    public static final class Builder extends SetCommand.Builder {
        private PropertyInteger failedMessageID;
        private PropertyInteger failedMessageType;
        private Property<FailureReason> failureReason;
        private Property<String> failureDescription;
        private Property<Bytes> failedPropertyIDs;

        public Builder() {
            super(identifier);
        }

        public FailureMessageState build() {
            return new FailureMessageState(this);
        }

        /**
         * @param failedMessageID Capability identifier of the failed message
         * @return The builder
         */
        public Builder setFailedMessageID(Property<Integer> failedMessageID) {
            this.failedMessageID = new PropertyInteger(0x01, false, 2, failedMessageID);
            addProperty(this.failedMessageID);
            return this;
        }
        
        /**
         * @param failedMessageType Message type of the failed message
         * @return The builder
         */
        public Builder setFailedMessageType(Property<Integer> failedMessageType) {
            this.failedMessageType = new PropertyInteger(0x02, false, 1, failedMessageType);
            addProperty(this.failedMessageType);
            return this;
        }
        
        /**
         * @param failureReason The failure reason
         * @return The builder
         */
        public Builder setFailureReason(Property<FailureReason> failureReason) {
            this.failureReason = failureReason.setIdentifier(0x03);
            addProperty(this.failureReason);
            return this;
        }
        
        /**
         * @param failureDescription Failure description
         * @return The builder
         */
        public Builder setFailureDescription(Property<String> failureDescription) {
            this.failureDescription = failureDescription.setIdentifier(0x04);
            addProperty(this.failureDescription);
            return this;
        }
        
        /**
         * @param failedPropertyIDs Array of failed property identifiers
         * @return The builder
         */
        public Builder setFailedPropertyIDs(Property<Bytes> failedPropertyIDs) {
            this.failedPropertyIDs = failedPropertyIDs.setIdentifier(0x05);
            addProperty(this.failedPropertyIDs);
            return this;
        }
    }

    public enum FailureReason implements ByteEnum {
        UNSUPPORTED_CAPABILITY((byte) 0x00),
        UNAUTHORISED((byte) 0x01),
        INCORRECT_STATE((byte) 0x02),
        EXECUTION_TIMEOUT((byte) 0x03),
        VEHICLE_ASLEEP((byte) 0x04),
        INVALID_COMMAND((byte) 0x05),
        PENDING((byte) 0x06),
        RATE_LIMIT((byte) 0x07);
    
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
    
        @Override public byte getByte() {
            return value;
        }
    }
}