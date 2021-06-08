/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.value.Bytes;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Failure Message capability
 */
public class FailureMessage {
    public static final int IDENTIFIER = Identifier.FAILURE_MESSAGE;

    public static final byte PROPERTY_FAILED_MESSAGE_ID = 0x01;
    public static final byte PROPERTY_FAILED_MESSAGE_TYPE = 0x02;
    public static final byte PROPERTY_FAILURE_REASON = 0x03;
    public static final byte PROPERTY_FAILURE_DESCRIPTION = 0x04;
    public static final byte PROPERTY_FAILED_PROPERTY_IDS = 0x05;

    /**
     * The failure message state
     */
    public static class State extends SetCommand {
        PropertyInteger failedMessageID = new PropertyInteger(PROPERTY_FAILED_MESSAGE_ID, false);
        PropertyInteger failedMessageType = new PropertyInteger(PROPERTY_FAILED_MESSAGE_TYPE, false);
        Property<FailureReason> failureReason = new Property<>(FailureReason.class, PROPERTY_FAILURE_REASON);
        Property<String> failureDescription = new Property<>(String.class, PROPERTY_FAILURE_DESCRIPTION);
        Property<Bytes> failedPropertyIDs = new Property<>(Bytes.class, PROPERTY_FAILED_PROPERTY_IDS);
    
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
         * @return Whether the command failed.
         */
        public boolean getPropertyFailed(Integer capabilityIdentifier, byte propertyIdentifier) {
            if ((getFailedMessageID().getValue() != null && getFailedPropertyIDs().getValue() != null) &&
                    capabilityIdentifier.equals(getFailedMessageID().getValue())) {
                Bytes failedIds = getFailedPropertyIDs().getValue();
    
                for (Byte failedId : failedIds) {
                    if (failedId == propertyIdentifier) return true;
                }
            }
    
            return false;
        }
    
        /**
         * Understand whether a set/get command failed
         *
         * @param identifier The command identifier
         * @param type       The command type
         * @return Whether the command failed.
         */
        public boolean getCommandFailed(Integer identifier, Integer type) {
            if (identifier == getFailedMessageID().getValue() &&
                    type == getFailedMessageType().getValue()) {
                return true;
            }
    
            return false;
        }
    
        State(byte[] bytes) {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_FAILED_MESSAGE_ID: return failedMessageID.update(p);
                        case PROPERTY_FAILED_MESSAGE_TYPE: return failedMessageType.update(p);
                        case PROPERTY_FAILURE_REASON: return failureReason.update(p);
                        case PROPERTY_FAILURE_DESCRIPTION: return failureDescription.update(p);
                        case PROPERTY_FAILED_PROPERTY_IDS: return failedPropertyIDs.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        public static final class Builder extends SetCommand.Builder<Builder> {
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                SetCommand baseSetCommand = super.build();
                Command resolved = CommandResolver.resolve(baseSetCommand.getByteArray());
                return (State) resolved;
            }
    
            /**
             * @param failedMessageID Capability identifier of the failed message
             * @return The builder
             */
            public Builder setFailedMessageID(Property<Integer> failedMessageID) {
                Property property = new PropertyInteger(PROPERTY_FAILED_MESSAGE_ID, false, 2, failedMessageID);
                addProperty(property);
                return this;
            }
            
            /**
             * @param failedMessageType Message type of the failed message
             * @return The builder
             */
            public Builder setFailedMessageType(Property<Integer> failedMessageType) {
                Property property = new PropertyInteger(PROPERTY_FAILED_MESSAGE_TYPE, false, 1, failedMessageType);
                addProperty(property);
                return this;
            }
            
            /**
             * @param failureReason The failure reason
             * @return The builder
             */
            public Builder setFailureReason(Property<FailureReason> failureReason) {
                Property property = failureReason.setIdentifier(PROPERTY_FAILURE_REASON);
                addProperty(property);
                return this;
            }
            
            /**
             * @param failureDescription Failure description
             * @return The builder
             */
            public Builder setFailureDescription(Property<String> failureDescription) {
                Property property = failureDescription.setIdentifier(PROPERTY_FAILURE_DESCRIPTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param failedPropertyIDs Array of failed property identifiers
             * @return The builder
             */
            public Builder setFailedPropertyIDs(Property<Bytes> failedPropertyIDs) {
                Property property = failedPropertyIDs.setIdentifier(PROPERTY_FAILED_PROPERTY_IDS);
                addProperty(property);
                return this;
            }
        }
    }

    public enum FailureReason implements ByteEnum {
        /**
         * Vehicle has not the capability to perform the command
         */
        UNSUPPORTED_CAPABILITY((byte) 0x00),
        /**
         * User has not been authenticated or lacks permissions
         */
        UNAUTHORISED((byte) 0x01),
        /**
         * Command can not be executed in the current vehicle state
         */
        INCORRECT_STATE((byte) 0x02),
        /**
         * Command failed to execute in time for an unknown reason
         */
        EXECUTION_TIMEOUT((byte) 0x03),
        /**
         * Vehicle has to be waken up before the command can be used. If this is for a virtual vehicle, the emulator has to be loaded
         */
        VEHICLE_ASLEEP((byte) 0x04),
        /**
         * Command not recognised
         */
        INVALID_COMMAND((byte) 0x05),
        /**
         * Capability is being refreshed
         */
        PENDING((byte) 0x06),
        /**
         * Capability rate limit has been exceeded
         */
        RATE_LIMIT((byte) 0x07),
        /**
         * API call to an OEM returned an error
         */
        OEM_ERROR((byte) 0x08),
        /**
         * Privacy mode is turned on, meaning vehicle location and other "private" data is not transmitted by the vehicle.
         */
        PRIVACY_MODE_ACTIVE((byte) 0x09);
    
        public static FailureReason fromByte(byte byteValue) throws CommandParseException {
            FailureReason[] values = FailureReason.values();
    
            for (int i = 0; i < values.length; i++) {
                FailureReason state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(FailureReason.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        FailureReason(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}