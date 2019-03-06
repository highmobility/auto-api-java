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
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.property.Property;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Send a notification to the car or smart device. The notification can have action items that the
 * user can respond with.
 */
public class Notification extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.NOTIFICATIONS, 0x00);

    private static final byte TEXT_IDENTIFIER = 0x01;
    private static final byte RECEIVED_ACTION_IDENTIFIER = 0x10;

    Property<String> text;
    ActionItem[] actions;
    PropertyInteger receivedAction = new PropertyInteger(RECEIVED_ACTION_IDENTIFIER,
            false);

    /**
     * @return Notification text.
     */
    @Nullable public Property<String> getText() {
        return text;
    }

    /**
     * @return Notification action items.
     */
    public ActionItem[] getActions() {
        return actions;
    }

    @Nullable public ActionItem getAction(int actionIdentifier) {
        if (actions == null) return null;

        for (int i = 0; i < actions.length; i++) {
            if (actions[i].getActionIdentifier() == actionIdentifier) return actions[i];
        }

        return null;
    }

    /**
     * @return The received action.
     */
    @Nullable public PropertyInteger getReceivedAction() {
        return receivedAction;
    }

    /**
     * @param text           Notification text.
     * @param actions        Notification action items.
     * @param receivedAction The received action.
     */
    public Notification(String text, ActionItem[] actions, @Nullable Integer receivedAction) {
        super(TYPE);

        ArrayList<Property> properties = new ArrayList<>();

        this.text = new Property(String.class, TEXT_IDENTIFIER, text);
        properties.add(this.text);

        this.actions = actions;
        properties.addAll(Arrays.asList(actions));

        if (receivedAction != null) {
            this.receivedAction.update(RECEIVED_ACTION_IDENTIFIER, false, 1, receivedAction);
            properties.add(this.receivedAction);
        }

        createBytes(properties);
    }

    Notification(byte[] bytes) {
        super(bytes);
        ArrayList<ActionItem> actionsBuilder = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case TEXT_IDENTIFIER:
                        text = new Property(String.class, p);
                        return text;
                    case ActionItem.IDENTIFIER:
                        ActionItem item = new ActionItem(p.getByteArray());
                        actionsBuilder.add(item);
                        return item;
                    case RECEIVED_ACTION_IDENTIFIER:
                        return receivedAction.update(p);
                }

                return null;
            });
        }

        actions = actionsBuilder.toArray(new ActionItem[0]);
    }

    private Notification(Builder builder) {
        super(builder);
        actions = builder.actions.toArray(new ActionItem[0]);
        text = builder.text;
        receivedAction = builder.receivedAction;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<ActionItem> actions = new ArrayList<>();
        Property<String> text;
        PropertyInteger receivedAction;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param actions The notification action items.
         * @return The builder.
         */
        public Builder setActions(ActionItem[] actions) {
            this.actions = Arrays.asList(actions);
            for (int i = 0; i < actions.length; i++) {
                addProperty(actions[i]);
            }
            return this;
        }

        /**
         * Add a single notification action item.
         *
         * @param action The notification action item.
         * @return The builder.
         */
        public Builder addAction(ActionItem action) {
            this.actions.add(action);
            addProperty(action);
            return this;
        }

        /**
         * @param text The notification text.
         * @return The builder.
         */
        public Builder setText(Property<String> text) {
            this.text = text;
            text.setIdentifier(TEXT_IDENTIFIER);
            addProperty(text);
            return this;
        }

        /**
         * @param receivedAction The received action.
         * @return The builder.
         */
        public Builder setReceivedAction(PropertyInteger receivedAction) {
            this.receivedAction = receivedAction;
            receivedAction.update(RECEIVED_ACTION_IDENTIFIER, false, 1);
            addProperty(receivedAction);
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}
