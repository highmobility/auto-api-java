package com.highmobility.autoapi;

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.IntProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Set the rooftop state. The result is sent through the evented Rooftop State message.
 */
public class ControlRooftop extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.ROOFTOP, 0x02);

    /**
     *
     * @param dimmingPercentage The dimming percentage
     * @param openPercentage The rooftop open percentage
     * @throws IllegalArgumentException When both arguments are null
     */
    public ControlRooftop(Float dimmingPercentage, Float openPercentage) {
        super(TYPE, getProperties(dimmingPercentage, openPercentage));
    }

    static HMProperty[] getProperties(Float dimmingPercentage, Float openPercentage) {
        List<Property> properties = new ArrayList<>();

        if (dimmingPercentage != null) {
            IntProperty prop = new IntProperty((byte) 0x01, (int) (dimmingPercentage * 100f), 1);
            properties.add(prop);
        }

        if (openPercentage != null) {
            IntProperty prop = new IntProperty((byte) 0x02, (int) (openPercentage * 100f), 1);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }

    ControlRooftop(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
