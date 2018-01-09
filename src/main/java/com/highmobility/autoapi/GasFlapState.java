package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.WindowState;

import java.util.ArrayList;
import java.util.List;

/**
 * This message is sent when a Get Gas Flap State message is received by the car.
 */
public class GasFlapState extends Command {
    public static final Type TYPE = new Type(Identifier.FUELING, 0x01);

    com.highmobility.autoapi.property.GasFlapState state;

    /**
     *
     * @return The gas flap state
     */
    public com.highmobility.autoapi.property.GasFlapState getState() {
        return state;
    }

    public GasFlapState(byte[] bytes) throws CommandParseException {
        super(bytes);

        List<WindowState> builder = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    state = com.highmobility.autoapi.property.GasFlapState.fromByte(property.getValueByte());
                    break;
            }
        }
    }
}