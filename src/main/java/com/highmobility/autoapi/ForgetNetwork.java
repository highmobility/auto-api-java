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
 * Forget a network that the car has previously connected to.
 */
public class ForgetNetwork extends Command {
    public static final Type TYPE = new Type(Identifier.WIFI, 0x03);
    public static final byte IDENTIFIER = 0x03;
    private Property<String> ssid = new Property(String.class, IDENTIFIER);

    /**
     * @return The network SSID.
     */
    public Property<String> getSsid() {
        return ssid;
    }

    /**
     * Forget a network that the car has previously connected to.
     *
     * @param ssid The network name.
     */
    public ForgetNetwork(String ssid) {
        super(TYPE);
        this.ssid.update(ssid);
        createBytes(this.ssid);
    }

    ForgetNetwork(byte[] bytes) {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER:
                        return ssid.update(p);
                }
                return null;
            });
        }
    }
}
