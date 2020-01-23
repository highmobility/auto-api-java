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
        if (property.getValueComponent() == null) throw new CommandParseException();
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