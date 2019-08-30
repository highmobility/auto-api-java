// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Zone extends PropertyValueObject {
    public static final int SIZE = 2;

    int horisontal;
    int vertical;

    /**
     * @return The horisontal.
     */
    public int getHorisontal() {
        return horisontal;
    }

    /**
     * @return The vertical.
     */
    public int getVertical() {
        return vertical;
    }

    public Zone(int horisontal, int vertical) {
        super(2);
        update(horisontal, vertical);
    }

    public Zone() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        horisontal = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        vertical = Property.getUnsignedInt(bytes, bytePosition, 1);
    }

    public void update(int horisontal, int vertical) {
        this.horisontal = horisontal;
        this.vertical = vertical;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(horisontal, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(vertical, 1));
    }

    public void update(Zone value) {
        update(value.horisontal, value.vertical);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}