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
package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.value.measurement.Frequency;
import com.highmobility.value.Bytes;

import static com.highmobility.utils.ByteUtils.hexFromByte;

public class Availability extends PropertyValueObject {
    public static final int SIZE = 12;

    UpdateRate updateRate;
    Frequency rateLimit;
    AppliesPer appliesPer;

    /**
     * @return The update rate.
     */
    public UpdateRate getUpdateRate() {
        return updateRate;
    }

    /**
     * @return Frequency denoting the rate limit.
     */
    public Frequency getRateLimit() {
        return rateLimit;
    }

    /**
     * @return Rate limit applies per.
     */
    public AppliesPer getAppliesPer() {
        return appliesPer;
    }

    public Availability(UpdateRate updateRate, Frequency rateLimit, AppliesPer appliesPer) {
        super(0);

        this.updateRate = updateRate;
        this.rateLimit = rateLimit;
        this.appliesPer = appliesPer;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, updateRate.getByte());
        bytePosition += 1;

        set(bytePosition, rateLimit);
        bytePosition += rateLimit.getLength();

        set(bytePosition, appliesPer.getByte());
    }

    public Availability(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 12) throw new CommandParseException();

        int bytePosition = 0;
        updateRate = UpdateRate.fromByte(get(bytePosition));
        bytePosition += 1;

        int rateLimitSize = Frequency.SIZE;
        rateLimit = new Frequency(getRange(bytePosition, bytePosition + rateLimitSize));
        bytePosition += rateLimitSize;

        appliesPer = AppliesPer.fromByte(get(bytePosition));
    }

    @Override public int getLength() {
        return 1 + 10 + 1;
    }

    public enum UpdateRate implements ByteEnum {
        /**
         * The property data is updated with high frequency during a trip.
         */
        TRIP_HIGH((byte) 0x00),
        /**
         * The property data is updated regularly during a trip.
         */
        TRIP((byte) 0x01),
        /**
         * The property data is updated at the start and end of each trip.
         */
        TRIP_START_END((byte) 0x02),
        /**
         * The property data is updated by the end of each trip.
         */
        TRIP_END((byte) 0x03),
        /**
         * The property data is updated sporadically.
         */
        UNKNOWN((byte) 0x04),
        /**
         * The property is not available for the vehicle.
         */
        NOT_AVAILABLE((byte) 0x05),
        /**
         * The property data is update as soon as the vehicle updates it internally.
         */
        ON_CHANGE((byte) 0x06);
    
        public static UpdateRate fromByte(byte byteValue) throws CommandParseException {
            UpdateRate[] values = UpdateRate.values();
    
            for (int i = 0; i < values.length; i++) {
                UpdateRate state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum UpdateRate does not contain " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        UpdateRate(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum AppliesPer implements ByteEnum {
        APP((byte) 0x00),
        VEHICLE((byte) 0x01);
    
        public static AppliesPer fromByte(byte byteValue) throws CommandParseException {
            AppliesPer[] values = AppliesPer.values();
    
            for (int i = 0; i < values.length; i++) {
                AppliesPer state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum AppliesPer does not contain " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        AppliesPer(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}