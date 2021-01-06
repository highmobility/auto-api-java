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
package com.highmobility.autoapi

import com.highmobility.autoapi.property.Property
import com.highmobility.autoapi.value.*
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KNotificationsTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "003801" + 
            "01000E01000B4f70656e20476172616765" +  // Notification text says 'Open Garage'
            "02000A0100071b00044f70656e" +  // Notification action named 'Open' with an ID 27
            "02000C0100091c000643616e63656c" +  // Notification action named 'Cancel' with an ID 28
            "0300040100011b" +  // Activated action with ID 27
            "04000401000100" // Clear notifications
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Notifications.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Notifications.State.Builder()
        builder.setText(Property("Open Garage"))
        builder.addActionItem(Property(ActionItem(27, "Open")))
        builder.addActionItem(Property(ActionItem(28, "Cancel")))
        builder.setActivatedAction(Property(27))
        builder.setClear(Property(Notifications.Clear.CLEAR))
        testState(builder.build())
    }
    
    private fun testState(state: Notifications.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.text.value == "Open Garage")
        assertTrue(state.actionItems[0].value?.id == 27)
        assertTrue(state.actionItems[0].value?.name == "Open")
        assertTrue(state.actionItems[1].value?.id == 28)
        assertTrue(state.actionItems[1].value?.name == "Cancel")
        assertTrue(state.activatedAction.value == 27)
        assertTrue(state.clear.value == Notifications.Clear.CLEAR)
    }
    
    @Test
    fun notification() {
        val bytes = Bytes(COMMAND_HEADER + "003801" +
            "01000E01000B4f70656e20476172616765" +
            "02000A0100071b00044f70656e" +
            "02000C0100091c000643616e63656c")
    
        val constructed = Notifications.Notification("Open Garage", 
            arrayListOf(
            ActionItem(27, "Open"), 
            ActionItem(28, "Cancel")))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Notifications.Notification
        assertTrue(resolved.text.value == "Open Garage")
        assertTrue(resolved.actionItems[0].value?.id == 27)
        assertTrue(resolved.actionItems[0].value?.name == "Open")
        assertTrue(resolved.actionItems[1].value?.id == 28)
        assertTrue(resolved.actionItems[1].value?.name == "Cancel")
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun action() {
        val bytes = Bytes(COMMAND_HEADER + "003801" +
            "0300040100011b")
    
        val constructed = Notifications.Action(27)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Notifications.Action
        assertTrue(resolved.activatedAction.value == 27)
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun clearNotification() {
        val bytes = Bytes(COMMAND_HEADER + "003801" +
            "04000401000100")
    
        val constructed = Notifications.ClearNotification()
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as Notifications.ClearNotification
        assertTrue(resolved == bytes)
    }
    
    @Test fun invalidClearNotificationClearThrows() {
        val bytes = Bytes(COMMAND_HEADER + "003801" +
            "040004010001CD")
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        debugLogExpected { 
            val resolved = CommandResolver.resolve(bytes)
            assertTrue(resolved is Command)
        }
    }
}