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

import com.highmobility.autoapi.property.CalendarProperty;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.StringProperty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Start parking. This clears the last parking ticket information and starts a new one. The result
 * is sent through the evented Parking Ticket message. The end time can be left unset depending on
 * the operator.
 */
public class StartParking extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.PARKING_TICKET, 0x02);

    /**
     * Start parking.
     *
     * @param operatorName     The operator name
     * @param operatorTicketId The ticket id
     * @param startDate        The parking start date
     * @param endDate          The parking end date
     */
    public StartParking(String operatorName, String operatorTicketId, Calendar startDate,
                        Calendar endDate) {
        super(TYPE, getProperties(operatorName, operatorTicketId, startDate, endDate));
    }

    static HMProperty[] getProperties(String operatorName, String operatorTicketId, Calendar
            startDate, Calendar endDate) {
        List<HMProperty> propertiesBuilder = new ArrayList<>();

        if (operatorName != null)
            propertiesBuilder.add(new StringProperty((byte) 0x01, operatorName));
        if (operatorTicketId != null)
            propertiesBuilder.add(new StringProperty((byte) 0x02, operatorTicketId));
        if (startDate != null) propertiesBuilder.add(new CalendarProperty((byte) 0x03, startDate));
        if (endDate != null) propertiesBuilder.add(new CalendarProperty((byte) 0x04, endDate));

        return propertiesBuilder.toArray(new HMProperty[propertiesBuilder.size()]);
    }

    StartParking(byte[] bytes) {
        super(bytes);
    }
}
