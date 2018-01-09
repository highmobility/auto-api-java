package com.highmobility.autoapi;

public class SetTheftAlarm extends Command {
    public static final Type TYPE = new Type(Identifier.THEFT_ALARM, 0x02);

    public SetTheftAlarm(TheftAlarmState.State state) {
        super(TYPE.addByte(state.getByte()), true);
    }

    SetTheftAlarm(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
