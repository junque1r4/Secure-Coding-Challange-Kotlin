USE SecureCards;

CREATE TABLE IF NOT EXISTS person (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    admin BOOLEAN NOT NULL
    );

INSERT INTO person (name, email, password, admin) VALUES
    ('Master Admin', 'admin@securecards.com', 'admin123', true);
