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

import javax.annotation.Nullable;
import com.highmobility.autoapi.property.Property;
import java.util.ArrayList;
import java.util.List;

/**
 * The Vehicle Status capability
 */
public class VehicleStatus {
    public static final int IDENTIFIER = Identifier.VEHICLE_STATUS;

    public static final byte PROPERTY_STATES = (byte)0x99;

    /**
     * Get vehicle status
     */
    public static class GetVehicleStatus extends GetCommand {
        public GetVehicleStatus() {
            super(State.class, IDENTIFIER);
        }
    
        GetVehicleStatus(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The vehicle status state
     */
    public static class State extends SetCommand {
        Property<Command>[] states;
    
        /**
         * @return The states
         */
        public Property<Command>[] getStates() {
            return states;
        }
    
        /**
         * @param identifier The identifier of the command.
         * @return The state for the given Command identifier, if exists.
         */
        @Nullable public Property<Command> getState(Integer identifier) {
            if (states == null) return null;
            for (int i = 0; i < states.length; i++) {
                Property<Command> command = states[i];
                if (command.getValue() != null && command.getValue().getIdentifier() == identifier)
                    return command;
            }
    
            return null;
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
}