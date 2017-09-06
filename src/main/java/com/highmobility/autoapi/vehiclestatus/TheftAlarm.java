package com.highmobility.autoapi.vehiclestatus;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.incoming.TheftAlarmState;
import com.highmobility.autoapi.Command.Identifier;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class TheftAlarm extends FeatureState {
    TheftAlarmState.State state;

    /**
     *
     * @return Theft alarm state
     */
    public TheftAlarmState.State getState() {
        return state;
    }

    public TheftAlarm(byte[] bytes) throws CommandParseException {
        super(Identifier.THEFT_ALARM);

        if (bytes.length < 4) throw new CommandParseException();
        state = TheftAlarmState.State.fromByte(bytes[3]);
    }
}