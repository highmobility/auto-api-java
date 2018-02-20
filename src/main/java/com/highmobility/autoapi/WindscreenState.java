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
import com.highmobility.autoapi.property.WindscreenDamage;
import com.highmobility.autoapi.property.WindscreenDamageZone;
import com.highmobility.autoapi.property.WindscreenDamageZoneMatrix;
import com.highmobility.autoapi.property.WindscreenReplacementState;
import com.highmobility.autoapi.property.WiperIntensity;
import com.highmobility.autoapi.property.WiperState;

import java.util.Calendar;

/**
 * This message is sent when a Get Windscreen State message is received by the car. The wipers
 * intensity is indicated even if the car has automatic wipers activated.
 */
public class WindscreenState extends CommandWithExistingProperties {
    public static final Type TYPE = new Type(Identifier.WINDSCREEN, 0x01);

    WiperState wiperState;
    WiperIntensity wiperIntensity;
    WindscreenDamage windscreenDamage;
    WindscreenDamageZone windscreenDamageZone;
    WindscreenDamageZoneMatrix windscreenDamageZoneMatrix;
    WindscreenReplacementState windscreenReplacementState;
    Float damageConfidence;
    Calendar damageDetectionTime;

    /**
     *
     * @return Wiper state
     */
    public WiperState getWiperState() {
        return wiperState;
    }

    /**
     *
     * @return Wiper intensity
     */
    public WiperIntensity getWiperIntensity() {
        return wiperIntensity;
    }

    /**
     *
     * @return Windscreen damage
     */
    public WindscreenDamage getWindscreenDamage() {
        return windscreenDamage;
    }

    /**
     *
     * @return Windscreen damage position, as viewed from inside the car.
     */
    public WindscreenDamageZone getWindscreenDamageZone() {
        return windscreenDamageZone;
    }

    /**
     *
     * @return The Windscreen Zone Matrix
     */
    public WindscreenDamageZoneMatrix getWindscreenDamageZoneMatrix() {
        return windscreenDamageZoneMatrix;
    }

    /**
     *
     * @return Windscreen replacement state
     */
    public WindscreenReplacementState getWindscreenReplacementState() {
        return windscreenReplacementState;
    }

    /**
     *
     * @return Damage confidence
     */
    public float getDamageConfidence() {
        return damageConfidence;
    }

    /**
     *
     * @return Damage detection time
     */
    public Calendar getDamageDetectionTime() {
        return damageDetectionTime;
    }

    public WindscreenState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    // active
                    wiperState = WiperState.fromByte(property.getValueByte());
                    break;
                case 0x02:
                    // intensity
                    wiperIntensity = WiperIntensity.fromByte(property.getValueByte());
                    break;
                case 0x03:
                    // damage
                    windscreenDamage = WindscreenDamage.fromByte(property.getValueByte());
                    break;
                case 0x04:
                    // zone matrix
                    windscreenDamageZoneMatrix = new WindscreenDamageZoneMatrix(property.getValueByte());
                    break;
                case 0x05:
                    // damage zone
                    windscreenDamageZone = new WindscreenDamageZone(property.getValueByte());
                    break;
                case 0x06:
                    windscreenReplacementState = WindscreenReplacementState.fromByte(property.getValueByte());
                    // replacement
                    break;
                case 0x07:
                    damageConfidence = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case 0x08:
                    damageDetectionTime = Property.getCalendar(property.getValueBytes());
                    // detection time
                    break;

            }
        }
    }
}