package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

import java.io.UnsupportedEncodingException;

/**
 * This message is sent when a Get Firmware Version is received by the car.
 */
public class FirmwareVersion extends Command {
    public static final Type TYPE = new Type(Identifier.FIRMWARE_VERSION, 0x01);

    String carSDKVersion;
    String carSDKBuild;
    String applicationVersion;

    public String getCarSDKVersion() {
        return carSDKVersion;
    }

    public String getCarSDKBuild() {
        return carSDKBuild;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public FirmwareVersion(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    byte[] value = property.getValueBytes();
                    carSDKVersion = (int)value[0] + "." +
                                    (int)value[1] + "." +
                                    (int)value[2];
                    break;
                case 0x02:
                    try {
                        carSDKBuild = Property.getString(property.getValueBytes());
                    } catch (UnsupportedEncodingException e) {
                        throw new CommandParseException(CommandParseException.CommandExceptionCode.UNSUPPORTED_VALUE_TYPE);
                    }
                    break;
                case 0x03:
                    try {
                        applicationVersion = Property.getString(property.getValueBytes());
                    } catch (UnsupportedEncodingException e) {
                        throw new CommandParseException(CommandParseException.CommandExceptionCode.UNSUPPORTED_VALUE_TYPE);
                    }
                    break;
            }
        }
    }
}