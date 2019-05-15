create database corporate;
use corporate;

drop table if exists corporate;

create table corporate (
  id char(36) not null primary key,
  name varchar(128),
  type tinyint not null,
  logo_file_id char(36),
  fax_num varchar(32),
  website varchar(255),
  phone varchar(20),
  address_id char(36),
  status tinyint not null,
  active tinyint(1) not null,
  last_role_id varchar(36) not null,
  created_on datetime(3) not null,
  updated_on datetime(3) not null,
  created_by varchar(30) not null,
  updated_by varchar(30) not null,
  version int not null
) engine = InnoDB, charset = utf8mb4;

drop table if exists `user`;

create table `user` (
  id char(36) not null primary key,
  username varchar(30) not null,
  member_of char(36) not null,
  name varchar(30),
  phone char(11),
  email varchar(255),
  qq varchar(255),
  status tinyint not null,
  created_on datetime(3) not null,
  updated_on datetime(3) not null,
  created_by varchar(30) not null,
  updated_by varchar(30) not null,
  version int not null
) engine = InnoDB, charset = utf8mb4;


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