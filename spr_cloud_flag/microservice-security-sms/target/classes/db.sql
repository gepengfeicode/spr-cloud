/*创建数据库*/
create database sprsecurity;
use sprsecurity;
/*--创建用户表*/
create table sys_user(
     id integer primary key auto_increment,
     name varchar(500),
     password varchar(500)
);
insert into sys_user  (name,password) values('admin','admin');
insert into sys_user  (name,password) values('user','user');
insert into sys_user  (name,password) values('root','$2a$10$FEfsokY8tKz/L5UHKdz2VeaalzTr3J8fV91aHVHNVYpq2U5Hol3NW');
/*创建角色表*/
create table sys_role(
     id integer primary key auto_increment,
     roleName varchar(50)
);
insert into sys_role (roleName) values ('管理员');
insert into sys_role (roleName) values ('用户');
/*创建用户角色中间表*/
create table sys_user_role(
  id integer primary key auto_increment,
  uid integer,
  rid integer
);
insert into sys_user_role(uid,rid) values('1','1');
insert into sys_user_role(uid,rid) values('2','2');
insert into sys_user_role(uid,rid) values('3','1');
/*创建权限表*/
create table sys_permission(
   id integer primary key auto_increment,
   permissionName varchar(50)
);
/*插入权限数据  注意 value  值 必须是ROLE_****开头的才可以*/
insert into sys_permission(permissionName) values ('ROLE_permission_add');
insert into sys_permission(permissionName) values ('ROLE_permission_query');
insert into sys_permission(permissionName) values ('ROLE_permission_del');
insert into sys_permission(permissionName) values ('ROLE_permission_edit');
insert into sys_permission(permissionName) values ('ROLE_permission_getIp');

/*创建角色与权限关联表*/
create table sys_role_permission(
        id integer primary key auto_increment,
        rid integer,
        pid integer
);

insert into sys_role_permission(rid, pid) values('1','1');
insert into sys_role_permission(rid, pid) values('1','2');
insert into sys_role_permission(rid, pid) values('1','3');
insert into sys_role_permission(rid, pid) values('1','4');
insert into sys_role_permission(rid, pid) values('2','2');


/*单点登录新增表*/
CREATE TABLE persistent_logins (
     `username` varchar(64) NOT NULL,
     `series` varchar(64) NOT NULL,
     `token` varchar(64) NOT NULL,
     `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`series`)
);