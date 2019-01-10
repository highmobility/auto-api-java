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

import java.util.ArrayList;
import java.util.List;

/**
 * Command sent when a Get Capabilities command is received by the car.
 */
public class Capabilities extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CAPABILITIES, 0x01);
    private static final byte IDENTIFIER = 0x01;

    CapabilityProperty[] capabilities;

    public boolean isSupported(Type type) {
        CapabilityProperty capability = getCapability(type);
        if (capability == null) return false;

        return capability.isSupported(type);
    }

    /**
     * Get the capability for a specific command type.
     *
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

    Capabilities(byte[] bytes) {
        super(bytes);
        ArrayList<CapabilityProperty> builder = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER:
                        CapabilityProperty capability =
                                new CapabilityProperty(p.getPropertyBytes());
                        builder.add(capability);
                        return capability;
                }
                return null;
            });
        }

        capabilities = builder.toArray(new CapabilityProperty[0]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }

    private Capabilities(Builder builder) {
        super(builder);
        capabilities = builder.capabilities.toArray(new CapabilityProperty[0]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<CapabilityProperty> capabilities = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * Add a capability.
         *
         * @param capability The capability.
         * @return The builder.
         */
        public Builder addCapability(CapabilityProperty capability) {
            capability.setIdentifier(IDENTIFIER);
            capabilities.add(capability);
            addProperty(capability);
            return this;
        }

        /**
         * Set the capabilities.
         *
         * @param capabilities The capabilities.
         * @return The builder.
         */
        public Builder setCapabilities(CapabilityProperty[] capabilities) {
            this.capabilities.clear();
            for (int i = 0; i < capabilities.length; i++) addCapability(capabilities[i]);
            return this;
        }

        public Capabilities build() {
            return new Capabilities(this);
        }
    }
}