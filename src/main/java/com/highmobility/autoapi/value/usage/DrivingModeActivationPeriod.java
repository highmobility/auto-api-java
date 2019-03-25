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

public class DrivingModeActivationPeriod extends PropertyValueObject {
    DrivingMode drivingMode;
    Double percentage;

    /**
     * @return The driving mode.
     */
    public DrivingMode getDrivingMode() {
        return drivingMode;
    }

    /**
     * @return The activation period.
     */
    public Double getPercentage() {
        return percentage;
    }

    public DrivingModeActivationPeriod(DrivingMode drivingMode, Double percentage) {
        super(9);
        update(drivingMode, percentage);
    }

    public DrivingModeActivationPeriod() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 9) throw new CommandParseException();
        drivingMode = DrivingMode.fromByte(get(0));
        percentage = Property.getDouble(getRange(1, 9));
    }

    public void update(DrivingMode drivingMode, Double percentage) {
        this.drivingMode = drivingMode;
        this.percentage = percentage;

        set(0, drivingMode.getByte());
        set(1, Property.doubleToBytes(percentage));
    }

    public void update(DrivingModeActivationPeriod value) {
        update(value.drivingMode, value.percentage);
    }
}