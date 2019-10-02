// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.value.DrivingModeActivationPeriod;
import com.highmobility.autoapi.value.DrivingModeEnergyConsumption;
import java.util.Calendar;
import javax.annotation.Nullable;
import com.highmobility.autoapi.value.DrivingMode;
import java.util.ArrayList;
import java.util.List;

public class UsageState extends SetCommand {
    PropertyInteger averageWeeklyDistance = new PropertyInteger(0x01, false);
    PropertyInteger averageWeeklyDistanceLongRun = new PropertyInteger(0x02, false);
    Property<Double> accelerationEvaluation = new Property(Double.class, 0x03);
    Property<Double> drivingStyleEvaluation = new Property(Double.class, 0x04);
    Property<DrivingModeActivationPeriod>[] drivingModesActivationPeriods;
    Property<DrivingModeEnergyConsumption>[] drivingModesEnergyConsumptions;
    Property<Float> lastTripEnergyConsumption = new Property(Float.class, 0x07);
    Property<Float> lastTripFuelConsumption = new Property(Float.class, 0x08);
    PropertyInteger mileageAfterLastTrip = new PropertyInteger(0x09, false);
    Property<Double> lastTripElectricPortion = new Property(Double.class, 0x0a);
    Property<Float> lastTripAverageEnergyRecuperation = new Property(Float.class, 0x0b);
    Property<Double> lastTripBatteryRemaining = new Property(Double.class, 0x0c);
    Property<Calendar> lastTripDate = new Property(Calendar.class, 0x0d);
    Property<Float> averageFuelConsumption = new Property(Float.class, 0x0e);
    Property<Float> currentFuelConsumption = new Property(Float.class, 0x0f);

    /**
     * @return Average weekly distance in km
     */
    public PropertyInteger getAverageWeeklyDistance() {
        return averageWeeklyDistance;
    }

    /**
     * @return Average weekyl distance, over long term, in km
     */
    public PropertyInteger getAverageWeeklyDistanceLongRun() {
        return averageWeeklyDistanceLongRun;
    }

    /**
     * @return Acceleration evaluation percentage
     */
    public Property<Double> getAccelerationEvaluation() {
        return accelerationEvaluation;
    }

    /**
     * @return Driving style evaluation percentage
     */
    public Property<Double> getDrivingStyleEvaluation() {
        return drivingStyleEvaluation;
    }

    /**
     * @return The driving modes activation periods
     */
    public Property<DrivingModeActivationPeriod>[] getDrivingModesActivationPeriods() {
        return drivingModesActivationPeriods;
    }

    /**
     * @return The driving modes energy consumptions
     */
    public Property<DrivingModeEnergyConsumption>[] getDrivingModesEnergyConsumptions() {
        return drivingModesEnergyConsumptions;
    }

    /**
     * @return Energy consumption in the last trip in kWh
     */
    public Property<Float> getLastTripEnergyConsumption() {
        return lastTripEnergyConsumption;
    }

    /**
     * @return Fuel consumption in the last trip in L
     */
    public Property<Float> getLastTripFuelConsumption() {
        return lastTripFuelConsumption;
    }

    /**
     * @return Mileage after the last trip in km
     */
    public PropertyInteger getMileageAfterLastTrip() {
        return mileageAfterLastTrip;
    }

    /**
     * @return Portion of the last trip used in electric mode
     */
    public Property<Double> getLastTripElectricPortion() {
        return lastTripElectricPortion;
    }

    /**
     * @return Energy recuperation rate for last trip, in kWh / 100 km
     */
    public Property<Float> getLastTripAverageEnergyRecuperation() {
        return lastTripAverageEnergyRecuperation;
    }

    /**
     * @return Battery % remaining after last trip
     */
    public Property<Double> getLastTripBatteryRemaining() {
        return lastTripBatteryRemaining;
    }

    /**
     * @return Milliseconds since UNIX Epoch time
     */
    public Property<Calendar> getLastTripDate() {
        return lastTripDate;
    }

    /**
     * @return Average fuel consumption for current trip in liters / 100 km
     */
    public Property<Float> getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    /**
     * @return Current fuel consumption in liters / 100 km
     */
    public Property<Float> getCurrentFuelConsumption() {
        return currentFuelConsumption;
    }

