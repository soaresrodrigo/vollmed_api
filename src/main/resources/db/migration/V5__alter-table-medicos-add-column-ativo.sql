ALTER TABLE medicos
ADD ativo TINYINT
AFTER especialidade;

UPDATE medicos
SET ativo = 1;