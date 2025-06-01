package com.back.domain.wiseSaying.controller;

import com.back.AppTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {

  @BeforeEach
  void beforeEach() {
    AppTest.clear();
  }

  @Test
  @DisplayName("등록")
  void t1() {
    final String out = AppTest.run("""
        등록
        현재를 사랑하라
        작자미상
        등록
        세상은 험난해
        엉엉순대
        목록
        삭제?id=1
        삭제?id=1
        수정?id=2
        시져시져
        꿍꿍
        빌드
        종료
        """);

    assertThat(out).contains("명언 :").contains("작가 :").contains("1번 명언이 등록되었습니다.");
  }
}