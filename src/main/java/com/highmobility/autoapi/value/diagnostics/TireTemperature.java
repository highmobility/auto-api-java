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

package com.highmobility.autoapi.value.diagnostics;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.value.TireLocation;
import com.highmobility.value.Bytes;

public class TireTemperature extends PropertyValueObject {
    TireLocation tireLocation;
    float temperature;

    /**
     * @return The tire location.
     */
    public TireLocation getTireLocation() {
        return tireLocation;
    }

    /**
     * @return The tire temperature.
     */
    public float getTemperature() {
        return temperature;
    }

    public TireTemperature(TireLocation tireLocation, float temperature) {
        super(5);
        update(tireLocation, temperature);
    }

    public TireTemperature() {
        super(5);
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 5) throw new CommandParseException();
        this.tireLocation = TireLocation.fromByte(get(0));
        this.temperature = Property.getFloat(bytes, 1);
    }

    public void update(TireLocation tireLocation, float temperature) {
        this.tireLocation = tireLocation;
        this.temperature = temperature;

        set(0, tireLocation.getByte());
        set(1, Property.floatToBytes(temperature));
    }

    public void update(TireTemperature pressure) {
        update(pressure.tireLocation, pressure.temperature);
    }
}
