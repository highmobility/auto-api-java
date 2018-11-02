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

package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import java.util.Arrays;
import java.util.Calendar;

import javax.annotation.Nullable;

/**
 * This property holds a timestamp for a specific property of when it was recorded by the car.
 */
public class PropertyTimestamp extends Property {
    public static final byte IDENTIFIER = (byte) 0xA4;

    private Calendar timestamp;
    private byte timestampPropertyIdentifier;
    private Bytes additionalData;

    /**
     * @return The property timestamp.
     */
    public Calendar getTimestamp() {
        return timestamp;
    }

    /**
     * @return The property identifier this timestamp is for.
     */
    public byte getTimestampPropertyIdentifier() {
        return timestampPropertyIdentifier;
    }

    /**
     * @return Additional data.
     */
    @Nullable public Bytes getAdditionalData() {
        return additionalData;
    }

    public PropertyTimestamp(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 12) throw new CommandParseException();
        timestamp = Property.getCalendar(bytes, 3);
        timestampPropertyIdentifier = bytes[11];
        if (bytes.length >= 12) {
            additionalData = new Bytes(Arrays.copyOfRange(bytes, 12, bytes.length));
        }
    }

    public PropertyTimestamp(Calendar timestamp, byte timestampPropertyIdentifier, @Nullable
            Bytes additionalData) {
        super(IDENTIFIER, 9 + (additionalData != null ? additionalData.getLength() : 0));
        ByteUtils.setBytes(bytes, Property.calendarToBytes(timestamp), 3);
        bytes[11] = timestampPropertyIdentifier;
        if (additionalData != null) ByteUtils.setBytes(bytes, additionalData.getByteArray(), 12);
    }
}