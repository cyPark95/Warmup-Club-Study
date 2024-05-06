![문제](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/a7bdaac4-145c-4874-93b6-1539378cd794)

위 코드는 가독성 및 확장성이 떨어져 유지보수가 어렵고, 확장성이 부족하다.<br>
이를 개선하기 위해 클린 코드의 원칙을 따르며 리팩토링을 진행한다.

## Refactoring

### STEP 0. 코드 분석

```java
package example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("숫자를 입력하세요 : ");
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();

        int r1 = 0, r2 = 0, r3 = 0, r4 = 0, r5 = 0, r6 = 0;

        for (int i = 0; i < a; i++) {
            double b = Math.random() * 6;

            if (b >= 0 && b < 1) {
                r1++;
            } else if (b >= 1 && b < 2) {
                r2++;
            } else if (b >= 2 && b < 3) {
                r3++;
            } else if (b >= 3 && b < 4) {
                r4++;
            } else if (b >= 4 && b < 5) {
                r5++;
            } else if (b >= 5 && b < 6) {
                r6++;
            }
        }

        System.out.printf("1은 %d번 나왔습니다.\n", r1);
        System.out.printf("2은 %d번 나왔습니다.\n", r2);
        System.out.printf("3은 %d번 나왔습니다.\n", r3);
        System.out.printf("4은 %d번 나왔습니다.\n", r4);
        System.out.printf("5은 %d번 나왔습니다.\n", r5);
        System.out.printf("6은 %d번 나왔습니다.\n", r6);
    }
}
```

- 문제점
    - 의미 없는 변수명
        - 코드의 의도를 파악하기 어렵다.
    - 매직 넘버 사용
        - 숫자 6이 직접 코드에 하드코딩되어 있어서, 추후 주사위의 눈금 수가 변경되거나 다른 유형의 주사위를 사용하는 경우 수정이 필요하다.
    - 반복된 코드

### STEP 1. 변수명 개선

```java
package example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("숫자를 입력하세요 : ");
        Scanner scanner = new Scanner(System.in);
        int diceRollCount = scanner.nextInt();

        int count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0, count6 = 0;

        for (int i = 0; i < diceRollCount; i++) {
            double diceNumber = Math.random() * 6;

            if (diceNumber >= 0 && diceNumber < 1) {
                count1++;
            } else if (diceNumber >= 1 && diceNumber < 2) {
                count2++;
            } else if (diceNumber >= 2 && diceNumber < 3) {
                count3++;
            } else if (diceNumber >= 3 && diceNumber < 4) {
                count4++;
            } else if (diceNumber >= 4 && diceNumber < 5) {
                count5++;
            } else if (diceNumber >= 5 && diceNumber < 6) {
                count6++;
            }
        }

        System.out.printf("1은 %d번 나왔습니다.\n", count1);
        System.out.printf("2은 %d번 나왔습니다.\n", count2);
        System.out.printf("3은 %d번 나왔습니다.\n", count3);
        System.out.printf("4은 %d번 나왔습니다.\n", count4);
        System.out.printf("5은 %d번 나왔습니다.\n", count5);
        System.out.printf("6은 %d번 나왔습니다.\n", count6);
    }
}
```

- 먼저 변수명을 의미있는 단어로 변경하여 가독성을 향상시킬 수 있다.
    - 주사위 던질 횟수: `a` -> `diceRollCount`
    - 주사위 숫자: `b` -> `diceNumber`
    - 주사위 숫자별 횟수: `r` -> `count`

### STEP 2. 메서드 분리

각각의 역할을 분리하면 코드의 가독성을 향상시킬 수 있다.

