package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

public class TireState {
    /**
     * The tire location
     */
    public enum Location {
        FRONT_LEFT((byte)0x00),
        FRONT_RIGHT((byte)0x01),
        REAR_RIGHT((byte)0x02),
        REAR_LEFT((byte)0x03);

        public static Location fromByte(byte value) throws CommandParseException {
            Location[] values = Location.values();

            for (int i = 0; i < values.length; i++) {
                Location value1 = values[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Location(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }

    Location location;
    Float pressure;
    Float temperature;
    Integer rpm;

    /**
     *
     * @return The location of the tire.
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @return The tire pressure in BAR.
     */
    public Float getPressure() {
        return pressure;
    }

    /**
     *
     * @return Tire temperature in celsius.
     */
    public Float getTemperature() {
        return temperature;
    }

    /**
     *
     * @return The RPM measured at this wheel.
     */
    public Integer getRpm() {
        return rpm;
    }

    TireState(byte[] bytes, int startingFrom) throws CommandParseException {
        location = Location.fromByte(bytes[startingFrom]);
        pressure = Property.getFloat(bytes, startingFrom + 1);
        temperature = Property.getFloat(bytes, startingFrom + 5);
        rpm = Property.getUnsignedInt(bytes, startingFrom + 9, 2);
    }
}