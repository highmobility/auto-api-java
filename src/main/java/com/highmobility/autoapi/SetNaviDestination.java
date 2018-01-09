package com.highmobility.autoapi;

import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Set the navigation destination. This will be forwarded to the navigation system of the car.
 */
public class SetNaviDestination extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.NAVI_DESTINATION, 0x02);

    /**
     *
     * @param latitude the latitude of the destination
     * @param longitude the longitude of the destination
     * @param name the destination name
     * @return the command bytes
     * @throws UnsupportedEncodingException when the name string is in wrong format
     * @throws IllegalArgumentException if all arguments are null
     */
    public SetNaviDestination(Float latitude, Float longitude, String name)
            throws UnsupportedEncodingException {
        super(TYPE, getProperties(latitude, longitude, name));
    }

    static HMProperty[] getProperties(Float latitude, Float longitude, String name)
            throws UnsupportedEncodingException {
        List<Property> properties = new ArrayList<>();

        if (latitude != null) {
            Property prop = new FloatProperty((byte) 0x01, latitude);
            properties.add(prop);
        }

        if (longitude != null) {
            Property prop = new FloatProperty((byte) 0x02, longitude);
            properties.add(prop);
        }

        if (name != null) {
            Property prop = new StringProperty((byte) 0x03, name);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }

    public SetNaviDestination(Property[] properties) throws CommandParseException, IllegalArgumentException {
        super(TYPE, properties);
    }

    SetNaviDestination(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
