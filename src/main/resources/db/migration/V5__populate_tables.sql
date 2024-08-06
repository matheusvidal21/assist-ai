-- Inserir dados na tabela roles
INSERT INTO `roles` (`name`) VALUES ('ADMIN'), ('USER'), ('SUPPORT');

-- Inserir dados na tabela users
INSERT INTO `users` (`name`, `username`, `password`, `email`) VALUES
    ('Alice Johnson', 'alice', 'password1', 'alice@example.com'),
    ('Bob Smith', 'bob', 'password2', 'bob@example.com'),
    ('Charlie Brown', 'charlie', 'password3', 'charlie@example.com'),
    ('David White', 'david', 'password4', 'david@example.com');

-- Inserir dados na tabela users_roles
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
    (1, 1), -- Alice as Admin
    (2, 2), -- Bob as User
    (3, 2), -- Charlie as User
    (4, 3); -- David as Support

-- Inserir dados na tabela tickets
INSERT INTO `tickets` (`user_id`, `issue`, `status`, `assigned_to`) VALUES
    (2, 'Cannot access account', 'OPEN', 4),
    (3, 'Website not loading', 'IN PROGRESS', 4);

-- Inserir dados na tabela messages
INSERT INTO `messages` (`from_user_id`, `to_user_id`, `content`, `ticket_id`) VALUES
    (2, 4, 'I am having trouble accessing my account.', 1),
    (4, 2, 'I will look into it.', 1),
    (3, 4, 'The website is down.', 2),
    (4, 3, 'We are working on it.', 2);

-- Verificar os dados inseridos
SELECT * FROM `roles`;
SELECT * FROM `users`;
SELECT * FROM `users_roles`;
SELECT * FROM `tickets`;
SELECT * FROM `messages`;
