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
import com.highmobility.autoapi.value.homecharger.PriceTariff;

import java.util.ArrayList;
import java.util.List;

/**
 * Set the price tariffs of the home charger.
 */
public class SetPriceTariffs extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HOME_CHARGER, 0x13);
    private static final byte IDENTIFIER_TARIFF = 0x0C;

    private Property<PriceTariff>[] priceTariffs;

    /**
     * @return All of the price tariffs.
     */
    public Property<PriceTariff>[] getPriceTariffs() {
        return priceTariffs;
    }

    /**
     * Get the price tariff for a pricing type.
     *
     * @param type The pricing type.
     * @return The price tariff, if exists.
     */
    public Property<PriceTariff> getPriceTariff(PriceTariff.PricingType type) {
        for (Property<PriceTariff> priceTariff : priceTariffs) {
            if (priceTariff.getValue() != null && priceTariff.getValue().getPricingType() == type)
                return priceTariff;
        }
        return null;
    }

    /**
     * @param priceTariffs The price tariffs of the different pricing types.
     */
    public SetPriceTariffs(Property<PriceTariff>[] priceTariffs) throws IllegalArgumentException {
        super(TYPE, validateTariffs(priceTariffs));
        this.priceTariffs = priceTariffs;
    }

    static Property<PriceTariff>[] validateTariffs(Property<PriceTariff>[] tariffs) throws IllegalArgumentException {
        ArrayList<PriceTariff.PricingType> types = new ArrayList<>(3);

        for (Property<PriceTariff> tariff : tariffs) {
            tariff.setIdentifier(IDENTIFIER_TARIFF);
            if (tariff.getValue() != null && types.contains(tariff.getValue().getPricingType()) == false)
                types.add(tariff.getValue().getPricingType());
            else throw new IllegalArgumentException("Duplicate pricing type of the pricing tariff");
        }

        return tariffs;
    }

    SetPriceTariffs(byte[] bytes) {
        super(bytes);
        List<Property> builder = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_TARIFF:
                        Property tariff = new Property(PriceTariff.class, p);
                        builder.add(tariff);
                        return tariff;
                }

                return null;
            });
        }

        priceTariffs = builder.toArray(new Property[0]);
    }

    @Override protected boolean propertiesExpected() {
        return false;
    }
}
