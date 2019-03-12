package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetMaintenanceState;
import com.highmobility.autoapi.MaintenanceState;
import com.highmobility.autoapi.property.Property;
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
                    "01000501000201F5" +
                    "020006010003000E61" +
                    "03000401000103" +
                    "04000401000105" +
                    "05000401000102" +
                    "06000501000201F4" +
                    "07000401000104" +
                    "08000B01000800000160E0EA1388" +
                    "09000B01000800000160E1560840" +
                    "0A000B01000800000160E0EA1388" +
                    "0B00430100401305000300000B4272616B6520666C756964002C4E657874206368616E676520617420737065636966696564206461746520617420746865206C61746573742E" +
                    "0B005201004F1303002001001256656869636C6520696E7370656374696F6E00344E657874206D616E6461746F72792076656869636C6520696E7370656374696F6E206F6E2073706563696669656420646174652E" +
                    "0C000B01000800000160E1560840"
    );

    @Test
    public void state() throws ParseException {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == MaintenanceState.class);
        MaintenanceState state = (MaintenanceState) command;
        assertTrue(state.getDaysToNextService().getValue() == 501);
        assertTrue(state.getKilometersToNextService().getValue() == 3681);

        // level8
        assertTrue(state.getCbsReportsCount().getValue() == 3);
        assertTrue(state.getMonthsToExhaustInspection().getValue() == 5);
        assertTrue(state.getTeleserviceAvailability().getValue() == TeleserviceAvailability.SUCCESSFUL);
        assertTrue(state.getServiceDistanceThreshold().getValue() == 500);
        assertTrue(state.getServiceTimeThreshold().getValue() == 4);

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
                    conditionBasedService.getIdentifier() == 3 &&
                    conditionBasedService.getDueStatus() == ConditionBasedService.DueStatus.OK &&
                    conditionBasedService.getText().equals("Brake fluid") &&
                    conditionBasedService.getDescription().equals("Next change at specified date " +
                            "at the latest."))
                    ||
                    (conditionBasedService.getYear() == 2019 &&
                            conditionBasedService.getMonth() == 3 &&
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
        assertTrue(TestUtils.dateIsSame(state.getBrakeFluidChangeDate().getValue(),
                "2018-01-10T18:30:00"));
    }

    @Test public void get() {
        String waitingForBytes = "003400";
        String commandBytes = ByteUtils.hexFromBytes(new GetMaintenanceState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("003401");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((MaintenanceState) state).getKilometersToNextService().getValue() == null);
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

        builder.setAutomaticTeleserviceCallDate(TestUtils.getValue("2018-11-23T10:33:50+0100"));
        builder.setTeleserviceBatteryCallDate(TestUtils.getValue("2018-11-23T10:33:50+0100"));
        builder.setNextInspectionDate(TestUtils.getValue("2018-11-23T10:33:50+0100"));

        ConditionBasedService service1 = new ConditionBasedService(2019, 5, 3,
                ConditionBasedService.DueStatus.OK, "Brake fluid");
        ConditionBasedService service2 = new ConditionBasedService(2019, 3, 32,
                ConditionBasedService.DueStatus.PENDING, "Vehicle inspection");

        builder.addConditionBasedService(service1);
        builder.addConditionBasedService(service2);

        builder.setBrakeFluidChangeDate(TestUtils.getValue("2018-11-23T10:33:50+0100"));

        MaintenanceState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));*/
    }
}