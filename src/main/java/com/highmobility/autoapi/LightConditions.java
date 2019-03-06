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

import javax.annotation.Nullable;

/**
 * This command is sent when a Get Light Conditions is received by the car.
 */
public class LightConditions extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.LIGHT_CONDITIONS, 0x01);
    private static final byte IDENTIFIER_OUTSIDE_LIGHT = 0x01;
    private static final byte IDENTIFIER_INSIDE_LIGHT = 0x02;
    Property<Float> outsideLight = new Property<>(Float.class,
            IDENTIFIER_OUTSIDE_LIGHT);
    Property<Float> insideLight = new Property<>(Float.class, IDENTIFIER_INSIDE_LIGHT);

    /**
     * @return The measured outside illuminance in lux.
     */
    @Nullable public Property<Float> getOutsideLight() {
        return outsideLight;
    }

    /**
     * @return The measured inside illuminance in lux.
     */
    @Nullable public Property<Float> getInsideLight() {
        return insideLight;
    }

    LightConditions(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_OUTSIDE_LIGHT:
                        return outsideLight.update(p);
                    case IDENTIFIER_INSIDE_LIGHT:
                        return insideLight.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private LightConditions(Builder builder) {
        super(builder);
        insideLight = builder.insideLight;
        outsideLight = builder.outsideLight;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Property<Float> outsideLight;
        private Property<Float> insideLight;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param outsideLight The measured outside illuminance in lux.
         * @return The builder.
         */
        public Builder setOutsideLight(Property<Float> outsideLight) {
            this.outsideLight = outsideLight;
            outsideLight.setIdentifier(IDENTIFIER_OUTSIDE_LIGHT);
            addProperty(outsideLight);
            return this;
        }

        /**
         * @param insideLight The measured inside illuminance in lux.
         * @return The builder.
         */
        public Builder setInsideLight(Property<Float> insideLight) {
            this.insideLight = insideLight;
            insideLight.setIdentifier(IDENTIFIER_INSIDE_LIGHT);
            addProperty(insideLight);
            return this;
        }

        public LightConditions build() {
            return new LightConditions(this);
        }
    }
}