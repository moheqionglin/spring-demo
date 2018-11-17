create database if not exists shardingDemo;

create table if not exists users_2018_8(
  id bigint,
  name varchar(64),
  createdTime timestamp ,
  modifiedTime TIMESTAMP,
  primary key (id)
);

create table if not exists  users_2018_9(
  id bigint,
  name varchar(64),
  createdTime timestamp ,
  modifiedTime TIMESTAMP,
  primary key (id)
);

create table if not exists users_2018_10(
  id bigint,
  name varchar(64),
  createdTime timestamp ,
  modifiedTime TIMESTAMP,
  primary key (id)
);
insert into users_2018_8(id , name, createdTime, modifiedTime)values (1, 'name-1', 20180810122432, 20180810122432);
insert into users_2018_9(id , name, createdTime, modifiedTime)values (1, 'name-2', 20180910122432, 20180910122432);
insert into users_2018_10(id , name, createdTime, modifiedTime)values (1, 'name-3', 20181010122432, 20181010122432);