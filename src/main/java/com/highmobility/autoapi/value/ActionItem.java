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

public class ActionItem extends PropertyValueObject {
    Integer id;
    String name;

    /**
     * @return Action identifier.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return Name of the action.
     */
    public String getName() {
        return name;
    }

    public ActionItem(Integer id, String name) {
        super(0);
        update(id, name);
    }

    public ActionItem(Property property) throws CommandParseException {
        super();
        if (property.getValueComponent() == null) throw new CommandParseException();
        update(property.getValueComponent().getValueBytes());
    }

    public ActionItem() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        id = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        int nameSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        name = Property.getString(value, bytePosition, nameSize);
    }

    public void update(Integer id, String name) {
        this.id = id;
        this.name = name;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(id, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(name.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(name));
    }

    public void update(ActionItem value) {
        update(value.id, value.name);
    }

    @Override public int getLength() {
        return 1 + name.length() + 2;
    }
}