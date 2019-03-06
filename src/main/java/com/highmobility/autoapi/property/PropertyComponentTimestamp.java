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

import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import java.util.Calendar;

public class PropertyComponentTimestamp extends PropertyComponent {
    private static final byte IDENTIFIER = 0x02;
    private Calendar timestamp;

    /**
     * @return The timestamp calendar.
     */
    public Calendar getCalendar() {
        return timestamp;
    }

    public PropertyComponentTimestamp(Bytes bytes) {
        super(bytes);
        // TODO: 2019-02-28
    }

    public PropertyComponentTimestamp(Calendar timestamp) {
        super(IDENTIFIER, 3 + PropertyComponentValue.CALENDAR_SIZE);
        ByteUtils.setBytes(bytes, Property.calendarToBytes(timestamp), 3);
        this.timestamp = timestamp;
    }
}

/**
 * This property holds a timestamp for a specific property of when it was recorded by the car.
 *
 * @return The timestamp calendar.
 * @return The property identifier this timestamp is for.
 * @return Additional data. Full property bytes if exists.
 * <p>
 * Create a property timestamp.
 * @param timestamp                   The timestamp.
 * @param timestampPropertyIdentifier The identifier of the property.
 * @param additionalData              Full property bytes to identify the property.
 * @return The timestamp calendar.
 * @return The property identifier this timestamp is for.
 * @return Additional data. Full property bytes if exists.
 * <p>
 * Create a property timestamp.
 * @param timestamp                   The timestamp.
 * @param timestampPropertyIdentifier The identifier of the property.
 * @param additionalData              Full property bytes to identify the property.
 * @return The timestamp calendar.
 * @return The property identifier this timestamp is for.
 * @return Additional data. Full property bytes if exists.
 * <p>
 * Create a property timestamp.
 * @param timestamp                   The timestamp.
 * @param timestampPropertyIdentifier The identifier of the property.
 * @param additionalData              Full property bytes to identify the property.
 * @return The timestamp calendar.
 * @return The property identifier this timestamp is for.
 * @return Additional data. Full property bytes if exists.
 * <p>
 * Create a property timestamp.
 * @param timestamp                   The timestamp.
 * @param timestampPropertyIdentifier The identifier of the property.
 * @param additionalData              Full property bytes to identify the property.
 *//*

public class PropertyTimestamp extends Property {
    public static final byte IDENTIFIER = (byte) 0xA4;
    public static final int LENGTH_WITHOUT_ADDITIONAL_DATA = 15;

    private Property<Calendar> timestamp;
    private byte timestampPropertyIdentifier;
    private Bytes additionalData;

    */
/**
 * @return The timestamp calendar.
 *//*

    public Calendar getCalendar() {
        return timestamp;
    }

    */
/**
 * @return The property identifier this timestamp is for.
 *//*

    public byte getTimestampPropertyIdentifier() {
        return timestampPropertyIdentifier;
    }

    */
/**
 * @return Additional data. Full property bytes if exists.
 *//*

    @Nullable public Bytes getAdditionalData() {
        return additionalData;
    }

    public PropertyTimestamp(byte[] bytes) {
        super(bytes);
        if (bytes.length < LENGTH_WITHOUT_ADDITIONAL_DATA) throw new IllegalArgumentException();
        timestamp = Property.getCalendar(bytes, 6);
        timestampPropertyIdentifier = bytes[14];
        additionalData = new Bytes(Arrays.copyOfRange(bytes, 15, bytes.length));
    }

    */
/**
 * Create a property timestamp.
 *
 * @param timestamp                   The timestamp.
 * @param timestampPropertyIdentifier The identifier of the property.
 * @param additionalData              Full property bytes to identify the property.
 *//*

    public PropertyTimestamp(Calendar timestamp, byte timestampPropertyIdentifier, @Nullable
            Bytes additionalData) {
        super(IDENTIFIER, 9 + (additionalData != null ? additionalData.getLength() : 0));

        ByteUtils.setBytes(bytes, Property.calendarToBytes(timestamp), 3);
        bytes[11] = timestampPropertyIdentifier;
        if (additionalData != null) ByteUtils.setBytes(bytes, additionalData.getByteArray(), 12);

        this.timestamp = timestamp;
        this.additionalData = additionalData;
        this.timestampPropertyIdentifier = timestampPropertyIdentifier;
    }

    public PropertyTimestamp(Calendar timestamp, Property property) {
        this(timestamp, property.getPropertyIdentifier(), property);
    }

    public PropertyTimestamp(Calendar timestamp) {
        this(timestamp, (byte) 0x00, null);
    }
}*/
