package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.incoming.LightsState;
import com.highmobility.byteutils.Bytes;

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

    public Lights(byte[] bytes) throws CommandParseException {
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

        rearExteriorLightActive = Bytes.getBool(bytes[4]);
        interiorLightActive = Bytes.getBool(bytes[5]);
    }
}