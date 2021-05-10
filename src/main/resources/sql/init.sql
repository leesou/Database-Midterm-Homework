DROP DATABASE IF EXISTS `midtermproj`;
CREATE DATABASE `midtermproj` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `midtermproj`;

DROP TABLE IF EXISTS `test_table`;
CREATE TABLE `test_table`
(
    `id`            INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增长，用户id',
    `user_name`     VARCHAR(100)                NOT NULL COMMENT '用户名',
    `password`      VARCHAR(100)                NOT NULL COMMENT '用户密码',
    PRIMARY KEY pk_id (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;