// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.TheftAlarmState.Status;
import com.highmobility.autoapi.property.Property;

/**
 * Set theft alarm
 */
public class SetTheftAlarm extends SetCommand {
    Property<Status> status = new Property(Status.class, 0x01);

    /**
     * @return The status
     */
    public Property<Status> getStatus() {
        return status;
    }
    
    /**
     * Set theft alarm
     *
     * @param status The status
     */
    public SetTheftAlarm(Status status) {
        super(Identifier.THEFT_ALARM);
    
        addProperty(this.status.update(status), true);
    }

    SetTheftAlarm(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return status.update(p);
                }
                return null;
            });
        }
        if (this.status.getValue() == null) 
            throw new NoPropertiesException();
    }
}