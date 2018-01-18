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

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;

import com.highmobility.autoapi.incoming.TrunkState;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class TrunkAccess extends FeatureState {
    TrunkState.LockState lockState;
    TrunkState.Position position;

    public TrunkAccess(TrunkState.LockState lockState, TrunkState.Position position) {
        super(Command.Identifier.TRUNK_ACCESS);
        this.lockState = lockState;
        this.position = position;

        bytes = getBytesWithOneByteLongFields(2);
        bytes[3] = lockState.getByte();
        bytes[4] = position.getByte();
    }

    TrunkAccess(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.TRUNK_ACCESS);

        lockState = TrunkState.LockState.fromByte(bytes[3]);
        position = TrunkState.Position.fromByte(bytes[4]);
        this.bytes = bytes;
    }

    public TrunkState.LockState getLockState() {
        return lockState;
    }

    public TrunkState.Position getPosition() {
        return position;
    }
}
