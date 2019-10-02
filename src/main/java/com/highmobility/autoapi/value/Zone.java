// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Zone extends PropertyValueObject {
    public static final int SIZE = 2;

    Integer horizontal;
    Integer vertical;

    /**
     * @return The horizontal.
     */
    public Integer getHorizontal() {
        return horizontal;
    }

    /**
     * @return The vertical.
     */
    public Integer getVertical() {
        return vertical;
    }

    public Zone(Integer horizontal, Integer vertical) {
        super(2);
        update(horizontal, vertical);
    }

    public Zone(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public Zone() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        horizontal = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        vertical = Property.getUnsignedInt(bytes, bytePosition, 1);
    }

    public void update(Integer horizontal, Integer vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(horizontal, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(vertical, 1));
    }

    public void update(Zone value) {
        update(value.horizontal, value.vertical);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}