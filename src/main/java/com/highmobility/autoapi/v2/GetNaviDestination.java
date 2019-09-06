// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all navi destination properties.
 */
public class GetNaviDestination extends GetCommand {
    public GetNaviDestination() {
        super(Identifier.NAVI_DESTINATION, getStateIdentifiers());
    }

    GetNaviDestination(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 };
    }
}