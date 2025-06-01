package com.back.domain.wiseSaying.entitiy;

public class WiseSaying {

  public int id;
  public String content;
  public String author;

  public WiseSaying() {
  }

  public WiseSaying(String content, String author) {
    this.content = content;
    this.author = author;
  }

  public WiseSaying(int removeId, String content, String author) {
    this.id = removeId;
    this.content = content;
    this.author = author;
  }
}
