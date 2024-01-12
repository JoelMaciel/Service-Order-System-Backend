SET foreign_key_checks = 0;

DELETE FROM technician;
DELETE FROM customer;
DELETE FROM order_service;

SET foreign_key_checks = 1;

INSERT INTO technician (id, name, cpf, phone_number) VALUES
    (1, 'Joel', '123.456.789-33', '(85) 999369699'),
    (2, 'Maciel', '987.654.321-22', '(88) 988996646');

INSERT INTO customer (id, name, cpf, phone_number) VALUES
    (1, 'Mirela', '111.111.111-44', '(81) 997369644'),
    (2, 'Alencar', '222.222.222-55', '(84) 987369655');

INSERT INTO order_service (opening_date, closing_date, priority, observation, status, technician_id, customer_id) VALUES
    ('2024-01-12 10:00:00', '2024-01-12 12:00:00', 'HIGH', 'Observation 1', 'OPEN', 1, 1),
    ('2024-01-13 14:00:00', '2024-01-13 16:00:00', 'MEDIUM', 'Observation 2', 'CLOSED', 2, 2);
