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
            "010006010003000000" +  // High beam is inactive
            "010006010003010000" +  // Low beam is inactive
            "010006010003020100" +  // Info light active for hazard warning
            "010006010003030000" +  // Brake failure/brake system malfunction is inactive
            "010006010003040000" +  // Hatch open is inactive
            "010006010003050000" +  // Fuel level is inactive
            "010006010003060000" +  // Engine coolant temperature is inactive
            "010006010003070000" +  // Battery charging condition is inactive
            "010006010003080000" +  // Engine oil is inactive
            "010006010003090000" +  // Position lights is inactive
            "0100060100030a0000" +  // Front fog light is inactive
            "0100060100030b0000" +  // Rear fog light is inactive
            "0100060100030c0000" +  // Park heating is inactive
            "0100060100030d0000" +  // Engine indicator is inactive
            "0100060100030e0000" +  // Service call for maintenance is inactive
            "0100060100030f0102" +  // Red light active for transmission fluid temperature
            "010006010003100000" +  // Transmission failure/malfunction is inactive
            "010006010003110000" +  // Anti-lock brake system failure is inactive
            "010006010003120000" +  // Worn brake linings is inactive
            "010006010003130000" +  // Windscreen washer fluid/windshield washer fluid is inactive
            "010006010003140000" +  // Tire failure/malfunction is inactive
            "010006010003150100" +  // Yellow light active for engine oil temperature
            "010006010003160000" +  // Engine coolant level is inactive
            "010006010003170000" +  // Steering failure is inactive
            "010006010003180000" +  // Electronic Speed Controller is inactive
            "010006010003190000" +  // Brake lights is inactive
            "0100060100031a0000" +  // AdBlue is inactive
            "0100060100031b0000" +  // Fuel filter differential pressure is inactive
            "0100060100031c0000" +  // Seat belt is inactive
            "0100060100031d0000" +  // Advanced emergency braking system is inactive
            "0100060100031e0000" +  // Autonomous Cruise Control is inactive
            "0100060100031f0000" +  // Trailer connected is inactive
            "010006010003200000" +  // Airbag is inactive
            "010006010003210000" +  // ESC switched off is inactive
            "010006010003220000" +  // Lane departure warning switched off is inactive
            "010006010003230000" +  // Air filter minder is inactive
            "010006010003240000" +  // Air suspension ride control fault is inactive
            "010006010003250000" +  // All wheel drive disabled is inactive
            "010006010003260000" +  // Anti theft is inactive
            "010006010003270000" +  // Blind spot detection is inactive
            "010006010003280000" +  // Charge system fault is inactive
            "010006010003290000" +  // Check fuel cap is inactive
            "0100060100032a0000" +  // Check fuel fill inlet is inactive
            "0100060100032b0000" +  // Check fuel filter is inactive
            "0100060100032c0000" +  // DC temperature warning is inactive
            "0100060100032d0000" +  // DC warning status is inactive
            "0100060100032e0000" +  // Diesel engine idle shutdown is inactive
            "0100060100032f0000" +  // Diesel engine warning is inactive
            "010006010003300000" +  // Diesel exhaust fluid system fault is inactive
            "010006010003310000" +  // Diesel exhaust over temperature is inactive
            "010006010003320000" +  // Diesel exhaust fluid quality is inactive
            "010006010003330000" +  // Diesel filter regeneration is inactive
            "010006010003340000" +  // Diesel particulate filter is inactive
            "010006010003350000" +  // Diesel pre heat is inactive
            "010006010003360000" +  // Electric trailer brake connection is inactive
            "010006010003370000" +  // EV battery cell max voltage warning is inactive
            "010006010003380000" +  // EV battery cell min voltage warning is inactive
            "010006010003390000" +  // EV battery charge energy storage warning is inactive
            "0100060100033a0000" +  // EV battery high level warning is inactive
            "0100060100033b0000" +  // EV battery high temperature warning is inactive
            "0100060100033c0000" +  // EV battery insulation resist warning is inactive
            "0100060100033d0000" +  // EV battery jump level warning is inactive
            "0100060100033e0000" +  // EV battery low level warning is inactive
            "0100060100033f0000" +  // EV battery max volt veh energy warning is inactive
            "010006010003400000" +  // EV battery min volt veh energy warning is inactive
            "010006010003410000" +  // EV battery over charge warning is inactive
            "010006010003420000" +  // EV battery poor cell warning is inactive
            "010006010003430000" +  // EV battery temperature difference warning is inactive
            "010006010003440000" +  // Forward collision warning is inactive
            "010006010003450000" +  // Fuel doof open is inactive
            "010006010003460000" +  // Hill descent control fault is inactive
            "010006010003470000" +  // Hill start assist warning is inactive
            "010006010003480000" +  // HV interlocking status warning is inactive
            "010006010003490000" +  // Lighting system failure is inactive
            "0100060100034a0000" +  // Malfunction indicator is inactive
            "0100060100034b0000" +  // Motor controller temperature warning is inactive
            "0100060100034c0000" +  // Park aid malfunction is inactive
            "0100060100034d0000" +  // Passive entry passive start is inactive
            "0100060100034e0000" +  // Powertrain malfunction is inactive
            "0100060100034f0000" +  // Restraints indicator warning is inactive
            "010006010003500000" +  // Start stop engine warning is inactive
            "010006010003510000" +  // Traction control disabled is inactive
            "010006010003520000" +  // Traction control active is inactive
            "010006010003530000" +  // Traction motor temperature warning is inactive
            "010006010003540000" +  // Tire pressure monitor system warning is inactive
            "010006010003550000" +  // Water in fuel is inactive
            "010006010003560000" +  // Tire warning front right is inactive
            "010006010003570000" +  // Tire warning front left is inactive
            "010006010003580000" +  // Tire warning rear right is inactive
            "010006010003590000" +  // Tire warning rear left is inactive
            "0100060100035a0000" +  // Tire warning system error is inactive
            "0100060100035b0000" +  // Battery low warning is inactive
            "0100060100035c0000" // Brake fluid warning is inactive
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as DashboardLights.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = DashboardLights.State.Builder()
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HIGH_BEAM, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.LOW_BEAM, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HAZARD_WARNING, ActiveState.ACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BRAKE_FAILURE, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HATCH_OPEN, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FUEL_LEVEL, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ENGINE_COOLANT_TEMPERATURE, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BATTERY_CHARGING_CONDITION, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ENGINE_OIL, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.POSITION_LIGHTS, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FRONT_FOG_LIGHT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.REAR_FOG_LIGHT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.PARK_HEATING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ENGINE_INDICATOR, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SERVICE_CALL, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRANSMISSION_FLUID_TEMPERATURE, ActiveState.ACTIVE, DashboardLight.Colour.RED)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRANSMISSION_FAILURE, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ANTI_LOCK_BRAKE_FAILURE, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.WORN_BRAKE_LININGS, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.WINDSCREEN_WASHER_FLUID, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_FAILURE, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ENGINE_OIL_LEVEL, ActiveState.ACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ENGINE_COOLANT_LEVEL, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.STEERING_FAILURE, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ESC_INDICATION, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BRAKE_LIGHTS, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ADBLUE_LEVEL, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FUEL_FILTER_DIFF_PRESSURE, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.SEAT_BELT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ADVANCED_BRAKING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ACC, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRAILER_CONNECTED, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.AIRBAG, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ESC_SWITCHED_OFF, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.LANE_DEPARTURE_WARNING_OFF, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.AIR_FILTER_MINDER, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.AIR_SUSPENSION_RIDE_CONTROL_FAULT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ALL_WHEEL_DRIVE_DISABLED, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ANTI_THEFT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BLIND_SPOT_DETECTION, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.CHARGE_SYSTEM_FAULT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.CHECK_FUEL_CAP, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.CHECK_FUEL_FILL_INLET, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.CHECK_FUEL_FILTER, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DC_TEMP_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DC_WARNING_STATUS, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_ENGINE_IDLE_SHUTDOWN, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_ENGINE_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_EXHAUST_FLUID_SYSTEM_FAULT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_EXHAUST_OVER_TEMP, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_EXHAUST_FLUID_QUALITY, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_FILTER_REGENERATION, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_PARTICULATE_FILTER, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.DIESEL_PRE_HEAT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.ELECTRIC_TRAILER_BRAKE_CONNECTION, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_CELL_MAX_VOLT_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_CELL_MIN_VOLT_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_CHARGE_ENERGY_STORAGE_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_HIGH_LEVEL_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_HIGH_TEMPERATURE_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_INSULATION_RESIST_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_JUMP_LEVEL_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_LOW_LEVEL_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_MAX_VOLT_VEH_ENERGY_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_MIN_VOLT_VEH_ENERGY_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_OVER_CHARGE_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_POOR_CELL_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.EV_BATTERY_TEMP_DIFF_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FORWARD_COLLISION_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.FUEL_DOOR_OPEN, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HILL_DESCENT_CONTROL_FAULT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HILL_START_ASSIST_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.HV_INTERLOCKING_STATUS_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.LIGHTING_SYSTEM_FAILURE, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.MALFUNCTION_INDICATOR, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.MOTOR_CONTROLLER_TEMP_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.PARK_AID_MALFUNCTION, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.PASSIVE_ENTRY_PASSIVE_START, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.POWERTRAIN_MALFUNCTION, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.RESTRAINTS_INDICATOR_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.START_STOP_ENGINE_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRACTION_CONTROL_DISABLED, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRACTION_CONTROL_ACTIVE, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TRACTION_MOTOR_TEMP_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_PRESSURE_MONITOR_SYSTEM_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.WATER_IN_FUEL, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_WARNING_FRONT_RIGHT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_WARNING_FRONT_LEFT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_WARNING_REAR_RIGHT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_WARNING_REAR_LEFT, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.TIRE_WARNING_SYSTEM_ERROR, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BATTERY_LOW_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        builder.addDashboardLight(Property(DashboardLight(DashboardLight.Name.BRAKE_FLUID_WARNING, ActiveState.INACTIVE, DashboardLight.Colour.INFO)))
        testState(builder.build())
    }
    
    private fun testState(state: DashboardLights.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getDashboardLights()[0].value?.name == DashboardLight.Name.HIGH_BEAM)
        assertTrue(state.getDashboardLights()[0].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[0].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[1].value?.name == DashboardLight.Name.LOW_BEAM)
        assertTrue(state.getDashboardLights()[1].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[1].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[2].value?.name == DashboardLight.Name.HAZARD_WARNING)
        assertTrue(state.getDashboardLights()[2].value?.state == ActiveState.ACTIVE)
        assertTrue(state.getDashboardLights()[2].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[3].value?.name == DashboardLight.Name.BRAKE_FAILURE)
        assertTrue(state.getDashboardLights()[3].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[3].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[4].value?.name == DashboardLight.Name.HATCH_OPEN)
        assertTrue(state.getDashboardLights()[4].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[4].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[5].value?.name == DashboardLight.Name.FUEL_LEVEL)
        assertTrue(state.getDashboardLights()[5].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[5].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[6].value?.name == DashboardLight.Name.ENGINE_COOLANT_TEMPERATURE)
        assertTrue(state.getDashboardLights()[6].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[6].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[7].value?.name == DashboardLight.Name.BATTERY_CHARGING_CONDITION)
        assertTrue(state.getDashboardLights()[7].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[7].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[8].value?.name == DashboardLight.Name.ENGINE_OIL)
        assertTrue(state.getDashboardLights()[8].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[8].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[9].value?.name == DashboardLight.Name.POSITION_LIGHTS)
        assertTrue(state.getDashboardLights()[9].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[9].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[10].value?.name == DashboardLight.Name.FRONT_FOG_LIGHT)
        assertTrue(state.getDashboardLights()[10].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[10].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[11].value?.name == DashboardLight.Name.REAR_FOG_LIGHT)
        assertTrue(state.getDashboardLights()[11].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[11].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[12].value?.name == DashboardLight.Name.PARK_HEATING)
        assertTrue(state.getDashboardLights()[12].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[12].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[13].value?.name == DashboardLight.Name.ENGINE_INDICATOR)
        assertTrue(state.getDashboardLights()[13].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[13].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[14].value?.name == DashboardLight.Name.SERVICE_CALL)
        assertTrue(state.getDashboardLights()[14].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[14].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[15].value?.name == DashboardLight.Name.TRANSMISSION_FLUID_TEMPERATURE)
        assertTrue(state.getDashboardLights()[15].value?.state == ActiveState.ACTIVE)
        assertTrue(state.getDashboardLights()[15].value?.colour == DashboardLight.Colour.RED)
        assertTrue(state.getDashboardLights()[16].value?.name == DashboardLight.Name.TRANSMISSION_FAILURE)
        assertTrue(state.getDashboardLights()[16].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[16].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[17].value?.name == DashboardLight.Name.ANTI_LOCK_BRAKE_FAILURE)
        assertTrue(state.getDashboardLights()[17].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[17].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[18].value?.name == DashboardLight.Name.WORN_BRAKE_LININGS)
        assertTrue(state.getDashboardLights()[18].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[18].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[19].value?.name == DashboardLight.Name.WINDSCREEN_WASHER_FLUID)
        assertTrue(state.getDashboardLights()[19].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[19].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[20].value?.name == DashboardLight.Name.TIRE_FAILURE)
        assertTrue(state.getDashboardLights()[20].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[20].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[21].value?.name == DashboardLight.Name.ENGINE_OIL_LEVEL)
        assertTrue(state.getDashboardLights()[21].value?.state == ActiveState.ACTIVE)
        assertTrue(state.getDashboardLights()[21].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[22].value?.name == DashboardLight.Name.ENGINE_COOLANT_LEVEL)
        assertTrue(state.getDashboardLights()[22].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[22].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[23].value?.name == DashboardLight.Name.STEERING_FAILURE)
        assertTrue(state.getDashboardLights()[23].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[23].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[24].value?.name == DashboardLight.Name.ESC_INDICATION)
        assertTrue(state.getDashboardLights()[24].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[24].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[25].value?.name == DashboardLight.Name.BRAKE_LIGHTS)
        assertTrue(state.getDashboardLights()[25].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[25].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[26].value?.name == DashboardLight.Name.ADBLUE_LEVEL)
        assertTrue(state.getDashboardLights()[26].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[26].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[27].value?.name == DashboardLight.Name.FUEL_FILTER_DIFF_PRESSURE)
        assertTrue(state.getDashboardLights()[27].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[27].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[28].value?.name == DashboardLight.Name.SEAT_BELT)
        assertTrue(state.getDashboardLights()[28].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[28].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[29].value?.name == DashboardLight.Name.ADVANCED_BRAKING)
        assertTrue(state.getDashboardLights()[29].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[29].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[30].value?.name == DashboardLight.Name.ACC)
        assertTrue(state.getDashboardLights()[30].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[30].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[31].value?.name == DashboardLight.Name.TRAILER_CONNECTED)
        assertTrue(state.getDashboardLights()[31].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[31].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[32].value?.name == DashboardLight.Name.AIRBAG)
        assertTrue(state.getDashboardLights()[32].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[32].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[33].value?.name == DashboardLight.Name.ESC_SWITCHED_OFF)
        assertTrue(state.getDashboardLights()[33].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[33].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[34].value?.name == DashboardLight.Name.LANE_DEPARTURE_WARNING_OFF)
        assertTrue(state.getDashboardLights()[34].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[34].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[35].value?.name == DashboardLight.Name.AIR_FILTER_MINDER)
        assertTrue(state.getDashboardLights()[35].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[35].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[36].value?.name == DashboardLight.Name.AIR_SUSPENSION_RIDE_CONTROL_FAULT)
        assertTrue(state.getDashboardLights()[36].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[36].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[37].value?.name == DashboardLight.Name.ALL_WHEEL_DRIVE_DISABLED)
        assertTrue(state.getDashboardLights()[37].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[37].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[38].value?.name == DashboardLight.Name.ANTI_THEFT)
        assertTrue(state.getDashboardLights()[38].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[38].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[39].value?.name == DashboardLight.Name.BLIND_SPOT_DETECTION)
        assertTrue(state.getDashboardLights()[39].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[39].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[40].value?.name == DashboardLight.Name.CHARGE_SYSTEM_FAULT)
        assertTrue(state.getDashboardLights()[40].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[40].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[41].value?.name == DashboardLight.Name.CHECK_FUEL_CAP)
        assertTrue(state.getDashboardLights()[41].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[41].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[42].value?.name == DashboardLight.Name.CHECK_FUEL_FILL_INLET)
        assertTrue(state.getDashboardLights()[42].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[42].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[43].value?.name == DashboardLight.Name.CHECK_FUEL_FILTER)
        assertTrue(state.getDashboardLights()[43].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[43].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[44].value?.name == DashboardLight.Name.DC_TEMP_WARNING)
        assertTrue(state.getDashboardLights()[44].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[44].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[45].value?.name == DashboardLight.Name.DC_WARNING_STATUS)
        assertTrue(state.getDashboardLights()[45].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[45].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[46].value?.name == DashboardLight.Name.DIESEL_ENGINE_IDLE_SHUTDOWN)
        assertTrue(state.getDashboardLights()[46].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[46].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[47].value?.name == DashboardLight.Name.DIESEL_ENGINE_WARNING)
        assertTrue(state.getDashboardLights()[47].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[47].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[48].value?.name == DashboardLight.Name.DIESEL_EXHAUST_FLUID_SYSTEM_FAULT)
        assertTrue(state.getDashboardLights()[48].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[48].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[49].value?.name == DashboardLight.Name.DIESEL_EXHAUST_OVER_TEMP)
        assertTrue(state.getDashboardLights()[49].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[49].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[50].value?.name == DashboardLight.Name.DIESEL_EXHAUST_FLUID_QUALITY)
        assertTrue(state.getDashboardLights()[50].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[50].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[51].value?.name == DashboardLight.Name.DIESEL_FILTER_REGENERATION)
        assertTrue(state.getDashboardLights()[51].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[51].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[52].value?.name == DashboardLight.Name.DIESEL_PARTICULATE_FILTER)
        assertTrue(state.getDashboardLights()[52].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[52].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[53].value?.name == DashboardLight.Name.DIESEL_PRE_HEAT)
        assertTrue(state.getDashboardLights()[53].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[53].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[54].value?.name == DashboardLight.Name.ELECTRIC_TRAILER_BRAKE_CONNECTION)
        assertTrue(state.getDashboardLights()[54].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[54].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[55].value?.name == DashboardLight.Name.EV_BATTERY_CELL_MAX_VOLT_WARNING)
        assertTrue(state.getDashboardLights()[55].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[55].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[56].value?.name == DashboardLight.Name.EV_BATTERY_CELL_MIN_VOLT_WARNING)
        assertTrue(state.getDashboardLights()[56].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[56].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[57].value?.name == DashboardLight.Name.EV_BATTERY_CHARGE_ENERGY_STORAGE_WARNING)
        assertTrue(state.getDashboardLights()[57].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[57].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[58].value?.name == DashboardLight.Name.EV_BATTERY_HIGH_LEVEL_WARNING)
        assertTrue(state.getDashboardLights()[58].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[58].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[59].value?.name == DashboardLight.Name.EV_BATTERY_HIGH_TEMPERATURE_WARNING)
        assertTrue(state.getDashboardLights()[59].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[59].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[60].value?.name == DashboardLight.Name.EV_BATTERY_INSULATION_RESIST_WARNING)
        assertTrue(state.getDashboardLights()[60].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[60].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[61].value?.name == DashboardLight.Name.EV_BATTERY_JUMP_LEVEL_WARNING)
        assertTrue(state.getDashboardLights()[61].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[61].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[62].value?.name == DashboardLight.Name.EV_BATTERY_LOW_LEVEL_WARNING)
        assertTrue(state.getDashboardLights()[62].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[62].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[63].value?.name == DashboardLight.Name.EV_BATTERY_MAX_VOLT_VEH_ENERGY_WARNING)
        assertTrue(state.getDashboardLights()[63].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[63].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[64].value?.name == DashboardLight.Name.EV_BATTERY_MIN_VOLT_VEH_ENERGY_WARNING)
        assertTrue(state.getDashboardLights()[64].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[64].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[65].value?.name == DashboardLight.Name.EV_BATTERY_OVER_CHARGE_WARNING)
        assertTrue(state.getDashboardLights()[65].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[65].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[66].value?.name == DashboardLight.Name.EV_BATTERY_POOR_CELL_WARNING)
        assertTrue(state.getDashboardLights()[66].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[66].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[67].value?.name == DashboardLight.Name.EV_BATTERY_TEMP_DIFF_WARNING)
        assertTrue(state.getDashboardLights()[67].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[67].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[68].value?.name == DashboardLight.Name.FORWARD_COLLISION_WARNING)
        assertTrue(state.getDashboardLights()[68].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[68].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[69].value?.name == DashboardLight.Name.FUEL_DOOR_OPEN)
        assertTrue(state.getDashboardLights()[69].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[69].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[70].value?.name == DashboardLight.Name.HILL_DESCENT_CONTROL_FAULT)
        assertTrue(state.getDashboardLights()[70].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[70].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[71].value?.name == DashboardLight.Name.HILL_START_ASSIST_WARNING)
        assertTrue(state.getDashboardLights()[71].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[71].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[72].value?.name == DashboardLight.Name.HV_INTERLOCKING_STATUS_WARNING)
        assertTrue(state.getDashboardLights()[72].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[72].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[73].value?.name == DashboardLight.Name.LIGHTING_SYSTEM_FAILURE)
        assertTrue(state.getDashboardLights()[73].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[73].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[74].value?.name == DashboardLight.Name.MALFUNCTION_INDICATOR)
        assertTrue(state.getDashboardLights()[74].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[74].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[75].value?.name == DashboardLight.Name.MOTOR_CONTROLLER_TEMP_WARNING)
        assertTrue(state.getDashboardLights()[75].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[75].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[76].value?.name == DashboardLight.Name.PARK_AID_MALFUNCTION)
        assertTrue(state.getDashboardLights()[76].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[76].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[77].value?.name == DashboardLight.Name.PASSIVE_ENTRY_PASSIVE_START)
        assertTrue(state.getDashboardLights()[77].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[77].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[78].value?.name == DashboardLight.Name.POWERTRAIN_MALFUNCTION)
        assertTrue(state.getDashboardLights()[78].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[78].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[79].value?.name == DashboardLight.Name.RESTRAINTS_INDICATOR_WARNING)
        assertTrue(state.getDashboardLights()[79].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[79].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[80].value?.name == DashboardLight.Name.START_STOP_ENGINE_WARNING)
        assertTrue(state.getDashboardLights()[80].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[80].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[81].value?.name == DashboardLight.Name.TRACTION_CONTROL_DISABLED)
        assertTrue(state.getDashboardLights()[81].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[81].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[82].value?.name == DashboardLight.Name.TRACTION_CONTROL_ACTIVE)
        assertTrue(state.getDashboardLights()[82].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[82].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[83].value?.name == DashboardLight.Name.TRACTION_MOTOR_TEMP_WARNING)
        assertTrue(state.getDashboardLights()[83].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[83].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[84].value?.name == DashboardLight.Name.TIRE_PRESSURE_MONITOR_SYSTEM_WARNING)
        assertTrue(state.getDashboardLights()[84].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[84].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[85].value?.name == DashboardLight.Name.WATER_IN_FUEL)
        assertTrue(state.getDashboardLights()[85].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[85].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[86].value?.name == DashboardLight.Name.TIRE_WARNING_FRONT_RIGHT)
        assertTrue(state.getDashboardLights()[86].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[86].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[87].value?.name == DashboardLight.Name.TIRE_WARNING_FRONT_LEFT)
        assertTrue(state.getDashboardLights()[87].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[87].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[88].value?.name == DashboardLight.Name.TIRE_WARNING_REAR_RIGHT)
        assertTrue(state.getDashboardLights()[88].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[88].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[89].value?.name == DashboardLight.Name.TIRE_WARNING_REAR_LEFT)
        assertTrue(state.getDashboardLights()[89].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[89].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[90].value?.name == DashboardLight.Name.TIRE_WARNING_SYSTEM_ERROR)
        assertTrue(state.getDashboardLights()[90].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[90].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[91].value?.name == DashboardLight.Name.BATTERY_LOW_WARNING)
        assertTrue(state.getDashboardLights()[91].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[91].value?.colour == DashboardLight.Colour.INFO)
        assertTrue(state.getDashboardLights()[92].value?.name == DashboardLight.Name.BRAKE_FLUID_WARNING)
        assertTrue(state.getDashboardLights()[92].value?.state == ActiveState.INACTIVE)
        assertTrue(state.getDashboardLights()[92].value?.colour == DashboardLight.Colour.INFO)
    }
    
    @Test fun testGetDashboardLights() {
        val defaultGetterBytes = Bytes(COMMAND_HEADER + "006100")
        val defaultGetter = DashboardLights.GetDashboardLights()
        assertTrue(defaultGetter == defaultGetterBytes)
        assertTrue(defaultGetter.getPropertyIdentifiers().isEmpty())
    }
    
    @Test fun testGetDashboardLightsAvailabilityAll() {
        val bytes = Bytes(COMMAND_HEADER + "006102")
        val created = DashboardLights.GetDashboardLightsAvailability()
        assertTrue(created.identifier == Identifier.DASHBOARD_LIGHTS)
        assertTrue(created.type == Type.GET_AVAILABILITY)
        assertTrue(created.getPropertyIdentifiers().isEmpty())
        assertTrue(created == bytes)
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as DashboardLights.GetDashboardLightsAvailability
        assertTrue(resolved.identifier == Identifier.DASHBOARD_LIGHTS)
        assertTrue(resolved.type == Type.GET_AVAILABILITY)
        assertTrue(resolved.getPropertyIdentifiers().isEmpty())
        assertTrue(resolved == bytes)
    }
    
}