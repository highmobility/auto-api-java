package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.TextInput;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class TextInputTest {
    @Test public void textInputTest() {
        byte[] waitingForBytes = Bytes.bytesFromHex("004400010003796573");
        byte[] bytes = new TextInput("yes").getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputOneLetter() {
        byte[] waitingForBytes = Bytes.bytesFromHex("00440001000179");
        byte[] bytes = new TextInput("y").getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputNoLetters() {
        byte[] waitingForBytes = Bytes.bytesFromHex("004400010000");
        byte[] bytes = new TextInput("").getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void resolve() {
        byte[] waitingForBytes = Bytes.bytesFromHex("004400010003796573");
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof TextInput);
        assertTrue(((TextInput)command).getText().equals("yes"));
    }
}
