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
import com.highmobility.value.Bytes;

public class CheckControlMessage extends PropertyValueObject {
    Integer ID;
    Integer remainingMinutes;
    String text;
    String status;

    /**
     * @return Check Control Message identifier.
     */
    public Integer getID() {
        return ID;
    }

    /**
     * @return Remaining time of the message in minutes.
     */
    public Integer getRemainingMinutes() {
        return remainingMinutes;
    }

    /**
     * @return CCM text.
     */
    public String getText() {
        return text;
    }

    /**
     * @return CCM status.
     */
    public String getStatus() {
        return status;
    }

    public CheckControlMessage(Integer ID, Integer remainingMinutes, String text, String status) {
        super(0);
        update(ID, remainingMinutes, text, status);
    }

    public CheckControlMessage(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public CheckControlMessage() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 10) throw new CommandParseException();

        int bytePosition = 0;
        ID = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;

        remainingMinutes = Property.getUnsignedInt(bytes, bytePosition, 4);
        bytePosition += 4;

        int textSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        text = Property.getString(value, bytePosition, textSize);
        bytePosition += textSize;

        int statusSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        status = Property.getString(value, bytePosition, statusSize);
    }

    public void update(Integer ID, Integer remainingMinutes, String text, String status) {
        this.ID = ID;
        this.remainingMinutes = remainingMinutes;
        this.text = text;
        this.status = status;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(ID, 2));
        bytePosition += 2;

        set(bytePosition, Property.intToBytes(remainingMinutes, 4));
        bytePosition += 4;

        set(bytePosition, Property.intToBytes(text.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(text));
        bytePosition += text.length();

        set(bytePosition, Property.intToBytes(status.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(status));
    }

    public void update(CheckControlMessage value) {
        update(value.ID, value.remainingMinutes, value.text, value.status);
    }

    @Override public int getLength() {
        return 2 + 4 + text.length() + 2 + status.length() + 2;
    }
}