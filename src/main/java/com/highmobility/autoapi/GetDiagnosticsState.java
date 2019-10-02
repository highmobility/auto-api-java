// TODO: license

package com.highmobility.autoapi;

/**
 * Get all diagnostics properties.
 */
public class GetDiagnosticsState extends GetCommand {
    public GetDiagnosticsState() {
        super(Identifier.DIAGNOSTICS);
    }

    GetDiagnosticsState(byte[] bytes) {
        super(bytes);
    }
}