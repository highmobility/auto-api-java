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

/**
 * Multi command
 */
public class MultiCommand extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.MULTI_COMMAND;

    public static final byte IDENTIFIER_MULTI_COMMANDS = 0x02;

    Property<Command>[] multiCommands;

    /**
     * @return The multi commands
     */
    public Property<Command>[] getMultiCommands() {
        return multiCommands;
    }
    
    /**
     * Multi command
     *
     * @param multiCommands The bytes of outgoing capabilities (commands)
     */
    public MultiCommand(Command[] multiCommands) {
        super(IDENTIFIER);
    
        ArrayList<Property> multiCommandsBuilder = new ArrayList<>();
        if (multiCommands != null) {
            for (Command multiCommand : multiCommands) {
                Property prop = new Property(0x02, multiCommand);
                multiCommandsBuilder.add(prop);
                addProperty(prop);
            }
        }
        this.multiCommands = multiCommandsBuilder.toArray(new Property[0]);
        createBytes();
    }

    MultiCommand(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<Command>> multiCommandsBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_MULTI_COMMANDS: {
                        Property multiCommand = new Property(Command.class, p);
                        multiCommandsBuilder.add(multiCommand);
                        return multiCommand;
                    }
                }
                return null;
            });
        }
    
        multiCommands = multiCommandsBuilder.toArray(new Property[0]);
        if (this.multiCommands.length == 0) 
            throw new NoPropertiesException();
    }
}