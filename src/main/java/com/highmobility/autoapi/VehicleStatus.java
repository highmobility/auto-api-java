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
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/**
 * The Vehicle Status capability
 */
public class VehicleStatus {
    public static final int IDENTIFIER = Identifier.VEHICLE_STATUS;

    public static final byte PROPERTY_VIN = 0x01;
    public static final byte PROPERTY_POWERTRAIN = 0x02;
    public static final byte PROPERTY_MODEL_NAME = 0x03;
    public static final byte PROPERTY_NAME = 0x04;
    public static final byte PROPERTY_LICENSE_PLATE = 0x05;
    public static final byte PROPERTY_SALES_DESIGNATION = 0x06;
    public static final byte PROPERTY_MODEL_YEAR = 0x07;
    public static final byte PROPERTY_COLOUR_NAME = 0x08;
    public static final byte PROPERTY_POWER_IN_KW = 0x09;
    public static final byte PROPERTY_NUMBER_OF_DOORS = 0x0a;
    public static final byte PROPERTY_NUMBER_OF_SEATS = 0x0b;
    public static final byte PROPERTY_ENGINE_VOLUME = 0x0c;
    public static final byte PROPERTY_ENGINE_MAX_TORQUE = 0x0d;
    public static final byte PROPERTY_GEARBOX = 0x0e;
    public static final byte PROPERTY_DISPLAY_UNIT = 0x0f;
    public static final byte PROPERTY_DRIVER_SEAT_LOCATION = 0x10;
    public static final byte PROPERTY_EQUIPMENTS = 0x11;
    public static final byte PROPERTY_BRAND = 0x12;
    public static final byte PROPERTY_STATES = (byte)0x99;

    /**
     * Get vehicle status
     */
    public static class GetVehicleStatus extends GetCommand {
        public GetVehicleStatus() {
            super(State.class, IDENTIFIER);
        }
    
