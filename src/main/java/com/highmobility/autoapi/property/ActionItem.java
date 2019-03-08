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

public class ActionItem extends PropertyValueObject {
    int actionIdentifier;
    String name;

    /**
     * @return The action item identifier
     */
    public int getActionIdentifier() {
        return actionIdentifier;
    }

    /**
     * @return Name of the action item
     */
    public String getName() {
        return name;
    }

    public ActionItem(int actionIdentifier, String name) {
        super(1 + name.length());
        update(actionIdentifier, name);
    }

    public ActionItem() {
        super();
    }

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length == 0) throw new CommandParseException();

        actionIdentifier = bytes[0];
        if (bytes.length > 1) name = Property.getString(getRange(1, bytes.length));
    }

    public void update(int actionIdentifier, String name) {
        this.actionIdentifier = actionIdentifier;
        this.name = name;
        bytes = new byte[1 + name.length()];

        set(0, Property.intToBytes(actionIdentifier, 1));
        set(1, Property.stringToBytes(name));
    }

    public void update(ActionItem value) {
        update(value.actionIdentifier, value.name);
    }
}
