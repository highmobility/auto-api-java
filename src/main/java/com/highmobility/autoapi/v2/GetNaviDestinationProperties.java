// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific navi destination properties.
 */
public class GetNaviDestinationProperties extends GetCommand {
    public GetNaviDestinationProperties(byte[] propertyIdentifiers) {
        super(Identifier.NAVI_DESTINATION, propertyIdentifiers);
    }
}