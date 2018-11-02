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

    ReductionTime[] reductionTimes;

    /**
     * @return The charging current times reductions.
     */
    public ReductionTime[] getReductionTimes() {
        return reductionTimes;
    }

    /**
     * Set Reduction of Charging-current Times.
     *
     * @param reductionTimes The charging current times reductions..
     */
    public SetReductionOfChargingCurrentTimes(ReductionTime[] reductionTimes) {
        super(TYPE, setIdentifiers(reductionTimes));
        this.reductionTimes = reductionTimes;
    }

    static ReductionTime[] setIdentifiers(ReductionTime[] reductionTimes) {
        for (int i = 0; i < reductionTimes.length; i++) {
            ReductionTime reductionTime = reductionTimes[i];
            reductionTime.setIdentifier(PROPERTY_IDENTIFIER);
        }

        return reductionTimes;
    }

    SetReductionOfChargingCurrentTimes(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 9) throw new CommandParseException();

        List<ReductionTime> builder = new ArrayList<>();

        for (int i = 0; i < properties.length; i++) {
            Property property = properties[i];
            if (property.getPropertyIdentifier() == PROPERTY_IDENTIFIER) {
                builder.add(new ReductionTime(property.getPropertyBytes()));
            }
        }

        reductionTimes = builder.toArray(new ReductionTime[builder.size()]);
    }
}
