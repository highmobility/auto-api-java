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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.value.DriverWorkingState;
import com.highmobility.autoapi.value.DriverTimeState;
import com.highmobility.autoapi.value.DriverCardPresent;
import com.highmobility.autoapi.value.Detected;
import com.highmobility.autoapi.property.PropertyInteger;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * The tachograph state
 */
public class TachographState extends SetCommand {
    public static final Identifier identifier = Identifier.TACHOGRAPH;

    Property<DriverWorkingState>[] driversWorkingStates;
    Property<DriverTimeState>[] driversTimeStates;
    Property<DriverCardPresent>[] driversCardsPresent;
    Property<Detected> vehicleMotion = new Property(Detected.class, 0x04);
    Property<VehicleOverspeed> vehicleOverspeed = new Property(VehicleOverspeed.class, 0x05);
    Property<VehicleDirection> vehicleDirection = new Property(VehicleDirection.class, 0x06);
    PropertyInteger vehicleSpeed = new PropertyInteger(0x07, true);

    /**
     * @return The drivers working states
     */
    public Property<DriverWorkingState>[] getDriversWorkingStates() {
        return driversWorkingStates;
    }

    /**
     * @return The drivers time states
     */
    public Property<DriverTimeState>[] getDriversTimeStates() {
        return driversTimeStates;
    }

    /**
     * @return The drivers cards present
     */
    public Property<DriverCardPresent>[] getDriversCardsPresent() {
        return driversCardsPresent;
    }

    /**
     * @return The vehicle motion
     */
    public Property<Detected> getVehicleMotion() {
        return vehicleMotion;
    }

    /**
     * @return The vehicle overspeed
     */
    public Property<VehicleOverspeed> getVehicleOverspeed() {
        return vehicleOverspeed;
    }

    /**
     * @return The vehicle direction
     */
    public Property<VehicleDirection> getVehicleDirection() {
        return vehicleDirection;
    }

    /**
     * @return The tachograph vehicle speed in km/h
     */
    public PropertyInteger getVehicleSpeed() {
        return vehicleSpeed;
    }

    /**
     * Get the driver working state for a specific driver.
     *
     * @param driverNumber The driver number.
     * @return The driver working state.
     */
    @Nullable public Property<DriverWorkingState> getDriverWorkingState(int driverNumber) {
        for (int i = 0; i < driversWorkingStates.length; i++) {
            Property<DriverWorkingState> state = driversWorkingStates[i];
            if (state.getValue() != null && state.getValue().getDriverNumber() == driverNumber)
                return state;
        }
        return null;
    }

    /**
     * Get the driver time state for a specific driver.
     *
     * @param driverNumber The driver number.
     * @return The driver time state.
     */
    @Nullable public Property<DriverTimeState> getDriverTimeState(int driverNumber) {
        for (int i = 0; i < driversTimeStates.length; i++) {
            Property<DriverTimeState> state = driversTimeStates[i];
            if (state.getValue() != null && state.getValue().getDriverNumber() == driverNumber)
                return state;
        }
        return null;
    }

    /**
     * Get the driver card for a specific driver.
     *
     * @param driverNumber The driver number.
     * @return The driver card.
     */
    @Nullable public Property<DriverCardPresent> getDriverCard(int driverNumber) {
        for (int i = 0; i < driversCardsPresent.length; i++) {
            Property<DriverCardPresent> state = driversCardsPresent[i];
            if (state.getValue() != null && state.getValue().getDriverNumber() == driverNumber)
                return state;
        }
        return null;
    }

