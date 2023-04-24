CREATE DATABASE apuesta;
CREATE TABLE pronostico (
  id int NOT NULL AUTO_INCREMENT,
  apostador varchar(45) NOT NULL,
  equipo_local varchar(45) NOT NULL,
  equipo_visitante varchar(45) NOT NULL,
  local varchar(1) DEFAULT NULL,
  empate varchar(1) DEFAULT NULL,
  visitante varchar(1) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO apuesta.pronostico (apostador, equipo_local, equipo_visitante, local, empate, visitante)
VALUES("Mariana", "Argentina", "Arabia Saudita", "X", "", "");
INSERT INTO apuesta.pronostico (apostador, equipo_local, equipo_visitante, local, empate, visitante)
VALUES("Mariana", "Polonia", "México", "", "X", "");
INSERT INTO apuesta.pronostico (apostador, equipo_local, equipo_visitante, local, empate, visitante)
VALUES("Mariana", "Argentina", "México", "X", "", "");
INSERT INTO apuesta.pronostico (apostador, equipo_local, equipo_visitante, local, empate, visitante)
VALUES("Mariana", "Arabia Saudita", "Polonia", "", "", "X");
INSERT INTO apuesta.pronostico (apostador, equipo_local, equipo_visitante, local, empate, visitante)
VALUES("Pedro", "Argentina", "Arabia Saudita", "X", "", "");
INSERT INTO apuesta.pronostico (apostador, equipo_local, equipo_visitante, local, empate, visitante)
VALUES("Pedro", "Polonia", "México", "", "", "X");
INSERT INTO apuesta.pronostico (apostador, equipo_local, equipo_visitante, local, empate, visitante)
VALUES("Pedro", "Argentina", "México", "X", "", "");
INSERT INTO apuesta.pronostico (apostador, equipo_local, equipo_visitante, local, empate, visitante)
VALUES("Pedro", "Arabia Saudita", "Polonia", "", "X", "");
INSERT INTO apuesta.pronostico (apostador, equipo_local, equipo_visitante, local, empate, visitante)
VALUES("Mariana", "Argentina", "Italia", "X", "", "");
INSERT INTO apuesta.pronostico (apostador, equipo_local, equipo_visitante, local, empate, visitante)
VALUES("Pedro", "Argentina", "Italia", "", "X", "");