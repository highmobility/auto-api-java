package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateSolarCharging;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.EnableDisableWifiHotspot;
import com.highmobility.autoapi.GetHomeChargerState;
import com.highmobility.autoapi.HomeChargerState;
import com.highmobility.autoapi.SetChargeCurrent;
import com.highmobility.autoapi.SetPriceTariffs;
import com.highmobility.autoapi.WifiState;
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

    @Test public void get() {
        byte[] waitingForBytes = Bytes.bytesFromHex("006000");
        byte[] commandBytes = new GetHomeChargerState().getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void setChargeCurrent() {
        byte[] waitingForBytes = Bytes.bytesFromHex("0060023f000000");
        byte[] commandBytes = new SetChargeCurrent(.5f).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void setPriceTariffs() {
        byte[] waitingForBytes = Bytes.bytesFromHex
                ("0060030C000800455552409000000C0008024555523e99999a");

        PriceTariff[] tariffs = new PriceTariff[2];

        tariffs[0] = new PriceTariff(PriceTariff.PricingType.STARTING_FEE, "EUR", 4.5f);
        tariffs[1] = new PriceTariff(PriceTariff.PricingType.PER_KWH, "EUR", .3f);

        byte[] commandBytes = new SetPriceTariffs(tariffs).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
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
    }

    @Test public void enableWifi() {
        byte[] waitingForBytes = Bytes.bytesFromHex("00600500");
        byte[] commandBytes = new EnableDisableWifiHotspot(false).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("006001");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((HomeChargerState)state).getChargeCurrent() == null);
    }
}
