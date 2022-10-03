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
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.value.measurement.Temperature;
import com.highmobility.value.Bytes;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

public class TemperatureExtreme extends PropertyValueObject {
    public static final int SIZE = 11;

    Extreme extreme;
    Temperature temperature;

    /**
     * @return The extreme.
     */
    public Extreme getExtreme() {
        return extreme;
    }

    /**
     * @return The temperature.
     */
    public Temperature getTemperature() {
        return temperature;
    }

    public TemperatureExtreme(Extreme extreme, Temperature temperature) {
        super(0);

        this.extreme = extreme;
        this.temperature = temperature;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, extreme.getByte());
        bytePosition += 1;

        set(bytePosition, temperature);
    }

    public TemperatureExtreme(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 11) throw new CommandParseException();

        int bytePosition = 0;
        extreme = Extreme.fromByte(get(bytePosition));
        bytePosition += 1;

        int temperatureSize = Temperature.SIZE;
        temperature = new Temperature(getRange(bytePosition, bytePosition + temperatureSize));
    }

    @Override public int getLength() {
        return 1 + 10;
    }

    public enum Extreme implements ByteEnum {
        HIGHEST((byte) 0x00),
        LOWEST((byte) 0x01);
    
        public static Extreme fromByte(byte byteValue) throws CommandParseException {
            Extreme[] values = Extreme.values();
    
            for (int i = 0; i < values.length; i++) {
                Extreme state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Extreme.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Extreme(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}