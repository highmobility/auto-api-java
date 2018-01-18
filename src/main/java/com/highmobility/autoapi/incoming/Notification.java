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

package com.highmobility.autoapi.incoming;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.NotificationAction;
import com.highmobility.autoapi.Property;
import com.highmobility.utils.Bytes;

import java.util.Arrays;

/**
 * Created by ttiganik on 13/09/16.
 */
public class Notification extends IncomingCommand {
    String text;
    NotificationAction[] notificationActions;

    /**
     *
     * @return Notification text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @return Notification actions
     */
    public NotificationAction[] getNotificationActions() {
        return notificationActions;
    }

    public Notification(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length < 3) throw new CommandParseException();

        byte[] lengthBytes = Arrays.copyOfRange(bytes, 3, 3 + 2);
        int textLength = Property.getUnsignedInt(lengthBytes);
        text = new String(Arrays.copyOfRange(bytes, 5, 5 + textLength));

        int actionItemsCountPosition = 5 + textLength;
        int actionItemCount = bytes[actionItemsCountPosition];
        int position = actionItemsCountPosition + 1;
        notificationActions = new NotificationAction[actionItemCount];
        for (int i = 0; i < actionItemCount; i++) {
            int identifier = bytes[position];
            int notificationTextLength = bytes[position + 1];
            String notificationText = new String (Arrays.copyOfRange(bytes, position + 2, position + 2 + notificationTextLength));
            position = position + 2 + notificationTextLength;
            NotificationAction action = new NotificationAction(identifier, notificationText);
            notificationActions[i] = action;
        }
    }
}
