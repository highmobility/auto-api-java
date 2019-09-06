// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.ActiveState;

public class ParkingBrakeState extends Command {
    Property<ActiveState> status = new Property(ActiveState.class, 0x01);

    /**
     * @return The status
     */
    public Property<ActiveState> getStatus() {
        return status;
    }

    ParkingBrakeState(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return status.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private ParkingBrakeState(Builder builder) {
        super(builder);

        status = builder.status;
    }

    public static final class Builder extends Command.Builder {
        private Property<ActiveState> status;

        public Builder() {
            super(Identifier.PARKING_BRAKE);
        }

        public ParkingBrakeState build() {
            return new ParkingBrakeState(this);
        }

        /**
         * @param status The status
         * @return The builder
         */
        public Builder setStatus(Property<ActiveState> status) {
            this.status = status.setIdentifier(0x01);
            addProperty(status);
            return this;
        }
    }
}