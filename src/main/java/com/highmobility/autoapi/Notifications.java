/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActionItem;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.property.ByteEnum;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import com.highmobility.value.Bytes;

import static com.highmobility.utils.ByteUtils.hexFromByte;

/**
 * The Notifications capability
 */
public class Notifications {
    public static final int IDENTIFIER = Identifier.NOTIFICATIONS;

    public static final byte PROPERTY_TEXT = 0x01;
    public static final byte PROPERTY_ACTION_ITEMS = 0x02;
    public static final byte PROPERTY_ACTIVATED_ACTION = 0x03;
    public static final byte PROPERTY_CLEAR = 0x04;

    /**
     * The notifications state
     */
    public static class State extends SetCommand {
        Property<String> text = new Property<>(String.class, PROPERTY_TEXT);
        List<Property<ActionItem>> actionItems;
        PropertyInteger activatedAction = new PropertyInteger(PROPERTY_ACTIVATED_ACTION, false);
        Property<Clear> clear = new Property<>(Clear.class, PROPERTY_CLEAR);
    
        /**
         * @return Text for the notification
         */
        public Property<String> getText() {
            return text;
        }
    
        /**
         * @return The action items
         */
        public List<Property<ActionItem>> getActionItems() {
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
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            final ArrayList<Property<ActionItem>> actionItemsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_TEXT: return text.update(p);
                        case PROPERTY_ACTION_ITEMS:
                            Property<ActionItem> actionItem = new Property<>(ActionItem.class, p);
                            actionItemsBuilder.add(actionItem);
                            return actionItem;
                        case PROPERTY_ACTIVATED_ACTION: return activatedAction.update(p);
                        case PROPERTY_CLEAR: return clear.update(p);
                    }
    
                    return null;
                });
            }
    
            actionItems = actionItemsBuilder;
        }
    
        private State(Builder builder) {
            super(builder);
    
            text = builder.text;
            actionItems = builder.actionItems;
            activatedAction = builder.activatedAction;
            clear = builder.clear;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<String> text;
            private final List<Property<ActionItem>> actionItems = new ArrayList<>();
            private PropertyInteger activatedAction;
            private Property<Clear> clear;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param text Text for the notification
             * @return The builder
             */
            public Builder setText(Property<String> text) {
                this.text = text.setIdentifier(PROPERTY_TEXT);
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
                actionItem.setIdentifier(PROPERTY_ACTION_ITEMS);
                addProperty(actionItem);
                actionItems.add(actionItem);
                return this;
            }
            
            /**
             * @param activatedAction Identifier of the activated action
             * @return The builder
             */
            public Builder setActivatedAction(Property<Integer> activatedAction) {
                this.activatedAction = new PropertyInteger(PROPERTY_ACTIVATED_ACTION, false, 1, activatedAction);
                addProperty(this.activatedAction);
                return this;
            }
            
            /**
             * @param clear The clear
             * @return The builder
             */
            public Builder setClear(Property<Clear> clear) {
                this.clear = clear.setIdentifier(PROPERTY_CLEAR);
                addProperty(this.clear);
                return this;
            }
        }
    }

    /**
     * Notification
     */
    public static class Notification extends SetCommand {
        Property<String> text = new Property<>(String.class, PROPERTY_TEXT);
        List<Property<ActionItem>> actionItems;
    
        /**
         * @return The text
         */
        public Property<String> getText() {
            return text;
        }
        
        /**
         * @return The action items
         */
        public List<Property<ActionItem>> getActionItems() {
            return actionItems;
        }
        
        /**
         * Notification
         *
         * @param text Text for the notification
         * @param actionItems The action items
         */
        public Notification(String text, @Nullable List<ActionItem> actionItems) {
            super(IDENTIFIER);
        
            addProperty(this.text.update(text));
            final ArrayList<Property<ActionItem>> actionItemsBuilder = new ArrayList<>();
            if (actionItems != null) {
                for (ActionItem actionItem : actionItems) {
                    Property<ActionItem> prop = new Property<>(0x02, actionItem);
                    actionItemsBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.actionItems = actionItemsBuilder;
            createBytes();
        }
    
        Notification(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
        
            final ArrayList<Property<ActionItem>> actionItemsBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_TEXT: return text.update(p);
                        case PROPERTY_ACTION_ITEMS: {
                            Property<ActionItem> actionItem = new Property<>(ActionItem.class, p);
                            actionItemsBuilder.add(actionItem);
                            return actionItem;
                        }
                    }
                    return null;
                });
            }
        
            actionItems = actionItemsBuilder;
            if (this.text.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Action
     */
    public static class Action extends SetCommand {
        PropertyInteger activatedAction = new PropertyInteger(PROPERTY_ACTIVATED_ACTION, false);
    
        /**
         * @return The activated action
         */
        public PropertyInteger getActivatedAction() {
            return activatedAction;
        }
        
        /**
         * Action
         *
         * @param activatedAction Identifier of the activated action
         */
        public Action(Integer activatedAction) {
            super(IDENTIFIER);
        
            addProperty(this.activatedAction.update(false, 1, activatedAction));
            createBytes();
        }
    
        Action(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_ACTIVATED_ACTION: return activatedAction.update(p);
                    }
                    return null;
                });
            }
            if (this.activatedAction.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Clear notification
     */
    public static class ClearNotification extends SetCommand {
        Property<Clear> clear = new Property<>(Clear.class, PROPERTY_CLEAR);
    
        /**
         * Clear notification
         */
        public ClearNotification() {
            super(IDENTIFIER);
        
            addProperty(clear.addValueComponent(new Bytes("00")));
            createBytes();
        }
    
        ClearNotification(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CLEAR: clear.update(p);
                    }
                    return null;
                });
            }
            if ((clear.getValue() == null || clear.getValueComponent().getValueBytes().equals(new Bytes("00")) == false)) 
                throw new NoPropertiesException();
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
    
            throw new CommandParseException("Enum Clear does not contain " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        Clear(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}