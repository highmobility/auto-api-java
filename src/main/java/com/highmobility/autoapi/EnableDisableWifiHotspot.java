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

/**
 * Enable or disable the home charger Wi-Fi Hotspot.
 */
public class EnableDisableWifiHotspot extends Command {
    public static final Type TYPE = new Type(Identifier.HOME_CHARGER, 0x05);

    /**
     * @return Whether to enable the Wi-Fi hotspot.
     */
    public boolean enable() {
        return enable;
    }

    private boolean enable;

    public EnableDisableWifiHotspot(boolean enable) {
        super(TYPE.addByte(Property.boolToByte(enable)));
        this.enable = enable;
    }

    EnableDisableWifiHotspot(byte[] bytes) {
        super(bytes);
        enable = Property.getBool(bytes[3]);
    }
}
