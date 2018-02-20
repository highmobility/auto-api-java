package com.highmobility.autoapi;

import com.highmobility.autoapi.property.HMProperty;

/**
 * Used for commands that require at least 1 property to be set.
 */
public class CommandWithExistingProperties extends CommandWithProperties {
    static final String ALL_ARGUMENTS_NULL_EXCEPTION = "One of the arguments must not be null";

    public CommandWithExistingProperties(byte[] bytes) {
        super(bytes);
        if (bytes.length < 7) throw new IllegalArgumentException(ALL_ARGUMENTS_NULL_EXCEPTION);
    }

    CommandWithExistingProperties(Type type, HMProperty[] properties) throws IllegalArgumentException {
        super(type, properties);
        if (properties == null || properties.length == 0)
            throw new IllegalArgumentException(ALL_ARGUMENTS_NULL_EXCEPTION);
    }

    CommandWithExistingProperties(CommandWithProperties.Builder builder) {
        super(builder);
    }
}
