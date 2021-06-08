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
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.ChargingCost;
import com.highmobility.autoapi.value.ChargingLocation;
import com.highmobility.autoapi.value.ChargingPoint;
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.autoapi.value.measurement.Energy;
import com.highmobility.autoapi.value.measurement.Length;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The Charging session capability
 */
public class ChargingSession {
    public static final int IDENTIFIER = Identifier.CHARGING_SESSION;

    public static final byte PROPERTY_PUBLIC_CHARGING_POINTS = 0x01;
    public static final byte PROPERTY_DISPLAYED_STATE_OF_CHARGE = 0x02;
    public static final byte PROPERTY_DISPLAYED_START_STATE_OF_CHARGE = 0x03;
    public static final byte PROPERTY_BUSINESS_ERRORS = 0x04;
    public static final byte PROPERTY_TIME_ZONE = 0x05;
    public static final byte PROPERTY_START_TIME = 0x06;
    public static final byte PROPERTY_END_TIME = 0x07;
    public static final byte PROPERTY_TOTAL_CHARGING_DURATION = 0x08;
    public static final byte PROPERTY_CALCULATED_ENERGY_CHARGED = 0x09;
    public static final byte PROPERTY_ENERGY_CHARGED = 0x0a;
    public static final byte PROPERTY_PRECONDITIONING_STATE = 0x0b;
    public static final byte PROPERTY_ODOMETER = 0x0c;
    public static final byte PROPERTY_CHARGING_COST = 0x0d;
    public static final byte PROPERTY_LOCATION = 0x0e;

    /**
     * The charging session state
     */
    public static class State extends SetCommand {
        List<Property<ChargingPoint>> publicChargingPoints;
        Property<Double> displayedStateOfCharge = new Property<>(Double.class, PROPERTY_DISPLAYED_STATE_OF_CHARGE);
        Property<Double> displayedStartStateOfCharge = new Property<>(Double.class, PROPERTY_DISPLAYED_START_STATE_OF_CHARGE);
        List<Property<String>> businessErrors;
        Property<String> timeZone = new Property<>(String.class, PROPERTY_TIME_ZONE);
        Property<Calendar> startTime = new Property<>(Calendar.class, PROPERTY_START_TIME);
        Property<Calendar> endTime = new Property<>(Calendar.class, PROPERTY_END_TIME);
        Property<Duration> totalChargingDuration = new Property<>(Duration.class, PROPERTY_TOTAL_CHARGING_DURATION);
        Property<Energy> calculatedEnergyCharged = new Property<>(Energy.class, PROPERTY_CALCULATED_ENERGY_CHARGED);
        Property<Energy> energyCharged = new Property<>(Energy.class, PROPERTY_ENERGY_CHARGED);
        Property<ActiveState> preconditioningState = new Property<>(ActiveState.class, PROPERTY_PRECONDITIONING_STATE);
        Property<Length> odometer = new Property<>(Length.class, PROPERTY_ODOMETER);
        Property<ChargingCost> chargingCost = new Property<>(ChargingCost.class, PROPERTY_CHARGING_COST);
        Property<ChargingLocation> location = new Property<>(ChargingLocation.class, PROPERTY_LOCATION);
    
        /**
         * @return Matching public charging points.
         */
        public List<Property<ChargingPoint>> getPublicChargingPoints() {
            return publicChargingPoints;
        }
    
        /**
         * @return Displayed state of charge to the driver
         */
        public Property<Double> getDisplayedStateOfCharge() {
            return displayedStateOfCharge;
        }
    
        /**
         * @return Displayed state of charge at start to the driver
         */
        public Property<Double> getDisplayedStartStateOfCharge() {
            return displayedStartStateOfCharge;
        }
    
        /**
         * @return The business errors
         */
        public List<Property<String>> getBusinessErrors() {
            return businessErrors;
        }
    
        /**
         * @return Time zone of the charging session
         */
        public Property<String> getTimeZone() {
            return timeZone;
        }
    
