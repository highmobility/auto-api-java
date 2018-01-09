package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ParkingTicketState;
import com.highmobility.autoapi.property.Property;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

/**
 * This is an evented message that is sent from the car every time the parking ticket state changes.
 * This message is also sent when a Get Parking Ticket message is received by the car. The state is
 * 0x00 Ended also when the parking ticket has never been set. Afterwards the car always keeps the
 * last parking ticket information.
 */
public class ParkingTicket extends Command {
    public static final Type TYPE = new Type(Identifier.PARKING_TICKET, 0x01);

    String operatorName;
    String operatorTicketId;
    Calendar ticketStart;
    Calendar ticketEnd;
    ParkingTicketState state;

    /**
     *
     * @return The ticket state
     */
    public ParkingTicketState getState() {
        return state;
    }

    /**
     *
     * @return The operator name
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     *
     * @return The ticket id
     */
    public String getOperatorTicketId() {
        return operatorTicketId;
    }

    /**
     *
     * @return Ticket start date
     */
    public Calendar getTicketStartDate() {
        return ticketStart;
    }

    /**
     *
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
                    try {
                        operatorName = Property.getString(property.getValueBytes());
                    } catch (UnsupportedEncodingException e) {
                        throw new CommandParseException(CommandParseException.CommandExceptionCode.UNSUPPORTED_VALUE_TYPE);
                    }
                    break;
                case 0x03:
                    try {
                        operatorTicketId = Property.getString(property.getValueBytes());
                    } catch (UnsupportedEncodingException e) {
                        throw new CommandParseException(CommandParseException.CommandExceptionCode.UNSUPPORTED_VALUE_TYPE);
                    }
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
}