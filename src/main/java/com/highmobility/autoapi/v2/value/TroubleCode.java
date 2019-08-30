// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class TroubleCode extends PropertyValueObject {
    int occurences;
    String ID;
    String ecuID;
    String status;

    /**
     * @return Number of occurences.
     */
    public int getOccurences() {
        return occurences;
    }

    /**
     * @return ID bytes formatted in UTF-8.
     */
    public String getID() {
        return ID;
    }

    /**
     * @return ECU ID bytes formatted in UTF-8.
     */
    public String getEcuID() {
        return ecuID;
    }

    /**
     * @return Status bytes formatted in UTF-8.
     */
    public String getStatus() {
        return status;
    }

    public TroubleCode(int occurences, String ID, String ecuID, String status) {
        super(0);
        update(occurences, ID, ecuID, status);
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

    public void update(int occurences, String ID, String ecuID, String status) {
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