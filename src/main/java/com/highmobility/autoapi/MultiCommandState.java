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
import java.util.ArrayList;
import java.util.List;

/**
 * The multi command state
 */
public class MultiCommandState extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.MULTI_COMMAND;

    public static final byte IDENTIFIER_MULTI_STATES = 0x01;

    Property<Command>[] multiStates;

    /**
     * @return The bytes of incoming capabilities (states)
     */
    public Property<Command>[] getMultiStates() {
        return multiStates;
    }

    MultiCommandState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> multiStatesBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_MULTI_STATES:
                        Property<Command> multiState = new Property(Command.class, p);
                        multiStatesBuilder.add(multiState);
                        return multiState;
                }

                return null;
            });
        }

        multiStates = multiStatesBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private MultiCommandState(Builder builder) {
        super(builder);

        multiStates = builder.multiStates.toArray(new Property[0]);
    }

    public static final class Builder extends SetCommand.Builder {
        private List<Property> multiStates = new ArrayList<>();

        public Builder() {
            super(IDENTIFIER);
        }

        public MultiCommandState build() {
            return new MultiCommandState(this);
        }

        /**
         * Add an array of multi states.
         * 
         * @param multiStates The multi states. The bytes of incoming capabilities (states)
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
         * @param multiState The multi state. The bytes of incoming capabilities (states)
         * @return The builder
         */
        public Builder addMultiState(Property<Command> multiState) {
            multiState.setIdentifier(IDENTIFIER_MULTI_STATES);
            addProperty(multiState);
            multiStates.add(multiState);
            return this;
        }
    }
}