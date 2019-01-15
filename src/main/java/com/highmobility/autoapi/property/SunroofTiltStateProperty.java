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
import com.highmobility.autoapi.property.value.rooftop.SunroofTiltState;

import java.util.Calendar;

import javax.annotation.Nullable;

public class SunroofTiltStateProperty extends Property {
    SunroofTiltState sunroofTiltState;

    public SunroofTiltState getValue() {
        return sunroofTiltState;
    }

    public SunroofTiltStateProperty(SunroofTiltState sunroofTiltState) {
        this((byte) 0x00, sunroofTiltState);
    }

    public SunroofTiltStateProperty(@Nullable SunroofTiltState sunroofTiltState,
                                    @Nullable Calendar timestamp,
                                    @Nullable PropertyFailure failure) {
        this(sunroofTiltState);
        setTimestampFailure(timestamp, failure);
    }

    public SunroofTiltStateProperty(byte identifier, SunroofTiltState sunroofTiltState) {
        super(identifier, sunroofTiltState == null ? 0 : 1);
        this.sunroofTiltState = sunroofTiltState;
        if (sunroofTiltState != null) bytes[3] = sunroofTiltState.getByte();
    }

    public SunroofTiltStateProperty(Property p) throws CommandParseException {
        super(p);
        update(p, null, null, false);
    }

    @Override
    public boolean update(Property p, PropertyFailure failure, PropertyTimestamp timestamp,
                          boolean propertyInArray) throws CommandParseException {
        if (p != null) sunroofTiltState = SunroofTiltState.fromByte(p.get(3));
        return super.update(p, failure, timestamp, propertyInArray);
    }

    public SunroofTiltStateProperty(byte identifier) {
        super(identifier);
    }

    // TBODO: ctors

}