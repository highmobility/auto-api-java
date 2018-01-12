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