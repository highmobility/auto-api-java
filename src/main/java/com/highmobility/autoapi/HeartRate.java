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
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.measurement.Frequency;
import com.highmobility.autoapi.capability.DisabledIn;

/**
 * The Heart Rate capability
 */
public class HeartRate {
    public static final int IDENTIFIER = Identifier.HEART_RATE;

    public static final byte PROPERTY_HEART_RATE = 0x01;

    public static final DisabledIn[] disabledIn = new DisabledIn[] { DisabledIn.WEB };

    /**
     * Send heart rate
     */
    public static class SendHeartRate extends SetCommand {
        Property<Frequency> heartRate = new Property<>(Frequency.class, PROPERTY_HEART_RATE);
    
        /**
         * @return The heart rate
         */
        public Property<Frequency> getHeartRate() {
            return heartRate;
        }
        
        /**
         * Send heart rate
         *
         * @param heartRate The heart rate
         */
        public SendHeartRate(Frequency heartRate) {
            super(IDENTIFIER);
        
            addProperty(this.heartRate.update(heartRate));
            createBytes();
        }
    
        SendHeartRate(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_HEART_RATE: return heartRate.update(p);
                    }
                    return null;
                });
            }
            if (this.heartRate.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
}