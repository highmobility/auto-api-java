// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.NotificationsState.Clear;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

/**
 * Clear notification
 */
public class ClearNotification extends SetCommand {
    Property<Clear> clear = new Property(Clear.class, 0x04);

    /**
     * Clear notification
     */
    public ClearNotification() {
        super(Identifier.NOTIFICATIONS);
    
        addProperty(clear.addValueComponent(new Bytes("00")), true);
    }

    ClearNotification(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x04: clear.update(p);
                }
                return null;
            });
        }
        if ((clear.getValue() == null || clear.getValueComponent().getValueBytes().equals(new Bytes("00")) == false)) 
            throw new NoPropertiesException();
    }
}