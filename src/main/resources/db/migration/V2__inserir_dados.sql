-- Inserindo cidades
INSERT INTO cidade (cid_id, cid_nome, cid_uf) VALUES
(1, 'Cuiabá', 'MT'),
(2, 'Várzea Grande', 'MT'),
(3, 'Rondonópolis', 'MT'),
(4, 'Sinop', 'MT'),
(5, 'Tangará da Serra', 'MT'),
(6, 'Lucas do Rio Verde', 'MT'),
(7, 'Sorriso', 'MT'),
(8, 'Barra do Garças', 'MT'),
(9, 'Primavera do Leste', 'MT'),
(10, 'Alta Floresta', 'MT'),
(11, 'Cáceres', 'MT'),
(12, 'Pontes e Lacerda', 'MT'),
(13, 'Nova Mutum', 'MT'),
(14, 'Campo Verde', 'MT'),
(15, 'Juína', 'MT'),
(16, 'Colíder', 'MT'),
(17, 'Guarantã do Norte', 'MT'),
(18, 'Peixoto de Azevedo', 'MT'),
(19, 'Querência', 'MT'),
(20, 'Jaciara', 'MT');

-- Inserindo endereços
INSERT INTO endereco ( end_tipo_logradouro, end_logradouro, end_numero, end_bairro, cid_id) VALUES
('Rua', 'Paulista', 1000, 'Centro', 1),
('Avenida', 'Atlântica', 500, 'Copacabana', 2),
('Rua', 'Amazonas', 300, 'Savassi', 3);

-- Inserindo pessoas
INSERT INTO pessoa (pes_nome, pes_data_nascimento, pes_sexo, pes_mae, pes_pai) VALUES
('João Silva', '1985-07-10', 'Masculino', 'Maria Silva', 'Carlos Silva'),
('Ana Souza', '1992-03-25', 'Feminino', 'Clara Souza', 'José Souza'),
('Pedro Oliveira', '1978-11-13', 'Masculino', 'Teresa Oliveira', 'Antonio Oliveira'),
('Alexandre', '1978-11-13', 'Masculino', 'Teresa Oliveira', 'Antonio Oliveira'),
('Andre', '1978-11-13', 'Masculino', 'Teresa Oliveira', 'Antonio Oliveira'),
('Marcela', '1978-11-13', 'Feminino', 'Teresa Oliveira', 'Antonio Oliveira'),
('Giovana', '1978-11-13', 'Feminino', 'Teresa Oliveira', 'Antonio Oliveira');

-- Relacionando pessoas a endereços
INSERT INTO pessoa_endereco (pes_id, end_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Inserindo servidores temporários
INSERT INTO servidor_temporario (pes_id, st_data_admissao, st_data_demissao) VALUES
(3, '2020-01-15', '2023-06-30'),
(4, '2020-01-15', '2023-05-30'),
(6, '2020-01-15', '2023-07-30');

-- Inserindo servidores efetivos
INSERT INTO servidor_efetivo (pes_id, se_matricula) VALUES
(2, '20231000'),
(3, '20231001');

-- Inserindo unidades
INSERT INTO unidade (unid_nome, unid_sigla) VALUES
('Secretaria de Educação', 'SEDUC'),
('Secretaria de Saúde', 'SESAU'),
('Secretaria de Fazenda', 'SEFAZ'),
('Secretaria de Segurança Pública', 'SESP'),
('Secretaria de Meio Ambiente', 'SEMA'),
('Secretaria de Assistência Social', 'SEAS'),
('Secretaria de Infraestrutura', 'SINFRA'),
('Secretaria de Administração', 'SAD'),
('Secretaria de Planejamento e Gestão', 'SEPLAG'),
('Secretaria de Cultura', 'SECULT'),
('Secretaria de Esportes', 'SESPORTE'),
('Secretaria de Agricultura Familiar', 'SEAF'),
('Secretaria de Desenvolvimento Econômico', 'SEDEC'),
('Secretaria de Trabalho e Emprego', 'SETRAB'),
('Secretaria de Ciência e Tecnologia', 'SECTI'),
('Secretaria de Justiça e Direitos Humanos', 'SEJUDH'),
('Secretaria de Turismo', 'SETUR'),
('Secretaria de Comunicação', 'SECOM');

-- Relacionando unidades a endereços
INSERT INTO unidade_endereco (unid_id, end_id) VALUES
(1, 1),
(2, 2);

-- Inserindo lotações
INSERT INTO lotacao (pes_id, unid_id, lot_data_lotacao, lot_data_remocao, lot_portaria) VALUES
(2, 1, '2021-05-10', NULL, 'Portaria 001/2021'),
(3, 2, '2019-09-20', NULL, 'Portaria 002/2019');


INSERT INTO usuario (user_nome, user_login, user_senha, user_email) VALUES
('João Silva', 'alexandre', '$2a$10$ai0zicF5DefunYeVEen5zu8wws7sIQZRVfUJ5bfoVDYT5/pd0kfwa', 'alexandre@gmail.com'),
('Ana Souza', 'marcela', '$2a$10$ai0zicF5DefunYeVEen5zu8wws7sIQZRVfUJ5bfoVDYT5/pd0kfwa', 'marcela@gmail.com'),
('Pedro Oliveira', 'admin', '$2a$10$ai0zicF5DefunYeVEen5zu8wws7sIQZRVfUJ5bfoVDYT5/pd0kfwa', 'admin@gmail.com');
