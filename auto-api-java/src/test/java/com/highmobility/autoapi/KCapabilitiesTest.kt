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

class KCapabilitiesTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "001001" + 
            "01000C010009002000050203040506" +  // Doors supports inside locks, locks, positions, inside locks state and locks state properties
            "01000A01000700230003020811" +  // Charging supports estimated range, charge limit and departure times properties
            "0200050100020101" +  // Webhook 'trip_started' is available to use.
            "0200050100020102" // Webhook 'trip_ended' is available to use.
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as Capabilities.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = Capabilities.State.Builder()
        builder.addCapability(Property(SupportedCapability(0x0020, Bytes(byteArrayOf(0x02, 0x03, 0x04, 0x05, 0x06)))))
        builder.addCapability(Property(SupportedCapability(0x0023, Bytes(byteArrayOf(0x02, 0x08, 0x11)))))
        builder.addWebhook(Property(Webhook(Webhook.Available.AVAILABLE, Event.TRIP_STARTED)))
        builder.addWebhook(Property(Webhook(Webhook.Available.AVAILABLE, Event.TRIP_ENDED)))
        testState(builder.build())
    }
    
    private fun testState(state: Capabilities.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.capabilities[0].value?.capabilityID == 0x0020)
        assertTrue(state.capabilities[0].value?.supportedPropertyIDs == Bytes(byteArrayOf(0x02, 0x03, 0x04, 0x05, 0x06)))
        assertTrue(state.capabilities[1].value?.capabilityID == 0x0023)
        assertTrue(state.capabilities[1].value?.supportedPropertyIDs == Bytes(byteArrayOf(0x02, 0x08, 0x11)))
        assertTrue(state.webhooks[0].value?.available == Webhook.Available.AVAILABLE)
        assertTrue(state.webhooks[0].value?.event == Event.TRIP_STARTED)
        assertTrue(state.webhooks[1].value?.available == Webhook.Available.AVAILABLE)
        assertTrue(state.webhooks[1].value?.event == Event.TRIP_ENDED)
    }
    
    @Test
    fun testGetCapabilities() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "001000")
        val defaultGetter = Capabilities.GetCapabilities()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0010000102")
        val propertyGetter = Capabilities.GetCapabilities(0x01, 0x02)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102"))
    }
}