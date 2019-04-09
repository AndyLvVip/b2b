create database corporate;
use corporate;

drop table if exists corporate;

create table corporate (
  id char(32) not null primary key,
  name varchar(128),
  type tinyint not null,
  logo_file_id char(32),
  fax_num varchar(32),
  website varchar(255),
  phone varchar(20),
  address_id char(32),
  status tinyint not null,
  active tinyint(1) not null,
  last_role_id varchar(32) not null,
  created_on datetime(3) not null,
  updated_on datetime(3) not null,
  created_by varchar(32) not null,
  updated_by varchar(32) not null,
  version int not null
) engine = InnoDB, charset = utf8;

drop table if exists `user`;

create table `user` (
  id char(32) not null primary key,
  username varchar(32) not null,
  member_of char(32) not null,
  name varchar(32),
  phone char(11),
  email varchar(255),
  qq varchar(255),
  status tinyint not null,
  created_on datetime(3) not null,
  updated_on datetime(3) not null,
  created_by varchar(32) not null,
  updated_by varchar(32) not null,
  version int not null
) engine = InnoDB, charset = utf8;