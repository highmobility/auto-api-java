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

package com.highmobility.autoapi.property.diagnostics;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.value.TireLocation;
import com.highmobility.value.Bytes;

public class TirePressure extends PropertyValueObject {
    TireLocation tireLocation;
    float pressure;

    /**
     * @return The tire location
     */
    public TireLocation getTireLocation() {
        return tireLocation;
    }

    /**
     * @return The tire pressure.
     */
    public float getPressure() {
        return pressure;
    }

    public TirePressure(TireLocation tireLocation, float pressure) {
        super(5);
        update(tireLocation, pressure);
    }

    public TirePressure() {
        super(5);
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 5) throw new CommandParseException();
        this.tireLocation = TireLocation.fromByte(get(0));
        this.pressure = Property.getFloat(bytes, 1);
    }

    public void update(TireLocation tireLocation, float pressure) {
        this.tireLocation = tireLocation;
        this.pressure = pressure;

        set(0, tireLocation.getByte());
        set(1, Property.floatToBytes(pressure));
    }

    public void update(TirePressure value) {
        update(value.tireLocation, value.pressure);
    }
}