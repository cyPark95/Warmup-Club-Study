![문제 1,2](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/d94f6e1d-7557-4fc6-ad22-9d1af858ac4f)
![문제3](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/f2cfacee-f67b-4dbc-8d4c-dff1e5b957d4)

# 문제 1

기존 코드를 Spring Data JPA로 변경

1. 의존성 추가
    - 빌드 도구(`Gradle` 사용)를 사용하여 Spring Data JPA 의존성을 추가한다.
   ```groovy
   implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
   ```

2. Entity 클래스 생성
    - 데이터베이스의 테이블과 매핑되는 클래스를 Entity 클래스를 생성한다.

   ```java
   package com.group.mission.domain.fruit;

   public enum FruitStatus {
       REGISTERED, SOLD
   }
   ```

   ```java
   package com.group.mission.domain.fruit;
   
   import jakarta.persistence.*;
   
   import java.time.LocalDate;
   
   @Entity
   public class Fruit {
   
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
   
       @Column(nullable = false, length = 20)
       private String name;
   
       @Column(nullable = false)
       private LocalDate warehousingDate;
   
       @Column(nullable = false)
       private Long price;
   
       @Enumerated(EnumType.STRING)
       private FruitStatus status;
   
       protected Fruit() {
       }
   
       public Fruit(String name, LocalDate warehousingDate, Long price) {
           this.name = name;
           this.warehousingDate = warehousingDate;
           this.price = price;
           this.status = FruitStatus.REGISTERED;
       }
   
       public Long getId() {
           return id;
       }
   
       public String getName() {
           return name;
       }
   
       public LocalDate getWarehousingDate() {
           return warehousingDate;
       }
   
       public long getPrice() {
           return price;
       }
   
       public FruitStatus getStatus() {
           return status;
       }
   
       public void sold() {
           this.status = FruitStatus.SOLD;
       }
   }
   ```
    - `@Entity`를 통해 JPA Entity 클래스를 명시한다.
    - `@Id`를 통해 기본 키(Primary Key) 필드를 나타낸다.
    - `@GeneratedValue`를 통해 기본 키의 값을 자동으로 생성하는 방법을 지정한다.
        - IDENTITY 전략을 사용하여 데이터베이스의 자동 증가(`AUTO_INCREMENT`) 기능 활용

