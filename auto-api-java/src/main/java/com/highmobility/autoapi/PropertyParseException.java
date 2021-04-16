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

import com.highmobility.utils.ByteUtils;

/**
 * This exception can be thrown from commands, and is normal when Vehicle side is trying to parse the
 * correct setter. There is either wrong data in the properties or some of the properties are not
 * expected.
 */
public class PropertyParseException extends Exception {
    private ErrorCode code;

    public ErrorCode getCode() {
        return code;
    }

    enum ErrorCode {SETTER_SUPERFLUOUS_PROPERTY}

    public PropertyParseException(String description) {
        super(description);
    }

    public PropertyParseException(ErrorCode code, Class command, Byte propertyId) {
        super(superfluousPropertyErrorMessage(command, propertyId));
        this.code = code;
    }

    private static String superfluousPropertyErrorMessage(Class command, Byte propertyId) {
        return String.format("Superfluous property %s for %s",
                ByteUtils.hexFromByte(propertyId),
                command.getSimpleName()
        );
    }
}