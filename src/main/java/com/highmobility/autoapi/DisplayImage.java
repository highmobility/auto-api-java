package com.highmobility.autoapi;

import com.highmobility.autoapi.property.StringProperty;

import java.io.UnsupportedEncodingException;

/**
 * Display an image in the head unit by providing the image URL.
 */
public class DisplayImage extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.GRAPHICS, 0x00);

    /**
     * @param url The url of the image that will be loaded to head unit
     * @throws UnsupportedEncodingException The url is not in UTF-8
     * @throws IllegalArgumentException        If the argument is not valid
     */
    public DisplayImage(String url) throws UnsupportedEncodingException {
        super(TYPE, StringProperty.getProperties(url));
    }

    DisplayImage(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
