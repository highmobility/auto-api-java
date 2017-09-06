package com.highmobility.autoapi.incoming;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.byteutils.Bytes;

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
            int messageSize = Bytes.getInt(Arrays.copyOfRange(bytes, messageSizePosition, messageSizePosition + 2));
            text = new String(Arrays.copyOfRange(bytes, messageSizePosition + 2, messageSizePosition + 2 + messageSize), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new CommandParseException();
        }
    }
}
