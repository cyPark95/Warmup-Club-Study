# HTTP(HyperText Transfer Protocol)

- 인터넷에서 데이터를 주고받기 위한 표준 프로토콜
- Protocol: 통신을 위한 규칙과 약속

## 규칙

```markdown
GET /user?name=spring
Host spring.com:8080

email@spring.com
```

- HTTP Method: `GET`
    - 요청을 받는 컴퓨터에게 요구하는 **행위**
        - GET: 데이터 요청(쿼리 사용)
        - POST: 데이터 전송(바디 사용)
        - PUT: 데이터 수정(바디 사용)
        - DELETE: 데이터 삭제(쿼리 사용)
- Host: `spring.com:8080`
    - 요청을 받는 컴퓨터의 정보를 나타냅니다.
- Path: `/user?name=spring`
    - 요청하는 자원의 경로를 나타냅니다.

## 정보 전달 방법

1. Query: `name=spring`
    - URL에 포함되어 데이터를 전달
    - `?`를 통해 쿼리와 Path를 구분한다.
    - 쿼리 사이에는 `&`를 통해 구분한다.
2. Body: `email@spring.com`
    - 별도의 본문을 통해 데이터를 전달

## 요청 문법

```http request
POST /user/me
Host: spring.com:8080

email@spring.com
```

1. 메소드, 경로, 쿼리
2. 헤더(여러 줄 가능)
3. 한 줄 공백
4. 바디(여러 줄 가능)

## URL(Uniform Resource Locator)

```markdown
http://spring.com:8080/user?name=spring
```

- 프로토콜: `http`
    - 사용하는 통신 프로토콜
- 도메인이름:포트: `spring.com:8080`
    - 서버의 주소
    - 도메인 이름은 IP로 대체 가능
- 자원 경로: `/user`
    - 요청하는 자원의 위치
- 쿼리: `name=spring`
    - 요청에 필요한 데이터

## 응답 문법

```http request
HTTP/1.1 200 OK
Content-Type: application/json

{
    "name": "spring",
    "age": null
}
```

1. 상태코드
2. 헤더(여러 줄 가능)
3. 한 줄 공백
4. 바디(여러 줄 가능)

### 상태 코드

- 200(OK): 성공
- 300(Moved Permanently): 리다이렉션
- 404(Not Found): 찾을 수 없음
- 500(Internal Server Error): 서버 내부 오류
