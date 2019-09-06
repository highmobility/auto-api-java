// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all dashboard lights properties.
 */
public class GetDashboardLights extends GetCommand {
    public GetDashboardLights() {
        super(Identifier.DASHBOARD_LIGHTS, getStateIdentifiers());
    }

    GetDashboardLights(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}