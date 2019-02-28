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
import com.highmobility.autoapi.property.NetworkSecurity;
import com.highmobility.autoapi.property.homecharger.AuthenticationMechanism;
import com.highmobility.autoapi.property.homecharger.Charging;
import com.highmobility.autoapi.property.homecharger.PlugType;
import com.highmobility.autoapi.property.homecharger.PriceTariff;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

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

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command instanceof HomeChargerState);
        HomeChargerState state = (HomeChargerState) command;

        assertTrue(state.getCharging().getValue() == Charging.Value.CHARGING);
        assertTrue(state.getAuthenticationMechanism().getValue() == AuthenticationMechanism.Value.APP);
        assertTrue(state.getPlugType().getValue() == PlugType.Value.TYPE_TWO);
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
    }

    @Test public void stateWithTimestamp() {
        Bytes timestampBytes = bytes.concat(new Bytes("A4000911010A112200000002"));
        HomeChargerState command = (HomeChargerState) CommandResolver.resolve(timestampBytes);
        assertTrue(command.getAuthenticationMechanism().getTimestamp() != null);
    }

    @Test public void build() {
        // TBODO:
        /*HomeChargerState.Builder builder = new HomeChargerState.Builder();

        builder.setCharging(Charging.CHARGING);
        builder.setAuthenticationMechanism(AuthenticationMechanism.APP);
        builder.setPlugType(PlugType.TYPE_TWO);
        builder.setChargingPower(11.5f);
        builder.setSolarChargingActive(true);
//        builder.setCo(new CoordinatesProperty(52.520008f, 13.404954f));

        builder.setHotspotEnabled(true);
        builder.setHotspotSsid("Charger 7612");
        builder.setHotspotSecurity(NetworkSecurity.WPA2_PERSONAL);
        builder.setHotspotPassword("ZW3vARNUBe");

        builder.addPriceTariff(new PriceTariff(PriceTariff.PricingType.STARTING_FEE, "EUR", 4.5f));
        builder.addPriceTariff(new PriceTariff(PriceTariff.PricingType.PER_KWH, "EUR", .3f));
        HomeChargerState command = builder.build();
        assertTrue(Arrays.equals(command.getByteArray(), ByteUtils.bytesFromHex
                ("00600101000102020001010300010104000441380000050001010600084252147d41567ab107000C3f0000003f800000000000000800010109000C4368617267657220373631320A0001030B000A5a57337641524e5542650C000800455552409000000C0008024555523e99999a")));*/
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

        PriceTariff[] tariffs = new PriceTariff[2];

        tariffs[0] = new PriceTariff(PriceTariff.PricingType.STARTING_FEE, "EUR", 4.5f);
        tariffs[1] = new PriceTariff(PriceTariff.PricingType.PER_KWH, "EUR", .3f);

        Command cmd = new SetPriceTariffs(tariffs);
        assertTrue(TestUtils.bytesTheSame(cmd, bytes));

        SetPriceTariffs command = (SetPriceTariffs) CommandResolver.resolve(bytes);

        assertTrue(command.getPriceTariffs().length == 2);
        assertTrue(command.getPriceTariff(PriceTariff.PricingType.STARTING_FEE).getValue().getCurrency()
                .equals("EUR"));
        assertTrue(command.getPriceTariff(PriceTariff.PricingType.STARTING_FEE).getValue().getPrice() == 4.5f);

        assertTrue(command.getPriceTariff(PriceTariff.PricingType.PER_KWH).getValue().getCurrency().equals
                ("EUR"));
        assertTrue(command.getPriceTariff(PriceTariff.PricingType.PER_KWH).getValue().getPrice() == .3f);
    }

    @Test public void setPriceTariffs0Properties() {
        Bytes bytes = new Bytes("006013");
        PriceTariff[] tariffs = new PriceTariff[0];
        Command cmd = new SetPriceTariffs(tariffs);
        assertTrue(TestUtils.bytesTheSame(cmd, bytes));
        SetPriceTariffs command = (SetPriceTariffs) CommandResolver.resolve(bytes);
        assertTrue(command.getPriceTariffs().length == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failSamePriceTariffTypes() {

        PriceTariff[] tariffs = new PriceTariff[2];

        tariffs[0] = new PriceTariff(PriceTariff.PricingType.PER_KWH, "EUR", 4.5f);
        tariffs[1] = new PriceTariff(PriceTariff.PricingType.PER_KWH, "EUR", .3f);

        new SetPriceTariffs(tariffs).getByteArray();
    }

    @Test(expected = IllegalArgumentException.class)
    public void failPriceTariffCurrencyLessThan3Characters() {
        new PriceTariff(PriceTariff.PricingType.PER_KWH, "E", 4.5f);
    }

    @Test public void activateSolarCharging() {
        Bytes waitingForBytes = new Bytes("006014" +
                "01000401000101");
        byte[] commandBytes = new ActivateDeactivateSolarCharging(true).getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));

        ActivateDeactivateSolarCharging command = (ActivateDeactivateSolarCharging)
                CommandResolver.resolve(waitingForBytes);
        assertTrue(command.activate() == true);
    }

    @Test public void enableWifi() {
        Bytes waitingForBytes = new Bytes("006015" +
                "01000401000100");

        byte[] commandBytes = new EnableDisableWifiHotspot(false).getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));

        EnableDisableWifiHotspot command = (EnableDisableWifiHotspot) CommandResolver.resolve
                (waitingForBytes);
        assertTrue(command.enable() == false);
    }

    @Test public void authenticate() {
        Bytes waitingForBytes = new Bytes("006016" +
                "01000401000100");

        Bytes commandBytes = new AuthenticateHomeCharger(false);
        assertTrue(waitingForBytes.equals(commandBytes));

        AuthenticateHomeCharger command = (AuthenticateHomeCharger) CommandResolver.resolve
                (waitingForBytes);
        assertTrue(command.getAuthenticate() == false);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("006001");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((HomeChargerState) state).getChargeCurrent().getValue() == null);
    }
}
