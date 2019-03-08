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
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Send a notification to the car or smart device. The notification can have action items that the
 * user can respond with.
 */
public class Notification extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.NOTIFICATIONS, 0x00);

    private static final byte IDENTIFIER_TEXT = 0x01;
    private static final byte IDENTIFIER_ACTION_ITEM = 0x02;
    private static final byte IDENTIFIER_RECEIVED_ACTION = 0x10;

    Property<String> text = new Property(String.class, IDENTIFIER_TEXT);
    Property<ActionItem>[] actions;
    PropertyInteger receivedAction = new PropertyInteger(IDENTIFIER_RECEIVED_ACTION, false);

    /**
     * @return Notification text.
     */
    public Property<String> getText() {
        return text;
    }

    /**
     * @return Notification action items.
     */
    public Property<ActionItem>[] getActions() {
        return actions;
    }

    @Nullable public Property<ActionItem> getAction(int actionIdentifier) {
        if (actions == null) return null;

        for (int i = 0; i < actions.length; i++) {
            if (actions[i].getValue() != null &&
                    actions[i].getValue().getActionIdentifier() == actionIdentifier)
                return actions[i];
        }

        return null;
    }

    /**
     * @return The received action.
     */
    public Property<Integer> getReceivedAction() {
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
        List<Property<ActionItem>> actionItemsBuilder = new ArrayList<>();

        this.text.update(text);
        properties.add(this.text);

        for (ActionItem action : actions) {
            Property p = new Property(IDENTIFIER_ACTION_ITEM, action);
            actionItemsBuilder.add(p);
            properties.add(p);
        }

        this.actions = actionItemsBuilder.toArray(new Property[0]);

        if (receivedAction != null) {
            this.receivedAction.update(false, 1, receivedAction);
            properties.add(this.receivedAction);
        }

        createBytes(properties);
    }

    Notification(byte[] bytes) {
        super(bytes);
        ArrayList<Property> actionsBuilder = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_TEXT:
                        return text.update(p);
                    case IDENTIFIER_ACTION_ITEM:
                        Property item = new Property(ActionItem.class, p);
                        actionsBuilder.add(item);
                        return item;
                    case IDENTIFIER_RECEIVED_ACTION:
                        return receivedAction.update(p);
                }

                return null;
            });
        }

        actions = actionsBuilder.toArray(new Property[0]);
    }

    private Notification(Builder builder) {
        super(builder);
        actions = builder.actions.toArray(new Property[0]);
        text = builder.text;
        receivedAction = builder.receivedAction;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<Property<ActionItem>> actions = new ArrayList<>();
        Property<String> text;
        PropertyInteger receivedAction;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param actions The notification action items.
         * @return The builder.
         */
        public Builder setActions(Property<ActionItem>[] actions) {
            this.actions.clear();
            for (int i = 0; i < actions.length; i++) {
                addAction(actions[i]);
            }
            return this;
        }

        /**
         * Add a single notification action item.
         *
         * @param action The notification action item.
         * @return The builder.
         */
        public Builder addAction(Property<ActionItem> action) {
            this.actions.add(action);
            addProperty(action.setIdentifier(IDENTIFIER_ACTION_ITEM));
            return this;
        }

        /**
         * @param text The notification text.
         * @return The builder.
         */
        public Builder setText(Property<String> text) {
            this.text = text;
            text.setIdentifier(IDENTIFIER_TEXT);
            addProperty(text);
            return this;
        }

        /**
         * @param receivedAction The received action.
         * @return The builder.
         */
        public Builder setReceivedAction(PropertyInteger receivedAction) {
            this.receivedAction = receivedAction;
            receivedAction.update(IDENTIFIER_RECEIVED_ACTION, false, 1);
            addProperty(receivedAction);
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}
