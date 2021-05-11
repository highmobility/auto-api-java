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
import com.highmobility.autoapi.value.Failure;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import javax.annotation.Nullable;

import static com.highmobility.utils.ByteUtils.hexFromByte;

public class PropertyComponentFailure extends PropertyComponent {
    private static final byte IDENTIFIER = 0x03;

    Failure failure;

    /**
     * @return The failure object
     */
    public Failure getFailure() {
        return failure;
    }

    /**
     * @return The failure reason.
     * @deprecated use {@link #getFailure()} instead
     */
    // TODO: 11/5/21 remove Reason deprecations after may 2022
    @Deprecated
    public Reason getFailureReason() throws CommandParseException {
        return Reason.fromByte(failure.getReason().getByte());
    }

    /**
     * @return The failure description.
     */
    @Nullable
    public String getFailureDescription() {
        return failure.getDescription();
    }

    public PropertyComponentFailure(Bytes bytes) throws CommandParseException {
        super(bytes);
        failure = new Failure(getRange(3, size()));
    }

    /**
     * @param failureReason The failure reason.
     * @param description   The failure description.
     * @deprecated use {@link #PropertyComponentFailure(Failure)} instead
     */
    public PropertyComponentFailure(Reason failureReason, @Nullable String description) throws CommandParseException {
        super(IDENTIFIER, 3 + description.length());

        bytes[3] = failureReason.getByte();
        if (description != null) {
            set(4, Property.intToBytes(description.length(), 2));
            ByteUtils.setBytes(bytes, description.getBytes(), 6);
        }

        this.valueBytes = getRange(3, size());
        this.failure = new Failure(Failure.Reason.fromByte(failureReason.getByte()), description);
    }

    PropertyComponentFailure(Failure failure) {
        super(IDENTIFIER, failure);
        this.failure = failure;
    }

    /**
     * @deprecated use {@link Failure#getReason()} instead
     */
    @Deprecated
    // TODO: 11/5/21 remove Reason deprecations after may 2022
    public enum Reason {
        /**
         * Property rate limit has been exceeded
         */
        RATE_LIMIT((byte) 0x00),
        /**
         * Failed to retrieve property in time
         */
        EXECUTION_TIMEOUT((byte) 0x01),
        /**
         * Could not interpret property
         */
        FORMAT_ERROR((byte) 0x02),
        /**
         * Insufficient permissions to get the property
         */
        UNAUTHORISED((byte) 0x03),
        /**
         * Property failed for unknown reason
         */
        UNKNOWN((byte) 0x04),
        /**
         * Property is being refreshed
         */
        PENDING((byte) 0x05),
        /**
         * Internal OEM error
         */
        INTERNAL_OEM_ERROR((byte) 0x06);

        public static Reason fromByte(byte byteValue) throws CommandParseException {
            Reason[] values = Reason.values();

            for (int i = 0; i < values.length; i++) {
                Reason value1 = values[i];
                if (value1.getByte() == byteValue) {
                    return value1;
                }
            }

            throw new CommandParseException("Failure.Reason does not contain " + hexFromByte(byteValue));
        }

        private final byte value;

        Reason(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}