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
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PowerTrain;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;
import com.highmobility.utils.ByteUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    Command[] states;

    String vin;
    PowerTrain powerTrain;
    String modelName;
    String name;
    String licensePlate;

    String salesDesignation;
    Integer modelYear;
    String color;
    Integer power;
    Integer numberOfDoors;
    Integer numberOfSeats;

    // l7
    Float engineVolume; // 0c The engine volume displacement in liters
    Integer maxTorque; // 0d maximum engine torque in Nm
    Gearbox gearBox; // 0e Gearbox type

    /**
     * @return All of the states.
     */
    public Command[] getStates() {
        return states;
    }

    /**
     * @param type The type of the command.
     * @return The state for the given Command type, if exists.
     */
    public Command getState(Type type) {
        if (states == null) return null;
        for (int i = 0; i < states.length; i++) {
            Command command = states[i];
            if (command.getType().equals(type)) return command;
        }

        return null;
    }

    /**
     * @return The vehicle's VIN number
     */
    public String getVin() {
        return vin;
    }

    /**
     * @return The vehicle's power train
     */
    public PowerTrain getPowerTrain() {
        return powerTrain;
    }

    /**
     * @return The vehicle's model name
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @return The vehicle's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The vehicle's license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * @return The sales designation of the model
     */
    public String getSalesDesignation() {
        return salesDesignation;
    }

    /**
     * @return The car model manufacturing year number
     */
    public Integer getModelYear() {
        return modelYear;
    }

    /**
     * @return The color name
     */
    public String getColorName() {
        return color;
    }

    /**
     * @return The power of the car measured in kw
     */
    public Integer getPower() {
        return power;
    }

    /**
     * @return The number of doors
     */
    public Integer getNumberOfDoors() {
        return numberOfDoors;
    }

    /**
     * @return The number of seats
     */
    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * @return The engine volume displacement in liters.
     */
    public Float getEngineVolume() {
        return engineVolume;
    }

    /**
     * @return The maximum engine torque in Nm.
     */
    public Integer getMaxTorque() {
        return maxTorque;
    }

    /**
     * @return The gearbox type.
     */
    public Gearbox getGearBox() {
        return gearBox;
    }

    VehicleStatus(byte[] bytes) {
        super(bytes);

        ArrayList<Command> states = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            try {
                switch (property.getPropertyIdentifier()) {
                    case VIN_IDENTIFIER:
                        vin = Property.getString(property.getValueBytes());
                        break;
                    case POWER_TRAIN_IDENTIFIER:
                        powerTrain = PowerTrain.fromByte(property.getValueByte());
                        break;
                    case MODEL_NAME_IDENTIFIER:
                        modelName = Property.getString(property.getValueBytes());
                        break;
                    case NAME_IDENTIFIER:
                        name = Property.getString(property.getValueBytes());
                        break;
                    case LICENSE_PLATE_IDENTIFIER:
                        licensePlate = Property.getString(property.getValueBytes());
                        break;
                    case SALES_DESIGNATION_IDENTIFIER:
                        salesDesignation = Property.getString(property.getValueBytes());
                        break;
                    case MODEL_YEAR_IDENTIFIER:
                        modelYear = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case COLOR_IDENTIFIER:
                        color = Property.getString(property.getValueBytes());
                        break;
                    case POWER_IDENTIFIER:
                        power = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case NUMBER_OF_DOORS_IDENTIFIER:
                        numberOfDoors = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case NUMBER_OF_SEATS_IDENTIFIER:
                        numberOfSeats = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case COMMAND_IDENTIFIER:
                        byte[] commandBytes = property.getValueBytes();
                        try {
                            Command command = CommandResolver.resolve(commandBytes);
                            if (command != null) states.add(command);
                        } catch (Exception e) {
                            logger.info("invalid state " + ByteUtils.hexFromBytes(commandBytes));
                        }
                        break;
                    case ENGINE_VOLUME_IDENTIFIER:
                        engineVolume = Property.getFloat(property.getValueBytes());
                        break;
                    case MAX_TORQUE_IDENTIFIER:
                        maxTorque = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case GEARBOX_IDENTIFIER:
                        gearBox = Gearbox.fromByte(property.getValueByte());
                        break;
                }
            } catch (Exception e) {
                logger.info(ByteUtils.hexFromBytes(property.getPropertyBytes()) + " " + e.toString());
            }
        }

        this.states = states.toArray(new Command[states.size()]);
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
        states = builder.states.toArray(new CommandWithProperties[builder.states.size()]);
        engineVolume = builder.engineVolume;
        maxTorque = builder.maxTorque;
        gearBox = builder.gearBox;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private String vin;
        private PowerTrain powerTrain;
        private String modelName;
        private String name;
        private String licensePlate;
        private String salesDesignation;
        private Integer modelYear;
        private String color;
        private Integer power;
        private Integer numberOfDoors;
        private Integer numberOfSeats;
        private List<Command> states = new ArrayList<>();

        private Float engineVolume;
        private Integer maxTorque;
        private Gearbox gearBox;

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
            addProperty(powerTrain);
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
        public Builder setModelYear(Integer modelYear) {
            this.modelYear = modelYear;
            addProperty(new IntegerProperty(MODEL_YEAR_IDENTIFIER, modelYear, 2));
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
        public Builder setPower(Integer power) {
            this.power = power;
            addProperty(new IntegerProperty(POWER_IDENTIFIER, power, 2));
            return this;
        }

        /**
         * @param numberOfDoors The number of doors.
         * @return The builder.
         */
        public Builder setNumberOfDoors(Integer numberOfDoors) {
            this.numberOfDoors = numberOfDoors;
            addProperty(new IntegerProperty(NUMBER_OF_DOORS_IDENTIFIER, numberOfDoors, 1));
            return this;
        }

        /**
         * @param numberOfSeats The number of seats.
         * @return The builder.
         */
        public Builder setNumberOfSeats(Integer numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
            addProperty(new IntegerProperty(NUMBER_OF_SEATS_IDENTIFIER, numberOfSeats, 1));
            return this;
        }

        /**
         * @param states The states.
         * @return The builder.
         */
        public Builder setStates(Command[] states) {
            this.states = Arrays.asList(states);

            for (int i = 0; i < states.length; i++) {
                addProperty(new CommandProperty(states[i]));
            }

            return this;
        }

        /**
         * Add a state.
         *
         * @param state A state.
         * @return The builder.
         */
        public Builder addState(Command state) {
            addProperty(new CommandProperty(state));
            states.add(state);
            return this;
        }

        /**
         * @param engineVolume The engine volume displacement in liters.
         * @return The builder.
         */
        public Builder setEngineVolume(Float engineVolume) {
            this.engineVolume = engineVolume;
            addProperty(new FloatProperty(ENGINE_VOLUME_IDENTIFIER, engineVolume));
            return this;
        }

        /**
         * @param maxTorque The maximum engine torque in Nm.
         * @return The builder.
         */
        public Builder setMaxTorque(Integer maxTorque) {
            this.maxTorque = maxTorque;
            addProperty(new IntegerProperty(MAX_TORQUE_IDENTIFIER, maxTorque, 2));
            return this;
        }

        /**
         * @param gearBox The gearbox type.
         * @return The builder.
         */
        public Builder setGearBox(Gearbox gearBox) {
            this.gearBox = gearBox;
            addProperty(gearBox);
            return this;
        }

        public VehicleStatus build() {
            return new VehicleStatus(this);
        }
    }

    public enum Gearbox implements HMProperty {
        MANUAL((byte) 0x00),
        AUTOMATIC((byte) 0x01),
        SEMI_AUTOMATIC((byte) 0x02);

        public static Gearbox fromByte(byte value) throws CommandParseException {
            Gearbox[] values = Gearbox.values();

            for (int i = 0; i < values.length; i++) {
                Gearbox value1 = values[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Gearbox(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }

        @Override public byte getPropertyIdentifier() {
            return GEARBOX_IDENTIFIER;
        }

        @Override public int getPropertyLength() {
            return 1;
        }

        @Override public byte[] getPropertyBytes() {
            return Property.getPropertyBytes(getPropertyIdentifier(), value);
        }
    }
}