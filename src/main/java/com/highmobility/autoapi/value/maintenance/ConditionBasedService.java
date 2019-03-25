/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
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

package com.highmobility.autoapi.value.maintenance;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class ConditionBasedService extends PropertyValueObject {
    public static final byte IDENTIFIER = 0x0B;

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
     * @return The month.
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @return The identifier.
     */
    public Integer getIdentifier() {
        return id;
    }

    /**
     * @return The text.
     */
    public String getText() {
        return text;
    }

    /**
     * @return The due status.
     */
    public DueStatus getDueStatus() {
        return dueStatus;
    }

    /**
     * @return The CBS description.
     */
    public String getDescription() {
        return description;
    }

    public ConditionBasedService(Integer year, Integer month, int id, DueStatus dueStatus,
                                 String text, String description) {
        super(7 + text.length());
        update(year, month, id, dueStatus, text, description);
    }

    public ConditionBasedService() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 4) throw new CommandParseException();

        this.year = Property.getUnsignedInt(get(0)) + 2000;
        this.month = Property.getUnsignedInt(get(1));

        this.id = Property.getUnsignedInt(bytes, 2, 2);
        this.dueStatus = DueStatus.fromByte(get(4));

        int textPosition = 5;
        int textLength = Property.getUnsignedInt(bytes, textPosition, 2);
        textPosition += 2;
        this.text = Property.getString(bytes, textPosition, textLength);
        textPosition += textLength;
        textLength = Property.getUnsignedInt(bytes, textPosition, 2);
        textPosition += 2;
        this.description = Property.getString(bytes, textPosition, textLength);
    }

    public void update(Integer year, Integer month, int id, DueStatus dueStatus, String text,
                       String description) {
        this.year = year;
        this.month = month;
        this.id = id;
        this.dueStatus = dueStatus;
        this.text = text;
        this.description = description;

        bytes = new byte[7 + text.length() + 2 + description.length()];

        set(0, Property.intToBytes(year - 2000, 1));
        set(1, Property.intToBytes(month, 1));
        set(2, Property.intToBytes(id, 2));
        set(4, dueStatus.getByte());
        set(5, Property.intToBytes(text.length(), 2));
        int position = 7;
        set(7, Property.stringToBytes(text));
        position += text.length();
        set(position, Property.intToBytes(description.length(), 2));
        set(position + 2, Property.stringToBytes(description));
    }

    public void update(ConditionBasedService value) {
        update(value.year, value.month, value.id, value.dueStatus, value.text, value.description);
    }

    public enum DueStatus {
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

        public byte getByte() {
            return value;
        }
    }
}