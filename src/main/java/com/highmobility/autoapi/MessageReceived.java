package com.highmobility.autoapi;

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Notify the car that a message has been received. Depending on the car system, it will display or
 * read it loud to the driver.
 */
public class MessageReceived extends CommandWithProperties { // TODO: verify that this subclass
    // is needed
    public static final Type TYPE = new Type(Identifier.MESSAGING, 0x00);

    public MessageReceived(String handle, String message) throws UnsupportedEncodingException,
            CommandParseException {
        super(TYPE, getProperties(handle, message));
    }

    static HMProperty[] getProperties(String handle, String message) throws
            UnsupportedEncodingException {
        ArrayList<HMProperty> properties = new ArrayList<>();

        if (handle != null) {
            StringProperty prop = new StringProperty((byte) 0x01, handle);
            properties.add(prop);
        }

        if (message != null) {
            StringProperty prop = new StringProperty((byte) 0x02, message);
            properties.add(prop);
        }

        return (properties.toArray(new HMProperty[properties.size()]));
    }

    public MessageReceived(Property[] properties) throws CommandParseException,
            IllegalArgumentException {
        super(TYPE, properties);
    }

    MessageReceived(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
