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
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.WindscreenDamage;
import com.highmobility.autoapi.property.WindscreenDamageZone;
import com.highmobility.autoapi.property.WindscreenDamageZoneMatrix;
import com.highmobility.autoapi.property.WindscreenReplacementState;
import com.highmobility.autoapi.property.WiperIntensity;
import com.highmobility.autoapi.property.WiperState;

import java.util.Calendar;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Windscreen State command is received by the car. The wipers intensity is
 * indicated even if the car has automatic wipers activated.
 */
public class WindscreenState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDSCREEN, 0x01);

    private static final byte DAMAGE_CONFIDENCE_IDENTIFIER = 0x07;
    private static final byte DAMAGE_DETECTION_TIME_IDENTIFIER = 0x08;
    private static final byte WINDSCREEN_REPLACEMENT_STATE_IDENTIFIER = 0x06;

    private static final byte IDENTIFIER_WIPERS_STATE = 0x01;
    private static final byte IDENTIFIER_WIPER_INTENSITY = 0x02;
    private static final byte IDENTIFIER_WINDSCREEN_DAMAGE = 0x03;

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
    @Nullable public WiperState getWiperState() {
        return wiperState;
    }

    /**
     * @return The wiper intensity.
     */
    @Nullable public WiperIntensity getWiperIntensity() {
        return wiperIntensity;
    }

    /**
     * @return The windscreen damage.
     */
    @Nullable public WindscreenDamage getWindscreenDamage() {
        return windscreenDamage;
    }

    /**
     * @return The windscreen damage position, as viewed from inside the car.
     */
    @Nullable public WindscreenDamageZone getWindscreenDamageZone() {
        return windscreenDamageZone;
    }

    /**
     * @return The windscreen zone matrix, as viewed from inside the car.
     */
    @Nullable public WindscreenDamageZoneMatrix getWindscreenDamageZoneMatrix() {
        return windscreenDamageZoneMatrix;
    }

    /**
     * @return The windscreen replacement state.
     */
    @Nullable public WindscreenReplacementState getWindscreenReplacementState() {
        return windscreenReplacementState;
    }

    /**
     * @return The damage confidence.
     */
    @Nullable public Float getDamageConfidence() {
        return damageConfidence;
    }

    /**
     * @return The damage detection time.
     */
    @Nullable public Calendar getDamageDetectionTime() {
        return damageDetectionTime;
    }

    WindscreenState(byte[] bytes) {
        super(bytes);
        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(property -> {
                switch (property.getPropertyIdentifier()) {
                    case IDENTIFIER_WIPERS_STATE:
                        wiperState = WiperState.fromByte(property.getValueByte());
                        return wiperState;
                    case IDENTIFIER_WIPER_INTENSITY:
                        wiperIntensity = WiperIntensity.fromByte(property.getValueByte());
                        return wiperIntensity;
                    case IDENTIFIER_WINDSCREEN_DAMAGE:
                        windscreenDamage = WindscreenDamage.fromByte(property.getValueByte());
                        return windscreenDamage;
                    case WindscreenDamageZoneMatrix.IDENTIFIER:
                        windscreenDamageZoneMatrix = new WindscreenDamageZoneMatrix(property
                                .getValueByte());
                        return windscreenDamageZoneMatrix;
                    case WindscreenDamageZone.IDENTIFIER:
                        windscreenDamageZone = new WindscreenDamageZone(property.getValueByte());
                        return windscreenDamageZone;
                    case WINDSCREEN_REPLACEMENT_STATE_IDENTIFIER:
                        windscreenReplacementState = WindscreenReplacementState.fromByte(property
                                .getValueByte());
                        return windscreenReplacementState;
                    case DAMAGE_CONFIDENCE_IDENTIFIER:
                        damageConfidence = Property.getUnsignedInt(property.getValueByte()) / 100f;
                        return damageConfidence;
                    case DAMAGE_DETECTION_TIME_IDENTIFIER:
                        damageDetectionTime = Property.getCalendar(property.getValueBytes());
                        return damageDetectionTime;
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
            addProperty(new Property(IDENTIFIER_WIPERS_STATE, wiperState.getByte()));
            return this;
        }

        /**
         * @param wiperIntensity The wipers intensity.
         * @return The builder.
         */
        public Builder setWiperIntensity(WiperIntensity wiperIntensity) {
            this.wiperIntensity = wiperIntensity;
            addProperty(new Property(IDENTIFIER_WIPER_INTENSITY, wiperIntensity.getByte()));
            return this;
        }

        /**
         * @param windscreenDamage The windscreen damage.
         * @return The builder.
         */
        public Builder setWindscreenDamage(WindscreenDamage windscreenDamage) {
            this.windscreenDamage = windscreenDamage;
            addProperty(new Property(IDENTIFIER_WINDSCREEN_DAMAGE, windscreenDamage.getByte()));
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
            addProperty(new Property(WINDSCREEN_REPLACEMENT_STATE_IDENTIFIER,
                    windscreenReplacementState.getByte()));
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