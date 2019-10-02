// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class TroubleCode extends PropertyValueObject {
    Integer occurences;
    String ID;
    String ecuID;
    String status;

    /**
     * @return Number of occurences.
     */
    public Integer getOccurences() {
        return occurences;
    }

    /**
     * @return Identifier.
     */
    public String getID() {
        return ID;
    }

    /**
     * @return Electronic Control Unit identifier.
     */
    public String getEcuID() {
        return ecuID;
    }

    /**
     * @return Status.
     */
    public String getStatus() {
        return status;
    }

    public TroubleCode(Integer occurences, String ID, String ecuID, String status) {
        super(0);
        update(occurences, ID, ecuID, status);
    }

    public TroubleCode(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public TroubleCode() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 7) throw new CommandParseException();

        int bytePosition = 0;
        occurences = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        int IDSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        ID = Property.getString(value, bytePosition, IDSize);
        bytePosition += IDSize;

        int ecuIDSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        ecuID = Property.getString(value, bytePosition, ecuIDSize);
        bytePosition += ecuIDSize;

        int statusSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        status = Property.getString(value, bytePosition, statusSize);
    }

    public void update(Integer occurences, String ID, String ecuID, String status) {
        this.occurences = occurences;
        this.ID = ID;
        this.ecuID = ecuID;
        this.status = status;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(occurences, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(ID.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(ID));
        bytePosition += ID.length();

        set(bytePosition, Property.intToBytes(ecuID.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(ecuID));
        bytePosition += ecuID.length();

        set(bytePosition, Property.intToBytes(status.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(status));
    }

    public void update(TroubleCode value) {
        update(value.occurences, value.ID, value.ecuID, value.status);
    }

    @Override public int getLength() {
        return 1 + ID.length() + 2 + ecuID.length() + 2 + status.length() + 2;
    }
}