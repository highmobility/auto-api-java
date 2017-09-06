package com.highmobility.autoapi.incoming;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.byteutils.Bytes;

/**
 * Created by ttiganik on 13/09/16.
 */
public class IgnitionState extends IncomingCommand {
    boolean on;

    /**
     *
     * @return the ignition state
     */
    public boolean isOn() {
        return on;
    }

    public IgnitionState(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length < 4) throw new CommandParseException();

        on = Bytes.getBool(bytes[3]);
    }
}
