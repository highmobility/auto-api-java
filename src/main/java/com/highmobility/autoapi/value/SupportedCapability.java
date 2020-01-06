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

public class SupportedCapability extends PropertyValueObject {
    Integer capabilityID;
    Bytes supportedPropertyIDs;

    /**
     * @return The identifier of the supported capability.
     */
    public Integer getCapabilityID() {
        return capabilityID;
    }

    /**
     * @return Array of supported property identifiers.
     */
    public Bytes getSupportedPropertyIDs() {
        return supportedPropertyIDs;
    }

    public SupportedCapability(Integer capabilityID, Bytes supportedPropertyIDs) {
        super(0);
        update(capabilityID, supportedPropertyIDs);
    }

    public SupportedCapability(Property property) throws CommandParseException {
        super();
        if (property.getValueComponent() == null) throw new CommandParseException();
        update(property.getValueComponent().getValueBytes());
    }

    public SupportedCapability() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        capabilityID = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;

        int supportedPropertyIDsSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        supportedPropertyIDs = getRange(bytePosition, bytePosition + supportedPropertyIDsSize);
    }

    public void update(Integer capabilityID, Bytes supportedPropertyIDs) {
        this.capabilityID = capabilityID;
        this.supportedPropertyIDs = supportedPropertyIDs;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(capabilityID, 2));
        bytePosition += 2;

        set(bytePosition, Property.intToBytes(supportedPropertyIDs.getLength(), 2));
        bytePosition += 2;
        set(bytePosition, supportedPropertyIDs);
    }

    public void update(SupportedCapability value) {
        update(value.capabilityID, value.supportedPropertyIDs);
    }

    @Override public int getLength() {
        return 2 + 2 + supportedPropertyIDs.getLength();
    }
}