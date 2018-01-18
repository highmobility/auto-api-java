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

package com.highmobility.autoapi.incoming;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.utils.Bytes;

import java.util.Arrays;

/**
 * Created by ttiganik on 13/09/16.
 *
 * This is an evented command that is sent from the car every time a new parcel is delivered or
 * removed. This command is also sent when a Get Delivered Parcels is received by the car.
 */
public class DeliveredParcels extends IncomingCommand {
    static final String TAG = "DeliveredParcels";
    String[] deliveredParcels;

    public DeliveredParcels(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length < 4) throw new CommandParseException();

        int count = bytes[3];

        if (count < 1) return;

        if (bytes.length < 3 + count * 8) throw new CommandParseException();

        deliveredParcels = new String[count];
        for (int i = 0; i < count; i++) {
            byte[] identifier = Arrays.copyOfRange(bytes, 4 + i * 8, 4 + i * 8 + 8);
            deliveredParcels[i] = Bytes.hexFromBytes(identifier);
        }
    }

    /**
     *
     * @return Array of tracking numbers of the parcels.
     */
    public String[] getDeliveredParcels() {
        return deliveredParcels;
    }
}
