package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateSolarCharging;
import com.highmobility.autoapi.AuthenticateHomeCharger;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.EnableDisableWifiHotspot;
import com.highmobility.autoapi.GetHomeChargerState;
import com.highmobility.autoapi.HomeChargerState;
import com.highmobility.autoapi.SetChargeCurrent;
import com.highmobility.autoapi.SetPriceTariffs;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.autoapi.value.NetworkSecurity;
import com.highmobility.autoapi.value.homecharger.AuthenticationMechanism;
import com.highmobility.autoapi.value.homecharger.Charging;
import com.highmobility.autoapi.value.homecharger.PlugType;
import com.highmobility.autoapi.value.homecharger.PriceTariff;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeChargerTest {
    Bytes bytes = new Bytes(
            "006001" +
                    "01000401000102" +
                    "02000401000101" +
                    "03000401000101" +
                    "04000701000441380000" +
                    "05000401000101" +
                    "08000401000101" +
                    "09000F01000C436861726765722037363132" +
                    "0A000401000103" +
                    "0B000D01000A5A57337641524E554265" +
                    "0D000401000101" +
                    "0E00070100043F000000" +
                    "0F00070100043F800000" +
                    "10000701000400000000" +
                    "110013010010404A428F9F44D445402ACF562174C4CE" +
                    "12000C010009004090000003455552" +
                    "12000F01000C023E99999A06526970706C65"
    );

    // charge current ok, after that should be max charge current

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        HomeChargerState state = (HomeChargerState) command;
        testState(state);
    }

    private void testState(HomeChargerState state) {
        assertTrue(state.getCharging().getValue() == Charging.CHARGING);
        assertTrue(state.getAuthenticationMechanism().getValue() == AuthenticationMechanism.APP);
        assertTrue(state.getPlugType().getValue() == PlugType.TYPE_TWO);
        assertTrue(state.getChargingPower().getValue() == 11.5f);
        assertTrue(state.isSolarChargingActive().getValue() == true);

        assertTrue(state.isHotspotEnabled().getValue() == true);
        assertTrue(state.getHotspotSsid().getValue().equals("Charger 7612"));
        assertTrue(state.getHotspotSecurity().getValue() == NetworkSecurity.WPA2_PERSONAL);
        assertTrue(state.getHotspotPassword().getValue().equals("ZW3vARNUBe"));

        assertTrue(state.isAuthenticated().getValue() == true);
        assertTrue(state.getChargeCurrent().getValue() == .5f);
        assertTrue(state.getMaximumChargeCurrent().getValue() == 1f);
        assertTrue(state.getMinimumChargeCurrent().getValue() == 0f);

        assertTrue(state.getCoordinates().getValue().getLatitude() == 52.520008);
        assertTrue(state.getCoordinates().getValue().getLongitude() == 13.404954);

        assertTrue(state.getPriceTariff(PriceTariff.PricingType.STARTING_FEE).getValue().getPrice() == 4.5f);
        assertTrue(state.getPriceTariff(PriceTariff.PricingType.STARTING_FEE).getValue().getCurrency().equals("EUR"));

        assertTrue(state.getPriceTariff(PriceTariff.PricingType.PER_KWH).getValue().getPrice() == .3f);
        assertTrue(state.getPriceTariff(PriceTariff.PricingType.PER_KWH).getValue().getCurrency()
                .equals("Ripple"));

        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        HomeChargerState.Builder builder = new HomeChargerState.Builder();

        builder.setCharging(new Property(Charging.CHARGING));
        builder.setAuthenticationMechanism(new Property(AuthenticationMechanism.APP));
        builder.setPlugType(new Property(PlugType.TYPE_TWO));
        builder.setChargingPower(new Property(11.5f));
        builder.setSolarChargingActive(new Property(true));
        builder.setHotspotEnabled(new Property(true));
        builder.setHotspotSsid(new Property("Charger 7612"));
        builder.setHotspotSecurity(new Property(NetworkSecurity.WPA2_PERSONAL));
        builder.setHotspotPassword(new Property("ZW3vARNUBe"));
        builder.setAuthenticated(new Property(true));
        builder.setChargeCurrent(new Property(.5f));
        builder.setMaximumChargeCurrent(new Property(1f));
        builder.setMinimumChargeCurrent(new Property(0f));
        builder.setCoordinates(new Property(new Coordinates(52.520008d, 13.404954d)));

        builder.addPriceTariff(new Property(new PriceTariff(
                PriceTariff.PricingType.STARTING_FEE, "EUR", 4.5f)));

        builder.addPriceTariff(new Property(new PriceTariff(
                PriceTariff.PricingType.PER_KWH, "Ripple", .3f)));

        HomeChargerState state = builder.build();
        testState(state);
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("006000");
        byte[] commandBytes = new GetHomeChargerState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void setChargeCurrent() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(
                "006012" +
                        "0100070100043f000000"
        );

        byte[] commandBytes = new SetChargeCurrent(.5f).getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        SetChargeCurrent command = (SetChargeCurrent) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getCurrent() == .5f);
    }

    @Test public void setPriceTariffs() {
        Bytes bytes = new Bytes(
                "006013" +
                        "0C000C010009004090000003455552" +
                        "0C000C010009023e99999a03455552");

        Property[] tariffs = new Property[2];

        tariffs[0] = new Property(new PriceTariff(PriceTariff.PricingType.STARTING_FEE, "EUR",
                4.5f));
        tariffs[1] = new Property(new PriceTariff(PriceTariff.PricingType.PER_KWH, "EUR",
                .3f));

        Command cmd = new SetPriceTariffs(tariffs);
        assertTrue(TestUtils.bytesTheSame(cmd, bytes));

        SetPriceTariffs command = (SetPriceTariffs) CommandResolver.resolve(bytes);

        assertTrue(command.getPriceTariffs().length == 2);
        assertTrue(command.getPriceTariff(PriceTariff.PricingType.STARTING_FEE).getValue()
                .getCurrency().equals("EUR"));
        assertTrue(command.getPriceTariff(PriceTariff.PricingType.STARTING_FEE).getValue()
                .getPrice() == 4.5f);

        assertTrue(command.getPriceTariff(PriceTariff.PricingType.PER_KWH).getValue().getCurrency
                ().equals("EUR"));
        assertTrue(command.getPriceTariff(PriceTariff.PricingType.PER_KWH).getValue().getPrice()
                == .3f);
    }

    @Test public void setPriceTariffs0Properties() {
        Bytes bytes = new Bytes("006013");
        Property[] tariffs = new Property[0];
        Command cmd = new SetPriceTariffs(tariffs);
        assertTrue(TestUtils.bytesTheSame(cmd, bytes));
        SetPriceTariffs command = (SetPriceTariffs) CommandResolver.resolve(bytes);
        assertTrue(command.getPriceTariffs().length == 0);
    }

    @Test
    public void failSamePriceTariffTypes() {
        Property[] tariffs = new Property[2];

        tariffs[0] = new Property(new PriceTariff(PriceTariff.PricingType.PER_KWH, "EUR",
                4.5f));
        tariffs[1] = new Property(new PriceTariff(PriceTariff.PricingType.PER_KWH, "EUR",
                .3f));
        assertThrows(IllegalArgumentException.class, () -> {
            new SetPriceTariffs(tariffs).getByteArray();
        });
    }

    @Test public void accept1CharTariff() {
        PriceTariff tariff = new PriceTariff(PriceTariff.PricingType.PER_KWH, "E", 4.5f);
        assertTrue(tariff.getCurrency().equals("E"));
    }

    @Test public void activateSolarCharging() {
        Bytes waitingForBytes = new Bytes("006014" +
                "01000401000101");
        byte[] commandBytes = new ActivateDeactivateSolarCharging(true).getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));

        ActivateDeactivateSolarCharging command = (ActivateDeactivateSolarCharging)
                CommandResolver.resolve(waitingForBytes);
        assertTrue(command.activate().getValue() == true);
    }

    @Test public void enableWifi() {
        Bytes waitingForBytes = new Bytes("006015" +
                "01000401000100");

        byte[] commandBytes = new EnableDisableWifiHotspot(false).getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));

        EnableDisableWifiHotspot command = (EnableDisableWifiHotspot) CommandResolver.resolve
                (waitingForBytes);
        assertTrue(command.enable().getValue() == false);
    }

    @Test public void authenticate() {
        Bytes waitingForBytes = new Bytes("006016" +
                "01000401000100");

        Bytes commandBytes = new AuthenticateHomeCharger(false);
        assertTrue(waitingForBytes.equals(commandBytes));

        AuthenticateHomeCharger command = (AuthenticateHomeCharger) CommandResolver.resolve
                (waitingForBytes);
        assertTrue(command.getAuthenticate().getValue() == false);
    }

    @Test public void failsWherePropertiesMandatory() {
        TestUtils.errorLogExpected(4, () -> {
            assertTrue(CommandResolver.resolve(SetChargeCurrent.TYPE.getIdentifierAndType()).getClass
                    () == Command.class);
            assertTrue(CommandResolver.resolve(ActivateDeactivateSolarCharging.TYPE
                    .getIdentifierAndType()).getClass() == Command.class);
            assertTrue(CommandResolver.resolve(EnableDisableWifiHotspot.TYPE.getIdentifierAndType())
                    .getClass() == Command.class);
            assertTrue(CommandResolver.resolve(AuthenticateHomeCharger.TYPE.getIdentifierAndType())
                    .getClass() == Command.class);
        });
    }
}
