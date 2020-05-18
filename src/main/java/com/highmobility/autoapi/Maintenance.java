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

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.ConditionBasedService;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The Maintenance capability
 */
public class Maintenance {
    public static final int IDENTIFIER = Identifier.MAINTENANCE;

    public static final byte PROPERTY_DAYS_TO_NEXT_SERVICE = 0x01;
    public static final byte PROPERTY_KILOMETERS_TO_NEXT_SERVICE = 0x02;
    public static final byte PROPERTY_CBS_REPORTS_COUNT = 0x03;
    public static final byte PROPERTY_MONTHS_TO_EXHAUST_INSPECTION = 0x04;
    public static final byte PROPERTY_TELESERVICE_AVAILABILITY = 0x05;
    public static final byte PROPERTY_SERVICE_DISTANCE_THRESHOLD = 0x06;
    public static final byte PROPERTY_SERVICE_TIME_THRESHOLD = 0x07;
    public static final byte PROPERTY_AUTOMATIC_TELESERVICE_CALL_DATE = 0x08;
    public static final byte PROPERTY_TELESERVICE_BATTERY_CALL_DATE = 0x09;
    public static final byte PROPERTY_NEXT_INSPECTION_DATE = 0x0A;
    public static final byte PROPERTY_CONDITION_BASED_SERVICES = 0x0B;
    public static final byte PROPERTY_BRAKE_FLUID_CHANGE_DATE = 0x0C;

    /**
     * Get all maintenance properties
     */
    public static class GetState extends GetCommand {
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        GetState(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific maintenance properties
     */
    public static class GetProperties extends GetCommand {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The maintenance state
     */
    public static class State extends SetCommand {
        PropertyInteger daysToNextService = new PropertyInteger(PROPERTY_DAYS_TO_NEXT_SERVICE, true);
        PropertyInteger kilometersToNextService = new PropertyInteger(PROPERTY_KILOMETERS_TO_NEXT_SERVICE, false);
        PropertyInteger cbsReportsCount = new PropertyInteger(PROPERTY_CBS_REPORTS_COUNT, false);
        PropertyInteger monthsToExhaustInspection = new PropertyInteger(PROPERTY_MONTHS_TO_EXHAUST_INSPECTION, false);
        Property<TeleserviceAvailability> teleserviceAvailability = new Property(TeleserviceAvailability.class, PROPERTY_TELESERVICE_AVAILABILITY);
        PropertyInteger serviceDistanceThreshold = new PropertyInteger(PROPERTY_SERVICE_DISTANCE_THRESHOLD, false);
        PropertyInteger serviceTimeThreshold = new PropertyInteger(PROPERTY_SERVICE_TIME_THRESHOLD, false);
        Property<Calendar> automaticTeleserviceCallDate = new Property(Calendar.class, PROPERTY_AUTOMATIC_TELESERVICE_CALL_DATE);
        Property<Calendar> teleserviceBatteryCallDate = new Property(Calendar.class, PROPERTY_TELESERVICE_BATTERY_CALL_DATE);
        Property<Calendar> nextInspectionDate = new Property(Calendar.class, PROPERTY_NEXT_INSPECTION_DATE);
        Property<ConditionBasedService>[] conditionBasedServices;
        Property<Calendar> brakeFluidChangeDate = new Property(Calendar.class, PROPERTY_BRAKE_FLUID_CHANGE_DATE);
    
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
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> conditionBasedServicesBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DAYS_TO_NEXT_SERVICE: return daysToNextService.update(p);
                        case PROPERTY_KILOMETERS_TO_NEXT_SERVICE: return kilometersToNextService.update(p);
                        case PROPERTY_CBS_REPORTS_COUNT: return cbsReportsCount.update(p);
                        case PROPERTY_MONTHS_TO_EXHAUST_INSPECTION: return monthsToExhaustInspection.update(p);
                        case PROPERTY_TELESERVICE_AVAILABILITY: return teleserviceAvailability.update(p);
                        case PROPERTY_SERVICE_DISTANCE_THRESHOLD: return serviceDistanceThreshold.update(p);
                        case PROPERTY_SERVICE_TIME_THRESHOLD: return serviceTimeThreshold.update(p);
                        case PROPERTY_AUTOMATIC_TELESERVICE_CALL_DATE: return automaticTeleserviceCallDate.update(p);
                        case PROPERTY_TELESERVICE_BATTERY_CALL_DATE: return teleserviceBatteryCallDate.update(p);
                        case PROPERTY_NEXT_INSPECTION_DATE: return nextInspectionDate.update(p);
                        case PROPERTY_CONDITION_BASED_SERVICES:
                            Property<ConditionBasedService> conditionBasedService = new Property(ConditionBasedService.class, p);
                            conditionBasedServicesBuilder.add(conditionBasedService);
                            return conditionBasedService;
                        case PROPERTY_BRAKE_FLUID_CHANGE_DATE: return brakeFluidChangeDate.update(p);
                    }
    
                    return null;
                });
            }
    
