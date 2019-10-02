// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.PropertyInteger;

/**
 * Action
 */
public class Action extends SetCommand {
    PropertyInteger activatedAction = new PropertyInteger(0x03, false);

    /**
     * @return The activated action
     */
    public PropertyInteger getActivatedAction() {
        return activatedAction;
    }
    
    /**
     * Action
     *
     * @param activatedAction The Identifier of the activated action
     */
    public Action(Integer activatedAction) {
        super(Identifier.NOTIFICATIONS);
    
        addProperty(this.activatedAction.update(false, 1, activatedAction), true);
    }

    Action(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x03: return activatedAction.update(p);
                }
                return null;
            });
        }
        if (this.activatedAction.getValue() == null) 
            throw new NoPropertiesException();
    }
}