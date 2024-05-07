# API 계충 분리

![문제](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/e184bcc5-bf12-4707-923c-eb6f2479d45c)

## 문제 1

#### FruitController.java

HTTP Request/Reponse를 담당하는 역할

```java
package com.group.mission.controller.fruit;

import com.group.mission.dto.fruit.request.FruitSaveRequest;
import com.group.mission.dto.fruit.request.FruitSoldRequest;
import com.group.mission.dto.fruit.request.FruitStatisticsRequest;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import com.group.mission.service.fruit.FruitService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fruit")
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping
    public void saveFruit(@RequestBody @Valid FruitSaveRequest request) {
        fruitService.saveFruit(request);
    }

    @PutMapping
    public void soldFruit(@RequestBody @Valid FruitSoldRequest request) {
        fruitService.soldFruit(request);
    }

    @GetMapping("/stat")
    public FruitStatisticResponse getStatistic(@Valid FruitStatisticsRequest request) {
        return fruitService.finStatistic(request);
    }
}
```

- HTTP 요청 수신
- 요청 데이터 바인딩
- 데이터 유효성 검증
- 비즈니스 로직 호출
- HTTP 응답 생성

#### FruitService.java

비즈니스 로직을 담당하는 역할

```java
package com.group.mission.service.fruit;

import com.group.mission.domain.Fruit;
import com.group.mission.dto.fruit.request.FruitSaveRequest;
import com.group.mission.dto.fruit.request.FruitSoldRequest;
import com.group.mission.dto.fruit.request.FruitStatisticsRequest;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import com.group.mission.repository.fruit.FruitRepository;
import org.springframework.stereotype.Service;

@Service
public class FruitService {

    private final FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public Fruit saveFruit(FruitSaveRequest request) {
        Fruit fruit = request.toDomain();
        return fruitRepository.save(fruit);
    }

    public void soldFruit(FruitSoldRequest request) {
        Fruit fruit = findById(request);
        fruit.sold();
        fruitRepository.save(fruit);
    }

    public FruitStatisticResponse finStatistic(FruitStatisticsRequest request) {
        return fruitRepository.findStatistic(request.name());
    }

    private Fruit findById(FruitSoldRequest request) {
        return fruitRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 ID[%d] 입니다.", request.id())));
    }
}
```

- 비즈니스 로직 수행
- 예외 처리

#### FruitRepository.java

데이터를 관리를 담당하는 역할

```java
package com.group.mission.repository.fruit;

import com.group.mission.domain.Fruit;
import com.group.mission.domain.FruitStatus;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class FruitRepository {

    private long sequence = 1L;
    private final static Map<Long, Fruit> FRUIT_STORE = new ConcurrentHashMap<>();

    @Override
    public Fruit save(Fruit fruit) {
        Fruit newFruit = new Fruit(
                sequence,
                fruit.getName(),
                fruit.getWarehousingDate(),
                fruit.getPrice(),
                fruit.getStatus()
        );

        FRUIT_STORE.put(sequence++, newFruit);
        return newFruit;
    }

    @Override
    public Optional<Fruit> findById(Long id) {
        return Optional.ofNullable(FRUIT_STORE.get(id));
    }

    @Override
    public FruitStatisticResponse findStatistic(String name) {
        Map<FruitStatus, Long> fruitStatusMap = calculateFruitStatus(name);
        return new FruitStatisticResponse(
                fruitStatusMap.getOrDefault(FruitStatus.SOLD, 0L),
                fruitStatusMap.getOrDefault(FruitStatus.REGISTERED, 0L)
        );
    }

    private static Map<FruitStatus, Long> calculateFruitStatus(String name) {
        return FRUIT_STORE.values().stream()
                .filter(fruit -> fruit.getName().equals(name))
                .collect(Collectors.toMap(
                        Fruit::getStatus,
                        Fruit::getPrice,
                        Long::sum
                ));
    }
}
```

- DB와 상호작용
- 데이터 가공

## 문제 2

추상화와 의존성 주입(Dependency Injection)을 이용하면, 클라이언트 코드의 수정 없이도 Repository 계층의 구현체를 변경할 수 있다.

### 추상화

#### FruitRepository.java

```java
package com.group.mission.repository.fruit;

import com.group.mission.domain.Fruit;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;

import java.util.Optional;

public interface FruitRepository {

    Fruit save(Fruit fruit);

    Optional<Fruit> findById(Long id);

    FruitStatisticResponse findStatistic(String name);
}
```

- `FruitRepository`를 인터페이스로 추상화 함으로써, 데이터 접근 계층에 대한 구체적인 구현을 숨길 수 있다.
- `FruitRepository` 인터페이스에 따라 `FruitMemoryRepository`와 `FruitMySqlRepository`를 구현한다.

