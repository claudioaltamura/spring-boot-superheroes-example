CREATE TABLE superheroes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    real_name VARCHAR(255),
    power DOUBLE NOT NULL,
    PRIMARY KEY (id)
);