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

import com.highmobility.autoapi.property.DashboardLight;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * This message is sent when a Get Dashboard Lights message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class DashboardLights extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.DASHBOARD_LIGHTS, 0x01);
    public static final byte IDENTIFIER_DASHBOARD_LIGHT = 0x01;

    Property<DashboardLight>[] lights;

    /**
     * @return All of the available dashboard lights.
     */
    public Property<DashboardLight>[] getLights() {
        return lights;
    }

    /**
     * Get a dashboard light for the given light type.
     *
     * @param type The dashboard light type.
     * @return The Dashboard light, if exists.
     */
    @Nullable public Property<DashboardLight> getLight(DashboardLight.Type type) {
        for (int i = 0; i < lights.length; i++) {
            Property<DashboardLight> light = lights[i];
            if (light.getValue() != null && light.getValue().getType() == type) return light;
        }

        return null;
    }

    DashboardLights(byte[] bytes) {
        super(bytes);

        List<Property<DashboardLight>> builder = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                if (p.getPropertyIdentifier() == IDENTIFIER_DASHBOARD_LIGHT) {
                    Property<DashboardLight> light =
                            new Property<>(DashboardLight.class, p);
                    builder.add(light);
                    return light;
                }
                return null;
            });
        }

        lights = builder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private DashboardLights(Builder builder) {
        super(builder);
        lights = builder.lights.toArray(new Property[0]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        List<Property<DashboardLight>> lights = new ArrayList<>();

        /**
         * @param lights The dashboard lights.
         * @return The builder.
         */
        public Builder setLights(Property<DashboardLight>[] lights) {
            this.lights = Arrays.asList(lights);
            for (int i = 0; i < lights.length; i++) {
                addProperty(lights[i]);
            }
            return this;
        }

        /**
         * Add a single dashboard light.
         *
         * @param light The Dashboard light.
         * @return The builder.
         */
        public Builder addLight(Property<DashboardLight> light) {
            this.lights.add(light);
            addProperty(light.setIdentifier(IDENTIFIER_DASHBOARD_LIGHT));
            return this;
        }

        public Builder() {
            super(TYPE);
        }

        public DashboardLights build() {
            return new DashboardLights(this);
        }
    }
}