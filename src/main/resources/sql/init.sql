DROP DATABASE IF EXISTS `midtermproj`;
CREATE DATABASE `midtermproj` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `midtermproj`;

DROP TABLE IF EXISTS `test_table`;
CREATE TABLE `test_table`
(
    `id`            INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增长id',
    `user_name`     VARCHAR(100)                NOT NULL COMMENT '用户名',
    `password`      VARCHAR(100)                NOT NULL COMMENT '用户密码',
    PRIMARY KEY pk_id (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;








DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `uid`           INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增长的用户id',
    `user_name`     VARCHAR(100)                NOT NULL COMMENT '用户姓名',
    `address`       VARCHAR(100)                NOT NULL COMMENT '用户住址',
    `phone_number`  VARCHAR(100)                NOT NULL COMMENT '用户联系电话',
    `email`         VARCHAR(100)                NOT NULL COMMENT '用户联系邮箱',
    PRIMARY KEY pk_uid (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET =utf8mb4
  COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`
(
    `did`               INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增长的部门id',
    `department_name`   VARCHAR(100)                NOT NULL COMMENT '部门名称',
    PRIMARY KEY pk_did(`did`)
) ENGINE = InnoDB
  DEFAULT CHARSET =utf8mb4
  COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS `worker`;
CREATE TABLE `worker`
(
    `wid`           INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增长的售后服务人员id',
    `worker_name`   VARCHAR(100)                NOT NULL COMMENT '员工姓名',
    `salary`        DOUBLE                      NOT NULL COMMENT '员工工资',
    `did`           INT UNSIGNED                NOT NULL COMMENT '所属部门',
    `phone_number`  VARCHAR(100)                NOT NULL COMMENT '员工电话',
    `email`         VARCHAR(100)                NOT NULL COMMENT '员工邮箱',
    PRIMARY KEY pk_wid (`wid`),
    FOREIGN KEY fk_did(`did`) REFERENCES `department`(`did`)
) ENGINE = InnoDB
  DEFAULT CHARSET =utf8mb4
  COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `pid`           INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增长的产品编号',
    `product_name`  VARCHAR(100)                NOT NULL COMMENT '产品名称',
    `price`         DOUBLE                      NOT NULL COMMENT '产品价格',
    `sell_time`     DATE                        NOT NULL COMMENT '出售时间',
    `duration`    INT UNSIGNED                NOT NULL COMMENT '保修期限长度',
    `uid`           INT UNSIGNED                NOT NULL COMMENT '购买者编号',
    PRIMARY KEY pk_pid(`pid`),
    FOREIGN KEY fk_uid(`uid`) REFERENCES `user`(`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET =utf8mb4
  COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS `after_sale_service`;
CREATE TABLE `after_sale_service`
(
    `sid`           INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增长的售后服务单编号',
    `service_type`  VARCHAR(100)                NOT NULL COMMENT '售后服务类型',
    `description`   VARCHAR(500)                NOT NULL COMMENT '售后服务描述',
    `state`         VARCHAR(100)                NOT NULL COMMENT '服务状态',
    `wid`           INT UNSIGNED                NOT NULL COMMENT '负责售后的员工id',
    `pid`           INT UNSIGNED                NOT NULL COMMENT '待售后的产品id',
    PRIMARY KEY pk_sid(`sid`),
    FOREIGN KEY fk_wid(`wid`) REFERENCES `worker`(`wid`),
    FOREIGN KEY fk_pid(`pid`) REFERENCES `product`(`pid`)
) ENGINE = InnoDB
  DEFAULT CHARSET =utf8mb4
  COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS `employ`;
CREATE TABLE `employ`
(
    `employer_wid`      INT UNSIGNED                 NOT NULL COMMENT '经理的员工id',
    `employee_wid`      INT UNSIGNED                 NOT NULL COMMENT '雇员的员工id',
    PRIMARY KEY pk_wids(`employer_wid`, `employee_wid`),
    FOREIGN KEY fk_erwid(`employer_wid`) REFERENCES `worker`(`wid`),
    FOREIGN KEY fk_eewid(`employee_wid`) REFERENCES `worker`(`wid`)
) ENGINE = InnoDB
  DEFAULT CHARSET =utf8mb4
  COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS `manage`;
CREATE TABLE `manage`
(
    `did`               INT UNSIGNED               NOT NULL COMMENT '部门id',
    `wid`               INT UNSIGNED               NOT NULL COMMENT '部门经理的员工id',
    PRIMARY KEY pk_did(`did`),
    FOREIGN KEY fk_did(`did`) REFERENCES `department`(`did`),
    FOREIGN KEY fk_wid(`wid`) REFERENCES `worker`(`wid`)
) ENGINE = InnoDB
  DEFAULT CHARSET =utf8mb4
  COLLATE = utf8mb4_general_ci;
