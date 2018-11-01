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

import com.highmobility.utils.Base64;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

public class CommandResolver {

    /**
     * Try to parse the command bytes to a more specific Command subclass. Check the returned
     * object's instance type (instanceOf) to understand which command was received.
     *
     * @param bytes the raw command bytes.
     * @return The parsed command.
     */
    public static Command resolve(Bytes bytes) {
        return resolve(bytes.getByteArray());
    }

    /**
     * Try to parse the command bytes to a more specific Command subclass. Check the returned
     * object's instance type (instanceOf) to understand which command was received.
     *
     * @param bytes the raw command bytes.
     * @return The parsed command.
     */
    public static Command resolve(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return new Command(bytes);
        if (bytes.length < 3) return null;
        Command command = null;

        try {
            if (bytesAreForType(bytes, Failure.TYPE)) {
                command = new Failure(bytes);
            } else if (bytesAreForIdentifier(bytes, Identifier.DIAGNOSTICS)) {
                if (bytesAreForType(bytes, GetDiagnosticsState.TYPE)) {
                    command = new GetDiagnosticsState(bytes);
                } else if (bytesAreForType(bytes, DiagnosticsState.TYPE)) {
                    command = new DiagnosticsState(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.VEHICLE_STATUS)) {
                if (bytesAreForType(bytes, GetVehicleStatus.TYPE)) {
                    command = new GetVehicleStatus(bytes);
                } else if (bytesAreForType(bytes, VehicleStatus.TYPE)) {
                    command = new VehicleStatus(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.HISTORICAL)) {
                if (bytesAreForType(bytes, GetHistoricalStates.TYPE)) {
                    command = new GetHistoricalStates(bytes);
                } else if (bytesAreForType(bytes, HistoricalStates.TYPE)) {
                    command = new HistoricalStates(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.DOOR_LOCKS)) {
                if (bytesAreForType(bytes, GetLockState.TYPE)) {
                    command = new GetLockState(bytes);
                } else if (bytesAreForType(bytes, LockState.TYPE)) {
                    command = new LockState(bytes);
                } else if (bytesAreForType(bytes, LockUnlockDoors.TYPE)) {
                    command = new LockUnlockDoors(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.THEFT_ALARM)) {
                if (bytesAreForType(bytes, GetTheftAlarmState.TYPE)) {
                    command = new GetTheftAlarmState(bytes);
                } else if (bytesAreForType(bytes, TheftAlarmState.TYPE)) {
                    command = new TheftAlarmState(bytes);
                } else if (bytesAreForType(bytes, SetTheftAlarm.TYPE)) {
                    command = new SetTheftAlarm(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.CAPABILITIES)) {
                if (bytesAreForType(bytes, GetCapabilities.TYPE)) {
                    command = new GetCapabilities(bytes);
                } else if (bytesAreForType(bytes, Capabilities.TYPE)) {
                    command = new Capabilities(bytes);
                } else if (bytesAreForType(bytes, GetCapability.TYPE)) {
                    command = new GetCapability(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.TRUNK_ACCESS)) {
                if (bytesAreForType(bytes, TrunkState.TYPE)) {
                    command = new TrunkState(bytes);
                } else if (bytesAreForType(bytes, GetTrunkState.TYPE)) {
                    command = new GetTrunkState(bytes);
                } else if (bytesAreForType(bytes, ControlTrunk.TYPE)) {
                    command = new ControlTrunk(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.CHARGING)) {
                if (bytesAreForType(bytes, GetChargeState.TYPE)) {
                    command = new GetChargeState(bytes);
                } else if (bytesAreForType(bytes, ChargeState.TYPE)) {
                    command = new ChargeState(bytes);
                } else if (bytesAreForType(bytes, StartStopCharging.TYPE)) {
                    command = new StartStopCharging(bytes);
                } else if (bytesAreForType(bytes, SetChargeLimit.TYPE)) {
                    command = new SetChargeLimit(bytes);
                } else if (bytesAreForType(bytes, OpenCloseChargePort.TYPE)) {
                    command = new OpenCloseChargePort(bytes);
                } else if (bytesAreForType(bytes, SetChargeMode.TYPE)) {
                    command = new SetChargeMode(bytes);
                } else if (bytesAreForType(bytes, SetChargeTimer.TYPE)) {
                    command = new SetChargeTimer(bytes);
                } else if (bytesAreForType(bytes, SetReductionOfChargingCurrentTimes.TYPE)) {
                    command = new SetReductionOfChargingCurrentTimes(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.CLIMATE)) {
                if (bytesAreForType(bytes, GetClimateState.TYPE)) {
                    command = new GetClimateState(bytes);
                } else if (bytesAreForType(bytes, ClimateState.TYPE)) {
                    command = new ClimateState(bytes);
                } else if (bytesAreForType(bytes, StartStopIonising.TYPE)) {
                    command = new StartStopIonising(bytes);
                } else if (bytesAreForType(bytes, StartStopHvac.TYPE)) {
                    command = new StartStopHvac(bytes);
                } else if (bytesAreForType(bytes, StartStopDefrosting.TYPE)) {
                    command = new StartStopDefrosting(bytes);
                } else if (bytesAreForType(bytes, StartStopDefogging.TYPE)) {
                    command = new StartStopDefogging(bytes);
                } else if (bytesAreForType(bytes, SetHvacStartingTimes.TYPE)) {
                    command = new SetHvacStartingTimes(bytes);
                } else if (bytesAreForType(bytes, SetTemperatureSettings.TYPE)) {
                    command = new SetTemperatureSettings(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.ROOFTOP)) {
                if (bytesAreForType(bytes, GetRooftopState.TYPE)) {
                    command = new GetRooftopState(bytes);
                } else if (bytesAreForType(bytes, RooftopState.TYPE)) {
                    command = new RooftopState(bytes);
                } else if (bytesAreForType(bytes, ControlRooftop.TYPE)) {
                    command = new ControlRooftop(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.HONK_FLASH)) {
                if (bytesAreForType(bytes, GetFlashersState.TYPE)) {
                    command = new GetFlashersState(bytes);
                } else if (bytesAreForType(bytes, FlashersState.TYPE)) {
                    command = new FlashersState(bytes);
                } else if (bytesAreForType(bytes, HonkAndFlash.TYPE)) {
                    command = new HonkAndFlash(bytes);
                } else if (bytesAreForType(bytes, ActivateDeactivateEmergencyFlasher.TYPE)) {
                    command = new ActivateDeactivateEmergencyFlasher(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.REMOTE_CONTROL)) {
                if (bytesAreForType(bytes, ControlCommand.TYPE)) {
                    command = new ControlCommand(bytes);
                } else if (bytesAreForType(bytes, ControlMode.TYPE)) {
                    command = new ControlMode(bytes);
                } else if (bytesAreForType(bytes, GetControlMode.TYPE)) {
                    command = new GetControlMode(bytes);
                } else if (bytesAreForType(bytes, StartControlMode.TYPE)) {
                    command = new StartControlMode(bytes);
                } else if (bytesAreForType(bytes, StopControlMode.TYPE)) {
                    command = new StopControlMode(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.VALET_MODE)) {
                if (bytesAreForType(bytes, ValetMode.TYPE)) {
                    command = new ValetMode(bytes);
                } else if (bytesAreForType(bytes, GetValetMode.TYPE)) {
                    command = new GetValetMode(bytes);
                } else if (bytesAreForType(bytes, ActivateDeactivateValetMode.TYPE)) {
                    command = new ActivateDeactivateValetMode(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.VEHICLE_LOCATION)) {
                if (bytesAreForType(bytes, VehicleLocation.TYPE)) {
                    command = new VehicleLocation(bytes);
                } else if (bytesAreForType(bytes, GetVehicleLocation.TYPE)) {
                    command = new GetVehicleLocation(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.VEHICLE_TIME)) {
                if (bytesAreForType(bytes, VehicleTime.TYPE)) {
                    command = new VehicleTime(bytes);
                } else if (bytesAreForType(bytes, GetVehicleTime.TYPE)) {
                    command = new GetVehicleTime(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.NAVI_DESTINATION)) {
                if (bytesAreForType(bytes, NaviDestination.TYPE)) {
                    command = new NaviDestination(bytes);
                } else if (bytesAreForType(bytes, GetNaviDestination.TYPE)) {
                    command = new GetNaviDestination(bytes);
                } else if (bytesAreForType(bytes, SetNaviDestination.TYPE)) {
                    command = new SetNaviDestination(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.MAINTENANCE)) {
                if (bytesAreForType(bytes, MaintenanceState.TYPE)) {
                    command = new MaintenanceState(bytes);
                } else if (bytesAreForType(bytes, GetMaintenanceState.TYPE)) {
                    command = new GetMaintenanceState(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.HEART_RATE)) {
                if (bytesAreForType(bytes, SendHeartRate.TYPE)) {
                    command = new SendHeartRate(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.ENGINE)) {
                if (bytesAreForType(bytes, IgnitionState.TYPE)) {
                    command = new IgnitionState(bytes);
                } else if (bytesAreForType(bytes, GetIgnitionState.TYPE)) {
                    command = new GetIgnitionState(bytes);
                } else if (bytesAreForType(bytes, TurnIgnitionOnOff.TYPE)) {
                    command = new TurnIgnitionOnOff(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.LIGHTS)) {
                if (bytesAreForType(bytes, LightsState.TYPE)) {
                    command = new LightsState(bytes);
                } else if (bytesAreForType(bytes, GetLightsState.TYPE)) {
                    command = new GetLightsState(bytes);
                } else if (bytesAreForType(bytes, ControlLights.TYPE)) {
                    command = new ControlLights(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.MESSAGING)) {
                if (bytesAreForType(bytes, SendMessage.TYPE)) {
                    command = new SendMessage(bytes);
                } else if (bytesAreForType(bytes, MessageReceived.TYPE)) {
                    command = new MessageReceived(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.NOTIFICATIONS)) {
                if (bytesAreForType(bytes, Notification.TYPE)) {
                    command = new Notification(bytes);
                } else if (bytesAreForType(bytes, NotificationAction.TYPE)) {
                    command = new NotificationAction(bytes);
                } else if (bytesAreForType(bytes, ClearNotification.TYPE)) {
                    command = new ClearNotification(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.WINDOWS)) {
                if (bytesAreForType(bytes, GetWindowsState.TYPE)) {
                    command = new GetWindowsState(bytes);
                } else if (bytesAreForType(bytes, WindowsState.TYPE)) {
                    command = new WindowsState(bytes);
                } else if (bytesAreForType(bytes, ControlWindows.TYPE)) {
                    command = new ControlWindows(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.WINDSCREEN)) {
                if (bytesAreForType(bytes, GetWindscreenState.TYPE)) {
                    command = new GetWindscreenState(bytes);
                } else if (bytesAreForType(bytes, WindscreenState.TYPE)) {
                    command = new WindscreenState(bytes);
                } else if (bytesAreForType(bytes, SetWindscreenDamage.TYPE)) {
                    command = new SetWindscreenDamage(bytes);
                } else if (bytesAreForType(bytes, SetWindscreenReplacementNeeded.TYPE)) {
                    command = new SetWindscreenReplacementNeeded(bytes);
                } else if (bytesAreForType(bytes, ControlWipers.TYPE)) {
                    command = new ControlWipers(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.FUELING)) {
                if (bytesAreForType(bytes, GetGasFlapState.TYPE)) {
                    command = new GetGasFlapState(bytes);
                } else if (bytesAreForType(bytes, GasFlapState.TYPE)) {
                    command = new GasFlapState(bytes);
                } else if (bytesAreForType(bytes, OpenGasFlap.TYPE)) {
                    command = new OpenGasFlap(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.PARKING_TICKET)) {
                if (bytesAreForType(bytes, GetParkingTicket.TYPE)) {
                    command = new GetParkingTicket(bytes);
                } else if (bytesAreForType(bytes, ParkingTicket.TYPE)) {
                    command = new ParkingTicket(bytes);
                } else if (bytesAreForType(bytes, StartParking.TYPE)) {
                    command = new StartParking(bytes);
                } else if (bytesAreForType(bytes, EndParking.TYPE)) {
                    command = new EndParking(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.KEYFOB_POSITION)) {
                if (bytesAreForType(bytes, KeyFobPosition.TYPE)) {
                    command = new KeyFobPosition(bytes);
                } else if (bytesAreForType(bytes, GetKeyfobPosition.TYPE)) {
                    command = new GetKeyfobPosition(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.FIRMWARE_VERSION)) {
                if (bytesAreForType(bytes, FirmwareVersion.TYPE)) {
                    command = new FirmwareVersion(bytes);
                } else if (bytesAreForType(bytes, GetFirmwareVersion.TYPE)) {
                    command = new GetFirmwareVersion(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.RACE)) {
                if (bytesAreForType(bytes, RaceState.TYPE)) {
                    command = new RaceState(bytes);
                } else if (bytesAreForType(bytes, GetRaceState.TYPE)) {
                    command = new GetRaceState(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.OFF_ROAD)) {
                if (bytesAreForType(bytes, OffroadState.TYPE)) {
                    command = new OffroadState(bytes);
                } else if (bytesAreForType(bytes, GetOffroadState.TYPE)) {
                    command = new GetOffroadState(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.CHASSIS_SETTINGS)) {
                if (bytesAreForType(bytes, ChassisSettings.TYPE)) {
                    command = new ChassisSettings(bytes);
                } else if (bytesAreForType(bytes, GetChassisSettings.TYPE)) {
                    command = new GetChassisSettings(bytes);
                } else if (bytesAreForType(bytes, SetChassisPosition.TYPE)) {
                    command = new SetChassisPosition(bytes);
                } else if (bytesAreForType(bytes, SetDrivingMode.TYPE)) {
                    command = new SetDrivingMode(bytes);
                } else if (bytesAreForType(bytes, SetSpringRate.TYPE)) {
                    command = new SetSpringRate(bytes);
                } else if (bytesAreForType(bytes, StartStopSportChrono.TYPE)) {
                    command = new StartStopSportChrono(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.SEATS)) {
                if (bytesAreForType(bytes, SeatsState.TYPE)) {
                    command = new SeatsState(bytes);
                } else if (bytesAreForType(bytes, GetSeatsState.TYPE)) {
                    command = new GetSeatsState(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.PARKING_BRAKE)) {
                if (bytesAreForType(bytes, ParkingBrakeState.TYPE)) {
                    command = new ParkingBrakeState(bytes);
                } else if (bytesAreForType(bytes, GetParkingBrakeState.TYPE)) {
                    command = new GetParkingBrakeState(bytes);
                } else if (bytesAreForType(bytes, ActivateInactivateParkingBrake.TYPE)) {
                    command = new ActivateInactivateParkingBrake(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.LIGHT_CONDITIONS)) {
                if (bytesAreForType(bytes, LightConditions.TYPE)) {
                    command = new LightConditions(bytes);
                } else if (bytesAreForType(bytes, GetLightConditions.TYPE)) {
                    command = new GetLightConditions(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.WEATHER_CONDITIONS)) {
                if (bytesAreForType(bytes, WeatherConditions.TYPE)) {
                    command = new WeatherConditions(bytes);
                } else if (bytesAreForType(bytes, GetWeatherConditions.TYPE)) {
                    command = new GetWeatherConditions(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.WIFI)) {
                if (bytesAreForType(bytes, WifiState.TYPE)) {
                    command = new WifiState(bytes);
                } else if (bytesAreForType(bytes, GetWifiState.TYPE)) {
                    command = new GetWifiState(bytes);
                } else if (bytesAreForType(bytes, ConnectToNetwork.TYPE)) {
                    command = new ConnectToNetwork(bytes);
                } else if (bytesAreForType(bytes, ForgetNetwork.TYPE)) {
                    command = new ForgetNetwork(bytes);
                } else if (bytesAreForType(bytes, EnableDisableWifi.TYPE)) {
                    command = new EnableDisableWifi(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.HOME_CHARGER)) {
                if (bytesAreForType(bytes, GetHomeChargerState.TYPE)) {
                    command = new GetHomeChargerState(bytes);
                } else if (bytesAreForType(bytes, HomeChargerState.TYPE)) {
                    command = new HomeChargerState(bytes);
                } else if (bytesAreForType(bytes, ActivateDeactivateSolarCharging.TYPE)) {
                    command = new ActivateDeactivateSolarCharging(bytes);
                } else if (bytesAreForType(bytes, EnableDisableWifiHotspot.TYPE)) {
                    command = new EnableDisableWifiHotspot(bytes);
                } else if (bytesAreForType(bytes, SetChargeCurrent.TYPE)) {
                    command = new SetChargeCurrent(bytes);
                } else if (bytesAreForType(bytes, SetPriceTariffs.TYPE)) {
                    command = new SetPriceTariffs(bytes);
                } else if (bytesAreForType(bytes, AuthenticateHomeCharger.TYPE)) {
                    command = new AuthenticateHomeCharger(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.DASHBOARD_LIGHTS)) {
                if (bytesAreForType(bytes, DashboardLights.TYPE)) {
                    command = new DashboardLights(bytes);
                } else if (bytesAreForType(bytes, GetDashboardLights.TYPE)) {
                    command = new GetDashboardLights(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.START_STOP)) {
                if (bytesAreForType(bytes, GetStartStopState.TYPE)) {
                    command = new GetStartStopState(bytes);
                } else if (bytesAreForType(bytes, StartStopState.TYPE)) {
                    command = new StartStopState(bytes);
                } else if (bytesAreForType(bytes, ActivateDeactivateStartStop.TYPE)) {
                    command = new ActivateDeactivateStartStop(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.CRUISE_CONTROL)) {
                if (bytesAreForType(bytes, GetCruiseControlState.TYPE)) {
                    command = new GetCruiseControlState(bytes);
                } else if (bytesAreForType(bytes, CruiseControlState.TYPE)) {
                    command = new CruiseControlState(bytes);
                } else if (bytesAreForType(bytes, ActivateDeactivateCruiseControl.TYPE)) {
                    command = new ActivateDeactivateCruiseControl(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.POWER_TAKE_OFF)) {
                if (bytesAreForType(bytes, GetPowerTakeOffState.TYPE)) {
                    command = new GetPowerTakeOffState(bytes);
                } else if (bytesAreForType(bytes, PowerTakeOffState.TYPE)) {
                    command = new PowerTakeOffState(bytes);
                } else if (bytesAreForType(bytes, ActivateDeactivatePowerTakeoff.TYPE)) {
                    command = new ActivateDeactivatePowerTakeoff(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.TACHOGRAPH)) {
                if (bytesAreForType(bytes, TachographState.TYPE)) {
                    command = new TachographState(bytes);
                } else if (bytesAreForType(bytes, GetTachographState.TYPE)) {
                    command = new GetTachographState(bytes);
                }
            } else if (bytesAreForType(bytes, LoadUrl.TYPE)) {
                command = new LoadUrl(bytes);
            } else if (bytesAreForType(bytes, VideoHandover.TYPE)) {
                command = new VideoHandover(bytes);
            } else if (bytesAreForType(bytes, DriverFatigueDetected.TYPE)) {
                command = new DriverFatigueDetected(bytes);
            } else if (bytesAreForType(bytes, WakeUp.TYPE)) {
                command = new WakeUp(bytes);
            } else if (bytesAreForType(bytes, DisplayImage.TYPE)) {
                command = new DisplayImage(bytes);
            } else if (bytesAreForType(bytes, TextInput.TYPE)) {
                command = new TextInput(bytes);
            } else if (bytesAreForIdentifier(bytes, Identifier.USAGE)) {
                if (bytesAreForType(bytes, GetUsage.TYPE)) {
                    command = new GetUsage(bytes);
                } else if (bytesAreForType(bytes, Usage.TYPE)) {
                    command = new Usage(bytes);
                }
            } else if (bytesAreForIdentifier(bytes, Identifier.MOBILE)) {
                if (bytesAreForType(bytes, GetMobileState.TYPE)) {
                    command = new GetMobileState(bytes);
                } else if (bytesAreForType(bytes, MobileState.TYPE)) {
                    command = new MobileState(bytes);
                }
            } else {
                Command.logger.info("Unknown command " + ByteUtils.hexFromBytes(ByteUtils
                        .trimmedBytes(bytes, 3)) + ".. ");
                command = new Command(bytes);
            }
        } catch (Exception e) {
            // the identifier is known but the command's parser class threw an exception.
            // return the base class.
            Command.logger.info("Failed to parse command " + ByteUtils.hexFromBytes(ByteUtils
                    .trimmedBytes(bytes, 3)) + ".. " + e.toString());
        }

        // The identifier was unknown. Return the base class.
        if (command == null) command = new Command(bytes);

        return command;
    }

    /**
     * Try to parse the command bytes to a more specific Command subclass. Check the returned
     * object's instance type (instanceOf) to understand which command was received.
     *
     * @param base64 the raw command bytes in base64.
     * @return The parsed command.
     */
    public static Command resolveBase64(String base64) {
        return resolve(Base64.decode(base64));
    }

    /**
     * Try to parse the command bytes to a more specific Command subclass. Check the returned
     * object's instance type (instanceOf) to understand which command was received.
     *
     * @param hexBytes the raw command bytes in hex.
     * @return The parsed command.
     */
    public static Command resolveHex(String hexBytes) {
        return resolve(ByteUtils.bytesFromHex(hexBytes));
    }

    /**
     * Try to parse the command bytes to a more specific Command subclass. Check the returned
     * object's instance type (instanceOf) to understand which command was received.
     *
     * @param value the raw command bytes in hex or base64.
     * @return The parsed command.
     */
    public static Command resolve(String value) {
        return resolve(new Bytes(value));
    }

    static boolean bytesAreForIdentifier(byte[] bytes, Identifier identifier) {
        return bytes[0] == identifier.getBytes()[0]
                && bytes[1] == identifier.getBytes()[1];
    }

    static boolean bytesAreForType(byte[] bytes, Type type) {
        byte[] identifierAndType = type.getIdentifierAndType();
        return bytes[0] == identifierAndType[0]
                && bytes[1] == identifierAndType[1]
                && bytes[2] == identifierAndType[2];
    }
}
