/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi.incoming;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.vehiclestatus.FeatureState;
import com.highmobility.utils.Bytes;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by ttiganik on 13/09/16.
 *
 * This command is sent when a Get Vehicle Status command is received by the car.
 *
 */
public class VehicleStatus extends IncomingCommand {
    public enum PowerTrain {
        UNKNOWN((byte)0x00),
        ALLELECTRIC((byte)0x01),
        COMBUSTIONENGINE((byte)0x02),
        PLUGINHYBRID((byte)0x03),
        HYDROGEN((byte)0x04),
        HYDROGENHYBRID((byte)0x05);

        public static PowerTrain fromByte(byte value) throws CommandParseException {
            PowerTrain[] values = PowerTrain.values();

            for (int i = 0; i < values.length; i++) {
                PowerTrain capability = values[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        PowerTrain(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    /**
     * Create the vehicle status command bytes
     *
     * @param vin vehicle vin
     * @param powerTrain the power train
     * @param modelName the model name
     * @param name the vehicle (nick)name
     * @param licensePlate the license plate number
     * @param featureStates the identifier states
     *
     * @return command bytes
     */
    public static byte[] getCommandBytes(String vin,
                                         PowerTrain powerTrain,
                                         String modelName,
                                         String name,
                                         String licensePlate,
                                         FeatureState[] featureStates) throws UnsupportedEncodingException {
        byte[] bytes = Command.VehicleStatus.VEHICLE_STATUS.getIdentifierAndType();
        bytes = Bytes.concatBytes(bytes, vin.getBytes("UTF-8"));

        bytes = Bytes.concatBytes(bytes, powerTrain.getByte());

        bytes = Bytes.concatBytes(bytes, (byte)modelName.length());
        bytes = Bytes.concatBytes(bytes, modelName.getBytes("UTF-8"));

        bytes = Bytes.concatBytes(bytes, (byte)name.length());
        bytes = Bytes.concatBytes(bytes, name.getBytes("UTF-8"));

        bytes = Bytes.concatBytes(bytes, (byte)licensePlate.length());
        bytes = Bytes.concatBytes(bytes, licensePlate.getBytes("UTF-8"));

        bytes = Bytes.concatBytes(bytes, (byte)featureStates.length);

        for (int i = 0; i < featureStates.length; i++) {
            byte[] capabilityBytes = featureStates[i].getBytes();
            bytes = Bytes.concatBytes(bytes, capabilityBytes);
        }

        return bytes;
    }

    /**
     *
     * @return The specific states for the vehicle's features.
     */
    public FeatureState[] getFeatureStates() {
        return featureStates;
    }

    /**
     *
     * @return The vehicle's VIN number
     */
    public String getVin() {
        return vin;
    }

    /**
     *
     * @return The vehicle's power train
     */
    public PowerTrain getPowerTrain() {
        return powerTrain;
    }

    /**
     *
     * @return The vehicle's model name
     */
    public String getModelName() {
        return modelName;
    }

    /**
     *
     * @return The vehicle's name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return The vehicle's license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    FeatureState[] featureStates;

    String vin;
    PowerTrain powerTrain;
    String modelName;
    String name;
    String licensePlate;

    public VehicleStatus(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 4) throw new CommandParseException();

        int lengthPosition = 21;
        int length = bytes[lengthPosition];
        int position = 22;
        try {
            byte[] vinBytes = Arrays.copyOfRange(bytes, 3, 20);
            vin = new String(vinBytes, "UTF-8");

            modelName = new String(Arrays.copyOfRange(bytes, position, position + length), "UTF-8");

            lengthPosition = position + length;
            length = bytes[lengthPosition];
            position = lengthPosition + 1;
            name = new String(Arrays.copyOfRange(bytes, position, position + length), "UTF-8");

            lengthPosition = position + length;
            length = bytes[lengthPosition];
            position = lengthPosition + 1;
            licensePlate = new String(Arrays.copyOfRange(bytes, position, position + length), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            throw new CommandParseException();
        }

        powerTrain = PowerTrain.fromByte(bytes[20]);

        int stateCountPosition = position + length;
        int stateCount = bytes[stateCountPosition];
        if (stateCount == 0) return;

        featureStates = new FeatureState[stateCount];
        int knownStatesCount = 0;
        int statePosition = stateCountPosition + 1;

        for (int i = 0; i < stateCount; i++) {
            int stateLength = bytes[statePosition + 2];
            byte[] stateBytes = Arrays.copyOfRange(bytes, statePosition,
                    statePosition + 3 + stateLength); // length = 2x identifier byte + length byte + bytes
            FeatureState state = FeatureState.fromBytes(stateBytes);

            featureStates[i] = state;
            statePosition += stateLength + 3;
            if (state != null) knownStatesCount++;
        }

        if (stateCount != knownStatesCount) {
            // resize the array if any of the capabilities is unknown(null)
            FeatureState[] trimmedStates = new FeatureState[knownStatesCount];
            int trimmedStatesPosition = 0;
            for (int i = 0; i < stateCount; i++) {
                FeatureState state = featureStates[i];
                if (state != null) {
                    trimmedStates[trimmedStatesPosition] = state;
                    trimmedStatesPosition++;
                }
            }

            featureStates = trimmedStates;
        }
    }
}
