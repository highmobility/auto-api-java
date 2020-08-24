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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import com.highmobility.autoapi.capability.DisabledIn;
import javax.annotation.Nullable;
import com.highmobility.value.Bytes;

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
         * @param startDate Start date for historical data query
         * @param endDate End date for historical data query
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
    
    /**
     * Get trips
     */
    public static class GetTrips extends SetCommand {
        PropertyInteger capabilityID = new PropertyInteger(PROPERTY_CAPABILITY_ID, false);
        Property<Calendar> startDate = new Property(Calendar.class, PROPERTY_START_DATE);
        Property<Calendar> endDate = new Property(Calendar.class, PROPERTY_END_DATE);
    
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
         * Get trips
         *
         * @param startDate Start date for historical data query
         * @param endDate End date for historical data query
         */
        public GetTrips(@Nullable Calendar startDate, @Nullable Calendar endDate) {
            super(IDENTIFIER);
        
            addProperty(capabilityID.addValueComponent(new Bytes("006a")));
            addProperty(this.startDate.update(startDate));
            addProperty(this.endDate.update(endDate));
            createBytes();
        }
    
        GetTrips(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CAPABILITY_ID: capabilityID.update(p);
                        case PROPERTY_START_DATE: return startDate.update(p);
                        case PROPERTY_END_DATE: return endDate.update(p);
                    }
                    return null;
                });
            }
            if ((capabilityID.getValue() == null || capabilityID.getValueComponent().getValueBytes().equals(new Bytes("006a")) == false)) 
                throw new NoPropertiesException();
        }
    }
}