    TachographState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> driversWorkingStatesBuilder = new ArrayList<>();
        ArrayList<Property> driversTimeStatesBuilder = new ArrayList<>();
        ArrayList<Property> driversCardsPresentBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01:
                        Property<DriverWorkingState> driversWorkingState = new Property(DriverWorkingState.class, p);
                        driversWorkingStatesBuilder.add(driversWorkingState);
                        return driversWorkingState;
                    case 0x02:
                        Property<DriverTimeState> driversTimeState = new Property(DriverTimeState.class, p);
                        driversTimeStatesBuilder.add(driversTimeState);
                        return driversTimeState;
                    case 0x03:
                        Property<DriverCardPresent> driversCardsPresent = new Property(DriverCardPresent.class, p);
                        driversCardsPresentBuilder.add(driversCardsPresent);
                        return driversCardsPresent;
                    case 0x04: return vehicleMotion.update(p);
                    case 0x05: return vehicleOverspeed.update(p);
                    case 0x06: return vehicleDirection.update(p);
                    case 0x07: return vehicleSpeed.update(p);
                }

                return null;
            });
        }

        driversWorkingStates = driversWorkingStatesBuilder.toArray(new Property[0]);
        driversTimeStates = driversTimeStatesBuilder.toArray(new Property[0]);
        driversCardsPresent = driversCardsPresentBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private TachographState(Builder builder) {
        super(builder);

        driversWorkingStates = builder.driversWorkingStates.toArray(new Property[0]);
        driversTimeStates = builder.driversTimeStates.toArray(new Property[0]);
        driversCardsPresent = builder.driversCardsPresent.toArray(new Property[0]);
        vehicleMotion = builder.vehicleMotion;
        vehicleOverspeed = builder.vehicleOverspeed;
        vehicleDirection = builder.vehicleDirection;
        vehicleSpeed = builder.vehicleSpeed;
    }

    public static final class Builder extends SetCommand.Builder {
        private List<Property> driversWorkingStates = new ArrayList<>();
        private List<Property> driversTimeStates = new ArrayList<>();
        private List<Property> driversCardsPresent = new ArrayList<>();
        private Property<Detected> vehicleMotion;
        private Property<VehicleOverspeed> vehicleOverspeed;
        private Property<VehicleDirection> vehicleDirection;
        private PropertyInteger vehicleSpeed;

        public Builder() {
            super(identifier);
        }

        public TachographState build() {
            return new TachographState(this);
        }

        /**
         * Add an array of drivers working states.
         * 
         * @param driversWorkingStates The drivers working states
         * @return The builder
         */
        public Builder setDriversWorkingStates(Property<DriverWorkingState>[] driversWorkingStates) {
            this.driversWorkingStates.clear();
            for (int i = 0; i < driversWorkingStates.length; i++) {
                addDriversWorkingState(driversWorkingStates[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single drivers working state.
         * 
         * @param driversWorkingState The drivers working state
         * @return The builder
         */
        public Builder addDriversWorkingState(Property<DriverWorkingState> driversWorkingState) {
            driversWorkingState.setIdentifier(0x01);
            addProperty(driversWorkingState);
            driversWorkingStates.add(driversWorkingState);
            return this;
        }
        
        /**
         * Add an array of drivers time states.
         * 
         * @param driversTimeStates The drivers time states
         * @return The builder
         */
        public Builder setDriversTimeStates(Property<DriverTimeState>[] driversTimeStates) {
            this.driversTimeStates.clear();
            for (int i = 0; i < driversTimeStates.length; i++) {
                addDriversTimeState(driversTimeStates[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single drivers time state.
         * 
         * @param driversTimeState The drivers time state
         * @return The builder
         */
        public Builder addDriversTimeState(Property<DriverTimeState> driversTimeState) {
            driversTimeState.setIdentifier(0x02);
            addProperty(driversTimeState);
            driversTimeStates.add(driversTimeState);
            return this;
        }
        
        /**
         * Add an array of drivers cards present.
         * 
         * @param driversCardsPresent The drivers cards present
         * @return The builder
         */
        public Builder setDriversCardsPresent(Property<DriverCardPresent>[] driversCardsPresent) {
            this.driversCardsPresent.clear();
            for (int i = 0; i < driversCardsPresent.length; i++) {
                addDriversCardsPresen(driversCardsPresent[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single drivers cards presen.
         * 
         * @param driversCardsPresen The drivers cards presen
         * @return The builder
         */
        public Builder addDriversCardsPresen(Property<DriverCardPresent> driversCardsPresen) {
            driversCardsPresen.setIdentifier(0x03);
            addProperty(driversCardsPresen);
            driversCardsPresent.add(driversCardsPresen);
            return this;
        }
        
        /**
         * @param vehicleMotion The vehicle motion
         * @return The builder
         */
        public Builder setVehicleMotion(Property<Detected> vehicleMotion) {
            this.vehicleMotion = vehicleMotion.setIdentifier(0x04);
            addProperty(this.vehicleMotion);
            return this;
        }
        
        /**
         * @param vehicleOverspeed The vehicle overspeed
         * @return The builder
         */
        public Builder setVehicleOverspeed(Property<VehicleOverspeed> vehicleOverspeed) {
            this.vehicleOverspeed = vehicleOverspeed.setIdentifier(0x05);
            addProperty(this.vehicleOverspeed);
            return this;
        }
        
        /**
         * @param vehicleDirection The vehicle direction
         * @return The builder
         */
        public Builder setVehicleDirection(Property<VehicleDirection> vehicleDirection) {
            this.vehicleDirection = vehicleDirection.setIdentifier(0x06);
            addProperty(this.vehicleDirection);
            return this;
        }
        
        /**
         * @param vehicleSpeed The tachograph vehicle speed in km/h
         * @return The builder
         */
        public Builder setVehicleSpeed(Property<Integer> vehicleSpeed) {
            this.vehicleSpeed = new PropertyInteger(0x07, true, 2, vehicleSpeed);
            addProperty(this.vehicleSpeed);
            return this;
        }
    }

    public enum VehicleOverspeed implements ByteEnum {
        NO_OVERSPEED((byte) 0x00),
        OVERSPEED((byte) 0x01);
    
        public static VehicleOverspeed fromByte(byte byteValue) throws CommandParseException {
            VehicleOverspeed[] values = VehicleOverspeed.values();
    
            for (int i = 0; i < values.length; i++) {
                VehicleOverspeed state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        VehicleOverspeed(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum VehicleDirection implements ByteEnum {
        FORWARD((byte) 0x00),
        REVERSE((byte) 0x01);
    
        public static VehicleDirection fromByte(byte byteValue) throws CommandParseException {
            VehicleDirection[] values = VehicleDirection.values();
    
            for (int i = 0; i < values.length; i++) {
                VehicleDirection state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        VehicleDirection(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}