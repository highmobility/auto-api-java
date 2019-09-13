// TODO: license

package com.highmobility.autoapi.v2;

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
        if (bytes == null || bytes.length < 3) return new Command(bytes);

        Command command = null;

        try {
            if (bytesAreForIdentifier(bytes, Identifier.VEHICLE_STATUS)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new VehicleStatusState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetVehicleStatusProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.PARKING_TICKET)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new ParkingTicketState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetParkingTicketProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.BROWSER)) {
                }
            else if (bytesAreForIdentifier(bytes, Identifier.WINDOWS)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new WindowsState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetWindowsProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.VEHICLE_TIME)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new VehicleTimeState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.DRIVER_FATIGUE)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new DriverFatigueState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.REMOTE_CONTROL)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new RemoteControlState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.FUELING)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new FuelingState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetGasFlapProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.NAVI_DESTINATION)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new NaviDestinationState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetNaviDestinationProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.LIGHT_CONDITIONS)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new LightConditionsState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetLightConditionsProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.OFFROAD)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new OffroadState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetOffroadProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.TRUNK)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new TrunkState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetTrunkProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.DOORS)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new DoorsState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetDoorsProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.VALET_MODE)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new ValetModeState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.DASHBOARD_LIGHTS)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new DashboardLightsState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.MULTI_COMMAND)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new MultiCommandState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.TEXT_INPUT)) {
                }
            else if (bytesAreForIdentifier(bytes, Identifier.LIGHTS)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new LightsState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetLightsProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.CHASSIS_SETTINGS)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new ChassisSettingsState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetChassisSettingsProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.NOTIFICATIONS)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new NotificationsState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.HOOD)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new HoodState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.CHARGING)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new ChargingState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetChargingProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.MOBILE)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new MobileState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.HOME_CHARGER)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new HomeChargerState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetHomeChargerProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.DIAGNOSTICS)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new DiagnosticsState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetDiagnosticsProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.USAGE)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new UsageState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetUsageProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.POWER_TAKEOFF)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new PowerTakeoffState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetPowerTakeoffProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.WAKE_UP)) {
                }
            else if (bytesAreForIdentifier(bytes, Identifier.VIDEO_HANDOVER)) {
                }
            else if (bytesAreForIdentifier(bytes, Identifier.HISTORICAL)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new HistoricalState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.WI_FI)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new WiFiState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetWiFiProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.VEHICLE_LOCATION)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new VehicleLocationState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetVehicleLocationProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.HEART_RATE)) {
                }
            else if (bytesAreForIdentifier(bytes, Identifier.GRAPHICS)) {
                }
            else if (bytesAreForIdentifier(bytes, Identifier.RACE)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new RaceState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetRaceProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.FIRMWARE_VERSION)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new FirmwareVersionState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetFirmwareVersionProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.THEFT_ALARM)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new TheftAlarmState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.SEATS)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new SeatsState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetSeatsProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.ENGINE_START_STOP)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new EngineStartStopState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.TACHOGRAPH)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new TachographState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetTachographProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.PARKING_BRAKE)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new ParkingBrakeState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.CAPABILITIES)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new CapabilitiesState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.MAINTENANCE)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new MaintenanceState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetMaintenanceProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.ROOFTOP_CONTROL)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new RooftopControlState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetRooftopProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.FAILURE_MESSAGE)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new FailureMessageState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.WINDSCREEN)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new WindscreenState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetWindscreenProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.CRUISE_CONTROL)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new CruiseControlState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetCruiseControlProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.KEYFOB_POSITION)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new KeyfobPositionState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.HONK_HORN_FLASH_LIGHTS)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new HonkHornFlashLightsState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetFlashersProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.WEATHER_CONDITIONS)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new WeatherConditionsState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.MESSAGING)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new MessagingState(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.IGNITION)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new IgnitionState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetIgnitionProperties(bytes);
                }
            }
            else if (bytesAreForIdentifier(bytes, Identifier.CLIMATE)) {
                if (bytesAreForType(bytes, Type.SET)) {
                    command = new ClimateState(bytes);
                }
                else if (bytesAreForType(bytes, Type.GET)) {
                    command = new GetClimateProperties(bytes);
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

    private static boolean bytesAreForIdentifier(byte[] bytes, Identifier identifier) {
        return bytes[0] == identifier.getBytes()[0]
                && bytes[1] == identifier.getBytes()[1];
    }

    private static boolean bytesAreForType(byte[] bytes, Type type) {
        return bytes[2] == type.getByte();
    }

    private static String commandToString(byte[] bytes) {
        return ByteUtils.hexFromBytes(ByteUtils
                .trimmedBytes(bytes, bytes.length >= 3 ? 3 : bytes.length));
    }
}