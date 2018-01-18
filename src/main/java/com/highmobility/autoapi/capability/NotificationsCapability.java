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

package com.highmobility.autoapi.capability;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;

/**
 * Created by root on 6/21/17.
 */

public class NotificationsCapability extends FeatureCapability {
    AvailableCapability.Capability notification;
    AvailableCapability.Capability notificationAction;

    public NotificationsCapability(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.NOTIFICATIONS);
        if (bytes.length != 5) throw new CommandParseException();
        notification = AvailableCapability.Capability.fromByte(bytes[3]);
        notificationAction = AvailableCapability.Capability.fromByte(bytes[4]);
    }

    public NotificationsCapability(AvailableCapability.Capability notification, AvailableCapability.Capability notificationAction) {
        super(Command.Identifier.NOTIFICATIONS);
        this.notification = notification;
        this.notificationAction = notificationAction;
    }

    public AvailableCapability.Capability getNotification() {
        return notification;
    }

    public AvailableCapability.Capability getNotificationAction() {
        return notificationAction;
    }

    @Override public byte[] getBytes() {
        byte[] bytes = getBytesWithCapabilityCount(2);
        bytes[3] = notification.getByte();
        bytes[4] = notificationAction.getByte();
        return bytes;
    }
}
