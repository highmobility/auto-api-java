/*
 * The MIT License
 *
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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

    public CommandParseException(String error) {
        super(error);
        this.code = CommandExceptionCode.PARSE_ERROR;
    }
}