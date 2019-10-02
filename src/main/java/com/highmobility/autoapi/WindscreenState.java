// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.value.Zone;
import java.util.Calendar;

public class WindscreenState extends SetCommand {
    Property<Wipers> wipers = new Property(Wipers.class, 0x01);
    Property<WipersIntensity> wipersIntensity = new Property(WipersIntensity.class, 0x02);
    Property<WindscreenDamage> windscreenDamage = new Property(WindscreenDamage.class, 0x03);
    Property<Zone> windscreenZoneMatrix = new Property(Zone.class, 0x04);
    Property<Zone> windscreenDamageZone = new Property(Zone.class, 0x05);
    Property<WindscreenNeedsReplacement> windscreenNeedsReplacement = new Property(WindscreenNeedsReplacement.class, 0x06);
    Property<Double> windscreenDamageConfidence = new Property(Double.class, 0x07);
    Property<Calendar> windscreenDamageDetectionTime = new Property(Calendar.class, 0x08);

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
                    case 0x01: return wipers.update(p);
                    case 0x02: return wipersIntensity.update(p);
                    case 0x03: return windscreenDamage.update(p);
                    case 0x04: return windscreenZoneMatrix.update(p);
                    case 0x05: return windscreenDamageZone.update(p);
                    case 0x06: return windscreenNeedsReplacement.update(p);
                    case 0x07: return windscreenDamageConfidence.update(p);
                    case 0x08: return windscreenDamageDetectionTime.update(p);
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
            super(Identifier.WINDSCREEN);
        }

        public WindscreenState build() {
            return new WindscreenState(this);
        }

        /**
         * @param wipers The wipers
         * @return The builder
         */
        public Builder setWipers(Property<Wipers> wipers) {
            this.wipers = wipers.setIdentifier(0x01);
            addProperty(this.wipers);
            return this;
        }
        
        /**
         * @param wipersIntensity The wipers intensity
         * @return The builder
         */
        public Builder setWipersIntensity(Property<WipersIntensity> wipersIntensity) {
            this.wipersIntensity = wipersIntensity.setIdentifier(0x02);
            addProperty(this.wipersIntensity);
            return this;
        }
        
        /**
         * @param windscreenDamage The windscreen damage
         * @return The builder
         */
        public Builder setWindscreenDamage(Property<WindscreenDamage> windscreenDamage) {
            this.windscreenDamage = windscreenDamage.setIdentifier(0x03);
            addProperty(this.windscreenDamage);
            return this;
        }
        
        /**
         * @param windscreenZoneMatrix Representing the size of the matrix, seen from the inside of the vehicle
         * @return The builder
         */
        public Builder setWindscreenZoneMatrix(Property<Zone> windscreenZoneMatrix) {
            this.windscreenZoneMatrix = windscreenZoneMatrix.setIdentifier(0x04);
            addProperty(this.windscreenZoneMatrix);
            return this;
        }
        
        /**
         * @param windscreenDamageZone Representing the position in the zone, seen from the inside of the vehicle (1-based index)
         * @return The builder
         */
        public Builder setWindscreenDamageZone(Property<Zone> windscreenDamageZone) {
            this.windscreenDamageZone = windscreenDamageZone.setIdentifier(0x05);
            addProperty(this.windscreenDamageZone);
            return this;
        }
        
        /**
         * @param windscreenNeedsReplacement The windscreen needs replacement
         * @return The builder
         */
        public Builder setWindscreenNeedsReplacement(Property<WindscreenNeedsReplacement> windscreenNeedsReplacement) {
            this.windscreenNeedsReplacement = windscreenNeedsReplacement.setIdentifier(0x06);
            addProperty(this.windscreenNeedsReplacement);
            return this;
        }
        
        /**
         * @param windscreenDamageConfidence Confidence of damage detection, 0% if no impact detected
         * @return The builder
         */
        public Builder setWindscreenDamageConfidence(Property<Double> windscreenDamageConfidence) {
            this.windscreenDamageConfidence = windscreenDamageConfidence.setIdentifier(0x07);
            addProperty(this.windscreenDamageConfidence);
            return this;
        }
        
        /**
         * @param windscreenDamageDetectionTime Milliseconds since UNIX Epoch time
         * @return The builder
         */
        public Builder setWindscreenDamageDetectionTime(Property<Calendar> windscreenDamageDetectionTime) {
            this.windscreenDamageDetectionTime = windscreenDamageDetectionTime.setIdentifier(0x08);
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