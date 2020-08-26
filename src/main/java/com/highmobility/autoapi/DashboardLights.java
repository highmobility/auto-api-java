/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.DashboardLight;
import java.util.ArrayList;
import java.util.List;

/**
 * The Dashboard Lights capability
 */
public class DashboardLights {
    public static final int IDENTIFIER = Identifier.DASHBOARD_LIGHTS;

    public static final byte PROPERTY_DASHBOARD_LIGHTS = 0x01;

    /**
     * Get dashboard lights
     */
    public static class GetDashboardLights extends GetCommand<State> {
        public GetDashboardLights() {
            super(State.class, IDENTIFIER);
        }
    
        GetDashboardLights(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The dashboard lights state
     */
    public static class State extends SetCommand {
        List<Property<DashboardLight>> dashboardLights;
    
        /**
         * @return The dashboard lights
         */
        public List<Property<DashboardLight>> getDashboardLights() {
            return dashboardLights;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            final ArrayList<Property<DashboardLight>> dashboardLightsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DASHBOARD_LIGHTS:
                            Property<DashboardLight> dashboardLight = new Property<>(DashboardLight.class, p);
                            dashboardLightsBuilder.add(dashboardLight);
                            return dashboardLight;
                    }
    
                    return null;
                });
            }
    
            dashboardLights = dashboardLightsBuilder;
        }
    
        private State(Builder builder) {
            super(builder);
    
            dashboardLights = builder.dashboardLights;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private final List<Property<DashboardLight>> dashboardLights = new ArrayList<>();
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
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
                dashboardLight.setIdentifier(PROPERTY_DASHBOARD_LIGHTS);
                addProperty(dashboardLight);
                dashboardLights.add(dashboardLight);
                return this;
            }
        }
    }
}