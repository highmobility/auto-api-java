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
package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class ChargingCost extends PropertyValueObject {
    String currency;
    Float calculatedChargingCost;
    Float calculatedSavings;
    Float simulatedImmediateChargingCost;

    /**
     * @return Currency ISO code.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return Calculated charging cost.
     */
    public Float getCalculatedChargingCost() {
        return calculatedChargingCost;
    }

    /**
     * @return Calculated savings from charging.
     */
    public Float getCalculatedSavings() {
        return calculatedSavings;
    }

    /**
     * @return Simulated charging costs.
     */
    public Float getSimulatedImmediateChargingCost() {
        return simulatedImmediateChargingCost;
    }

    public ChargingCost(String currency, Float calculatedChargingCost, Float calculatedSavings, Float simulatedImmediateChargingCost) {
        super(0);

        this.currency = currency;
        this.calculatedChargingCost = calculatedChargingCost;
        this.calculatedSavings = calculatedSavings;
        this.simulatedImmediateChargingCost = simulatedImmediateChargingCost;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(Property.getUtf8Length(currency), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(currency));
        bytePosition += Property.getUtf8Length(currency);

        set(bytePosition, Property.floatToBytes(calculatedChargingCost));
        bytePosition += 4;

        set(bytePosition, Property.floatToBytes(calculatedSavings));
        bytePosition += 4;

        set(bytePosition, Property.floatToBytes(simulatedImmediateChargingCost));
    }

    public ChargingCost(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 14) throw new CommandParseException();

        int bytePosition = 0;
        int currencySize = getItemSize(bytePosition);
        bytePosition += 2;
        currency = Property.getString(bytes, bytePosition, currencySize);
        bytePosition += currencySize;

        calculatedChargingCost = Property.getFloat(bytes, bytePosition);
        bytePosition += 4;

        calculatedSavings = Property.getFloat(bytes, bytePosition);
        bytePosition += 4;

        simulatedImmediateChargingCost = Property.getFloat(bytes, bytePosition);
    }

    @Override public int getLength() {
        return Property.getUtf8Length(currency) + 2 + 4 + 4 + 4;
    }
}