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

import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;

/**
 * Command sent when a Get Maintenance State message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class MaintenanceState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.MAINTENANCE, 0x01);

    private static final byte DAYS_IDENTIFIER = 0x01;
    private static final byte KILOMETERS_IDENTIFIER = 0x02;

    private Integer kilometersToNextService;
    private Integer daysToNextService;

    /**
     * @return The amount of kilometers until next servicing of the car
     */
    public Integer getKilometersToNextService() {
        return kilometersToNextService;
    }

    /**
     * @return The number of days until next servicing of the car, whereas negative is overdue
     */
    public Integer getDaysToNextService() {
        return daysToNextService;
    }

    public MaintenanceState(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case DAYS_IDENTIFIER:
                    daysToNextService = Property.getUnsignedInt(property.getValueBytes());
                    break;
                case KILOMETERS_IDENTIFIER:
                    kilometersToNextService = Property.getUnsignedInt(property.getValueBytes());
                    break;
            }
        }
    }

    private MaintenanceState(Builder builder) {
        super(builder);
        kilometersToNextService = builder.kilometersToNextService;
        daysToNextService = builder.daysToNextService;
    }

    @Override public boolean isState() {
        return true;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Integer kilometersToNextService;
        private Integer daysToNextService;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param kilometersToNextService The amount of kilometers until next servicing of the car.
         * @return The builder.
         */
        public Builder setKilometersToNextService(Integer kilometersToNextService) {
            this.kilometersToNextService = kilometersToNextService;
            addProperty(new IntegerProperty(KILOMETERS_IDENTIFIER, kilometersToNextService, 3));
            return this;
        }

        /**
         * @param daysToNextService The number of days until next servicing of the car, whereas
         *                          negative is overdue.
         * @return The builder.
         */
        public Builder setDaysToNextService(Integer daysToNextService) {
            this.daysToNextService = daysToNextService;
            addProperty(new IntegerProperty(DAYS_IDENTIFIER, daysToNextService, 2));
            return this;
        }

        public MaintenanceState build() {
            return new MaintenanceState(this);
        }
    }
}