// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.Time;

public class VehicleTimeState extends Command {
    Property<Time> vehicleTime = new Property(Time.class, 0x01);

    /**
     * @return Vehicle time in a 24h format
     */
    public Property<Time> getVehicleTime() {
        return vehicleTime;
    }

    VehicleTimeState(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return vehicleTime.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private VehicleTimeState(Builder builder) {
        super(builder);

        vehicleTime = builder.vehicleTime;
    }

    public static final class Builder extends Command.Builder {
        private Property<Time> vehicleTime;

        public Builder() {
            super(Identifier.VEHICLE_TIME);
        }

        public VehicleTimeState build() {
            return new VehicleTimeState(this);
        }

        /**
         * @param vehicleTime Vehicle time in a 24h format
         * @return The builder
         */
        public Builder setVehicleTime(Property<Time> vehicleTime) {
            this.vehicleTime = vehicleTime.setIdentifier(0x01);
            addProperty(vehicleTime);
            return this;
        }
    }
}