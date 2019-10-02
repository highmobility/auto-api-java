package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.ActionItem;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class NotificationsTest extends BaseTest {
    Bytes notification = new Bytes(
            "003801" +
                    "0100140100115374617274206e617669676174696f6e3f" +
                    "0200080100050000024e6f" +
                    "020009010006010003596573" +
                    "0300040100012A" // 42
    );

    @Test public void incomingNotification() {
        Command command = CommandResolver.resolve(notification);
        testStateNotification((NotificationsState) command);
    }

    @Test public void outgoingNotification() {
        ActionItem action1, action2;
        action1 = new ActionItem(0, "No");
        action2 = new ActionItem(1, "Yes");
        ActionItem[] actions = new ActionItem[]{action1, action2};
        Notification state = new Notification("Start navigation?", actions);

        // we expect that items are ordered in this test. It should not matter really
        assertTrue(state.getText().getValue().equals("Start navigation?"));

        assertTrue(state.getActionItems()[0] != null);
        assertTrue(state.getActionItems()[1] != null);

        assertTrue(state.getActionItems()[0].getValue().getName().equals("No"));
        assertTrue(state.getActionItems()[1].getValue().getName().equals("Yes"));

        Bytes expected = new Bytes(
                "003801" +
                        "0100140100115374617274206e617669676174696f6e3f" +
                        "0200080100050000024e6f" +
                        "020009010006010003596573");

        assertTrue(bytesTheSame(state, expected));
    }

    @Test public void buildNotification() {
        NotificationsState.Builder builder = new NotificationsState.Builder();

        ActionItem action1, action2 = null;
        action1 = new ActionItem(0, "No");
        action2 = new ActionItem(1, "Yes");
        Property[] actions = new Property[]{new Property(action1), new Property(action2)};

        builder.setText(new Property("Start navigation?"));
        builder.setActionItems(actions);

        builder.setActivatedAction(new Property(42));
        testStateNotification(builder.build());
    }

    private void testStateNotification(NotificationsState state) {
        assertTrue(state.getText().getValue().equals("Start navigation?"));

        assertTrue(state.getActionItems()[0] != null);
        assertTrue(state.getActionItems()[1] != null);

        assertTrue(state.getActionItems()[0].getValue().getName().equals("No"));
        assertTrue(state.getActionItems()[1].getValue().getName().equals("Yes"));

        assertTrue(state.getActivatedAction().getValue() == 42);
        assertTrue(bytesTheSame(state, notification));
    }

    Bytes action = new Bytes("003801" +
            "030004010001FE");

    @Test public void buildAction() {
        // Action cannot be built from OEM side, it has to be a state.
        NotificationsState.Builder builder = new NotificationsState.Builder();
        builder.setActivatedAction(new Property(254));

        NotificationsState state = builder.build();
        assertTrue(state.getActivatedAction().getValue() == 254);
        assertTrue(bytesTheSame(state, action));
    }

    @Test public void action() {
        testStateAction(new Action(254));
        setRuntime(CommandResolver.RunTime.JAVA);
        Action command = (Action) CommandResolver.resolve(action);
        testStateAction(command);
    }

    private void testStateAction(Action state) {
        assertTrue(state.getActivatedAction().getValue() == 254);
        assertTrue(bytesTheSame(state, action));
    }

    @Test public void clear() {
        Bytes clear = new Bytes("00380104000401000100");

        assertTrue(bytesTheSame(new ClearNotification(), clear));

        setRuntime(CommandResolver.RunTime.JAVA);
        Command command = CommandResolver.resolve(clear);
        assertTrue(command instanceof ClearNotification);
    }
}
