INSERT INTO users (first_name, last_name, password, username) VALUES ( 'bob', 'bombaster', '$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'bob');
INSERT INTO users (first_name, last_name, password, username) VALUES ( 'john', 'johnson', '$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'john');
INSERT INTO users (first_name, last_name, password, username) VALUES ( 'silver', 'star', '$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'silver');
INSERT INTO users (first_name, last_name, password, username) VALUES ( 'samurai', 'silver', '$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'samurai');
INSERT INTO users (first_name, last_name, password, username) VALUES ( 'vi', 'unknown', '$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'vi');
INSERT INTO users (first_name, last_name, password, username) VALUES ( 'panam', 'petrova', '$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'panam');
INSERT INTO users (first_name, last_name, password, username) VALUES ( 'jack', 'ivanov', '$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'jack');
INSERT INTO users (first_name, last_name, password, username) VALUES ( 'soul', 'sidorov', '$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'soul');
INSERT INTO users (first_name, last_name, password, username) VALUES ( 'mitch', 'kuznezov', '$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'mitch');
INSERT INTO users (first_name, last_name, password, username) VALUES ( 'goro', 'takimura', '$2y$12$03d0usVKKgPvmgt4dMfGLuyFgHUzTZw8w8vhrjR98ppMBRrpTbvTa', 'goro');

INSERT INTO roles ( name) VALUES ( 'ROLE_USER');
INSERT INTO roles ( name) VALUES ( 'ROLE_ADMIN');

INSERT INTO users_roles (user_id, roles_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, roles_id) VALUES (3, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES (4, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES (5, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES (6, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES (7, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES (8, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES (9, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES (10, 1);

INSERT INTO event ( description, event_date_time, name, total_event_sum, user_id, shopping_list_id) VALUES ('event_description 1', '2021-06-29 21:00:00.000000', 'first_event', 0.00, 1, null);
INSERT INTO event ( description, event_date_time, name, total_event_sum, user_id, shopping_list_id) VALUES ('event_description 2', '2021-06-22 21:00:00.000000', 'second_event', 0.00, 1, null);

INSERT INTO event_member ( saldo, event_id, user_id) VALUES (0.00, 1, 1);
INSERT INTO event_member ( saldo, event_id, user_id) VALUES (0.00, 1, 4);
INSERT INTO event_member ( saldo, event_id, user_id) VALUES (0.00, 1, 6);
INSERT INTO event_member ( saldo, event_id, user_id) VALUES (0.00, 2, 1);
INSERT INTO event_member ( saldo, event_id, user_id) VALUES (0.00, 2, 10);
INSERT INTO event_member ( saldo, event_id, user_id) VALUES (0.00, 2, 7);
INSERT INTO event_member ( saldo, event_id, user_id) VALUES (0.00, 2, 5);

INSERT INTO events_users (event_id, user_id) VALUES (1, 1);
INSERT INTO events_users (event_id, user_id) VALUES (4, 1);
INSERT INTO events_users (event_id, user_id) VALUES (6, 1);
INSERT INTO events_users (event_id, user_id) VALUES (1, 2);
INSERT INTO events_users (event_id, user_id) VALUES (10, 2);
INSERT INTO events_users (event_id, user_id) VALUES (7, 2);
INSERT INTO events_users (event_id, user_id) VALUES (5, 2);

INSERT INTO expense (bill_photo, comment, total_expense_sum, buyer_id, event_id) VALUES (null, 'first_expense', 1212.00, 1, 1);
INSERT INTO expense (bill_photo, comment, total_expense_sum, buyer_id, event_id) VALUES (null, 'second_expense', 3444.00, 4, 1);
INSERT INTO expense (bill_photo, comment, total_expense_sum, buyer_id, event_id) VALUES (null, 'dfsgsdfgh', 1334.00, 6, 1);
INSERT INTO expense (bill_photo, comment, total_expense_sum, buyer_id, event_id) VALUES (null, 'third_expense', 1111.00, 10, 2);
INSERT INTO expense (bill_photo, comment, total_expense_sum, buyer_id, event_id) VALUES (null, 'forth_expense', 4444.00, 7, 2);
INSERT INTO expense (bill_photo, comment, total_expense_sum, buyer_id, event_id) VALUES (null, 'five_expense', 7777.00, 5, 2);



