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

package com.highmobility.autoapi.property.tachograph;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class DriverCard extends PropertyValueObject {
    int driverNumber;
    boolean present;

    public int getDriverNumber() {
        return driverNumber;
    }

    public boolean isPresent() {
        return present;
    }

    public DriverCard(int driverNumber, boolean present) {
        super(2);
        update(driverNumber, present);
    }

    public DriverCard() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes bytes) throws CommandParseException {
        super.update(bytes);
        if (getLength() < 2) throw new CommandParseException();
        driverNumber = Property.getUnsignedInt(get(0));
        present = Property.getBool(get(1));
    }

    public void update(int driverNumber, boolean present) {
        this.driverNumber = driverNumber;
        this.present = present;
        bytes = new byte[2];
        // TODO: 2019-03-07 drivernumber is unsigned. test if there is overflow eg > 128
        set(0, (byte) driverNumber);
        set(1, Property.boolToByte(present));
    }

    public void update(DriverCard value) {
        update(value.driverNumber, value.present);
    }
}