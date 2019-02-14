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
import com.highmobility.autoapi.property.ParkingTicketState;
import com.highmobility.autoapi.property.Property;

import java.util.Calendar;

import javax.annotation.Nullable;

/**
 * Command sent from the car every time the parking ticket state changes. or when a Get Parking
 * Ticket message is received. The state is Ended also when the parking ticket has never been set.
 * Afterwards the car always keeps the last parking ticket information.
 */
public class ParkingTicket extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.PARKING_TICKET, 0x01);

    private static final byte IDENTIFIER_OPERATOR_NAME = 0x02;
    private static final byte IDENTIFIER_OPERATOR_TICKET_ID = 0x03;
    private static final byte IDENTIFIER_TICKET_START = 0x04;
    private static final byte IDENTIFIER_TICKET_END = 0x05;
    private static final byte IDENTIFIER_STATE = 0x01;

    ParkingTicketState state;
    ObjectPropertyString operatorName = new ObjectPropertyString(IDENTIFIER_OPERATOR_NAME);
    ObjectPropertyString operatorTicketId = new ObjectPropertyString(IDENTIFIER_OPERATOR_TICKET_ID);
    Calendar ticketStart;
    Calendar ticketEnd;

    /**
     * @return The ticket state.
     */
    @Nullable public ParkingTicketState getState() {
        return state;
    }

    /**
     * @return The operator name.
     */
    @Nullable public ObjectPropertyString getOperatorName() {
        return operatorName;
    }

    /**
     * @return The ticket id.
     */
    @Nullable public ObjectPropertyString getOperatorTicketId() {
        return operatorTicketId;
    }

    /**
     * @return Ticket start date.
     */
    @Nullable public Calendar getTicketStartDate() {
        return ticketStart;
    }

    /**
     * @return Ticket end date.
     */
    @Nullable public Calendar getTicketEndDate() {
        return ticketEnd;
    }

    ParkingTicket(byte[] bytes) {
        super(bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_STATE:
                        state = ParkingTicketState.fromByte(p.getValueByte());
                        return state;
                    case IDENTIFIER_OPERATOR_NAME:
                        return operatorName.update(p);
                    case IDENTIFIER_OPERATOR_TICKET_ID:
                        return operatorTicketId.update(p);
                    case IDENTIFIER_TICKET_START:
                        ticketStart = Property.getCalendar(p.getValueBytesArray());
                        return ticketStart;
                    case IDENTIFIER_TICKET_END:
                        ticketEnd = Property.getCalendar(p.getValueBytesArray());
                        return ticketEnd;
                }
                return null;
            });
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
        private ObjectPropertyString operatorName;
        private ObjectPropertyString operatorTicketId;
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
            addProperty(new Property(IDENTIFIER_STATE, state.getByte()));
            return this;
        }

        /**
         * @param operatorName The operator name.
         * @return The builder.
         */
        public Builder setOperatorName(ObjectPropertyString operatorName) {
            this.operatorName = operatorName;
            addProperty(operatorName.setIdentifier(IDENTIFIER_OPERATOR_NAME));
            return this;
        }

        /**
         * @param operatorTicketId The ticket id.
         * @return The builder.
         */
        public Builder setOperatorTicketId(ObjectPropertyString operatorTicketId) {
            this.operatorTicketId = operatorTicketId;
            addProperty(operatorTicketId.setIdentifier(IDENTIFIER_OPERATOR_TICKET_ID));
            return this;
        }

        /**
         * @param ticketStart The ticket start date.
         * @return The builder.
         */
        public Builder setTicketStart(Calendar ticketStart) {
            this.ticketStart = ticketStart;
            addProperty(new CalendarProperty(IDENTIFIER_TICKET_START, ticketStart));
            return this;
        }

        /**
         * @param ticketEnd The ticket end date.
         * @return The builder.
         */
        public Builder setTicketEnd(Calendar ticketEnd) {
            this.ticketEnd = ticketEnd;
            addProperty(new CalendarProperty(IDENTIFIER_TICKET_END, ticketEnd));
            return this;
        }

        public ParkingTicket build() {
            return new ParkingTicket(this);
        }
    }
}