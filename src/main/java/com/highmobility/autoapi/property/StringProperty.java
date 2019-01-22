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

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.exception.ParseException;
import com.highmobility.utils.ByteUtils;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.annotation.Nullable;

public class StringProperty extends Property {
    public static final String CHARSET = "UTF-8";

    String value;

    public StringProperty(byte identifier) {
        super(identifier);
    }

    @Nullable public String getValue() {
        return value;
    }

    public StringProperty(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    public StringProperty(String value) {
        this((byte) 0x00, value);
    }

    public StringProperty(byte identifier, String value) {
        super(identifier, value != null ? value.length() : 0);
        if (value != null) {
            byte[] stringBytes;
            try {
                stringBytes = value.getBytes(CHARSET);
            } catch (UnsupportedEncodingException e) {
                Command.logger.info(CHARSET + " charset not supported.");
                throw new ParseException();
            }
            ByteUtils.setBytes(bytes, stringBytes, 3);
        }

        this.value = value;
    }

    /**
     * @param value     The string value.
     * @param timestamp The property timestamp.
     * @param failure   The property failure.
     */
    public StringProperty(@Nullable String value, @Nullable Calendar timestamp,
                          @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);

        if (p.getValueLength() != 0) {
            try {
                this.value = new String(getValueBytes(), CHARSET);
            } catch (UnsupportedEncodingException e) {
                this.value = "Unsupported encoding";
            }
        }

        return this;
    }

    public static Property[] getProperties(String value, byte identifier) {
        return new Property[]{new StringProperty(identifier, value)};
    }
}