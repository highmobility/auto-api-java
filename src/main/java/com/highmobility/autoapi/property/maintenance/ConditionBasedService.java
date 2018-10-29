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
import com.highmobility.utils.ByteUtils;

import java.time.LocalDate;

public class ConditionBasedService extends Property {
    public static final byte IDENTIFIER = 0x0B;

    LocalDate date;
    Integer id;
    DueStatus dueStatus;
    String text;

    /**
     * @return
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return
     */
    public Integer getIdentifier() {
        return id;
    }

    /**
     * @return
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

    public ConditionBasedService(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 10) throw new CommandParseException();

        int year = bytes[3] + 2000;
        int month = bytes[4];
        this.date = LocalDate.of(year, month, 1);
        this.id = Property.getUnsignedInt(bytes, 5, 2);
        this.dueStatus = DueStatus.fromByte(bytes[7]);
        this.text = Property.getString(bytes, 10, bytes.length - 10);
    }

    public ConditionBasedService(LocalDate date, int id, DueStatus dueStatus, String text) {
        super(IDENTIFIER, 7 + text.length());
        bytes[3] = (byte) (date.getYear() - 2000);
        bytes[4] = (byte) date.getMonth().getValue();
        ByteUtils.setBytes(bytes, Property.intToBytes(id, 2), 5);
        bytes[7] = dueStatus.getByte();
        ByteUtils.setBytes(bytes, Property.intToBytes(text.length(), 2), 8);
        ByteUtils.setBytes(bytes, Property.stringToBytes(text), 10);
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