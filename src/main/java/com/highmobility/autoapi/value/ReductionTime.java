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

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class ReductionTime extends PropertyValueObject {
    public static final int SIZE = 3;

    StartStop startStop;
    Time time;

    /**
     * @return The start stop.
     */
    public StartStop getStartStop() {
        return startStop;
    }

    /**
     * @return The time.
     */
    public Time getTime() {
        return time;
    }

    public ReductionTime(StartStop startStop, Time time) {
        super(3);
        update(startStop, time);
    }

    public ReductionTime(Property property) throws CommandParseException {
        super();
        if (property.getValueComponent() == null) throw new CommandParseException();
        update(property.getValueComponent().getValueBytes());
    }

    public ReductionTime() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        startStop = StartStop.fromByte(get(bytePosition));
        bytePosition += 1;

        int timeSize = Time.SIZE;
        time = new Time();
        time.update(getRange(bytePosition, bytePosition + timeSize));
    }

    public void update(StartStop startStop, Time time) {
        this.startStop = startStop;
        this.time = time;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, startStop.getByte());
        bytePosition += 1;

        set(bytePosition, time);
    }

    public void update(ReductionTime value) {
        update(value.startStop, value.time);
    }

    @Override public int getLength() {
        return 1 + 2;
    }
}