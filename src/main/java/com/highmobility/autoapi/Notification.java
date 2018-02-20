/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

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
public class Notification extends CommandWithExistingProperties {
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
