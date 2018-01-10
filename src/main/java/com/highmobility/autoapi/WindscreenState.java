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
public class WindscreenState extends CommandWithProperties {
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