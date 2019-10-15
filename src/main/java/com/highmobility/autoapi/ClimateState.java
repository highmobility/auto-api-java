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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.HvacWeekdayStartingTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The climate state
 */
public class ClimateState extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.CLIMATE;

    public static final byte IDENTIFIER_INSIDE_TEMPERATURE = 0x01;
    public static final byte IDENTIFIER_OUTSIDE_TEMPERATURE = 0x02;
    public static final byte IDENTIFIER_DRIVER_TEMPERATURE_SETTING = 0x03;
    public static final byte IDENTIFIER_PASSENGER_TEMPERATURE_SETTING = 0x04;
    public static final byte IDENTIFIER_HVAC_STATE = 0x05;
    public static final byte IDENTIFIER_DEFOGGING_STATE = 0x06;
    public static final byte IDENTIFIER_DEFROSTING_STATE = 0x07;
    public static final byte IDENTIFIER_IONISING_STATE = 0x08;
    public static final byte IDENTIFIER_DEFROSTING_TEMPERATURE_SETTING = 0x09;
    public static final byte IDENTIFIER_HVAC_WEEKDAY_STARTING_TIMES = 0x0b;
    public static final byte IDENTIFIER_REAR_TEMPERATURE_SETTING = 0x0c;

    Property<Float> insideTemperature = new Property(Float.class, IDENTIFIER_INSIDE_TEMPERATURE);
    Property<Float> outsideTemperature = new Property(Float.class, IDENTIFIER_OUTSIDE_TEMPERATURE);
    Property<Float> driverTemperatureSetting = new Property(Float.class, IDENTIFIER_DRIVER_TEMPERATURE_SETTING);
    Property<Float> passengerTemperatureSetting = new Property(Float.class, IDENTIFIER_PASSENGER_TEMPERATURE_SETTING);
    Property<ActiveState> hvacState = new Property(ActiveState.class, IDENTIFIER_HVAC_STATE);
    Property<ActiveState> defoggingState = new Property(ActiveState.class, IDENTIFIER_DEFOGGING_STATE);
    Property<ActiveState> defrostingState = new Property(ActiveState.class, IDENTIFIER_DEFROSTING_STATE);
    Property<ActiveState> ionisingState = new Property(ActiveState.class, IDENTIFIER_IONISING_STATE);
    Property<Float> defrostingTemperatureSetting = new Property(Float.class, IDENTIFIER_DEFROSTING_TEMPERATURE_SETTING);
    Property<HvacWeekdayStartingTime>[] hvacWeekdayStartingTimes;
    Property<Float> rearTemperatureSetting = new Property(Float.class, IDENTIFIER_REAR_TEMPERATURE_SETTING);

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
                    case IDENTIFIER_INSIDE_TEMPERATURE: return insideTemperature.update(p);
                    case IDENTIFIER_OUTSIDE_TEMPERATURE: return outsideTemperature.update(p);
                    case IDENTIFIER_DRIVER_TEMPERATURE_SETTING: return driverTemperatureSetting.update(p);
                    case IDENTIFIER_PASSENGER_TEMPERATURE_SETTING: return passengerTemperatureSetting.update(p);
                    case IDENTIFIER_HVAC_STATE: return hvacState.update(p);
                    case IDENTIFIER_DEFOGGING_STATE: return defoggingState.update(p);
                    case IDENTIFIER_DEFROSTING_STATE: return defrostingState.update(p);
                    case IDENTIFIER_IONISING_STATE: return ionisingState.update(p);
                    case IDENTIFIER_DEFROSTING_TEMPERATURE_SETTING: return defrostingTemperatureSetting.update(p);
                    case IDENTIFIER_HVAC_WEEKDAY_STARTING_TIMES:
                        Property<HvacWeekdayStartingTime> hvacWeekdayStartingTime = new Property(HvacWeekdayStartingTime.class, p);
                        hvacWeekdayStartingTimesBuilder.add(hvacWeekdayStartingTime);
                        return hvacWeekdayStartingTime;
                    case IDENTIFIER_REAR_TEMPERATURE_SETTING: return rearTemperatureSetting.update(p);
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
            super(IDENTIFIER);
        }

        public ClimateState build() {
            return new ClimateState(this);
        }

        /**
         * @param insideTemperature The inside temperature in celsius
         * @return The builder
         */
        public Builder setInsideTemperature(Property<Float> insideTemperature) {
            this.insideTemperature = insideTemperature.setIdentifier(IDENTIFIER_INSIDE_TEMPERATURE);
            addProperty(this.insideTemperature);
            return this;
        }
        
        /**
         * @param outsideTemperature The outside temperature in celsius
         * @return The builder
         */
        public Builder setOutsideTemperature(Property<Float> outsideTemperature) {
            this.outsideTemperature = outsideTemperature.setIdentifier(IDENTIFIER_OUTSIDE_TEMPERATURE);
            addProperty(this.outsideTemperature);
            return this;
        }
        
        /**
         * @param driverTemperatureSetting The driver temperature setting in celsius
         * @return The builder
         */
        public Builder setDriverTemperatureSetting(Property<Float> driverTemperatureSetting) {
            this.driverTemperatureSetting = driverTemperatureSetting.setIdentifier(IDENTIFIER_DRIVER_TEMPERATURE_SETTING);
            addProperty(this.driverTemperatureSetting);
            return this;
        }
        
        /**
         * @param passengerTemperatureSetting The passenger temperature setting in celsius
         * @return The builder
         */
        public Builder setPassengerTemperatureSetting(Property<Float> passengerTemperatureSetting) {
            this.passengerTemperatureSetting = passengerTemperatureSetting.setIdentifier(IDENTIFIER_PASSENGER_TEMPERATURE_SETTING);
            addProperty(this.passengerTemperatureSetting);
            return this;
        }
        
        /**
         * @param hvacState The hvac state
         * @return The builder
         */
        public Builder setHvacState(Property<ActiveState> hvacState) {
            this.hvacState = hvacState.setIdentifier(IDENTIFIER_HVAC_STATE);
            addProperty(this.hvacState);
            return this;
        }
        
        /**
         * @param defoggingState The defogging state
         * @return The builder
         */
        public Builder setDefoggingState(Property<ActiveState> defoggingState) {
            this.defoggingState = defoggingState.setIdentifier(IDENTIFIER_DEFOGGING_STATE);
            addProperty(this.defoggingState);
            return this;
        }
        
        /**
         * @param defrostingState The defrosting state
         * @return The builder
         */
        public Builder setDefrostingState(Property<ActiveState> defrostingState) {
            this.defrostingState = defrostingState.setIdentifier(IDENTIFIER_DEFROSTING_STATE);
            addProperty(this.defrostingState);
            return this;
        }
        
        /**
         * @param ionisingState The ionising state
         * @return The builder
         */
        public Builder setIonisingState(Property<ActiveState> ionisingState) {
            this.ionisingState = ionisingState.setIdentifier(IDENTIFIER_IONISING_STATE);
            addProperty(this.ionisingState);
            return this;
        }
        
        /**
         * @param defrostingTemperatureSetting The defrosting temperature setting in celsius
         * @return The builder
         */
        public Builder setDefrostingTemperatureSetting(Property<Float> defrostingTemperatureSetting) {
            this.defrostingTemperatureSetting = defrostingTemperatureSetting.setIdentifier(IDENTIFIER_DEFROSTING_TEMPERATURE_SETTING);
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
            hvacWeekdayStartingTime.setIdentifier(IDENTIFIER_HVAC_WEEKDAY_STARTING_TIMES);
            addProperty(hvacWeekdayStartingTime);
            hvacWeekdayStartingTimes.add(hvacWeekdayStartingTime);
            return this;
        }
        
        /**
         * @param rearTemperatureSetting The rear temperature in celsius
         * @return The builder
         */
        public Builder setRearTemperatureSetting(Property<Float> rearTemperatureSetting) {
            this.rearTemperatureSetting = rearTemperatureSetting.setIdentifier(IDENTIFIER_REAR_TEMPERATURE_SETTING);
            addProperty(this.rearTemperatureSetting);
            return this;
        }
    }
}