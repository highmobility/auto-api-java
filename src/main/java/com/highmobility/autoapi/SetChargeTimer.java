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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.charging.ChargingTimer;

import java.util.ArrayList;
import java.util.List;

/**
 * Set the charge timer of the car. The command can include one of the different timer types or
 * all.
 */
public class SetChargeTimer extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x16);

    public static final byte PROPERTY_IDENTIFIER = 0x0D;

    Property<ChargingTimer>[] timers;

    /**
     * @return The charge timers.
     */
    public Property<ChargingTimer>[] getChargingTimers() {
        return timers;
    }

    /**
     * Get a charge timer for a specific timer type. Null if doesn't exist.
     *
     * @param type The charge timer type.
     * @return The charge timer.
     */
    public Property<ChargingTimer> getChargingTimer(ChargingTimer.Type type) {
        if (timers == null) return null;
        for (int i = 0; i < timers.length; i++) {
            Property<ChargingTimer> timer = timers[i];
            if (timer.getValue() != null && timer.getValue().getType() == type)
                return timer;
        }
        return null;
    }

    /**
     * @param timers The charging timers.
     */
    public SetChargeTimer(ChargingTimer[] timers) {
        super(TYPE);

        ArrayList<Property> builder = new ArrayList<>();

        for (ChargingTimer timer : timers) {

            for (ChargingTimer timer2 : timers) {
                if (timer2 != timer && timer2.getType() == timer.getType())
                    throw new IllegalArgumentException();
            }

            Property<ChargingTimer> prop = new Property(PROPERTY_IDENTIFIER, timer);
            builder.add(prop);
        }

        this.timers = builder.toArray(new Property[0]);
        createBytes(builder);
    }

    SetChargeTimer(byte[] bytes) {
        super(bytes);
        List<Property<ChargingTimer>> builder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case PROPERTY_IDENTIFIER:
                        Property<ChargingTimer> timer =
                                new Property(ChargingTimer.class, p);
                        builder.add(timer);
                        return timer;
                }
                return null;
            });
        }

        timers = builder.toArray(new Property[0]);
    }
}
