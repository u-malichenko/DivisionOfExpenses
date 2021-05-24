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
    event_date_time timestamp,
    name varchar(255),
    total_event_sum numeric(19,2),
    user_id bigint
        constraint fk31rxexkqqbeymnpw4d3bf9vsy
            references users,
    shopping_list_id bigint
);

create table if not exists events_users
(
    user_id  bigint not null
        constraint fk4qi1ew1ii81ipjy5p3oy5ypeq
            references users,
    event_id bigint not null
        constraint fkma083iclvtw8x773pvlf2k6mt
            references event
);

create table if not exists invited_events_users
(
    user_id  bigint not null
        constraint FKp5o1w7ukqaepwei6tx7yvglks
            references users,
    event_id bigint not null
        constraint FK9r3oi2ntgo4tgv09hbmmgnvx3
            references event
);

create table if not exists expense
(
    id                bigserial not null
        constraint expense_pkey
            primary key,
    create_date       timestamp,
    modify_date       timestamp,
    bill_photo        varchar(255),
    comment           varchar(255),
    expense_date timestamp,
    total_expense_sum numeric(19,2),
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
    create_date timestamp,
    modify_date timestamp,
    summa double precision,
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
    create_date timestamp,
    modify_date timestamp,
    coefficient double precision,
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
    create_date timestamp,
    modify_date timestamp,
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
    create_date timestamp,
    modify_date timestamp,
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
            references users,
    user_id bigint not null
        constraint fk7nsfg6lgq1vs83244qkrapjnx
            references roles
);

insert into roles (name)
values
('ROLE_USER'),
('ROLE_ADMIN');

insert into users (password, username, first_name, last_name)
values
('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'bob', 'bob', 'johnson'),
('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'john', 'john', 'johnson');

insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 2);