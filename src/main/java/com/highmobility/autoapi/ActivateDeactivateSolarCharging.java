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
 * Activate or deactivate charging from solar power.
 */
public class ActivateDeactivateSolarCharging extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HOME_CHARGER, 0x14);
    private static final byte IDENTIFIER = 0x01;

    private Property<Boolean> activate = new Property(Boolean.class, IDENTIFIER);

    /**
     * @return Whether to activate the solar charging.
     */
    public Property<Boolean> activate() {
        return activate;
    }

    public ActivateDeactivateSolarCharging(Boolean activate) {
        super(TYPE);
        this.activate.update(activate);
        createBytes(this.activate);
    }

    ActivateDeactivateSolarCharging(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER:
                        return activate.update(p);
                }
                return null;
            });
        }
    }
}
