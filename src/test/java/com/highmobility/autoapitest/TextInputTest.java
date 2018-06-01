package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.TextInput;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class TextInputTest {
    @Test public void textInputTest() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004400010003796573");
        byte[] bytes = new TextInput("yes").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputOneLetter() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("00440001000179");
        byte[] bytes = new TextInput("y").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputNoLetters() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004400010000");
        byte[] bytes = new TextInput("").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void resolve() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004400010003796573");
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof TextInput);
        assertTrue(((TextInput)command).getText().equals("yes"));
    }
}
