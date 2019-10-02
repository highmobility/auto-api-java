// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Forget network
 */
public class ForgetNetwork extends SetCommand {
    Property<String> networkSSID = new Property(String.class, 0x03);

    /**
     * @return The network ssid
     */
    public Property<String> getNetworkSSID() {
        return networkSSID;
    }
    
    /**
     * Forget network
     *
     * @param networkSSID The The network SSID
     */
    public ForgetNetwork(String networkSSID) {
        super(Identifier.WI_FI);
    
        addProperty(this.networkSSID.update(networkSSID), true);
    }

    ForgetNetwork(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x03: return networkSSID.update(p);
                }
                return null;
            });
        }
        if (this.networkSSID.getValue() == null) 
            throw new NoPropertiesException();
    }
}