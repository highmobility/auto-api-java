package com.highmobility.autoapi;

import com.highmobility.autoapi.property.AutoHvacState;
import com.highmobility.autoapi.property.ClimateProfileProperty;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;

/**
 * Set the climate profile. The result is sent through the evented Climate State message with the
 * new state.
 */
public class SetClimateProfile extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x02);

    /**
     * Create a set climate profile command. At least one parameter is expected not to be null.
     *
     * @param autoHvacStates       The auto hvac states.
     * @param driverTemperature    The driver temperature.
     * @param passengerTemperature The passenger temperature.
     *
     * @throws IllegalArgumentException When all arguments are null
     */
    public SetClimateProfile(AutoHvacState[] autoHvacStates,
                                           Float driverTemperature,
                                           Float passengerTemperature) {
        super(TYPE, getProperties(autoHvacStates, driverTemperature, passengerTemperature));
    }

    static HMProperty[] getProperties(AutoHvacState[] autoHvacStates,
                                      Float driverTemperature,
                                      Float passengerTemperature) {
        ArrayList<Property> properties = new ArrayList<>();

        if (autoHvacStates != null) {
            ClimateProfileProperty prop = new ClimateProfileProperty((byte) 0x01, autoHvacStates);
            properties.add(prop);
        }

        if (driverTemperature != null) {
            FloatProperty prop = new FloatProperty((byte) 0x02, driverTemperature);
            properties.add(prop);
        }

        if (passengerTemperature != null) {
            FloatProperty prop = new FloatProperty((byte) 0x03, passengerTemperature);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }

    SetClimateProfile(Property[] properties) throws CommandParseException,
            IllegalArgumentException {
        super(TYPE, properties);
    }
}
