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

import com.highmobility.autoapi.property.Capability;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Command sent when a Get Capabilities command is received by the car.
 */
public class Capabilities extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CAPABILITIES, 0x01);
    private static final byte IDENTIFIER = 0x01;

    Property<Capability>[] capabilities;

    public boolean isSupported(Type type) {
        Property<Capability> capability = getCapability(type);
        if (capability == null || capability.getValue() == null) return false;

        return capability.getValue().isSupported(type);
    }

    /**
     * Get the capability for a specific command type.
     *
     * @param type The type of the capability.
     * @return The capability if exists, otherwise null
     */
    public Property<Capability> getCapability(Type type) {
        for (int i = 0; i < capabilities.length; i++) {
            Property<Capability> capability = capabilities[i];
            if (capability.getValue() == null) continue;

            if (capability.getValue().getIdentifierBytes()[0] == type.getIdentifierBytes()[0]
                    && capability.getValue().getIdentifierBytes()[1] == type.getIdentifierBytes()[1]) {
                Type[] types = capability.getValue().getTypes();
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
    public Property<Capability>[] getCapabilities() {
        return capabilities;
    }

    Capabilities(byte[] bytes) {
        super(bytes);
        ArrayList<Property> builder = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER:
                        Property capability = new Property(Capability.class, p);
                        builder.add(capability);
                        return capability;
                }
                return null;
            });
        }

        capabilities = builder.toArray(new Property[0]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }

    private Capabilities(Builder builder) {
        super(builder);
        capabilities = builder.capabilities.toArray(new Property[0]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<Property> capabilities = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * Add a capability.
         *
         * @param capability The capability.
         * @return The builder.
         */
        public Builder addCapability(Property<Capability> capability) {
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
        public Builder setCapabilities(Property<Capability>[] capabilities) {
            this.capabilities.clear();
            for (int i = 0; i < capabilities.length; i++) addCapability(capabilities[i]);
            return this;
        }

        public Capabilities build() {
            return new Capabilities(this);
        }
    }
}