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

package com.highmobility.autoapi.rooftop;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;

public enum ConvertibleRoofState implements HMProperty {
    CLOSED((byte) 0x00),
    OPEN((byte) 0x01),
    EMERGENCY_LOCKED((byte) 0x02),
    CLOSED_SECURED((byte) 0x03),
    OPEN_SECURED((byte) 0x04),
    HARD_TOP_MOUNTED((byte) 0x05),
    INTERMEDIATE_POSITION((byte) 0x06),
    LOADING_POSITION((byte) 0x07),
    LOADING_POSITION_IMMEDIATE((byte) 0x08);

    public static ConvertibleRoofState fromByte(byte byteValue) throws CommandParseException {
        ConvertibleRoofState[] values = ConvertibleRoofState.values();

        for (int i = 0; i < values.length; i++) {
            ConvertibleRoofState state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;
    private byte identifier;

    ConvertibleRoofState(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    public byte getIdentifier() {
        return identifier;
    }

    public ConvertibleRoofState setIdentifier(byte identifier) {
        this.identifier = identifier;
        return this;
    }

    @Override public byte getPropertyIdentifier() {
        return identifier;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), value);
    }
}