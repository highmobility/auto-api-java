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

class KWiFiTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "005901" + 
            "01000401000101" +  // WiFi is enabled
            "02000401000101" +  // WiFi is connected
            "030007010004484f4d45" +  // WiFi network name is 'HOME'
            "04000401000103" // WiFi network uses the WPA2-Personal algorithm
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as WiFi.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = WiFi.State.Builder()
        builder.setStatus(Property(EnabledState.ENABLED))
        builder.setNetworkConnected(Property(ConnectionState.CONNECTED))
        builder.setNetworkSSID(Property("HOME"))
        builder.setNetworkSecurity(Property(NetworkSecurity.WPA2_PERSONAL))
        testState(builder.build())
    }
    
    private fun testState(state: WiFi.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getStatus().value == EnabledState.ENABLED)
        assertTrue(state.getNetworkConnected().value == ConnectionState.CONNECTED)
        assertTrue(state.getNetworkSSID().value == "HOME")
        assertTrue(state.getNetworkSecurity().value == NetworkSecurity.WPA2_PERSONAL)
    }
    
    @Test
    fun testGetState() {
        val bytes = Bytes(COMMAND_HEADER + "005900")
        assertTrue(WiFi.GetState() == bytes)
    }
    
    @Test
    fun testGetProperties() {
        val bytes = Bytes(COMMAND_HEADER + "0059000102030405")
        val getter = WiFi.GetProperties(0x01, 0x02, 0x03, 0x04, 0x05)
        assertTrue(getter == bytes)
    }
    
    @Test
    fun testConnectToNetwork() {
        val bytes = Bytes(COMMAND_HEADER + "005901" + 
            "030007010004484f4d45" +
            "04000401000103" +
            "05001101000E67726561745f7365637265743132")
    
        val constructed = WiFi.ConnectToNetwork("HOME", NetworkSecurity.WPA2_PERSONAL, "great_secret12")
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as WiFi.ConnectToNetwork
        assertTrue(resolved.getNetworkSSID().value == "HOME")
        assertTrue(resolved.getNetworkSecurity().value == NetworkSecurity.WPA2_PERSONAL)
        assertTrue(resolved.getPassword().value == "great_secret12")
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testForgetNetwork() {
        val bytes = Bytes(COMMAND_HEADER + "005901" + 
            "030007010004484f4d45")
    
        val constructed = WiFi.ForgetNetwork("HOME")
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as WiFi.ForgetNetwork
        assertTrue(resolved.getNetworkSSID().value == "HOME")
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testEnableDisableWiFi() {
        val bytes = Bytes(COMMAND_HEADER + "005901" + 
            "01000401000101")
    
        val constructed = WiFi.EnableDisableWiFi(EnabledState.ENABLED)
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as WiFi.EnableDisableWiFi
        assertTrue(resolved.getStatus().value == EnabledState.ENABLED)
        assertTrue(resolved == bytes)
    }
}