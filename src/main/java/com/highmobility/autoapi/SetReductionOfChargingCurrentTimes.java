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
import com.highmobility.autoapi.value.ReductionTime;
import java.util.ArrayList;

/**
 * Set reduction of charging current times
 */
public class SetReductionOfChargingCurrentTimes extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.CHARGING;

    public static final byte IDENTIFIER_REDUCTION_TIMES = 0x13;

    Property<ReductionTime>[] reductionTimes;

    /**
     * @return The reduction times
     */
    public Property<ReductionTime>[] getReductionTimes() {
        return reductionTimes;
    }
    
    /**
     * Set reduction of charging current times
     *
     * @param reductionTimes The reduction times
     */
    public SetReductionOfChargingCurrentTimes(ReductionTime[] reductionTimes) {
        super(IDENTIFIER);
    
        ArrayList<Property> reductionTimesBuilder = new ArrayList<>();
        if (reductionTimes != null) {
            for (ReductionTime reductionTime : reductionTimes) {
                Property prop = new Property(0x13, reductionTime);
                reductionTimesBuilder.add(prop);
                addProperty(prop, true);
            }
        }
        this.reductionTimes = reductionTimesBuilder.toArray(new Property[0]);
    }

    SetReductionOfChargingCurrentTimes(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<ReductionTime>> reductionTimesBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_REDUCTION_TIMES: {
                        Property reductionTime = new Property(ReductionTime.class, p);
                        reductionTimesBuilder.add(reductionTime);
                        return reductionTime;
                    }
                }
                return null;
            });
        }
    
        reductionTimes = reductionTimesBuilder.toArray(new Property[0]);
        if (this.reductionTimes.length == 0) 
            throw new NoPropertiesException();
    }
}