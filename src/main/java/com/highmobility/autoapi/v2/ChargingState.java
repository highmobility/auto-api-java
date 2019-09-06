// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.property.PropertyInteger;
import com.highmobility.autoapi.v2.value.Position;
import com.highmobility.autoapi.v2.value.DepartureTime;
import com.highmobility.autoapi.v2.value.ReductionTime;
import com.highmobility.autoapi.v2.value.Timer;
import java.util.ArrayList;
import java.util.List;

public class ChargingState extends Command {
    PropertyInteger estimatedRange = new PropertyInteger(0x02, false);
    Property<Double> batterLevel = new Property(Double.class, 0x03);
    Property<Float> batteryCurrentAC = new Property(Float.class, 0x04);
    Property<Float> batteryCurrentDC = new Property(Float.class, 0x05);
    Property<Float> chargerVoltageAC = new Property(Float.class, 0x06);
    Property<Float> chargerVoltageDC = new Property(Float.class, 0x07);
    Property<Double> chargeLimit = new Property(Double.class, 0x08);
    PropertyInteger timeToCompleteCharge = new PropertyInteger(0x09, false);
    Property<Float> chargingRateKW = new Property(Float.class, 0x0a);
    Property<Position> chargePortState = new Property(Position.class, 0x0b);
    Property<ChargeMode> chargeMode = new Property(ChargeMode.class, 0x0c);
    Property<Float> maxChargingCurrent = new Property(Float.class, 0x0e);
    Property<PlugType> plugType = new Property(PlugType.class, 0x0f);
    Property<ChargingWindowChosen> chargingWindowChosen = new Property(ChargingWindowChosen.class, 0x10);
    Property<DepartureTime> departureTimes[];
    Property<ReductionTime> reductionTimes[];
    Property<Float> batteryTemperature = new Property(Float.class, 0x14);
    Property<Timer> timers[];
    Property<PluggedIn> pluggedIn = new Property(PluggedIn.class, 0x16);
    Property<ChargingState> chargingState = new Property(ChargingState.class, 0x17);

    /**
     * @return Estimated range in km
     */
    public PropertyInteger getEstimatedRange() {
        return estimatedRange;
    }

    /**
     * @return Battery level percentage between 0.0-1.0
     */
    public Property<Double> getBatterLevel() {
        return batterLevel;
    }

    /**
     * @return Battery current in 4-bytes per IEEE 754
     */
    public Property<Float> getBatteryCurrentAC() {
        return batteryCurrentAC;
    }

    /**
     * @return Battery current in 4-bytes per IEEE 754
     */
    public Property<Float> getBatteryCurrentDC() {
        return batteryCurrentDC;
    }

    /**
     * @return Charger voltage in 4-bytes per IEEE 754
     */
    public Property<Float> getChargerVoltageAC() {
        return chargerVoltageAC;
    }

    /**
     * @return Charger voltage in 4-bytes per IEEE 754
     */
    public Property<Float> getChargerVoltageDC() {
        return chargerVoltageDC;
    }

    /**
     * @return Charge limit percentage between 0.0-1.0
     */
    public Property<Double> getChargeLimit() {
        return chargeLimit;
    }

    /**
     * @return Time until charging completed in minutes
     */
    public PropertyInteger getTimeToCompleteCharge() {
        return timeToCompleteCharge;
    }

    /**
     * @return Charge rate in kW represented as 4-bytes per IEEE 754, when charging
     */
    public Property<Float> getChargingRateKW() {
        return chargingRateKW;
    }

    /**
     * @return The charge port state
     */
    public Property<Position> getChargePortState() {
        return chargePortState;
    }

    /**
     * @return The charge mode
     */
    public Property<ChargeMode> getChargeMode() {
        return chargeMode;
    }

    /**
     * @return Maximum charging current in 4-bytes per IEEE 754
     */
    public Property<Float> getMaxChargingCurrent() {
        return maxChargingCurrent;
    }

    /**
     * @return The plug type
     */
    public Property<PlugType> getPlugType() {
        return plugType;
    }

    /**
     * @return The charging window chosen
     */
    public Property<ChargingWindowChosen> getChargingWindowChosen() {
        return chargingWindowChosen;
    }

    /**
     * @return The departure times
     */
    public Property<DepartureTime>[] getDepartureTimes() {
        return departureTimes;
    }

    /**
     * @return The reduction times
     */
    public Property<ReductionTime>[] getReductionTimes() {
        return reductionTimes;
    }

    /**
     * @return Battery temperature in Celsius in 4-bytes per IEEE 754
     */
    public Property<Float> getBatteryTemperature() {
        return batteryTemperature;
    }

