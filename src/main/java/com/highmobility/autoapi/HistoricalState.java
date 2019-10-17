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
 * The historical state
 */
public class HistoricalState extends SetCommand {
    public static final int IDENTIFIER = Identifier.HISTORICAL;

    public static final byte IDENTIFIER_STATES = 0x01;

    Property<Command>[] states;

    /**
     * @return The states
     */
    public Property<Command>[] getStates() {
        return states;
    }

    HistoricalState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> statesBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_STATES:
                        Property<Command> state = new Property(Command.class, p);
                        statesBuilder.add(state);
                        return state;
                }

                return null;
            });
        }

        states = statesBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private HistoricalState(Builder builder) {
        super(builder);

        states = builder.states.toArray(new Property[0]);
    }

    public static final class Builder extends SetCommand.Builder {
        private List<Property> states = new ArrayList<>();

        public Builder() {
            super(IDENTIFIER);
        }

        public HistoricalState build() {
            return new HistoricalState(this);
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
            state.setIdentifier(IDENTIFIER_STATES);
            addProperty(state);
            states.add(state);
            return this;
        }
    }
}