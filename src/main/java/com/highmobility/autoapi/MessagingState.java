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
 * The messaging state
 */
public class MessagingState extends SetCommand {
    public static final Identifier identifier = Identifier.MESSAGING;

    Property<String> text = new Property(String.class, 0x01);
    Property<String> handle = new Property(String.class, 0x02);

    /**
     * @return The text
     */
    public Property<String> getText() {
        return text;
    }

    /**
     * @return The optional handle of message
     */
    public Property<String> getHandle() {
        return handle;
    }

    MessagingState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return text.update(p);
                    case 0x02: return handle.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private MessagingState(Builder builder) {
        super(builder);

        text = builder.text;
        handle = builder.handle;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<String> text;
        private Property<String> handle;

        public Builder() {
            super(identifier);
        }

        public MessagingState build() {
            return new MessagingState(this);
        }

        /**
         * @param text The text
         * @return The builder
         */
        public Builder setText(Property<String> text) {
            this.text = text.setIdentifier(0x01);
            addProperty(this.text);
            return this;
        }
        
        /**
         * @param handle The optional handle of message
         * @return The builder
         */
        public Builder setHandle(Property<String> handle) {
            this.handle = handle.setIdentifier(0x02);
            addProperty(this.handle);
            return this;
        }
    }
}