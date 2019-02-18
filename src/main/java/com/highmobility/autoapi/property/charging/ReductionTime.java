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
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.value.StartStop;
import com.highmobility.autoapi.property.value.Time;
import com.highmobility.utils.ByteUtils;

import java.util.Arrays;

public class ReductionTime extends Property {
    Time time;
    StartStop startStop;

    public ReductionTime(StartStop startStop, Time time) {
        this((byte) 0x00, startStop, time);
    }

    public Time getTime() {
        return time;
    }

    public StartStop getStartStop() {
        return startStop;
    }

    public ReductionTime(byte identifier, StartStop startStop, Time time) {
        super(identifier, 3);
        this.startStop = startStop;
        this.time = time;
        bytes[6] = startStop.getByte();
        ByteUtils.setBytes(bytes, time.getByteArray(), 7);
    }

    public ReductionTime(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 9) throw new CommandParseException();
        this.startStop = StartStop.fromByte(bytes[6]);
        this.time = new Time(Arrays.copyOfRange(bytes, 7, 9));
    }
}
