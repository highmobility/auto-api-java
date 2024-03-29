/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.EngineType;
import com.highmobility.autoapi.value.measurement.Mass;
import com.highmobility.autoapi.value.measurement.Power;
import com.highmobility.autoapi.value.measurement.Torque;
import com.highmobility.autoapi.value.measurement.Volume;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Vehicle Information capability
 */
public class VehicleInformation {
    public static final int IDENTIFIER = Identifier.VEHICLE_INFORMATION;

    public static final byte PROPERTY_POWERTRAIN = 0x02;
    public static final byte PROPERTY_MODEL_NAME = 0x03;
    public static final byte PROPERTY_NAME = 0x04;
    public static final byte PROPERTY_LICENSE_PLATE = 0x05;
    public static final byte PROPERTY_SALES_DESIGNATION = 0x06;
    public static final byte PROPERTY_MODEL_YEAR = 0x07;
    public static final byte PROPERTY_COLOUR_NAME = 0x08;
    public static final byte PROPERTY_POWER_IN_KW = 0x09;
    public static final byte PROPERTY_NUMBER_OF_DOORS = 0x0a;
    public static final byte PROPERTY_NUMBER_OF_SEATS = 0x0b;
    public static final byte PROPERTY_ENGINE_VOLUME = 0x0c;
    public static final byte PROPERTY_ENGINE_MAX_TORQUE = 0x0d;
    public static final byte PROPERTY_GEARBOX = 0x0e;
    public static final byte PROPERTY_DISPLAY_UNIT = 0x0f;
    public static final byte PROPERTY_DRIVER_SEAT_LOCATION = 0x10;
    public static final byte PROPERTY_EQUIPMENTS = 0x11;
    public static final byte PROPERTY_POWER = 0x13;
    public static final byte PROPERTY_LANGUAGE = 0x14;
    public static final byte PROPERTY_TIMEFORMAT = 0x15;
    public static final byte PROPERTY_DRIVE = 0x16;
    public static final byte PROPERTY_POWERTRAIN_SECONDARY = 0x17;
    public static final byte PROPERTY_FUEL_TANK_CAPACITY = 0x18;
    public static final byte PROPERTY_BUILD_DATE = 0x19;
    public static final byte PROPERTY_COUNTRY_CODE = 0x1a;
    public static final byte PROPERTY_MODEL_KEY = 0x1b;
    public static final byte PROPERTY_DATA_QUALITY = 0x1c;
    public static final byte PROPERTY_EXTRA_EQUIPMENT_CODES = 0x1d;
    public static final byte PROPERTY_SERIES = 0x1e;
    public static final byte PROPERTY_LAST_DATA_TRANSFER_DATE = 0x1f;
    public static final byte PROPERTY_TIME_ZONE = 0x20;
    public static final byte PROPERTY_VEHICLE_MASS = 0x21;

