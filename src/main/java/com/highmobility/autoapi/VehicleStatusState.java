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
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.PropertyInteger;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * The vehicle status state
 */
public class VehicleStatusState extends SetCommand {
    public static final Identifier identifier = Identifier.VEHICLE_STATUS;

    Property<String> vin = new Property(String.class, 0x01);
    Property<Powertrain> powertrain = new Property(Powertrain.class, 0x02);
    Property<String> modelName = new Property(String.class, 0x03);
    Property<String> name = new Property(String.class, 0x04);
    Property<String> licensePlate = new Property(String.class, 0x05);
    Property<String> salesDesignation = new Property(String.class, 0x06);
    PropertyInteger modelYear = new PropertyInteger(0x07, false);
    Property<String> colourName = new Property(String.class, 0x08);
    PropertyInteger powerInKW = new PropertyInteger(0x09, false);
    PropertyInteger numberOfDoors = new PropertyInteger(0x0a, false);
    PropertyInteger numberOfSeats = new PropertyInteger(0x0b, false);
    Property<Float> engineVolume = new Property(Float.class, 0x0c);
    PropertyInteger engineMaxTorque = new PropertyInteger(0x0d, false);
    Property<Gearbox> gearbox = new Property(Gearbox.class, 0x0e);
    Property<DisplayUnit> displayUnit = new Property(DisplayUnit.class, 0x0f);
    Property<DriverSeatLocation> driverSeatLocation = new Property(DriverSeatLocation.class, 0x10);
    Property<String>[] equipments;
    Property<String> brand = new Property(String.class, 0x12);
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
     * @return The car brand bytes
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
    @Nullable public Property<Command> getState(Identifier identifier) {
        if (states == null) return null;
        for (int i = 0; i < states.length; i++) {
            Property<Command> command = states[i];
            if (command.getValue() != null && command.getValue().getIdentifier() == identifier)
                return command;
        }

        return null;
    }

    VehicleStatusState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> equipmentsBuilder = new ArrayList<>();
        ArrayList<Property> statesBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return vin.update(p);
                    case 0x02: return powertrain.update(p);
                    case 0x03: return modelName.update(p);
                    case 0x04: return name.update(p);
                    case 0x05: return licensePlate.update(p);
                    case 0x06: return salesDesignation.update(p);
                    case 0x07: return modelYear.update(p);
                    case 0x08: return colourName.update(p);
                    case 0x09: return powerInKW.update(p);
                    case 0x0a: return numberOfDoors.update(p);
                    case 0x0b: return numberOfSeats.update(p);
                    case 0x0c: return engineVolume.update(p);
                    case 0x0d: return engineMaxTorque.update(p);
                    case 0x0e: return gearbox.update(p);
                    case 0x0f: return displayUnit.update(p);
                    case 0x10: return driverSeatLocation.update(p);
                    case 0x11:
                        Property<String> equipment = new Property(String.class, p);
                        equipmentsBuilder.add(equipment);
                        return equipment;
                    case 0x12: return brand.update(p);
                    case (byte)0x99:
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

    @Override public boolean isState() {
        return true;
    }

    private VehicleStatusState(Builder builder) {
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
            super(identifier);
        }

        public VehicleStatusState build() {
            return new VehicleStatusState(this);
        }

        /**
         * @param vin The unique Vehicle Identification Number
         * @return The builder
         */
        public Builder setVin(Property<String> vin) {
            this.vin = vin.setIdentifier(0x01);
            addProperty(this.vin);
            return this;
        }
        
        /**
         * @param powertrain The powertrain
         * @return The builder
         */
        public Builder setPowertrain(Property<Powertrain> powertrain) {
            this.powertrain = powertrain.setIdentifier(0x02);
            addProperty(this.powertrain);
            return this;
        }
        
        /**
         * @param modelName The car model name
         * @return The builder
         */
        public Builder setModelName(Property<String> modelName) {
            this.modelName = modelName.setIdentifier(0x03);
            addProperty(this.modelName);
            return this;
        }
        
