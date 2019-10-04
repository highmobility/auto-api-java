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

import com.highmobility.autoapi.WindscreenState.WindscreenNeedsReplacement;
import com.highmobility.autoapi.property.Property;

/**
 * Set windscreen replacement needed
 */
public class SetWindscreenReplacementNeeded extends SetCommand {
    public static final Identifier identifier = Identifier.WINDSCREEN;

    Property<WindscreenNeedsReplacement> windscreenNeedsReplacement = new Property(WindscreenNeedsReplacement.class, 0x06);

    /**
     * @return The windscreen needs replacement
     */
    public Property<WindscreenNeedsReplacement> getWindscreenNeedsReplacement() {
        return windscreenNeedsReplacement;
    }
    
    /**
     * Set windscreen replacement needed
     *
     * @param windscreenNeedsReplacement The windscreen needs replacement
     */
    public SetWindscreenReplacementNeeded(WindscreenNeedsReplacement windscreenNeedsReplacement) {
        super(identifier);
    
        addProperty(this.windscreenNeedsReplacement.update(windscreenNeedsReplacement), true);
    }

    SetWindscreenReplacementNeeded(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x06: return windscreenNeedsReplacement.update(p);
                }
                return null;
            });
        }
        if (this.windscreenNeedsReplacement.getValue() == null) 
            throw new NoPropertiesException();
    }
}