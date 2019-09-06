// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.OnOffState;

public class IgnitionState extends Command {
    Property<OnOffState> status = new Property(OnOffState.class, 0x01);
    Property<OnOffState> accessoriesStatus = new Property(OnOffState.class, 0x02);

    /**
     * @return The status
     */
    public Property<OnOffState> getStatus() {
        return status;
    }

    /**
     * @return The accessories status
     */
    public Property<OnOffState> getAccessoriesStatus() {
        return accessoriesStatus;
    }

    IgnitionState(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return status.update(p);
                    case 0x02: return accessoriesStatus.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private IgnitionState(Builder builder) {
        super(builder);

        status = builder.status;
        accessoriesStatus = builder.accessoriesStatus;
    }

    public static final class Builder extends Command.Builder {
        private Property<OnOffState> status;
        private Property<OnOffState> accessoriesStatus;

        public Builder() {
            super(Identifier.IGNITION);
        }

        public IgnitionState build() {
            return new IgnitionState(this);
        }

        /**
         * @param status The status
         * @return The builder
         */
        public Builder setStatus(Property<OnOffState> status) {
            this.status = status.setIdentifier(0x01);
            addProperty(status);
            return this;
        }
        
        /**
         * @param accessoriesStatus The accessories status
         * @return The builder
         */
        public Builder setAccessoriesStatus(Property<OnOffState> accessoriesStatus) {
            this.accessoriesStatus = accessoriesStatus.setIdentifier(0x02);
            addProperty(accessoriesStatus);
            return this;
        }
    }
}