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

import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;

/**
 * Send an action to a previously received Notification message.
 */
public class NotificationAction extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.NOTIFICATIONS, 0x11);

    private static final byte IDENTIFIER = 0x01;

    int actionIdentifier;

    /**
     * @return The identifier of selected action item.
     */
    public int getActionIdentifier() {
        return actionIdentifier;
    }

    public NotificationAction(int actionIdentifier) {
        super(TYPE.addProperty(new IntegerProperty(IDENTIFIER, actionIdentifier, 1)));
        this.actionIdentifier = actionIdentifier;
    }

    public NotificationAction(byte[] bytes) {
        super(bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                if (p.getPropertyIdentifier() == IDENTIFIER) {
                    actionIdentifier = Property.getUnsignedInt(p.getValueByte());
                    return actionIdentifier;
                }

                return null;
            });
        }
    }

    private NotificationAction(Builder builder) {
        super(builder);
        actionIdentifier = builder.actionIdentifier;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private int actionIdentifier;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param actionIdentifier The identifier of selected action item.
         * @return The builder.
         */
        public Builder setActionIdentifier(int actionIdentifier) {
            this.actionIdentifier = actionIdentifier;
            addProperty(new IntegerProperty(IDENTIFIER, actionIdentifier, 1));
            return this;
        }

        public NotificationAction build() {
            return new NotificationAction(this);
        }
    }
}
