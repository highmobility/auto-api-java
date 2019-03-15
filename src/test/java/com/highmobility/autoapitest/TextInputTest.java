package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.TextInput;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextInputTest {
    @Test public void textInputTest() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004400" +
                "010006010003796573");
        byte[] bytes = new TextInput("yes").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputOneLetter() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004400" +
                "01000401000179");
        byte[] bytes = new TextInput("y").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputNoLetters() {
        Bytes waitingForBytes = new Bytes("004400" +
                "010003010000");
        TextInput ti = new TextInput("");
        assertTrue(TestUtils.bytesTheSame(ti, waitingForBytes));
    }

    @Test public void resolve() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004400" +
                "010006010003796573");
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof TextInput);
        assertTrue(((TextInput)command).getText().getValue().equals("yes"));
    }
}
