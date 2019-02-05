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

import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.HvacStartingTime;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.value.Weekday;

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

    FloatProperty insideTemperature = new FloatProperty(IDENTIFIER_INSIDE_TEMPERATURE);
    FloatProperty outsideTemperature = new FloatProperty(IDENTIFIER_OUTSIDE_TEMPERATURE);
    FloatProperty driverTemperatureSetting =
            new FloatProperty(IDENTIFIER_DRIVER_TEMPERATURE_SETTING);
    FloatProperty passengerTemperatureSetting =
            new FloatProperty(IDENTIFIER_PASSENGER_TEMPERATURE_SETTING);
    ObjectProperty<Boolean> hvacActive = new ObjectProperty<>(Boolean.class, IDENTIFIER_HVAC_ACTIVE);
    ObjectProperty<Boolean> defoggingActive = new ObjectProperty<>(Boolean.class, IDENTIFIER_DEFOGGING_ACTIVE);
    ObjectProperty<Boolean> defrostingActive = new ObjectProperty<>(Boolean.class, IDENTIFIER_DEFROSTING_ACTIVE);
    ObjectProperty<Boolean> ionisingActive = new ObjectProperty<>(Boolean.class, IDENTIFIER_IONISING_ACTIVE);
    FloatProperty defrostingTemperature = new FloatProperty(IDENTIFIER_DEFROSTING_TEMPERATURE);

    // level8
    HvacStartingTime[] hvacStartingTimes;
    FloatProperty rearTemperatureSetting = new FloatProperty(IDENTIFIER_REAR_TEMPERATURE_SETTING);

    /**
     * @return The inside temperature.
     */
    @Nullable public FloatProperty getInsideTemperature() {
        return insideTemperature;
    }

    /**
     * @return The outside temperature.
     */
    @Nullable public FloatProperty getOutsideTemperature() {
        return outsideTemperature;
    }

    /**
     * @return The driver temperature setting.
     */
    @Nullable public FloatProperty getDriverTemperatureSetting() {
        return driverTemperatureSetting;
    }

    /**
     * @return The passenger temperature setting.
     */
    @Nullable public FloatProperty getPassengerTemperatureSetting() {
        return passengerTemperatureSetting;
    }

    /**
     * @return Whether HVAC is active or not.
     */
    @Nullable public ObjectProperty<Boolean> isHvacActive() {
        return hvacActive;
    }

    /**
     * @return Whether defogging is active or not.
     */
    @Nullable public ObjectProperty<Boolean> isDefoggingActive() {
        return defoggingActive;
    }

    /**
     * @return Whether defrosting is active or not.
     */
    @Nullable public ObjectProperty<Boolean> isDefrostingActive() {
        return defrostingActive;
    }

    /**
     * @return Whether ionising is active or not.
     */
    @Nullable public ObjectProperty<Boolean> isIonisingActive() {
        return ionisingActive;
    }

    /**
     * @return The defrosting temperature.
     */
    @Nullable public FloatProperty getDefrostingTemperature() {
        return defrostingTemperature;
    }

    /**
     * @return The HVAC weekday starting times.
     */
    public HvacStartingTime[] getHvacStartingTimes() {
        return hvacStartingTimes;
    }

    /**
     * @param weekday The weekday of the hvac starting times.
     * @return The HVAC weekday starting times.
     */
    @Nullable public HvacStartingTime getHvacStartingTime(Weekday weekday) {
        for (HvacStartingTime hvacStartingTime : hvacStartingTimes) {
            if (hvacStartingTime.getValue().getWeekday() == weekday) return hvacStartingTime;
        }

        return null;
    }

    /**
     * @return The rear temperature setting.
     */
    @Nullable public FloatProperty getRearTemperatureSetting() {
        return rearTemperatureSetting;
    }

    ClimateState(byte[] bytes) {
        super(bytes);

        ArrayList<HvacStartingTime> builder = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
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
                        HvacStartingTime time = new HvacStartingTime(p);
                        builder.add(time);
                        return time;
                    case IDENTIFIER_REAR_TEMPERATURE_SETTING:
                        return rearTemperatureSetting.update(p);
                }
                return null;
            });
        }

        hvacStartingTimes = builder.toArray(new HvacStartingTime[0]);
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
        hvacStartingTimes = builder.hvacStartingTimes.toArray(new HvacStartingTime[0]);
        rearTemperatureSetting = builder.rearTemperatureSetting;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private FloatProperty insideTemperature;
        private FloatProperty outsideTemperature;
        private FloatProperty driverTemperatureSetting;
        private FloatProperty passengerTemperatureSetting;
        private ObjectProperty<Boolean> hvacActive;
        private ObjectProperty<Boolean> defoggingActive;
        private ObjectProperty<Boolean> defrostingActive;
        private ObjectProperty<Boolean> ionisingActive;
        private FloatProperty defrostingTemperature;
        private ArrayList<HvacStartingTime> hvacStartingTimes = new ArrayList<>();

        private FloatProperty rearTemperatureSetting;

        public Builder() {
            super(TYPE);
        }

        /**
         * r
         *
         * @param insideTemperature The inside temperature.
         * @return The builder.
         */
        public Builder setInsideTemperature(FloatProperty insideTemperature) {
            this.insideTemperature = insideTemperature;
            insideTemperature.setIdentifier(IDENTIFIER_INSIDE_TEMPERATURE);
            addProperty(insideTemperature);
            return this;
        }

        /**
         * @param outsideTemperature The outside temperature.
         * @return The builder.
         */
        public Builder setOutsideTemperature(FloatProperty outsideTemperature) {
            this.outsideTemperature = outsideTemperature;
            outsideTemperature.setIdentifier(IDENTIFIER_OUTSIDE_TEMPERATURE);
            addProperty(outsideTemperature);
            return this;
        }

        /**
         * @param driverTemperatureSetting The driver temperature.
         * @return The builder.
         */
        public Builder setDriverTemperatureSetting(FloatProperty driverTemperatureSetting) {
            this.driverTemperatureSetting = driverTemperatureSetting;
            driverTemperatureSetting.setIdentifier(IDENTIFIER_DRIVER_TEMPERATURE_SETTING);
            addProperty(driverTemperatureSetting);
            return this;
        }

        /**
         * @param passengerTemperatureSetting The passenger temperature setting.
         * @return The builder.
         */
        public Builder setPassengerTemperatureSetting(FloatProperty passengerTemperatureSetting) {
            this.passengerTemperatureSetting = passengerTemperatureSetting;
            passengerTemperatureSetting.setIdentifier(IDENTIFIER_PASSENGER_TEMPERATURE_SETTING);
            addProperty(passengerTemperatureSetting);
            return this;
        }

        /**
         * @param hvacActive Whether HVAC is active or not.
         * @return The builder.
         */
        public Builder setHvacActive(ObjectProperty<Boolean> hvacActive) {
            this.hvacActive = hvacActive;
            hvacActive.setIdentifier(IDENTIFIER_HVAC_ACTIVE);
            addProperty(hvacActive);
            return this;
        }

        /**
         * @param defoggingActive Whether defogging is active or not.
         * @return The builder.
         */
        public Builder setDefoggingActive(ObjectProperty<Boolean> defoggingActive) {
            this.defoggingActive = defoggingActive;
            defoggingActive.setIdentifier(IDENTIFIER_DEFOGGING_ACTIVE);
            addProperty(defoggingActive);
            return this;
        }

        /**
         * @param defrostingActive Whether defrosting is active or not.
         * @return The builder.
         */
        public Builder setDefrostingActive(ObjectProperty<Boolean> defrostingActive) {
            this.defrostingActive = defrostingActive;
            defrostingActive.setIdentifier(IDENTIFIER_DEFROSTING_ACTIVE);
            addProperty(defrostingActive);
            return this;
        }

        /**
         * @param ionisingActive Whether ionising is active or not.
         * @return The builder.
         */
        public Builder setIonisingActive(ObjectProperty<Boolean> ionisingActive) {
            this.ionisingActive = ionisingActive;
            ionisingActive.setIdentifier(IDENTIFIER_IONISING_ACTIVE);
            addProperty(ionisingActive);
            return this;
        }

        /**
         * @param defrostingTemperature The defrosting temperature
         * @return The builder.
         */
        public Builder setDefrostingTemperature(FloatProperty defrostingTemperature) {
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
        public Builder setHvacStartingTimes(HvacStartingTime[] hvacStartingTimes) {
            this.hvacStartingTimes.clear();

            for (HvacStartingTime hvacStartingTime : hvacStartingTimes) {
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
        public Builder addHvacStartingTime(HvacStartingTime hvacStartingTime) {
            hvacStartingTime.setIdentifier(IDENTIFIER_HVAC_TIME);
            this.hvacStartingTimes.add(hvacStartingTime);
            addProperty(hvacStartingTime);
            return this;
        }

        /**
         * @param rearTemperatureSetting The rear temperature setting.
         * @return The builder.
         */
        public Builder setRearTemperatureSetting(FloatProperty rearTemperatureSetting) {
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
