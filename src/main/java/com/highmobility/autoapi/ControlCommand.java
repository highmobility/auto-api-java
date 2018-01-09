package com.highmobility.autoapi;

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.IntProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * To be sent every time the controls for the car wants to be changed or once a second if the
 * controls remain the same. If the car does not receive the command every second it will stop the
 * control mode.
 */
public class ControlCommand extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.REMOTE_CONTROL, 0x04);

    /**
     *
     * @param speed Speed in km/h, can range between -5 to 5 whereas a negative speed will
     *              reverse the car.
     * @param angle angle of the car.
     * @return the command bytes
     * @throws IllegalArgumentException When all arguments are null or invalid
     */
    public ControlCommand(Integer speed, Integer angle)  {
        super(TYPE, getProperties(speed, angle));
    }

    static HMProperty[] getProperties(Integer speed, Integer angle) {
        List<Property> properties = new ArrayList<>();

        if (speed != null) {
            if (speed > 5 || speed < -5) throw new IllegalArgumentException();

            IntProperty prop = new IntProperty((byte) 0x01, speed, 1);
            properties.add(prop);
        }

        if (angle != null) {
            IntProperty prop = new IntProperty((byte) 0x02, angle, 2);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }

    ControlCommand(Property[] properties) throws CommandParseException, IllegalArgumentException {
        super(TYPE, properties);
    }

    ControlCommand(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
