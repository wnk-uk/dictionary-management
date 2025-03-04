CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     role ENUM('USER', 'ADMIN') NOT NULL
);

CREATE TABLE IF NOT EXISTS sheet_data (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     data CLOB NOT NULL
);