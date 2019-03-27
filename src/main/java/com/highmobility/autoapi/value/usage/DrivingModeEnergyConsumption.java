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

package com.highmobility.autoapi.value.usage;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.value.DrivingMode;
import com.highmobility.value.Bytes;

public class DrivingModeEnergyConsumption extends PropertyValueObject {
    DrivingMode drivingMode;
    Float energyConsumption;

    /**
     * @return The driving mode.
     */
    public DrivingMode getDrivingMode() {
        return drivingMode;
    }

    /**
     * @return The energy consumption in the driving mode in kWh.
     */
    public Float getEnergyConsumption() {
        return energyConsumption;
    }

    public DrivingModeEnergyConsumption(DrivingMode drivingMode, Float energyConsumption) {
        super(5);
        update(drivingMode, energyConsumption);
    }

    public DrivingModeEnergyConsumption() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 5) throw new CommandParseException();
        drivingMode = DrivingMode.fromByte(get(0));
        energyConsumption = Property.getFloat(getRange(1, 5));
    }

    public void update(DrivingMode drivingMode, Float energyConsumption) {
        this.drivingMode = drivingMode;
        this.energyConsumption = energyConsumption;

        set(0, drivingMode.getByte());
        set(1, Property.floatToBytes(energyConsumption));
    }

    public void update(DrivingModeEnergyConsumption value) {
        update(value.drivingMode, value.energyConsumption);
    }
}