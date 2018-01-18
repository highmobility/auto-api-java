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

package com.highmobility.autoapi.incoming;

import com.highmobility.autoapi.CommandParseException;

/**
 * Created by ttiganik on 16/12/2016.
 *
 * This is an evented message that is sent from the car every time the valet mode changes.
 * This message is also sent when a Get Valet Mode message is received by the car.
 */
public class ValetMode extends IncomingCommand {
    boolean isActive;

    ValetMode(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length != 4) throw new CommandParseException();

        isActive = bytes[3] == 0x01;
    }

    /**
     *
     * @return Whether Valet Mode is active
     */
    public boolean isActive() {
        return isActive;
    }
}
