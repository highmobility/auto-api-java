/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
        if (property.getValueComponent() == null) throw new CommandParseException();
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