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
import com.highmobility.value.Bytes;
import javax.annotation.Nullable;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

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
    public static final byte PROPERTY_SUNROOF_RAIN_EVENT = 0x06;

    /**
     * Get Rooftop Control property availability information
     */
    public static class GetRooftopStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Rooftop Control property availability
         */
        public GetRooftopStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Rooftop Control property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetRooftopStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Rooftop Control property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetRooftopStateAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetRooftopStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get rooftop state
     */
    public static class GetRooftopState extends GetCommand<State> {
        /**
         * Get all Rooftop Control properties
         */
        public GetRooftopState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Rooftop Control properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetRooftopState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Rooftop Control properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetRooftopState(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetRooftopState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Rooftop Control properties
     * 
     * @deprecated use {@link GetRooftopState#GetRooftopState(byte...)} instead
     */
    @Deprecated
    public static class GetRooftopProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetRooftopProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetRooftopProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetRooftopProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Control rooftop
     */
    public static class ControlRooftop extends SetCommand {
        Property<Double> dimming = new Property<>(Double.class, PROPERTY_DIMMING);
        Property<Double> position = new Property<>(Double.class, PROPERTY_POSITION);
        Property<ConvertibleRoofState> convertibleRoofState = new Property<>(ConvertibleRoofState.class, PROPERTY_CONVERTIBLE_ROOF_STATE);
        Property<SunroofTiltState> sunroofTiltState = new Property<>(SunroofTiltState.class, PROPERTY_SUNROOF_TILT_STATE);
        Property<SunroofState> sunroofState = new Property<>(SunroofState.class, PROPERTY_SUNROOF_STATE);
    
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
    
        ControlRooftop(byte[] bytes) throws CommandParseException, PropertyParseException {
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
            if (this.dimming.getValue() == null && this.position.getValue() == null && this.convertibleRoofState.getValue() == null && this.sunroofTiltState.getValue() == null && this.sunroofState.getValue() == null) {
                throw new PropertyParseException(optionalPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The rooftop control state
     */
    public static class State extends SetCommand {
        Property<Double> dimming = new Property<>(Double.class, PROPERTY_DIMMING);
        Property<Double> position = new Property<>(Double.class, PROPERTY_POSITION);
        Property<ConvertibleRoofState> convertibleRoofState = new Property<>(ConvertibleRoofState.class, PROPERTY_CONVERTIBLE_ROOF_STATE);
        Property<SunroofTiltState> sunroofTiltState = new Property<>(SunroofTiltState.class, PROPERTY_SUNROOF_TILT_STATE);
        Property<SunroofState> sunroofState = new Property<>(SunroofState.class, PROPERTY_SUNROOF_STATE);
        Property<SunroofRainEvent> sunroofRainEvent = new Property<>(SunroofRainEvent.class, PROPERTY_SUNROOF_RAIN_EVENT);
    
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
    
        /**
         * @return Sunroof event happened in case of rain
         */
        public Property<SunroofRainEvent> getSunroofRainEvent() {
            return sunroofRainEvent;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_DIMMING: return dimming.update(p);
                        case PROPERTY_POSITION: return position.update(p);
                        case PROPERTY_CONVERTIBLE_ROOF_STATE: return convertibleRoofState.update(p);
                        case PROPERTY_SUNROOF_TILT_STATE: return sunroofTiltState.update(p);
                        case PROPERTY_SUNROOF_STATE: return sunroofState.update(p);
                        case PROPERTY_SUNROOF_RAIN_EVENT: return sunroofRainEvent.update(p);
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
            sunroofRainEvent = builder.sunroofRainEvent;
        }
    
        public static final class Builder extends SetCommand.Builder<Builder> {
            private Property<Double> dimming;
            private Property<Double> position;
            private Property<ConvertibleRoofState> convertibleRoofState;
            private Property<SunroofTiltState> sunroofTiltState;
            private Property<SunroofState> sunroofState;
            private Property<SunroofRainEvent> sunroofRainEvent;
    
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
            
            /**
             * @param sunroofRainEvent Sunroof event happened in case of rain
             * @return The builder
             */
            public Builder setSunroofRainEvent(Property<SunroofRainEvent> sunroofRainEvent) {
                this.sunroofRainEvent = sunroofRainEvent.setIdentifier(PROPERTY_SUNROOF_RAIN_EVENT);
                addProperty(this.sunroofRainEvent);
                return this;
            }
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
    
            throw new CommandParseException(
                enumValueDoesNotExist(ConvertibleRoofState.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
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
    
            throw new CommandParseException(
                enumValueDoesNotExist(SunroofTiltState.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
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
    
            throw new CommandParseException(
                enumValueDoesNotExist(SunroofState.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        SunroofState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum SunroofRainEvent implements ByteEnum {
        NO_EVENT((byte) 0x00),
        IN_STROKE_POSITION_BECAUSE_OF_RAIN((byte) 0x01),
        AUTOMATICALLY_IN_STROKE_POSITION((byte) 0x02);
    
        public static SunroofRainEvent fromByte(byte byteValue) throws CommandParseException {
            SunroofRainEvent[] values = SunroofRainEvent.values();
    
            for (int i = 0; i < values.length; i++) {
                SunroofRainEvent state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(SunroofRainEvent.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        SunroofRainEvent(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}