    /**
     * @return The timers
     */
    public Property<Timer>[] getTimers() {
        return timers;
    }

    /**
     * @return The plugged in
     */
    public Property<PluggedIn> getPluggedIn() {
        return pluggedIn;
    }

    /**
     * @return The charging state
     */
    public Property<ChargingState> getChargingState() {
        return chargingState;
    }

    ChargingState(byte[] bytes) {
        super(bytes);

        ArrayList<Property> departureTimesBuilder = new ArrayList<>();
        ArrayList<Property> reductionTimesBuilder = new ArrayList<>();
        ArrayList<Property> timersBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x02: return estimatedRange.update(p);
                    case 0x03: return batterLevel.update(p);
                    case 0x04: return batteryCurrentAC.update(p);
                    case 0x05: return batteryCurrentDC.update(p);
                    case 0x06: return chargerVoltageAC.update(p);
                    case 0x07: return chargerVoltageDC.update(p);
                    case 0x08: return chargeLimit.update(p);
                    case 0x09: return timeToCompleteCharge.update(p);
                    case 0x0a: return chargingRateKW.update(p);
                    case 0x0b: return chargePortState.update(p);
                    case 0x0c: return chargeMode.update(p);
                    case 0x0e: return maxChargingCurrent.update(p);
                    case 0x0f: return plugType.update(p);
                    case 0x10: return chargingWindowChosen.update(p);
                    case 0x11:
                        Property<DepartureTime> departureTime = new Property(DepartureTime.class, p);
                        departureTimesBuilder.add(departureTime);
                        return departureTime;
                    case 0x13:
                        Property<ReductionTime> reductionTime = new Property(ReductionTime.class, p);
                        reductionTimesBuilder.add(reductionTime);
                        return reductionTime;
                    case 0x14: return batteryTemperature.update(p);
                    case 0x15:
                        Property<Timer> timer = new Property(Timer.class, p);
                        timersBuilder.add(timer);
                        return timer;
                    case 0x16: return pluggedIn.update(p);
                    case 0x17: return chargingState.update(p);
                }

