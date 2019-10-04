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

package com.highmobility.autoapi.certificate;

import com.highmobility.autoapi.Identifier;
import com.highmobility.value.BitLocation;

/**
 * Used to get the Auto API bit locations for the Access Certificate permissions field in crypto
 * package.
 */
public class PermissionLocation {
    public static BitLocation allowCarSdkResetLocation() {
        return new BitLocation(1, 2);
    }

    public static BitLocation locationFor(Identifier identifier, Type type) {
        switch (identifier) {
            case CAPABILITIES:
                if (type == Type.READ) return new BitLocation(2, 0);
                break;
            case VEHICLE_STATUS:
                if (type == Type.READ) return new BitLocation(2, 1);
                break;
            case DIAGNOSTICS:
                if (type == Type.READ) return new BitLocation(2, 2);
                break;
            case DOORS:
                if (type == Type.READ) return new BitLocation(2, 3);
                else if (type == Type.WRITE) return new BitLocation(2, 4);
                break;
            case IGNITION:
                if (type == Type.READ) return new BitLocation(2, 5);
                else if (type == Type.WRITE) return new BitLocation(2, 6);
                break;
            case TRUNK:
                if (type == Type.READ) return new BitLocation(2, 7);
                else if (type == Type.WRITE) return new BitLocation(3, 0);
                break;
            case WAKE_UP:
                if (type == Type.WRITE) return new BitLocation(3, 2);
                break;
            case CHARGING:
                if (type == Type.READ) return new BitLocation(3, 3);
                else if (type == Type.WRITE) return new BitLocation(3, 4);
                break;
            case CLIMATE:
                if (type == Type.READ) return new BitLocation(3, 5);
                else if (type == Type.WRITE) return new BitLocation(3, 6);
                break;
            case LIGHTS:
                if (type == Type.READ) return new BitLocation(3, 7);
                else if (type == Type.WRITE) return new BitLocation(4, 0);
                break;
            case WINDOWS:
                if (type == Type.READ) return new BitLocation(7, 1);
                else if (type == Type.WRITE) return new BitLocation(4, 1);
                break;
            case ROOFTOP_CONTROL:
                if (type == Type.READ) return new BitLocation(4, 2);
                else if (type == Type.WRITE) return new BitLocation(4, 3);
                break;
            case WINDSCREEN:
                if (type == Type.READ) return new BitLocation(4, 4);
                else if (type == Type.WRITE) return new BitLocation(4, 5);
                break;
            case HONK_HORN_FLASH_LIGHTS:
                if (type == Type.READ) return new BitLocation(7, 2);
                else if (type == Type.WRITE) return new BitLocation(4, 6);
                break;

            // head unit

            case NOTIFICATIONS:
                if (type == Type.READ) return headUnitReadLocation();
                else if (type == Type.WRITE) return headUnitWriteLocation();
                break;
            case MESSAGING:
                if (type == Type.READ) return headUnitReadLocation();
                else if (type == Type.WRITE) return headUnitWriteLocation();
                break;
            case VIDEO_HANDOVER:
            case BROWSER:
            case GRAPHICS:
            case TEXT_INPUT:
                // above fall through to here and only have write permission
                if (type == Type.WRITE) return headUnitWriteLocation();
                break;

            case REMOTE_CONTROL:
                if (type == Type.READ) return new BitLocation(5, 0);
                else if (type == Type.WRITE) return new BitLocation(5, 1);
                break;
            case VALET_MODE:
                if (type == Type.READ) return new BitLocation(5, 2);
                else if (type == Type.WRITE) return new BitLocation(5, 3);
                else if (type == Type.LIMITED) return new BitLocation(5, 4);
                break;
            case FUELING:
                if (type == Type.READ) return new BitLocation(8, 5);
                else if (type == Type.WRITE) return new BitLocation(5, 5);
                break;
            case HEART_RATE:
                if (type == Type.WRITE) return new BitLocation(5, 6);
                break;
            case DRIVER_FATIGUE:
                if (type == Type.READ) return new BitLocation(5, 7);
                break;
            case VEHICLE_LOCATION:
                if (type == Type.READ) return new BitLocation(6, 0);
                break;
            case NAVI_DESTINATION:
                if (type == Type.READ) return new BitLocation(7, 3);
                else if (type == Type.WRITE) return new BitLocation(6, 1);
                break;
            case THEFT_ALARM:
                if (type == Type.READ) return new BitLocation(6, 2);
                else return new BitLocation(6, 3);
            case PARKING_TICKET:
                if (type == Type.READ) return new BitLocation(6, 4);
                else if (type == Type.WRITE) return new BitLocation(6, 5);
                break;
            case KEYFOB_POSITION:
                if (type == Type.READ) return new BitLocation(6, 6);
                break;
            case VEHICLE_TIME:
                if (type == Type.READ) return new BitLocation(7, 0);
                break;
            case RACE:
                if (type == Type.READ) return new BitLocation(7, 4);
                break;
            case OFFROAD:
                if (type == Type.READ) return new BitLocation(7, 5);
                break;
            case CHASSIS_SETTINGS:
                if (type == Type.READ) return new BitLocation(7, 6);
                else if (type == Type.WRITE) return new BitLocation(7, 7);
                break;
            case SEATS:
                if (type == Type.READ) return new BitLocation(8, 0);
                else if (type == Type.WRITE) return new BitLocation(8, 1);
                break;
            case PARKING_BRAKE:
                if (type == Type.READ) return new BitLocation(8, 2);
                else if (type == Type.WRITE) return new BitLocation(8, 3);
                break;
            case LIGHT_CONDITIONS:
            case WEATHER_CONDITIONS:
                if (type == Type.READ) return new BitLocation(8, 4);
                break;
            case WI_FI:
                if (type == Type.READ) return new BitLocation(8, 6);
                else if (type == Type.WRITE) return new BitLocation(8, 7);
                break;
            case HOME_CHARGER:
                if (type == Type.READ) return new BitLocation(9, 0);
                else if (type == Type.WRITE) return new BitLocation(9, 1);
                break;
            case DASHBOARD_LIGHTS:
                if (type == Type.READ) return new BitLocation(9, 2);
                break;
            case CRUISE_CONTROL:
                if (type == Type.READ) return new BitLocation(9, 3);
                else if (type == Type.WRITE) return new BitLocation(9, 4);
                break;
            case ENGINE_START_STOP:
                if (type == Type.READ) return new BitLocation(9, 5);
                else if (type == Type.WRITE) return new BitLocation(9, 6);
                break;
            case TACHOGRAPH:
                if (type == Type.READ) return new BitLocation(9, 7);
                break;
            case POWER_TAKEOFF:
                if (type == Type.READ) return new BitLocation(10, 0);
                else if (type == Type.WRITE) return new BitLocation(10, 1);
                break;
        }

        return null;
    }

    public static BitLocation headUnitReadLocation() {
        return new BitLocation(6, 7);
    }

    public static BitLocation headUnitWriteLocation() {
        return new BitLocation(4, 7);
    }

    public enum Type {READ, WRITE, LIMITED}
}