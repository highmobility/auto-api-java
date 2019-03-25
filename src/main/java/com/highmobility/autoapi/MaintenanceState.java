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
import com.highmobility.autoapi.value.maintenance.ConditionBasedService;
import com.highmobility.autoapi.value.maintenance.TeleserviceAvailability;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Command sent when a Get Maintenance State message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class MaintenanceState extends Command {
    public static final Type TYPE = new Type(Identifier.MAINTENANCE, 0x01);

    private static final byte IDENTIFIER_DAYS_TO_NEXT_SERVICE = 0x01;
    private static final byte IDENTIFIER_KILOMETERS_TO_NEXT_SERVICE = 0x02;
    private static final byte IDENTIFIER_CBS_REPORTS_COUNT = 0x03;
    private static final byte IDENTIFIER_MONTHS_TO_EXHAUST_INSPECTION = 0x04;
    private static final byte IDENTIFIER_SERVICE_DISTANCE_THRESHOLD = 0x06;
    private static final byte IDENTIFIER_SERVICE_TIME_THRESHOLD = 0x07;
    private static final byte IDENTIFIER_AUTOMATIC_TELESERVICE_CALL_DATE = 0x08;
    private static final byte IDENTIFIER_TELESERVICE_BATTERY_CALL_DATE = 0x09;
    private static final byte IDENTIFIER_NEXT_INSPECTION_DATE = 0x0A;
    private static final byte IDENTIFIER_CONDITION_BASED_SERVICES = 0x0B;
    private static final byte IDENTIFIER_BRAKE_FLUID_CHANGE_DATE = 0x0C;
    private static final byte IDENTIFIER_TELESERVICE_AVAILABILITY = 0x05;

    private PropertyInteger kilometersToNextService =
            new PropertyInteger(IDENTIFIER_KILOMETERS_TO_NEXT_SERVICE, false);
    private PropertyInteger daysToNextService =
            new PropertyInteger(IDENTIFIER_DAYS_TO_NEXT_SERVICE, false);

    // level8
    private PropertyInteger cbsReportsCount =
            new PropertyInteger(IDENTIFIER_CBS_REPORTS_COUNT, false);
    private PropertyInteger monthsToExhaustInspection =
            new PropertyInteger(IDENTIFIER_MONTHS_TO_EXHAUST_INSPECTION, false);
    private Property<TeleserviceAvailability> teleserviceAvailability =
            new Property(TeleserviceAvailability.class, IDENTIFIER_TELESERVICE_AVAILABILITY);
    private PropertyInteger serviceDistanceThreshold =
            new PropertyInteger(IDENTIFIER_SERVICE_DISTANCE_THRESHOLD, false);
    private PropertyInteger serviceTimeThreshold =
            new PropertyInteger(IDENTIFIER_SERVICE_TIME_THRESHOLD, false);

    private Property<Calendar> automaticTeleserviceCallDate = new Property(Calendar.class,
            IDENTIFIER_AUTOMATIC_TELESERVICE_CALL_DATE);
    private Property<Calendar> teleserviceBatteryCallDate = new Property(Calendar.class,
            IDENTIFIER_TELESERVICE_BATTERY_CALL_DATE);
    private Property<Calendar> nextInspectionDate = new Property(Calendar.class,
            IDENTIFIER_NEXT_INSPECTION_DATE);
    private Property<ConditionBasedService>[] conditionBasedServices;
    private Property<Calendar> brakeFluidChangeDate = new Property(Calendar.class,
            IDENTIFIER_BRAKE_FLUID_CHANGE_DATE);

    /**
     * @return The amount of kilometers until next servicing of the car
     */
    public Property<Integer> getKilometersToNextService() {
        return kilometersToNextService;
    }

    /**
     * @return The number of days until next servicing of the car, whereas negative is overdue
     */
    public Property<Integer> getDaysToNextService() {
        return daysToNextService;
    }

    /**
     * @return The number of CBS reports.
     */
    public Property<Integer> getCbsReportsCount() {
        return cbsReportsCount;
    }

    /**
     * @return The number of Months until exhaust inspection.
     */
    public Property<Integer> getMonthsToExhaustInspection() {
        return monthsToExhaustInspection;
    }

    /**
     * @return The Teleservice availability.
     */
    public Property<TeleserviceAvailability> getTeleserviceAvailability() {
        return teleserviceAvailability;
    }

    /**
     * @return The service distance threshold in km.
     */
    public Property<Integer> getServiceDistanceThreshold() {
        return serviceDistanceThreshold;
    }

    /**
     * @return The service time threshold in weeks.
     */
    public Property<Integer> getServiceTimeThreshold() {
        return serviceTimeThreshold;
    }

    /**
     * @return The automatic Teleservice call date.
     */
    public Property<Calendar> getAutomaticTeleserviceCallDate() {
        return automaticTeleserviceCallDate;
    }

    /**
     * @return The Teleservice battery call date.
     */
    public Property<Calendar> getTeleserviceBatteryCallDate() {
        return teleserviceBatteryCallDate;
    }

    /**
     * @return The next inspection date.
     */
    public Property<Calendar> getNextInspectionDate() {
        return nextInspectionDate;
    }

    /**
     * @return The condition based services.
     */
    public Property<ConditionBasedService>[] getConditionBasedServices() {
        return conditionBasedServices;
    }

    /**
     * @return The brake fluid change date.
     */
    public Property<Calendar> getBrakeFluidChangeDate() {
        return brakeFluidChangeDate;
    }

    MaintenanceState(byte[] bytes) {
        super(bytes);
        ArrayList<Property<ConditionBasedService>> conditionBasedServices = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_DAYS_TO_NEXT_SERVICE:
                        return daysToNextService.update(p);
                    case IDENTIFIER_KILOMETERS_TO_NEXT_SERVICE:
                        return kilometersToNextService.update(p);
                    case IDENTIFIER_CBS_REPORTS_COUNT:
                        return cbsReportsCount.update(p);
                    case IDENTIFIER_MONTHS_TO_EXHAUST_INSPECTION:
                        return monthsToExhaustInspection.update(p);
                    case IDENTIFIER_TELESERVICE_AVAILABILITY:
                        return teleserviceAvailability.update(p);
                    case IDENTIFIER_SERVICE_DISTANCE_THRESHOLD:
                        return serviceDistanceThreshold.update(p);
                    case IDENTIFIER_SERVICE_TIME_THRESHOLD:
                        return serviceTimeThreshold.update(p);
                    case IDENTIFIER_AUTOMATIC_TELESERVICE_CALL_DATE:
                        return automaticTeleserviceCallDate.update(p);
                    case IDENTIFIER_TELESERVICE_BATTERY_CALL_DATE:
                        return teleserviceBatteryCallDate.update(p);
                    case IDENTIFIER_NEXT_INSPECTION_DATE:
                        return nextInspectionDate.update(p);
                    case IDENTIFIER_CONDITION_BASED_SERVICES:
                        Property s = new Property(ConditionBasedService.class, p);
                        conditionBasedServices.add(s);
                        return s;
                    case IDENTIFIER_BRAKE_FLUID_CHANGE_DATE:
                        return brakeFluidChangeDate.update(p);
                }

                return null;
            });
        }

        this.conditionBasedServices = conditionBasedServices.toArray(new Property[0]);
    }

    private MaintenanceState(Builder builder) {
        super(builder);
        kilometersToNextService = builder.kilometersToNextService;
        daysToNextService = builder.daysToNextService;
        this.cbsReportsCount = builder.cbsReportsCount;
        this.monthsToExhaustInspection = builder.monthsToExhaustInspection;
        this.teleserviceAvailability = builder.teleserviceAvailability;
        this.serviceDistanceThreshold = builder.serviceDistanceThreshold;
        this.serviceTimeThreshold = builder.serviceTimeThreshold;
        this.automaticTeleserviceCallDate = builder.automaticTeleserviceCallDate;
        this.teleserviceBatteryCallDate = builder.teleserviceBatteryCallDate;
        this.nextInspectionDate = builder.nextInspectionDate;
        this.conditionBasedServices = builder.conditionBasedServices.toArray(new Property[0]);
        this.brakeFluidChangeDate = builder.brakeFluidChangeDate;
    }

    @Override public boolean isState() {
        return true;
    }

    public static final class Builder extends Command.Builder {
        private PropertyInteger kilometersToNextService;
        private PropertyInteger daysToNextService;
        private PropertyInteger cbsReportsCount;
        private PropertyInteger monthsToExhaustInspection;
        private Property teleserviceAvailability;
        private PropertyInteger serviceDistanceThreshold;
        private PropertyInteger serviceTimeThreshold;
        private Property automaticTeleserviceCallDate;
        private Property teleserviceBatteryCallDate;
        private Property nextInspectionDate;
        private ArrayList<Property> conditionBasedServices = new ArrayList<>();
        private Property brakeFluidChangeDate;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param kilometersToNextService The amount of kilometers until next servicing of the car.
         * @return The builder.
         */
        public Builder setKilometersToNextService(Property<Integer> kilometersToNextService) {
            this.kilometersToNextService =
                    new PropertyInteger(IDENTIFIER_KILOMETERS_TO_NEXT_SERVICE, false, 3,
                            kilometersToNextService);
            addProperty(this.kilometersToNextService);
            return this;
        }

        /**
         * @param daysToNextService The number of days until next servicing of the car, whereas
         *                          negative is overdue.
         * @return The builder.
         */
        public Builder setDaysToNextService(Property<Integer> daysToNextService) {
            this.daysToNextService = new PropertyInteger(IDENTIFIER_DAYS_TO_NEXT_SERVICE, false,
                    2, daysToNextService);

            addProperty(this.daysToNextService);
            return this;
        }

        /**
         * @param cbsReportsCount The cbs report count.
         * @return The builder.
         */
        public Builder setCbsReportsCount(Property<Integer> cbsReportsCount) {
            this.cbsReportsCount = new PropertyInteger(IDENTIFIER_CBS_REPORTS_COUNT, false, 1,
                    cbsReportsCount);
            addProperty(this.cbsReportsCount);
            return this;
        }

        /**
         * @param monthsToExhaustInspection The months to exhaust inspection.
         * @return The builder.
         */
        public Builder setMonthsToExhaustInspection(Property<Integer> monthsToExhaustInspection) {
            this.monthsToExhaustInspection =
                    new PropertyInteger(IDENTIFIER_MONTHS_TO_EXHAUST_INSPECTION, false, 1,
                            monthsToExhaustInspection);
            addProperty(this.monthsToExhaustInspection);
            return this;
        }

        /**
         * @param teleserviceAvailability The teleservice availability.
         * @return The builder.
         */
        public Builder setTeleserviceAvailability(Property<TeleserviceAvailability> teleserviceAvailability) {
            this.teleserviceAvailability = teleserviceAvailability;
            addProperty(teleserviceAvailability.setIdentifier(IDENTIFIER_TELESERVICE_AVAILABILITY));
            return this;
        }

        /**
         * @param serviceDistanceThreshold The service distance threshold.
         * @return The builder.
         */
        public Builder setServiceDistanceThreshold(Property<Integer> serviceDistanceThreshold) {
            this.serviceDistanceThreshold =
                    new PropertyInteger(IDENTIFIER_SERVICE_DISTANCE_THRESHOLD, false, 2,
                            serviceDistanceThreshold);
            addProperty(this.serviceDistanceThreshold);
            return this;
        }

        /**
         * @param serviceTimeThreshold The service time threshold.
         * @return The builder.
         */
        public Builder setServiceTimeThreshold(Property<Integer> serviceTimeThreshold) {
            this.serviceTimeThreshold = new PropertyInteger(IDENTIFIER_SERVICE_TIME_THRESHOLD,
                    false, 1, serviceTimeThreshold);
            addProperty(this.serviceTimeThreshold);
            return this;
        }

        /**
         * @param automaticTeleserviceCallDate The automatic teleservice call date.
         * @return The builder.
         */
        public Builder setAutomaticTeleserviceCallDate(Property<Calendar> automaticTeleserviceCallDate) {
            this.automaticTeleserviceCallDate = automaticTeleserviceCallDate;
            addProperty(automaticTeleserviceCallDate.setIdentifier(IDENTIFIER_AUTOMATIC_TELESERVICE_CALL_DATE));
            return this;
        }

        /**
         * @param teleserviceBatteryCallDate The teleservice battery call date.
         * @return The builder.
         */
        public Builder setTeleserviceBatteryCallDate(Property<Calendar> teleserviceBatteryCallDate) {
            this.teleserviceBatteryCallDate = teleserviceBatteryCallDate;
            addProperty(teleserviceBatteryCallDate.setIdentifier(IDENTIFIER_TELESERVICE_BATTERY_CALL_DATE));
            return this;
        }

        /**
         * @param nextInspectionDate The next inspection date.
         * @return The builder.
         */
        public Builder setNextInspectionDate(Property<Calendar> nextInspectionDate) {
            this.nextInspectionDate = nextInspectionDate;
            addProperty(nextInspectionDate.setIdentifier(IDENTIFIER_NEXT_INSPECTION_DATE));
            return this;
        }

        /**
         * @param conditionBasedServices The condition based services.
         * @return The builder.
         */
        public Builder setConditionBasedServices(Property<ConditionBasedService>[] conditionBasedServices) {
            this.conditionBasedServices.clear();
            for (Property<ConditionBasedService> conditionBasedService : conditionBasedServices) {
                addConditionBasedService(conditionBasedService);
            }

            return this;
        }

        /**
         * @param conditionBasedService The condition based service.
         * @return The builder.
         */
        public Builder addConditionBasedService(Property<ConditionBasedService> conditionBasedService) {
            addProperty(conditionBasedService);
            conditionBasedServices.add(conditionBasedService.setIdentifier(IDENTIFIER_CONDITION_BASED_SERVICES));
            return this;
        }

        /**
         * @param brakeFluidChangeDate The brake fluid change date.
         * @return The builder.
         */
        public Builder setBrakeFluidChangeDate(Property<Calendar> brakeFluidChangeDate) {
            this.brakeFluidChangeDate = brakeFluidChangeDate;
            addProperty(brakeFluidChangeDate.setIdentifier(IDENTIFIER_BRAKE_FLUID_CHANGE_DATE));
            return this;
        }

        public MaintenanceState build() {
            return new MaintenanceState(this);
        }
    }
}