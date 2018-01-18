/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi.incoming;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.capability.FeatureCapability;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.utils.Bytes;

import java.util.Arrays;

/**
 * Created by ttiganik on 14/09/16.
 *
 * This command is sent when a Get Capabilities command is received by the car.
 */
public class Capabilities extends IncomingCommand {
    FeatureCapability[] capabilities;

    /**
     * Create the capabilities command bytes
     *
     * @param capabilities the capabilities
     * @return command bytes
     */
    public static byte[] getCommandBytes(FeatureCapability[] capabilities) {
        byte[] bytes = Command.Capabilities.CAPABILITIES.getIdentifierAndType();
        bytes = Bytes.concatBytes(bytes, (byte)capabilities.length);

        for (int i = 0; i < capabilities.length; i++) {
            byte[] capabilityBytes = capabilities[i].getBytes();
            bytes = Bytes.concatBytes(bytes, capabilityBytes);
        }                    

        return bytes;
    }

    /**
     *
     * @return All of the Capabilities that are available for the vehicle.
     */
    public FeatureCapability[] getCapabilities() {
        return capabilities;
    }

    public Capabilities(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length < 4) throw new CommandParseException();

        int capabilitiesCount = bytes[3];
        if (capabilitiesCount == 0) return;

        capabilities = new FeatureCapability[capabilitiesCount];
        int knownCapabilitiesCount = 0;
        int capabilityPosition = 4;

        for (int i = 0; i < capabilitiesCount; i++) {
            int capabilityLength = bytes[capabilityPosition + 2];
            byte[] capabilityBytes = Arrays.copyOfRange(bytes, capabilityPosition,
                        capabilityPosition + 3 + capabilityLength); // length = 2x identifier byte + length byte + bytes
            FeatureCapability featureCapability = FeatureCapability.fromBytes(capabilityBytes);

            capabilities[i] = featureCapability;
            capabilityPosition += capabilityLength + 3;
            if (featureCapability != null) {
                knownCapabilitiesCount++;
            }
            else {
                knownCapabilitiesCount ++;
                knownCapabilitiesCount --;
            }
        }

        if (capabilitiesCount != knownCapabilitiesCount) {
            // resize the array if any of the capabilities is unknown(null)
            FeatureCapability[] trimmedCapabilities = new FeatureCapability[knownCapabilitiesCount];
            int trimmedCapabilitiesPosition = 0;
            for (int i = 0; i < capabilitiesCount; i++) {
                FeatureCapability featureCapability = capabilities[i];
                if (featureCapability != null) {
                    trimmedCapabilities[trimmedCapabilitiesPosition] = featureCapability;
                    trimmedCapabilitiesPosition++;
                }
            }

            capabilities = trimmedCapabilities;
        }
    }
}
