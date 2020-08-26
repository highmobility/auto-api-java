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
package com.highmobility.autoapi.property;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.value.Bytes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;

import javax.annotation.Nullable;

public class PropertyComponentValue<V> extends PropertyComponent {
    static final byte IDENTIFIER = 0x01;

    Class<V> valueClass = null;
    @Nullable
    protected V value;

    @Nullable
    public V getValue() {
        return value;
    }

    @Nullable
    public Byte getValueByte() {
        return valueBytes != null && valueBytes.getLength() > 0 ? valueBytes.get(0) : null;
    }

    public Class<V> getValueClass() {
        return valueClass;
    }

    PropertyComponentValue(byte identifier, int valueSize) {
        super(identifier, valueSize);
    }

    PropertyComponentValue(Bytes value, Class<V> valueClass) throws CommandParseException {
        super(value);
        if (valueClass != null) setClass(valueClass);
    }

    PropertyComponentValue(@Nullable V value) {
        super(IDENTIFIER, getBytes(value));
        valueClass = (Class<V>) value.getClass();
        this.value = value;
    }

    public void setClass(Class<V> valueClass) throws CommandParseException {
        // map bytes to the type
        if (PropertyValueObject.class.isAssignableFrom(valueClass)) {
            try {
                // constructing PropertyValueObject subclasses can throw CommandParseException.
                Constructor constructor = valueClass.getConstructor(new Class[]{Bytes.class});
                V parsedValue = (V) constructor.newInstance(new Object[]{valueBytes});
                this.value = parsedValue;
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Cannot instantiate value: " + valueClass +
                        "\n" + e.getMessage());
            } catch (InvocationTargetException e) {
                // throw error that is from auto api value parsing
                if (e.getCause() instanceof CommandParseException) {
                    throw (CommandParseException) e.getCause();
                } else {
                    e.printStackTrace();
                    throw new CommandParseException("Value initialisation error");
                }
            }
        } else if (ByteEnum.class.isAssignableFrom(valueClass)) {
            value = valueClass.getEnumConstants()[valueBytes.get(0)];
        } else if (Boolean.class.isAssignableFrom(valueClass)) {
            value = (V) Property.getBool(valueBytes.get(0));
        } else if (Float.class.isAssignableFrom(valueClass)) {
            value = (V) (Float) Property.getFloat(valueBytes);
        } else if (Double.class.isAssignableFrom(valueClass)) {
            value = (V) (Double) Property.getDouble(valueBytes);
        } else if (Calendar.class.isAssignableFrom(valueClass)) {
            value = (V) Property.getCalendar(valueBytes);
        } else if (String.class.isAssignableFrom(valueClass)) {
            value = (V) Property.getString(valueBytes);
        } else if (int[].class.isAssignableFrom(valueClass)) {
            value = (V) Property.getIntegerArray(valueBytes);
        } else if (Command.class.isAssignableFrom(valueClass)) {
            value = (V) CommandResolver.resolve(valueBytes);
        } else if (Bytes.class.isAssignableFrom(valueClass)) {
            value = (V) valueBytes;
        } else if (Byte.class.isAssignableFrom(valueClass)) {
            value = (V) valueBytes.get(0);
        }

        this.valueClass = valueClass;
    }

    public static Bytes getBytes(Object value) {
        // this is for builder/set command
        if (value instanceof PropertyValueObject) {
            return ((PropertyValueObject) value);
        } else if (value instanceof ByteEnum) {
            byte byteValue = ((ByteEnum) value).getByte();
            return new Bytes(new byte[]{byteValue});
        } else if (value instanceof Boolean) {
            return new Bytes(new byte[]{Property.boolToByte((Boolean) value)});
        } else if (value instanceof Number) {
            if (value instanceof Float) {
                return new Bytes(Property.floatToBytes((Float) value));
            } else if (value instanceof Integer) {
                // integer length is defaulted to 4 here. It should be set to the correct one in
                // the IntegerProperty's update.
                return new Bytes(Property.intToBytes((Integer) value, 4));
            } else if (value instanceof Double) {
                return new Bytes(Property.doubleToBytes((Double) value));
            } else if (value instanceof Byte) {
                return new Bytes(new byte[]{(Byte) value});
            }
        } else if (value instanceof String) {
            return new Bytes(Property.stringToBytes((String) value));
        } else if (value instanceof Calendar) {
            return new Bytes(Property.calendarToBytes((Calendar) value));
        } else if (value instanceof int[]) {
            return Property.integerArrayToBytes((int[]) value);
        } else if (value instanceof Command) {
            return (Command) value;
        } else if (value instanceof Bytes) {
            return (Bytes) value;
        } else {
            throw new IllegalArgumentException("Type " + (value != null ? value.getClass() :
                    "null") + " not supported for Property");
        }

        return null;
    }

    public static final int CALENDAR_SIZE = 8;
    public static final String CHARSET = "UTF-8";
}
