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
import com.highmobility.autoapi.value.AddressComponent;
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.autoapi.value.EcoDrivingThreshold;
import com.highmobility.autoapi.value.measurement.FuelEfficiency;
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.autoapi.value.measurement.Speed;
import com.highmobility.autoapi.value.measurement.Volume;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Trips capability
 */
public class Trips {
    public static final int IDENTIFIER = Identifier.TRIPS;

    public static final byte PROPERTY_TYPE = 0x01;
    public static final byte PROPERTY_DRIVER_NAME = 0x02;
    public static final byte PROPERTY_DESCRIPTION = 0x03;
    public static final byte PROPERTY_START_TIME = 0x04;
    public static final byte PROPERTY_END_TIME = 0x05;
    public static final byte PROPERTY_START_ADDRESS = 0x06;
    public static final byte PROPERTY_END_ADDRESS = 0x07;
    public static final byte PROPERTY_START_COORDINATES = 0x08;
    public static final byte PROPERTY_END_COORDINATES = 0x09;
    public static final byte PROPERTY_START_ODOMETER = 0x0a;
    public static final byte PROPERTY_END_ODOMETER = 0x0b;
    public static final byte PROPERTY_AVERAGE_FUEL_CONSUMPTION = 0x0c;
    public static final byte PROPERTY_DISTANCE = 0x0d;
    public static final byte PROPERTY_START_ADDRESS_COMPONENTS = 0x0e;
    public static final byte PROPERTY_END_ADDRESS_COMPONENTS = 0x0f;
    public static final byte PROPERTY_EVENT = 0x10;
    public static final byte PROPERTY_ECO_LEVEL = 0x11;
    public static final byte PROPERTY_THRESHOLDS = 0x12;
    public static final byte PROPERTY_TOTAL_FUEL_CONSUMPTION = 0x13;
    public static final byte PROPERTY_TOTAL_IDLE_FUEL_CONSUMPTION = 0x14;
    public static final byte PROPERTY_MAXIMUM_SPEED = 0x15;
    public static final byte PROPERTY_ROAD_TYPE = 0x16;
    public static final byte PROPERTY_BRAKING_COUNT = 0x17;

    /**
     * The trips state
     */
    public static class State extends SetCommand {
        Property<Type> type = new Property<>(Type.class, PROPERTY_TYPE);
        Property<String> driverName = new Property<>(String.class, PROPERTY_DRIVER_NAME);
        Property<String> description = new Property<>(String.class, PROPERTY_DESCRIPTION);
        Property<Calendar> startTime = new Property<>(Calendar.class, PROPERTY_START_TIME);
        Property<Calendar> endTime = new Property<>(Calendar.class, PROPERTY_END_TIME);
        Property<String> startAddress = new Property<>(String.class, PROPERTY_START_ADDRESS);
        Property<String> endAddress = new Property<>(String.class, PROPERTY_END_ADDRESS);
        Property<Coordinates> startCoordinates = new Property<>(Coordinates.class, PROPERTY_START_COORDINATES);
        Property<Coordinates> endCoordinates = new Property<>(Coordinates.class, PROPERTY_END_COORDINATES);
        Property<Length> startOdometer = new Property<>(Length.class, PROPERTY_START_ODOMETER);
        Property<Length> endOdometer = new Property<>(Length.class, PROPERTY_END_ODOMETER);
        Property<FuelEfficiency> averageFuelConsumption = new Property<>(FuelEfficiency.class, PROPERTY_AVERAGE_FUEL_CONSUMPTION);
        Property<Length> distance = new Property<>(Length.class, PROPERTY_DISTANCE);
        List<Property<AddressComponent>> startAddressComponents;
        List<Property<AddressComponent>> endAddressComponents;
        Property<Event> event = new Property<>(Event.class, PROPERTY_EVENT);
        Property<EcoLevel> ecoLevel = new Property<>(EcoLevel.class, PROPERTY_ECO_LEVEL);
        List<Property<EcoDrivingThreshold>> thresholds;
        Property<Volume> totalFuelConsumption = new Property<>(Volume.class, PROPERTY_TOTAL_FUEL_CONSUMPTION);
        Property<Volume> totalIdleFuelConsumption = new Property<>(Volume.class, PROPERTY_TOTAL_IDLE_FUEL_CONSUMPTION);
        Property<Speed> maximumSpeed = new Property<>(Speed.class, PROPERTY_MAXIMUM_SPEED);
        Property<RoadType> roadType = new Property<>(RoadType.class, PROPERTY_ROAD_TYPE);
        PropertyInteger brakingCount = new PropertyInteger(PROPERTY_BRAKING_COUNT, false);
    
