create database ops_user;

use ops_user;

drop table if exists `user`;

create table `user` (
  id char(36) not null primary key,
  username varchar(30) not null,
  name varchar(30),
  phone char(11),
  email varchar(255),
  qq varchar(255),
  enabled tinyint(1) not null,
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