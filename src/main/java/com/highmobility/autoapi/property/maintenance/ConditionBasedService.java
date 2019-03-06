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

package com.highmobility.autoapi.property.maintenance;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.utils.ByteUtils;

import java.time.LocalDate;

public class ConditionBasedService extends PropertyValueObject {
    public static final byte IDENTIFIER = 0x0B;

    LocalDate date;
    Integer id;
    DueStatus dueStatus;
    String text;
    String description;

    /**
     * @return The date.
     */
    public LocalDate getDate() {
        return date;
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

    public ConditionBasedService(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 10) throw new CommandParseException();

        int year = bytes[6] + 2000;
        int month = bytes[7];
        this.date = LocalDate.of(year, month, 1);
        this.id = Property.getUnsignedInt(bytes, 8, 2);
        this.dueStatus = DueStatus.fromByte(bytes[10]);

        int textPosition = 11;
        int textLength = Property.getUnsignedInt(bytes, textPosition, 2);
        textPosition += 2;
        this.text = Property.getString(bytes, textPosition, textLength);
        textPosition += textLength;
        textLength = Property.getUnsignedInt(bytes, textPosition, 2);
        textPosition += 2;
        this.description = Property.getString(bytes, textPosition, textLength);
    }

    ConditionBasedService(LocalDate date, int id, DueStatus dueStatus, String text) {
        // TBODO:
        super(IDENTIFIER, 7 + text.length());
        bytes[6] = (byte) (date.getYear() - 2000);
        bytes[7] = (byte) date.getMonth().getValue();
        ByteUtils.setBytes(bytes, Property.intToBytes(id, 2), 8);
        bytes[10] = dueStatus.getByte();
        ByteUtils.setBytes(bytes, Property.intToBytes(text.length(), 2), 11);
        ByteUtils.setBytes(bytes, Property.stringToBytes(text), 13);
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