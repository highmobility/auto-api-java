package com.highmobility.autoapi.incoming;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Property;
import com.highmobility.utils.Bytes;

/**
 * Created by ttiganik on 13/09/16.
 */
public class LightsState extends IncomingCommand {
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
    boolean rearExteriorLightActive;
    boolean interiorLightActive;
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

    /**
     *
     * @return Ambient color in rgba values from 0-255
     */
    public int[] getAmbientColor() {
        return ambientColor;
    }

    public LightsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length != 9) throw new CommandParseException();

        if (bytes[3] == 0x00) {
            frontExteriorLightState = FrontExteriorLightState.INACTIVE;
        }
        else if (bytes[3] == 0x01) {
            frontExteriorLightState = FrontExteriorLightState.ACTIVE;
        }
        else if (bytes[3] == 0x02) {
            frontExteriorLightState = FrontExteriorLightState.ACTIVE_WITH_FULL_BEAM;
        }

        rearExteriorLightActive = Property.getBool(bytes[4]);
        interiorLightActive = Property.getBool(bytes[5]);

        ambientColor = new int[4];

        ambientColor[0] = bytes[6] & 0xFF;
        ambientColor[1] = bytes[7] & 0xFF;
        ambientColor[2] = bytes[8] & 0xFF;
        ambientColor[3] = 255;
    }
}
