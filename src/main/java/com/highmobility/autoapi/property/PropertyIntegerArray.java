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

//package com.highmobility.autoapi.property;
//
//import com.highmobility.autoapi.CommandParseException;
//
//import javax.annotation.Nullable;
//
//public class PropertyIntegerArray extends Property<int[]> {
//    // int is expected to be 1 byte, unsigned
//    int[] value;
//
//    @Nullable public int[] getValue() {
//        return value;
//    }
//
//    public PropertyIntegerArray(int[] value) {
//        this((byte) 0x00, value);
//    }
//
//    public PropertyIntegerArray(Property p) throws CommandParseException {
//        super(int[].class, p.getPropertyIdentifier());
//        update(p);
//    }
//
//    public PropertyIntegerArray(byte identifier, int[] value) {
//        this(identifier);
//        update(value);
//    }
//
//    public PropertyIntegerArray(byte identifier) {
//        super(int[].class, identifier);
//    }
//
//    @Override public Property update(int[] value) {
//        this.value = value;
//
//
//
//        return this;
//    }
//
//    @Override public Property update(Property p) throws CommandParseException {
//        super.update(p);
//
//        return this;
//    }
//}

// TODO: 2019-03-05 delete class