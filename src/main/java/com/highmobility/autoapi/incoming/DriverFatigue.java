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
 * Created by root on 6/28/17.
 */
public class DriverFatigue extends IncomingCommand {
    public enum FatigueLevel {
        LIGHT, PAUSE_RECOMMENDED, DRIVER_NEEDS_REST, READY_TO_TAKE_OVER;

        static FatigueLevel fromByte(byte value) {
            switch (value) {
                case 0x00:
                    return LIGHT;
                case 0x01:
                    return PAUSE_RECOMMENDED;
                case 0x02:
                    return DRIVER_NEEDS_REST;
                case 0x03:
                    return READY_TO_TAKE_OVER;
            }
            return LIGHT;
        }
    }

    FatigueLevel fatigueLevel;

    /**
     *
     * @return An evented message that notifies about driver fatigue. Sent continously when level 1 or higher.
     */
    public FatigueLevel getFatigueLevel() {
        return fatigueLevel;
    }

    public DriverFatigue(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 4) throw new CommandParseException();

        fatigueLevel = FatigueLevel.fromByte(bytes[3]);
    }
}
