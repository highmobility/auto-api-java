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