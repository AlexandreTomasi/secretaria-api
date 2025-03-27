CREATE TABLE cidade (
    cid_id SERIAL PRIMARY KEY,
    cid_nome VARCHAR(200) NOT NULL,
    cid_uf CHAR(2) NOT NULL
);

CREATE TABLE endereco (
    end_id SERIAL PRIMARY KEY,
    end_tipo_logradouro VARCHAR(50),
    end_logradouro VARCHAR(200),
    end_numero INT,
    end_bairro VARCHAR(100),
    cid_id INT REFERENCES cidade(cid_id)
);

CREATE TABLE pessoa (
    pes_id SERIAL PRIMARY KEY,
    pes_nome VARCHAR(200) NOT NULL,
    pes_data_nascimento DATE,
    pes_sexo VARCHAR(9),
    pes_mae VARCHAR(200),
    pes_pai VARCHAR(200)
);

CREATE TABLE foto_pessoa (
    fp_id SERIAL PRIMARY KEY,
    pes_id INT REFERENCES pessoa(pes_id),
    fp_data DATE,
    fp_bucket VARCHAR(50),
    fp_hash VARCHAR(50)
);

CREATE TABLE pessoa_endereco (
    pes_id INT REFERENCES pessoa(pes_id),
    end_id INT REFERENCES endereco(end_id),
    PRIMARY KEY (pes_id, end_id)
);

CREATE TABLE servidor_temporario (
    pes_id INT PRIMARY KEY REFERENCES pessoa(pes_id),
    st_data_admissao DATE,
    st_data_demissao DATE
);

CREATE TABLE servidor_efetivo (
    pes_id INT PRIMARY KEY REFERENCES pessoa(pes_id),
    se_matricula VARCHAR(20) NOT NULL
);

CREATE TABLE unidade (
    unid_id SERIAL PRIMARY KEY,
    unid_nome VARCHAR(200) NOT NULL,
    unid_sigla VARCHAR(20)
);

CREATE TABLE unidade_endereco (
    unid_id INT REFERENCES unidade(unid_id),
    end_id INT REFERENCES endereco(end_id),
    PRIMARY KEY (unid_id, end_id)
);

CREATE TABLE lotacao (
    lot_id SERIAL PRIMARY KEY,
    pes_id INT REFERENCES pessoa(pes_id),
    unid_id INT REFERENCES unidade(unid_id),
    lot_data_lotacao DATE,
    lot_data_remocao DATE,
    lot_portaria VARCHAR(100)
);

CREATE TABLE usuario (
    user_id SERIAL PRIMARY KEY,
    user_nome VARCHAR(200) NOT NULL,
    user_login VARCHAR(20) NOT NULL,
    user_senha VARCHAR(200) NOT NULL,
    user_email VARCHAR(200) NOT NULL
);