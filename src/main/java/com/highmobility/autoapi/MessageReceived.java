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

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Command to notify the car that a message has been received. Depending on the car system, it will
 * display or read it loud to the driver.
 */
public class MessageReceived extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.MESSAGING, 0x00);

    private static final byte RECIPIENT_IDENTIFIER = 0x01;
    private static final byte MESSAGE_IDENTIFIER = 0x02;

    private String handle;
    private String message;

    /**
     * @return The sender handle (e.g. phone number).
     */
    @Nullable public String getSenderHandle() {
        return handle;
    }

    /**
     * @return The message content text.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param senderHandle The sender handle (e.g. phone number).
     * @param message      The message content text.
     */
    public MessageReceived(@Nullable String senderHandle, String message) {
        super(TYPE, getProperties(senderHandle, message));
        this.handle = senderHandle;
        this.message = message;
    }

    static Property[] getProperties(String handle, String message) {
        ArrayList<HMProperty> properties = new ArrayList<>();

        if (handle != null) {
            StringProperty prop = new StringProperty(RECIPIENT_IDENTIFIER, handle);
            properties.add(prop);
        }

        if (message != null) {
            StringProperty prop = new StringProperty(MESSAGE_IDENTIFIER, message);
            properties.add(prop);
        }

        return (properties.toArray(new Property[0]));
    }

    MessageReceived(byte[] bytes) {
        super(bytes);

        for (Property property : properties) {
            switch (property.getPropertyIdentifier()) {
                case RECIPIENT_IDENTIFIER:
                    handle = Property.getString(property.getValueBytes());
                    break;
                case MESSAGE_IDENTIFIER:
                    message = Property.getString(property.getValueBytes());
                    break;
            }
        }
    }
}
