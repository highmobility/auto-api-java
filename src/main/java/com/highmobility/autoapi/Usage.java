/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.DrivingMode;
import com.highmobility.autoapi.value.DrivingModeActivationPeriod;
import com.highmobility.autoapi.value.DrivingModeEnergyConsumption;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Nullable;

/**
 * The Usage capability
 */
public class Usage {
    public static final int IDENTIFIER = Identifier.USAGE;

    public static final byte PROPERTY_AVERAGE_WEEKLY_DISTANCE = 0x01;
    public static final byte PROPERTY_AVERAGE_WEEKLY_DISTANCE_LONG_RUN = 0x02;
    public static final byte PROPERTY_ACCELERATION_EVALUATION = 0x03;
    public static final byte PROPERTY_DRIVING_STYLE_EVALUATION = 0x04;
    public static final byte PROPERTY_DRIVING_MODES_ACTIVATION_PERIODS = 0x05;
    public static final byte PROPERTY_DRIVING_MODES_ENERGY_CONSUMPTIONS = 0x06;
    public static final byte PROPERTY_LAST_TRIP_ENERGY_CONSUMPTION = 0x07;
    public static final byte PROPERTY_LAST_TRIP_FUEL_CONSUMPTION = 0x08;
    public static final byte PROPERTY_MILEAGE_AFTER_LAST_TRIP = 0x09;
    public static final byte PROPERTY_LAST_TRIP_ELECTRIC_PORTION = 0x0a;
    public static final byte PROPERTY_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION = 0x0b;
    public static final byte PROPERTY_LAST_TRIP_BATTERY_REMAINING = 0x0c;
    public static final byte PROPERTY_LAST_TRIP_DATE = 0x0d;
    public static final byte PROPERTY_AVERAGE_FUEL_CONSUMPTION = 0x0e;
    public static final byte PROPERTY_CURRENT_FUEL_CONSUMPTION = 0x0f;

    /**
     * Get usage
     */
    public static class GetUsage extends GetCommand {
        public GetUsage() {
            super(State.class, IDENTIFIER);
        }
    
