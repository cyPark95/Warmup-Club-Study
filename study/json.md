# JSON(JavaScript Object Notation)

- 객체 표기법, 즉 무언가를 표현하기 위한 형식이다.
- HTTP 프로토콜을 사용하는 통신에서 JSON은 주로 Request/Response의 Body 데이터로 사용된다.

## 용법

```json
{
  "name": "Spring",
  "age": 20
}
```

- 중괄호(`{}`)로 묶여 있다.
- 중괄호 안에, "key": value로 표기한다.
- 키("key")와 값("value")은 `:`로 구분된다.
- 속성(키-값)은 `,`로 구분한다.

### 데이터 타입

- 값("value)에는 다양한 타입이 올 수 있다.
    - 문자열(String)
    - 숫자(Number)
    - 객체(Object)
    - 배열(Array)
    - 불리언(Boolean)
    - null
- Java의 Map<String, Object>와 유사하다.

## HTTP 통신

- GET 요청에서는 주로 URL의 쿼리 스트링(Query String)을 통해 데이터를 전달한다.
- POST 요청에서는 주로 Body에 JSON 형식의 데이터를 포함하여 서버로 전송한다.
