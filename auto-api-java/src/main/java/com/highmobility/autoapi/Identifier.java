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

public class Identifier {
    public static final int FAILURE_MESSAGE = 2;
    public static final int FIRMWARE_VERSION = 3;

    public static final int CAPABILITIES = 16;
    public static final int VEHICLE_STATUS = 17;
    public static final int HISTORICAL = 18;
    public static final int MULTI_COMMAND = 19;
    public static final int VEHICLE_INFORMATION = 20;

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
    public static final int TRIPS = 106;

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