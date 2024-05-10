# 데이터베이스 생성
CREATE DATABASE library;
# 데이터베이스 목록 조회
SHOW DATABASES;

# 데이터베이스 선택
USE library;

# 테이블 생성
CREATE TABLE user
(
    # AUTO_INCREMENT 설정해준 필드는 데이터를 명시적으로 집어 넣지 않더라도 1부터 1씩 증가하며 자동 저장
    id   BIGINT AUTO_INCREMENT,
    name VARCHAR(20),
    age  INT,

    # id 필드를 유일한 키로 지정
    PRIMARY KEY (id)
);

SHOW TABLES;

CREATE TABLE book
(
    id   BIGINT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE user_loan_history
(
    id        BIGINT AUTO_INCREMENT,
    user_id   BIGINT,
    book_name VARCHAR(255),
    is_return BOOLEAN,

    PRIMARY KEY (id)
)
