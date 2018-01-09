package com.highmobility.autoapi;

import com.highmobility.autoapi.property.CapabilityProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;

/**
 * This message is sent when a Get Capabilities message is received by the car. The capabilities are
 * passed along as an array.
 */
public class Capabilities extends Command {
    public static final Type TYPE = new Type(Identifier.CAPABILITIES, 0x01);

    CapabilityProperty[] capabilities;

    /*
    // TODO
    public static byte[] getCommandBytes(CapabilityProperty[] capabilities) {
        byte[] bytes = Identifier.CAPABILITIES.getBytesWithType(TYPE);

        bytes = Bytes.concatBytes(bytes, (byte) capabilities.length);

        for (int i = 0; i < capabilities.length; i++) {
            byte[] capabilityBytes = capabilities[i].getAllBytes();
            bytes = Bytes.concatBytes(bytes, capabilityBytes);
        }

        return bytes;
    }
*/
    public boolean isSupported(Type type) {
        CapabilityProperty capability = getCapability(type);
        if (capability == null) return false;

        return capability.isSupported(type);
    }

    /**
     * @param type The type of the capability.
     * @return The capability if exists, otherwise null
     */
    public CapabilityProperty getCapability(Type type) {
        for (int i = 0; i < capabilities.length; i++) {
            CapabilityProperty capa = capabilities[i];
            if (capa.getIdentifier()[0] == type.getIdentifier()[0]
                    && capa.getIdentifier()[1] == type.getIdentifier()[1]) {
                return capa;
            }
        }

        return null;
    }

    /**
     * @return All of the Capabilities that are available for the vehicle.
     */
    public CapabilityProperty[] getCapabilities() {
        return capabilities;
    }

    public Capabilities(byte[] bytes) throws CommandParseException {
        super(bytes);
        ArrayList<CapabilityProperty> builder = new ArrayList<>();
        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    try {
                        // to make the map work, all capabilities need to be mapped to a constant key.
                        // cannot get from map with a byte[]
                        CapabilityProperty capability = new CapabilityProperty(property.getPropertyBytes());
                        builder.add(capability);
                    }
                    catch (CommandParseException e) {
                        // don't use unknown capability
                    }
                    break;
            }
        }

        capabilities = builder.toArray(new CapabilityProperty[builder.size()]);
    }
}