```java
package example;

import java.util.Scanner;

public class Main {

    private static int count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0, count6 = 0;

    public static void main(String[] args) throws Exception {
        int diceRollCount = getDiceRollCount();
        for (int i = 0; i < diceRollCount; i++) {
            int diceNumber = roll();
            increaseCount(diceNumber);
        }
        printAllDiceNumberCount();
    }

    private static int getDiceRollCount() {
        System.out.print("숫자를 입력하세요 : ");
        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.nextInt();
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    private static int roll() {
        return (int) (Math.random() * 6);
    }

    private static void increaseCount(int diceNumber) {
        if (diceNumber == 0) {
            count1++;
        } else if (diceNumber == 1) {
            count2++;
        } else if (diceNumber == 2) {
            count3++;
        } else if (diceNumber == 3) {
            count4++;
        } else if (diceNumber == 4) {
            count5++;
        } else if (diceNumber == 5) {
            count6++;
        }
    }

    private static void printAllDiceNumberCount() {
        System.out.printf("1은 %d번 나왔습니다.\n", count1);
        System.out.printf("2은 %d번 나왔습니다.\n", count2);
        System.out.printf("3은 %d번 나왔습니다.\n", count3);
        System.out.printf("4은 %d번 나왔습니다.\n", count4);
        System.out.printf("5은 %d번 나왔습니다.\n", count5);
        System.out.printf("6은 %d번 나왔습니다.\n", count6);
    }
}
```

- 기능에 따라 4개의 메서드로 분리한다.
    - `getDiceRollCount`: 사용자로부터 주사위 던질 횟수를 입력받는다.
        - 할당받은 Scanner의 참조를 해제하고, 입력받는 과정에서 발생할 수 있는 예외에 대한 처리 로직을 추가했다.
    - `roll`: 주사위를 던져 숫자를 반환받는다.
    - `increaseCount`: 주사위 숫자가 나온 횟수를 기록한다.
    - `printAllDiceNumberCount`: 각각의 주사위 숫자가 나온 횟수를 출력한다.

이렇게 하면 `main` 메서드만 봤을 때, 어느정도 로직의 흐름을 이해할 수 있다.

### STEP 3. 중복 로직 제거

배열을 통해 주사위를 던져 나온 숫자를 기록한다면 조건문과 중복된 로직을 제거할 수 있다.

```java
package example;

import java.util.Scanner;

public class Main {

    static int[] count = new int[6];

    public static void main(String[] args) throws Exception {
        int diceRollCount = getDiceRollCount();
        for (int i = 0; i < diceRollCount; i++) {
            int diceNumber = roll();
            increaseCount(diceNumber);
        }
        printAllDiceNumberCount();
    }

    private static int getDiceRollCount() {
        System.out.print("숫자를 입력하세요 : ");
        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.nextInt();
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    private static int roll() {
        return (int) (Math.random() * 6);
    }

    private static void increaseCount(int diceNumber) {
        count[diceNumber]++;
    }

    private static void printAllDiceNumberCount() {
        for (int i = 0; i < 6; i++) {
            System.out.printf("%d은 %d번 나왔습니다.\n", i + 1, count[i]);
        }
    }
}
```

- 배열을 사용하여 주사위를 던진 후 각 숫자를 기록하면 조건문을 제거할 수 있다.
- 반복문을 통해 각 주사위 숫자가 나온 횟수를 출력하는 로직의 중복을 제거할 수 있다.

### STEP 4. 클래스 분리

현재 `Main` 클래스에 모든 역할이 구현돼 있다.<br>
따라서 객체지향 원칙 중 단일 책임의 원칙을 적용하기 위해 역할에 따라 클래스로 분리한다.

#### Dice.java

`Dice` 클래스는 주사위 관련 로직을 담당한다.

```java
package example.doamin;

public class Dice {

    public int roll() {
        return (int) (Math.random() * 6);
    }
}
```

#### DiceNumberCounter.java

`DiceNumberCounter` 클래스는 주사위를 던져 나온 숫자의 수 관련 로직을 담당한다.

```java
package example.doamin;

public class DiceNumberCounter {

    private final int[] counts = new int[6];

    public int[] getCounts() {
        return counts;
    }

    public void increaseCount(int diceNumber) {
        counts[diceNumber]++;
    }
}

```

#### InputView.java

`InputView` 클래스는 입력에 대한 로직을 담당한다.

```java
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
```

#### OutputView.java

`OutputView` 클래스는 출력에 대한 로직을 담당한다.

