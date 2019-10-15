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

import com.highmobility.autoapi.property.Property;
import javax.annotation.Nullable;

/**
 * Message received
 */
public class MessageReceived extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.MESSAGING;

    public static final byte IDENTIFIER_TEXT = 0x01;
    public static final byte IDENTIFIER_HANDLE = 0x02;

    Property<String> text = new Property(String.class, IDENTIFIER_TEXT);
    @Nullable Property<String> handle = new Property(String.class, IDENTIFIER_HANDLE);

    /**
     * @return The text
     */
    public Property<String> getText() {
        return text;
    }
    
    /**
     * @return The handle
     */
    public @Nullable Property<String> getHandle() {
        return handle;
    }
    
    /**
     * Message received
     *
     * @param text The text
     * @param handle The optional handle of message
     */
    public MessageReceived(String text, @Nullable String handle) {
        super(IDENTIFIER);
    
        addProperty(this.text.update(text));
        addProperty(this.handle.update(handle), true);
    }

    MessageReceived(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_TEXT: return text.update(p);
                    case IDENTIFIER_HANDLE: return handle.update(p);
                }
                return null;
            });
        }
        if (this.text.getValue() == null) 
            throw new NoPropertiesException();
    }
}