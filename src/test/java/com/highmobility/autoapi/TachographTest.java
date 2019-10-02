package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Detected;
import com.highmobility.autoapi.value.DriverCardPresent;
import com.highmobility.autoapi.value.DriverTimeState;
import com.highmobility.autoapi.value.DriverWorkingState;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TachographTest extends BaseTest {
    Bytes bytes = new Bytes
            ("006401" +
                    "0100050100020102" +
                    "0100050100020200" +
                    "0200050100020102" +
                    "0200050100020206" +
                    "0300050100020101" +
                    "0300050100020201" +
                    "04000401000101" +
                    "05000401000100" +
                    "06000401000100" +
                    "0700050100020050"
            );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        TachographState state = (TachographState) command;
        testState(state);
    }

    private void testState(TachographState state) {
        assertTrue(state.getDriverWorkingState(1).getValue().getWorkingState() == DriverWorkingState.WorkingState.WORKING);
        assertTrue(state.getDriverWorkingState(2).getValue().getWorkingState() == DriverWorkingState.WorkingState.RESTING);
        assertTrue(state.getDriverTimeState(1).getValue().getTimeState() == DriverTimeState.TimeState.FOUR_REACHED);
        assertTrue(state.getDriverTimeState(2).getValue().getTimeState() == DriverTimeState.TimeState.SIXTEEN_REACHED);

        assertTrue(state.getDriverCard(1).getValue().getCardPresent() == DriverCardPresent.CardPresent.PRESENT);
        assertTrue(state.getDriverCard(2).getValue().getCardPresent() == DriverCardPresent.CardPresent.PRESENT);

        assertTrue(state.getVehicleMotion().getValue() == Detected.DETECTED);
        assertTrue(state.getVehicleOverspeed().getValue() == TachographState.VehicleOverspeed.NO_OVERSPEED);
        assertTrue(state.getVehicleDirection().getValue() == TachographState.VehicleDirection.FORWARD);
        assertTrue(state.getVehicleSpeed().getValue() == 80);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        TachographState.Builder builder = new TachographState.Builder();

        builder.addDriversWorkingState(new Property(new DriverWorkingState(1,
                DriverWorkingState.WorkingState.WORKING)));
        builder.addDriversWorkingState(new Property(new DriverWorkingState(2,
                DriverWorkingState.WorkingState.RESTING)));

        builder.addDriversTimeState(new Property(new DriverTimeState(1,
                DriverTimeState.TimeState.FOUR_REACHED)));
        builder.addDriversTimeState(new Property(new DriverTimeState(2,
                DriverTimeState.TimeState.SIXTEEN_REACHED)));

        builder.addDriversCardsPresen(new Property(new DriverCardPresent(1,
                DriverCardPresent.CardPresent.PRESENT)));
        builder.addDriversCardsPresen(new Property(new DriverCardPresent(2,
                DriverCardPresent.CardPresent.PRESENT)));

        builder.setVehicleMotion(new Property(Detected.DETECTED));
        builder.setVehicleOverspeed(new Property(TachographState.VehicleOverspeed.NO_OVERSPEED));
        builder.setVehicleDirection(new Property(TachographState.VehicleDirection.FORWARD));
        builder.setVehicleSpeed(new Property(80));

        TachographState state = builder.build();
        testState(state);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("006400");
        byte[] commandBytes = new GetTachographState().getByteArray();

        assertTrue(waitingForBytes.equals(commandBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetTachographState);
    }
}
