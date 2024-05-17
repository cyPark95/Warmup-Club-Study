CREATE TABLE `team`
(
    `id`         BIGINT AUTO_INCREMENT COMMENT '식별 값(PK)',
    `name`       VARCHAR(50) NOT NULL COMMENT '이름',
    `created_at` DATETIME    NOT NULL COMMENT '생성 날짜',
    `updated_at` DATETIME    NOT NULL COMMENT '최종 수정 날짜',

    PRIMARY KEY (`id`)
);

CREATE TABLE `employee`
(
    `id`              BIGINT AUTO_INCREMENT COMMENT '식별 값(PK)',
    `team_id`         BIGINT      NOT NULL COMMENT '팀 식별 값(FK)',
    `name`            VARCHAR(50) NOT NULL COMMENT '이름',
    `role`            VARCHAR(20) NOT NULL COMMENT '역할',
    `work_start_date` DATE        NOT NULL COMMENT '회사에 들어온 날짜',
    `birthday`        DATE        NOT NULL COMMENT '생일',
    `created_at`      DATETIME    NOT NULL COMMENT '생성 날짜',
    `updated_at`      DATETIME    NOT NULL COMMENT '최종 수정 날짜',

    PRIMARY KEY (`id`),
    FOREIGN KEY (`team_id`) REFERENCES team (id)
);

CREATE TABLE `attendance`
(
    `id`          BIGINT AUTO_INCREMENT COMMENT '식별 값(PK)',
    `employee_id` BIGINT   NOT NULL COMMENT '직원 식별 값(FK)',
    `clock_in`    DATETIME NOT NULL COMMENT '출근 시간',
    `clock_out`   DATETIME COMMENT '퇴근 시간',
    `created_at`  DATETIME NOT NULL COMMENT '생성 날짜',
    `updated_at`  DATETIME NOT NULL COMMENT '최종 수정 날짜',

    PRIMARY KEY (`id`)
)
