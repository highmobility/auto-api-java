// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ReductionTime;
import java.util.ArrayList;

/**
 * Set reduction of charging current times
 */
public class SetReductionOfChargingCurrentTimes extends SetCommand {
    Property<ReductionTime>[] reductionTimes;

    /**
     * @return The reduction times
     */
    public Property<ReductionTime>[] getReductionTimes() {
        return reductionTimes;
    }
    
    /**
     * Set reduction of charging current times
     *
     * @param reductionTimes The reduction times
     */
    public SetReductionOfChargingCurrentTimes(ReductionTime[] reductionTimes) {
        super(Identifier.CHARGING);
    
        ArrayList<Property> reductionTimesBuilder = new ArrayList<>();
        if (reductionTimes != null) {
            for (ReductionTime reductionTime : reductionTimes) {
                Property prop = new Property(0x13, reductionTime);
                reductionTimesBuilder.add(prop);
                addProperty(prop, true);
            }
        }
        this.reductionTimes = reductionTimesBuilder.toArray(new Property[0]);
    }

    SetReductionOfChargingCurrentTimes(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<ReductionTime>> reductionTimesBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x13: {
                        Property reductionTime = new Property(ReductionTime.class, p);
                        reductionTimesBuilder.add(reductionTime);
                        return reductionTime;
                    }
                }
                return null;
            });
        }
    
        reductionTimes = reductionTimesBuilder.toArray(new Property[0]);
        if (this.reductionTimes.length == 0) 
            throw new NoPropertiesException();
    }
}