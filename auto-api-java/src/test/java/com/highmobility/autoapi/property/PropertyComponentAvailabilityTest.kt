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
package com.highmobility.autoapi.property;

import com.highmobility.autoapi.BaseTest
import com.highmobility.autoapi.value.Availability
import com.highmobility.autoapi.value.measurement.Frequency
import com.highmobility.value.Bytes
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PropertyComponentAvailabilityTest : BaseTest() {

    @Test
    fun bytesCtor() {
        val availability = PropertyComponentAvailability(Bytes("05000C000e003fe999999999999a01"))
        assertTrue(availability.availability.updateRate == Availability.UpdateRate.TRIP_HIGH)
        assertTrue(availability.availability.rateLimit.value == 0.8)
        assertTrue(availability.availability.rateLimit.unit == Frequency.Unit.HERTZ)
        assertTrue(availability.availability.appliesPer == Availability.AppliesPer.VEHICLE)
        assertTrue(availability.identifier == 0x05.toByte())
        assertTrue(availability.equals("05000C000e003fe999999999999a01"))
    }

    @Test
    fun valuesCtor() {
        val availability = Availability(
            Availability.UpdateRate.TRIP_HIGH,
            Frequency(0.8, Frequency.Unit.HERTZ),
            Availability.AppliesPer.VEHICLE
        )
        val availabilityComponent = PropertyComponentAvailability(availability)

        assertTrue(availabilityComponent.availability == availability)
        assertTrue(availabilityComponent.identifier == 0x05.toByte())
        assertTrue(availabilityComponent.equals("05000C000e003fe999999999999a01"))
    }

    /*
  - id: 0x05
    name: availability
    size: 12
    examples:
      - data_component: '000e003fe999999999999a01'
        values:
          update_rate: 'trip_high'
          rate_limit:
            hertz: 0.8
          applies_per: 'vehicle'
        description: Property is updated with a high frequency of 0.8Hz per vehicle.
        */
}