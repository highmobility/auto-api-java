// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;

public class RooftopControlState extends Command {
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

    RooftopControlState(byte[] bytes) {
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

    public static final class Builder extends Command.Builder {
        private Property<Double> dimming;
        private Property<Double> position;
        private Property<ConvertibleRoofState> convertibleRoofState;
        private Property<SunroofTiltState> sunroofTiltState;
        private Property<SunroofState> sunroofState;

        public Builder() {
            super(Identifier.ROOFTOP_CONTROL);
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
            addProperty(dimming);
            return this;
        }
        
        /**
         * @param position 100% is fully open, 0% is closed
         * @return The builder
         */
        public Builder setPosition(Property<Double> position) {
            this.position = position.setIdentifier(0x02);
            addProperty(position);
            return this;
        }
        
        /**
         * @param convertibleRoofState The convertible roof state
         * @return The builder
         */
        public Builder setConvertibleRoofState(Property<ConvertibleRoofState> convertibleRoofState) {
            this.convertibleRoofState = convertibleRoofState.setIdentifier(0x03);
            addProperty(convertibleRoofState);
            return this;
        }
        
        /**
         * @param sunroofTiltState The sunroof tilt state
         * @return The builder
         */
        public Builder setSunroofTiltState(Property<SunroofTiltState> sunroofTiltState) {
            this.sunroofTiltState = sunroofTiltState.setIdentifier(0x04);
            addProperty(sunroofTiltState);
            return this;
        }
        
        /**
         * @param sunroofState The sunroof state
         * @return The builder
         */
        public Builder setSunroofState(Property<SunroofState> sunroofState) {
            this.sunroofState = sunroofState.setIdentifier(0x05);
            addProperty(sunroofState);
            return this;
        }
    }

    public enum ConvertibleRoofState {
        CLOSED((byte)0x00),
        OPEN((byte)0x01),
        EMERGENCY_LOCKED((byte)0x02),
        CLOSED_SECURED((byte)0x03),
        OPEN_SECURED((byte)0x04),
        HARD_TOP_MOUNTED((byte)0x05),
        INTERMEDIATE_POSITION((byte)0x06),
        LOADING_POSITION((byte)0x07),
        LOADING_POSITION_IMMEDIATE((byte)0x08);
    
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
    
        public byte getByte() {
            return value;
        }
    }

    public enum SunroofTiltState {
        CLOSED((byte)0x00),
        TILTED((byte)0x01),
        HALF_TILTED((byte)0x02);
    
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
    
        public byte getByte() {
            return value;
        }
    }

    public enum SunroofState {
        CLOSED((byte)0x00),
        OPEN((byte)0x01),
        INTERMEDIATE((byte)0x02);
    
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
    
        public byte getByte() {
            return value;
        }
    }
}