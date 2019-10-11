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
 * Send heart rate
 */
public class SendHeartRate extends SetCommand {
    public static final Identifier identifier = Identifier.HEART_RATE;

    PropertyInteger heartRate = new PropertyInteger(0x01, false);

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
        super(identifier);
    
        addProperty(this.heartRate.update(false, 1, heartRate), true);
    }

    SendHeartRate(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return heartRate.update(p);
                }
                return null;
            });
        }
        if (this.heartRate.getValue() == null) 
            throw new NoPropertiesException();
    }
}