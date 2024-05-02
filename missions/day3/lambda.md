# 람다(Lambda)

강의에서 Spring Boot 프로젝트와 DB를 연동하는 과정을 학습했고, <br>
JdbcTemplate를 사용하는 과정에서 **익명 클래스**와 **람다식**을 사용했다.

[자바 개발자를 위한 코틀린 입문(Java to Kotlin Starter Guide) - 17강. 코틀린에서 람다를 다루는 방법](https://www.inflearn.com/course/lecture?courseSlug=java-to-kotlin&unitId=110633)
강의를 통해 익명 클래스와 람다에 대해 학습 할 수 있다.

```java
public class Fruit {

    private final String name;
    private final int price;

    public Fruit(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
```

- 1,000원 이상의 과일만 출력하는 경우 `fruitFilter` 메서드를 통해 필터링 할 수 있다.

```java
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Fruit> fruits = List.of(
                new Fruit("사과", 900),
                new Fruit("사과", 1_500),
                new Fruit("바나나", 500),
                new Fruit("수박", 5_000)
        );

        filterFruit(fruits);
    }

    private static List<Fruit> filterFruit(List<Fruit> fruits) {
        ArrayList<Fruit> result = new ArrayList<>();

        for (Fruit fruit : fruits) {
            if (fruit.getPrice() >= 1_000) {
                result.add(fruit);
            }
        }

        return result;
    }
}
```

하지만 요구사항이 다양하다면 무수한 조건 식과 메서드가 필요하다. <br>
이를 해결하기위해 익명 클래스를 사용한다.

인터페이스를 활용하여 메서드를 호출하는 쪽에서 **익명 클래스**를 구현함으로써 그때 그때 필요한 요구 조건을 만들 수 있다.

```java
public interface FruitFilter {

    boolean isSelected(Fruit fruit);
}
```

위와 같이 인터페이스를 만들고, `fruitFilter` 메서드의 파라미터로 `FruitFilter`를 받도록 구현한다면, `fruitFilter` 메서드를 호출하는 쪽에서 원하는 요구사항을 구현할 수 있다.

```java
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Fruit> fruits = List.of(
                new Fruit("사과", 900),
                new Fruit("사과", 1_500),
                new Fruit("바나나", 500),
                new Fruit("수박", 5_000)
        );

        filterFruit(fruits, new FruitFilter() {
            @Override
            public boolean isSelected(Fruit fruit) {
                return fruit.getPrice() >= 1_000;
            }
        });
    }

    private static List<Fruit> filterFruit(List<Fruit> fruits, FruitFilter condition) {
        ArrayList<Fruit> result = new ArrayList<>();

        for (Fruit fruit : fruits) {
            if (condition.isSelected(fruit)) {
                result.add(fruit);
            }
        }

        return result;
    }
}
```

- 익명 클래스를 사용하면 무수한 메서드 생성을 방지할 수 있다.
- 단점
    - **익명 클래스**를 사용하는 것은 복잡하다.
    - 다양한 요구 조건을 만족시켜야 하는 경우가 있을 수 있다.

이러한 단점을 해결하기위해 JDK 8부터 람다(이름이 없는 함수)가 등장했다. <br>
또한, Predicate, Consumer 등과 같은 여러 인터페이스들을 미리 만들어 놨다.

- 람다를 활용하면 호출하는 부분을 `filterFruit(fruits, fruit -> fruit.getPrice() > 1_000)`와 같이 변경할 수 있다.
- `filterFruit` 메서드는 JDK 8에서 미리 만들어둔 `Predicate`를 통해 리팩토링 할 수 있다.

> 람다는 `변수 -> 변수를 이용하는 함수` 또는 `(변수1, 변수2) -> 변수를 이용하는 함수` 형태로 표현할 수 있다.

- 또한, JDK 8에서는 `for`문과 `if`문을 활용한 코드를 갈결하게 표현할 수 있는 스트림이 등장했다.
- 스트림은 병렬처리에도 강점을 갖는다.

```java
import java.util.List;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        List<Fruit> fruits = List.of(
                new Fruit("사과", 900),
                new Fruit("사과", 1_500),
                new Fruit("바나나", 500),
                new Fruit("수박", 5_000)
        );

        filterFruit(fruits, fruit -> fruit.getPrice() >= 1_000);
    }

    private static List<Fruit> filterFruit(List<Fruit> fruits, Predicate<Fruit> condition) {
        return fruits.stream()
                .filter(condition)
                .toList();
    }
}
```

람다는 메서드 레퍼런스를 활용할 수 있다.

```java
import java.util.List;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        List<Fruit> fruits = List.of(
                new Fruit("사과", 900),
                new Fruit("사과", 1_500),
                new Fruit("바나나", 500),
                new Fruit("수박", 5_000)
        );

        filterFruit(fruits, Main::condition);
    }

    private static boolean condition(Fruit fruit) {
        return fruit.getPrice() >= 1_000;
    }

    private static List<Fruit> filterFruit(List<Fruit> fruits, Predicate<Fruit> condition) {
        return fruits.stream()
                .filter(condition)
                .toList();
    }
}
```

위의 코드와 같이 1,000원 이상을 검증하는 로직을 별도의 메서드로 분리한다면, `filterFruit(fruits, Main::condition)`와 같은 형태로 관리할 수 있다.

> Java에서 메서드를 `2급 시민`으로 간주하기 때문에, 변수에 할당되거나 파라미터로 전달할 수 없다.<br>
> 하지만 메서드 레퍼런스를 활용하면 메서드 자체를 넘겨주는 것 **처럼** 사용할 수 있다.

## 익명 클래스(Anonymous Class) 란?

- 익명 클래스는 내부 클래스(Inner Class)의 한 형태로, 이름이 지정되지 않은 클래스이다.
- 이 클래스는 정의와 동시에 인스턴스화되므로, 일반적으로 단 한 번만 사용되고 그 후에는 재사용되지 않는다.

> [By Oracle](https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html)
> - 클래스 선언과 인스턴스 생성을 한 번에 할 수 있어 코드를 간결하게 작성할 수 있다.
> - 이름이 없다는 점을 제외하면 로컬 클래스와 같고, 한 번만 필요한 경우 사용된다.

### 특징

- 코드의 간결성
    - 클래스의 선언과 인스턴스화를 한 번에 처리할 수 있다.
    - 이로인해 코드를 간결하게 만들고, 가독성을 높일 수 있다.

- 유연한 구현
    - 필요한 인터페이스를 즉석에서 구현할 수 있다.
    - 따라서 구현이 간단한 콜백 및 이벤트 처리와 같은 상황에 자주 활용된다.

- 재사용성의 감소
    - 한 번만 사용되는 경우 별도의 클래스로 분리할 필요가 없으므로 재사용성에 크게 의존하지 않는다.

## 람다 표현식(Lambda Expression)이 란?

- Java 8부터 도입된 기능으로, 함수형 프로그래밍 스타일을 지원한다.
- 이름이 없는 익명 함수(Anonymous Function)로, 간결한 방식으로 메서드를 표현할 수 있다.
- 이는 익명 클래스의 코드가 복잡해지는 단점을 보완한다.
- 메서드의 파라미터로 전달하거나 변수에 할당할 수 있다.
- 주로 함수형 인터페이스를 구현하기 위해 사용된다.

> 함수형 프로그래밍
> - 불변성
> - 고차 함수(Higher-order Function)
>   - 1급 객체로 사용
>   - 함수를 다른 함수의 인자로 전달하거나, 함수를 반환할 수 있다.
> - 순수 함수(Pure Function)
>   - 동일한 입력에 대해 항상 동일한 출력을 반환한다.
>   - 외부 상태에 의존하지 않으며, Side Effect를 최소화 한다.

### 람다식 문법

```text
(인자 목록) -> {함수 본문}
```

- 인자 목록
    - 인자는 괄호 `()`로 감싸서 작성한다.
    - 인자가 한 개인 경우에는 괄호 `()`를 생략할 수 있다.
        - `(one)` 또는 `one`
    - 인자의 타입은 컴파일러가 추론할 수 있으므로 생략할 수 있지만, 직접 명시 할 수 있다.
        - `(Integer one, Integer two)`

- 함수 본문
    - 화살표(`->`)의 오른쪽에 함수 본문을 정의한다.
    - 함수 본문은 중괄호(`{}`) 내부에 구현한다.
        - `name -> System.out.println(name)`
    - 함수 본문이 한 줄로 구성된 경우 중괄호 `{}`를 생략할 수 있고, `return`문으로만 이루어져 있다면 `return` 키워드도 생략할 수 있다.
        - `(one, two) -> one + two`

- 변수 캡쳐(Variable Capture)
    - 로컬 변수 캡처
        - 람다식이나 익명 클래스에서 메서드 내부의 로컬 변수를 사용할 수 있습니다.
        - `final`이거나 `effective final`인 경우에만 참조할 수 있다.
        - 이렇게 함으로써 다른 스레드에 의해서 변경되는 경우에 발생할 수 있는 concurrency 문제를 방지할 수 있다.
    - effective final
        - `final` 키워드를 명시적으로 사용하지 않았지만 한 번만 할당된 변수를 의미한다.
    - 익명 클래스와 람다의 차이
        - 익명 클래스는 새로운 스코프를 생성하기 때문에 내부에서는 외부 스코프의 변수를 쉐도우(shadow)할 수 있다.
        - 반면에 람다식은 람다를 감싸는 스코프와 동일한 스코프갖기 때문에 내부에서는 외부 변수를 직접 참조할 수 있다.

### 메서드 레퍼런스

메서드를 직접 가르키는 표현으로, 람다식을 더 간결하게 표현할 수 있고, 가독성을 높일 수 있다.

- 종류
    - 정적 메서드 레퍼런스
        - `클래스명::정적 메서드명`
    - 특정 객체의 인스턴스 메서드 레퍼런스
        - `객체 인스턴스::인스턴스 메서드명`
    - 임의 객체의 인스턴스 메서드 레퍼런스
        - `클래스명::인스턴스 메서드명`
    - 생성자 레퍼런스
        - `클래스명::new`

### 함수형 인터페이스(Functional Interface)

- 딱 하나의 추상 메서드를 가지고 있는 인터페이스
    - SAM(Single Abstract Method) 인터페이스라고 한다.
- `@FunctionalInterface` 어노테이션을 사용하면 함수형 인터페이스임을 명시적으로 표현할 수 있다.
    - 컴파일러가 해당 인터페이스가 함수형 인터페이스의 규칙을 따르는지 검증해준다.
- Java에서 제공하는 함수형 인터페이스는 `java.util.function` 패키지에 정의되어 있다.

#### Function<T, R>

```java
R apply(T t);
```

- T 타입의 인자를 받아서 R 타입을 반환하는 함수형 인터페이스
- 함수 조합용 메소드
    - `andThen`
    - `compose`

#### BiFunction<T, U, R>

```java
R apply(T t, U u);
```

- 두 개의 인자(T, U)를 받아서 R 타입을 반환하는 함수형 인터페이스

#### Consumer<T>

```java
void Accept(T t);
```

- T 타입의 인자를 받아서 아무 값도 반환하지 않는 함수형 인터페이스
- 함수 조합용 메소드
    - `andThen`

#### Supplier<T>

```java
T get();
```

- T 타입의 값을 반환하는 함수형 인터페이스

#### Predicate<T>

```java
boolean test(T t);
```

- T 타입의 인자를 받아서 boolean을 반환하는 함수형 인터페이스
- 함수 조합용 메소드
    - `And`
    - `Or`
    - `Negate`

#### UnaryOperator<T>

```java
<T> UnaryOperator<T> identity();
```

- Function<T, R>의 특수한 형태
- 하나의 T 타입의 인자를 받아서 동일한 타입을 반환하는 함수형 인터페이스

#### BinaryOperator<T>

- BiFunction<T, U, R>의 특수한 형태
- 동일한 타입의 인자를 두 개를 받아서 동일한 타입을 반환하는 함수형 인터페이스
- 2개의 인자와 반환 타입이 모두 동일

## 스트림(Stream) 이란?

#### Java 8에서 추가된 기능으로 일련의 데이터의 흐름을 나타내며, 이를 통해 데이터를 처리하는 방법

- 데이터를 담고 있는 저장소가 아니다.
- 함수형 프로그래밍의 개념을 따른다.
    - Funtional in nature
    - 데이터를 처리할 때, 소스 데이터를 변경하지 않는다.
- 스트림으로 처리하는 데이터는 한 번만 처리됩니다.
    - 중간 연산과 최종 연산을 수행하는 과정에서 데이터가 한 번만 흐르게 된다.
- 데이터 소스가 씉나지 않는 경우 무제한일 수도 있다.
    - 이런 경우에는 `Short Circuit` 메소드를 사용해서 제한할 수 있다.
- 중개 연산은 근본적으로 lazy 하게 동작한다.
- 스트림 API가 내부적으로 병렬 실행을 지원하기 때문에 쉽게 병렬 처리할 수 있다.

### 스트림 파이프라인

- 0 또는 다수의 중개 연산과 한 개의 종료 연산으로 구성
- 스트림의 소스 데이터는 오직 터미널 연산을 실행할 때만 처리한다.

### 중개 연산(Intermediate Operation)

- 스트림을 리턴한다.
- Stateless / Stateful 연산으로 구분할 수 있다.
    - Stateless 연산: `map`, `filter`와 같이 각 요소를 독립적으로 처리한다. 따라서 병렬 처리가 쉽다.
    - Statefull 연산: `sorted`, `distinct`와 같이 전체 스트림을 순회하며 상태를 유지하기 때문에 병렬 처리에 제약이 있을 수 있다.
- filter, map, limit, skip, sorted, ...

### 종료 연산(terminal operation)

- 스트림을 리턴하지 않는다.
- collect, allMatch, count, forEach, min, max, ...

### 스트림 API

#### 걸러내기

- Filter(Predicate)

#### 변경하기

- Map(Function) 또는 FlatMap(Function)

#### 생성하기

- generate(Supplier) 또는 Iterate(T seed, UnaryOperator)

#### 제한하기

- limit(long) 또는 skip(long)

#### 특정 조건을 만족하는지 확인하기

- anyMatch(), allMatch(), nonMatch()

#### 개수 세기

- count()

#### 데이터 하나로 뭉치기

- reduce(identity, BiFunction), collect(), sum(), max()
