package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ByteProperty;
import com.highmobility.autoapi.property.HMProperty;

import java.util.ArrayList;

/**
 * Unlock/Lock and Open/Close the trunk. The result is not received by the ack but instead sent
 * through the evented Trunk State message.
 */
public class OpenCloseTrunk extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.TRUNK_ACCESS, 0x02);

    /**
     * Create the command from lock state and position. One of the properties can be null.
     *
     * @param state The trunk lock state.
     * @param position The trunk position.
     * @return The command bytes
     * @throws IllegalArgumentException If all arguments are null
     */
    public OpenCloseTrunk(TrunkState.LockState state, TrunkState.Position position) {
         super(TYPE, getProperties(state, position));
    }

    static HMProperty[] getProperties(TrunkState.LockState state, TrunkState.Position position) {
        ArrayList<HMProperty> properties = new ArrayList<>();

        if (state != null) {
            properties.add(new ByteProperty((byte) 0x01, state.getByte()));
        }

        if (position!= null) {
            properties.add(new ByteProperty((byte) 0x02, position.getByte()));
        }

        return properties.toArray(new HMProperty[properties.size()]);
    }

    OpenCloseTrunk(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
