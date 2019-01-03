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

    DashboardLight[] lights;

    /**
     * @return All of the available dashboard lights.
     */
    public DashboardLight[] getLights() {
        return lights;
    }

    /**
     * Get a dashboard light for the given light type.
     *
     * @param type The dashboard light type.
     * @return The Dashboard light, if exists.
     */
    @Nullable public DashboardLight getLight(DashboardLight.Type type) {
        for (int i = 0; i < lights.length; i++) {
            DashboardLight light = lights[i];
            if (light.getType() == type) return light;
        }

        return null;
    }

    DashboardLights(byte[] bytes) {
        super(bytes);

        List<DashboardLight> builder = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                if (p.getPropertyIdentifier() == DashboardLight.IDENTIFIER) {
                    DashboardLight light = new DashboardLight(p.getByteArray());
                    builder.add(light);
                    return light;
                }
                return null;
            });
        }

        lights = builder.toArray(new DashboardLight[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private DashboardLights(Builder builder) {
        super(builder);
        lights = builder.lights.toArray(new DashboardLight[0]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        List<DashboardLight> lights = new ArrayList<>();

        /**
         * @param lights The dashboard lights.
         * @return The builder.
         */
        public Builder setLights(DashboardLight[] lights) {
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
        public Builder addLight(DashboardLight light) {
            this.lights.add(light);
            addProperty(light);
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