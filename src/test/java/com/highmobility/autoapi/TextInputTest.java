package com.highmobility.autoapi;

import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextInputTest extends BaseTest {
    @Test public void textInputTest() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "004401" +
                "010006010003796573");
        byte[] bytes = new TextInput.TextInputCommand("yes").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputOneLetter() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "004401" +
                "01000401000179");
        byte[] bytes = new TextInput.TextInputCommand("y").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputNoLetters() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "004401" +
                "010003010000");
        TextInput.TextInputCommand ti = new TextInput.TextInputCommand("");
        assertTrue(TestUtils.bytesTheSame(ti, waitingForBytes));
    }

    @Test public void resolve() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "004401" +
                "010006010003796573");
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof TextInput.TextInputCommand);
        assertTrue(((TextInput.TextInputCommand) command).getText().getValue().equals("yes"));
    }
}
