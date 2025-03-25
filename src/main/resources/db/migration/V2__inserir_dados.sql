-- Inserindo cidades
INSERT INTO cidade (cid_nome, cid_uf) VALUES
('São Paulo', 'SP'),
('Rio de Janeiro', 'RJ'),
('Belo Horizonte', 'MG');

-- Inserindo endereços
INSERT INTO endereco (end_tipo_logradouro, end_logradouro, end_numero, end_bairro, cid_id) VALUES
('Rua', 'Paulista', 1000, 'Centro', 1),
('Avenida', 'Atlântica', 500, 'Copacabana', 2),
('Rua', 'Amazonas', 300, 'Savassi', 3);

-- Inserindo pessoas
INSERT INTO pessoa (pes_nome, pes_data_nascimento, pes_sexo, pes_mae, pes_pai) VALUES
('João Silva', '1985-07-10', 'Masculino', 'Maria Silva', 'Carlos Silva'),
('Ana Souza', '1992-03-25', 'Feminino', 'Clara Souza', 'José Souza'),
('Pedro Oliveira', '1978-11-13', 'Masculino', 'Teresa Oliveira', 'Antonio Oliveira');

-- Relacionando pessoas a endereços
INSERT INTO pessoa_endereco (pes_id, end_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Inserindo servidores temporários
INSERT INTO servidor_temporario (pes_id, st_data_admissao, st_data_demissao) VALUES
(1, '2020-01-15', '2023-06-30');

-- Inserindo servidores efetivos
INSERT INTO servidor_efetivo (pes_id, se_matricula) VALUES
(2, '20231000'),
(3, '20231001');

-- Inserindo unidades
INSERT INTO unidade (unid_nome, unid_sigla) VALUES
('Secretaria de Educação', 'SEDUC'),
('Secretaria de Saúde', 'SESAU');

-- Relacionando unidades a endereços
INSERT INTO unidade_endereco (unid_id, end_id) VALUES
(1, 1),
(2, 2);

-- Inserindo lotações
INSERT INTO lotacao (pes_id, unid_id, lot_data_lotacao, lot_data_remocao, lot_portaria) VALUES
(2, 1, '2021-05-10', NULL, 'Portaria 001/2021'),
(3, 2, '2019-09-20', NULL, 'Portaria 002/2019');