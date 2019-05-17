create database platform_sys;

use platform_sys;

drop table if exists menu;

create table menu (
  id bigint not null primary key ,
  name varchar(255),
  url varchar(255),
  parent_id bigint,
  sequence decimal(10, 6) not null,
  icon varchar(255),
  created_on datetime(3) not null,
  created_by varchar(30) not null,
  updated_on datetime(3) not null,
  updated_by varchar(30) not null,
  version int not null
) engine = InnoDB, charset = utf8mb4;

insert into menu(id, name, url, parent_id, sequence, icon, created_on, created_by, updated_on, updated_by, version)
values (1, '会员中心', '#', null, 1, null, now(), 'admin', now(), 'admin', 1),
       (2, '基本信息', 'center/base-info', 1, 1, null, now(), 'admin', now(), 'admin', 1),
       (3, '账户安全', 'center/security', 1, 2, null, now(), 'admin', now(), 'admin', 1),
       (4, '企业认证信息', 'center/cert-info-corporate', 1, 3, null, now(), 'admin', now(), 'admin', 1),
       (5, '个人认证信息', 'center/cert-info-personal', 1, 4, null, now(), 'admin', now(), 'admin', 1),
       (6, '地址管理', 'center/address', 1, 5, null, now(), 'admin', now(), 'admin', 1);

drop table if exists role;
create table role (
  id char(36) not null primary key ,
  corporate_id char(36) not null,
  name varchar(50) not null,
  created_on datetime(3) not null,
  created_by varchar(30) not null,
  updated_on datetime(3) not null,
  updated_by varchar(30) not null,
  version int not null
) engine = InnoDB, charset = utf8mb4;

insert into role(id, corporate_id, name, created_on, created_by, updated_on, updated_by, version)
values('register', 'template', '注册', now(), 'admin', now(), 'admin', 1);


drop table if exists permission;
create table permission (
  id char(36) not null primary key ,
  role_id char(36) not null,
  menu_id bigint not null,
  permission bigint not null,
  created_on datetime(3) not null,
  created_by varchar(30) not null,
  updated_on datetime(3) not null,
  updated_by varchar(30) not null,
  version int not null
) engine = InnoDB, charset = utf8mb4;

insert into permission(id, role_id, menu_id, permission, created_on, created_by, updated_on, updated_by, version)
values(uuid(), 'register', 2, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'register', 3, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'register', 4, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'register', 5, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'register', 6, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1)
      ;

drop table if exists user_role;
create table user_role (
    id char(36) not null primary key ,
    user_id char(36) not null ,
    role_id char(36) not null,
    created_on datetime(3) not null,
    created_by varchar(30) not null,
    updated_on datetime(3) not null,
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