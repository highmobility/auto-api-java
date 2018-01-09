package com.highmobility.autoapi;

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.utils.Bytes;

/**
 * CommandWithProperties expects at least 1 property and no size for the whole command.
 * It is used for constructing commands with properties.
 */
abstract class CommandWithProperties extends Command {
    static final String ALL_ARGUMENTS_NULL_EXCEPTION = "One of the arguments must not be null";

    CommandWithProperties(Type type, HMProperty[] properties) throws IllegalArgumentException {
        super( null, true);

        if (properties == null || properties.length == 0) throw new IllegalArgumentException(ALL_ARGUMENTS_NULL_EXCEPTION);

        bytes = new byte[] {
                type.getIdentifierAndType()[0],
                type.getIdentifierAndType()[1],
                type.getIdentifierAndType()[2]
        };

        for (int i = 0; i < properties.length; i++) {
            bytes = Bytes.concatBytes(bytes, properties[i].getPropertyBytes());
        }
    }

    public CommandWithProperties(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 7) throw new IllegalArgumentException(ALL_ARGUMENTS_NULL_EXCEPTION);
    }
}
