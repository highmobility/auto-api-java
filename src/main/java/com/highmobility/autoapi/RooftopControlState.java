/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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
import com.highmobility.autoapi.property.ByteEnum;

/**
 * The rooftop control state
 */
public class RooftopControlState extends SetCommand {
    public static final Identifier identifier = Identifier.ROOFTOP_CONTROL;

    Property<Double> dimming = new Property(Double.class, 0x01);
    Property<Double> position = new Property(Double.class, 0x02);
    Property<ConvertibleRoofState> convertibleRoofState = new Property(ConvertibleRoofState.class, 0x03);
    Property<SunroofTiltState> sunroofTiltState = new Property(SunroofTiltState.class, 0x04);
    Property<SunroofState> sunroofState = new Property(SunroofState.class, 0x05);

    /**
     * @return 100% is opaque, 0% is transparent
     */
    public Property<Double> getDimming() {
        return dimming;
    }

    /**
     * @return 100% is fully open, 0% is closed
     */
    public Property<Double> getPosition() {
        return position;
    }

    /**
     * @return The convertible roof state
     */
    public Property<ConvertibleRoofState> getConvertibleRoofState() {
        return convertibleRoofState;
    }

    /**
     * @return The sunroof tilt state
     */
    public Property<SunroofTiltState> getSunroofTiltState() {
        return sunroofTiltState;
    }

    /**
     * @return The sunroof state
     */
    public Property<SunroofState> getSunroofState() {
        return sunroofState;
    }

    RooftopControlState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return dimming.update(p);
                    case 0x02: return position.update(p);
                    case 0x03: return convertibleRoofState.update(p);
                    case 0x04: return sunroofTiltState.update(p);
                    case 0x05: return sunroofState.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private RooftopControlState(Builder builder) {
        super(builder);

        dimming = builder.dimming;
        position = builder.position;
        convertibleRoofState = builder.convertibleRoofState;
        sunroofTiltState = builder.sunroofTiltState;
        sunroofState = builder.sunroofState;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<Double> dimming;
        private Property<Double> position;
        private Property<ConvertibleRoofState> convertibleRoofState;
        private Property<SunroofTiltState> sunroofTiltState;
        private Property<SunroofState> sunroofState;

        public Builder() {
            super(identifier);
        }

        public RooftopControlState build() {
            return new RooftopControlState(this);
        }

        /**
         * @param dimming 100% is opaque, 0% is transparent
         * @return The builder
         */
        public Builder setDimming(Property<Double> dimming) {
            this.dimming = dimming.setIdentifier(0x01);
            addProperty(this.dimming);
            return this;
        }
        
        /**
         * @param position 100% is fully open, 0% is closed
         * @return The builder
         */
        public Builder setPosition(Property<Double> position) {
            this.position = position.setIdentifier(0x02);
            addProperty(this.position);
            return this;
        }
        
        /**
         * @param convertibleRoofState The convertible roof state
         * @return The builder
         */
        public Builder setConvertibleRoofState(Property<ConvertibleRoofState> convertibleRoofState) {
            this.convertibleRoofState = convertibleRoofState.setIdentifier(0x03);
            addProperty(this.convertibleRoofState);
            return this;
        }
        
        /**
         * @param sunroofTiltState The sunroof tilt state
         * @return The builder
         */
        public Builder setSunroofTiltState(Property<SunroofTiltState> sunroofTiltState) {
            this.sunroofTiltState = sunroofTiltState.setIdentifier(0x04);
            addProperty(this.sunroofTiltState);
            return this;
        }
        
        /**
         * @param sunroofState The sunroof state
         * @return The builder
         */
        public Builder setSunroofState(Property<SunroofState> sunroofState) {
            this.sunroofState = sunroofState.setIdentifier(0x05);
            addProperty(this.sunroofState);
            return this;
        }
    }

    public enum ConvertibleRoofState implements ByteEnum {
        CLOSED((byte) 0x00),
        OPEN((byte) 0x01),
        EMERGENCY_LOCKED((byte) 0x02),
        CLOSED_SECURED((byte) 0x03),
        OPEN_SECURED((byte) 0x04),
        HARD_TOP_MOUNTED((byte) 0x05),
        INTERMEDIATE_POSITION((byte) 0x06),
        LOADING_POSITION((byte) 0x07),
        LOADING_POSITION_IMMEDIATE((byte) 0x08);
    
        public static ConvertibleRoofState fromByte(byte byteValue) throws CommandParseException {
            ConvertibleRoofState[] values = ConvertibleRoofState.values();
    
            for (int i = 0; i < values.length; i++) {
                ConvertibleRoofState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        ConvertibleRoofState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum SunroofTiltState implements ByteEnum {
        CLOSED((byte) 0x00),
        TILTED((byte) 0x01),
        HALF_TILTED((byte) 0x02);
    
        public static SunroofTiltState fromByte(byte byteValue) throws CommandParseException {
            SunroofTiltState[] values = SunroofTiltState.values();
    
            for (int i = 0; i < values.length; i++) {
                SunroofTiltState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        SunroofTiltState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum SunroofState implements ByteEnum {
        CLOSED((byte) 0x00),
        OPEN((byte) 0x01),
        INTERMEDIATE((byte) 0x02);
    
        public static SunroofState fromByte(byte byteValue) throws CommandParseException {
            SunroofState[] values = SunroofState.values();
    
            for (int i = 0; i < values.length; i++) {
                SunroofState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        SunroofState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}