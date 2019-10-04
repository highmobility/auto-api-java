/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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
import com.highmobility.autoapi.property.PropertyInteger;

/**
 * The offroad state
 */
public class OffroadState extends SetCommand {
    public static final Identifier identifier = Identifier.OFFROAD;

    PropertyInteger routeIncline = new PropertyInteger(0x01, true);
    Property<Double> wheelSuspension = new Property(Double.class, 0x02);

    /**
     * @return The route elevation incline in degrees, which is a negative number for decline
     */
    public PropertyInteger getRouteIncline() {
        return routeIncline;
    }

    /**
     * @return The wheel suspension level percentage, whereas 0.0 is no suspension and 1.0 maximum suspension
     */
    public Property<Double> getWheelSuspension() {
        return wheelSuspension;
    }

    OffroadState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return routeIncline.update(p);
                    case 0x02: return wheelSuspension.update(p);
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

    public static final class Builder extends SetCommand.Builder {
        private PropertyInteger routeIncline;
        private Property<Double> wheelSuspension;

        public Builder() {
            super(identifier);
        }

        public OffroadState build() {
            return new OffroadState(this);
        }

        /**
         * @param routeIncline The route elevation incline in degrees, which is a negative number for decline
         * @return The builder
         */
        public Builder setRouteIncline(Property<Integer> routeIncline) {
            this.routeIncline = new PropertyInteger(0x01, true, 2, routeIncline);
            addProperty(this.routeIncline);
            return this;
        }
        
        /**
         * @param wheelSuspension The wheel suspension level percentage, whereas 0.0 is no suspension and 1.0 maximum suspension
         * @return The builder
         */
        public Builder setWheelSuspension(Property<Double> wheelSuspension) {
            this.wheelSuspension = wheelSuspension.setIdentifier(0x02);
            addProperty(this.wheelSuspension);
            return this;
        }
    }
}