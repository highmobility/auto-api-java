// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Coordinates;

public class VehicleLocationState extends SetCommand {
    Property<Coordinates> coordinates = new Property(Coordinates.class, 0x04);
    Property<Double> heading = new Property(Double.class, 0x05);
    Property<Double> altitude = new Property(Double.class, 0x06);

    /**
     * @return The coordinates
     */
    public Property<Coordinates> getCoordinates() {
        return coordinates;
    }

    /**
     * @return Heading in degrees
     */
    public Property<Double> getHeading() {
        return heading;
    }

    /**
     * @return Altitude in meters above the WGS 84 reference ellipsoid
     */
    public Property<Double> getAltitude() {
        return altitude;
    }

    VehicleLocationState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x04: return coordinates.update(p);
                    case 0x05: return heading.update(p);
                    case 0x06: return altitude.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private VehicleLocationState(Builder builder) {
        super(builder);

        coordinates = builder.coordinates;
        heading = builder.heading;
        altitude = builder.altitude;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<Coordinates> coordinates;
        private Property<Double> heading;
        private Property<Double> altitude;

        public Builder() {
            super(Identifier.VEHICLE_LOCATION);
        }

        public VehicleLocationState build() {
            return new VehicleLocationState(this);
        }

        /**
         * @param coordinates The coordinates
         * @return The builder
         */
        public Builder setCoordinates(Property<Coordinates> coordinates) {
            this.coordinates = coordinates.setIdentifier(0x04);
            addProperty(this.coordinates);
            return this;
        }
        
        /**
         * @param heading Heading in degrees
         * @return The builder
         */
        public Builder setHeading(Property<Double> heading) {
            this.heading = heading.setIdentifier(0x05);
            addProperty(this.heading);
            return this;
        }
        
        /**
         * @param altitude Altitude in meters above the WGS 84 reference ellipsoid
         * @return The builder
         */
        public Builder setAltitude(Property<Double> altitude) {
            this.altitude = altitude.setIdentifier(0x06);
            addProperty(this.altitude);
            return this;
        }
    }
}