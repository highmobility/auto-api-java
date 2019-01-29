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

import com.highmobility.autoapi.property.CommandProperty;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PowerTrain;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;
import com.highmobility.autoapi.property.value.DisplayUnit;
import com.highmobility.autoapi.property.value.DriverSeatLocation;
import com.highmobility.autoapi.property.value.Gearbox;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Vehicle Status is received by the car. The states are passed along as an
 * array of all states that the vehicle possesses. No states are included for Capabilities that are
 * unsupported.
 */
public class VehicleStatus extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.VEHICLE_STATUS, 0x01);

    private static final byte VIN_IDENTIFIER = 0x01;
    private static final byte POWER_TRAIN_IDENTIFIER = 0x02;
    private static final byte MODEL_NAME_IDENTIFIER = 0x03;
    private static final byte NAME_IDENTIFIER = 0x04;
    private static final byte LICENSE_PLATE_IDENTIFIER = 0x05;
    private static final byte SALES_DESIGNATION_IDENTIFIER = 0x06;
    private static final byte MODEL_YEAR_IDENTIFIER = 0x07;
    private static final byte COLOR_IDENTIFIER = 0x08;
    private static final byte POWER_IDENTIFIER = 0x09;
    private static final byte NUMBER_OF_DOORS_IDENTIFIER = 0x0A;
    private static final byte NUMBER_OF_SEATS_IDENTIFIER = 0x0B;
    private static final byte COMMAND_IDENTIFIER = (byte) 0x99;

    private static final byte ENGINE_VOLUME_IDENTIFIER = 0x0C;
    private static final byte MAX_TORQUE_IDENTIFIER = 0x0D;
    private static final byte GEARBOX_IDENTIFIER = 0x0E;

    private static final byte IDENTIFIER_DISPLAY_UNIT = 0x0F;
    private static final byte IDENTIFIER_DRIVER_SEAT_LOCATION = 0x10;
    private static final byte IDENTIFIER_EQUIPMENTS = 0x11;

    private static final byte IDENTIFIER_BRAND = 0x12;
    private static final byte IDENTIFIER_STATE = (byte) 0x99;

    CommandProperty[] states;

    String vin;
    PowerTrain powerTrain;
    String modelName;
    String name;
    String licensePlate;

    String salesDesignation;
    IntegerProperty modelYear;
    String color;
    IntegerProperty power;
    IntegerProperty numberOfDoors;
    IntegerProperty numberOfSeats;

    // l7
    FloatProperty engineVolume; // 0c The engine volume displacement in liters
    IntegerProperty maxTorque; // 0d maximum engine torque in Nm
    Gearbox gearBox; // 0e Gearbox type

    // l8
    DisplayUnit displayUnit;
    DriverSeatLocation driverSeatLocation;
    String[] equipments;

    // l9
    String brand;

    /**
     * @return All of the states.
     */
    public CommandProperty[] getStates() {
        return states;
    }

    /**
     * @param type The type of the command.
     * @return The state for the given Command type, if exists.
     */
    @Nullable public CommandProperty getState(Type type) {
        if (states == null) return null;
        for (int i = 0; i < states.length; i++) {
            CommandProperty command = states[i];
            if (command.getValue() != null && command.getValue().getType().equals(type))
                return command;
        }

        return null;
    }

    /**
     * @return The vehicle's VIN number
     */
    @Nullable public String getVin() {
        return vin;
    }

    /**
     * @return The vehicle's power train
     */
    @Nullable public PowerTrain getPowerTrain() {
        return powerTrain;
    }

    /**
     * @return The vehicle's model name
     */
    @Nullable public String getModelName() {
        return modelName;
    }

    /**
     * @return The vehicle's name
     */
    @Nullable public String getName() {
        return name;
    }

    /**
     * @return The vehicle's license plate
     */
    @Nullable public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * @return The sales designation of the model
     */
    @Nullable public String getSalesDesignation() {
        return salesDesignation;
    }

    /**
     * @return The car model manufacturing year number
     */
    @Nullable public IntegerProperty getModelYear() {
        return modelYear;
    }

    /**
     * @return The color name
     */
    @Nullable public String getColorName() {
        return color;
    }

    /**
     * @return The power of the car measured in kw
     */
    @Nullable public IntegerProperty getPower() {
        return power;
    }

    /**
     * @return The number of doors
     */
    @Nullable public IntegerProperty getNumberOfDoors() {
        return numberOfDoors;
    }

    /**
     * @return The number of seats
     */
    @Nullable public IntegerProperty getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * @return The engine volume displacement in liters.
     */
    @Nullable public FloatProperty getEngineVolume() {
        return engineVolume;
    }

    /**
     * @return The maximum engine torque in Nm.
     */
    @Nullable public IntegerProperty getMaxTorque() {
        return maxTorque;
    }

    /**
     * @return The gearbox type.
     */
    @Nullable public Gearbox getGearBox() {
        return gearBox;
    }

    /**
     * @return The display unit.
     */
    @Nullable public DisplayUnit getDisplayUnit() {
        return displayUnit;
    }

    /**
     * @return The driver seat location.
     */
    @Nullable public DriverSeatLocation getDriverSeatLocation() {
        return driverSeatLocation;
    }

    /**
     * @return The equipments that the vehicle is equipped with.
     */
    public String[] getEquipments() {
        return equipments;
    }

    /**
     * @return The vehicle brand name.
     */
    @Nullable public String getBrand() {
        return brand;
    }

    VehicleStatus(byte[] bytes) {
        super(bytes);

        ArrayList<CommandProperty> states = new ArrayList<>();
        ArrayList<String> equipments = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case VIN_IDENTIFIER:
                        vin = Property.getString(p.getValueBytes());
                        return vin;
                    case POWER_TRAIN_IDENTIFIER:
                        powerTrain = PowerTrain.fromByte(p.getValueByte());
                        return powerTrain;
                    case MODEL_NAME_IDENTIFIER:
                        modelName = Property.getString(p.getValueBytes());
                        return modelName;
                    case NAME_IDENTIFIER:
                        name = Property.getString(p.getValueBytes());
                        return name;
                    case LICENSE_PLATE_IDENTIFIER:
                        licensePlate = Property.getString(p.getValueBytes());
                        return licensePlate;
                    case SALES_DESIGNATION_IDENTIFIER:
                        salesDesignation = Property.getString(p.getValueBytes());
                        return salesDesignation;
                    case MODEL_YEAR_IDENTIFIER:
                        modelYear = new IntegerProperty(p, false);
                        return modelYear;
                    case COLOR_IDENTIFIER:
                        color = Property.getString(p.getValueBytes());
                        return color;
                    case POWER_IDENTIFIER:
                        power = new IntegerProperty(p, false);
                        return power;
                    case NUMBER_OF_DOORS_IDENTIFIER:
                        numberOfDoors = new IntegerProperty(p, false);
                        return numberOfDoors;
                    case NUMBER_OF_SEATS_IDENTIFIER:
                        numberOfSeats = new IntegerProperty(p, false);
                        return numberOfSeats;
                    case COMMAND_IDENTIFIER:
                        CommandProperty command = new CommandProperty(p);
                        states.add(command);
                        return command;
                    case ENGINE_VOLUME_IDENTIFIER:
                        engineVolume = new FloatProperty(p);
                        return engineVolume;
                    case MAX_TORQUE_IDENTIFIER:
                        maxTorque = new IntegerProperty(p, false);
                        return maxTorque;
                    case GEARBOX_IDENTIFIER:
                        gearBox = Gearbox.fromByte(p.getValueByte());
                        return gearBox;
                    case IDENTIFIER_DISPLAY_UNIT:
                        displayUnit = DisplayUnit.fromByte(p.getValueByte());
                        return displayUnit;
                    case IDENTIFIER_DRIVER_SEAT_LOCATION:
                        driverSeatLocation = DriverSeatLocation.fromByte(p.getValueByte());
                        return driverSeatLocation;
                    case IDENTIFIER_EQUIPMENTS:
                        String equipment = Property.getString(p.getValueBytes());
                        equipments.add(equipment);
                        return equipment;
                    case IDENTIFIER_BRAND:
                        brand = Property.getString(p.getValueBytes());
                        return brand;
                }

                return null;
            });
        }

        this.states = states.toArray(new CommandProperty[0]);
        this.equipments = equipments.toArray(new String[states.size()]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
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
        states = builder.states.toArray(new CommandProperty[0]);
        engineVolume = builder.engineVolume;
        maxTorque = builder.maxTorque;
        gearBox = builder.gearBox;

        displayUnit = builder.displayUnit;
        driverSeatLocation = builder.driverSeatLocation;
        equipments = builder.equipments.toArray(new String[0]);
        brand = builder.brand;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private String vin;
        private PowerTrain powerTrain;
        private String modelName;
        private String name;
        private String licensePlate;
        private String salesDesignation;
        private IntegerProperty modelYear;
        private String color;
        private IntegerProperty power;
        private IntegerProperty numberOfDoors;
        private IntegerProperty numberOfSeats;
        private List<CommandProperty> states = new ArrayList<>();

        private FloatProperty engineVolume;
        private IntegerProperty maxTorque;
        private Gearbox gearBox;

        DisplayUnit displayUnit;
        DriverSeatLocation driverSeatLocation;
        List<String> equipments = new ArrayList<>();

        private String brand;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param vin The VIN.
         * @return The builder.
         */
        public Builder setVin(String vin) {
            this.vin = vin;
            addProperty(new StringProperty(VIN_IDENTIFIER, vin));
            return this;
        }

        /**
         * @param powerTrain The power train.
         * @return The builder.
         */
        public Builder setPowerTrain(PowerTrain powerTrain) {
            this.powerTrain = powerTrain;
            addProperty(new Property(POWER_TRAIN_IDENTIFIER, powerTrain.getByte()));
            return this;
        }

        /**
         * @param modelName The model name.
         * @return The builder.
         */
        public Builder setModelName(String modelName) {
            this.modelName = modelName;
            addProperty(new StringProperty(MODEL_NAME_IDENTIFIER, modelName));
            return this;
        }

        /**
         * @param name The vehicle name.
         * @return The builder.
         */
        public Builder setName(String name) {
            this.name = name;
            addProperty(new StringProperty(NAME_IDENTIFIER, name));
            return this;
        }

        /**
         * @param licensePlate The license plate number.
         * @return The builder.
         */
        public Builder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            addProperty(new StringProperty(LICENSE_PLATE_IDENTIFIER, licensePlate));
            return this;
        }

        /**
         * @param salesDesignation The sales designation.
         * @return The builder.
         */
        public Builder setSalesDesignation(String salesDesignation) {
            this.salesDesignation = salesDesignation;
            addProperty(new StringProperty(SALES_DESIGNATION_IDENTIFIER, salesDesignation));
            return this;
        }

        /**
         * @param modelYear The model year.
         * @return The builder.
         */
        public Builder setModelYear(IntegerProperty modelYear) {
            this.modelYear = modelYear;
            modelYear.setIdentifier(MODEL_YEAR_IDENTIFIER, 2);
            addProperty(modelYear);
            return this;
        }

        /**
         * @param color The color name
         * @return The builder.
         */
        public Builder setColorName(String color) {
            this.color = color;
            addProperty(new StringProperty(COLOR_IDENTIFIER, color));
            return this;
        }

        /**
         * @param power The power in kw.
         * @return The builder.
         */
        public Builder setPower(IntegerProperty power) {
            this.power = power;
            power.setIdentifier(POWER_IDENTIFIER, 2);
            addProperty(power);
            return this;
        }

        /**
         * @param numberOfDoors The number of doors.
         * @return The builder.
         */
        public Builder setNumberOfDoors(IntegerProperty numberOfDoors) {
            this.numberOfDoors = numberOfDoors;
            numberOfDoors.setIdentifier(NUMBER_OF_DOORS_IDENTIFIER, 1);
            addProperty(numberOfDoors);
            return this;
        }

        /**
         * @param numberOfSeats The number of seats.
         * @return The builder.
         */
        public Builder setNumberOfSeats(IntegerProperty numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
            numberOfSeats.setIdentifier(NUMBER_OF_SEATS_IDENTIFIER, 1);
            addProperty(numberOfSeats);
            return this;
        }

        /**
         * @param states The states.
         * @return The builder.
         */
        public Builder setStates(CommandProperty[] states) {
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
        public Builder addState(CommandProperty state) {
            state.setIdentifier(IDENTIFIER_STATE);
            addProperty(state);
            states.add(state);
            return this;
        }

        /**
         * @param engineVolume The engine volume displacement in liters.
         * @return The builder.
         */
        public Builder setEngineVolume(FloatProperty engineVolume) {
            this.engineVolume = engineVolume;
            engineVolume.setIdentifier(ENGINE_VOLUME_IDENTIFIER);
            addProperty(engineVolume);
            return this;
        }

        /**
         * @param maxTorque The maximum engine torque in Nm.
         * @return The builder.
         */
        public Builder setMaxTorque(IntegerProperty maxTorque) {
            this.maxTorque = maxTorque;
            maxTorque.setIdentifier(MAX_TORQUE_IDENTIFIER, 2);
            addProperty(maxTorque);
            return this;
        }

        /**
         * @param gearBox The gearbox type.
         * @return The builder.
         */
        public Builder setGearBox(Gearbox gearBox) {
            this.gearBox = gearBox;
            addProperty(new Property(GEARBOX_IDENTIFIER, gearBox.getByte()));
            return this;
        }

        /**
         * @param displayUnit The display unit.
         * @return The builder.
         */
        public Builder setDisplayUnit(DisplayUnit displayUnit) {
            this.displayUnit = displayUnit;
            addProperty(new Property(IDENTIFIER_DISPLAY_UNIT, displayUnit.getByte()));
            return this;
        }

        /**
         * @param driverSeatLocation The driver seat location.
         * @return The builder.
         */
        public Builder setDriverSeatLocation(DriverSeatLocation driverSeatLocation) {
            this.driverSeatLocation = driverSeatLocation;
            addProperty(new Property(IDENTIFIER_DRIVER_SEAT_LOCATION, driverSeatLocation.getByte
                    ()));
            return this;
        }

        /**
         * @param equipments The equipments that the vehicle is equipped with.
         * @return The builder.
         */
        public Builder setEquipments(String[] equipments) {
            this.equipments.clear();
            for (String equipment : equipments) {
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
        public Builder addEquipment(String equipment) {
            equipments.add(equipment);
            addProperty(new StringProperty(IDENTIFIER_EQUIPMENTS, equipment));
            return this;
        }

        /**
         * @param brand The brand.
         * @return The builder.
         */
        public Builder setBrand(String brand) {
            this.brand = brand;
            addProperty(new StringProperty(IDENTIFIER_BRAND, brand));
            return this;
        }

        public VehicleStatus build() {
            return new VehicleStatus(this);
        }
    }
}