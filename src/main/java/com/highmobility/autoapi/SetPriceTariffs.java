/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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
import com.highmobility.autoapi.value.PriceTariff;
import java.util.ArrayList;

/**
 * Set price tariffs
 */
public class SetPriceTariffs extends SetCommand {
    public static final Identifier identifier = Identifier.HOME_CHARGER;

    Property<PriceTariff>[] priceTariffs;

    /**
     * @return The price tariffs
     */
    public Property<PriceTariff>[] getPriceTariffs() {
        return priceTariffs;
    }
    
    /**
     * Set price tariffs
     *
     * @param priceTariffs The price tariffs
     */
    public SetPriceTariffs(PriceTariff[] priceTariffs) {
        super(identifier);
    
        ArrayList<Property> priceTariffsBuilder = new ArrayList<>();
        if (priceTariffs != null) {
            for (PriceTariff priceTariff : priceTariffs) {
                Property prop = new Property(0x12, priceTariff);
                priceTariffsBuilder.add(prop);
                addProperty(prop, true);
            }
        }
        this.priceTariffs = priceTariffsBuilder.toArray(new Property[0]);
    }

    SetPriceTariffs(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<PriceTariff>> priceTariffsBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x12: {
                        Property priceTariff = new Property(PriceTariff.class, p);
                        priceTariffsBuilder.add(priceTariff);
                        return priceTariff;
                    }
                }
                return null;
            });
        }
    
        priceTariffs = priceTariffsBuilder.toArray(new Property[0]);
        if (this.priceTariffs.length == 0) 
            throw new NoPropertiesException();
    }
}