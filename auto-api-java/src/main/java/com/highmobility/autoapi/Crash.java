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
import com.highmobility.autoapi.value.CrashIncident;
import com.highmobility.autoapi.value.EnabledState;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Crash capability
 */
public class Crash {
    public static final int IDENTIFIER = Identifier.CRASH;

    public static final byte PROPERTY_INCIDENTS = 0x01;
    public static final byte PROPERTY_TYPE = 0x02;
    public static final byte PROPERTY_TIPPED_STATE = 0x03;
    public static final byte PROPERTY_AUTOMATIC_ECALL = 0x04;
    public static final byte PROPERTY_SEVERITY = 0x05;
    public static final byte PROPERTY_IMPACT_ZONE = 0x06;
    public static final byte PROPERTY_STATUS = 0x07;

    /**
     * Get Crash property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Crash property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Crash property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Crash property availabilities
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
     * Get Crash properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Crash properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Crash properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Crash properties
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
     * Get specific Crash properties
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
     * The crash state
     */
    public static class State extends SetCommand {
        List<Property<CrashIncident>> incidents;
        Property<Type> type = new Property<>(Type.class, PROPERTY_TYPE);
        Property<TippedState> tippedState = new Property<>(TippedState.class, PROPERTY_TIPPED_STATE);
        Property<EnabledState> automaticECall = new Property<>(EnabledState.class, PROPERTY_AUTOMATIC_ECALL);
        PropertyInteger severity = new PropertyInteger(PROPERTY_SEVERITY, false);
        List<Property<ImpactZone>> impactZone;
        Property<Status> status = new Property<>(Status.class, PROPERTY_STATUS);
    
        /**
         * @return The incidents
         */
        public List<Property<CrashIncident>> getIncidents() {
            return incidents;
        }
    
        /**
         * @return The type
         */
        public Property<Type> getType() {
            return type;
        }
    
        /**
         * @return The tipped state
         */
        public Property<TippedState> getTippedState() {
            return tippedState;
        }
    
        /**
         * @return Automatic emergency call enabled state
         */
        public Property<EnabledState> getAutomaticECall() {
            return automaticECall;
        }
    
        /**
         * @return Severity of the crash (from 0 to 7 - very high severity)
         */
        public PropertyInteger getSeverity() {
            return severity;
        }
    
        /**
         * @return Impact zone of the crash
         */
        public List<Property<ImpactZone>> getImpactZone() {
            return impactZone;
        }
    
        /**
         * @return The system effect an inpact had on the vehicle.
         */
        public Property<Status> getStatus() {
            return status;
        }
    
        State(byte[] bytes) {
            super(bytes);
    
            final ArrayList<Property<CrashIncident>> incidentsBuilder = new ArrayList<>();
            final ArrayList<Property<ImpactZone>> impactZoneBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_INCIDENTS:
                            Property<CrashIncident> incident = new Property<>(CrashIncident.class, p);
                            incidentsBuilder.add(incident);
                            return incident;
                        case PROPERTY_TYPE: return type.update(p);
                        case PROPERTY_TIPPED_STATE: return tippedState.update(p);
                        case PROPERTY_AUTOMATIC_ECALL: return automaticECall.update(p);
                        case PROPERTY_SEVERITY: return severity.update(p);
                        case PROPERTY_IMPACT_ZONE:
                            Property<ImpactZone> impactZone = new Property<>(ImpactZone.class, p);
                            impactZoneBuilder.add(impactZone);
                            return impactZone;
                        case PROPERTY_STATUS: return status.update(p);
                    }
    
                    return null;
                });
            }
    
            incidents = incidentsBuilder;
            impactZone = impactZoneBuilder;
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
             * Add an array of incidents
             * 
             * @param incidents The incidents
             * @return The builder
             */
            public Builder setIncidents(Property<CrashIncident>[] incidents) {
                for (int i = 0; i < incidents.length; i++) {
                    addIncident(incidents[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single incident
             * 
             * @param incident The incident
             * @return The builder
             */
            public Builder addIncident(Property<CrashIncident> incident) {
                incident.setIdentifier(PROPERTY_INCIDENTS);
                addProperty(incident);
                return this;
            }
            
            /**
             * @param type The type
             * @return The builder
             */
            public Builder setType(Property<Type> type) {
                Property property = type.setIdentifier(PROPERTY_TYPE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param tippedState The tipped state
             * @return The builder
             */
            public Builder setTippedState(Property<TippedState> tippedState) {
                Property property = tippedState.setIdentifier(PROPERTY_TIPPED_STATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param automaticECall Automatic emergency call enabled state
             * @return The builder
             */
            public Builder setAutomaticECall(Property<EnabledState> automaticECall) {
                Property property = automaticECall.setIdentifier(PROPERTY_AUTOMATIC_ECALL);
                addProperty(property);
                return this;
            }
            
            /**
             * @param severity Severity of the crash (from 0 to 7 - very high severity)
             * @return The builder
             */
            public Builder setSeverity(Property<Integer> severity) {
                Property property = new PropertyInteger(PROPERTY_SEVERITY, false, 1, severity);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of impact zone
             * 
             * @param impactZone The impact zone. Impact zone of the crash
             * @return The builder
             */
            public Builder setImpactZone(Property<ImpactZone>[] impactZone) {
                for (int i = 0; i < impactZone.length; i++) {
                    addImpactZone(impactZone[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single impact zone
             * 
             * @param impactZone The impact zone. Impact zone of the crash
             * @return The builder
             */
            public Builder addImpactZone(Property<ImpactZone> impactZone) {
                impactZone.setIdentifier(PROPERTY_IMPACT_ZONE);
                addProperty(impactZone);
                return this;
            }
            
            /**
             * @param status The system effect an inpact had on the vehicle.
             * @return The builder
             */
            public Builder setStatus(Property<Status> status) {
                Property property = status.setIdentifier(PROPERTY_STATUS);
                addProperty(property);
                return this;
            }
        }
    }

    public enum Type implements ByteEnum {
        PEDESTRIAN((byte) 0x00),
        NON_PEDESTRIAN((byte) 0x01);
    
        public static Type fromByte(byte byteValue) throws CommandParseException {
            Type[] values = Type.values();
    
            for (int i = 0; i < values.length; i++) {
                Type state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Type.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Type(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum TippedState implements ByteEnum {
        TIPPED_OVER((byte) 0x00),
        NOT_TIPPED((byte) 0x01);
    
        public static TippedState fromByte(byte byteValue) throws CommandParseException {
            TippedState[] values = TippedState.values();
    
            for (int i = 0; i < values.length; i++) {
                TippedState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(TippedState.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        TippedState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum ImpactZone implements ByteEnum {
        PEDESTRIAN_PROTECTION((byte) 0x00),
        ROLLOVER((byte) 0x01),
        REAR_PASSENGER_SIDE((byte) 0x02),
        REAR_DRIVER_SIDE((byte) 0x03),
        SIDE_PASSENGER_SIDE((byte) 0x04),
        SIDE_DRIVER_SIDE((byte) 0x05),
        FRONT_PASSENGER_SIDE((byte) 0x06),
        FRONT_DRIVER_SIDE((byte) 0x07);
    
        public static ImpactZone fromByte(byte byteValue) throws CommandParseException {
            ImpactZone[] values = ImpactZone.values();
    
            for (int i = 0; i < values.length; i++) {
                ImpactZone state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(ImpactZone.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        ImpactZone(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum Status implements ByteEnum {
        NORMAL((byte) 0x00),
        RESTRAINTS_ENGAGED((byte) 0x01),
        AIRBAG_TRIGGERED((byte) 0x02);
    
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
}