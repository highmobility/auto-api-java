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

import com.highmobility.autoapi.property.SpringRateProperty;
import com.highmobility.autoapi.property.value.Axle;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Set the spring rate. The result is sent through the Chassis Settings message.
 */
public class SetSpringRate extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x14);
    private static final byte PROPERTY_IDENTIFIER = 0x01;

    SpringRateProperty[] springRates;

    public SpringRateProperty[] getSpringRates() {
        return springRates;
    }

    /**
     * @param axle The axle.
     * @return The spring rate for the given axle.
     */
    @Nullable public SpringRateProperty getSpringRate(Axle axle) {
        for (SpringRateProperty springRate : springRates) {
            if (springRate.getValue() != null && springRate.getValue().getAxle() == axle)
                return springRate;
        }

        return null;
    }

    /**
     * @param springRates The spring rates.
     */
    public SetSpringRate(SpringRateProperty[] springRates) {
        super(TYPE, getValues(springRates));
        this.springRates = springRates;
    }

    static SpringRateProperty[] getValues(SpringRateProperty[] springRates) {
        for (SpringRateProperty springRate : springRates) {
            springRate.setIdentifier(PROPERTY_IDENTIFIER);
        }

        return springRates;
    }

    SetSpringRate(byte[] bytes) {
        super(bytes);
        ArrayList<SpringRateProperty> builder = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                if (p.getPropertyIdentifier() == PROPERTY_IDENTIFIER) {
                    builder.add(new SpringRateProperty(p));
                }
                return null; // TODO: 12/11/2018
            });
        }

        springRates = builder.toArray(new SpringRateProperty[0]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }
}
