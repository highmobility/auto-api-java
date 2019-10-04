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

class GetCommand extends Command {
    // the get state ctor
    GetCommand(Identifier identifier) {
        super(identifier, 3);
        set(0, identifier.getBytes());
        set(2, (byte) 0x00);
        type = Type.GET;
    }

    GetCommand(Identifier identifier, byte[] propertyIdentifiers) {
        super(identifier, 3 + (propertyIdentifiers != null ? propertyIdentifiers.length : 0));

        set(0, identifier.getBytes());
        set(2, (byte) 0x00);
        set(3, propertyIdentifiers);

        type = Type.GET;
    }

    GetCommand(byte[] bytes) {
        super(bytes);
    }
}
