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
import com.highmobility.autoapi.value.DisplayUnit;
import com.highmobility.autoapi.value.DriverSeatLocation;
import com.highmobility.autoapi.value.Gearbox;
import com.highmobility.autoapi.value.PowerTrain;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Vehicle Status is received by the car. The states are passed along as an
 * array of all states that the vehicle possesses. No states are included for Capabilities that are
 * unsupported.
 */
public class VehicleStatus extends Command {
    public static final Type TYPE = new Type(Identifier.VEHICLE_STATUS, 0x01);

    private static final byte IDENTIFIER_VIN = 0x01;
    private static final byte IDENTIFIER_POWER_TRAIN = 0x02;
    private static final byte IDENTIFIER_MODEL_NAME = 0x03;
    private static final byte IDENTIFIER_NAME = 0x04;
    private static final byte IDENTIFIER_LICENSE_PLATE = 0x05;
    private static final byte IDENTIFIER_SALES_DESIGNATION = 0x06;
    private static final byte IDENTIFIER_MODEL_YEAR = 0x07;
    private static final byte IDENTIFIER_COLOR = 0x08;
    private static final byte IDENTIFIER_POWER = 0x09;
    private static final byte IDENTIFIER_NUMBER_OF_DOORS = 0x0A;
    private static final byte IDENTIFIER_NUMBER_OF_SEATS = 0x0B;
    private static final byte COMMAND_IDENTIFIER = (byte) 0x99;

    private static final byte IDENTIFIER_ENGINE_VOLUME = 0x0C;
    private static final byte IDENTIFIER_MAX_TORQUE = 0x0D;
    private static final byte IDENTIFIER_GEARBOX = 0x0E;

    private static final byte IDENTIFIER_DISPLAY_UNIT = 0x0F;
    private static final byte IDENTIFIER_DRIVER_SEAT_LOCATION = 0x10;
    private static final byte IDENTIFIER_EQUIPMENTS = 0x11;

    private static final byte IDENTIFIER_BRAND = 0x12;
    private static final byte IDENTIFIER_STATE = (byte) 0x99;

    Property<Command>[] states;

    Property<String> vin = new Property(String.class, IDENTIFIER_VIN);
    Property<PowerTrain> powerTrain = new Property(PowerTrain.class, IDENTIFIER_POWER_TRAIN);
    Property<String> modelName = new Property(String.class, IDENTIFIER_MODEL_NAME);
    Property<String> name = new Property(String.class, IDENTIFIER_NAME);
    Property<String> licensePlate = new Property(String.class, IDENTIFIER_LICENSE_PLATE);

    Property<String> salesDesignation = new Property(String.class, IDENTIFIER_SALES_DESIGNATION);
    Property<Integer> modelYear = new PropertyInteger(IDENTIFIER_MODEL_YEAR, false);
    Property<String> color = new Property(String.class, IDENTIFIER_COLOR);
    Property<Integer> power = new PropertyInteger(IDENTIFIER_POWER, false);
    Property<Integer> numberOfDoors = new PropertyInteger(IDENTIFIER_NUMBER_OF_DOORS, false);
    Property<Integer> numberOfSeats = new PropertyInteger(IDENTIFIER_NUMBER_OF_SEATS, false);

    // l7
    Property<Float> engineVolume = new Property(Float.class, IDENTIFIER_ENGINE_VOLUME);
    Property<Integer> maxTorque = new PropertyInteger(IDENTIFIER_MAX_TORQUE, false);
    Property<Gearbox> gearBox = new Property(Gearbox.class, IDENTIFIER_GEARBOX);

    // l8
    Property<DisplayUnit> displayUnit = new Property(DisplayUnit.class, IDENTIFIER_DISPLAY_UNIT);
    Property<DriverSeatLocation> driverSeatLocation = new Property(DriverSeatLocation.class,
            IDENTIFIER_DRIVER_SEAT_LOCATION);
    Property<String>[] equipments;

    // l9
    Property<String> brand = new Property(String.class, IDENTIFIER_BRAND);

    /**
     * @return All of the states.
     */
    public Property<Command>[] getStates() {
        return states;
    }

