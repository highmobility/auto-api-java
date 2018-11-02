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
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

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

    String text;
    ActionItem[] actions;
    Integer receivedAction;

    /**
     * @return Notification text.
     */
    @Nullable public String getText() {
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
    @Nullable public Integer getReceivedAction() {
        return receivedAction;
    }

    public Notification(String text, ActionItem[] actions, Integer receivedAction) {
        super(TYPE, getProperties(text, actions, receivedAction));
        this.text = text;
        this.actions = actions;
        this.receivedAction = receivedAction;
    }

    Notification(byte[] bytes) throws CommandParseException {
        super(bytes);
        ArrayList<ActionItem> actionsBuilder = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case TEXT_IDENTIFIER:
                    text = Property.getString(property.getValueBytes());
                    break;

                case ActionItem.IDENTIFIER:
                    actionsBuilder.add(new ActionItem(property.getPropertyBytes()));
                    break;

                case RECEIVED_ACTION_IDENTIFIER:
                    receivedAction = Property.getSignedInt(property.getValueByte());
                    break;

            }
        }

        actions = actionsBuilder.toArray(new ActionItem[actionsBuilder.size()]);
    }

    static HMProperty[] getProperties(String text, ActionItem[] actions, Integer receivedAction) {
        HMProperty[] properties = new HMProperty[actions.length + 2];
        properties[0] = new StringProperty(TEXT_IDENTIFIER, text);

        for (int i = 0; i < actions.length; i++) {
            properties[i + 1] = actions[i];
        }

        properties[properties.length - 1] = new IntegerProperty(RECEIVED_ACTION_IDENTIFIER,
                receivedAction, 1);

        return properties;
    }

    private Notification(Builder builder) {
        super(builder);
        actions = builder.actions.toArray(new ActionItem[builder.actions.size()]);
        text = builder.text;
        receivedAction = builder.receivedAction;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<ActionItem> actions = new ArrayList<>();
        String text;
        Integer receivedAction;

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
        public Builder setText(String text) {
            this.text = text;
            addProperty(new StringProperty(TEXT_IDENTIFIER, text));
            return this;
        }

        /**
         * @param receivedAction The received action.
         * @return The builder.
         */
        public Builder setReceivedAction(Integer receivedAction) {
            this.receivedAction = receivedAction;
            addProperty(new IntegerProperty(RECEIVED_ACTION_IDENTIFIER, receivedAction, 1));
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}
