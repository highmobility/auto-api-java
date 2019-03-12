package com.highmobility.autoapi.value.charging;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.PropertyValueSingleByte;

public enum PlugType implements PropertyValueSingleByte {
    TYPE_1((byte) 0x00),
    TYPE_2((byte) 0x01),
    COMBINED_CHARGING_SYSTEM((byte) 0x02),
    CHA_DE_MO((byte) 0x03);

    public static PlugType fromByte(byte byteValue) throws CommandParseException {
        PlugType[] values = PlugType.values();

        for (int i = 0; i < values.length; i++) {
            PlugType state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    PlugType(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }


}
