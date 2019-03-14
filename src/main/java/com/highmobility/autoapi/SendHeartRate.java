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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;

/**
 * Heart rate can be sent to the car from a health accessory of a smart watch. This is only possible
 * to send through a direct Bluetooth link for privacy reasons.
 */
public class SendHeartRate extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HEART_RATE, 0x12);
    private static final byte IDENTIFIER = 0x01;

    PropertyInteger heartRate = new PropertyInteger(IDENTIFIER, false);

    /**
     * @return The heart rate.
     */
    public Property<Integer> getHeartRate() {
        return heartRate;
    }

    /**
     * Send the driver heart rate to the car.
     *
     * @param heartRate The heart rate.
     */
    public SendHeartRate(int heartRate) {
        super(TYPE);
        this.heartRate.update(false, 1, heartRate);
        createBytes(this.heartRate);
    }

    SendHeartRate(byte[] bytes) throws CommandParseException {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER:
                        return this.heartRate.update(p);
                }
                return null;
            });
        }

        if (this.heartRate.getValue() == null) throw new CommandParseException();
    }
}
