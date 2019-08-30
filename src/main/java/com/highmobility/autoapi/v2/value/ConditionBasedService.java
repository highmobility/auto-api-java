// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class ConditionBasedService extends PropertyValueObject {
    int year;
    int month;
    int id;
    DueStatus dueStatus;
    String text;
    String description;

    /**
     * @return Value between 0 and 99, which corresponds to 2000-2099.
     */
    public int getYear() {
        return year;
    }

    /**
     * @return Value between 1 and 12.
     */
    public int getMonth() {
        return month;
    }

    /**
     * @return CBS identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * @return The due status.
     */
    public DueStatus getDueStatus() {
        return dueStatus;
    }

    /**
     * @return CBS text bytes formatted in UTF-8.
     */
    public String getText() {
        return text;
    }

    /**
     * @return Description bytes formatted in UTF-8.
     */
    public String getDescription() {
        return description;
    }

    public ConditionBasedService(int year, int month, int id, DueStatus dueStatus, String text, String description) {
        super(0);
        update(year, month, id, dueStatus, text, description);
    }

    public ConditionBasedService() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 9) throw new CommandParseException();

        int bytePosition = 0;
        year = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        month = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        id = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;

        dueStatus = DueStatus.fromByte(get(bytePosition));
        bytePosition += 1;

        int textSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        text = Property.getString(value, bytePosition, textSize);
        bytePosition += textSize;

        int descriptionSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        description = Property.getString(value, bytePosition, descriptionSize);
    }

    public void update(int year, int month, int id, DueStatus dueStatus, String text, String description) {
        this.year = year;
        this.month = month;
        this.id = id;
        this.dueStatus = dueStatus;
        this.text = text;
        this.description = description;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(year, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(month, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(id, 2));
        bytePosition += 2;

        set(bytePosition, dueStatus.getByte());
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(text.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(text));
        bytePosition += text.length();

        set(bytePosition, Property.intToBytes(description.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(description));
    }

    public void update(ConditionBasedService value) {
        update(value.year, value.month, value.id, value.dueStatus, value.text, value.description);
    }

    @Override public int getLength() {
        return 1 + 1 + 2 + 1 + text.length() + 2 + description.length() + 2;
    }

    public enum DueStatus {
        OK((byte)0x00),
        PENDING((byte)0x01),
        OVERDUE((byte)0x02);
    
        public static DueStatus fromByte(byte byteValue) throws CommandParseException {
            DueStatus[] values = DueStatus.values();
    
            for (int i = 0; i < values.length; i++) {
                DueStatus state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        DueStatus(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }
}