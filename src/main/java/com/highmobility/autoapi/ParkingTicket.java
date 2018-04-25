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

import com.highmobility.autoapi.property.ParkingTicketState;
import com.highmobility.autoapi.property.Property;

import java.util.Calendar;

/**
 * Command sent from the car every time the parking ticket state changes. or when a Get Parking
 * Ticket message is received. The state is 0x00 Ended also when the parking ticket has never been
 * set. Afterwards the car always keeps the last parking ticket information.
 */
public class ParkingTicket extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.PARKING_TICKET, 0x01);

    String operatorName;
    String operatorTicketId;
    Calendar ticketStart;
    Calendar ticketEnd;
    ParkingTicketState state;

    /**
     * @return The ticket state
     */
    public ParkingTicketState getState() {
        return state;
    }

    /**
     * @return The operator name
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * @return The ticket id
     */
    public String getOperatorTicketId() {
        return operatorTicketId;
    }

    /**
     * @return Ticket start date
     */
    public Calendar getTicketStartDate() {
        return ticketStart;
    }

    /**
     * @return Ticket end date. null if not set
     */
    public Calendar getTicketEndDate() {
        return ticketEnd;
    }

    public ParkingTicket(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    state = ParkingTicketState.fromByte(property.getValueByte());
                    break;
                case 0x02:
                    operatorName = Property.getString(property.getValueBytes());
                    break;
                case 0x03:
                    operatorTicketId = Property.getString(property.getValueBytes());
                    break;
                case 0x04:
                    ticketStart = Property.getCalendar(property.getValueBytes());
                    break;
                case 0x05:
                    ticketEnd = Property.getCalendar(property.getValueBytes());
                    break;
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }
}