// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class SupportedCapability extends PropertyValueObject {
    int capabilityID;
    Bytes supportedPropertyIDs;

    /**
     * @return The identifier of the supported capability.
     */
    public int getCapabilityID() {
        return capabilityID;
    }

    /**
     * @return Array of supported property identifiers.
     */
    public Bytes getSupportedPropertyIDs() {
        return supportedPropertyIDs;
    }

    public SupportedCapability(int capabilityID, Bytes supportedPropertyIDs) {
        super(0);
        update(capabilityID, supportedPropertyIDs);
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

        supportedPropertyIDs = getRange(bytePosition, getLength());
    }

    public void update(int capabilityID, Bytes supportedPropertyIDs) {
        this.capabilityID = capabilityID;
        this.supportedPropertyIDs = supportedPropertyIDs;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(capabilityID, 2));
        bytePosition += 2;

        set(bytePosition, supportedPropertyIDs);
    }

    public void update(SupportedCapability value) {
        update(value.capabilityID, value.supportedPropertyIDs);
    }

    @Override public int getLength() {
        return 2 + supportedPropertyIDs.getLength();
    }
}