// TODO: license

package com.highmobility.autoapi;

/**
 * Get all race properties.
 */
public class GetRaceState extends GetCommand {
    public GetRaceState() {
        super(Identifier.RACE);
    }

    GetRaceState(byte[] bytes) {
        super(bytes);
    }
}