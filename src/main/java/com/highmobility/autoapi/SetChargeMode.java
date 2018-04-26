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

import com.highmobility.autoapi.property.ChargeMode;

/**
 * Set the charge mode of the car.
 */
public class SetChargeMode extends Command {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x05);

    ChargeMode chargeMode;

    /**
     * @return The charge mode.
     */
    public ChargeMode getChargeMode() {
        return chargeMode;
    }

    /**
     * Set the charge mode of the car.
     *
     * @param chargeMode The charge mode.
     */
    public SetChargeMode(ChargeMode chargeMode) {
        super(TYPE.addByte(chargeMode.getByte()));
        this.chargeMode = chargeMode;
    }

    SetChargeMode(byte[] bytes) throws CommandParseException {
        super(bytes);
        this.chargeMode = ChargeMode.fromByte(bytes[3]);
    }
}
