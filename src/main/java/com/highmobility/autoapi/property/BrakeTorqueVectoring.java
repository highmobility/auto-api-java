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

public class BrakeTorqueVectoring extends PropertyValueObject {
    Axle axle;
    boolean active;

    /**
     * @return The axle.
     */
    public Axle getAxle() {
        return axle;
    }

    /**
     * @return Whether brake torque vectoring is active or not.
     */
    public boolean isActive() {
        return active;
    }
    
    public BrakeTorqueVectoring(Axle axle, boolean active) {
        super(2);
        update(axle, active);
    }

    public BrakeTorqueVectoring() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();
        axle = Axle.fromByte(get(0));
        active = Property.getBool(get(1));
    }

    public void update(Axle axle, boolean active) {
        this.axle = axle;
        this.active = active;
        bytes = new byte[2];

        set(0, axle.getByte());
        set(1, Property.boolToByte(active));
    }

    public void update(BrakeTorqueVectoring value) {
        update(value.axle, value.active);
    }
}