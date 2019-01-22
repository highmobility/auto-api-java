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

public class DiagnosticsTroubleCode extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public DiagnosticsTroubleCode(byte identifier) {
        super(identifier);
    }

    public DiagnosticsTroubleCode(@Nullable Value value, @Nullable Calendar timestamp,
                                  @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public DiagnosticsTroubleCode(Value value) {
        this((byte) 0x00, value);
    }

    public DiagnosticsTroubleCode(byte identifier, Value value) {
        super(identifier, value == null ? 0 : value.getLength());

        this.value = value;
        if (value != null) {
            bytes[3] = (byte) value.numberOfOccurences;

            int textPosition = 4;
            int textLength = value.id.length();
            bytes[textPosition] = (byte) textLength;
            textPosition++;
            ByteUtils.setBytes(bytes, Property.stringToBytes(value.id), textPosition);

            textPosition += textLength;
            textLength = value.ecuId.length();
            bytes[textPosition] = (byte) textLength;
            textPosition += 1;
            ByteUtils.setBytes(bytes, Property.stringToBytes(value.ecuId), textPosition);

            textPosition += textLength;
            textLength = value.status.length();
            bytes[textPosition] = (byte) textLength;
            textPosition += 1;
            ByteUtils.setBytes(bytes, Property.stringToBytes(value.status), textPosition);
        }
    }

    public DiagnosticsTroubleCode(int numberOfOccurences, String id, String ecuId, String status) {
        this((byte) 0x00, numberOfOccurences, id, ecuId, status);
    }

    public DiagnosticsTroubleCode(byte identifier, int numberOfOccurences, String id,
                                  String ecuId, String status) {
        this(identifier, new Value(numberOfOccurences, id, ecuId, status));
    }

    public DiagnosticsTroubleCode(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= 4) value = new Value(p);
        return this;
    }

    public static class Value implements PropertyValue {
        int numberOfOccurences;
        String id;
        String ecuId;
        String status;

        /**
         * @return The number of occurences.
         */
        public int getNumberOfOccurences() {
            return numberOfOccurences;
        }

        public String getId() {
            return id;
        }

        public String getEcuId() {
            return ecuId;
        }

        public String getStatus() {
            return status;
        }

        public Value(int numberOfOccurences, String id, String ecuId,
                     String status) {
            this.numberOfOccurences = numberOfOccurences;
            this.id = id;
            this.ecuId = ecuId;
            this.status = status;
        }

        public Value(Property bytes) throws CommandParseException {
            this.numberOfOccurences = bytes.get(3);

            int textPosition = 4;
            int textLength = Property.getUnsignedInt(bytes, textPosition, 1);
            textPosition++;
            this.id = Property.getString(bytes, textPosition, textLength);

            textPosition += textLength;
            textLength = Property.getUnsignedInt(bytes, textPosition, 1);
            textPosition++;
            this.ecuId = Property.getString(bytes, textPosition, textLength);

            textPosition += textLength;
            textLength = Property.getUnsignedInt(bytes, textPosition, 1);
            textPosition++;
            this.status = Property.getString(bytes, textPosition, textLength);
        }

        @Override public int getLength() {
            return 4 + id.length() + ecuId.length() + status.length();
        }
    }
}
