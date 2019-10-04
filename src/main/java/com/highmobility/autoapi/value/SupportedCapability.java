/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
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