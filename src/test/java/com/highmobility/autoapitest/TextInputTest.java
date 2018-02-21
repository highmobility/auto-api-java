package com.highmobility.autoapitest;

import com.highmobility.autoapi.TextInput;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class TextInputTest {
    @Test public void textInputTest() throws UnsupportedEncodingException {
        byte[] waitingForBytes = Bytes.bytesFromHex("004400010003796573");
        byte[] bytes = new TextInput("yes").getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputOneLetter() throws UnsupportedEncodingException {
        byte[] waitingForBytes = Bytes.bytesFromHex("00440001000179");
        byte[] bytes = new TextInput("y").getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }

    @Test public void textInputNoLetters() throws UnsupportedEncodingException {
        byte[] waitingForBytes = Bytes.bytesFromHex("004400010000");
        byte[] bytes = new TextInput("").getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }
}
