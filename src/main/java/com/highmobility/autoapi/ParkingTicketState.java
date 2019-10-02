// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;
import java.util.Calendar;

public class ParkingTicketState extends SetCommand {
    Property<Status> status = new Property(Status.class, 0x01);
    Property<String> operatorName = new Property(String.class, 0x02);
    Property<String> operatorTicketID = new Property(String.class, 0x03);
    Property<Calendar> ticketStartTime = new Property(Calendar.class, 0x04);
    Property<Calendar> ticketEndTime = new Property(Calendar.class, 0x05);

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
     * @return Operator Ticket ID
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
                    case 0x01: return status.update(p);
                    case 0x02: return operatorName.update(p);
                    case 0x03: return operatorTicketID.update(p);
                    case 0x04: return ticketStartTime.update(p);
                    case 0x05: return ticketEndTime.update(p);
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
            super(Identifier.PARKING_TICKET);
        }

        public ParkingTicketState build() {
            return new ParkingTicketState(this);
        }

        /**
         * @param status The status
         * @return The builder
         */
        public Builder setStatus(Property<Status> status) {
            this.status = status.setIdentifier(0x01);
            addProperty(this.status);
            return this;
        }
        
        /**
         * @param operatorName Operator name
         * @return The builder
         */
        public Builder setOperatorName(Property<String> operatorName) {
            this.operatorName = operatorName.setIdentifier(0x02);
            addProperty(this.operatorName);
            return this;
        }
        
        /**
         * @param operatorTicketID Operator Ticket ID
         * @return The builder
         */
        public Builder setOperatorTicketID(Property<String> operatorTicketID) {
            this.operatorTicketID = operatorTicketID.setIdentifier(0x03);
            addProperty(this.operatorTicketID);
            return this;
        }
        
        /**
         * @param ticketStartTime Milliseconds since UNIX Epoch time
         * @return The builder
         */
        public Builder setTicketStartTime(Property<Calendar> ticketStartTime) {
            this.ticketStartTime = ticketStartTime.setIdentifier(0x04);
            addProperty(this.ticketStartTime);
            return this;
        }
        
        /**
         * @param ticketEndTime Milliseconds since UNIX Epoch time
         * @return The builder
         */
        public Builder setTicketEndTime(Property<Calendar> ticketEndTime) {
            this.ticketEndTime = ticketEndTime.setIdentifier(0x05);
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