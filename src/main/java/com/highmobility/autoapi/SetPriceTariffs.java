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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.homecharger.PriceTariff;

import java.util.ArrayList;
import java.util.List;

/**
 * Set the price tariffs of the home charger.
 */
public class SetPriceTariffs extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HOME_CHARGER, 0x13);
    private static final byte IDENTIFIER_TARIFF = 0x0C;

    private PriceTariff[] priceTariffs;

    /**
     * @return All of the price tariffs.
     */
    public PriceTariff[] getPriceTariffs() {
        return priceTariffs;
    }

    /**
     * Get the price tariff for a pricing type.
     *
     * @param type The pricing type.
     * @return The price tariff, if exists.
     */
    public PriceTariff getPriceTariff(PriceTariff.PricingType type) {
        for (PriceTariff priceTariff : priceTariffs) {
            if (priceTariff.getPricingType() == type) return priceTariff;
        }
        return null;
    }

    /**
     * @param priceTariffs The price tariffs of the different pricing types.
     */
    public SetPriceTariffs(PriceTariff[] priceTariffs) throws IllegalArgumentException {
        super(TYPE, validateTariffs(priceTariffs));
        this.priceTariffs = priceTariffs;
    }

    static PriceTariff[] validateTariffs(PriceTariff[] tariffs) throws IllegalArgumentException {
        ArrayList<PriceTariff.PricingType> types = new ArrayList<>(3);
        for (PriceTariff tariff : tariffs) {
            tariff.setIdentifier(IDENTIFIER_TARIFF);
            if (types.contains(tariff.getPricingType()) == false)
                types.add(tariff.getPricingType());
            else throw new IllegalArgumentException("Duplicate pricing type of the pricing tariff");
        }

        return tariffs;
    }

    SetPriceTariffs(byte[] bytes) throws CommandParseException {
        super(bytes);
        List<PriceTariff> builder = new ArrayList<>();

        for (Property property : properties) {
            if (property.getPropertyIdentifier() == IDENTIFIER_TARIFF) {
                builder.add(new PriceTariff(property.getPropertyBytes()));
            }
        }

        priceTariffs = builder.toArray(new PriceTariff[builder.size()]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }
}
