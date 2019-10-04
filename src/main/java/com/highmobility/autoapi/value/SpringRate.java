/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class SpringRate extends PropertyValueObject {
    public static final int SIZE = 2;

    Axle axle;
    Integer springRate;

    /**
     * @return The axle.
     */
    public Axle getAxle() {
        return axle;
    }

    /**
     * @return The suspension spring rate in N/mm.
     */
    public Integer getSpringRate() {
        return springRate;
    }

    public SpringRate(Axle axle, Integer springRate) {
        super(2);
        update(axle, springRate);
    }

    public SpringRate(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public SpringRate() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        axle = Axle.fromByte(get(bytePosition));
        bytePosition += 1;

        springRate = Property.getUnsignedInt(bytes, bytePosition, 1);
    }

    public void update(Axle axle, Integer springRate) {
        this.axle = axle;
        this.springRate = springRate;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, axle.getByte());
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(springRate, 1));
    }

    public void update(SpringRate value) {
        update(value.axle, value.springRate);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}