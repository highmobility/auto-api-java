/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.autoapi.value.measurement.Angle;
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.value.Bytes;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Vehicle Location capability
 */
public class VehicleLocation {
    public static final int IDENTIFIER = Identifier.VEHICLE_LOCATION;

    public static final byte PROPERTY_COORDINATES = 0x04;
    public static final byte PROPERTY_HEADING = 0x05;
    public static final byte PROPERTY_ALTITUDE = 0x06;
    public static final byte PROPERTY_PRECISION = 0x07;
    public static final byte PROPERTY_GPS_SOURCE = 0x08;
    public static final byte PROPERTY_GPS_SIGNAL_STRENGTH = 0x09;

    /**
     * Get Vehicle Location property availability information
     */
    public static class GetVehicleLocationAvailability extends GetAvailabilityCommand {
        /**
         * Get every Vehicle Location property availability
         */
        public GetVehicleLocationAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Vehicle Location property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetVehicleLocationAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Vehicle Location property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetVehicleLocationAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetVehicleLocationAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get vehicle location
     */
    public static class GetVehicleLocation extends GetCommand<State> {
        /**
         * Get all Vehicle Location properties
         */
        public GetVehicleLocation() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Vehicle Location properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetVehicleLocation(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Vehicle Location properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetVehicleLocation(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetVehicleLocation(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Vehicle Location properties
     * 
     * @deprecated use {@link GetVehicleLocation#GetVehicleLocation(byte...)} instead
     */
    @Deprecated
    public static class GetVehicleLocationProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetVehicleLocationProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetVehicleLocationProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetVehicleLocationProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The vehicle location state
     */
    public static class State extends SetCommand {
        Property<Coordinates> coordinates = new Property<>(Coordinates.class, PROPERTY_COORDINATES);
        Property<Angle> heading = new Property<>(Angle.class, PROPERTY_HEADING);
        Property<Length> altitude = new Property<>(Length.class, PROPERTY_ALTITUDE);
        Property<Length> precision = new Property<>(Length.class, PROPERTY_PRECISION);
        Property<GpsSource> gpsSource = new Property<>(GpsSource.class, PROPERTY_GPS_SOURCE);
        Property<Double> gpsSignalStrength = new Property<>(Double.class, PROPERTY_GPS_SIGNAL_STRENGTH);
    
        /**
         * @return The coordinates
         */
        public Property<Coordinates> getCoordinates() {
            return coordinates;
        }
    
        /**
         * @return Heading angle
         */
        public Property<Angle> getHeading() {
            return heading;
        }
    
        /**
         * @return Altitude above the WGS 84 reference ellipsoid
         */
        public Property<Length> getAltitude() {
            return altitude;
        }
    
        /**
         * @return The precision
         */
        public Property<Length> getPrecision() {
            return precision;
        }
    
        /**
         * @return Type of GPS source
         */
        public Property<GpsSource> getGpsSource() {
            return gpsSource;
        }
    
        /**
         * @return GPS signal strength percentage between 0.0-1.0
         */
        public Property<Double> getGpsSignalStrength() {
            return gpsSignalStrength;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_COORDINATES: return coordinates.update(p);
                        case PROPERTY_HEADING: return heading.update(p);
                        case PROPERTY_ALTITUDE: return altitude.update(p);
                        case PROPERTY_PRECISION: return precision.update(p);
                        case PROPERTY_GPS_SOURCE: return gpsSource.update(p);
                        case PROPERTY_GPS_SIGNAL_STRENGTH: return gpsSignalStrength.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            coordinates = builder.coordinates;
            heading = builder.heading;
            altitude = builder.altitude;
            precision = builder.precision;
            gpsSource = builder.gpsSource;
            gpsSignalStrength = builder.gpsSignalStrength;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<Coordinates> coordinates;
            private Property<Angle> heading;
            private Property<Length> altitude;
            private Property<Length> precision;
            private Property<GpsSource> gpsSource;
            private Property<Double> gpsSignalStrength;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param coordinates The coordinates
             * @return The builder
             */
            public Builder setCoordinates(Property<Coordinates> coordinates) {
                this.coordinates = coordinates.setIdentifier(PROPERTY_COORDINATES);
                addProperty(this.coordinates);
                return this;
            }
            
            /**
             * @param heading Heading angle
             * @return The builder
             */
            public Builder setHeading(Property<Angle> heading) {
                this.heading = heading.setIdentifier(PROPERTY_HEADING);
                addProperty(this.heading);
                return this;
            }
            
            /**
             * @param altitude Altitude above the WGS 84 reference ellipsoid
             * @return The builder
             */
            public Builder setAltitude(Property<Length> altitude) {
                this.altitude = altitude.setIdentifier(PROPERTY_ALTITUDE);
                addProperty(this.altitude);
                return this;
            }
            
            /**
             * @param precision The precision
             * @return The builder
             */
            public Builder setPrecision(Property<Length> precision) {
                this.precision = precision.setIdentifier(PROPERTY_PRECISION);
                addProperty(this.precision);
                return this;
            }
            
            /**
             * @param gpsSource Type of GPS source
             * @return The builder
             */
            public Builder setGpsSource(Property<GpsSource> gpsSource) {
                this.gpsSource = gpsSource.setIdentifier(PROPERTY_GPS_SOURCE);
                addProperty(this.gpsSource);
                return this;
            }
            
            /**
             * @param gpsSignalStrength GPS signal strength percentage between 0.0-1.0
             * @return The builder
             */
            public Builder setGpsSignalStrength(Property<Double> gpsSignalStrength) {
                this.gpsSignalStrength = gpsSignalStrength.setIdentifier(PROPERTY_GPS_SIGNAL_STRENGTH);
                addProperty(this.gpsSignalStrength);
                return this;
            }
        }
    }

    public enum GpsSource implements ByteEnum {
        DEAD_RECKONING((byte) 0x00),
        REAL((byte) 0x01),
        NONE((byte) 0x02);
    
        public static GpsSource fromByte(byte byteValue) throws CommandParseException {
            GpsSource[] values = GpsSource.values();
    
            for (int i = 0; i < values.length; i++) {
                GpsSource state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(GpsSource.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        GpsSource(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}