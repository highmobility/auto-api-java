// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class CheckControlMessage extends PropertyValueObject {
    int ID;
    int remainingMinutes;
    String text;
    String status;

    /**
     * @return Check Control Message identifier.
     */
    public int getID() {
        return ID;
    }

    /**
     * @return Remaining time of the message in minutes.
     */
    public int getRemainingMinutes() {
        return remainingMinutes;
    }

    /**
     * @return CCM text bytes formatted in UTF-8.
     */
    public String getText() {
        return text;
    }

    /**
     * @return CCM status bytes formatted in UTF-8.
     */
    public String getStatus() {
        return status;
    }

    public CheckControlMessage(int ID, int remainingMinutes, String text, String status) {
        super(0);
        update(ID, remainingMinutes, text, status);
    }

    public CheckControlMessage() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 10) throw new CommandParseException();

        int bytePosition = 0;
        ID = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;

        remainingMinutes = Property.getUnsignedInt(bytes, bytePosition, 4);
        bytePosition += 4;

        int textSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        text = Property.getString(value, bytePosition, textSize);
        bytePosition += textSize;

        int statusSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        status = Property.getString(value, bytePosition, statusSize);
    }

    public void update(int ID, int remainingMinutes, String text, String status) {
        this.ID = ID;
        this.remainingMinutes = remainingMinutes;
        this.text = text;
        this.status = status;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(ID, 2));
        bytePosition += 2;

        set(bytePosition, Property.intToBytes(remainingMinutes, 4));
        bytePosition += 4;

        set(bytePosition, Property.intToBytes(text.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(text));
        bytePosition += text.length();

        set(bytePosition, Property.intToBytes(status.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(status));
    }

    public void update(CheckControlMessage value) {
        update(value.ID, value.remainingMinutes, value.text, value.status);
    }

    @Override public int getLength() {
        return 2 + 4 + text.length() + 2 + status.length() + 2;
    }
}