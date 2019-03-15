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
 * Activate or deactivate valet mode. The result is sent through the Valet Mode message.
 */
public class ActivateDeactivateValetMode extends Command {
    public static final Type TYPE = new Type(Identifier.VALET_MODE, 0x12);
    private static final byte IDENTIFIER = 0x01;
    Property<Boolean> activate = new Property(Boolean.class, IDENTIFIER);

    /**
     * @return Whether valet mode should be activated.
     */
    public Property<Boolean> activate() {
        return activate;
    }

    /**
     * @param activate Whether valet mode should be activated.
     */
    public ActivateDeactivateValetMode(Boolean activate) {
        super(TYPE);
        this.activate.update(activate);
        createBytes(this.activate);
    }

    ActivateDeactivateValetMode(byte[] bytes) {
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
