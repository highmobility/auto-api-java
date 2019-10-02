// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.HomeChargerState.AuthenticationState;
import com.highmobility.autoapi.property.Property;

/**
 * Authenticate expire
 */
public class AuthenticateExpire extends SetCommand {
    Property<AuthenticationState> authenticationState = new Property(AuthenticationState.class, 0x0d);

    /**
     * @return The authentication state
     */
    public Property<AuthenticationState> getAuthenticationState() {
        return authenticationState;
    }
    
    /**
     * Authenticate expire
     *
     * @param authenticationState The authentication state
     */
    public AuthenticateExpire(AuthenticationState authenticationState) {
        super(Identifier.HOME_CHARGER);
    
        addProperty(this.authenticationState.update(authenticationState), true);
    }

    AuthenticateExpire(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x0d: return authenticationState.update(p);
                }
                return null;
            });
        }
        if (this.authenticationState.getValue() == null) 
            throw new NoPropertiesException();
    }
}