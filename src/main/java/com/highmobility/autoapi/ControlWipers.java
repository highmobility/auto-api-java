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

import com.highmobility.autoapi.WindscreenState.Wipers;
import com.highmobility.autoapi.WindscreenState.WipersIntensity;
import com.highmobility.autoapi.property.Property;
import javax.annotation.Nullable;

/**
 * Control wipers
 */
public class ControlWipers extends SetCommand {
    public static final Identifier identifier = Identifier.WINDSCREEN;

    Property<Wipers> wipers = new Property(Wipers.class, 0x01);
    @Nullable Property<WipersIntensity> wipersIntensity = new Property(WipersIntensity.class, 0x02);

    /**
     * @return The wipers
     */
    public Property<Wipers> getWipers() {
        return wipers;
    }
    
    /**
     * @return The wipers intensity
     */
    public @Nullable Property<WipersIntensity> getWipersIntensity() {
        return wipersIntensity;
    }
    
    /**
     * Control wipers
     *
     * @param wipers The wipers
     * @param wipersIntensity The wipers intensity
     */
    public ControlWipers(Wipers wipers, @Nullable WipersIntensity wipersIntensity) {
        super(identifier);
    
        addProperty(this.wipers.update(wipers));
        addProperty(this.wipersIntensity.update(wipersIntensity), true);
    }

    ControlWipers(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return wipers.update(p);
                    case 0x02: return wipersIntensity.update(p);
                }
                return null;
            });
        }
        if (this.wipers.getValue() == null) 
            throw new NoPropertiesException();
    }
}