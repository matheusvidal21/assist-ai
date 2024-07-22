INSERT INTO `users` (`username`, `password`, `email`, `role`, `name`)
VALUES
    ('john_doe', 'password123', 'john.doe@example.com', 'ADMIN', 'John Doe'),
    ('jane_smith', 'password123', 'jane.smith@example.com', 'AGENT', 'Jane Smith'),
    ('sam_wilson', 'password123', 'sam.wilson@example.com', 'CUSTOMER', 'Sam Wilson');

INSERT INTO `tickets` (`user_id`, `issue`, `status`, `assigned_to`)
VALUES
    (1, 'Unable to login', 'OPEN', 2),
    (2, 'System crash when uploading file', 'IN_PROGRESS', 3),
    (3, 'Password reset not working', 'CLOSED', 1);

INSERT INTO `messages` (`from_user_id`, `to_user_id`, `content`, `ticket_id`)
VALUES
    (1, 2, 'I am unable to login. Please help.', 1),
    (2, 1, 'Can you provide more details about the issue?', 1),
    (2, 3, 'We are looking into the system crash issue.', 2),
    (3, 2, 'Thanks for the update.', 2),
    (3, 1, 'The password reset issue has been resolved.', 3),
    (1, 3, 'Thank you for fixing the issue.', 3);
