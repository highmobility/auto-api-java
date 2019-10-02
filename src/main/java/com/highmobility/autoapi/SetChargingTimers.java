// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Timer;
import java.util.ArrayList;

/**
 * Set charging timers
 */
public class SetChargingTimers extends SetCommand {
    Property<Timer>[] timers;

    /**
     * @return The timers
     */
    public Property<Timer>[] getTimers() {
        return timers;
    }
    
    /**
     * Set charging timers
     *
     * @param timers The timers
     */
    public SetChargingTimers(Timer[] timers) {
        super(Identifier.CHARGING);
    
        ArrayList<Property> timersBuilder = new ArrayList<>();
        if (timers != null) {
            for (Timer timer : timers) {
                Property prop = new Property(0x15, timer);
                timersBuilder.add(prop);
                addProperty(prop, true);
            }
        }
        this.timers = timersBuilder.toArray(new Property[0]);
    }

    SetChargingTimers(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<Timer>> timersBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x15: {
                        Property timer = new Property(Timer.class, p);
                        timersBuilder.add(timer);
                        return timer;
                    }
                }
                return null;
            });
        }
    
        timers = timersBuilder.toArray(new Property[0]);
        if (this.timers.length == 0) 
            throw new NoPropertiesException();
    }
}