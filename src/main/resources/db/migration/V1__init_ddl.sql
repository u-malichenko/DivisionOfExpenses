create table if not exists event_roles
(
    event_id bigint not null,
    roles_id bigint not null
        constraint uk_qydu3p9hsfvlkaxq1tvftf4b0
            unique
);

create table if not exists users_expense
(
    expense_id bigint not null,
    user_id bigint not null
        constraint uk_mf679n92xfppdm65roapo95je
            unique
);

create table if not exists roles
(
    id bigserial not null
        constraint roles_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    name varchar(255)
);

create table if not exists users
(
    id bigserial not null
        constraint users_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    username varchar(255)
);

create table if not exists event
(
    id bigserial not null
        constraint event_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    description varchar(255),
    event_date_time timestamp default current_timestamp,
    name varchar(255),
    total_event_sum numeric(19,2),
    user_id bigint
        constraint fk31rxexkqqbeymnpw4d3bf9vsy
            references users,
    shopping_list_id bigint
);

create table event_member
(
    id bigserial not null
        constraint event_member_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    saldo numeric(19, 2) default 0.00,
    event_id    bigint
        constraint fk8e54pd1io5a7gbp6x1io4vkft
            references event,
    user_id     bigint
        constraint fkoobns1hxage1y1gcnf9ol8y2m
            references users
);

create table does.events_members
(
    event_id        bigint not null
        constraint fk265xqmjoy0dc03sbni7n60oi2
            references event,
    event_member_id bigint not null
        constraint fks2yng8cie6pnvvhunn4cji4v3
            references event_member
);

create table if not exists events_users
(
    user_id bigint not null
        constraint fk4qi1ew1ii81ipjy5p3oy5ypeq
            references users,
    event_id bigint not null
        constraint fkma083iclvtw8x773pvlf2k6mt
            references event
);

create table if not exists expense
(
    id bigserial not null
        constraint expense_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    bill_photo varchar(255),
    comment varchar(255),
    expense_date timestamp default current_timestamp,
    total_expense_sum numeric(19,2) default 0.00,
    buyer_id bigint
        constraint fk2wm2hsnaeugfskywd72bsfx2c
            references users,
    event_id bigint
        constraint fkk3gx26ki6g8jinamvk52opp8f
            references event
);

create table if not exists direct_payer
(
    id bigserial not null
        constraint direct_payer_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    summa numeric(19,2) default 0.00,
    expense_id bigint
        constraint fkn7ogtverci9obeokrp4d8d1jg
            references expense,
    user_id bigint
        constraint fk4nmrd19f3mct7urj75tbujhxq
            references users
);

create table if not exists partitial_payer
(
    id bigserial not null
        constraint partitial_payer_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    coefficient numeric(19,2) default 1.00,
    expense_id bigint
        constraint fkbry2b6ee2rkvhi01rlq8r6x0j
            references expense,
    user_id bigint
        constraint fksnqrvo7uxs5gv50w3qfegu02b
            references users
);

create table if not exists shopping_list
(
    id bigserial not null
        constraint shopping_list_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    comment varchar(255),
    enabled_flag boolean,
    event_id bigint
        constraint fkss1lu0q3tgd9rm59satrp7bjo
            references event
);

alter table event
    add constraint fkcaeoetktnp0k5au437rysqaef
        foreign key (shopping_list_id) references shopping_list;

create table if not exists shopping_list_item
(
    id bigserial not null
        constraint shopping_list_item_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    name varchar(255),
    quantity varchar(255),
    shoppinglistitem_id bigint
        constraint fkewo6pgiv4ap559xv2fkl4ejwt
            references shopping_list
);

create table if not exists users_roles
(
    role_id bigint not null
        constraint fk7bvb4gjgqcgxvgddx0pgefkei
            references roles,
    user_id bigint not null
        constraint fk7nsfg6lgq1vs83244qkrapjnx
            references users
);

