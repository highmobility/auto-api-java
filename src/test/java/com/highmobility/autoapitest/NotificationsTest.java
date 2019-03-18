package com.highmobility.autoapitest;

import com.highmobility.autoapi.ClearNotification;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.Notification;
import com.highmobility.autoapi.NotificationAction;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActionItem;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class NotificationsTest {
    Bytes bytes = new Bytes(
            "003800" +
                    "0100140100115374617274206e617669676174696f6e3f" +
                    "020006010003004e6f" +
                    "02000701000401596573" +
                    "1000040100012A" // 42
    );

    @Test public void incomingNotification() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.getClass() == Notification.class);
        Notification state = (Notification) command;
        assertTrue(state.getText().getValue().equals("Start navigation?"));

        assertTrue(state.getAction(0) != null);
        assertTrue(state.getAction(1) != null);

        assertTrue(state.getAction(0).getValue().getName().equals("No"));
        assertTrue(state.getAction(1).getValue().getName().equals("Yes"));

        assertTrue(state.getReceivedAction().getValue() == 42);
    }

    @Test public void outgoingNotification() {
        ActionItem action1, action2;
        action1 = new ActionItem(0, "No");
        action2 = new ActionItem(1, "Yes");
        ActionItem[] actions = new ActionItem[]{action1, action2};
        Notification notification = new Notification("Start navigation?", actions, 42);

        // we expect that testState are ordered in this test. It should not matter really
        assertTrue(TestUtils.bytesTheSame(notification, bytes));
    }

    @Test public void buildNotification() {
        Notification.Builder builder = new Notification.Builder();

        ActionItem action1 = null, action2 = null;
        action1 = new ActionItem(0, "No");
        action2 = new ActionItem(1, "Yes");
        Property[] actions = new Property[]{new Property(action1),
                new Property(action2)};

        builder.setText(new Property("Start navigation?"));
        builder.setActions(actions);

        builder.setReceivedAction(new Property(42));

        Notification command = builder.build();
        assertTrue(command.equals(bytes));
        assertTrue(command.getActions().length == 2);
    }

    @Test public void incomingNotificationAction() {
        Bytes bytes = new Bytes(
                "003811" +
                        "010004010001FE");

        NotificationAction command = (NotificationAction) CommandResolver.resolve(bytes);
        assertTrue(command.getActionIdentifier() == 254);
    }

    @Test public void outgoingNotificationAction() {
        Bytes waitingForBytes = new Bytes
                ("003811" +
                        "010004010001FE");
        byte[] bytes = new NotificationAction(254).getByteArray();
        assertTrue(waitingForBytes.equals(bytes));

        NotificationAction action = (NotificationAction) CommandResolver.resolve(waitingForBytes);
        assertTrue(action.getActionIdentifier() == 254);
    }

    @Test public void buildNotificationAction() {
        NotificationAction.Builder builder = new NotificationAction.Builder();
        builder.setActionIdentifier(new Property(254));
        NotificationAction command = builder.build();
        assertTrue(command.getActionIdentifier() == 254);
        assertTrue(Arrays.equals(command.getByteArray(), ByteUtils.bytesFromHex("003811" +
                "010004010001FE")));
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

    @Test public void failsWherePropertiesMandatory() {
        assertTrue(CommandResolver.resolve(NotificationAction.TYPE.getIdentifierAndType()).getClass() == Command.class);
    }
}