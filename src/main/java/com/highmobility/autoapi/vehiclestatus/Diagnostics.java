package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;

import com.highmobility.autoapi.incoming.DiagnosticsState;
import com.highmobility.utils.Bytes;

import java.math.BigInteger;
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

    public Diagnostics(int mileage,
                       int oilTemperature,
                       int speed,
                       int rpm,
                       float fuelLevel,
                       DiagnosticsState.WasherFluidLevel washerFluidLevel) {
        super(Command.Identifier.DIAGNOSTICS);
        this.mileage = mileage;
        this.oilTemperature = oilTemperature;
        this.speed = speed;
        this.rpm = rpm;
        this.fuelLevel = fuelLevel;
        this.washerFluidLevel = washerFluidLevel;

        byte[] mileageBytes = Bytes.intToBytes(mileage, 3);
        byte[] oilTemperatureBytes = Bytes.intToBytes(oilTemperature, 2);
        byte[] speedBytes = Bytes.intToBytes(speed, 2);
        byte[] engineRpmBytes = Bytes.intToBytes(rpm, 2);

        bytes = getBytesWithMoreThanOneByteLongFields(6, 5);

        Bytes.setBytes(bytes, mileageBytes, 3);
        Bytes.setBytes(bytes, oilTemperatureBytes, 6);
        Bytes.setBytes(bytes, speedBytes, 8);
        Bytes.setBytes(bytes, engineRpmBytes, 10);

        bytes[12] = (byte)(int)(fuelLevel * 100);
        bytes[13] = washerFluidLevel.getByte();
    }

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

        this.bytes = bytes;
    }
}
