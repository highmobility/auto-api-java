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
package com.highmobility.autoapi.permissions;

import com.highmobility.autoapi.Identifier;
import com.highmobility.autoapi.certificate.PermissionLocation;
import com.highmobility.value.BitLocation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutoApiPermissionsTest {
    @Test public void resetTest() {
        BitLocation location = PermissionLocation.allowCarSdkResetLocation();
        assertTrue(locationIs(location, 1, 2));
    }

    @Test public void testTrunk() {
        BitLocation read = PermissionLocation.locationFor(Identifier.TRUNK,
                PermissionLocation.Type.READ);
        assertTrue(locationIs(read, 2, 7));

        BitLocation write = PermissionLocation.locationFor(Identifier.TRUNK,
                PermissionLocation.Type.WRITE);
        assertTrue(locationIs(write, 3, 0));
    }

    @Test public void testCharging() {
        BitLocation read = PermissionLocation.locationFor(Identifier.CHARGING,
                PermissionLocation.Type.READ);
        assertTrue(locationIs(read, 3, 3));

        BitLocation write = PermissionLocation.locationFor(Identifier.CHARGING,
                PermissionLocation.Type.WRITE);
        assertTrue(locationIs(write, 3, 4));
    }

    @Test public void testHeadUnit() {
        BitLocation location = PermissionLocation.locationFor(Identifier.NOTIFICATIONS,
                PermissionLocation.Type.WRITE);
        assertTrue(locationIs(location, 4, 7));

        location = PermissionLocation.locationFor(Identifier.GRAPHICS, PermissionLocation.Type
                .WRITE);
        assertTrue(locationIs(location, 4, 7));

        location = PermissionLocation.locationFor(Identifier.MESSAGING, PermissionLocation.Type
                .WRITE);
        assertTrue(locationIs(location, 4, 7));

        location = PermissionLocation.locationFor(Identifier.VIDEO_HANDOVER, PermissionLocation
                .Type.WRITE);
        assertTrue(locationIs(location, 4, 7));
    }

    @Test public void testWindscreen() {
        BitLocation read = PermissionLocation.locationFor(Identifier.WINDSCREEN,
                PermissionLocation.Type.READ);
        assertTrue(locationIs(read, 4, 4));

        BitLocation write = PermissionLocation.locationFor(Identifier.WINDSCREEN,
                PermissionLocation.Type.WRITE);
        assertTrue(locationIs(write, 4, 5));
    }

    @Test public void windows() {
        BitLocation read = PermissionLocation.locationFor(Identifier.WINDOWS,
                PermissionLocation.Type.READ);
        assertTrue(locationIs(read, 7, 1));

        BitLocation write = PermissionLocation.locationFor(Identifier.WINDOWS,
                PermissionLocation.Type.WRITE);
        assertTrue(locationIs(write, 4, 1));
    }

    @Test public void testPowerTakeoff() {
        BitLocation read = PermissionLocation.locationFor(Identifier.POWER_TAKEOFF,
                PermissionLocation.Type.READ);
        assertTrue(locationIs(read, 10, 0));

        BitLocation write = PermissionLocation.locationFor(Identifier.POWER_TAKEOFF,
                PermissionLocation.Type.WRITE);
        assertTrue(locationIs(write, 10, 1));
    }

    @Test public void messaging() {
        BitLocation location = PermissionLocation.locationFor(Identifier.MESSAGING,
                PermissionLocation.Type.READ);
        assertTrue(locationIs(location, 6, 7));
        location = PermissionLocation.locationFor(Identifier.MESSAGING,
                PermissionLocation.Type.WRITE);
        assertTrue(locationIs(location, 4, 7));
    }

    @Test public void graphics() {
        BitLocation location = PermissionLocation.locationFor(Identifier.GRAPHICS,
                PermissionLocation.Type.READ);
        assertTrue(location == null);
        location = PermissionLocation.locationFor(Identifier.GRAPHICS,
                PermissionLocation.Type.WRITE);
        assertTrue(locationIs(location, 4, 7));
    }

    @Test public void textInput() {
        BitLocation location = PermissionLocation.locationFor(Identifier.TEXT_INPUT,
                PermissionLocation.Type.READ);
        assertTrue(location == null);
        location = PermissionLocation.locationFor(Identifier.TEXT_INPUT,
                PermissionLocation.Type.WRITE);
        assertTrue(locationIs(location, 4, 7));
    }

    @Test public void race() {
        BitLocation location = PermissionLocation.locationFor(Identifier.RACE,
                PermissionLocation.Type.READ);
        assertTrue(locationIs(location, 7, 4));
        location = PermissionLocation.locationFor(Identifier.OFFROAD,
                PermissionLocation.Type.READ);
        assertTrue(locationIs(location, 7, 5));
    }

    @Test public void fueling() {
        BitLocation location = PermissionLocation.locationFor(Identifier.FUELING,
                PermissionLocation.Type.READ);
        assertTrue(locationIs(location, 8, 5));
        location = PermissionLocation.locationFor(Identifier.FUELING,
                PermissionLocation.Type.WRITE);
        assertTrue(locationIs(location, 5, 5));
    }

    @Test public void environment() {
        BitLocation location = PermissionLocation.locationFor(Identifier.WEATHER_CONDITIONS,
                PermissionLocation.Type.READ);
        assertTrue(locationIs(location, 8, 4));
        location = PermissionLocation.locationFor(Identifier.LIGHT_CONDITIONS,
                PermissionLocation.Type.READ);
        assertTrue(locationIs(location, 8, 4));
    }

    @Test public void startStop() {
        BitLocation location = PermissionLocation.locationFor(Identifier.ENGINE_START_STOP,
                PermissionLocation.Type.READ);
        assertTrue(locationIs(location, 9, 5));
        location = PermissionLocation.locationFor(Identifier.ENGINE_START_STOP,
                PermissionLocation.Type.WRITE);
        assertTrue(locationIs(location, 9, 6));
    }

    boolean locationIs(BitLocation location, int byteLocation, int bitLocation) {
        return location.getByteLocation() == byteLocation && location.getBitLocation() ==
                bitLocation;
    }

}
