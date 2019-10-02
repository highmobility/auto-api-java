// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.Time;

public class NaviDestinationState extends SetCommand {
    Property<Coordinates> coordinates = new Property(Coordinates.class, 0x01);
    Property<String> destinationName = new Property(String.class, 0x02);
    PropertyInteger dataSlotsFree = new PropertyInteger(0x03, false);
    PropertyInteger dataSlotsMax = new PropertyInteger(0x04, false);
    Property<Time> arrivalDuration = new Property(Time.class, 0x05);
    PropertyInteger distanceToDestination = new PropertyInteger(0x06, false);

    /**
     * @return The coordinates
     */
    public Property<Coordinates> getCoordinates() {
        return coordinates;
    }

    /**
     * @return Destination name
     */
    public Property<String> getDestinationName() {
        return destinationName;
    }

    /**
     * @return Remaining number of POI data slots available.
     */
    public PropertyInteger getDataSlotsFree() {
        return dataSlotsFree;
    }

    /**
     * @return Maximum number of POI data slots.
     */
    public PropertyInteger getDataSlotsMax() {
        return dataSlotsMax;
    }

    /**
     * @return Remaining time until reaching the destination.
     */
    public Property<Time> getArrivalDuration() {
        return arrivalDuration;
    }

    /**
     * @return Remaining distance to reach the destination.
     */
    public PropertyInteger getDistanceToDestination() {
        return distanceToDestination;
    }

    NaviDestinationState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return coordinates.update(p);
                    case 0x02: return destinationName.update(p);
                    case 0x03: return dataSlotsFree.update(p);
                    case 0x04: return dataSlotsMax.update(p);
                    case 0x05: return arrivalDuration.update(p);
                    case 0x06: return distanceToDestination.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private NaviDestinationState(Builder builder) {
        super(builder);

        coordinates = builder.coordinates;
        destinationName = builder.destinationName;
        dataSlotsFree = builder.dataSlotsFree;
        dataSlotsMax = builder.dataSlotsMax;
        arrivalDuration = builder.arrivalDuration;
        distanceToDestination = builder.distanceToDestination;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<Coordinates> coordinates;
        private Property<String> destinationName;
        private PropertyInteger dataSlotsFree;
        private PropertyInteger dataSlotsMax;
        private Property<Time> arrivalDuration;
        private PropertyInteger distanceToDestination;

        public Builder() {
            super(Identifier.NAVI_DESTINATION);
        }

        public NaviDestinationState build() {
            return new NaviDestinationState(this);
        }

        /**
         * @param coordinates The coordinates
         * @return The builder
         */
        public Builder setCoordinates(Property<Coordinates> coordinates) {
            this.coordinates = coordinates.setIdentifier(0x01);
            addProperty(this.coordinates);
            return this;
        }
        
        /**
         * @param destinationName Destination name
         * @return The builder
         */
        public Builder setDestinationName(Property<String> destinationName) {
            this.destinationName = destinationName.setIdentifier(0x02);
            addProperty(this.destinationName);
            return this;
        }
        
        /**
         * @param dataSlotsFree Remaining number of POI data slots available.
         * @return The builder
         */
        public Builder setDataSlotsFree(Property<Integer> dataSlotsFree) {
            this.dataSlotsFree = new PropertyInteger(0x03, false, 1, dataSlotsFree);
            addProperty(this.dataSlotsFree);
            return this;
        }
        
        /**
         * @param dataSlotsMax Maximum number of POI data slots.
         * @return The builder
         */
        public Builder setDataSlotsMax(Property<Integer> dataSlotsMax) {
            this.dataSlotsMax = new PropertyInteger(0x04, false, 1, dataSlotsMax);
            addProperty(this.dataSlotsMax);
            return this;
        }
        
        /**
         * @param arrivalDuration Remaining time until reaching the destination.
         * @return The builder
         */
        public Builder setArrivalDuration(Property<Time> arrivalDuration) {
            this.arrivalDuration = arrivalDuration.setIdentifier(0x05);
            addProperty(this.arrivalDuration);
            return this;
        }
        
        /**
         * @param distanceToDestination Remaining distance to reach the destination.
         * @return The builder
         */
        public Builder setDistanceToDestination(Property<Integer> distanceToDestination) {
            this.distanceToDestination = new PropertyInteger(0x06, false, 2, distanceToDestination);
            addProperty(this.distanceToDestination);
            return this;
        }
    }
}