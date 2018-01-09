package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.utils.Bytes;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class AccelerationProperty extends Property {
    public enum AccelerationType {
        LONGITUDINAL((byte)0x00),
        LATERAL((byte)0x01),
        FRONT_LATERAL((byte)0x02),
        REAR_LATERAL((byte)0x03);

        public static AccelerationType fromByte(byte byteValue) throws CommandParseException {
            AccelerationType[] values = AccelerationType.values();

            for (int i = 0; i < values.length; i++) {
                AccelerationType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        AccelerationType(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }

    AccelerationType accelerationType;
    float acceleration;

    /**
     *
     * @return The acceleration type
     */
    public AccelerationType getAccelerationType() {
        return accelerationType;
    }

    /**
     *
     * @return The acceleration in g-force
     */
    public float getAcceleration() {
        return acceleration;
    }

    public AccelerationProperty(byte[] bytes) throws CommandParseException {
        super(bytes);

        accelerationType = AccelerationType.fromByte(bytes[3]);
        acceleration = Property.getFloat(Arrays.copyOfRange(bytes, 4, 8));
    }

    public AccelerationProperty(byte identifier, AccelerationType type, float acceleration) throws UnsupportedEncodingException {
        super(identifier, 5);
        bytes[3] = type.getByte();
        Bytes.setBytes(bytes, Property.floatToBytes(acceleration), 4);
    }
}