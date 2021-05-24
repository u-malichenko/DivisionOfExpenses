insert into "doe".roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into "doe".users (password, username, first_name, last_name)
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

insert into "doe".users_roles (role_id, user_id)
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