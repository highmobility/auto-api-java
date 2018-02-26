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
public class DiagnosticsState extends CommandWithExistingProperties {
    public static final Type TYPE = new Type(Identifier.DIAGNOSTICS, 0x01);

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

    /**
     *
     * @return The car mileage (odometer) in km
     */
    public Integer getMileage() {
        return mileage;
    }

    /**
     *
     * @return Engine oil temperature in Celsius, whereas can be negative
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
     * @return RPM of the Engine
     */
    public Integer getRpm() {
        return rpm;
    }

    /**
     *
     * @return Fuel level percentage between 0-100
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

    public DiagnosticsState(byte[] bytes) throws CommandParseException {
        super(bytes);
        ArrayList<TireStateProperty> tireStatesBuilder = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    mileage = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case 0x02:
                    oilTemperature = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case 0x03:
                    speed = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case 0x04:
                    rpm = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case 0x05:
                    fuelLevel = Property.getUnsignedInt(property.getValueBytes()) / 100f;
                    break;
                case 0x06:
                    range = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case 0x07:
                    currentFuelConsumption = Property.getFloat(property.getValueBytes());
                    break;
                case 0x08:
                    tripFuelConsumption = Property.getFloat(property.getValueBytes());
                    break;
                case 0x09:
                    washerFluidLevel = WasherFluidLevel.fromByte(property.getValueByte());
                    break;
                case 0x0a:
                    TireStateProperty state = new TireStateProperty(property.getValueBytes(), 0);
                    tireStatesBuilder.add(state);
                    break;
                case 0x0b:
                    batteryVoltage = Property.getFloat(property.getValueBytes());
                    break;
                case 0x0c:
                    adBlueLevel = Property.getFloat(property.getValueBytes());
                    break;
                case 0x0d:
                    distanceDrivenSinceReset = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case 0x0e:
                    distanceDrivenSinceEngineStart = Property.getUnsignedInt(property.getValueBytes());
                    break;
            }
        }

        tireStates = tireStatesBuilder.toArray(new TireStateProperty[tireStatesBuilder.size()]);
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

        public Builder() {
            super(TYPE);
        }

        public Builder setMileage(Integer mileage) {
            this.mileage = mileage;
            addProperty(new IntegerProperty((byte) 0x01, mileage, 3));
            return this;
        }

        public Builder setOilTemperature(Integer oilTemperature) {
            this.oilTemperature = oilTemperature;
            addProperty(new IntegerProperty((byte) 0x02, oilTemperature, 2));
            return this;
        }

        public Builder setSpeed(Integer speed) {
            this.speed = speed;
            addProperty(new IntegerProperty((byte) 0x03, speed, 2));
            return this;
        }

        public Builder setRpm(Integer rpm) {
            this.rpm = rpm;
            addProperty(new IntegerProperty((byte) 0x04, rpm, 2));
            return this;
        }

        public Builder setFuelLevel(Float fuelLevel) {
            this.fuelLevel = fuelLevel;
            addProperty(new IntegerProperty((byte) 0x05, Property.floatToIntPercentage(fuelLevel), 1));
            return this;
        }

        public Builder setRange(Integer range) {
            this.range = range;
            addProperty(new IntegerProperty((byte) 0x06, range, 2));
            return this;
        }

        public Builder setCurrentFuelConsumption(Float currentFuelConsumption) {
            this.currentFuelConsumption = currentFuelConsumption;
            addProperty(new FloatProperty((byte) 0x07, currentFuelConsumption));
            return this;
        }

        public Builder setTripFuelConsumption(Float tripFuelConsumption) {
            this.tripFuelConsumption = tripFuelConsumption;
            addProperty(new FloatProperty((byte) 0x08, tripFuelConsumption));
            return this;
        }

        public Builder setWasherFluidLevel(WasherFluidLevel washerFluidLevel) {
            this.washerFluidLevel = washerFluidLevel;
            washerFluidLevel.setIdentifier((byte) 0x09);
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
            addProperty(new FloatProperty((byte) 0x0b, batteryVoltage));
            return this;
        }

        public Builder setAdBlueLevel(Float adBlueLevel) {
            this.adBlueLevel = adBlueLevel;
            addProperty(new FloatProperty((byte) 0x0c, adBlueLevel));
            return this;
        }

        public Builder setDistanceDrivenSinceReset(Integer distanceDrivenSinceReset) {
            this.distanceDrivenSinceReset = distanceDrivenSinceReset;
            addProperty(new IntegerProperty((byte) 0x0d, distanceDrivenSinceReset, 2));
            return this;
        }

        public Builder setDistanceDrivenSinceEngineStart(Integer distanceDrivenSinceEngineStart) {
            this.distanceDrivenSinceEngineStart = distanceDrivenSinceEngineStart;
            addProperty(new IntegerProperty((byte) 0x0e, distanceDrivenSinceEngineStart, 2));
            return this;
        }

        public DiagnosticsState build() {
            return new DiagnosticsState(this);
        }
    }
}