package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ConditionBasedService;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MaintenanceTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "003401" +
                    "01000501000201F5" +
                    "02000701000400000E61" +
                    "03000401000103" +
                    "04000401000105" +
                    "05000401000102" +
                    "06000501000201F4" +
                    "07000401000104" +
                    "08000B01000800000160E0EA1388" +
                    "09000B01000800000160E1560840" +
                    "0A000B01000800000160E0EA1388" +
                    "0B004401004107E305000300000B4272616B6520666C756964002C4E657874206368616E676520617420737065636966696564206461746520617420746865206C61746573742E" +
                    "0B005301005007E303002001001256656869636C6520696E7370656374696F6E00344E657874206D616E6461746F72792076656869636C6520696E7370656374696F6E206F6E2073706563696669656420646174652E" +
                    "0C000B01000800000160E1560840"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        testState((Maintenance.State) command);
    }

    private void testState(Maintenance.State state) {
        assertTrue(state.getDaysToNextService().getValue() == 501);
        assertTrue(state.getKilometersToNextService().getValue() == 3681);

        // level8
        assertTrue(state.getCbsReportsCount().getValue() == 3);
        assertTrue(state.getMonthsToExhaustInspection().getValue() == 5);
        assertTrue(state.getTeleserviceAvailability().getValue() == Maintenance.TeleserviceAvailability.SUCCESSFUL);
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

        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "003400";
        String commandBytes = ByteUtils.hexFromBytes(new Maintenance.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        Maintenance.State.Builder builder = new Maintenance.State.Builder();
        builder.setDaysToNextService(new Property(501));
        builder.setKilometersToNextService(new Property(3681));

        builder.setCbsReportsCount(new Property(3));
        builder.setMonthsToExhaustInspection(new Property(5));
        builder.setTeleserviceAvailability(new Property(Maintenance.TeleserviceAvailability.SUCCESSFUL));
        builder.setServiceDistanceThreshold(new Property(500));
        builder.setServiceTimeThreshold(new Property(4));

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

        Maintenance.State state = builder.build();
        testState(state);
    }
}