set foreign_key_checks = 0;

delete from person;
delete from technician;
delete from customer;
delete from order_service;

set foreign_key_checks = 1;


INSERT INTO person (id, name, cpf, phone_number) VALUES
    (1, 'Joel', '12345678901', '123-456-7890'),
    (2, 'Maciel', '98765432101', '987-654-3210'),
    (3, 'Mirela', '11111111111', '111-111-1111'),
    (4, 'Alencar', '22222222222', '222-222-2222');

INSERT INTO technician (id) VALUES
    (1), (2);


INSERT INTO customer (id) VALUES
    (3), (4);

INSERT INTO order_service (opening_date, closing_date, priority, observation, status, technician_id, customer_id) VALUES
    ('2024-01-12 10:00:00', '2024-01-12 12:00:00', 'HIGH', 'Observation 1', 'OPEN', 1, 3),
    ('2024-01-13 14:00:00', '2024-01-13 16:00:00', 'MEDIUM', 'Observation 2', 'CLOSED', 2, 4);
