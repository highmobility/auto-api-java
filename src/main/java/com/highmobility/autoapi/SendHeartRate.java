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

import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;

/**
 * Heart rate can be sent to the car from a health accessory of a smart watch. This is only possible
 * to send through a direct Bluetooth link for privacy reasons.
 */
public class SendHeartRate extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HEART_RATE, 0x12);
    private static final byte IDENTIFIER = 0x01;

    int heartRate;

    /**
     * @return The heart rate.
     */
    public int getHeartRate() {
        return heartRate;
    }

    /**
     * Send the driver heart rate to the car.
     *
     * @param heartRate The heart rate.
     */
    public SendHeartRate(int heartRate) {
        super(TYPE.addProperty(new IntegerProperty(IDENTIFIER, heartRate, 1)));
        this.heartRate = heartRate;
    }

    public SendHeartRate(byte[] bytes) throws CommandParseException {
        super(bytes);
        Property prop = getProperty(IDENTIFIER);
        if (prop == null) throw new CommandParseException();
        this.heartRate = Property.getUnsignedInt(prop.getValueByte());
    }
}
