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

import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.WasherFluidLevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This message is sent when a Get Diagnostics State message is received by the car. The new status
 * is included in the message payload and may be the result of user, device or car triggered action.
 */
public class DiagnosticsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.DIAGNOSTICS, 0x01);

    private static final byte MILEAGE_IDENTIFIER = 0x01;
    private static final byte OIL_TEMPERATURE_IDENTIFIER = 0x02;
    private static final byte SPEED_IDENTIFIER = 0x03;
    private static final byte RPM_IDENTIFIER = 0x04;
    private static final byte FUEL_LEVEL_IDENTIFIER = 0x05;
    private static final byte RANGE_IDENTIFIER = 0x06;
    private static final byte CURRENT_FUEL_CONSUMPTION_IDENTIFIER = 0x07;
    private static final byte TRIP_FUEL_CONSUMPTION_IDENTIFIER = 0x08;
    private static final byte WASHER_FLUID_LEVEL_IDENTIFIER = 0x09;
    private static final byte BATTERY_VOLTAGE_IDENTIFIER = 0x0B;
    private static final byte AD_BLUE_LEVEL_IDENTIFIER = 0x0C;
    private static final byte DISTANCE_DRIVEN_SINCE_RESET_IDENTIFIER = 0x0D;
    private static final byte DISTANCE_DRIVEN_SINCE_ENGINE_START_IDENTIFIER = 0x0E;
    private static final byte FUEL_VOLUME_IDENTIFIER = 0x0F;

    Integer mileage;
    Integer oilTemperature;
    Integer speed;
    Integer rpm;
    Float fuelLevel;
    Integer range;
    Float currentFuelConsumption;
    Float tripFuelConsumption;
    WasherFluidLevel washerFluidLevel;
    TireStateProperty[] tireStates;
    Float batteryVoltage;
    Float adBlueLevel;
    Integer distanceDrivenSinceReset;
    Integer distanceDrivenSinceEngineStart;
    Float fuelVolume;

    /**
     *
     * @return The car mileage (odometer) in km
     */
    public Integer getMileage() {
        return mileage;
    }

    /**
     *
     * @return The engine oil temperature in Celsius, whereas can be negative
     */
    public Integer getOilTemperature() {
        return oilTemperature;
    }

    /**
     *
     * @return The car speed in km/h, whereas can be negative
     */
    public Integer getSpeed() {
        return speed;
    }
    
    /**
     *
     * @return The RPM of the engine
     */
    public Integer getRpm() {
        return rpm;
    }

    /**
     *
     * @return The Fuel level percentage between 0-100
     */
    public Float getFuelLevel() {
        return fuelLevel;
    }

    /**
     *
     * @return The estimated range
     */
    public Integer getRange() {
        return range;
    }

    /**
     *
     * @return Current fuel consumption
     */
    public Float getCurrentFuelConsumption() {
        return currentFuelConsumption;
    }

    /**
     *
     * @return Average fuel consumption for trip
     */
    public Float getTripFuelConsumption() {
        return tripFuelConsumption;
    }

    /**
     *
     * @return Washer fluid level
     */
    public WasherFluidLevel getWasherFluidLevel() {
        return washerFluidLevel;
    }

    /**
     *
     * @return The list of tire states that are available.
     */
    public TireStateProperty[] getTireStates() {
        return tireStates;
    }

    /**
     * Get the tire state at tire location.
     * @param location The location of the tire.
     * @return The tire state.
     */
    public TireStateProperty getTireState(TireStateProperty.Location location) {
        for (TireStateProperty state: getTireStates()) {
            if (state.getLocation() == location) return state;
        }

        return null;
    }

    /**
     *
     * @return The battery voltage
     */
    public Float getBatteryVoltage() {
        return batteryVoltage;
    }

    /**
     *
     * @return AdBlue level in liters
     */
    public Float getAdBlueLevel() {
        return adBlueLevel;
    }

    /**
     *
     * @return The distance driven in km since reset
     */
    public Integer getDistanceDrivenSinceReset() {
        return distanceDrivenSinceReset;
    }

    /**
     *
     * @return The distance driven in km since engine start
     */
    public Integer getDistanceDrivenSinceEngineStart() {
        return distanceDrivenSinceEngineStart;
    }

    /**
     *
     * @return The fuel volume measured in liters
     */
    public Float getFuelVolume() {
        return fuelVolume;
    }

    public DiagnosticsState(byte[] bytes) throws CommandParseException {
        super(bytes);
        ArrayList<TireStateProperty> tireStatesBuilder = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case MILEAGE_IDENTIFIER:
                    mileage = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case OIL_TEMPERATURE_IDENTIFIER:
                    oilTemperature = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case SPEED_IDENTIFIER:
                    speed = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case RPM_IDENTIFIER:
                    rpm = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case FUEL_LEVEL_IDENTIFIER:
                    fuelLevel = Property.getUnsignedInt(property.getValueBytes()) / 100f;
                    break;
                case RANGE_IDENTIFIER:
                    range = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case CURRENT_FUEL_CONSUMPTION_IDENTIFIER:
                    currentFuelConsumption = Property.getFloat(property.getValueBytes());
                    break;
                case TRIP_FUEL_CONSUMPTION_IDENTIFIER:
                    tripFuelConsumption = Property.getFloat(property.getValueBytes());
                    break;
                case WASHER_FLUID_LEVEL_IDENTIFIER:
                    washerFluidLevel = WasherFluidLevel.fromByte(property.getValueByte());
                    break;
                case TireStateProperty.identifier:
                    TireStateProperty state = new TireStateProperty(property.getValueBytes(), 0);
                    tireStatesBuilder.add(state);
                    break;
                case BATTERY_VOLTAGE_IDENTIFIER:
                    batteryVoltage = Property.getFloat(property.getValueBytes());
                    break;
                case AD_BLUE_LEVEL_IDENTIFIER:
                    adBlueLevel = Property.getFloat(property.getValueBytes());
                    break;
                case DISTANCE_DRIVEN_SINCE_RESET_IDENTIFIER:
                    distanceDrivenSinceReset = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case DISTANCE_DRIVEN_SINCE_ENGINE_START_IDENTIFIER:
                    distanceDrivenSinceEngineStart = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case FUEL_VOLUME_IDENTIFIER:
                    fuelVolume = Property.getFloat(property.getValueBytes());
                    break;
            }
        }

        tireStates = tireStatesBuilder.toArray(new TireStateProperty[tireStatesBuilder.size()]);
    }

    @Override public boolean isState() {
        return true;
    }

    private DiagnosticsState(Builder builder) {
        super(builder);
        mileage = builder.mileage;
        oilTemperature = builder.oilTemperature;
        speed = builder.speed;
        rpm = builder.rpm;
        range = builder.range;
        fuelLevel = builder.fuelLevel;
        currentFuelConsumption = builder.currentFuelConsumption;
        tripFuelConsumption = builder.tripFuelConsumption;
        washerFluidLevel = builder.washerFluidLevel;
        tireStates = builder.tireStates.toArray(new TireStateProperty[builder.tireStates.size()]);
        batteryVoltage = builder.batteryVoltage;
        adBlueLevel = builder.adBlueLevel;
        distanceDrivenSinceReset = builder.distanceDrivenSinceReset;
        distanceDrivenSinceEngineStart = builder.distanceDrivenSinceEngineStart;
        fuelVolume = builder.fuelVolume;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Integer mileage;
        private Integer oilTemperature;
        private Integer speed;
        private Integer rpm;
        private Float fuelLevel;
        private Integer range;
        private Float currentFuelConsumption;
        private Float tripFuelConsumption;
        private WasherFluidLevel washerFluidLevel;
        private List<TireStateProperty> tireStates = new ArrayList<>();
        private Float batteryVoltage;
        private Float adBlueLevel;
        private Integer distanceDrivenSinceReset;
        private Integer distanceDrivenSinceEngineStart;
        private Float fuelVolume;

        public Builder() {
            super(TYPE);
        }

        public Builder setMileage(Integer mileage) {
            this.mileage = mileage;
            addProperty(new IntegerProperty(MILEAGE_IDENTIFIER, mileage, 3));
            return this;
        }

        public Builder setOilTemperature(Integer oilTemperature) {
            this.oilTemperature = oilTemperature;
            addProperty(new IntegerProperty(OIL_TEMPERATURE_IDENTIFIER, oilTemperature, 2));
            return this;
        }

        public Builder setSpeed(Integer speed) {
            this.speed = speed;
            addProperty(new IntegerProperty(SPEED_IDENTIFIER, speed, 2));
            return this;
        }

        public Builder setRpm(Integer rpm) {
            this.rpm = rpm;
            addProperty(new IntegerProperty(RPM_IDENTIFIER, rpm, 2));
            return this;
        }

        public Builder setFuelLevel(Float fuelLevel) {
            this.fuelLevel = fuelLevel;
            addProperty(new IntegerProperty(FUEL_LEVEL_IDENTIFIER, Property.floatToIntPercentage(fuelLevel), 1));
            return this;
        }

        public Builder setRange(Integer range) {
            this.range = range;
            addProperty(new IntegerProperty(RANGE_IDENTIFIER, range, 2));
            return this;
        }

        public Builder setCurrentFuelConsumption(Float currentFuelConsumption) {
            this.currentFuelConsumption = currentFuelConsumption;
            addProperty(new FloatProperty(CURRENT_FUEL_CONSUMPTION_IDENTIFIER, currentFuelConsumption));
            return this;
        }

        public Builder setTripFuelConsumption(Float tripFuelConsumption) {
            this.tripFuelConsumption = tripFuelConsumption;
            addProperty(new FloatProperty(TRIP_FUEL_CONSUMPTION_IDENTIFIER, tripFuelConsumption));
            return this;
        }

        public Builder setWasherFluidLevel(WasherFluidLevel washerFluidLevel) {
            this.washerFluidLevel = washerFluidLevel;
            washerFluidLevel.setIdentifier(WASHER_FLUID_LEVEL_IDENTIFIER);
            addProperty(washerFluidLevel);
            return this;
        }

        public Builder setTireStates(TireStateProperty[] tireStates) {
            this.tireStates.addAll(Arrays.asList(tireStates));

            for (int i = 0; i < tireStates.length; i++) {
                addProperty(tireStates[i]);
            }

            return this;
        }

        public Builder addTireState(TireStateProperty tireState) {
            addProperty(tireState);
            tireStates.add(tireState);
            return this;
        }

        public Builder setBatteryVoltage(Float batteryVoltage) {
            this.batteryVoltage = batteryVoltage;
            addProperty(new FloatProperty(BATTERY_VOLTAGE_IDENTIFIER, batteryVoltage));
            return this;
        }

        public Builder setAdBlueLevel(Float adBlueLevel) {
            this.adBlueLevel = adBlueLevel;
            addProperty(new FloatProperty(AD_BLUE_LEVEL_IDENTIFIER, adBlueLevel));
            return this;
        }

        public Builder setDistanceDrivenSinceReset(Integer distanceDrivenSinceReset) {
            this.distanceDrivenSinceReset = distanceDrivenSinceReset;
            addProperty(new IntegerProperty(DISTANCE_DRIVEN_SINCE_RESET_IDENTIFIER, distanceDrivenSinceReset, 2));
            return this;
        }

        public Builder setDistanceDrivenSinceEngineStart(Integer distanceDrivenSinceEngineStart) {
            this.distanceDrivenSinceEngineStart = distanceDrivenSinceEngineStart;
            addProperty(new IntegerProperty(DISTANCE_DRIVEN_SINCE_ENGINE_START_IDENTIFIER, distanceDrivenSinceEngineStart, 2));
            return this;
        }

        public Builder setFuelVolume(Float fuelVolume) {
            this.fuelVolume = fuelVolume;
            addProperty(new FloatProperty(FUEL_VOLUME_IDENTIFIER, fuelVolume));
            return this;
        }

        public DiagnosticsState build() {
            return new DiagnosticsState(this);
        }
    }
}