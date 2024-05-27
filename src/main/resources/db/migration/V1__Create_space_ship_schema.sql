-- Crear tabla para Production
CREATE TABLE production (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    launched_year SMALLINT,
    seasons SMALLINT
);

-- Crear tabla para SpaceShip
CREATE TABLE spaceship (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    last_captain VARCHAR(255),
    faction VARCHAR(255)
);

-- Tabla de relación entre SpaceShip y Production
CREATE TABLE spaceship_production (
    spaceship_id VARCHAR(255) NOT NULL,
    production_id INTEGER NOT NULL,
    FOREIGN KEY (spaceship_id) REFERENCES spaceship(id),
    FOREIGN KEY (production_id) REFERENCES production(id),
    PRIMARY KEY (spaceship_id, production_id)
);

-- Insert example data for testing
INSERT INTO spaceship (id, name, type, last_captain, faction)
VALUES  ('1', 'USS Enterprise', 'Starship', 'Jean-Luc Picard', 'Federation'),
        ('2', 'USS Discovery', 'Explorer', 'Gabriel Lorca', 'Federation'),
    ('3', 'Defiant', 'Warship', 'Benjamin Sisko', 'Federation'),
    ('4', 'Excelsior', 'Explorer', 'Hikaru Sulu', 'Federation'),
    ('5', 'Prometheus', 'Tactical Cruiser', 'Unknown', 'Federation'),
    ('6', 'Voyager', 'Starship', 'Kathryn Janeway', 'Federation');
INSERT INTO production (type, name, launched_year, seasons)
VALUES ('Movie', 'Star Trek: The Motion Picture', 1979, NULL),
        ('Movie', 'Star Trek: The Motion Discovery', 2020, NULL),
        ('Serie', 'Star Trek: Discovery', NULL, 5),
       ('Serie', 'Star Trek: The Next Generation', NULL, 7);

INSERT INTO spaceship_production (spaceship_id, production_id)
VALUES ('1', 1),
        ('2', 3),
        ('2', 4),
        ('2', 1),
       ('4', 2),
        ('5', 4),
        ('6', 1),
        ('3', 4),
        ('3', 1),
       ('1', 2);

