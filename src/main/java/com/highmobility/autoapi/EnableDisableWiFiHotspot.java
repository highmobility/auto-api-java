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
import com.highmobility.autoapi.value.EnabledState;

/**
 * Enable disable wi fi hotspot
 */
public class EnableDisableWiFiHotspot extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.HOME_CHARGER;

    public static final byte IDENTIFIER_WI_FI_HOTSPOT_ENABLED = 0x08;

    Property<EnabledState> wifiHotspotEnabled = new Property(EnabledState.class, IDENTIFIER_WI_FI_HOTSPOT_ENABLED);

    /**
     * @return The wi fi hotspot enabled
     */
    public Property<EnabledState> getWifiHotspotEnabled() {
        return wifiHotspotEnabled;
    }
    
    /**
     * Enable disable wi fi hotspot
     *
     * @param wifiHotspotEnabled The wi fi hotspot enabled
     */
    public EnableDisableWiFiHotspot(EnabledState wifiHotspotEnabled) {
        super(IDENTIFIER);
    
        addProperty(this.wifiHotspotEnabled.update(wifiHotspotEnabled));
        createBytes();
    }

    EnableDisableWiFiHotspot(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_WI_FI_HOTSPOT_ENABLED: return wifiHotspotEnabled.update(p);
                }
                return null;
            });
        }
        if (this.wifiHotspotEnabled.getValue() == null) 
            throw new NoPropertiesException();
    }
}