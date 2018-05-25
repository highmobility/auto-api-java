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

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.utils.Bytes;
import java.util.Arrays;
import java.util.HashMap;

public class Type {
    static HashMap<Type, Type> stateCommands;
    static {
        stateCommands = new HashMap<>();
        stateCommands.put(GetDiagnosticsState.TYPE, DiagnosticsState.TYPE);
        stateCommands.put(GetMaintenanceState.TYPE, MaintenanceState.TYPE);
        stateCommands.put(GetRaceState.TYPE, RaceState.TYPE);
        stateCommands.put(GetOffroadState.TYPE, OffroadState.TYPE);
        stateCommands.put(GetLockState.TYPE, LockState.TYPE);
        stateCommands.put(GetIgnitionState.TYPE, IgnitionState.TYPE);
        stateCommands.put(GetTrunkState.TYPE, TrunkState.TYPE);
        stateCommands.put(GetChassisSettings.TYPE, ChassisSettings.TYPE);
        stateCommands.put(GetChargeState.TYPE, ChargeState.TYPE);
        stateCommands.put(GetClimateState.TYPE, ClimateState.TYPE);
        stateCommands.put(GetLightsState.TYPE, LightsState.TYPE);
        stateCommands.put(GetRooftopState.TYPE, RooftopState.TYPE);
        stateCommands.put(GetSeatsState.TYPE, SeatsState.TYPE);
        stateCommands.put(GetWindscreenState.TYPE, WindscreenState.TYPE);
        stateCommands.put(GetWindowsState.TYPE, WindowsState.TYPE);
        stateCommands.put(GetFlashersState.TYPE, FlashersState.TYPE);
        stateCommands.put(GetWifiState.TYPE, WifiState.TYPE);
        stateCommands.put(GetControlMode.TYPE, ControlMode.TYPE);
        stateCommands.put(GetKeyfobPosition.TYPE, KeyfobPosition.TYPE);
        stateCommands.put(GetParkingBrakeState.TYPE, ParkingBrakeState.TYPE);
        stateCommands.put(GetParkingTicket.TYPE, ParkingTicket.TYPE);
        stateCommands.put(GetTheftAlarmState.TYPE, TheftAlarmState.TYPE);
        stateCommands.put(GetValetMode.TYPE, ValetMode.TYPE);
        stateCommands.put(GetVehicleLocation.TYPE, VehicleLocation.TYPE);
        stateCommands.put(GetVehicleTime.TYPE, VehicleTime.TYPE);
        stateCommands.put(GetNaviDestination.TYPE, NaviDestination.TYPE);
        stateCommands.put(GetLightConditions.TYPE, LightConditions.TYPE);
        stateCommands.put(GetWeatherConditions.TYPE, WeatherConditions.TYPE);
        stateCommands.put(GetHomeChargerState.TYPE, HomeChargerState.TYPE);
        stateCommands.put(GetGasFlapState.TYPE, GasFlapState.TYPE);
        stateCommands.put(GetFirmwareVersion.TYPE, FirmwareVersion.TYPE);
        stateCommands.put(GetDashboardLights.TYPE, DashboardLights.TYPE);
    }

    byte[] identifierAndType;
    Identifier identifier; // this is for debug purpose

    public byte[] getIdentifierAndType() {
        return identifierAndType;
    }

    public byte getType() {
        return identifierAndType[2];
    }

    public Type getStateCommand() {
        return stateCommands.get(this);
    }

    /**
     *
     * @param identifierAndType 2 identifier bytes and 1 type byte
     */
    public Type(byte[] identifierAndType) {
        this.identifierAndType = identifierAndType;
        identifier = Identifier.fromBytes(identifierAndType[0], identifierAndType[1]);
    }

    public Type(byte[] identifier, int type) {
        this(Bytes.concatBytes(identifier, (byte) type));
    }

    Type(Identifier identifier, int type) {
        this(identifier.getBytesWithType((byte) type));
    }

    Type(int identifierFirstByte, int identifierSecondByte, int type) {
        this(new byte[] { (byte) identifierFirstByte, (byte) identifierSecondByte, (byte) type} );
    }

    byte[] addByte(byte extraByte) {
        return Bytes.concatBytes(identifierAndType, extraByte);
    }

    byte[] addProperty(HMProperty property) {
        return addBytes(property.getPropertyBytes());
    }

    byte[] addBytes(byte[] extraBytes) {
        return Bytes.concatBytes(identifierAndType, extraBytes);
    }

    byte[] getIdentifier() {
        return new byte[] { identifierAndType[0], identifierAndType[1] };
    }

    @Override public boolean equals(Object obj) {
        return obj.getClass() == Type.class
                && Arrays.equals(((Type)obj).getIdentifierAndType(), getIdentifierAndType());
    }

    @Override public String toString() {
        return ByteUtils.hexFromBytes(getIdentifierAndType());
    }
}