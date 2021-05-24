create table if not exists doe.flyway_schema_history
(
    installed_rank integer                             not null
        constraint flyway_schema_history_pk
            primary key,
    version        varchar(50),
    description    varchar(200)                        not null,
    type           varchar(20)                         not null,
    script         varchar(1000)                       not null,
    checksum       integer,
    installed_by   varchar(100)                        not null,
    installed_on   timestamp DEFAULT CURRENT_TIMESTAMP not null,
    execution_time integer                             not null,
    success        boolean                             not null
);

create index if not exists flyway_schema_history_s_idx
    on doe.flyway_schema_history (success);

create table if not exists doe.event_roles
(
    event_id bigint not null,
    roles_id bigint not null
        constraint uk_qydu3p9hsfvlkaxq1tvftf4b0
            unique
);

create table if not exists doe.expense
(
    id                bigint generated by default as identity
        constraint expense_pkey
            primary key,
    create_date       timestamp DEFAULT CURRENT_TIMESTAMP not null,
    modify_date       timestamp DEFAULT CURRENT_TIMESTAMP not null,
    expense_date      timestamp DEFAULT CURRENT_TIMESTAMP not null,
    bill_photo        varchar(255),
    comment           varchar(255),
    total_expense_sum numeric(19, 2),
    buyer_id          bigint,
    event_id          bigint
);

create table if not exists doe.users_expense
(
    expense_id bigint not null,
    user_id    bigint not null
        constraint uk_mf679n92xfppdm65roapo95je
            unique
        constraint fkcqr54mudug04wh17ikfj63nwn
            references doe.expense
);

create table if not exists doe.roles
(
    id          bigint generated by default as identity
        constraint roles_pkey
            primary key,
    create_date timestamp DEFAULT CURRENT_TIMESTAMP not null,
    modify_date timestamp DEFAULT CURRENT_TIMESTAMP not null,
    name        varchar(255)
);

create table if not exists doe.users
(
    id          bigint generated by default as identity
        constraint users_pkey
            primary key,
    create_date timestamp DEFAULT CURRENT_TIMESTAMP not null,
    modify_date timestamp DEFAULT CURRENT_TIMESTAMP not null,
    first_name  varchar(255),
    last_name   varchar(255),
    password    varchar(255),
    username    varchar(255)
);

create table if not exists doe.event
(
    id               bigint generated by default as identity
        constraint event_pkey
            primary key,
    create_date      timestamp      DEFAULT CURRENT_TIMESTAMP not null,
    modify_date      timestamp      DEFAULT CURRENT_TIMESTAMP not null,
    description      varchar(255),
    event_date_time  timestamp      DEFAULT CURRENT_TIMESTAMP not null,
    name             varchar(255),
    total_event_sum  numeric(19, 2) DEFAULT 0.00,
    user_id          bigint
        constraint fk31rxexkqqbeymnpw4d3bf9vsy
            references doe.users,
    shopping_list_id bigint
);

create table if not exists doe.events_users
(
    user_id  bigint not null
        constraint fk4qi1ew1ii81ipjy5p3oy5ypeq
            references doe.users,
    event_id bigint not null
        constraint fkma083iclvtw8x773pvlf2k6mt
            references doe.event
);

create table if not exists doe.expense
(
    id                bigint generated by default as identity
        constraint expense_pkey
            primary key,
    create_date       timestamp DEFAULT CURRENT_TIMESTAMP not null,
    modify_date       timestamp DEFAULT CURRENT_TIMESTAMP not null,
    bill_photo        varchar(255),
    comment           varchar(255),
    expense_date      timestamp DEFAULT CURRENT_TIMESTAMP not null,
    total_expense_sum numeric(19, 2),
    buyer_id          bigint
        constraint fk2wm2hsnaeugfskywd72bsfx2c
            references doe.users,
    event_id          bigint
        constraint fk33vnonl4r0y7r0e2qy06n5nfg
            references doe.event
);

create table if not exists doe.direct_payer
(
    id          bigint generated by default as identity
        constraint direct_payer_pkey
            primary key,
    create_date timestamp      DEFAULT CURRENT_TIMESTAMP not null,
    modify_date timestamp      DEFAULT CURRENT_TIMESTAMP not null,
    summa       numeric(19, 2) DEFAULT 0.00,
    expense_id  bigint
        constraint fk8bobcky0vfots3fkiwht6hrqe
            references doe.expense,
    user_id     bigint
        constraint fk4nmrd19f3mct7urj75tbujhxq
            references doe.users
);

create table if not exists doe.invited_events_users
(
    user_id  bigint not null
        constraint fkp5o1w7ukqaepwei6tx7yvglks
            references doe.users,
    event_id bigint not null
        constraint fk9r3oi2ntgo4tgv09hbmmgnvx3
            references doe.event
);

create table if not exists doe.partitial_payer
(
    id          bigint generated by default as identity
        constraint partitial_payer_pkey
            primary key,
    create_date timestamp      DEFAULT CURRENT_TIMESTAMP not null,
    modify_date timestamp      DEFAULT CURRENT_TIMESTAMP not null,
    coefficient numeric(19, 2) default 1.00,
    expense_id  bigint
        constraint fk3fsnhfxxlmt0e9xa6s91ramr5
            references doe.expense,
    user_id     bigint
        constraint fksnqrvo7uxs5gv50w3qfegu02b
            references doe.users
);

create table if not exists doe.shopping_list
(
    id           bigint generated by default as identity
        constraint shopping_list_pkey
            primary key,
    create_date  timestamp DEFAULT CURRENT_TIMESTAMP not null,
    modify_date  timestamp DEFAULT CURRENT_TIMESTAMP not null,
    comment      varchar(255),
    enabled_flag boolean,
    event_id     bigint
        constraint fkss1lu0q3tgd9rm59satrp7bjo
            references doe.event
);

create table if not exists doe.shopping_list_item
(
    id                  bigint generated by default as identity
        constraint shopping_list_item_pkey
            primary key,
    create_date         timestamp DEFAULT CURRENT_TIMESTAMP not null,
    modify_date         timestamp DEFAULT CURRENT_TIMESTAMP not null,
    name                varchar(255),
    quantity            varchar(255),
    shoppinglistitem_id bigint
        constraint fkewo6pgiv4ap559xv2fkl4ejwt
            references doe.shopping_list
);

create table if not exists doe.users_roles
(
    user_id bigint not null
        constraint fk7bvb4gjgqcgxvgddx0pgefkei
            references doe.users,
    role_id bigint not null
        constraint fk7nsfg6lgq1vs83244qkrapjnx
            references doe.roles
);
