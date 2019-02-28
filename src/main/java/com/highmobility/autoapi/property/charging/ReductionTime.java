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

package com.highmobility.autoapi.property.charging;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.value.StartStop;
import com.highmobility.autoapi.property.value.Time;
import com.highmobility.value.Bytes;

public class ReductionTime extends PropertyValueObject {
    Time time;
    StartStop startStop;

    public Time getTime() {
        return time;
    }

    public StartStop getStartStop() {
        return startStop;
    }

    public ReductionTime(StartStop startStop, Time time) {
        super(3);
        this.startStop = startStop;
        this.time = time;

        set(0, startStop.getByte());
        set(1, time);
    }

    public ReductionTime() {
    }

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (getLength() < 3) throw new CommandParseException();
        this.startStop = StartStop.fromByte(get(0));
        this.time = new Time(getRange(1, 3));
    }
}
