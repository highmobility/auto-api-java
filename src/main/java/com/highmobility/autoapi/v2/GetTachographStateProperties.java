// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific tachograph properties.
 */
public class GetTachographStateProperties extends GetCommand {
    public GetTachographStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.TACHOGRAPH, propertyIdentifiers);
    }
}