        GetUsage(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific usage properties
     */
    public static class GetUsageProperties extends GetCommand {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetUsageProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetUsageProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetUsageProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The usage state
     */
    public static class State extends SetCommand {
        PropertyInteger averageWeeklyDistance = new PropertyInteger(PROPERTY_AVERAGE_WEEKLY_DISTANCE, false);
        PropertyInteger averageWeeklyDistanceLongRun = new PropertyInteger(PROPERTY_AVERAGE_WEEKLY_DISTANCE_LONG_RUN, false);
        Property<Double> accelerationEvaluation = new Property(Double.class, PROPERTY_ACCELERATION_EVALUATION);
        Property<Double> drivingStyleEvaluation = new Property(Double.class, PROPERTY_DRIVING_STYLE_EVALUATION);
        Property<DrivingModeActivationPeriod>[] drivingModesActivationPeriods;
        Property<DrivingModeEnergyConsumption>[] drivingModesEnergyConsumptions;
        Property<Float> lastTripEnergyConsumption = new Property(Float.class, PROPERTY_LAST_TRIP_ENERGY_CONSUMPTION);
        Property<Float> lastTripFuelConsumption = new Property(Float.class, PROPERTY_LAST_TRIP_FUEL_CONSUMPTION);
        PropertyInteger mileageAfterLastTrip = new PropertyInteger(PROPERTY_MILEAGE_AFTER_LAST_TRIP, false);
        Property<Double> lastTripElectricPortion = new Property(Double.class, PROPERTY_LAST_TRIP_ELECTRIC_PORTION);
        Property<Float> lastTripAverageEnergyRecuperation = new Property(Float.class, PROPERTY_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION);
        Property<Double> lastTripBatteryRemaining = new Property(Double.class, PROPERTY_LAST_TRIP_BATTERY_REMAINING);
        Property<Calendar> lastTripDate = new Property(Calendar.class, PROPERTY_LAST_TRIP_DATE);
        Property<Float> averageFuelConsumption = new Property(Float.class, PROPERTY_AVERAGE_FUEL_CONSUMPTION);
        Property<Float> currentFuelConsumption = new Property(Float.class, PROPERTY_CURRENT_FUEL_CONSUMPTION);
    
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
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> drivingModesActivationPeriodsBuilder = new ArrayList<>();
            ArrayList<Property> drivingModesEnergyConsumptionsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_AVERAGE_WEEKLY_DISTANCE: return averageWeeklyDistance.update(p);
                        case PROPERTY_AVERAGE_WEEKLY_DISTANCE_LONG_RUN: return averageWeeklyDistanceLongRun.update(p);
                        case PROPERTY_ACCELERATION_EVALUATION: return accelerationEvaluation.update(p);
                        case PROPERTY_DRIVING_STYLE_EVALUATION: return drivingStyleEvaluation.update(p);
                        case PROPERTY_DRIVING_MODES_ACTIVATION_PERIODS:
                            Property<DrivingModeActivationPeriod> drivingModesActivationPeriod = new Property(DrivingModeActivationPeriod.class, p);
                            drivingModesActivationPeriodsBuilder.add(drivingModesActivationPeriod);
                            return drivingModesActivationPeriod;
                        case PROPERTY_DRIVING_MODES_ENERGY_CONSUMPTIONS:
                            Property<DrivingModeEnergyConsumption> drivingModeEnergyConsumption = new Property(DrivingModeEnergyConsumption.class, p);
                            drivingModesEnergyConsumptionsBuilder.add(drivingModeEnergyConsumption);
                            return drivingModeEnergyConsumption;
                        case PROPERTY_LAST_TRIP_ENERGY_CONSUMPTION: return lastTripEnergyConsumption.update(p);
                        case PROPERTY_LAST_TRIP_FUEL_CONSUMPTION: return lastTripFuelConsumption.update(p);
                        case PROPERTY_MILEAGE_AFTER_LAST_TRIP: return mileageAfterLastTrip.update(p);
                        case PROPERTY_LAST_TRIP_ELECTRIC_PORTION: return lastTripElectricPortion.update(p);
                        case PROPERTY_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION: return lastTripAverageEnergyRecuperation.update(p);
                        case PROPERTY_LAST_TRIP_BATTERY_REMAINING: return lastTripBatteryRemaining.update(p);
                        case PROPERTY_LAST_TRIP_DATE: return lastTripDate.update(p);
                        case PROPERTY_AVERAGE_FUEL_CONSUMPTION: return averageFuelConsumption.update(p);
                        case PROPERTY_CURRENT_FUEL_CONSUMPTION: return currentFuelConsumption.update(p);
                    }
    
                    return null;
                });
            }
    
            drivingModesActivationPeriods = drivingModesActivationPeriodsBuilder.toArray(new Property[0]);
            drivingModesEnergyConsumptions = drivingModesEnergyConsumptionsBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
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
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param averageWeeklyDistance Average weekly distance in km
             * @return The builder
             */
            public Builder setAverageWeeklyDistance(Property<Integer> averageWeeklyDistance) {
                this.averageWeeklyDistance = new PropertyInteger(PROPERTY_AVERAGE_WEEKLY_DISTANCE, false, 2, averageWeeklyDistance);
                addProperty(this.averageWeeklyDistance);
                return this;
            }
            
            /**
             * @param averageWeeklyDistanceLongRun Average weekyl distance, over long term, in km
             * @return The builder
             */
            public Builder setAverageWeeklyDistanceLongRun(Property<Integer> averageWeeklyDistanceLongRun) {
                this.averageWeeklyDistanceLongRun = new PropertyInteger(PROPERTY_AVERAGE_WEEKLY_DISTANCE_LONG_RUN, false, 2, averageWeeklyDistanceLongRun);
                addProperty(this.averageWeeklyDistanceLongRun);
                return this;
            }
            
            /**
             * @param accelerationEvaluation Acceleration evaluation percentage
             * @return The builder
             */
            public Builder setAccelerationEvaluation(Property<Double> accelerationEvaluation) {
                this.accelerationEvaluation = accelerationEvaluation.setIdentifier(PROPERTY_ACCELERATION_EVALUATION);
                addProperty(this.accelerationEvaluation);
                return this;
            }
            
            /**
             * @param drivingStyleEvaluation Driving style evaluation percentage
             * @return The builder
             */
            public Builder setDrivingStyleEvaluation(Property<Double> drivingStyleEvaluation) {
                this.drivingStyleEvaluation = drivingStyleEvaluation.setIdentifier(PROPERTY_DRIVING_STYLE_EVALUATION);
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
                drivingModesActivationPeriod.setIdentifier(PROPERTY_DRIVING_MODES_ACTIVATION_PERIODS);
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
                drivingModeEnergyConsumption.setIdentifier(PROPERTY_DRIVING_MODES_ENERGY_CONSUMPTIONS);
                addProperty(drivingModeEnergyConsumption);
                drivingModesEnergyConsumptions.add(drivingModeEnergyConsumption);
                return this;
            }
            
