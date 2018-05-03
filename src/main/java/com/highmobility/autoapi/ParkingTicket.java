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
import com.highmobility.autoapi.property.ParkingTicketState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import java.util.Calendar;

/**
 * Command sent from the car every time the parking ticket state changes. or when a Get Parking
 * Ticket message is received. The state is Ended also when the parking ticket has never been
 * set. Afterwards the car always keeps the last parking ticket information.
 */
public class ParkingTicket extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.PARKING_TICKET, 0x01);

    private static final byte OPERATOR_NAME_IDENTIFIER = 0x02;
    private static final byte OPERATOR_TICKET_ID_IDENTIFIER = 0x03;
    private static final byte TICKET_START_IDENTIFIER = 0x04;
    private static final byte TICKET_END_IDENTIFIER = 0x05;

    ParkingTicketState state;
    String operatorName;
    String operatorTicketId;
    Calendar ticketStart;
    Calendar ticketEnd;

    /**
     * @return The ticket state.
     */
    public ParkingTicketState getState() {
        return state;
    }

    /**
     * @return The operator name.
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * @return The ticket id.
     */
    public String getOperatorTicketId() {
        return operatorTicketId;
    }

    /**
     * @return Ticket start date.
     */
    public Calendar getTicketStartDate() {
        return ticketStart;
    }

    /**
     * @return Ticket end date.
     */
    public Calendar getTicketEndDate() {
        return ticketEnd;
    }

    public ParkingTicket(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case ParkingTicketState.IDENTIFIER:
                    state = ParkingTicketState.fromByte(property.getValueByte());
                    break;
                case OPERATOR_NAME_IDENTIFIER:
                    operatorName = Property.getString(property.getValueBytes());
                    break;
                case OPERATOR_TICKET_ID_IDENTIFIER:
                    operatorTicketId = Property.getString(property.getValueBytes());
                    break;
                case TICKET_START_IDENTIFIER:
                    ticketStart = Property.getCalendar(property.getValueBytes());
                    break;
                case TICKET_END_IDENTIFIER:
                    ticketEnd = Property.getCalendar(property.getValueBytes());
                    break;
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private ParkingTicket(Builder builder) {
        super(builder);
        operatorName = builder.operatorName;
        operatorTicketId = builder.operatorTicketId;
        ticketStart = builder.ticketStart;
        ticketEnd = builder.ticketEnd;
        state = builder.state;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private ParkingTicketState state;
        private String operatorName;
        private String operatorTicketId;
        private Calendar ticketStart;
        private Calendar ticketEnd;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param state The parking ticket state.
         * @return The builder.
         */
        public Builder setState(ParkingTicketState state) {
            this.state = state;
            addProperty(state);
            return this;
        }

        /**
         * @param operatorName The operator name.
         * @return The builder.
         */
        public Builder setOperatorName(String operatorName) {
            this.operatorName = operatorName;
            addProperty(new StringProperty(OPERATOR_NAME_IDENTIFIER, operatorName));
            return this;
        }

        /**
         * @param operatorTicketId The ticket id.
         * @return The builder.
         */
        public Builder setOperatorTicketId(String operatorTicketId) {
            this.operatorTicketId = operatorTicketId;
            addProperty(new StringProperty(OPERATOR_TICKET_ID_IDENTIFIER, operatorTicketId));
            return this;
        }

        /**
         * @param ticketStart The ticket start date.
         * @return The builder.
         */
        public Builder setTicketStart(Calendar ticketStart) {
            this.ticketStart = ticketStart;
            addProperty(new CalendarProperty(TICKET_START_IDENTIFIER, ticketStart));
            return this;
        }

        /**
         * @param ticketEnd The ticket end date.
         * @return The builder.
         */
        public Builder setTicketEnd(Calendar ticketEnd) {
            this.ticketEnd = ticketEnd;
            addProperty(new CalendarProperty(TICKET_END_IDENTIFIER, ticketEnd));
            return this;
        }

        public ParkingTicket build() {
            return new ParkingTicket(this);
        }
    }
}