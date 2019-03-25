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

import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.Nullable;

/**
 * Get historical states. The car will respond with the Historical States message.
 */
public class GetHistoricalStates extends Command {
    public static final Type TYPE = new Type(Identifier.HISTORICAL, 0x00);

    private static final byte IDENTIFIER_CAPABILITY = 0x01;
    private static final byte IDENTIFIER_START_DATE = 0x02;
    private static final byte IDENTIFIER_END_DATE = 0x03;

    private Property<Identifier> identifier = new Property(Identifier.class, IDENTIFIER_CAPABILITY);
    private Property<Calendar> startDate = new Property(Calendar.class, IDENTIFIER_START_DATE);
    private Property<Calendar> endDate = new Property(Calendar.class, IDENTIFIER_END_DATE);

    /**
     * @return The identifier of the command historical states are requested for.
     */
    public Property<Identifier> getIdentifier() {
        return identifier;
    }

    /**
     * @return The start date of the historical states.
     */
    public Property<Calendar> getStartDate() {
        return startDate;
    }

    /**
     * @return The end date of the historical states.
     */
    public Property<Calendar> getEndDate() {
        return endDate;
    }

    /**
     * @param identifier The identifier of the command historical states are requested for.
     * @param startDate  The start date of the historical states.
     * @param endDate    The end date of the historical states.
     */
    public GetHistoricalStates(Identifier identifier, @Nullable Calendar startDate, @Nullable
            Calendar endDate) {
        super(TYPE);

        ArrayList<Property> properties = new ArrayList<>();

        this.identifier.update(identifier);
        properties.add(this.identifier);

        if (startDate != null) {
            this.startDate.update(startDate);
            properties.add(this.startDate);
        }

        if (endDate != null) {
            this.endDate.update(endDate);
            properties.add(this.endDate);
        }

        createBytes(properties);
    }

    GetHistoricalStates(byte[] bytes) {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_CAPABILITY:
                        return identifier.update(p);
                    case IDENTIFIER_START_DATE:
                        return startDate.update(p);
                    case IDENTIFIER_END_DATE:
                        return endDate.update(p);
                }

                return null;
            });
        }
    }

    @Override protected boolean propertiesExpected() {
        return true;
    }
}
