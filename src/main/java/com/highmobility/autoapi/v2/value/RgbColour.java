// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class RgbColour extends PropertyValueObject {
    public static final int SIZE = 3;

    int red;
    int green;
    int blue;

    /**
     * @return The red component of RGB.
     */
    public int getRed() {
        return red;
    }

    /**
     * @return The green component of RGB.
     */
    public int getGreen() {
        return green;
    }

    /**
     * @return The blue component of RGB.
     */
    public int getBlue() {
        return blue;
    }

    public RgbColour(int red, int green, int blue) {
        super(3);
        update(red, green, blue);
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

    public void update(int red, int green, int blue) {
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