// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific parking ticket properties.
 */
public class GetParkingTicketProperties extends GetCommand {
    public GetParkingTicketProperties(byte[] propertyIdentifiers) {
        super(Identifier.PARKING_TICKET, propertyIdentifiers);
    }
}