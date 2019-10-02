// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class HmkitVersion extends PropertyValueObject {
    public static final int SIZE = 3;

    Integer major;
    Integer minor;
    Integer patch;

    /**
     * @return HMKit version major number.
     */
    public Integer getMajor() {
        return major;
    }

    /**
     * @return HMKit version minor number.
     */
    public Integer getMinor() {
        return minor;
    }

    /**
     * @return HMKit version patch number.
     */
    public Integer getPatch() {
        return patch;
    }

    public HmkitVersion(Integer major, Integer minor, Integer patch) {
        super(3);
        update(major, minor, patch);
    }

    public HmkitVersion(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public HmkitVersion() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        major = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        minor = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        patch = Property.getUnsignedInt(bytes, bytePosition, 1);
    }

    public void update(Integer major, Integer minor, Integer patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(major, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(minor, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(patch, 1));
    }

    public void update(HmkitVersion value) {
        update(value.major, value.minor, value.patch);
    }

    @Override public int getLength() {
        return 1 + 1 + 1;
    }
}