            /**
             * @param lastTripEnergyConsumption Energy consumption in the last trip in kWh
             * @return The builder
             */
            public Builder setLastTripEnergyConsumption(Property<Float> lastTripEnergyConsumption) {
                this.lastTripEnergyConsumption = lastTripEnergyConsumption.setIdentifier(PROPERTY_LAST_TRIP_ENERGY_CONSUMPTION);
                addProperty(this.lastTripEnergyConsumption);
                return this;
            }
            
            /**
             * @param lastTripFuelConsumption Fuel consumption in the last trip in L
             * @return The builder
             */
            public Builder setLastTripFuelConsumption(Property<Float> lastTripFuelConsumption) {
                this.lastTripFuelConsumption = lastTripFuelConsumption.setIdentifier(PROPERTY_LAST_TRIP_FUEL_CONSUMPTION);
                addProperty(this.lastTripFuelConsumption);
                return this;
            }
            
            /**
             * @param mileageAfterLastTrip Mileage after the last trip in km
             * @return The builder
             */
            public Builder setMileageAfterLastTrip(Property<Integer> mileageAfterLastTrip) {
                this.mileageAfterLastTrip = new PropertyInteger(PROPERTY_MILEAGE_AFTER_LAST_TRIP, false, 4, mileageAfterLastTrip);
                addProperty(this.mileageAfterLastTrip);
                return this;
            }
            
            /**
             * @param lastTripElectricPortion Portion of the last trip used in electric mode
             * @return The builder
             */
            public Builder setLastTripElectricPortion(Property<Double> lastTripElectricPortion) {
                this.lastTripElectricPortion = lastTripElectricPortion.setIdentifier(PROPERTY_LAST_TRIP_ELECTRIC_PORTION);
                addProperty(this.lastTripElectricPortion);
                return this;
            }
            
            /**
             * @param lastTripAverageEnergyRecuperation Energy recuperation rate for last trip, in kWh / 100 km
             * @return The builder
             */
            public Builder setLastTripAverageEnergyRecuperation(Property<Float> lastTripAverageEnergyRecuperation) {
                this.lastTripAverageEnergyRecuperation = lastTripAverageEnergyRecuperation.setIdentifier(PROPERTY_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION);
                addProperty(this.lastTripAverageEnergyRecuperation);
                return this;
            }
            
            /**
             * @param lastTripBatteryRemaining Battery % remaining after last trip
             * @return The builder
             */
            public Builder setLastTripBatteryRemaining(Property<Double> lastTripBatteryRemaining) {
                this.lastTripBatteryRemaining = lastTripBatteryRemaining.setIdentifier(PROPERTY_LAST_TRIP_BATTERY_REMAINING);
                addProperty(this.lastTripBatteryRemaining);
                return this;
            }
            
            /**
             * @param lastTripDate Milliseconds since UNIX Epoch time
             * @return The builder
             */
            public Builder setLastTripDate(Property<Calendar> lastTripDate) {
                this.lastTripDate = lastTripDate.setIdentifier(PROPERTY_LAST_TRIP_DATE);
                addProperty(this.lastTripDate);
                return this;
            }
            
            /**
             * @param averageFuelConsumption Average fuel consumption for current trip in liters / 100 km
             * @return The builder
             */
            public Builder setAverageFuelConsumption(Property<Float> averageFuelConsumption) {
                this.averageFuelConsumption = averageFuelConsumption.setIdentifier(PROPERTY_AVERAGE_FUEL_CONSUMPTION);
                addProperty(this.averageFuelConsumption);
                return this;
            }
            
            /**
             * @param currentFuelConsumption Current fuel consumption in liters / 100 km
             * @return The builder
             */
            public Builder setCurrentFuelConsumption(Property<Float> currentFuelConsumption) {
                this.currentFuelConsumption = currentFuelConsumption.setIdentifier(PROPERTY_CURRENT_FUEL_CONSUMPTION);
                addProperty(this.currentFuelConsumption);
                return this;
            }
        }
    }
}