                return null;
            });
        }

        departureTimes = departureTimesBuilder.toArray(new Property[0]);
        reductionTimes = reductionTimesBuilder.toArray(new Property[0]);
        timers = timersBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private ChargingState(Builder builder) {
        super(builder);

        estimatedRange = builder.estimatedRange;
        batterLevel = builder.batterLevel;
        batteryCurrentAC = builder.batteryCurrentAC;
        batteryCurrentDC = builder.batteryCurrentDC;
        chargerVoltageAC = builder.chargerVoltageAC;
        chargerVoltageDC = builder.chargerVoltageDC;
        chargeLimit = builder.chargeLimit;
        timeToCompleteCharge = builder.timeToCompleteCharge;
        chargingRateKW = builder.chargingRateKW;
        chargePortState = builder.chargePortState;
        chargeMode = builder.chargeMode;
        maxChargingCurrent = builder.maxChargingCurrent;
        plugType = builder.plugType;
        chargingWindowChosen = builder.chargingWindowChosen;
        departureTimes = builder.departureTimes.toArray(new Property[0]);
        reductionTimes = builder.reductionTimes.toArray(new Property[0]);
        batteryTemperature = builder.batteryTemperature;
        timers = builder.timers.toArray(new Property[0]);
        pluggedIn = builder.pluggedIn;
        chargingState = builder.chargingState;
    }

    public static final class Builder extends Command.Builder {
        private PropertyInteger estimatedRange;
        private Property<Double> batterLevel;
        private Property<Float> batteryCurrentAC;
        private Property<Float> batteryCurrentDC;
        private Property<Float> chargerVoltageAC;
        private Property<Float> chargerVoltageDC;
        private Property<Double> chargeLimit;
        private PropertyInteger timeToCompleteCharge;
        private Property<Float> chargingRateKW;
        private Property<Position> chargePortState;
        private Property<ChargeMode> chargeMode;
        private Property<Float> maxChargingCurrent;
        private Property<PlugType> plugType;
        private Property<ChargingWindowChosen> chargingWindowChosen;
        private List<Property> departureTimes = new ArrayList<>();
        private List<Property> reductionTimes = new ArrayList<>();
        private Property<Float> batteryTemperature;
        private List<Property> timers = new ArrayList<>();
        private Property<PluggedIn> pluggedIn;
        private Property<ChargingState> chargingState;

        public Builder() {
            super(Identifier.CHARGING);
        }

        public ChargingState build() {
            return new ChargingState(this);
        }

        /**
         * @param estimatedRange Estimated range in km
         * @return The builder
         */
        public Builder setEstimatedRange(PropertyInteger estimatedRange) {
            this.estimatedRange = new PropertyInteger(0x02, false, 2, estimatedRange);
            addProperty(estimatedRange);
            return this;
        }
        
        /**
         * @param batterLevel Battery level percentage between 0.0-1.0
         * @return The builder
         */
        public Builder setBatterLevel(Property<Double> batterLevel) {
            this.batterLevel = batterLevel.setIdentifier(0x03);
            addProperty(batterLevel);
            return this;
        }
        
        /**
         * @param batteryCurrentAC Battery current in 4-bytes per IEEE 754
         * @return The builder
         */
        public Builder setBatteryCurrentAC(Property<Float> batteryCurrentAC) {
            this.batteryCurrentAC = batteryCurrentAC.setIdentifier(0x04);
            addProperty(batteryCurrentAC);
            return this;
        }
        
        /**
         * @param batteryCurrentDC Battery current in 4-bytes per IEEE 754
         * @return The builder
         */
        public Builder setBatteryCurrentDC(Property<Float> batteryCurrentDC) {
            this.batteryCurrentDC = batteryCurrentDC.setIdentifier(0x05);
            addProperty(batteryCurrentDC);
            return this;
        }
        
        /**
         * @param chargerVoltageAC Charger voltage in 4-bytes per IEEE 754
         * @return The builder
         */
        public Builder setChargerVoltageAC(Property<Float> chargerVoltageAC) {
            this.chargerVoltageAC = chargerVoltageAC.setIdentifier(0x06);
            addProperty(chargerVoltageAC);
            return this;
        }
        
        /**
         * @param chargerVoltageDC Charger voltage in 4-bytes per IEEE 754
         * @return The builder
         */
        public Builder setChargerVoltageDC(Property<Float> chargerVoltageDC) {
            this.chargerVoltageDC = chargerVoltageDC.setIdentifier(0x07);
            addProperty(chargerVoltageDC);
            return this;
        }
        
        /**
         * @param chargeLimit Charge limit percentage between 0.0-1.0
         * @return The builder
         */
        public Builder setChargeLimit(Property<Double> chargeLimit) {
            this.chargeLimit = chargeLimit.setIdentifier(0x08);
            addProperty(chargeLimit);
            return this;
        }
        
        /**
         * @param timeToCompleteCharge Time until charging completed in minutes
         * @return The builder
         */
        public Builder setTimeToCompleteCharge(PropertyInteger timeToCompleteCharge) {
            this.timeToCompleteCharge = new PropertyInteger(0x09, false, 1, timeToCompleteCharge);
            addProperty(timeToCompleteCharge);
            return this;
        }
        
        /**
         * @param chargingRateKW Charge rate in kW represented as 4-bytes per IEEE 754, when charging
         * @return The builder
         */
        public Builder setChargingRateKW(Property<Float> chargingRateKW) {
            this.chargingRateKW = chargingRateKW.setIdentifier(0x0a);
            addProperty(chargingRateKW);
            return this;
        }
        
        /**
         * @param chargePortState The charge port state
         * @return The builder
         */
        public Builder setChargePortState(Property<Position> chargePortState) {
            this.chargePortState = chargePortState.setIdentifier(0x0b);
            addProperty(chargePortState);
            return this;
        }
        
        /**
         * @param chargeMode The charge mode
         * @return The builder
         */
        public Builder setChargeMode(Property<ChargeMode> chargeMode) {
            this.chargeMode = chargeMode.setIdentifier(0x0c);
            addProperty(chargeMode);
            return this;
        }
        
        /**
         * @param maxChargingCurrent Maximum charging current in 4-bytes per IEEE 754
         * @return The builder
         */
        public Builder setMaxChargingCurrent(Property<Float> maxChargingCurrent) {
            this.maxChargingCurrent = maxChargingCurrent.setIdentifier(0x0e);
            addProperty(maxChargingCurrent);
            return this;
        }
        
        /**
         * @param plugType The plug type
         * @return The builder
         */
        public Builder setPlugType(Property<PlugType> plugType) {
            this.plugType = plugType.setIdentifier(0x0f);
            addProperty(plugType);
            return this;
        }
        
        /**
         * @param chargingWindowChosen The charging window chosen
         * @return The builder
         */
        public Builder setChargingWindowChosen(Property<ChargingWindowChosen> chargingWindowChosen) {
            this.chargingWindowChosen = chargingWindowChosen.setIdentifier(0x10);
            addProperty(chargingWindowChosen);
            return this;
        }
        
        /**
         * Add an array of departure times.
         * 
         * @param departureTimes The departure times
         * @return The builder
         */
        public Builder setDepartureTimes(Property<DepartureTime>[] departureTimes) {
            this.departureTimes.clear();
            for (int i = 0; i < departureTimes.length; i++) {
                addDepartureTime(departureTimes[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single departure time.
         * 
         * @param departureTime The departure time
         * @return The builder
         */
        public Builder addDepartureTime(Property<DepartureTime> departureTime) {
            departureTime.setIdentifier(0x11);
            addProperty(departureTime);
            departureTimes.add(departureTime);
            return this;
        }
        
        /**
         * Add an array of reduction times.
         * 
         * @param reductionTimes The reduction times
         * @return The builder
         */
        public Builder setReductionTimes(Property<ReductionTime>[] reductionTimes) {
            this.reductionTimes.clear();
            for (int i = 0; i < reductionTimes.length; i++) {
                addReductionTime(reductionTimes[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single reduction time.
         * 
         * @param reductionTime The reduction time
         * @return The builder
         */
        public Builder addReductionTime(Property<ReductionTime> reductionTime) {
            reductionTime.setIdentifier(0x13);
            addProperty(reductionTime);
            reductionTimes.add(reductionTime);
            return this;
        }
        
        /**
         * @param batteryTemperature Battery temperature in Celsius in 4-bytes per IEEE 754
         * @return The builder
         */
        public Builder setBatteryTemperature(Property<Float> batteryTemperature) {
            this.batteryTemperature = batteryTemperature.setIdentifier(0x14);
            addProperty(batteryTemperature);
            return this;
        }
        
        /**
         * Add an array of timers.
         * 
         * @param timers The timers
         * @return The builder
         */
        public Builder setTimers(Property<Timer>[] timers) {
            this.timers.clear();
            for (int i = 0; i < timers.length; i++) {
                addTimer(timers[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single timer.
         * 
         * @param timer The timer
         * @return The builder
         */
        public Builder addTimer(Property<Timer> timer) {
            timer.setIdentifier(0x15);
            addProperty(timer);
            timers.add(timer);
            return this;
        }
        
        /**
         * @param pluggedIn The plugged in
         * @return The builder
         */
        public Builder setPluggedIn(Property<PluggedIn> pluggedIn) {
            this.pluggedIn = pluggedIn.setIdentifier(0x16);
            addProperty(pluggedIn);
            return this;
        }
        
        /**
         * @param chargingState The charging state
         * @return The builder
         */
        public Builder setChargingState(Property<ChargingState> chargingState) {
            this.chargingState = chargingState.setIdentifier(0x17);
            addProperty(chargingState);
            return this;
        }
    }

    public enum ChargeMode {
        IMMEDIATE((byte)0x00),
        TIMER_BASED((byte)0x01),
        INDUCTIVE((byte)0x02);
    
        public static ChargeMode fromByte(byte byteValue) throws CommandParseException {
            ChargeMode[] values = ChargeMode.values();
    
            for (int i = 0; i < values.length; i++) {
                ChargeMode state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        ChargeMode(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }

    public enum PlugType {
        TYPE_1((byte)0x00),
        TYPE_2((byte)0x01),
        CCS((byte)0x02),
        CHADEMO((byte)0x03);
    
        public static PlugType fromByte(byte byteValue) throws CommandParseException {
            PlugType[] values = PlugType.values();
    
            for (int i = 0; i < values.length; i++) {
                PlugType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        PlugType(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }

    public enum ChargingWindowChosen {
        NOT_CHOSEN((byte)0x00),
        CHOSEN((byte)0x01);
    
        public static ChargingWindowChosen fromByte(byte byteValue) throws CommandParseException {
            ChargingWindowChosen[] values = ChargingWindowChosen.values();
    
            for (int i = 0; i < values.length; i++) {
                ChargingWindowChosen state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        ChargingWindowChosen(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }

    public enum PluggedIn {
        DISCONNECTED((byte)0x00),
        PLUGGED_IN((byte)0x01);
    
        public static PluggedIn fromByte(byte byteValue) throws CommandParseException {
            PluggedIn[] values = PluggedIn.values();
    
            for (int i = 0; i < values.length; i++) {
                PluggedIn state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        PluggedIn(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }

    public enum ChargingState {
        NOT_CHARGING((byte)0x00),
        CHARGING((byte)0x01),
        CHARGING_COMPLETE((byte)0x02),
        INITIALISING((byte)0x03),
        CHARGING_PAUSED((byte)0x04),
        CHARGING_ERROR((byte)0x05);
    
        public static ChargingState fromByte(byte byteValue) throws CommandParseException {
            ChargingState[] values = ChargingState.values();
    
            for (int i = 0; i < values.length; i++) {
                ChargingState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        ChargingState(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }
}