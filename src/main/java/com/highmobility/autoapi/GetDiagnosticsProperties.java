// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific diagnostics properties.
 */
public class GetDiagnosticsProperties extends GetCommand {
    public GetDiagnosticsProperties(byte[] propertyIdentifiers) {
        super(Identifier.DIAGNOSTICS, propertyIdentifiers);
    }
}