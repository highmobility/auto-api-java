// TODO: license

package com.highmobility.autoapi;

/**
 * Get all navi destination properties.
 */
public class GetNaviDestination extends GetCommand {
    public GetNaviDestination() {
        super(Identifier.NAVI_DESTINATION);
    }

    GetNaviDestination(byte[] bytes) {
        super(bytes);
    }
}