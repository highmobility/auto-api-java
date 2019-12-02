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

import com.highmobility.autoapi.ChassisSettingsState.SportChrono;
import com.highmobility.autoapi.property.Property;

/**
 * Start stop sports chrono
 */
public class StartStopSportsChrono extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.CHASSIS_SETTINGS;

    public static final byte IDENTIFIER_SPORT_CHRONO = 0x02;

    Property<SportChrono> sportChrono = new Property(SportChrono.class, IDENTIFIER_SPORT_CHRONO);

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
        super(IDENTIFIER);
    
        addProperty(this.sportChrono.update(sportChrono));
        createBytes();
    }

    StartStopSportsChrono(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_SPORT_CHRONO: return sportChrono.update(p);
                }
                return null;
            });
        }
        if (this.sportChrono.getValue() == null) 
            throw new NoPropertiesException();
    }
}