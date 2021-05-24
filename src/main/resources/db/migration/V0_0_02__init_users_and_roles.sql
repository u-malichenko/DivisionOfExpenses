insert into "doe".roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into "doe".users (password, username, first_name, last_name)
values ('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'bob', 'bob', 'johnson'),
       ('$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'john', 'john', 'johnson');

insert into "doe".users_roles (user_id, role_id)
values (1, 1),
       (2, 2);