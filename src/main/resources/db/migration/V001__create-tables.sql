CREATE TABLE person (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    phone_number VARCHAR(20),
    CONSTRAINT uk_person_cpf UNIQUE (cpf)
);

CREATE TABLE technician (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES person(id),
    CONSTRAINT uc_technician_id UNIQUE (id)
);

CREATE TABLE customer (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES person(id),
    CONSTRAINT uc_customer_id UNIQUE (id)
);

CREATE TABLE order_service (
    id INT AUTO_INCREMENT PRIMARY KEY,
    opening_date DATETIME,
    closing_date DATETIME,
    priority ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL,
    observation TEXT,
    status ENUM('OPEN', 'IN_PROGRESS', 'CLOSED') NOT NULL,
    technician_id INT,
    customer_id INT,
    FOREIGN KEY (technician_id) REFERENCES technician(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);
