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
import com.highmobility.autoapi.property.SpringRate;
import com.highmobility.autoapi.property.value.Axle;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Set the spring rate. The result is sent through the Chassis Settings message.
 */
public class SetSpringRate extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x14);
    private static final byte PROPERTY_IDENTIFIER = 0x01;

    SpringRate[] springRates;

    public SpringRate[] getSpringRates() {
        return springRates;
    }

    /**
     * @param axle The axle.
     * @return The spring rate for the given axle.
     */
    @Nullable public SpringRate getSpringRate(Axle axle) {
        for (SpringRate springRate : springRates) {
            if (springRate.getAxle() == axle)
                return springRate;
        }

        return null;
    }

    /**
     * @param springRates The spring rates.
     */
    public SetSpringRate(SpringRate[] springRates) {
        super(TYPE, getValues(springRates));
        this.springRates = springRates;
    }

    static Property[] getValues(SpringRate[] springRates) {
        ArrayList<Property<SpringRate>> builder = new ArrayList<>();
        for (SpringRate springRate : springRates) {
            builder.add(new Property<>(PROPERTY_IDENTIFIER, springRate));
        }

        return builder.toArray(new Property[0]);
    }

    SetSpringRate(byte[] bytes) {
        super(bytes);
        ArrayList<SpringRate> builder = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                if (p.getPropertyIdentifier() == PROPERTY_IDENTIFIER) {
                    Property<SpringRate> prop = new Property<>(SpringRate.class, p);
                    builder.add(prop.getValue());
                    return prop;
                }
                return null;
            });
        }

        springRates = builder.toArray(new SpringRate[0]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }
}
