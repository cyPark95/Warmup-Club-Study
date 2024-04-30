## 문제 1

![문제1](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/2a64a197-93db-43f9-ab7d-05f8d2d25156)

- HTTP Request

```text
HTTP GET /api/v1/calc?num1=10&num2=5
Host: localhost:8080
```

- HTTP Response

```text
Status Code: 200 OK
Content-Type: application/json

{
    "add": 15,
    "minus": 5,
    "multiply": 50
}
```

### POST MAN 테스트 결과

![Postman1](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/42b1a890-c207-473d-ac0f-5764efe854f7)

## 문제 1

![문제2](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/9e6e5446-7e4d-4656-bcd2-52ef1ed9be85)

- HTTP Request

```text
HTTP GET /api/v1/day-of-the-week?date=2023-01-01
Host: localhost:8080
```

- HTTP Response

```text
Status Code: 200 OK
Content-Type: application/json

{
    "dayOfTheWeek": "SUN"
}
```

### POST MAN 테스트 결과

![Postman2](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/496046b0-0012-4cc2-9a06-487fb7045fd6)

## 문제 1

![문제3](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/3bb7542c-0872-40d3-86f1-a80d2028093e)

- HTTP Request

```text
HTTP POST /api/v1/sum-of-numbers
Host: localhost:8080
Content-Type: application/json

{
    "numbers": [1, 2, 3, 4, 5]
}
```

- HTTP Response

```text
Status Code: 200 OK
Content-Type: application/json

15
```

### POST MAN 테스트 결과

![Postman3](https://github.com/cyPark95/Warmup-Club-Study/assets/139435149/69aa0eff-1c8d-4b24-b99c-30c0ff1b7548)