#### FruitMemoryRepository.java

```java
package com.group.mission.repository.fruit;

import com.group.mission.domain.Fruit;
import com.group.mission.domain.FruitStatus;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class FruitMemoryRepository implements FruitRepository {

    private long sequence = 1L;
    private final static Map<Long, Fruit> FRUIT_STORE = new ConcurrentHashMap<>();

    @Override
    public Fruit save(Fruit fruit) {
        Fruit newFruit = new Fruit(
                sequence,
                fruit.getName(),
                fruit.getWarehousingDate(),
                fruit.getPrice(),
                fruit.getStatus()
        );

        FRUIT_STORE.put(sequence++, newFruit);
        return newFruit;
    }

    @Override
    public Optional<Fruit> findById(Long id) {
        return Optional.ofNullable(FRUIT_STORE.get(id));
    }

    @Override
    public FruitStatisticResponse findStatistic(String name) {
        Map<FruitStatus, Long> fruitStatusMap = calculateFruitStatus(name);
        return new FruitStatisticResponse(
                fruitStatusMap.getOrDefault(FruitStatus.SOLD, 0L),
                fruitStatusMap.getOrDefault(FruitStatus.REGISTERED, 0L)
        );
    }

    private static Map<FruitStatus, Long> calculateFruitStatus(String name) {
        return FRUIT_STORE.values().stream()
                .filter(fruit -> fruit.getName().equals(name))
                .collect(Collectors.toMap(
                        Fruit::getStatus,
                        Fruit::getPrice,
                        Long::sum
                ));
    }
}
```

#### FruitMySqlRepository.java

```java
package com.group.mission.repository.fruit;

import com.group.mission.domain.Fruit;
import com.group.mission.domain.FruitStatus;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class FruitMySqlRepository implements FruitRepository {

    private static final String TABLE = "fruit";
    public static final RowMapper<Fruit> FRUIT_ROW_MAPPER = (rs, rowNum) -> new Fruit(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getObject("warehousingDate", LocalDate.class),
            rs.getLong("price"),
            FruitStatus.valueOf(rs.getString("status"))
    );

    private final JdbcTemplate jdbcTemplate;

    public FruitMySqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Fruit save(Fruit fruit) {
        if (Objects.isNull(fruit.getId())) {
            return insert(fruit);
        }

        return update(fruit);
    }

    @Override
    public Optional<Fruit> findById(Long id) {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", TABLE);

        List<Fruit> fruit = jdbcTemplate.query(
                sql,
                ps -> ps.setLong(1, id),
                FRUIT_ROW_MAPPER
        );
        return DataAccessUtils.optionalResult(fruit);
    }

    @Override
    public FruitStatisticResponse findStatistic(String name) {
        String sql = String.format("SELECT status, SUM(price) AS total_amount FROM %s WHERE name = ? GROUP BY status", TABLE);

        return jdbcTemplate.query(
                sql,
                ps -> ps.setString(1, name),
                this::createFruitStatisticResponse
        );
    }

    private FruitStatisticResponse createFruitStatisticResponse(ResultSet rs) throws SQLException {
        long salesAmount = 0;
        long notSalesAmount = 0;

        while (rs.next()) {
            String status = rs.getString("status");
            if (FruitStatus.valueOf(status) == FruitStatus.SOLD) {
                salesAmount += rs.getLong("total_amount");
            } else {
                notSalesAmount += rs.getLong("total_amount");
            }
        }

        return new FruitStatisticResponse(salesAmount, notSalesAmount);
    }

    private Fruit insert(Fruit fruit) {
        String sql = String.format("INSERT INTO %s (name, warehousingDate, price, status) VALUES (?, ?, ?, ?)", TABLE);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, fruit.getName());
                    ps.setObject(2, fruit.getWarehousingDate());
                    ps.setLong(3, fruit.getPrice());
                    ps.setString(4, fruit.getStatus().name());
                    return ps;
                },
                keyHolder);
        return createFruit(keyHolder.getKey(), fruit);
    }

    private Fruit createFruit(Number key, Fruit fruit) {
        if (Objects.isNull(key)) {
            throw new RuntimeException("데이터 삽입에 실패하였습니다.");
        }

        return new Fruit(
                key.longValue(),
                fruit.getName(),
                fruit.getWarehousingDate(),
                fruit.getPrice(),
                fruit.getStatus()
        );
    }

    private Fruit update(Fruit fruit) {
        String sql = String.format("UPDATE %s SET name = ?, warehousingDate = ?, price = ?, status = ? WHERE id = ?", TABLE);

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, fruit.getName());
            ps.setObject(2, fruit.getWarehousingDate());
            ps.setLong(3, fruit.getPrice());
            ps.setString(4, fruit.getStatus().name());
            ps.setLong(5, fruit.getId());
            return ps;
        });
        return fruit;
    }
}
```

