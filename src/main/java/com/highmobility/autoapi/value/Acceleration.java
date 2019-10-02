// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

public class Acceleration extends PropertyValueObject {
    public static final int SIZE = 5;

    Direction direction;
    Float gForce;

    /**
     * @return The direction.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @return The accelaration in g-force.
     */
    public Float getGForce() {
        return gForce;
    }

    public Acceleration(Direction direction, Float gForce) {
        super(5);
        update(direction, gForce);
    }

    public Acceleration(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public Acceleration() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 5) throw new CommandParseException();

        int bytePosition = 0;
        direction = Direction.fromByte(get(bytePosition));
        bytePosition += 1;

        gForce = Property.getFloat(bytes, bytePosition);
    }

    public void update(Direction direction, Float gForce) {
        this.direction = direction;
        this.gForce = gForce;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, direction.getByte());
        bytePosition += 1;

        set(bytePosition, Property.floatToBytes(gForce));
    }

    public void update(Acceleration value) {
        update(value.direction, value.gForce);
    }

    @Override public int getLength() {
        return 1 + 4;
    }

    public enum Direction implements ByteEnum {
        LONGITUDINAL((byte) 0x00),
        LATERAL((byte) 0x01),
        FRONT_LATERAL((byte) 0x02),
        REAR_LATERAL((byte) 0x03);
    
        public static Direction fromByte(byte byteValue) throws CommandParseException {
            Direction[] values = Direction.values();
    
            for (int i = 0; i < values.length; i++) {
                Direction state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Direction(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}