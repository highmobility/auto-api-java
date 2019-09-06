// TODO: license

package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;

import java.util.Calendar;

import javax.annotation.Nullable;

public class StartParking extends SetCommand {
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
     * @param operatorName The Operator Name bytes formatted in UTF-8
     * @param operatorTicketID The Operator Ticket ID bytes formatted in UTF-8
     * @param ticketStartTime The Milliseconds since UNIX Epoch time
     * @param ticketEndTime The Milliseconds since UNIX Epoch time
     */
    public StartParking(String operatorName, String operatorTicketID, Calendar ticketStartTime, Calendar ticketEndTime) {
        super(Identifier.PARKING_TICKET);

        addProperty(new Property((byte) 0x01, new byte[] {0x01}));
        addProperty(this.operatorName.update(operatorName));
        addProperty(this.operatorTicketID.update(operatorTicketID));
        addProperty(this.ticketStartTime.update(ticketStartTime));
        addProperty(this.ticketEndTime.update(ticketEndTime));
    }

    StartParking(byte[] bytes) {
        super(bytes);
    }
}