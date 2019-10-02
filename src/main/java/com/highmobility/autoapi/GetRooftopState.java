// TODO: license

package com.highmobility.autoapi;

/**
 * Get all rooftop control properties.
 */
public class GetRooftopState extends GetCommand {
    public GetRooftopState() {
        super(Identifier.ROOFTOP_CONTROL);
    }

    GetRooftopState(byte[] bytes) {
        super(bytes);
    }
}