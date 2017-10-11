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