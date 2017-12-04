package com.highmobility.autoapi.incoming;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Property;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 6/28/17.
 */
public class DiagnosticsState extends IncomingCommand {
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


    public DiagnosticsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length < 5) throw new CommandParseException();

        int size = Property.getInt(Arrays.copyOfRange(bytes, 3, 5));

        if (size > 0) {
            // TODO: refactor this to IEnumerator? that gives back identifer and value bytes until end
            // or identifier and value locations
            int step = 5;
            while (step + 3 <= bytes.length) {
                byte identifier = bytes[step];
                int propertySize = Property.getInt(bytes, step + 1, 2);
                int propertyValueStart = step + 3;
                switch (identifier) {
                    case 0x01:
                        mileage = Property.getInt(bytes, propertyValueStart, propertySize);
                    break;
                    case 0x02:
                        oilTemperature = Property.getInt(bytes, propertyValueStart, propertySize);
                        break;
                    case 0x03:
                        speed = Property.getInt(bytes, propertyValueStart, propertySize);
                        break;
                    case 0x04:
                        rpm = Property.getInt(bytes, propertyValueStart, propertySize);
                        break;
                    case 0x05:
                        fuelLevel = Property.getInt(bytes, propertyValueStart, propertySize) / 100f;
                        break;
                    case 0x06:
                        range = Property.getInt(bytes, propertyValueStart, propertySize);
                        break;
                    case 0x07:
                        currentFuelConsumption = Property.getFloat(bytes, propertyValueStart);
                        break;
                    case 0x08:
                        tripFuelConsumption = Property.getFloat(bytes, propertyValueStart);
                        break;
                    case 0x09:
                        washerFluidLevel = WasherFluidLevel.fromByte(bytes[propertyValueStart]);
                        break;
                    case 0x0a:
                        if (tireStates == null) tireStates = new HashSet<>();
                        TireState state = new TireState(bytes, propertyValueStart);
                        tireStates.add(state);
                        break;
                    default:
                        parseBaseProperty(propertyValueStart, propertySize);
                    break;
                }

                step += 3 + propertySize;
            }
        }

        tireStates = Collections.unmodifiableSet(tireStates);
    }
}
