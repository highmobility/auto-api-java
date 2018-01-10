package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.WindowState;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an evented message that is sent from the car every time the windows state changes. This
 * message is also sent when a Get Windows State is received by the car. The new status is included
 * in the message payload and may be the result of user, device or car triggered action.
 */
public class WindowsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDOWS, 0x01);

    WindowState[] windowStates;

    /**
     * @return All of the window states
     */
    public WindowState[] getWindowStates() {
        return windowStates;
    }

    public WindowState getWindowState(WindowState.Position position) {
        for (int i = 0; i < windowStates.length; i++) {
            if (windowStates[i].getPosition() == position) return windowStates[i];
        }

        return null;
    }

    public WindowsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        List<WindowState> builder = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    WindowState state = new WindowState(property.getValueBytes()[0], property
                            .getValueBytes()[1]);
                    builder.add(state);
                    break;
            }
        }

        windowStates = builder.toArray(new WindowState[builder.size()]);
    }
}