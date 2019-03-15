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
import com.highmobility.autoapi.value.DrivingMode;
import com.highmobility.autoapi.value.usage.DrivingModeActivationPeriod;
import com.highmobility.autoapi.value.usage.DrivingModeEnergyConsumption;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Nullable;

/**
 * This message is sent when a Get Usage message is received by the car. The new state is included
 * in the message payload and may be the result of user, device or car triggered action.
 */
public class Usage extends Command {
    public static final Type TYPE = new Type(Identifier.USAGE, 0x01);

    private static final byte IDENTIFIER_AVERAGE_WEEKLY_DISTANCE = ((byte) 0x01);
    private static final byte IDENTIFIER_AVERAGE_WEEKLY_DISTANCE_LONG_TERM = ((byte) 0x02);
    private static final byte IDENTIFIER_ACCELERATION_EVALUATION = ((byte) 0x03);
    private static final byte IDENTIFIER_DRIVING_STYLE_EVALUATION = ((byte) 0x04);
    private static final byte IDENTIFIER_LAST_TRIP_ENERGY_CONSUMPTION = ((byte) 0x07);
    private static final byte IDENTIFIER_LAST_TRIP_FUEL_CONSUMPTION = ((byte) 0x08);
    private static final byte IDENTIFIER_MILEAGE_AFTER_LAST_TRIP = ((byte) 0x09);
    private static final byte IDENTIFIER_LAST_TRIP_ELECTRIC_PORTION = ((byte) 0x0A);
    private static final byte IDENTIFIER_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION = ((byte) 0x0B);
    private static final byte IDENTIFIER_LAST_TRIP_BATTERY_REMAINING = ((byte) 0x0C);
    private static final byte IDENTIFIER_LAST_TRIP_DATE = ((byte) 0x0D);
    private static final byte IDENTIFIER_AVERAGE_FUEL_CONSUMPTION = ((byte) 0x0E);
    private static final byte IDENTIFIER_CURRENT_FUEL_CONSUMPTION = ((byte) 0x0F);

    private PropertyInteger averageWeeklyDistance =
            new PropertyInteger(IDENTIFIER_AVERAGE_WEEKLY_DISTANCE, false);
    private PropertyInteger averageWeeklyDistanceLongTerm =
            new PropertyInteger(IDENTIFIER_AVERAGE_WEEKLY_DISTANCE_LONG_TERM, false);
    private Property<Double> accelerationEvaluation =
            new Property(Double.class, IDENTIFIER_ACCELERATION_EVALUATION);
    private Property<Double> drivingStyleEvaluation =
            new Property(Double.class, IDENTIFIER_DRIVING_STYLE_EVALUATION);
    private Property<DrivingModeActivationPeriod>[] drivingModeActivationPeriods;
    private Property<DrivingModeEnergyConsumption>[] drivingModeEnergyConsumptions;
    private Property<Float> lastTripEnergyConsumption = new Property(Float.class,
            IDENTIFIER_LAST_TRIP_ENERGY_CONSUMPTION);
    private Property<Float> lastTripFuelConsumption = new Property(Float.class,
            IDENTIFIER_LAST_TRIP_FUEL_CONSUMPTION);
    private Property<Float> mileageAfterLastTrip = new Property(Float.class,
            IDENTIFIER_MILEAGE_AFTER_LAST_TRIP);
    private Property<Double> lastTripElectricPortion =
            new Property(Double.class, IDENTIFIER_LAST_TRIP_ELECTRIC_PORTION);
    private Property<Float> lastTripAverageEnergyRecuperation =
            new Property(Float.class, IDENTIFIER_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION);
    private Property<Double> lastTripBatteryRemaining =
            new Property(Double.class, IDENTIFIER_LAST_TRIP_BATTERY_REMAINING);
    private Property<Calendar> lastTripDate = new Property(Calendar.class,
            IDENTIFIER_LAST_TRIP_DATE);
    private Property<Float> averageFuelConsumption = new Property(Float.class,
            IDENTIFIER_AVERAGE_FUEL_CONSUMPTION);
    private Property<Float> currentFuelConsumption = new Property(Float.class,
            IDENTIFIER_CURRENT_FUEL_CONSUMPTION);

