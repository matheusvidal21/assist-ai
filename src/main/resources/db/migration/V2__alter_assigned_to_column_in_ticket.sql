ALTER TABLE `tickets` DROP COLUMN `assigned_to`;

ALTER TABLE `tickets` ADD COLUMN `assigned_to` INT;

ALTER TABLE `tickets` ADD CONSTRAINT `fk_tickets_users` FOREIGN KEY (`assigned_to`) REFERENCES `users` (`id`);