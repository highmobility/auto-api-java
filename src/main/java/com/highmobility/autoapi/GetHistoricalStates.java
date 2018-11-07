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

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.CalendarProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;

import java.util.Calendar;

import javax.annotation.Nullable;

/**
 * Get historical states. The car will respond with the Historical States message.
 */
public class GetHistoricalStates extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HISTORICAL, 0x00);

    private static final byte IDENTIFIER_CAPABILITY = 0x01;
    private static final byte IDENTIFIER_START_DATE = 0x02;
    private static final byte IDENTIFIER_END_DATE = 0x03;

    private Identifier identifier;
    private Calendar startDate;
    private Calendar endDate;

    /**
     * @return The identifier of the command historical states are requested for.
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /**
     * @return The start date of the historical states.
     */
    @Nullable public Calendar getStartDate() {
        return startDate;
    }

    /**
     * @return The end date of the historical states.
     */
    @Nullable public Calendar getEndDate() {
        return endDate;
    }

    /**
     * @param identifier The identifier of the command historical states are requested for.
     * @param startDate  The start date of the historical states.
     * @param endDate    The end date of the historical states.
     */
    public GetHistoricalStates(Identifier identifier, @Nullable Calendar startDate, @Nullable
            Calendar endDate) {
        super(TYPE.addBytes(getBytes(identifier, startDate, endDate)));
        this.identifier = identifier;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private static byte[] getBytes(Identifier identifier, Calendar startDate, Calendar endDate) {
        byte[] bytes = new byte[5 + 11 + 11];

        Property identifierProperty = new Property(IDENTIFIER_CAPABILITY, identifier.getBytes());
        ByteUtils.setBytes(bytes, identifierProperty.getByteArray(), 0);

        if (startDate != null) {
            CalendarProperty startDateProp = new CalendarProperty(IDENTIFIER_START_DATE, startDate);
            ByteUtils.setBytes(bytes, startDateProp.getByteArray(), 5);
        }

        if (endDate != null) {
            CalendarProperty endDateProp = new CalendarProperty(IDENTIFIER_END_DATE, endDate);
            ByteUtils.setBytes(bytes, endDateProp.getByteArray(), 16);
        }

        return bytes;
    }

    GetHistoricalStates(byte[] bytes) {
        super(bytes);
        // TBODO: 31/10/2018
    }
}
