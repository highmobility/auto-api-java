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
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PowerTrain;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This message is sent when a Get Vehicle Status is received by the car. The states are passed
 * along as an array of all states that the vehicle possesses. No states are included for
 * Capabilities that are unsupported.
 */
public class VehicleStatus extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.VEHICLE_STATUS, 0x01);

    CommandWithProperties[] states;

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

    /**
     * @return The specific states for the vehicle's features.
     */
    public CommandWithProperties[] getStates() {
        return states;
    }

    /**
     *
     * @param type The type of the command.
     *
     * @return The state for the given Command type, if exists.
     */
    public CommandWithProperties getState(Type type) {
        if (states == null) return null;
        for (int i = 0; i < states.length; i++) {
            CommandWithProperties command = states[i];
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

    VehicleStatus(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<CommandWithProperties> states = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            try {
                switch (property.getPropertyIdentifier()) {
                    case 0x01:
                        vin = Property.getString(property.getValueBytes());
                        break;
                    case 0x02:
                        powerTrain = PowerTrain.fromByte(property.getValueByte());
                        break;
                    case 0x03:
                        modelName = Property.getString(property.getValueBytes());
                        break;
                    case 0x04:
                        name = Property.getString(property.getValueBytes());
                        break;
                    case 0x05:
                        licensePlate = Property.getString(property.getValueBytes());
                        break;
                    case 0x06:
                        salesDesignation = Property.getString(property.getValueBytes());
                        break;
                    case 0x07:
                        modelYear = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case 0x08:
                        color = Property.getString(property.getValueBytes());
                        break;
                    case 0x09:
                        power = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case 0x0A:
                        numberOfDoors = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case 0x0B:
                        numberOfSeats = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case (byte) 0x99:
                        byte[] commandBytes = property.getValueBytes();
                        Command command = CommandResolver.resolve(commandBytes);
                        if (command instanceof CommandWithProperties) { // some commands might be corrupt, dont add these
                            states.add((CommandWithProperties) command);
                        }
                        break;
                }
            } catch (UnsupportedEncodingException e) {
                throw new CommandParseException(CommandParseException.CommandExceptionCode.UNSUPPORTED_VALUE_TYPE);
            }
        }

        this.states = states.toArray(new CommandWithProperties[states.size()]);
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
        private List<CommandWithProperties> states = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        public Builder setVin(String vin) throws UnsupportedEncodingException {
            this.vin = vin;
            addProperty(new StringProperty((byte) 0x01, vin));
            return this;
        }

        public Builder setPowerTrain(PowerTrain powerTrain) {
            this.powerTrain = powerTrain;
            addProperty(powerTrain);
            return this;
        }

        public Builder setModelName(String modelName) throws UnsupportedEncodingException {
            this.modelName = modelName;
            addProperty(new StringProperty((byte) 0x03, modelName));
            return this;
        }

        public Builder setName(String name) throws UnsupportedEncodingException {
            this.name = name;
            addProperty(new StringProperty((byte) 0x04, name));
            return this;
        }

        public Builder setLicensePlate(String licensePlate) throws UnsupportedEncodingException {
            this.licensePlate = licensePlate;
            addProperty(new StringProperty((byte) 0x05, licensePlate));
            return this;
        }

        public Builder setSalesDesignation(String salesDesignation) throws
                UnsupportedEncodingException {
            this.salesDesignation = salesDesignation;
            addProperty(new StringProperty((byte) 0x06, salesDesignation));
            return this;
        }

        public Builder setModelYear(Integer modelYear) {
            this.modelYear = modelYear;
            addProperty(new IntegerProperty((byte) 0x07, modelYear, 2));
            return this;
        }

        public Builder setColor(String color) throws UnsupportedEncodingException {
            this.color = color;
            addProperty(new StringProperty((byte) 0x08, color));
            return this;
        }

        public Builder setPower(Integer power) {
            this.power = power;
            addProperty(new IntegerProperty((byte) 0x09, power, 2));
            return this;
        }

        public Builder setNumberOfDoors(Integer numberOfDoors) {
            this.numberOfDoors = numberOfDoors;
            addProperty(new IntegerProperty((byte) 0x0a, numberOfDoors, 1));
            return this;
        }

        public Builder setNumberOfSeats(Integer numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
            addProperty(new IntegerProperty((byte) 0x0b, numberOfSeats, 1));
            return this;
        }

        public Builder setStates(CommandWithProperties[] states) {
            this.states.addAll(Arrays.asList(states));

            for (int i = 0; i < states.length; i++) {
                addProperty(new CommandProperty(states[i]));
            }

            return this;
        }

        public Builder addState(CommandWithProperties state) {
            addProperty(new CommandProperty(state));
            states.add(state);
            return this;
        }

        public VehicleStatus build() {
            return new VehicleStatus(this);
        }
    }
}