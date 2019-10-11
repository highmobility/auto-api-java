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
import com.highmobility.autoapi.value.ActiveState;

/**
 * Activate deactivate solar charging
 */
public class ActivateDeactivateSolarCharging extends SetCommand {
    public static final Identifier identifier = Identifier.HOME_CHARGER;

    Property<ActiveState> solarCharging = new Property(ActiveState.class, 0x05);

    /**
     * @return The solar charging
     */
    public Property<ActiveState> getSolarCharging() {
        return solarCharging;
    }
    
    /**
     * Activate deactivate solar charging
     *
     * @param solarCharging The solar charging
     */
    public ActivateDeactivateSolarCharging(ActiveState solarCharging) {
        super(identifier);
    
        addProperty(this.solarCharging.update(solarCharging), true);
    }

    ActivateDeactivateSolarCharging(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x05: return solarCharging.update(p);
                }
                return null;
            });
        }
        if (this.solarCharging.getValue() == null) 
            throw new NoPropertiesException();
    }
}