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
import com.highmobility.autoapi.value.windscreen.WindscreenDamage;
import com.highmobility.autoapi.value.windscreen.WindscreenDamageZone;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Set the windscreen damage. This is for instance used to reset the glass damage or correct it. The
 * result is sent through the Windscreen State command. Damage confidence percentage is
 * automatically set to either 0% or 100%.
 */
public class SetWindscreenDamage extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDSCREEN, 0x12);

    private static final byte IDENTIFIER_WINDSCREEN_DAMAGE = 0x03;
    private static final byte IDENTIFIER_WINDSCREEN_DAMAGE_ZONE = 0x05;

    private Property<WindscreenDamage> damage = new Property(WindscreenDamage.class,
            IDENTIFIER_WINDSCREEN_DAMAGE);
    private Property<WindscreenDamageZone> zone = new Property(WindscreenDamageZone.class,
            IDENTIFIER_WINDSCREEN_DAMAGE_ZONE);

    /**
     * @return The windscreen damage.
     */
    public Property<WindscreenDamage> getDamage() {
        return damage;
    }

    /**
     * @return The windscreen damage zone.
     */
    public Property<WindscreenDamageZone> getZone() {
        return zone;
    }

    /**
     * @param damage The damage size
     * @param zone   The damage zone
     */
    public SetWindscreenDamage(WindscreenDamage damage, @Nullable WindscreenDamageZone zone) {
        super(TYPE);

        ArrayList<Property> builder = new ArrayList<>();

        this.damage.update(damage);
        builder.add(this.damage);

        if (zone != null) {
            this.zone.update(zone);
            builder.add(this.zone);
        }

        createBytes(builder);
    }

    SetWindscreenDamage(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_WINDSCREEN_DAMAGE:
                        return damage.update(p);
                    case IDENTIFIER_WINDSCREEN_DAMAGE_ZONE:
                        return zone.update(p);
                }
                return null;
            });
        }
    }
}
