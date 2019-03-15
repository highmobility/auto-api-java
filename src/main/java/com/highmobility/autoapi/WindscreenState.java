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
import com.highmobility.autoapi.value.windscreen.WindscreenDamage;
import com.highmobility.autoapi.value.windscreen.WindscreenDamageZone;
import com.highmobility.autoapi.value.windscreen.WindscreenDamageZoneMatrix;
import com.highmobility.autoapi.value.windscreen.WindscreenReplacementState;
import com.highmobility.autoapi.value.windscreen.WiperIntensity;
import com.highmobility.autoapi.value.windscreen.WiperState;

import java.util.Calendar;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Windscreen State command is received by the car. The wipers intensity is
 * indicated even if the car has automatic wipers activated.
 */
public class WindscreenState extends Command {
    public static final Type TYPE = new Type(Identifier.WINDSCREEN, 0x01);

    private static final byte IDENTIFIER_DAMAGE_CONFIDENCE = 0x07;
    private static final byte IDENTIFIER_DAMAGE_DETECTION_TIME = 0x08;
    private static final byte WINDSCREEN_REPLACEMENT_STATE_IDENTIFIER = 0x06;

    private static final byte IDENTIFIER_WIPERS_STATE = 0x01;
    private static final byte IDENTIFIER_WIPER_INTENSITY = 0x02;
    private static final byte IDENTIFIER_WINDSCREEN_DAMAGE = 0x03;
    private static final byte IDENTIFIER_DAMAGE_ZONE_MATRIX = 0x04;
    public static final byte IDENTIFIER_DAMAGE_ZONE = 0x05;

    Property<WiperState> wiperState = new Property(WiperState.class, IDENTIFIER_WIPERS_STATE);
    Property<WiperIntensity> wiperIntensity = new Property(WiperIntensity.class,
            IDENTIFIER_WIPER_INTENSITY);
    Property<WindscreenDamage> windscreenDamage = new Property(WindscreenDamage.class,
            WINDSCREEN_REPLACEMENT_STATE_IDENTIFIER);
    Property<WindscreenDamageZone> windscreenDamageZone = new Property(WindscreenDamageZone.class
            , IDENTIFIER_WINDSCREEN_DAMAGE);
    Property<WindscreenDamageZoneMatrix> windscreenDamageZoneMatrix =
            new Property(WindscreenDamageZoneMatrix.class, IDENTIFIER_DAMAGE_ZONE_MATRIX);
    Property<WindscreenReplacementState> windscreenReplacementState =
            new Property(WindscreenReplacementState.class, WINDSCREEN_REPLACEMENT_STATE_IDENTIFIER);
    Property<Double> damageConfidence =
            new Property(Double.class, IDENTIFIER_DAMAGE_CONFIDENCE);
    Property<Calendar> damageDetectionTime = new Property(Calendar.class,
            IDENTIFIER_DAMAGE_DETECTION_TIME);

    /**
     * @return The wiper state.
     */
    @Nullable public Property<WiperState> getWiperState() {
        return wiperState;
    }

    /**
     * @return The wiper intensity.
     */
    @Nullable public Property<WiperIntensity> getWiperIntensity() {
        return wiperIntensity;
    }

    /**
     * @return The windscreen damage.
     */
    @Nullable public Property<WindscreenDamage> getWindscreenDamage() {
        return windscreenDamage;
    }

    /**
     * @return The windscreen damage position, as viewed from inside the car.
     */
    @Nullable public Property<WindscreenDamageZone> getWindscreenDamageZone() {
        return windscreenDamageZone;
    }

    /**
     * @return The windscreen zone matrix, as viewed from inside the car.
     */
    @Nullable public Property<WindscreenDamageZoneMatrix> getWindscreenDamageZoneMatrix() {
        return windscreenDamageZoneMatrix;
    }

    /**
     * @return The windscreen replacement state.
     */
    @Nullable public Property<WindscreenReplacementState> getWindscreenReplacementState() {
        return windscreenReplacementState;
    }

    /**
     * @return The damage confidence.
     */

    @Nullable public Property<Double> getDamageConfidence() {
        return damageConfidence;
    }

    /**
     * @return The damage detection time.
     */
    @Nullable public Property<Calendar> getDamageDetectionTime() {
        return damageDetectionTime;
    }

