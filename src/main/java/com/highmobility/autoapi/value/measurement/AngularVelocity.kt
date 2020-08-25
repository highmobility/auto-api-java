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

class AngularVelocity : MeasurementType {
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

    fun inRevolutionsPerMinute(): Double {
        return when (unit) {
            Unit.REVOLUTIONS_PER_MINUTE -> value * 1.0
            Unit.DEGREES_PER_SECOND -> value * 6.0
            Unit.RADIANS_PER_SECOND -> value * 9.549296585514
        }
    }
    
    fun inDegreesPerSecond() = inRevolutionsPerMinute() / 6.0
    
    fun inRadiansPerSecond() = inRevolutionsPerMinute() / 9.549296585514
    
    override fun getMeasurementId(): Byte {
        return 0x03
    }    
    
    enum class Unit(val id: Byte) {
        REVOLUTIONS_PER_MINUTE(0x00),
        DEGREES_PER_SECOND(0x01),
        RADIANS_PER_SECOND(0x02);
    
        companion object {
            private val map = values().associateBy(Unit::id)
            fun fromInt(type: Byte) = map[type] ?: throw CommandParseException()
        }
    }
}