package com.back;

import com.back.domain.wiseSaying.repository.WiseSayingRepository;
import com.back.standard.util.TestUtil;
import java.io.*;

public class AppTest {

  public static void clear() {
    File dbFolder = new File("./db/wiseSaying/");
    File[] files = dbFolder.listFiles();
    if (files != null) {
      for (File file : files) {
        file.delete();
      }
    }
  }

  public static String run(String input) {
    ByteArrayInputStream testIn = TestUtil.setInToByteArray(input);
    ByteArrayOutputStream testOut = TestUtil.setOutToByteArray();

    try {
      App app = new App();
      app.wiseSayingApp();
      System.out.flush();

      return testOut.toString();
    } catch (Exception e) {
      e.printStackTrace();

      return "앱 동작 중 오류가 발생했습니다.";
    } finally {
      TestUtil.clearSetInToByteArray(testIn);
      TestUtil.clearSetOutToByteArray(testOut);
    }
  }
}