        /**
         * @return Type of the trip
         */
        public Property<Type> getType() {
            return type;
        }
    
        /**
         * @return Name of the driver of the trip
         */
        public Property<String> getDriverName() {
            return driverName;
        }
    
        /**
         * @return Description of the trip
         */
        public Property<String> getDescription() {
            return description;
        }
    
        /**
         * @return Start time of the trip
         */
        public Property<Calendar> getStartTime() {
            return startTime;
        }
    
        /**
         * @return End time of the trip
         */
        public Property<Calendar> getEndTime() {
            return endTime;
        }
    
        /**
         * @return Start address of the trip
         */
        public Property<String> getStartAddress() {
            return startAddress;
        }
    
        /**
         * @return End address of the trip
         */
        public Property<String> getEndAddress() {
            return endAddress;
        }
    
        /**
         * @return Start coordinates of the trip
         */
        public Property<Coordinates> getStartCoordinates() {
            return startCoordinates;
        }
    
        /**
         * @return End coordinates of the trip
         */
        public Property<Coordinates> getEndCoordinates() {
            return endCoordinates;
        }
    
        /**
         * @return Odometer reading at the start of the trip
         */
        public Property<Length> getStartOdometer() {
            return startOdometer;
        }
    
        /**
         * @return Odometer reading at the end of the trip
         */
        public Property<Length> getEndOdometer() {
            return endOdometer;
        }
    
        /**
         * @return Average fuel consumption during the trip
         */
        public Property<FuelEfficiency> getAverageFuelConsumption() {
            return averageFuelConsumption;
        }
    
        /**
         * @return Distance of the trip
         */
        public Property<Length> getDistance() {
            return distance;
        }
    
        /**
         * @return Start address components
         */
        public List<Property<AddressComponent>> getStartAddressComponents() {
            return startAddressComponents;
        }
    
        /**
         * @return End address components
         */
        public List<Property<AddressComponent>> getEndAddressComponents() {
            return endAddressComponents;
        }
    
        /**
         * @return The event
         */
        public Property<Event> getEvent() {
            return event;
        }
    
        /**
         * @return The eco level
         */
        public Property<EcoLevel> getEcoLevel() {
            return ecoLevel;
        }
    
        /**
         * @return Eco driving thresholds
         */
        public List<Property<EcoDrivingThreshold>> getThresholds() {
            return thresholds;
        }
    
        /**
         * @return Total fuel consumption during the trip
         */
        public Property<Volume> getTotalFuelConsumption() {
            return totalFuelConsumption;
        }
    
        /**
         * @return Fuel consumed while idle since the last ignition on.
         */
        public Property<Volume> getTotalIdleFuelConsumption() {
            return totalIdleFuelConsumption;
        }
    
        /**
         * @return Maximum speed recorded since the last igntion on.
         */
        public Property<Speed> getMaximumSpeed() {
            return maximumSpeed;
        }
    
        /**
         * @return Type of road travelled on.
         */
        public Property<RoadType> getRoadType() {
            return roadType;
        }
    
        /**
         * @return Number of times the brakes were applied during the trip.
         */
        public PropertyInteger getBrakingCount() {
            return brakingCount;
        }
    
