// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.PriceTariff;
import java.util.ArrayList;

/**
 * Set price tariffs
 */
public class SetPriceTariffs extends SetCommand {
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
        super(Identifier.HOME_CHARGER);
    
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