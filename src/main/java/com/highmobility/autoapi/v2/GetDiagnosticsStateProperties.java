// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific diagnostics properties.
 */
public class GetDiagnosticsStateProperties extends GetCommand {
    public GetDiagnosticsStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.DIAGNOSTICS, propertyIdentifiers);
    }
}