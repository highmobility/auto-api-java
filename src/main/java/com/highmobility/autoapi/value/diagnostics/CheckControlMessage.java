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

public class CheckControlMessage extends PropertyValueObject {
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

    public CheckControlMessage(int id, int remainingTime, String text, String status) {
        super(16);
        update(id, remainingTime, text, status);
    }

    public CheckControlMessage() {
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (value.getLength() < 5) throw new CommandParseException();

        this.id = Property.getUnsignedInt(value, 0, 2);
        this.remainingTime = Property.getUnsignedInt(value, 2, 4);

        int textPosition = 6;
        int textLength = Property.getUnsignedInt(value, textPosition, 2);

        textPosition += 2;
        this.text = Property.getString(value, textPosition, textLength);

        textPosition += textLength;
        textLength = Property.getUnsignedInt(value, textPosition, 1);
        textPosition++;
        this.status = Property.getString(value, textPosition, textLength);
    }

    public void update(int id, int remainingTime, String text, String status) {
        this.id = id;
        this.remainingTime = remainingTime;
        this.text = text;
        this.status = status;

        this.bytes = new byte[getLength()];

        set(0, Property.intToBytes(id, 2));
        set(2, Property.intToBytes(remainingTime, 4));

        int textPosition = 6;
        int textLength = text.length();
        set(textPosition, Property.intToBytes(textLength, 2));
        textPosition += 2;
        set(textPosition, Property.stringToBytes(text));
        textPosition += textLength;
        textLength = status.length();
        set(textPosition, (byte) textLength);
        textPosition++;
        set(textPosition, Property.stringToBytes(status));
    }

    public void update(CheckControlMessage checkControlMessage) {
        update(checkControlMessage.id, checkControlMessage.remainingTime,
                checkControlMessage.text, checkControlMessage.status);
    }

    @Override public int getLength() {
        return 9 + text.length() + status.length();
    }
}