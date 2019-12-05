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

/**
 * The light conditions state
 */
public class LightConditionsState extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.LIGHT_CONDITIONS;

    public static final byte IDENTIFIER_OUTSIDE_LIGHT = 0x01;
    public static final byte IDENTIFIER_INSIDE_LIGHT = 0x02;

    Property<Float> outsideLight = new Property(Float.class, IDENTIFIER_OUTSIDE_LIGHT);
    Property<Float> insideLight = new Property(Float.class, IDENTIFIER_INSIDE_LIGHT);

    /**
     * @return Measured outside illuminance in lux
     */
    public Property<Float> getOutsideLight() {
        return outsideLight;
    }

    /**
     * @return Measured inside illuminance in lux
     */
    public Property<Float> getInsideLight() {
        return insideLight;
    }

    LightConditionsState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_OUTSIDE_LIGHT: return outsideLight.update(p);
                    case IDENTIFIER_INSIDE_LIGHT: return insideLight.update(p);
                }

                return null;
            });
        }
    }

    private LightConditionsState(Builder builder) {
        super(builder);

        outsideLight = builder.outsideLight;
        insideLight = builder.insideLight;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<Float> outsideLight;
        private Property<Float> insideLight;

        public Builder() {
            super(IDENTIFIER);
        }

        public LightConditionsState build() {
            return new LightConditionsState(this);
        }

        /**
         * @param outsideLight Measured outside illuminance in lux
         * @return The builder
         */
        public Builder setOutsideLight(Property<Float> outsideLight) {
            this.outsideLight = outsideLight.setIdentifier(IDENTIFIER_OUTSIDE_LIGHT);
            addProperty(this.outsideLight);
            return this;
        }
        
        /**
         * @param insideLight Measured inside illuminance in lux
         * @return The builder
         */
        public Builder setInsideLight(Property<Float> insideLight) {
            this.insideLight = insideLight.setIdentifier(IDENTIFIER_INSIDE_LIGHT);
            addProperty(this.insideLight);
            return this;
        }
    }
}