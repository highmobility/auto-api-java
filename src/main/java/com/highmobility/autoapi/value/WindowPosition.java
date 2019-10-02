// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

public class WindowPosition extends PropertyValueObject {
    public static final int SIZE = 2;

    WindowLocation windowLocation;
    Position position;

    /**
     * @return The window location.
     */
    public WindowLocation getWindowLocation() {
        return windowLocation;
    }

    /**
     * @return The position.
     */
    public Position getPosition() {
        return position;
    }

    public WindowPosition(WindowLocation windowLocation, Position position) {
        super(2);
        update(windowLocation, position);
    }

    public WindowPosition(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public WindowPosition() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        windowLocation = WindowLocation.fromByte(get(bytePosition));
        bytePosition += 1;

        position = Position.fromByte(get(bytePosition));
    }

    public void update(WindowLocation windowLocation, Position position) {
        this.windowLocation = windowLocation;
        this.position = position;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, windowLocation.getByte());
        bytePosition += 1;

        set(bytePosition, position.getByte());
    }

    public void update(WindowPosition value) {
        update(value.windowLocation, value.position);
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum Position implements ByteEnum {
        CLOSED((byte) 0x00),
        OPEN((byte) 0x01),
        INTERMEDIATE((byte) 0x02);
    
        public static Position fromByte(byte byteValue) throws CommandParseException {
            Position[] values = Position.values();
    
            for (int i = 0; i < values.length; i++) {
                Position state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Position(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}