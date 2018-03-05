CREATE TABLE cidade (
  id                 BIGINT NOT NULL AUTO_INCREMENT,
  ibge_id            INTEGER,
  uf                 CHARACTER VARYING(2),
  name        		 CHARACTER VARYING(255),
  capital            BOOLEAN,
  lon                DECIMAL(15,2),
  lat                DECIMAL(15,2),
  no_accents         CHARACTER VARYING(255),
  alternative_names  CHARACTER VARYING(255),
  microregion        CHARACTER VARYING(255),
  mesoregion         CHARACTER VARYING(255),
  excluido           BOOLEAN   NOT NULL DEFAULT FALSE,
  CONSTRAINT pk_cidade PRIMARY KEY (id)
);