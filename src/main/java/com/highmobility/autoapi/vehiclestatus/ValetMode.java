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

package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.Property;
import com.highmobility.utils.Bytes;

/**
 * Created by ttiganik on 16/12/2016.
 */

public class ValetMode extends FeatureState {
    boolean isActive;

    /**
     *
     * @return Whether Valet Mode is active
     */
    public boolean isActive() {
        return isActive;
    }

    public ValetMode(boolean isActive) {
        super(Command.Identifier.VALET_MODE);
        this.isActive = isActive;
        bytes = getBytesWithOneByteLongFields(1);
        bytes[3] = Property.boolToByte(isActive);
    }

    ValetMode(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.VALET_MODE);
        if (bytes.length != 4) throw new CommandParseException();
        isActive = bytes[3] == 0x01;
        this.bytes = bytes;
    }
}
