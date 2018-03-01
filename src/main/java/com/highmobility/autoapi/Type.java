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

public class Type {
    byte[] identifierAndType;
    Identifier identifier; // this is for debug purpose

    public byte[] getIdentifierAndType() {
        return identifierAndType;
    }

    public byte getType() {
        return identifierAndType[2];
    }

    /**
     *
     * @param identifierAndType 2 identifier bytes and 1 type byte
     */
    public Type(byte[] identifierAndType) {
        this.identifierAndType = identifierAndType;
        identifier = Identifier.fromBytes(identifierAndType[0], identifierAndType[1]);
    }

    public Type(byte[] identifier, int type) {
        this(Bytes.concatBytes(identifier, (byte) type));
    }

    Type(Identifier identifier, int type) {
        this(identifier.getBytesWithType((byte) type));
    }

    Type(int identifierFirstByte, int identifierSecondByte, int type) {
        this(new byte[] { (byte) identifierFirstByte, (byte) identifierSecondByte, (byte) type} );
    }

    byte[] addByte(byte extraByte) {
        return Bytes.concatBytes(identifierAndType, extraByte);
    }

    byte[] addBytes(byte[] extraBytes) {
        return Bytes.concatBytes(identifierAndType, extraBytes);
    }

    byte[] getIdentifier() {
        return new byte[] { identifierAndType[0], identifierAndType[1] };
    }

//    boolean isGetStateCommand() {
//        return identifier == 0x00 && type !=
//    }

    @Override public boolean equals(Object obj) {
        return obj.getClass() == Type.class
                && Arrays.equals(((Type)obj).getIdentifierAndType(), getIdentifierAndType());
    }

    @Override public String toString() {
        return Bytes.hexFromBytes(getIdentifierAndType());
    }
}