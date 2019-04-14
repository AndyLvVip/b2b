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
