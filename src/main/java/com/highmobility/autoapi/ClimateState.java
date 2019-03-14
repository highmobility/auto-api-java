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

import com.highmobility.autoapi.value.HvacStartingTime;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Weekday;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Climate State command is received by the car. Also sent once the HVAC
 * system has been turned on/off, when the defrosting/defogging states change or when the profile is
 * updated.
 * <p>
 * Auto-HVAC (Heating, Ventilation and Air Conditioning) allows you to schedule times when HVAC is
 * triggered automatically to reach the desired driver temperature setting.
 */
public class ClimateState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x01);

    private static final byte IDENTIFIER_INSIDE_TEMPERATURE = 0x01;
    private static final byte IDENTIFIER_OUTSIDE_TEMPERATURE = 0x02;
    private static final byte IDENTIFIER_DRIVER_TEMPERATURE_SETTING = 0x03;
    private static final byte IDENTIFIER_PASSENGER_TEMPERATURE_SETTING = 0x04;
    private static final byte IDENTIFIER_HVAC_ACTIVE = 0x05;
    private static final byte IDENTIFIER_DEFOGGING_ACTIVE = 0x06;
    private static final byte IDENTIFIER_DEFROSTING_ACTIVE = 0x07;
    private static final byte IDENTIFIER_IONISING_ACTIVE = 0x08;
    private static final byte IDENTIFIER_DEFROSTING_TEMPERATURE = 0x09;
    private static final byte IDENTIFIER_HVAC_TIME = 0x0B;
    private static final byte IDENTIFIER_REAR_TEMPERATURE_SETTING = 0x0C;

    Property<Float> insideTemperature = new Property(Float.class,
            IDENTIFIER_INSIDE_TEMPERATURE);
    Property<Float> outsideTemperature = new Property(Float.class,
            IDENTIFIER_OUTSIDE_TEMPERATURE);
    Property<Float> driverTemperatureSetting =
            new Property(Float.class, IDENTIFIER_DRIVER_TEMPERATURE_SETTING);
    Property<Float> passengerTemperatureSetting =
            new Property(Float.class, IDENTIFIER_PASSENGER_TEMPERATURE_SETTING);
    Property<Boolean> hvacActive = new Property(Boolean.class,
            IDENTIFIER_HVAC_ACTIVE);
    Property<Boolean> defoggingActive = new Property(Boolean.class,
            IDENTIFIER_DEFOGGING_ACTIVE);
    Property<Boolean> defrostingActive = new Property(Boolean.class,
            IDENTIFIER_DEFROSTING_ACTIVE);
    Property<Boolean> ionisingActive = new Property(Boolean.class,
            IDENTIFIER_IONISING_ACTIVE);
    Property<Float> defrostingTemperature = new Property(Float.class,
            IDENTIFIER_DEFROSTING_TEMPERATURE);

    // level8
    Property<HvacStartingTime>[] hvacStartingTimes;
    Property<Float> rearTemperatureSetting = new Property(Float.class,
            IDENTIFIER_REAR_TEMPERATURE_SETTING);

    /**
     * @return The inside temperature.
     */
    public Property<Float> getInsideTemperature() {
        return insideTemperature;
    }

    /**
     * @return The outside temperature.
     */
    public Property<Float> getOutsideTemperature() {
        return outsideTemperature;
    }

    /**
     * @return The driver temperature setting.
     */
    public Property<Float> getDriverTemperatureSetting() {
        return driverTemperatureSetting;
    }

    /**
     * @return The passenger temperature setting.
     */
    public Property<Float> getPassengerTemperatureSetting() {
        return passengerTemperatureSetting;
    }

    /**
     * @return Whether HVAC is active or not.
     */
    public Property<Boolean> isHvacActive() {
        return hvacActive;
    }

    /**
     * @return Whether defogging is active or not.
     */
    public Property<Boolean> isDefoggingActive() {
        return defoggingActive;
    }

    /**
     * @return Whether defrosting is active or not.
     */
    public Property<Boolean> isDefrostingActive() {
        return defrostingActive;
    }

    /**
     * @return Whether ionising is active or not.
     */
    public Property<Boolean> isIonisingActive() {
        return ionisingActive;
    }

    /**
     * @return The defrosting temperature.
     */
    public Property<Float> getDefrostingTemperature() {
        return defrostingTemperature;
    }

    /**
     * @return The HVAC weekday starting times.
     */
    @Nullable public Property<HvacStartingTime>[] getHvacStartingTimes() {
        return hvacStartingTimes;
    }

    /**
     * @param weekday The weekday of the hvac starting times.
     * @return The HVAC weekday starting times.
     */
    @Nullable public Property<HvacStartingTime> getHvacStartingTime(Weekday weekday) {
        for (Property<HvacStartingTime> hvacStartingTime : hvacStartingTimes) {
            if (hvacStartingTime.getValue().getWeekday() == weekday) return hvacStartingTime;
        }

        return null;
    }

    /**
     * @return The rear temperature setting.
     */
    public Property<Float> getRearTemperatureSetting() {
        return rearTemperatureSetting;
    }

    ClimateState(byte[] bytes) {
        super(bytes);

        ArrayList<Property> builder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_INSIDE_TEMPERATURE:
                        return insideTemperature.update(p);
                    case IDENTIFIER_OUTSIDE_TEMPERATURE:
                        return outsideTemperature.update(p);
                    case IDENTIFIER_DRIVER_TEMPERATURE_SETTING:
                        return driverTemperatureSetting.update(p);
                    case IDENTIFIER_PASSENGER_TEMPERATURE_SETTING:
                        return passengerTemperatureSetting.update(p);
                    case IDENTIFIER_HVAC_ACTIVE:
                        return hvacActive.update(p);
                    case IDENTIFIER_DEFOGGING_ACTIVE:
                        return defoggingActive.update(p);
                    case IDENTIFIER_DEFROSTING_ACTIVE:
                        return defrostingActive.update(p);
                    case IDENTIFIER_IONISING_ACTIVE:
                        return ionisingActive.update(p);
                    case IDENTIFIER_DEFROSTING_TEMPERATURE:
                        return defrostingTemperature.update(p);
                    case IDENTIFIER_HVAC_TIME:
                        Property<HvacStartingTime> time =
                                new Property(HvacStartingTime.class, p);
                        builder.add(time);
                        return time;
                    case IDENTIFIER_REAR_TEMPERATURE_SETTING:
                        return rearTemperatureSetting.update(p);
                }
                return null;
            });
        }

        hvacStartingTimes = builder.toArray(new Property[0]);
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
        hvacActive = builder.hvacActive;
        defoggingActive = builder.defoggingActive;
        defrostingActive = builder.defrostingActive;
        ionisingActive = builder.ionisingActive;
        defrostingTemperature = builder.defrostingTemperature;
        hvacStartingTimes = builder.hvacStartingTimes.toArray(new Property[0]);
        rearTemperatureSetting = builder.rearTemperatureSetting;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Property<Float> insideTemperature;
        private Property<Float> outsideTemperature;
        private Property<Float> driverTemperatureSetting;
        private Property<Float> passengerTemperatureSetting;
        private Property<Boolean> hvacActive;
        private Property<Boolean> defoggingActive;
        private Property<Boolean> defrostingActive;
        private Property<Boolean> ionisingActive;
        private Property<Float> defrostingTemperature;
        private ArrayList<Property<HvacStartingTime>> hvacStartingTimes = new ArrayList<>();

        private Property<Float> rearTemperatureSetting;

        public Builder() {
            super(TYPE);
        }

        /**
         * r
         *
         * @param insideTemperature The inside temperature.
         * @return The builder.
         */
        public Builder setInsideTemperature(Property<Float> insideTemperature) {
            this.insideTemperature = insideTemperature;
            insideTemperature.setIdentifier(IDENTIFIER_INSIDE_TEMPERATURE);
            addProperty(insideTemperature);
            return this;
        }

        /**
         * @param outsideTemperature The outside temperature.
         * @return The builder.
         */
        public Builder setOutsideTemperature(Property<Float> outsideTemperature) {
            this.outsideTemperature = outsideTemperature;
            outsideTemperature.setIdentifier(IDENTIFIER_OUTSIDE_TEMPERATURE);
            addProperty(outsideTemperature);
            return this;
        }

        /**
         * @param driverTemperatureSetting The driver temperature.
         * @return The builder.
         */
        public Builder setDriverTemperatureSetting(Property<Float> driverTemperatureSetting) {
            this.driverTemperatureSetting = driverTemperatureSetting;
            driverTemperatureSetting.setIdentifier(IDENTIFIER_DRIVER_TEMPERATURE_SETTING);
            addProperty(driverTemperatureSetting);
            return this;
        }

        /**
         * @param passengerTemperatureSetting The passenger temperature setting.
         * @return The builder.
         */
        public Builder setPassengerTemperatureSetting(Property<Float> passengerTemperatureSetting) {
            this.passengerTemperatureSetting = passengerTemperatureSetting;
            passengerTemperatureSetting.setIdentifier(IDENTIFIER_PASSENGER_TEMPERATURE_SETTING);
            addProperty(passengerTemperatureSetting);
            return this;
        }

        /**
         * @param hvacActive Whether HVAC is active or not.
         * @return The builder.
         */
        public Builder setHvacActive(Property<Boolean> hvacActive) {
            this.hvacActive = hvacActive;
            hvacActive.setIdentifier(IDENTIFIER_HVAC_ACTIVE);
            addProperty(hvacActive);
            return this;
        }

        /**
         * @param defoggingActive Whether defogging is active or not.
         * @return The builder.
         */
        public Builder setDefoggingActive(Property<Boolean> defoggingActive) {
            this.defoggingActive = defoggingActive;
            defoggingActive.setIdentifier(IDENTIFIER_DEFOGGING_ACTIVE);
            addProperty(defoggingActive);
            return this;
        }

        /**
         * @param defrostingActive Whether defrosting is active or not.
         * @return The builder.
         */
        public Builder setDefrostingActive(Property<Boolean> defrostingActive) {
            this.defrostingActive = defrostingActive;
            defrostingActive.setIdentifier(IDENTIFIER_DEFROSTING_ACTIVE);
            addProperty(defrostingActive);
            return this;
        }

        /**
         * @param ionisingActive Whether ionising is active or not.
         * @return The builder.
         */
        public Builder setIonisingActive(Property<Boolean> ionisingActive) {
            this.ionisingActive = ionisingActive;
            ionisingActive.setIdentifier(IDENTIFIER_IONISING_ACTIVE);
            addProperty(ionisingActive);
            return this;
        }

        /**
         * @param defrostingTemperature The defrosting temperature
         * @return The builder.
         */
        public Builder setDefrostingTemperature(Property<Float> defrostingTemperature) {
            this.defrostingTemperature = defrostingTemperature;
            defrostingTemperature.setIdentifier(IDENTIFIER_DEFROSTING_TEMPERATURE);
            addProperty(defrostingTemperature);
            return this;
        }

        /**
         * Set hvac starting times.
         *
         * @param hvacStartingTimes the HVAC starting times.
         * @return The builder.
         */
        public Builder setHvacStartingTimes(Property<HvacStartingTime>[] hvacStartingTimes) {
            this.hvacStartingTimes.clear();

            for (Property<HvacStartingTime> hvacStartingTime : hvacStartingTimes) {
                addHvacStartingTime(hvacStartingTime);

            }
            return this;
        }

        /**
         * Add a HVAC starting time.
         *
         * @param hvacStartingTime the HVAC starting time.
         * @return The builder.
         */
        public Builder addHvacStartingTime(Property<HvacStartingTime> hvacStartingTime) {
            hvacStartingTime.setIdentifier(IDENTIFIER_HVAC_TIME);
            this.hvacStartingTimes.add(hvacStartingTime);
            addProperty(hvacStartingTime);
            return this;
        }

        /**
         * @param rearTemperatureSetting The rear temperature setting.
         * @return The builder.
         */
        public Builder setRearTemperatureSetting(Property<Float> rearTemperatureSetting) {
            this.rearTemperatureSetting = rearTemperatureSetting;
            rearTemperatureSetting.setIdentifier(IDENTIFIER_REAR_TEMPERATURE_SETTING);
            addProperty(rearTemperatureSetting);
            return this;
        }

        public ClimateState build() {
            return new ClimateState(this);
        }
    }
}
