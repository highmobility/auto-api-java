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

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ChargeTimer;
import java.util.ArrayList;

/**
 * Set the charge timer of the car. The command can include one of the different timer types or
 * all.
 */
public class SetChargeTimer extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x06);

    public SetChargeTimer(ChargeTimer[] chargeTimers) {
        super(TYPE, validateTariffs(chargeTimers));
    }

    static ChargeTimer[] validateTariffs(ChargeTimer[] timers) throws IllegalArgumentException {
        ArrayList<ChargeTimer.Type> types = new ArrayList<>(3);
        for (ChargeTimer timer : timers) {
            if (types.contains(timer.getType()) == false) types.add(timer.getType());
            else throw new IllegalArgumentException("Duplicate timer types are not allowed");
        }

        return timers;
    }

    SetChargeTimer(byte[] bytes) {
        super(bytes);
    }
}
