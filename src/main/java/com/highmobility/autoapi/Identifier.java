/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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

public class Identifier {
    public static final int FAILURE_MESSAGE = 2;
    public static final int FIRMWARE_VERSION = 3;
    public static final int CAPABILITIES = 16;
    public static final int VEHICLE_STATUS = 17;
    public static final int HISTORICAL = 18;
    public static final int MULTI_COMMAND = 19;
    public static final int DOORS = 32;
    public static final int TRUNK = 33;
    public static final int WAKE_UP = 34;
    public static final int CHARGING = 35;
    public static final int CLIMATE = 36;
    public static final int ROOFTOP_CONTROL = 37;
    public static final int HONK_HORN_FLASH_LIGHTS = 38;
    public static final int REMOTE_CONTROL = 39;
    public static final int VALET_MODE = 40;
    public static final int HEART_RATE = 41;

    public static final int VEHICLE_LOCATION = 48;
    public static final int NAVI_DESTINATION = 49;

    public static final int DIAGNOSTICS = 51;
    public static final int MAINTENANCE = 52;
    public static final int IGNITION = 53;
    public static final int LIGHTS = 54;
    public static final int MESSAGING = 55;
    public static final int NOTIFICATIONS = 56;

    public static final int FUELING = 64;
    public static final int DRIVER_FATIGUE = 65;
    public static final int WINDSCREEN = 66;
    public static final int VIDEO_HANDOVER = 67;
    public static final int TEXT_INPUT = 68;
    public static final int WINDOWS = 69;

    public static final int THEFT_ALARM = 70;
    public static final int PARKING_TICKET = 71;
    public static final int KEYFOB_POSITION = 72;
    public static final int BROWSER = 73;

    public static final int VEHICLE_TIME = 80;
    public static final int GRAPHICS = 81;
    public static final int OFFROAD = 82;
    public static final int CHASSIS_SETTINGS = 83;
    public static final int LIGHT_CONDITIONS = 84;
    public static final int WEATHER_CONDITIONS = 85;
    public static final int SEATS = 86;
    public static final int RACE = 87;
    public static final int PARKING_BRAKE = 88;
    public static final int WI_FI = 89;

    public static final int HOME_CHARGER = 96;
    public static final int DASHBOARD_LIGHTS = 97;
    public static final int CRUISE_CONTROL = 98;
    public static final int ENGINE_START_STOP = 99;
    public static final int TACHOGRAPH = 100;
    public static final int POWER_TAKEOFF = 101;
    public static final int MOBILE = 102;
    public static final int HOOD = 103;
    public static final int USAGE = 104;
    public static final int ENGINE = 105;

    public static int fromBytes(byte first, byte second) {
        return ((first & 0xff) << 8) | (second & 0xff);
    }

    public static int fromBytes(byte[] bytes) {
        return fromBytes(bytes[0], bytes[1]);
    }

    public static byte[] toBytes(Integer identifier) {
        return Property.intToBytes(identifier, 2);
    }
}