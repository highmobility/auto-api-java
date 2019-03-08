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

    private Property<String> operatorName = new Property(String.class, IDENTIFIER_OPERATOR_NAME);
    private Property<String> operatorTicketId = new Property(String.class,
            IDENTIFIER_OPERATOR_TICKET_ID);
    private Property<Calendar> startDate = new Property(Calendar.class, START_DATE_IDENTIFIER);
    private Property<Calendar> endDate = new Property(Calendar.class, END_DATE_IDENTIFIER);

    /**
     * @return The operator name.
     */
    public Property<String> getOperatorName() {
        return operatorName;
    }

    /**
     * @return The operator ticket id.
     */
    public Property<String> getOperatorTicketId() {
        return operatorTicketId;
    }

    /**
     * @return The start date.
     */
    public Property<Calendar> getStartDate() {
        return startDate;
    }

    /**
     * @return The end date.
     */
    public Property<Calendar> getEndDate() {
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

        if (operatorName != null) {
            propertiesBuilder.add(this.operatorName.update(operatorName));
        }

        propertiesBuilder.add(this.operatorTicketId.update(operatorTicketId));
        propertiesBuilder.add(this.startDate.update(startDate));

        if (endDate != null) {
            propertiesBuilder.add(this.endDate.update(endDate));
        }

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
                        return startDate.update(p);
                    case END_DATE_IDENTIFIER:
                        return endDate.update(p);
                }
                return null;
            });
        }

        if (operatorTicketId == null || startDate == null) throw new CommandParseException();
    }
}
