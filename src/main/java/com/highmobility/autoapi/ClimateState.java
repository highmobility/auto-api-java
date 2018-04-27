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

import com.highmobility.autoapi.property.AutoHvacProperty;
import com.highmobility.autoapi.property.AutoHvacState;
import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.Bytes;

/**
 * Command sent when a Get Climate State command is received by the car. Also sent once the HVAC
 * system has been turned on/off, when the defrosting/defogging states change or when the profile is
 * updated.
 * <p>
 * Auto-HVAC (Heating, Ventilation and Air Conditioning) allows you to schedule times when HVAC is
 * triggered automatically to reach the desired driver temperature setting.
 */
public class ClimateState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x01);

    private static final byte INSIDE_TEMPERATURE_IDENTIFIER = 0x01;
    private static final byte OUTSIDE_TEMPERATURE_IDENTIFIER = 0x02;
    private static final byte DRIVER_TEMPERATURE_SETTING_IDENTIFIER = 0x03;
    private static final byte PASSENGER_TEMPERATURE_SETTING_IDENTIFIER = 0x04;
    private static final byte HVAC_ACTIVE_IDENTIFIER = 0x05;
    private static final byte DEFOGGING_ACTIVE_IDENTIFIER = 0x06;
    private static final byte DEFROSTING_ACTIVE_IDENTIFIER = 0x07;
    private static final byte IONISING_ACTIVE_IDENTIFIER = 0x08;
    private static final byte DEFROSTING_TEMPERATURE_IDENTIFIER = 0x09;
    private static final byte HVAC_PROFILE_IDENTIFIER = 0x0A;

    Float insideTemperature;
    Float outsideTemperature;
    Float driverTemperatureSetting;
    Float passengerTemperatureSetting;
    Boolean hvacActive;
    Boolean defoggingActive;
    Boolean defrostingActive;
    Boolean ionisingActive;
    Float defrostingTemperature;
    Boolean autoHvacConstant;
    AutoHvacState[] autoHvacStates;
    AutoHvacProperty autoHvacState;

    /**
     * @return The inside temperature.
     */
    public Float getInsideTemperature() {
        return insideTemperature;
    }

    /**
     * @return The outside temperature.
     */
    public Float getOutsideTemperature() {
        return outsideTemperature;
    }

    /**
     * @return The driver temperature setting.
     */
    public Float getDriverTemperatureSetting() {
        return driverTemperatureSetting;
    }

    /**
     * @return The passenger temperature setting.
     */
    public Float getPassengerTemperatureSetting() {
        return passengerTemperatureSetting;
    }

    /**
     * @return Whether HVAC is active or not.
     */
    public Boolean isHvacActive() {
        return hvacActive;
    }

    /**
     * @return Whether defogging is active or not.
     */
    public Boolean isDefoggingActive() {
        return defoggingActive;
    }

    /**
     * @return Whether defrosting is active or not.
     */
    public Boolean isDefrostingActive() {
        return defrostingActive;
    }

    /**
     * @return Whether ionising is active or not.
     */
    public Boolean isIonisingActive() {
        return ionisingActive;
    }

    /**
     * @return The defrosting temperature.
     */
    public Float getDefrostingTemperature() {
        return defrostingTemperature;
    }

    /**
     * @return Whether autoHVAC is constant(based on the car surroundings)
     * @deprecated use {@link #getAutoHvacState()} instead
     */
    @Deprecated
    public Boolean isAutoHvacConstant() {
        return autoHvacConstant;
    }

    /**
     * @return Array of State's that describe if and when the AutoHVAC is active.
     * @deprecated use {@link #getAutoHvacState()} instead
     */
    @Deprecated
    public AutoHvacState[] getAutoHvacStates() {
        return autoHvacStates;
    }

    /**
     * @return The Auto HVAC state.
     */
    public AutoHvacProperty getAutoHvacState() {
        return autoHvacState;
    }

    ClimateState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];

            switch (property.getPropertyIdentifier()) {
                case INSIDE_TEMPERATURE_IDENTIFIER:
                    insideTemperature = Property.getFloat(property.getValueBytes());
                    break;
                case OUTSIDE_TEMPERATURE_IDENTIFIER:
                    outsideTemperature = Property.getFloat(property.getValueBytes());
                    break;
                case DRIVER_TEMPERATURE_SETTING_IDENTIFIER:
                    driverTemperatureSetting = Property.getFloat(property.getValueBytes());
                    break;
                case PASSENGER_TEMPERATURE_SETTING_IDENTIFIER:
                    passengerTemperatureSetting = Property.getFloat(property.getValueBytes());
                    break;
                case HVAC_ACTIVE_IDENTIFIER:
                    hvacActive = Property.getBool(property.getValueByte());
                    break;
                case DEFOGGING_ACTIVE_IDENTIFIER:
                    defoggingActive = Property.getBool(property.getValueByte());
                    break;
                case DEFROSTING_ACTIVE_IDENTIFIER:
                    defrostingActive = Property.getBool(property.getValueByte());
                    break;
                case IONISING_ACTIVE_IDENTIFIER:
                    ionisingActive = Property.getBool(property.getValueByte());
                    break;
                case DEFROSTING_TEMPERATURE_IDENTIFIER:
                    defrostingTemperature = Property.getFloat(property.getValueBytes());
                    break;
                case HVAC_PROFILE_IDENTIFIER:
                    byte[] value = property.getValueBytes();
                    int hvacActiveOnDays = value[0];
                    autoHvacConstant = Property.getBit(hvacActiveOnDays, 7);
                    autoHvacStates = new AutoHvacState[7];

                    for (int j = 0; j < 7; j++) {
                        boolean active = Property.getBit(hvacActiveOnDays, j);
                        int hour = value[1 + j * 2];
                        int minute = value[1 + j * 2 + 1];
                        autoHvacStates[j] = new AutoHvacState(active, j, hour, minute);
                    }

                    autoHvacState = new AutoHvacProperty(property.getPropertyBytes());
                    break;
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private ClimateState(Builder builder) {
        super(builder);
        insideTemperature = builder.insideTemperature;
        outsideTemperature = builder.outsideTemperature;
        driverTemperatureSetting = builder.driverTemperatureSetting;
        passengerTemperatureSetting = builder.passengerTemperatureSetting;
        hvacActive = builder.hvacActive;
        defoggingActive = builder.defoggingActive;
        defrostingActive = builder.defrostingActive;
        ionisingActive = builder.ionisingActive;
        defrostingTemperature = builder.defrostingTemperature;
        autoHvacState = builder.autoHvacState;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Float insideTemperature;
        private Float outsideTemperature;
        private Float driverTemperatureSetting;
        private Float passengerTemperatureSetting;
        private Boolean hvacActive;
        private Boolean defoggingActive;
        private Boolean defrostingActive;
        private Boolean ionisingActive;
        private Float defrostingTemperature;

        private AutoHvacProperty autoHvacState;

        public Builder() {
            super(TYPE);
        }

        /**
         *
         * @param insideTemperature The inside temperature.
         * @return The builder.
         */
        public Builder setInsideTemperature(Float insideTemperature) {
            this.insideTemperature = insideTemperature;
            addProperty(new FloatProperty(INSIDE_TEMPERATURE_IDENTIFIER, insideTemperature));
            return this;
        }

        /**
         *
         * @param outsideTemperature The outside temperature.
         * @return The builder.
         */
        public Builder setOutsideTemperature(Float outsideTemperature) {
            this.outsideTemperature = outsideTemperature;
            addProperty(new FloatProperty(OUTSIDE_TEMPERATURE_IDENTIFIER, outsideTemperature));
            return this;
        }

        /**
         *
         * @param driverTemperatureSetting The driver temperature.
         * @return The builder.
         */
        public Builder setDriverTemperatureSetting(Float driverTemperatureSetting) {
            this.driverTemperatureSetting = driverTemperatureSetting;
            addProperty(new FloatProperty(DRIVER_TEMPERATURE_SETTING_IDENTIFIER, driverTemperatureSetting));
            return this;
        }

        /**
         *
         * @param passengerTemperatureSetting The passenger temperature setting.
         * @return The builder.
         */
        public Builder setPassengerTemperatureSetting(Float passengerTemperatureSetting) {
            this.passengerTemperatureSetting = passengerTemperatureSetting;
            addProperty(new FloatProperty(PASSENGER_TEMPERATURE_SETTING_IDENTIFIER, passengerTemperatureSetting));
            return this;
        }

        /**
         *
         * @param hvacActive Whether HVAC is active or not.
         * @return The builder.
         */
        public Builder setHvacActive(Boolean hvacActive) {
            this.hvacActive = hvacActive;
            addProperty(new BooleanProperty(HVAC_ACTIVE_IDENTIFIER, hvacActive));
            return this;
        }

        /**
         *
         * @param defoggingActive Whether defogging is active or not.
         * @return The builder.
         */
        public Builder setDefoggingActive(Boolean defoggingActive) {
            this.defoggingActive = defoggingActive;
            addProperty(new BooleanProperty(DEFOGGING_ACTIVE_IDENTIFIER, defoggingActive));
            return this;
        }

        /**
         *
         * @param defrostingActive Whether defrosting is active or not.
         * @return The builder.
         */
        public Builder setDefrostingActive(Boolean defrostingActive) {
            this.defrostingActive = defrostingActive;
            addProperty(new BooleanProperty(DEFROSTING_ACTIVE_IDENTIFIER, defrostingActive));
            return this;
        }

        /**
         *
         * @param ionisingActive Whether ionising is active or not.
         * @return The builder.
         */
        public Builder setIonisingActive(Boolean ionisingActive) {
            this.ionisingActive = ionisingActive;
            addProperty(new BooleanProperty(IONISING_ACTIVE_IDENTIFIER, ionisingActive));
            return this;
        }

        /**
         *
         * @param defrostingTemperature The defrosting temperature
         * @return The builder.
         */
        public Builder setDefrostingTemperature(Float defrostingTemperature) {
            this.defrostingTemperature = defrostingTemperature;
            addProperty(new FloatProperty(DEFROSTING_TEMPERATURE_IDENTIFIER, defrostingTemperature));
            return this;
        }

        /**
         *
         * @param autoHvacState The Auto HVAC state.
         * @return The builder.
         */
        public Builder setAutoHvacState(AutoHvacProperty autoHvacState) {
            this.autoHvacState = autoHvacState;
            autoHvacState.setIdentifier(HVAC_PROFILE_IDENTIFIER);
            addProperty(autoHvacState);
            return this;
        }

        public ClimateState build() {
            return new ClimateState(this);
        }
    }
}
