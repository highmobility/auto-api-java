package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.autoapi.value.EnabledState;
import com.highmobility.autoapi.value.NetworkSecurity;
import com.highmobility.autoapi.value.PriceTariff;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeChargerTest extends BaseTest {
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
                    "12000D01000A00409000000003455552" +
                    "12001001000D023E99999A0006526970706C65"
    );

    // charge current ok, after that should be max charge current

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        HomeChargerState state = (HomeChargerState) command;
        testState(state);
    }

    private void testState(HomeChargerState state) {
        assertTrue(state.getChargingStatus().getValue() == HomeChargerState.ChargingStatus.CHARGING);
        assertTrue(state.getAuthenticationMechanism().getValue() == HomeChargerState.AuthenticationMechanism.APP);
        assertTrue(state.getPlugType().getValue() == HomeChargerState.PlugType.TYPE_2);
        assertTrue(state.getChargingPowerKW().getValue() == 11.5f);
        assertTrue(state.getSolarCharging().getValue() == ActiveState.ACTIVE);

        assertTrue(state.getWifiHotspotEnabled().getValue() == EnabledState.ENABLED);
        assertTrue(state.getWifiHotspotSSID().getValue().equals("Charger 7612"));
        assertTrue(state.getWiFiHotspotSecurity().getValue() == NetworkSecurity.WPA2_PERSONAL);
        assertTrue(state.getWiFiHotspotPassword().getValue().equals("ZW3vARNUBe"));

        assertTrue(state.getAuthenticationState().getValue() == HomeChargerState.AuthenticationState.AUTHENTICATED);
        assertTrue(state.getChargeCurrentDC().getValue() == .5f);
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

        builder.setChargingStatus(new Property(HomeChargerState.ChargingStatus.CHARGING));
        builder.setAuthenticationMechanism(new Property(HomeChargerState.AuthenticationMechanism.APP));
        builder.setPlugType(new Property(HomeChargerState.PlugType.TYPE_2));
        builder.setChargingPowerKW(new Property(11.5f));
        builder.setSolarCharging(new Property(ActiveState.ACTIVE));
        builder.setWifiHotspotEnabled(new Property(EnabledState.ENABLED));
        builder.setWifiHotspotSSID(new Property("Charger 7612"));
        builder.setWiFiHotspotSecurity(new Property(NetworkSecurity.WPA2_PERSONAL));
        builder.setWiFiHotspotPassword(new Property("ZW3vARNUBe"));
        builder.setAuthenticationState(new Property(HomeChargerState.AuthenticationState.AUTHENTICATED));
        builder.setChargeCurrentDC(new Property(.5f));
        builder.setMaximumChargeCurrent(new Property(1f));
        builder.setMinimumChargeCurrent(new Property(0f));
        builder.setCoordinates(new Property(new Coordinates(52.520008d, 13.404954d)));

        builder.addPriceTariff(new Property(new PriceTariff(
                PriceTariff.PricingType.STARTING_FEE, 4.5f, "EUR")));

        builder.addPriceTariff(new Property(new PriceTariff(
                PriceTariff.PricingType.PER_KWH, .3f, "Ripple")));

        HomeChargerState state = builder.build();
        testState(state);
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("006000");
        byte[] commandBytes = new GetHomeChargerState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void setChargeCurrent() {
        Bytes waitingForBytes = new Bytes("006001" +
                        "0E00070100043f000000");

        Command commandBytes = new SetChargeCurrent(.5f);
        assertTrue(bytesTheSame(commandBytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetChargeCurrent command = (SetChargeCurrent) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getChargeCurrentDC().getValue() == .5f);
    }

    @Test public void setPriceTariffs() {
        Bytes bytes = new Bytes(
                "006001" +
                        "12000D01000A00409000000003455552" +
                        "12000D01000A023e99999a0003455552");

        PriceTariff[] tariffs = new PriceTariff[2];

        tariffs[0] = new PriceTariff(PriceTariff.PricingType.STARTING_FEE, 4.5f, "EUR");
        tariffs[1] = new PriceTariff(PriceTariff.PricingType.PER_KWH, .3f, "EUR");

        Command cmd = new SetPriceTariffs(tariffs);
        assertTrue(bytesTheSame(cmd, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetPriceTariffs command = (SetPriceTariffs) CommandResolver.resolve(bytes);
        assertTrue(command.getPriceTariffs().length == 2);

        assertTrue(command.getPriceTariffs()[0].getValue().getPricingType() == PriceTariff.PricingType.STARTING_FEE);
        assertTrue(command.getPriceTariffs()[0].getValue().getCurrency().equals("EUR"));
        assertTrue(command.getPriceTariffs()[0].getValue().getPrice() == 4.5f);

        assertTrue(command.getPriceTariffs()[1].getValue().getPricingType() == PriceTariff.PricingType.PER_KWH);
        assertTrue(command.getPriceTariffs()[1].getValue().getCurrency().equals("EUR"));
        assertTrue(command.getPriceTariffs()[1].getValue().getPrice() == .3f);
    }

    // TODO: 25/09/2019 cannot build this from yml
    /*    @Test
    public void failSamePriceTariffTypes() {
        PriceTariff[] tariffs = new PriceTariff[2];
        tariffs[0] = new PriceTariff(PriceTariff.PricingType.PER_KWH, 4.5f, "EUR");
        tariffs[1] = new PriceTariff(PriceTariff.PricingType.PER_KWH, .3f, "EUR");
        assertThrows(IllegalArgumentException.class, () -> {
            new SetPriceTariffs(tariffs).getByteArray();
        });
    }*/

    @Test public void accept1CharTariff() {
        PriceTariff tariff = new PriceTariff(PriceTariff.PricingType.PER_KWH, 4.5f, "E");
        assertTrue(tariff.getCurrency().equals("E"));
    }

    @Test public void activateSolarCharging() {
        Bytes waitingForBytes = new Bytes("006001" +
                "05000401000101");
        Command commandBytes = new ActivateDeactivateSolarCharging(ActiveState.ACTIVE);
        assertTrue(bytesTheSame(commandBytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ActivateDeactivateSolarCharging command = (ActivateDeactivateSolarCharging)
                CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getSolarCharging().getValue() == ActiveState.ACTIVE);
    }

    @Test public void enableWifi() {
        Bytes waitingForBytes = new Bytes("006001" +
                "08000401000100");

        byte[] commandBytes = new EnableDisableWiFiHotspot(EnabledState.DISABLED).getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        EnableDisableWiFiHotspot command = (EnableDisableWiFiHotspot) CommandResolver.resolve
                (waitingForBytes);
        assertTrue(command.getWifiHotspotEnabled().getValue() == EnabledState.DISABLED);
    }

    @Test public void authenticate() {
        Bytes waitingForBytes = new Bytes("006001" +
                "0D000401000100");

        Bytes commandBytes =
                new AuthenticateExpire(HomeChargerState.AuthenticationState.UNAUTHENTICATED);
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        AuthenticateExpire command = (AuthenticateExpire) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getAuthenticationState().getValue() == HomeChargerState.AuthenticationState.UNAUTHENTICATED);
    }
}
