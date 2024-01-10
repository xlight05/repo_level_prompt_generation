## Create and use database

CREATE DATABASE hucourses;
USE hucourses;

## Create tables

CREATE TABLE courses      (course_id smallint not null auto_increment, 
			   title varchar(40) not null, 
			   name varchar(40) not null, 
			   primary key(course_id)) 
			  type=InnoDB;

CREATE TABLE types        (type_id tinyint not null auto_increment, 
			   type varchar(40) not null, 
			   primary key(type_id)) 
			  type = InnoDB;

CREATE TABLE attrs        (attr_id int not null auto_increment, 
			   type_id tinyint not null, foreign key(type_id) references types(type_id), 
			   attr varchar(40) not null, 
			   primary key(attr_id)) 
			  type=InnoDB;

CREATE TABLE course_attrs (course_id smallint not null, foreign key(course_id) references courses(course_id), 
			   attr_id int not null, foreign key(attr_id) references attrs(attr_id)) 
			  type=InnoDB;

## Populate tables

insert into courses set title="English 69", name="Lancent Ideologies";
insert into courses set title="Government 400", name="Taiwanese Subservience";
insert into courses set title="Visual and Environmental Studies 20", name="Playground Design";

insert into types set type="Professor";
insert into types set type="Department";
insert into types set type="Core Status";

insert into attrs set type_id=1, attr="David Kane";
insert into attrs set type_id=1, attr="Emiry Huang";
insert into attrs set type_id=1, attr="Professor X";
insert into attrs set type_id=2, attr="English";
insert into attrs set type_id=2, attr="Government";
insert into attrs set type_id=2, attr="Visual and Environmental Studies";
insert into attrs set type_id=3, attr="Core";

insert into course_attrs set course_id=1, attr_id=1;
insert into course_attrs set course_id=1, attr_id=4;
insert into course_attrs set course_id=2, attr_id=2;
insert into course_attrs set course_id=2, attr_id=5;
insert into course_attrs set course_id=3, attr_id=3;
insert into course_attrs set course_id=3, attr_id=6;
insert into course_attrs set course_id=3, attr_id=7;
