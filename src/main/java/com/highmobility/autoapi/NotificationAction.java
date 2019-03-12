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
import com.highmobility.autoapi.property.IntegerProperty;

/**
 * Send an action to a previously received Notification message.
 */
public class NotificationAction extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.NOTIFICATIONS, 0x11);

    private static final byte IDENTIFIER = 0x01;

    IntegerProperty actionIdentifier = new IntegerProperty(IDENTIFIER, false);

    /**
     * @return The identifier of selected action item.
     */
    public int getActionIdentifier() {
        return actionIdentifier.getValue();
    }

    /**
     * @param actionIdentifier The identifier of selected action item.
     */
    public NotificationAction(int actionIdentifier) {
        super(TYPE);
        this.actionIdentifier.update(false, 1, actionIdentifier);
        createBytes(this.actionIdentifier);
    }

    NotificationAction(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                if (p.getPropertyIdentifier() == IDENTIFIER) {
                    return actionIdentifier.update(p);
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
        private IntegerProperty actionIdentifier;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param actionIdentifier The identifier of selected action item.
         * @return The builder.
         */
        public Builder setActionIdentifier(Property<Integer> actionIdentifier) {
            this.actionIdentifier = new IntegerProperty(IDENTIFIER, false, 1, actionIdentifier);
            addProperty(this.actionIdentifier);
            return this;
        }

        public NotificationAction build() {
            return new NotificationAction(this);
        }
    }
}