```java
package example.view;

public class OutputView {

    public void printAllDiceNumberCount(int[] count) {
        for (int i = 0; i < 6; i++) {
            System.out.printf("%d은 %d번 나왔습니다.\n", i + 1, count[i]);
        }
    }
}
```

#### DiceGame.java

`DiceGame` 클래스는 주사위 게임의 로직을 담당한다.

```java
package example;

import example.doamin.Dice;
import example.doamin.DiceNumberCounter;
import example.view.InputView;
import example.view.OutputView;

public class DiceGame {

    private final InputView inputView;
    private final OutputView outputView;
    private final Dice dice;
    private final DiceNumberCounter diceNumberCounter;

    public DiceGame() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.dice = new Dice();
        this.diceNumberCounter = new DiceNumberCounter();
    }

    public void start() {
        int diceRollCount = inputView.getDiceRollCount();
        rollDice(diceRollCount);
        printAllDiceNumberCount();
    }

    private void rollDice(int diceRollCount) {
        for (int i = 0; i < diceRollCount; i++) {
            int diceNumber = dice.roll();
            diceNumberCounter.increaseCount(diceNumber);
        }
    }

    private void printAllDiceNumberCount() {
        outputView.printAllDiceNumberCount(diceNumberCounter.getCounts());
    }
}
```

클래스를 분리하면 클래스마다 명확한 책임을 가지므로 코드의 가독성이 향상되고, 유지보수가 용이해 진다.

### 한 걸음 더!

![한 걸음 더](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/d45677a3-2c54-4432-bada-03b1f2ee52fd)
리팩토링을 진행한 지금도 여전히 문제점은 존재한다.<br>
주사위 숫자의 범위가 변경되면 기존 코드 또한 수정이 필요하다.

주사위 숫자가 변경되더라도 기존 코드의 변경없이 수정할 수 있도록, 매개변수를 통해 확장성 있는 코드를 구현한다.

- 주사위 숫자의 범위를 생성자를 통해 초기화한다.

#### Dice.java

```java
package example.doamin;

public class Dice {

    private final int minNumber;
    private final int maxNumber;

    public Dice(int minNumber, int maxNumber) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
    }

    public int getMinNumber() {
        return minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int roll() {
        return (int) (Math.random() * (maxNumber - minNumber + 1)) + minNumber;
    }
}
```

- 주사위 숫자별 횟수를 배열이 아닌 `Map`을 통해 저장하면 주사위의 숫자가 변경되어도 기존 코드를 수정하지 않고도 다양한 주사위 숫자에 대응할 수 있다.

#### DiceNumberCounter.java

```java
package example.doamin;

import java.util.HashMap;
import java.util.Map;

public class DiceNumberCounter {

    private final Map<Integer, Integer> counts = new HashMap<>();

    public void increaseCount(int count) {
        counts.put(count, counts.getOrDefault(count, 0) + 1);
    }

    public int getCount(int count) {
        return counts.getOrDefault(count, 0);
    }
}
```

#### DiceGame.java

```java
package example;

import example.doamin.Dice;
import example.doamin.DiceNumberCounter;
import example.view.InputView;
import example.view.OutputView;

public class DiceGame {

    private final InputView inputView;
    private final OutputView outputView;
    private final Dice dice;
    private final DiceNumberCounter diceNumberCounter;

    public DiceGame(int diceMinNumber, int diceMaxNumber) {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.dice = new Dice(diceMinNumber, diceMaxNumber);
        this.diceNumberCounter = new DiceNumberCounter();
    }

    public void start() {
        int diceRollCount = inputView.getDiceRollCount();
        rollDice(diceRollCount);
        printAllDiceNumberCount();
    }

    private void rollDice(int diceRollCount) {
        for (int i = 0; i < diceRollCount; i++) {
            int diceNumber = dice.roll();
            diceNumberCounter.increaseCount(diceNumber);
        }
    }

    private void printAllDiceNumberCount() {
        for (int i = dice.getMinNumber(); i <= dice.getMaxNumber(); i++) {
            outputView.printDiceNumberCount(i, diceNumberCounter.getCount(i));
        }
    }
}
```

