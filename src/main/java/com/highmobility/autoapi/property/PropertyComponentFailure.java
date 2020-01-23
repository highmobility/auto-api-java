/*
 * The MIT License
 *
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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