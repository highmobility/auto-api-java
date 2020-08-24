/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

import static com.highmobility.utils.ByteUtils.hexFromByte;

public class TroubleCode extends PropertyValueObject {
    Integer occurrences;
    String ID;
    String ecuID;
    String status;
    System system;

    /**
     * @return Number of occurrences.
     */
    public Integer getOccurrences() {
        return occurrences;
    }

    /**
     * @return Identifier.
     */
    public String getID() {
        return ID;
    }

    /**
     * @return Electronic Control Unit identifier.
     */
    public String getEcuID() {
        return ecuID;
    }

    /**
     * @return Status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return The system.
     */
    public System getSystem() {
        return system;
    }

    public TroubleCode(Integer occurrences, String ID, String ecuID, String status, System system) {
        super(0);

        this.occurrences = occurrences;
        this.ID = ID;
        this.ecuID = ecuID;
        this.status = status;
        this.system = system;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(occurrences, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(ID), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(ID));
        bytePosition += Property.getUtf8Length(ID);

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(ecuID), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(ecuID));
        bytePosition += Property.getUtf8Length(ecuID);

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(status), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(status));
        bytePosition += Property.getUtf8Length(status);

        set(bytePosition, system.getByte());
    }

    public TroubleCode(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 8) throw new CommandParseException();

        int bytePosition = 0;
        occurrences = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        int IDSize = getItemSize(bytePosition);
        bytePosition += 2;
        ID = Property.getString(bytes, bytePosition, IDSize);
        bytePosition += IDSize;

        int ecuIDSize = getItemSize(bytePosition);
        bytePosition += 2;
        ecuID = Property.getString(bytes, bytePosition, ecuIDSize);
        bytePosition += ecuIDSize;

        int statusSize = getItemSize(bytePosition);
        bytePosition += 2;
        status = Property.getString(bytes, bytePosition, statusSize);
        bytePosition += statusSize;

        system = System.fromByte(get(bytePosition));
    }

    @Override public int getLength() {
        return 1 + Property.getUtf8Length(ID) + 2 + Property.getUtf8Length(ecuID) + 2 + Property.getUtf8Length(status) + 2 + 1;
    }

    public enum System implements ByteEnum {
        UNKNOWN((byte) 0x00),
        BODY((byte) 0x01),
        CHASSIS((byte) 0x02),
        POWERTRAIN((byte) 0x03),
        NETWORK((byte) 0x04);
    
        public static System fromByte(byte byteValue) throws CommandParseException {
            System[] values = System.values();
    
            for (int i = 0; i < values.length; i++) {
                System state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum System does not contain " + hexFromByte(byteValue));
        }
    
        private byte value;
    
        System(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}