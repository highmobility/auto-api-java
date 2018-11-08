package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetMaintenanceState;
import com.highmobility.autoapi.MaintenanceState;
import com.highmobility.autoapi.property.maintenance.ConditionBasedService;
import com.highmobility.autoapi.property.maintenance.TeleserviceAvailability;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MaintenanceTest {
    Bytes bytes = new Bytes(
            "003401" +
                    "01000201F5" +
                    "020003000E61" +
                    "03000103" +
                    "04000105" +
                    "05000102" +
                    "06000201F4" +
                    "07000104" +
                    "080008120B170A2132003C" +
                    "090008120B170A2132003C" +
                    "0A0008120B170A2132003C" +
                    "0B00401305000300000B4272616B6520666C756964002C4E657874206368616E676520617420737065636966696564206461746520617420746865206C61746573742E" +
                    "0B004F1303002001001256656869636C6520696E7370656374696F6E00344E657874206D616E6461746F72792076656869636C6520696E7370656374696F6E206F6E2073706563696669656420646174652E" +
                    "0C0008120B170A2132003C"
    );

    @Test
    public void state() throws ParseException {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == MaintenanceState.class);
        MaintenanceState state = (MaintenanceState) command;
        assertTrue(state.getDaysToNextService() == 501);
        assertTrue(state.getKilometersToNextService() == 3681);

        // level8
        assertTrue(state.getCbsReportsCount() == 3);
        assertTrue(state.getMonthsToExhaustInspection() == 5);
        assertTrue(state.getTeleserviceAvailability() == TeleserviceAvailability.SUCCESSFUL);
        assertTrue(state.getServiceDistanceThreshold() == 500);
        assertTrue(state.getServiceTimeThreshold() == 4);

        assertTrue(TestUtils.dateIsSame(state.getAutomaticTeleserviceCallDate(),
                "2018-11-23T10:33:50+0100"));

        assertTrue(TestUtils.dateIsSame(state.getTeleserviceBatteryCallDate(),
                "2018-11-23T10:33:50+0100"));

        assertTrue(TestUtils.dateIsSame(state.getNextInspectionDate(), "2018-11-23T10:33:50+0100"));

        assertTrue(state.getConditionBasedServices().length == 2);
        int count = 0;
        for (ConditionBasedService conditionBasedService : state.getConditionBasedServices()) {
            if ((conditionBasedService.getDate().getYear() == 2019 &&
                    conditionBasedService.getDate().getMonth().getValue() == 5 &&
                    conditionBasedService.getIdentifier() == 3 &&
                    conditionBasedService.getDueStatus() == ConditionBasedService.DueStatus.OK &&
                    conditionBasedService.getText().equals("Brake fluid") &&
                    conditionBasedService.getDescription().equals("Next change at specified date " +
                            "at the latest."))
                    ||
                    (conditionBasedService.getDate().getYear() == 2019 &&
                            conditionBasedService.getDate().getMonth().getValue() == 3 &&
                            conditionBasedService.getIdentifier() == 32 &&
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
        assertTrue(TestUtils.dateIsSame(state.getBrakeFluidChangeDate(),
                "2018-11-23T10:33:50+0100"));
    }

    @Test public void get() {
        String waitingForBytes = "003400";
        String commandBytes = ByteUtils.hexFromBytes(new GetMaintenanceState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("003401");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((MaintenanceState) state).getKilometersToNextService() == null);
    }

    @Test public void build() throws ParseException {
        // TBODO: 29/10/2018 uncomment/fix test.
        return;
        /*MaintenanceState.Builder builder = new MaintenanceState.Builder();
        builder.setDaysToNextService(501);
        builder.setKilometersToNextService(3681);

        builder.setCbsReportsCount(3);
        builder.setMonthsToExhaustInspection(5);
        builder.setTeleserviceAvailability(TeleserviceAvailability.SUCCESSFUL);
        builder.setServiceDistanceThreshold(500);
        builder.setServiceTimeThreshold(4);

        builder.setAutomaticTeleserviceCallDate(TestUtils.getCalendar("2018-11-23T10:33:50+0100"));
        builder.setTeleserviceBatteryCallDate(TestUtils.getCalendar("2018-11-23T10:33:50+0100"));
        builder.setNextInspectionDate(TestUtils.getCalendar("2018-11-23T10:33:50+0100"));

        ConditionBasedService service1 = new ConditionBasedService(LocalDate.of(2019, 5, 1), 3,
                ConditionBasedService.DueStatus.OK, "Brake fluid");
        ConditionBasedService service2 = new ConditionBasedService(LocalDate.of(2019, 3, 1), 32,
                ConditionBasedService.DueStatus.PENDING, "Vehicle inspection");

        builder.addConditionBasedService(service1);
        builder.addConditionBasedService(service2);

        builder.setBrakeFluidChangeDate(TestUtils.getCalendar("2018-11-23T10:33:50+0100"));

        MaintenanceState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));*/
    }
}