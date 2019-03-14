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

import com.highmobility.autoapi.value.ParkingTicketState;
import com.highmobility.autoapi.property.Property;

import java.util.Calendar;

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

    Property<ParkingTicketState> state = new Property(ParkingTicketState.class, IDENTIFIER_STATE);
    Property<String> operatorName = new Property(String.class, IDENTIFIER_OPERATOR_NAME);
    Property<String> operatorTicketId = new Property(String.class, IDENTIFIER_OPERATOR_TICKET_ID);
    Property<Calendar> ticketStart = new Property(Calendar.class, IDENTIFIER_TICKET_START);
    Property<Calendar> ticketEnd = new Property(Calendar.class, IDENTIFIER_TICKET_END);

    /**
     * @return The ticket state.
     */
    public Property<ParkingTicketState> getState() {
        return state;
    }

    /**
     * @return The operator name.
     */
    public Property<String> getOperatorName() {
        return operatorName;
    }

    /**
     * @return The ticket id.
     */
    public Property<String> getOperatorTicketId() {
        return operatorTicketId;
    }

    /**
     * @return Ticket start date.
     */
    public Property<Calendar> getTicketStartDate() {
        return ticketStart;
    }

    /**
     * @return Ticket end date.
     */
    public Property<Calendar> getTicketEndDate() {
        return ticketEnd;
    }

    ParkingTicket(byte[] bytes) {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_STATE:
                        return state.update(p);
                    case IDENTIFIER_OPERATOR_NAME:
                        return operatorName.update(p);
                    case IDENTIFIER_OPERATOR_TICKET_ID:
                        return operatorTicketId.update(p);
                    case IDENTIFIER_TICKET_START:
                        return ticketStart.update(p);
                    case IDENTIFIER_TICKET_END:
                        return ticketEnd.update(p);
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
        private Property<ParkingTicketState> state;
        private Property<String> operatorName;
        private Property<String> operatorTicketId;
        private Property<Calendar> ticketStart;
        private Property<Calendar> ticketEnd;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param state The parking ticket state.
         * @return The builder.
         */
        public Builder setState(Property<ParkingTicketState> state) {
            this.state = state;
            addProperty(state.setIdentifier(IDENTIFIER_STATE));
            return this;
        }

        /**
         * @param operatorName The operator name.
         * @return The builder.
         */
        public Builder setOperatorName(Property<String> operatorName) {
            this.operatorName = operatorName;
            addProperty(operatorName.setIdentifier(IDENTIFIER_OPERATOR_NAME));
            return this;
        }

        /**
         * @param operatorTicketId The ticket id.
         * @return The builder.
         */
        public Builder setOperatorTicketId(Property<String> operatorTicketId) {
            this.operatorTicketId = operatorTicketId;
            addProperty(operatorTicketId.setIdentifier(IDENTIFIER_OPERATOR_TICKET_ID));
            return this;
        }

        /**
         * @param ticketStart The ticket start date.
         * @return The builder.
         */
        public Builder setTicketStart(Property<Calendar> ticketStart) {
            this.ticketStart = ticketStart;
            addProperty(ticketStart.setIdentifier(IDENTIFIER_TICKET_START));
            return this;
        }

        /**
         * @param ticketEnd The ticket end date.
         * @return The builder.
         */
        public Builder setTicketEnd(Property<Calendar> ticketEnd) {
            this.ticketEnd = ticketEnd;
            addProperty(ticketEnd.setIdentifier(IDENTIFIER_TICKET_END));
            return this;
        }

        public ParkingTicket build() {
            return new ParkingTicket(this);
        }
    }
}