insert into users (password, username, first_name, last_name)
values ('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'bob', 'bob', 'bombaster'),
       ('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'john', 'john', 'johnson'),
       ('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'silver', 'silver', 'star'),
       ('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'samurai', 'samurai', 'silver'),
       ('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'vi', 'vi', 'unknown'),
       ('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'panam', 'panam', 'petrova'),
       ('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'jack', 'jack', 'ivanov'),
       ('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'soul', 'soul', 'sidorov'),
       ('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'mitch', 'mitch', 'kuznezov'),
       ('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'goro', 'goro', 'takimura');

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users_roles (role_id, user_id)
values (1, 1),
       (2, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10);

insert into event (description, name, user_id, total_event_sum)
values ('first event description', 'first event', 1, 2030.00),
       ('second event description', 'second event', 2, 9330.30),
       ('third event description', 'third event', 3, 6204.00),
       ('fourth event description', 'fourth event', 4, 11074.00),
       ('fives event description', 'fives event', 5, 1004.00),
       ('sixs event description', 'sixs event', 6, 1004.00),
       ('sevens event description', 'sevens event', 7, 1004.00),
       ('eighths event description', 'eights event', 8, 1004.00),
       ('nines event description', 'nines event', 9, 4000.00);

insert into events_users (user_id, event_id)
values (1, 1),
       (2, 1),
       (3, 1),
       (2, 2),
       (3, 2),
       (5, 2),
       (6, 2),
       (3, 3),
       (8, 3),
       (9, 3),
       (2, 4),
       (3, 4),
       (4, 4),
       (5, 4),
       (6, 4),
       (7, 4),
       (8, 4),
       (9, 4),
       (5, 5),
       (9, 5),
       (6, 6),
       (1, 7),
       (4, 7),
       (7, 7),
       (5, 8),
       (8, 8),
       (1, 9),
       (3, 9),
       (7, 9),
       (8, 9),
       (9, 9);

insert into expense (comment, total_expense_sum, buyer_id, event_id)
values ('expense 1', 1000.00, 1, 1),
       ('expense 2', 1030.00, 2, 1),
       ('expense 3', 1030.20, 2, 2),
       ('expense 4', 1130.00, 3, 2),
       ('expense 5', 1034.10, 5, 2),
       ('expense 6', 3034.00, 5, 2),
       ('expense 7', 1034.00, 6, 2),
       ('expense 8', 1034.00, 6, 2),
       ('expense 9', 1034.00, 6, 2),
       ('expense 10', 1034.00, 3, 3),
       ('expense 11', 1034.00, 3, 3),
       ('expense 12', 1034.00, 3, 3),
       ('expense 13', 1034.00, 8, 3),
       ('expense 14', 1034.00, 9, 3),
       ('expense 15', 1034.00, 9, 3),
       ('expense 16', 1034.00, 2, 4),
       ('expense 17', 1004.00, 3, 4),
       ('expense 18', 1004.00, 4, 4),
       ('expense 19', 1004.00, 5, 4),
       ('expense 20', 1004.00, 5, 4),
       ('expense 21', 1004.00, 6, 4),
       ('expense 22', 1004.00, 7, 4),
       ('expense 23', 1004.00, 8, 4),
       ('expense 24', 1004.00, 9, 4),
       ('expense 25', 1004.00, 9, 4),
       ('expense 26', 1004.00, 9, 4),
       ('expense 27', 1004.00, 5, 5),
       ('expense 28', 1004.00, 6, 6),
       ('expense 29', 1004.00, 7, 7),
       ('expense 30', 1004.00, 8, 8),
       ('expense 31', 1000.00, 9, 9),
       ('expense 32', 1000.00, 8, 9),
       ('expense 33', 1000.00, 3, 9),
       ('expense 34', 1000.00, 8, 9);

insert into direct_payer (expense_id, user_id, summa)
values (1, 1, 100.00),
       (1, 2, 300.00),
       (2, 5, 50.00),
       (2, 6, 110.00),
       (4, 2, 100.00),
       (4, 3, 100.00),
       (4, 4, 100.00),
       (4, 9, 130.00),
       (5, 9, 400.00),
       (7, 1, 140.00),
       (7, 4, 20.00),
       (9, 3, 160.00),
       (9, 7, 200.00),
       (9, 8, 340.00);

insert into partitial_payer (expense_id, user_id)
values (1, 3),
       (2, 2),
       (2, 3),
       (3, 3),
       (3, 8),
       (3, 9),
       (4, 5),
       (4, 6),
       (4, 7),
       (4, 8),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 5),
       (8, 8),
       (9, 1),
       (9, 9);