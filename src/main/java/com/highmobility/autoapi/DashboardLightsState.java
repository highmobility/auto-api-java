// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.DashboardLight;
import java.util.ArrayList;
import java.util.List;

public class DashboardLightsState extends SetCommand {
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
                    case 0x01:
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
            super(Identifier.DASHBOARD_LIGHTS);
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
            dashboardLight.setIdentifier(0x01);
            addProperty(dashboardLight);
            dashboardLights.add(dashboardLight);
            return this;
        }
    }
}