package com.highmobility.autoapi.value.measurement

import com.highmobility.autoapi.CommandParseException
import com.highmobility.autoapi.property.PropertyValueObject
import com.highmobility.value.Bytes

open class MeasurementType : PropertyValueObject {
    constructor() : super(SIZE)

    constructor(valueBytes: Bytes) : super(valueBytes) {
        if (valueBytes.length != length ||
            valueBytes[0] != getMeasurementId()
        ) throw CommandParseException("Measurement type bytes length should be $SIZE")
    }

    protected open fun getMeasurementId(): Byte {
        return 0xAA.toByte()
    }

    override fun getLength(): Int {
        return SIZE
    }

    companion object {
        const val SIZE = 10
    }
}