        State(byte[] bytes) {
            super(bytes);
    
            final ArrayList<Property<AddressComponent>> startAddressComponentsBuilder = new ArrayList<>();
            final ArrayList<Property<AddressComponent>> endAddressComponentsBuilder = new ArrayList<>();
            final ArrayList<Property<EcoDrivingThreshold>> thresholdsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_TYPE: return type.update(p);
                        case PROPERTY_DRIVER_NAME: return driverName.update(p);
                        case PROPERTY_DESCRIPTION: return description.update(p);
                        case PROPERTY_START_TIME: return startTime.update(p);
                        case PROPERTY_END_TIME: return endTime.update(p);
                        case PROPERTY_START_ADDRESS: return startAddress.update(p);
                        case PROPERTY_END_ADDRESS: return endAddress.update(p);
                        case PROPERTY_START_COORDINATES: return startCoordinates.update(p);
                        case PROPERTY_END_COORDINATES: return endCoordinates.update(p);
                        case PROPERTY_START_ODOMETER: return startOdometer.update(p);
                        case PROPERTY_END_ODOMETER: return endOdometer.update(p);
                        case PROPERTY_AVERAGE_FUEL_CONSUMPTION: return averageFuelConsumption.update(p);
                        case PROPERTY_DISTANCE: return distance.update(p);
                        case PROPERTY_START_ADDRESS_COMPONENTS:
                            Property<AddressComponent> startAddressComponent = new Property<>(AddressComponent.class, p);
                            startAddressComponentsBuilder.add(startAddressComponent);
                            return startAddressComponent;
                        case PROPERTY_END_ADDRESS_COMPONENTS:
                            Property<AddressComponent> endAddressComponent = new Property<>(AddressComponent.class, p);
                            endAddressComponentsBuilder.add(endAddressComponent);
                            return endAddressComponent;
                        case PROPERTY_EVENT: return event.update(p);
                        case PROPERTY_ECO_LEVEL: return ecoLevel.update(p);
                        case PROPERTY_THRESHOLDS:
                            Property<EcoDrivingThreshold> threshold = new Property<>(EcoDrivingThreshold.class, p);
                            thresholdsBuilder.add(threshold);
                            return threshold;
                        case PROPERTY_TOTAL_FUEL_CONSUMPTION: return totalFuelConsumption.update(p);
                        case PROPERTY_TOTAL_IDLE_FUEL_CONSUMPTION: return totalIdleFuelConsumption.update(p);
                        case PROPERTY_MAXIMUM_SPEED: return maximumSpeed.update(p);
                        case PROPERTY_ROAD_TYPE: return roadType.update(p);
                        case PROPERTY_BRAKING_COUNT: return brakingCount.update(p);
                    }
    
