// TODO: license

package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;

/**
 * End parking
 */
public class EndParking extends SetCommand {

    /**
     * End parking
     */
    public EndParking() {
        super(Identifier.PARKING_TICKET);

        addProperty(new Property((byte) 0x01, new byte[] {0x00}));
    }

    EndParking(byte[] bytes) {
        super(bytes);
    }
}