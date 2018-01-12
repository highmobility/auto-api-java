package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Property;
import com.highmobility.autoapi.incoming.ChargeState;
import com.highmobility.utils.Bytes;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by ttiganik on 16/12/2016.
 */

public class Charging extends FeatureState {
    ChargeState.ChargingState chargingState;
    int estimatedRange;
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

    public Charging(ChargeState.ChargingState chargingState,
                    int estimatedRange, float batteryLevel,
                    float batteryCurrent) {
        super(Command.Identifier.CHARGING);
        this.chargingState = chargingState;
        this.estimatedRange = estimatedRange;
        this.batteryLevel = batteryLevel;
        this.batteryCurrent = batteryCurrent;

        bytes = getBytesWithMoreThanOneByteLongFields(4, 4);
        bytes[3] = chargingState.getByte();
        byte[] estimatedRangeBytes = Property.intToBytes(estimatedRange, 2);
        bytes[4] = estimatedRangeBytes[0];
        bytes[5] = estimatedRangeBytes[1];
        bytes[6] = (byte)(int)(batteryLevel * 100);

        byte[] batteryCurrentBytes = ByteBuffer.allocate(4).putFloat(batteryCurrent).array();

        bytes[7] = batteryCurrentBytes[0];
        bytes[8] = batteryCurrentBytes[1];
        bytes[9] = batteryCurrentBytes[2];
        bytes[10] = batteryCurrentBytes[3];
    }

    Charging(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.CHARGING);

        if (bytes.length != 11) throw new CommandParseException();

        chargingState = ChargeState.ChargingState.fromByte(bytes[3]);
        estimatedRange = ((bytes[4] & 0xff) << 8) | (bytes[5] & 0xff);
        batteryLevel = bytes[6] / 100f;
        byte[] batteryCurrentBytes = Arrays.copyOfRange(bytes, 7, 7 + 4);
        batteryCurrent = ByteBuffer.wrap(batteryCurrentBytes).getFloat();
        this.bytes = bytes;
    }
}
