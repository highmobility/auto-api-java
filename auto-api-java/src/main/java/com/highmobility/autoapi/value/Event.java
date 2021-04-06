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
package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.ByteEnum;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;


public enum Event implements ByteEnum {
    /**
     * Sent every time when the webhook is configured or changed.
     */
    PING((byte) 0x00),
    /**
     * Sent every time a vehicle starts a trip.
     */
    TRIP_STARTED((byte) 0x01),
    /**
     * Sent when a vehicle ends a trip.
     */
    TRIP_ENDED((byte) 0x02),
    /**
     * Sent when the vehicle location changes.
     */
    VEHICLE_LOCATION_CHANGED((byte) 0x03),
    /**
     * Sent when the authorization status changes.
     */
    AUTHORIZATION_CHANGED((byte) 0x04),
    /**
     * Sent when the tire pressure changed to low or too high.
     */
    TIRE_PRESSURE_CHANGED((byte) 0x05),
    /**
     * Sent when the vehicle’s acceleration rate exceeds the configurable limit for hard acceleration for a duration exceeding the configurable time duration limit.
     */
    HARSH_ACCELERATION_TRIGGERED((byte) 0x06),
    /**
     * Sent when the vehicle’s acceleration pedal position exceeds the configurable limit for hard acceleration for a duration exceeding the configurable time duration limit.
     */
    HARSH_ACCELERATION_PEDAL_POSITION_TRIGGERED((byte) 0x07),
    /**
     * Indicates if the vehicle’s deceleration rate exceeds the configurable limit for harsh braking for a duration exceeding the configurable time limit.
     */
    HARSH_BRAKING_TRIGGERED((byte) 0x08),
    /**
     * Indicates if the vehicle’s lateral acceleration rate exceeds the configurable limit for harsh cornering for a duration exceeding the configurable time duration limit.
     */
    HARSH_CORNERING_TRIGGERED((byte) 0x09),
    /**
     * Indicates a seat belt is buckled while the vehicle is moving.
     */
    SEAT_BELT_TRIGGERED((byte) 0x0a),
    /**
     * Triggered when the property "condition_based_services" in Diagnostics capability changes.
     */
    MAINTENANCE_CHANGED((byte) 0x0b),
    /**
     * Triggered when the warning/malfunction indicator lights change.
     */
    DASHBOARD_LIGHTS_CHANGED((byte) 0x0c),
    /**
     * Triggered when the ignition state changes, e.g. at the beginning and the end of a trip.
     */
    IGNITION_CHANGED((byte) 0x0d),
    /**
     * Triggered when accident assistance call is triggered, either manually by the driver or automatically by the vehicle.
     */
    ACCIDENT_REPORTED((byte) 0x0e),
    /**
     * Triggered when intelligent emergency call is triggered.
     */
    EMERGENCY_REPORTED((byte) 0x0f),
    /**
     * Triggered when the driver calls the roadside assistance service.
     */
    BREAKDOWN_REPORTED((byte) 0x10),
    /**
     * Triggered when the 12V battery runs low
     */
    BATTERY_GUARD_WARNING((byte) 0x11),
    /**
     * Triggered when the engine state changes.
     */
    ENGINE_CHANGED((byte) 0x12),
    /**
     * Triggered when the vehicle fleet clearance has changed.
     */
    FLEET_CLEARANCE_CHANGED((byte) 0x13);

    public static Event fromByte(byte byteValue) throws CommandParseException {
        Event[] values = Event.values();

        for (int i = 0; i < values.length; i++) {
            Event state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException(
            enumValueDoesNotExist(Event.class.getSimpleName(), byteValue)
        );
    }

    private final byte value;

    Event(byte value) {
        this.value = value;
    }

    @Override public byte getByte() {
        return value;
    }
}