    WindscreenState(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_WIPERS_STATE:
                        return wiperState.update(p);
                    case IDENTIFIER_WIPER_INTENSITY:
                        return wiperIntensity.update(p);
                    case IDENTIFIER_WINDSCREEN_DAMAGE:
                        return windscreenDamage.update(p);
                    case IDENTIFIER_DAMAGE_ZONE_MATRIX:
                        return windscreenDamageZoneMatrix.update(p);
                    case IDENTIFIER_DAMAGE_ZONE:
                        return windscreenDamageZone.update(p);
                    case WINDSCREEN_REPLACEMENT_STATE_IDENTIFIER:
                        return windscreenReplacementState.update(p);
                    case IDENTIFIER_DAMAGE_CONFIDENCE:
                        return damageConfidence.update(p);
                    case IDENTIFIER_DAMAGE_DETECTION_TIME:
                        return damageDetectionTime.update(p);
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

    public static final class Builder extends Command.Builder {
        private Property<WiperState> wiperState;
        private Property<WiperIntensity> wiperIntensity;
        private Property<WindscreenDamage> windscreenDamage;
        private Property<WindscreenDamageZone> windscreenDamageZone;
        private Property<WindscreenDamageZoneMatrix> windscreenDamageZoneMatrix;
        private Property<WindscreenReplacementState> windscreenReplacementState;

        private Property<Double> damageConfidence;
        private Property<Calendar> damageDetectionTime;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param wiperState The wiper state.
         * @return The builder.
         */
        public Builder setWiperState(Property<WiperState> wiperState) {
            this.wiperState = wiperState;
            addProperty(wiperState.setIdentifier(IDENTIFIER_WIPERS_STATE));
            return this;
        }

        /**
         * @param wiperIntensity The wipers intensity.
         * @return The builder.
         */
        public Builder setWiperIntensity(Property<WiperIntensity> wiperIntensity) {
            this.wiperIntensity = wiperIntensity;
            addProperty(wiperIntensity.setIdentifier(IDENTIFIER_WIPER_INTENSITY));
            return this;
        }

        /**
         * @param windscreenDamage The windscreen damage.
         * @return The builder.
         */
        public Builder setWindscreenDamage(Property<WindscreenDamage> windscreenDamage) {
            this.windscreenDamage = windscreenDamage;
            addProperty(windscreenDamage.setIdentifier(IDENTIFIER_WINDSCREEN_DAMAGE));
            return this;
        }

        /**
         * @param windscreenDamageZone The windscreen damage position, as viewed from inside the
         *                             car.
         * @return The builder.
         */
        public Builder setWindscreenDamageZone(Property<WindscreenDamageZone> windscreenDamageZone) {
            this.windscreenDamageZone = windscreenDamageZone;
            addProperty(windscreenDamageZone.setIdentifier(IDENTIFIER_DAMAGE_ZONE));
            return this;
        }

        /**
         * @param windscreenDamageZoneMatrix The windscreen damage zone matrix, as viewed from
         *                                   inside the car.
         * @return The builder.
         */
        public Builder setWindscreenDamageZoneMatrix(Property<WindscreenDamageZoneMatrix>
                                                             windscreenDamageZoneMatrix) {
            this.windscreenDamageZoneMatrix = windscreenDamageZoneMatrix;
            addProperty(windscreenDamageZoneMatrix.setIdentifier(IDENTIFIER_DAMAGE_ZONE_MATRIX));
            return this;
        }

        /**
         * @param windscreenReplacementState The windscreen replacement state.
         * @return The builder.
         */
        public Builder setWindscreenReplacementState(Property<WindscreenReplacementState>
                                                             windscreenReplacementState) {
            this.windscreenReplacementState = windscreenReplacementState;
            addProperty(windscreenReplacementState.setIdentifier(WINDSCREEN_REPLACEMENT_STATE_IDENTIFIER));
            return this;
        }

        /**
         * @param damageConfidence The damage confidence.
         * @return The builder.
         */
        public Builder setDamageConfidence(Property<Double> damageConfidence) {
            this.damageConfidence = damageConfidence;
            damageConfidence.setIdentifier(IDENTIFIER_DAMAGE_CONFIDENCE);
            addProperty(damageConfidence);
            return this;
        }

        /**
         * @param damageDetectionTime The damage detection time.
         * @return The builder.
         */
        public Builder setDamageDetectionTime(Property<Calendar> damageDetectionTime) {
            this.damageDetectionTime = damageDetectionTime;
            addProperty(damageDetectionTime.setIdentifier(IDENTIFIER_DAMAGE_DETECTION_TIME));
            return this;
        }

        public WindscreenState build() {
            return new WindscreenState(this);
        }
    }
}