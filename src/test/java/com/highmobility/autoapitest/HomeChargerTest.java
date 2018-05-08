package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateSolarCharging;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.EnableDisableWifiHotspot;
import com.highmobility.autoapi.GetHomeChargerState;
import com.highmobility.autoapi.HomeChargerState;
import com.highmobility.autoapi.SetChargeCurrent;
import com.highmobility.autoapi.SetPriceTariffs;
import com.highmobility.autoapi.property.ChargeCurrentProperty;
import com.highmobility.autoapi.property.CoordinatesProperty;
import com.highmobility.autoapi.property.HomeCharger.AuthenticationMechanism;
import com.highmobility.autoapi.property.HomeCharger.Charging;
import com.highmobility.autoapi.property.HomeCharger.PlugType;
import com.highmobility.autoapi.property.HomeCharger.PriceTariff;
import com.highmobility.autoapi.property.NetworkSecurity;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class HomeChargerTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "00600101000102020001010300010104000441380000050001010600084252147d41567ab107000C3f0000003f800000000000000800010109000C4368617267657220373631320A0001030B000A5a57337641524e5542650C000800455552409000000C0008024555523e99999a");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command instanceof HomeChargerState);
        HomeChargerState state = (HomeChargerState) command;

        assertTrue(state.getCharging() == Charging.CHARGING);
        assertTrue(state.getAuthenticationMechanism() == AuthenticationMechanism.APP);
        assertTrue(state.getPlugType() == PlugType.TYPE_TWO);
        assertTrue(state.getChargingPower() == 11.5f);
        assertTrue(state.isSolarChargingActive() == true);
        assertTrue(state.getLocation().getLatitude() == 52.520008f);
        assertTrue(state.getLocation().getLongitude() == 13.404954f);

        assertTrue(state.getChargeCurrent().getChargeCurrent() == .5f);
        assertTrue(state.getChargeCurrent().getMaximumValue() == 1f);
        assertTrue(state.getChargeCurrent().getMinimumValue() == 0f);

        assertTrue(state.isHotspotEnabled() == true);
        assertTrue(state.getHotspotSsid().equals("Charger 7612"));
        assertTrue(state.getHotspotSecurity() == NetworkSecurity.WPA2_PERSONAL);
        assertTrue(state.getHotspotPassword().equals("ZW3vARNUBe"));

        assertTrue(state.getPriceTariff(PriceTariff.PricingType.STARTING_FEE).getPrice() == 4.5f);
        assertTrue(state.getPriceTariff(PriceTariff.PricingType.STARTING_FEE).getCurrency()
                .equals("EUR"));

        assertTrue(state.getPriceTariff(PriceTariff.PricingType.PER_KWH).getPrice() == .3f);
        assertTrue(state.getPriceTariff(PriceTariff.PricingType.STARTING_FEE).getCurrency()
                .equals("EUR"));
    }

    @Test public void build() {
        HomeChargerState.Builder builder = new HomeChargerState.Builder();

        builder.setCharging(Charging.CHARGING);
        builder.setAuthenticationMechanism(AuthenticationMechanism.APP);
        builder.setPlugType(PlugType.TYPE_TWO);
        builder.setChargingPower(11.5f);
        builder.setSolarChargingActive(true);
        builder.setLocation(new CoordinatesProperty(52.520008f, 13.404954f));

        builder.setChargeCurrent(new ChargeCurrentProperty(.5f, 1f, 0f));
        builder.setHotspotEnabled(true);
        builder.setHotspotSsid("Charger 7612");
        builder.setHotspotSecurity(NetworkSecurity.WPA2_PERSONAL);
        builder.setHotspotPassword("ZW3vARNUBe");

        builder.addPriceTariff(new PriceTariff(PriceTariff.PricingType.STARTING_FEE, "EUR", 4.5f));
        builder.addPriceTariff(new PriceTariff(PriceTariff.PricingType.PER_KWH, "EUR", .3f));
        HomeChargerState command = builder.build();
        assertTrue(Arrays.equals(command.getBytes(), Bytes.bytesFromHex
                ("00600101000102020001010300010104000441380000050001010600084252147d41567ab107000C3f0000003f800000000000000800010109000C4368617267657220373631320A0001030B000A5a57337641524e5542650C000800455552409000000C0008024555523e99999a")));
    }

    @Test public void get() {
        byte[] waitingForBytes = Bytes.bytesFromHex("006000");
        byte[] commandBytes = new GetHomeChargerState().getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void setChargeCurrent() {
        byte[] waitingForBytes = Bytes.bytesFromHex("0060023f000000");
        byte[] commandBytes = new SetChargeCurrent(.5f).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        SetChargeCurrent command = (SetChargeCurrent) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getCurrent() == .5f);
    }

    @Test public void setPriceTariffs() {
        byte[] waitingForBytes = Bytes.bytesFromHex
                ("0060030C000800455552409000000C0008024555523e99999a");

        PriceTariff[] tariffs = new PriceTariff[2];

        tariffs[0] = new PriceTariff(PriceTariff.PricingType.STARTING_FEE, "EUR", 4.5f);
        tariffs[1] = new PriceTariff(PriceTariff.PricingType.PER_KWH, "EUR", .3f);

        byte[] commandBytes = new SetPriceTariffs(tariffs).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        SetPriceTariffs command = (SetPriceTariffs) CommandResolver.resolve(waitingForBytes);


        assertTrue(command.getPriceTariffs().length == 2);
        assertTrue(command.getPriceTariff(PriceTariff.PricingType.STARTING_FEE).getCurrency().equals("EUR"));
        assertTrue(command.getPriceTariff(PriceTariff.PricingType.STARTING_FEE).getPrice() == 4.5f);

        assertTrue(command.getPriceTariff(PriceTariff.PricingType.PER_KWH).getCurrency().equals("EUR"));
        assertTrue(command.getPriceTariff(PriceTariff.PricingType.PER_KWH).getPrice() == .3f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failSamePriceTariffTypes() {

        PriceTariff[] tariffs = new PriceTariff[2];

        tariffs[0] = new PriceTariff(PriceTariff.PricingType.PER_KWH, "EUR", 4.5f);
        tariffs[1] = new PriceTariff(PriceTariff.PricingType.PER_KWH, "EUR", .3f);

        new SetPriceTariffs(tariffs).getBytes();
    }

    @Test public void activateSolarCharging() {
        byte[] waitingForBytes = Bytes.bytesFromHex("00600401");
        byte[] commandBytes = new ActivateDeactivateSolarCharging(true).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        ActivateDeactivateSolarCharging command = (ActivateDeactivateSolarCharging)
                CommandResolver.resolve(waitingForBytes);
        assertTrue(command.activate() == true);
    }

    @Test public void enableWifi() {
        byte[] waitingForBytes = Bytes.bytesFromHex("00600501");
        byte[] commandBytes = new EnableDisableWifiHotspot(true).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        EnableDisableWifiHotspot command = (EnableDisableWifiHotspot) CommandResolver.resolve
                (waitingForBytes);
        assertTrue(command.enable() == true);
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("006001");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((HomeChargerState) state).getChargeCurrent() == null);
    }
}
