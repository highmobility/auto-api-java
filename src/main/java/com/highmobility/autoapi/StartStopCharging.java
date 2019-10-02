// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.ChargingState.Status;
import com.highmobility.autoapi.property.Property;

/**
 * Start stop charging
 */
public class StartStopCharging extends SetCommand {
    Property<Status> status = new Property(Status.class, 0x17);

    /**
     * @return The status
     */
    public Property<Status> getStatus() {
        return status;
    }
    
    /**
     * Start stop charging
     *
     * @param status The status
     */
    public StartStopCharging(Status status) {
        super(Identifier.CHARGING);
    
        if (status == Status.CHARGING_COMPLETE ||
            status == Status.INITIALISING ||
            status == Status.CHARGING_PAUSED ||
            status == Status.CHARGING_ERROR) throw new IllegalArgumentException();
    
        addProperty(this.status.update(status), true);
    }

    StartStopCharging(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x17: return status.update(p);
                }
                return null;
            });
        }
        if (this.status.getValue() == null) 
            throw new NoPropertiesException();
    }
}