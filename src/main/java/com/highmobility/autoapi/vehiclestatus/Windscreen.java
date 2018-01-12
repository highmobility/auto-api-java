package com.highmobility.autoapi.vehiclestatus;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Property;
import com.highmobility.autoapi.WindscreenDamagePosition;
import com.highmobility.autoapi.incoming.WindscreenState;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class Windscreen extends FeatureState {
    WindscreenState.WiperState wiperState;
    WindscreenState.WiperIntensity wiperIntensity;
    WindscreenState.WindscreenDamage windscreenDamage;
    WindscreenDamagePosition windscreenDamagePosition;
    WindscreenState.WindscreenReplacementState windscreenReplacementState;
    float damageConfidence;
    Date damageDetectionTime;

    /**
     *
     * @return Wiper state
     */
    public WindscreenState.WiperState getWiperState() {
        return wiperState;
    }

    /**
     *
     * @return Wiper intensity
     */
    public WindscreenState.WiperIntensity getWiperIntensity() {
        return wiperIntensity;
    }

    /**
     *
     * @return Windscreen damage
     */
    public WindscreenState.WindscreenDamage getWindscreenDamage() {
        return windscreenDamage;
    }

    /**
     *
     * @return Windscreen damage position, as viewed from inside the car.
     *          null if unavailable
     */
    public WindscreenDamagePosition getWindscreenDamagePosition() {
        return windscreenDamagePosition;
    }

    /**
     *
     * @return Windscreen replacement state
     */
    public WindscreenState.WindscreenReplacementState getWindscreenReplacementState() {
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
    public Date getDamageDetectionTime() {
        return damageDetectionTime;
    }

    public Windscreen(WindscreenState.WiperState wiperState,
                      WindscreenState.WiperIntensity wiperIntensity,
                      WindscreenState.WindscreenDamage windscreenDamage,
                      WindscreenDamagePosition windscreenDamagePosition,
                      WindscreenState.WindscreenReplacementState windscreenReplacementState,
                      float damageConfidence,
                      Date damageDetectionTime) {
        super(Command.Identifier.WINDSCREEN);

        this.wiperState = wiperState;
        this.wiperIntensity = wiperIntensity;
        this.windscreenDamage = windscreenDamage;
        this.windscreenDamagePosition = windscreenDamagePosition;
        this.windscreenReplacementState = windscreenReplacementState;
        this.damageConfidence = damageConfidence;
        this.damageDetectionTime = damageDetectionTime;

        bytes = getBytesWithMoreThanOneByteLongFields(7, 4);
    }

    public Windscreen(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.WINDSCREEN);

        if (bytes.length < 16) throw new CommandParseException();

        wiperState = WindscreenState.WiperState.fromByte(bytes[3]);
        wiperIntensity = WindscreenState.WiperIntensity.fromByte(bytes[4]);
        windscreenDamage = WindscreenState.WindscreenDamage.fromByte(bytes[5]);

        windscreenReplacementState = WindscreenState.WindscreenReplacementState.fromByte(bytes[8]);
        damageConfidence = (float) Property.getUnsignedInt(bytes[9]) / 100f;

        if (bytes[6] != 0x00) {
            int horizontalSize = bytes[6] >> 4;
            int verticalSize = bytes[6] & 0x0F;

            int horizontalDamagePosition = bytes[7] >> 4;
            int verticalDamagePosition = bytes[7] & 0x0F;

            windscreenDamagePosition = new WindscreenDamagePosition(horizontalSize,
                    verticalSize,
                    horizontalDamagePosition,
                    verticalDamagePosition);
        }

        damageDetectionTime = Property.getDate(Arrays.copyOfRange(bytes, 10, 10 + 6));
    }
}