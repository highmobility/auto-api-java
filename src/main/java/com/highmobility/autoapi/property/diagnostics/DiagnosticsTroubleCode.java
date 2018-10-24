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
import com.highmobility.utils.ByteUtils;

public class DiagnosticsTroubleCode extends Property {
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
        super((byte) 0x00, 3 + id.length() + ecuId.length() + status.length());

        this.numberOfOccurences = numberOfOccurences;
        this.id = id;
        this.ecuId = ecuId;
        this.status = status;

        bytes[3] = (byte) numberOfOccurences;
        bytes[4] = (byte) id.length();
        bytes[5] = (byte) ecuId.length();

        ByteUtils.setBytes(bytes, Property.stringToBytes(id), 6);
        ByteUtils.setBytes(bytes, Property.stringToBytes(ecuId), 6 + id.length());
        ByteUtils.setBytes(bytes, Property.stringToBytes(status), 6 + id.length() + ecuId.length());
    }

    public DiagnosticsTroubleCode(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 6) throw new CommandParseException();
        this.numberOfOccurences = bytes[3];
        int idLength = bytes[4];
        int ecuIdLength = bytes[5];
        id = Property.getString(bytes, 6, idLength);
        ecuId = Property.getString(bytes, 6 + idLength, ecuIdLength);
        int statusPosition = 6 + idLength + ecuIdLength;
        status = Property.getString(bytes, statusPosition, bytes.length - statusPosition);
    }
}
