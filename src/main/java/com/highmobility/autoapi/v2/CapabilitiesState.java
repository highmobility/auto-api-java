// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.SupportedCapability;
import java.util.ArrayList;
import java.util.List;

public class CapabilitiesState extends Command {
    Property<SupportedCapability> capabilities[];

    /**
     * @return The capabilities
     */
    public Property<SupportedCapability>[] getCapabilities() {
        return capabilities;
    }

    CapabilitiesState(byte[] bytes) {
        super(bytes);

        ArrayList<Property> capabilitiesBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01:
                        Property<SupportedCapability> capabilitie = new Property(SupportedCapability.class, p);
                        capabilitiesBuilder.add(capabilitie);
                        return capabilitie;
                }

                return null;
            });
        }

        capabilities = capabilitiesBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private CapabilitiesState(Builder builder) {
        super(builder);

        capabilities = builder.capabilities.toArray(new Property[0]);
    }

    public static final class Builder extends Command.Builder {
        private List<Property> capabilities = new ArrayList<>();

        public Builder() {
            super(Identifier.CAPABILITIES);
        }

        public CapabilitiesState build() {
            return new CapabilitiesState(this);
        }

        /**
         * Add an array of capabilities.
         * 
         * @param capabilities The capabilities
         * @return The builder
         */
        public Builder setCapabilities(Property<SupportedCapability>[] capabilities) {
            this.capabilities.clear();
            for (int i = 0; i < capabilities.length; i++) {
                addCapabilitie(capabilities[i]);
            }
        
            return this;
        }
        /**
         * Add a single capabilitie.
         * 
         * @param capabilitie The capabilitie
         * @return The builder
         */
        public Builder addCapabilitie(Property<SupportedCapability> capabilitie) {
            capabilitie.setIdentifier(0x01);
            addProperty(capabilitie);
            capabilities.add(capabilitie);
            return this;
        }
    }
}