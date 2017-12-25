INSERT INTO `role` (`id`, `name`, create_time)
VALUES (1, 'ROLE_ADMIN', '2017-12-25 4:55:44'), (2, 'ROLE_USER', '2017-12-25 4:55:44');

INSERT INTO `user` (`id`, `password`, `username`, create_time)
VALUES (1, 'root', 'root', '2017-12-25 4:55:44'), (2, 'sang', 'sang', '2017-12-25 4:55:44');

INSERT INTO `user_roles` (`user_id`, `roles_id`) VALUES (1, 1), (2, 2);