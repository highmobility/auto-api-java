// TODO: license

package com.highmobility.autoapi;

/**
 * Get all tachograph properties.
 */
public class GetTachographState extends GetCommand {
    public GetTachographState() {
        super(Identifier.TACHOGRAPH);
    }

    GetTachographState(byte[] bytes) {
        super(bytes);
    }
}