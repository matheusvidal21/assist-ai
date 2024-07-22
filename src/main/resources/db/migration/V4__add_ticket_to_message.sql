-- Adicionar a coluna ticket_id na tabela messages
ALTER TABLE `messages`
    ADD COLUMN `ticket_id` INT NULL,
    ADD CONSTRAINT `fk_ticket`
    FOREIGN KEY (`ticket_id`) REFERENCES `tickets`(`id`);
