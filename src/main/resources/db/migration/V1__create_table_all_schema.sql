CREATE TABLE IF NOT EXISTS usuario (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       username VARCHAR(255) NOT NULL UNIQUE,
                                       password VARCHAR(255) NOT NULL,
                                       email VARCHAR(255) NOT NULL UNIQUE,
                                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS anime (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
                                     genre VARCHAR(255) NOT NULL,
                                     anime_year INT NOT NULL,
                                     image_url VARCHAR(255) NOT NULL,
                                     created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                     usuario_id BIGINT,  -- Adicionando referência ao usuário que postou o anime
                                     FOREIGN KEY (usuario_id) REFERENCES usuario(id)  -- Relacionamento com a tabela usuario
);

CREATE TABLE IF NOT EXISTS avaliacao (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         rating INT NOT NULL,
                                         comment VARCHAR(1000),
                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         usuario_id BIGINT,
                                         anime_id BIGINT,
                                         FOREIGN KEY (usuario_id) REFERENCES usuario(id),
                                         FOREIGN KEY (anime_id) REFERENCES anime(id)
);

-- Inserindo alguns animes de exemplo
INSERT INTO anime (title, genre, anime_year, image_url, created_at, usuario_id) VALUES
                                                                                    ('Naruto', 'Ação', 2002, 'https://i.pinimg.com/736x/79/b8/b5/79b8b57c6f026109aa272d473de67677.jpg', NOW(), 1),
                                                                                    ('Attack on Titan', 'Ação', 2013, 'https://wallpapers.com/images/hd/attack-on-titan-pictures-uk5xzd7go5nb00sj.jpg', NOW(), 2),
                                                                                    ('My Hero Academia', 'Ação', 2016, 'https://images8.alphacoders.com/707/thumb-1920-707447.png', NOW(), 2),
                                                                                    ('One Piece', 'Aventura', 1999, 'https://e1.pngegg.com/pngimages/23/556/png-clipart-one-piece-logo-one-piece-thumbnail.png', NOW(), 2),
                                                                                    ('Death Note', 'Suspense', 2006, 'https://e1.pngegg.com/pngimages/548/687/png-clipart-death-note-v2-death-note-anime-thumbnail.png', NOW(), 3);


-- Inserindo alguns usuários de exemplo
INSERT INTO usuario (username, password, email, created_at, updated_at) VALUES
                                                                            ('luanmartins', '$2a$13$/a8Q8gtgqqMNpdTiAucAgu0eoyz0aHJ1eK6EJ8XtWXpY6yzdhISgK', 'luan.martins@example.com', NOW(), NOW()),
                                                                            ('carolrodrigues', '$2a$13$/a8Q8gtgqqMNpdTiAucAgu0eoyz0aHJ1eK6EJ8XtWXpY6yzdhISgK', 'carol.rodrigues@example.com', NOW(), NOW()),
                                                                            ('ricardosouza', '$2a$13$/a8Q8gtgqqMNpdTiAucAgu0eoyz0aHJ1eK6EJ8XtWXpY6yzdhISgK', 'ricardo.souza@example.com', NOW(), NOW()),
                                                                            ('andreapereira', '$2a$13$/a8Q8gtgqqMNpdTiAucAgu0eoyz0aHJ1eK6EJ8XtWXpY6yzdhISgK', 'andrea.pereira@example.com', NOW(), NOW()),
                                                                            ('pedrohenrique', '$2a$13$/a8Q8gtgqqMNpdTiAucAgu0eoyz0aHJ1eK6EJ8XtWXpY6yzdhISgK', 'pedro.henrique@example.com', NOW(), NOW());


-- Atualizando a tabela de animes para relacioná-los com os usuários
UPDATE anime SET usuario_id = 1 WHERE id = 1;  -- Naruto
UPDATE anime SET usuario_id = 2 WHERE id = 2;  -- Attack on Titan
UPDATE anime SET usuario_id = 2 WHERE id = 3;  -- My Hero Academia
UPDATE anime SET usuario_id = 2 WHERE id = 4;  -- One Piece
UPDATE anime SET usuario_id = 3 WHERE id = 5;  -- Death Note