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
    `id`         BIGINT AUTO_INCREMENT COMMENT '식별 값(PK)',
    `name`       VARCHAR(50) NOT NULL COMMENT '이름',
    `role`       VARCHAR(20) NOT NULL COMMENT '역할',
    `join_date`  DATE        NOT NULL COMMENT '회사에 들어온 날짜',
    `birthday`   DATE        NOT NULL COMMENT '생일',
    `created_at` DATETIME    NOT NULL COMMENT '생성 날짜',
    `updated_at` DATETIME    NOT NULL COMMENT '최종 수정 날짜',

    PRIMARY KEY (`id`)
)