#### Main.java

```java
package example;

public class Main {

    private static final int DICE_MIN_NUMBER = 1;
    private static final int DICE_MAX_NUMBER = 6;

    public static void main(String[] args) {
        DiceGame diceGame = new DiceGame(DICE_MIN_NUMBER, DICE_MAX_NUMBER);
        diceGame.start();
    }
}
```

이렇게 수정하면 주사위 숫자의 범위가 변경된다해도, `Main` 클래스의 `DICE_MIN_NUMBER`와 `DICE_MAX_NUMBER`의 값만 수정하면 된다.

### 디자인 패턴 적용

현재는 주사위 숫자의 범위 내에서 랜덤 숫자를 가져오는 방식이다.<br>
하지만 주사위 던지기 전략이 변경된다면 기존 코드의 수정이 필요하다.

자유롭게 주사위 던지기 전략을 수정할 수 있도록, 전략 패턴을 적용한다.

#### DiceStrategy.java

```java
package example.domain.strategy;

@FunctionalInterface
public interface DiceRollStrategy {

    int roll(int min, int max);
}
```

- `DiceRollStrategy` 인터페이스를 통해 주사위 던지기 전략을 추상화 한다.

#### Dice.java

```java
package example.domain;

import example.domain.strategy.DiceRollStrategy;

public class Dice {

    private final int minNumber;
    private final int maxNumber;
    private final DiceRollStrategy rollStrategy;

    public Dice(int minNumber, int maxNumber, DiceRollStrategy rollStrategy) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.rollStrategy = rollStrategy;
    }

    public int getMinNumber() {
        return minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int roll() {
        return rollStrategy.roll(minNumber, maxNumber);
    }
}
```

- `DiceRollStrategy` 인터페이스는 생성자를 통해 `Dice` 객체에 주입되며, 이를 통해 주사위를 던지는 전략을 객체 생성 시에 정의할 수 있다.
- 전략 패턴을 적용하면 주사위 게임의 규칙이 변경되어도 기존 코드를 수정하지 않고 매개변수만 변경하여 적용할 수 있다.
- 또 다른 문제로 기존 방식은 무작위 숫자를 선택하는 방식으로 테스트 시 결과를 예측할 수 없어 정확한 검증이 어렵다.
- 이러한 접근을 통해 원하는 주사위 숫자 선택 전략으로 교체하여 쉽게 테스트 코드를 작성할 수 있다.
- 결과적으로, 이러한 구조는 유연성이 향상됩니다.

#### Main.java

```java
package example;

import example.domain.strategy.RandomDiceRollStrategy;

public class Main {

    private static final int DICE_MIN_NUMBER = 1;
    private static final int DICE_MAX_NUMBER = 6;

    public static void main(String[] args) {
        DiceGame diceGame = new DiceGame(
                DICE_MIN_NUMBER,
                DICE_MAX_NUMBER,
                new RandomDiceRollStrategy()
        );
        diceGame.start();
    }
}
```

- 주사위 던지기 전략은 다양한 방법을 통해 주입할 수 있다.
    1. 인터페이스 구현체 사용
        - `RandomDiceRollStrategy` 클래스를 통해 `DiceRollStrategy` 인터페이스를 구현함으로써, 랜덤 숫자 생성 로직을 명시적으로 정의할 수 있다.
           ```java
           package example.domain.strategy;
   
           public class RandomDiceRollStrategy implements DiceRollStrategy {
           
               @Override
               public int roll(int min, int max) {
                   return (int) (Math.random() * (max - min + 1)) + min;
               }
           }
           ```
    2. 람다 표현식 사용
        - `DiceRollStrategy`는 함수형 인터페이스이므로 람다 표현식을 이용하여 간결하게 구현할 수 있다.
        - `new RandomDiceRollStrategy()`를 `(min, max) -> (int) (Math.random() * (max - min + 1)) + min`로 대체할 수 있다.
    3. 메서드 레퍼런스 사용
        - 아래와 같이 별도의 메서드로 랜덤 숫자 생성 로직을 정의한 후, 이를 메서드 레퍼런스를 통해 참조할 수 있다.
          ```java
          private static int getNumberInRange(int min, int max) {
             return (int) (Math.random() * (max - min + 1)) + min;
          }
          ```
        - 메인 메서드에서 `RandomDiceRollStrategy()` 대신 `Main::getNumberInRange`를 사용할 수 있다.

