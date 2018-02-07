package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.CommandWithProperties;
import com.highmobility.autoapi.RooftopState;
import com.highmobility.autoapi.property.IntProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

public class PropertyTest {



    @Test public void propertyLength() {
        IntProperty property = new IntProperty((byte) 0x01, 2, 2);
        assertTrue(Arrays.equals(property.getPropertyBytes(), new byte[]{ 0x01, 0x00, 0x02, 0x00, 0x02 }));

        String longString =
                "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring";

        try {
            StringProperty stringProperty = new StringProperty((byte) 0x02, longString);
            assertTrue(stringProperty.getPropertyBytes()[1] == 0x01);
            assertTrue(stringProperty.getPropertyBytes()[2] == 0x4A);
        } catch (UnsupportedEncodingException e) {
            fail();
        }
    }

    @Test public void nonce() {
        CommandWithProperties command = getCommandWithSignature();
        byte[] nonce = command.getNonce();
        assertTrue(Arrays.equals(nonce, Bytes.bytesFromHex("324244433743483436")));
    }

    @Test public void signature() {
        CommandWithProperties command = getCommandWithSignature();
        assertTrue(Arrays.equals(command.getSignature(), Bytes.bytesFromHex("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6")));
    }

    @Test public void signedBytes() {
        CommandWithProperties command = getCommandWithSignature();
        byte[] signedBytes = command.getSignedBytes();
        assertTrue(Arrays.equals(signedBytes, Bytes.bytesFromHex("00460101000101A00009324244433743483436")));
    }

    CommandWithProperties getCommandWithSignature() {
        byte[] bytes = Bytes.bytesFromHex("00460101000101A00009324244433743483436A100404D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6");
        try {
            Command command = CommandResolver.resolve(bytes);

            if (command instanceof CommandWithProperties) {
                return (CommandWithProperties) command;
            }

            throw new CommandParseException();
        } catch (CommandParseException e) {
            fail();
            return null;
        }
    }

    @Test public void unknownProperty() throws CommandParseException {
        byte[] bytes = Bytes.bytesFromHex(
                "002501" +
                    "01000101" +
                    "1A000135");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.is(RooftopState.TYPE));

        assertTrue(command.getClass() == RooftopState.class);
        RooftopState state = (RooftopState) command;
        assertTrue(state.getDimmingPercentage() == .01f);
        assertTrue(state.getOpenPercentage() == null);
        assertTrue(state.getProperties().length == 2);

        boolean foundUnknownProperty = false;
        boolean foundDimmingProperty = false;

        for (int i = 0; i < state.getProperties().length; i++) {
            Property property = state.getProperties()[i];
            if (property.getPropertyIdentifier() == 0x1A) {
                assertTrue(property.getPropertyLength() == 1);
                assertTrue(Arrays.equals(property.getValueBytes(), new byte[] { 0x35 }));
                assertTrue(Arrays.equals(property.getPropertyBytes(), Bytes.bytesFromHex("1A000135")));
                foundUnknownProperty = true;
            }
            else if (property.getPropertyIdentifier() == 0x01) {
                assertTrue(property.getPropertyLength() == 1);
                assertTrue(Arrays.equals(property.getValueBytes(), new byte[] { 0x01 }));
                assertTrue(Arrays.equals(property.getPropertyBytes(), Bytes.bytesFromHex("01000101")));
                foundDimmingProperty = true;
            }
        }

        assertTrue(foundDimmingProperty == true);
        assertTrue(foundUnknownProperty == true);
    }
}
