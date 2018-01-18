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