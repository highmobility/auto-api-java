package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ActionItem;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Send a notification to the car or smart device. The notification can have action items that the
 * user can respond with.
 */
public class Notification extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.NOTIFICATIONS, 0x00);

    String text;
    ActionItem[] actions;

    /**
     * @return Notification text
     */
    public String getText() {
        return text;
    }

    /**
     * @return Notification actions
     */
    public ActionItem[] getActions() {
        return actions;
    }

    public ActionItem getAction(int actionIdentifier) {
        if (actions == null) return null;

        for (int i = 0; i < actions.length; i++) {
            if (actions[i].getActionIdentifier() == actionIdentifier) return actions[i];
        }

        return null;
    }

    public Notification(String text, ActionItem[] actions) throws
            UnsupportedEncodingException, CommandParseException {
        super(TYPE, getProperties(text, actions));
        this.text = text;
        this.actions = actions;
    }

    Notification(byte[] bytes) throws CommandParseException {
        super(bytes);
        ArrayList<ActionItem> actionsBuilder = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01: {
                    try {
                        text = Property.getString(property.getValueBytes());
                    } catch (UnsupportedEncodingException e) {
                        throw new CommandParseException(CommandParseException.CommandExceptionCode.UNSUPPORTED_VALUE_TYPE);
                    }
                    break;
                }
                case 0x02: {
                    try {
                        actionsBuilder.add(new ActionItem(property.getPropertyBytes()));
                    } catch (UnsupportedEncodingException e) {
                        throw new CommandParseException(CommandParseException.CommandExceptionCode.UNSUPPORTED_VALUE_TYPE);
                    }
                    break;
                }
            }
        }

        actions = actionsBuilder.toArray(new ActionItem[actionsBuilder.size()]);
    }

    static HMProperty[] getProperties(String text, ActionItem[] actions) throws
            UnsupportedEncodingException {
        HMProperty[] properties = new HMProperty[actions.length + 1];
        properties[0] = new StringProperty((byte) 0x01, text);

        for (int i = 0; i < actions.length; i++) {
            properties[i + 1] = actions[i];
        }

        return properties;
    }
}
