/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
            super(State.class, IDENTIFIER);
        }
    
        GetParkingTicket(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific parking ticket properties
     */
    public static class GetParkingTicketProperties extends GetCommand {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetParkingTicketProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetParkingTicketProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetParkingTicketProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
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