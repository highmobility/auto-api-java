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
            CapabilityProperty capa = capabilities[i];
            if (capa.getIdentifier()[0] == type.getIdentifier()[0]
                    && capa.getIdentifier()[1] == type.getIdentifier()[1]) {
                return capa;
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

    public Capabilities(byte[] bytes) throws CommandParseException {
        super(bytes);
        ArrayList<CapabilityProperty> builder = new ArrayList<>();
        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    try {
                        // to make the map work, all capabilities need to be mapped to a constant key.
                        // cannot get from map with a byte[]
                        CapabilityProperty capability = new CapabilityProperty(property.getPropertyBytes());
                        builder.add(capability);
                    }
                    catch (CommandParseException e) {
                        // don't use unknown capability
                    }
                    break;
            }
        }

        capabilities = builder.toArray(new CapabilityProperty[builder.size()]);
    }

    private Capabilities(Builder builder) {
        super(TYPE, builder.getProperties());
        capabilities = builder.capabilities.toArray(new CapabilityProperty[builder.capabilities.size()]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<CapabilityProperty> capabilities = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        public Builder setCapabilities(CapabilityProperty[] properties) {
            capabilities.addAll(Arrays.asList(properties));

            for (int i = 0; i < properties.length; i++) {
                addProperty(properties[i]);
            }

            return this;
        }

        public Builder addCapability(CapabilityProperty capability) {
            capabilities.add(capability);
            addProperty(capability);
            return this;
        }

        public Capabilities build() {
            return new Capabilities(this);
        }
    }
}