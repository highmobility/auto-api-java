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

class Frequency : MeasurementType {
    val value: Double
    val unit: Unit

    constructor(value: Double, unit: Unit) : super() {
        this.value = value
        this.unit = unit
    
        bytes[0] = 0x0e
        bytes[1] = unit.id
        set(2, Property.doubleToBytes(value))
    }

    constructor(valueBytes: Bytes) : super(valueBytes, 0x0e) {
        unit = Unit.fromInt(valueBytes[1])
        value = Property.getDouble(valueBytes, 2)
    }

    fun inHertz(): Double {
        return when (unit) {
            Unit.HERTZ -> value * 1.0
            Unit.MILLIHERTZ -> value * 0.001
            Unit.KILOHERTZ -> value * 1.0e+3
            Unit.MEGAHERTZ -> value * 1.0e+6
            Unit.GIGAHERTZ -> value * 1.0e+9
            Unit.BEATS_PER_MINUTE -> value * 60.0
        }
    }
    
    fun inMillihertz() = inHertz() / 0.001
    
    fun inKilohertz() = inHertz() / 1.0e+3
    
    fun inMegahertz() = inHertz() / 1.0e+6
    
    fun inGigahertz() = inHertz() / 1.0e+9
    
    fun inBeatsPerMinute() = inHertz() / 60.0
    
    enum class Unit(val id: Byte) {
        HERTZ(0x00),
        MILLIHERTZ(0x01),
        KILOHERTZ(0x03),
        MEGAHERTZ(0x04),
        GIGAHERTZ(0x05),
        BEATS_PER_MINUTE(0x08);
    
        companion object {
            private val map = values().associateBy(Unit::id)
            fun fromInt(type: Byte) = map[type] ?: throw CommandParseException()
        }
    }
}