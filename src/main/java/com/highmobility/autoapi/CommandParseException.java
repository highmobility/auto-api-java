/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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

/**
 * Created by ttiganik on 07/06/16.
 */
public class CommandParseException extends Exception{
    public enum CommandExceptionCode {
        // the command bytes could not be parsed
        PARSE_ERROR,
        // A property value was expected to be of a certain type that it wasn't
        UNSUPPORTED_VALUE_TYPE
    }

    public CommandExceptionCode code;

    public CommandParseException() {
        this.code = CommandExceptionCode.PARSE_ERROR;
    }

    public CommandParseException(CommandExceptionCode code) {
        this.code = code;
    }
}