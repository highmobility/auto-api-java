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
import com.highmobility.autoapi.value.ActionItem;
import java.util.ArrayList;
import javax.annotation.Nullable;

/**
 * Notification
 */
public class Notification extends SetCommand {
    public static final Identifier identifier = Identifier.NOTIFICATIONS;

    Property<String> text = new Property(String.class, 0x01);
    @Nullable Property<ActionItem>[] actionItems;

    /**
     * @return The text
     */
    public Property<String> getText() {
        return text;
    }
    
    /**
     * @return The action items
     */
    public @Nullable Property<ActionItem>[] getActionItems() {
        return actionItems;
    }
    
    /**
     * Notification
     *
     * @param text Text for the notification
     * @param actionItems The action items
     */
    public Notification(String text, @Nullable ActionItem[] actionItems) {
        super(identifier);
    
        addProperty(this.text.update(text));
        ArrayList<Property> actionItemsBuilder = new ArrayList<>();
        if (actionItems != null) {
            for (ActionItem actionItem : actionItems) {
                Property prop = new Property(0x02, actionItem);
                actionItemsBuilder.add(prop);
                addProperty(prop, true);
            }
        }
        this.actionItems = actionItemsBuilder.toArray(new Property[0]);
    }

    Notification(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<ActionItem>> actionItemsBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return text.update(p);
                    case 0x02: {
                        Property actionItem = new Property(ActionItem.class, p);
                        actionItemsBuilder.add(actionItem);
                        return actionItem;
                    }
                }
                return null;
            });
        }
    
        actionItems = actionItemsBuilder.toArray(new Property[0]);
        if (this.text.getValue() == null) 
            throw new NoPropertiesException();
    }
}