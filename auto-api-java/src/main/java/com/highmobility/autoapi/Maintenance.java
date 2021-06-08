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
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

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
    public static final byte PROPERTY_NEXT_INSPECTION_DATE = 0x0a;
    public static final byte PROPERTY_CONDITION_BASED_SERVICES = 0x0b;
    public static final byte PROPERTY_BRAKE_FLUID_CHANGE_DATE = 0x0c;
    public static final byte PROPERTY_TIME_TO_NEXT_SERVICE = 0x0d;
    public static final byte PROPERTY_DISTANCE_TO_NEXT_SERVICE = 0x0e;
    public static final byte PROPERTY_TIME_TO_EXHAUST_INSPECTION = 0x0f;
    public static final byte PROPERTY_LAST_ECALL = 0x10;
    public static final byte PROPERTY_DISTANCE_TO_NEXT_OIL_SERVICE = 0x11;
    public static final byte PROPERTY_TIME_TO_NEXT_OIL_SERVICE = 0x12;

    /**
     * Get Maintenance property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Maintenance property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Maintenance property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Maintenance property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get Maintenance properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Maintenance properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Maintenance properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Maintenance properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Maintenance properties
     * 
     * @deprecated use {@link GetState#GetState(byte...)} instead
     */
    @Deprecated
    public static class GetProperties extends GetCommand<State> {
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
        Property<Duration> daysToNextService = new Property<>(Duration.class, PROPERTY_DAYS_TO_NEXT_SERVICE);
        Property<Length> kilometersToNextService = new Property<>(Length.class, PROPERTY_KILOMETERS_TO_NEXT_SERVICE);
        PropertyInteger cbsReportsCount = new PropertyInteger(PROPERTY_CBS_REPORTS_COUNT, false);
        Property<Duration> monthsToExhaustInspection = new Property<>(Duration.class, PROPERTY_MONTHS_TO_EXHAUST_INSPECTION);
        Property<TeleserviceAvailability> teleserviceAvailability = new Property<>(TeleserviceAvailability.class, PROPERTY_TELESERVICE_AVAILABILITY);
        Property<Length> serviceDistanceThreshold = new Property<>(Length.class, PROPERTY_SERVICE_DISTANCE_THRESHOLD);
        Property<Duration> serviceTimeThreshold = new Property<>(Duration.class, PROPERTY_SERVICE_TIME_THRESHOLD);
        Property<Calendar> automaticTeleserviceCallDate = new Property<>(Calendar.class, PROPERTY_AUTOMATIC_TELESERVICE_CALL_DATE);
        Property<Calendar> teleserviceBatteryCallDate = new Property<>(Calendar.class, PROPERTY_TELESERVICE_BATTERY_CALL_DATE);
        Property<Calendar> nextInspectionDate = new Property<>(Calendar.class, PROPERTY_NEXT_INSPECTION_DATE);
        List<Property<ConditionBasedService>> conditionBasedServices;
        Property<Calendar> brakeFluidChangeDate = new Property<>(Calendar.class, PROPERTY_BRAKE_FLUID_CHANGE_DATE);
        Property<Duration> timeToNextService = new Property<>(Duration.class, PROPERTY_TIME_TO_NEXT_SERVICE);
        Property<Length> distanceToNextService = new Property<>(Length.class, PROPERTY_DISTANCE_TO_NEXT_SERVICE);
        Property<Duration> timeToExhaustInspection = new Property<>(Duration.class, PROPERTY_TIME_TO_EXHAUST_INSPECTION);
        Property<Calendar> lastECall = new Property<>(Calendar.class, PROPERTY_LAST_ECALL);
        Property<Length> distanceToNextOilService = new Property<>(Length.class, PROPERTY_DISTANCE_TO_NEXT_OIL_SERVICE);
        Property<Duration> timeToNextOilService = new Property<>(Duration.class, PROPERTY_TIME_TO_NEXT_OIL_SERVICE);
    
        /**
         * @return Time until next servicing of the car
         * @deprecated removed the unit from the name. Replaced by {@link #getTimeToNextService()}
         */
        @Deprecated
        public Property<Duration> getDaysToNextService() {
            return daysToNextService;
        }
    
        /**
         * @return The distance until next servicing of the vehicle
         * @deprecated removed the unit from the name. Replaced by {@link #getDistanceToNextService()}
         */
        @Deprecated
        public Property<Length> getKilometersToNextService() {
            return kilometersToNextService;
        }
    
        /**
         * @return The number of CBS reports
         */
        public PropertyInteger getCbsReportsCount() {
            return cbsReportsCount;
        }
    
        /**
         * @return Time until exhaust inspection
         * @deprecated removed the unit from the name. Replaced by {@link #getTimeToExhaustInspection()}
         */
        @Deprecated
        public Property<Duration> getMonthsToExhaustInspection() {
            return monthsToExhaustInspection;
        }
    
        /**
         * @return The teleservice availability
         */
        public Property<TeleserviceAvailability> getTeleserviceAvailability() {
            return teleserviceAvailability;
        }
    
        /**
         * @return Distance threshold for service
         */
        public Property<Length> getServiceDistanceThreshold() {
            return serviceDistanceThreshold;
        }
    
        /**
         * @return Time threshold for service
         */
        public Property<Duration> getServiceTimeThreshold() {
            return serviceTimeThreshold;
        }
    
        /**
         * @return Automatic teleservice call date
         */
        public Property<Calendar> getAutomaticTeleserviceCallDate() {
            return automaticTeleserviceCallDate;
        }
    
        /**
         * @return Teleservice batter call date
         */
        public Property<Calendar> getTeleserviceBatteryCallDate() {
            return teleserviceBatteryCallDate;
        }
    
        /**
         * @return Next inspection date
         */
        public Property<Calendar> getNextInspectionDate() {
            return nextInspectionDate;
        }
    
        /**
         * @return The condition based services
         */
        public List<Property<ConditionBasedService>> getConditionBasedServices() {
            return conditionBasedServices;
        }
    
        /**
         * @return Brake fluid change date
         */
        public Property<Calendar> getBrakeFluidChangeDate() {
            return brakeFluidChangeDate;
        }
    
        /**
         * @return Time until next servicing of the vehicle
         */
        public Property<Duration> getTimeToNextService() {
            return timeToNextService;
        }
    
        /**
         * @return The distance until next servicing of the vehicle
         */
        public Property<Length> getDistanceToNextService() {
            return distanceToNextService;
        }
    
        /**
         * @return Time until exhaust inspection
         */
        public Property<Duration> getTimeToExhaustInspection() {
            return timeToExhaustInspection;
        }
    
        /**
         * @return Date-time of the last eCall
         */
        public Property<Calendar> getLastECall() {
            return lastECall;
        }
    
        /**
         * @return Indicates the remaining distance until the next oil service; if this limit was exceeded, this value indicates the distance that has been driven since then.
         */
        public Property<Length> getDistanceToNextOilService() {
            return distanceToNextOilService;
        }
    
        /**
         * @return Indicates the time remaining until the next oil service; if this limit was exceeded, this value indicates the time that has passed since then.
         */
        public Property<Duration> getTimeToNextOilService() {
            return timeToNextOilService;
        }
    
        State(byte[] bytes) {
            super(bytes);
    
            final ArrayList<Property<ConditionBasedService>> conditionBasedServicesBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
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
                            Property<ConditionBasedService> conditionBasedService = new Property<>(ConditionBasedService.class, p);
                            conditionBasedServicesBuilder.add(conditionBasedService);
                            return conditionBasedService;
                        case PROPERTY_BRAKE_FLUID_CHANGE_DATE: return brakeFluidChangeDate.update(p);
                        case PROPERTY_TIME_TO_NEXT_SERVICE: return timeToNextService.update(p);
                        case PROPERTY_DISTANCE_TO_NEXT_SERVICE: return distanceToNextService.update(p);
                        case PROPERTY_TIME_TO_EXHAUST_INSPECTION: return timeToExhaustInspection.update(p);
                        case PROPERTY_LAST_ECALL: return lastECall.update(p);
                        case PROPERTY_DISTANCE_TO_NEXT_OIL_SERVICE: return distanceToNextOilService.update(p);
                        case PROPERTY_TIME_TO_NEXT_OIL_SERVICE: return timeToNextOilService.update(p);
                    }
    
                    return null;
                });
            }
    
            conditionBasedServices = conditionBasedServicesBuilder;
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
             * @param daysToNextService Time until next servicing of the car
             * @return The builder
             * @deprecated removed the unit from the name. Replaced by {@link #getTimeToNextService()}
             */
            @Deprecated
            public Builder setDaysToNextService(Property<Duration> daysToNextService) {
                Property property = daysToNextService.setIdentifier(PROPERTY_DAYS_TO_NEXT_SERVICE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param kilometersToNextService The distance until next servicing of the vehicle
             * @return The builder
             * @deprecated removed the unit from the name. Replaced by {@link #getDistanceToNextService()}
             */
            @Deprecated
            public Builder setKilometersToNextService(Property<Length> kilometersToNextService) {
                Property property = kilometersToNextService.setIdentifier(PROPERTY_KILOMETERS_TO_NEXT_SERVICE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param cbsReportsCount The number of CBS reports
             * @return The builder
             */
            public Builder setCbsReportsCount(Property<Integer> cbsReportsCount) {
                Property property = new PropertyInteger(PROPERTY_CBS_REPORTS_COUNT, false, 1, cbsReportsCount);
                addProperty(property);
                return this;
            }
            
            /**
             * @param monthsToExhaustInspection Time until exhaust inspection
             * @return The builder
             * @deprecated removed the unit from the name. Replaced by {@link #getTimeToExhaustInspection()}
             */
            @Deprecated
            public Builder setMonthsToExhaustInspection(Property<Duration> monthsToExhaustInspection) {
                Property property = monthsToExhaustInspection.setIdentifier(PROPERTY_MONTHS_TO_EXHAUST_INSPECTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param teleserviceAvailability The teleservice availability
             * @return The builder
             */
            public Builder setTeleserviceAvailability(Property<TeleserviceAvailability> teleserviceAvailability) {
                Property property = teleserviceAvailability.setIdentifier(PROPERTY_TELESERVICE_AVAILABILITY);
                addProperty(property);
                return this;
            }
            
            /**
             * @param serviceDistanceThreshold Distance threshold for service
             * @return The builder
             */
            public Builder setServiceDistanceThreshold(Property<Length> serviceDistanceThreshold) {
                Property property = serviceDistanceThreshold.setIdentifier(PROPERTY_SERVICE_DISTANCE_THRESHOLD);
                addProperty(property);
                return this;
            }
            
            /**
             * @param serviceTimeThreshold Time threshold for service
             * @return The builder
             */
            public Builder setServiceTimeThreshold(Property<Duration> serviceTimeThreshold) {
                Property property = serviceTimeThreshold.setIdentifier(PROPERTY_SERVICE_TIME_THRESHOLD);
                addProperty(property);
                return this;
            }
            
            /**
             * @param automaticTeleserviceCallDate Automatic teleservice call date
             * @return The builder
             */
            public Builder setAutomaticTeleserviceCallDate(Property<Calendar> automaticTeleserviceCallDate) {
                Property property = automaticTeleserviceCallDate.setIdentifier(PROPERTY_AUTOMATIC_TELESERVICE_CALL_DATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param teleserviceBatteryCallDate Teleservice batter call date
             * @return The builder
             */
            public Builder setTeleserviceBatteryCallDate(Property<Calendar> teleserviceBatteryCallDate) {
                Property property = teleserviceBatteryCallDate.setIdentifier(PROPERTY_TELESERVICE_BATTERY_CALL_DATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param nextInspectionDate Next inspection date
             * @return The builder
             */
            public Builder setNextInspectionDate(Property<Calendar> nextInspectionDate) {
                Property property = nextInspectionDate.setIdentifier(PROPERTY_NEXT_INSPECTION_DATE);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of condition based services
             * 
             * @param conditionBasedServices The condition based services
             * @return The builder
             */
            public Builder setConditionBasedServices(Property<ConditionBasedService>[] conditionBasedServices) {
                for (int i = 0; i < conditionBasedServices.length; i++) {
                    addConditionBasedService(conditionBasedServices[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single condition based service
             * 
             * @param conditionBasedService The condition based service
             * @return The builder
             */
            public Builder addConditionBasedService(Property<ConditionBasedService> conditionBasedService) {
                conditionBasedService.setIdentifier(PROPERTY_CONDITION_BASED_SERVICES);
                addProperty(conditionBasedService);
                return this;
            }
            
            /**
             * @param brakeFluidChangeDate Brake fluid change date
             * @return The builder
             */
            public Builder setBrakeFluidChangeDate(Property<Calendar> brakeFluidChangeDate) {
                Property property = brakeFluidChangeDate.setIdentifier(PROPERTY_BRAKE_FLUID_CHANGE_DATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param timeToNextService Time until next servicing of the vehicle
             * @return The builder
             */
            public Builder setTimeToNextService(Property<Duration> timeToNextService) {
                Property property = timeToNextService.setIdentifier(PROPERTY_TIME_TO_NEXT_SERVICE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param distanceToNextService The distance until next servicing of the vehicle
             * @return The builder
             */
            public Builder setDistanceToNextService(Property<Length> distanceToNextService) {
                Property property = distanceToNextService.setIdentifier(PROPERTY_DISTANCE_TO_NEXT_SERVICE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param timeToExhaustInspection Time until exhaust inspection
             * @return The builder
             */
            public Builder setTimeToExhaustInspection(Property<Duration> timeToExhaustInspection) {
                Property property = timeToExhaustInspection.setIdentifier(PROPERTY_TIME_TO_EXHAUST_INSPECTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param lastECall Date-time of the last eCall
             * @return The builder
             */
            public Builder setLastECall(Property<Calendar> lastECall) {
                Property property = lastECall.setIdentifier(PROPERTY_LAST_ECALL);
                addProperty(property);
                return this;
            }
            
            /**
             * @param distanceToNextOilService Indicates the remaining distance until the next oil service; if this limit was exceeded, this value indicates the distance that has been driven since then.
             * @return The builder
             */
            public Builder setDistanceToNextOilService(Property<Length> distanceToNextOilService) {
                Property property = distanceToNextOilService.setIdentifier(PROPERTY_DISTANCE_TO_NEXT_OIL_SERVICE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param timeToNextOilService Indicates the time remaining until the next oil service; if this limit was exceeded, this value indicates the time that has passed since then.
             * @return The builder
             */
            public Builder setTimeToNextOilService(Property<Duration> timeToNextOilService) {
                Property property = timeToNextOilService.setIdentifier(PROPERTY_TIME_TO_NEXT_OIL_SERVICE);
                addProperty(property);
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
    
            throw new CommandParseException(
                enumValueDoesNotExist(TeleserviceAvailability.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        TeleserviceAvailability(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}