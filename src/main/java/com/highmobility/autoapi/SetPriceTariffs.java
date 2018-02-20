/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.HomeCharger.PriceTariff;

import java.util.ArrayList;

/**
 * Set the price tariffs of the home charger.
 */
public class SetPriceTariffs extends CommandWithExistingProperties {
    public static final Type TYPE = new Type(Identifier.HOME_CHARGER, 0x03);

    /**
     *
     * @param priceTariffs The price tariffs of the different pricing types.
     */
    public SetPriceTariffs(PriceTariff[] priceTariffs) throws IllegalArgumentException {
        super(TYPE, validateTariffs(priceTariffs));
    }

    static PriceTariff[] validateTariffs(PriceTariff[] tariffs) throws IllegalArgumentException {
        ArrayList<PriceTariff.PricingType> types = new ArrayList<>(3);
        for (PriceTariff tariff : tariffs) {
            if (types.contains(tariff.getPricingType()) == false) types.add(tariff.getPricingType());
            else throw new IllegalArgumentException("Duplicate pricing type of the pricing tariff");
        }

        return tariffs;
    }

    SetPriceTariffs(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
