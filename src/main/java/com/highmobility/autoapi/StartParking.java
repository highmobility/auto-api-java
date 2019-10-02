// TODO: license

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
    Property<Status> status = new Property(Status.class, 0x01);
    @Nullable Property<String> operatorName = new Property(String.class, 0x02);
    Property<String> operatorTicketID = new Property(String.class, 0x03);
    Property<Calendar> ticketStartTime = new Property(Calendar.class, 0x04);
    @Nullable Property<Calendar> ticketEndTime = new Property(Calendar.class, 0x05);

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
     * @param operatorName The Operator name
     * @param operatorTicketID The Operator Ticket ID
     * @param ticketStartTime The Milliseconds since UNIX Epoch time
     * @param ticketEndTime The Milliseconds since UNIX Epoch time
     */
    public StartParking(@Nullable String operatorName, String operatorTicketID, Calendar ticketStartTime, @Nullable Calendar ticketEndTime) {
        super(Identifier.PARKING_TICKET);
    
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
                    case 0x01: status.update(p);
                    case 0x02: return operatorName.update(p);
                    case 0x03: return operatorTicketID.update(p);
                    case 0x04: return ticketStartTime.update(p);
                    case 0x05: return ticketEndTime.update(p);
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