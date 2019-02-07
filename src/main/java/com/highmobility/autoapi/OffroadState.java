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

import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.ObjectPropertyPercentage;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Offroad State is received by the car.
 */
public class OffroadState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.OFF_ROAD, 0x01);

    private static final byte IDENTIFIER_ROUTE_INCLINE = 0x01;
    private static final byte IDENTIFIER_WHEEL_SUSPENSION = 0x02;

    ObjectPropertyInteger routeIncline = new ObjectPropertyInteger(IDENTIFIER_ROUTE_INCLINE, false);
    ObjectPropertyPercentage wheelSuspension =
            new ObjectPropertyPercentage(IDENTIFIER_WHEEL_SUSPENSION);

    /**
     * @return The route elevation incline in degrees, which is a negative number for decline.
     */
    @Nullable public ObjectPropertyInteger getRouteIncline() {
        return routeIncline;
    }

    /**
     * @return The wheel suspension level percentage, whereas 0 is no suspension and 1 maximum.
     * suspension
     */
    @Nullable public ObjectPropertyPercentage getWheelSuspension() {
        return wheelSuspension;
    }

    public OffroadState(byte[] bytes) {
        super(TYPE, bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_ROUTE_INCLINE:
                        return routeIncline.update(p);
                    case IDENTIFIER_WHEEL_SUSPENSION:
                        return wheelSuspension.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private OffroadState(Builder builder) {
        super(builder);
        routeIncline = builder.routeIncline;
        wheelSuspension = builder.wheelSuspension;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private ObjectPropertyInteger routeIncline;
        private ObjectPropertyPercentage wheelSuspension;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param routeIncline The route elevation incline in degrees, which is a negative number
         *                     for decline.
         * @return The builder.
         */
        public Builder setRouteIncline(ObjectPropertyInteger routeIncline) {
            this.routeIncline = routeIncline;
            routeIncline.update(IDENTIFIER_ROUTE_INCLINE, false, 2);
            addProperty(routeIncline);
            return this;
        }

        /**
         * @param wheelSuspension The wheel suspension level percentage, whereas 0 is no suspension
         *                        and 1 maximum suspension.
         * @return The builder.
         */
        public Builder setWheelSuspension(ObjectPropertyPercentage wheelSuspension) {
            this.wheelSuspension = wheelSuspension;
            wheelSuspension.setIdentifier(IDENTIFIER_WHEEL_SUSPENSION);
            addProperty(wheelSuspension);
            return this;
        }

        public OffroadState build() {
            return new OffroadState(this);
        }
    }
}