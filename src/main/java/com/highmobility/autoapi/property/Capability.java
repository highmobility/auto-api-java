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
import com.highmobility.autoapi.Identifier;
import com.highmobility.autoapi.Type;
import com.highmobility.value.Bytes;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Nullable;

/**
 * Created by ttiganik on 14/10/2016.
 */
public class Capability extends PropertyValueObject {
    Type[] types;
    byte[] identifierBytes;
    Identifier identifier;

    /**
     * @return All of the command types supported for this category
     */
    public Type[] getTypes() {
        return types;
    }

    /**
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
     * @return The command's category identifier.
     */
    public byte[] getIdentifierBytes() {
        return identifierBytes;
    }

    /**
     * @return The command's identifier.
     */
    @Nullable public Identifier getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier The identifier of the category.
     * @param types      All the types supported for the given category identifier.
     * @throws IllegalArgumentException when types are not from the same category or parameters are
     *                                  invalid.
     */
    public Capability(Identifier identifier, Type[] types) {
        this(identifier.getBytes(), types);
    }

    /**
     * @param identifier The 2 byte identifier of the category.
     * @param types      All the types supported for the given category identifier.
     * @throws IllegalArgumentException when types are not from the same category or parameters are
     *                                  invalid.
     */
    public Capability(byte[] identifier, Type[] types) {
        super(2 + types.length);
        update(identifier, types);
    }

    public Capability() {
        super();
    } // need for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        identifierBytes = value.getRange(0, 2).getByteArray();

        ArrayList<Type> builder = new ArrayList<>();

        for (int i = 2; i < value.getLength(); i++) {
            Type type = new Type(identifierBytes, bytes[i]);
            builder.add(type);
        }

        types = builder.toArray(new Type[0]);
    }

    public void update(byte[] categoryIdentifier, Type[] types) {
        this.identifierBytes = categoryIdentifier;

        Type getCommand = null;
        boolean stateExists = false;
        for (int i = 0; i < types.length; i++) {
            Type type = types[i];
            if (type.getType() == 0x00) getCommand = type;
            if (type.getType() == 0x01) stateExists = true;
        }

        if (getCommand != null && stateExists == false) {
            Type stateType = getCommand.getStateCommand();
            if (stateType != null) {
                types = Arrays.copyOf(types, types.length + 1);
                types[types.length - 1] = stateType;
            }
        }

        this.types = types;
        bytes = new byte[2 + types.length];

        byte[] typeBytes = new byte[types.length];
        for (int i = 0; i < types.length; i++) {
            Type type = types[i];
            typeBytes[i] = type.getType();
            if (type.getIdentifierAndType()[0] != categoryIdentifier[0]
                    || type.getIdentifierAndType()[1] != categoryIdentifier[1])
                throw new IllegalArgumentException();
        }

        set(0, categoryIdentifier);
        set(2, typeBytes);
    }

    public void update(Capability value) {
        update(value.identifierBytes, value.types);
    }
}
