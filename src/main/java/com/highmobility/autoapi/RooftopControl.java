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

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;
import javax.annotation.Nullable;

/**
 * The Rooftop Control capability
 */
public class RooftopControl {
    public static final int IDENTIFIER = Identifier.ROOFTOP_CONTROL;

    public static final byte PROPERTY_DIMMING = 0x01;
    public static final byte PROPERTY_POSITION = 0x02;
    public static final byte PROPERTY_CONVERTIBLE_ROOF_STATE = 0x03;
    public static final byte PROPERTY_SUNROOF_TILT_STATE = 0x04;
    public static final byte PROPERTY_SUNROOF_STATE = 0x05;

    /**
     * Get rooftop state
     */
    public static class GetRooftopState extends GetCommand {
        public GetRooftopState() {
            super(IDENTIFIER);
        }
    
        GetRooftopState(byte[] bytes) {
            super(bytes);
        }
    }
    
    /**
     * Get specific rooftop control properties
     */
    public static class GetRooftopProperties extends GetCommand {
        Bytes propertyIdentifiers;
    
        /**
         * @return The property identifiers.
         */
        public Bytes getPropertyIdentifiers() {
            return propertyIdentifiers;
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetRooftopProperties(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers.getByteArray());
            this.propertyIdentifiers = propertyIdentifiers;
        }
    
        GetRooftopProperties(byte[] bytes) {
            super(bytes);
            propertyIdentifiers = getRange(3, getLength());
        }
    }

    /**
     * The rooftop control state
     */
    public static class State extends SetCommand {
        Property<Double> dimming = new Property(Double.class, PROPERTY_DIMMING);
        Property<Double> position = new Property(Double.class, PROPERTY_POSITION);
        Property<ConvertibleRoofState> convertibleRoofState = new Property(ConvertibleRoofState.class, PROPERTY_CONVERTIBLE_ROOF_STATE);
        Property<SunroofTiltState> sunroofTiltState = new Property(SunroofTiltState.class, PROPERTY_SUNROOF_TILT_STATE);
        Property<SunroofState> sunroofState = new Property(SunroofState.class, PROPERTY_SUNROOF_STATE);
    
        /**
         * @return 1.0 (100%) is opaque, 0.0 (0%) is transparent
         */
        public Property<Double> getDimming() {
            return dimming;
        }
    
        /**
         * @return 1.0 (100%) is fully open, 0.0 (0%) is closed
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
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DIMMING: return dimming.update(p);
                        case PROPERTY_POSITION: return position.update(p);
                        case PROPERTY_CONVERTIBLE_ROOF_STATE: return convertibleRoofState.update(p);
                        case PROPERTY_SUNROOF_TILT_STATE: return sunroofTiltState.update(p);
                        case PROPERTY_SUNROOF_STATE: return sunroofState.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
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
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param dimming 1.0 (100%) is opaque, 0.0 (0%) is transparent
             * @return The builder
             */
            public Builder setDimming(Property<Double> dimming) {
                this.dimming = dimming.setIdentifier(PROPERTY_DIMMING);
                addProperty(this.dimming);
                return this;
            }
            
            /**
             * @param position 1.0 (100%) is fully open, 0.0 (0%) is closed
             * @return The builder
             */
            public Builder setPosition(Property<Double> position) {
                this.position = position.setIdentifier(PROPERTY_POSITION);
                addProperty(this.position);
                return this;
            }
            
            /**
             * @param convertibleRoofState The convertible roof state
             * @return The builder
             */
            public Builder setConvertibleRoofState(Property<ConvertibleRoofState> convertibleRoofState) {
                this.convertibleRoofState = convertibleRoofState.setIdentifier(PROPERTY_CONVERTIBLE_ROOF_STATE);
                addProperty(this.convertibleRoofState);
                return this;
            }
            
