// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.HvacWeekdayStartingTime;
import java.util.ArrayList;

/**
 * Change starting times
 */
public class ChangeStartingTimes extends SetCommand {
    Property<HvacWeekdayStartingTime>[] hvacWeekdayStartingTimes;

    /**
     * @return The hvac weekday starting times
     */
    public Property<HvacWeekdayStartingTime>[] getHvacWeekdayStartingTimes() {
        return hvacWeekdayStartingTimes;
    }
    
    /**
     * Change starting times
     *
     * @param hvacWeekdayStartingTimes The hvac weekday starting times
     */
    public ChangeStartingTimes(HvacWeekdayStartingTime[] hvacWeekdayStartingTimes) {
        super(Identifier.CLIMATE);
    
        ArrayList<Property> hvacWeekdayStartingTimesBuilder = new ArrayList<>();
        if (hvacWeekdayStartingTimes != null) {
            for (HvacWeekdayStartingTime hvacWeekdayStartingTime : hvacWeekdayStartingTimes) {
                Property prop = new Property(0x0b, hvacWeekdayStartingTime);
                hvacWeekdayStartingTimesBuilder.add(prop);
                addProperty(prop, true);
            }
        }
        this.hvacWeekdayStartingTimes = hvacWeekdayStartingTimesBuilder.toArray(new Property[0]);
    }

    ChangeStartingTimes(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<HvacWeekdayStartingTime>> hvacWeekdayStartingTimesBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x0b: {
                        Property hvacWeekdayStartingTime = new Property(HvacWeekdayStartingTime.class, p);
                        hvacWeekdayStartingTimesBuilder.add(hvacWeekdayStartingTime);
                        return hvacWeekdayStartingTime;
                    }
                }
                return null;
            });
        }
    
        hvacWeekdayStartingTimes = hvacWeekdayStartingTimesBuilder.toArray(new Property[0]);
        if (this.hvacWeekdayStartingTimes.length == 0) 
            throw new NoPropertiesException();
    }
}