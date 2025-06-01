package com.back.standard.util;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtilTest {

  @Test
  @DisplayName("TestUtil.setInToByteArray()")
  public void t1() {
    ByteArrayInputStream is = TestUtil.setInToByteArray("""
        등록
        나의 죽음을 적들에게 알리지 말라!
        이순신
        """);

    Scanner scanner = new Scanner(is);

    String cmd = scanner.nextLine();
    String content = scanner.nextLine();
    String author = scanner.nextLine();

    assertThat(cmd).isEqualTo("등록");
    assertThat(content).isEqualTo("나의 죽음을 적들에게 알리지 말라!");
    assertThat(author).isEqualTo("이순신");

    TestUtil.clearSetInToByteArray(is);
    scanner.close();
  }

  @Test
  @DisplayName("TestUtil.setOutToByteArray()")
  public void t2() {
    ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();

    System.out.println("2 / 이순신 / 나의 죽음을 적들에게 알리지 말라!");

    String out = byteArrayOutputStream.toString().trim();
    TestUtil.clearSetOutToByteArray(byteArrayOutputStream);

    assertThat(out).isEqualTo("2 / 이순신 / 나의 죽음을 적들에게 알리지 말라!");
    System.out.println("이제는 화면에 출력됩니다.");
  }
}