    /**
     * @return The average weekly distance in km.
     */
    public Property<Integer> getAverageWeeklyDistance() {
        return averageWeeklyDistance;
    }

    /**
     * @return The average weekly distance, over long term, in km
     */
    public Property<Integer> getAverageWeeklyDistanceLongTerm() {
        return averageWeeklyDistanceLongTerm;
    }

    /**
     * @return The acceleration evaluation.
     */
    public Property<Double> getAccelerationEvaluation() {
        return accelerationEvaluation;
    }

    /**
     * @return The driving style's evaluation in %
     */
    public Property<Double> getDrivingStyleEvaluation() {
        return drivingStyleEvaluation;
    }

    /**
     * @return The % values of the period used for the driving modes.
     */
    public Property<DrivingModeActivationPeriod>[] getDrivingModeActivationPeriods() {
        return drivingModeActivationPeriods;
    }

    /**
     * @param mode The driving mode.
     * @return The driving mode activation period for given mode.
     */
    @Nullable
    public Property<DrivingModeActivationPeriod> getDrivingModeActivationPeriod(DrivingMode mode) {
        for (Property<DrivingModeActivationPeriod> drivingModeActivationPeriod :
                drivingModeActivationPeriods) {
            if (drivingModeActivationPeriod.getValue() != null && drivingModeActivationPeriod.getValue().getDrivingMode() == mode) {
                return drivingModeActivationPeriod;
            }
        }
        return null;
    }

    /**
     * @return The energy consumptions in the driving modes in kWh.
     */
    public Property<DrivingModeEnergyConsumption>[] getDrivingModeEnergyConsumptions() {
        return drivingModeEnergyConsumptions;
    }

    /**
     * @param mode The driving mode.
     * @return The driving mode energy consumptionfor given mode.
     */
    @Nullable
    public Property<DrivingModeEnergyConsumption> getDrivingModeEnergyConsumption(DrivingMode mode) {
        for (Property<DrivingModeEnergyConsumption> drivingModeEnergyConsumption :
                drivingModeEnergyConsumptions) {
            if (drivingModeEnergyConsumption.getValue() != null && drivingModeEnergyConsumption.getValue().getDrivingMode() == mode) {
                return drivingModeEnergyConsumption;
            }
        }
        return null;
    }

    /**
     * @return The energy consumption in the last trip in kWh.
     */
    public Property<Float> getLastTripEnergyConsumption() {
        return lastTripEnergyConsumption;
    }

    /**
     * @return The fuel consumption in the last trip in L.
     */
    public Property<Float> getLastTripFuelConsumption() {
        return lastTripFuelConsumption;
    }

    /**
     * @return The mileage after the last trip in km.
     */
    public Property<Float> getMileageAfterLastTrip() {
        return mileageAfterLastTrip;
    }

    /**
     * @return The % of the last trip used in electric mode.
     */
    public Property<Double> getLastTripElectricPortion() {
        return lastTripElectricPortion;
    }

    /**
     * @return The energy recuperation rate for last trip, in kWh / 100 km.
     */
    public Property<Float> getLastTripAverageEnergyRecuperation() {
        return lastTripAverageEnergyRecuperation;
    }

    /**
     * @return The battery % remaining after last trip.
     */
    public Property<Double> getLastTripBatteryRemaining() {
        return lastTripBatteryRemaining;
    }

    /**
     * @return The last trip date.
     */
    public Property<Calendar> getLastTripDate() {
        return lastTripDate;
    }

    /**
     * @return The average fuel consumption in liters/100km.
     */
    public Property<Float> getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    /**
     * @return The current fuel consumption in liters/100km.
     */
    @Nullable public Property<Float> getCurrentFuelConsumption() {
        return currentFuelConsumption;
    }

