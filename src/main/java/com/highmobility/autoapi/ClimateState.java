// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.HvacWeekdayStartingTime;
import java.util.ArrayList;
import java.util.List;

public class ClimateState extends SetCommand {
    Property<Float> insideTemperature = new Property(Float.class, 0x01);
    Property<Float> outsideTemperature = new Property(Float.class, 0x02);
    Property<Float> driverTemperatureSetting = new Property(Float.class, 0x03);
    Property<Float> passengerTemperatureSetting = new Property(Float.class, 0x04);
    Property<ActiveState> hvacState = new Property(ActiveState.class, 0x05);
    Property<ActiveState> defoggingState = new Property(ActiveState.class, 0x06);
    Property<ActiveState> defrostingState = new Property(ActiveState.class, 0x07);
    Property<ActiveState> ionisingState = new Property(ActiveState.class, 0x08);
    Property<Float> defrostingTemperatureSetting = new Property(Float.class, 0x09);
    Property<HvacWeekdayStartingTime>[] hvacWeekdayStartingTimes;
    Property<Float> rearTemperatureSetting = new Property(Float.class, 0x0c);

    /**
     * @return The inside temperature in celsius
     */
    public Property<Float> getInsideTemperature() {
        return insideTemperature;
    }

    /**
     * @return The outside temperature in celsius
     */
    public Property<Float> getOutsideTemperature() {
        return outsideTemperature;
    }

    /**
     * @return The driver temperature setting in celsius
     */
    public Property<Float> getDriverTemperatureSetting() {
        return driverTemperatureSetting;
    }

    /**
     * @return The passenger temperature setting in celsius
     */
    public Property<Float> getPassengerTemperatureSetting() {
        return passengerTemperatureSetting;
    }

    /**
     * @return The hvac state
     */
    public Property<ActiveState> getHvacState() {
        return hvacState;
    }

    /**
     * @return The defogging state
     */
    public Property<ActiveState> getDefoggingState() {
        return defoggingState;
    }

    /**
     * @return The defrosting state
     */
    public Property<ActiveState> getDefrostingState() {
        return defrostingState;
    }

    /**
     * @return The ionising state
     */
    public Property<ActiveState> getIonisingState() {
        return ionisingState;
    }

    /**
     * @return The defrosting temperature setting in celsius
     */
    public Property<Float> getDefrostingTemperatureSetting() {
        return defrostingTemperatureSetting;
    }

    /**
     * @return The hvac weekday starting times
     */
    public Property<HvacWeekdayStartingTime>[] getHvacWeekdayStartingTimes() {
        return hvacWeekdayStartingTimes;
    }

    /**
     * @return The rear temperature in celsius
     */
    public Property<Float> getRearTemperatureSetting() {
        return rearTemperatureSetting;
    }

