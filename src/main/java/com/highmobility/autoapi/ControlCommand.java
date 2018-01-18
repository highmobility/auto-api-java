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
    private static final byte speedIdentifier = 0x01;
    private static final byte angleIdentifier = 0x02;

    Integer speed;
    Integer angle;

    /**
     *
     * @return The requested speed in km/h, can range between -5 to 5 whereas a negative speed will
     *              reverse the car.
     */
    public Integer getSpeed() {
        return speed;
    }

    /**
     *
     * @return The requested angle of the car
     */
    public Integer getAngle() {
        return angle;
    }

    /**
     *
     * @param speed Speed in km/h, can range between -5 to 5 whereas a negative speed will
     *              reverse the car.
     * @param angle angle of the car.
     * @throws IllegalArgumentException When all arguments are null or invalid
     */
    public ControlCommand(Integer speed, Integer angle)  {
        super(TYPE, getProperties(speed, angle));
        this.speed = speed;
        this.angle = angle;
    }

    static HMProperty[] getProperties(Integer speed, Integer angle) {
        List<Property> properties = new ArrayList<>();

        if (speed != null) {
            if (speed > 5 || speed < -5) throw new IllegalArgumentException();

            IntProperty prop = new IntProperty(speedIdentifier, speed, 1);
            properties.add(prop);
        }

        if (angle != null) {
            IntProperty prop = new IntProperty(angleIdentifier, angle, 2);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }

    ControlCommand(byte[] bytes) throws CommandParseException {
        super(bytes);
        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case speedIdentifier:
                    speed = Property.getSignedInt(property.getValueByte());
                    break;
                case angleIdentifier:
                    angle = Property.getSignedInt(property.getValueBytes());
                    break;
            }
        }
    }
}
