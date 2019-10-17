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
import com.highmobility.autoapi.value.SpringRate;
import java.util.ArrayList;

/**
 * Set spring rates
 */
public class SetSpringRates extends SetCommand {
    public static final int IDENTIFIER = Identifier.CHASSIS_SETTINGS;

    public static final byte IDENTIFIER_CURRENT_SPRING_RATES = 0x05;

    Property<SpringRate>[] currentSpringRates;

    /**
     * @return The current spring rates
     */
    public Property<SpringRate>[] getCurrentSpringRates() {
        return currentSpringRates;
    }
    
    /**
     * Set spring rates
     *
     * @param currentSpringRates The current values for the spring rates
     */
    public SetSpringRates(SpringRate[] currentSpringRates) {
        super(IDENTIFIER);
    
        ArrayList<Property> currentSpringRatesBuilder = new ArrayList<>();
        if (currentSpringRates != null) {
            for (SpringRate currentSpringRate : currentSpringRates) {
                Property prop = new Property(0x05, currentSpringRate);
                currentSpringRatesBuilder.add(prop);
                addProperty(prop, true);
            }
        }
        this.currentSpringRates = currentSpringRatesBuilder.toArray(new Property[0]);
    }

    SetSpringRates(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<SpringRate>> currentSpringRatesBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_CURRENT_SPRING_RATES: {
                        Property currentSpringRate = new Property(SpringRate.class, p);
                        currentSpringRatesBuilder.add(currentSpringRate);
                        return currentSpringRate;
                    }
                }
                return null;
            });
        }
    
        currentSpringRates = currentSpringRatesBuilder.toArray(new Property[0]);
        if (this.currentSpringRates.length == 0) 
            throw new NoPropertiesException();
    }
}