SET foreign_key_checks = 0;

DELETE FROM technician;
DELETE FROM customer;
DELETE FROM order_service;

SET foreign_key_checks = 1;

INSERT INTO technician (id, name, cpf, phone_number, job_function) VALUES
    (1, 'Joel', '123.456.789-33', '(85) 999369699', 'Java Developer'),
    (2, 'Maciel', '987.654.321-22', '(88) 988996646', 'Electrician'),
    (3, 'Paul', '637.324.561-85', '(88) 988996646', 'Mechanic'),
    (4, 'Jobs Rubens', '053.879.780-08', '(88) 988996646', 'Full Stack');


INSERT INTO customer (id, name, cnpj, phone_number) VALUES
    (1, 'Microsoft Corporation', '04.128.563/0001-10', '(81) 997369644'),
    (2, 'Apple Inc', '10.338.320/0001-00', '(84) 987369655'),
    (3, 'Intel Corporation', '02.217.319/0001-07', '(81) 997238945'),
    (4, 'NVIDIA Corporation', '17.167.396/0001-69', '(84) 988336521');

INSERT INTO order_service (id,opening_date, closing_date, priority, observation, status, technician_id, customer_id) VALUES
    (1,'2024-01-12 10:00:00', null , 'HIGH', 'Observation 1', 'OPEN', 1, 1),
    (2, '2024-01-09 14:00:00', '2024-01-18 16:00:00', 'MEDIUM', 'Observation 2', 'CLOSED', 2, 2),
    (3, '2024-01-15 09:30:00', null, 'LOW', 'Observation 3', 'IN_PROGRESS', 3, 3),
    (4, '2023-11-20 12:45:00', '2024-01-07 16:15:00', 'HIGH', 'Observation 4', 'CLOSED', 1, 4),
    (5, '2024-01-18 16:00:00', '2024-01-25 14:30:00', 'MEDIUM', 'Observation 5', 'CLOSED', 2, 1);

