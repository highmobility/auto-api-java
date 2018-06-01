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

import com.highmobility.autoapi.property.CalendarProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.WindscreenDamage;
import com.highmobility.autoapi.property.WindscreenDamageZone;
import com.highmobility.autoapi.property.WindscreenDamageZoneMatrix;
import com.highmobility.autoapi.property.WindscreenReplacementState;
import com.highmobility.autoapi.property.WiperIntensity;
import com.highmobility.autoapi.property.WiperState;

import java.util.Calendar;

/**
 * Command sent when a Get Windscreen State command is received by the car. The wipers intensity is
 * indicated even if the car has automatic wipers activated.
 */
public class WindscreenState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDSCREEN, 0x01);

    public static final byte DAMAGE_CONFIDENCE_IDENTIFIER = 0x07;
    public static final byte DAMAGE_DETECTION_TIME_IDENTIFIER = 0x08;

    WiperState wiperState;
    WiperIntensity wiperIntensity;
    WindscreenDamage windscreenDamage;
    WindscreenDamageZone windscreenDamageZone;
    WindscreenDamageZoneMatrix windscreenDamageZoneMatrix;
    WindscreenReplacementState windscreenReplacementState;
    Float damageConfidence;
    Calendar damageDetectionTime;

    /**
     * @return The wiper state.
     */
    public WiperState getWiperState() {
        return wiperState;
    }

    /**
     * @return The wiper intensity.
     */
    public WiperIntensity getWiperIntensity() {
        return wiperIntensity;
    }

    /**
     * @return The windscreen damage.
     */
    public WindscreenDamage getWindscreenDamage() {
        return windscreenDamage;
    }

    /**
     * @return The windscreen damage position, as viewed from inside the car.
     */
    public WindscreenDamageZone getWindscreenDamageZone() {
        return windscreenDamageZone;
    }

    /**
     * @return The windscreen zone matrix, as viewed from inside the car.
     */
    public WindscreenDamageZoneMatrix getWindscreenDamageZoneMatrix() {
        return windscreenDamageZoneMatrix;
    }

    /**
     * @return The windscreen replacement state.
     */
    public WindscreenReplacementState getWindscreenReplacementState() {
        return windscreenReplacementState;
    }

    /**
     * @return The damage confidence.
     */
    public Float getDamageConfidence() {
        return damageConfidence;
    }

    /**
     * @return The damage detection time.
     */
    public Calendar getDamageDetectionTime() {
        return damageDetectionTime;
    }

    public WindscreenState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case WiperState.IDENTIFIER:
                    // active
                    wiperState = WiperState.fromByte(property.getValueByte());
                    break;
                case WiperIntensity.IDENTIFIER:
                    // intensity
                    wiperIntensity = WiperIntensity.fromByte(property.getValueByte());
                    break;
                case WindscreenDamage.IDENTIFIER:
                    // damage
                    windscreenDamage = WindscreenDamage.fromByte(property.getValueByte());
                    break;
                case WindscreenDamageZoneMatrix.IDENTIFIER:
                    // zone matrix
                    windscreenDamageZoneMatrix = new WindscreenDamageZoneMatrix(property
                            .getValueByte());
                    break;
                case WindscreenDamageZone.IDENTIFIER:
                    // damage zone
                    windscreenDamageZone = new WindscreenDamageZone(property.getValueByte());
                    break;
                case WindscreenReplacementState.IDENTIFIER:
                    windscreenReplacementState = WindscreenReplacementState.fromByte(property
                            .getValueByte());
                    // replacement
                    break;
                case DAMAGE_CONFIDENCE_IDENTIFIER:
                    damageConfidence = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case DAMAGE_DETECTION_TIME_IDENTIFIER:
                    damageDetectionTime = Property.getCalendar(property.getValueBytes());
                    // detection time
                    break;

            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private WindscreenState(Builder builder) {
        super(builder);
        wiperState = builder.wiperState;
        wiperIntensity = builder.wiperIntensity;
        windscreenDamage = builder.windscreenDamage;
        windscreenDamageZone = builder.windscreenDamageZone;
        windscreenDamageZoneMatrix = builder.windscreenDamageZoneMatrix;
        windscreenReplacementState = builder.windscreenReplacementState;
        damageConfidence = builder.damageConfidence;
        damageDetectionTime = builder.damageDetectionTime;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private WiperState wiperState;
        private WiperIntensity wiperIntensity;
        private WindscreenDamage windscreenDamage;
        private WindscreenDamageZone windscreenDamageZone;
        private WindscreenDamageZoneMatrix windscreenDamageZoneMatrix;
        private WindscreenReplacementState windscreenReplacementState;
        private Float damageConfidence;
        private Calendar damageDetectionTime;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param wiperState The wiper state.
         * @return The builder.
         */
        public Builder setWiperState(WiperState wiperState) {
            this.wiperState = wiperState;
            addProperty(wiperState);
            return this;
        }

        /**
         * @param wiperIntensity The wipers intensity.
         * @return The builder.
         */
        public Builder setWiperIntensity(WiperIntensity wiperIntensity) {
            this.wiperIntensity = wiperIntensity;
            addProperty(wiperIntensity);
            return this;
        }

        /**
         * @param windscreenDamage The windscreen damage.
         * @return The builder.
         */
        public Builder setWindscreenDamage(WindscreenDamage windscreenDamage) {
            this.windscreenDamage = windscreenDamage;
            addProperty(windscreenDamage);
            return this;
        }

        /**
         * @param windscreenDamageZone The windscreen damage position, as viewed from inside the
         *                             car.
         * @return The builder.
         */
        public Builder setWindscreenDamageZone(WindscreenDamageZone windscreenDamageZone) {
            this.windscreenDamageZone = windscreenDamageZone;
            addProperty(windscreenDamageZone);
            return this;
        }

        /**
         * @param windscreenDamageZoneMatrix The windscreen damage zone matrix, as viewed from
         *                                   inside the car.
         * @return The builder.
         */
        public Builder setWindscreenDamageZoneMatrix(WindscreenDamageZoneMatrix
                                                             windscreenDamageZoneMatrix) {
            this.windscreenDamageZoneMatrix = windscreenDamageZoneMatrix;
            addProperty(windscreenDamageZoneMatrix);
            return this;
        }

        /**
         * @param windscreenReplacementState The windscreen replacement state.
         * @return The builder.
         */
        public Builder setWindscreenReplacementState(WindscreenReplacementState
                                                             windscreenReplacementState) {
            this.windscreenReplacementState = windscreenReplacementState;
            addProperty(windscreenReplacementState);
            return this;
        }

        /**
         * @param damageConfidence The damage confidence.
         * @return The builder.
         */
        public Builder setDamageConfidence(Float damageConfidence) {
            this.damageConfidence = damageConfidence;
            addProperty(new PercentageProperty(DAMAGE_CONFIDENCE_IDENTIFIER, damageConfidence));
            return this;
        }

        /**
         * @param damageDetectionTime The damage detection time.
         * @return The builder.
         */
        public Builder setDamageDetectionTime(Calendar damageDetectionTime) {
            this.damageDetectionTime = damageDetectionTime;
            addProperty(new CalendarProperty(DAMAGE_DETECTION_TIME_IDENTIFIER,
                    damageDetectionTime));
            return this;
        }

        public WindscreenState build() {
            return new WindscreenState(this);
        }
    }
}