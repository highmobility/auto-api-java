// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class RgbColour extends PropertyValueObject {
    public static final int SIZE = 3;

    Integer red;
    Integer green;
    Integer blue;

    /**
     * @return The red component of RGB.
     */
    public Integer getRed() {
        return red;
    }

    /**
     * @return The green component of RGB.
     */
    public Integer getGreen() {
        return green;
    }

    /**
     * @return The blue component of RGB.
     */
    public Integer getBlue() {
        return blue;
    }

    public RgbColour(Integer red, Integer green, Integer blue) {
        super(3);
        update(red, green, blue);
    }

    public RgbColour(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public RgbColour() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        red = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        green = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        blue = Property.getUnsignedInt(bytes, bytePosition, 1);
    }

    public void update(Integer red, Integer green, Integer blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(red, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(green, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(blue, 1));
    }

    public void update(RgbColour value) {
        update(value.red, value.green, value.blue);
    }

    @Override public int getLength() {
        return 1 + 1 + 1;
    }
}