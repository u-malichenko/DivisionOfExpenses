create table "divisionOfExpenses".roles
(
    name varchar(50) not null
);

alter table "divisionOfExpenses".roles owner to ravilich;

create unique index roles_name_uindex
	on "divisionOfExpenses".roles using ??? (name);