                    return null;
                });
            }
    
            startAddressComponents = startAddressComponentsBuilder;
            endAddressComponents = endAddressComponentsBuilder;
            thresholds = thresholdsBuilder;
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
             * @param type Type of the trip
             * @return The builder
             */
            public Builder setType(Property<Type> type) {
                Property property = type.setIdentifier(PROPERTY_TYPE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param driverName Name of the driver of the trip
             * @return The builder
             */
            public Builder setDriverName(Property<String> driverName) {
                Property property = driverName.setIdentifier(PROPERTY_DRIVER_NAME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param description Description of the trip
             * @return The builder
             */
            public Builder setDescription(Property<String> description) {
                Property property = description.setIdentifier(PROPERTY_DESCRIPTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param startTime Start time of the trip
             * @return The builder
             */
            public Builder setStartTime(Property<Calendar> startTime) {
                Property property = startTime.setIdentifier(PROPERTY_START_TIME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param endTime End time of the trip
             * @return The builder
             */
            public Builder setEndTime(Property<Calendar> endTime) {
                Property property = endTime.setIdentifier(PROPERTY_END_TIME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param startAddress Start address of the trip
             * @return The builder
             */
            public Builder setStartAddress(Property<String> startAddress) {
                Property property = startAddress.setIdentifier(PROPERTY_START_ADDRESS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param endAddress End address of the trip
             * @return The builder
             */
            public Builder setEndAddress(Property<String> endAddress) {
                Property property = endAddress.setIdentifier(PROPERTY_END_ADDRESS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param startCoordinates Start coordinates of the trip
             * @return The builder
             */
            public Builder setStartCoordinates(Property<Coordinates> startCoordinates) {
                Property property = startCoordinates.setIdentifier(PROPERTY_START_COORDINATES);
                addProperty(property);
                return this;
            }
            
            /**
             * @param endCoordinates End coordinates of the trip
             * @return The builder
             */
            public Builder setEndCoordinates(Property<Coordinates> endCoordinates) {
                Property property = endCoordinates.setIdentifier(PROPERTY_END_COORDINATES);
                addProperty(property);
                return this;
            }
            
            /**
             * @param startOdometer Odometer reading at the start of the trip
             * @return The builder
             */
            public Builder setStartOdometer(Property<Length> startOdometer) {
                Property property = startOdometer.setIdentifier(PROPERTY_START_ODOMETER);
                addProperty(property);
                return this;
            }
            
            /**
             * @param endOdometer Odometer reading at the end of the trip
             * @return The builder
             */
            public Builder setEndOdometer(Property<Length> endOdometer) {
                Property property = endOdometer.setIdentifier(PROPERTY_END_ODOMETER);
                addProperty(property);
                return this;
            }
            
            /**
             * @param averageFuelConsumption Average fuel consumption during the trip
             * @return The builder
             */
            public Builder setAverageFuelConsumption(Property<FuelEfficiency> averageFuelConsumption) {
                Property property = averageFuelConsumption.setIdentifier(PROPERTY_AVERAGE_FUEL_CONSUMPTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param distance Distance of the trip
             * @return The builder
             */
            public Builder setDistance(Property<Length> distance) {
                Property property = distance.setIdentifier(PROPERTY_DISTANCE);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of start address components
             * 
             * @param startAddressComponents The start address components. Start address components
             * @return The builder
             */
            public Builder setStartAddressComponents(Property<AddressComponent>[] startAddressComponents) {
                for (int i = 0; i < startAddressComponents.length; i++) {
                    addStartAddressComponent(startAddressComponents[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single start address component
             * 
             * @param startAddressComponent The start address component. Start address components
             * @return The builder
             */
            public Builder addStartAddressComponent(Property<AddressComponent> startAddressComponent) {
                startAddressComponent.setIdentifier(PROPERTY_START_ADDRESS_COMPONENTS);
                addProperty(startAddressComponent);
                return this;
            }
            
            /**
             * Add an array of end address components
             * 
             * @param endAddressComponents The end address components. End address components
             * @return The builder
             */
            public Builder setEndAddressComponents(Property<AddressComponent>[] endAddressComponents) {
                for (int i = 0; i < endAddressComponents.length; i++) {
                    addEndAddressComponent(endAddressComponents[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single end address component
             * 
             * @param endAddressComponent The end address component. End address components
             * @return The builder
             */
            public Builder addEndAddressComponent(Property<AddressComponent> endAddressComponent) {
                endAddressComponent.setIdentifier(PROPERTY_END_ADDRESS_COMPONENTS);
                addProperty(endAddressComponent);
                return this;
            }
            
            /**
             * @param event The event
             * @return The builder
             */
            public Builder setEvent(Property<Event> event) {
                Property property = event.setIdentifier(PROPERTY_EVENT);
                addProperty(property);
                return this;
            }
            
            /**
             * @param ecoLevel The eco level
             * @return The builder
             */
            public Builder setEcoLevel(Property<EcoLevel> ecoLevel) {
                Property property = ecoLevel.setIdentifier(PROPERTY_ECO_LEVEL);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of thresholds
             * 
             * @param thresholds The thresholds. Eco driving thresholds
             * @return The builder
             */
            public Builder setThresholds(Property<EcoDrivingThreshold>[] thresholds) {
                for (int i = 0; i < thresholds.length; i++) {
                    addThreshold(thresholds[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single threshold
             * 
             * @param threshold The threshold. Eco driving thresholds
             * @return The builder
             */
            public Builder addThreshold(Property<EcoDrivingThreshold> threshold) {
                threshold.setIdentifier(PROPERTY_THRESHOLDS);
                addProperty(threshold);
                return this;
            }
            
            /**
             * @param totalFuelConsumption Total fuel consumption during the trip
             * @return The builder
             */
            public Builder setTotalFuelConsumption(Property<Volume> totalFuelConsumption) {
                Property property = totalFuelConsumption.setIdentifier(PROPERTY_TOTAL_FUEL_CONSUMPTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param totalIdleFuelConsumption Fuel consumed while idle since the last ignition on.
             * @return The builder
             */
            public Builder setTotalIdleFuelConsumption(Property<Volume> totalIdleFuelConsumption) {
                Property property = totalIdleFuelConsumption.setIdentifier(PROPERTY_TOTAL_IDLE_FUEL_CONSUMPTION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param maximumSpeed Maximum speed recorded since the last igntion on.
             * @return The builder
             */
            public Builder setMaximumSpeed(Property<Speed> maximumSpeed) {
                Property property = maximumSpeed.setIdentifier(PROPERTY_MAXIMUM_SPEED);
                addProperty(property);
                return this;
            }
            
            /**
             * @param roadType Type of road travelled on.
             * @return The builder
             */
            public Builder setRoadType(Property<RoadType> roadType) {
                Property property = roadType.setIdentifier(PROPERTY_ROAD_TYPE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param brakingCount Number of times the brakes were applied during the trip.
             * @return The builder
             */
            public Builder setBrakingCount(Property<Integer> brakingCount) {
                Property property = new PropertyInteger(PROPERTY_BRAKING_COUNT, false, 2, brakingCount);
                addProperty(property);
                return this;
            }
        }
    }

    public enum Type implements ByteEnum {
        SINGLE((byte) 0x00),
        MULTI((byte) 0x01),
        ECO((byte) 0x02);
    
        public static Type fromByte(byte byteValue) throws CommandParseException {
            Type[] values = Type.values();
    
            for (int i = 0; i < values.length; i++) {
                Type state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Type.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Type(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum Event implements ByteEnum {
        HARSH_BRAKING((byte) 0x00),
        HARSH_ACCELERATION((byte) 0x01),
        SHARP_TURN((byte) 0x02),
        OVER_RPM((byte) 0x03),
        OVERSPEED((byte) 0x04),
        IDLING_ENGINE_ON((byte) 0x05),
        EMERGENCY_BRAKING((byte) 0x06);
    
        public static Event fromByte(byte byteValue) throws CommandParseException {
            Event[] values = Event.values();
    
            for (int i = 0; i < values.length; i++) {
                Event state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Event.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Event(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum EcoLevel implements ByteEnum {
        HIGH((byte) 0x00),
        MEDIUM((byte) 0x01);
    
        public static EcoLevel fromByte(byte byteValue) throws CommandParseException {
            EcoLevel[] values = EcoLevel.values();
    
            for (int i = 0; i < values.length; i++) {
                EcoLevel state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(EcoLevel.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        EcoLevel(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum RoadType implements ByteEnum {
        PRIVATE_OR_GRAVEL((byte) 0x00),
        LOCAL((byte) 0x01),
        COUNTY((byte) 0x02),
        RURAL((byte) 0x03),
        FEDERAL_HIGHWAY((byte) 0x04),
        HIGHWAY((byte) 0x05),
        COUNTRY((byte) 0x06),
        NATIONAL((byte) 0x07);
    
        public static RoadType fromByte(byte byteValue) throws CommandParseException {
            RoadType[] values = RoadType.values();
    
            for (int i = 0; i < values.length; i++) {
                RoadType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(RoadType.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        RoadType(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}