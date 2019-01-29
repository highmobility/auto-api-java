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
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyValue;
import com.highmobility.autoapi.property.value.StartStop;
import com.highmobility.autoapi.property.value.Time;
import com.highmobility.utils.ByteUtils;

import java.util.Arrays;
import java.util.Calendar;

import javax.annotation.Nullable;

public class ReductionTime extends Property {
    Value value;


    @Nullable public Value getValue() {
        return value;
    }

    public ReductionTime(byte identifier) {
        super(identifier);
    }

    public ReductionTime(StartStop startStop, Time time) {
        this(new Value(startStop, time));
    }

    public ReductionTime(Value value) {
        this((byte) 0x00, value);
    }

    public ReductionTime(@Nullable Value value, @Nullable Calendar timestamp,
                         @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public ReductionTime(byte identifier, Value value) {
        super(identifier, value);
        if (value != null) {
            bytes[3] = value.startStop.getByte();
            ByteUtils.setBytes(bytes, value.time.getByteArray(), 4);
            this.value = value;
        }
    }

    public ReductionTime(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);

        if (p.getValueLength() >= 3) this.value = new Value(bytes);

        return this;
    }

    public static class Value implements PropertyValue {
        Time time;
        StartStop startStop;

        public Time getTime() {
            return time;
        }

        public StartStop getStartStop() {
            return startStop;
        }

        public Value(StartStop startStop, Time time) {
            this.startStop = startStop;
            this.time = time;
        }

        public Value(byte[] bytes) throws CommandParseException {
            if (bytes.length < 6) throw new CommandParseException();
            this.startStop = StartStop.fromByte(bytes[3]);
            this.time = new Time(Arrays.copyOfRange(bytes, 4, 6));
        }

        @Override public int getLength() {
            return 3;
        }
    }
}
