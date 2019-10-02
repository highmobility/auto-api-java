// TODO: license

package com.highmobility.autoapi;

/**
 * Get all dashboard lights properties.
 */
public class GetDashboardLights extends GetCommand {
    public GetDashboardLights() {
        super(Identifier.DASHBOARD_LIGHTS);
    }

    GetDashboardLights(byte[] bytes) {
        super(bytes);
    }
}