        GetVehicleStatus(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific vehicle status properties
     */
    public static class GetVehicleStatusProperties extends GetCommand {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetVehicleStatusProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetVehicleStatusProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetVehicleStatusProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The vehicle status state
     */
    public static class State extends SetCommand {
        Property<String> vin = new Property(String.class, PROPERTY_VIN);
        Property<Powertrain> powertrain = new Property(Powertrain.class, PROPERTY_POWERTRAIN);
        Property<String> modelName = new Property(String.class, PROPERTY_MODEL_NAME);
        Property<String> name = new Property(String.class, PROPERTY_NAME);
        Property<String> licensePlate = new Property(String.class, PROPERTY_LICENSE_PLATE);
        Property<String> salesDesignation = new Property(String.class, PROPERTY_SALES_DESIGNATION);
        PropertyInteger modelYear = new PropertyInteger(PROPERTY_MODEL_YEAR, false);
        Property<String> colourName = new Property(String.class, PROPERTY_COLOUR_NAME);
        PropertyInteger powerInKW = new PropertyInteger(PROPERTY_POWER_IN_KW, false);
        PropertyInteger numberOfDoors = new PropertyInteger(PROPERTY_NUMBER_OF_DOORS, false);
        PropertyInteger numberOfSeats = new PropertyInteger(PROPERTY_NUMBER_OF_SEATS, false);
        Property<Float> engineVolume = new Property(Float.class, PROPERTY_ENGINE_VOLUME);
        PropertyInteger engineMaxTorque = new PropertyInteger(PROPERTY_ENGINE_MAX_TORQUE, false);
        Property<Gearbox> gearbox = new Property(Gearbox.class, PROPERTY_GEARBOX);
        Property<DisplayUnit> displayUnit = new Property(DisplayUnit.class, PROPERTY_DISPLAY_UNIT);
        Property<DriverSeatLocation> driverSeatLocation = new Property(DriverSeatLocation.class, PROPERTY_DRIVER_SEAT_LOCATION);
        Property<String>[] equipments;
        Property<String> brand = new Property(String.class, PROPERTY_BRAND);
        Property<Command>[] states;
    
        /**
         * @return The unique Vehicle Identification Number
         */
        public Property<String> getVin() {
            return vin;
        }
    
        /**
         * @return The powertrain
         */
        public Property<Powertrain> getPowertrain() {
            return powertrain;
        }
    
        /**
         * @return The car model name
         */
        public Property<String> getModelName() {
            return modelName;
        }
    
        /**
         * @return The car name (nickname)
         */
        public Property<String> getName() {
            return name;
        }
    
        /**
         * @return The license plate number
         */
        public Property<String> getLicensePlate() {
            return licensePlate;
        }
    
        /**
         * @return The sales designation of the model
         */
        public Property<String> getSalesDesignation() {
            return salesDesignation;
        }
    
        /**
         * @return The car model manufacturing year number
         */
        public PropertyInteger getModelYear() {
            return modelYear;
        }
    
        /**
         * @return The colour name
         */
        public Property<String> getColourName() {
            return colourName;
        }
    
        /**
         * @return The power of the car measured in kW
         */
        public PropertyInteger getPowerInKW() {
            return powerInKW;
        }
    
        /**
         * @return The number of doors
         */
        public PropertyInteger getNumberOfDoors() {
            return numberOfDoors;
        }
    
        /**
         * @return The number of seats
         */
        public PropertyInteger getNumberOfSeats() {
            return numberOfSeats;
        }
    
        /**
         * @return The engine volume displacement in liters
         */
        public Property<Float> getEngineVolume() {
            return engineVolume;
        }
    
        /**
         * @return The maximum engine torque in Nm
         */
        public PropertyInteger getEngineMaxTorque() {
            return engineMaxTorque;
        }
    
        /**
         * @return The gearbox
         */
        public Property<Gearbox> getGearbox() {
            return gearbox;
        }
    
        /**
         * @return The display unit
         */
        public Property<DisplayUnit> getDisplayUnit() {
            return displayUnit;
        }
    
        /**
         * @return The driver seat location
         */
        public Property<DriverSeatLocation> getDriverSeatLocation() {
            return driverSeatLocation;
        }
    
        /**
         * @return Names of equipment the vehicle is equipped with
         */
        public Property<String>[] getEquipments() {
            return equipments;
        }
    
        /**
         * @return The car brand
         */
        public Property<String> getBrand() {
            return brand;
        }
    
        /**
         * @return The states
         */
        public Property<Command>[] getStates() {
            return states;
        }
    
        /**
         * @param identifier The identifier of the command.
         * @return The state for the given Command identifier, if exists.
         */
        @Nullable public Property<Command> getState(Integer identifier) {
            if (states == null) return null;
            for (int i = 0; i < states.length; i++) {
                Property<Command> command = states[i];
                if (command.getValue() != null && command.getValue().getIdentifier() == identifier)
                    return command;
            }
    
            return null;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> equipmentsBuilder = new ArrayList<>();
            ArrayList<Property> statesBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_VIN: return vin.update(p);
                        case PROPERTY_POWERTRAIN: return powertrain.update(p);
                        case PROPERTY_MODEL_NAME: return modelName.update(p);
                        case PROPERTY_NAME: return name.update(p);
                        case PROPERTY_LICENSE_PLATE: return licensePlate.update(p);
                        case PROPERTY_SALES_DESIGNATION: return salesDesignation.update(p);
                        case PROPERTY_MODEL_YEAR: return modelYear.update(p);
                        case PROPERTY_COLOUR_NAME: return colourName.update(p);
                        case PROPERTY_POWER_IN_KW: return powerInKW.update(p);
                        case PROPERTY_NUMBER_OF_DOORS: return numberOfDoors.update(p);
                        case PROPERTY_NUMBER_OF_SEATS: return numberOfSeats.update(p);
                        case PROPERTY_ENGINE_VOLUME: return engineVolume.update(p);
                        case PROPERTY_ENGINE_MAX_TORQUE: return engineMaxTorque.update(p);
                        case PROPERTY_GEARBOX: return gearbox.update(p);
                        case PROPERTY_DISPLAY_UNIT: return displayUnit.update(p);
                        case PROPERTY_DRIVER_SEAT_LOCATION: return driverSeatLocation.update(p);
                        case PROPERTY_EQUIPMENTS:
                            Property<String> equipment = new Property(String.class, p);
                            equipmentsBuilder.add(equipment);
                            return equipment;
                        case PROPERTY_BRAND: return brand.update(p);
                        case PROPERTY_STATES:
                            Property<Command> state = new Property(Command.class, p);
                            statesBuilder.add(state);
                            return state;
                    }
    
                    return null;
                });
            }
    
