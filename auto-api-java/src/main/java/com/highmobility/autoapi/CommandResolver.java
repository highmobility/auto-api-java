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

import static com.highmobility.autoapi.AutoApiLogger.getLogger;
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
        if (bytes == null || bytes.length < 3 + Command.HEADER_LENGTH) return new Command(bytes);

        Command command = null;
        Integer identifier = Identifier.fromBytes(bytes[Command.HEADER_LENGTH], bytes[Command.HEADER_LENGTH + 1]);
        Integer type = Type.fromByte(bytes[Command.HEADER_LENGTH + 2]);

        try {
            switch (identifier) {
                case VEHICLE_STATUS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new VehicleStatus.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new VehicleStatus.GetVehicleStatus(bytes, true);
                    }
                    break;
                }
                case PARKING_TICKET: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new ParkingTicket.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(2);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new ParkingTicket.StartParking(bytes);
                                        case 1:
                                            return new ParkingTicket.EndParking(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        command = new ParkingTicket.GetParkingTicket(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new ParkingTicket.GetParkingTicketAvailability(bytes, true);
                    }
                    break;
                }
                case BROWSER: {
                    if (type == Type.SET) {
                        command = new Browser.LoadUrl(bytes);
                    }
                    break;
                }
                case WINDOWS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Windows.State(bytes);
                        } else {
                            command = new Windows.ControlWindows(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Windows.GetWindows(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Windows.GetWindowsAvailability(bytes, true);
                    }
                    break;
                }
                case VEHICLE_TIME: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new VehicleTime.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new VehicleTime.GetVehicleTime(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new VehicleTime.GetVehicleTimeAvailability(bytes, true);
                    }
                    break;
                }
                case DRIVER_FATIGUE: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new DriverFatigue.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new DriverFatigue.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new DriverFatigue.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case REMOTE_CONTROL: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new RemoteControl.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(3);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new RemoteControl.ControlCommand(bytes);
                                        case 1:
                                            return new RemoteControl.StartControl(bytes);
                                        case 2:
                                            return new RemoteControl.StopControl(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        command = new RemoteControl.GetControlState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new RemoteControl.GetControlStateAvailability(bytes, true);
                    }
                    break;
                }
                case FUELING: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Fueling.State(bytes);
                        } else {
                            command = new Fueling.ControlGasFlap(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Fueling.GetGasFlapState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Fueling.GetGasFlapStateAvailability(bytes, true);
                    }
                    break;
                }
                case NAVI_DESTINATION: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new NaviDestination.State(bytes);
                        } else {
                            command = new NaviDestination.SetNaviDestination(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new NaviDestination.GetNaviDestination(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new NaviDestination.GetNaviDestinationAvailability(bytes, true);
                    }
                    break;
                }
                case LIGHT_CONDITIONS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new LightConditions.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new LightConditions.GetLightConditions(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new LightConditions.GetLightConditionsAvailability(bytes, true);
                    }
                    break;
                }
                case OFFROAD: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Offroad.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Offroad.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Offroad.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case TRUNK: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Trunk.State(bytes);
                        } else {
                            command = new Trunk.ControlTrunk(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Trunk.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Trunk.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case DOORS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Doors.State(bytes);
                        } else {
                            command = new Doors.LockUnlockDoors(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Doors.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Doors.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case VALET_MODE: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new ValetMode.State(bytes);
                        } else {
                            command = new ValetMode.ActivateDeactivateValetMode(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new ValetMode.GetValetMode(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new ValetMode.GetValetModeAvailability(bytes, true);
                    }
                    break;
                }
                case DASHBOARD_LIGHTS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new DashboardLights.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new DashboardLights.GetDashboardLights(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new DashboardLights.GetDashboardLightsAvailability(bytes, true);
                    }
                    break;
                }
                case MULTI_COMMAND: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new MultiCommand.State(bytes);
                        } else {
                            command = new MultiCommand.MultiCommandCommand(bytes);
                        }
                    }
                    break;
                }
                case TEXT_INPUT: {
                    if (type == Type.SET) {
                        command = new TextInput.TextInputCommand(bytes);
                    }
                    break;
                }
                case LIGHTS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Lights.State(bytes);
                        } else {
                            command = new Lights.ControlLights(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Lights.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Lights.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case CHASSIS_SETTINGS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new ChassisSettings.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(4);
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
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        command = new ChassisSettings.GetChassisSettings(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new ChassisSettings.GetChassisSettingsAvailability(bytes, true);
                    }
                    break;
                }
                case NOTIFICATIONS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Notifications.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(3);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new Notifications.Notification(bytes);
                                        case 1:
                                            return new Notifications.Action(bytes);
                                        case 2:
                                            return new Notifications.ClearNotification(bytes);
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
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Hood.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Hood.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Hood.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case CHARGING: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Charging.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(6);
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
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        command = new Charging.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Charging.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case MOBILE: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Mobile.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Mobile.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Mobile.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case HOME_CHARGER: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new HomeCharger.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(5);
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
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        command = new HomeCharger.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new HomeCharger.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case DIAGNOSTICS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Diagnostics.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Diagnostics.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Diagnostics.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case USAGE: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Usage.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Usage.GetUsage(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Usage.GetUsageAvailability(bytes, true);
                    }
                    break;
                }
                case VEHICLE_INFORMATION: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new VehicleInformation.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new VehicleInformation.GetVehicleInformation(bytes, true);
                    }
                    break;
                }
                case POWER_TAKEOFF: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new PowerTakeoff.State(bytes);
                        } else {
                            command = new PowerTakeoff.ActivateDeactivatePowerTakeoff(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new PowerTakeoff.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new PowerTakeoff.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case WAKE_UP: {
                    if (type == Type.SET) {
                        command = new WakeUp.WakeUpCommand(bytes);
                    }
                    break;
                }
                case VIDEO_HANDOVER: {
                    if (type == Type.SET) {
                        command = new VideoHandover.VideoHandoverCommand(bytes);
                    }
                    break;
                }
                case HISTORICAL: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Historical.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(2);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new Historical.RequestStates(bytes);
                                        case 1:
                                            return new Historical.GetTrips(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    }
                    break;
                }
                case WI_FI: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new WiFi.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(3);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new WiFi.ConnectToNetwork(bytes);
                                        case 1:
                                            return new WiFi.ForgetNetwork(bytes);
                                        case 2:
                                            return new WiFi.EnableDisableWiFi(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        command = new WiFi.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new WiFi.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case VEHICLE_LOCATION: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new VehicleLocation.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new VehicleLocation.GetVehicleLocation(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new VehicleLocation.GetVehicleLocationAvailability(bytes, true);
                    }
                    break;
                }
                case HEART_RATE: {
                    if (type == Type.SET) {
                        command = new HeartRate.SendHeartRate(bytes);
                    }
                    break;
                }
                case GRAPHICS: {
                    if (type == Type.SET) {
                        command = new Graphics.DisplayImage(bytes);
                    }
                    break;
                }
                case RACE: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Race.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Race.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Race.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case FIRMWARE_VERSION: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new FirmwareVersion.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new FirmwareVersion.GetFirmwareVersion(bytes, true);
                    }
                    break;
                }
                case THEFT_ALARM: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new TheftAlarm.State(bytes);
                        } else {
                            command = new TheftAlarm.SetTheftAlarm(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new TheftAlarm.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new TheftAlarm.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case SEATS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Seats.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Seats.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Seats.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case TACHOGRAPH: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Tachograph.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Tachograph.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Tachograph.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case PARKING_BRAKE: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new ParkingBrake.State(bytes);
                        } else {
                            command = new ParkingBrake.SetParkingBrake(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new ParkingBrake.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new ParkingBrake.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case CAPABILITIES: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Capabilities.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Capabilities.GetCapabilities(bytes, true);
                    }
                    break;
                }
                case MAINTENANCE: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Maintenance.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Maintenance.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Maintenance.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case ROOFTOP_CONTROL: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new RooftopControl.State(bytes);
                        } else {
                            command = new RooftopControl.ControlRooftop(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new RooftopControl.GetRooftopState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new RooftopControl.GetRooftopStateAvailability(bytes, true);
                    }
                    break;
                }
                case FAILURE_MESSAGE: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new FailureMessage.State(bytes);
                        }
                    }
                    break;
                }
                case WINDSCREEN: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Windscreen.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(3);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new Windscreen.SetWindscreenDamage(bytes);
                                        case 1:
                                            return new Windscreen.SetWindscreenReplacementNeeded(bytes);
                                        case 2:
                                            return new Windscreen.ControlWipers(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        command = new Windscreen.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Windscreen.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case CRUISE_CONTROL: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new CruiseControl.State(bytes);
                        } else {
                            command = new CruiseControl.ActivateDeactivateCruiseControl(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new CruiseControl.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new CruiseControl.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case TRIPS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Trips.State(bytes);
                        }
                    }
                    break;
                }
                case KEYFOB_POSITION: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new KeyfobPosition.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new KeyfobPosition.GetKeyfobPosition(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new KeyfobPosition.GetKeyfobPositionAvailability(bytes, true);
                    }
                    break;
                }
                case HONK_HORN_FLASH_LIGHTS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new HonkHornFlashLights.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(2);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new HonkHornFlashLights.HonkFlash(bytes);
                                        case 1:
                                            return new HonkHornFlashLights.ActivateDeactivateEmergencyFlasher(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        command = new HonkHornFlashLights.GetFlashersState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new HonkHornFlashLights.GetFlashersStateAvailability(bytes, true);
                    }
                    break;
                }
                case ENGINE: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Engine.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(2);
                            while (iterator.hasNext()) {
                                command = iterator.parseNext(index -> {
                                    switch (index) {
                                        case 0:
                                            return new Engine.TurnEngineOnOff(bytes);
                                        case 1:
                                            return new Engine.ActivateDeactivateStartStop(bytes);
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        command = new Engine.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Engine.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case WEATHER_CONDITIONS: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new WeatherConditions.State(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new WeatherConditions.GetWeatherConditions(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new WeatherConditions.GetWeatherConditionsAvailability(bytes, true);
                    }
                    break;
                }
                case MESSAGING: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Messaging.State(bytes);
                        } else {
                            command = new Messaging.MessageReceived(bytes);
                        }
                    }
                    break;
                }
                case IGNITION: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Ignition.State(bytes);
                        } else {
                            command = new Ignition.TurnIgnitionOnOff(bytes);
                        }
                    } else if (type == Type.GET) {
                        command = new Ignition.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Ignition.GetStateAvailability(bytes, true);
                    }
                    break;
                }
                case CLIMATE: {
                    if (type == Type.SET) {
                        if (getEnvironment() == Environment.OWNER) {
                            command = new Climate.State(bytes);
                        } else {
                            SetterIterator iterator = new SetterIterator(6);
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
                                    }
                                    return null;
                                });
                            }
                        }
                    } else if (type == Type.GET) {
                        command = new Climate.GetState(bytes, true);
                    } else if (type == Type.GET_AVAILABILITY) {
                        command = new Climate.GetStateAvailability(bytes, true);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            // the identifier is known but the command's parser class threw an exception.
            // return the base class.
            getLogger().error(String.format("Failed to parse command %s", commandToString(bytes)), e);
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
                .trimmedBytes(bytes, Math.min(bytes.length, 3)));
    }

    /**
     * @return The runtime
     * @deprecated use {@link #getEnvironment()} instead
     */
    static RunTime getRuntime() {
        if (_environment == Environment.VEHICLE) return RunTime.JAVA;
        else return RunTime.ANDROID;
    }
    
    /**
     * Override the runtime.
     * <p>
     * Some commands are disabled in Android/Desktop environments. Use this method to override the runtime.
     * </p>
     *
     * @param runtime The runtime.
     * @deprecated use {@link Environment} instead
     */
    public static void setRuntime(RunTime runtime) {
        if (runtime == RunTime.JAVA) _environment = Environment.VEHICLE;
        else _environment = Environment.OWNER;
    }
    
    /**
     * @deprecated use {@link Environment} instead
     */
    @Deprecated
    public enum RunTime {
        ANDROID, JAVA
    }
    
    static Environment _environment = Environment.OWNER;
    
    /**
     * @return The environment
     */
    static Environment getEnvironment() {
        return _environment;
    }
    
    /**
     * Override the environment.
     * <p>
     * Some commands are disabled when using the AutoAPI as the vehicle owner or as the vehicle. This
     * method can be used to override the default {@link Environment#OWNER} environment.
     * </p>
     *
     * @param environment The environment.
     */
    public static void setEnvironment(Environment environment) {
        _environment = environment;
    }
    
    /**
     * The possible environments of the AutoAPI package user. The default is
     * {@link Environment#OWNER}, which works for both Android and Fleet, when Android phone or the
     * fleet manager are the vehicle owners.
     */
    public enum Environment {
        OWNER, VEHICLE
    }
    /**
     * The purpose is to loop the possible setters.
     * <p>
     * NoPropertiesException is ok when parsing Setters because we are in the process of trying to
     * find correct setter.
     */
    protected static class SetterIterator {
        private final int currentSize;
        private int currentIndex = 0;
        public Command theParsedCommand;

        SetterIterator(int count) {
            this.currentSize = count;
        }

        public boolean hasNext() {
            return currentIndex < currentSize && theParsedCommand == null;
        }

        public Command parseNext(SetterIteration next) throws CommandParseException {
            try {
                theParsedCommand = next.iterate(currentIndex);
            } catch (NoPropertiesException e) {
                // its ok, we are trying to find the command
            }
            currentIndex++;
            return theParsedCommand;
        }

        public interface SetterIteration {
            Command iterate(int number) throws NoPropertiesException, CommandParseException;
        }
    }
}