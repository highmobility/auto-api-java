package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;

import com.highmobility.autoapi.incoming.DiagnosticsState;
import com.highmobility.byteutils.Bytes;

import java.util.Arrays;

/**
 * Created by ttiganik on 21/12/2016.
 */
public class Diagnostics extends FeatureState {
    int mileage;
    int oilTemperature;
    int speed;
    int rpm;
    float fuelLevel;
    DiagnosticsState.WasherFluidLevel washerFluidLevel;

    Diagnostics(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.DIAGNOSTICS);

        if (bytes.length < 14) throw new CommandParseException();

        mileage = Bytes.getInt(Arrays.copyOfRange(bytes, 3, 3 + 3));
        oilTemperature = Bytes.getInt(Arrays.copyOfRange(bytes, 6, 6 + 2));
        speed = Bytes.getInt(Arrays.copyOfRange(bytes, 8, 8 + 2));
        rpm = Bytes.getInt(Arrays.copyOfRange(bytes, 10, 10 + 2));
        fuelLevel = (int)bytes[12] / 100f;
        if (bytes[13] == 0x00) washerFluidLevel = DiagnosticsState.WasherFluidLevel.LOW;
        else washerFluidLevel = DiagnosticsState.WasherFluidLevel.FULL;
    }

    /**
     *
     * @return The car mileage (odometer) in km
     */
    public int getMileage() {
        return mileage;
    }

    /**
     *
     * @return Engine oil temperature in Celsius, whereas can be negative
     */
    public int getOilTemperature() {
        return oilTemperature;
    }

    /**
     *
     * @return The car speed in km/h, whereas can be negative
     */
    public int getSpeed() {
        return speed;
    }

    /**
     *
     * @return RPM of the Engine
     */
    public int getRpm() {
        return rpm;
    }

    /**
     *
     * @return Fuel level percentage between 0-100
     */
    public float getFuelLevel() {
        return fuelLevel;
    }

    /**
     *
     * @return Washer fluid level
     */
    public DiagnosticsState.WasherFluidLevel getWasherFluidLevel() {
        return washerFluidLevel;
    }
}
