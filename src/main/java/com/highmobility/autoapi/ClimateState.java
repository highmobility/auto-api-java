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

import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.HvacStartingTime;

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

    private static final byte INSIDE_TEMPERATURE_IDENTIFIER = 0x01;
    private static final byte OUTSIDE_TEMPERATURE_IDENTIFIER = 0x02;
    private static final byte DRIVER_TEMPERATURE_SETTING_IDENTIFIER = 0x03;
    private static final byte PASSENGER_TEMPERATURE_SETTING_IDENTIFIER = 0x04;
    private static final byte HVAC_ACTIVE_IDENTIFIER = 0x05;
    private static final byte DEFOGGING_ACTIVE_IDENTIFIER = 0x06;
    private static final byte DEFROSTING_ACTIVE_IDENTIFIER = 0x07;
    private static final byte IONISING_ACTIVE_IDENTIFIER = 0x08;
    private static final byte DEFROSTING_TEMPERATURE_IDENTIFIER = 0x09;
    private static final byte HVAC_TIME_IDENTIFIER = 0x0B;
    private static final byte IDENTIFIER_REAR_TEMPERATURE = 0x0C;

    FloatProperty insideTemperature;
    FloatProperty outsideTemperature;
    FloatProperty driverTemperatureSetting;
    FloatProperty passengerTemperatureSetting;
    BooleanProperty hvacActive;
    BooleanProperty defoggingActive;
    BooleanProperty defrostingActive;
    BooleanProperty ionisingActive;
    FloatProperty defrostingTemperature;

    // level8
    HvacStartingTime[] hvacStartingTimes;
    FloatProperty rearTemperatureSetting;

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
    @Nullable public BooleanProperty isHvacActive() {
        return hvacActive;
    }

    /**
     * @return Whether defogging is active or not.
     */
    @Nullable public BooleanProperty isDefoggingActive() {
        return defoggingActive;
    }

    /**
     * @return Whether defrosting is active or not.
     */
    @Nullable public BooleanProperty isDefrostingActive() {
        return defrostingActive;
    }

    /**
     * @return Whether ionising is active or not.
     */
    @Nullable public BooleanProperty isIonisingActive() {
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
    @Nullable public HvacStartingTime getHvacStartingTime(HvacStartingTime.Weekday weekday) {
        for (HvacStartingTime hvacStartingTime : hvacStartingTimes) {
            if (hvacStartingTime.getWeekday() == weekday) return hvacStartingTime;
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

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case INSIDE_TEMPERATURE_IDENTIFIER:
                        insideTemperature = new FloatProperty(p);
                        return insideTemperature;
                    case OUTSIDE_TEMPERATURE_IDENTIFIER:
                        outsideTemperature = new FloatProperty(p);
                        return outsideTemperature;
                    case DRIVER_TEMPERATURE_SETTING_IDENTIFIER:
                        driverTemperatureSetting = new FloatProperty(p);
                        return driverTemperatureSetting;
                    case PASSENGER_TEMPERATURE_SETTING_IDENTIFIER:
                        passengerTemperatureSetting = new FloatProperty(p);
                        return passengerTemperatureSetting;
                    case HVAC_ACTIVE_IDENTIFIER:
                        hvacActive = new BooleanProperty(p);
                        return hvacActive;
                    case DEFOGGING_ACTIVE_IDENTIFIER:
                        defoggingActive = new BooleanProperty(p);
                        return defoggingActive;
                    case DEFROSTING_ACTIVE_IDENTIFIER:
                        defrostingActive = new BooleanProperty(p);
                        return defrostingActive;
                    case IONISING_ACTIVE_IDENTIFIER:
                        ionisingActive = new BooleanProperty(p);
                        return ionisingActive;
                    case DEFROSTING_TEMPERATURE_IDENTIFIER:
                        defrostingTemperature = new FloatProperty(p);
                        return defrostingTemperature;
                    case HVAC_TIME_IDENTIFIER:
                        HvacStartingTime time = new HvacStartingTime(p.getByteArray());
                        builder.add(new HvacStartingTime(p.getByteArray()));
                        return time;
                    case IDENTIFIER_REAR_TEMPERATURE:
                        rearTemperatureSetting = new FloatProperty(p);
                        return rearTemperatureSetting;
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
        private BooleanProperty hvacActive;
        private BooleanProperty defoggingActive;
        private BooleanProperty defrostingActive;
        private BooleanProperty ionisingActive;
        private FloatProperty defrostingTemperature;
        private ArrayList<HvacStartingTime> hvacStartingTimes = new ArrayList<>();

        private FloatProperty rearTemperatureSetting;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param insideTemperature The inside temperature.
         * @return The builder.
         */
        public Builder setInsideTemperature(FloatProperty insideTemperature) {
            this.insideTemperature = insideTemperature;
            insideTemperature.setIdentifier(INSIDE_TEMPERATURE_IDENTIFIER);
            addProperty(insideTemperature);
            return this;
        }

        /**
         * @param outsideTemperature The outside temperature.
         * @return The builder.
         */
        public Builder setOutsideTemperature(FloatProperty outsideTemperature) {
            this.outsideTemperature = outsideTemperature;
            outsideTemperature.setIdentifier(OUTSIDE_TEMPERATURE_IDENTIFIER);
            addProperty(outsideTemperature);
            return this;
        }

        /**
         * @param driverTemperatureSetting The driver temperature.
         * @return The builder.
         */
        public Builder setDriverTemperatureSetting(FloatProperty driverTemperatureSetting) {
            this.driverTemperatureSetting = driverTemperatureSetting;
            driverTemperatureSetting.setIdentifier(DRIVER_TEMPERATURE_SETTING_IDENTIFIER);
            addProperty(driverTemperatureSetting);
            return this;
        }

        /**
         * @param passengerTemperatureSetting The passenger temperature setting.
         * @return The builder.
         */
        public Builder setPassengerTemperatureSetting(FloatProperty passengerTemperatureSetting) {
            this.passengerTemperatureSetting = passengerTemperatureSetting;
            passengerTemperatureSetting.setIdentifier(PASSENGER_TEMPERATURE_SETTING_IDENTIFIER);
            addProperty(passengerTemperatureSetting);
            return this;
        }

        /**
         * @param hvacActive Whether HVAC is active or not.
         * @return The builder.
         */
        public Builder setHvacActive(BooleanProperty hvacActive) {
            this.hvacActive = hvacActive;
            hvacActive.setIdentifier(HVAC_ACTIVE_IDENTIFIER);
            addProperty(hvacActive);
            return this;
        }

        /**
         * @param defoggingActive Whether defogging is active or not.
         * @return The builder.
         */
        public Builder setDefoggingActive(BooleanProperty defoggingActive) {
            this.defoggingActive = defoggingActive;
            defoggingActive.setIdentifier(DEFOGGING_ACTIVE_IDENTIFIER);
            addProperty(defoggingActive);
            return this;
        }

        /**
         * @param defrostingActive Whether defrosting is active or not.
         * @return The builder.
         */
        public Builder setDefrostingActive(BooleanProperty defrostingActive) {
            this.defrostingActive = defrostingActive;
            defrostingActive.setIdentifier(DEFROSTING_ACTIVE_IDENTIFIER);
            addProperty(defrostingActive);
            return this;
        }

        /**
         * @param ionisingActive Whether ionising is active or not.
         * @return The builder.
         */
        public Builder setIonisingActive(BooleanProperty ionisingActive) {
            this.ionisingActive = ionisingActive;
            ionisingActive.setIdentifier(IONISING_ACTIVE_IDENTIFIER);
            addProperty(ionisingActive);
            return this;
        }

        /**
         * @param defrostingTemperature The defrosting temperature
         * @return The builder.
         */
        public Builder setDefrostingTemperature(FloatProperty defrostingTemperature) {
            this.defrostingTemperature = defrostingTemperature;
            defrostingTemperature.setIdentifier(DEFROSTING_TEMPERATURE_IDENTIFIER);
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
            hvacStartingTime.setIdentifier(HVAC_TIME_IDENTIFIER);
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
            rearTemperatureSetting.setIdentifier(IDENTIFIER_REAR_TEMPERATURE);
            addProperty(rearTemperatureSetting);
            return this;
        }

        public ClimateState build() {
            return new ClimateState(this);
        }
    }
}
