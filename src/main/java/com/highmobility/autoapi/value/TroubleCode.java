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

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class TroubleCode extends PropertyValueObject {
    Integer occurences;
    String ID;
    String ecuID;
    String status;

    /**
     * @return Number of occurences.
     */
    public Integer getOccurences() {
        return occurences;
    }

    /**
     * @return Identifier.
     */
    public String getID() {
        return ID;
    }

    /**
     * @return Electronic Control Unit identifier.
     */
    public String getEcuID() {
        return ecuID;
    }

    /**
     * @return Status.
     */
    public String getStatus() {
        return status;
    }

    public TroubleCode(Integer occurences, String ID, String ecuID, String status) {
        super(0);
        update(occurences, ID, ecuID, status);
    }

    public TroubleCode(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public TroubleCode() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 7) throw new CommandParseException();

        int bytePosition = 0;
        occurences = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        int IDSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        ID = Property.getString(value, bytePosition, IDSize);
        bytePosition += IDSize;

        int ecuIDSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        ecuID = Property.getString(value, bytePosition, ecuIDSize);
        bytePosition += ecuIDSize;

        int statusSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        status = Property.getString(value, bytePosition, statusSize);
    }

    public void update(Integer occurences, String ID, String ecuID, String status) {
        this.occurences = occurences;
        this.ID = ID;
        this.ecuID = ecuID;
        this.status = status;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(occurences, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(ID.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(ID));
        bytePosition += ID.length();

        set(bytePosition, Property.intToBytes(ecuID.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(ecuID));
        bytePosition += ecuID.length();

        set(bytePosition, Property.intToBytes(status.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(status));
    }

    public void update(TroubleCode value) {
        update(value.occurences, value.ID, value.ecuID, value.status);
    }

    @Override public int getLength() {
        return 1 + ID.length() + 2 + ecuID.length() + 2 + status.length() + 2;
    }
}