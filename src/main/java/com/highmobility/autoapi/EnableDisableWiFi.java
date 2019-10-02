// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.EnabledState;

/**
 * Enable disable wi fi
 */
public class EnableDisableWiFi extends SetCommand {
    Property<EnabledState> status = new Property(EnabledState.class, 0x01);

    /**
     * @return The status
     */
    public Property<EnabledState> getStatus() {
        return status;
    }
    
    /**
     * Enable disable wi fi
     *
     * @param status The status
     */
    public EnableDisableWiFi(EnabledState status) {
        super(Identifier.WI_FI);
    
        addProperty(this.status.update(status), true);
    }

    EnableDisableWiFi(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return status.update(p);
                }
                return null;
            });
        }
        if (this.status.getValue() == null) 
            throw new NoPropertiesException();
    }
}