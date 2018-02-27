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

/**
 * Heart rate can be sent to the car from a health accessory of a smartwatch. This is only possible
 * to send through a direct Bluetooth link for privacy reasons.
 */
public class SendHeartRate extends Command {
    public static final Type TYPE = new Type(Identifier.HEART_RATE, 0x02);

    /**
     * Send the driver heart rate to the car.
     *
     * @param heartRate The heart rate.
     */
    public SendHeartRate(int heartRate) {
        super(TYPE.addByte((byte) heartRate), true);
    }

    public SendHeartRate(byte[] bytes) {
        super(bytes);
    }
}
