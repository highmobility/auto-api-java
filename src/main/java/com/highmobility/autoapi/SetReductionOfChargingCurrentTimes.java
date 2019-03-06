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
import com.highmobility.autoapi.property.charging.ReductionTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Set Reduction of Charging-current Times.
 */
public class SetReductionOfChargingCurrentTimes extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x17);
    private static final byte PROPERTY_IDENTIFIER = 0x01;

    Property<ReductionTime>[] reductionTimes;

    /**
     * @return The charging current times reductions.
     */
    public Property<ReductionTime>[] getReductionTimes() {
        return reductionTimes;
    }

    /**
     * Set Reduction of Charging-current Times.
     *
     * @param reductionTimes The charging current times reductions..
     */
    public SetReductionOfChargingCurrentTimes(ReductionTime[] reductionTimes) {
        super(TYPE);

        List<Property> builder = new ArrayList<>();

        for (int i = 0; i < reductionTimes.length; i++) {
            ReductionTime time = reductionTimes[i];
            Property<ReductionTime> reductionTime =
                    new Property<>(PROPERTY_IDENTIFIER, time);
            builder.add(reductionTime);
        }

        this.reductionTimes = builder.toArray(new Property[0]);

        createBytes(builder);
    }

    SetReductionOfChargingCurrentTimes(byte[] bytes) {
        super(bytes);

        List<Property> builder = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case PROPERTY_IDENTIFIER:
                        Property<ReductionTime> reductionTimeProperty =
                                new Property<>(ReductionTime.class, p);
                        builder.add(reductionTimeProperty);
                        return reductionTimeProperty;
                }
                return null;
            });
        }

        reductionTimes = builder.toArray(new Property[0]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }
}
