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

import javax.annotation.Nullable;

public class PropertyComponentFailure extends PropertyComponent {
    private static final byte IDENTIFIER = 0x03;

    Reason failureReason;
    String description;

    public PropertyComponentFailure(Bytes bytes) throws CommandParseException {
        super(bytes);
        failureReason = Reason.fromByte(get(3));
        int descriptionLength = Property.getUnsignedInt(this, 4, 1);
        description = Property.getString(this, 5, descriptionLength);
    }

    /**
     * @return The failure reason.
     */
    public Reason getFailureReason() {
        return failureReason;
    }

    /**
     * @return The failure description.
     */

    @Nullable public String getFailureDescription() {
        return description;
    }

    /**
     * @param failureReason The failure reason.
     * @param description   The failure description.
     */

    public PropertyComponentFailure(Reason failureReason, @Nullable String description) {
        super(IDENTIFIER, 2 + description.length());

        bytes[3] = failureReason.getByte();
        if (description != null) {
            bytes[4] = (byte) description.length();
            ByteUtils.setBytes(bytes, description.getBytes(), 5);
        }

        this.failureReason = failureReason;
        this.description = description;
    }

    public enum Reason {
        RATE_LIMIT((byte) 0x00),        // Property rate limit has been exceeded
        EXECUTION_TIMEOUT((byte) 0x01), // Failed to retrieve property in time
        FORMAT_ERROR((byte) 0x02),      // Could not interpret property
        UNAUTHORISED((byte) 0x03),      // Insufficient permissions to get the property
        UNKNOWN((byte) 0x04),           // Property failed for unknown reason
        PENDING((byte) 0x05);           // Property is being refreshed

        public static Reason fromByte(byte value) throws CommandParseException {
            Reason[] values = Reason.values();

            for (int i = 0; i < values.length; i++) {
                Reason value1 = values[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Reason(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}

/**
 * This property holds a timestamp for a specific property of when it was recorded by the car.
 *
 * @return The identifier of the failed property.
 * @return The failure reason.
 * @return The failure description.
 * @param identifier    The identifier of the failed property.
 * @param failureReason The failure reason.
 * @param description   The failure description.
 * @param failureReason The failure reason.
 * @param description   The failure description.
 * @return The identifier of the failed property.
 * @return The failure reason.
 * @return The failure description.
 * @param identifier    The identifier of the failed property.
 * @param failureReason The failure reason.
 * @param description   The failure description.
 * @param failureReason The failure reason.
 * @param description   The failure description.
 * @return The identifier of the failed property.
 * @return The failure reason.
 * @return The failure description.
 * @param identifier    The identifier of the failed property.
 * @param failureReason The failure reason.
 * @param description   The failure description.
 * @param failureReason The failure reason.
 * @param description   The failure description.
 * @return The identifier of the failed property.
 * @return The failure reason.
 * @return The failure description.
 * @return The identifier of the failed property.
 * @return The failure reason.
 * @return The failure description.
 * @return The identifier of the failed property.
 * @return The failure reason.
 * @return The failure description.
 * @return The identifier of the failed property.
 * @return The failure reason.
 * @return The failure description.
 * @return The identifier of the failed property.
 * @return The failure reason.
 * @return The failure description.
 * @return The identifier of the failed property.
 * @return The failure reason.
 * @return The failure description.
 *//*

public class PropertyFailure extends Property {
    public static final byte IDENTIFIER = (byte) 0xA5;

    byte failedPropertyIdentifier;
    String description;
    Reason reason;

    */
/**
 * @return The identifier of the failed property.
 *//*

    public byte getFailedPropertyIdentifier() {
        return failedPropertyIdentifier;
    }

    */
/**
 * @return The failure reason.
 *//*

    public Reason getFailureReason() {
        return reason;
    }

    */
/**
 * @return The failure description.
 *//*

    @Nullable public String getFailureDescription() {
        return description;
    }

    // should be internal
    public PropertyFailure update(Property parentProperty) {
        failedPropertyIdentifier = parentProperty.getPropertyIdentifier();
        bytes[3] = failedPropertyIdentifier;
        return this;
    }

    public PropertyFailure(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 6) throw new CommandParseException();

        failedPropertyIdentifier = bytes[6];
        reason = Reason.fromByte(bytes[7]);
        int descriptionLength = Property.getUnsignedInt(bytes, 8, 1);
        if (descriptionLength > 0) {
            description = Property.getString(bytes, 9, descriptionLength);
        }
    }


/**
 * @param failureReason The failure reason.
 * @param description   The failure description.
 *//*

    public PropertyFailure(Reason failureReason, @Nullable String description) {
        this((byte) 0x00, failureReason, description);
    }

    public enum Reason {
        RATE_LIMIT((byte) 0x00),        // Property rate limit has been exceeded
        EXECUTION_TIMEOUT((byte) 0x01), // Failed to retrieve property in time
        FORMAT_ERROR((byte) 0x02),      // Could not interpret property
        UNAUTHORISED((byte) 0x03),      // Insufficient permissions to get the property
        UNKNOWN((byte) 0x04),           // Property failed for unknown reason
        PENDING((byte) 0x05);           // Property is being refreshed

        public static Reason fromByte(byte value) throws CommandParseException {
            Reason[] values = Reason.values();

            for (int i = 0; i < values.length; i++) {
                Reason value1 = values[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Reason(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}*/
