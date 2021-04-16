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
import com.highmobility.autoapi.value.Zone;
import com.highmobility.value.Bytes;
import java.util.Calendar;
import javax.annotation.Nullable;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Windscreen capability
 */
public class Windscreen {
    public static final int IDENTIFIER = Identifier.WINDSCREEN;

    public static final byte PROPERTY_WIPERS_STATUS = 0x01;
    public static final byte PROPERTY_WIPERS_INTENSITY = 0x02;
    public static final byte PROPERTY_WINDSCREEN_DAMAGE = 0x03;
    public static final byte PROPERTY_WINDSCREEN_ZONE_MATRIX = 0x04;
    public static final byte PROPERTY_WINDSCREEN_DAMAGE_ZONE = 0x05;
    public static final byte PROPERTY_WINDSCREEN_NEEDS_REPLACEMENT = 0x06;
    public static final byte PROPERTY_WINDSCREEN_DAMAGE_CONFIDENCE = 0x07;
    public static final byte PROPERTY_WINDSCREEN_DAMAGE_DETECTION_TIME = 0x08;

    /**
     * Get Windscreen property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Windscreen property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Windscreen property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Windscreen property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get Windscreen properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Windscreen properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Windscreen properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Windscreen properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Windscreen properties
     * 
     * @deprecated use {@link GetState#GetState(byte...)} instead
     */
    @Deprecated
    public static class GetProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Set windscreen damage
     */
    public static class SetWindscreenDamage extends SetCommand {
        Property<WindscreenDamage> windscreenDamage = new Property<>(WindscreenDamage.class, PROPERTY_WINDSCREEN_DAMAGE);
        Property<Zone> windscreenDamageZone = new Property<>(Zone.class, PROPERTY_WINDSCREEN_DAMAGE_ZONE);
    
        /**
         * @return The windscreen damage
         */
        public Property<WindscreenDamage> getWindscreenDamage() {
            return windscreenDamage;
        }
        
        /**
         * @return The windscreen damage zone
         */
        public Property<Zone> getWindscreenDamageZone() {
            return windscreenDamageZone;
        }
        
        /**
         * Set windscreen damage
         * 
         * @param windscreenDamage The windscreen damage
         * @param windscreenDamageZone Representing the position in the zone, seen from the inside of the vehicle (1-based index)
         */
        public SetWindscreenDamage(WindscreenDamage windscreenDamage, @Nullable Zone windscreenDamageZone) {
            super(IDENTIFIER);
        
            addProperty(this.windscreenDamage.update(windscreenDamage));
            addProperty(this.windscreenDamageZone.update(windscreenDamageZone));
            createBytes();
        }
    
        SetWindscreenDamage(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_WINDSCREEN_DAMAGE: return windscreenDamage.update(p);
                        case PROPERTY_WINDSCREEN_DAMAGE_ZONE: return windscreenDamageZone.update(p);
                    }
        
