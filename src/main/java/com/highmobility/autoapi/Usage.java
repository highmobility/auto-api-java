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

import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.ObjectPropertyPercentage;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.usage.DrivingModeActivationPeriod;
import com.highmobility.autoapi.property.usage.DrivingModeEnergyConsumption;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Nullable;

/**
 * This message is sent when a Get Usage message is received by the car. The new state is included
 * in the message payload and may be the result of user, device or car triggered action.
 */
public class Usage extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.USAGE, 0x01);

    private static final byte IDENTIFIER_AVERAGE_WEEKLY_DISTANCE = ((byte) 0x01);
    private static final byte IDENTIFIER_AVERAGE_WEEKLY_DISTANCE_LONG_TERM = ((byte) 0x02);
    private static final byte IDENTIFIER_ACCELERATION_EVALUATION = ((byte) 0x03);
    private static final byte IDENTIFIER_DRIVING_STYLE_EVALUATION = ((byte) 0x04);
    private static final byte IDENTIFIER_LAST_TRIP_ENERGY_CONSUMPTION = ((byte) 0x07);
    private static final byte IDENTIFIER_LAST_TRIP_FUEL_CONSUMPTION = ((byte) 0x08);
    private static final byte IDENTIFIER_MILEAGE_AFTER_LAST_TRIP = ((byte) 0x97);
    private static final byte IDENTIFIER_LAST_TRIP_ELECTRIC_PORTION = ((byte) 0x0A);
    private static final byte IDENTIFIER_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION = ((byte) 0x0B);
    private static final byte IDENTIFIER_LAST_TRIP_BATTERY_REMAINING = ((byte) 0x0C);
    private static final byte IDENTIFIER_LAST_TRIP_DATE = ((byte) 0x0D);
    private static final byte IDENTIFIER_AVERAGE_FUEL_CONSUMPTION = ((byte) 0x0E);
    private static final byte IDENTIFIER_CURRENT_FUEL_CONSUMPTION = ((byte) 0x0F);

    private ObjectPropertyInteger averageWeeklyDistance =
            new ObjectPropertyInteger(IDENTIFIER_AVERAGE_WEEKLY_DISTANCE, false);
    private ObjectPropertyInteger averageWeeklyDistanceLongTerm =
            new ObjectPropertyInteger(IDENTIFIER_AVERAGE_WEEKLY_DISTANCE_LONG_TERM, false);
    private ObjectPropertyPercentage accelerationEvaluation =
            new ObjectPropertyPercentage(IDENTIFIER_ACCELERATION_EVALUATION);
    private ObjectPropertyPercentage drivingStyleEvaluation =
            new ObjectPropertyPercentage(IDENTIFIER_DRIVING_STYLE_EVALUATION);
    private DrivingModeActivationPeriod[] drivingModeActivationPeriods;
    private DrivingModeEnergyConsumption[] drivingModeEnergyConsumptions;
    private ObjectProperty<Float> lastTripEnergyConsumption = new ObjectProperty<>(Float.class,
            IDENTIFIER_LAST_TRIP_ENERGY_CONSUMPTION);
    private ObjectProperty<Float> lastTripFuelConsumption = new ObjectProperty<>(Float.class,
            IDENTIFIER_LAST_TRIP_FUEL_CONSUMPTION);
    private ObjectProperty<Float> mileageAfterLastTrip = new ObjectProperty<>(Float.class,
            IDENTIFIER_MILEAGE_AFTER_LAST_TRIP);
    private ObjectPropertyPercentage lastTripElectricPortion =
            new ObjectPropertyPercentage(IDENTIFIER_LAST_TRIP_ELECTRIC_PORTION);
    private ObjectProperty<Float> lastTripAverageEnergyRecuperation =
            new ObjectProperty<>(Float.class, IDENTIFIER_LAST_TRIP_AVERAGE_ENERGY_RECUPERATION);
    private ObjectPropertyPercentage lastTripBatteryRemaining =
            new ObjectPropertyPercentage(IDENTIFIER_LAST_TRIP_BATTERY_REMAINING);
    private Calendar lastTripDate;
    private ObjectProperty<Float> averageFuelConsumption = new ObjectProperty<>(Float.class,
            IDENTIFIER_AVERAGE_FUEL_CONSUMPTION);
    private ObjectProperty<Float> currentFuelConsumption = new ObjectProperty<>(Float.class,
            IDENTIFIER_CURRENT_FUEL_CONSUMPTION);

    /**
     * @return The average weekly distance in km.
     */
    @Nullable public ObjectPropertyInteger getAverageWeeklyDistance() {
        return averageWeeklyDistance;
    }

    /**
     * @return The average weekly distance, over long term, in km
     */
    @Nullable public ObjectPropertyInteger getAverageWeeklyDistanceLongTerm() {
        return averageWeeklyDistanceLongTerm;
    }

    /**
     * @return The acceleration evaluation.
     */
    @Nullable public ObjectPropertyPercentage getAccelerationEvaluation() {
        return accelerationEvaluation;
    }

    /**
     * @return The driving style's evaluation in %
     */
    @Nullable public ObjectPropertyPercentage getDrivingStyleEvaluation() {
        return drivingStyleEvaluation;
    }

    /**
     * @return The % values of the period used for the driving modes.
     */
    public DrivingModeActivationPeriod[] getDrivingModeActivationPeriods() {
        return drivingModeActivationPeriods;
    }

    /**
     * @param mode The driving mode.
     * @return The driving mode activation period for given mode.
     */
    @Nullable public DrivingModeActivationPeriod getDrivingModeActivationPeriod(DrivingMode mode) {
        for (DrivingModeActivationPeriod drivingModeActivationPeriod :
                drivingModeActivationPeriods) {
            if (drivingModeActivationPeriod.getDrivingMode() == mode) {
                return drivingModeActivationPeriod;
            }
        }
        return null;
    }

    /**
     * @return The energy consumptions in the driving modes in kWh.
     */
    public DrivingModeEnergyConsumption[] getDrivingModeEnergyConsumptions() {
        return drivingModeEnergyConsumptions;
    }

    /**
     * @param mode The driving mode.
     * @return The driving mode energy consumptionfor given mode.
     */
    @Nullable
    public DrivingModeEnergyConsumption getDrivingModeEnergyConsumption(DrivingMode mode) {
        for (DrivingModeEnergyConsumption drivingModeEnergyConsumption :
                drivingModeEnergyConsumptions) {
            if (drivingModeEnergyConsumption.getDrivingMode() == mode) {
                return drivingModeEnergyConsumption;
            }
        }
        return null;
    }

    /**
     * @return The energy consumption in the last trip in kWh.
     */
    @Nullable public ObjectProperty<Float> getLastTripEnergyConsumption() {
        return lastTripEnergyConsumption;
    }

    /**
     * @return The fuel consumption in the last trip in L.
     */
    @Nullable public ObjectProperty<Float> getLastTripFuelConsumption() {
        return lastTripFuelConsumption;
    }

    /**
     * @return The mileage after the last trip in km.
     */
    @Nullable public ObjectProperty<Float> getMileageAfterLastTrip() {
        return mileageAfterLastTrip;
    }

    /**
     * @return The % of the last trip used in electric mode.
     */
    @Nullable public ObjectPropertyPercentage getLastTripElectricPortion() {
        return lastTripElectricPortion;
    }

    /**
     * @return The energy recuperation rate for last trip, in kWh / 100 km.
     */
    @Nullable public ObjectProperty<Float> getLastTripAverageEnergyRecuperation() {
        return lastTripAverageEnergyRecuperation;
    }

    /**
     * @return The battery % remaining after last trip.
     */
    @Nullable public ObjectPropertyPercentage getLastTripBatteryRemaining() {
        return lastTripBatteryRemaining;
    }

    /**
     * @return The last trip date.
     */
    @Nullable public Calendar getLastTripDate() {
        return lastTripDate;
    }

    /**
     * @return The average fuel consumption in liters/100km.
     */
    @Nullable public ObjectProperty<Float> getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    /**
     * @return The current fuel consumption in liters/100km.
     */
    @Nullable public ObjectProperty<Float> getCurrentFuelConsumption() {
        return currentFuelConsumption;
    }

    Usage(byte[] bytes) {
        super(bytes);

        ArrayList<DrivingModeActivationPeriod> drivingModeActivationPeriods = new ArrayList<>();
        ArrayList<DrivingModeEnergyConsumption> drivingModeEnergyConsumptions = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
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
                        DrivingModeActivationPeriod drivingModeActivationPeriod =
                                new DrivingModeActivationPeriod(p.getByteArray());
                        drivingModeActivationPeriods.add(drivingModeActivationPeriod);
                        return drivingModeActivationPeriod;
                    case DrivingModeEnergyConsumption.IDENTIFIER:
                        DrivingModeEnergyConsumption drivingModeEnergyConsumption =
                                new DrivingModeEnergyConsumption(p.getByteArray());
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
                        lastTripDate = Property.getCalendar(p.getValueBytesArray());
                        return lastTripDate;
                    case IDENTIFIER_AVERAGE_FUEL_CONSUMPTION:
                        return averageFuelConsumption.update(p);
                    case IDENTIFIER_CURRENT_FUEL_CONSUMPTION:
                        return currentFuelConsumption.update(p);
                }

                return null;
            });
        }

        this.drivingModeActivationPeriods =
                drivingModeActivationPeriods.toArray(new DrivingModeActivationPeriod[0]);
        this.drivingModeEnergyConsumptions =
                drivingModeEnergyConsumptions.toArray(new DrivingModeEnergyConsumption[0]);
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
        drivingModeActivationPeriods = builder.drivingModeActivationPeriods.toArray(new
                DrivingModeActivationPeriod[0]);
        drivingModeEnergyConsumptions = builder.drivingModeEnergyConsumptions.toArray(new
                DrivingModeEnergyConsumption[0]);
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

    public static final class Builder extends CommandWithProperties.Builder {
        private ObjectPropertyInteger averageWeeklyDistance;
        private ObjectPropertyInteger averageWeeklyDistanceLongTerm;
        private ObjectPropertyPercentage accelerationEvaluation;
        private ObjectPropertyPercentage drivingStyleEvaluation;
        private List<DrivingModeActivationPeriod> drivingModeActivationPeriods = new ArrayList<>();
        private List<DrivingModeEnergyConsumption> drivingModeEnergyConsumptions = new
                ArrayList<>();
        private ObjectProperty<Float> lastTripEnergyConsumption;
        private ObjectProperty<Float> lastTripFuelConsumption;
        private ObjectProperty<Float> mileageAfterLastTrip;
        private ObjectPropertyPercentage lastTripElectricPortion;
        private ObjectProperty<Float> lastTripAverageEnergyRecuperation;
        private ObjectPropertyPercentage lastTripBatteryRemaining;
        private Calendar lastTripDate;
        private ObjectProperty<Float> averageFuelConsumption;
        private ObjectProperty<Float> currentFuelConsumption;

        // TBODO:
        public Builder() {
            super(TYPE);
        }

        public Usage build() {
            return new Usage(this);
        }
    }
}