            conditionBasedServices = conditionBasedServicesBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
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
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param daysToNextService Number of days until next servicing of the car, whereas negative is overdue
             * @return The builder
             */
            public Builder setDaysToNextService(Property<Integer> daysToNextService) {
                this.daysToNextService = new PropertyInteger(PROPERTY_DAYS_TO_NEXT_SERVICE, true, 2, daysToNextService);
                addProperty(this.daysToNextService);
                return this;
            }
            
            /**
             * @param kilometersToNextService The amount of kilometers until next servicing of the car
             * @return The builder
             */
            public Builder setKilometersToNextService(Property<Integer> kilometersToNextService) {
                this.kilometersToNextService = new PropertyInteger(PROPERTY_KILOMETERS_TO_NEXT_SERVICE, false, 4, kilometersToNextService);
                addProperty(this.kilometersToNextService);
                return this;
            }
            
            /**
             * @param cbsReportsCount The number of CBS reports
             * @return The builder
             */
            public Builder setCbsReportsCount(Property<Integer> cbsReportsCount) {
                this.cbsReportsCount = new PropertyInteger(PROPERTY_CBS_REPORTS_COUNT, false, 1, cbsReportsCount);
                addProperty(this.cbsReportsCount);
                return this;
            }
            
            /**
             * @param monthsToExhaustInspection Number of months until exhaust inspection
             * @return The builder
             */
            public Builder setMonthsToExhaustInspection(Property<Integer> monthsToExhaustInspection) {
                this.monthsToExhaustInspection = new PropertyInteger(PROPERTY_MONTHS_TO_EXHAUST_INSPECTION, false, 1, monthsToExhaustInspection);
                addProperty(this.monthsToExhaustInspection);
                return this;
            }
            
            /**
             * @param teleserviceAvailability The teleservice availability
             * @return The builder
             */
            public Builder setTeleserviceAvailability(Property<TeleserviceAvailability> teleserviceAvailability) {
                this.teleserviceAvailability = teleserviceAvailability.setIdentifier(PROPERTY_TELESERVICE_AVAILABILITY);
                addProperty(this.teleserviceAvailability);
                return this;
            }
            
            /**
             * @param serviceDistanceThreshold Distance threshold for Service
             * @return The builder
             */
            public Builder setServiceDistanceThreshold(Property<Integer> serviceDistanceThreshold) {
                this.serviceDistanceThreshold = new PropertyInteger(PROPERTY_SERVICE_DISTANCE_THRESHOLD, false, 2, serviceDistanceThreshold);
                addProperty(this.serviceDistanceThreshold);
                return this;
            }
            
            /**
             * @param serviceTimeThreshold Time threshold, in weeks, for Service
             * @return The builder
             */
            public Builder setServiceTimeThreshold(Property<Integer> serviceTimeThreshold) {
                this.serviceTimeThreshold = new PropertyInteger(PROPERTY_SERVICE_TIME_THRESHOLD, false, 1, serviceTimeThreshold);
                addProperty(this.serviceTimeThreshold);
                return this;
            }
            
            /**
             * @param automaticTeleserviceCallDate Milliseconds since UNIX Epoch time
             * @return The builder
             */
            public Builder setAutomaticTeleserviceCallDate(Property<Calendar> automaticTeleserviceCallDate) {
                this.automaticTeleserviceCallDate = automaticTeleserviceCallDate.setIdentifier(PROPERTY_AUTOMATIC_TELESERVICE_CALL_DATE);
                addProperty(this.automaticTeleserviceCallDate);
                return this;
            }
            
            /**
             * @param teleserviceBatteryCallDate Milliseconds since UNIX Epoch time
             * @return The builder
             */
            public Builder setTeleserviceBatteryCallDate(Property<Calendar> teleserviceBatteryCallDate) {
                this.teleserviceBatteryCallDate = teleserviceBatteryCallDate.setIdentifier(PROPERTY_TELESERVICE_BATTERY_CALL_DATE);
                addProperty(this.teleserviceBatteryCallDate);
                return this;
            }
            
            /**
             * @param nextInspectionDate Milliseconds since UNIX Epoch time
             * @return The builder
             */
            public Builder setNextInspectionDate(Property<Calendar> nextInspectionDate) {
                this.nextInspectionDate = nextInspectionDate.setIdentifier(PROPERTY_NEXT_INSPECTION_DATE);
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
                conditionBasedService.setIdentifier(PROPERTY_CONDITION_BASED_SERVICES);
                addProperty(conditionBasedService);
                conditionBasedServices.add(conditionBasedService);
                return this;
            }
            
            /**
             * @param brakeFluidChangeDate Milliseconds since UNIX Epoch time
             * @return The builder
             */
            public Builder setBrakeFluidChangeDate(Property<Calendar> brakeFluidChangeDate) {
                this.brakeFluidChangeDate = brakeFluidChangeDate.setIdentifier(PROPERTY_BRAKE_FLUID_CHANGE_DATE);
                addProperty(this.brakeFluidChangeDate);
                return this;
            }
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