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
// TODO: 2019-03-05 delete
import java.io.UnsupportedEncodingException;

//public class Property<String> extends Property<String> {
//    public static final String CHARSET = "UTF-8";
//
//    public PropertyString(String value) {
//        this((byte) 0x00, value);
//    }
//
//    public PropertyString(Property p) throws CommandParseException {
//        super(String.class, p.getPropertyIdentifier());
//        update(p);
//    }
//
//    public PropertyString(byte identifier, String value) {
//        this(identifier);
//        update(value);
//    }
//
//    public PropertyString(byte identifier) {
//        super(String.class, identifier);
//    }
//
//    @Override public Property update(String value) {
//        if (value != null) {
//
//        }
//
//        this.value = value;
//        return this;
//    }
//
//    @Override public Property update(Property p) throws CommandParseException {
//        super.update(p);
//        if (p.getValueLength() != 0) {
//            try {
//                this.value = new String(getValueBytesArray(), CHARSET);
//            } catch (UnsupportedEncodingException e) {
//                this.value = "Unsupported encoding";
//            }
//        }
//
//        return this;
//    }
//}