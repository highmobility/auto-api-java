// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActionItem;
import java.util.ArrayList;
import javax.annotation.Nullable;

/**
 * Notification
 */
public class Notification extends SetCommand {
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
     * @param text The Text for the notification
     * @param actionItems The action items
     */
    public Notification(String text, @Nullable ActionItem[] actionItems) {
        super(Identifier.NOTIFICATIONS);
    
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