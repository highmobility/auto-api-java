/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.highmobility.autoapi;

import com.highmobility.utils.Base64;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;
import static com.highmobility.autoapi.Identifier.*;
public class CommandResolver {
    private static final int GET_STATE_LENGTH = Command.HEADER_LENGTH + 3;
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
        if (bytes == null || bytes.length < 3 + Command.HEADER_LENGTH) return new Command(bytes);

        Command command = null;
        Integer identifier = Identifier.fromBytes(bytes[Command.HEADER_LENGTH], bytes[Command.HEADER_LENGTH + 1]);
        Integer type = Type.fromByte(bytes[Command.HEADER_LENGTH + 2]);

        try {
            switch (identifier) {
                case VEHICLE_STATUS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new VehicleStatus.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new VehicleStatus.GetVehicleStatus(bytes);
                        } else {
                            command = new VehicleStatus.GetVehicleStatusProperties(bytes);
                        }
                    }
                    break;
                }
                case PARKING_TICKET: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new ParkingTicket.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(3);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new ParkingTicket.StartParking(bytes);
                                        case 1:
                                            return new ParkingTicket.EndParking(bytes);
                                        case 2:
                                            return new ParkingTicket.State(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new ParkingTicket.GetParkingTicket(bytes);
                        } else {
                            command = new ParkingTicket.GetParkingTicketProperties(bytes);
                        }
                    }
                    break;
                }
                case BROWSER: {
                    command = new Browser.LoadUrl(bytes);
                    break;
                }
                case WINDOWS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Windows.State(bytes);
                        } else {
                            command = new Windows.ControlWindows(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Windows.GetWindows(bytes);
                        } else {
                            command = new Windows.GetWindowsProperties(bytes);
                        }
                    }
                    break;
                }
                case VEHICLE_TIME: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new VehicleTime.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new VehicleTime.GetVehicleTime(bytes);
                        }
                    }
                    break;
                }
                case DRIVER_FATIGUE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new DriverFatigue.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new DriverFatigue.GetState(bytes);
                        }
                    }
                    break;
                }
                case REMOTE_CONTROL: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new RemoteControl.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(4);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new RemoteControl.ControlCommand(bytes);
                                        case 1:
                                            return new RemoteControl.StartControl(bytes);
                                        case 2:
                                            return new RemoteControl.StopControl(bytes);
                                        case 3:
                                            return new RemoteControl.State(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new RemoteControl.GetControlState(bytes);
                        }
                    }
                    break;
                }
                case FUELING: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Fueling.State(bytes);
                        } else {
                            command = new Fueling.ControlGasFlap(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Fueling.GetGasFlapState(bytes);
                        } else {
                            command = new Fueling.GetGasFlapProperties(bytes);
                        }
                    }
                    break;
                }
                case NAVI_DESTINATION: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new NaviDestination.State(bytes);
                        } else {
                            command = new NaviDestination.SetNaviDestination(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new NaviDestination.GetNaviDestination(bytes);
                        } else {
                            command = new NaviDestination.GetNaviDestinationProperties(bytes);
                        }
                    }
                    break;
                }
                case LIGHT_CONDITIONS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new LightConditions.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new LightConditions.GetLightConditions(bytes);
                        } else {
                            command = new LightConditions.GetLightConditionsProperties(bytes);
                        }
                    }
                    break;
                }
                case OFFROAD: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Offroad.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Offroad.GetState(bytes);
                        } else {
                            command = new Offroad.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case TRUNK: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Trunk.State(bytes);
                        } else {
                            command = new Trunk.ControlTrunk(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Trunk.GetState(bytes);
                        } else {
                            command = new Trunk.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case DOORS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Doors.State(bytes);
                        } else {
                            command = new Doors.LockUnlockDoors(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Doors.GetState(bytes);
                        } else {
                            command = new Doors.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case VALET_MODE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new ValetMode.State(bytes);
                        } else {
                            command = new ValetMode.ActivateDeactivateValetMode(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new ValetMode.GetValetMode(bytes);
                        }
                    }
                    break;
                }
                case DASHBOARD_LIGHTS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new DashboardLights.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new DashboardLights.GetDashboardLights(bytes);
                        }
                    }
                    break;
                }
                case MULTI_COMMAND: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new MultiCommand.State(bytes);
                        } else {
                            command = new MultiCommand.MultiCommandCommand(bytes);
                        }
                    }
                    break;
                }
                case TEXT_INPUT: {
                    command = new TextInput.TextInputCommand(bytes);
                    break;
                }
                case LIGHTS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Lights.State(bytes);
                        } else {
                            command = new Lights.ControlLights(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Lights.GetState(bytes);
                        } else {
                            command = new Lights.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case CHASSIS_SETTINGS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new ChassisSettings.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(5);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new ChassisSettings.SetDrivingMode(bytes);
                                        case 1:
                                            return new ChassisSettings.StartStopSportsChrono(bytes);
                                        case 2:
                                            return new ChassisSettings.SetSpringRates(bytes);
                                        case 3:
                                            return new ChassisSettings.SetChassisPosition(bytes);
                                        case 4:
                                            return new ChassisSettings.State(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new ChassisSettings.GetChassisSettings(bytes);
                        } else {
                            command = new ChassisSettings.GetChassisSettingsProperties(bytes);
                        }
                    }
                    break;
                }
                case NOTIFICATIONS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Notifications.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(4);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new Notifications.Notification(bytes);
                                        case 1:
                                            return new Notifications.Action(bytes);
                                        case 2:
                                            return new Notifications.ClearNotification(bytes);
                                        case 3:
                                            return new Notifications.State(bytes);
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
                            command = new Hood.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Hood.GetState(bytes);
                        }
                    }
                    break;
                }
                case CHARGING: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Charging.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(7);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new Charging.StartStopCharging(bytes);
                                        case 1:
                                            return new Charging.SetChargeLimit(bytes);
                                        case 2:
                                            return new Charging.OpenCloseChargingPort(bytes);
                                        case 3:
                                            return new Charging.SetChargeMode(bytes);
                                        case 4:
                                            return new Charging.SetChargingTimers(bytes);
                                        case 5:
                                            return new Charging.SetReductionOfChargingCurrentTimes(bytes);
                                        case 6:
                                            return new Charging.State(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Charging.GetState(bytes);
                        } else {
                            command = new Charging.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case MOBILE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Mobile.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Mobile.GetState(bytes);
                        }
                    }
                    break;
                }
                case HOME_CHARGER: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new HomeCharger.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(6);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new HomeCharger.SetChargeCurrent(bytes);
                                        case 1:
                                            return new HomeCharger.SetPriceTariffs(bytes);
                                        case 2:
                                            return new HomeCharger.ActivateDeactivateSolarCharging(bytes);
                                        case 3:
                                            return new HomeCharger.EnableDisableWiFiHotspot(bytes);
                                        case 4:
                                            return new HomeCharger.AuthenticateExpire(bytes);
                                        case 5:
                                            return new HomeCharger.State(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new HomeCharger.GetState(bytes);
                        } else {
                            command = new HomeCharger.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case DIAGNOSTICS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Diagnostics.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Diagnostics.GetState(bytes);
                        } else {
                            command = new Diagnostics.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case USAGE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Usage.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Usage.GetUsage(bytes);
                        } else {
                            command = new Usage.GetUsageProperties(bytes);
                        }
                    }
                    break;
                }
                case POWER_TAKEOFF: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new PowerTakeoff.State(bytes);
                        } else {
                            command = new PowerTakeoff.ActivateDeactivatePowerTakeoff(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new PowerTakeoff.GetState(bytes);
                        } else {
                            command = new PowerTakeoff.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case WAKE_UP: {
                    command = new WakeUp.WakeUpCommand(bytes);
                    break;
                }
                case VIDEO_HANDOVER: {
                    command = new VideoHandover.VideoHandoverCommand(bytes);
                    break;
                }
                case HISTORICAL: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Historical.State(bytes);
                        } else {
                            command = new Historical.RequestStates(bytes);
                        }
                    }
                    break;
                }
                case WI_FI: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new WiFi.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(4);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new WiFi.ConnectToNetwork(bytes);
                                        case 1:
                                            return new WiFi.ForgetNetwork(bytes);
                                        case 2:
                                            return new WiFi.EnableDisableWiFi(bytes);
                                        case 3:
                                            return new WiFi.State(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new WiFi.GetState(bytes);
                        } else {
                            command = new WiFi.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case VEHICLE_LOCATION: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new VehicleLocation.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new VehicleLocation.GetVehicleLocation(bytes);
                        } else {
                            command = new VehicleLocation.GetVehicleLocationProperties(bytes);
                        }
                    }
                    break;
                }
                case HEART_RATE: {
                    command = new HeartRate.SendHeartRate(bytes);
                    break;
                }
                case GRAPHICS: {
                    command = new Graphics.DisplayImage(bytes);
                    break;
                }
                case RACE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Race.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Race.GetState(bytes);
                        } else {
                            command = new Race.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case FIRMWARE_VERSION: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new FirmwareVersion.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new FirmwareVersion.GetFirmwareVersion(bytes);
                        } else {
                            command = new FirmwareVersion.GetFirmwareVersionProperties(bytes);
                        }
                    }
                    break;
                }
                case THEFT_ALARM: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new TheftAlarm.State(bytes);
                        } else {
                            command = new TheftAlarm.SetTheftAlarm(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new TheftAlarm.GetState(bytes);
                        }
                    }
                    break;
                }
                case SEATS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Seats.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Seats.GetState(bytes);
                        } else {
                            command = new Seats.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case ENGINE_START_STOP: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new EngineStartStop.State(bytes);
                        } else {
                            command = new EngineStartStop.ActivateDeactivateStartStop(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new EngineStartStop.GetState(bytes);
                        }
                    }
                    break;
                }
                case TACHOGRAPH: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Tachograph.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Tachograph.GetState(bytes);
                        } else {
                            command = new Tachograph.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case PARKING_BRAKE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new ParkingBrake.State(bytes);
                        } else {
                            command = new ParkingBrake.SetParkingBrake(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new ParkingBrake.GetState(bytes);
                        }
                    }
                    break;
                }
                case CAPABILITIES: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Capabilities.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Capabilities.GetCapabilities(bytes);
                        }
                    }
                    break;
                }
                case MAINTENANCE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Maintenance.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Maintenance.GetState(bytes);
                        } else {
                            command = new Maintenance.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case ROOFTOP_CONTROL: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new RooftopControl.State(bytes);
                        } else {
                            command = new RooftopControl.ControlRooftop(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new RooftopControl.GetRooftopState(bytes);
                        } else {
                            command = new RooftopControl.GetRooftopProperties(bytes);
                        }
                    }
                    break;
                }
                case FAILURE_MESSAGE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new FailureMessage.State(bytes);
                        }
                    }    break;
                }
                case WINDSCREEN: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Windscreen.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(4);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new Windscreen.SetWindscreenDamage(bytes);
                                        case 1:
                                            return new Windscreen.SetWindscreenReplacementNeeded(bytes);
                                        case 2:
                                            return new Windscreen.ControlWipers(bytes);
                                        case 3:
                                            return new Windscreen.State(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Windscreen.GetState(bytes);
                        } else {
                            command = new Windscreen.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case CRUISE_CONTROL: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new CruiseControl.State(bytes);
                        } else {
                            command = new CruiseControl.ActivateDeactivateCruiseControl(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new CruiseControl.GetState(bytes);
                        } else {
                            command = new CruiseControl.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case KEYFOB_POSITION: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new KeyfobPosition.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new KeyfobPosition.GetKeyfobPosition(bytes);
                        }
                    }
                    break;
                }
                case HONK_HORN_FLASH_LIGHTS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new HonkHornFlashLights.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(3);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new HonkHornFlashLights.HonkFlash(bytes);
                                        case 1:
                                            return new HonkHornFlashLights.ActivateDeactivateEmergencyFlasher(bytes);
                                        case 2:
                                            return new HonkHornFlashLights.State(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new HonkHornFlashLights.GetFlashersState(bytes);
                        } else {
                            command = new HonkHornFlashLights.GetFlashersProperties(bytes);
                        }
                    }
                    break;
                }
                case ENGINE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Engine.State(bytes);
                        } else {
                            command = new Engine.TurnEngineOnOff(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Engine.GetState(bytes);
                        }
                    }
                    break;
                }
                case WEATHER_CONDITIONS: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new WeatherConditions.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new WeatherConditions.GetWeatherConditions(bytes);
                        }
                    }
                    break;
                }
                case MESSAGING: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Messaging.State(bytes);
                        } else {
                            command = new Messaging.MessageReceived(bytes);
                        }
                    }
                    break;
                }
                case IGNITION: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Ignition.State(bytes);
                        } else {
                            command = new Ignition.TurnIgnitionOnOff(bytes);
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Ignition.GetState(bytes);
                        } else {
                            command = new Ignition.GetProperties(bytes);
                        }
                    }
                    break;
                }
                case CLIMATE: {
                    if (type == Type.SET) {
                        if (getRuntime() == RunTime.ANDROID) {
                            command = new Climate.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(7);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new Climate.ChangeStartingTimes(bytes);
                                        case 1:
                                            return new Climate.StartStopHvac(bytes);
                                        case 2:
                                            return new Climate.StartStopDefogging(bytes);
                                        case 3:
                                            return new Climate.StartStopDefrosting(bytes);
                                        case 4:
                                            return new Climate.StartStopIonising(bytes);
                                        case 5:
                                            return new Climate.SetTemperatureSettings(bytes);
                                        case 6:
                                            return new Climate.State(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        if (bytes.length == GET_STATE_LENGTH) {
                            command = new Climate.GetState(bytes);
                        } else {
                            command = new Climate.GetProperties(bytes);
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
            _runtime = (System.getProperty("java.runtime.name").equals("Android Runtime")) ?
                RunTime.ANDROID : RunTime.JAVA;
            return _runtime;
    }

    /**
     * Override the runtime.
     * <p>
     * Some commands are disabled in Android/Desktop environments. Use this method to override the runtime.
     * </p>
     *
     * @param runtime The runtime.
     */
    public static void setRuntime(RunTime runtime) {
        _runtime = runtime;
    }

    public enum RunTime {
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
            return currentIndex < currentSize && theParsedCommand == null;
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