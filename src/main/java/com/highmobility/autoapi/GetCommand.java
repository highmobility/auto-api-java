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

import com.highmobility.value.Bytes;

/**
 * @param <T> response class
 */
public abstract class GetCommand<T extends Command> extends Command {
    Bytes propertyIdentifiers;
    final Class<T> responseClass;

    /**
     * @return The queried property identifiers.
     */
    public Bytes getPropertyIdentifiers() {
        return propertyIdentifiers;
    }

    /**
     * @return The response class.
     */
    public Class<T> getResponseClass() {
        return responseClass;
    }

    // the get state ctor
    GetCommand(Class<T> responseClass, Integer identifier) {
        super(identifier, 3);
        this.responseClass = responseClass;
        set(COMMAND_TYPE_POSITION, (byte) 0x00);
        type = Type.GET;
        propertyIdentifiers = new Bytes();
    }

    GetCommand(Class<T> responseClass, Integer identifier, Bytes propertyIdentifiers) {
        super(identifier, 3 + (propertyIdentifiers != null ? propertyIdentifiers.getLength() : 0));
        this.responseClass = responseClass;
        set(COMMAND_TYPE_POSITION, (byte) 0x00);
        set(COMMAND_TYPE_POSITION + 1, propertyIdentifiers);
        type = Type.GET;
        this.propertyIdentifiers = propertyIdentifiers;
    }

    GetCommand(Class<T> responseClass, byte[] bytes) throws CommandParseException {
        super(bytes);
        this.responseClass = responseClass;
        if (bytes[COMMAND_TYPE_POSITION] != Type.GET) throw new CommandParseException();
        propertyIdentifiers = getRange(COMMAND_TYPE_POSITION + 1, getLength());
    }
}
