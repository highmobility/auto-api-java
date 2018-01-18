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

package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Property;
import com.highmobility.autoapi.incoming.LightsState;
import com.highmobility.utils.Bytes;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class Lights extends FeatureState {
    LightsState.FrontExteriorLightState frontExteriorLightState;
    boolean rearExteriorLightActive;
    boolean interiorLightActive;

    /**
     *
     * @return Front exterior light state
     */
    public LightsState.FrontExteriorLightState getFrontExteriorLightState() {
        return frontExteriorLightState;
    }

    /**
     *
     * @return Rear exterior light state
     */
    public boolean isRearExteriorLightActive() {
        return rearExteriorLightActive;
    }

    /**
     *
     * @return Interior light state
     */
    public boolean isInteriorLightActive() {
        return interiorLightActive;
    }

    public Lights(LightsState.FrontExteriorLightState frontExteriorLightState,
                  boolean rearExteriorLightActive,
                  boolean interiorLightActive) {
        super(Command.Identifier.LIGHTS);
        this.frontExteriorLightState = frontExteriorLightState;
        this.rearExteriorLightActive = rearExteriorLightActive;
        this.interiorLightActive = interiorLightActive;

        bytes = getBytesWithOneByteLongFields(3);
        bytes[3] = frontExteriorLightState.getByte();
        bytes[4] = Property.boolToByte(rearExteriorLightActive);
        bytes[5] = Property.boolToByte(interiorLightActive);
    }

    Lights(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.LIGHTS);

        if (bytes.length != 6) throw new CommandParseException();

        if (bytes[3] == 0x00) {
            frontExteriorLightState = LightsState.FrontExteriorLightState.INACTIVE;
        }
        else if (bytes[3] == 0x01) {
            frontExteriorLightState = LightsState.FrontExteriorLightState.ACTIVE;
        }
        else if (bytes[3] == 0x02) {
            frontExteriorLightState = LightsState.FrontExteriorLightState.ACTIVE_WITH_FULL_BEAM;
        }

        rearExteriorLightActive = Property.getBool(bytes[4]);
        interiorLightActive = Property.getBool(bytes[5]);

        this.bytes = bytes;
    }
}