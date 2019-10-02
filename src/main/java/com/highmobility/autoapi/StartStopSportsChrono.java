// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.ChassisSettingsState.SportChrono;
import com.highmobility.autoapi.property.Property;

/**
 * Start stop sports chrono
 */
public class StartStopSportsChrono extends SetCommand {
    Property<SportChrono> sportChrono = new Property(SportChrono.class, 0x02);

    /**
     * @return The sport chrono
     */
    public Property<SportChrono> getSportChrono() {
        return sportChrono;
    }
    
    /**
     * Start stop sports chrono
     *
     * @param sportChrono The sport chrono
     */
    public StartStopSportsChrono(SportChrono sportChrono) {
        super(Identifier.CHASSIS_SETTINGS);
    
        addProperty(this.sportChrono.update(sportChrono), true);
    }

    StartStopSportsChrono(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x02: return sportChrono.update(p);
                }
                return null;
            });
        }
        if (this.sportChrono.getValue() == null) 
            throw new NoPropertiesException();
    }
}