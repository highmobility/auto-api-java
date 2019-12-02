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
import com.highmobility.autoapi.property.ByteEnum;
import java.util.Calendar;

/**
 * The parking ticket state
 */
public class ParkingTicketState extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.PARKING_TICKET;

    public static final byte IDENTIFIER_STATUS = 0x01;
    public static final byte IDENTIFIER_OPERATOR_NAME = 0x02;
    public static final byte IDENTIFIER_OPERATOR_TICKET_ID = 0x03;
    public static final byte IDENTIFIER_TICKET_START_TIME = 0x04;
    public static final byte IDENTIFIER_TICKET_END_TIME = 0x05;

    Property<Status> status = new Property(Status.class, IDENTIFIER_STATUS);
    Property<String> operatorName = new Property(String.class, IDENTIFIER_OPERATOR_NAME);
    Property<String> operatorTicketID = new Property(String.class, IDENTIFIER_OPERATOR_TICKET_ID);
    Property<Calendar> ticketStartTime = new Property(Calendar.class, IDENTIFIER_TICKET_START_TIME);
    Property<Calendar> ticketEndTime = new Property(Calendar.class, IDENTIFIER_TICKET_END_TIME);

    /**
     * @return The status
     */
    public Property<Status> getStatus() {
        return status;
    }

    /**
     * @return Operator name
     */
    public Property<String> getOperatorName() {
        return operatorName;
    }

    /**
     * @return Operator ticket ID
     */
    public Property<String> getOperatorTicketID() {
        return operatorTicketID;
    }

    /**
     * @return Milliseconds since UNIX Epoch time
     */
    public Property<Calendar> getTicketStartTime() {
        return ticketStartTime;
    }

    /**
     * @return Milliseconds since UNIX Epoch time
     */
    public Property<Calendar> getTicketEndTime() {
        return ticketEndTime;
    }

    ParkingTicketState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_STATUS: return status.update(p);
                    case IDENTIFIER_OPERATOR_NAME: return operatorName.update(p);
                    case IDENTIFIER_OPERATOR_TICKET_ID: return operatorTicketID.update(p);
                    case IDENTIFIER_TICKET_START_TIME: return ticketStartTime.update(p);
                    case IDENTIFIER_TICKET_END_TIME: return ticketEndTime.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private ParkingTicketState(Builder builder) {
        super(builder);

        status = builder.status;
        operatorName = builder.operatorName;
        operatorTicketID = builder.operatorTicketID;
        ticketStartTime = builder.ticketStartTime;
        ticketEndTime = builder.ticketEndTime;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<Status> status;
        private Property<String> operatorName;
        private Property<String> operatorTicketID;
        private Property<Calendar> ticketStartTime;
        private Property<Calendar> ticketEndTime;

        public Builder() {
            super(IDENTIFIER);
        }

        public ParkingTicketState build() {
            return new ParkingTicketState(this);
        }

        /**
         * @param status The status
         * @return The builder
         */
        public Builder setStatus(Property<Status> status) {
            this.status = status.setIdentifier(IDENTIFIER_STATUS);
            addProperty(this.status);
            return this;
        }
        
        /**
         * @param operatorName Operator name
         * @return The builder
         */
        public Builder setOperatorName(Property<String> operatorName) {
            this.operatorName = operatorName.setIdentifier(IDENTIFIER_OPERATOR_NAME);
            addProperty(this.operatorName);
            return this;
        }
        
        /**
         * @param operatorTicketID Operator ticket ID
         * @return The builder
         */
        public Builder setOperatorTicketID(Property<String> operatorTicketID) {
            this.operatorTicketID = operatorTicketID.setIdentifier(IDENTIFIER_OPERATOR_TICKET_ID);
            addProperty(this.operatorTicketID);
            return this;
        }
        
        /**
         * @param ticketStartTime Milliseconds since UNIX Epoch time
         * @return The builder
         */
        public Builder setTicketStartTime(Property<Calendar> ticketStartTime) {
            this.ticketStartTime = ticketStartTime.setIdentifier(IDENTIFIER_TICKET_START_TIME);
            addProperty(this.ticketStartTime);
            return this;
        }
        
        /**
         * @param ticketEndTime Milliseconds since UNIX Epoch time
         * @return The builder
         */
        public Builder setTicketEndTime(Property<Calendar> ticketEndTime) {
            this.ticketEndTime = ticketEndTime.setIdentifier(IDENTIFIER_TICKET_END_TIME);
            addProperty(this.ticketEndTime);
            return this;
        }
    }

    public enum Status implements ByteEnum {
        ENDED((byte) 0x00),
        STARTED((byte) 0x01);
    
        public static Status fromByte(byte byteValue) throws CommandParseException {
            Status[] values = Status.values();
    
            for (int i = 0; i < values.length; i++) {
                Status state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Status(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}