        /**
         * @param name The car name (nickname)
         * @return The builder
         */
        public Builder setName(Property<String> name) {
            this.name = name.setIdentifier(0x04);
            addProperty(this.name);
            return this;
        }
        
        /**
         * @param licensePlate The license plate number
         * @return The builder
         */
        public Builder setLicensePlate(Property<String> licensePlate) {
            this.licensePlate = licensePlate.setIdentifier(0x05);
            addProperty(this.licensePlate);
            return this;
        }
        
        /**
         * @param salesDesignation The sales designation of the model
         * @return The builder
         */
        public Builder setSalesDesignation(Property<String> salesDesignation) {
            this.salesDesignation = salesDesignation.setIdentifier(0x06);
            addProperty(this.salesDesignation);
            return this;
        }
        
        /**
         * @param modelYear The car model manufacturing year number
         * @return The builder
         */
        public Builder setModelYear(Property<Integer> modelYear) {
            this.modelYear = new PropertyInteger(0x07, false, 2, modelYear);
            addProperty(this.modelYear);
            return this;
        }
        
        /**
         * @param colourName The colour name
         * @return The builder
         */
        public Builder setColourName(Property<String> colourName) {
            this.colourName = colourName.setIdentifier(0x08);
            addProperty(this.colourName);
            return this;
        }
        
        /**
         * @param powerInKW The power of the car measured in kW
         * @return The builder
         */
        public Builder setPowerInKW(Property<Integer> powerInKW) {
            this.powerInKW = new PropertyInteger(0x09, false, 2, powerInKW);
            addProperty(this.powerInKW);
            return this;
        }
        
        /**
         * @param numberOfDoors The number of doors
         * @return The builder
         */
        public Builder setNumberOfDoors(Property<Integer> numberOfDoors) {
            this.numberOfDoors = new PropertyInteger(0x0a, false, 1, numberOfDoors);
            addProperty(this.numberOfDoors);
            return this;
        }
        
        /**
         * @param numberOfSeats The number of seats
         * @return The builder
         */
        public Builder setNumberOfSeats(Property<Integer> numberOfSeats) {
            this.numberOfSeats = new PropertyInteger(0x0b, false, 1, numberOfSeats);
            addProperty(this.numberOfSeats);
            return this;
        }
        
        /**
         * @param engineVolume The engine volume displacement in liters
         * @return The builder
         */
        public Builder setEngineVolume(Property<Float> engineVolume) {
            this.engineVolume = engineVolume.setIdentifier(0x0c);
            addProperty(this.engineVolume);
            return this;
        }
        
        /**
         * @param engineMaxTorque The maximum engine torque in Nm
         * @return The builder
         */
        public Builder setEngineMaxTorque(Property<Integer> engineMaxTorque) {
            this.engineMaxTorque = new PropertyInteger(0x0d, false, 2, engineMaxTorque);
            addProperty(this.engineMaxTorque);
            return this;
        }
        
        /**
         * @param gearbox The gearbox
         * @return The builder
         */
        public Builder setGearbox(Property<Gearbox> gearbox) {
            this.gearbox = gearbox.setIdentifier(0x0e);
            addProperty(this.gearbox);
            return this;
        }
        
        /**
         * @param displayUnit The display unit
         * @return The builder
         */
        public Builder setDisplayUnit(Property<DisplayUnit> displayUnit) {
            this.displayUnit = displayUnit.setIdentifier(0x0f);
            addProperty(this.displayUnit);
            return this;
        }
        
        /**
         * @param driverSeatLocation The driver seat location
         * @return The builder
         */
        public Builder setDriverSeatLocation(Property<DriverSeatLocation> driverSeatLocation) {
            this.driverSeatLocation = driverSeatLocation.setIdentifier(0x10);
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
            equipment.setIdentifier(0x11);
            addProperty(equipment);
            equipments.add(equipment);
            return this;
        }
        
        /**
         * @param brand The car brand bytes
         * @return The builder
         */
        public Builder setBrand(Property<String> brand) {
            this.brand = brand.setIdentifier(0x12);
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
            state.setIdentifier(0x99);
            addProperty(state);
            states.add(state);
            return this;
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