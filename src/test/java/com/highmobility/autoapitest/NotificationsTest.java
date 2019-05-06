package com.highmobility.autoapitest;

import com.highmobility.autoapi.ClearNotification;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.Notification;
import com.highmobility.autoapi.NotificationAction;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActionItem;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

    Bytes actionBytes = new Bytes("003811" +
            "010004010001FE");

    Bytes clearBytes = new Bytes("003802");

    @Test public void incomingNotification() {
        Command command = CommandResolver.resolve(bytes);
        testStateNotification((Notification) command);
    }

    @Test public void outgoingNotification() {
        ActionItem action1, action2;
        action1 = new ActionItem(0, "No");
        action2 = new ActionItem(1, "Yes");
        ActionItem[] actions = new ActionItem[]{action1, action2};
        Notification notification = new Notification("Start navigation?", actions, 42);
        // we expect that items are ordered in this test. It should not matter really
        testStateNotification(notification);
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
        testStateNotification(builder.build());
    }

    private void testStateNotification(Notification state) {
        assertTrue(state.getText().getValue().equals("Start navigation?"));

        assertTrue(state.getAction(0) != null);
        assertTrue(state.getAction(1) != null);

        assertTrue(state.getAction(0).getValue().getName().equals("No"));
        assertTrue(state.getAction(1).getValue().getName().equals("Yes"));

        assertTrue(state.getReceivedAction().getValue() == 42);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void incomingNotificationAction() {
        NotificationAction command = (NotificationAction) CommandResolver.resolve(actionBytes);
        testStateNotificationAction(command);
    }

    @Test public void outgoingNotificationAction() {
        testStateNotificationAction(new NotificationAction(254));
    }

    @Test public void buildNotificationAction() {
        NotificationAction.Builder builder = new NotificationAction.Builder();
        builder.setActionIdentifier(new Property(254));
        testStateNotificationAction(builder.build());
    }

    private void testStateNotificationAction(NotificationAction state) {
        assertTrue(state.getActionIdentifier() == 254);
        assertTrue(TestUtils.bytesTheSame(state, actionBytes));
    }

    @Test public void incomingClear() {
        Command command = CommandResolver.resolve(clearBytes);
        assertTrue(command.is(ClearNotification.TYPE));
    }

    @Test public void outgoingClear() {
        assertTrue(TestUtils.bytesTheSame(new ClearNotification(), clearBytes));
    }

    @Test public void failsWherePropertiesMandatory() {
        TestUtils.errorLogExpected(() -> {
            assertTrue(CommandResolver.resolve(NotificationAction.TYPE.getIdentifierAndType()).getClass() == Command.class);
        });
    }
}