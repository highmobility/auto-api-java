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
import com.highmobility.autoapi.value.ActiveSelectedState;
import com.highmobility.value.Bytes;
import java.util.Calendar;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Theft Alarm capability
 */
public class TheftAlarm {
    public static final int IDENTIFIER = Identifier.THEFT_ALARM;

    public static final byte PROPERTY_STATUS = 0x01;
    public static final byte PROPERTY_INTERIOR_PROTECTION_STATUS = 0x02;
    public static final byte PROPERTY_TOW_PROTECTION_STATUS = 0x03;
    public static final byte PROPERTY_LAST_WARNING_REASON = 0x04;
    public static final byte PROPERTY_LAST_EVENT = 0x05;
    public static final byte PROPERTY_LAST_EVENT_LEVEL = 0x06;
    public static final byte PROPERTY_EVENT_TYPE = 0x07;

    /**
     * Get Theft Alarm property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Theft Alarm property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Theft Alarm property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Theft Alarm property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get Theft Alarm properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Theft Alarm properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Theft Alarm properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Theft Alarm properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Theft Alarm properties
     * 
     * @deprecated use {@link GetState#GetState(byte...)} instead
     */
    @Deprecated
    public static class GetProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Set theft alarm
     */
    public static class SetTheftAlarm extends SetCommand {
        Property<Status> status = new Property<>(Status.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<Status> getStatus() {
            return status;
        }
        
        /**
         * Set theft alarm
         * 
         * @param status The status
         */
        public SetTheftAlarm(Status status) {
            super(IDENTIFIER);
        
            addProperty(this.status.update(status));
            createBytes();
        }
    
        SetTheftAlarm(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_STATUS) return status.update(p);
                    
                    return null;
                });
            }
            if (this.status.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The theft alarm state
     */
    public static class State extends SetCommand {
        Property<Status> status = new Property<>(Status.class, PROPERTY_STATUS);
        Property<ActiveSelectedState> interiorProtectionStatus = new Property<>(ActiveSelectedState.class, PROPERTY_INTERIOR_PROTECTION_STATUS);
        Property<ActiveSelectedState> towProtectionStatus = new Property<>(ActiveSelectedState.class, PROPERTY_TOW_PROTECTION_STATUS);
        Property<LastWarningReason> lastWarningReason = new Property<>(LastWarningReason.class, PROPERTY_LAST_WARNING_REASON);
        Property<Calendar> lastEvent = new Property<>(Calendar.class, PROPERTY_LAST_EVENT);
        Property<LastEventLevel> lastEventLevel = new Property<>(LastEventLevel.class, PROPERTY_LAST_EVENT_LEVEL);
        Property<EventType> eventType = new Property<>(EventType.class, PROPERTY_EVENT_TYPE);
    
        /**
         * @return The status
         */
        public Property<Status> getStatus() {
            return status;
        }
    
        /**
         * @return Interior protection sensor status
         */
        public Property<ActiveSelectedState> getInteriorProtectionStatus() {
            return interiorProtectionStatus;
        }
    
        /**
         * @return Tow protection sensor status
         */
        public Property<ActiveSelectedState> getTowProtectionStatus() {
            return towProtectionStatus;
        }
    
        /**
         * @return The last warning reason
         */
        public Property<LastWarningReason> getLastWarningReason() {
            return lastWarningReason;
        }
    
        /**
         * @return Last event happening date
         */
        public Property<Calendar> getLastEvent() {
            return lastEvent;
        }
    
        /**
         * @return Level of impact for the last event
         */
        public Property<LastEventLevel> getLastEventLevel() {
            return lastEventLevel;
        }
    
        /**
         * @return Position of the last even relative to the vehicle
         */
        public Property<EventType> getEventType() {
            return eventType;
        }
    
        State(byte[] bytes) {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_INTERIOR_PROTECTION_STATUS: return interiorProtectionStatus.update(p);
                        case PROPERTY_TOW_PROTECTION_STATUS: return towProtectionStatus.update(p);
                        case PROPERTY_LAST_WARNING_REASON: return lastWarningReason.update(p);
                        case PROPERTY_LAST_EVENT: return lastEvent.update(p);
                        case PROPERTY_LAST_EVENT_LEVEL: return lastEventLevel.update(p);
                        case PROPERTY_EVENT_TYPE: return eventType.update(p);
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
             * @param status The status
             * @return The builder
             */
            public Builder setStatus(Property<Status> status) {
                Property property = status.setIdentifier(PROPERTY_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param interiorProtectionStatus Interior protection sensor status
             * @return The builder
             */
            public Builder setInteriorProtectionStatus(Property<ActiveSelectedState> interiorProtectionStatus) {
                Property property = interiorProtectionStatus.setIdentifier(PROPERTY_INTERIOR_PROTECTION_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param towProtectionStatus Tow protection sensor status
             * @return The builder
             */
            public Builder setTowProtectionStatus(Property<ActiveSelectedState> towProtectionStatus) {
                Property property = towProtectionStatus.setIdentifier(PROPERTY_TOW_PROTECTION_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param lastWarningReason The last warning reason
             * @return The builder
             */
            public Builder setLastWarningReason(Property<LastWarningReason> lastWarningReason) {
                Property property = lastWarningReason.setIdentifier(PROPERTY_LAST_WARNING_REASON);
                addProperty(property);
                return this;
            }
            
            /**
             * @param lastEvent Last event happening date
             * @return The builder
             */
            public Builder setLastEvent(Property<Calendar> lastEvent) {
                Property property = lastEvent.setIdentifier(PROPERTY_LAST_EVENT);
                addProperty(property);
                return this;
            }
            
            /**
             * @param lastEventLevel Level of impact for the last event
             * @return The builder
             */
            public Builder setLastEventLevel(Property<LastEventLevel> lastEventLevel) {
                Property property = lastEventLevel.setIdentifier(PROPERTY_LAST_EVENT_LEVEL);
                addProperty(property);
                return this;
            }
            
            /**
             * @param eventType Position of the last even relative to the vehicle
             * @return The builder
             */
            public Builder setEventType(Property<EventType> eventType) {
                Property property = eventType.setIdentifier(PROPERTY_EVENT_TYPE);
                addProperty(property);
                return this;
            }
        }
    }

    public enum Status implements ByteEnum {
        UNARMED((byte) 0x00),
        ARMED((byte) 0x01),
        TRIGGERED((byte) 0x02);
    
        public static Status fromByte(byte byteValue) throws CommandParseException {
            Status[] values = Status.values();
    
            for (int i = 0; i < values.length; i++) {
                Status state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Status.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Status(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum LastWarningReason implements ByteEnum {
        NO_ALARM((byte) 0x00),
        BASIS_ALARM((byte) 0x01),
        DOOR_FRONT_LEFT((byte) 0x02),
        DOOR_FRONT_RIGHT((byte) 0x03),
        DOOR_REAR_LEFT((byte) 0x04),
        DOOR_REAR_RIGHT((byte) 0x05),
        HOOD((byte) 0x06),
        TRUNK((byte) 0x07),
        COMMON_ALM_IN((byte) 0x08),
        PANIC((byte) 0x09),
        GLOVEBOX((byte) 0x0a),
        CENTER_BOX((byte) 0x0b),
        REAR_BOX((byte) 0x0c),
        SENSOR_VTA((byte) 0x0d),
        ITS((byte) 0x0e),
        ITS_SLV((byte) 0x0f),
        TPS((byte) 0x10),
        HORN((byte) 0x11),
        HOLD_COM((byte) 0x12),
        REMOTE((byte) 0x13),
        UNKNOWN((byte) 0x14);
    
        public static LastWarningReason fromByte(byte byteValue) throws CommandParseException {
            LastWarningReason[] values = LastWarningReason.values();
    
            for (int i = 0; i < values.length; i++) {
                LastWarningReason state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(LastWarningReason.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        LastWarningReason(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum LastEventLevel implements ByteEnum {
        LOW((byte) 0x00),
        MEDIUM((byte) 0x01),
        HIGH((byte) 0x02);
    
        public static LastEventLevel fromByte(byte byteValue) throws CommandParseException {
            LastEventLevel[] values = LastEventLevel.values();
    
            for (int i = 0; i < values.length; i++) {
                LastEventLevel state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(LastEventLevel.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        LastEventLevel(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum EventType implements ByteEnum {
        IDLE((byte) 0x00),
        FRONT_LEFT((byte) 0x01),
        FRONT_MIDDLE((byte) 0x02),
        FRONT_RIGHT((byte) 0x03),
        RIGHT((byte) 0x04),
        REAR_RIGHT((byte) 0x05),
        REAR_MIDDLE((byte) 0x06),
        REAR_LEFT((byte) 0x07),
        LEFT((byte) 0x08),
        UNKNOWN((byte) 0x09);
    
        public static EventType fromByte(byte byteValue) throws CommandParseException {
            EventType[] values = EventType.values();
    
            for (int i = 0; i < values.length; i++) {
                EventType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(EventType.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        EventType(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}