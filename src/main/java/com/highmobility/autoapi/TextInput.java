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

/**
 * Send a keystroke or entire sentences as input to the car head unit. This can act as an
 * alternative to the input devices that the car is equipped with.
 */
public class TextInput extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.TEXT_INPUT, 0x00);
    private static final byte IDENTIFIER = 0x01;

    Property<String> text = new Property(String.class, IDENTIFIER);

    /**
     * @return The text.
     */
    public Property<String> getText() {
        return text;
    }

    /**
     * @param text The text.
     */
    public TextInput(String text) {
        super(TYPE);
        this.text.update(text);
        createBytes(this.text);
    }

    TextInput(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER:
                        return text.update(p);
                }
                return null;
            });
        }
    }
}