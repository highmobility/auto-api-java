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
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.PropertyInteger;

/**
 * The Heart Rate capability
 */
public class HeartRate {
    public static final int IDENTIFIER = Identifier.HEART_RATE;

    public static final byte PROPERTY_HEART_RATE = 0x01;

    /**
     * Send heart rate
     */
    public static class SendHeartRate extends SetCommand {
        PropertyInteger heartRate = new PropertyInteger(PROPERTY_HEART_RATE, false);
    
        /**
         * @return The heart rate
         */
        public PropertyInteger getHeartRate() {
            return heartRate;
        }
        
        /**
         * Send heart rate
         *
         * @param heartRate The heart rate
         */
        public SendHeartRate(Integer heartRate) {
            super(IDENTIFIER);
        
            addProperty(this.heartRate.update(false, 1, heartRate));
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