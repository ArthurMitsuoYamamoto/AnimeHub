CREATE TABLE anime (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       genre VARCHAR(255) NOT NULL,
                       anime_year INT NOT NULL,
                       image_url VARCHAR(255) NOT NULL
);


CREATE TABLE usuario (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(255) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE avaliacao (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           rating INT NOT NULL,
                           comment VARCHAR(1000),
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           usuario_id BIGINT,
                           anime_id BIGINT,
                           FOREIGN KEY (usuario_id) REFERENCES usuario(id),
                           FOREIGN KEY (anime_id) REFERENCES anime(id)
);