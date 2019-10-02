// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;

public class OffroadState extends SetCommand {
    PropertyInteger routeIncline = new PropertyInteger(0x01, true);
    Property<Double> wheelSuspension = new Property(Double.class, 0x02);

    /**
     * @return The route elevation incline in degrees, which is a negative number for decline
     */
    public PropertyInteger getRouteIncline() {
        return routeIncline;
    }

    /**
     * @return The wheel suspension level percentage, whereas 0.0 is no suspension and 1.0 maximum suspension
     */
    public Property<Double> getWheelSuspension() {
        return wheelSuspension;
    }

    OffroadState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return routeIncline.update(p);
                    case 0x02: return wheelSuspension.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private OffroadState(Builder builder) {
        super(builder);

        routeIncline = builder.routeIncline;
        wheelSuspension = builder.wheelSuspension;
    }

    public static final class Builder extends SetCommand.Builder {
        private PropertyInteger routeIncline;
        private Property<Double> wheelSuspension;

        public Builder() {
            super(Identifier.OFFROAD);
        }

        public OffroadState build() {
            return new OffroadState(this);
        }

        /**
         * @param routeIncline The route elevation incline in degrees, which is a negative number for decline
         * @return The builder
         */
        public Builder setRouteIncline(Property<Integer> routeIncline) {
            this.routeIncline = new PropertyInteger(0x01, true, 2, routeIncline);
            addProperty(this.routeIncline);
            return this;
        }
        
        /**
         * @param wheelSuspension The wheel suspension level percentage, whereas 0.0 is no suspension and 1.0 maximum suspension
         * @return The builder
         */
        public Builder setWheelSuspension(Property<Double> wheelSuspension) {
            this.wheelSuspension = wheelSuspension.setIdentifier(0x02);
            addProperty(this.wheelSuspension);
            return this;
        }
    }
}