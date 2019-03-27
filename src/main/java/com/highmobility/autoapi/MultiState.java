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

import javax.annotation.Nullable;

/**
 * This message is sent when a Multi Command message is received by the car. The new states are
 * included in the message payload.
 */
public class MultiState extends Command {
    public static final Type TYPE = new Type(Identifier.MULTI_COMMAND, 0x01);

    private static final byte PROP_IDENTIFIER = 0x01;

    Property<Command>[] commands;

    /**
     * @return All of the commands.
     */
    public Property<Command>[] getCommands() {
        return commands;
    }

    /**
     * Get a command with a type.
     *
     * @param type The command type.
     * @return The command.
     */
    @Nullable public Property<Command> getCommand(Type type) {
        for (Property<Command> command : commands) {
            if (command.getValue() != null && command.getValue().is(type)) return command;
        }

        return null;
    }

    MultiState(byte[] bytes) {
        super(bytes);

        ArrayList<Property<Command>> builder = new ArrayList<>();
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                if (p.getPropertyIdentifier() == PROP_IDENTIFIER) {
                    Property c = new Property(Command.class, p);
                    builder.add(c);
                    return c;
                }

                return null;
            });
        }

        commands = builder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private MultiState(Builder builder) {
        super(builder);
        commands = builder.commands.toArray(new Property[0]);
    }

    public static final class Builder extends Command.Builder {
        private List<Property<Command>> commands = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * @param command The command.
         * @return The builder.
         */
        public Builder addCommand(Property<Command> command) {
            commands.add(command);
            addProperty(command.setIdentifier(PROP_IDENTIFIER));
            return this;
        }

        /**
         * @param commands The commands.
         * @return The builder.
         */
        public Builder setCommands(Property<Command>[] commands) {
            this.commands.clear();
            for (Property<Command> command : commands) {
                addCommand(command);
            }
            return this;
        }

        public MultiState build() {
            return new MultiState(this);
        }
    }
}
