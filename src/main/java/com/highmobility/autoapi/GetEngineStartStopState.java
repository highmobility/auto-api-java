// TODO: license

package com.highmobility.autoapi;

/**
 * Get all engine start stop properties.
 */
public class GetEngineStartStopState extends GetCommand {
    public GetEngineStartStopState() {
        super(Identifier.ENGINE_START_STOP);
    }

    GetEngineStartStopState(byte[] bytes) {
        super(bytes);
    }
}