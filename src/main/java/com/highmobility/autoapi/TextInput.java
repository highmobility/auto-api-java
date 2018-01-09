package com.highmobility.autoapi;

import com.highmobility.autoapi.property.StringProperty;

import java.io.UnsupportedEncodingException;

/**
 * Send a keystroke or entire sentences as input to the car head unit. This can act as an alternative
 * to the input devices that the car is equipped with.
 */
public class TextInput extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.TEXT_INPUT, 0x00);

    /**
     * @param text The text
     * @throws UnsupportedEncodingException The url is not in UTF-8
     * @throws IllegalArgumentException        If the argument is not valid
     */
    public TextInput(String text) throws UnsupportedEncodingException {
        super(TYPE, StringProperty.getProperties(text));
    }

    TextInput(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}