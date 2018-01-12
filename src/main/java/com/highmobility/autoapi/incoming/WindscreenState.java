package com.highmobility.autoapi.incoming;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Property;
import com.highmobility.autoapi.WindscreenDamagePosition;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by ttiganik on 13/09/16.
 */
public class WindscreenState extends IncomingCommand {
    public enum WiperState {
        INACTIVE((byte)0x00),
        ACTIVE((byte)0x01),
        AUTOMATIC((byte)0x02);

        public static WiperState fromByte(byte byteValue) throws CommandParseException {
            WiperState[] values = WiperState.values();

            for (int i = 0; i < values.length; i++) {
                WiperState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        WiperState(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }
    public enum WiperIntensity {
        LEVEL_0((byte)0x00),
        LEVEL_1((byte)0x01),
        LEVEL_2((byte)0x02),
        LEVEL_3((byte)0x03);

        public static WiperIntensity fromByte(byte byteValue) throws CommandParseException {
            WiperIntensity[] values = WiperIntensity.values();

            for (int i = 0; i < values.length; i++) {
                WiperIntensity state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        WiperIntensity(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }
    public enum WindscreenDamage {
        NO_IMPACT((byte)0x00),
        IMPACT_NO_DAMAGE((byte)0x01),
        DAMAGE_SMALLER_THAN_1((byte)0x02),
        DAMAGE_LARGER_THAN_1((byte)0x03);

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

        private byte capabilityByte;

        WindscreenDamage(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    public enum WindscreenReplacementState {
        UNKNOWN((byte)0x00),
        REPLACEMENT_NOT_NEEDED((byte)0x01),
        REPLACEMENT_NEEDED((byte)0x02);

        public static WindscreenReplacementState fromByte(byte byteValue) throws CommandParseException {
            WindscreenReplacementState[] values = WindscreenReplacementState.values();

            for (int i = 0; i < values.length; i++) {
                WindscreenReplacementState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        WindscreenReplacementState(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    WiperState wiperState;
    WiperIntensity wiperIntensity;
    WindscreenDamage windscreenDamage;
    WindscreenReplacementState windscreenReplacementState;
    WindscreenDamagePosition windscreenDamagePosition;
    float damageConfidence;
    Date damageDetectionTime;

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
     * @return Windscreen replacement state
     */
    public WindscreenReplacementState getWindscreenReplacementState() {
        return windscreenReplacementState;
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

    public WindscreenState(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length < 16) throw new CommandParseException();

        wiperState = WiperState.fromByte(bytes[3]);
        wiperIntensity = WiperIntensity.fromByte(bytes[4]);
        windscreenDamage = WindscreenDamage.fromByte(bytes[5]);

        windscreenReplacementState = WindscreenReplacementState.fromByte(bytes[8]);
        damageConfidence = (float)Property.getUnsignedInt(bytes[9]) / 100f;

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
