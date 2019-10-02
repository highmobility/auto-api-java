// TODO: license

package com.highmobility.autoapi;

/**
 * Get all cruise control properties.
 */
public class GetCruiseControlState extends GetCommand {
    public GetCruiseControlState() {
        super(Identifier.CRUISE_CONTROL);
    }

    GetCruiseControlState(byte[] bytes) {
        super(bytes);
    }
}