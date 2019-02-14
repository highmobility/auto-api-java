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
import com.highmobility.autoapi.property.ObjectPropertyString;
import com.highmobility.autoapi.property.Property;

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

    public static final byte IDENTIFIER_OPERATOR_NAME = 0x01;
    public static final byte IDENTIFIER_OPERATOR_TICKET_ID = 0x02;
    public static final byte START_DATE_IDENTIFIER = 0x03;
    public static final byte END_DATE_IDENTIFIER = 0x04;

    private ObjectPropertyString operatorName = new ObjectPropertyString(IDENTIFIER_OPERATOR_NAME);
    private ObjectPropertyString operatorTicketId =
            new ObjectPropertyString(IDENTIFIER_OPERATOR_TICKET_ID);
    private Calendar startDate;
    private Calendar endDate; // TODO: 2019-02-14 property

    /**
     * @return The operator name.
     */
    @Nullable public ObjectPropertyString getOperatorName() {
        return operatorName;
    }

    /**
     * @return The operator ticket id.
     */
    public ObjectPropertyString getOperatorTicketId() {
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
        super(TYPE);

        List<Property> propertiesBuilder = new ArrayList<>();

        this.operatorName.update(operatorName);
        propertiesBuilder.add(this.operatorName);

        this.operatorTicketId.update(operatorTicketId);
        propertiesBuilder.add(this.operatorTicketId);

        propertiesBuilder.add(new CalendarProperty(START_DATE_IDENTIFIER, startDate));
        this.startDate = startDate;

        if (endDate != null) {
            propertiesBuilder.add(new CalendarProperty(END_DATE_IDENTIFIER, endDate));
        }
        this.endDate = endDate;

        createBytes(propertiesBuilder);
    }

    StartParking(byte[] bytes) throws CommandParseException {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_OPERATOR_NAME:
                        return operatorName.update(p);
                    case IDENTIFIER_OPERATOR_TICKET_ID:
                        return operatorTicketId.update(p);
                    case START_DATE_IDENTIFIER:
                        startDate = Property.getCalendar(p.getValueBytesArray());
                        break;
                    case END_DATE_IDENTIFIER:
                        endDate = Property.getCalendar(p.getValueBytesArray());
                        break;
                }
                return null;
            });
        }

        if (operatorTicketId == null || startDate == null) throw new CommandParseException();
    }
}
