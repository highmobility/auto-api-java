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
 * Send an action to a previously received Notification message.
 */
public class NotificationAction extends Command {
    public static final Type TYPE = new Type(Identifier.NOTIFICATIONS, 0x01);

    int actionIdentifier;

    /**
     * @return The identifier of selected action item.
     */
    public int getActionIdentifier() {
        return actionIdentifier;
    }

    public NotificationAction(int actionIdentifier) {
        super(TYPE.addByte((byte) actionIdentifier));
        this.actionIdentifier = actionIdentifier;
    }

    public NotificationAction(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length != 4) throw new CommandParseException();
        actionIdentifier = Property.getUnsignedInt(bytes[3]);
    }

    private NotificationAction(Builder builder) {
        super(builder.getBytes());
        actionIdentifier = builder.actionIdentifier;
    }

    public static final class Builder {
        private int actionIdentifier;

        public Builder() {
        }

        /**
         * @param actionIdentifier The identifier of selected action item.
         * @return The builder.
         */
        public Builder setActionIdentifier(int actionIdentifier) {
            this.actionIdentifier = actionIdentifier;
            return this;
        }

        public NotificationAction build() {
            return new NotificationAction(this);
        }

        byte[] getBytes() {
            return TYPE.addByte((byte) actionIdentifier);
        }
    }
}
