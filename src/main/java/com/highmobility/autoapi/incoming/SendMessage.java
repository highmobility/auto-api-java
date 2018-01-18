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
import com.highmobility.autoapi.Property;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by ttiganik on 13/09/16.
 */
public class SendMessage extends IncomingCommand {
    String recipientHandle;
    String text;

    /**
     *
     * @return The recipient handle (e.g. phone number)
     */
    public String getRecipientHandle() {
        return recipientHandle;
    }

    /**
     *
     * @return The text message
     */
    public String getText() {
        return text;
    }

    public SendMessage(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length < 4) throw new CommandParseException();
        int recipientSize = bytes[3];
        try {
            recipientHandle = new String(Arrays.copyOfRange(bytes, 4, 4 + recipientSize), "UTF-8");
            int messageSizePosition = 4 + recipientSize;
            int messageSize = Property.getUnsignedInt(Arrays.copyOfRange(bytes, messageSizePosition, messageSizePosition + 2));
            text = new String(Arrays.copyOfRange(bytes, messageSizePosition + 2, messageSizePosition + 2 + messageSize), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new CommandParseException();
        }
    }
}