3. Repository 인터페이스 생성
    - Repository 인터페이스는 데이터베이스와 상호작용을 위한 메서드들을 정의하면, Spring Data JPA가 자동으로 해당 메서드들의 구현체를 생성하여 제공한다.
   ```java
   package com.group.mission.repository.fruit;
   
   import com.group.mission.domain.fruit.Fruit;
   import org.springframework.data.jpa.repository.JpaRepository;
   
   import java.util.List;
   
   public interface FruitRepository extends JpaRepository<Fruit, Long> {
   
        @Query("SELECT new com.group.mission.dto.fruit.response.FruitStatisticResponse(" +
            "SUM(CASE WHEN f.status = 'SOLD' THEN f.price END), " +
            "SUM(CASE WHEN f.status = 'REGISTERED' THEN f.price END)" +
            ") " +
            "FROM Fruit f " +
            "WHERE f.name = :name")
        FruitStatisticResponse findStatistic(String name);
   }
   ```
    - `JpaRepository<Fruit, Long>` 인터페이스를 확장하여 Spring Data JPA에서 제공하는 기본적인 CRUD 메서드들을 상속받을 수 있다.
    - Spring Data JPA 구현체 `SimpleJpaRepository`에 `@Repository` 어노테이션이 붙어 있기 때문에 별도로 `@Repository` 어노테이션을 사용하지 않아도 스프링
      빈으로 등록된다.
    - Spring Data JPA에서 자동으로 만들어주지 않는 쿼리의 경우, `@Query` 어노테이션을 통해 JPQL(JPA Query Language) 쿼리를 작성한다.
        - Projection을 통해 필요한 속성만 조회하고, DTO 클래스에 매핑한다.

   ```java
   package com.group.mission.service.fruit;
   
   import com.group.mission.domain.fruit.Fruit;
   import com.group.mission.domain.fruit.FruitStatus;
   import com.group.mission.dto.fruit.request.*;
   import com.group.mission.dto.fruit.response.FruitStatisticResponse;
   import com.group.mission.repository.fruit.FruitRepository;
   import org.springframework.stereotype.Service;
   import org.springframework.transaction.annotation.Transactional;
   
   import java.util.List;
   
   @Service
   @Transactional(readOnly = true)
   public class FruitService {
   
       private final FruitRepository fruitRepository;
   
       public FruitService(FruitRepository fruitRepository) {
           this.fruitRepository = fruitRepository;
       }
   
       @Transactional
       public void saveFruit(FruitSaveRequest request) {
           Fruit fruit = request.toDomain();
           fruitRepository.save(fruit);
       }
   
       @Transactional
       public void soldFruit(FruitSoldRequest request) {
           Fruit fruit = findById(request);
           fruit.sold();
       }
   
       public FruitStatisticResponse findStatistic(FruitStatisticsRequest request) {
           return fruitRepository.findStatistic(request.name());
       }
   
       private Fruit findById(FruitSoldRequest request) {
           return fruitRepository.findById(request.id())
                   .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 ID[%d] 입니다.", request.id())));
       }
   }
   ```

    - 클래스 레벨에서 `@Transactional` 어노테이션의 `readOnly` 속성을 `true`로 설정하여 해당 클래스의 모든 메서드는 읽기 전용 트랜잭션으로 실행된다.
        - CUD 작업이 필요한 경우에는 메서드 레벨에 `@Transactional` 어노테이션을 사용하여 트랜잭션을 관리한다.
    - `soldFruit` 메서드에서는 JPA의 변경감지를 이용하여 별도의 `save` 메서드를 사용하지 않는다.

4. JPA 설정 구성
   ```yaml
   spring:
     jpa:
       hibernate:
        ddl-auto: none
       properties:
        hibernate:
          show_sql: true
          format_sql: true
          dialect: org.hibernate.dialect.MySQL8Dialect
   ```

    - `spring.jpa.hibernate.ddl-auto`: JPA가 데이터베이스의 스키마를 자동으로 관리하는 방식을 지정
        - `create`: 기존 테이블이 있다면 삭제 후 다시 생성
        - `create`-drop: 스프링이 종료될 때 테이블을 모두 제거
        - `update`: 객체와 테이블이 다른 부분만 변경
        - `validate`: 객체와 테이블이 동일한지 확인
        - `none`: 별다를 조치를 하지 않는다
    - `spring.jpa.properties.hibernate.show_sql`: Hibernate가 생성한 SQL 쿼리를 로깅할지 지정
    - `spring.jpa.properties.hibernate.format_sql`: Hibernate가 생성한 SQL을 보기 쉽게 포맷팅할지 지정
    - `spring.jpa.properties.hibernate.dialect`: Hibernate가 사용할 데이터베이스의 방언(Dialect)을 지정

# 문제 2

판매된 과일의 수 조회

#### FruitRepository.java

```java
public interface FruitRepository extends JpaRepository<Fruit, Long> {

    FruitCountResponse countByNameAndStatus(String name, FruitStatus status);
}
```

- Spring Data JPA는 By 앞에 `count`를 사용하면 쿼리 결과 개수를 조회할 수 있다.

#### FruitService.java

```java

@Service
@Transactional(readOnly = true)
public class FruitService {

    private final FruitRepository fruitRepository;

    // 이하 메서드들 생략

    public FruitCountResponse findFruitCount(FruitCountRequest request) {
        return fruitRepository.countByNameAndStatus(request.name(), FruitStatus.SOLD);
    }
}
```

#### FruitController.java

