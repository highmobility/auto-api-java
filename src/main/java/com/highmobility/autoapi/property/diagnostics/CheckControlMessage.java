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

package com.highmobility.autoapi.property.diagnostics;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyValue;
import com.highmobility.utils.ByteUtils;

import java.util.Calendar;

import javax.annotation.Nullable;

public class CheckControlMessage extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public CheckControlMessage(byte identifier) {
        super(identifier);
    }

    public CheckControlMessage(@Nullable Value value, @Nullable Calendar timestamp,
                               @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public CheckControlMessage(Value value) {
        this((byte) 0x00, value);
    }

    public CheckControlMessage(byte identifier, Value value) {
        super(identifier, value);

        this.value = value;

        if (value != null) {
            this.value = value;

            ByteUtils.setBytes(bytes, Property.intToBytes(value.id, 2), 3);
            ByteUtils.setBytes(bytes, Property.intToBytes(value.remainingTime, 4), 5);

            int textPosition = 9;
            int textLength = value.text.length();
            bytes[textPosition] = (byte) textLength;
            ByteUtils.setBytes(bytes, Property.intToBytes(textLength, 2), textPosition);
            textPosition += 2;
            ByteUtils.setBytes(bytes, Property.stringToBytes(value.text), textPosition);

            textPosition += textLength;
            textLength = value.status.length();
            bytes[textPosition] = (byte) textLength;
            textPosition += 1;
            ByteUtils.setBytes(bytes, Property.stringToBytes(value.status), textPosition);
        }
    }

    public CheckControlMessage(int id, int remainingTime, String text, String status) {
        this(new Value(id, remainingTime, text, status));
    }

    public CheckControlMessage(byte identifier, int id, int remainingTime, String text,
                               String status) {
        this(identifier, new Value(id, remainingTime, text, status));
    }

    public CheckControlMessage(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 5) value = new Value(p);
        return this;
    }

    public static class Value implements PropertyValue {
        int id;
        int remainingTime;
        String text;
        String status;

        public int getId() {
            return id;
        }

        /**
         * @return The message remaining time in minutes.
         */
        public int getRemainingTime() {
            return remainingTime;
        }

        /**
         * @return The message text.
         */
        public String getText() {
            return text;
        }

        /**
         * @return The message status.
         */
        public String getStatus() {
            return status;
        }

        public Value(int id, int remainingTime, String text, String status) {
            this.id = id;
            this.remainingTime = remainingTime;
            this.text = text;
            this.status = status;
        }

        public Value(Property property) {
            this.id = Property.getUnsignedInt(property, 3, 2);
            this.remainingTime = Property.getUnsignedInt(property, 5, 4);

            int textPosition = 9;
            int textLength = Property.getUnsignedInt(property, textPosition, 2);
            textPosition += 2;
            this.text = Property.getString(property, textPosition, textLength);

            textPosition += textLength;
            textLength = Property.getUnsignedInt(property, textPosition, 1);
            textPosition++;
            this.status = Property.getString(property, textPosition, textLength);
        }

        @Override public int getLength() {
            return 9 + text.length() + status.length();
        }
    }
}
