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

package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.property.Property;

import java.util.Arrays;

/**
 * Used for identifying known auto api categories
 */
public enum Identifier {
    FAILURE_MESSAGE(new byte[]{0x00, (byte) 0x02}),
    CAPABILITIES(new byte[]{0x00, (byte) 0x10}),
    VEHICLE_STATUS(new byte[]{0x00, (byte) 0x11}),
    HISTORICAL(new byte[]{0x00, (byte) 0x12}),
    MULTI_COMMAND(new byte[]{0x00, (byte) 0x13}),
    DOORS(new byte[]{0x00, (byte) 0x20}),
    TRUNK(new byte[]{0x00, (byte) 0x21}),
    WAKE_UP(new byte[]{0x00, (byte) 0x22}),
    CHARGING(new byte[]{0x00, (byte) 0x23}),
    CLIMATE(new byte[]{0x00, (byte) 0x24}),
    ROOFTOP_CONTROL(new byte[]{0x00, (byte) 0x25}),
    HONK_HORN_FLASH_LIGHTS(new byte[]{0x00, (byte) 0x26}),
    REMOTE_CONTROL(new byte[]{0x00, (byte) 0x27}),
    VALET_MODE(new byte[]{0x00, (byte) 0x28}),
    HEART_RATE(new byte[]{0x00, (byte) 0x29}),
    VEHICLE_LOCATION(new byte[]{0x00, (byte) 0x30}),
    VEHICLE_TIME(new byte[]{0x00, (byte) 0x50}),
    NAVI_DESTINATION(new byte[]{0x00, (byte) 0x31}),
    DIAGNOSTICS(new byte[]{0x00, (byte) 0x33}),
    MAINTENANCE(new byte[]{0x00, (byte) 0x34}),
    IGNITION(new byte[]{0x00, (byte) 0x35}),
    LIGHTS(new byte[]{0x00, (byte) 0x36}),
    MESSAGING(new byte[]{0x00, (byte) 0x37}),
    NOTIFICATIONS(new byte[]{0x00, (byte) 0x38}),
    WINDOWS(new byte[]{0x00, (byte) 0x45}),
    WINDSCREEN(new byte[]{0x00, (byte) 0x42}),
    VIDEO_HANDOVER(new byte[]{0x00, (byte) 0x43}),
    BROWSER(new byte[]{0x00, (byte) 0x49}),
    GRAPHICS(new byte[]{0x00, (byte) 0x51}),
    TEXT_INPUT(new byte[]{0x00, (byte) 0x44}),
    FUELING(new byte[]{0x00, (byte) 0x40}),
    DRIVER_FATIGUE(new byte[]{0x00, (byte) 0x41}),
    THEFT_ALARM(new byte[]{0x00, (byte) 0x46}),
    PARKING_TICKET(new byte[]{0x00, (byte) 0x47}),
    KEYFOB_POSITION(new byte[]{0x00, (byte) 0x48}),
    FIRMWARE_VERSION(new byte[]{0x00, (byte) 0x03}),
    SEATS(new byte[]{0x00, (byte) 0x56}),
    RACE(new byte[]{0x00, (byte) 0x57}),
    OFFROAD(new byte[]{0x00, (byte) 0x52}),
    PARKING_BRAKE(new byte[]{0x00, (byte) 0x58}),
    LIGHT_CONDITIONS(new byte[]{0x00, (byte) 0x54}),
    WEATHER_CONDITIONS(new byte[]{0x00, (byte) 0x55}),
    CHASSIS_SETTINGS(new byte[]{0x00, (byte) 0x53}),
    WI_FI(new byte[]{0x00, (byte) 0x59}),
    HOME_CHARGER(new byte[]{0x00, (byte) 0x60}),
    DASHBOARD_LIGHTS(new byte[]{0x00, (byte) 0x61}),
    CRUISE_CONTROL(new byte[]{0x00, (byte) 0x62}),
    ENGINE_START_STOP(new byte[]{0x00, (byte) 0x63}),
    TACHOGRAPH(new byte[]{0x00, (byte) 0x64}),
    POWER_TAKEOFF(new byte[]{0x00, (byte) 0x65}),
    MOBILE(new byte[]{0x00, (byte) 0x66}),
    HOOD(new byte[]{0x00, (byte) 0x67}),
    USAGE(new byte[]{0x00, (byte) 0x68});

    public static Identifier fromBytes(byte[] bytes) {
        return fromBytes(bytes[0], bytes[1]);
    }

    public static Identifier fromBytes(byte firstByte, byte secondByte) {
        Identifier[] allValues = Identifier.values();

        for (int i = 0; i < allValues.length; i++) {
            Identifier identifier = allValues[i];
            if (is(identifier, firstByte, secondByte)) {
                return identifier;
            }
        }

        return null;
    }

    Identifier(byte[] identifier) {
        this.identifier = identifier;
    }

    private byte[] identifier;

    public byte[] getBytes() {
        return identifier;
    }

    public Integer asInt() {
        return Property.getSignedInt(getBytes());
    }

    public static boolean is(Identifier feature, byte firstByte, byte secondByte) {
        return feature.getBytes()[0] == firstByte && feature.getBytes()[1] == secondByte;
    }

    public boolean equals(Integer intValue) {
        return Arrays.equals(getBytes(), Property.intToBytes(intValue, 2));
    }
}