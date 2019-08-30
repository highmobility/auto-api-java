// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;
import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Acceleration extends PropertyValueObject {
    public static final int SIZE = 5;

    Type type;
    float gForce;

    /**
     * @return The type.
     */
    public Type getType() {
        return type;
    }

    /**
     * @return The accelaration in g-force per IEEE 754.
     */
    public float getGForce() {
        return gForce;
    }

    public Acceleration(Type type, float gForce) {
        super(5);
        update(type, gForce);
    }

    public Acceleration() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 5) throw new CommandParseException();

        int bytePosition = 0;
        type = Type.fromByte(get(bytePosition));
        bytePosition += 1;

        gForce = Property.getFloat(bytes, bytePosition);
    }

    public void update(Type type, float gForce) {
        this.type = type;
        this.gForce = gForce;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, type.getByte());
        bytePosition += 1;

        set(bytePosition, Property.floatToBytes(gForce));
    }

    public void update(Acceleration value) {
        update(value.type, value.gForce);
    }

    @Override public int getLength() {
        return 1 + 4;
    }

    public enum Type {
        LONGITUDINAL((byte)0x00),
        LATERAL((byte)0x01),
        FRONT_LATERAL((byte)0x02),
        REAR_LATERAL((byte)0x03);
    
        public static Type fromByte(byte byteValue) throws CommandParseException {
            Type[] values = Type.values();
    
            for (int i = 0; i < values.length; i++) {
                Type state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Type(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }
}