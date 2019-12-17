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

import com.highmobility.autoapi.capability.DisabledIn;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Nullable;

/**
 * The Historical capability
 */
public class Historical {
    public static final int IDENTIFIER = Identifier.HISTORICAL;

    public static final byte PROPERTY_STATES = 0x01;
    public static final byte PROPERTY_CAPABILITY_ID = 0x02;
    public static final byte PROPERTY_START_DATE = 0x03;
    public static final byte PROPERTY_END_DATE = 0x04;

    public static final DisabledIn[] disabledIn = new DisabledIn[] { DisabledIn.BLE };

    /**
     * The historical state
     */
    public static class State extends SetCommand {
        Property<Command>[] states;
    
        /**
         * @return The states
         */
        public Property<Command>[] getStates() {
            return states;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> statesBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATES:
                            Property<Command> state = new Property(Command.class, p);
                            statesBuilder.add(state);
                            return state;
                    }
    
                    return null;
                });
            }
    
            states = statesBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            states = builder.states.toArray(new Property[0]);
        }
    
        public static final class Builder extends SetCommand.Builder {
            private List<Property> states = new ArrayList<>();
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * Add an array of states.
             * 
             * @param states The states
             * @return The builder
             */
            public Builder setStates(Property<Command>[] states) {
                this.states.clear();
                for (int i = 0; i < states.length; i++) {
                    addState(states[i]);
                }
            
                return this;
            }
            /**
             * Add a single state.
             * 
             * @param state The state
             * @return The builder
             */
            public Builder addState(Property<Command> state) {
                state.setIdentifier(PROPERTY_STATES);
                addProperty(state);
                states.add(state);
                return this;
            }
        }
    }

    /**
     * Request states
     */
    public static class RequestStates extends SetCommand {
        PropertyInteger capabilityID = new PropertyInteger(PROPERTY_CAPABILITY_ID, false);
        Property<Calendar> startDate = new Property(Calendar.class, PROPERTY_START_DATE);
        Property<Calendar> endDate = new Property(Calendar.class, PROPERTY_END_DATE);
    
        /**
         * @return The capability id
         */
        public PropertyInteger getCapabilityID() {
            return capabilityID;
        }
        
        /**
         * @return The start date
         */
        public Property<Calendar> getStartDate() {
            return startDate;
        }
        
        /**
         * @return The end date
         */
        public Property<Calendar> getEndDate() {
            return endDate;
        }
        
        /**
         * Request states
         *
         * @param capabilityID The identifier of the Capability
         * @param startDate Milliseconds since UNIX Epoch time
         * @param endDate Milliseconds since UNIX Epoch time
         */
        public RequestStates(Integer capabilityID, @Nullable Calendar startDate, @Nullable Calendar endDate) {
            super(IDENTIFIER);
        
            addProperty(this.capabilityID.update(false, 2, capabilityID));
            addProperty(this.startDate.update(startDate));
            addProperty(this.endDate.update(endDate));
            createBytes();
        }
    
        RequestStates(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CAPABILITY_ID: return capabilityID.update(p);
                        case PROPERTY_START_DATE: return startDate.update(p);
                        case PROPERTY_END_DATE: return endDate.update(p);
                    }
                    return null;
                });
            }
            if (this.capabilityID.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
}