    /**
     * @param type The type of the command.
     * @return The state for the given Command type, if exists.
     */
    @Nullable public Property<Command> getState(Type type) {
        if (states == null) return null;
        for (int i = 0; i < states.length; i++) {
            Property<Command> command = states[i];
            if (command.getValue() != null && command.getValue().getType().equals(type))
                return command;
        }

        return null;
    }

    /**
     * @return The vehicle's VIN number
     */
    @Nullable public Property<String> getVin() {
        return vin;
    }

    /**
     * @return The vehicle's power train
     */
    @Nullable public Property<PowerTrain> getPowerTrain() {
        return powerTrain;
    }

    /**
     * @return The vehicle's model name
     */
    @Nullable public Property<String> getModelName() {
        return modelName;
    }

    /**
     * @return The vehicle's name
     */
    @Nullable public Property<String> getName() {
        return name;
    }

    /**
     * @return The vehicle's license plate
     */
    @Nullable public Property<String> getLicensePlate() {
        return licensePlate;
    }

    /**
     * @return The sales designation of the model
     */
    @Nullable public Property<String> getSalesDesignation() {
        return salesDesignation;
    }

    /**
     * @return The car model manufacturing year number
     */
    @Nullable public Property<Integer> getModelYear() {
        return modelYear;
    }

    /**
     * @return The color name
     */
    @Nullable public Property<String> getColorName() {
        return color;
    }

    /**
     * @return The power of the car measured in kw
     */
    @Nullable public Property<Integer> getPower() {
        return power;
    }

    /**
     * @return The number of doors
     */
    @Nullable public Property<Integer> getNumberOfDoors() {
        return numberOfDoors;
    }

    /**
     * @return The number of seats
     */
    @Nullable public Property<Integer> getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * @return The engine volume displacement in liters.
     */
    @Nullable public Property<Float> getEngineVolume() {
        return engineVolume;
    }

    /**
     * @return The maximum engine torque in Nm.
     */
    @Nullable public Property<Integer> getMaxTorque() {
        return maxTorque;
    }

    /**
     * @return The gearbox type.
     */
    @Nullable public Property<Gearbox> getGearBox() {
        return gearBox;
    }

    /**
     * @return The display unit.
     */
    @Nullable public Property<DisplayUnit> getDisplayUnit() {
        return displayUnit;
    }

    /**
     * @return The driver seat location.
     */
    @Nullable public Property<DriverSeatLocation> getDriverSeatLocation() {
        return driverSeatLocation;
    }

    /**
     * @return The equipments that the vehicle is equipped with.
     */
    public Property<String>[] getEquipments() {
        return equipments;
    }

    /**
     * @return The vehicle brand name.
     */
    @Nullable public Property<String> getBrand() {
        return brand;
    }

    VehicleStatus(byte[] bytes) {
        super(bytes);

        ArrayList<Property<Command>> states = new ArrayList<>();
        ArrayList<Property<String>> equipments = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_VIN:
                        return vin.update(p);
                    case IDENTIFIER_POWER_TRAIN:
                        return powerTrain.update(p);
                    case IDENTIFIER_MODEL_NAME:
                        return modelName.update(p);
                    case IDENTIFIER_NAME:
                        return name.update(p);
                    case IDENTIFIER_LICENSE_PLATE:
                        return licensePlate.update(p);
                    case IDENTIFIER_SALES_DESIGNATION:
                        return salesDesignation.update(p);
                    case IDENTIFIER_MODEL_YEAR:
                        return modelYear.update(p);
                    case IDENTIFIER_COLOR:
                        return color.update(p);
                    case IDENTIFIER_POWER:
                        return power.update(p);
                    case IDENTIFIER_NUMBER_OF_DOORS:
                        return numberOfDoors.update(p);
                    case IDENTIFIER_NUMBER_OF_SEATS:
                        return numberOfSeats.update(p);
                    case COMMAND_IDENTIFIER:
                        Property<Command> command = new Property(Command.class, p);
                        states.add(command);
                        return command;
                    case IDENTIFIER_ENGINE_VOLUME:
                        return engineVolume.update(p);
                    case IDENTIFIER_MAX_TORQUE:
                        return maxTorque.update(p);
                    case IDENTIFIER_GEARBOX:
                        return gearBox.update(p);
                    case IDENTIFIER_DISPLAY_UNIT:
                        return displayUnit.update(p);
                    case IDENTIFIER_DRIVER_SEAT_LOCATION:
                        return driverSeatLocation.update(p);
                    case IDENTIFIER_EQUIPMENTS:
                        Property<String> equipment = new Property(String.class, p);
                        equipments.add(equipment);
                        return equipment;
                    case IDENTIFIER_BRAND:
                        return brand.update(p);
                }

