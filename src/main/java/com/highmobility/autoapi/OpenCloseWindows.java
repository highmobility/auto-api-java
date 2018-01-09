package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.WindowProperty;
import com.highmobility.autoapi.property.WindowState;

/**
 * Open or close the windows. Either one or all windows can be controlled with the same message. The
 * result is not received by the ack but instead sent through the evented Windows State message.
 */
public class OpenCloseWindows extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDOWS, 0x02);

    WindowState[] windowStates;

    public WindowState[] getWindowStates() {
        return windowStates;
    }

    public OpenCloseWindows(WindowState[] windowStates) throws CommandParseException {
        super(TYPE, getProperties(windowStates));
        this.windowStates = windowStates;
    }

    OpenCloseWindows(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length != 4) throw new CommandParseException();
    }

    static Property[] getProperties(WindowState[] windowStates) throws CommandParseException {
        if (windowStates == null) throw new CommandParseException();
        WindowProperty[] properties = new WindowProperty[windowStates.length];

        for (int i = 0; i < windowStates.length; i++) {
            WindowState state = windowStates[i];
            WindowProperty property = new WindowProperty(state);
            properties[i] = property;
        }

        return properties;
    }
}