        /**
         * @return Start time of the charging session
         */
        public Property<Calendar> getStartTime() {
            return startTime;
        }
    
        /**
         * @return End time of the charging session
         */
        public Property<Calendar> getEndTime() {
            return endTime;
        }
    
        /**
         * @return Total time charging was active during the session
         */
        public Property<Duration> getTotalChargingDuration() {
            return totalChargingDuration;
        }
    
        /**
         * @return Calculated amount of energy charged during the session
         */
        public Property<Energy> getCalculatedEnergyCharged() {
            return calculatedEnergyCharged;
        }
    
        /**
         * @return Energy charged during the session
         */
        public Property<Energy> getEnergyCharged() {
            return energyCharged;
        }
    
        /**
         * @return Preconditioning is active or not
         */
        public Property<ActiveState> getPreconditioningState() {
            return preconditioningState;
        }
    
        /**
         * @return The vehicle odometer value in a given units
         */
        public Property<Length> getOdometer() {
            return odometer;
        }
    
        /**
         * @return Charging cost information
         */
        public Property<ChargingCost> getChargingCost() {
            return chargingCost;
        }
    
        /**
         * @return Charging location address
         */
        public Property<ChargingLocation> getLocation() {
            return location;
        }
    
        State(byte[] bytes) {
            super(bytes);
    
            final ArrayList<Property<ChargingPoint>> publicChargingPointsBuilder = new ArrayList<>();
            final ArrayList<Property<String>> businessErrorsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_PUBLIC_CHARGING_POINTS:
                            Property<ChargingPoint> publicChargingPoint = new Property<>(ChargingPoint.class, p);
                            publicChargingPointsBuilder.add(publicChargingPoint);
                            return publicChargingPoint;
                        case PROPERTY_DISPLAYED_STATE_OF_CHARGE: return displayedStateOfCharge.update(p);
                        case PROPERTY_DISPLAYED_START_STATE_OF_CHARGE: return displayedStartStateOfCharge.update(p);
                        case PROPERTY_BUSINESS_ERRORS:
                            Property<String> businessError = new Property<>(String.class, p);
                            businessErrorsBuilder.add(businessError);
                            return businessError;
                        case PROPERTY_TIME_ZONE: return timeZone.update(p);
                        case PROPERTY_START_TIME: return startTime.update(p);
                        case PROPERTY_END_TIME: return endTime.update(p);
                        case PROPERTY_TOTAL_CHARGING_DURATION: return totalChargingDuration.update(p);
                        case PROPERTY_CALCULATED_ENERGY_CHARGED: return calculatedEnergyCharged.update(p);
                        case PROPERTY_ENERGY_CHARGED: return energyCharged.update(p);
                        case PROPERTY_PRECONDITIONING_STATE: return preconditioningState.update(p);
                        case PROPERTY_ODOMETER: return odometer.update(p);
                        case PROPERTY_CHARGING_COST: return chargingCost.update(p);
                        case PROPERTY_LOCATION: return location.update(p);
                    }
    
