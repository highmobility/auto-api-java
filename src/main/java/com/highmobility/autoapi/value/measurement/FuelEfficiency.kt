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

class FuelEfficiency : MeasurementType {
    val value: Double
    val unit: Unit

    constructor(value: Double, unit: Unit) : super() {
        this.value = value
        this.unit = unit
    
        bytes[0] = getMeasurementId()
        bytes[1] = unit.id
        set(2, Property.doubleToBytes(value))
    }

    constructor(valueBytes: Bytes) : super(valueBytes) {
        unit = Unit.fromInt(valueBytes[1])
        value = Property.getDouble(valueBytes, 2)
    }

    fun inLitersPer100Kilometers(): Double {
        return when (unit) {
            Unit.LITERS_PER_100_KILOMETERS -> value
            Unit.MILES_PER_IMPERIAL_GALLON -> 282.4809363 / value
            Unit.MILES_PER_GALLON -> 235.2145833 / value
        }
    }
    
    fun inMilesPerImperialGallon() = 282.4809363 / inLitersPer100Kilometers()
    
    fun inMilesPerGallon() = 235.2145833 / inLitersPer100Kilometers()
    
    override fun getMeasurementId(): Byte {
        return 0x0f
    }    
    
    enum class Unit(val id: Byte) {
        LITERS_PER_100_KILOMETERS(0x00),
        MILES_PER_IMPERIAL_GALLON(0x01),
        MILES_PER_GALLON(0x02);
    
        companion object {
            private val map = values().associateBy(Unit::id)
            fun fromInt(type: Byte) = map[type] ?: throw CommandParseException()
        }
    }
}