    /**
     * Get vehicle information
     */
    public static class GetVehicleInformation extends GetCommand<State> {
        /**
         * Get all Vehicle Information properties
         */
        public GetVehicleInformation() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Vehicle Information properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetVehicleInformation(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Vehicle Information properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetVehicleInformation(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetVehicleInformation(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Vehicle Information properties
     * 
     * @deprecated use {@link GetVehicleInformation#GetVehicleInformation(byte...)} instead
     */
    @Deprecated
    public static class GetVehicleInformationProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetVehicleInformationProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetVehicleInformationProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetVehicleInformationProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The vehicle information state
     */
    public static class State extends SetCommand {
        Property<EngineType> powertrain = new Property<>(EngineType.class, PROPERTY_POWERTRAIN);
        Property<String> modelName = new Property<>(String.class, PROPERTY_MODEL_NAME);
        Property<String> name = new Property<>(String.class, PROPERTY_NAME);
        Property<String> licensePlate = new Property<>(String.class, PROPERTY_LICENSE_PLATE);
        Property<String> salesDesignation = new Property<>(String.class, PROPERTY_SALES_DESIGNATION);
        PropertyInteger modelYear = new PropertyInteger(PROPERTY_MODEL_YEAR, false);
        Property<String> colourName = new Property<>(String.class, PROPERTY_COLOUR_NAME);
        Property<Power> powerInKW = new Property<>(Power.class, PROPERTY_POWER_IN_KW);
        PropertyInteger numberOfDoors = new PropertyInteger(PROPERTY_NUMBER_OF_DOORS, false);
        PropertyInteger numberOfSeats = new PropertyInteger(PROPERTY_NUMBER_OF_SEATS, false);
        Property<Volume> engineVolume = new Property<>(Volume.class, PROPERTY_ENGINE_VOLUME);
        Property<Torque> engineMaxTorque = new Property<>(Torque.class, PROPERTY_ENGINE_MAX_TORQUE);
        Property<Gearbox> gearbox = new Property<>(Gearbox.class, PROPERTY_GEARBOX);
        Property<DisplayUnit> displayUnit = new Property<>(DisplayUnit.class, PROPERTY_DISPLAY_UNIT);
        Property<DriverSeatLocation> driverSeatLocation = new Property<>(DriverSeatLocation.class, PROPERTY_DRIVER_SEAT_LOCATION);
        List<Property<String>> equipments;
        Property<Power> power = new Property<>(Power.class, PROPERTY_POWER);
        Property<String> language = new Property<>(String.class, PROPERTY_LANGUAGE);
        Property<Timeformat> timeformat = new Property<>(Timeformat.class, PROPERTY_TIMEFORMAT);
        Property<Drive> drive = new Property<>(Drive.class, PROPERTY_DRIVE);
        Property<EngineType> powertrainSecondary = new Property<>(EngineType.class, PROPERTY_POWERTRAIN_SECONDARY);
        Property<Volume> fuelTankCapacity = new Property<>(Volume.class, PROPERTY_FUEL_TANK_CAPACITY);
        Property<Calendar> buildDate = new Property<>(Calendar.class, PROPERTY_BUILD_DATE);
        Property<String> countryCode = new Property<>(String.class, PROPERTY_COUNTRY_CODE);
        Property<String> modelKey = new Property<>(String.class, PROPERTY_MODEL_KEY);
        Property<DataQuality> dataQuality = new Property<>(DataQuality.class, PROPERTY_DATA_QUALITY);
        List<Property<String>> extraEquipmentCodes;
        Property<String> series = new Property<>(String.class, PROPERTY_SERIES);
        Property<Calendar> lastDataTransferDate = new Property<>(Calendar.class, PROPERTY_LAST_DATA_TRANSFER_DATE);
        Property<TimeZone> timeZone = new Property<>(TimeZone.class, PROPERTY_TIME_ZONE);
        Property<Mass> vehicleMass = new Property<>(Mass.class, PROPERTY_VEHICLE_MASS);
    
        /**
         * @return Type of the (primary) powertrain
         */
        public Property<EngineType> getPowertrain() {
            return powertrain;
        }
    
        /**
         * @return The vehicle model name
         */
        public Property<String> getModelName() {
            return modelName;
        }
    
        /**
         * @return The vehicle name (nickname)
         */
        public Property<String> getName() {
            return name;
        }
    
        /**
         * @return The license plate number
         */
        public Property<String> getLicensePlate() {
            return licensePlate;
        }
    
        /**
         * @return The sales designation of the model
         */
        public Property<String> getSalesDesignation() {
            return salesDesignation;
        }
    
        /**
         * @return The vehicle model manufacturing year number
         */
        public PropertyInteger getModelYear() {
            return modelYear;
        }
    
        /**
         * @return The colour name
         */
        public Property<String> getColourName() {
            return colourName;
        }
    
        /**
         * @return The power of the vehicle
         * @deprecated removed the unit from the name. Replaced by {@link #getPower()}
         */
        @Deprecated
        public Property<Power> getPowerInKW() {
            return powerInKW;
        }
    
        /**
         * @return The number of doors
         */
        public PropertyInteger getNumberOfDoors() {
            return numberOfDoors;
        }
    
        /**
         * @return The number of seats
         */
        public PropertyInteger getNumberOfSeats() {
            return numberOfSeats;
        }
    
        /**
         * @return The engine volume displacement
         */
        public Property<Volume> getEngineVolume() {
            return engineVolume;
        }
    
        /**
         * @return The maximum engine torque
         */
        public Property<Torque> getEngineMaxTorque() {
            return engineMaxTorque;
        }
    
        /**
         * @return The gearbox
         */
        public Property<Gearbox> getGearbox() {
            return gearbox;
        }
    
        /**
         * @return The display unit
         */
        public Property<DisplayUnit> getDisplayUnit() {
            return displayUnit;
        }
    
        /**
         * @return The driver seat location
         */
        public Property<DriverSeatLocation> getDriverSeatLocation() {
            return driverSeatLocation;
        }
    
        /**
         * @return Names of equipment the vehicle is equipped with
         */
        public List<Property<String>> getEquipments() {
            return equipments;
        }
    
        /**
         * @return The power of the vehicle
         */
        public Property<Power> getPower() {
            return power;
        }
    
        /**
         * @return The language on headunit
         */
        public Property<String> getLanguage() {
            return language;
        }
    
        /**
         * @return The timeformat on headunit
         */
        public Property<Timeformat> getTimeformat() {
            return timeformat;
        }
    
        /**
         * @return Wheels driven by the engine
         */
        public Property<Drive> getDrive() {
            return drive;
        }
    
        /**
         * @return The powertrain secondary
         */
        public Property<EngineType> getPowertrainSecondary() {
            return powertrainSecondary;
        }
    
        /**
         * @return The fuel tank capacity measured in liters
         */
        public Property<Volume> getFuelTankCapacity() {
            return fuelTankCapacity;
        }
    
        /**
         * @return Build (construction) date of the vehicle.
         */
        public Property<Calendar> getBuildDate() {
            return buildDate;
        }
    
        /**
         * @return The country code of the vehicle.
         */
        public Property<String> getCountryCode() {
            return countryCode;
        }
    
        /**
         * @return The model key of the vehicle.
         */
        public Property<String> getModelKey() {
            return modelKey;
        }
    
        /**
         * @return Evaluation of the timeliness of the available vehicle data.
         */
        public Property<DataQuality> getDataQuality() {
            return dataQuality;
        }
    
        /**
         * @return Codes of the extra equipment the vehicle has
         */
        public List<Property<String>> getExtraEquipmentCodes() {
            return extraEquipmentCodes;
        }
    
        /**
         * @return The vehicle model's series
         */
        public Property<String> getSeries() {
            return series;
        }
    
        /**
         * @return The last trip date
         */
        public Property<Calendar> getLastDataTransferDate() {
            return lastDataTransferDate;
        }
    
        /**
         * @return Time zone setting in the vehicle.
         */
        public Property<TimeZone> getTimeZone() {
            return timeZone;
        }
    
        /**
         * @return Vehicle mass.
         */
        public Property<Mass> getVehicleMass() {
            return vehicleMass;
        }
    
        State(byte[] bytes) {
            super(bytes);
    
            final ArrayList<Property<String>> equipmentsBuilder = new ArrayList<>();
            final ArrayList<Property<String>> extraEquipmentCodesBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_POWERTRAIN: return powertrain.update(p);
                        case PROPERTY_MODEL_NAME: return modelName.update(p);
                        case PROPERTY_NAME: return name.update(p);
                        case PROPERTY_LICENSE_PLATE: return licensePlate.update(p);
                        case PROPERTY_SALES_DESIGNATION: return salesDesignation.update(p);
                        case PROPERTY_MODEL_YEAR: return modelYear.update(p);
                        case PROPERTY_COLOUR_NAME: return colourName.update(p);
                        case PROPERTY_POWER_IN_KW: return powerInKW.update(p);
                        case PROPERTY_NUMBER_OF_DOORS: return numberOfDoors.update(p);
                        case PROPERTY_NUMBER_OF_SEATS: return numberOfSeats.update(p);
                        case PROPERTY_ENGINE_VOLUME: return engineVolume.update(p);
                        case PROPERTY_ENGINE_MAX_TORQUE: return engineMaxTorque.update(p);
                        case PROPERTY_GEARBOX: return gearbox.update(p);
                        case PROPERTY_DISPLAY_UNIT: return displayUnit.update(p);
                        case PROPERTY_DRIVER_SEAT_LOCATION: return driverSeatLocation.update(p);
                        case PROPERTY_EQUIPMENTS:
                            Property<String> equipment = new Property<>(String.class, p);
                            equipmentsBuilder.add(equipment);
                            return equipment;
                        case PROPERTY_POWER: return power.update(p);
                        case PROPERTY_LANGUAGE: return language.update(p);
                        case PROPERTY_TIMEFORMAT: return timeformat.update(p);
                        case PROPERTY_DRIVE: return drive.update(p);
                        case PROPERTY_POWERTRAIN_SECONDARY: return powertrainSecondary.update(p);
                        case PROPERTY_FUEL_TANK_CAPACITY: return fuelTankCapacity.update(p);
                        case PROPERTY_BUILD_DATE: return buildDate.update(p);
                        case PROPERTY_COUNTRY_CODE: return countryCode.update(p);
                        case PROPERTY_MODEL_KEY: return modelKey.update(p);
                        case PROPERTY_DATA_QUALITY: return dataQuality.update(p);
                        case PROPERTY_EXTRA_EQUIPMENT_CODES:
                            Property<String> extraEquipmentCode = new Property<>(String.class, p);
                            extraEquipmentCodesBuilder.add(extraEquipmentCode);
                            return extraEquipmentCode;
                        case PROPERTY_SERIES: return series.update(p);
                        case PROPERTY_LAST_DATA_TRANSFER_DATE: return lastDataTransferDate.update(p);
                        case PROPERTY_TIME_ZONE: return timeZone.update(p);
                        case PROPERTY_VEHICLE_MASS: return vehicleMass.update(p);
                    }
    
                    return null;
                });
            }
    
            equipments = equipmentsBuilder;
            extraEquipmentCodes = extraEquipmentCodesBuilder;
        }
    
        public static final class Builder extends SetCommand.Builder<Builder> {
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                SetCommand baseSetCommand = super.build();
                Command resolved = CommandResolver.resolve(baseSetCommand.getByteArray());
                return (State) resolved;
            }
    
            /**
             * @param powertrain Type of the (primary) powertrain
             * @return The builder
             */
            public Builder setPowertrain(Property<EngineType> powertrain) {
                Property property = powertrain.setIdentifier(PROPERTY_POWERTRAIN);
                addProperty(property);
                return this;
            }
            
            /**
             * @param modelName The vehicle model name
             * @return The builder
             */
            public Builder setModelName(Property<String> modelName) {
                Property property = modelName.setIdentifier(PROPERTY_MODEL_NAME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param name The vehicle name (nickname)
             * @return The builder
             */
            public Builder setName(Property<String> name) {
                Property property = name.setIdentifier(PROPERTY_NAME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param licensePlate The license plate number
             * @return The builder
             */
            public Builder setLicensePlate(Property<String> licensePlate) {
                Property property = licensePlate.setIdentifier(PROPERTY_LICENSE_PLATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param salesDesignation The sales designation of the model
             * @return The builder
             */
            public Builder setSalesDesignation(Property<String> salesDesignation) {
                Property property = salesDesignation.setIdentifier(PROPERTY_SALES_DESIGNATION);
                addProperty(property);
                return this;
            }
            
            /**
             * @param modelYear The vehicle model manufacturing year number
             * @return The builder
             */
            public Builder setModelYear(Property<Integer> modelYear) {
                Property property = new PropertyInteger(PROPERTY_MODEL_YEAR, false, 2, modelYear);
                addProperty(property);
                return this;
            }
            
            /**
             * @param colourName The colour name
             * @return The builder
             */
            public Builder setColourName(Property<String> colourName) {
                Property property = colourName.setIdentifier(PROPERTY_COLOUR_NAME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param powerInKW The power of the vehicle
             * @return The builder
             * @deprecated removed the unit from the name. Replaced by {@link #getPower()}
             */
            @Deprecated
            public Builder setPowerInKW(Property<Power> powerInKW) {
                Property property = powerInKW.setIdentifier(PROPERTY_POWER_IN_KW);
                addProperty(property);
                return this;
            }
            
            /**
             * @param numberOfDoors The number of doors
             * @return The builder
             */
            public Builder setNumberOfDoors(Property<Integer> numberOfDoors) {
                Property property = new PropertyInteger(PROPERTY_NUMBER_OF_DOORS, false, 1, numberOfDoors);
                addProperty(property);
                return this;
            }
            
            /**
             * @param numberOfSeats The number of seats
             * @return The builder
             */
            public Builder setNumberOfSeats(Property<Integer> numberOfSeats) {
                Property property = new PropertyInteger(PROPERTY_NUMBER_OF_SEATS, false, 1, numberOfSeats);
                addProperty(property);
                return this;
            }
            
            /**
             * @param engineVolume The engine volume displacement
             * @return The builder
             */
            public Builder setEngineVolume(Property<Volume> engineVolume) {
                Property property = engineVolume.setIdentifier(PROPERTY_ENGINE_VOLUME);
                addProperty(property);
                return this;
            }
            
            /**
             * @param engineMaxTorque The maximum engine torque
             * @return The builder
             */
            public Builder setEngineMaxTorque(Property<Torque> engineMaxTorque) {
                Property property = engineMaxTorque.setIdentifier(PROPERTY_ENGINE_MAX_TORQUE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param gearbox The gearbox
             * @return The builder
             */
            public Builder setGearbox(Property<Gearbox> gearbox) {
                Property property = gearbox.setIdentifier(PROPERTY_GEARBOX);
                addProperty(property);
                return this;
            }
            
            /**
             * @param displayUnit The display unit
             * @return The builder
             */
            public Builder setDisplayUnit(Property<DisplayUnit> displayUnit) {
                Property property = displayUnit.setIdentifier(PROPERTY_DISPLAY_UNIT);
                addProperty(property);
                return this;
            }
            
            /**
             * @param driverSeatLocation The driver seat location
             * @return The builder
             */
            public Builder setDriverSeatLocation(Property<DriverSeatLocation> driverSeatLocation) {
                Property property = driverSeatLocation.setIdentifier(PROPERTY_DRIVER_SEAT_LOCATION);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of equipments
             * 
             * @param equipments The equipments. Names of equipment the vehicle is equipped with
             * @return The builder
             */
            public Builder setEquipments(Property<String>[] equipments) {
                for (int i = 0; i < equipments.length; i++) {
                    addEquipment(equipments[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single equipment
             * 
             * @param equipment The equipment. Names of equipment the vehicle is equipped with
             * @return The builder
             */
            public Builder addEquipment(Property<String> equipment) {
                equipment.setIdentifier(PROPERTY_EQUIPMENTS);
                addProperty(equipment);
                return this;
            }
            
            /**
             * @param power The power of the vehicle
             * @return The builder
             */
            public Builder setPower(Property<Power> power) {
                Property property = power.setIdentifier(PROPERTY_POWER);
                addProperty(property);
                return this;
            }
            
            /**
             * @param language The language on headunit
             * @return The builder
             */
            public Builder setLanguage(Property<String> language) {
                Property property = language.setIdentifier(PROPERTY_LANGUAGE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param timeformat The timeformat on headunit
             * @return The builder
             */
            public Builder setTimeformat(Property<Timeformat> timeformat) {
                Property property = timeformat.setIdentifier(PROPERTY_TIMEFORMAT);
                addProperty(property);
                return this;
            }
            
            /**
             * @param drive Wheels driven by the engine
             * @return The builder
             */
            public Builder setDrive(Property<Drive> drive) {
                Property property = drive.setIdentifier(PROPERTY_DRIVE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param powertrainSecondary The powertrain secondary
             * @return The builder
             */
            public Builder setPowertrainSecondary(Property<EngineType> powertrainSecondary) {
                Property property = powertrainSecondary.setIdentifier(PROPERTY_POWERTRAIN_SECONDARY);
                addProperty(property);
                return this;
            }
            
            /**
             * @param fuelTankCapacity The fuel tank capacity measured in liters
             * @return The builder
             */
            public Builder setFuelTankCapacity(Property<Volume> fuelTankCapacity) {
                Property property = fuelTankCapacity.setIdentifier(PROPERTY_FUEL_TANK_CAPACITY);
                addProperty(property);
                return this;
            }
            
            /**
             * @param buildDate Build (construction) date of the vehicle.
             * @return The builder
             */
            public Builder setBuildDate(Property<Calendar> buildDate) {
                Property property = buildDate.setIdentifier(PROPERTY_BUILD_DATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param countryCode The country code of the vehicle.
             * @return The builder
             */
            public Builder setCountryCode(Property<String> countryCode) {
                Property property = countryCode.setIdentifier(PROPERTY_COUNTRY_CODE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param modelKey The model key of the vehicle.
             * @return The builder
             */
            public Builder setModelKey(Property<String> modelKey) {
                Property property = modelKey.setIdentifier(PROPERTY_MODEL_KEY);
                addProperty(property);
                return this;
            }
            
            /**
             * @param dataQuality Evaluation of the timeliness of the available vehicle data.
             * @return The builder
             */
            public Builder setDataQuality(Property<DataQuality> dataQuality) {
                Property property = dataQuality.setIdentifier(PROPERTY_DATA_QUALITY);
                addProperty(property);
                return this;
            }
            
            /**
             * Add an array of extra equipment codes
             * 
             * @param extraEquipmentCodes The extra equipment codes. Codes of the extra equipment the vehicle has
             * @return The builder
             */
            public Builder setExtraEquipmentCodes(Property<String>[] extraEquipmentCodes) {
                for (int i = 0; i < extraEquipmentCodes.length; i++) {
                    addExtraEquipmentCode(extraEquipmentCodes[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single extra equipment code
             * 
             * @param extraEquipmentCode The extra equipment code. Codes of the extra equipment the vehicle has
             * @return The builder
             */
            public Builder addExtraEquipmentCode(Property<String> extraEquipmentCode) {
                extraEquipmentCode.setIdentifier(PROPERTY_EXTRA_EQUIPMENT_CODES);
                addProperty(extraEquipmentCode);
                return this;
            }
            
            /**
             * @param series The vehicle model's series
             * @return The builder
             */
            public Builder setSeries(Property<String> series) {
                Property property = series.setIdentifier(PROPERTY_SERIES);
                addProperty(property);
                return this;
            }
            
            /**
             * @param lastDataTransferDate The last trip date
             * @return The builder
             */
            public Builder setLastDataTransferDate(Property<Calendar> lastDataTransferDate) {
                Property property = lastDataTransferDate.setIdentifier(PROPERTY_LAST_DATA_TRANSFER_DATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param timeZone Time zone setting in the vehicle.
             * @return The builder
             */
            public Builder setTimeZone(Property<TimeZone> timeZone) {
                Property property = timeZone.setIdentifier(PROPERTY_TIME_ZONE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param vehicleMass Vehicle mass.
             * @return The builder
             */
            public Builder setVehicleMass(Property<Mass> vehicleMass) {
                Property property = vehicleMass.setIdentifier(PROPERTY_VEHICLE_MASS);
                addProperty(property);
                return this;
            }
        }
    }

    public enum Gearbox implements ByteEnum {
        MANUAL((byte) 0x00),
        AUTOMATIC((byte) 0x01),
        SEMI_AUTOMATIC((byte) 0x02);
    
        public static Gearbox fromByte(byte byteValue) throws CommandParseException {
            Gearbox[] values = Gearbox.values();
    
            for (int i = 0; i < values.length; i++) {
                Gearbox state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Gearbox.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Gearbox(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum DisplayUnit implements ByteEnum {
        KM((byte) 0x00),
        MILES((byte) 0x01);
    
        public static DisplayUnit fromByte(byte byteValue) throws CommandParseException {
            DisplayUnit[] values = DisplayUnit.values();
    
            for (int i = 0; i < values.length; i++) {
                DisplayUnit state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(DisplayUnit.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        DisplayUnit(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum DriverSeatLocation implements ByteEnum {
        LEFT((byte) 0x00),
        RIGHT((byte) 0x01),
        CENTER((byte) 0x02);
    
        public static DriverSeatLocation fromByte(byte byteValue) throws CommandParseException {
            DriverSeatLocation[] values = DriverSeatLocation.values();
    
            for (int i = 0; i < values.length; i++) {
                DriverSeatLocation state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(DriverSeatLocation.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        DriverSeatLocation(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum Timeformat implements ByteEnum {
        TWELVE_H((byte) 0x00),
        TWENTY_FOUR_H((byte) 0x01);
    
        public static Timeformat fromByte(byte byteValue) throws CommandParseException {
            Timeformat[] values = Timeformat.values();
    
            for (int i = 0; i < values.length; i++) {
                Timeformat state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Timeformat.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Timeformat(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum Drive implements ByteEnum {
        /**
         * Front-wheel drive
         */
        FWD((byte) 0x00),
        /**
         * Rear-wheel drive
         */
        RWD((byte) 0x01),
        /**
         * Four-wheel drive
         */
        FOUR_WD((byte) 0x02),
        /**
         * All-wheel drive
         */
        AWD((byte) 0x03);
    
        public static Drive fromByte(byte byteValue) throws CommandParseException {
            Drive[] values = Drive.values();
    
            for (int i = 0; i < values.length; i++) {
                Drive state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Drive.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Drive(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum DataQuality implements ByteEnum {
        /**
         * No data available
         */
        NO_DATA((byte) 0x00),
        /**
         * Data transmitted within the last 48h
         */
        UP_TO_DATE((byte) 0x01),
        /**
         * Data transmitted within the last 7 days, but not within the last 48h
         */
        ALMOST_UP_TO_DATE((byte) 0x02),
        /**
         * No data transferred within the last 7 days
         */
        OUT_OF_DATE((byte) 0x03);
    
        public static DataQuality fromByte(byte byteValue) throws CommandParseException {
            DataQuality[] values = DataQuality.values();
    
            for (int i = 0; i < values.length; i++) {
                DataQuality state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(DataQuality.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        DataQuality(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum TimeZone implements ByteEnum {
        WINTERTIME((byte) 0x00),
        SUMMERTIME((byte) 0x01),
        UTC((byte) 0x02),
        MANUAL((byte) 0x03);
    
        public static TimeZone fromByte(byte byteValue) throws CommandParseException {
            TimeZone[] values = TimeZone.values();
    
            for (int i = 0; i < values.length; i++) {
                TimeZone state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(TimeZone.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        TimeZone(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}