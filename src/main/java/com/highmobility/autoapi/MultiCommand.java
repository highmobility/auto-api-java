// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import java.util.ArrayList;

/**
 * Multi command
 */
public class MultiCommand extends SetCommand {
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
     * @param multiCommands The The bytes of outgoing capabilities (commands)
     */
    public MultiCommand(Command[] multiCommands) {
        super(Identifier.MULTI_COMMAND);
    
        ArrayList<Property> multiCommandsBuilder = new ArrayList<>();
        if (multiCommands != null) {
            for (Command multiCommand : multiCommands) {
                Property prop = new Property(0x02, multiCommand);
                multiCommandsBuilder.add(prop);
                addProperty(prop, true);
            }
        }
        this.multiCommands = multiCommandsBuilder.toArray(new Property[0]);
    }

    MultiCommand(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<Command>> multiCommandsBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x02: {
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