package example.view;

import java.util.Scanner;

public class InputView {

    public int getDiceRollCount() {
        System.out.print("숫자를 입력하세요 : ");
        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.nextInt();
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }
}
