drop table if exists user;

create table user (
  id char(36) not null,
  username varchar(30) not null,
  password char(60) not null,
  phone char(11) null,
  email varchar(255) null,
  created_on datetime(3) not null,
  primary key (id)
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