#### FruitService.java

```java
package com.group.mission.service.fruit;

import com.group.mission.domain.Fruit;
import com.group.mission.dto.fruit.request.FruitSaveRequest;
import com.group.mission.dto.fruit.request.FruitSoldRequest;
import com.group.mission.dto.fruit.request.FruitStatisticsRequest;
import com.group.mission.dto.fruit.response.FruitStatisticResponse;
import com.group.mission.repository.fruit.FruitRepository;
import org.springframework.stereotype.Service;

@Service
public class FruitService {

    private final FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    // 메서드 생략
}
```

- `FruitService`에서는 구현체가 아닌 `FruitRepository` 인터페이스에 의존하여, 의존성 역전 원칙(Dependency Inversion Principle)을 적용한다.

### 의존성 주입

`@Service`, `@Repository` 어노테이션을 사용한 클래스는 스프링 컨테이너에 등록된다.<br>
스프링은 객체 생성 과정에서 필요한 의존성을 자동으로 주입해 준다.

그러나 현재 상황에서는 `FruitRepository`의 구현체가 2개이므로 각각 스프링 빈으로 등록되고,
이로 인해 스프링은 `FruitService`에 어떤 빈을 주입해야 할지 알 수 없어서 애플리케이션 실행 시 에러가 발생합니다.

```text
***************************
APPLICATION FAILED TO START
***************************

Description:

Parameter 0 of constructor in com.group.mission.service.fruit.FruitService required a single bean, but 2 were found:
	- fruitMemoryRepository: defined in file [Warmup-Club-Study\missions\day6\mission\build\classes\java\main\com\group\mission\repository\fruit\FruitMemoryRepository.class]
	- fruitMySqlRepository: defined in file [Warmup-Club-Study\missions\day6\mission\build\classes\java\main\com\group\mission\repository\fruit\FruitMySqlRepository.class]

This may be due to missing parameter name information

Action:

Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed
```

- Description
    - `FruitService`의 생성자 0번째 매개변수에는 하나의 빈이 필요하지만, 2개의 빈이 발견
    - 어떤 클래스인지도 친절하게 알려준다.
- Action
    - 둘 중 하나를 `@Primary`로 지정
    - 여러개의 빈을 수용할 수 있도록 업데이트
    - 어떤 빈을 사용할지 식별하기 위해 `@Qualifier`를 사용

스프링에서 제시해준 3가지 방법 중 `@Primary`와 `@Quialifier`를 통해 문제를 해결해 본다.

#### 우선순위 지정

`@Primary`로 지정하면 중복된 여러 스프링 빈 중 먼저 주입 될 우선순위를 지정할 수 있다.

- 우선적으로 주입할 클래스에 `@Primary` 어노테이션을 적용하면 된다.

```java

@Primary
@Repository
public class FruitMySqlRepository implements FruitRepository {
    // 구현 내용 생략
}
```

하지만 `@Primary`는 몇 가지 단점이 있다.

- 한 개의 빈만 우선순위로 설정할 수 있다.
- 우선순위를 명시적으로 지정하는 것이 아니기 때문에, 우선순위를 파악하기 위해선 `@Primary` 어노테이션을 찾봐야 한다.

#### 스프링 빈 식별

`@Qualifier` 어노테이션을 사용하여 빈을 식별할 수 있다.

- `@Qualifier` 어노테이션은 스프링 빈을 명시적으로 식별하기 위해 식별 값을 지정할 수 있다.
- 만약 식별 값을 생략한다면, 스프링은 기본적으로 해당 스프링 빈의 클래스 이름을 사용한다.

```java

@Qualifier("fruitMemoryRepository")
@Repository
public class FruitMemoryRepository implements FruitRepository {
    // 구현 내용 생략
}
```

```java

@Qualifier("fruitMySqlRepository")
@Repository
public class FruitMySqlRepository implements FruitRepository {
    // 구현 내용 생략
}
```

- 식별 값을 지정한 @Qualifier 어노테이션을 생성자나 필드, 메소드의 매개변수에 적용하여 해당 스프링 빈을 주입받을 수 있다.

```java

@Service
public class FruitService {

    private final FruitRepository fruitRepository;

    @Autowired
    public FruitService(@Qualifier("fruitMySqlRepository") FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    // 이하 메서드들 생략
}
```

> `@Primary`와 `@Qualifier` 우선순위
>
> 스프링은 기본적으로 자동보다 수동으로 지정하는 것이 높은 우선순위를 갖는다.<br>
> 따라서, 자동적으로 우선순위를 지정해주는 `@Primary` 어노테이션 보다 명시적으로 지정하는 `@Qualifier` 어노테이션이 높은 우선순위를 갖는다.
