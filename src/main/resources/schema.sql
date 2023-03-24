drop table if exists customers;

create table customers (
    id int primary key generated always as identity,
    player_name varchar(255) not null,
    player_uuid varchar(255) not null,
    package_id varchar(255) not null
);