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

package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public class ObjectPropertyInteger extends ObjectProperty<Integer> {
    // since int has different signed and length options, its better to have a property subclass.
    boolean signed;

    public ObjectPropertyInteger(Class<Integer> theClass, byte identifier, boolean signed) {
        super(theClass, identifier);
        this.signed = signed;
    }

    @Override public ObjectProperty update(Property p) throws CommandParseException {
        if (p.getValueLength() >= 1) {
            if (signed) value = Property.getSignedInt(p.getValueBytesArray());
            else value = Property.getUnsignedInt(p.getValueBytesArray());
        }
        return this;
    }
}
