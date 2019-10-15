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
import com.highmobility.autoapi.value.DashboardLight;
import java.util.ArrayList;
import java.util.List;

/**
 * The dashboard lights state
 */
public class DashboardLightsState extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.DASHBOARD_LIGHTS;

    public static final byte IDENTIFIER_DASHBOARD_LIGHTS = 0x01;

    Property<DashboardLight>[] dashboardLights;

    /**
     * @return The dashboard lights
     */
    public Property<DashboardLight>[] getDashboardLights() {
        return dashboardLights;
    }

    DashboardLightsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> dashboardLightsBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_DASHBOARD_LIGHTS:
                        Property<DashboardLight> dashboardLight = new Property(DashboardLight.class, p);
                        dashboardLightsBuilder.add(dashboardLight);
                        return dashboardLight;
                }

                return null;
            });
        }

        dashboardLights = dashboardLightsBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private DashboardLightsState(Builder builder) {
        super(builder);

        dashboardLights = builder.dashboardLights.toArray(new Property[0]);
    }

    public static final class Builder extends SetCommand.Builder {
        private List<Property> dashboardLights = new ArrayList<>();

        public Builder() {
            super(IDENTIFIER);
        }

        public DashboardLightsState build() {
            return new DashboardLightsState(this);
        }

        /**
         * Add an array of dashboard lights.
         * 
         * @param dashboardLights The dashboard lights
         * @return The builder
         */
        public Builder setDashboardLights(Property<DashboardLight>[] dashboardLights) {
            this.dashboardLights.clear();
            for (int i = 0; i < dashboardLights.length; i++) {
                addDashboardLight(dashboardLights[i]);
            }
        
            return this;
        }
        /**
         * Add a single dashboard light.
         * 
         * @param dashboardLight The dashboard light
         * @return The builder
         */
        public Builder addDashboardLight(Property<DashboardLight> dashboardLight) {
            dashboardLight.setIdentifier(IDENTIFIER_DASHBOARD_LIGHTS);
            addProperty(dashboardLight);
            dashboardLights.add(dashboardLight);
            return this;
        }
    }
}