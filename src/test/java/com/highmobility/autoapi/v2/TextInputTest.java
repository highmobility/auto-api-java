package com.highmobility.autoapi.v2;

import com.highmobility.autoapitest.TestUtils;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextInputTest extends BaseTest {
    @Test public void textInputTest() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004401" +
                "010006010003796573");
        byte[] bytes = new TextInput("yes").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputOneLetter() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004401" +
                "01000401000179");
        byte[] bytes = new TextInput("y").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputNoLetters() {
        Bytes waitingForBytes = new Bytes("004401" +
                "010003010000");
        TextInput ti = new TextInput("");
        assertTrue(TestUtils.bytesTheSame(ti, waitingForBytes));
    }

    @Test public void resolve() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004401" +
                "010006010003796573");
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof TextInput);
        assertTrue(((TextInput) command).getText().getValue().equals("yes"));
    }
}