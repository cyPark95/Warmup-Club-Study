# 도서 관리 애플리케이션

## 요구사항

- 사용자
    - 도서관의 사용자를 등록할 수 있다. (이름 필수, 나이 선택)
        - HTTP Request
            - HTTP Method : POST
            - HTTP Path : /user
            - HTTP Body (JSON)
              ```json
              {
                "name": String (null 불가능),
                "age": Integer
              }
              ```
        - HTTP Response
            - HTTP 상태 200 OK, 결과 반환 X
    - 도서관 사용자의 목록을 볼 수 있다.
        - HTTP Request
            - HTTP Method : GET
            - HTTP Path : /user
            - 쿼리 : 없음
        - HTTP Response
          ```json
          [{
            "id": Long,
            "name": String (null 불가능),
            "age": Integer
          }, ...]
          ```
    - 도서관 사용자 이름을 업데이트 할 수 있다.
        - HTTP Request
            - HTTP Method : PUT
            - HTTP Path : /user
            - HTTP Body (JSON)
              ```json
              {
                "id": Long,
                "name": String(변경되어야 하는 사용자 이름)
              }
              ```
        - HTTP Response
            - HTTP 상태 200 OK, 결과 반환 X
    - 도서관 사용자를 삭제할 수 있다.
        - HTTP Request
            - HTTP Method : DELETE
            - HTTP Path : /user
            - 쿼리 :
                - 문자열 name (삭제되어야 하는 사용자 이름)
        - HTTP Response
            - HTTP 상태 200 OK, 결과 반환 X


- 책
    - 도서관에 책을 등록할 수 있다.
        - HTTP Request
            - HTTP Method : POST
            - HTTP Path : /book
            - HTTP Body (JSON)
              ```json
              {
                "name": String
              }
              ```
        - HTTP Response
            - HTTP 상태 200 OK, 결과 반환 X
    - 사용자가 책을 빌릴 수 있다.
        - 다른 사람이 그 책을 진작 빌렸다면 빌릴 수 없다.
        - HTTP Request
            - HTTP Method : POST
            - HTTP Path : /book/loan
            - HTTP Body (JSON)
              ```json
              {
                "userName": String,
                "bookName": String
              }
              ```
        - HTTP Response
            - HTTP 상태 200 OK, 결과 반환 X
    - 사용자가 책을 반납할 수 있다.
        - HTTP Request
            - HTTP Method : PUT
            - HTTP Path : /book/return
            - HTTP Body (JSON)
              ```json
              {
                "userName": String,
                "bookName": String
              }
              ```
        - HTTP Response
            - HTTP 상태 200 OK, 결과 반환 X
