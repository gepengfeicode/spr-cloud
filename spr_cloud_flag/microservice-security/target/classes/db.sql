/*创建数据库*/
create database sprsecurity;
use sprsecurity;
/*--创建用户表*/
create table sys_user(
                         id integer primary key auto_increment,
                         name varchar(50),
                         password varchar(50)
);
insert into sys_user  (name,password) values('admin','admin');