            /**
             * @param sunroofTiltState The sunroof tilt state
             * @return The builder
             */
            public Builder setSunroofTiltState(Property<SunroofTiltState> sunroofTiltState) {
                this.sunroofTiltState = sunroofTiltState.setIdentifier(PROPERTY_SUNROOF_TILT_STATE);
                addProperty(this.sunroofTiltState);
                return this;
            }
            
            /**
             * @param sunroofState The sunroof state
             * @return The builder
             */
            public Builder setSunroofState(Property<SunroofState> sunroofState) {
                this.sunroofState = sunroofState.setIdentifier(PROPERTY_SUNROOF_STATE);
                addProperty(this.sunroofState);
                return this;
            }
        }
    }

    /**
     * Control rooftop
     */
    public static class ControlRooftop extends SetCommand {
        Property<Double> dimming = new Property(Double.class, PROPERTY_DIMMING);
        Property<Double> position = new Property(Double.class, PROPERTY_POSITION);
        Property<ConvertibleRoofState> convertibleRoofState = new Property(ConvertibleRoofState.class, PROPERTY_CONVERTIBLE_ROOF_STATE);
        Property<SunroofTiltState> sunroofTiltState = new Property(SunroofTiltState.class, PROPERTY_SUNROOF_TILT_STATE);
        Property<SunroofState> sunroofState = new Property(SunroofState.class, PROPERTY_SUNROOF_STATE);
    
        /**
         * @return The dimming
         */
        public Property<Double> getDimming() {
            return dimming;
        }
        
        /**
         * @return The position
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
        
        /**
         * Control rooftop
         *
         * @param dimming 1.0 (100%) is opaque, 0.0 (0%) is transparent
         * @param position 1.0 (100%) is fully open, 0.0 (0%) is closed
         * @param convertibleRoofState The convertible roof state
         * @param sunroofTiltState The sunroof tilt state
         * @param sunroofState The sunroof state
         */
        public ControlRooftop(@Nullable Double dimming, @Nullable Double position, @Nullable ConvertibleRoofState convertibleRoofState, @Nullable SunroofTiltState sunroofTiltState, @Nullable SunroofState sunroofState) {
            super(IDENTIFIER);
        
            addProperty(this.dimming.update(dimming));
            addProperty(this.position.update(position));
            if (convertibleRoofState == ConvertibleRoofState.EMERGENCY_LOCKED ||
                convertibleRoofState == ConvertibleRoofState.CLOSED_SECURED ||
                convertibleRoofState == ConvertibleRoofState.OPEN_SECURED ||
                convertibleRoofState == ConvertibleRoofState.HARD_TOP_MOUNTED ||
                convertibleRoofState == ConvertibleRoofState.INTERMEDIATE_POSITION ||
                convertibleRoofState == ConvertibleRoofState.LOADING_POSITION ||
                convertibleRoofState == ConvertibleRoofState.LOADING_POSITION_IMMEDIATE) throw new IllegalArgumentException();
        
            addProperty(this.convertibleRoofState.update(convertibleRoofState));
            addProperty(this.sunroofTiltState.update(sunroofTiltState));
            addProperty(this.sunroofState.update(sunroofState));
            if (this.dimming.getValue() == null && this.position.getValue() == null && this.convertibleRoofState.getValue() == null && this.sunroofTiltState.getValue() == null && this.sunroofState.getValue() == null) throw new IllegalArgumentException();
            createBytes();
        }
    
        ControlRooftop(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DIMMING: return dimming.update(p);
                        case PROPERTY_POSITION: return position.update(p);
                        case PROPERTY_CONVERTIBLE_ROOF_STATE: return convertibleRoofState.update(p);
                        case PROPERTY_SUNROOF_TILT_STATE: return sunroofTiltState.update(p);
                        case PROPERTY_SUNROOF_STATE: return sunroofState.update(p);
                    }
                    return null;
                });
            }
            if (this.dimming.getValue() == null && this.position.getValue() == null && this.convertibleRoofState.getValue() == null && this.sunroofTiltState.getValue() == null && this.sunroofState.getValue() == null) throw new NoPropertiesException();
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