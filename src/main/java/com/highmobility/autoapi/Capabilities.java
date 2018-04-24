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

import com.highmobility.autoapi.property.CapabilityProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This message is sent when a Get Capabilities message is received by the car. The capabilities are
 * passed along as an array.
 */
public class Capabilities extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CAPABILITIES, 0x01);

    CapabilityProperty[] capabilities;

    public boolean isSupported(Type type) {
        CapabilityProperty capability = getCapability(type);
        if (capability == null) return false;

        return capability.isSupported(type);
    }

    /**
     * @param type The type of the capability.
     * @return The capability if exists, otherwise null
     */
    public CapabilityProperty getCapability(Type type) {
        for (int i = 0; i < capabilities.length; i++) {
            CapabilityProperty capability = capabilities[i];
            if (capability.getIdentifierBytes()[0] == type.getIdentifier()[0]
                    && capability.getIdentifierBytes()[1] == type.getIdentifier()[1]) {
                Type[] types = capability.getTypes();
                for (int j = 0; j < types.length; j++) {
                    Type existingType = types[j];
                    if (existingType.getType() == type.getType()) {
                        return capability;
                    }
                }
            }
        }

        return null;
    }

    /**
     * @return All of the Capabilities that are available for the vehicle.
     */
    public CapabilityProperty[] getCapabilities() {
        return capabilities;
    }

    public Capabilities(byte[] bytes) {
        super(bytes);
        ArrayList<CapabilityProperty> builder = new ArrayList<>();
        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    try {
                        CapabilityProperty capability = new CapabilityProperty(property
                                .getPropertyBytes());
                        builder.add(capability);
                    } catch (Exception e) {
                        // don't use unknown capability
                    }
                    break;
            }
        }

        capabilities = builder.toArray(new CapabilityProperty[builder.size()]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }

    private Capabilities(Builder builder) {
        super(builder);
        capabilities = builder.capabilities.toArray(new CapabilityProperty[builder.capabilities
                .size()]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<CapabilityProperty> capabilities = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * Add an array of capabilities.
         *
         * @param capabilities The capabilities.
         * @return The builder.
         */
        public Builder addCapabilities(CapabilityProperty[] capabilities) {
            this.capabilities.addAll(Arrays.asList(capabilities));

            for (int i = 0; i < capabilities.length; i++) {
                addProperty(capabilities[i]);
            }

            return this;
        }

        /**
         * Add a capability.
         *
         * @param capability The capability.
         * @return The builder.
         */
        public Builder addCapability(CapabilityProperty capability) {
            capabilities.add(capability);
            addProperty(capability);
            return this;
        }


        /**
         * Add an array of capabilities.
         *
         * @param capabilities The capabilities.
         * @return The builder.
         * @deprecated use {@link #addCapabilities(CapabilityProperty[])} instead.
         */
        @Deprecated
        public Builder setCapabilities(CapabilityProperty[] capabilities) {
            this.capabilities.addAll(Arrays.asList(capabilities));

            for (int i = 0; i < capabilities.length; i++) {
                addProperty(capabilities[i]);
            }

            return this;
        }

        public Capabilities build() {
            return new Capabilities(this);
        }
    }
}