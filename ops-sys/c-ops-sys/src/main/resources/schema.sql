create database ops_sys;

use ops_sys;

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
values (1, '交易', '#', null, 1, null, now(), 'admin', now(), 'admin', 1),
       (2, '订单', '/bs-order/order', 1, 1, null, now(), 'admin', now(), 'admin', 1),
       (3, '发货单', '/bs-dispatch/dispatch', 1, 2, null, now(), 'admin', now(), 'admin', 1),
       (4, '产品', '#', null, 2, null, now(), 'admin', now(), 'admin', 1),
       (5, '产品上架管理', '/bs-product/product-management', 4, 1, null, now(), 'admin', now(), 'admin', 1),
       (6, '产品分类', '/bs-product/category', 4, 2, null, now(), 'admin', now(), 'admin', 1),
       (7, '产品属性', '/bs-product/property', 4, 3, null, now(), 'admin', now(), 'admin', 1),
       (8, '资金', '#', null, 3, null, now(), 'admin', now(), 'admin', 1),
       (9, '付款单', '/bs-finance/pay-apply', 8, 1, null, now(), 'admin', now(), 'admin', 1),
       (10, '会员', '#', null, 4, null, now(), 'admin', now(), 'admin', 1),
       (11, '会员管理', '/bs-plat-corporate/corporate', 10, 1, null, now(), 'admin', now(), 'admin', 1),
       (12, '企业成员', '/bs-plat-corporate/member', 10, 2, null, now(), 'admin', now(), 'admin', 1),
       (13, '会员权限', '/bs-plat-corporate/permission', 10, 3, null, now(), 'admin', now(), 'admin', 1),
       (14, '认证审核', '/bs-plat-corporate/audit', 10, 4, null, now(), 'admin', now(), 'admin', 1),
       (15, '会员主营信息', '/bs-plat-corporate/main-business', 10, 5, null, now(), 'admin', now(), 'admin', 1),
       (16, '内容', '#', null, 5, null, now(), 'admin', now(), 'admin', 1),
       (17, '资讯发布', '/bs-plat-content/news-publish', 16, 1, null, now(), 'admin', now(), 'admin', 1),
       (18, '资讯类型', '/bs-plat-content/news-type', 16, 2, null, now(), 'admin', now(), 'admin', 1),
       (19, '广告管理', '/bs-plat-content/advert', 16, 3, null, now(), 'admin', now(), 'admin', 1),
       (20, '橱窗管理', '/bs-plat-content/show-window', 16, 4, null, now(), 'admin', now(), 'admin', 1),
       (21, '搜索关键字', '/bs-plat-content/search-keyword', 16, 5, null, now(), 'admin', now(), 'admin', 1),
       (22, '会员列表排序', '/bs-plat-corporate/corporate-sequence', 16, 6, null, now(), 'admin', now(), 'admin', 1),
       (23, '网页动态发布', '/bs-plat-content/web-page-publish', 16, 7, null, now(), 'admin', now(), 'admin', 1),
       (24, '活动类型', '/bs-plat-content/activity-type', 16, 8, null, now(), 'admin', now(), 'admin', 1),
       (25, '活动报名', '/bs-plat-content/activity-apply', 16, 9, null, now(), 'admin', now(), 'admin', 1),
       (26, '省市区', '/bs-plat-content/region', 16, 10, null, now(), 'admin', now(), 'admin', 1),
       (27, '客服', '#', null, 6, null, now(), 'admin', now(), 'admin', 1),
       (28, '常见问题', '/bs-plat-service/faq', 27, 1, null, now(), 'admin', now(), 'admin', 1),
       (29, '消息查询', '/bs-plat-service/message', 27, 2, null, now(), 'admin', now(), 'admin', 1),
       (30, '会员投诉', '/bs-plat-service/complain', 27, 3, null, now(), 'admin', now(), 'admin', 1),
       (31, '会员建议', '/bs-plat-service/suggestion', 27, 4, null, now(), 'admin', now(), 'admin', 1),
       (32, '客服', '#', null, 7, null, now(), 'admin', now(), 'admin', 1),
       (33, '运营账号', '/bs-ops-sys/user', 32, 1, null, now(), 'admin', now(), 'admin', 1),
       (34, '运营角色', '/bs-ops-sys/role', 32, 2, null, now(), 'admin', now(), 'admin', 1),
       (35, '数据字典', '/bs-ops-sys/dictionary', 32, 3, null, now(), 'admin', now(), 'admin', 1),
       (36, '日记', '/bs-ops-sys/log', 32, 4, null, now(), 'admin', now(), 'admin', 1)
;



drop table if exists role;
create table role (
                      id char(36) not null primary key ,
                      name varchar(50) not null,
                      remark varchar(255),
                      created_on datetime(3) not null,
                      created_by varchar(30) not null,
                      updated_on datetime(3) not null,
                      updated_by varchar(30) not null,
                      version int not null
) engine = InnoDB, charset = utf8mb4;

insert into role(id, name, remark, created_on, created_by, updated_on, updated_by, version)
values('admin', '管理员', '拥有最高权限的角色', now(), 'admin', now(), 'admin', 1);


drop table if exists permission;
create table permission (
                            id char(36) not null primary key ,
                            role_id char(36) not null,
                            menu_id bigint not null,
                            permission int not null,
                            created_on datetime(3) not null,
                            created_by varchar(30) not null,
                            updated_on datetime(3) not null,
                            updated_by varchar(30) not null,
                            version int not null
) engine = InnoDB, charset = utf8mb4;

insert into permission(id, role_id, menu_id, permission, created_on, created_by, updated_on, updated_by, version)
values(uuid(), 'admin', 2, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 3, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 5, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 6, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 7, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 9, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 11, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 12, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 13, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 14, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 15, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 17, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 18, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 19, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 20, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 21, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 22, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 23, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 24, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 25, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 26, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 28, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 29, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 30, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 31, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 33, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 34, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 35, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1),
      (uuid(), 'admin', 36, 1 | (1 << 1) | (1 << 2) | (1 << 3), now(), 'admin', now(), 'admin', 1)
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


drop table if exists permission_unit;
create table permission_unit(
                                id char(36) not null primary key ,
                                menu_id bigint not null,
                                unit int not null,
                                label varchar(20) not null,
                                created_on datetime(3) not null,
                                created_by varchar(30) not null,
                                updated_on datetime(3) not null,
                                updated_by varchar(30) not null,
                                version int not null
) engine = InnoDB, charset = utf8mb4;

insert into permission_unit (id, menu_id, unit, label, created_on, created_by, updated_on, updated_by, version)
values
(uuid(), 2, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 3, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 5, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 6, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 7, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 9, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 11, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 12, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 13, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 14, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 15, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 17, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 18, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 19, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 20, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 21, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 22, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 23, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 24, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 25, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 26, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 28, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 29, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 30, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 31, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 33, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 34, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 35, 1, '查看', now(), 'admin', now(), 'admin', 1),

(uuid(), 36, 1, '查看', now(), 'admin', now(), 'admin', 1)

;

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