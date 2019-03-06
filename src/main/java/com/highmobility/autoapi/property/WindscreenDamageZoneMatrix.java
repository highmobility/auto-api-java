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

package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.value.Bytes;

/**
 * Created by root on 6/29/17.
 */

public class WindscreenDamageZoneMatrix extends PropertyValueObject {
    int windscreenSizeHorizontal;
    int windscreenSizeVertical;

    public int getWindscreenSizeHorizontal() {
        return windscreenSizeHorizontal;
    }

    public int getWindscreenSizeVertical() {
        return windscreenSizeVertical;
    }

    public WindscreenDamageZoneMatrix(int windscreenSizeHorizontal, int windscreenSizeVertical) {
        super(1);
        update(windscreenSizeHorizontal, windscreenSizeVertical);
    }

    public WindscreenDamageZoneMatrix() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 1) throw new CommandParseException();

        windscreenSizeHorizontal = get(0) >> 4;
        windscreenSizeVertical = get(0) & 0x0F;
    }

    public void update(int windscreenSizeHorizontal, int windscreenSizeVertical) {
        this.windscreenSizeHorizontal = windscreenSizeHorizontal;
        this.windscreenSizeVertical = windscreenSizeVertical;
        bytes = new byte[1];
        set(0, (byte) (((windscreenSizeHorizontal & 0x0F) << 4) | (windscreenSizeVertical & 0x0F)));

    }

    public void update(WindscreenDamageZoneMatrix value) {
        update(value.windscreenSizeHorizontal, value.windscreenSizeVertical);
    }
}