    Usage(byte[] bytes) {
        super(bytes);

        ArrayList<Property> drivingModeActivationPeriods =
                new ArrayList<>();
        ArrayList<Property> drivingModeEnergyConsumptions =
                new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_AVERAGE_WEEKLY_DISTANCE:
                        return averageWeeklyDistance.update(p);
                    case IDENTIFIER_AVERAGE_WEEKLY_DISTANCE_LONG_TERM:
                        return averageWeeklyDistanceLongTerm.update(p);
                    case IDENTIFIER_ACCELERATION_EVALUATION:
                        return accelerationEvaluation.update(p);
                    case IDENTIFIER_DRIVING_STYLE_EVALUATION:
                        return drivingStyleEvaluation.update(p);
                    case DrivingModeActivationPeriod.IDENTIFIER:
                        Property drivingModeActivationPeriod =
                                new Property(DrivingModeActivationPeriod.class, p);
                        drivingModeActivationPeriods.add(drivingModeActivationPeriod);
                        return drivingModeActivationPeriod;
                    case DrivingModeEnergyConsumption.IDENTIFIER:
                        Property drivingModeEnergyConsumption =
                                new Property(DrivingModeEnergyConsumption.class, p);
                        drivingModeEnergyConsumptions.add(drivingModeEnergyConsumption);
                        return drivingModeEnergyConsumption;
                    case IDENTIFIER_LAST_TRIP_ENERGY_CONSUMPTION:
                        return lastTripEnergyConsumption.update(p);
                    case IDENTIFIER_LAST_TRIP_FUEL_CONSUMPTION:
                        return lastTripFuelConsumption.update(p);
                    case IDENTIFIER_MILEAGE_AFTER_LAST_TRIP:
                        return mileageAfterLastTrip.update(p);
                    case IDENTIFIER_LAST_TRIP_ELECTRIC_PORTION:
                        return lastTripElectricPortion.update(p);
                    case IDENTIFIER_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION:
                        return lastTripAverageEnergyRecuperation.update(p);
                    case IDENTIFIER_LAST_TRIP_BATTERY_REMAINING:
                        return lastTripBatteryRemaining.update(p);
                    case IDENTIFIER_LAST_TRIP_DATE:
                        return lastTripDate.update(p);
                    case IDENTIFIER_AVERAGE_FUEL_CONSUMPTION:
                        return averageFuelConsumption.update(p);
                    case IDENTIFIER_CURRENT_FUEL_CONSUMPTION:
                        return currentFuelConsumption.update(p);
                }

                return null;
            });
        }

        this.drivingModeActivationPeriods = drivingModeActivationPeriods.toArray(new Property[0]);
        this.drivingModeEnergyConsumptions = drivingModeEnergyConsumptions.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private Usage(Builder builder) {
        super(builder);

        averageWeeklyDistance = builder.averageWeeklyDistance;
        averageWeeklyDistanceLongTerm = builder.averageWeeklyDistanceLongTerm;
        accelerationEvaluation = builder.accelerationEvaluation;
        drivingStyleEvaluation = builder.drivingStyleEvaluation;
        drivingModeActivationPeriods =
                builder.drivingModeActivationPeriods.toArray(new Property[0]);
        drivingModeEnergyConsumptions =
                builder.drivingModeEnergyConsumptions.toArray(new Property[0]);
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

    public static final class Builder extends Command.Builder {
        private PropertyInteger averageWeeklyDistance;
        private PropertyInteger averageWeeklyDistanceLongTerm;
        private Property<Double> accelerationEvaluation;
        private Property<Double> drivingStyleEvaluation;
        private List<Property> drivingModeActivationPeriods =
                new ArrayList<>();
        private List<Property> drivingModeEnergyConsumptions = new
                ArrayList<>();
        private Property<Float> lastTripEnergyConsumption;
        private Property<Float> lastTripFuelConsumption;
        private Property<Float> mileageAfterLastTrip;
        private Property<Double> lastTripElectricPortion;
        private Property<Float> lastTripAverageEnergyRecuperation;
        private Property<Double> lastTripBatteryRemaining;
        private Property<Calendar> lastTripDate;
        private Property<Float> averageFuelConsumption;
        private Property<Float> currentFuelConsumption;

        // TBODO:
        public Builder() {
            super(TYPE);
        }

        public Usage build() {
            return new Usage(this);
        }
    }
}