```java

@RestController
@RequestMapping("/api/v1/fruit")
public class FruitController {

    private final FruitService fruitService;

    // 이하 메서드들 생략

    @GetMapping("/count")
    public FruitCountResponse getFruitCount(@Valid FruitCountRequest request) {
        return fruitService.findFruitCount(request);
    }
}
```

HTTP 데이터를 매핑 할 Request/Response DTO 객체는 Java 16에서 정식으로 추가된 record를 사용했다.

#### FruitCountRequest.java

```java
package com.group.mission.dto.fruit.request;

import jakarta.validation.constraints.NotEmpty;

public record FruitCountRequest(
        @NotEmpty
        String name
) {
}
```

#### FruitCountResponse.java

```java
package com.group.mission.dto.fruit.response;

public record FruitCountResponse(
        Long count
) {
}
```

## 문제 3

판매되지 않은 특정 금액 이상 또는 이하의 과일 목록 조회

#### FruitRepository.java

```java
public interface FruitRepository extends JpaRepository<Fruit, Long> {

    // 이하 메서드들 생략

    List<Fruit> findAllByPriceGreaterThanEqualAndStatus(long price, FruitStatus status);

    List<Fruit> findAllByPriceLessThanEqualAndStatus(long price, FruitStatus status);
}
```

- Spring Data JPA는 By 뒤에 `GreateThanEqual`, `LessThanEqual`을 통해 조회결과에 이상, 이하 조건을 추가할 수 있다.

#### FruitService.java

#### ComparisonOperator.java

```java
package com.group.mission.common;

public enum ComparisonOperator {
    GTE, LTE
}
```

- GTE (Greater Than or Equal)와 LTE (Less Than or Equal)를 열거형을 통해 정의한다.

```java

@Service
@Transactional(readOnly = true)
public class FruitService {

    private final FruitRepository fruitRepository;

    // 이하 메서드들 생략

    public List<FruitResponse> findRegisteredFruits(FruitRequest request) {
        List<Fruit> fruits = findFruitsByComparisonOperator(request, FruitStatus.REGISTERED);
        return fruits.stream()
                .map(FruitResponse::from)
                .toList();
    }

    private List<Fruit> findFruitsByComparisonOperator(FruitRequest request, FruitStatus status) {
        if (request.option() == ComparisonOperator.LTE) {
            return fruitRepository.findAllByPriceLessThanEqualAndStatus(request.price(), status);
        }
        return fruitRepository.findAllByPriceGreaterThanEqualAndStatus(request.price(), status);
    }
}
```

- `findFruitsByComparisonOperator` 메서드에서는 요청으로 들어온 `option` 조건에 맞는 `FruitRepository` 메서드를 호출한다.
- `FruitResponse` 클래스는 정적 팩토리 메서드를 통해 생성하도록 구현하여 메서드 레퍼런스를 사용함으로써 스트림 연산을 간결하게 처리할 수 있다.

#### FruitController.java

```java

@RestController
@RequestMapping("/api/v1/fruit")
public class FruitController {

    private final FruitService fruitService;

    // 이하 메서드들 생략

    @GetMapping("/list")
    public List<FruitResponse> getFruits(@Valid FruitRequest request) {
        return fruitService.findFruits(request);
    }
}
```

#### FruitRequest.java

```java
package com.group.mission.dto.fruit.request;

import com.group.mission.common.ComparisonOperator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FruitRequest(
        @NotNull
        ComparisonOperator option,
        @Min(0)
        long price
) {
}
```

#### FruitResponse.java

```java
package com.group.mission.dto.fruit.response;

import com.group.mission.domain.fruit.Fruit;

import java.time.LocalDate;

public record FruitResponse(
        String name,
        Long price,
        LocalDate warehousingDate
) {

    public static FruitResponse from(Fruit fruit) {
        return new FruitResponse(fruit.getName(), fruit.getPrice(), fruit.getWarehousingDate());
    }
}
```
