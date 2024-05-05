## Fruit 테이블

```sql
CREATE TABLE `fruit`
(
    id              BIGINT AUTO_INCREMENT,
    name            VARCHAR(20) NOT NULL,
    warehousingDate DATE        NOT NULL,
    price           BIGINT      NOT NULL,
    is_sold         BOOLEAN DEFAULT FALSE,

    PRIMARY KEY (id)
);
```

## 문제 1

![문제1](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/ac18b6fa-4121-465a-9f3a-b8d2d43313fc)

- HTTP Request

```text
HTTP POST /api/v1/fruit
Host: localhost:8080

{
    "name": "사과",
    "warehousingDate": "2024-02-01",
    "price": 5000
}
```

- HTTP Response

```text
Status Code: 200 OK
```

### POST MAN 테스트 결과

![Postman1](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/997c095a-ea9e-4083-996a-5e59c1b29a39)

> 한 걸음 더!
>
> 자바에서 정수를 다루는 대표적인 두 가지 방법 `int`와 `long`의 차이점은 범위이다.
> - `int`: 4Byte
> - `long`: 8Byte
>
> API에서 `long`을 사용한 이유는 가격이 `int`의 범위를 초과할 수 있기 때문이다.
> 초과하는 경우, Overflow로 인해 예상하지 못한 결과가 발생할 수 있다.
>
> ID(PK) 필드 역시, 데이터가 계속 쌓인다면 `int` 범위를 초과할 수 있으므로 `Long`을 사용한다.

## 문제 2

![문제2](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/7183de54-1f5e-4608-897f-faf562e545aa)

- HTTP Request

```text
HTTP PUT /api/v1/fruit
Host: localhost:8080

{
    "id": 3
}
```

- HTTP Response

```text
Status Code: 200 OK
```

### POST MAN 테스트 결과

![Postman2](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/87f9f756-d4b3-4804-94df-91d4bef1ff25)

## 문제 3

![문제3](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/a437eba2-c435-410e-abad-568e2a7e3914)

- HTTP Request

```text
HTTP GET /api/v1/fruit/stat?name=사과
Host: localhost:8080
```

- HTTP Response

```text
Status Code: 200 OK
Content-Type: application/json

{
    "salesAmount": 6000,
    "notSalesAmount": 4000
}
```

### POST MAN 테스트 결과

![Postman3](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/bc9d814a-adf1-4964-ac30-79147d4a624d)
