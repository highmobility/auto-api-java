package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.incoming.ChargeState;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by ttiganik on 16/12/2016.
 */

public class Charging extends FeatureState {
    ChargeState.ChargingState chargingState;
    float estimatedRange;
    float batteryLevel;
    float batteryCurrent;

    public ChargeState.ChargingState getChargingState() {
        return chargingState;
    }

    /**
     *
     * @return Estimated range in km
     */
    public float getEstimatedRange() {
        return estimatedRange;
    }

    /**
     *
     * @return Battery level percentage
     */
    public float getBatteryLevel() {
        return batteryLevel;
    }

    /**
     *
     * @return Battery current
     */
    public float getBatteryCurrent() {
        return batteryCurrent;
    }

    public byte[] getBytes() {
        byte[] bytes = getBytesWithCapabilityCount(3);
        // TODO: implement
        return bytes;
    }


    Charging(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.CHARGING);

        if (bytes.length != 11) throw new CommandParseException();

        chargingState = ChargeState.ChargingState.fromByte(bytes[3]);
        estimatedRange = ((bytes[4] & 0xff) << 8) | (bytes[5] & 0xff);
        batteryLevel = bytes[6] / 100f;
        byte[] batteryCurrentBytes = Arrays.copyOfRange(bytes, 7, 7 + 4);
        batteryCurrent = ByteBuffer.wrap(batteryCurrentBytes).getFloat();
    }
}
