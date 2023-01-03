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
package com.highmobility.autoapi.value.measurement

import com.highmobility.autoapi.CommandParseException
import com.highmobility.autoapi.property.Property
import com.highmobility.value.Bytes

class Mass : MeasurementType {
    val value: Double
    val unit: Unit

    constructor(value: Double, unit: Unit) : super() {
        this.value = value
        this.unit = unit
    
        bytes[0] = 0x13
        bytes[1] = unit.id
        set(2, Property.doubleToBytes(value))
    }

    constructor(valueBytes: Bytes) : super(valueBytes, 0x13) {
        unit = Unit.fromInt(valueBytes[1])
        value = Property.getDouble(valueBytes, 2)
    }

    fun inKilograms(): Double {
        return when (unit) {
            Unit.KILOGRAMS -> value * 1.0
            Unit.GRAMS -> value * 1.0e-3
            Unit.DECIGRAMS -> value * 1.0e-4
            Unit.CENTIGRAMS -> value * 1.0e-5
            Unit.MILLIGRAMS -> value * 1.0e-6
            Unit.MICROGRAMS -> value * 1.0e-9
            Unit.NANOGRAMS -> value * 1.0e-12
            Unit.PICOGRAMS -> value * 1.0e-15
            Unit.OUNCES -> value * 0.0283495
            Unit.POUNDS -> value * 0.453592
            Unit.STONES -> value * 0.157473
            Unit.METRIC_TONS -> value * 1.0e3
            Unit.SHORT_TONS -> value * 907.185
            Unit.CARATS -> value * 0.0002
            Unit.OUNCES_TROY -> value * 0.03110348
            Unit.SLUGS -> value * 14.5939
        }
    }
    
    fun inGrams() = inKilograms() / 1.0e-3
    
    fun inDecigrams() = inKilograms() / 1.0e-4
    
    fun inCentigrams() = inKilograms() / 1.0e-5
    
    fun inMilligrams() = inKilograms() / 1.0e-6
    
    fun inMicrograms() = inKilograms() / 1.0e-9
    
    fun inNanograms() = inKilograms() / 1.0e-12
    
    fun inPicograms() = inKilograms() / 1.0e-15
    
    fun inOunces() = inKilograms() / 0.0283495
    
    fun inPounds() = inKilograms() / 0.453592
    
    fun inStones() = inKilograms() / 0.157473
    
    fun inMetricTons() = inKilograms() / 1.0e3
    
    fun inShortTons() = inKilograms() / 907.185
    
    fun inCarats() = inKilograms() / 0.0002
    
    fun inOuncesTroy() = inKilograms() / 0.03110348
    
    fun inSlugs() = inKilograms() / 14.5939
    
    enum class Unit(val id: Byte) {
        KILOGRAMS(0x00),
        GRAMS(0x01),
        DECIGRAMS(0x02),
        CENTIGRAMS(0x03),
        MILLIGRAMS(0x04),
        MICROGRAMS(0x05),
        NANOGRAMS(0x06),
        PICOGRAMS(0x07),
        OUNCES(0x08),
        POUNDS(0x09),
        STONES(0x0a),
        METRIC_TONS(0x0b),
        SHORT_TONS(0x0c),
        CARATS(0x0d),
        OUNCES_TROY(0x0e),
        SLUGS(0x0f);
    
        companion object {
            private val map = values().associateBy(Unit::id)
            fun fromInt(type: Byte) = map[type] ?: throw CommandParseException()
        }
    }
}