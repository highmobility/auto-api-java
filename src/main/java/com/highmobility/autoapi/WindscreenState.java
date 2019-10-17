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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.value.Zone;
import java.util.Calendar;

/**
 * The windscreen state
 */
public class WindscreenState extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.WINDSCREEN;

    public static final byte IDENTIFIER_WIPERS = 0x01;
    public static final byte IDENTIFIER_WIPERS_INTENSITY = 0x02;
    public static final byte IDENTIFIER_WINDSCREEN_DAMAGE = 0x03;
    public static final byte IDENTIFIER_WINDSCREEN_ZONE_MATRIX = 0x04;
    public static final byte IDENTIFIER_WINDSCREEN_DAMAGE_ZONE = 0x05;
    public static final byte IDENTIFIER_WINDSCREEN_NEEDS_REPLACEMENT = 0x06;
    public static final byte IDENTIFIER_WINDSCREEN_DAMAGE_CONFIDENCE = 0x07;
    public static final byte IDENTIFIER_WINDSCREEN_DAMAGE_DETECTION_TIME = 0x08;

    Property<Wipers> wipers = new Property(Wipers.class, IDENTIFIER_WIPERS);
    Property<WipersIntensity> wipersIntensity = new Property(WipersIntensity.class, IDENTIFIER_WIPERS_INTENSITY);
    Property<WindscreenDamage> windscreenDamage = new Property(WindscreenDamage.class, IDENTIFIER_WINDSCREEN_DAMAGE);
    Property<Zone> windscreenZoneMatrix = new Property(Zone.class, IDENTIFIER_WINDSCREEN_ZONE_MATRIX);
    Property<Zone> windscreenDamageZone = new Property(Zone.class, IDENTIFIER_WINDSCREEN_DAMAGE_ZONE);
    Property<WindscreenNeedsReplacement> windscreenNeedsReplacement = new Property(WindscreenNeedsReplacement.class, IDENTIFIER_WINDSCREEN_NEEDS_REPLACEMENT);
    Property<Double> windscreenDamageConfidence = new Property(Double.class, IDENTIFIER_WINDSCREEN_DAMAGE_CONFIDENCE);
    Property<Calendar> windscreenDamageDetectionTime = new Property(Calendar.class, IDENTIFIER_WINDSCREEN_DAMAGE_DETECTION_TIME);

    /**
     * @return The wipers
     */
    public Property<Wipers> getWipers() {
        return wipers;
    }

    /**
     * @return The wipers intensity
     */
    public Property<WipersIntensity> getWipersIntensity() {
        return wipersIntensity;
    }

    /**
     * @return The windscreen damage
     */
    public Property<WindscreenDamage> getWindscreenDamage() {
        return windscreenDamage;
    }

    /**
     * @return Representing the size of the matrix, seen from the inside of the vehicle
     */
    public Property<Zone> getWindscreenZoneMatrix() {
        return windscreenZoneMatrix;
    }

    /**
     * @return Representing the position in the zone, seen from the inside of the vehicle (1-based index)
     */
    public Property<Zone> getWindscreenDamageZone() {
        return windscreenDamageZone;
    }

    /**
     * @return The windscreen needs replacement
     */
    public Property<WindscreenNeedsReplacement> getWindscreenNeedsReplacement() {
        return windscreenNeedsReplacement;
    }

    /**
     * @return Confidence of damage detection, 0% if no impact detected
     */
    public Property<Double> getWindscreenDamageConfidence() {
        return windscreenDamageConfidence;
    }

    /**
     * @return Milliseconds since UNIX Epoch time
     */
    public Property<Calendar> getWindscreenDamageDetectionTime() {
        return windscreenDamageDetectionTime;
    }

    WindscreenState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_WIPERS: return wipers.update(p);
                    case IDENTIFIER_WIPERS_INTENSITY: return wipersIntensity.update(p);
                    case IDENTIFIER_WINDSCREEN_DAMAGE: return windscreenDamage.update(p);
                    case IDENTIFIER_WINDSCREEN_ZONE_MATRIX: return windscreenZoneMatrix.update(p);
                    case IDENTIFIER_WINDSCREEN_DAMAGE_ZONE: return windscreenDamageZone.update(p);
                    case IDENTIFIER_WINDSCREEN_NEEDS_REPLACEMENT: return windscreenNeedsReplacement.update(p);
                    case IDENTIFIER_WINDSCREEN_DAMAGE_CONFIDENCE: return windscreenDamageConfidence.update(p);
                    case IDENTIFIER_WINDSCREEN_DAMAGE_DETECTION_TIME: return windscreenDamageDetectionTime.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private WindscreenState(Builder builder) {
        super(builder);

        wipers = builder.wipers;
        wipersIntensity = builder.wipersIntensity;
        windscreenDamage = builder.windscreenDamage;
        windscreenZoneMatrix = builder.windscreenZoneMatrix;
        windscreenDamageZone = builder.windscreenDamageZone;
        windscreenNeedsReplacement = builder.windscreenNeedsReplacement;
        windscreenDamageConfidence = builder.windscreenDamageConfidence;
        windscreenDamageDetectionTime = builder.windscreenDamageDetectionTime;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<Wipers> wipers;
        private Property<WipersIntensity> wipersIntensity;
        private Property<WindscreenDamage> windscreenDamage;
        private Property<Zone> windscreenZoneMatrix;
        private Property<Zone> windscreenDamageZone;
        private Property<WindscreenNeedsReplacement> windscreenNeedsReplacement;
        private Property<Double> windscreenDamageConfidence;
        private Property<Calendar> windscreenDamageDetectionTime;

        public Builder() {
            super(IDENTIFIER);
        }

        public WindscreenState build() {
            return new WindscreenState(this);
        }

        /**
         * @param wipers The wipers
         * @return The builder
         */
        public Builder setWipers(Property<Wipers> wipers) {
            this.wipers = wipers.setIdentifier(IDENTIFIER_WIPERS);
            addProperty(this.wipers);
            return this;
        }
        
        /**
         * @param wipersIntensity The wipers intensity
         * @return The builder
         */
        public Builder setWipersIntensity(Property<WipersIntensity> wipersIntensity) {
            this.wipersIntensity = wipersIntensity.setIdentifier(IDENTIFIER_WIPERS_INTENSITY);
            addProperty(this.wipersIntensity);
            return this;
        }
        
        /**
         * @param windscreenDamage The windscreen damage
         * @return The builder
         */
        public Builder setWindscreenDamage(Property<WindscreenDamage> windscreenDamage) {
            this.windscreenDamage = windscreenDamage.setIdentifier(IDENTIFIER_WINDSCREEN_DAMAGE);
            addProperty(this.windscreenDamage);
            return this;
        }
        
        /**
         * @param windscreenZoneMatrix Representing the size of the matrix, seen from the inside of the vehicle
         * @return The builder
         */
        public Builder setWindscreenZoneMatrix(Property<Zone> windscreenZoneMatrix) {
            this.windscreenZoneMatrix = windscreenZoneMatrix.setIdentifier(IDENTIFIER_WINDSCREEN_ZONE_MATRIX);
            addProperty(this.windscreenZoneMatrix);
            return this;
        }
        
        /**
         * @param windscreenDamageZone Representing the position in the zone, seen from the inside of the vehicle (1-based index)
         * @return The builder
         */
        public Builder setWindscreenDamageZone(Property<Zone> windscreenDamageZone) {
            this.windscreenDamageZone = windscreenDamageZone.setIdentifier(IDENTIFIER_WINDSCREEN_DAMAGE_ZONE);
            addProperty(this.windscreenDamageZone);
            return this;
        }
        
        /**
         * @param windscreenNeedsReplacement The windscreen needs replacement
         * @return The builder
         */
        public Builder setWindscreenNeedsReplacement(Property<WindscreenNeedsReplacement> windscreenNeedsReplacement) {
            this.windscreenNeedsReplacement = windscreenNeedsReplacement.setIdentifier(IDENTIFIER_WINDSCREEN_NEEDS_REPLACEMENT);
            addProperty(this.windscreenNeedsReplacement);
            return this;
        }
        
        /**
         * @param windscreenDamageConfidence Confidence of damage detection, 0% if no impact detected
         * @return The builder
         */
        public Builder setWindscreenDamageConfidence(Property<Double> windscreenDamageConfidence) {
            this.windscreenDamageConfidence = windscreenDamageConfidence.setIdentifier(IDENTIFIER_WINDSCREEN_DAMAGE_CONFIDENCE);
            addProperty(this.windscreenDamageConfidence);
            return this;
        }
        
        /**
         * @param windscreenDamageDetectionTime Milliseconds since UNIX Epoch time
         * @return The builder
         */
        public Builder setWindscreenDamageDetectionTime(Property<Calendar> windscreenDamageDetectionTime) {
            this.windscreenDamageDetectionTime = windscreenDamageDetectionTime.setIdentifier(IDENTIFIER_WINDSCREEN_DAMAGE_DETECTION_TIME);
            addProperty(this.windscreenDamageDetectionTime);
            return this;
        }
    }

    public enum Wipers implements ByteEnum {
        INACTIVE((byte) 0x00),
        ACTIVE((byte) 0x01),
        AUTOMATIC((byte) 0x02);
    
        public static Wipers fromByte(byte byteValue) throws CommandParseException {
            Wipers[] values = Wipers.values();
    
            for (int i = 0; i < values.length; i++) {
                Wipers state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Wipers(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum WipersIntensity implements ByteEnum {
        LEVEL_0((byte) 0x00),
        LEVEL_1((byte) 0x01),
        LEVEL_2((byte) 0x02),
        LEVEL_3((byte) 0x03);
    
        public static WipersIntensity fromByte(byte byteValue) throws CommandParseException {
            WipersIntensity[] values = WipersIntensity.values();
    
            for (int i = 0; i < values.length; i++) {
                WipersIntensity state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        WipersIntensity(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum WindscreenDamage implements ByteEnum {
        NO_IMPACT_DETECTED((byte) 0x00),
        IMPACT_BUT_NO_DAMAGE_DETECTED((byte) 0x01),
        DAMAGE_SMALLER_THAN_1_INCH((byte) 0x02),
        DAMAGE_LARGER_THAN_1_INCH((byte) 0x03);
    
        public static WindscreenDamage fromByte(byte byteValue) throws CommandParseException {
            WindscreenDamage[] values = WindscreenDamage.values();
    
            for (int i = 0; i < values.length; i++) {
                WindscreenDamage state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        WindscreenDamage(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum WindscreenNeedsReplacement implements ByteEnum {
        UNKNOWN((byte) 0x00),
        NO_REPLACEMENT_NEEDED((byte) 0x01),
        REPLACEMENT_NEEDED((byte) 0x02);
    
        public static WindscreenNeedsReplacement fromByte(byte byteValue) throws CommandParseException {
            WindscreenNeedsReplacement[] values = WindscreenNeedsReplacement.values();
    
            for (int i = 0; i < values.length; i++) {
                WindscreenNeedsReplacement state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        WindscreenNeedsReplacement(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}