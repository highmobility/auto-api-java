package com.highmobility.autoapi;

import com.highmobility.autoapi.capability.*;
import com.highmobility.autoapi.incoming.Capability;
import com.highmobility.autoapi.incoming.IncomingCommand;
import com.highmobility.autoapi.Command.Identifier;
import com.highmobility.utils.Bytes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Capabilities {
    @Test
    public void capabilities() {
        byte[] bytes = Bytes.bytesFromHex("00100112" +
                "00200101" +
                "0021020300" +
                "00220100" +
                "00230100" +
                "0024020100" +
                "0025020101" +
                "002603010101" +
                "00270101" +
                "00280101" +
                "00290100" +
                "00300101" +
                "00310101" +
                "00320101" +
                "00400100" +
                "003603000101" +
                "0037020000" +
                "0038020100" +
                "0042020002");

        com.highmobility.autoapi.incoming.IncomingCommand command = null;

        try {
            command = com.highmobility.autoapi.incoming.IncomingCommand.create(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == com.highmobility.autoapi.incoming.Capabilities.class);
        FeatureCapability[] capabilities = ((com.highmobility.autoapi.incoming.Capabilities)command).getCapabilities();
        int expectedCapabilitiesCount = 18;
        assertTrue(capabilities.length == expectedCapabilitiesCount);
        int capabilitiesFound = 0;
        for (int i = 0; i < capabilities.length; i++) {
            FeatureCapability capa = capabilities[i];

            switch (capa.getIdentifier()) {
                case DOOR_LOCKS: {
                    AvailableGetStateCapability capability = (AvailableGetStateCapability) capa;
                    assertTrue(capability.getCapability() == AvailableGetStateCapability.Capability.AVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case TRUNK_ACCESS: {
                    TrunkAccessCapability trunkAccessCapability = (TrunkAccessCapability) capa;
                    assertTrue(trunkAccessCapability.getLockCapability()
                            == TrunkAccessCapability.LockCapability.GET_STATE_UNLOCK_AVAILABLE);
                    assertTrue(trunkAccessCapability.getPositionCapability()
                            == TrunkAccessCapability.PositionCapability.UNAVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case WAKE_UP: {
                    AvailableCapability capability = (AvailableCapability) capa;
                    assertTrue(capability.getCapability() == AvailableCapability.Capability.UNAVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case CHARGING: {
                    AvailableGetStateCapability capability = (AvailableGetStateCapability) capa;
                    assertTrue(capability.getCapability() == AvailableGetStateCapability.Capability.UNAVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case CLIMATE: {
                    ClimateCapability capability = (ClimateCapability) capa;
                    assertTrue(capability.getClimateCapability()
                            == AvailableGetStateCapability.Capability.AVAILABLE);
                    assertTrue(capability.getProfileCapability()
                            == ClimateCapability.ProfileCapability.UNAVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case ROOFTOP: {
                    RooftopCapability capability = (RooftopCapability) capa;
                    assertTrue(capability.getDimmingCapability()
                            == RooftopCapability.DimmingCapability.AVAILABLE);
                    assertTrue(capability.getOpenCloseCapability()
                            == RooftopCapability.OpenCloseCapability.AVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case HONK_FLASH: {
                    HonkFlashCapability capability = (HonkFlashCapability) capa;
                    assertTrue(capability.getEmergencyFlasherCapability()
                            == AvailableCapability.Capability.AVAILABLE);
                    assertTrue(capability.getFlashLightsCapability()
                            == AvailableCapability.Capability.AVAILABLE);
                    assertTrue(capability.getHonkHornCapability()
                            == AvailableCapability.Capability.AVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case REMOTE_CONTROL: {
                    AvailableCapability capability = (AvailableCapability) capa;
                    assertTrue(capability.getCapability() == AvailableCapability.Capability.AVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case VALET_MODE: {
                    AvailableGetStateCapability capability = (AvailableGetStateCapability) capa;
                    assertTrue(capability.getCapability() == AvailableGetStateCapability.Capability.AVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case HEART_RATE: {
                    AvailableCapability capability = (AvailableCapability) capa;
                    assertTrue(capability.getCapability() == AvailableCapability.Capability.UNAVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case VEHICLE_LOCATION: {
                    AvailableCapability capability = (AvailableCapability) capa;
                    assertTrue(capability.getCapability() == AvailableCapability.Capability.AVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case NAVI_DESTINATION: {
                    AvailableCapability capability = (AvailableCapability) capa;
                    assertTrue(capability.getCapability() == AvailableCapability.Capability.AVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case DELIVERED_PARCELS: {
                    AvailableCapability capability = (AvailableCapability) capa;
                    assertTrue(capability.getCapability() == AvailableCapability.Capability.AVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case FUELING: {
                    AvailableCapability capability = (AvailableCapability) capa;
                    assertTrue(capability.getCapability() == AvailableCapability.Capability.UNAVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case LIGHTS: {
                    LightsCapability capability = (LightsCapability) capa;
                    assertTrue(capability.getExteriorLightsCapability() == AvailableGetStateCapability.Capability.UNAVAILABLE);
                    assertTrue(capability.getInteriorLightsCapability() == AvailableGetStateCapability.Capability.AVAILABLE);
                    assertTrue(capability.getAmbientLightsCapability() == AvailableCapability.Capability.AVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case MESSAGING: {
                    MessagingCapability capability = (MessagingCapability) capa;
                    assertTrue(capability.getSendMessage() == AvailableCapability.Capability.UNAVAILABLE);
                    assertTrue(capability.getMessageReceived() == AvailableCapability.Capability.UNAVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case NOTIFICATIONS: {
                    NotificationsCapability capability = (NotificationsCapability) capa;
                    assertTrue(capability.getNotification() == AvailableCapability.Capability.AVAILABLE);
                    assertTrue(capability.getNotificationAction() == AvailableCapability.Capability.UNAVAILABLE);
                    capabilitiesFound++;
                    break;
                }
                case WINDSCREEN: {
                    WindscreenCapability capability = (WindscreenCapability) capa;
                    assertTrue(capability.getWiperCapability() == AvailableCapability.Capability.UNAVAILABLE);
                    assertTrue(capability.getWindscreenDamageCapability() == AvailableGetStateCapability.Capability.GET_STATE_AVAILABLE);
                    capabilitiesFound++;
                    break;
                }
            }
        }

        assertTrue(capabilitiesFound == expectedCapabilitiesCount);
    }

    byte[] knownCapabilitiesBytes = Bytes.bytesFromHex("001001" +
            "1F" + // length
            "002001010021020300002201000023010000240201000025020101002603010101002701010028010100290100003001010031010100320101" +
            "00330100" +
            "00340101" +
            "003603020201" +
            "0037020101" +
            "0038020000" +
            "00400101" +
            "00410101" +
            "0042020102" + // 22(0x16)
            "00430100" +
            "00440100" + // 24(0x18)
            "00450101" +
            "00350102" +
            "00460101" +
            "00470102" +
            "00480100" +
            "00500101" +
            "00490101" +
            "00510100"
    );

    com.highmobility.autoapi.incoming.Capabilities capabilites = null;
    @Before
    public void setUp() {
        try {
            IncomingCommand command = IncomingCommand.create(knownCapabilitiesBytes);
            assertTrue(command.getClass() == com.highmobility.autoapi.incoming.Capabilities.class);
            capabilites = (com.highmobility.autoapi.incoming.Capabilities)command;
        } catch (CommandParseException e) {
            fail("capabilities init failed");
        }
    }

    @Test
    public void capabilities_init() {
        assertTrue(capabilites.getCapabilities() != null);
        assertTrue(capabilites.getCapabilities().length == 31);
    }

    @Test
    public void unknownCapabilities_init() {
        // 00 59 unknown
        byte[] unknownCapabilitiesBytes = Bytes.bytesFromHex("0010010D005901010021020300002201000023010000240201030025020101002603010101002701010028010100290100003001010031010100320101");
        com.highmobility.autoapi.incoming.Capabilities unknownCapabilities= null;

        try {
            unknownCapabilities = new com.highmobility.autoapi.incoming.Capabilities(unknownCapabilitiesBytes);
        } catch (CommandParseException e) {
            fail("unknowncapabilities init failed");
        }

        assertTrue(unknownCapabilities.getCapabilities().length == 12);
        for (int i = 0; i < unknownCapabilities.getCapabilities().length; i++) {
            assertTrue(unknownCapabilities.getCapabilities()[i] != null);
        }
    }

    @Test
    public void capabilites_init_door_locks() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Command.Identifier.DOOR_LOCKS) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableGetStateCapability.class);
        if (featureCapability.getClass() == AvailableGetStateCapability.class) {
            assertTrue(((AvailableGetStateCapability) featureCapability).getCapability() == AvailableGetStateCapability.Capability.AVAILABLE);
        }
    }

    @Test
    public void capabilites_init_trunk_access() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.TRUNK_ACCESS) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == TrunkAccessCapability.class);
        if (featureCapability.getClass() == TrunkAccessCapability.class) {
            assertTrue(((TrunkAccessCapability) featureCapability).getLockCapability() == TrunkAccessCapability.LockCapability.GET_STATE_UNLOCK_AVAILABLE);
            assertTrue(((TrunkAccessCapability) featureCapability).getPositionCapability() == TrunkAccessCapability.PositionCapability.UNAVAILABLE);
        }
    }

    @Test
    public void capabilites_init_wake_up() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.WAKE_UP) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        if (featureCapability.getClass() == AvailableCapability.class) {
            assertTrue(((AvailableCapability) featureCapability).getCapability() == AvailableCapability.Capability.UNAVAILABLE);
        }
    }

    @Test
    public void capabilites_init_charging() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.CHARGING) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableGetStateCapability.class);
        if (featureCapability.getClass() == AvailableGetStateCapability.class) {
            assertTrue(((AvailableGetStateCapability) featureCapability).getCapability() == AvailableGetStateCapability.Capability.UNAVAILABLE);
        }
    }

    @Test
    public void capabilites_init_climate() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.CLIMATE) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == ClimateCapability.class);
        if (featureCapability.getClass() == ClimateCapability.class) {
            assertTrue(((ClimateCapability) featureCapability).getClimateCapability() == AvailableGetStateCapability.Capability.AVAILABLE);
            assertTrue(((ClimateCapability) featureCapability).getProfileCapability() == ClimateCapability.ProfileCapability.UNAVAILABLE);
        }
    }

    @Test
    public void capabilites_init_rooftop() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.ROOFTOP) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == RooftopCapability.class);
        if (featureCapability.getClass() == RooftopCapability.class) {
            assertTrue(((RooftopCapability) featureCapability).getDimmingCapability() == RooftopCapability.DimmingCapability.AVAILABLE);
            assertTrue(((RooftopCapability) featureCapability).getOpenCloseCapability() == RooftopCapability.OpenCloseCapability.AVAILABLE);
        }
    }

    @Test
    public void capabilites_init_honkflash() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.HONK_FLASH) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == HonkFlashCapability.class);
        if (featureCapability.getClass() == HonkFlashCapability.class) {
            assertTrue(((HonkFlashCapability) featureCapability).getHonkHornCapability() == AvailableCapability.Capability.AVAILABLE);
            assertTrue(((HonkFlashCapability) featureCapability).getFlashLightsCapability() == AvailableCapability.Capability.AVAILABLE);
            assertTrue(((HonkFlashCapability) featureCapability).getEmergencyFlasherCapability() == AvailableCapability.Capability.AVAILABLE);
        }
    }

    @Test
    public void capabilites_init_remote_control() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.REMOTE_CONTROL) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        if (featureCapability.getClass() == AvailableCapability.class) {
            assertTrue(((AvailableCapability) featureCapability).getCapability() == AvailableCapability.Capability.AVAILABLE);
        }
    }

    @Test
    public void capabilites_init_valet_mode() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.VALET_MODE) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableGetStateCapability.class);
        if (featureCapability.getClass() == AvailableGetStateCapability.class) {
            assertTrue(((AvailableGetStateCapability) featureCapability).getCapability() == AvailableGetStateCapability.Capability.AVAILABLE);
        }
    }

    @Test
    public void capabilites_init_heart_rate() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.HEART_RATE) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        if (featureCapability.getClass() == AvailableCapability.class) {
            assertTrue(((AvailableCapability) featureCapability).getCapability() == AvailableCapability.Capability.UNAVAILABLE);
        }
    }

    @Test
    public void capabilites_init_vehicle_location() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.VEHICLE_LOCATION) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        if (featureCapability.getClass() == AvailableCapability.class) {
            assertTrue(((AvailableCapability) featureCapability).getCapability() == AvailableCapability.Capability.AVAILABLE);
        }
    }

    @Test
    public void capabilites_init_navi_destination() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.NAVI_DESTINATION) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        if (featureCapability.getClass() == AvailableCapability.class) {
            assertTrue(((AvailableCapability) featureCapability).getCapability() == AvailableCapability.Capability.AVAILABLE);
        }
    }

    @Test
    public void capabilites_init_delivered_parcels() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.DELIVERED_PARCELS) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        if (featureCapability.getClass() == AvailableCapability.class) {
            assertTrue(((AvailableCapability) featureCapability).getCapability() == AvailableCapability.Capability.AVAILABLE);
        }
    }

    @Test
    public void capabilites_init_diagnostics() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.DIAGNOSTICS) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        if (featureCapability.getClass() == AvailableCapability.class) {
            assertTrue(((AvailableCapability) featureCapability).getCapability() == AvailableCapability.Capability.UNAVAILABLE);
        }
    }

    @Test
    public void capabilites_init_maintenance() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.MAINTENANCE) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        if (featureCapability.getClass() == AvailableCapability.class) {
            assertTrue(((AvailableCapability) featureCapability).getCapability() == AvailableCapability.Capability.AVAILABLE);
        }
    }

    @Test
    public void capabilites_init_engine() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.ENGINE) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableGetStateCapability.class);
        assertTrue(((AvailableGetStateCapability) featureCapability).getCapability()
                == AvailableGetStateCapability.Capability.GET_STATE_AVAILABLE);
    }

    @Test
    public void capabilites_init_lights() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.LIGHTS) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == LightsCapability.class);

        assertTrue(((LightsCapability) featureCapability).getExteriorLightsCapability()
                == AvailableGetStateCapability.Capability.GET_STATE_AVAILABLE);
        assertTrue(((LightsCapability) featureCapability).getInteriorLightsCapability()
                == AvailableGetStateCapability.Capability.GET_STATE_AVAILABLE);
        assertTrue(((LightsCapability) featureCapability).getAmbientLightsCapability()
                == AvailableCapability.Capability.AVAILABLE);

    }

    @Test
    public void capabilites_init_messaging() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.MESSAGING) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == MessagingCapability.class);

        assertTrue(((MessagingCapability) featureCapability).getMessageReceived()
                == AvailableCapability.Capability.AVAILABLE);
        assertTrue(((MessagingCapability) featureCapability).getSendMessage()
                == AvailableCapability.Capability.AVAILABLE);
    }

    @Test
    public void capabilites_init_notifications() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.NOTIFICATIONS) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == NotificationsCapability.class);

        assertTrue(((NotificationsCapability) featureCapability).getNotification()
                == AvailableCapability.Capability.UNAVAILABLE);
        assertTrue(((NotificationsCapability) featureCapability).getNotificationAction()
                == AvailableCapability.Capability.UNAVAILABLE);
    }

    @Test
    public void capabilites_init_fueling() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.FUELING) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        assertTrue(((AvailableCapability) featureCapability).getCapability()
                == AvailableCapability.Capability.AVAILABLE);
    }

    @Test
    public void capabilites_init_driver_fatigue() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.DRIVER_FATIGUE) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        assertTrue(((AvailableCapability) featureCapability).getCapability()
                == AvailableCapability.Capability.AVAILABLE);
    }

    @Test
    public void capabilites_init_video_handover() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.VIDEO_HANDOVER) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        assertTrue(((AvailableCapability) featureCapability).getCapability()
                == AvailableCapability.Capability.UNAVAILABLE);
    }

    @Test
    public void capabilites_init_text_input() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.TEXT_INPUT) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        assertTrue(((AvailableCapability) featureCapability).getCapability()
                == AvailableCapability.Capability.UNAVAILABLE);
    }

    @Test
    public void capabilites_init_windows() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.WINDOWS) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        assertTrue(((AvailableCapability) featureCapability).getCapability()
                == AvailableCapability.Capability.AVAILABLE);
    }

    @Test
    public void capabilites_init_theft_alarm() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.THEFT_ALARM) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableGetStateCapability.class);
        assertTrue(((AvailableGetStateCapability) featureCapability).getCapability()
                == AvailableGetStateCapability.Capability.AVAILABLE);
    }

    @Test
    public void capabilites_init_parking_ticket() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.PARKING_TICKET) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableGetStateCapability.class);
        assertTrue(((AvailableGetStateCapability) featureCapability).getCapability()
                == AvailableGetStateCapability.Capability.GET_STATE_AVAILABLE);
    }

    @Test
    public void capabilites_init_keyfob_position() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.KEYFOB_POSITION) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        assertTrue(((AvailableCapability) featureCapability).getCapability()
                == AvailableCapability.Capability.UNAVAILABLE);
    }

    // single capabilities

    @Test
    public void capability_init_climate() {
        byte[] message = Bytes.bytesFromHex("00130024020002");
        Capability capability = null;
        try {
            capability = new Capability(message);
        } catch (CommandParseException e) {
            fail("climate capability init failed");
            e.printStackTrace();
        }

        assertTrue(capability.getCapability().getClass() == ClimateCapability.class);

        if (capability.getCapability().getClass() == ClimateCapability.class) {
            assertTrue(((ClimateCapability)capability.getCapability()).getClimateCapability() ==
                    AvailableGetStateCapability.Capability.UNAVAILABLE);
            assertTrue(((ClimateCapability)capability.getCapability()).getProfileCapability() ==
                    ClimateCapability.ProfileCapability.GET_STATE_AVAILABLE);
        }
    }

    @Test
    public void capability_init_heartrate() {
        byte[] message = Bytes.bytesFromHex("001300290101");
        Capability capability = null;
        try {
            capability = new Capability(message);
        } catch (CommandParseException e) {
            fail("climate capability init failed");
            e.printStackTrace();
        }

        assertTrue(capability.getCapability().getClass() == AvailableCapability.class);

        if (capability.getCapability().getClass() == AvailableCapability.class) {
            assertTrue(((AvailableCapability)capability.getCapability()).getCapability() ==
                    AvailableCapability.Capability.AVAILABLE);
        }
    }

    @Test
    public void capabilites_init_graphics() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.GRAPHICS) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        if (featureCapability.getClass() == AvailableCapability.class) {
            assertTrue(((AvailableCapability) featureCapability).getCapability() == AvailableCapability.Capability.UNAVAILABLE);
        }
    }

    @Test
    public void capabilites_init_browser() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.BROWSER) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        if (featureCapability.getClass() == AvailableCapability.class) {
            assertTrue(((AvailableCapability) featureCapability).getCapability() == AvailableCapability.Capability.AVAILABLE);
        }
    }

    @Test
    public void capabilites_init_vehicle_time() {
        FeatureCapability featureCapability = null;
        for (int i = 0; i < capabilites.getCapabilities().length; i++) {
            FeatureCapability iteratingFeatureCapability = capabilites.getCapabilities()[i];
            if (iteratingFeatureCapability.getIdentifier() == Identifier.VEHICLE_TIME) {
                featureCapability = iteratingFeatureCapability;
                break;
            }
        }

        assertTrue(featureCapability != null);
        assertTrue(featureCapability.getClass() == AvailableCapability.class);
        if (featureCapability.getClass() == AvailableCapability.class) {
            assertTrue(((AvailableCapability) featureCapability).getCapability() == AvailableCapability.Capability.AVAILABLE);
        }
    }
}
