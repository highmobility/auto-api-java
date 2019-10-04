/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.PropertyInteger;
import java.util.Calendar;
import com.highmobility.autoapi.value.ConditionBasedService;
import java.util.ArrayList;
import java.util.List;

/**
 * The maintenance state
 */
public class MaintenanceState extends SetCommand {
    public static final Identifier identifier = Identifier.MAINTENANCE;

    PropertyInteger daysToNextService = new PropertyInteger(0x01, true);
    PropertyInteger kilometersToNextService = new PropertyInteger(0x02, false);
    PropertyInteger cbsReportsCount = new PropertyInteger(0x03, false);
    PropertyInteger monthsToExhaustInspection = new PropertyInteger(0x04, false);
    Property<TeleserviceAvailability> teleserviceAvailability = new Property(TeleserviceAvailability.class, 0x05);
    PropertyInteger serviceDistanceThreshold = new PropertyInteger(0x06, false);
    PropertyInteger serviceTimeThreshold = new PropertyInteger(0x07, false);
    Property<Calendar> automaticTeleserviceCallDate = new Property(Calendar.class, 0x08);
    Property<Calendar> teleserviceBatteryCallDate = new Property(Calendar.class, 0x09);
    Property<Calendar> nextInspectionDate = new Property(Calendar.class, 0x0A);
    Property<ConditionBasedService>[] conditionBasedServices;
    Property<Calendar> brakeFluidChangeDate = new Property(Calendar.class, 0x0C);

    /**
     * @return Number of days until next servicing of the car, whereas negative is overdue
     */
    public PropertyInteger getDaysToNextService() {
        return daysToNextService;
    }

    /**
     * @return The amount of kilometers until next servicing of the car
     */
    public PropertyInteger getKilometersToNextService() {
        return kilometersToNextService;
    }

    /**
     * @return The number of CBS reports
     */
    public PropertyInteger getCbsReportsCount() {
        return cbsReportsCount;
    }

    /**
     * @return Number of months until exhaust inspection
     */
    public PropertyInteger getMonthsToExhaustInspection() {
        return monthsToExhaustInspection;
    }

    /**
     * @return The teleservice availability
     */
    public Property<TeleserviceAvailability> getTeleserviceAvailability() {
        return teleserviceAvailability;
    }

    /**
     * @return Distance threshold for Service
     */
    public PropertyInteger getServiceDistanceThreshold() {
        return serviceDistanceThreshold;
    }

    /**
     * @return Time threshold, in weeks, for Service
     */
    public PropertyInteger getServiceTimeThreshold() {
        return serviceTimeThreshold;
    }

    /**
     * @return Milliseconds since UNIX Epoch time
     */
    public Property<Calendar> getAutomaticTeleserviceCallDate() {
        return automaticTeleserviceCallDate;
    }

    /**
     * @return Milliseconds since UNIX Epoch time
     */
    public Property<Calendar> getTeleserviceBatteryCallDate() {
        return teleserviceBatteryCallDate;
    }

    /**
     * @return Milliseconds since UNIX Epoch time
     */
    public Property<Calendar> getNextInspectionDate() {
        return nextInspectionDate;
    }

    /**
     * @return The condition based services
     */
    public Property<ConditionBasedService>[] getConditionBasedServices() {
        return conditionBasedServices;
    }

    /**
     * @return Milliseconds since UNIX Epoch time
     */
    public Property<Calendar> getBrakeFluidChangeDate() {
        return brakeFluidChangeDate;
    }

