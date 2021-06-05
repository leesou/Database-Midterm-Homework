DROP DATABASE IF EXISTS `midtermproj`;
CREATE DATABASE `midtermproj` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `midtermproj`;


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
    `department_name`   VARCHAR(100) UNIQUE         NOT NULL COMMENT '部门名称',
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
    `employee_wid`      INT UNSIGNED                 NOT NULL COMMENT '雇员的员工id',
    `employer_wid`      INT UNSIGNED                 NOT NULL COMMENT '经理的员工id',
    PRIMARY KEY pk_eewid(`employee_wid`),
    FOREIGN KEY fk_erwid(`employer_wid`) REFERENCES `worker`(`wid`),
    FOREIGN KEY fk_eewid(`employee_wid`) REFERENCES `worker`(`wid`)
) ENGINE = InnoDB
  DEFAULT CHARSET =utf8mb4
  COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS `manage`;
CREATE TABLE `manage`
(
    `did`               INT UNSIGNED               NOT NULL COMMENT '部门id',
    `manager_wid`       INT UNSIGNED   UNIQUE      NOT NULL COMMENT '部门经理的员工id',
    PRIMARY KEY pk_did(`did`),
    FOREIGN KEY fk_did(`did`) REFERENCES `department`(`did`),
    FOREIGN KEY fk_wid(`manager_wid`) REFERENCES `worker`(`wid`)
) ENGINE = InnoDB
  DEFAULT CHARSET =utf8mb4
  COLLATE = utf8mb4_general_ci;

-- 插入一个影子员工和影子部门，用于接管删除部门时未交接的维修单
insert into department
value(1, 'shade_dep');
insert into worker
value (1, 'shade_worker', 0, 1, '', '');

-- 按员工姓名建立worker表的索引
create index worker_name_index on worker(worker_name);

-- 按商品售出日期建立product表的索引
create index product_date_index on product(sell_time);

-- 添加worker的同时也要添加employ，用触发器实现
-- 即使添加的是一个manager，因为是先insert worker，再insert manage，所以不会出问题
CREATE TRIGGER `worker_insert_trigger` AFTER INSERT
    ON `worker` FOR EACH ROW
        INSERT INTO `employ`
        SELECT `worker`.`wid`, `manage`.`manager_wid` FROM `worker`, `manage`
        WHERE `worker`.`wid` = NEW.wid AND `worker`.`did` = `manage`.`did`;

-- 删除worker的同时也要删除employ，用触发器实现
-- 由于manager在employ内不会出现到employee_wid内，所以删manager时不会产生影响
CREATE TRIGGER `worker_delete_trigger` BEFORE DELETE
    ON `worker` FOR EACH ROW
        DELETE FROM `employ`
        WHERE `employ`.`employee_wid` = OLD.`wid`;