            equipments = equipmentsBuilder.toArray(new Property[0]);
            states = statesBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            vin = builder.vin;
            powertrain = builder.powertrain;
            modelName = builder.modelName;
            name = builder.name;
            licensePlate = builder.licensePlate;
            salesDesignation = builder.salesDesignation;
            modelYear = builder.modelYear;
            colourName = builder.colourName;
            powerInKW = builder.powerInKW;
            numberOfDoors = builder.numberOfDoors;
            numberOfSeats = builder.numberOfSeats;
            engineVolume = builder.engineVolume;
            engineMaxTorque = builder.engineMaxTorque;
            gearbox = builder.gearbox;
            displayUnit = builder.displayUnit;
            driverSeatLocation = builder.driverSeatLocation;
            equipments = builder.equipments.toArray(new Property[0]);
            brand = builder.brand;
            states = builder.states.toArray(new Property[0]);
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<String> vin;
            private Property<Powertrain> powertrain;
            private Property<String> modelName;
            private Property<String> name;
            private Property<String> licensePlate;
            private Property<String> salesDesignation;
            private PropertyInteger modelYear;
            private Property<String> colourName;
            private PropertyInteger powerInKW;
            private PropertyInteger numberOfDoors;
            private PropertyInteger numberOfSeats;
            private Property<Float> engineVolume;
            private PropertyInteger engineMaxTorque;
            private Property<Gearbox> gearbox;
            private Property<DisplayUnit> displayUnit;
            private Property<DriverSeatLocation> driverSeatLocation;
            private List<Property> equipments = new ArrayList<>();
            private Property<String> brand;
            private List<Property> states = new ArrayList<>();
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param vin The unique Vehicle Identification Number
             * @return The builder
             */
            public Builder setVin(Property<String> vin) {
                this.vin = vin.setIdentifier(PROPERTY_VIN);
                addProperty(this.vin);
                return this;
            }
            
            /**
             * @param powertrain The powertrain
             * @return The builder
             */
            public Builder setPowertrain(Property<Powertrain> powertrain) {
                this.powertrain = powertrain.setIdentifier(PROPERTY_POWERTRAIN);
                addProperty(this.powertrain);
                return this;
            }
            
            /**
             * @param modelName The car model name
             * @return The builder
             */
            public Builder setModelName(Property<String> modelName) {
                this.modelName = modelName.setIdentifier(PROPERTY_MODEL_NAME);
                addProperty(this.modelName);
                return this;
            }
            
            /**
             * @param name The car name (nickname)
             * @return The builder
             */
            public Builder setName(Property<String> name) {
                this.name = name.setIdentifier(PROPERTY_NAME);
                addProperty(this.name);
                return this;
            }
            
            /**
             * @param licensePlate The license plate number
             * @return The builder
             */
            public Builder setLicensePlate(Property<String> licensePlate) {
                this.licensePlate = licensePlate.setIdentifier(PROPERTY_LICENSE_PLATE);
                addProperty(this.licensePlate);
                return this;
            }
            
            /**
             * @param salesDesignation The sales designation of the model
             * @return The builder
             */
            public Builder setSalesDesignation(Property<String> salesDesignation) {
                this.salesDesignation = salesDesignation.setIdentifier(PROPERTY_SALES_DESIGNATION);
                addProperty(this.salesDesignation);
                return this;
            }
            
            /**
             * @param modelYear The car model manufacturing year number
             * @return The builder
             */
            public Builder setModelYear(Property<Integer> modelYear) {
                this.modelYear = new PropertyInteger(PROPERTY_MODEL_YEAR, false, 2, modelYear);
                addProperty(this.modelYear);
                return this;
            }
            
            /**
             * @param colourName The colour name
             * @return The builder
             */
            public Builder setColourName(Property<String> colourName) {
                this.colourName = colourName.setIdentifier(PROPERTY_COLOUR_NAME);
                addProperty(this.colourName);
                return this;
            }
            
            /**
             * @param powerInKW The power of the car measured in kW
             * @return The builder
             */
            public Builder setPowerInKW(Property<Integer> powerInKW) {
                this.powerInKW = new PropertyInteger(PROPERTY_POWER_IN_KW, false, 2, powerInKW);
                addProperty(this.powerInKW);
                return this;
            }
            
            /**
             * @param numberOfDoors The number of doors
             * @return The builder
             */
            public Builder setNumberOfDoors(Property<Integer> numberOfDoors) {
                this.numberOfDoors = new PropertyInteger(PROPERTY_NUMBER_OF_DOORS, false, 1, numberOfDoors);
                addProperty(this.numberOfDoors);
                return this;
            }
            
            /**
             * @param numberOfSeats The number of seats
             * @return The builder
             */
            public Builder setNumberOfSeats(Property<Integer> numberOfSeats) {
                this.numberOfSeats = new PropertyInteger(PROPERTY_NUMBER_OF_SEATS, false, 1, numberOfSeats);
                addProperty(this.numberOfSeats);
                return this;
            }
            
            /**
             * @param engineVolume The engine volume displacement in liters
             * @return The builder
             */
            public Builder setEngineVolume(Property<Float> engineVolume) {
                this.engineVolume = engineVolume.setIdentifier(PROPERTY_ENGINE_VOLUME);
                addProperty(this.engineVolume);
                return this;
            }
            
