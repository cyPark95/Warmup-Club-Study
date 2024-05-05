DROP TABLE IF EXISTS `fruit`;

CREATE TABLE `fruit`
(
    id              BIGINT AUTO_INCREMENT,
    name            VARCHAR(20) NOT NULL,
    warehousingDate DATE        NOT NULL,
    price           BIGINT      NOT NULL,
    is_sold         BOOLEAN DEFAULT FALSE,

    PRIMARY KEY (id)
);
