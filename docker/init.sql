CREATE DATABASE IF NOT EXISTS citizen_db;
USE citizen_db;

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     role VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS citizen (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       first_name VARCHAR(255) NOT NULL,
                                       last_name VARCHAR(255) NOT NULL,
                                       passport_number VARCHAR(50) NOT NULL UNIQUE,
                                       street VARCHAR(255),
                                       city VARCHAR(255),
                                       zip_code VARCHAR(20),
                                       user_id BIGINT UNIQUE,
                                       FOREIGN KEY (user_id) REFERENCES users(id)
);


INSERT INTO users (username, password, role) VALUES
    ('admin', '$2a$10$l10xpAodaC57o4lrM9gIGeM/tIMMsjeGgJOsd2tZE6RHM1tEEhM96', 'ADMIN');

INSERT INTO citizen (first_name, last_name, passport_number, street, city, zip_code, user_id) VALUES
    ('Sara', 'Miller', 'AB123456', 'Wola', 'Warszawa', '62-701', NULL);

