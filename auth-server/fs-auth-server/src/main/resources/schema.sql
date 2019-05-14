create database fs_auth;
use fs_auth;

drop table if exists user;

create table user (
  id varchar(36) not null,
  username varchar(30) not null,
  name varchar(30) null,
  password char(60) not null,
  phone char(11) null,
  email varchar(100) null,
  created_on datetime(3) not null,
  primary key (username),
  index idx_username (username)
) engine = InnoDb, charset = utf8mb4;


-- -----------------------------------------------------
-- Table `oauth_access_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oauth_access_token` (
  `token_id` VARCHAR(256) NOT NULL,
  `token` BLOB NULL DEFAULT NULL,
  `authentication_id` VARCHAR(128) NOT NULL,
  `user_name` VARCHAR(256) NULL DEFAULT NULL,
  `client_id` VARCHAR(256) NULL DEFAULT NULL,
  `authentication` BLOB NULL DEFAULT NULL,
  `refresh_token` VARCHAR(256) NULL DEFAULT NULL,
PRIMARY KEY (`token_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `oauth_refresh_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oauth_refresh_token` (
  `token_id` VARCHAR(256) NOT NULL,
  `token` BLOB NULL DEFAULT NULL,
  `authentication` BLOB NULL DEFAULT NULL,
  primary key (token_id)
  )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Table `undo_log`
-- -----------------------------------------------------

drop table if exists undo_log;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;