package com.back.standard.util;

import java.io.*;
import java.util.Scanner;
import java.util.stream.Stream;

public class TestUtil {

  private static final InputStream ORIGINAL_IN = System.in;
  private static final PrintStream ORIGINAL_OUT = System.out;

  public static ByteArrayInputStream setInToByteArray(String input) {
    ByteArrayInputStream is = new ByteArrayInputStream(input.getBytes());
    System.setIn(is);
    return is;
  }

  public static void clearSetInToByteArray(ByteArrayInputStream is) {
    System.setIn(ORIGINAL_IN);
    try {
      is.close();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static ByteArrayOutputStream setOutToByteArray() {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    System.setOut(new PrintStream(os, true));
    return os;
  }

  public static void clearSetOutToByteArray(ByteArrayOutputStream os) {
    System.setOut(ORIGINAL_OUT);
    try {
      os.close();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}