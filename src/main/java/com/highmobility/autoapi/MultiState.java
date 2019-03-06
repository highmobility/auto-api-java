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

import javax.annotation.Nullable;

/**
 * This message is sent when a Multi Command message is received by the car. The new states are
 * included in the message payload.
 */
public class MultiState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.MULTI_COMMAND, 0x01);

    private static final byte PROP_IDENTIFIER = 0x01;

    Property<CommandWithProperties>[] commands;

    /**
     * @return All of the commands.
     */
    public Property<CommandWithProperties>[] getCommands() {
        return commands;
    }

    /**
     * Get a command with a type.
     *
     * @param type The command type.
     * @return The command.
     */
    @Nullable public Property<CommandWithProperties> getCommand(Type type) {
        for (Property<CommandWithProperties> command : commands) {
            if (command.getValue() != null && command.getValue().is(type)) return command;
        }

        return null;
    }

    MultiState(byte[] bytes) {
        super(bytes);

        ArrayList<Property<CommandWithProperties>> builder = new ArrayList<>();
        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                if (p.getPropertyIdentifier() == PROP_IDENTIFIER) {
                    Property c = new Property(CommandWithProperties.class, p);
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

    // TBODO:
    /*
    MultiState(Command[] commands) {
        super(TYPE, getProperties(commands));
        this.commands = commands;
    }

    private static Property[] getProperties(Command[] commands) {
        ArrayList<Property> properties = new ArrayList<>();

        for (Command command : commands) {
            Property prop = new Property(PROP_IDENTIFIER, command);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }
     */
}
