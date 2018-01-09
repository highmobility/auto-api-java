package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This message is sent when a Get Diagnostics State message is received by the car. The new status
 * is included in the message payload and may be the result of user, device or car triggered action.
 */
public class DiagnosticsState extends Command {
    public static final Type TYPE = new Type(Identifier.DIAGNOSTICS, 0x01);

    public enum WasherFluidLevel {
        LOW((byte)0x00),
        FULL((byte)0x01);

        public static WasherFluidLevel fromByte(byte value) throws CommandParseException {
            WasherFluidLevel[] values = WasherFluidLevel.values();

            for (int i = 0; i < values.length; i++) {
                WasherFluidLevel capability = values[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        WasherFluidLevel(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    Integer mileage;
    Integer oilTemperature;
    Integer speed;
    Integer rpm;
    Integer range;
    Float fuelLevel;
    Float currentFuelConsumption;
    Float tripFuelConsumption;
    WasherFluidLevel washerFluidLevel;
    Set<TireState> tireStates;

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
     * @return The estimated range
     */
    public Integer getRange() {
        return range;
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
    public Set<TireState> getTireStates() {
        return tireStates;
    }

    /**
     * Get the tire state at tire location.
     * @param location The location of the tire.
     * @return The tire state.
     */
    public TireState getTireState(TireState.Location location) {
        for (TireState state: getTireStates()) {
            if (state.getLocation() == location) return state;
        }

        return null;
    }

    public DiagnosticsState(byte[] bytes) throws CommandParseException {
        super(bytes);

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
                    if (tireStates == null) tireStates = new HashSet<>();
                    TireState state = new TireState(property.getValueBytes(), 0);
                    tireStates.add(state);
                    break;
            }
        }

        tireStates = Collections.unmodifiableSet(tireStates);
    }
}