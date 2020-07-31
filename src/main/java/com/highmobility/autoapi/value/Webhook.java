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
import com.highmobility.value.Bytes;

public class Webhook extends PropertyValueObject {
    public static final int SIZE = 2;

    Available available;
    Event event;

    /**
     * @return If the specified webhook is available..
     */
    public Available getAvailable() {
        return available;
    }

    /**
     * @return The event.
     */
    public Event getEvent() {
        return event;
    }

    public Webhook(Available available, Event event) {
        super(0);

        this.available = available;
        this.event = event;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, available.getByte());
        bytePosition += 1;

        set(bytePosition, event.getByte());
    }

    public Webhook(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        available = Available.fromByte(get(bytePosition));
        bytePosition += 1;

        event = Event.fromByte(get(bytePosition));
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum Available implements ByteEnum {
        UNAVAILABLE((byte) 0x00),
        AVAILABLE((byte) 0x01);
    
        public static Available fromByte(byte byteValue) throws CommandParseException {
            Available[] values = Available.values();
    
            for (int i = 0; i < values.length; i++) {
                Available state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Available(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum Event implements ByteEnum {
        /**
         * Sent every time when the webhook is configured or changed.
         */
        PING((byte) 0x00),
        /**
         * Sent every time a vehicle starts a trip.
         */
        TRIP_STARTED((byte) 0x01),
        /**
         * Sent when a vehicle ends a trip.
         */
        TRIP_ENDED((byte) 0x02),
        /**
         * Sent when the vehicle location changes.
         */
        VEHICLE_LOCATION_CHANGED((byte) 0x03),
        /**
         * Sent when the authorization status changes.
         */
        AUTHORIZATION_CHANGED((byte) 0x04),
        /**
         * Sent when the tire pressure changed to low or too high.
         */
        TIRE_PRESSURE_CHANGED((byte) 0x05);
    
        public static Event fromByte(byte byteValue) throws CommandParseException {
            Event[] values = Event.values();
    
            for (int i = 0; i < values.length; i++) {
                Event state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Event(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}