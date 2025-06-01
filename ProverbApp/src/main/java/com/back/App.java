package com.back;

import com.back.domain.wiseSaying.controller.WiseSayingController;
import com.back.domain.wiseSaying.entitiy.WiseSaying;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class App {

  private final WiseSayingController wiseSayingController = new WiseSayingController();

  public void wiseSayingApp() {
    System.out.println("== 명언 앱 ==");

    Scanner scanner = new Scanner(System.in);
    List<WiseSaying> wiseSayingList = wiseSayingController.handleGetList();

    label:
    while (true) {
      System.out.print("명령) ");
      String cmd = scanner.nextLine().trim();
      String cmdType = cmd.substring(0, 2);

      switch (cmdType) {
        case "종료":
          break label;

        case "등록":
          registerWiseSaying(wiseSayingList, scanner);
          break;

        case "목록":
          showWiseSayingList(wiseSayingList);
          break;

        case "삭제":
          deleteWiseSaying(wiseSayingList, Integer.parseInt(cmd.split("=")[1]));
          break;

        case "수정":
          updateWiseSaying(wiseSayingList, Integer.parseInt(cmd.split("=")[1]), scanner);
          break;

        case "빌드":
          wiseSayingController.handleMergeJsonFiles();
          System.out.println("data.json 파일의 내용이 갱신되었습니다.");
          break;
      }
    }

    scanner.close();
  }

  private void registerWiseSaying(List<WiseSaying> wiseSayingList, Scanner scanner) {
    while (true) {
      System.out.print("명언 : ");
      String content = scanner.nextLine().trim();

      if (containsSpecificChar(content)) {
        while (true) {
          System.out.print("작가 : ");
          String author = scanner.nextLine().trim();

          if (containsSpecificChar(author)) {
            wiseSayingList.add(wiseSayingController.handleCreate(content, author));
            System.out.println(wiseSayingList.get(wiseSayingList.size() - 1).id + "번 명언이 등록되었습니다.");
            return;
          }

          System.out.println("작가에 특수문자를 제외하고 입력해주세요.");
        }
      }

      System.out.println("명언에 특수문자를 제외하고 입력해주세요.");
    }
  }

  private void updateWiseSaying(List<WiseSaying> wiseSayingList, int modifyId, Scanner scanner) {
    int modifyListIndex = IntStream.range(0, wiseSayingList.size())
        .filter(i -> wiseSayingList.get(i).id == modifyId).findFirst().orElse(-1);

    if (modifyListIndex != -1) {
      WiseSaying targetWiseSaying = wiseSayingList.get(modifyListIndex);
      System.out.println("명언(기존) : " + targetWiseSaying.content);

      while (true) {
        System.out.print("명언 : ");
        String newContent = scanner.nextLine().trim();

        if (containsSpecificChar(newContent)) {
          System.out.println("작가(기존) : " + targetWiseSaying.author);

          while (true) {
            System.out.print("작가 : ");
            String newAuthor = scanner.nextLine().trim();

            if (containsSpecificChar(newAuthor)) {
              wiseSayingController.handleUpdate(modifyId, newContent, newAuthor);
              wiseSayingList.set(modifyListIndex, new WiseSaying(modifyId, newContent, newAuthor));
              return;
            }

            System.out.println("명언에 특수문자를 제외하고 입력해주세요.");
          }
        }

        System.out.println("명언에 특수문자를 제외하고 입력해주세요.");
      }
    } else {
      System.out.println(modifyId + "번 명언은 존재하지 않습니다.");
    }
  }

  private void deleteWiseSaying(List<WiseSaying> wiseSayingList, int removeId) {
    int removeListIndex = IntStream.range(0, wiseSayingList.size())
        .filter(i -> wiseSayingList.get(i).id == removeId).findFirst().orElse(-1);

    if (removeListIndex != -1) {
      wiseSayingController.handleDelete(removeId);
      wiseSayingList.remove(removeListIndex);

      System.out.println(removeId + "번 명언이 삭제되었습니다.");
    } else {
      System.out.println(removeId + "번 명언은 존재하지 않습니다.");
    }
  }

  private boolean containsSpecificChar(String text) {
    Pattern pattern = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?`~]");
    Matcher matcher = pattern.matcher(text);
    return !matcher.find();
  }

  private void showWiseSayingList(List<WiseSaying> wiseSayingList) {
    System.out.println("번호 / 작가 / 명언");
    System.out.println("----------------");

    for (int idx = wiseSayingList.size() - 1; idx >= 0; idx--) {
      System.out.println(wiseSayingList.get(idx).id + " / " + wiseSayingList.get(idx).author + " / "
          + wiseSayingList.get(idx).content);
    }
  }
}
