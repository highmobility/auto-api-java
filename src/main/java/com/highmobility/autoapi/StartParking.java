package com.highmobility.autoapi;

import com.highmobility.autoapi.property.CalendarProperty;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.StringProperty;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Start parking. This clears the last parking ticket information and starts a new one. The result
 * is sent through the evented Parking Ticket message. The end time can be left unset depending on
 * the operator.
 */
public class StartParking extends CommandWithProperties {
    // 00470201000E4265726c696e205061726b696e67020008363438393432333303000811010a1122000000
    public static final Type TYPE = new Type(Identifier.PARKING_TICKET, 0x02);

    public StartParking(String operatorName, String operatorTicketId, Calendar startDate,
                        Calendar endDate) throws UnsupportedEncodingException {
        super(TYPE, getProperties(operatorName, operatorTicketId, startDate, endDate));
    }

    static HMProperty[] getProperties(String operatorName, String operatorTicketId, Calendar startDate,
                                      Calendar endDate) throws UnsupportedEncodingException {
        List<HMProperty> propertiesBuilder = new ArrayList<>();

        if (operatorName != null) propertiesBuilder.add(new StringProperty((byte) 0x01, operatorName));
        if (operatorTicketId != null) propertiesBuilder.add(new StringProperty((byte) 0x02, operatorTicketId));
        if (startDate != null) propertiesBuilder.add(new CalendarProperty((byte) 0x03, startDate));
        if (endDate != null) propertiesBuilder.add(new CalendarProperty((byte) 0x04, endDate));

        return propertiesBuilder.toArray(new HMProperty[propertiesBuilder.size()]);
    }

    StartParking(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
