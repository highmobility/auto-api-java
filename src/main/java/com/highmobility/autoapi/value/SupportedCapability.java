// TODO: license

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