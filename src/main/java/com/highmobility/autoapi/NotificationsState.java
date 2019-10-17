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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.value.ActionItem;
import com.highmobility.autoapi.property.PropertyInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * The notifications state
 */
public class NotificationsState extends SetCommand {
    public static final int IDENTIFIER = Identifier.NOTIFICATIONS;

    public static final byte IDENTIFIER_TEXT = 0x01;
    public static final byte IDENTIFIER_ACTION_ITEMS = 0x02;
    public static final byte IDENTIFIER_ACTIVATED_ACTION = 0x03;
    public static final byte IDENTIFIER_CLEAR = 0x04;

    Property<String> text = new Property(String.class, IDENTIFIER_TEXT);
    Property<ActionItem>[] actionItems;
    PropertyInteger activatedAction = new PropertyInteger(IDENTIFIER_ACTIVATED_ACTION, false);
    Property<Clear> clear = new Property(Clear.class, IDENTIFIER_CLEAR);

    /**
     * @return Text for the notification
     */
    public Property<String> getText() {
        return text;
    }

    /**
     * @return The action items
     */
    public Property<ActionItem>[] getActionItems() {
        return actionItems;
    }

    /**
     * @return Identifier of the activated action
     */
    public PropertyInteger getActivatedAction() {
        return activatedAction;
    }

    /**
     * @return The clear
     */
    public Property<Clear> getClear() {
        return clear;
    }

    NotificationsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> actionItemsBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_TEXT: return text.update(p);
                    case IDENTIFIER_ACTION_ITEMS:
                        Property<ActionItem> actionItem = new Property(ActionItem.class, p);
                        actionItemsBuilder.add(actionItem);
                        return actionItem;
                    case IDENTIFIER_ACTIVATED_ACTION: return activatedAction.update(p);
                    case IDENTIFIER_CLEAR: return clear.update(p);
                }

                return null;
            });
        }

        actionItems = actionItemsBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private NotificationsState(Builder builder) {
        super(builder);

        text = builder.text;
        actionItems = builder.actionItems.toArray(new Property[0]);
        activatedAction = builder.activatedAction;
        clear = builder.clear;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<String> text;
        private List<Property> actionItems = new ArrayList<>();
        private PropertyInteger activatedAction;
        private Property<Clear> clear;

        public Builder() {
            super(IDENTIFIER);
        }

        public NotificationsState build() {
            return new NotificationsState(this);
        }

        /**
         * @param text Text for the notification
         * @return The builder
         */
        public Builder setText(Property<String> text) {
            this.text = text.setIdentifier(IDENTIFIER_TEXT);
            addProperty(this.text);
            return this;
        }
        
        /**
         * Add an array of action items.
         * 
         * @param actionItems The action items
         * @return The builder
         */
        public Builder setActionItems(Property<ActionItem>[] actionItems) {
            this.actionItems.clear();
            for (int i = 0; i < actionItems.length; i++) {
                addActionItem(actionItems[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single action item.
         * 
         * @param actionItem The action item
         * @return The builder
         */
        public Builder addActionItem(Property<ActionItem> actionItem) {
            actionItem.setIdentifier(IDENTIFIER_ACTION_ITEMS);
            addProperty(actionItem);
            actionItems.add(actionItem);
            return this;
        }
        
        /**
         * @param activatedAction Identifier of the activated action
         * @return The builder
         */
        public Builder setActivatedAction(Property<Integer> activatedAction) {
            this.activatedAction = new PropertyInteger(IDENTIFIER_ACTIVATED_ACTION, false, 1, activatedAction);
            addProperty(this.activatedAction);
            return this;
        }
        
        /**
         * @param clear The clear
         * @return The builder
         */
        public Builder setClear(Property<Clear> clear) {
            this.clear = clear.setIdentifier(IDENTIFIER_CLEAR);
            addProperty(this.clear);
            return this;
        }
    }

    public enum Clear implements ByteEnum {
        CLEAR((byte) 0x00);
    
        public static Clear fromByte(byte byteValue) throws CommandParseException {
            Clear[] values = Clear.values();
    
            for (int i = 0; i < values.length; i++) {
                Clear state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Clear(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}