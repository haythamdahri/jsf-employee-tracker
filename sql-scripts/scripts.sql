create databse employee_tracker;
use employee_tracker;

create table department (
    id int primary key auto_increment,
    name varchar(255) not null
) ENGINE=INNODB;

create table employee(
    id int primary key auto_increment,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null,
    department_id int,
    constraint fk_department foreign key(department_id) references department(id)
) ENGINE=INNODB;

insert into department values (NULL, 'Informatique'), (NULL, 'Industriel'), (NULL, 'Commerce et Marketing');

insert into employee values (NULL, 'Haytham', 'Dahri', 'haytham.dahri@gmail.com', 1), (NULL, 'Dahri', 'Imrane', 'imrane.dahri@gmail.com', 2), (NULL, 'Baba', 'Ahmed', 'ahmed.baba@gmail.com', 3);