## 프로젝트 구조

```text
├─DiceGame.java
├─Main.java
├─domain
│  ├─Dice.java
│  ├─DiceNumberCounter.java
│  └─strategy
│     ├─DiceRollStrategy.java
│     └─RandomDiceRollStrategy.java
└─view
   ├─InputView.java
   └─OutputView.java
```

## 전체 코드

#### Main.java

```java
package example;

public class Main {

    private static final int DICE_MIN_NUMBER = 1;
    private static final int DICE_MAX_NUMBER = 6;

    public static void main(String[] args) {
        DiceGame diceGame = new DiceGame(
                DICE_MIN_NUMBER,
                DICE_MAX_NUMBER,
                Main::getNumberInRange
        );
        diceGame.start();
    }

    private static int getNumberInRange(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
```

#### DiceGame.java

```java
package example;

import example.domain.Dice;
import example.domain.DiceNumberCounter;
import example.domain.strategy.DiceRollStrategy;
import example.view.InputView;
import example.view.OutputView;

public class DiceGame {

    private final InputView inputView;
    private final OutputView outputView;
    private final Dice dice;
    private final DiceNumberCounter diceNumberCounter;

    public DiceGame(int diceMinNumber, int diceMaxNumber, DiceRollStrategy strategy) {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.dice = new Dice(diceMinNumber, diceMaxNumber, strategy);
        this.diceNumberCounter = new DiceNumberCounter();
    }

    public void start() {
        int diceRollCount = inputView.getDiceRollCount();
        rollDice(diceRollCount);
        printAllDiceNumberCount();
    }

    private void rollDice(int diceRollCount) {
        for (int i = 0; i < diceRollCount; i++) {
            int diceNumber = dice.roll();
            diceNumberCounter.increaseCount(diceNumber);
        }
    }

    private void printAllDiceNumberCount() {
        for (int i = dice.getMinNumber(); i <= dice.getMaxNumber(); i++) {
            outputView.printDiceNumberCount(i, diceNumberCounter.getCount(i));
        }
    }
}
```

#### DiceRollStrategy.java

```java
package example.domain.strategy;

@FunctionalInterface
public interface DiceRollStrategy {

    int roll(int min, int max);
}
```

#### RandomDiceRollStrategy.java

```java
package example.domain.strategy;

public class RandomDiceRollStrategy implements DiceRollStrategy {

    @Override
    public int roll(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
```

#### Dice.java

```java
package example.domain;

import example.domain.strategy.DiceRollStrategy;

public class Dice {

    private final int minNumber;
    private final int maxNumber;
    private final DiceRollStrategy rollStrategy;

    public Dice(int minNumber, int maxNumber, DiceRollStrategy rollStrategy) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.rollStrategy = rollStrategy;
    }

    public int getMinNumber() {
        return minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int roll() {
        return rollStrategy.roll(minNumber, maxNumber);
    }
}
```

#### DiceNumberCounter.java

```java
package example.domain;

import java.util.HashMap;
import java.util.Map;

public class DiceNumberCounter {

    private final Map<Integer, Integer> counts = new HashMap<>();

    public void increaseCount(int count) {
        counts.put(count, counts.getOrDefault(count, 0) + 1);
    }

    public int getCount(int count) {
        return counts.getOrDefault(count, 0);
    }
}
```

#### InputView.java

```java
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
```

#### OutputView.java

```java
package example.view;

public class OutputView {

    public void printDiceNumberCount(int diceNumber, int count) {
        System.out.printf("%d은 %d번 나왔습니다.\n", diceNumber, count);
    }
}
```
