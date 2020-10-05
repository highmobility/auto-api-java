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
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.value.Bytes;

public class CheckControlMessage extends PropertyValueObject {
    Integer ID;
    Duration remainingTime;
    String text;
    String status;

    /**
     * @return Check Control Message identifier.
     */
    public Integer getID() {
        return ID;
    }

    /**
     * @return Remaining time of the message.
     */
    public Duration getRemainingTime() {
        return remainingTime;
    }

    /**
     * @return CCM text.
     */
    public String getText() {
        return text;
    }

    /**
     * @return CCM status.
     */
    public String getStatus() {
        return status;
    }

    public CheckControlMessage(Integer ID, Duration remainingTime, String text, String status) {
        super(0);

        this.ID = ID;
        this.remainingTime = remainingTime;
        this.text = text;
        this.status = status;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(ID, 2));
        bytePosition += 2;

        set(bytePosition, remainingTime);
        bytePosition += remainingTime.getLength();

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(text), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(text));
        bytePosition += Property.getUtf8Length(text);

        set(bytePosition, Property.intToBytes(Property.getUtf8Length(status), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(status));
    }

    public CheckControlMessage(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 16) throw new CommandParseException();

        int bytePosition = 0;
        ID = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;

        int remainingTimeSize = Duration.SIZE;
        remainingTime = new Duration(getRange(bytePosition, bytePosition + remainingTimeSize));
        bytePosition += remainingTimeSize;

        int textSize = getItemSize(bytePosition);
        bytePosition += 2;
        text = Property.getString(bytes, bytePosition, textSize);
        bytePosition += textSize;

        int statusSize = getItemSize(bytePosition);
        bytePosition += 2;
        status = Property.getString(bytes, bytePosition, statusSize);
    }

    @Override public int getLength() {
        return 2 + 10 + Property.getUtf8Length(text) + 2 + Property.getUtf8Length(status) + 2;
    }
}