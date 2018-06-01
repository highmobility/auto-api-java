package com.highmobility.autoapitest;

import com.highmobility.autoapi.ClearNotification;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.Notification;
import com.highmobility.autoapi.NotificationAction;
import com.highmobility.autoapi.property.ActionItem;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class NotificationsTest {
    @Test public void incomingNotification() {
        Bytes bytes = new Bytes
                ("0038000100115374617274206e617669676174696f6e3f020003004e6f02000401596573");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
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
        byte[] bytes = ByteUtils.bytesFromHex
                ("0038000100115374617274206e617669676174696f6e3f020003004e6f02000401596573");

        ActionItem action1 = null, action2 = null;
        action1 = new ActionItem(0, "No");
        action2 = new ActionItem(1, "Yes");
        ActionItem[] actions = new ActionItem[]{action1, action2};
        Notification notification = new Notification("Start navigation?", actions);

        // we expect that properties are ordered in this test. It should not matter really
        assertTrue(Arrays.equals(notification.getByteArray(), bytes));
    }

    @Test public void buildNotification() {
        Notification.Builder builder = new Notification.Builder();

        ActionItem action1 = null, action2 = null;
        action1 = new ActionItem(0, "No");
        action2 = new ActionItem(1, "Yes");
        ActionItem[] actions = new ActionItem[]{action1, action2};

        builder.setText("Start navigation?");
        builder.setActions(actions);

        Notification command = builder.build();
        assertTrue(Arrays.equals(command.getByteArray(), ByteUtils.bytesFromHex
                ("0038000100115374617274206e617669676174696f6e3f020003004e6f02000401596573")));
        assertTrue(command.getActions().length == 2);

        Notification.Builder builder2 = new Notification.Builder();
        builder2.setText("Start navigation?");
        builder2.addAction(actions[0]);
        builder2.addAction(actions[1]);

        Notification command2 = builder2.build();
        assertTrue(Arrays.equals(command2.getByteArray(), ByteUtils.bytesFromHex
                ("0038000100115374617274206e617669676174696f6e3f020003004e6f02000401596573")));
        assertTrue(command2.getActions().length == 2);
    }

    @Test public void incomingNotificationAction() {
        Bytes bytes = new Bytes(
                "003801FE");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command.getClass() == NotificationAction.class);
        NotificationAction state = (NotificationAction) command;
        assertTrue(state.getActionIdentifier() == 254);
    }

    @Test public void outgoingNotificationAction() {
        Bytes waitingForBytes = new Bytes
                ("003801FE");
        byte[] bytes = new NotificationAction(254).getByteArray();
        assertTrue(waitingForBytes.equals(bytes));

        NotificationAction action = (NotificationAction) CommandResolver.resolve(waitingForBytes);
        assertTrue(action.getActionIdentifier() == 254);
    }

    @Test public void buildNotificationAction() {
        NotificationAction.Builder builder = new NotificationAction.Builder();
        builder.setActionIdentifier(254);
        NotificationAction command = builder.build();
        assertTrue(command.getActionIdentifier() == 254);
        assertTrue(Arrays.equals(command.getByteArray(), ByteUtils.bytesFromHex("003801FE")));
    }

    @Test public void incomingClear() {
        Bytes waitingForBytes = new Bytes
                ("003802");

        Command command = null;
        try {
            command = CommandResolver.resolve(waitingForBytes);
        } catch (Exception e) {
            fail();
        }
        assertTrue(command.is(ClearNotification.TYPE));
    }

    @Test public void outgoingClear() {
        byte[] bytes = ByteUtils.bytesFromHex
                ("003802");
        assertTrue(Arrays.equals(new ClearNotification().getByteArray(), bytes));
    }
}