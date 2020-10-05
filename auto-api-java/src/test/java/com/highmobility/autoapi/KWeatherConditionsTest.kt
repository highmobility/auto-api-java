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
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KWeatherConditionsTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "005501" + 
            "01000B0100083ff0000000000000" // Rain intensity is at 100% (maximum rain)
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as WeatherConditions.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = WeatherConditions.State.Builder()
        builder.setRainIntensity(Property(1.0))
        testState(builder.build())
    }
    
    private fun testState(state: WeatherConditions.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getRainIntensity().value == 1.0)
    }
    
    @Test
    fun testGetWeatherConditions() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "005500")
        val defaultGetter = WeatherConditions.GetWeatherConditions()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
    }
    
    @Test
    fun testGetWeatherConditionsAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "005502")
        val created = WeatherConditions.GetWeatherConditionsAvailability()
        assertTrue(created.identifier == Identifier.WEATHER_CONDITIONS)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as WeatherConditions.GetWeatherConditionsAvailability
        assertTrue(resolved.identifier == Identifier.WEATHER_CONDITIONS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
}