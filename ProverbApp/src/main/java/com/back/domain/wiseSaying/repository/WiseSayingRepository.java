package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entitiy.WiseSaying;
import java.io.IOException;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WiseSayingRepository {

  private final String WISE_SAYING_DIR = "db/wiseSaying/";
  private final String LAST_ID_FILE_PATH = WISE_SAYING_DIR + "/lastId.txt";
  private final String WISE_SAYING_LIST_FILE_PATH = WISE_SAYING_DIR + "/data.json";

  public WiseSaying save(WiseSaying wiseSaying) {
    try {
      Files.createDirectories(Paths.get(WISE_SAYING_DIR));

      wiseSaying.id = getNewId();
      Path newFilePath = Paths.get(WISE_SAYING_DIR + wiseSaying.id + ".json");
      Files.writeString(newFilePath, createJson(wiseSaying), StandardOpenOption.CREATE);

      Files.writeString(Paths.get(LAST_ID_FILE_PATH), String.valueOf(wiseSaying.id),
          StandardOpenOption.CREATE);
      return wiseSaying;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<WiseSaying> getAll() {
    try {
      List<WiseSaying> wiseSayingList = new ArrayList<>();
      String[] lines = Files.readString(Paths.get(WISE_SAYING_LIST_FILE_PATH)).split("\n");
      final int WISE_SAYING_FIELD_NUM = 3;

      for (int i = 2; i < lines.length; i += WISE_SAYING_FIELD_NUM + 2) {
        WiseSaying wiseSaying = new WiseSaying();

        String idValue = lines[i].split(": ")[1];
        wiseSaying.id = Integer.parseInt(idValue.substring(0, idValue.indexOf(",")));

        String contentValue = lines[i + 1].split(": ")[1];
        wiseSaying.content = contentValue.substring(1, contentValue.length() - 2);

        String authorValue = lines[i + 2].split(": ")[1];
        wiseSaying.author = authorValue.substring(1, authorValue.length() - 1);

        wiseSayingList.add(wiseSaying);
      }

      return wiseSayingList;
    } catch (IOException e) {
      return new ArrayList<>();
    }
  }

  public void update(WiseSaying wiseSaying) {
    try {
      Path filePath = Paths.get(WISE_SAYING_DIR + wiseSaying.id + ".json");
      Files.writeString(filePath, createJson(wiseSaying), StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void remove(int removeId) {
    try {
      Files.deleteIfExists(Paths.get(WISE_SAYING_DIR + removeId + ".json"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void mergeJsonFilesAndSave() {
    try {
      Files.createDirectories(Paths.get(WISE_SAYING_DIR));

      boolean isFirstFile = true;
      StringBuilder wiseSayingListString = new StringBuilder();
      wiseSayingListString.append("[\n  ");

      for (int i = 1; i < getNewId(); i++) {
        Path wiseSayingJsonFilePath = Paths.get(WISE_SAYING_DIR + i + ".json");
        if (Files.exists(wiseSayingJsonFilePath)) {
          String wiseSayingJsonFile = Files.readString(wiseSayingJsonFilePath);
          String indentedWiseSayingJsonFile = wiseSayingJsonFile.replaceAll("\n", "\n  ");

          if (isFirstFile) {
            isFirstFile = false;
          } else {
            wiseSayingListString.append(",\n  ");
          }

          wiseSayingListString.append(indentedWiseSayingJsonFile);
        }
      }

      wiseSayingListString.append("\n]");

      if (isFirstFile) {
        wiseSayingListString.setLength(0);
        wiseSayingListString.append("[]");
      }

      Files.writeString(Paths.get(WISE_SAYING_DIR + "data.json"), wiseSayingListString,
          StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private int getNewId() throws IOException {
    try {
      String lastID = Files.readString(Paths.get(LAST_ID_FILE_PATH));
      return Integer.parseInt(lastID) + 1;
    } catch (NoSuchFileException e) {
      return 1;
    }
  }

  private String createJson(WiseSaying wiseSaying) {
    String template = """
        '{'
          "id": {0},
          "content": "{1}",
          "author": "{2}"
        '}'""";

    return MessageFormat.format(template, wiseSaying.id, wiseSaying.content, wiseSaying.author);
  }
}
