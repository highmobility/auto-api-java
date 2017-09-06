package com.highmobility.autoapi.vehiclestatus;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class VideoHandover extends FeatureState {
    public VideoHandover(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.VIDEO_HANDOVER);

        if (bytes.length < 5) throw new CommandParseException();

    }
}