DROP DATABASE IF EXISTS tipocambio;
CREATE SCHEMA tipocambio;
use tipocambio;
CREATE TABLE roles (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE usuarios (
  id bigint NOT NULL AUTO_INCREMENT,
  email varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE tipo_camb (
  id bigint NOT NULL AUTO_INCREMENT,
  moneda_origen varchar(255) DEFAULT NULL,
  multiplo_cambio double DEFAULT NULL,
  moneda_destino varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE usuarios_roles (
  usuario_id bigint NOT NULL,
  rol_id bigint NOT NULL,
  PRIMARY KEY (usuario_id,rol_id),
  FOREIGN KEY (rol_id) REFERENCES roles (id),
  FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);

INSERT INTO tipo_camb(moneda_origen,multiplo_cambio,moneda_destino) VALUES('SOL',0.4,'EUR');
INSERT INTO tipo_camb(moneda_origen,multiplo_cambio,moneda_destino) VALUES('SOL',0.3,'DOL');
INSERT INTO tipo_camb(moneda_origen,multiplo_cambio,moneda_destino) VALUES('EUR',4,'SOL');

INSERT INTO roles(NAME) VALUES('ROLE_ADMIN');
INSERT INTO roles(NAME) VALUES('ROLE_USER');