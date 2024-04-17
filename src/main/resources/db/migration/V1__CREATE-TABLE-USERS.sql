CREATE TABLE USERS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    lastName VARCHAR(250) NOT NULL,
    identification VARCHAR(250) NOT NULL,
    cellphone BIGINT NOT NULL,
    username VARCHAR(250) UNIQUE NOT NULL,
    password VARCHAR(250) NOT NULL,
    role ENUM('ADMIN', 'SUB_ADMIN', 'WAITRESS', 'BARTENDER', 'COOK') NOT NULL
);