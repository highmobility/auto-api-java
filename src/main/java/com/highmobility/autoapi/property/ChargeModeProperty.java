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
import com.highmobility.autoapi.property.charging.ChargeMode;
import com.highmobility.value.Bytes;

import java.util.Calendar;

import javax.annotation.Nullable;

public class ChargeModeProperty extends Property {
    ChargeMode chargeMode;

    public ChargeMode getValue() {
        return chargeMode;
    }

    public ChargeModeProperty(ChargeMode ChargeMode) {
        this((byte) 0x00, ChargeMode);
    }

    public ChargeModeProperty(@Nullable ChargeMode ChargeMode, @Nullable Calendar timestamp,
                              @Nullable PropertyFailure failure) {
        this(ChargeMode);
        setTimestampFailure(timestamp, failure);
    }

    public ChargeModeProperty(byte identifier, ChargeMode chargeMode) {
        super(identifier, chargeMode == null ? 0 : 1);
        this.chargeMode = chargeMode;
        if (chargeMode != null) bytes[3] = chargeMode.getByte();
    }

    public ChargeModeProperty(Bytes bytes) throws CommandParseException {
        super(bytes);
        chargeMode = chargeMode.fromByte(bytes.get(3));
    }

    // TBODO: ctors

}