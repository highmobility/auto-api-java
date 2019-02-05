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

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.value.Axle;
import com.highmobility.value.Bytes;

//public class SpringRateProperty extends Property {
    /*Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public SpringRateProperty(byte identifier) {
        super(identifier);
    }

    public SpringRateProperty(Axle axle, Integer springRate) {
        this(new Value(axle, springRate));
    }

    public SpringRateProperty(@Nullable Value value, @Nullable Calendar timestamp,
                              @Nullable PropertyFailure failure) {
        super(value, timestamp, failure);
        update(value);
    }

    public SpringRateProperty(Value value) {
        super(value);
        update(value);
    }

    public SpringRateProperty(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    public SpringRateProperty update(Property p) throws CommandParseException {
        super.update(p);
        ignoreInvalidByteSizeException(() -> value = new Value(p.getValueBytes()));
        // TODO: 2019-01-31 test that IllegalArgument/nullpointer is not thrown and value is null
        return this;
    }

    public SpringRateProperty update(Value value) {
        super.update(value);
        this.value = value;
        return this;
    }
*/
public class SpringRate extends PropertyValueObject {
    Axle axle;
    Integer springRate;

    /**
     * @return The axle.
     */
    public Axle getAxle() {
        return axle;
    }

    /**
     * @return The suspension spring rate in N/mm
     */
    public Integer getSpringRate() {
        return springRate;
    }

    public SpringRate() {
    }

    public SpringRate(Axle axle, int springRate) {
        super();
        this.axle = axle;
        this.springRate = springRate;
        byte[] bytes = new byte[2];
        bytes[0] = axle.getByte();
        bytes[1] = Property.intToBytes(springRate, 1)[0];
        this.bytes = new Bytes(bytes);
    }

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.getLength() >= 2) {
            axle = Axle.fromByte(bytes.get(0));
            springRate = Property.getUnsignedInt(bytes.get(1));
        } else throw new IllegalArgumentException();
    }

    //    }
}