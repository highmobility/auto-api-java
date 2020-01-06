/*
 * The MIT License
 *
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActionItem;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class NotificationsTest extends BaseTest {
    Bytes notification = new Bytes(
            COMMAND_HEADER + "003801" +
                    "0100140100115374617274206e617669676174696f6e3f" +
                    "0200080100050000024e6f" +
                    "020009010006010003596573" +
                    "0300040100012A" // 42
    );

    @Test public void incomingNotification() {
        Command command = CommandResolver.resolve(notification);
        testStateNotification((Notifications.State) command);
    }

    @Test public void outgoingNotification() {
        ActionItem action1, action2;
        action1 = new ActionItem(0, "No");
        action2 = new ActionItem(1, "Yes");
        ActionItem[] actions = new ActionItem[]{action1, action2};
        Notifications.Notification state = new Notifications.Notification("Start navigation?", actions);

        // we expect that items are ordered in this test. It should not matter really
        assertTrue(state.getText().getValue().equals("Start navigation?"));

        assertTrue(state.getActionItems()[0] != null);
        assertTrue(state.getActionItems()[1] != null);

        assertTrue(state.getActionItems()[0].getValue().getName().equals("No"));
        assertTrue(state.getActionItems()[1].getValue().getName().equals("Yes"));

        Bytes expected = new Bytes(
                COMMAND_HEADER + "003801" +
                        "0100140100115374617274206e617669676174696f6e3f" +
                        "0200080100050000024e6f" +
                        "020009010006010003596573");

        assertTrue(bytesTheSame(state, expected));
    }

    @Test public void buildNotification() {
        Notifications.State.Builder builder = new Notifications.State.Builder();

        ActionItem action1, action2;
        action1 = new ActionItem(0, "No");
        action2 = new ActionItem(1, "Yes");
        Property[] actions = new Property[]{new Property(action1), new Property(action2)};

        builder.setText(new Property("Start navigation?"));
        builder.setActionItems(actions);

        builder.setActivatedAction(new Property(42));
        testStateNotification(builder.build());
    }

    private void testStateNotification(Notifications.State state) {
        assertTrue(state.getText().getValue().equals("Start navigation?"));

        assertTrue(state.getActionItems()[0] != null);
        assertTrue(state.getActionItems()[1] != null);

        assertTrue(state.getActionItems()[0].getValue().getName().equals("No"));
        assertTrue(state.getActionItems()[1].getValue().getName().equals("Yes"));

        assertTrue(state.getActivatedAction().getValue() == 42);
        assertTrue(bytesTheSame(state, notification));
    }

    Bytes action = new Bytes(COMMAND_HEADER + "003801" +
            "030004010001FE");

    @Test public void buildAction() {
        // Action cannot be built from OEM side, it has to be a state.
        Notifications.State.Builder builder = new Notifications.State.Builder();
        builder.setActivatedAction(new Property(254));

        Notifications.State state = builder.build();
        assertTrue(state.getActivatedAction().getValue() == 254);
        assertTrue(bytesTheSame(state, action));
    }

    @Test public void action() {
        testStateAction(new Notifications.Action(254));
        setRuntime(CommandResolver.RunTime.JAVA);
        Notifications.Action command = (Notifications.Action) CommandResolver.resolve(action);
        testStateAction(command);
    }

    private void testStateAction(Notifications.Action state) {
        assertTrue(state.getActivatedAction().getValue() == 254);
        assertTrue(bytesTheSame(state, action));
    }

    @Test public void clear() {
        Bytes clear = new Bytes(COMMAND_HEADER + "00380104000401000100");

        assertTrue(bytesTheSame(new Notifications.ClearNotification(), clear));

        setRuntime(CommandResolver.RunTime.JAVA);
        Command command = CommandResolver.resolve(clear);
        assertTrue(command instanceof Notifications.ClearNotification);
    }
}
