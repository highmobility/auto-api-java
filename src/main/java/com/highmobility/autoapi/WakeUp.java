// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

/**
 * Wake up
 */
public class WakeUp extends SetCommand {
    Property<Status> status = new Property(Status.class, 0x01);

    /**
     * Wake up
     */
    public WakeUp() {
        super(Identifier.WAKE_UP);
    
        addProperty(status.addValueComponent(new Bytes("00")), true);
    }

    WakeUp(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: status.update(p);
                }
                return null;
            });
        }
        if ((status.getValue() == null || status.getValueComponent().getValueBytes().equals(new Bytes("00")) == false)) 
            throw new NoPropertiesException();
    }

    public enum Status implements ByteEnum {
        WAKE_UP((byte) 0x00);
    
        public static Status fromByte(byte byteValue) throws CommandParseException {
            Status[] values = Status.values();
    
            for (int i = 0; i < values.length; i++) {
                Status state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Status(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}