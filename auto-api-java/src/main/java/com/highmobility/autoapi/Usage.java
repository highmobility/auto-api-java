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
import com.highmobility.autoapi.value.AccelerationDuration;
import com.highmobility.autoapi.value.DistanceOverTime;
import com.highmobility.autoapi.value.DrivingMode;
import com.highmobility.autoapi.value.DrivingModeActivationPeriod;
import com.highmobility.autoapi.value.DrivingModeEnergyConsumption;
import com.highmobility.autoapi.value.Grade;
import com.highmobility.autoapi.value.TripMeter;
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.autoapi.value.measurement.Energy;
import com.highmobility.autoapi.value.measurement.EnergyEfficiency;
import com.highmobility.autoapi.value.measurement.FuelEfficiency;
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.autoapi.value.measurement.Power;
import com.highmobility.autoapi.value.measurement.Speed;
import com.highmobility.autoapi.value.measurement.Volume;
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
    public static final byte PROPERTY_ODOMETER_AFTER_LAST_TRIP = 0x10;
    public static final byte PROPERTY_SAFETY_DRIVING_SCORE = 0x11;
    public static final byte PROPERTY_RAPID_ACCELERATION_GRADE = 0x12;
    public static final byte PROPERTY_RAPID_DECELERATION_GRADE = 0x13;
    public static final byte PROPERTY_LATE_NIGHT_GRADE = 0x14;
    public static final byte PROPERTY_DISTANCE_OVER_TIME = 0x15;
    public static final byte PROPERTY_ELECTRIC_CONSUMPTION_RATE_SINCE_START = 0x16;
    public static final byte PROPERTY_ELECTRIC_CONSUMPTION_RATE_SINCE_RESET = 0x17;
    public static final byte PROPERTY_ELECTRIC_DISTANCE_LAST_TRIP = 0x18;
    public static final byte PROPERTY_ELECTRIC_DISTANCE_SINCE_RESET = 0x19;
    public static final byte PROPERTY_ELECTRIC_DURATION_LAST_TRIP = 0x1a;
    public static final byte PROPERTY_ELECTRIC_DURATION_SINCE_RESET = 0x1b;
    public static final byte PROPERTY_FUEL_CONSUMPTION_RATE_LAST_TRIP = 0x1c;
    public static final byte PROPERTY_FUEL_CONSUMPTION_RATE_SINCE_RESET = 0x1d;
    public static final byte PROPERTY_AVERAGE_SPEED_LAST_TRIP = 0x1e;
    public static final byte PROPERTY_AVERAGE_SPEED_SINCE_RESET = 0x1f;
    public static final byte PROPERTY_FUEL_DISTANCE_LAST_TRIP = 0x20;
    public static final byte PROPERTY_FUEL_DISTANCE_SINCE_RESET = 0x21;
    public static final byte PROPERTY_DRIVING_DURATION_LAST_TRIP = 0x22;
    public static final byte PROPERTY_DRIVING_DURATION_SINCE_RESET = 0x23;
    public static final byte PROPERTY_ECO_SCORE_TOTAL = 0x24;
    public static final byte PROPERTY_ECO_SCORE_FREE_WHEEL = 0x25;
    public static final byte PROPERTY_ECO_SCORE_CONSTANT = 0x26;
    public static final byte PROPERTY_ECO_SCORE_BONUS_RANGE = 0x27;
    public static final byte PROPERTY_TRIP_METERS = 0x28;
    public static final byte PROPERTY_ELECTRIC_CONSUMPTION_AVERAGE = 0x29;
    public static final byte PROPERTY_BRAKING_EVALUATION = 0x2a;
    public static final byte PROPERTY_AVERAGE_SPEED = 0x2b;
    public static final byte PROPERTY_RECUPERATION_POWER = 0x2c;
    public static final byte PROPERTY_ACCELERATION_DURATIONS = 0x2d;

    /**
     * Get Usage property availability information
     */
    public static class GetUsageAvailability extends GetAvailabilityCommand {
        /**
         * Get every Usage property availability
         */
        public GetUsageAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Usage property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetUsageAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Usage property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetUsageAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetUsageAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get usage
     */
    public static class GetUsage extends GetCommand<State> {
        /**
         * Get all Usage properties
         */
        public GetUsage() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Usage properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetUsage(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Usage properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetUsage(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetUsage(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Usage properties
     * 
     * @deprecated use {@link GetUsage#GetUsage(byte...)} instead
     */
    @Deprecated
    public static class GetUsageProperties extends GetCommand<State> {
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
        Property<Length> averageWeeklyDistance = new Property<>(Length.class, PROPERTY_AVERAGE_WEEKLY_DISTANCE);
        Property<Length> averageWeeklyDistanceLongRun = new Property<>(Length.class, PROPERTY_AVERAGE_WEEKLY_DISTANCE_LONG_RUN);
        Property<Double> accelerationEvaluation = new Property<>(Double.class, PROPERTY_ACCELERATION_EVALUATION);
        Property<Double> drivingStyleEvaluation = new Property<>(Double.class, PROPERTY_DRIVING_STYLE_EVALUATION);
        List<Property<DrivingModeActivationPeriod>> drivingModesActivationPeriods;
        List<Property<DrivingModeEnergyConsumption>> drivingModesEnergyConsumptions;
        Property<Energy> lastTripEnergyConsumption = new Property<>(Energy.class, PROPERTY_LAST_TRIP_ENERGY_CONSUMPTION);
        Property<Volume> lastTripFuelConsumption = new Property<>(Volume.class, PROPERTY_LAST_TRIP_FUEL_CONSUMPTION);
        Property<Length> mileageAfterLastTrip = new Property<>(Length.class, PROPERTY_MILEAGE_AFTER_LAST_TRIP);
        Property<Double> lastTripElectricPortion = new Property<>(Double.class, PROPERTY_LAST_TRIP_ELECTRIC_PORTION);
        Property<EnergyEfficiency> lastTripAverageEnergyRecuperation = new Property<>(EnergyEfficiency.class, PROPERTY_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION);
        Property<Double> lastTripBatteryRemaining = new Property<>(Double.class, PROPERTY_LAST_TRIP_BATTERY_REMAINING);
        Property<Calendar> lastTripDate = new Property<>(Calendar.class, PROPERTY_LAST_TRIP_DATE);
        Property<FuelEfficiency> averageFuelConsumption = new Property<>(FuelEfficiency.class, PROPERTY_AVERAGE_FUEL_CONSUMPTION);
        Property<FuelEfficiency> currentFuelConsumption = new Property<>(FuelEfficiency.class, PROPERTY_CURRENT_FUEL_CONSUMPTION);
        Property<Length> odometerAfterLastTrip = new Property<>(Length.class, PROPERTY_ODOMETER_AFTER_LAST_TRIP);
        Property<Double> safetyDrivingScore = new Property<>(Double.class, PROPERTY_SAFETY_DRIVING_SCORE);
        Property<Grade> rapidAccelerationGrade = new Property<>(Grade.class, PROPERTY_RAPID_ACCELERATION_GRADE);
        Property<Grade> rapidDecelerationGrade = new Property<>(Grade.class, PROPERTY_RAPID_DECELERATION_GRADE);
        Property<Grade> lateNightGrade = new Property<>(Grade.class, PROPERTY_LATE_NIGHT_GRADE);
        Property<DistanceOverTime> distanceOverTime = new Property<>(DistanceOverTime.class, PROPERTY_DISTANCE_OVER_TIME);
        Property<EnergyEfficiency> electricConsumptionRateSinceStart = new Property<>(EnergyEfficiency.class, PROPERTY_ELECTRIC_CONSUMPTION_RATE_SINCE_START);
        Property<EnergyEfficiency> electricConsumptionRateSinceReset = new Property<>(EnergyEfficiency.class, PROPERTY_ELECTRIC_CONSUMPTION_RATE_SINCE_RESET);
        Property<Length> electricDistanceLastTrip = new Property<>(Length.class, PROPERTY_ELECTRIC_DISTANCE_LAST_TRIP);
        Property<Length> electricDistanceSinceReset = new Property<>(Length.class, PROPERTY_ELECTRIC_DISTANCE_SINCE_RESET);
        Property<Duration> electricDurationLastTrip = new Property<>(Duration.class, PROPERTY_ELECTRIC_DURATION_LAST_TRIP);
        Property<Duration> electricDurationSinceReset = new Property<>(Duration.class, PROPERTY_ELECTRIC_DURATION_SINCE_RESET);
        Property<FuelEfficiency> fuelConsumptionRateLastTrip = new Property<>(FuelEfficiency.class, PROPERTY_FUEL_CONSUMPTION_RATE_LAST_TRIP);
        Property<FuelEfficiency> fuelConsumptionRateSinceReset = new Property<>(FuelEfficiency.class, PROPERTY_FUEL_CONSUMPTION_RATE_SINCE_RESET);
        Property<Speed> averageSpeedLastTrip = new Property<>(Speed.class, PROPERTY_AVERAGE_SPEED_LAST_TRIP);
        Property<Speed> averageSpeedSinceReset = new Property<>(Speed.class, PROPERTY_AVERAGE_SPEED_SINCE_RESET);
        Property<Length> fuelDistanceLastTrip = new Property<>(Length.class, PROPERTY_FUEL_DISTANCE_LAST_TRIP);
        Property<Length> fuelDistanceSinceReset = new Property<>(Length.class, PROPERTY_FUEL_DISTANCE_SINCE_RESET);
        Property<Duration> drivingDurationLastTrip = new Property<>(Duration.class, PROPERTY_DRIVING_DURATION_LAST_TRIP);
        Property<Duration> drivingDurationSinceReset = new Property<>(Duration.class, PROPERTY_DRIVING_DURATION_SINCE_RESET);
        Property<Double> ecoScoreTotal = new Property<>(Double.class, PROPERTY_ECO_SCORE_TOTAL);
        Property<Double> ecoScoreFreeWheel = new Property<>(Double.class, PROPERTY_ECO_SCORE_FREE_WHEEL);
        Property<Double> ecoScoreConstant = new Property<>(Double.class, PROPERTY_ECO_SCORE_CONSTANT);
        Property<Length> ecoScoreBonusRange = new Property<>(Length.class, PROPERTY_ECO_SCORE_BONUS_RANGE);
        List<Property<TripMeter>> tripMeters;
        Property<EnergyEfficiency> electricConsumptionAverage = new Property<>(EnergyEfficiency.class, PROPERTY_ELECTRIC_CONSUMPTION_AVERAGE);
        Property<Double> brakingEvaluation = new Property<>(Double.class, PROPERTY_BRAKING_EVALUATION);
        Property<Speed> averageSpeed = new Property<>(Speed.class, PROPERTY_AVERAGE_SPEED);
        Property<Power> recuperationPower = new Property<>(Power.class, PROPERTY_RECUPERATION_POWER);
        List<Property<AccelerationDuration>> accelerationDurations;
    
        /**
         * @return Average weekly distance
         */
        public Property<Length> getAverageWeeklyDistance() {
            return averageWeeklyDistance;
        }
    
        /**
         * @return Average weekyl distance over long term
         */
        public Property<Length> getAverageWeeklyDistanceLongRun() {
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
        public List<Property<DrivingModeActivationPeriod>> getDrivingModesActivationPeriods() {
            return drivingModesActivationPeriods;
        }
    
        /**
         * @return The driving modes energy consumptions
         */
        public List<Property<DrivingModeEnergyConsumption>> getDrivingModesEnergyConsumptions() {
            return drivingModesEnergyConsumptions;
        }
    
        /**
         * @return Energy consumption in the last trip
         */
        public Property<Energy> getLastTripEnergyConsumption() {
            return lastTripEnergyConsumption;
        }
    
        /**
         * @return Fuel consumption in the last trip
         */
        public Property<Volume> getLastTripFuelConsumption() {
            return lastTripFuelConsumption;
        }
    
        /**
         * @return Mileage after the last trip
         * @deprecated 'mileage' is an incorrect term for this. Replaced by {@link #getOdometerAfterLastTrip()}
         */
        @Deprecated
        public Property<Length> getMileageAfterLastTrip() {
            return mileageAfterLastTrip;
        }
    
        /**
         * @return Portion of the last trip used in electric mode
         */
        public Property<Double> getLastTripElectricPortion() {
            return lastTripElectricPortion;
        }
    
        /**
         * @return Energy recuperation rate for last trip
         */
        public Property<EnergyEfficiency> getLastTripAverageEnergyRecuperation() {
            return lastTripAverageEnergyRecuperation;
        }
    
        /**
         * @return Battery % remaining after last trip
         */
        public Property<Double> getLastTripBatteryRemaining() {
            return lastTripBatteryRemaining;
        }
    
        /**
         * @return The last trip date
         */
        public Property<Calendar> getLastTripDate() {
            return lastTripDate;
        }
    
        /**
         * @return Average fuel consumption for current trip
         */
        public Property<FuelEfficiency> getAverageFuelConsumption() {
            return averageFuelConsumption;
        }
    
        /**
         * @return Current fuel consumption
         */
        public Property<FuelEfficiency> getCurrentFuelConsumption() {
            return currentFuelConsumption;
        }
    
        /**
         * @return Odometer after the last trip
         */
        public Property<Length> getOdometerAfterLastTrip() {
            return odometerAfterLastTrip;
        }
    
        /**
         * @return Safety driving score as percentage
         */
        public Property<Double> getSafetyDrivingScore() {
            return safetyDrivingScore;
        }
    
        /**
         * @return Grade given for rapid acceleration over time
         */
        public Property<Grade> getRapidAccelerationGrade() {
            return rapidAccelerationGrade;
        }
    
        /**
         * @return Grade given for rapid deceleration over time
         */
        public Property<Grade> getRapidDecelerationGrade() {
            return rapidDecelerationGrade;
        }
    
        /**
         * @return Grade given for late night driving over time
         */
        public Property<Grade> getLateNightGrade() {
            return lateNightGrade;
        }
    
        /**
         * @return Distance driven over a given time period
         */
        public Property<DistanceOverTime> getDistanceOverTime() {
            return distanceOverTime;
        }
    
        /**
         * @return Electric energy consumption rate since the start of a trip
         */
        public Property<EnergyEfficiency> getElectricConsumptionRateSinceStart() {
            return electricConsumptionRateSinceStart;
        }
    
        /**
         * @return Electric energy consumption rate since a reset
         */
        public Property<EnergyEfficiency> getElectricConsumptionRateSinceReset() {
            return electricConsumptionRateSinceReset;
        }
    
        /**
         * @return Distance travelled with electricity in last trip
         */
        public Property<Length> getElectricDistanceLastTrip() {
            return electricDistanceLastTrip;
        }
    
        /**
         * @return Distance travelled with electricity since reset
         */
        public Property<Length> getElectricDistanceSinceReset() {
            return electricDistanceSinceReset;
        }
    
        /**
         * @return Duration of travelling using electricity during last trip
         */
        public Property<Duration> getElectricDurationLastTrip() {
            return electricDurationLastTrip;
        }
    
        /**
         * @return Duration of travelling using electricity since reset
         */
        public Property<Duration> getElectricDurationSinceReset() {
            return electricDurationSinceReset;
        }
    
        /**
         * @return Liquid fuel consumption rate during last trip
         */
        public Property<FuelEfficiency> getFuelConsumptionRateLastTrip() {
            return fuelConsumptionRateLastTrip;
        }
    
        /**
         * @return Liquid fuel consumption rate since reset
         */
        public Property<FuelEfficiency> getFuelConsumptionRateSinceReset() {
            return fuelConsumptionRateSinceReset;
        }
    
        /**
         * @return Average speed during last trip
         */
        public Property<Speed> getAverageSpeedLastTrip() {
            return averageSpeedLastTrip;
        }
    
        /**
         * @return Average speed since reset
         */
        public Property<Speed> getAverageSpeedSinceReset() {
            return averageSpeedSinceReset;
        }
    
        /**
         * @return Distance travelled with (liquid) fuel during last trip
         */
        public Property<Length> getFuelDistanceLastTrip() {
            return fuelDistanceLastTrip;
        }
    
        /**
         * @return Distance travelled with (liquid) fuel since reset
         */
        public Property<Length> getFuelDistanceSinceReset() {
            return fuelDistanceSinceReset;
        }
    
        /**
         * @return Duration of last trip
         */
        public Property<Duration> getDrivingDurationLastTrip() {
            return drivingDurationLastTrip;
        }
    
        /**
         * @return Duration of travelling since reset
         */
        public Property<Duration> getDrivingDurationSinceReset() {
            return drivingDurationSinceReset;
        }
    
        /**
         * @return Overall eco-score rating
         */
        public Property<Double> getEcoScoreTotal() {
            return ecoScoreTotal;
        }
    
        /**
         * @return Eco-score rating for free-wheeling
         */
        public Property<Double> getEcoScoreFreeWheel() {
            return ecoScoreFreeWheel;
        }
    
        /**
         * @return Eco-score rating constant
         */
        public Property<Double> getEcoScoreConstant() {
            return ecoScoreConstant;
        }
    
        /**
         * @return Eco-score bonus range
         */
        public Property<Length> getEcoScoreBonusRange() {
            return ecoScoreBonusRange;
        }
    
        /**
         * @return The trip meters
         */
        public List<Property<TripMeter>> getTripMeters() {
            return tripMeters;
        }
    
        /**
         * @return Average electric energy consumption calculated based on the last 20km
         */
        public Property<EnergyEfficiency> getElectricConsumptionAverage() {
            return electricConsumptionAverage;
        }
    
        /**
         * @return Braking evaluation percentage
         */
        public Property<Double> getBrakingEvaluation() {
            return brakingEvaluation;
        }
    
        /**
         * @return Average speed at data collection.
         */
        public Property<Speed> getAverageSpeed() {
            return averageSpeed;
        }
    
        /**
         * @return Recuperation energy of the drivetrain.
         */
        public Property<Power> getRecuperationPower() {
            return recuperationPower;
        }
    
        /**
         * @return Durations of normal or other accelerations.
         */
        public List<Property<AccelerationDuration>> getAccelerationDurations() {
            return accelerationDurations;
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
    
        State(byte[] bytes) {
            super(bytes);
    
            final ArrayList<Property<DrivingModeActivationPeriod>> drivingModesActivationPeriodsBuilder = new ArrayList<>();
            final ArrayList<Property<DrivingModeEnergyConsumption>> drivingModesEnergyConsumptionsBuilder = new ArrayList<>();
            final ArrayList<Property<TripMeter>> tripMetersBuilder = new ArrayList<>();
            final ArrayList<Property<AccelerationDuration>> accelerationDurationsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_AVERAGE_WEEKLY_DISTANCE: return averageWeeklyDistance.update(p);
                        case PROPERTY_AVERAGE_WEEKLY_DISTANCE_LONG_RUN: return averageWeeklyDistanceLongRun.update(p);
                        case PROPERTY_ACCELERATION_EVALUATION: return accelerationEvaluation.update(p);
                        case PROPERTY_DRIVING_STYLE_EVALUATION: return drivingStyleEvaluation.update(p);
                        case PROPERTY_DRIVING_MODES_ACTIVATION_PERIODS:
                            Property<DrivingModeActivationPeriod> drivingModesActivationPeriod = new Property<>(DrivingModeActivationPeriod.class, p);
                            drivingModesActivationPeriodsBuilder.add(drivingModesActivationPeriod);
                            return drivingModesActivationPeriod;
                        case PROPERTY_DRIVING_MODES_ENERGY_CONSUMPTIONS:
                            Property<DrivingModeEnergyConsumption> drivingModeEnergyConsumption = new Property<>(DrivingModeEnergyConsumption.class, p);
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
                        case PROPERTY_ODOMETER_AFTER_LAST_TRIP: return odometerAfterLastTrip.update(p);
                        case PROPERTY_SAFETY_DRIVING_SCORE: return safetyDrivingScore.update(p);
                        case PROPERTY_RAPID_ACCELERATION_GRADE: return rapidAccelerationGrade.update(p);
                        case PROPERTY_RAPID_DECELERATION_GRADE: return rapidDecelerationGrade.update(p);
                        case PROPERTY_LATE_NIGHT_GRADE: return lateNightGrade.update(p);
                        case PROPERTY_DISTANCE_OVER_TIME: return distanceOverTime.update(p);
                        case PROPERTY_ELECTRIC_CONSUMPTION_RATE_SINCE_START: return electricConsumptionRateSinceStart.update(p);
                        case PROPERTY_ELECTRIC_CONSUMPTION_RATE_SINCE_RESET: return electricConsumptionRateSinceReset.update(p);
                        case PROPERTY_ELECTRIC_DISTANCE_LAST_TRIP: return electricDistanceLastTrip.update(p);
                        case PROPERTY_ELECTRIC_DISTANCE_SINCE_RESET: return electricDistanceSinceReset.update(p);
                        case PROPERTY_ELECTRIC_DURATION_LAST_TRIP: return electricDurationLastTrip.update(p);
                        case PROPERTY_ELECTRIC_DURATION_SINCE_RESET: return electricDurationSinceReset.update(p);
                        case PROPERTY_FUEL_CONSUMPTION_RATE_LAST_TRIP: return fuelConsumptionRateLastTrip.update(p);
                        case PROPERTY_FUEL_CONSUMPTION_RATE_SINCE_RESET: return fuelConsumptionRateSinceReset.update(p);
                        case PROPERTY_AVERAGE_SPEED_LAST_TRIP: return averageSpeedLastTrip.update(p);
                        case PROPERTY_AVERAGE_SPEED_SINCE_RESET: return averageSpeedSinceReset.update(p);
                        case PROPERTY_FUEL_DISTANCE_LAST_TRIP: return fuelDistanceLastTrip.update(p);
                        case PROPERTY_FUEL_DISTANCE_SINCE_RESET: return fuelDistanceSinceReset.update(p);
                        case PROPERTY_DRIVING_DURATION_LAST_TRIP: return drivingDurationLastTrip.update(p);
                        case PROPERTY_DRIVING_DURATION_SINCE_RESET: return drivingDurationSinceReset.update(p);
                        case PROPERTY_ECO_SCORE_TOTAL: return ecoScoreTotal.update(p);
                        case PROPERTY_ECO_SCORE_FREE_WHEEL: return ecoScoreFreeWheel.update(p);
                        case PROPERTY_ECO_SCORE_CONSTANT: return ecoScoreConstant.update(p);
                        case PROPERTY_ECO_SCORE_BONUS_RANGE: return ecoScoreBonusRange.update(p);
                        case PROPERTY_TRIP_METERS:
                            Property<TripMeter> tripMeter = new Property<>(TripMeter.class, p);
                            tripMetersBuilder.add(tripMeter);
                            return tripMeter;
                        case PROPERTY_ELECTRIC_CONSUMPTION_AVERAGE: return electricConsumptionAverage.update(p);
                        case PROPERTY_BRAKING_EVALUATION: return brakingEvaluation.update(p);
                        case PROPERTY_AVERAGE_SPEED: return averageSpeed.update(p);
                        case PROPERTY_RECUPERATION_POWER: return recuperationPower.update(p);
                        case PROPERTY_ACCELERATION_DURATIONS:
                            Property<AccelerationDuration> accelerationDuration = new Property<>(AccelerationDuration.class, p);
                            accelerationDurationsBuilder.add(accelerationDuration);
                            return accelerationDuration;
                    }
    
                    return null;
                });
            }
    
            drivingModesActivationPeriods = drivingModesActivationPeriodsBuilder;
            drivingModesEnergyConsumptions = drivingModesEnergyConsumptionsBuilder;
            tripMeters = tripMetersBuilder;
            accelerationDurations = accelerationDurationsBuilder;
        }
    
        public static final class Builder extends SetCommand.Builder<Builder> {
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                SetCommand baseSetCommand = super.build();
                Command resolved = CommandResolver.resolve(baseSetCommand.getByteArray());
                return (State) resolved;
            }
    
            /**
             * @param averageWeeklyDistance Average weekly distance
             * @return The builder
             */
            public Builder setAverageWeeklyDistance(Property<Length> averageWeeklyDistance) {
                Property property = averageWeeklyDistance.setIdentifier(PROPERTY_AVERAGE_WEEKLY_DISTANCE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param averageWeeklyDistanceLongRun Average weekyl distance over long term
             * @return The builder
             */
            public Builder setAverageWeeklyDistanceLongRun(Property<Length> averageWeeklyDistanceLongRun) {
                Property property = averageWeeklyDistanceLongRun.setIdentifier(PROPERTY_AVERAGE_WEEKLY_DISTANCE_LONG_RUN);
                addProperty(property);
                return this;
            }
            
            /**
             * @param accelerationEvaluation Acceleration evaluation percentage
             * @return The builder
             */
            public Builder setAccelerationEvaluation(Property<Double> accelerationEvaluation) {
                Property property = accelerationEvaluation.setIdentifier(PROPERTY_ACCELERATION_EVALUATION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param drivingStyleEvaluation Driving style evaluation percentage
             * @return The builder
             */
            public Builder setDrivingStyleEvaluation(Property<Double> drivingStyleEvaluation) {
                Property property = drivingStyleEvaluation.setIdentifier(PROPERTY_DRIVING_STYLE_EVALUATION);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of driving modes activation periods
             * 
             * @param drivingModesActivationPeriods The driving modes activation periods
             * @return The builder
             */
            public Builder setDrivingModesActivationPeriods(Property<DrivingModeActivationPeriod>[] drivingModesActivationPeriods) {
                for (int i = 0; i < drivingModesActivationPeriods.length; i++) {
                    addDrivingModesActivationPeriod(drivingModesActivationPeriods[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single driving modes activation period
             * 
             * @param drivingModesActivationPeriod The driving modes activation period
             * @return The builder
             */
            public Builder addDrivingModesActivationPeriod(Property<DrivingModeActivationPeriod> drivingModesActivationPeriod) {
                drivingModesActivationPeriod.setIdentifier(PROPERTY_DRIVING_MODES_ACTIVATION_PERIODS);
                addProperty(drivingModesActivationPeriod);
                return this;
            }
            
            /**
             * Add an array of driving modes energy consumptions
             * 
             * @param drivingModesEnergyConsumptions The driving modes energy consumptions
             * @return The builder
             */
            public Builder setDrivingModesEnergyConsumptions(Property<DrivingModeEnergyConsumption>[] drivingModesEnergyConsumptions) {
                for (int i = 0; i < drivingModesEnergyConsumptions.length; i++) {
                    addDrivingModeEnergyConsumption(drivingModesEnergyConsumptions[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single driving mode energy consumption
             * 
             * @param drivingModeEnergyConsumption The driving mode energy consumption
             * @return The builder
             */
            public Builder addDrivingModeEnergyConsumption(Property<DrivingModeEnergyConsumption> drivingModeEnergyConsumption) {
                drivingModeEnergyConsumption.setIdentifier(PROPERTY_DRIVING_MODES_ENERGY_CONSUMPTIONS);
                addProperty(drivingModeEnergyConsumption);
                return this;
            }
            
            /**
             * @param lastTripEnergyConsumption Energy consumption in the last trip
             * @return The builder
             */
            public Builder setLastTripEnergyConsumption(Property<Energy> lastTripEnergyConsumption) {
                Property property = lastTripEnergyConsumption.setIdentifier(PROPERTY_LAST_TRIP_ENERGY_CONSUMPTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param lastTripFuelConsumption Fuel consumption in the last trip
             * @return The builder
             */
            public Builder setLastTripFuelConsumption(Property<Volume> lastTripFuelConsumption) {
                Property property = lastTripFuelConsumption.setIdentifier(PROPERTY_LAST_TRIP_FUEL_CONSUMPTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param mileageAfterLastTrip Mileage after the last trip
             * @return The builder
             * @deprecated 'mileage' is an incorrect term for this. Replaced by {@link #getOdometerAfterLastTrip()}
             */
            @Deprecated
            public Builder setMileageAfterLastTrip(Property<Length> mileageAfterLastTrip) {
                Property property = mileageAfterLastTrip.setIdentifier(PROPERTY_MILEAGE_AFTER_LAST_TRIP);
                addProperty(property);
                return this;
            }
            
            /**
             * @param lastTripElectricPortion Portion of the last trip used in electric mode
             * @return The builder
             */
            public Builder setLastTripElectricPortion(Property<Double> lastTripElectricPortion) {
                Property property = lastTripElectricPortion.setIdentifier(PROPERTY_LAST_TRIP_ELECTRIC_PORTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param lastTripAverageEnergyRecuperation Energy recuperation rate for last trip
             * @return The builder
             */
            public Builder setLastTripAverageEnergyRecuperation(Property<EnergyEfficiency> lastTripAverageEnergyRecuperation) {
                Property property = lastTripAverageEnergyRecuperation.setIdentifier(PROPERTY_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param lastTripBatteryRemaining Battery % remaining after last trip
             * @return The builder
             */
            public Builder setLastTripBatteryRemaining(Property<Double> lastTripBatteryRemaining) {
                Property property = lastTripBatteryRemaining.setIdentifier(PROPERTY_LAST_TRIP_BATTERY_REMAINING);
                addProperty(property);
                return this;
            }
            
            /**
             * @param lastTripDate The last trip date
             * @return The builder
             */
            public Builder setLastTripDate(Property<Calendar> lastTripDate) {
                Property property = lastTripDate.setIdentifier(PROPERTY_LAST_TRIP_DATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param averageFuelConsumption Average fuel consumption for current trip
             * @return The builder
             */
            public Builder setAverageFuelConsumption(Property<FuelEfficiency> averageFuelConsumption) {
                Property property = averageFuelConsumption.setIdentifier(PROPERTY_AVERAGE_FUEL_CONSUMPTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param currentFuelConsumption Current fuel consumption
             * @return The builder
             */
            public Builder setCurrentFuelConsumption(Property<FuelEfficiency> currentFuelConsumption) {
                Property property = currentFuelConsumption.setIdentifier(PROPERTY_CURRENT_FUEL_CONSUMPTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param odometerAfterLastTrip Odometer after the last trip
             * @return The builder
             */
            public Builder setOdometerAfterLastTrip(Property<Length> odometerAfterLastTrip) {
                Property property = odometerAfterLastTrip.setIdentifier(PROPERTY_ODOMETER_AFTER_LAST_TRIP);
                addProperty(property);
                return this;
            }
            
            /**
             * @param safetyDrivingScore Safety driving score as percentage
             * @return The builder
             */
            public Builder setSafetyDrivingScore(Property<Double> safetyDrivingScore) {
                Property property = safetyDrivingScore.setIdentifier(PROPERTY_SAFETY_DRIVING_SCORE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param rapidAccelerationGrade Grade given for rapid acceleration over time
             * @return The builder
             */
            public Builder setRapidAccelerationGrade(Property<Grade> rapidAccelerationGrade) {
                Property property = rapidAccelerationGrade.setIdentifier(PROPERTY_RAPID_ACCELERATION_GRADE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param rapidDecelerationGrade Grade given for rapid deceleration over time
             * @return The builder
             */
            public Builder setRapidDecelerationGrade(Property<Grade> rapidDecelerationGrade) {
                Property property = rapidDecelerationGrade.setIdentifier(PROPERTY_RAPID_DECELERATION_GRADE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param lateNightGrade Grade given for late night driving over time
             * @return The builder
             */
            public Builder setLateNightGrade(Property<Grade> lateNightGrade) {
                Property property = lateNightGrade.setIdentifier(PROPERTY_LATE_NIGHT_GRADE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param distanceOverTime Distance driven over a given time period
             * @return The builder
             */
            public Builder setDistanceOverTime(Property<DistanceOverTime> distanceOverTime) {
                Property property = distanceOverTime.setIdentifier(PROPERTY_DISTANCE_OVER_TIME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param electricConsumptionRateSinceStart Electric energy consumption rate since the start of a trip
             * @return The builder
             */
            public Builder setElectricConsumptionRateSinceStart(Property<EnergyEfficiency> electricConsumptionRateSinceStart) {
                Property property = electricConsumptionRateSinceStart.setIdentifier(PROPERTY_ELECTRIC_CONSUMPTION_RATE_SINCE_START);
                addProperty(property);
                return this;
            }
            
            /**
             * @param electricConsumptionRateSinceReset Electric energy consumption rate since a reset
             * @return The builder
             */
            public Builder setElectricConsumptionRateSinceReset(Property<EnergyEfficiency> electricConsumptionRateSinceReset) {
                Property property = electricConsumptionRateSinceReset.setIdentifier(PROPERTY_ELECTRIC_CONSUMPTION_RATE_SINCE_RESET);
                addProperty(property);
                return this;
            }
            
            /**
             * @param electricDistanceLastTrip Distance travelled with electricity in last trip
             * @return The builder
             */
            public Builder setElectricDistanceLastTrip(Property<Length> electricDistanceLastTrip) {
                Property property = electricDistanceLastTrip.setIdentifier(PROPERTY_ELECTRIC_DISTANCE_LAST_TRIP);
                addProperty(property);
                return this;
            }
            
            /**
             * @param electricDistanceSinceReset Distance travelled with electricity since reset
             * @return The builder
             */
            public Builder setElectricDistanceSinceReset(Property<Length> electricDistanceSinceReset) {
                Property property = electricDistanceSinceReset.setIdentifier(PROPERTY_ELECTRIC_DISTANCE_SINCE_RESET);
                addProperty(property);
                return this;
            }
            
            /**
             * @param electricDurationLastTrip Duration of travelling using electricity during last trip
             * @return The builder
             */
            public Builder setElectricDurationLastTrip(Property<Duration> electricDurationLastTrip) {
                Property property = electricDurationLastTrip.setIdentifier(PROPERTY_ELECTRIC_DURATION_LAST_TRIP);
                addProperty(property);
                return this;
            }
            
            /**
             * @param electricDurationSinceReset Duration of travelling using electricity since reset
             * @return The builder
             */
            public Builder setElectricDurationSinceReset(Property<Duration> electricDurationSinceReset) {
                Property property = electricDurationSinceReset.setIdentifier(PROPERTY_ELECTRIC_DURATION_SINCE_RESET);
                addProperty(property);
                return this;
            }
            
            /**
             * @param fuelConsumptionRateLastTrip Liquid fuel consumption rate during last trip
             * @return The builder
             */
            public Builder setFuelConsumptionRateLastTrip(Property<FuelEfficiency> fuelConsumptionRateLastTrip) {
                Property property = fuelConsumptionRateLastTrip.setIdentifier(PROPERTY_FUEL_CONSUMPTION_RATE_LAST_TRIP);
                addProperty(property);
                return this;
            }
            
            /**
             * @param fuelConsumptionRateSinceReset Liquid fuel consumption rate since reset
             * @return The builder
             */
            public Builder setFuelConsumptionRateSinceReset(Property<FuelEfficiency> fuelConsumptionRateSinceReset) {
                Property property = fuelConsumptionRateSinceReset.setIdentifier(PROPERTY_FUEL_CONSUMPTION_RATE_SINCE_RESET);
                addProperty(property);
                return this;
            }
            
            /**
             * @param averageSpeedLastTrip Average speed during last trip
             * @return The builder
             */
            public Builder setAverageSpeedLastTrip(Property<Speed> averageSpeedLastTrip) {
                Property property = averageSpeedLastTrip.setIdentifier(PROPERTY_AVERAGE_SPEED_LAST_TRIP);
                addProperty(property);
                return this;
            }
            
            /**
             * @param averageSpeedSinceReset Average speed since reset
             * @return The builder
             */
            public Builder setAverageSpeedSinceReset(Property<Speed> averageSpeedSinceReset) {
                Property property = averageSpeedSinceReset.setIdentifier(PROPERTY_AVERAGE_SPEED_SINCE_RESET);
                addProperty(property);
                return this;
            }
            
            /**
             * @param fuelDistanceLastTrip Distance travelled with (liquid) fuel during last trip
             * @return The builder
             */
            public Builder setFuelDistanceLastTrip(Property<Length> fuelDistanceLastTrip) {
                Property property = fuelDistanceLastTrip.setIdentifier(PROPERTY_FUEL_DISTANCE_LAST_TRIP);
                addProperty(property);
                return this;
            }
            
            /**
             * @param fuelDistanceSinceReset Distance travelled with (liquid) fuel since reset
             * @return The builder
             */
            public Builder setFuelDistanceSinceReset(Property<Length> fuelDistanceSinceReset) {
                Property property = fuelDistanceSinceReset.setIdentifier(PROPERTY_FUEL_DISTANCE_SINCE_RESET);
                addProperty(property);
                return this;
            }
            
            /**
             * @param drivingDurationLastTrip Duration of last trip
             * @return The builder
             */
            public Builder setDrivingDurationLastTrip(Property<Duration> drivingDurationLastTrip) {
                Property property = drivingDurationLastTrip.setIdentifier(PROPERTY_DRIVING_DURATION_LAST_TRIP);
                addProperty(property);
                return this;
            }
            
            /**
             * @param drivingDurationSinceReset Duration of travelling since reset
             * @return The builder
             */
            public Builder setDrivingDurationSinceReset(Property<Duration> drivingDurationSinceReset) {
                Property property = drivingDurationSinceReset.setIdentifier(PROPERTY_DRIVING_DURATION_SINCE_RESET);
                addProperty(property);
                return this;
            }
            
            /**
             * @param ecoScoreTotal Overall eco-score rating
             * @return The builder
             */
            public Builder setEcoScoreTotal(Property<Double> ecoScoreTotal) {
                Property property = ecoScoreTotal.setIdentifier(PROPERTY_ECO_SCORE_TOTAL);
                addProperty(property);
                return this;
            }
            
            /**
             * @param ecoScoreFreeWheel Eco-score rating for free-wheeling
             * @return The builder
             */
            public Builder setEcoScoreFreeWheel(Property<Double> ecoScoreFreeWheel) {
                Property property = ecoScoreFreeWheel.setIdentifier(PROPERTY_ECO_SCORE_FREE_WHEEL);
                addProperty(property);
                return this;
            }
            
            /**
             * @param ecoScoreConstant Eco-score rating constant
             * @return The builder
             */
            public Builder setEcoScoreConstant(Property<Double> ecoScoreConstant) {
                Property property = ecoScoreConstant.setIdentifier(PROPERTY_ECO_SCORE_CONSTANT);
                addProperty(property);
                return this;
            }
            
            /**
             * @param ecoScoreBonusRange Eco-score bonus range
             * @return The builder
             */
            public Builder setEcoScoreBonusRange(Property<Length> ecoScoreBonusRange) {
                Property property = ecoScoreBonusRange.setIdentifier(PROPERTY_ECO_SCORE_BONUS_RANGE);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of trip meters
             * 
             * @param tripMeters The trip meters
             * @return The builder
             */
            public Builder setTripMeters(Property<TripMeter>[] tripMeters) {
                for (int i = 0; i < tripMeters.length; i++) {
                    addTripMeter(tripMeters[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single trip meter
             * 
             * @param tripMeter The trip meter
             * @return The builder
             */
            public Builder addTripMeter(Property<TripMeter> tripMeter) {
                tripMeter.setIdentifier(PROPERTY_TRIP_METERS);
                addProperty(tripMeter);
                return this;
            }
            
            /**
             * @param electricConsumptionAverage Average electric energy consumption calculated based on the last 20km
             * @return The builder
             */
            public Builder setElectricConsumptionAverage(Property<EnergyEfficiency> electricConsumptionAverage) {
                Property property = electricConsumptionAverage.setIdentifier(PROPERTY_ELECTRIC_CONSUMPTION_AVERAGE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param brakingEvaluation Braking evaluation percentage
             * @return The builder
             */
            public Builder setBrakingEvaluation(Property<Double> brakingEvaluation) {
                Property property = brakingEvaluation.setIdentifier(PROPERTY_BRAKING_EVALUATION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param averageSpeed Average speed at data collection.
             * @return The builder
             */
            public Builder setAverageSpeed(Property<Speed> averageSpeed) {
                Property property = averageSpeed.setIdentifier(PROPERTY_AVERAGE_SPEED);
                addProperty(property);
                return this;
            }
            
            /**
             * @param recuperationPower Recuperation energy of the drivetrain.
             * @return The builder
             */
            public Builder setRecuperationPower(Property<Power> recuperationPower) {
                Property property = recuperationPower.setIdentifier(PROPERTY_RECUPERATION_POWER);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of acceleration durations
             * 
             * @param accelerationDurations The acceleration durations. Durations of normal or other accelerations.
             * @return The builder
             */
            public Builder setAccelerationDurations(Property<AccelerationDuration>[] accelerationDurations) {
                for (int i = 0; i < accelerationDurations.length; i++) {
                    addAccelerationDuration(accelerationDurations[i]);
                }
            
                return this;
            }
            /**
             * Add a single acceleration duration
             * 
             * @param accelerationDuration The acceleration duration. Durations of normal or other accelerations.
             * @return The builder
             */
            public Builder addAccelerationDuration(Property<AccelerationDuration> accelerationDuration) {
                accelerationDuration.setIdentifier(PROPERTY_ACCELERATION_DURATIONS);
                addProperty(accelerationDuration);
                return this;
            }
        }
    }
}