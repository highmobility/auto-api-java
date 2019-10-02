// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import java.util.Calendar;
import javax.annotation.Nullable;

/**
 * Request states
 */
public class RequestStates extends SetCommand {
    PropertyInteger capabilityID = new PropertyInteger(0x02, false);
    @Nullable Property<Calendar> startDate = new Property(Calendar.class, 0x03);
    @Nullable Property<Calendar> endDate = new Property(Calendar.class, 0x04);

    /**
     * @return The capability id
     */
    public PropertyInteger getCapabilityID() {
        return capabilityID;
    }
    
    /**
     * @return The start date
     */
    public @Nullable Property<Calendar> getStartDate() {
        return startDate;
    }
    
    /**
     * @return The end date
     */
    public @Nullable Property<Calendar> getEndDate() {
        return endDate;
    }
    
    /**
     * Request states
     *
     * @param capabilityID The The identifier of the Capability
     * @param startDate The Milliseconds since UNIX Epoch time
     * @param endDate The Milliseconds since UNIX Epoch time
     */
    public RequestStates(Integer capabilityID, @Nullable Calendar startDate, @Nullable Calendar endDate) {
        super(Identifier.HISTORICAL);
    
        addProperty(this.capabilityID.update(false, 2, capabilityID));
        addProperty(this.startDate.update(startDate));
        addProperty(this.endDate.update(endDate), true);
    }

    RequestStates(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x02: return capabilityID.update(p);
                    case 0x03: return startDate.update(p);
                    case 0x04: return endDate.update(p);
                }
                return null;
            });
        }
        if (this.capabilityID.getValue() == null) 
            throw new NoPropertiesException();
    }
}