    MaintenanceState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> conditionBasedServicesBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return daysToNextService.update(p);
                    case 0x02: return kilometersToNextService.update(p);
                    case 0x03: return cbsReportsCount.update(p);
                    case 0x04: return monthsToExhaustInspection.update(p);
                    case 0x05: return teleserviceAvailability.update(p);
                    case 0x06: return serviceDistanceThreshold.update(p);
                    case 0x07: return serviceTimeThreshold.update(p);
                    case 0x08: return automaticTeleserviceCallDate.update(p);
                    case 0x09: return teleserviceBatteryCallDate.update(p);
                    case 0x0A: return nextInspectionDate.update(p);
                    case 0x0B:
                        Property<ConditionBasedService> conditionBasedService = new Property(ConditionBasedService.class, p);
                        conditionBasedServicesBuilder.add(conditionBasedService);
                        return conditionBasedService;
                    case 0x0C: return brakeFluidChangeDate.update(p);
                }

                return null;
            });
        }

        conditionBasedServices = conditionBasedServicesBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private MaintenanceState(Builder builder) {
        super(builder);

        daysToNextService = builder.daysToNextService;
        kilometersToNextService = builder.kilometersToNextService;
        cbsReportsCount = builder.cbsReportsCount;
        monthsToExhaustInspection = builder.monthsToExhaustInspection;
        teleserviceAvailability = builder.teleserviceAvailability;
        serviceDistanceThreshold = builder.serviceDistanceThreshold;
        serviceTimeThreshold = builder.serviceTimeThreshold;
        automaticTeleserviceCallDate = builder.automaticTeleserviceCallDate;
        teleserviceBatteryCallDate = builder.teleserviceBatteryCallDate;
        nextInspectionDate = builder.nextInspectionDate;
        conditionBasedServices = builder.conditionBasedServices.toArray(new Property[0]);
        brakeFluidChangeDate = builder.brakeFluidChangeDate;
    }

    public static final class Builder extends SetCommand.Builder {
        private PropertyInteger daysToNextService;
        private PropertyInteger kilometersToNextService;
        private PropertyInteger cbsReportsCount;
        private PropertyInteger monthsToExhaustInspection;
        private Property<TeleserviceAvailability> teleserviceAvailability;
        private PropertyInteger serviceDistanceThreshold;
        private PropertyInteger serviceTimeThreshold;
        private Property<Calendar> automaticTeleserviceCallDate;
        private Property<Calendar> teleserviceBatteryCallDate;
        private Property<Calendar> nextInspectionDate;
        private List<Property> conditionBasedServices = new ArrayList<>();
        private Property<Calendar> brakeFluidChangeDate;

        public Builder() {
            super(identifier);
        }

        public MaintenanceState build() {
            return new MaintenanceState(this);
        }

        /**
         * @param daysToNextService Number of days until next servicing of the car, whereas negative is overdue
         * @return The builder
         */
        public Builder setDaysToNextService(Property<Integer> daysToNextService) {
            this.daysToNextService = new PropertyInteger(0x01, true, 2, daysToNextService);
            addProperty(this.daysToNextService);
            return this;
        }
        
        /**
         * @param kilometersToNextService The amount of kilometers until next servicing of the car
         * @return The builder
         */
        public Builder setKilometersToNextService(Property<Integer> kilometersToNextService) {
            this.kilometersToNextService = new PropertyInteger(0x02, false, 4, kilometersToNextService);
            addProperty(this.kilometersToNextService);
            return this;
        }
        
        /**
         * @param cbsReportsCount The number of CBS reports
         * @return The builder
         */
        public Builder setCbsReportsCount(Property<Integer> cbsReportsCount) {
            this.cbsReportsCount = new PropertyInteger(0x03, false, 1, cbsReportsCount);
            addProperty(this.cbsReportsCount);
            return this;
        }
        
        /**
         * @param monthsToExhaustInspection Number of months until exhaust inspection
         * @return The builder
         */
        public Builder setMonthsToExhaustInspection(Property<Integer> monthsToExhaustInspection) {
            this.monthsToExhaustInspection = new PropertyInteger(0x04, false, 1, monthsToExhaustInspection);
            addProperty(this.monthsToExhaustInspection);
            return this;
        }
        
        /**
         * @param teleserviceAvailability The teleservice availability
         * @return The builder
         */
        public Builder setTeleserviceAvailability(Property<TeleserviceAvailability> teleserviceAvailability) {
            this.teleserviceAvailability = teleserviceAvailability.setIdentifier(0x05);
            addProperty(this.teleserviceAvailability);
            return this;
        }
        
        /**
         * @param serviceDistanceThreshold Distance threshold for Service
         * @return The builder
         */
        public Builder setServiceDistanceThreshold(Property<Integer> serviceDistanceThreshold) {
            this.serviceDistanceThreshold = new PropertyInteger(0x06, false, 2, serviceDistanceThreshold);
            addProperty(this.serviceDistanceThreshold);
            return this;
        }
        
        /**
         * @param serviceTimeThreshold Time threshold, in weeks, for Service
         * @return The builder
         */
        public Builder setServiceTimeThreshold(Property<Integer> serviceTimeThreshold) {
            this.serviceTimeThreshold = new PropertyInteger(0x07, false, 1, serviceTimeThreshold);
            addProperty(this.serviceTimeThreshold);
            return this;
        }
        
        /**
         * @param automaticTeleserviceCallDate Milliseconds since UNIX Epoch time
         * @return The builder
         */
        public Builder setAutomaticTeleserviceCallDate(Property<Calendar> automaticTeleserviceCallDate) {
            this.automaticTeleserviceCallDate = automaticTeleserviceCallDate.setIdentifier(0x08);
            addProperty(this.automaticTeleserviceCallDate);
            return this;
        }
        
        /**
         * @param teleserviceBatteryCallDate Milliseconds since UNIX Epoch time
         * @return The builder
         */
        public Builder setTeleserviceBatteryCallDate(Property<Calendar> teleserviceBatteryCallDate) {
            this.teleserviceBatteryCallDate = teleserviceBatteryCallDate.setIdentifier(0x09);
            addProperty(this.teleserviceBatteryCallDate);
            return this;
        }
        
        /**
         * @param nextInspectionDate Milliseconds since UNIX Epoch time
         * @return The builder
         */
        public Builder setNextInspectionDate(Property<Calendar> nextInspectionDate) {
            this.nextInspectionDate = nextInspectionDate.setIdentifier(0x0A);
            addProperty(this.nextInspectionDate);
            return this;
        }
        
        /**
         * Add an array of condition based services.
         * 
         * @param conditionBasedServices The condition based services
         * @return The builder
         */
        public Builder setConditionBasedServices(Property<ConditionBasedService>[] conditionBasedServices) {
            this.conditionBasedServices.clear();
            for (int i = 0; i < conditionBasedServices.length; i++) {
                addConditionBasedService(conditionBasedServices[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single condition based service.
         * 
         * @param conditionBasedService The condition based service
         * @return The builder
         */
        public Builder addConditionBasedService(Property<ConditionBasedService> conditionBasedService) {
            conditionBasedService.setIdentifier(0x0B);
            addProperty(conditionBasedService);
            conditionBasedServices.add(conditionBasedService);
            return this;
        }
        
        /**
         * @param brakeFluidChangeDate Milliseconds since UNIX Epoch time
         * @return The builder
         */
        public Builder setBrakeFluidChangeDate(Property<Calendar> brakeFluidChangeDate) {
            this.brakeFluidChangeDate = brakeFluidChangeDate.setIdentifier(0x0C);
            addProperty(this.brakeFluidChangeDate);
            return this;
        }
    }

    public enum TeleserviceAvailability implements ByteEnum {
        PENDING((byte) 0x00),
        IDLE((byte) 0x01),
        SUCCESSFUL((byte) 0x02),
        ERROR((byte) 0x03);
    
        public static TeleserviceAvailability fromByte(byte byteValue) throws CommandParseException {
            TeleserviceAvailability[] values = TeleserviceAvailability.values();
    
            for (int i = 0; i < values.length; i++) {
                TeleserviceAvailability state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        TeleserviceAvailability(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}