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

import java.util.ArrayList;

/**
 * This message is sent when a Get Historical States is received. The states are passed along as an
 * array of all states for the given period.
 */
public class HistoricalStates extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HISTORICAL, 0x01);
    private static final byte STATE_IDENTIFIER = 0x01;

    CommandProperty[] states;

    /**
     * @return The historical states. Use {@link CommandWithProperties#getTimestamp()} to understand
     * the command time.
     */
    public CommandProperty[] getStates() {
        return states;
    }

    /**
     * @param type The type.
     * @return The historical states for the type.
     */
    public CommandProperty[] getStates(Type type) {
        ArrayList<CommandProperty> builder = new ArrayList<>();

        for (int i = 0; i < states.length; i++) {
            CommandProperty prop = states[i];
            if (prop.getValue() != null) {
                CommandWithProperties command = prop.getValue();
                if (command.getType().equals(type)) {
                    builder.add(prop);
                }
            }
        }

        return builder.toArray(new CommandProperty[0]);
    }

    HistoricalStates(byte[] bytes) {
        super(bytes);

        ArrayList<CommandProperty> builder = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                if (p.getPropertyIdentifier() == STATE_IDENTIFIER) {
                    CommandProperty state = new CommandProperty(p);
                    builder.add(state);
                    return state;

                }
                return null;
            });
        }

        states = builder.toArray(new CommandProperty[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    // TBODO:
    /*private HistoricalStates(Builder builder) {
        super(builder);
        state = builder.state;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        public Builder() {
            super(TYPE);
        }

        public HistoricalStates build() {
            return new HistoricalStates(this);
        }
    }*/
}
