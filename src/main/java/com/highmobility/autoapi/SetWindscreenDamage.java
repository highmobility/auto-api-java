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

import com.highmobility.autoapi.WindscreenState.WindscreenDamage;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Zone;
import javax.annotation.Nullable;

/**
 * Set windscreen damage
 */
public class SetWindscreenDamage extends SetCommand {
    public static final Identifier identifier = Identifier.WINDSCREEN;

    Property<WindscreenDamage> windscreenDamage = new Property(WindscreenDamage.class, 0x03);
    @Nullable Property<Zone> windscreenDamageZone = new Property(Zone.class, 0x05);

    /**
     * @return The windscreen damage
     */
    public Property<WindscreenDamage> getWindscreenDamage() {
        return windscreenDamage;
    }
    
    /**
     * @return The windscreen damage zone
     */
    public @Nullable Property<Zone> getWindscreenDamageZone() {
        return windscreenDamageZone;
    }
    
    /**
     * Set windscreen damage
     *
     * @param windscreenDamage The windscreen damage
     * @param windscreenDamageZone Representing the position in the zone, seen from the inside of the vehicle (1-based index)
     */
    public SetWindscreenDamage(WindscreenDamage windscreenDamage, @Nullable Zone windscreenDamageZone) {
        super(identifier);
    
        addProperty(this.windscreenDamage.update(windscreenDamage));
        addProperty(this.windscreenDamageZone.update(windscreenDamageZone), true);
    }

    SetWindscreenDamage(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x03: return windscreenDamage.update(p);
                    case 0x05: return windscreenDamageZone.update(p);
                }
                return null;
            });
        }
        if (this.windscreenDamage.getValue() == null) 
            throw new NoPropertiesException();
    }
}