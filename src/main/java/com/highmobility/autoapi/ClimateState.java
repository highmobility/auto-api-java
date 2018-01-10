package com.highmobility.autoapi;

import com.highmobility.autoapi.property.AutoHvacState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.Bytes;

/**
 * This message is sent when a Get Climate State message is received by the car. It is also sent
 * once the HVAC system has been turned on/off, when the defrosting/defogging states change or when
 * the profile is updated.
 *
 * Auto-HVAC (Heating, Ventilation and Air Conditioning) allows you to schedule times when HVAC is
 * triggered automatically to reach the desired driver temperature setting.
 */
public class ClimateState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x01);

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

    /**
     *
     * @return Inside temperature.
     */
    public Float getInsideTemperature() {
        return insideTemperature;
    }

    /**
     *
     * @return Outside temperature.
     */
    public Float getOutsideTemperature() {
        return outsideTemperature;
    }

    /**
     *
     * @return Driver temperature setting.
     */
    public Float getDriverTemperatureSetting() {
        return driverTemperatureSetting;
    }

    /**
     *
     * @return Passenger temperature setting.
     */
    public Float getPassengerTemperatureSetting() {
        return passengerTemperatureSetting;
    }

    /**
     *
     * @return Whether HVAC is active or not
     */
    public Boolean isHvacActive() {
        return hvacActive;
    }

    /**
     *
     * @return Whether defogging is active or not.
     */
    public Boolean isDefoggingActive() {
        return defoggingActive;
    }

    /**
     *
     * @return Whether defrosting is active or not.
     */
    public Boolean isDefrostingActive() {
        return defrostingActive;
    }

    /**
     *
     * @return Whether ionising is active or not.
     */
    public Boolean isIonisingActive() {
        return ionisingActive;
    }

    /**
     *
     * @return The defrosting temperature
     */
    public Float getDefrostingTemperature() {
        return defrostingTemperature;
    }

    /**
     *
     * @return Whether autoHVAC is constant(based on the car surroundings)
     */
    public Boolean isAutoHvacConstant() {
        return autoHvacConstant;
    }

    /**
     *
     * @return Array of AutoHvacState's that describe if and when the AutoHVAC is active.
     */
    public AutoHvacState[] getAutoHvacStates() {
        return autoHvacStates;
    }

    ClimateState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    insideTemperature = Property.getFloat(property.getValueBytes());
                    break;
                case 0x02:
                    outsideTemperature = Property.getFloat(property.getValueBytes());
                    break;
                case 0x03:
                    driverTemperatureSetting = Property.getFloat(property.getValueBytes());
                    break;
                case 0x04:
                    passengerTemperatureSetting = Property.getFloat(property.getValueBytes());
                    break;
                case 0x05:
                    hvacActive = Property.getBool(property.getValueByte());
                    break;
                case 0x06:
                    defoggingActive = Property.getBool(property.getValueByte());
                    break;
                case 0x07:
                    defrostingActive = Property.getBool(property.getValueByte());
                    break;
                case 0x08:
                    ionisingActive = Property.getBool(property.getValueByte());
                    break;
                case 0x09:
                    defrostingTemperature = Property.getFloat(property.getValueBytes());
                    break;
                case 0x0A:
                    byte[] value = property.getValueBytes();
                    int hvacActiveOnDays = value[0];
                    autoHvacConstant = Bytes.getBit(hvacActiveOnDays, 7);
                    autoHvacStates = new AutoHvacState[7];

                    for (int j = 0; j < 7; j++) {
                        boolean active = Bytes.getBit(hvacActiveOnDays, j);
                        int hour = value[1 + j * 2];
                        int minute = value[1 + j * 2 + 1];
                        autoHvacStates[j] = new AutoHvacState(active, j, hour, minute);
                    }

                    break;
            }
        }
    }
}
