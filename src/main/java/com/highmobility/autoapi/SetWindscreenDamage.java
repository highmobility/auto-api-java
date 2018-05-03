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

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.WindscreenDamage;
import com.highmobility.autoapi.property.WindscreenDamageZone;
import com.highmobility.autoapi.property.WindscreenReplacementState;

import java.util.ArrayList;
import java.util.List;

/**
 * Set the windscreen damage. This is for instance used to reset the glass damage or correct it. The
 * result is sent through the Windscreen State command. Damage confidence percentage is
 * automatically set to either 0% or 100%.
 */
public class SetWindscreenDamage extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDSCREEN, 0x02);

    private WindscreenDamage damage;
    private WindscreenDamageZone zone;
    private WindscreenReplacementState replacementState;

    /**
     * @return The windscreen damage.
     */
    public WindscreenDamage getDamage() {
        return damage;
    }

    /**
     * @return The windscreen damage zone.
     */
    public WindscreenDamageZone getZone() {
        return zone;
    }

    /**
     * @return The windscreen replacement state.
     */
    public WindscreenReplacementState getReplacementState() {
        return replacementState;
    }

    /**
     * @param damage           The damage size
     * @param zone             The damage zone
     * @param replacementState The replacement state
     */
    public SetWindscreenDamage(WindscreenDamage damage, WindscreenDamageZone zone,
                               WindscreenReplacementState replacementState) {
        super(TYPE, getProperties(damage, zone, replacementState));
        this.damage = damage;
        this.zone = zone;
        this.replacementState = replacementState;
    }

    SetWindscreenDamage(byte[] bytes) throws CommandParseException {
        super(bytes);
        for (Property property : properties) {
            switch (property.getPropertyIdentifier()) {
                case WindscreenDamage.IDENTIFIER:
                    damage = WindscreenDamage.fromByte(property.getValueByte());
                    break;
                case WindscreenDamageZone.IDENTIFIER:
                    zone = new WindscreenDamageZone(property.getValueByte());
                    break;
                case WindscreenReplacementState.IDENTIFIER:
                    replacementState = WindscreenReplacementState.fromByte(property.getValueByte());
                    break;
            }
        }
    }

    static HMProperty[] getProperties(WindscreenDamage damage, WindscreenDamageZone zone,
                                      WindscreenReplacementState replacementState) {

        List<HMProperty> propertiesBuilder = new ArrayList<>();

        if (damage != null) propertiesBuilder.add(damage);
        if (zone != null) propertiesBuilder.add(zone);
        if (replacementState != null) propertiesBuilder.add(replacementState);

        return propertiesBuilder.toArray(new HMProperty[propertiesBuilder.size()]);
    }
}
