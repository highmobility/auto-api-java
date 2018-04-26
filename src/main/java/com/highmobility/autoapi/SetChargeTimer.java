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
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Set the charge timer of the car. The command can include one of the different timer types or
 * all.
 */
public class SetChargeTimer extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x06);

    ChargeTimer[] timers;

    /**
     * @return The charge timers.
     */
    public ChargeTimer[] getChargeTimers() {
        return timers;
    }

    /**
     * Get a charge timer for a specific timer type. Null if doesn't exist.
     *
     * @param type The charge timer type.
     * @return The charge timer.
     */
    public ChargeTimer getChargeTimer(ChargeTimer.Type type) {
        if (timers == null) return null;
        for (int i = 0; i < timers.length; i++) {
            ChargeTimer timer = timers[i];
            if (timer.getType() == type) return timer;
        }
        return null;
    }

    public SetChargeTimer(ChargeTimer[] chargeTimers) {
        super(TYPE, validateTariffs(chargeTimers));
        this.timers = chargeTimers;
    }

    static ChargeTimer[] validateTariffs(ChargeTimer[] timers) throws IllegalArgumentException {
        ArrayList<ChargeTimer.Type> types = new ArrayList<>(3);
        for (ChargeTimer timer : timers) {
            if (types.contains(timer.getType()) == false) types.add(timer.getType());
            else throw new IllegalArgumentException("Duplicate timer types are not allowed");
        }

        return timers;
    }

    SetChargeTimer(byte[] bytes) throws CommandParseException {
        super(bytes);
        List<ChargeTimer> builder = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            if (property.getPropertyIdentifier() == 0x0D) {
                builder.add(new ChargeTimer(property.getPropertyBytes()));
            }
        }

        timers = builder.toArray(new ChargeTimer[builder.size()]);
    }
}
