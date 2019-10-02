// TODO: license

package com.highmobility.autoapi;

/**
 * Get all driver fatigue properties.
 */
public class GetDriverFatigueState extends GetCommand {
    public GetDriverFatigueState() {
        super(Identifier.DRIVER_FATIGUE);
    }

    GetDriverFatigueState(byte[] bytes) {
        super(bytes);
    }
}