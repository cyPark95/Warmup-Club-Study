# SQL

## DDL(Data Definition Language)

데이터 정의하기 위한 SQL

#### 데이터베이스 생성

```sql
CREATE DATABASE [데이터베이스 이름];
```

#### 데이터베이스 목록 조회

```sql
SHOW DATABASES;
```

#### 데이터베이스 삭제

```sql
DROP DATABASE [데이터베이스 이름];
```

#### 데이터베이스 선택

```sql
USE [데이터베이스 이름];
```

#### 테이블 생성

```sql
CREATE TABLE [테이블 이름] (
    [필드1 이름] [타입] [부가조건],
    [필드2 이름] [타입] [부가조건],
    ...
    PRIMARY KEY ([필드 이름])
);
```

- 부가 조건에 `AUTO_INCREMENT` 설정해준 필드는 데이터를 명시적으로 집어 넣지 않더라도 1부터 1씩 자동 증가하며 저장
- PRIMARY KEY: 유일한 키로 지정

>데이터 타입
>
> - 정수 타입
    >     - tinyint : 1바이트 정수
>     - int : 4바이트 정수
>     - bigint : 8바이트 정수
> - 실수 타입
    >     - double : 8바이트 실수
>     - decimal(A, B) : 소수점을 B개 가지고 있는 전체 A자릿수 실수
> - 문자열 타입
    >     - char(A) : A글자가 들어갈 수 있는 문자열
>     - varchar(A) : 최대 A글자가 들어갈 수 있는 문자열
> - 날짜, 시간 타입
    >     - date : 날짜, yyyy-MM-dd 형식
>     - time : 시간, HH:mm:ss 형식
>     - datetime : 날짜와 시간을 합친 형식, yyyy-MM-dd HH:mm:ss 형식

#### 테이블 목록 조회

```sql
SHOW TABLES;
```

#### 테이블 삭제

```sql
DROP TABLE [테이블 이름];
```

## DML(Data Manipulation Language)

데이터 조작을 위한 SQL

## 종류(CRUD)

- 데이터 삽입 (생성 - Create)
- 데이터 조회 (읽기 - Retrieve or Read)
- 데이터 수정 (업데이트 - Update)
- 데이터 삭제 (제거 - Delete)

#### 데이터 삽입

```sql
INSERT INTO [테이블 이름] (필드1이름, 필드2이름, ...) VALUES (값1, 값2, ...)
```

- `AUTO_INCREMENT`를 설정해준 필드는 자동으로 삽입된다.

#### 데이터 조회

```sql
SELECT * FROM [테이블 이름] WHERE [조건];
```

- `*`을 사용하면 모든 필드를 조회할 수 있다.
- `SELECT` 절 필드 이름을 명시하면 해당 필드만 조회할 수 있다.
- `WHERE` 절을 통해 데이터를 필터링 할 수 있다.

#### 데이터 수정

```sql
UPDATE [테이블 이름] SET 필드1이름=값1, 필드2이름=값2, ... WHERE [조건];
```

- 만약 조건이 붙지 않는다면 모든 데이터가 업데이트 되기 때문에 주의해야 한다.

#### 데이터 삭제

```sql
DELETE FROM [테이블 이름] WHERE [조건];
```

- 데이터 수정과 마찬가지로, 조건이 붙지 않는다면 모든 데이터가 삭제되기 때문에 주의해야 한다.
