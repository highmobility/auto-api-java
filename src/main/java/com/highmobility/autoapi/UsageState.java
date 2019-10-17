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
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.DrivingModeActivationPeriod;
import com.highmobility.autoapi.value.DrivingModeEnergyConsumption;
import java.util.Calendar;
import javax.annotation.Nullable;
import com.highmobility.autoapi.value.DrivingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * The usage state
 */
public class UsageState extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.USAGE;

    public static final byte IDENTIFIER_AVERAGE_WEEKLY_DISTANCE = 0x01;
    public static final byte IDENTIFIER_AVERAGE_WEEKLY_DISTANCE_LONG_RUN = 0x02;
    public static final byte IDENTIFIER_ACCELERATION_EVALUATION = 0x03;
    public static final byte IDENTIFIER_DRIVING_STYLE_EVALUATION = 0x04;
    public static final byte IDENTIFIER_DRIVING_MODES_ACTIVATION_PERIODS = 0x05;
    public static final byte IDENTIFIER_DRIVING_MODES_ENERGY_CONSUMPTIONS = 0x06;
    public static final byte IDENTIFIER_LAST_TRIP_ENERGY_CONSUMPTION = 0x07;
    public static final byte IDENTIFIER_LAST_TRIP_FUEL_CONSUMPTION = 0x08;
    public static final byte IDENTIFIER_MILEAGE_AFTER_LAST_TRIP = 0x09;
    public static final byte IDENTIFIER_LAST_TRIP_ELECTRIC_PORTION = 0x0a;
    public static final byte IDENTIFIER_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION = 0x0b;
    public static final byte IDENTIFIER_LAST_TRIP_BATTERY_REMAINING = 0x0c;
    public static final byte IDENTIFIER_LAST_TRIP_DATE = 0x0d;
    public static final byte IDENTIFIER_AVERAGE_FUEL_CONSUMPTION = 0x0e;
    public static final byte IDENTIFIER_CURRENT_FUEL_CONSUMPTION = 0x0f;

    PropertyInteger averageWeeklyDistance = new PropertyInteger(IDENTIFIER_AVERAGE_WEEKLY_DISTANCE, false);
    PropertyInteger averageWeeklyDistanceLongRun = new PropertyInteger(IDENTIFIER_AVERAGE_WEEKLY_DISTANCE_LONG_RUN, false);
    Property<Double> accelerationEvaluation = new Property(Double.class, IDENTIFIER_ACCELERATION_EVALUATION);
    Property<Double> drivingStyleEvaluation = new Property(Double.class, IDENTIFIER_DRIVING_STYLE_EVALUATION);
    Property<DrivingModeActivationPeriod>[] drivingModesActivationPeriods;
    Property<DrivingModeEnergyConsumption>[] drivingModesEnergyConsumptions;
    Property<Float> lastTripEnergyConsumption = new Property(Float.class, IDENTIFIER_LAST_TRIP_ENERGY_CONSUMPTION);
    Property<Float> lastTripFuelConsumption = new Property(Float.class, IDENTIFIER_LAST_TRIP_FUEL_CONSUMPTION);
    PropertyInteger mileageAfterLastTrip = new PropertyInteger(IDENTIFIER_MILEAGE_AFTER_LAST_TRIP, false);
    Property<Double> lastTripElectricPortion = new Property(Double.class, IDENTIFIER_LAST_TRIP_ELECTRIC_PORTION);
    Property<Float> lastTripAverageEnergyRecuperation = new Property(Float.class, IDENTIFIER_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION);
    Property<Double> lastTripBatteryRemaining = new Property(Double.class, IDENTIFIER_LAST_TRIP_BATTERY_REMAINING);
    Property<Calendar> lastTripDate = new Property(Calendar.class, IDENTIFIER_LAST_TRIP_DATE);
    Property<Float> averageFuelConsumption = new Property(Float.class, IDENTIFIER_AVERAGE_FUEL_CONSUMPTION);
    Property<Float> currentFuelConsumption = new Property(Float.class, IDENTIFIER_CURRENT_FUEL_CONSUMPTION);

    /**
     * @return Average weekly distance in km
     */
    public PropertyInteger getAverageWeeklyDistance() {
        return averageWeeklyDistance;
    }

    /**
     * @return Average weekyl distance, over long term, in km
     */
    public PropertyInteger getAverageWeeklyDistanceLongRun() {
        return averageWeeklyDistanceLongRun;
    }

    /**
     * @return Acceleration evaluation percentage
     */
    public Property<Double> getAccelerationEvaluation() {
        return accelerationEvaluation;
    }

    /**
     * @return Driving style evaluation percentage
     */
    public Property<Double> getDrivingStyleEvaluation() {
        return drivingStyleEvaluation;
    }

    /**
     * @return The driving modes activation periods
     */
    public Property<DrivingModeActivationPeriod>[] getDrivingModesActivationPeriods() {
        return drivingModesActivationPeriods;
    }

    /**
     * @return The driving modes energy consumptions
     */
    public Property<DrivingModeEnergyConsumption>[] getDrivingModesEnergyConsumptions() {
        return drivingModesEnergyConsumptions;
    }

    /**
     * @return Energy consumption in the last trip in kWh
     */
    public Property<Float> getLastTripEnergyConsumption() {
        return lastTripEnergyConsumption;
    }

    /**
     * @return Fuel consumption in the last trip in L
     */
    public Property<Float> getLastTripFuelConsumption() {
        return lastTripFuelConsumption;
    }

    /**
     * @return Mileage after the last trip in km
     */
    public PropertyInteger getMileageAfterLastTrip() {
        return mileageAfterLastTrip;
    }

    /**
     * @return Portion of the last trip used in electric mode
     */
    public Property<Double> getLastTripElectricPortion() {
        return lastTripElectricPortion;
    }

    /**
     * @return Energy recuperation rate for last trip, in kWh / 100 km
     */
    public Property<Float> getLastTripAverageEnergyRecuperation() {
        return lastTripAverageEnergyRecuperation;
    }

    /**
     * @return Battery % remaining after last trip
     */
    public Property<Double> getLastTripBatteryRemaining() {
        return lastTripBatteryRemaining;
    }

    /**
     * @return Milliseconds since UNIX Epoch time
     */
    public Property<Calendar> getLastTripDate() {
        return lastTripDate;
    }

    /**
     * @return Average fuel consumption for current trip in liters / 100 km
     */
    public Property<Float> getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    /**
     * @return Current fuel consumption in liters / 100 km
     */
    public Property<Float> getCurrentFuelConsumption() {
        return currentFuelConsumption;
    }

    /**
     * @param mode The driving mode.
     * @return The driving mode activation period for given mode.
     */
    @Nullable public Property<DrivingModeActivationPeriod> getDrivingModeActivationPeriod(DrivingMode mode) {
        for (Property<DrivingModeActivationPeriod> drivingModeActivationPeriod :
                drivingModesActivationPeriods) {
            if (drivingModeActivationPeriod.getValue() != null && drivingModeActivationPeriod.getValue().getDrivingMode() == mode) {
                return drivingModeActivationPeriod;
            }
        }
        return null;
    }

    /**
     * @param mode The driving mode.
     * @return The driving mode energy consumptionfor given mode.
     */
    @Nullable public Property<DrivingModeEnergyConsumption> getDrivingModeEnergyConsumption(DrivingMode mode) {
        for (Property<DrivingModeEnergyConsumption> drivingModeEnergyConsumption :
                drivingModesEnergyConsumptions) {
            if (drivingModeEnergyConsumption.getValue() != null && drivingModeEnergyConsumption.getValue().getDrivingMode() == mode) {
                return drivingModeEnergyConsumption;
            }
        }
        return null;
    }

    UsageState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> drivingModesActivationPeriodsBuilder = new ArrayList<>();
        ArrayList<Property> drivingModesEnergyConsumptionsBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_AVERAGE_WEEKLY_DISTANCE: return averageWeeklyDistance.update(p);
                    case IDENTIFIER_AVERAGE_WEEKLY_DISTANCE_LONG_RUN: return averageWeeklyDistanceLongRun.update(p);
                    case IDENTIFIER_ACCELERATION_EVALUATION: return accelerationEvaluation.update(p);
                    case IDENTIFIER_DRIVING_STYLE_EVALUATION: return drivingStyleEvaluation.update(p);
                    case IDENTIFIER_DRIVING_MODES_ACTIVATION_PERIODS:
                        Property<DrivingModeActivationPeriod> drivingModesActivationPeriod = new Property(DrivingModeActivationPeriod.class, p);
                        drivingModesActivationPeriodsBuilder.add(drivingModesActivationPeriod);
                        return drivingModesActivationPeriod;
                    case IDENTIFIER_DRIVING_MODES_ENERGY_CONSUMPTIONS:
                        Property<DrivingModeEnergyConsumption> drivingModeEnergyConsumption = new Property(DrivingModeEnergyConsumption.class, p);
                        drivingModesEnergyConsumptionsBuilder.add(drivingModeEnergyConsumption);
                        return drivingModeEnergyConsumption;
                    case IDENTIFIER_LAST_TRIP_ENERGY_CONSUMPTION: return lastTripEnergyConsumption.update(p);
                    case IDENTIFIER_LAST_TRIP_FUEL_CONSUMPTION: return lastTripFuelConsumption.update(p);
                    case IDENTIFIER_MILEAGE_AFTER_LAST_TRIP: return mileageAfterLastTrip.update(p);
                    case IDENTIFIER_LAST_TRIP_ELECTRIC_PORTION: return lastTripElectricPortion.update(p);
                    case IDENTIFIER_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION: return lastTripAverageEnergyRecuperation.update(p);
                    case IDENTIFIER_LAST_TRIP_BATTERY_REMAINING: return lastTripBatteryRemaining.update(p);
                    case IDENTIFIER_LAST_TRIP_DATE: return lastTripDate.update(p);
                    case IDENTIFIER_AVERAGE_FUEL_CONSUMPTION: return averageFuelConsumption.update(p);
                    case IDENTIFIER_CURRENT_FUEL_CONSUMPTION: return currentFuelConsumption.update(p);
                }

                return null;
            });
        }

        drivingModesActivationPeriods = drivingModesActivationPeriodsBuilder.toArray(new Property[0]);
        drivingModesEnergyConsumptions = drivingModesEnergyConsumptionsBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private UsageState(Builder builder) {
        super(builder);

        averageWeeklyDistance = builder.averageWeeklyDistance;
        averageWeeklyDistanceLongRun = builder.averageWeeklyDistanceLongRun;
        accelerationEvaluation = builder.accelerationEvaluation;
        drivingStyleEvaluation = builder.drivingStyleEvaluation;
        drivingModesActivationPeriods = builder.drivingModesActivationPeriods.toArray(new Property[0]);
        drivingModesEnergyConsumptions = builder.drivingModesEnergyConsumptions.toArray(new Property[0]);
        lastTripEnergyConsumption = builder.lastTripEnergyConsumption;
        lastTripFuelConsumption = builder.lastTripFuelConsumption;
        mileageAfterLastTrip = builder.mileageAfterLastTrip;
        lastTripElectricPortion = builder.lastTripElectricPortion;
        lastTripAverageEnergyRecuperation = builder.lastTripAverageEnergyRecuperation;
        lastTripBatteryRemaining = builder.lastTripBatteryRemaining;
        lastTripDate = builder.lastTripDate;
        averageFuelConsumption = builder.averageFuelConsumption;
        currentFuelConsumption = builder.currentFuelConsumption;
    }

    public static final class Builder extends SetCommand.Builder {
        private PropertyInteger averageWeeklyDistance;
        private PropertyInteger averageWeeklyDistanceLongRun;
        private Property<Double> accelerationEvaluation;
        private Property<Double> drivingStyleEvaluation;
        private List<Property> drivingModesActivationPeriods = new ArrayList<>();
        private List<Property> drivingModesEnergyConsumptions = new ArrayList<>();
        private Property<Float> lastTripEnergyConsumption;
        private Property<Float> lastTripFuelConsumption;
        private PropertyInteger mileageAfterLastTrip;
        private Property<Double> lastTripElectricPortion;
        private Property<Float> lastTripAverageEnergyRecuperation;
        private Property<Double> lastTripBatteryRemaining;
        private Property<Calendar> lastTripDate;
        private Property<Float> averageFuelConsumption;
        private Property<Float> currentFuelConsumption;

        public Builder() {
            super(IDENTIFIER);
        }

        public UsageState build() {
            return new UsageState(this);
        }

        /**
         * @param averageWeeklyDistance Average weekly distance in km
         * @return The builder
         */
        public Builder setAverageWeeklyDistance(Property<Integer> averageWeeklyDistance) {
            this.averageWeeklyDistance = new PropertyInteger(IDENTIFIER_AVERAGE_WEEKLY_DISTANCE, false, 2, averageWeeklyDistance);
            addProperty(this.averageWeeklyDistance);
            return this;
        }
        
        /**
         * @param averageWeeklyDistanceLongRun Average weekyl distance, over long term, in km
         * @return The builder
         */
        public Builder setAverageWeeklyDistanceLongRun(Property<Integer> averageWeeklyDistanceLongRun) {
            this.averageWeeklyDistanceLongRun = new PropertyInteger(IDENTIFIER_AVERAGE_WEEKLY_DISTANCE_LONG_RUN, false, 2, averageWeeklyDistanceLongRun);
            addProperty(this.averageWeeklyDistanceLongRun);
            return this;
        }
        
        /**
         * @param accelerationEvaluation Acceleration evaluation percentage
         * @return The builder
         */
        public Builder setAccelerationEvaluation(Property<Double> accelerationEvaluation) {
            this.accelerationEvaluation = accelerationEvaluation.setIdentifier(IDENTIFIER_ACCELERATION_EVALUATION);
            addProperty(this.accelerationEvaluation);
            return this;
        }
        
        /**
         * @param drivingStyleEvaluation Driving style evaluation percentage
         * @return The builder
         */
        public Builder setDrivingStyleEvaluation(Property<Double> drivingStyleEvaluation) {
            this.drivingStyleEvaluation = drivingStyleEvaluation.setIdentifier(IDENTIFIER_DRIVING_STYLE_EVALUATION);
            addProperty(this.drivingStyleEvaluation);
            return this;
        }
        
        /**
         * Add an array of driving modes activation periods.
         * 
         * @param drivingModesActivationPeriods The driving modes activation periods
         * @return The builder
         */
        public Builder setDrivingModesActivationPeriods(Property<DrivingModeActivationPeriod>[] drivingModesActivationPeriods) {
            this.drivingModesActivationPeriods.clear();
            for (int i = 0; i < drivingModesActivationPeriods.length; i++) {
                addDrivingModesActivationPeriod(drivingModesActivationPeriods[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single driving modes activation period.
         * 
         * @param drivingModesActivationPeriod The driving modes activation period
         * @return The builder
         */
        public Builder addDrivingModesActivationPeriod(Property<DrivingModeActivationPeriod> drivingModesActivationPeriod) {
            drivingModesActivationPeriod.setIdentifier(IDENTIFIER_DRIVING_MODES_ACTIVATION_PERIODS);
            addProperty(drivingModesActivationPeriod);
            drivingModesActivationPeriods.add(drivingModesActivationPeriod);
            return this;
        }
        
        /**
         * Add an array of driving modes energy consumptions.
         * 
         * @param drivingModesEnergyConsumptions The driving modes energy consumptions
         * @return The builder
         */
        public Builder setDrivingModesEnergyConsumptions(Property<DrivingModeEnergyConsumption>[] drivingModesEnergyConsumptions) {
            this.drivingModesEnergyConsumptions.clear();
            for (int i = 0; i < drivingModesEnergyConsumptions.length; i++) {
                addDrivingModeEnergyConsumption(drivingModesEnergyConsumptions[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single driving mode energy consumption.
         * 
         * @param drivingModeEnergyConsumption The driving mode energy consumption
         * @return The builder
         */
        public Builder addDrivingModeEnergyConsumption(Property<DrivingModeEnergyConsumption> drivingModeEnergyConsumption) {
            drivingModeEnergyConsumption.setIdentifier(IDENTIFIER_DRIVING_MODES_ENERGY_CONSUMPTIONS);
            addProperty(drivingModeEnergyConsumption);
            drivingModesEnergyConsumptions.add(drivingModeEnergyConsumption);
            return this;
        }
        
        /**
         * @param lastTripEnergyConsumption Energy consumption in the last trip in kWh
         * @return The builder
         */
        public Builder setLastTripEnergyConsumption(Property<Float> lastTripEnergyConsumption) {
            this.lastTripEnergyConsumption = lastTripEnergyConsumption.setIdentifier(IDENTIFIER_LAST_TRIP_ENERGY_CONSUMPTION);
            addProperty(this.lastTripEnergyConsumption);
            return this;
        }
        
        /**
         * @param lastTripFuelConsumption Fuel consumption in the last trip in L
         * @return The builder
         */
        public Builder setLastTripFuelConsumption(Property<Float> lastTripFuelConsumption) {
            this.lastTripFuelConsumption = lastTripFuelConsumption.setIdentifier(IDENTIFIER_LAST_TRIP_FUEL_CONSUMPTION);
            addProperty(this.lastTripFuelConsumption);
            return this;
        }
        
        /**
         * @param mileageAfterLastTrip Mileage after the last trip in km
         * @return The builder
         */
        public Builder setMileageAfterLastTrip(Property<Integer> mileageAfterLastTrip) {
            this.mileageAfterLastTrip = new PropertyInteger(IDENTIFIER_MILEAGE_AFTER_LAST_TRIP, false, 4, mileageAfterLastTrip);
            addProperty(this.mileageAfterLastTrip);
            return this;
        }
        
        /**
         * @param lastTripElectricPortion Portion of the last trip used in electric mode
         * @return The builder
         */
        public Builder setLastTripElectricPortion(Property<Double> lastTripElectricPortion) {
            this.lastTripElectricPortion = lastTripElectricPortion.setIdentifier(IDENTIFIER_LAST_TRIP_ELECTRIC_PORTION);
            addProperty(this.lastTripElectricPortion);
            return this;
        }
        
        /**
         * @param lastTripAverageEnergyRecuperation Energy recuperation rate for last trip, in kWh / 100 km
         * @return The builder
         */
        public Builder setLastTripAverageEnergyRecuperation(Property<Float> lastTripAverageEnergyRecuperation) {
            this.lastTripAverageEnergyRecuperation = lastTripAverageEnergyRecuperation.setIdentifier(IDENTIFIER_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION);
            addProperty(this.lastTripAverageEnergyRecuperation);
            return this;
        }
        
        /**
         * @param lastTripBatteryRemaining Battery % remaining after last trip
         * @return The builder
         */
        public Builder setLastTripBatteryRemaining(Property<Double> lastTripBatteryRemaining) {
            this.lastTripBatteryRemaining = lastTripBatteryRemaining.setIdentifier(IDENTIFIER_LAST_TRIP_BATTERY_REMAINING);
            addProperty(this.lastTripBatteryRemaining);
            return this;
        }
        
        /**
         * @param lastTripDate Milliseconds since UNIX Epoch time
         * @return The builder
         */
        public Builder setLastTripDate(Property<Calendar> lastTripDate) {
            this.lastTripDate = lastTripDate.setIdentifier(IDENTIFIER_LAST_TRIP_DATE);
            addProperty(this.lastTripDate);
            return this;
        }
        
        /**
         * @param averageFuelConsumption Average fuel consumption for current trip in liters / 100 km
         * @return The builder
         */
        public Builder setAverageFuelConsumption(Property<Float> averageFuelConsumption) {
            this.averageFuelConsumption = averageFuelConsumption.setIdentifier(IDENTIFIER_AVERAGE_FUEL_CONSUMPTION);
            addProperty(this.averageFuelConsumption);
            return this;
        }
        
        /**
         * @param currentFuelConsumption Current fuel consumption in liters / 100 km
         * @return The builder
         */
        public Builder setCurrentFuelConsumption(Property<Float> currentFuelConsumption) {
            this.currentFuelConsumption = currentFuelConsumption.setIdentifier(IDENTIFIER_CURRENT_FUEL_CONSUMPTION);
            addProperty(this.currentFuelConsumption);
            return this;
        }
    }
}