    /**
     * @param mode The driving mode.
     * @return The driving mode activation period for given mode.
     */
    @Nullable public Property<DrivingModeActivationPeriod> getDrivingModeActivationPeriod(DrivingMode mode) {
        for (Property<DrivingModeActivationPeriod> drivingModeActivationPeriod :
                drivingModesActivationPeriods) {
            if (drivingModeActivationPeriod.getValue() != null && drivingModeActivationPeriod.getValue().getDrivingMode() == mode) {
                return drivingModeActivationPeriod;
            }
        }
        return null;
    }

    /**
     * @param mode The driving mode.
     * @return The driving mode energy consumptionfor given mode.
     */
    @Nullable public Property<DrivingModeEnergyConsumption> getDrivingModeEnergyConsumption(DrivingMode mode) {
        for (Property<DrivingModeEnergyConsumption> drivingModeEnergyConsumption :
                drivingModesEnergyConsumptions) {
            if (drivingModeEnergyConsumption.getValue() != null && drivingModeEnergyConsumption.getValue().getDrivingMode() == mode) {
                return drivingModeEnergyConsumption;
            }
        }
        return null;
    }

    UsageState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> drivingModesActivationPeriodsBuilder = new ArrayList<>();
        ArrayList<Property> drivingModesEnergyConsumptionsBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return averageWeeklyDistance.update(p);
                    case 0x02: return averageWeeklyDistanceLongRun.update(p);
                    case 0x03: return accelerationEvaluation.update(p);
                    case 0x04: return drivingStyleEvaluation.update(p);
                    case 0x05:
                        Property<DrivingModeActivationPeriod> drivingModesActivationPeriod = new Property(DrivingModeActivationPeriod.class, p);
                        drivingModesActivationPeriodsBuilder.add(drivingModesActivationPeriod);
                        return drivingModesActivationPeriod;
                    case 0x06:
                        Property<DrivingModeEnergyConsumption> drivingModesEnergyConsumption = new Property(DrivingModeEnergyConsumption.class, p);
                        drivingModesEnergyConsumptionsBuilder.add(drivingModesEnergyConsumption);
                        return drivingModesEnergyConsumption;
                    case 0x07: return lastTripEnergyConsumption.update(p);
                    case 0x08: return lastTripFuelConsumption.update(p);
                    case 0x09: return mileageAfterLastTrip.update(p);
                    case 0x0a: return lastTripElectricPortion.update(p);
                    case 0x0b: return lastTripAverageEnergyRecuperation.update(p);
                    case 0x0c: return lastTripBatteryRemaining.update(p);
                    case 0x0d: return lastTripDate.update(p);
                    case 0x0e: return averageFuelConsumption.update(p);
                    case 0x0f: return currentFuelConsumption.update(p);
                }

                return null;
            });
        }

        drivingModesActivationPeriods = drivingModesActivationPeriodsBuilder.toArray(new Property[0]);
        drivingModesEnergyConsumptions = drivingModesEnergyConsumptionsBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private UsageState(Builder builder) {
        super(builder);

        averageWeeklyDistance = builder.averageWeeklyDistance;
        averageWeeklyDistanceLongRun = builder.averageWeeklyDistanceLongRun;
        accelerationEvaluation = builder.accelerationEvaluation;
        drivingStyleEvaluation = builder.drivingStyleEvaluation;
        drivingModesActivationPeriods = builder.drivingModesActivationPeriods.toArray(new Property[0]);
        drivingModesEnergyConsumptions = builder.drivingModesEnergyConsumptions.toArray(new Property[0]);
        lastTripEnergyConsumption = builder.lastTripEnergyConsumption;
        lastTripFuelConsumption = builder.lastTripFuelConsumption;
        mileageAfterLastTrip = builder.mileageAfterLastTrip;
        lastTripElectricPortion = builder.lastTripElectricPortion;
        lastTripAverageEnergyRecuperation = builder.lastTripAverageEnergyRecuperation;
        lastTripBatteryRemaining = builder.lastTripBatteryRemaining;
        lastTripDate = builder.lastTripDate;
        averageFuelConsumption = builder.averageFuelConsumption;
        currentFuelConsumption = builder.currentFuelConsumption;
    }

    public static final class Builder extends SetCommand.Builder {
        private PropertyInteger averageWeeklyDistance;
        private PropertyInteger averageWeeklyDistanceLongRun;
        private Property<Double> accelerationEvaluation;
        private Property<Double> drivingStyleEvaluation;
        private List<Property> drivingModesActivationPeriods = new ArrayList<>();
        private List<Property> drivingModesEnergyConsumptions = new ArrayList<>();
        private Property<Float> lastTripEnergyConsumption;
        private Property<Float> lastTripFuelConsumption;
        private PropertyInteger mileageAfterLastTrip;
        private Property<Double> lastTripElectricPortion;
        private Property<Float> lastTripAverageEnergyRecuperation;
        private Property<Double> lastTripBatteryRemaining;
        private Property<Calendar> lastTripDate;
        private Property<Float> averageFuelConsumption;
        private Property<Float> currentFuelConsumption;

        public Builder() {
            super(Identifier.USAGE);
        }

        public UsageState build() {
            return new UsageState(this);
        }

        /**
         * @param averageWeeklyDistance Average weekly distance in km
         * @return The builder
         */
        public Builder setAverageWeeklyDistance(Property<Integer> averageWeeklyDistance) {
            this.averageWeeklyDistance = new PropertyInteger(0x01, false, 2, averageWeeklyDistance);
            addProperty(this.averageWeeklyDistance);
            return this;
        }
        
        /**
         * @param averageWeeklyDistanceLongRun Average weekyl distance, over long term, in km
         * @return The builder
         */
        public Builder setAverageWeeklyDistanceLongRun(Property<Integer> averageWeeklyDistanceLongRun) {
            this.averageWeeklyDistanceLongRun = new PropertyInteger(0x02, false, 2, averageWeeklyDistanceLongRun);
            addProperty(this.averageWeeklyDistanceLongRun);
            return this;
        }
        
        /**
         * @param accelerationEvaluation Acceleration evaluation percentage
         * @return The builder
         */
        public Builder setAccelerationEvaluation(Property<Double> accelerationEvaluation) {
            this.accelerationEvaluation = accelerationEvaluation.setIdentifier(0x03);
            addProperty(this.accelerationEvaluation);
            return this;
        }
        
        /**
         * @param drivingStyleEvaluation Driving style evaluation percentage
         * @return The builder
         */
        public Builder setDrivingStyleEvaluation(Property<Double> drivingStyleEvaluation) {
            this.drivingStyleEvaluation = drivingStyleEvaluation.setIdentifier(0x04);
            addProperty(this.drivingStyleEvaluation);
            return this;
        }
        
        /**
         * Add an array of driving modes activation periods.
         * 
         * @param drivingModesActivationPeriods The driving modes activation periods
         * @return The builder
         */
        public Builder setDrivingModesActivationPeriods(Property<DrivingModeActivationPeriod>[] drivingModesActivationPeriods) {
            this.drivingModesActivationPeriods.clear();
            for (int i = 0; i < drivingModesActivationPeriods.length; i++) {
                addDrivingModesActivationPeriod(drivingModesActivationPeriods[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single driving modes activation period.
         * 
         * @param drivingModesActivationPeriod The driving modes activation period
         * @return The builder
         */
        public Builder addDrivingModesActivationPeriod(Property<DrivingModeActivationPeriod> drivingModesActivationPeriod) {
            drivingModesActivationPeriod.setIdentifier(0x05);
            addProperty(drivingModesActivationPeriod);
            drivingModesActivationPeriods.add(drivingModesActivationPeriod);
            return this;
        }
        
        /**
         * Add an array of driving modes energy consumptions.
         * 
         * @param drivingModesEnergyConsumptions The driving modes energy consumptions
         * @return The builder
         */
        public Builder setDrivingModesEnergyConsumptions(Property<DrivingModeEnergyConsumption>[] drivingModesEnergyConsumptions) {
            this.drivingModesEnergyConsumptions.clear();
            for (int i = 0; i < drivingModesEnergyConsumptions.length; i++) {
                addDrivingModesEnergyConsumption(drivingModesEnergyConsumptions[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single driving modes energy consumption.
         * 
         * @param drivingModesEnergyConsumption The driving modes energy consumption
         * @return The builder
         */
        public Builder addDrivingModesEnergyConsumption(Property<DrivingModeEnergyConsumption> drivingModesEnergyConsumption) {
            drivingModesEnergyConsumption.setIdentifier(0x06);
            addProperty(drivingModesEnergyConsumption);
            drivingModesEnergyConsumptions.add(drivingModesEnergyConsumption);
            return this;
        }
        
        /**
         * @param lastTripEnergyConsumption Energy consumption in the last trip in kWh
         * @return The builder
         */
        public Builder setLastTripEnergyConsumption(Property<Float> lastTripEnergyConsumption) {
            this.lastTripEnergyConsumption = lastTripEnergyConsumption.setIdentifier(0x07);
            addProperty(this.lastTripEnergyConsumption);
            return this;
        }
        
        /**
         * @param lastTripFuelConsumption Fuel consumption in the last trip in L
         * @return The builder
         */
        public Builder setLastTripFuelConsumption(Property<Float> lastTripFuelConsumption) {
            this.lastTripFuelConsumption = lastTripFuelConsumption.setIdentifier(0x08);
            addProperty(this.lastTripFuelConsumption);
            return this;
        }
        
        /**
         * @param mileageAfterLastTrip Mileage after the last trip in km
         * @return The builder
         */
        public Builder setMileageAfterLastTrip(Property<Integer> mileageAfterLastTrip) {
            this.mileageAfterLastTrip = new PropertyInteger(0x09, false, 4, mileageAfterLastTrip);
            addProperty(this.mileageAfterLastTrip);
            return this;
        }
        
        /**
         * @param lastTripElectricPortion Portion of the last trip used in electric mode
         * @return The builder
         */
        public Builder setLastTripElectricPortion(Property<Double> lastTripElectricPortion) {
            this.lastTripElectricPortion = lastTripElectricPortion.setIdentifier(0x0a);
            addProperty(this.lastTripElectricPortion);
            return this;
        }
        
        /**
         * @param lastTripAverageEnergyRecuperation Energy recuperation rate for last trip, in kWh / 100 km
         * @return The builder
         */
        public Builder setLastTripAverageEnergyRecuperation(Property<Float> lastTripAverageEnergyRecuperation) {
            this.lastTripAverageEnergyRecuperation = lastTripAverageEnergyRecuperation.setIdentifier(0x0b);
            addProperty(this.lastTripAverageEnergyRecuperation);
            return this;
        }
        
        /**
         * @param lastTripBatteryRemaining Battery % remaining after last trip
         * @return The builder
         */
        public Builder setLastTripBatteryRemaining(Property<Double> lastTripBatteryRemaining) {
            this.lastTripBatteryRemaining = lastTripBatteryRemaining.setIdentifier(0x0c);
            addProperty(this.lastTripBatteryRemaining);
            return this;
        }
        
        /**
         * @param lastTripDate Milliseconds since UNIX Epoch time
         * @return The builder
         */
        public Builder setLastTripDate(Property<Calendar> lastTripDate) {
            this.lastTripDate = lastTripDate.setIdentifier(0x0d);
            addProperty(this.lastTripDate);
            return this;
        }
        
        /**
         * @param averageFuelConsumption Average fuel consumption for current trip in liters / 100 km
         * @return The builder
         */
        public Builder setAverageFuelConsumption(Property<Float> averageFuelConsumption) {
            this.averageFuelConsumption = averageFuelConsumption.setIdentifier(0x0e);
            addProperty(this.averageFuelConsumption);
            return this;
        }
        
        /**
         * @param currentFuelConsumption Current fuel consumption in liters / 100 km
         * @return The builder
         */
        public Builder setCurrentFuelConsumption(Property<Float> currentFuelConsumption) {
            this.currentFuelConsumption = currentFuelConsumption.setIdentifier(0x0f);
            addProperty(this.currentFuelConsumption);
            return this;
        }
    }
}