    ClimateState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> hvacWeekdayStartingTimesBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return insideTemperature.update(p);
                    case 0x02: return outsideTemperature.update(p);
                    case 0x03: return driverTemperatureSetting.update(p);
                    case 0x04: return passengerTemperatureSetting.update(p);
                    case 0x05: return hvacState.update(p);
                    case 0x06: return defoggingState.update(p);
                    case 0x07: return defrostingState.update(p);
                    case 0x08: return ionisingState.update(p);
                    case 0x09: return defrostingTemperatureSetting.update(p);
                    case 0x0b:
                        Property<HvacWeekdayStartingTime> hvacWeekdayStartingTime = new Property(HvacWeekdayStartingTime.class, p);
                        hvacWeekdayStartingTimesBuilder.add(hvacWeekdayStartingTime);
                        return hvacWeekdayStartingTime;
                    case 0x0c: return rearTemperatureSetting.update(p);
                }

                return null;
            });
        }

        hvacWeekdayStartingTimes = hvacWeekdayStartingTimesBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private ClimateState(Builder builder) {
        super(builder);

        insideTemperature = builder.insideTemperature;
        outsideTemperature = builder.outsideTemperature;
        driverTemperatureSetting = builder.driverTemperatureSetting;
        passengerTemperatureSetting = builder.passengerTemperatureSetting;
        hvacState = builder.hvacState;
        defoggingState = builder.defoggingState;
        defrostingState = builder.defrostingState;
        ionisingState = builder.ionisingState;
        defrostingTemperatureSetting = builder.defrostingTemperatureSetting;
        hvacWeekdayStartingTimes = builder.hvacWeekdayStartingTimes.toArray(new Property[0]);
        rearTemperatureSetting = builder.rearTemperatureSetting;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<Float> insideTemperature;
        private Property<Float> outsideTemperature;
        private Property<Float> driverTemperatureSetting;
        private Property<Float> passengerTemperatureSetting;
        private Property<ActiveState> hvacState;
        private Property<ActiveState> defoggingState;
        private Property<ActiveState> defrostingState;
        private Property<ActiveState> ionisingState;
        private Property<Float> defrostingTemperatureSetting;
        private List<Property> hvacWeekdayStartingTimes = new ArrayList<>();
        private Property<Float> rearTemperatureSetting;

        public Builder() {
            super(Identifier.CLIMATE);
        }

        public ClimateState build() {
            return new ClimateState(this);
        }

        /**
         * @param insideTemperature The inside temperature in celsius
         * @return The builder
         */
        public Builder setInsideTemperature(Property<Float> insideTemperature) {
            this.insideTemperature = insideTemperature.setIdentifier(0x01);
            addProperty(this.insideTemperature);
            return this;
        }
        
        /**
         * @param outsideTemperature The outside temperature in celsius
         * @return The builder
         */
        public Builder setOutsideTemperature(Property<Float> outsideTemperature) {
            this.outsideTemperature = outsideTemperature.setIdentifier(0x02);
            addProperty(this.outsideTemperature);
            return this;
        }
        
        /**
         * @param driverTemperatureSetting The driver temperature setting in celsius
         * @return The builder
         */
        public Builder setDriverTemperatureSetting(Property<Float> driverTemperatureSetting) {
            this.driverTemperatureSetting = driverTemperatureSetting.setIdentifier(0x03);
            addProperty(this.driverTemperatureSetting);
            return this;
        }
        
        /**
         * @param passengerTemperatureSetting The passenger temperature setting in celsius
         * @return The builder
         */
        public Builder setPassengerTemperatureSetting(Property<Float> passengerTemperatureSetting) {
            this.passengerTemperatureSetting = passengerTemperatureSetting.setIdentifier(0x04);
            addProperty(this.passengerTemperatureSetting);
            return this;
        }
        
        /**
         * @param hvacState The hvac state
         * @return The builder
         */
        public Builder setHvacState(Property<ActiveState> hvacState) {
            this.hvacState = hvacState.setIdentifier(0x05);
            addProperty(this.hvacState);
            return this;
        }
        
        /**
         * @param defoggingState The defogging state
         * @return The builder
         */
        public Builder setDefoggingState(Property<ActiveState> defoggingState) {
            this.defoggingState = defoggingState.setIdentifier(0x06);
            addProperty(this.defoggingState);
            return this;
        }
        
        /**
         * @param defrostingState The defrosting state
         * @return The builder
         */
        public Builder setDefrostingState(Property<ActiveState> defrostingState) {
            this.defrostingState = defrostingState.setIdentifier(0x07);
            addProperty(this.defrostingState);
            return this;
        }
        
        /**
         * @param ionisingState The ionising state
         * @return The builder
         */
        public Builder setIonisingState(Property<ActiveState> ionisingState) {
            this.ionisingState = ionisingState.setIdentifier(0x08);
            addProperty(this.ionisingState);
            return this;
        }
        
        /**
         * @param defrostingTemperatureSetting The defrosting temperature setting in celsius
         * @return The builder
         */
        public Builder setDefrostingTemperatureSetting(Property<Float> defrostingTemperatureSetting) {
            this.defrostingTemperatureSetting = defrostingTemperatureSetting.setIdentifier(0x09);
            addProperty(this.defrostingTemperatureSetting);
            return this;
        }
        
        /**
         * Add an array of hvac weekday starting times.
         * 
         * @param hvacWeekdayStartingTimes The hvac weekday starting times
         * @return The builder
         */
        public Builder setHvacWeekdayStartingTimes(Property<HvacWeekdayStartingTime>[] hvacWeekdayStartingTimes) {
            this.hvacWeekdayStartingTimes.clear();
            for (int i = 0; i < hvacWeekdayStartingTimes.length; i++) {
                addHvacWeekdayStartingTime(hvacWeekdayStartingTimes[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single hvac weekday starting time.
         * 
         * @param hvacWeekdayStartingTime The hvac weekday starting time
         * @return The builder
         */
        public Builder addHvacWeekdayStartingTime(Property<HvacWeekdayStartingTime> hvacWeekdayStartingTime) {
            hvacWeekdayStartingTime.setIdentifier(0x0b);
            addProperty(hvacWeekdayStartingTime);
            hvacWeekdayStartingTimes.add(hvacWeekdayStartingTime);
            return this;
        }
        
        /**
         * @param rearTemperatureSetting The rear temperature in celsius
         * @return The builder
         */
        public Builder setRearTemperatureSetting(Property<Float> rearTemperatureSetting) {
            this.rearTemperatureSetting = rearTemperatureSetting.setIdentifier(0x0c);
            addProperty(this.rearTemperatureSetting);
            return this;
        }
    }
}