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
import static com.highmobility.autoapi.Identifier.*;
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
        if (bytes == null || bytes.length < 3) return new Command(bytes);

        Command command = null;
        Integer identifier = Identifier.fromBytes(bytes[0], bytes[1]);
        Integer type = Type.fromByte(bytes[2]);

        try {
            switch (identifier) {
                case VEHICLE_STATUS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new VehicleStatusState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetVehicleStatus(bytes);
                        } else {
                            command = new GetVehicleStatusProperties(bytes);
                        }
                    }
                    break;
                }
                case PARKING_TICKET: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new ParkingTicketState(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(3);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new StartParking(bytes);
                                        case 1:
                                            return new EndParking(bytes);
                                        case 2:
                                            return new ParkingTicketState(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetParkingTicket(bytes);
                        } else {
                            command = new GetParkingTicketProperties(bytes);
                        }
                    }
                    break;
                }
                case BROWSER: {
                    command = new LoadUrl(bytes);
                    break;
                }
                case WINDOWS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new WindowsState(bytes);
                        } else {
                            command = new ControlWindows(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetWindows(bytes);
                        } else {
                            command = new GetWindowsProperties(bytes);
                        }
                    }
                    break;
                }
                case VEHICLE_TIME: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new VehicleTimeState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetVehicleTime(bytes);
                        }
                    }
                    break;
                }
                case DRIVER_FATIGUE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new DriverFatigueState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetDriverFatigueState(bytes);
                        }
                    }
                    break;
                }
                case REMOTE_CONTROL: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new RemoteControlState(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(4);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new ControlCommand(bytes);
                                        case 1:
                                            return new StartControl(bytes);
                                        case 2:
                                            return new StopControl(bytes);
                                        case 3:
                                            return new RemoteControlState(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetControlState(bytes);
                        }
                    }
                    break;
                }
                case FUELING: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new FuelingState(bytes);
                        } else {
                            command = new ControlGasFlap(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetGasFlapState(bytes);
                        } else {
                            command = new GetGasFlapProperties(bytes);
                        }
                    }
                    break;
                }
                case NAVI_DESTINATION: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new NaviDestinationState(bytes);
                        } else {
                            command = new SetNaviDestination(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetNaviDestination(bytes);
                        } else {
                            command = new GetNaviDestinationProperties(bytes);
                        }
                    }
                    break;
                }
                case LIGHT_CONDITIONS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new LightConditionsState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetLightConditions(bytes);
                        } else {
                            command = new GetLightConditionsProperties(bytes);
                        }
                    }
                    break;
                }
                case OFFROAD: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new OffroadState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetOffroadState(bytes);
                        } else {
                            command = new GetOffroadProperties(bytes);
                        }
                    }
                    break;
                }
                case TRUNK: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new TrunkState(bytes);
                        } else {
                            command = new ControlTrunk(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetTrunkState(bytes);
                        } else {
                            command = new GetTrunkProperties(bytes);
                        }
                    }
                    break;
                }
                case DOORS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new DoorsState(bytes);
                        } else {
                            command = new LockUnlockDoors(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetDoorsState(bytes);
                        } else {
                            command = new GetDoorsProperties(bytes);
                        }
                    }
                    break;
                }
                case VALET_MODE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new ValetModeState(bytes);
                        } else {
                            command = new ActivateDeactivateValetMode(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetValetMode(bytes);
                        }
                    }
                    break;
                }
                case DASHBOARD_LIGHTS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new DashboardLightsState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetDashboardLights(bytes);
                        }
                    }
                    break;
                }
                case MULTI_COMMAND: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new MultiCommandState(bytes);
                        } else {
                            command = new MultiCommand(bytes);
                        }
                    }
                    break;
                }
                case TEXT_INPUT: {
                    command = new TextInput(bytes);
                    break;
                }
                case LIGHTS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new LightsState(bytes);
                        } else {
                            command = new ControlLights(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetLightsState(bytes);
                        } else {
                            command = new GetLightsProperties(bytes);
                        }
                    }
                    break;
                }
                case CHASSIS_SETTINGS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new ChassisSettingsState(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(5);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new SetDrivingMode(bytes);
                                        case 1:
                                            return new StartStopSportsChrono(bytes);
                                        case 2:
                                            return new SetSpringRates(bytes);
                                        case 3:
                                            return new SetChassisPosition(bytes);
                                        case 4:
                                            return new ChassisSettingsState(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetChassisSettings(bytes);
                        } else {
                            command = new GetChassisSettingsProperties(bytes);
                        }
                    }
                    break;
                }
                case NOTIFICATIONS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new NotificationsState(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(4);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new Notification(bytes);
                                        case 1:
                                            return new Action(bytes);
                                        case 2:
                                            return new ClearNotification(bytes);
                                        case 3:
                                            return new NotificationsState(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    }
                    break;
                }
                case HOOD: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new HoodState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetHoodState(bytes);
                        }
                    }
                    break;
                }
                case CHARGING: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new ChargingState(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(7);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new StartStopCharging(bytes);
                                        case 1:
                                            return new SetChargeLimit(bytes);
                                        case 2:
                                            return new OpenCloseChargingPort(bytes);
                                        case 3:
                                            return new SetChargeMode(bytes);
                                        case 4:
                                            return new SetChargingTimers(bytes);
                                        case 5:
                                            return new SetReductionOfChargingCurrentTimes(bytes);
                                        case 6:
                                            return new ChargingState(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetChargingState(bytes);
                        } else {
                            command = new GetChargingProperties(bytes);
                        }
                    }
                    break;
                }
                case MOBILE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new MobileState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetMobileState(bytes);
                        }
                    }
                    break;
                }
                case HOME_CHARGER: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new HomeChargerState(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(6);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new SetChargeCurrent(bytes);
                                        case 1:
                                            return new SetPriceTariffs(bytes);
                                        case 2:
                                            return new ActivateDeactivateSolarCharging(bytes);
                                        case 3:
                                            return new EnableDisableWiFiHotspot(bytes);
                                        case 4:
                                            return new AuthenticateExpire(bytes);
                                        case 5:
                                            return new HomeChargerState(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetHomeChargerState(bytes);
                        } else {
                            command = new GetHomeChargerProperties(bytes);
                        }
                    }
                    break;
                }
                case DIAGNOSTICS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new DiagnosticsState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetDiagnosticsState(bytes);
                        } else {
                            command = new GetDiagnosticsProperties(bytes);
                        }
                    }
                    break;
                }
                case USAGE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new UsageState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetUsage(bytes);
                        } else {
                            command = new GetUsageProperties(bytes);
                        }
                    }
                    break;
                }
                case POWER_TAKEOFF: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new PowerTakeoffState(bytes);
                        } else {
                            command = new ActivateDeactivatePowerTakeoff(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetPowerTakeoffState(bytes);
                        } else {
                            command = new GetPowerTakeoffProperties(bytes);
                        }
                    }
                    break;
                }
                case WAKE_UP: {
                    command = new WakeUp(bytes);
                    break;
                }
                case VIDEO_HANDOVER: {
                    command = new VideoHandover(bytes);
                    break;
                }
                case HISTORICAL: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new HistoricalState(bytes);
                        } else {
                            command = new RequestStates(bytes);
                        }
                    }
                    break;
                }
                case WI_FI: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new WiFiState(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(4);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new ConnectToNetwork(bytes);
                                        case 1:
                                            return new ForgetNetwork(bytes);
                                        case 2:
                                            return new EnableDisableWiFi(bytes);
                                        case 3:
                                            return new WiFiState(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetWiFiState(bytes);
                        } else {
                            command = new GetWiFiProperties(bytes);
                        }
                    }
                    break;
                }
                case VEHICLE_LOCATION: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new VehicleLocationState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetVehicleLocation(bytes);
                        } else {
                            command = new GetVehicleLocationProperties(bytes);
                        }
                    }
                    break;
                }
                case HEART_RATE: {
                    command = new SendHeartRate(bytes);
                    break;
                }
                case GRAPHICS: {
                    command = new DisplayImage(bytes);
                    break;
                }
                case RACE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new RaceState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetRaceState(bytes);
                        } else {
                            command = new GetRaceProperties(bytes);
                        }
                    }
                    break;
                }
                case FIRMWARE_VERSION: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new FirmwareVersionState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetFirmwareVersion(bytes);
                        } else {
                            command = new GetFirmwareVersionProperties(bytes);
                        }
                    }
                    break;
                }
                case THEFT_ALARM: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new TheftAlarmState(bytes);
                        } else {
                            command = new SetTheftAlarm(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetTheftAlarmState(bytes);
                        }
                    }
                    break;
                }
                case SEATS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new SeatsState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetSeatsState(bytes);
                        } else {
                            command = new GetSeatsProperties(bytes);
                        }
                    }
                    break;
                }
                case ENGINE_START_STOP: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new EngineStartStopState(bytes);
                        } else {
                            command = new ActivateDeactivateStartStop(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetEngineStartStopState(bytes);
                        }
                    }
                    break;
                }
                case TACHOGRAPH: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new TachographState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetTachographState(bytes);
                        } else {
                            command = new GetTachographProperties(bytes);
                        }
                    }
                    break;
                }
                case PARKING_BRAKE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new ParkingBrakeState(bytes);
                        } else {
                            command = new SetParkingBrake(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetParkingBrakeState(bytes);
                        }
                    }
                    break;
                }
                case CAPABILITIES: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new CapabilitiesState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetCapabilities(bytes);
                        }
                    }
                    break;
                }
                case MAINTENANCE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new MaintenanceState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetMaintenanceState(bytes);
                        } else {
                            command = new GetMaintenanceProperties(bytes);
                        }
                    }
                    break;
                }
                case ROOFTOP_CONTROL: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new RooftopControlState(bytes);
                        } else {
                            command = new ControlRooftop(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetRooftopState(bytes);
                        } else {
                            command = new GetRooftopProperties(bytes);
                        }
                    }
                    break;
                }
                case FAILURE_MESSAGE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new FailureMessageState(bytes);
                        }
                    }    break;
                }
                case WINDSCREEN: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new WindscreenState(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(4);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new SetWindscreenDamage(bytes);
                                        case 1:
                                            return new SetWindscreenReplacementNeeded(bytes);
                                        case 2:
                                            return new ControlWipers(bytes);
                                        case 3:
                                            return new WindscreenState(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetWindscreenState(bytes);
                        } else {
                            command = new GetWindscreenProperties(bytes);
                        }
                    }
                    break;
                }
                case CRUISE_CONTROL: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new CruiseControlState(bytes);
                        } else {
                            command = new ActivateDeactivateCruiseControl(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetCruiseControlState(bytes);
                        } else {
                            command = new GetCruiseControlProperties(bytes);
                        }
                    }
                    break;
                }
                case KEYFOB_POSITION: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new KeyfobPositionState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetKeyfobPosition(bytes);
                        }
                    }
                    break;
                }
                case HONK_HORN_FLASH_LIGHTS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new HonkHornFlashLightsState(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(3);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new HonkFlash(bytes);
                                        case 1:
                                            return new ActivateDeactivateEmergencyFlasher(bytes);
                                        case 2:
                                            return new HonkHornFlashLightsState(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetFlashersState(bytes);
                        } else {
                            command = new GetFlashersProperties(bytes);
                        }
                    }
                    break;
                }
                case WEATHER_CONDITIONS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new WeatherConditionsState(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetWeatherConditions(bytes);
                        }
                    }
                    break;
                }
                case MESSAGING: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new MessagingState(bytes);
                        } else {
                            command = new MessageReceived(bytes);
                        }
                    }
                    break;
                }
                case IGNITION: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new IgnitionState(bytes);
                        } else {
                            command = new TurnIgnitionOnOff(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetIgnitionState(bytes);
                        } else {
                            command = new GetIgnitionProperties(bytes);
                        }
                    }
                    break;
                }
                case CLIMATE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new ClimateState(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(7);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new ChangeStartingTimes(bytes);
                                        case 1:
                                            return new StartStopHvac(bytes);
                                        case 2:
                                            return new StartStopDefogging(bytes);
                                        case 3:
                                            return new StartStopDefrosting(bytes);
                                        case 4:
                                            return new StartStopIonising(bytes);
                                        case 5:
                                            return new SetTemperatureSettings(bytes);
                                        case 6:
                                            return new ClimateState(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == 3) {
                            command = new GetClimateState(bytes);
                        } else {
                            command = new GetClimateProperties(bytes);
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            // the identifier is known but the command's parser class threw an exception.
            // return the base class.
            Command.logger.error("Failed to parse command {}", commandToString(bytes), e);
        }

        // The identifier was unknown or failed to parse. Return the base class.
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

    private static String commandToString(byte[] bytes) {
        return ByteUtils.hexFromBytes(ByteUtils
                .trimmedBytes(bytes, bytes.length >= 3 ? 3 : bytes.length));
    }

    static RunTime _runtime;

    static RunTime getRuntime() {
        if (_runtime == null)
            _runtime = (System.getProperty("java.runtime.name") == "Android Runtime") ?
                RunTime.ANDROID : RunTime.JAVA;
            return _runtime;
    }

    enum RunTime {
        ANDROID, JAVA
    }


    /**
     * The purpose is to loop the possible setters.
     * <p>
     * NoPropertiesException is ok when parsing Setters because we are in the process of trying to
     * find correct setter.
     */
    protected static class SetterIterator {
        private int currentSize;
        private int currentIndex = 0;
        public Command theParsedCommand;

        SetterIterator(int count) {
            this.currentSize = count;
        }

        public boolean hasNext() {
            boolean hasNext = currentIndex < currentSize && theParsedCommand == null;
            return hasNext;
        }

        public Command parseNext(PropertyIteration next) throws CommandParseException {
            try {
                theParsedCommand = next.iterate(currentIndex);
            } catch (NoPropertiesException e) {
                // its ok, we are trying to find the command
            }
            currentIndex++;
            return theParsedCommand;
        }

        public interface PropertyIteration {
            Command iterate(int number) throws NoPropertiesException, CommandParseException;
        }
    }
}