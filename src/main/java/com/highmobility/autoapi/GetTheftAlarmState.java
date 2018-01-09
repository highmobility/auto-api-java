package com.highmobility.autoapi;

/**
 * Get the theft alarm state. The car will respond with the Theft Alarm message.
 */
public class GetTheftAlarmState extends Command {
    public static final Type TYPE = new Type(Identifier.THEFT_ALARM, 0x00);

    public GetTheftAlarmState() {
        super(TYPE);
    }

    GetTheftAlarmState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
