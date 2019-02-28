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

package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.utils.ByteUtils;

import java.util.Calendar;

public class CalendarProperty extends Property {
    public static final int CALENDAR_SIZE = 8;

    private Calendar calendar;

    public Calendar getValue() {
        return calendar;
    }

    public CalendarProperty(byte identifier) {
        super(identifier);
    }

    public CalendarProperty(Calendar c) {
        this((byte) 0x00, c);
    }

    public CalendarProperty(byte identifier, Calendar calendar) {
        super(identifier, CALENDAR_SIZE);
        this.calendar = calendar;
        ByteUtils.setBytes(bytes, Property.calendarToBytes(calendar), 6);
    }

    public CalendarProperty(Property p) throws CommandParseException {
        super(p);
        update(p);
    }

    @Override public Property update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() >= CALENDAR_SIZE) calendar = Property.getCalendar(p.getValueBytesArray());
        return this;
    }
}
