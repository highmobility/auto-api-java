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
import com.highmobility.autoapi.property.PropertyInteger;
import java.util.Calendar;
import javax.annotation.Nullable;

/**
 * Request states
 */
public class RequestStates extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.HISTORICAL;

    public static final byte IDENTIFIER_CAPABILITY_ID = 0x02;
    public static final byte IDENTIFIER_START_DATE = 0x03;
    public static final byte IDENTIFIER_END_DATE = 0x04;

    PropertyInteger capabilityID = new PropertyInteger(IDENTIFIER_CAPABILITY_ID, false);
    @Nullable Property<Calendar> startDate = new Property(Calendar.class, IDENTIFIER_START_DATE);
    @Nullable Property<Calendar> endDate = new Property(Calendar.class, IDENTIFIER_END_DATE);

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
     * @param capabilityID The identifier of the Capability
     * @param startDate Milliseconds since UNIX Epoch time
     * @param endDate Milliseconds since UNIX Epoch time
     */
    public RequestStates(Integer capabilityID, @Nullable Calendar startDate, @Nullable Calendar endDate) {
        super(IDENTIFIER);
    
        addProperty(this.capabilityID.update(false, 2, capabilityID));
        addProperty(this.startDate.update(startDate));
        addProperty(this.endDate.update(endDate), true);
    }

    RequestStates(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_CAPABILITY_ID: return capabilityID.update(p);
                    case IDENTIFIER_START_DATE: return startDate.update(p);
                    case IDENTIFIER_END_DATE: return endDate.update(p);
                }
                return null;
            });
        }
        if (this.capabilityID.getValue() == null) 
            throw new NoPropertiesException();
    }
}