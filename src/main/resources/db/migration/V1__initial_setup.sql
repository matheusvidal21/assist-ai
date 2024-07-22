CREATE TABLE IF NOT EXISTS `users` (
                                       `id` INT AUTO_INCREMENT PRIMARY KEY,
                                       `username` VARCHAR(50) UNIQUE NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) UNIQUE NOT NULL,
    `role` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS `tickets` (
                                         `id` INT AUTO_INCREMENT PRIMARY KEY,
                                         `user_id` INT,
                                         `issue` TEXT NOT NULL,
                                         `status` VARCHAR(20) DEFAULT 'OPEN',
    `assigned_to` VARCHAR(50),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
    );

CREATE TABLE IF NOT EXISTS `messages` (
                                          `id` INT AUTO_INCREMENT PRIMARY KEY,
                                          `from_user_id` INT,
                                          `to_user_id` INT,
                                          `content` TEXT NOT NULL,
                                          `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                          FOREIGN KEY (`from_user_id`) REFERENCES `users`(`id`),
    FOREIGN KEY (`to_user_id`) REFERENCES `users`(`id`)
    );