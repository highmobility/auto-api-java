package com.highmobility.autoapi;

import com.highmobility.autoapi.property.StringProperty;

import java.io.UnsupportedEncodingException;

/**
 * Load a URL in the head unit browser. A URL shortener can be used in other cases. Note that for the
 * car emulator the URL has to be for a secure site (HTTPS).
 */
public class LoadUrl extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.BROWSER, 0x00);

    /**
     * @param url The url that will be loaded to head unit
     * @throws UnsupportedEncodingException The url is not in UTF-8
     * @throws IllegalArgumentException        If the argument is not valid
     */
    public LoadUrl(String url) throws UnsupportedEncodingException {
        super(TYPE, StringProperty.getProperties(url));
    }

    LoadUrl(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
