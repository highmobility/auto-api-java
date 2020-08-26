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
import com.highmobility.autoapi.property.ByteEnum;
import java.util.Calendar;
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.autoapi.value.measurement.FuelEfficiency;
import com.highmobility.autoapi.value.AddressComponent;
import java.util.ArrayList;
import java.util.List;

import static com.highmobility.utils.ByteUtils.hexFromByte;

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

    /**
     * The trips state
     */
    public static class State extends SetCommand {
        Property type = new Property<>(Type.class, PROPERTY_TYPE);
        Property driverName = new Property<>(String.class, PROPERTY_DRIVER_NAME);
        Property description = new Property<>(String.class, PROPERTY_DESCRIPTION);
        Property startTime = new Property<>(Calendar.class, PROPERTY_START_TIME);
        Property endTime = new Property<>(Calendar.class, PROPERTY_END_TIME);
        Property startAddress = new Property<>(String.class, PROPERTY_START_ADDRESS);
        Property endAddress = new Property<>(String.class, PROPERTY_END_ADDRESS);
        Property startCoordinates = new Property<>(Coordinates.class, PROPERTY_START_COORDINATES);
        Property endCoordinates = new Property<>(Coordinates.class, PROPERTY_END_COORDINATES);
        Property startOdometer = new Property<>(Length.class, PROPERTY_START_ODOMETER);
        Property endOdometer = new Property<>(Length.class, PROPERTY_END_ODOMETER);
        Property averageFuelConsumption = new Property<>(FuelEfficiency.class, PROPERTY_AVERAGE_FUEL_CONSUMPTION);
        Property distance = new Property<>(Length.class, PROPERTY_DISTANCE);
        Property<AddressComponent>[] startAddressComponents;
        Property<AddressComponent>[] endAddressComponents;
    
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
        public Property<AddressComponent>[] getStartAddressComponents() {
            return startAddressComponents;
        }
    
        /**
         * @return End address components
         */
        public Property<AddressComponent>[] getEndAddressComponents() {
            return endAddressComponents;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            final ArrayList<Property> startAddressComponentsBuilder = new ArrayList<>();
            final ArrayList<Property> endAddressComponentsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
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
                            Property startAddressComponent = new Property<>(AddressComponent.class, p);
                            startAddressComponentsBuilder.add(startAddressComponent);
                            return startAddressComponent;
                        case PROPERTY_END_ADDRESS_COMPONENTS:
                            Property endAddressComponent = new Property<>(AddressComponent.class, p);
                            endAddressComponentsBuilder.add(endAddressComponent);
                            return endAddressComponent;
                    }
    
                    return null;
                });
            }
    
            startAddressComponents = startAddressComponentsBuilder.toArray(new Property[0]);
            endAddressComponents = endAddressComponentsBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            type = builder.type;
            driverName = builder.driverName;
            description = builder.description;
            startTime = builder.startTime;
            endTime = builder.endTime;
            startAddress = builder.startAddress;
            endAddress = builder.endAddress;
            startCoordinates = builder.startCoordinates;
            endCoordinates = builder.endCoordinates;
            startOdometer = builder.startOdometer;
            endOdometer = builder.endOdometer;
            averageFuelConsumption = builder.averageFuelConsumption;
            distance = builder.distance;
            startAddressComponents = builder.startAddressComponents.toArray(new Property[0]);
            endAddressComponents = builder.endAddressComponents.toArray(new Property[0]);
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<Type> type;
            private Property<String> driverName;
            private Property<String> description;
            private Property<Calendar> startTime;
            private Property<Calendar> endTime;
            private Property<String> startAddress;
            private Property<String> endAddress;
            private Property<Coordinates> startCoordinates;
            private Property<Coordinates> endCoordinates;
            private Property<Length> startOdometer;
            private Property<Length> endOdometer;
            private Property<FuelEfficiency> averageFuelConsumption;
            private Property<Length> distance;
            private final List<Property> startAddressComponents = new ArrayList<>();
            private final List<Property> endAddressComponents = new ArrayList<>();
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param type Type of the trip
             * @return The builder
             */
            public Builder setType(Property<Type> type) {
                this.type = type.setIdentifier(PROPERTY_TYPE);
                addProperty(this.type);
                return this;
            }
            
            /**
             * @param driverName Name of the driver of the trip
             * @return The builder
             */
            public Builder setDriverName(Property<String> driverName) {
                this.driverName = driverName.setIdentifier(PROPERTY_DRIVER_NAME);
                addProperty(this.driverName);
                return this;
            }
            
            /**
             * @param description Description of the trip
             * @return The builder
             */
            public Builder setDescription(Property<String> description) {
                this.description = description.setIdentifier(PROPERTY_DESCRIPTION);
                addProperty(this.description);
                return this;
            }
            
            /**
             * @param startTime Start time of the trip
             * @return The builder
             */
            public Builder setStartTime(Property<Calendar> startTime) {
                this.startTime = startTime.setIdentifier(PROPERTY_START_TIME);
                addProperty(this.startTime);
                return this;
            }
            
            /**
             * @param endTime End time of the trip
             * @return The builder
             */
            public Builder setEndTime(Property<Calendar> endTime) {
                this.endTime = endTime.setIdentifier(PROPERTY_END_TIME);
                addProperty(this.endTime);
                return this;
            }
            
            /**
             * @param startAddress Start address of the trip
             * @return The builder
             */
            public Builder setStartAddress(Property<String> startAddress) {
                this.startAddress = startAddress.setIdentifier(PROPERTY_START_ADDRESS);
                addProperty(this.startAddress);
                return this;
            }
            
            /**
             * @param endAddress End address of the trip
             * @return The builder
             */
            public Builder setEndAddress(Property<String> endAddress) {
                this.endAddress = endAddress.setIdentifier(PROPERTY_END_ADDRESS);
                addProperty(this.endAddress);
                return this;
            }
            
            /**
             * @param startCoordinates Start coordinates of the trip
             * @return The builder
             */
            public Builder setStartCoordinates(Property<Coordinates> startCoordinates) {
                this.startCoordinates = startCoordinates.setIdentifier(PROPERTY_START_COORDINATES);
                addProperty(this.startCoordinates);
                return this;
            }
            
            /**
             * @param endCoordinates End coordinates of the trip
             * @return The builder
             */
            public Builder setEndCoordinates(Property<Coordinates> endCoordinates) {
                this.endCoordinates = endCoordinates.setIdentifier(PROPERTY_END_COORDINATES);
                addProperty(this.endCoordinates);
                return this;
            }
            
            /**
             * @param startOdometer Odometer reading at the start of the trip
             * @return The builder
             */
            public Builder setStartOdometer(Property<Length> startOdometer) {
                this.startOdometer = startOdometer.setIdentifier(PROPERTY_START_ODOMETER);
                addProperty(this.startOdometer);
                return this;
            }
            
            /**
             * @param endOdometer Odometer reading at the end of the trip
             * @return The builder
             */
            public Builder setEndOdometer(Property<Length> endOdometer) {
                this.endOdometer = endOdometer.setIdentifier(PROPERTY_END_ODOMETER);
                addProperty(this.endOdometer);
                return this;
            }
            
            /**
             * @param averageFuelConsumption Average fuel consumption during the trip
             * @return The builder
             */
            public Builder setAverageFuelConsumption(Property<FuelEfficiency> averageFuelConsumption) {
                this.averageFuelConsumption = averageFuelConsumption.setIdentifier(PROPERTY_AVERAGE_FUEL_CONSUMPTION);
                addProperty(this.averageFuelConsumption);
                return this;
            }
            
            /**
             * @param distance Distance of the trip
             * @return The builder
             */
            public Builder setDistance(Property<Length> distance) {
                this.distance = distance.setIdentifier(PROPERTY_DISTANCE);
                addProperty(this.distance);
                return this;
            }
            
            /**
             * Add an array of start address components.
             * 
             * @param startAddressComponents The start address components. Start address components
             * @return The builder
             */
            public Builder setStartAddressComponents(Property<AddressComponent>[] startAddressComponents) {
                this.startAddressComponents.clear();
                for (int i = 0; i < startAddressComponents.length; i++) {
                    addStartAddressComponent(startAddressComponents[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single start address component.
             * 
             * @param startAddressComponent The start address component. Start address components
             * @return The builder
             */
            public Builder addStartAddressComponent(Property<AddressComponent> startAddressComponent) {
                startAddressComponent.setIdentifier(PROPERTY_START_ADDRESS_COMPONENTS);
                addProperty(startAddressComponent);
                startAddressComponents.add(startAddressComponent);
                return this;
            }
            
            /**
             * Add an array of end address components.
             * 
             * @param endAddressComponents The end address components. End address components
             * @return The builder
             */
            public Builder setEndAddressComponents(Property<AddressComponent>[] endAddressComponents) {
                this.endAddressComponents.clear();
                for (int i = 0; i < endAddressComponents.length; i++) {
                    addEndAddressComponent(endAddressComponents[i]);
                }
            
                return this;
            }
            /**
             * Add a single end address component.
             * 
             * @param endAddressComponent The end address component. End address components
             * @return The builder
             */
            public Builder addEndAddressComponent(Property<AddressComponent> endAddressComponent) {
                endAddressComponent.setIdentifier(PROPERTY_END_ADDRESS_COMPONENTS);
                addProperty(endAddressComponent);
                endAddressComponents.add(endAddressComponent);
                return this;
            }
        }
    }

    public enum Type implements ByteEnum {
        SINGLE((byte) 0x00),
        MULTI((byte) 0x01);
    
        public static Type fromByte(byte byteValue) throws CommandParseException {
            Type[] values = Type.values();
    
            for (int i = 0; i < values.length; i++) {
                Type state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum Type does not contain " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        Type(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}