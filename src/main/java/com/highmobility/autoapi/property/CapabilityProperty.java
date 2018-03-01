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

import com.highmobility.autoapi.Identifier;
import com.highmobility.autoapi.Type;
import com.highmobility.utils.Bytes;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ttiganik on 14/10/2016.
 */
public class CapabilityProperty extends Property {
    static final byte defaultIdentifier     = 0x01;

    Type[] types;

    byte[] identifierBytes;
    Identifier identifier;

    /**
     *
     * @return All of the command types supported for this category
     */
    public Type[] getTypes() {
        return types;
    }

    /**
     *
     * @param type The Type of the command to check for
     * @return True if command is supported
     */
    public boolean isSupported(Type type) {
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(type)) return true;
        }

        return false;
    }

    /**
     *
     * @return The command's category identifier
     */
    public byte[] getIdentifierBytes() {
        return identifierBytes;
    }

    /**
     * Tries to match the identifier bytes to one of the defined identifiers
     *
     * @return The identifier if there is a match
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    public CapabilityProperty(byte[] bytes) {
        super(bytes);

        int propertyLength = Property.getUnsignedInt(bytes, 1, 2);
        identifierBytes = new byte[] { bytes[3], bytes[4] };
        identifier = Identifier.fromBytes(identifierBytes);
        if (propertyLength < 3) return;

        ArrayList<Type> builder = new ArrayList<>();

        for (int i = 5; i < 3 + propertyLength; i++) {
            Type type = new Type(identifierBytes, bytes[i]);
            builder.add(type);
        }

        types = builder.toArray(new Type[builder.size()]);
    }

    /**
     *
     * @param categoryIdentifier the 2 byte identifier of the category
     * @param types All the types supported for the given category identifier
     * @throws IllegalArgumentException when types are not from the same category or parameters are invalid
     */
    public CapabilityProperty(byte[] categoryIdentifier, Type[] types) throws IllegalArgumentException {
        super(defaultIdentifier, getValue(categoryIdentifier, types));
        this.identifierBytes = categoryIdentifier;
        this.identifier = Identifier.fromBytes(categoryIdentifier);
        this.types = types;

        // add the state (0x01) if get state exists(0x00) and 0x01 already doesn't exist.
        boolean getStateExists = false, stateExists = false;
        for (int i = 0; i < types.length; i++) {
            Type type = types[i];
            if (type.getType() == 0x00) {
                getStateExists = true;
            }
            if (type.getType() == 0x01) stateExists = true;
        }

        if (getStateExists && stateExists == false) {
            types = Arrays.copyOf(types, types.length + 1);
            byte[] typeBytes = new byte[3];
            Bytes.setBytes(typeBytes, categoryIdentifier, 0);
            typeBytes[2] = 0x01;
            Type stateType = new Type(typeBytes);
            types[types.length - 1] = stateType;
            this.types = types;
            byte[] newValue = getValue(categoryIdentifier, types);
            byte[] newBytes = baseBytes(defaultIdentifier, newValue.length);
            Bytes.setBytes(newBytes, newValue, 3);
            this.bytes = newBytes;
        }
    }

    /**
     *
     * @param categoryIdentifier the identifier of the category
     * @param types All the types supported for the given category identifier
     * @throws IllegalArgumentException when types are not from the same category or parameters are invalid
     */
    public CapabilityProperty(Identifier categoryIdentifier, Type[] types) throws IllegalArgumentException {
        this(categoryIdentifier.getBytes(), types);
    }

    static byte[] getValue(byte[] categoryIdentifier, Type[] types) throws IllegalArgumentException {
        byte[] bytes = new byte[2 + types.length];
        Bytes.setBytes(bytes, categoryIdentifier, 0);
        for (int i = 0; i < types.length; i++) {
            Type type = types[i];
            bytes[2 + i] = type.getType();
            if (type.getIdentifierAndType()[0] != categoryIdentifier[0]
                || type.getIdentifierAndType()[1] != categoryIdentifier[1]) throw new IllegalArgumentException();
        }

        return bytes;
    }

    byte[] getBytes() {
        byte[] bytes = new byte[5 + types.length];
        bytes[0] = 0x01;
        byte[] lengthBytes = Property.intToBytes((2 + types.length), 2);
        bytes[1] = lengthBytes[0];
        bytes[2] = lengthBytes[1];

        if (types != null) {
            for (int i = 0; i < types.length; i++) {
                bytes[3 + i] = types[i].getType();
            }
        }

        return bytes;
    }
}