                return null;
            });
        }

        this.states = states.toArray(new Property[0]);
        this.equipments = equipments.toArray(new Property[0]);
    }

    private VehicleStatus(Builder builder) {
        super(builder);
        vin = builder.vin;
        powerTrain = builder.powerTrain;
        modelName = builder.modelName;
        name = builder.name;
        licensePlate = builder.licensePlate;
        salesDesignation = builder.salesDesignation;
        modelYear = builder.modelYear;
        color = builder.color;
        power = builder.power;
        numberOfDoors = builder.numberOfDoors;
        numberOfSeats = builder.numberOfSeats;
        states = builder.states.toArray(new Property[0]);
        engineVolume = builder.engineVolume;
        maxTorque = builder.maxTorque;
        gearBox = builder.gearBox;

        displayUnit = builder.displayUnit;
        driverSeatLocation = builder.driverSeatLocation;
        equipments = builder.equipments.toArray(new Property[0]);
        brand = builder.brand;
    }

    public static final class Builder extends Command.Builder {
        private Property<String> vin;
        private Property<PowerTrain> powerTrain;
        private Property<String> modelName;
        private Property<String> name;
        private Property<String> licensePlate;
        private Property<String> salesDesignation;
        private PropertyInteger modelYear;
        private Property<String> color;
        private PropertyInteger power;
        private PropertyInteger numberOfDoors;
        private PropertyInteger numberOfSeats;
        private List<Property<Command>> states = new ArrayList<>();

        private Property<Float> engineVolume;
        private PropertyInteger maxTorque;
        private Property<Gearbox> gearBox;

        Property<DisplayUnit> displayUnit;
        Property<DriverSeatLocation> driverSeatLocation;
        List<Property<String>> equipments = new ArrayList<>();

        private Property<String> brand;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param vin The VIN.
         * @return The builder.
         */
        public Builder setVin(Property<String> vin) {
            this.vin = vin;
            addProperty(vin.setIdentifier(IDENTIFIER_VIN));
            return this;
        }

        /**
         * @param powerTrain The power train.
         * @return The builder.
         */
        public Builder setPowerTrain(Property<PowerTrain> powerTrain) {
            this.powerTrain = powerTrain;
            addProperty(powerTrain.setIdentifier(IDENTIFIER_POWER_TRAIN));
            return this;
        }

        /**
         * @param modelName The model name.
         * @return The builder.
         */
        public Builder setModelName(Property<String> modelName) {
            this.modelName = modelName;
            addProperty(modelName.setIdentifier(IDENTIFIER_MODEL_NAME));
            return this;
        }

        /**
         * @param name The vehicle name.
         * @return The builder.
         */
        public Builder setName(Property<String> name) {
            this.name = name;
            addProperty(name.setIdentifier(IDENTIFIER_NAME));
            return this;
        }

        /**
         * @param licensePlate The license plate number.
         * @return The builder.
         */
        public Builder setLicensePlate(Property<String> licensePlate) {
            this.licensePlate = licensePlate;
            addProperty(licensePlate.setIdentifier(IDENTIFIER_LICENSE_PLATE));
            return this;
        }

        /**
         * @param salesDesignation The sales designation.
         * @return The builder.
         */
        public Builder setSalesDesignation(Property<String> salesDesignation) {
            this.salesDesignation = salesDesignation;
            addProperty(salesDesignation.setIdentifier(IDENTIFIER_SALES_DESIGNATION));
            return this;
        }

        /**
         * @param modelYear The model year.
         * @return The builder.
         */
        public Builder setModelYear(Property<Integer> modelYear) {
            this.modelYear = new PropertyInteger(IDENTIFIER_MODEL_YEAR, false, 2, modelYear);
            addProperty(this.modelYear);
            return this;
        }

        /**
         * @param color The color name
         * @return The builder.
         */
        public Builder setColorName(Property<String> color) {
            this.color = color;
            addProperty(color.setIdentifier(IDENTIFIER_COLOR));
            return this;
        }

        /**
         * @param power The power in kw.
         * @return The builder.
         */
        public Builder setPower(Property<Integer> power) {
            this.power = new PropertyInteger(IDENTIFIER_POWER, false, 2, power);
            addProperty(this.power);
            return this;
        }

        /**
         * @param numberOfDoors The number of doors.
         * @return The builder.
         */
        public Builder setNumberOfDoors(Property<Integer> numberOfDoors) {
            this.numberOfDoors = new PropertyInteger(IDENTIFIER_NUMBER_OF_DOORS, false, 1,
                    numberOfDoors);
            addProperty(this.numberOfDoors);
            return this;
        }

        /**
         * @param numberOfSeats The number of seats.
         * @return The builder.
         */
        public Builder setNumberOfSeats(Property<Integer> numberOfSeats) {
            this.numberOfSeats = new PropertyInteger(IDENTIFIER_NUMBER_OF_SEATS, false, 1,
                    numberOfSeats);
            addProperty(this.numberOfSeats);
            return this;
        }

        /**
         * @param states The states.
         * @return The builder.
         */
        public Builder setStates(Property<Command>[] states) {
            this.states.clear();

            for (int i = 0; i < states.length; i++) {
                addState(states[i]);
            }

            return this;
        }

        /**
         * Add a state.
         *
         * @param state A state.
         * @return The builder.
         */
        public Builder addState(Property<Command> state) {
            state.setIdentifier(IDENTIFIER_STATE);
            addProperty(state);
            states.add(state);
            return this;
        }

        /**
         * @param engineVolume The engine volume displacement in liters.
         * @return The builder.
         */
        public Builder setEngineVolume(Property<Float> engineVolume) {
            this.engineVolume = engineVolume;
            engineVolume.setIdentifier(IDENTIFIER_ENGINE_VOLUME);
            addProperty(engineVolume);
            return this;
        }

        /**
         * @param maxTorque The maximum engine torque in Nm.
         * @return The builder.
         */
        public Builder setMaxTorque(Property<Integer> maxTorque) {
            this.maxTorque = new PropertyInteger(IDENTIFIER_MAX_TORQUE, false, 2, maxTorque);
            addProperty(this.maxTorque);
            return this;
        }

        /**
         * @param gearBox The gearbox type.
         * @return The builder.
         */
        public Builder setGearBox(Property<Gearbox> gearBox) {
            this.gearBox = gearBox;
            addProperty(gearBox.setIdentifier(IDENTIFIER_GEARBOX));
            return this;
        }

        /**
         * @param displayUnit The display unit.
         * @return The builder.
         */
        public Builder setDisplayUnit(Property<DisplayUnit> displayUnit) {
            this.displayUnit = displayUnit;
            addProperty(displayUnit.setIdentifier(IDENTIFIER_DISPLAY_UNIT));
            return this;
        }

        /**
         * @param driverSeatLocation The driver seat location.
         * @return The builder.
         */
        public Builder setDriverSeatLocation(Property<DriverSeatLocation> driverSeatLocation) {
            this.driverSeatLocation = driverSeatLocation;
            addProperty(driverSeatLocation.setIdentifier(IDENTIFIER_DRIVER_SEAT_LOCATION));
            return this;
        }

        /**
         * @param equipments The equipments that the vehicle is equipped with.
         * @return The builder.
         */
        public Builder setEquipments(Property<String>[] equipments) {
            this.equipments.clear();
            for (Property<String> equipment : equipments) {
                addEquipment(equipment);
            }
            return this;
        }

        /**
         * Add an equipment.
         *
         * @param equipment The equipment.
         * @return The builder.
         */
        public Builder addEquipment(Property<String> equipment) {
            equipment.setIdentifier(IDENTIFIER_EQUIPMENTS);
            equipments.add(equipment);
            addProperty(equipment);
            return this;
        }

        /**
         * @param brand The brand.
         * @return The builder.
         */
        public Builder setBrand(Property<String> brand) {
            this.brand = brand;
            addProperty(brand.setIdentifier(IDENTIFIER_BRAND));
            return this;
        }

        public VehicleStatus build() {
            return new VehicleStatus(this);
        }
    }
}