            /**
             * @param engineMaxTorque The maximum engine torque in Nm
             * @return The builder
             */
            public Builder setEngineMaxTorque(Property<Integer> engineMaxTorque) {
                this.engineMaxTorque = new PropertyInteger(PROPERTY_ENGINE_MAX_TORQUE, false, 2, engineMaxTorque);
                addProperty(this.engineMaxTorque);
                return this;
            }
            
            /**
             * @param gearbox The gearbox
             * @return The builder
             */
            public Builder setGearbox(Property<Gearbox> gearbox) {
                this.gearbox = gearbox.setIdentifier(PROPERTY_GEARBOX);
                addProperty(this.gearbox);
                return this;
            }
            
            /**
             * @param displayUnit The display unit
             * @return The builder
             */
            public Builder setDisplayUnit(Property<DisplayUnit> displayUnit) {
                this.displayUnit = displayUnit.setIdentifier(PROPERTY_DISPLAY_UNIT);
                addProperty(this.displayUnit);
                return this;
            }
            
            /**
             * @param driverSeatLocation The driver seat location
             * @return The builder
             */
            public Builder setDriverSeatLocation(Property<DriverSeatLocation> driverSeatLocation) {
                this.driverSeatLocation = driverSeatLocation.setIdentifier(PROPERTY_DRIVER_SEAT_LOCATION);
                addProperty(this.driverSeatLocation);
                return this;
            }
            
            /**
             * Add an array of equipments.
             * 
             * @param equipments The equipments. Names of equipment the vehicle is equipped with
             * @return The builder
             */
            public Builder setEquipments(Property<String>[] equipments) {
                this.equipments.clear();
                for (int i = 0; i < equipments.length; i++) {
                    addEquipment(equipments[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single equipment.
             * 
             * @param equipment The equipment. Names of equipment the vehicle is equipped with
             * @return The builder
             */
            public Builder addEquipment(Property<String> equipment) {
                equipment.setIdentifier(PROPERTY_EQUIPMENTS);
                addProperty(equipment);
                equipments.add(equipment);
                return this;
            }
            
            /**
             * @param brand The car brand
             * @return The builder
             */
            public Builder setBrand(Property<String> brand) {
                this.brand = brand.setIdentifier(PROPERTY_BRAND);
                addProperty(this.brand);
                return this;
            }
            
            /**
             * Add an array of states.
             * 
             * @param states The states
             * @return The builder
             */
            public Builder setStates(Property<Command>[] states) {
                this.states.clear();
                for (int i = 0; i < states.length; i++) {
                    addState(states[i]);
                }
            
                return this;
            }
            /**
             * Add a single state.
             * 
             * @param state The state
             * @return The builder
             */
            public Builder addState(Property<Command> state) {
                state.setIdentifier(PROPERTY_STATES);
                addProperty(state);
                states.add(state);
                return this;
            }
        }
    }

    public enum Powertrain implements ByteEnum {
        UNKNOWN((byte) 0x00),
        ALL_ELECTRIC((byte) 0x01),
        COMBUSTION_ENGINE((byte) 0x02),
        PHEV((byte) 0x03),
        HYDROGEN((byte) 0x04),
        HYDROGEN_HYBRID((byte) 0x05);
    
        public static Powertrain fromByte(byte byteValue) throws CommandParseException {
            Powertrain[] values = Powertrain.values();
    
            for (int i = 0; i < values.length; i++) {
                Powertrain state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Powertrain(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
    
    public enum Gearbox implements ByteEnum {
        MANUAL((byte) 0x00),
        AUTOMATIC((byte) 0x01),
        SEMI_AUTOMATIC((byte) 0x02);
    
        public static Gearbox fromByte(byte byteValue) throws CommandParseException {
            Gearbox[] values = Gearbox.values();
    
            for (int i = 0; i < values.length; i++) {
                Gearbox state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Gearbox(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
    
    public enum DisplayUnit implements ByteEnum {
        KM((byte) 0x00),
        MILES((byte) 0x01);
    
        public static DisplayUnit fromByte(byte byteValue) throws CommandParseException {
            DisplayUnit[] values = DisplayUnit.values();
    
            for (int i = 0; i < values.length; i++) {
                DisplayUnit state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        DisplayUnit(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
    
    public enum DriverSeatLocation implements ByteEnum {
        LEFT((byte) 0x00),
        RIGHT((byte) 0x01),
        CENTER((byte) 0x02);
    
        public static DriverSeatLocation fromByte(byte byteValue) throws CommandParseException {
            DriverSeatLocation[] values = DriverSeatLocation.values();
    
            for (int i = 0; i < values.length; i++) {
                DriverSeatLocation state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        DriverSeatLocation(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}