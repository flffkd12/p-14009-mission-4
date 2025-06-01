package com.back.domain.wiseSaying.controller;

import com.back.domain.wiseSaying.entitiy.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;
import java.util.List;

public class WiseSayingController {

  private final WiseSayingService wiseSayingService = new WiseSayingService();

  public WiseSaying handleCreate(String content, String author) {
    return wiseSayingService.create(content, author);
  }

  public List<WiseSaying> handleGetList() {
    return wiseSayingService.getList();
  }

  public void handleUpdate(int updateId, String newContent, String newAuthor) {
    wiseSayingService.update(updateId, newContent, newAuthor);
  }

  public void handleDelete(int removeId) {
    wiseSayingService.delete(removeId);
  }

  public void handleMergeJsonFiles() {
    wiseSayingService.mergeJsonFiles();
  }
}
