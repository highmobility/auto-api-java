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

import com.highmobility.autoapi.property.ObjectProperty;

/**
 * Set the charge current of the home charger.
 */
public class SetChargeCurrent extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HOME_CHARGER, 0x12);
    private static final byte IDENTIFIER = 0x01;

    private ObjectProperty<Float> current = new ObjectProperty<>(Float.class, IDENTIFIER);

    /**
     * @return The charge current.
     */
    public float getCurrent() {
        return current.getValue();
    }

    /**
     * @param chargeCurrent The charge current.
     */
    public SetChargeCurrent(float chargeCurrent) {
        super(TYPE);
        current.update(chargeCurrent);
        createBytes(current);
    }

    SetChargeCurrent(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                if (p.getPropertyIdentifier() == IDENTIFIER) {
                    return current.update(p);
                }
                return null;
            });
        }

        if (current.getValue() == null) throw new CommandParseException();
    }
}
