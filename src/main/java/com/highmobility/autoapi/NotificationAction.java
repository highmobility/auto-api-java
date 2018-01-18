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

import com.highmobility.utils.Bytes;

import java.io.UnsupportedEncodingException;

/**
 * Created by root on 6/29/17.
 */

public class NotificationAction {
    public int getIdentifier() {
        return identifier;
    }

    public String getText() {
        return text;
    }

    int identifier;
    String text;

    public NotificationAction(int identifier, String text) {
        this.identifier = identifier;
        this.text = text;
    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] command = new byte[] {(byte) identifier, (byte) text.length()};
        command = Bytes.concatBytes(command, text.getBytes("UTF-8"));
        return command;
    }
}