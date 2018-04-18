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

import com.highmobility.utils.Bytes;

import java.util.Arrays;

/**
 * Get the capability of a certain feature. The car will respond with the Capability message - to
 * what extent the capability is supported, if at all.
 */
public class GetCapability extends Command {
    public static final Type TYPE = new Type(Identifier.CAPABILITIES, 0x02);

    byte[] categoryBytes;
    Identifier category;

    /**
     * @return The identifier of the category the capabilities were requested for.
     */
    public byte[] getCapabilityIdentifierBytes() {
        return categoryBytes;
    }

    /**
     * @return The identifier of the category the capabilities were requested for.
     */
    public Identifier getCapabilityIdentifier() {
        return category;
    }

    /**
     *
     * @param type The feature type
     */
    public GetCapability(Type type) {
        super(getBytes(type));
        this.category = type.identifier;
        this.categoryBytes = this.category.getBytes();
    }

    static byte[] getBytes(Type type) {
        byte[] bytes = new byte[5];
        Bytes.setBytes(bytes, TYPE.getIdentifierAndType(), 0);
        Bytes.setBytes(bytes, type.getIdentifier(), 3);
        return bytes;
    }

    GetCapability(byte[] bytes) {
        super(bytes);
        categoryBytes = Arrays.copyOfRange(bytes, 3, 5);
        category = Identifier.fromBytes(categoryBytes);
    }
}
