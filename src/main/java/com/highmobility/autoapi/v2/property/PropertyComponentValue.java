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

package com.highmobility.autoapi.v2.property;

import com.highmobility.autoapi.v2.Command;
import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.CommandResolver;
import com.highmobility.autoapi.v2.Identifier;
import com.highmobility.value.Bytes;

import java.util.Calendar;

import javax.annotation.Nullable;

public class PropertyComponentValue<T> extends PropertyComponent {
    static final byte IDENTIFIER = 0x01;

    Class<T> valueClass = null;
    @Nullable protected T value;

    @Nullable public T getValue() {
        return value;
    }

    @Nullable public Byte getValueByte() {
        return valueBytes != null && valueBytes.getLength() > 0 ? valueBytes.get(0) : null;
    }

    public Class<T> getValueClass() {
        return valueClass;
    }

    PropertyComponentValue(byte identifier, int valueSize) {
        super(identifier, valueSize);
    }

    PropertyComponentValue(Bytes value, Class<T> valueClass) throws CommandParseException {
        super(value);
        if (valueClass != null) setClass(valueClass);
    }

    PropertyComponentValue(T value) {
        super(IDENTIFIER, getBytes(value));
        valueClass = (Class<T>) value.getClass();
        this.value = value;
    }

    public void setClass(Class<T> valueClass) throws CommandParseException {
        // map bytes to the type
        if (PropertyValueObject.class.isAssignableFrom(valueClass)) {
            try {
                T parsedValue = valueClass.newInstance();
                ((PropertyValueObject) parsedValue).update(valueBytes);
                this.value = parsedValue;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Cannot instantiate value: " + valueClass +
                        "\n" + e.getMessage());
            }
        } else if (ByteEnum.class.isAssignableFrom(valueClass)) {
            value = valueClass.getEnumConstants()[valueBytes.get(0)];
        } else if (Boolean.class.isAssignableFrom(valueClass)) {
            value = (T) Property.getBool(valueBytes.get(0));
        } else if (Float.class.isAssignableFrom(valueClass)) {
            value = (T) (Float) Property.getFloat(valueBytes);
        } else if (Double.class.isAssignableFrom(valueClass)) {
            value = (T) (Double) Property.getDouble(valueBytes);
        } else if (Calendar.class.isAssignableFrom(valueClass)) {
            value = (T) Property.getCalendar(valueBytes);
        } else if (String.class.isAssignableFrom(valueClass)) {
            value = (T) Property.getString(valueBytes);
        } else if (int[].class.isAssignableFrom(valueClass)) {
            value = (T) Property.getIntegerArray(valueBytes);
        } else if (Command.class.isAssignableFrom(valueClass)) {
            value = (T) CommandResolver.resolve(valueBytes);
        } else if (Identifier.class.isAssignableFrom(valueClass)) {
            value = (T) Property.getIdentifier(valueBytes);
        } else if (Bytes.class.isAssignableFrom(valueClass)) {
            value = (T) valueBytes;
        } else if (Byte.class.isAssignableFrom(valueClass)) {
            value = (T) valueBytes.get(0);
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
        } else if (value instanceof Identifier) {
            return Property.identifierToBytes((Identifier) value);
        } else if (value instanceof Command) {
            return (Command) value;
        } else if (value instanceof Byte) {
            return new Bytes(new byte[]{(Byte) value});
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
