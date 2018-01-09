package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.utils.Bytes;

import java.io.UnsupportedEncodingException;

public class ActionItem implements HMProperty {
    byte[] bytes;
    int actionIdentifier;
    String name;

    /**
     *
     * @return The action item identifier
     */
    public int getActionIdentifier() {
        return actionIdentifier;
    }

    /**
     *
     * @return Name of the action item
     */
    public String getName() {
        return name;
    }

    public ActionItem(int actionIdentifier, String name) throws UnsupportedEncodingException {
        this.actionIdentifier = actionIdentifier;
        this.name = name;

        bytes = new byte[]{ getPropertyIdentifier() };
        bytes = Bytes.concatBytes(bytes, Bytes.intToBytes(getPropertyLength(), 2));
        bytes = Bytes.concatBytes(bytes, (byte) getActionIdentifier());
        bytes = Bytes.concatBytes(bytes, name.getBytes(StringProperty.CHARSET));
    }

    public ActionItem(byte[] bytes) throws CommandParseException, UnsupportedEncodingException {
        if (bytes.length < 5) throw new CommandParseException();
        this.bytes = bytes;

        actionIdentifier = bytes[3];
        name = Property.getString(bytes, 4, bytes.length - 4);
    }

    @Override public byte getPropertyIdentifier() {
        return 0x02;
    }

    @Override public int getPropertyLength() {
        return 1 + name.length();
    }

    @Override public byte[] getPropertyBytes()  {
        return bytes;
    }
}
