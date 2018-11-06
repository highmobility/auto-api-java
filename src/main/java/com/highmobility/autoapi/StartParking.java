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
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Start parking. This clears the last parking ticket information and starts a new one. The result
 * is sent through the evented Parking Ticket command. The end time can be left unset depending on
 * the operator.
 */
public class StartParking extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.PARKING_TICKET, 0x02);

    public static final byte OPERATOR_NAME_IDENTIFIER = 0x01;
    public static final byte OPERATOR_TICKET_ID_IDENTIFIER = 0x02;
    public static final byte START_DATE_IDENTIFIER = 0x03;
    public static final byte END_DATE_IDENTIFIER = 0x04;

    private String operatorName;
    private String operatorTicketId;
    private Calendar startDate;
    private Calendar endDate;

    /**
     * @return The operator name.
     */
    @Nullable public String getOperatorName() {
        return operatorName;
    }

    /**
     * @return The operator ticket id.
     */
    public String getOperatorTicketId() {
        return operatorTicketId;
    }

    /**
     * @return The start date.
     */
    public Calendar getStartDate() {
        return startDate;
    }

    /**
     * @return The end date.
     */
    @Nullable public Calendar getEndDate() {
        return endDate;
    }

    /**
     * Start parking.
     *
     * @param operatorName     The operator name
     * @param operatorTicketId The ticket id
     * @param startDate        The parking start date
     * @param endDate          The parking end date
     */
    public StartParking(@Nullable String operatorName, String operatorTicketId, Calendar startDate,
                        @Nullable Calendar endDate) {
        super(TYPE, getProperties(operatorName, operatorTicketId, startDate, endDate));
        this.operatorName = operatorName;
        this.operatorTicketId = operatorTicketId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    static HMProperty[] getProperties(String operatorName, String operatorTicketId, Calendar
            startDate, Calendar endDate) {
        List<HMProperty> propertiesBuilder = new ArrayList<>();

        if (operatorName != null)
            propertiesBuilder.add(new StringProperty(OPERATOR_NAME_IDENTIFIER, operatorName));
        if (operatorTicketId != null)
            propertiesBuilder.add(new StringProperty(OPERATOR_TICKET_ID_IDENTIFIER,
                    operatorTicketId));
        if (startDate != null)
            propertiesBuilder.add(new CalendarProperty(START_DATE_IDENTIFIER, startDate));
        if (endDate != null)
            propertiesBuilder.add(new CalendarProperty(END_DATE_IDENTIFIER, endDate));

        return propertiesBuilder.toArray(new HMProperty[propertiesBuilder.size()]);
    }

    StartParking(byte[] bytes) throws CommandParseException {
        super(bytes);
        for (Property property : properties) {
            switch (property.getPropertyIdentifier()) {
                case OPERATOR_NAME_IDENTIFIER:
                    operatorName = Property.getString(property.getValueBytes());
                    break;
                case OPERATOR_TICKET_ID_IDENTIFIER:
                    operatorTicketId = Property.getString(property.getValueBytes());
                    break;
                case START_DATE_IDENTIFIER:
                    startDate = Property.getCalendar(property.getValueBytes());
                    break;
                case END_DATE_IDENTIFIER:
                    endDate = Property.getCalendar(property.getValueBytes());
                    break;
            }
        }

        if (operatorTicketId == null || startDate == null) throw new CommandParseException();
    }
}