                    return null;
                });
            }
    
            publicChargingPoints = publicChargingPointsBuilder;
            businessErrors = businessErrorsBuilder;
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
             * Add an array of public charging points
             * 
             * @param publicChargingPoints The public charging points. Matching public charging points.
             * @return The builder
             */
            public Builder setPublicChargingPoints(Property<ChargingPoint>[] publicChargingPoints) {
                for (int i = 0; i < publicChargingPoints.length; i++) {
                    addPublicChargingPoint(publicChargingPoints[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single public charging point
             * 
             * @param publicChargingPoint The public charging point. Matching public charging points.
             * @return The builder
             */
            public Builder addPublicChargingPoint(Property<ChargingPoint> publicChargingPoint) {
                publicChargingPoint.setIdentifier(PROPERTY_PUBLIC_CHARGING_POINTS);
                addProperty(publicChargingPoint);
                return this;
            }
            
            /**
             * @param displayedStateOfCharge Displayed state of charge to the driver
             * @return The builder
             */
            public Builder setDisplayedStateOfCharge(Property<Double> displayedStateOfCharge) {
                Property property = displayedStateOfCharge.setIdentifier(PROPERTY_DISPLAYED_STATE_OF_CHARGE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param displayedStartStateOfCharge Displayed state of charge at start to the driver
             * @return The builder
             */
            public Builder setDisplayedStartStateOfCharge(Property<Double> displayedStartStateOfCharge) {
                Property property = displayedStartStateOfCharge.setIdentifier(PROPERTY_DISPLAYED_START_STATE_OF_CHARGE);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of business errors
             * 
             * @param businessErrors The business errors
             * @return The builder
             */
            public Builder setBusinessErrors(Property<String>[] businessErrors) {
                for (int i = 0; i < businessErrors.length; i++) {
                    addBusinessError(businessErrors[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single business error
             * 
             * @param businessError The business error
             * @return The builder
             */
            public Builder addBusinessError(Property<String> businessError) {
                businessError.setIdentifier(PROPERTY_BUSINESS_ERRORS);
                addProperty(businessError);
                return this;
            }
            
            /**
             * @param timeZone Time zone of the charging session
             * @return The builder
             */
            public Builder setTimeZone(Property<String> timeZone) {
                Property property = timeZone.setIdentifier(PROPERTY_TIME_ZONE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param startTime Start time of the charging session
             * @return The builder
             */
            public Builder setStartTime(Property<Calendar> startTime) {
                Property property = startTime.setIdentifier(PROPERTY_START_TIME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param endTime End time of the charging session
             * @return The builder
             */
            public Builder setEndTime(Property<Calendar> endTime) {
                Property property = endTime.setIdentifier(PROPERTY_END_TIME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param totalChargingDuration Total time charging was active during the session
             * @return The builder
             */
            public Builder setTotalChargingDuration(Property<Duration> totalChargingDuration) {
                Property property = totalChargingDuration.setIdentifier(PROPERTY_TOTAL_CHARGING_DURATION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param calculatedEnergyCharged Calculated amount of energy charged during the session
             * @return The builder
             */
            public Builder setCalculatedEnergyCharged(Property<Energy> calculatedEnergyCharged) {
                Property property = calculatedEnergyCharged.setIdentifier(PROPERTY_CALCULATED_ENERGY_CHARGED);
                addProperty(property);
                return this;
            }
            
            /**
             * @param energyCharged Energy charged during the session
             * @return The builder
             */
            public Builder setEnergyCharged(Property<Energy> energyCharged) {
                Property property = energyCharged.setIdentifier(PROPERTY_ENERGY_CHARGED);
                addProperty(property);
                return this;
            }
            
            /**
             * @param preconditioningState Preconditioning is active or not
             * @return The builder
             */
            public Builder setPreconditioningState(Property<ActiveState> preconditioningState) {
                Property property = preconditioningState.setIdentifier(PROPERTY_PRECONDITIONING_STATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param odometer The vehicle odometer value in a given units
             * @return The builder
             */
            public Builder setOdometer(Property<Length> odometer) {
                Property property = odometer.setIdentifier(PROPERTY_ODOMETER);
                addProperty(property);
                return this;
            }
            
            /**
             * @param chargingCost Charging cost information
             * @return The builder
             */
            public Builder setChargingCost(Property<ChargingCost> chargingCost) {
                Property property = chargingCost.setIdentifier(PROPERTY_CHARGING_COST);
                addProperty(property);
                return this;
            }
            
            /**
             * @param location Charging location address
             * @return The builder
             */
            public Builder setLocation(Property<ChargingLocation> location) {
                Property property = location.setIdentifier(PROPERTY_LOCATION);
                addProperty(property);
                return this;
            }
        }
    }
}