create table if not exists roles
(
    id          bigint generated by default as identity
        constraint roles_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    name        varchar(255)
);

create table if not exists users
(
    id          bigint generated by default as identity
        constraint users_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    email       varchar(255) not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    first_name  varchar(255),
    last_name   varchar(255),
    password    varchar(255),
    username    varchar(255) not null
        constraint uk_r43af9ap4edm43mmtq01oddj6
            unique
);

create table if not exists event
(
    id               bigint generated by default as identity
        constraint event_pkey
            primary key,
    create_date      timestamp default current_timestamp,
    modify_date      timestamp default current_timestamp,
    description      varchar(255),
    event_date_time  timestamp default current_timestamp,
    name             varchar(255),
    total_event_sum  numeric(19, 2) default 0.00,
    user_id          bigint
        constraint fk31rxexkqqbeymnpw4d3bf9vsy
            references users,
    shopping_list_id bigint
);

create table if not exists event_member
(
    id          bigint generated by default as identity
        constraint event_member_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    saldo       numeric(19, 2) default 0.00,
    event_id    bigint
        constraint fk8e54pd1io5a7gbp6x1io4vkft
            references event,
    user_id     bigint
        constraint fkoobns1hxage1y1gcnf9ol8y2m
            references users
);

create table if not exists events_users
(
    event_id bigint not null
        constraint fk9kmcoy2u0tyold6dcq9lo3lrr
            references users,
    user_id  bigint not null
        constraint fktqq1uwhthsaqxn0ntt7b57n9d
            references event
);

create table if not exists expense
(
    id                bigint generated by default as identity
        constraint expense_pkey
            primary key,
    create_date       timestamp default current_timestamp,
    modify_date       timestamp default current_timestamp,
    bill_photo        varchar(255),
    comment           varchar(255),
    expense_date      timestamp default current_timestamp,
    total_expense_sum numeric(19, 2) default 0.00,
    buyer_id          bigint
        constraint fk2wm2hsnaeugfskywd72bsfx2c
            references users,
    event_id          bigint
        constraint fk33vnonl4r0y7r0e2qy06n5nfg
            references event
);

create table if not exists direct_payer
(
    id          bigint generated by default as identity
        constraint direct_payer_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    summa       numeric(19, 2) default 0.00,
    expense_id  bigint
        constraint fk8bobcky0vfots3fkiwht6hrqe
            references expense,
    user_id     bigint
        constraint fk4nmrd19f3mct7urj75tbujhxq
            references users
);

create table if not exists partitial_payer
(
    id          bigint generated by default as identity
        constraint partitial_payer_pkey
            primary key,
    create_date timestamp default current_timestamp,
    modify_date timestamp default current_timestamp,
    coefficient numeric(19, 2) default 1.00,
    expense_id  bigint
        constraint fk3fsnhfxxlmt0e9xa6s91ramr5
            references expense,
    user_id     bigint
        constraint fksnqrvo7uxs5gv50w3qfegu02b
            references users
);

create table if not exists shopping_list
(
    id           bigint generated by default as identity
        constraint shopping_list_pkey
            primary key,
    create_date  timestamp default current_timestamp,
    modify_date  timestamp default current_timestamp,
    comment      varchar(255),
    enabled_flag boolean,
    event_id     bigint
        constraint fkss1lu0q3tgd9rm59satrp7bjo
            references event
);

alter table event
    add constraint fkcaeoetktnp0k5au437rysqaef
        foreign key (shopping_list_id) references shopping_list;

create table if not exists shopping_list_item
(
    id                  bigint generated by default as identity
        constraint shopping_list_item_pkey
            primary key,
    create_date         timestamp default current_timestamp,
    modify_date         timestamp default current_timestamp,
    name                varchar(255),
    quantity            varchar(255),
    shoppinglistitem_id bigint
        constraint fkewo6pgiv4ap559xv2fkl4ejwt
            references shopping_list
);

create table if not exists users_roles
(
    user_id  bigint not null
        constraint fk2o0jvgh89lemvvo17cbqvdxaa
            references users,
    roles_id bigint not null
        constraint fka62j07k5mhgifpp955h37ponj
            references roles
);

create table if not exists registration_ticket
(
    id              bigint generated by default as identity
        constraint registration_ticket_pkey
            primary key,
    create_date     timestamp,
    modify_date     timestamp,
    checking_ticket varchar(255) not null
        constraint uk_sanvyo2hbblkl6as6n7p2uue4
            unique,
    email           varchar(255) not null
        constraint uk_q01x9r2mi017u4rhlvayep1uu
            unique,
    first_name      varchar(255),
    last_name       varchar(255),
    password        varchar(255),
    username        varchar(255) not null
        constraint uk_5j6ra5jmhkerods8hhtcjnpps
            unique
);

