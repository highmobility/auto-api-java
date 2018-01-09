package com.highmobility.autoapi;

import com.highmobility.utils.Bytes;

import java.util.Arrays;

public class Type {
    byte[] identifierAndType;
    Identifier identifier; // this is for debug purpose

    byte[] getIdentifierAndType() {
        return identifierAndType;
    }

    byte[] addByte(byte extraByte) {
        return Bytes.concatBytes(identifierAndType, extraByte);
    }

    byte[] getIdentifier() {
        return new byte[] { identifierAndType[0], identifierAndType[1] };
    }

    byte getType() {
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

    Type(Identifier identifier, int type) {
        this(identifier.getBytesWithType((byte) type));
    }

    Type(int identifierFirstByte, int identifierSecondByte, int type) {
        this(new byte[] { (byte) identifierFirstByte, (byte) identifierSecondByte, (byte) type} );
    }

    Type(byte[] identifier, int type) {
        this(Bytes.concatBytes(identifier, (byte) type));
    }

    @Override public boolean equals(Object obj) {
        return obj.getClass() == Type.class
                && Arrays.equals(((Type)obj).getIdentifierAndType(), getIdentifierAndType());
    }
}