                    return null;
                });
            }
            if (this.windscreenDamage.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Set windscreen replacement needed
     */
    public static class SetWindscreenReplacementNeeded extends SetCommand {
        Property<WindscreenNeedsReplacement> windscreenNeedsReplacement = new Property<>(WindscreenNeedsReplacement.class, PROPERTY_WINDSCREEN_NEEDS_REPLACEMENT);
    
        /**
         * @return The windscreen needs replacement
         */
        public Property<WindscreenNeedsReplacement> getWindscreenNeedsReplacement() {
            return windscreenNeedsReplacement;
        }
        
        /**
         * Set windscreen replacement needed
         * 
         * @param windscreenNeedsReplacement The windscreen needs replacement
         */
        public SetWindscreenReplacementNeeded(WindscreenNeedsReplacement windscreenNeedsReplacement) {
            super(IDENTIFIER);
        
            addProperty(this.windscreenNeedsReplacement.update(windscreenNeedsReplacement));
            createBytes();
        }
    
        SetWindscreenReplacementNeeded(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_WINDSCREEN_NEEDS_REPLACEMENT) return windscreenNeedsReplacement.update(p);
                    
                    return null;
                });
            }
            if (this.windscreenNeedsReplacement.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Control wipers
     */
    public static class ControlWipers extends SetCommand {
        Property<WipersStatus> wipersStatus = new Property<>(WipersStatus.class, PROPERTY_WIPERS_STATUS);
        Property<WipersIntensity> wipersIntensity = new Property<>(WipersIntensity.class, PROPERTY_WIPERS_INTENSITY);
    
        /**
         * @return The wipers status
         */
        public Property<WipersStatus> getWipersStatus() {
            return wipersStatus;
        }
        
        /**
         * @return The wipers intensity
         */
        public Property<WipersIntensity> getWipersIntensity() {
            return wipersIntensity;
        }
        
        /**
         * Control wipers
         * 
         * @param wipersStatus The wipers status
         * @param wipersIntensity The wipers intensity
         */
        public ControlWipers(WipersStatus wipersStatus, @Nullable WipersIntensity wipersIntensity) {
            super(IDENTIFIER);
        
            addProperty(this.wipersStatus.update(wipersStatus));
            addProperty(this.wipersIntensity.update(wipersIntensity));
            createBytes();
        }
    
        ControlWipers(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_WIPERS_STATUS: return wipersStatus.update(p);
                        case PROPERTY_WIPERS_INTENSITY: return wipersIntensity.update(p);
                    }
        
                    return null;
                });
            }
            if (this.wipersStatus.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The windscreen state
     */
    public static class State extends SetCommand {
        Property<WipersStatus> wipersStatus = new Property<>(WipersStatus.class, PROPERTY_WIPERS_STATUS);
        Property<WipersIntensity> wipersIntensity = new Property<>(WipersIntensity.class, PROPERTY_WIPERS_INTENSITY);
        Property<WindscreenDamage> windscreenDamage = new Property<>(WindscreenDamage.class, PROPERTY_WINDSCREEN_DAMAGE);
        Property<Zone> windscreenZoneMatrix = new Property<>(Zone.class, PROPERTY_WINDSCREEN_ZONE_MATRIX);
        Property<Zone> windscreenDamageZone = new Property<>(Zone.class, PROPERTY_WINDSCREEN_DAMAGE_ZONE);
        Property<WindscreenNeedsReplacement> windscreenNeedsReplacement = new Property<>(WindscreenNeedsReplacement.class, PROPERTY_WINDSCREEN_NEEDS_REPLACEMENT);
        Property<Double> windscreenDamageConfidence = new Property<>(Double.class, PROPERTY_WINDSCREEN_DAMAGE_CONFIDENCE);
        Property<Calendar> windscreenDamageDetectionTime = new Property<>(Calendar.class, PROPERTY_WINDSCREEN_DAMAGE_DETECTION_TIME);
    
        /**
         * @return The wipers status
         */
        public Property<WipersStatus> getWipersStatus() {
            return wipersStatus;
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
         * @return Windscreen damage detection date
         */
        public Property<Calendar> getWindscreenDamageDetectionTime() {
            return windscreenDamageDetectionTime;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_WIPERS_STATUS: return wipersStatus.update(p);
                        case PROPERTY_WIPERS_INTENSITY: return wipersIntensity.update(p);
                        case PROPERTY_WINDSCREEN_DAMAGE: return windscreenDamage.update(p);
                        case PROPERTY_WINDSCREEN_ZONE_MATRIX: return windscreenZoneMatrix.update(p);
                        case PROPERTY_WINDSCREEN_DAMAGE_ZONE: return windscreenDamageZone.update(p);
                        case PROPERTY_WINDSCREEN_NEEDS_REPLACEMENT: return windscreenNeedsReplacement.update(p);
                        case PROPERTY_WINDSCREEN_DAMAGE_CONFIDENCE: return windscreenDamageConfidence.update(p);
                        case PROPERTY_WINDSCREEN_DAMAGE_DETECTION_TIME: return windscreenDamageDetectionTime.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            wipersStatus = builder.wipersStatus;
            wipersIntensity = builder.wipersIntensity;
            windscreenDamage = builder.windscreenDamage;
            windscreenZoneMatrix = builder.windscreenZoneMatrix;
            windscreenDamageZone = builder.windscreenDamageZone;
            windscreenNeedsReplacement = builder.windscreenNeedsReplacement;
            windscreenDamageConfidence = builder.windscreenDamageConfidence;
            windscreenDamageDetectionTime = builder.windscreenDamageDetectionTime;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<WipersStatus> wipersStatus;
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
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param wipersStatus The wipers status
             * @return The builder
             */
            public Builder setWipersStatus(Property<WipersStatus> wipersStatus) {
                this.wipersStatus = wipersStatus.setIdentifier(PROPERTY_WIPERS_STATUS);
                addProperty(this.wipersStatus);
                return this;
            }
            
            /**
             * @param wipersIntensity The wipers intensity
             * @return The builder
             */
            public Builder setWipersIntensity(Property<WipersIntensity> wipersIntensity) {
                this.wipersIntensity = wipersIntensity.setIdentifier(PROPERTY_WIPERS_INTENSITY);
                addProperty(this.wipersIntensity);
                return this;
            }
            
            /**
             * @param windscreenDamage The windscreen damage
             * @return The builder
             */
            public Builder setWindscreenDamage(Property<WindscreenDamage> windscreenDamage) {
                this.windscreenDamage = windscreenDamage.setIdentifier(PROPERTY_WINDSCREEN_DAMAGE);
                addProperty(this.windscreenDamage);
                return this;
            }
            
            /**
             * @param windscreenZoneMatrix Representing the size of the matrix, seen from the inside of the vehicle
             * @return The builder
             */
            public Builder setWindscreenZoneMatrix(Property<Zone> windscreenZoneMatrix) {
                this.windscreenZoneMatrix = windscreenZoneMatrix.setIdentifier(PROPERTY_WINDSCREEN_ZONE_MATRIX);
                addProperty(this.windscreenZoneMatrix);
                return this;
            }
            
            /**
             * @param windscreenDamageZone Representing the position in the zone, seen from the inside of the vehicle (1-based index)
             * @return The builder
             */
            public Builder setWindscreenDamageZone(Property<Zone> windscreenDamageZone) {
                this.windscreenDamageZone = windscreenDamageZone.setIdentifier(PROPERTY_WINDSCREEN_DAMAGE_ZONE);
                addProperty(this.windscreenDamageZone);
                return this;
            }
            
            /**
             * @param windscreenNeedsReplacement The windscreen needs replacement
             * @return The builder
             */
            public Builder setWindscreenNeedsReplacement(Property<WindscreenNeedsReplacement> windscreenNeedsReplacement) {
                this.windscreenNeedsReplacement = windscreenNeedsReplacement.setIdentifier(PROPERTY_WINDSCREEN_NEEDS_REPLACEMENT);
                addProperty(this.windscreenNeedsReplacement);
                return this;
            }
            
            /**
             * @param windscreenDamageConfidence Confidence of damage detection, 0% if no impact detected
             * @return The builder
             */
            public Builder setWindscreenDamageConfidence(Property<Double> windscreenDamageConfidence) {
                this.windscreenDamageConfidence = windscreenDamageConfidence.setIdentifier(PROPERTY_WINDSCREEN_DAMAGE_CONFIDENCE);
                addProperty(this.windscreenDamageConfidence);
                return this;
            }
            
            /**
             * @param windscreenDamageDetectionTime Windscreen damage detection date
             * @return The builder
             */
            public Builder setWindscreenDamageDetectionTime(Property<Calendar> windscreenDamageDetectionTime) {
                this.windscreenDamageDetectionTime = windscreenDamageDetectionTime.setIdentifier(PROPERTY_WINDSCREEN_DAMAGE_DETECTION_TIME);
                addProperty(this.windscreenDamageDetectionTime);
                return this;
            }
        }
    }

    public enum WipersStatus implements ByteEnum {
        INACTIVE((byte) 0x00),
        ACTIVE((byte) 0x01),
        AUTOMATIC((byte) 0x02);
    
        public static WipersStatus fromByte(byte byteValue) throws CommandParseException {
            WipersStatus[] values = WipersStatus.values();
    
            for (int i = 0; i < values.length; i++) {
                WipersStatus state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(WipersStatus.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        WipersStatus(byte value) {
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
    
            throw new CommandParseException(
                enumValueDoesNotExist(WipersIntensity.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
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
    
            throw new CommandParseException(
                enumValueDoesNotExist(WindscreenDamage.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
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
    
            throw new CommandParseException(
                enumValueDoesNotExist(WindscreenNeedsReplacement.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        WindscreenNeedsReplacement(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}