// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific tachograph properties.
 */
public class GetTachographProperties extends GetCommand {
    public GetTachographProperties(byte[] propertyIdentifiers) {
        super(Identifier.TACHOGRAPH, propertyIdentifiers);
    }
}