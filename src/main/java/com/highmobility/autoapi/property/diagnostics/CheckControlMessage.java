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

import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;

public class CheckControlMessage extends Property {
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
        super((byte) 0x00, 9 + text.length() + status.length());

        this.id = id;
        this.remainingTime = remainingTime;
        this.text = text;
        this.status = status;

        ByteUtils.setBytes(bytes, Property.intToBytes(id, 2), 3);
        ByteUtils.setBytes(bytes, Property.intToBytes(remainingTime, 4), 5);

        int textPosition = 9;
        int textLength = text.length();
        bytes[textPosition] = (byte) textLength;
        ByteUtils.setBytes(bytes, Property.intToBytes(textLength, 2), textPosition);
        textPosition += 2;
        ByteUtils.setBytes(bytes, Property.stringToBytes(text), textPosition);

        textPosition += textLength;
        textLength = status.length();
        bytes[textPosition] = (byte) textLength;
        textPosition += 1;
        ByteUtils.setBytes(bytes, Property.stringToBytes(status), textPosition);
    }

    CheckControlMessage(byte[] bytes) {
        super(bytes);

        this.id = Property.getUnsignedInt(bytes, 3, 2);
        this.remainingTime = Property.getUnsignedInt(bytes, 5, 4);

        int textPosition = 9;
        int textLength = Property.getUnsignedInt(bytes, textPosition, 2);
        textPosition+=2;
        this.text = Property.getString(bytes, textPosition, textLength);

        textPosition += textLength;
        textLength = Property.getUnsignedInt(bytes, textPosition, 1);
        textPosition++;
        this.status = Property.getString(bytes, textPosition, textLength);
    }
}
