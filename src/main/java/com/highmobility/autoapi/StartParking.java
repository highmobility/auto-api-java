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

import com.highmobility.autoapi.ParkingTicketState.Status;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;
import java.util.Calendar;
import javax.annotation.Nullable;

/**
 * Start parking
 */
public class StartParking extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.PARKING_TICKET;

    public static final byte IDENTIFIER_STATUS = 0x01;
    public static final byte IDENTIFIER_OPERATOR_NAME = 0x02;
    public static final byte IDENTIFIER_OPERATOR_TICKET_ID = 0x03;
    public static final byte IDENTIFIER_TICKET_START_TIME = 0x04;
    public static final byte IDENTIFIER_TICKET_END_TIME = 0x05;

    Property<Status> status = new Property(Status.class, IDENTIFIER_STATUS);
    @Nullable Property<String> operatorName = new Property(String.class, IDENTIFIER_OPERATOR_NAME);
    Property<String> operatorTicketID = new Property(String.class, IDENTIFIER_OPERATOR_TICKET_ID);
    Property<Calendar> ticketStartTime = new Property(Calendar.class, IDENTIFIER_TICKET_START_TIME);
    @Nullable Property<Calendar> ticketEndTime = new Property(Calendar.class, IDENTIFIER_TICKET_END_TIME);

    /**
     * @return The operator name
     */
    public @Nullable Property<String> getOperatorName() {
        return operatorName;
    }
    
    /**
     * @return The operator ticket id
     */
    public Property<String> getOperatorTicketID() {
        return operatorTicketID;
    }
    
    /**
     * @return The ticket start time
     */
    public Property<Calendar> getTicketStartTime() {
        return ticketStartTime;
    }
    
    /**
     * @return The ticket end time
     */
    public @Nullable Property<Calendar> getTicketEndTime() {
        return ticketEndTime;
    }
    
    /**
     * Start parking
     *
     * @param operatorName Operator name
     * @param operatorTicketID Operator ticket ID
     * @param ticketStartTime Milliseconds since UNIX Epoch time
     * @param ticketEndTime Milliseconds since UNIX Epoch time
     */
    public StartParking(@Nullable String operatorName, String operatorTicketID, Calendar ticketStartTime, @Nullable Calendar ticketEndTime) {
        super(IDENTIFIER);
    
        addProperty(status.addValueComponent(new Bytes("01")));
        addProperty(this.operatorName.update(operatorName));
        addProperty(this.operatorTicketID.update(operatorTicketID));
        addProperty(this.ticketStartTime.update(ticketStartTime));
        addProperty(this.ticketEndTime.update(ticketEndTime), true);
    }

    StartParking(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_STATUS: status.update(p);
                    case IDENTIFIER_OPERATOR_NAME: return operatorName.update(p);
                    case IDENTIFIER_OPERATOR_TICKET_ID: return operatorTicketID.update(p);
                    case IDENTIFIER_TICKET_START_TIME: return ticketStartTime.update(p);
                    case IDENTIFIER_TICKET_END_TIME: return ticketEndTime.update(p);
                }
                return null;
            });
        }
        if ((status.getValue() == null || status.getValueComponent().getValueBytes().equals(new Bytes("01")) == false) ||
            this.operatorTicketID.getValue() == null ||
            this.ticketStartTime.getValue() == null) 
            throw new NoPropertiesException();
    }
}