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

/**
 * Forget network
 */
public class ForgetNetwork extends SetCommand {
    public static final Identifier identifier = Identifier.WI_FI;

    Property<String> networkSSID = new Property(String.class, 0x03);

    /**
     * @return The network ssid
     */
    public Property<String> getNetworkSSID() {
        return networkSSID;
    }
    
    /**
     * Forget network
     *
     * @param networkSSID The network SSID
     */
    public ForgetNetwork(String networkSSID) {
        super(identifier);
    
        addProperty(this.networkSSID.update(networkSSID), true);
    }

    ForgetNetwork(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x03: return networkSSID.update(p);
                }
                return null;
            });
        }
        if (this.networkSSID.getValue() == null) 
            throw new NoPropertiesException();
    }
}