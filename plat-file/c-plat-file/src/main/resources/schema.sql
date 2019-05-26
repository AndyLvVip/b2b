create database plat_file;

use plat_file;

drop table if exists file_set_info;
create table file_set_info (
                               id char(36) not null primary key,
                               file_src_remark varchar(20) not null,
                               created_by varchar(30) not null,
                               created_on datetime(3) not null,
                               updated_by varchar(30) not null,
                               updated_on datetime(3) not null,
                               version int not null
) engine = InnoDB, charset = utf8mb4;

drop table if exists file_item_info;
create table file_item_info(
                               id char(36) not null primary key,
                               file_set_info_id char(36) not null,
                               file_path varchar(255) not null,
                               file_name varchar(255) not null,
                               size bigint not null,
                               sequence int not null,
                               created_by varchar(30) not null,
                               created_on datetime(3) not null,
                               updated_by varchar(30) not null,
                               updated_on datetime(3) not null,
                               version int not null,
                               index idx_file_set_info_id (file_set_info_id)
) engine = InnoDB, charset = utf8mb4;

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