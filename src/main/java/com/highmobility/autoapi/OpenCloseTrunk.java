package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ByteProperty;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.TrunkLockState;
import com.highmobility.autoapi.property.TrunkPosition;

import java.util.ArrayList;

/**
 * Unlock/Lock and Open/Close the trunk. The result is not received by the ack but instead sent
 * through the evented Trunk State message.
 */
public class OpenCloseTrunk extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.TRUNK_ACCESS, 0x02);


    TrunkLockState state;
    TrunkPosition position;

    /**
     *
     * @return The requested trunk lock state
     */
    public TrunkLockState getState() {
        return state;
    }

    /**
     *
     * @return The requested trunk position
     */
    public TrunkPosition getPosition() {
        return position;
    }

    /**
     * Create the command from lock state and position. One of the properties can be null.
     *
     * @param state The trunk lock state.
     * @param position The trunk position.
     *
     * @throws IllegalArgumentException If all arguments are null
     */
    public OpenCloseTrunk(TrunkLockState state, TrunkPosition position) {
         super(TYPE, getProperties(state, position));
         this.state = state;
         this.position = position;
    }

    static HMProperty[] getProperties(TrunkLockState state, TrunkPosition position) {
        ArrayList<HMProperty> properties = new ArrayList<>();

        if (state != null) {
            properties.add(new ByteProperty(TrunkLockState.defaultIdentifier, state.getByte()));
        }

        if (position!= null) {
            properties.add(new ByteProperty(TrunkPosition.defaultIdentifier, position.getByte()));
        }

        return properties.toArray(new HMProperty[properties.size()]);
    }

    OpenCloseTrunk(byte[] bytes) throws CommandParseException {
        super(bytes);
        for (int i = 0; i < properties.length; i++) {
            Property property = properties[i];
            if (property.getPropertyIdentifier() == TrunkLockState.defaultIdentifier) {
                state = TrunkLockState.fromByte(property.getValueByte());
            }
            else if (property.getPropertyIdentifier() == TrunkPosition.defaultIdentifier) {
                position = TrunkPosition.fromByte(property.getValueByte());
            }
        }
    }
}
