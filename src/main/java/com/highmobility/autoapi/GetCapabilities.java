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

/**
 * Get the vehicle capabilities. The car will respond with the Capabilities message that manifests
 * all different APIs that are enabled on the specific car. It is good practice to only inspect the
 * vehicle capabilities the first time when access is gained. The capabilities are fixed for each
 * car type and will not change between every session unless the user meanwhile receives new
 * permissions (requires a whole new certificate).
 */
public class GetCapabilities extends Command {
    public static final Type TYPE = new Type(Identifier.CAPABILITIES, 0x00);

    public GetCapabilities() {
        super(TYPE);
    }

    GetCapabilities(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
