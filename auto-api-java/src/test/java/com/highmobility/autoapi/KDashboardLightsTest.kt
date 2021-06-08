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
package com.highmobility.autoapi

import com.highmobility.autoapi.property.Property
import com.highmobility.autoapi.value.*
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KDashboardLightsTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "006101" + 
            "0100050100020000" +  // High beam is off
            "0100050100020100" +  // Low beam is off
            "0100050100020201" +  // Hazard warning is on
            "0100050100020300" +  // Brake failure/brake system malfunction is off
            "0100050100020400" +  // Hatch open is off
            "0100050100020500" +  // Fuel level is off
            "0100050100020600" +  // Engine coolant temperature is off
            "0100050100020700" +  // Battery charging condition is off
            "0100050100020800" +  // Engine oil is off
            "0100050100020900" +  // Position lights is off
            "0100050100020a00" +  // Front fog light is off
            "0100050100020b00" +  // Rear fog light is off
            "0100050100020c00" +  // Park heating is off
            "0100050100020d00" +  // Engine indicator is off
            "0100050100020e00" +  // Service call for maintenance is off
            "0100050100020f01" +  // Transmission fluid temperature is on
            "0100050100021000" +  // Transmission failure/malfunction is off
            "0100050100021100" +  // Anti-lock brake system failure is off
            "0100050100021200" +  // Worn brake linings is off
            "0100050100021300" +  // Windscreen washer fluid/windshield washer fluid is off
            "0100050100021400" +  // Tire failure/malfunction is off
            "0100050100021501" +  // Engine oil temperature is on
            "0100050100021600" +  // Engine coolant level is off
            "0100050100021700" +  // Steering failure is off
            "0100050100021800" +  // Electronic Speed Controller is off
            "0100050100021900" +  // Brake lights is off
            "0100050100021a00" +  // AdBlue is off
            "0100050100021b00" +  // Fuel filter differential pressure is off
            "0100050100021c00" +  // Seat belt is off
            "0100050100021d00" +  // Advanced emergency braking system is off
            "0100050100021e00" +  // Autonomous Cruise Control is off
            "0100050100021f00" +  // Trailer connected is off
            "0100050100022000" +  // Airbag is off
            "0100050100022100" +  // ESC switched off is off
            "0100050100022200" +  // Lane departure warning switched off is off
            "0100050100022300" +  // Air filter minder is off
            "0100050100022400" +  // Air suspension ride control fault is off
            "0100050100022500" +  // All wheel drive disabled is off
            "0100050100022600" +  // Anti theft is off
            "0100050100022700" +  // Blind spot detection is off
            "0100050100022800" +  // Charge system fault is off
            "0100050100022900" +  // Check fuel cap is off
            "0100050100022a00" +  // Check fuel fill inlet is off
            "0100050100022b00" +  // Check fuel filter is off
            "0100050100022c00" +  // DC temperature warning is off
            "0100050100022d00" +  // DC warning status is off
            "0100050100022e00" +  // Diesel engine idle shutdown is off
            "0100050100022f00" +  // Diesel engine warning is off
            "0100050100023000" +  // Diesel exhaust fluid system fault is off
            "0100050100023100" +  // Diesel exhaust over temperature is off
            "0100050100023200" +  // Diesel exhaust fluid quality is off
            "0100050100023300" +  // Diesel filter regeneration is off
            "0100050100023400" +  // Diesel particulate filter is off
            "0100050100023500" +  // Diesel pre heat is off
            "0100050100023600" +  // Electric trailer brake connection is off
            "0100050100023700" +  // EV battery cell max voltage warning is off
            "0100050100023800" +  // EV battery cell min voltage warning is off
            "0100050100023900" +  // EV battery charge energy storage warning is off
            "0100050100023a00" +  // EV battery high level warning is off
            "0100050100023b00" +  // EV battery high temperature warning is off
            "0100050100023c00" +  // EV battery insulation resist warning is off
            "0100050100023d00" +  // EV battery jump level warning is off
            "0100050100023e00" +  // EV battery low level warning is off
            "0100050100023f00" +  // EV battery max volt veh energy warning is off
            "0100050100024000" +  // EV battery min volt veh energy warning is off
            "0100050100024100" +  // EV battery over charge warning is off
            "0100050100024200" +  // EV battery poor cell warning is off
            "0100050100024300" +  // EV battery temperature difference warning is off
            "0100050100024400" +  // Forward collision warning is off
            "0100050100024500" +  // Fuel doof open is off
            "0100050100024600" +  // Hill descent control fault is off
            "0100050100024700" +  // Hill start assist warning is off
            "0100050100024800" +  // HV interlocking status warning is off
            "0100050100024900" +  // Lighting system failure is off
            "0100050100024a00" +  // Malfunction indicator is off
            "0100050100024b00" +  // Motor controller temperature warning is off
            "0100050100024c00" +  // Park aid malfunction is off
            "0100050100024d00" +  // Passive entry passive start is off
            "0100050100024e00" +  // Powertrain malfunction is off
            "0100050100024f00" +  // Restraints indicator warning is off
            "0100050100025000" +  // Start stop engine warning is off
            "0100050100025100" +  // Traction control disabled is off
            "0100050100025200" +  // Traction control active is off
            "0100050100025300" +  // Traction motor temperature warning is off
            "0100050100025400" +  // Tire pressure monitor system warning is off
            "0100050100025500" +  // Water in fuel is off
            "0100050100025600" +  // Tire warning front right is off
            "0100050100025700" +  // Tire warning front left is off
            "0100050100025800" +  // Tire warning rear right is off
            "0100050100025900" +  // Tire warning rear left is off
            "0100050100025a00" +  // Tire warning system error is off
            "0100050100025b00" +  // Battery low warning is off
            "0100050100025c00" +  // Brake fluid warning is off
            "0100050100025d00" +  // Active hood fault is off
            "0100050100025e00" +  // Active spoiler fault is off
            "0100050100025f00" +  // Adjust tire pressure is off
            "0100050100026000" +  // Steering lock alert is off
            "0100050100026100" +  // Anti pollution failure engine start impossible is off
            "0100050100026200" +  // Anti pollution system failure is off
            "0100050100026300" +  // Anti reverse system failing is off
            "0100050100026400" +  // Auto parking brake is off
            "0100050100026500" +  // Automatic braking deactive is off
            "0100050100026600" +  // Automatic braking system fault is off
            "0100050100026700" +  // Automatic lights settings failure is off
            "0100050100026800" +  // Keyfob battery alarm is off
            "0100050100026900" +  // Trunk open is off
            "0100050100026a00" +  // Check reversing lamp is off
            "0100050100026b00" +  // Crossing line system alert failure is off
            "0100050100026c00" +  // Dipped beam headlamps front left failure is off
            "0100050100026d00" +  // Dipped beam headlamps front right failure is off
            "0100050100026e00" +  // Directional headlamps failure is off
            "0100050100026f00" +  // Directional light failure is off
            "0100050100027000" +  // Dsg failing is off
            "0100050100027100" +  // Electric mode not available is off
            "0100050100027200" +  // Electronic lock failure is off
            "0100050100027300" +  // Engine control system failure is off
            "0100050100027400" +  // Engine oil pressure alert is off
            "0100050100027500" +  // Esp failure is off
            "0100050100027600" +  // Excessive oil temperature is off
            "0100050100027700" +  // Tire front left flat is off
            "0100050100027800" +  // Tire front right flat is off
            "0100050100027900" +  // Tire rear left flat is off
            "0100050100027a00" +  // Tire rear right flat is off
            "0100050100027b00" +  // Fog light front left failure is off
            "0100050100027c00" +  // Fog light front right failure is off
            "0100050100027d00" +  // Fog light rear left failure is off
            "0100050100027e00" +  // Fog light rear right failure is off
            "0100050100027f00" +  // Fog light front fault is off
            "0100050100028000" +  // Door front left open is off
            "0100050100028100" +  // Door front left open high speed is off
            "0100050100028200" +  // Tire front left not monitored is off
            "0100050100028300" +  // Door front right open is off
            "0100050100028400" +  // Door front right open high speed is off
            "0100050100028500" +  // Tire front right not monitored is off
            "0100050100028600" +  // Headlights left failure is off
            "0100050100028700" +  // Headlights right failure is off
            "0100050100028800" +  // Hybrid system fault is off
            "0100050100028900" +  // Hybrid system fault repaired vehicle is off
            "0100050100028a00" +  // Hydraulic pressure or brake fuild insufficient is off
            "0100050100028b00" +  // Lane departure fault is off
            "0100050100028c00" +  // Limited visibility aids camera is off
            "0100050100028d00" +  // Tire pressure low is off
            "0100050100028e00" +  // Maintenance date exceeded is off
            "0100050100028f00" +  // Maintenance odometer exceeded is off
            "0100050100029000" +  // Other failing system is off
            "0100050100029100" +  // Parking brake control failing is off
            "0100050100029200" +  // Parking space measuring system failure is off
            "0100050100029300" +  // Place gear to parking is off
            "0100050100029400" +  // Power steering assitance failure is off
            "0100050100029500" +  // Power steering failure is off
            "0100050100029600" +  // Preheating deactivated battery too low is off
            "0100050100029700" +  // Preheating deactivated fuel level too low is off
            "0100050100029800" +  // Preheating deactivated battery set the clock is off
            "0100050100029900" +  // Fog light rear fault is off
            "0100050100029a00" +  // Door rear left open is off
            "0100050100029b00" +  // Door rear left open high speed is off
            "0100050100029c00" +  // Tire rear left not monitored is off
            "0100050100029d00" +  // Door rear right open is off
            "0100050100029e00" +  // Door rear right open high speed is off
            "0100050100029f00" +  // Tire rear right not monitored is off
            "010005010002a000" +  // Screen rear open is off
            "010005010002a100" +  // Retractable roof mechanism fault is off
            "010005010002a200" +  // Reverse light left failure is off
            "010005010002a300" +  // Reverse light right failure is off
            "010005010002a400" +  // Risk of ice is off
            "010005010002a500" +  // Roof operation impossible apply parking break is off
            "010005010002a600" +  // Roof operation impossible apply start engine is off
            "010005010002a700" +  // Roof operation impossible temperature too high is off
            "010005010002a800" +  // Seatbelt passenger front right unbuckled is off
            "010005010002a900" +  // Seatbelt passenger rear left unbuckled is off
            "010005010002aa00" +  // Seatbelt passenger rear center unbuckled is off
            "010005010002ab00" +  // Seatbelt passenger rear right unbuckled is off
            "010005010002ac00" +  // Battery secondary low is off
            "010005010002ad00" +  // Shock sensor failing is off
            "010005010002ae00" +  // Side lights front left failure is off
            "010005010002af00" +  // Side lights front right failure is off
            "010005010002b000" +  // Side lights rear left failure is off
            "010005010002b100" +  // Side lights rear right failure is off
            "010005010002b200" +  // Spare wheel fitter driving aids deactivated is off
            "010005010002b300" +  // Speed control failure is off
            "010005010002b400" +  // Stop light left failure is off
            "010005010002b500" +  // Stop light right failure is off
            "010005010002b600" +  // Suspension failure is off
            "010005010002b700" +  // Suspension failure reduce speed is off
            "010005010002b800" +  // Suspension fault limited to 90kmh is off
            "010005010002b900" +  // Tire pressure sensor failure is off
            "010005010002ba00" +  // Trunk open high speed is off
            "010005010002bb00" +  // Trunk window open is off
            "010005010002bc00" +  // Turn signal front left failure is off
            "010005010002bd00" +  // Turn signal front right failure is off
            "010005010002be00" +  // Turn signal rear left failure is off
            "010005010002bf00" +  // Turn signal rear right failure is off
            "010005010002c000" +  // Tire under inflation is off
            "010005010002c100" +  // Wheel pressure fault is off
            "010005010002c200" +  // Oil change warning is off
            "010005010002c300" +  // Inspection warning is off
            "02000401000101" +  // Right turn signal`s bulb has failed.
            "02000401000105" // A high beam has failed.
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as DashboardLights.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = DashboardLights.State.Builder()
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HIGH_BEAM, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.LOW_BEAM, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HAZARD_WARNING, OnOffState.ON)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BRAKE_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HATCH_OPEN, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FUEL_LEVEL, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ENGINE_COOLANT_TEMPERATURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BATTERY_CHARGING_CONDITION, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ENGINE_OIL, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.POSITION_LIGHTS, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FRONT_FOG_LIGHT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.REAR_FOG_LIGHT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.PARK_HEATING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ENGINE_INDICATOR, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SERVICE_CALL, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRANSMISSION_FLUID_TEMPERATURE, OnOffState.ON)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRANSMISSION_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ANTI_LOCK_BRAKE_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.WORN_BRAKE_LININGS, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.WINDSCREEN_WASHER_FLUID, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ENGINE_OIL_LEVEL, OnOffState.ON)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ENGINE_COOLANT_LEVEL, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.STEERING_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ESC_INDICATION, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BRAKE_LIGHTS, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ADBLUE_LEVEL, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FUEL_FILTER_DIFF_PRESSURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SEAT_BELT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ADVANCED_BRAKING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ACC, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRAILER_CONNECTED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.AIRBAG, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ESC_SWITCHED_OFF, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.LANE_DEPARTURE_WARNING_OFF, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.AIR_FILTER_MINDER, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.AIR_SUSPENSION_RIDE_CONTROL_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ALL_WHEEL_DRIVE_DISABLED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ANTI_THEFT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BLIND_SPOT_DETECTION, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.CHARGE_SYSTEM_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.CHECK_FUEL_CAP, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.CHECK_FUEL_FILL_INLET, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.CHECK_FUEL_FILTER, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DC_TEMP_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DC_WARNING_STATUS, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_ENGINE_IDLE_SHUTDOWN, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_ENGINE_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_EXHAUST_FLUID_SYSTEM_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_EXHAUST_OVER_TEMP, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_EXHAUST_FLUID_QUALITY, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_FILTER_REGENERATION, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_PARTICULATE_FILTER, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_PRE_HEAT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ELECTRIC_TRAILER_BRAKE_CONNECTION, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_CELL_MAX_VOLT_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_CELL_MIN_VOLT_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_CHARGE_ENERGY_STORAGE_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_HIGH_LEVEL_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_HIGH_TEMPERATURE_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_INSULATION_RESIST_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_JUMP_LEVEL_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_LOW_LEVEL_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_MAX_VOLT_VEH_ENERGY_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_MIN_VOLT_VEH_ENERGY_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_OVER_CHARGE_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_POOR_CELL_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_TEMP_DIFF_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FORWARD_COLLISION_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FUEL_DOOR_OPEN, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HILL_DESCENT_CONTROL_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HILL_START_ASSIST_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HV_INTERLOCKING_STATUS_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.LIGHTING_SYSTEM_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.MALFUNCTION_INDICATOR, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.MOTOR_CONTROLLER_TEMP_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.PARK_AID_MALFUNCTION, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.PASSIVE_ENTRY_PASSIVE_START, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.POWERTRAIN_MALFUNCTION, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.RESTRAINTS_INDICATOR_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.START_STOP_ENGINE_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRACTION_CONTROL_DISABLED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRACTION_CONTROL_ACTIVE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRACTION_MOTOR_TEMP_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_PRESSURE_MONITOR_SYSTEM_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.WATER_IN_FUEL, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_WARNING_FRONT_RIGHT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_WARNING_FRONT_LEFT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_WARNING_REAR_RIGHT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_WARNING_REAR_LEFT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_WARNING_SYSTEM_ERROR, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BATTERY_LOW_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BRAKE_FLUID_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ACTIVE_HOOD_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ACTIVE_SPOILER_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ADJUST_TIRE_PRESSURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.STEERING_LOCK_ALERT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ANTI_POLLUTION_FAILURE_ENGINE_START_IMPOSSIBLE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ANTI_POLLUTION_SYSTEM_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ANTI_REVERSE_SYSTEM_FAILING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.AUTO_PARKING_BRAKE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.AUTOMATIC_BRAKING_DEACTIVE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.AUTOMATIC_BRAKING_SYSTEM_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.AUTOMATIC_LIGHTS_SETTINGS_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.KEYFOB_BATTERY_ALARM, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRUNK_OPEN, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.CHECK_REVERSING_LAMP, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.CROSSING_LINE_SYSTEM_ALERT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIPPED_BEAM_HEADLAMPS_FRONT_LEFT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIPPED_BEAM_HEADLAMPS_FRONT_RIGHT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIRECTIONAL_HEADLAMPS_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIRECTIONAL_LIGHT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DSG_FAILING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ELECTRIC_MODE_NOT_AVAILABLE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ELECTRONIC_LOCK_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ENGINE_CONTROL_SYSTEM_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ENGINE_OIL_PRESSURE_ALERT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ESP_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EXCESSIVE_OIL_TEMPERATURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_FRONT_LEFT_FLAT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_FRONT_RIGHT_FLAT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_REAR_LEFT_FLAT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_REAR_RIGHT_FLAT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FOG_LIGHT_FRONT_LEFT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FOG_LIGHT_FRONT_RIGHT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FOG_LIGHT_REAR_LEFT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FOG_LIGHT_REAR_RIGHT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FOG_LIGHT_FRONT_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DOOR_FRONT_LEFT_OPEN, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DOOR_FRONT_LEFT_OPEN_HIGH_SPEED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_FRONT_LEFT_NOT_MONITORED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DOOR_FRONT_RIGHT_OPEN, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DOOR_FRONT_RIGHT_OPEN_HIGH_SPEED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_FRONT_RIGHT_NOT_MONITORED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HEADLIGHTS_LEFT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HEADLIGHTS_RIGHT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HYBRID_SYSTEM_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HYBRID_SYSTEM_FAULT_REPAIRED_VEHICLE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HYDRAULIC_PRESSURE_OR_BRAKE_FUILD_INSUFFICIENT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.LANE_DEPARTURE_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.LIMITED_VISIBILITY_AIDS_CAMERA, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_PRESSURE_LOW, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.MAINTENANCE_DATE_EXCEEDED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.MAINTENANCE_ODOMETER_EXCEEDED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.OTHER_FAILING_SYSTEM, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.PARKING_BRAKE_CONTROL_FAILING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.PARKING_SPACE_MEASURING_SYSTEM_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.PLACE_GEAR_TO_PARKING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.POWER_STEERING_ASSITANCE_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.POWER_STEERING_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.PREHEATING_DEACTIVATED_BATTERY_TOO_LOW, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.PREHEATING_DEACTIVATED_FUEL_LEVEL_TOO_LOW, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.PREHEATING_DEACTIVATED_BATTERY_SET_THE_CLOCK, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FOG_LIGHT_REAR_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DOOR_REAR_LEFT_OPEN, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DOOR_REAR_LEFT_OPEN_HIGH_SPEED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_REAR_LEFT_NOT_MONITORED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DOOR_REAR_RIGHT_OPEN, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DOOR_REAR_RIGHT_OPEN_HIGH_SPEED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_REAR_RIGHT_NOT_MONITORED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SCREEN_REAR_OPEN, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.RETRACTABLE_ROOF_MECHANISM_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.REVERSE_LIGHT_LEFT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.REVERSE_LIGHT_RIGHT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.RISK_OF_ICE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ROOF_OPERATION_IMPOSSIBLE_APPLY_PARKING_BREAK, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ROOF_OPERATION_IMPOSSIBLE_APPLY_START_ENGINE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ROOF_OPERATION_IMPOSSIBLE_TEMPERATURE_TOO_HIGH, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SEATBELT_PASSENGER_FRONT_RIGHT_UNBUCKLED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SEATBELT_PASSENGER_REAR_LEFT_UNBUCKLED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SEATBELT_PASSENGER_REAR_CENTER_UNBUCKLED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SEATBELT_PASSENGER_REAR_RIGHT_UNBUCKLED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BATTERY_SECONDARY_LOW, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SHOCK_SENSOR_FAILING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SIDE_LIGHTS_FRONT_LEFT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SIDE_LIGHTS_FRONT_RIGHT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SIDE_LIGHTS_REAR_LEFT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SIDE_LIGHTS_REAR_RIGHT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SPARE_WHEEL_FITTER_DRIVING_AIDS_DEACTIVATED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SPEED_CONTROL_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.STOP_LIGHT_LEFT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.STOP_LIGHT_RIGHT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SUSPENSION_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SUSPENSION_FAILURE_REDUCE_SPEED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SUSPENSION_FAULT_LIMITED_TO_90KMH, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_PRESSURE_SENSOR_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRUNK_OPEN_HIGH_SPEED, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRUNK_WINDOW_OPEN, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TURN_SIGNAL_FRONT_LEFT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TURN_SIGNAL_FRONT_RIGHT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TURN_SIGNAL_REAR_LEFT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TURN_SIGNAL_REAR_RIGHT_FAILURE, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_UNDER_INFLATION, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.WHEEL_PRESSURE_FAULT, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.OIL_CHANGE_WARNING, OnOffState.OFF)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.INSPECTION_WARNING, OnOffState.OFF)))
        builder.addBulbFailure(Property(DashboardLights.BulbFailures.TURN_SIGNAL_RIGHT))
        builder.addBulbFailure(Property(DashboardLights.BulbFailures.HIGH_BEAM))
        testState(builder.build())
    }
    
    private fun testState(state: DashboardLights.State) {
        assertTrue(state.dashboardLights[0].value?.name == DashboardLight.Name.HIGH_BEAM)
        assertTrue(state.dashboardLights[0].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[1].value?.name == DashboardLight.Name.LOW_BEAM)
        assertTrue(state.dashboardLights[1].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[2].value?.name == DashboardLight.Name.HAZARD_WARNING)
        assertTrue(state.dashboardLights[2].value?.state == OnOffState.ON)
        assertTrue(state.dashboardLights[3].value?.name == DashboardLight.Name.BRAKE_FAILURE)
        assertTrue(state.dashboardLights[3].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[4].value?.name == DashboardLight.Name.HATCH_OPEN)
        assertTrue(state.dashboardLights[4].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[5].value?.name == DashboardLight.Name.FUEL_LEVEL)
        assertTrue(state.dashboardLights[5].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[6].value?.name == DashboardLight.Name.ENGINE_COOLANT_TEMPERATURE)
        assertTrue(state.dashboardLights[6].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[7].value?.name == DashboardLight.Name.BATTERY_CHARGING_CONDITION)
        assertTrue(state.dashboardLights[7].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[8].value?.name == DashboardLight.Name.ENGINE_OIL)
        assertTrue(state.dashboardLights[8].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[9].value?.name == DashboardLight.Name.POSITION_LIGHTS)
        assertTrue(state.dashboardLights[9].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[10].value?.name == DashboardLight.Name.FRONT_FOG_LIGHT)
        assertTrue(state.dashboardLights[10].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[11].value?.name == DashboardLight.Name.REAR_FOG_LIGHT)
        assertTrue(state.dashboardLights[11].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[12].value?.name == DashboardLight.Name.PARK_HEATING)
        assertTrue(state.dashboardLights[12].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[13].value?.name == DashboardLight.Name.ENGINE_INDICATOR)
        assertTrue(state.dashboardLights[13].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[14].value?.name == DashboardLight.Name.SERVICE_CALL)
        assertTrue(state.dashboardLights[14].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[15].value?.name == DashboardLight.Name.TRANSMISSION_FLUID_TEMPERATURE)
        assertTrue(state.dashboardLights[15].value?.state == OnOffState.ON)
        assertTrue(state.dashboardLights[16].value?.name == DashboardLight.Name.TRANSMISSION_FAILURE)
        assertTrue(state.dashboardLights[16].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[17].value?.name == DashboardLight.Name.ANTI_LOCK_BRAKE_FAILURE)
        assertTrue(state.dashboardLights[17].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[18].value?.name == DashboardLight.Name.WORN_BRAKE_LININGS)
        assertTrue(state.dashboardLights[18].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[19].value?.name == DashboardLight.Name.WINDSCREEN_WASHER_FLUID)
        assertTrue(state.dashboardLights[19].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[20].value?.name == DashboardLight.Name.TIRE_FAILURE)
        assertTrue(state.dashboardLights[20].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[21].value?.name == DashboardLight.Name.ENGINE_OIL_LEVEL)
        assertTrue(state.dashboardLights[21].value?.state == OnOffState.ON)
        assertTrue(state.dashboardLights[22].value?.name == DashboardLight.Name.ENGINE_COOLANT_LEVEL)
        assertTrue(state.dashboardLights[22].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[23].value?.name == DashboardLight.Name.STEERING_FAILURE)
        assertTrue(state.dashboardLights[23].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[24].value?.name == DashboardLight.Name.ESC_INDICATION)
        assertTrue(state.dashboardLights[24].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[25].value?.name == DashboardLight.Name.BRAKE_LIGHTS)
        assertTrue(state.dashboardLights[25].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[26].value?.name == DashboardLight.Name.ADBLUE_LEVEL)
        assertTrue(state.dashboardLights[26].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[27].value?.name == DashboardLight.Name.FUEL_FILTER_DIFF_PRESSURE)
        assertTrue(state.dashboardLights[27].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[28].value?.name == DashboardLight.Name.SEAT_BELT)
        assertTrue(state.dashboardLights[28].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[29].value?.name == DashboardLight.Name.ADVANCED_BRAKING)
        assertTrue(state.dashboardLights[29].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[30].value?.name == DashboardLight.Name.ACC)
        assertTrue(state.dashboardLights[30].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[31].value?.name == DashboardLight.Name.TRAILER_CONNECTED)
        assertTrue(state.dashboardLights[31].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[32].value?.name == DashboardLight.Name.AIRBAG)
        assertTrue(state.dashboardLights[32].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[33].value?.name == DashboardLight.Name.ESC_SWITCHED_OFF)
        assertTrue(state.dashboardLights[33].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[34].value?.name == DashboardLight.Name.LANE_DEPARTURE_WARNING_OFF)
        assertTrue(state.dashboardLights[34].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[35].value?.name == DashboardLight.Name.AIR_FILTER_MINDER)
        assertTrue(state.dashboardLights[35].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[36].value?.name == DashboardLight.Name.AIR_SUSPENSION_RIDE_CONTROL_FAULT)
        assertTrue(state.dashboardLights[36].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[37].value?.name == DashboardLight.Name.ALL_WHEEL_DRIVE_DISABLED)
        assertTrue(state.dashboardLights[37].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[38].value?.name == DashboardLight.Name.ANTI_THEFT)
        assertTrue(state.dashboardLights[38].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[39].value?.name == DashboardLight.Name.BLIND_SPOT_DETECTION)
        assertTrue(state.dashboardLights[39].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[40].value?.name == DashboardLight.Name.CHARGE_SYSTEM_FAULT)
        assertTrue(state.dashboardLights[40].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[41].value?.name == DashboardLight.Name.CHECK_FUEL_CAP)
        assertTrue(state.dashboardLights[41].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[42].value?.name == DashboardLight.Name.CHECK_FUEL_FILL_INLET)
        assertTrue(state.dashboardLights[42].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[43].value?.name == DashboardLight.Name.CHECK_FUEL_FILTER)
        assertTrue(state.dashboardLights[43].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[44].value?.name == DashboardLight.Name.DC_TEMP_WARNING)
        assertTrue(state.dashboardLights[44].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[45].value?.name == DashboardLight.Name.DC_WARNING_STATUS)
        assertTrue(state.dashboardLights[45].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[46].value?.name == DashboardLight.Name.DIESEL_ENGINE_IDLE_SHUTDOWN)
        assertTrue(state.dashboardLights[46].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[47].value?.name == DashboardLight.Name.DIESEL_ENGINE_WARNING)
        assertTrue(state.dashboardLights[47].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[48].value?.name == DashboardLight.Name.DIESEL_EXHAUST_FLUID_SYSTEM_FAULT)
        assertTrue(state.dashboardLights[48].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[49].value?.name == DashboardLight.Name.DIESEL_EXHAUST_OVER_TEMP)
        assertTrue(state.dashboardLights[49].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[50].value?.name == DashboardLight.Name.DIESEL_EXHAUST_FLUID_QUALITY)
        assertTrue(state.dashboardLights[50].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[51].value?.name == DashboardLight.Name.DIESEL_FILTER_REGENERATION)
        assertTrue(state.dashboardLights[51].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[52].value?.name == DashboardLight.Name.DIESEL_PARTICULATE_FILTER)
        assertTrue(state.dashboardLights[52].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[53].value?.name == DashboardLight.Name.DIESEL_PRE_HEAT)
        assertTrue(state.dashboardLights[53].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[54].value?.name == DashboardLight.Name.ELECTRIC_TRAILER_BRAKE_CONNECTION)
        assertTrue(state.dashboardLights[54].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[55].value?.name == DashboardLight.Name.EV_BATTERY_CELL_MAX_VOLT_WARNING)
        assertTrue(state.dashboardLights[55].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[56].value?.name == DashboardLight.Name.EV_BATTERY_CELL_MIN_VOLT_WARNING)
        assertTrue(state.dashboardLights[56].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[57].value?.name == DashboardLight.Name.EV_BATTERY_CHARGE_ENERGY_STORAGE_WARNING)
        assertTrue(state.dashboardLights[57].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[58].value?.name == DashboardLight.Name.EV_BATTERY_HIGH_LEVEL_WARNING)
        assertTrue(state.dashboardLights[58].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[59].value?.name == DashboardLight.Name.EV_BATTERY_HIGH_TEMPERATURE_WARNING)
        assertTrue(state.dashboardLights[59].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[60].value?.name == DashboardLight.Name.EV_BATTERY_INSULATION_RESIST_WARNING)
        assertTrue(state.dashboardLights[60].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[61].value?.name == DashboardLight.Name.EV_BATTERY_JUMP_LEVEL_WARNING)
        assertTrue(state.dashboardLights[61].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[62].value?.name == DashboardLight.Name.EV_BATTERY_LOW_LEVEL_WARNING)
        assertTrue(state.dashboardLights[62].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[63].value?.name == DashboardLight.Name.EV_BATTERY_MAX_VOLT_VEH_ENERGY_WARNING)
        assertTrue(state.dashboardLights[63].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[64].value?.name == DashboardLight.Name.EV_BATTERY_MIN_VOLT_VEH_ENERGY_WARNING)
        assertTrue(state.dashboardLights[64].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[65].value?.name == DashboardLight.Name.EV_BATTERY_OVER_CHARGE_WARNING)
        assertTrue(state.dashboardLights[65].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[66].value?.name == DashboardLight.Name.EV_BATTERY_POOR_CELL_WARNING)
        assertTrue(state.dashboardLights[66].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[67].value?.name == DashboardLight.Name.EV_BATTERY_TEMP_DIFF_WARNING)
        assertTrue(state.dashboardLights[67].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[68].value?.name == DashboardLight.Name.FORWARD_COLLISION_WARNING)
        assertTrue(state.dashboardLights[68].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[69].value?.name == DashboardLight.Name.FUEL_DOOR_OPEN)
        assertTrue(state.dashboardLights[69].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[70].value?.name == DashboardLight.Name.HILL_DESCENT_CONTROL_FAULT)
        assertTrue(state.dashboardLights[70].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[71].value?.name == DashboardLight.Name.HILL_START_ASSIST_WARNING)
        assertTrue(state.dashboardLights[71].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[72].value?.name == DashboardLight.Name.HV_INTERLOCKING_STATUS_WARNING)
        assertTrue(state.dashboardLights[72].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[73].value?.name == DashboardLight.Name.LIGHTING_SYSTEM_FAILURE)
        assertTrue(state.dashboardLights[73].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[74].value?.name == DashboardLight.Name.MALFUNCTION_INDICATOR)
        assertTrue(state.dashboardLights[74].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[75].value?.name == DashboardLight.Name.MOTOR_CONTROLLER_TEMP_WARNING)
        assertTrue(state.dashboardLights[75].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[76].value?.name == DashboardLight.Name.PARK_AID_MALFUNCTION)
        assertTrue(state.dashboardLights[76].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[77].value?.name == DashboardLight.Name.PASSIVE_ENTRY_PASSIVE_START)
        assertTrue(state.dashboardLights[77].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[78].value?.name == DashboardLight.Name.POWERTRAIN_MALFUNCTION)
        assertTrue(state.dashboardLights[78].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[79].value?.name == DashboardLight.Name.RESTRAINTS_INDICATOR_WARNING)
        assertTrue(state.dashboardLights[79].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[80].value?.name == DashboardLight.Name.START_STOP_ENGINE_WARNING)
        assertTrue(state.dashboardLights[80].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[81].value?.name == DashboardLight.Name.TRACTION_CONTROL_DISABLED)
        assertTrue(state.dashboardLights[81].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[82].value?.name == DashboardLight.Name.TRACTION_CONTROL_ACTIVE)
        assertTrue(state.dashboardLights[82].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[83].value?.name == DashboardLight.Name.TRACTION_MOTOR_TEMP_WARNING)
        assertTrue(state.dashboardLights[83].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[84].value?.name == DashboardLight.Name.TIRE_PRESSURE_MONITOR_SYSTEM_WARNING)
        assertTrue(state.dashboardLights[84].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[85].value?.name == DashboardLight.Name.WATER_IN_FUEL)
        assertTrue(state.dashboardLights[85].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[86].value?.name == DashboardLight.Name.TIRE_WARNING_FRONT_RIGHT)
        assertTrue(state.dashboardLights[86].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[87].value?.name == DashboardLight.Name.TIRE_WARNING_FRONT_LEFT)
        assertTrue(state.dashboardLights[87].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[88].value?.name == DashboardLight.Name.TIRE_WARNING_REAR_RIGHT)
        assertTrue(state.dashboardLights[88].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[89].value?.name == DashboardLight.Name.TIRE_WARNING_REAR_LEFT)
        assertTrue(state.dashboardLights[89].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[90].value?.name == DashboardLight.Name.TIRE_WARNING_SYSTEM_ERROR)
        assertTrue(state.dashboardLights[90].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[91].value?.name == DashboardLight.Name.BATTERY_LOW_WARNING)
        assertTrue(state.dashboardLights[91].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[92].value?.name == DashboardLight.Name.BRAKE_FLUID_WARNING)
        assertTrue(state.dashboardLights[92].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[93].value?.name == DashboardLight.Name.ACTIVE_HOOD_FAULT)
        assertTrue(state.dashboardLights[93].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[94].value?.name == DashboardLight.Name.ACTIVE_SPOILER_FAULT)
        assertTrue(state.dashboardLights[94].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[95].value?.name == DashboardLight.Name.ADJUST_TIRE_PRESSURE)
        assertTrue(state.dashboardLights[95].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[96].value?.name == DashboardLight.Name.STEERING_LOCK_ALERT)
        assertTrue(state.dashboardLights[96].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[97].value?.name == DashboardLight.Name.ANTI_POLLUTION_FAILURE_ENGINE_START_IMPOSSIBLE)
        assertTrue(state.dashboardLights[97].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[98].value?.name == DashboardLight.Name.ANTI_POLLUTION_SYSTEM_FAILURE)
        assertTrue(state.dashboardLights[98].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[99].value?.name == DashboardLight.Name.ANTI_REVERSE_SYSTEM_FAILING)
        assertTrue(state.dashboardLights[99].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[100].value?.name == DashboardLight.Name.AUTO_PARKING_BRAKE)
        assertTrue(state.dashboardLights[100].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[101].value?.name == DashboardLight.Name.AUTOMATIC_BRAKING_DEACTIVE)
        assertTrue(state.dashboardLights[101].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[102].value?.name == DashboardLight.Name.AUTOMATIC_BRAKING_SYSTEM_FAULT)
        assertTrue(state.dashboardLights[102].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[103].value?.name == DashboardLight.Name.AUTOMATIC_LIGHTS_SETTINGS_FAILURE)
        assertTrue(state.dashboardLights[103].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[104].value?.name == DashboardLight.Name.KEYFOB_BATTERY_ALARM)
        assertTrue(state.dashboardLights[104].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[105].value?.name == DashboardLight.Name.TRUNK_OPEN)
        assertTrue(state.dashboardLights[105].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[106].value?.name == DashboardLight.Name.CHECK_REVERSING_LAMP)
        assertTrue(state.dashboardLights[106].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[107].value?.name == DashboardLight.Name.CROSSING_LINE_SYSTEM_ALERT_FAILURE)
        assertTrue(state.dashboardLights[107].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[108].value?.name == DashboardLight.Name.DIPPED_BEAM_HEADLAMPS_FRONT_LEFT_FAILURE)
        assertTrue(state.dashboardLights[108].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[109].value?.name == DashboardLight.Name.DIPPED_BEAM_HEADLAMPS_FRONT_RIGHT_FAILURE)
        assertTrue(state.dashboardLights[109].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[110].value?.name == DashboardLight.Name.DIRECTIONAL_HEADLAMPS_FAILURE)
        assertTrue(state.dashboardLights[110].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[111].value?.name == DashboardLight.Name.DIRECTIONAL_LIGHT_FAILURE)
        assertTrue(state.dashboardLights[111].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[112].value?.name == DashboardLight.Name.DSG_FAILING)
        assertTrue(state.dashboardLights[112].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[113].value?.name == DashboardLight.Name.ELECTRIC_MODE_NOT_AVAILABLE)
        assertTrue(state.dashboardLights[113].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[114].value?.name == DashboardLight.Name.ELECTRONIC_LOCK_FAILURE)
        assertTrue(state.dashboardLights[114].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[115].value?.name == DashboardLight.Name.ENGINE_CONTROL_SYSTEM_FAILURE)
        assertTrue(state.dashboardLights[115].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[116].value?.name == DashboardLight.Name.ENGINE_OIL_PRESSURE_ALERT)
        assertTrue(state.dashboardLights[116].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[117].value?.name == DashboardLight.Name.ESP_FAILURE)
        assertTrue(state.dashboardLights[117].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[118].value?.name == DashboardLight.Name.EXCESSIVE_OIL_TEMPERATURE)
        assertTrue(state.dashboardLights[118].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[119].value?.name == DashboardLight.Name.TIRE_FRONT_LEFT_FLAT)
        assertTrue(state.dashboardLights[119].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[120].value?.name == DashboardLight.Name.TIRE_FRONT_RIGHT_FLAT)
        assertTrue(state.dashboardLights[120].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[121].value?.name == DashboardLight.Name.TIRE_REAR_LEFT_FLAT)
        assertTrue(state.dashboardLights[121].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[122].value?.name == DashboardLight.Name.TIRE_REAR_RIGHT_FLAT)
        assertTrue(state.dashboardLights[122].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[123].value?.name == DashboardLight.Name.FOG_LIGHT_FRONT_LEFT_FAILURE)
        assertTrue(state.dashboardLights[123].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[124].value?.name == DashboardLight.Name.FOG_LIGHT_FRONT_RIGHT_FAILURE)
        assertTrue(state.dashboardLights[124].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[125].value?.name == DashboardLight.Name.FOG_LIGHT_REAR_LEFT_FAILURE)
        assertTrue(state.dashboardLights[125].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[126].value?.name == DashboardLight.Name.FOG_LIGHT_REAR_RIGHT_FAILURE)
        assertTrue(state.dashboardLights[126].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[127].value?.name == DashboardLight.Name.FOG_LIGHT_FRONT_FAULT)
        assertTrue(state.dashboardLights[127].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[128].value?.name == DashboardLight.Name.DOOR_FRONT_LEFT_OPEN)
        assertTrue(state.dashboardLights[128].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[129].value?.name == DashboardLight.Name.DOOR_FRONT_LEFT_OPEN_HIGH_SPEED)
        assertTrue(state.dashboardLights[129].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[130].value?.name == DashboardLight.Name.TIRE_FRONT_LEFT_NOT_MONITORED)
        assertTrue(state.dashboardLights[130].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[131].value?.name == DashboardLight.Name.DOOR_FRONT_RIGHT_OPEN)
        assertTrue(state.dashboardLights[131].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[132].value?.name == DashboardLight.Name.DOOR_FRONT_RIGHT_OPEN_HIGH_SPEED)
        assertTrue(state.dashboardLights[132].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[133].value?.name == DashboardLight.Name.TIRE_FRONT_RIGHT_NOT_MONITORED)
        assertTrue(state.dashboardLights[133].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[134].value?.name == DashboardLight.Name.HEADLIGHTS_LEFT_FAILURE)
        assertTrue(state.dashboardLights[134].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[135].value?.name == DashboardLight.Name.HEADLIGHTS_RIGHT_FAILURE)
        assertTrue(state.dashboardLights[135].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[136].value?.name == DashboardLight.Name.HYBRID_SYSTEM_FAULT)
        assertTrue(state.dashboardLights[136].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[137].value?.name == DashboardLight.Name.HYBRID_SYSTEM_FAULT_REPAIRED_VEHICLE)
        assertTrue(state.dashboardLights[137].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[138].value?.name == DashboardLight.Name.HYDRAULIC_PRESSURE_OR_BRAKE_FUILD_INSUFFICIENT)
        assertTrue(state.dashboardLights[138].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[139].value?.name == DashboardLight.Name.LANE_DEPARTURE_FAULT)
        assertTrue(state.dashboardLights[139].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[140].value?.name == DashboardLight.Name.LIMITED_VISIBILITY_AIDS_CAMERA)
        assertTrue(state.dashboardLights[140].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[141].value?.name == DashboardLight.Name.TIRE_PRESSURE_LOW)
        assertTrue(state.dashboardLights[141].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[142].value?.name == DashboardLight.Name.MAINTENANCE_DATE_EXCEEDED)
        assertTrue(state.dashboardLights[142].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[143].value?.name == DashboardLight.Name.MAINTENANCE_ODOMETER_EXCEEDED)
        assertTrue(state.dashboardLights[143].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[144].value?.name == DashboardLight.Name.OTHER_FAILING_SYSTEM)
        assertTrue(state.dashboardLights[144].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[145].value?.name == DashboardLight.Name.PARKING_BRAKE_CONTROL_FAILING)
        assertTrue(state.dashboardLights[145].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[146].value?.name == DashboardLight.Name.PARKING_SPACE_MEASURING_SYSTEM_FAILURE)
        assertTrue(state.dashboardLights[146].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[147].value?.name == DashboardLight.Name.PLACE_GEAR_TO_PARKING)
        assertTrue(state.dashboardLights[147].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[148].value?.name == DashboardLight.Name.POWER_STEERING_ASSITANCE_FAILURE)
        assertTrue(state.dashboardLights[148].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[149].value?.name == DashboardLight.Name.POWER_STEERING_FAILURE)
        assertTrue(state.dashboardLights[149].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[150].value?.name == DashboardLight.Name.PREHEATING_DEACTIVATED_BATTERY_TOO_LOW)
        assertTrue(state.dashboardLights[150].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[151].value?.name == DashboardLight.Name.PREHEATING_DEACTIVATED_FUEL_LEVEL_TOO_LOW)
        assertTrue(state.dashboardLights[151].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[152].value?.name == DashboardLight.Name.PREHEATING_DEACTIVATED_BATTERY_SET_THE_CLOCK)
        assertTrue(state.dashboardLights[152].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[153].value?.name == DashboardLight.Name.FOG_LIGHT_REAR_FAULT)
        assertTrue(state.dashboardLights[153].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[154].value?.name == DashboardLight.Name.DOOR_REAR_LEFT_OPEN)
        assertTrue(state.dashboardLights[154].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[155].value?.name == DashboardLight.Name.DOOR_REAR_LEFT_OPEN_HIGH_SPEED)
        assertTrue(state.dashboardLights[155].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[156].value?.name == DashboardLight.Name.TIRE_REAR_LEFT_NOT_MONITORED)
        assertTrue(state.dashboardLights[156].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[157].value?.name == DashboardLight.Name.DOOR_REAR_RIGHT_OPEN)
        assertTrue(state.dashboardLights[157].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[158].value?.name == DashboardLight.Name.DOOR_REAR_RIGHT_OPEN_HIGH_SPEED)
        assertTrue(state.dashboardLights[158].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[159].value?.name == DashboardLight.Name.TIRE_REAR_RIGHT_NOT_MONITORED)
        assertTrue(state.dashboardLights[159].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[160].value?.name == DashboardLight.Name.SCREEN_REAR_OPEN)
        assertTrue(state.dashboardLights[160].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[161].value?.name == DashboardLight.Name.RETRACTABLE_ROOF_MECHANISM_FAULT)
        assertTrue(state.dashboardLights[161].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[162].value?.name == DashboardLight.Name.REVERSE_LIGHT_LEFT_FAILURE)
        assertTrue(state.dashboardLights[162].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[163].value?.name == DashboardLight.Name.REVERSE_LIGHT_RIGHT_FAILURE)
        assertTrue(state.dashboardLights[163].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[164].value?.name == DashboardLight.Name.RISK_OF_ICE)
        assertTrue(state.dashboardLights[164].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[165].value?.name == DashboardLight.Name.ROOF_OPERATION_IMPOSSIBLE_APPLY_PARKING_BREAK)
        assertTrue(state.dashboardLights[165].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[166].value?.name == DashboardLight.Name.ROOF_OPERATION_IMPOSSIBLE_APPLY_START_ENGINE)
        assertTrue(state.dashboardLights[166].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[167].value?.name == DashboardLight.Name.ROOF_OPERATION_IMPOSSIBLE_TEMPERATURE_TOO_HIGH)
        assertTrue(state.dashboardLights[167].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[168].value?.name == DashboardLight.Name.SEATBELT_PASSENGER_FRONT_RIGHT_UNBUCKLED)
        assertTrue(state.dashboardLights[168].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[169].value?.name == DashboardLight.Name.SEATBELT_PASSENGER_REAR_LEFT_UNBUCKLED)
        assertTrue(state.dashboardLights[169].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[170].value?.name == DashboardLight.Name.SEATBELT_PASSENGER_REAR_CENTER_UNBUCKLED)
        assertTrue(state.dashboardLights[170].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[171].value?.name == DashboardLight.Name.SEATBELT_PASSENGER_REAR_RIGHT_UNBUCKLED)
        assertTrue(state.dashboardLights[171].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[172].value?.name == DashboardLight.Name.BATTERY_SECONDARY_LOW)
        assertTrue(state.dashboardLights[172].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[173].value?.name == DashboardLight.Name.SHOCK_SENSOR_FAILING)
        assertTrue(state.dashboardLights[173].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[174].value?.name == DashboardLight.Name.SIDE_LIGHTS_FRONT_LEFT_FAILURE)
        assertTrue(state.dashboardLights[174].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[175].value?.name == DashboardLight.Name.SIDE_LIGHTS_FRONT_RIGHT_FAILURE)
        assertTrue(state.dashboardLights[175].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[176].value?.name == DashboardLight.Name.SIDE_LIGHTS_REAR_LEFT_FAILURE)
        assertTrue(state.dashboardLights[176].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[177].value?.name == DashboardLight.Name.SIDE_LIGHTS_REAR_RIGHT_FAILURE)
        assertTrue(state.dashboardLights[177].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[178].value?.name == DashboardLight.Name.SPARE_WHEEL_FITTER_DRIVING_AIDS_DEACTIVATED)
        assertTrue(state.dashboardLights[178].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[179].value?.name == DashboardLight.Name.SPEED_CONTROL_FAILURE)
        assertTrue(state.dashboardLights[179].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[180].value?.name == DashboardLight.Name.STOP_LIGHT_LEFT_FAILURE)
        assertTrue(state.dashboardLights[180].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[181].value?.name == DashboardLight.Name.STOP_LIGHT_RIGHT_FAILURE)
        assertTrue(state.dashboardLights[181].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[182].value?.name == DashboardLight.Name.SUSPENSION_FAILURE)
        assertTrue(state.dashboardLights[182].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[183].value?.name == DashboardLight.Name.SUSPENSION_FAILURE_REDUCE_SPEED)
        assertTrue(state.dashboardLights[183].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[184].value?.name == DashboardLight.Name.SUSPENSION_FAULT_LIMITED_TO_90KMH)
        assertTrue(state.dashboardLights[184].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[185].value?.name == DashboardLight.Name.TIRE_PRESSURE_SENSOR_FAILURE)
        assertTrue(state.dashboardLights[185].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[186].value?.name == DashboardLight.Name.TRUNK_OPEN_HIGH_SPEED)
        assertTrue(state.dashboardLights[186].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[187].value?.name == DashboardLight.Name.TRUNK_WINDOW_OPEN)
        assertTrue(state.dashboardLights[187].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[188].value?.name == DashboardLight.Name.TURN_SIGNAL_FRONT_LEFT_FAILURE)
        assertTrue(state.dashboardLights[188].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[189].value?.name == DashboardLight.Name.TURN_SIGNAL_FRONT_RIGHT_FAILURE)
        assertTrue(state.dashboardLights[189].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[190].value?.name == DashboardLight.Name.TURN_SIGNAL_REAR_LEFT_FAILURE)
        assertTrue(state.dashboardLights[190].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[191].value?.name == DashboardLight.Name.TURN_SIGNAL_REAR_RIGHT_FAILURE)
        assertTrue(state.dashboardLights[191].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[192].value?.name == DashboardLight.Name.TIRE_UNDER_INFLATION)
        assertTrue(state.dashboardLights[192].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[193].value?.name == DashboardLight.Name.WHEEL_PRESSURE_FAULT)
        assertTrue(state.dashboardLights[193].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[194].value?.name == DashboardLight.Name.OIL_CHANGE_WARNING)
        assertTrue(state.dashboardLights[194].value?.state == OnOffState.OFF)
        assertTrue(state.dashboardLights[195].value?.name == DashboardLight.Name.INSPECTION_WARNING)
        assertTrue(state.dashboardLights[195].value?.state == OnOffState.OFF)
        assertTrue(state.bulbFailures[0].value == DashboardLights.BulbFailures.TURN_SIGNAL_RIGHT)
        assertTrue(state.bulbFailures[1].value == DashboardLights.BulbFailures.HIGH_BEAM)
        assertTrue(bytesTheSame(state, bytes))
    }
    
    @Test
    fun testGetDashboardLights() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "006100")
        val defaultGetter = DashboardLights.GetDashboardLights()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
        
        val propertyGetterBytes = Bytes(COMMAND_HEADER + "0061000102")
        val propertyGetter = DashboardLights.GetDashboardLights(0x01, 0x02)
        assertTrue(propertyGetter == propertyGetterBytes)
        assertTrue(propertyGetter.getPropertyIdentifiers() == Bytes("0102"))
    }
    
    @Test
    fun testGetDashboardLightsAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "006102")
        val created = DashboardLights.GetDashboardLightsAvailability()
        assertTrue(created.identifier == Identifier.DASHBOARD_LIGHTS)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(bytes) as DashboardLights.GetDashboardLightsAvailability
        assertTrue(resolved.identifier == Identifier.DASHBOARD_LIGHTS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
    @Test
    fun testGetDashboardLightsAvailabilitySome() {
        val identifierBytes = Bytes("0102")
        val allBytes = Bytes(COMMAND_HEADER + "006102" + identifierBytes)
        val constructed = DashboardLights.GetDashboardLightsAvailability(identifierBytes)
        assertTrue(constructed.identifier == Identifier.DASHBOARD_LIGHTS)
        assertTrue(constructed.type == Type.GET_AVAILABILITY)
        assertTrue(constructed.getPropertyIdentifiers() == identifierBytes)
        assertTrue(constructed == allBytes)
        val secondConstructed = DashboardLights.GetDashboardLightsAvailability(0x01, 0x02)
        assertTrue(constructed == secondConstructed)
    
        setEnvironment(CommandResolver.Environment.VEHICLE)
    
        val resolved = CommandResolver.resolve(allBytes) as DashboardLights.GetDashboardLightsAvailability
        assertTrue(resolved.identifier == Identifier.DASHBOARD_LIGHTS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers() == identifierBytes)
        assertTrue(resolved == allBytes)
    }
}