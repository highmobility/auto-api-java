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

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * This message is sent when a Get Lights State message is received by the car.
 */
public class LightsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.LIGHTS, 0x01);

    public enum FrontExteriorLightState {
        INACTIVE((byte)(0x00)),
        ACTIVE((byte)0x01),
        ACTIVE_WITH_FULL_BEAM((byte)0x02);

        public static FrontExteriorLightState fromByte(byte value) throws CommandParseException {
            FrontExteriorLightState[] values = FrontExteriorLightState.values();

            for (int i = 0; i < values.length; i++) {
                FrontExteriorLightState capability = values[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        FrontExteriorLightState(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    FrontExteriorLightState frontExteriorLightState;
    Boolean rearExteriorLightActive;
    Boolean interiorLightActive;
    int[] ambientColor;

    /**
     *
     * @return Front exterior light state
     */
    public FrontExteriorLightState getFrontExteriorLightState() {
        return frontExteriorLightState;
    }

    /**
     *
     * @return Rear exterior light state
     */
    public Boolean isRearExteriorLightActive() {
        return rearExteriorLightActive;
    }

    /**
     *
     * @return Interior light state
     */
    public Boolean isInteriorLightActive() {
        return interiorLightActive;
    }

    /**
     *
     * @return Ambient color in rgba values from 0-255
     */
    public int[] getAmbientColor() {
        return ambientColor;
    }

    public LightsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    frontExteriorLightState = FrontExteriorLightState.fromByte(property.getValueByte());
                    break;
                case 0x02:
                    rearExteriorLightActive = Property.getBool(property.getValueByte());
                    break;
                case 0x03:
                    interiorLightActive = Property.getBool(property.getValueByte());
                    break;
                case 0x04:
                    byte[] valueBytes = property.getValueBytes();
                    if (valueBytes.length != 3) throw new CommandParseException();
                    ambientColor = new int[4];

                    ambientColor[0] = valueBytes[0] & 0xFF;
                    ambientColor[1] = valueBytes[1] & 0xFF;
                    ambientColor[2] = valueBytes[2] & 0xFF;
                    ambientColor[3] = 255;
                    break;
            }
        }
    }
}