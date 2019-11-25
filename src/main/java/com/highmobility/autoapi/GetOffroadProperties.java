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

import com.highmobility.value.Bytes;

/**
 * Get specific offroad properties.
 */
public class GetOffroadProperties extends GetCommand {
    public static final Integer IDENTIFIER = Identifier.OFFROAD;

    Bytes propertyIdentifiers;

    /**
     * @return The property identifiers.
     */
    public Bytes getPropertyIdentifiers() {
        return propertyIdentifiers;
    }

    /**
     * @param propertyIdentifiers The property identifiers
     */
    public GetOffroadProperties(Bytes propertyIdentifiers) {
        super(IDENTIFIER, propertyIdentifiers.getByteArray());
        this.propertyIdentifiers = propertyIdentifiers;
    }

    GetOffroadProperties(byte[] bytes) throws CommandParseException {
        super(bytes);
        propertyIdentifiers = getRange(5, getLength());
    }
}
