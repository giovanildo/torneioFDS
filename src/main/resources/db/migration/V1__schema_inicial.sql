CREATE TABLE tab_eatleta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL DEFAULT 'USER'
);

CREATE TABLE tab_clube (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    nacionalidade VARCHAR(60) NOT NULL
);

CREATE TABLE tab_torneio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    porque_do_nome VARCHAR(255),
    data_torneio DATE NOT NULL
);

CREATE TABLE tab_competidor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    torneio_id BIGINT NOT NULL,
    eatleta_id BIGINT NOT NULL,
    clube_id BIGINT NOT NULL,
    FOREIGN KEY (torneio_id) REFERENCES tab_torneio(id),
    FOREIGN KEY (eatleta_id) REFERENCES tab_eatleta(id),
    FOREIGN KEY (clube_id) REFERENCES tab_clube(id)
);

CREATE TABLE tab_partida (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    torneio_id BIGINT NOT NULL,
    rodada INTEGER NOT NULL,
    encerrada BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (torneio_id) REFERENCES tab_torneio(id)
);

CREATE TABLE tab_competidor_em_campo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    partida_id BIGINT NOT NULL,
    competidor_id BIGINT NOT NULL,
    gols INTEGER NOT NULL DEFAULT 0,
    joga_em_casa BOOLEAN NOT NULL,
    FOREIGN KEY (partida_id) REFERENCES tab_partida(id),
    FOREIGN KEY (competidor_id) REFERENCES tab_competidor(id)
);

CREATE TABLE tab_premio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    torneio_id BIGINT NOT NULL,
    eatleta_id BIGINT NOT NULL,
    clube_id BIGINT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    pontos INTEGER NOT NULL DEFAULT 0,
    gols_pro INTEGER NOT NULL DEFAULT 0,
    gols_contra INTEGER NOT NULL DEFAULT 0,
    vitorias INTEGER NOT NULL DEFAULT 0,
    derrotas INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (torneio_id) REFERENCES tab_torneio(id),
    FOREIGN KEY (eatleta_id) REFERENCES tab_eatleta(id),
    FOREIGN KEY (clube_id) REFERENCES tab_clube(id)
);
