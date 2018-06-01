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

import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.Property;

/**
 * This command is sent when a Get Light Conditions is received by the car.
 */
public class LightConditions extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.LIGHT_CONDITIONS, 0x01);
    private static final byte OUTSIDE_LIGHT_IDENTIFIER = 0x01;
    private static final byte INSIDE_LIGHT_IDENTIFIER = 0x02;
    Float outsideLight;
    Float insideLight;

    /**
     * @return The measured outside illuminance in lux.
     */
    public Float getOutsideLight() {
        return outsideLight;
    }

    /**
     * @return The measured inside illuminance in lux.
     */
    public Float getInsideLight() {
        return insideLight;
    }

    public LightConditions(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case OUTSIDE_LIGHT_IDENTIFIER:
                    outsideLight = Property.getFloat(property.getValueBytes());
                    break;
                case INSIDE_LIGHT_IDENTIFIER:
                    insideLight = Property.getFloat(property.getValueBytes());
                    break;
            }
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
        private Float outsideLight;
        private Float insideLight;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param outsideLight The measured outside illuminance in lux.
         * @return The builder.
         */
        public Builder setOutsideLight(float outsideLight) {
            this.outsideLight = outsideLight;
            addProperty(new FloatProperty(OUTSIDE_LIGHT_IDENTIFIER, outsideLight));
            return this;
        }

        /**
         * @param insideLight The measured inside illuminance in lux.
         * @return The builder.
         */
        public Builder setInsideLight(float insideLight) {
            this.insideLight = insideLight;
            addProperty(new FloatProperty(INSIDE_LIGHT_IDENTIFIER, insideLight));
            return this;
        }

        public LightConditions build() {
            return new LightConditions(this);
        }
    }
}