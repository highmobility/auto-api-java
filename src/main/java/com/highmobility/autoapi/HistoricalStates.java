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
 * This message is sent when a Get Historical States is received. The states are passed along as an
 * array of all states for the given period.
 */
public class HistoricalStates extends Command {
    public static final Type TYPE = new Type(Identifier.HISTORICAL, 0x01);
    private static final byte STATE_IDENTIFIER = 0x01;

    Property<Command>[] states;

    /**
     * @return The historical states. Use {@link Command#getTimestamp()} to understand the command
     * time.
     */
    public Property<Command>[] getStates() {
        return states;
    }

    /**
     * @param type The type.
     * @return The historical states for the type.
     */
    public Property<Command>[] getStates(Type type) {
        List<Property> builder = new ArrayList<>();

        for (int i = 0; i < states.length; i++) {
            Property<Command> prop = states[i];
            if (prop.getValue() != null) {
                Command command = prop.getValue();
                if (command.getType().equals(type)) {
                    builder.add(prop);
                }
            }
        }

        return builder.toArray(new Property[0]);
    }

    HistoricalStates(byte[] bytes) {
        super(bytes);

        List<Property> builder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                if (p.getPropertyIdentifier() == STATE_IDENTIFIER) {
                    Property c = new Property(Command.class, p);
                    builder.add(c);
                    return c;

                }
                return null;
            });
        }

        states = builder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private HistoricalStates(Builder builder) {
        super(builder);
        states = builder.states.toArray(new Property[0]);
    }

    public static final class Builder extends Command.Builder {
        public List<Property> states = new ArrayList<>();

        /**
         * @param state The historical state.
         * @return The builder.
         */
        public Builder addState(Property<Command> state) {
            this.states.add(state.setIdentifier(STATE_IDENTIFIER));
            addProperty(state);
            return this;
        }

        /**
         * @param states The historical states.
         * @return The builder.
         */
        public Builder setStates(Property<Command>[] states) {
            this.states.clear();
            for (Property<Command> state : states) {
                addState(state);
            }
            return this;
        }

        public Builder() {
            super(TYPE);
        }

        public HistoricalStates build() {
            return new HistoricalStates(this);
        }
    }
}
