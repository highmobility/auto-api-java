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

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

public class ConditionBasedService extends PropertyValueObject {
    Integer year;
    Integer month;
    Integer id;
    DueStatus dueStatus;
    String text;
    String description;

    /**
     * @return The year.
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @return Value between 1 and 12.
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @return CBS identifier.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return The due status.
     */
    public DueStatus getDueStatus() {
        return dueStatus;
    }

    /**
     * @return CBS text.
     */
    public String getText() {
        return text;
    }

    /**
     * @return Description.
     */
    public String getDescription() {
        return description;
    }

    public ConditionBasedService(Integer year, Integer month, Integer id, DueStatus dueStatus, String text, String description) {
        super(0);

        this.year = year;
        this.month = month;
        this.id = id;
        this.dueStatus = dueStatus;
        this.text = text;
        this.description = description;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(year, 2));
        bytePosition += 2;

        set(bytePosition, Property.intToBytes(month, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(id, 2));
        bytePosition += 2;

        set(bytePosition, dueStatus.getByte());
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(text), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(text));
        bytePosition += Property.getUtf8Length(text);

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(description), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(description));
    }

    public ConditionBasedService(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 10) throw new CommandParseException();

        int bytePosition = 0;
        year = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;

        month = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        id = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;

        dueStatus = DueStatus.fromByte(get(bytePosition));
        bytePosition += 1;

        int textSize = getItemSize(bytePosition);
        bytePosition += 2;
        text = Property.getString(bytes, bytePosition, textSize);
        bytePosition += textSize;

        int descriptionSize = getItemSize(bytePosition);
        bytePosition += 2;
        description = Property.getString(bytes, bytePosition, descriptionSize);
    }

    @Override public int getLength() {
        return 2 + 1 + 2 + 1 + Property.getUtf8Length(text) + 2 + Property.getUtf8Length(description) + 2;
    }

    public enum DueStatus implements ByteEnum {
        OK((byte) 0x00),
        PENDING((byte) 0x01),
        OVERDUE((byte) 0x02);
    
        public static DueStatus fromByte(byte byteValue) throws CommandParseException {
            DueStatus[] values = DueStatus.values();
    
            for (int i = 0; i < values.length; i++) {
                DueStatus state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(DueStatus.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        DueStatus(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}