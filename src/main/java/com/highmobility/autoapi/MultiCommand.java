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
import java.util.ArrayList;
import java.util.List;

/**
 * The Multi Command capability
 */
public class MultiCommand {
    public static final int IDENTIFIER = Identifier.MULTI_COMMAND;

    public static final byte PROPERTY_MULTI_STATES = 0x01;
    public static final byte PROPERTY_MULTI_COMMANDS = 0x02;

    /**
     * The multi command state
     */
    public static class State extends SetCommand {
        List<Property<Command>> multiStates;
    
        /**
         * @return The incoming states
         */
        public List<Property<Command>> getMultiStates() {
            return multiStates;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            final ArrayList<Property<Command>> multiStatesBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_MULTI_STATES:
                            Property<Command> multiState = new Property<>(Command.class, p);
                            multiStatesBuilder.add(multiState);
                            return multiState;
                    }
    
                    return null;
                });
            }
    
            multiStates = multiStatesBuilder;
        }
    
        private State(Builder builder) {
            super(builder);
    
            multiStates = builder.multiStates;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private final List<Property<Command>> multiStates = new ArrayList<>();
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * Add an array of multi states.
             * 
             * @param multiStates The multi states. The incoming states
             * @return The builder
             */
            public Builder setMultiStates(Property<Command>[] multiStates) {
                this.multiStates.clear();
                for (int i = 0; i < multiStates.length; i++) {
                    addMultiState(multiStates[i]);
                }
            
                return this;
            }
            /**
             * Add a single multi state.
             * 
             * @param multiState The multi state. The incoming states
             * @return The builder
             */
            public Builder addMultiState(Property<Command> multiState) {
                multiState.setIdentifier(PROPERTY_MULTI_STATES);
                addProperty(multiState);
                multiStates.add(multiState);
                return this;
            }
        }
    }

    /**
     * Multi command command
     */
    public static class MultiCommandCommand extends SetCommand {
        List<Property<Command>> multiCommands;
    
        /**
         * @return The multi commands
         */
        public List<Property<Command>> getMultiCommands() {
            return multiCommands;
        }
        
        /**
         * Multi command command
         *
         * @param multiCommands The outgoing commands
         */
        public MultiCommandCommand(List<Command> multiCommands) {
            super(IDENTIFIER);
        
            final ArrayList<Property<Command>> multiCommandsBuilder = new ArrayList<>();
            if (multiCommands != null) {
                for (Command multiCommand : multiCommands) {
                    Property prop = new Property(0x02, multiCommand);
                    multiCommandsBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.multiCommands = multiCommandsBuilder;
            createBytes();
        }
    
        MultiCommandCommand(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
        
            final ArrayList<Property<Command>> multiCommandsBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_MULTI_COMMANDS: {
                            Property<Command> multiCommand = new Property<>(Command.class, p);
                            multiCommandsBuilder.add(multiCommand);
                            return multiCommand;
                        }
                    }
                    return null;
                });
            }
        
            multiCommands = multiCommandsBuilder;
            if (this.multiCommands.size() == 0) 
                throw new NoPropertiesException();
        }
    }
}