// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.NetworkSecurity;
import javax.annotation.Nullable;

/**
 * Connect to network
 */
public class ConnectToNetwork extends SetCommand {
    Property<String> networkSSID = new Property(String.class, 0x03);
    Property<NetworkSecurity> networkSecurity = new Property(NetworkSecurity.class, 0x04);
    @Nullable Property<String> password = new Property(String.class, 0x05);

    /**
     * @return The network ssid
     */
    public Property<String> getNetworkSSID() {
        return networkSSID;
    }
    
    /**
     * @return The network security
     */
    public Property<NetworkSecurity> getNetworkSecurity() {
        return networkSecurity;
    }
    
    /**
     * @return The password
     */
    public @Nullable Property<String> getPassword() {
        return password;
    }
    
    /**
     * Connect to network
     *
     * @param networkSSID The The network SSID
     * @param networkSecurity The network security
     * @param password The The network password
     */
    public ConnectToNetwork(String networkSSID, NetworkSecurity networkSecurity, @Nullable String password) {
        super(Identifier.WI_FI);
    
        addProperty(this.networkSSID.update(networkSSID));
        addProperty(this.networkSecurity.update(networkSecurity));
        addProperty(this.password.update(password), true);
    }

    ConnectToNetwork(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x03: return networkSSID.update(p);
                    case 0x04: return networkSecurity.update(p);
                    case 0x05: return password.update(p);
                }
                return null;
            });
        }
        if (this.networkSSID.getValue() == null ||
            this.networkSecurity.getValue() == null) 
            throw new NoPropertiesException();
    }
}