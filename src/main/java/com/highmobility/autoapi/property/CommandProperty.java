package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandWithProperties;
import com.highmobility.utils.Bytes;

public class CommandProperty extends Property {
    private static final byte defaultIdentifier = (byte)0x99;

    public CommandProperty(CommandWithProperties value) {
        this(defaultIdentifier, value);
    }

    public CommandProperty(byte identifier, CommandWithProperties value) {
        super(identifier, value.getBytes().length);
        Bytes.setBytes(bytes, value.getBytes(), 3);
    }
}