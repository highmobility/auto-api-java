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

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;
import java.util.Calendar;
import javax.annotation.Nullable;

/**
 * The Parking Ticket capability
 */
public class ParkingTicket {
    public static final int IDENTIFIER = Identifier.PARKING_TICKET;

    public static final byte PROPERTY_STATUS = 0x01;
    public static final byte PROPERTY_OPERATOR_NAME = 0x02;
    public static final byte PROPERTY_OPERATOR_TICKET_ID = 0x03;
    public static final byte PROPERTY_TICKET_START_TIME = 0x04;
    public static final byte PROPERTY_TICKET_END_TIME = 0x05;

    /**
     * Get parking ticket
     */
    public static class GetParkingTicket extends GetCommand {
        public GetParkingTicket() {
            super(IDENTIFIER);
        }
    
        GetParkingTicket(byte[] bytes) {
            super(bytes);
        }
    }
    
    /**
     * Get specific parking ticket properties
     */
    public static class GetParkingTicketProperties extends GetCommand {
        Bytes propertyIdentifiers;
    
        /**
         * @return The property identifiers.
         */
        public Bytes getPropertyIdentifiers() {
            return propertyIdentifiers;
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetParkingTicketProperties(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers.getByteArray());
            this.propertyIdentifiers = propertyIdentifiers;
        }
    
        GetParkingTicketProperties(byte[] bytes) {
            super(bytes);
            propertyIdentifiers = getRange(3, getLength());
        }
    }

    /**
     * The parking ticket state
     */
    public static class State extends SetCommand {
        Property<Status> status = new Property(Status.class, PROPERTY_STATUS);
        Property<String> operatorName = new Property(String.class, PROPERTY_OPERATOR_NAME);
        Property<String> operatorTicketID = new Property(String.class, PROPERTY_OPERATOR_TICKET_ID);
        Property<Calendar> ticketStartTime = new Property(Calendar.class, PROPERTY_TICKET_START_TIME);
        Property<Calendar> ticketEndTime = new Property(Calendar.class, PROPERTY_TICKET_END_TIME);
    
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
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_OPERATOR_NAME: return operatorName.update(p);
                        case PROPERTY_OPERATOR_TICKET_ID: return operatorTicketID.update(p);
                        case PROPERTY_TICKET_START_TIME: return ticketStartTime.update(p);
                        case PROPERTY_TICKET_END_TIME: return ticketEndTime.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
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
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param status The status
             * @return The builder
             */
            public Builder setStatus(Property<Status> status) {
                this.status = status.setIdentifier(PROPERTY_STATUS);
                addProperty(this.status);
                return this;
            }
            
            /**
             * @param operatorName Operator name
             * @return The builder
             */
            public Builder setOperatorName(Property<String> operatorName) {
                this.operatorName = operatorName.setIdentifier(PROPERTY_OPERATOR_NAME);
                addProperty(this.operatorName);
                return this;
            }
            
            /**
             * @param operatorTicketID Operator ticket ID
             * @return The builder
             */
            public Builder setOperatorTicketID(Property<String> operatorTicketID) {
                this.operatorTicketID = operatorTicketID.setIdentifier(PROPERTY_OPERATOR_TICKET_ID);
                addProperty(this.operatorTicketID);
                return this;
            }
            
            /**
             * @param ticketStartTime Milliseconds since UNIX Epoch time
             * @return The builder
             */
            public Builder setTicketStartTime(Property<Calendar> ticketStartTime) {
                this.ticketStartTime = ticketStartTime.setIdentifier(PROPERTY_TICKET_START_TIME);
                addProperty(this.ticketStartTime);
                return this;
            }
            
            /**
             * @param ticketEndTime Milliseconds since UNIX Epoch time
             * @return The builder
             */
            public Builder setTicketEndTime(Property<Calendar> ticketEndTime) {
                this.ticketEndTime = ticketEndTime.setIdentifier(PROPERTY_TICKET_END_TIME);
                addProperty(this.ticketEndTime);
                return this;
            }
        }
    }

    /**
     * Start parking
     */
    public static class StartParking extends SetCommand {
        Property<Status> status = new Property(Status.class, PROPERTY_STATUS);
        Property<String> operatorName = new Property(String.class, PROPERTY_OPERATOR_NAME);
        Property<String> operatorTicketID = new Property(String.class, PROPERTY_OPERATOR_TICKET_ID);
        Property<Calendar> ticketStartTime = new Property(Calendar.class, PROPERTY_TICKET_START_TIME);
        Property<Calendar> ticketEndTime = new Property(Calendar.class, PROPERTY_TICKET_END_TIME);
    
        /**
         * @return The operator name
         */
        public Property<String> getOperatorName() {
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
        public Property<Calendar> getTicketEndTime() {
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
            addProperty(this.ticketEndTime.update(ticketEndTime));
            createBytes();
        }
    
        StartParking(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: status.update(p);
                        case PROPERTY_OPERATOR_NAME: return operatorName.update(p);
                        case PROPERTY_OPERATOR_TICKET_ID: return operatorTicketID.update(p);
                        case PROPERTY_TICKET_START_TIME: return ticketStartTime.update(p);
                        case PROPERTY_TICKET_END_TIME: return ticketEndTime.update(p);
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
    
    /**
     * End parking
     */
    public static class EndParking extends SetCommand {
        Property<Status> status = new Property(Status.class, PROPERTY_STATUS);
    
        /**
         * End parking
         */
        public EndParking() {
            super(IDENTIFIER);
        
            addProperty(status.addValueComponent(new Bytes("00")));
            createBytes();
        }
    
        EndParking(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: status.update(p);
                    }
                    return null;
                });
            }
            if ((status.getValue() == null || status.getValueComponent().getValueBytes().equals(new Bytes("00")) == false)) 
                throw new NoPropertiesException();
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