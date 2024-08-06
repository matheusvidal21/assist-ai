CREATE TABLE IF NOT EXISTS `users` (
                                       `name` VARCHAR(255) NOT NULL,
                                       `id` INT AUTO_INCREMENT PRIMARY KEY,
                                       `username` VARCHAR(50) UNIQUE NOT NULL,
                                       `password` VARCHAR(100) NOT NULL,
                                       `email` VARCHAR(100) UNIQUE NOT NULL,
                                       `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `roles` (
                                       `id` INT AUTO_INCREMENT PRIMARY KEY,
                                       `name` VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS `users_roles` (
                                                `user_id` INT,
                                                `role_id` INT,
                                                FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
                                                FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`),
                                                PRIMARY KEY (`user_id`, `role_id`)
);

CREATE TABLE IF NOT EXISTS `tickets` (
                                         `id` INT AUTO_INCREMENT PRIMARY KEY,
                                         `user_id` INT,
                                         `issue` TEXT NOT NULL,
                                         `status` VARCHAR(20) DEFAULT 'OPEN',
                                         `assigned_to` INT,
                                         `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                          FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
                                         FOREIGN KEY (`assigned_to`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `messages` (
                                          `id` INT AUTO_INCREMENT PRIMARY KEY,
                                          `from_user_id` INT,
                                          `to_user_id` INT,
                                          `content` TEXT NOT NULL,
                                          `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                          `ticket_id` INT NULL,
                                          FOREIGN KEY (`from_user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
                                          FOREIGN KEY (`to_user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
                                          FOREIGN KEY (`ticket_id`) REFERENCES `tickets`(`id`)
);


