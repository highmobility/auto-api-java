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

import com.highmobility.autoapi.exception.ParseException;
import com.highmobility.value.Bytes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Command extends Bytes {
    public static final Logger logger = LoggerFactory.getLogger(Command.class);

    Type type;

    Command(byte[] bytes) {
        super(bytes);
        setTypeAndBytes(bytes);
    }

    Command(Type type) {
        super(type.getIdentifierAndType());
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    /**
     * @param type The command value to compare this command with.
     * @return True if the command has the given commandType.
     */
    public boolean is(Type type) {
        return type.equals(this.type);
    }

    private void setTypeAndBytes(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return; // empty IncomingCommand
        if (bytes.length < 3) throw new ParseException();
        this.type = new Type(bytes[0], bytes[1], bytes[2]);
    }
}
