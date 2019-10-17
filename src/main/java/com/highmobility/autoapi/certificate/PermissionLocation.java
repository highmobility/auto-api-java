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

    public static BitLocation locationFor(Integer identifier, Type type) {
        switch (identifier) {
            case Identifier.CAPABILITIES:
                if (type == Type.READ) return new BitLocation(2, 0);
                break;
            case Identifier.VEHICLE_STATUS:
                if (type == Type.READ) return new BitLocation(2, 1);
                break;
            case Identifier.DIAGNOSTICS:
                if (type == Type.READ) return new BitLocation(2, 2);
                break;
            case Identifier.DOORS:
                if (type == Type.READ) return new BitLocation(2, 3);
                else if (type == Type.WRITE) return new BitLocation(2, 4);
                break;
            case Identifier.IGNITION:
                if (type == Type.READ) return new BitLocation(2, 5);
                else if (type == Type.WRITE) return new BitLocation(2, 6);
                break;
            case Identifier.TRUNK:
                if (type == Type.READ) return new BitLocation(2, 7);
                else if (type == Type.WRITE) return new BitLocation(3, 0);
                break;
            case Identifier.WAKE_UP:
                if (type == Type.WRITE) return new BitLocation(3, 2);
                break;
            case Identifier.CHARGING:
                if (type == Type.READ) return new BitLocation(3, 3);
                else if (type == Type.WRITE) return new BitLocation(3, 4);
                break;
            case Identifier.CLIMATE:
                if (type == Type.READ) return new BitLocation(3, 5);
                else if (type == Type.WRITE) return new BitLocation(3, 6);
                break;
            case Identifier.LIGHTS:
                if (type == Type.READ) return new BitLocation(3, 7);
                else if (type == Type.WRITE) return new BitLocation(4, 0);
                break;
            case Identifier.WINDOWS:
                if (type == Type.READ) return new BitLocation(7, 1);
                else if (type == Type.WRITE) return new BitLocation(4, 1);
                break;
            case Identifier.ROOFTOP_CONTROL:
                if (type == Type.READ) return new BitLocation(4, 2);
                else if (type == Type.WRITE) return new BitLocation(4, 3);
                break;
            case Identifier.WINDSCREEN:
                if (type == Type.READ) return new BitLocation(4, 4);
                else if (type == Type.WRITE) return new BitLocation(4, 5);
                break;
            case Identifier.HONK_HORN_FLASH_LIGHTS:
                if (type == Type.READ) return new BitLocation(7, 2);
                else if (type == Type.WRITE) return new BitLocation(4, 6);
                break;

            // head unit

            case Identifier.NOTIFICATIONS:
                if (type == Type.READ) return headUnitReadLocation();
                else if (type == Type.WRITE) return headUnitWriteLocation();
                break;
            case Identifier.MESSAGING:
                if (type == Type.READ) return headUnitReadLocation();
                else if (type == Type.WRITE) return headUnitWriteLocation();
                break;
            case Identifier.VIDEO_HANDOVER:
            case Identifier.BROWSER:
            case Identifier.GRAPHICS:
            case Identifier.TEXT_INPUT:
                // above fall through to here and only have write permission
                if (type == Type.WRITE) return headUnitWriteLocation();
                break;

            case Identifier.REMOTE_CONTROL:
                if (type == Type.READ) return new BitLocation(5, 0);
                else if (type == Type.WRITE) return new BitLocation(5, 1);
                break;
            case Identifier.VALET_MODE:
                if (type == Type.READ) return new BitLocation(5, 2);
                else if (type == Type.WRITE) return new BitLocation(5, 3);
                else if (type == Type.LIMITED) return new BitLocation(5, 4);
                break;
            case Identifier.FUELING:
                if (type == Type.READ) return new BitLocation(8, 5);
                else if (type == Type.WRITE) return new BitLocation(5, 5);
                break;
            case Identifier.HEART_RATE:
                if (type == Type.WRITE) return new BitLocation(5, 6);
                break;
            case Identifier.DRIVER_FATIGUE:
                if (type == Type.READ) return new BitLocation(5, 7);
                break;
            case Identifier.VEHICLE_LOCATION:
                if (type == Type.READ) return new BitLocation(6, 0);
                break;
            case Identifier.NAVI_DESTINATION:
                if (type == Type.READ) return new BitLocation(7, 3);
                else if (type == Type.WRITE) return new BitLocation(6, 1);
                break;
            case Identifier.THEFT_ALARM:
                if (type == Type.READ) return new BitLocation(6, 2);
                else return new BitLocation(6, 3);
            case Identifier.PARKING_TICKET:
                if (type == Type.READ) return new BitLocation(6, 4);
                else if (type == Type.WRITE) return new BitLocation(6, 5);
                break;
            case Identifier.KEYFOB_POSITION:
                if (type == Type.READ) return new BitLocation(6, 6);
                break;
            case Identifier.VEHICLE_TIME:
                if (type == Type.READ) return new BitLocation(7, 0);
                break;
            case Identifier.RACE:
                if (type == Type.READ) return new BitLocation(7, 4);
                break;
            case Identifier.OFFROAD:
                if (type == Type.READ) return new BitLocation(7, 5);
                break;
            case Identifier.CHASSIS_SETTINGS:
                if (type == Type.READ) return new BitLocation(7, 6);
                else if (type == Type.WRITE) return new BitLocation(7, 7);
                break;
            case Identifier.SEATS:
                if (type == Type.READ) return new BitLocation(8, 0);
                else if (type == Type.WRITE) return new BitLocation(8, 1);
                break;
            case Identifier.PARKING_BRAKE:
                if (type == Type.READ) return new BitLocation(8, 2);
                else if (type == Type.WRITE) return new BitLocation(8, 3);
                break;
            case Identifier.LIGHT_CONDITIONS:
            case Identifier.WEATHER_CONDITIONS:
                if (type == Type.READ) return new BitLocation(8, 4);
                break;
            case Identifier.WI_FI:
                if (type == Type.READ) return new BitLocation(8, 6);
                else if (type == Type.WRITE) return new BitLocation(8, 7);
                break;
            case Identifier.HOME_CHARGER:
                if (type == Type.READ) return new BitLocation(9, 0);
                else if (type == Type.WRITE) return new BitLocation(9, 1);
                break;
            case Identifier.DASHBOARD_LIGHTS:
                if (type == Type.READ) return new BitLocation(9, 2);
                break;
            case Identifier.CRUISE_CONTROL:
                if (type == Type.READ) return new BitLocation(9, 3);
                else if (type == Type.WRITE) return new BitLocation(9, 4);
                break;
            case Identifier.ENGINE_START_STOP:
                if (type == Type.READ) return new BitLocation(9, 5);
                else if (type == Type.WRITE) return new BitLocation(9, 6);
                break;
            case Identifier.TACHOGRAPH:
                if (type == Type.READ) return new BitLocation(9, 7);
                break;
            case Identifier.POWER_TAKEOFF:
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