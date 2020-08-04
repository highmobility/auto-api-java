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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ConditionBasedService;
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.autoapi.value.measurement.Length;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MaintenanceTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "003401" +
//                    "01000D01000A0703407f500000000000" + // 501.0 days until next service
//                    "02000D01000A120440acc20000000000" + // 3'681km until next service
            "03000401000103" +
//                    "04000D01000A07054014000000000000" + // 5 months until exhaust inspection
            "05000401000102" +
            "06000D01000A120440b3880000000000" + // Service distance threshold is 5000.0km
            "07000D01000A07044010000000000000" + // Service time threshold is 4 weeks
            "08000B01000800000160E0EA1388" +
            "09000B01000800000160E1560840" +
            "0A000B01000800000160E0EA1388" +
            "0B004401004107E305000300000B4272616B6520666C756964002C4E657874206368616E676520617420737065636966696564206461746520617420746865206C61746573742E" +
            "0B005301005007E303002001001256656869636C6520696E7370656374696F6E00344E657874206D616E6461746F72792076656869636C6520696E7370656374696F6E206F6E2073706563696669656420646174652E" +
            "0C000B01000800000160E1560840" +
            "0D000D01000A0703407f500000000000" + // 501.0 days until next service
            "0E000D01000A120440acc20000000000" + // 3'681km until next service
            "0F000D01000A07054014000000000000" // 5 months until exhaust inspection
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        testState((Maintenance.State) command);
    }

    private void testState(Maintenance.State state) {
        // level8
        assertTrue(state.getCbsReportsCount().getValue() == 3);
        assertTrue(state.getTeleserviceAvailability().getValue() == Maintenance.TeleserviceAvailability.SUCCESSFUL);
        assertTrue(state.getServiceDistanceThreshold().getValue().getValue() == 5000d);
        assertTrue(state.getServiceTimeThreshold().getValue().getValue() == 4d);

        assertTrue(TestUtils.dateIsSame(state.getAutomaticTeleserviceCallDate().getValue(),
                "2018-01-10T16:32:05"));

        assertTrue(TestUtils.dateIsSame(state.getTeleserviceBatteryCallDate().getValue(),
                "2018-01-10T18:30:00"));

        assertTrue(TestUtils.dateIsSame(state.getNextInspectionDate().getValue(), "2018-01-10T16" +
                ":32:05"));

        assertTrue(state.getConditionBasedServices().length == 2);

        int count = 0;
        for (Property<ConditionBasedService> conditionBasedServiceProp :
                state.getConditionBasedServices()) {
            ConditionBasedService conditionBasedService = conditionBasedServiceProp.getValue();

            if ((conditionBasedService.getYear() == 2019 &&
                    conditionBasedService.getMonth() == 5 &&
                    conditionBasedService.getId() == 3 &&
                    conditionBasedService.getDueStatus() == ConditionBasedService.DueStatus.OK &&
                    conditionBasedService.getText().equals("Brake fluid") &&
                    conditionBasedService.getDescription().equals("Next change at specified date " +
                            "at the latest."))
                    ||
                    (conditionBasedService.getYear() == 2019 &&
                            conditionBasedService.getMonth() == 3 &&
                            conditionBasedService.getId() == 32 &&
                            conditionBasedService.getDueStatus() == ConditionBasedService
                                    .DueStatus.PENDING &&
                            conditionBasedService.getText().equals("Vehicle inspection") &&
                            conditionBasedService.getDescription().equals("Next mandatory vehicle" +
                                    " inspection on specified date."))
            ) {
                count++;
            }
        }

        assertTrue(count == 2);
        assertTrue(TestUtils.dateIsSame(state.getBrakeFluidChangeDate().getValue(),
                "2018-01-10T18:30:00"));

        assertTrue(state.getTimeToNextService().getValue().getValue() == 501d);
        assertTrue(state.getDistanceToNextService().getValue().getValue() == 3681d);
        assertTrue(state.getTimeToExhaustInspection().getValue().getValue() == 5d);

        assertTrue(bytesTheSame(state, bytes));
    }

    @Test
    public void get() {
        String waitingForBytes = COMMAND_HEADER + "003400";
        String commandBytes = ByteUtils.hexFromBytes(new Maintenance.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test
    public void build() {
        Maintenance.State.Builder builder = new Maintenance.State.Builder();

        builder.setCbsReportsCount(new Property(3));
        builder.setTeleserviceAvailability(new Property(Maintenance.TeleserviceAvailability.SUCCESSFUL));

        builder.setServiceDistanceThreshold(new Property(new Length(5000d, Length.Unit.KILOMETERS)));
        builder.setServiceTimeThreshold(new Property(new Duration(4d, Duration.Unit.WEEKS)));

        builder.setAutomaticTeleserviceCallDate(new Property(TestUtils.getCalendar("2018-01-10T16" +
                ":32:05")));
        builder.setTeleserviceBatteryCallDate(new Property(TestUtils.getCalendar("2018-01-10T18" +
                ":30:00")));
        builder.setNextInspectionDate(new Property(TestUtils.getCalendar("2018-01-10T16:32:05")));

        ConditionBasedService service1 = new ConditionBasedService(2019, 5, 3,
                ConditionBasedService.DueStatus.OK, "Brake fluid", "Next change at specified date" +
                " at the latest.");
        ConditionBasedService service2 = new ConditionBasedService(2019, 3, 32,
                ConditionBasedService.DueStatus.PENDING, "Vehicle inspection", "Next mandatory " +
                "vehicle inspection on specified date.");

        builder.addConditionBasedService(new Property(service1));
        builder.addConditionBasedService(new Property(service2));

        builder.setBrakeFluidChangeDate(new Property(TestUtils.getCalendar("2018-01-10T18:30:00")));

        builder.setTimeToNextService(new Property(new Duration(501d, Duration.Unit.DAYS)));
        builder.setDistanceToNextService(new Property(new Length(3681d, Length.Unit.KILOMETERS)));
        builder.setTimeToExhaustInspection(new Property(new Duration(5d, Duration.Unit.MONTHS)));

        Maintenance.State state = builder.build();
        testState(state);
    }
}