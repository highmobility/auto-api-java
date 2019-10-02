// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.ParkingTicketState.Status;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

/**
 * End parking
 */
public class EndParking extends SetCommand {
    Property<Status> status = new Property(Status.class, 0x01);

    /**
     * End parking
     */
    public EndParking() {
        super(Identifier.PARKING_TICKET);
    
        addProperty(status.addValueComponent(new Bytes("00")), true);
    }

    EndParking(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: status.update(p);
                }
                return null;
            });
        }
        if ((status.getValue() == null || status.getValueComponent().getValueBytes().equals(new Bytes("00")) == false)) 
            throw new NoPropertiesException();
    }
}