package com.highmobility.autoapi.property;

public class ClimateProfileProperty extends Property {
    public ClimateProfileProperty(byte identifier, AutoHvacState[] states) throws IllegalArgumentException {
        super(identifier, 15);

        if (states.length != 7) throw new IllegalArgumentException("Less than 7 auto hvac states");

        byte autoHvacDatesByte = 0;
        
        for (int i = 0; i < 7; i++) {
            if (states[i].isActive()) {
                autoHvacDatesByte = (byte) (autoHvacDatesByte | (1 << i));
            }

            bytes[4 + i * 2] = (byte)states[i].getStartHour();
            bytes[4 + i * 2 + 1] = (byte)states[i].getStartMinute();
        }

        bytes[3] = autoHvacDatesByte;
    }
}
