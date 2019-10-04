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
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

public class ConditionBasedService extends PropertyValueObject {
    Integer year;
    Integer month;
    Integer id;
    DueStatus dueStatus;
    String text;
    String description;

    /**
     * @return The year.
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @return Value between 1 and 12.
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @return CBS identifier.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return The due status.
     */
    public DueStatus getDueStatus() {
        return dueStatus;
    }

    /**
     * @return CBS text.
     */
    public String getText() {
        return text;
    }

    /**
     * @return Description.
     */
    public String getDescription() {
        return description;
    }

    public ConditionBasedService(Integer year, Integer month, Integer id, DueStatus dueStatus, String text, String description) {
        super(0);
        update(year, month, id, dueStatus, text, description);
    }

    public ConditionBasedService(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public ConditionBasedService() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 10) throw new CommandParseException();

        int bytePosition = 0;
        year = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;

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

    public void update(Integer year, Integer month, Integer id, DueStatus dueStatus, String text, String description) {
        this.year = year;
        this.month = month;
        this.id = id;
        this.dueStatus = dueStatus;
        this.text = text;
        this.description = description;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(year, 2));
        bytePosition += 2;

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
        return 2 + 1 + 2 + 1 + text.length() + 2 + description.length() + 2;
    }

    public enum DueStatus implements ByteEnum {
        OK((byte) 0x00),
        PENDING((byte) 0x01),
        OVERDUE((byte) 0x02);
    
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
    
        @Override public byte getByte() {
            return value;
        }
    }
}