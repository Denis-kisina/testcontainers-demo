DROP TABLE IF EXISTS customers;
CREATE TABLE customers
(
    id         int not null auto_increment primary key,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);
insert into customers (first_name, last_name)
values ('Denis', 'Kisina');
insert into customers (first_name, last_name)
values ('Nish', 'Naima');

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         VARCHAR PRIMARY KEy,
    name VARCHAR(255) NOT NULL,
    email  VARCHAR(255) UNIQUE NOT NULL
);