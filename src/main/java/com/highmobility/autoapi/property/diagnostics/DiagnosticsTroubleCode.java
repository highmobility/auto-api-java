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
import com.highmobility.autoapi.property.PropertyTimestamp;
import com.highmobility.utils.ByteUtils;

import javax.annotation.Nullable;

public class DiagnosticsTroubleCode extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public DiagnosticsTroubleCode(int numberOfOccurences, String id, String ecuId, String status) {
        super((byte) 0x00, 4 + id.length() + ecuId.length() + status.length());

        value = new Value(numberOfOccurences, id, ecuId, status);

        bytes[3] = (byte) numberOfOccurences;

        int textPosition = 4;
        int textLength = id.length();
        bytes[textPosition] = (byte) textLength;
        textPosition++;
        ByteUtils.setBytes(bytes, Property.stringToBytes(id), textPosition);

        textPosition += textLength;
        textLength = ecuId.length();
        bytes[textPosition] = (byte) textLength;
        textPosition += 1;
        ByteUtils.setBytes(bytes, Property.stringToBytes(ecuId), textPosition);

        textPosition += textLength;
        textLength = status.length();
        bytes[textPosition] = (byte) textLength;
        textPosition += 1;
        ByteUtils.setBytes(bytes, Property.stringToBytes(status), textPosition);
    }

    public DiagnosticsTroubleCode(Property p) throws CommandParseException {
        super(p);
        update(p, null, null, false);
    }

    @Override
    public boolean update(Property p, PropertyFailure failure, PropertyTimestamp timestamp,
                          boolean propertyInArray) throws CommandParseException {
        if (p != null) value = new Value(p);
        return super.update(p, failure, timestamp, propertyInArray);
    }

    public static class Value {
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
            if (bytes.getLength() < 6) throw new CommandParseException();
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
    }
}
