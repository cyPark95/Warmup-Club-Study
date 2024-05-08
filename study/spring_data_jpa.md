# Spring Data JPA

복잡한 JPA 코드를 쉽게 사용할 수 있도록 도와주는 스프링 프레임워크의 모듈

- 스프링은 `JpaRepository<Entity, ID>`를 구현 받는 Repository에 대해 자동으로 `SimpleJpaRepository` 기능을 사용할 수 있게도 해 준다.
    - save: 주어지는 객체를 저장하거나 업데이트 시켜준다.
    - findAll: 주어지는 객체가 매핑된 테이블의 모든 데이터를 가져온다.
    - findById: id를 기준으로 특정한 1개의 데이터를 가져온다.
    - delete: 주어진 객체를 삭제한다.
- 함수 이름만 작성하면, 알아서 SQL을 만들어 준다.

## 구절

- By 앞에 사용되는 구절
    - find
        - 반환 타입은 객체가 될 수도 있고, `Optional<타입>`이 될 수도 있다.
    - findAll
        - 쿼리의 결과물이 N개인 경우 사용한다.
        - 반환 타입 `List<타입>`
    - exist
        - 쿼리 결과가 존재하는지 확인한다.
        - 반환 타입 `boolean`
    - count
        - SQL의 결과 개수를 센다.
        - 반환 타입 `long`

- By 뒤에 사용되는 구절
    - 필드 이름이 들어간다.
    - 필드들은 `And` 또는 `Or`로 조합될 수 있다.
    - 필드 이름만 작성하면 동등 조건(`=`)으로 쿼리가 생성된다.
    - 그 외에 다양한 조건을 활용할 수 있다.
        - GreaterThan : 초과
        - GreaterThanEqual : 이상
        - LessThan : 미만
        - LessThanEqual : 이하
        - Between : 사이에
        - StartsWith : ~로 시작하는
        - EndsWith : ~로 끝나는
