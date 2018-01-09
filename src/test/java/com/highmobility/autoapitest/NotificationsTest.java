package com.highmobility.autoapitest;

import com.highmobility.autoapi.ClearNotification;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.Notification;
import com.highmobility.autoapi.NotificationAction;
import com.highmobility.autoapi.property.ActionItem;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class NotificationsTest {
    @Test public void incomingNotification() {
        byte[] bytes = Bytes.bytesFromHex
                ("0038000100115374617274206e617669676174696f6e3f020003004e6f02000401596573");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == Notification.class);
        Notification state = (Notification) command;
        assertTrue(state.getText().equals("Start navigation?"));

        assertTrue(state.getAction(0) != null);
        assertTrue(state.getAction(1) != null);

        assertTrue(state.getAction(0).getName().equals("No"));
        assertTrue(state.getAction(1).getName().equals("Yes"));
    }


    @Test public void outgoingNotification() {
        byte[] bytes = Bytes.bytesFromHex
                ("0038000100115374617274206e617669676174696f6e3f020003004e6f02000401596573");

        try {
            ActionItem action1 = null, action2 = null;
            action1 = new ActionItem(0, "No");
            action2 = new ActionItem(1, "Yes");
            ActionItem[] actions = new ActionItem[] { action1, action2 };
            Notification notification = new Notification("Start navigation?", actions);

            // we expect that properties are ordered in this test. It should not matter really
            assertTrue(Arrays.equals(notification.getBytes(), bytes));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fail();
        } catch (CommandParseException e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test public void incomingNotificationAction() throws CommandParseException {
        byte[] bytes = Bytes.bytesFromHex
                ("003801FE");

        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.getClass() == NotificationAction.class);
        NotificationAction state = (NotificationAction) command;
        assertTrue(state.getActionIdentifier() == 254);
    }


    @Test public void outgoingNotificationAction() {
        byte[] expecting = Bytes.bytesFromHex
                ("003801FE");
        byte[] bytes = new NotificationAction(254).getBytes();
        assertTrue(Arrays.equals(expecting, bytes));
    }

    @Test public void incomingClear() throws CommandParseException {
        byte[] bytes = Bytes.bytesFromHex
                ("003802");

        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(ClearNotification.TYPE));
    }


    @Test public void outgoingClear() {
        byte[] bytes = Bytes.bytesFromHex
                ("003802");
        assertTrue(Arrays.equals(new ClearNotification().getBytes(), bytes));
    }
}