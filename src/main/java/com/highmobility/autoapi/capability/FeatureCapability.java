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

package com.highmobility.autoapi.capability;

import com.highmobility.autoapi.Command.Identifier;
import com.highmobility.autoapi.CommandParseException;

/**
 * Created by ttiganik on 14/10/2016.
 */
public class FeatureCapability {
    Identifier identifier;

    FeatureCapability(Identifier identifier) {
        this.identifier = identifier;
    }

    public static FeatureCapability fromBytes(byte[] capabilityBytes) throws CommandParseException {
        if (capabilityBytes.length < 4) throw new CommandParseException();

        FeatureCapability featureCapability = null;
        Identifier feature = Identifier.fromIdentifier(capabilityBytes[0], capabilityBytes[1]);

        if (feature == Identifier.DOOR_LOCKS
                || feature == Identifier.CHARGING
                || feature == Identifier.VALET_MODE
                || feature == Identifier.ENGINE
                || feature == Identifier.THEFT_ALARM
                || feature == Identifier.PARKING_TICKET) {
            featureCapability = new AvailableGetStateCapability(feature, capabilityBytes);
        }
        else if (feature == Identifier.TRUNK_ACCESS) {
            featureCapability = new TrunkAccessCapability(capabilityBytes);
        }
        else if (feature == Identifier.WAKE_UP
                || feature == Identifier.REMOTE_CONTROL
                || feature == Identifier.HEART_RATE
                || feature == Identifier.VEHICLE_LOCATION
                || feature == Identifier.VEHICLE_TIME
                || feature == Identifier.NAVI_DESTINATION
                || feature == Identifier.DELIVERED_PARCELS
                || feature == Identifier.DIAGNOSTICS
                || feature == Identifier.MAINTENANCE
                || feature == Identifier.DRIVER_FATIGUE
                || feature == Identifier.VIDEO_HANDOVER
                || feature == Identifier.BROWSER
                || feature == Identifier.GRAPHICS
                || feature == Identifier.TEXT_INPUT
                || feature == Identifier.WINDOWS
                || feature == Identifier.KEYFOB_POSITION
                || feature == Identifier.FUELING) {
            featureCapability =  new AvailableCapability(feature, capabilityBytes);
        }
        else if (feature == Identifier.CLIMATE) {
            featureCapability = new ClimateCapability(capabilityBytes);
        }
        else if (feature == Identifier.ROOFTOP) {
            featureCapability = new RooftopCapability(capabilityBytes);
        }
        else if (feature == Identifier.HONK_FLASH) {
            featureCapability = new HonkFlashCapability(capabilityBytes);
        }
        else if (feature == Identifier.LIGHTS) {
            featureCapability = new LightsCapability(capabilityBytes);
        }
        else if (feature == Identifier.MESSAGING) {
            featureCapability = new MessagingCapability(capabilityBytes);
        }
        else if (feature == Identifier.NOTIFICATIONS) {
            featureCapability = new NotificationsCapability(capabilityBytes);
        }
        else if (feature == Identifier.WINDSCREEN) {
            featureCapability = new WindscreenCapability(capabilityBytes);
        }

        return featureCapability;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public byte[] getBytes() {return null;} // override this

    byte[] getBytesWithCapabilityCount(int capabilityCount) {
        byte[] bytes = new byte[3 + capabilityCount];
        bytes[0] = identifier.getIdentifier()[0];
        bytes[1] = identifier.getIdentifier()[1];
        bytes[2] = (byte) capabilityCount;
        return bytes;
    }
}
