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

package com.highmobility.autoapi.value.diagnostics;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class DiagnosticsTroubleCode extends PropertyValueObject {
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

    public DiagnosticsTroubleCode(int numberOfOccurences, String id, String ecuId, String status) {
        super(0);
        update(numberOfOccurences, id, ecuId, status);
    }

    public DiagnosticsTroubleCode() {
        super(0);
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 5) throw new CommandParseException();

        this.numberOfOccurences = get(0);

        int textPosition = 1;
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

    public void update(int numberOfOccurences, String id, String ecuId, String status) {
        this.numberOfOccurences = numberOfOccurences;
        this.id = id;
        this.ecuId = ecuId;
        this.status = status;
        this.bytes = new byte[getLength()];

        set(0, (byte) numberOfOccurences);

        int textPosition = 1;
        int textLength = id.length();
        bytes[textPosition] = (byte) textLength;
        textPosition++;
        set(textPosition, Property.stringToBytes(id));

        textPosition += textLength;
        textLength = ecuId.length();
        bytes[textPosition] = (byte) textLength;
        textPosition += 1;
        set(textPosition, Property.stringToBytes(ecuId));

        textPosition += textLength;
        textLength = status.length();
        bytes[textPosition] = (byte) textLength;
        textPosition += 1;
        set(textPosition, Property.stringToBytes(status));
    }

    public void update(DiagnosticsTroubleCode value) {
        update(value.numberOfOccurences, value.id, value.ecuId, value.status);
    }

    @Override public int getLength() {
        return 4 + id.length() + ecuId.length() + status.length();
    }
}
