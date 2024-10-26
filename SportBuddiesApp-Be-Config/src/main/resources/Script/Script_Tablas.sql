create database sportbuddies;
use sportbuddies;

-- ################ --
-- ### USUARIOS ### --
-- ################ --
CREATE TABLE `usuarios` (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(50) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `apellido` varchar(50) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `url_imagen` VARCHAR(255)
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `idusuarios_UNIQUE` (`id_usuario`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ############# --
-- ### ROLES ### --
-- ############# --

CREATE TABLE `roles` (
  `id_rol` bigint NOT NULL AUTO_INCREMENT,
  `nombre_rol` varchar(45) NOT NULL,
  PRIMARY KEY (`id_rol`),
  UNIQUE KEY `idRol_UNIQUE` (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ###################### --
-- ### CLIENTES OAUTH ### --
-- ###################### --

CREATE TABLE clientes_oauth (
    id_cliente_oauth bigint NOT NULL AUTO_INCREMENT,
    client_id varchar(255) NOT NULL,
    client_secret varchar(255) DEFAULT NULL,
    nombre_cliente varchar(255) NOT NULL,
    metodos_autenticacion varchar(1000) NOT NULL,
    tipos_autorizacion varchar(1000) NOT NULL,
    redireccion_uris varchar(1000) DEFAULT NULL,
    redireccion_uris_logout varchar(1000) DEFAULT NULL,
    permisos varchar(1000) NOT NULL,
	access_token bigint,
	refresh_token bigint,
    PRIMARY KEY (id_cliente_oauth)
);

-- ################ --
-- ### DEPORTES ### --
-- ################ --

CREATE TABLE deportes (
    id_deporte BIGINT NOT NULL AUTO_INCREMENT,
    actividad varchar(100) NOT NULL,
	PRIMARY KEY (`id_deporte`)
);

-- ########################## --
-- ### RESERVAS_ACTIVIDAD ### --
-- ########################## --
CREATE TABLE reservas_actividad (
    id_reserva_actividad BIGINT AUTO_INCREMENT PRIMARY KEY,
	fecha_reserva DATE,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    requerimientos JSON,
    usuarios_max_requeridos BIGINT NOT NULL,
    actividad VARCHAR(255) NOT NULL,
    usuario_actividad_fk BIGINT NOT NULL COMMENT 'Id del usuario que ha creado la reserva de la actividad',
    direccion VARCHAR(255),
    provincia VARCHAR(255),
    municipio VARCHAR(255),
    codigo_postal BIGINT,
	urgencia Varchar(20),
	abono_pista DECIMAL(5,2),
    CONSTRAINT fk_usuario_actividad FOREIGN KEY (usuario_actividad_fk) REFERENCES usuarios(id_usuario)
);

-- ######################## --
-- ### RESERVAS_USUARIO ### --
-- ######################## --

CREATE TABLE reservas_usuario (
    id_reserva bigint NOT NULL AUTO_INCREMENT,
	fecha_reserva DATE,
	hora_inicio_reserva TIME,
	hora_fin_reserva TIME,
	usuario_reserva_fk bigint NOT NULL,
	deporte_reserva_fk bigint NOT NULL,
	reserva_actividad_fk bigint NOT NULL,
	abonado tinyint(1) NOT NULL,
	PRIMARY KEY (`id_reserva`),
    FOREIGN KEY (usuario_reserva_fk) REFERENCES usuarios(id_usuario),
	FOREIGN KEY (deporte_reserva_fk) REFERENCES deportes(id_deporte),
	FOREIGN KEY (reserva_actividad_fk) REFERENCES reservas_actividad(id_reserva_actividad)
);

-- ####################### --
-- ### Usuario_In_Role ### --
-- ####################### --
CREATE TABLE usuarios_in_role (
    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, role_id),
    UNIQUE (usuario_id, role_id)
);

-- ################## --
-- ### PROVINCIAS ### --
-- ################## --
CREATE TABLE provincias (
    id_provincia bigint NOT NULL,
    sigla_provincia varchar(10),
    nombre_provincia varchar(200),
    PRIMARY KEY (id_provincia)
);

-- ################## --
-- ### MUNICIPIOS ### --
-- ################## --
CREATE TABLE municipios (
    id_municipio bigint NOT NULL PRIMARY KEY,
    nombre_municipio varchar(200),
    municipio_provincia_fk bigint NOT NULL,
    CONSTRAINT fk_municipio_provincia FOREIGN KEY (municipio_provincia_fk) REFERENCES provincias(id_provincia)
);

-- ###################### --
-- ### PLANES_DE_PAGO ### --
-- ###################### --
CREATE TABLE planes_de_pago (
	id_plan_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
	nombre_plan VARCHAR(50),
	limite_reservas BIGINT,
	precio_plan DECIMAL(5,2)
);

-- #################### --
-- ### TIPO_ESTADOS ### --
-- #################### --
CREATE TABLE tipo_estados(
	id_tipo BIGINT AUTO_INCREMENT PRIMARY KEY,
	estado varchar(50),
	descripcion VARCHAR(200) COMMENT 'Breve descripción del estado en el que se encuentra la suscripción'
);


-- ##################### --
-- ### SUSCRIPCIONES ### --
-- ##################### --
CREATE TABLE suscripciones (
	id_sucripcion BIGINT AUTO_INCREMENT PRIMARY KEY,
	suscripcion_usuario_fk BIGINT COMMENT 'ID del usuario asociado a la suscripcion',
	fecha_inicio DATE COMMENT 'Inicio de la fecha de la suscripcion',
	fecha_fin DATE COMMENT 'Fin de la fecha de la suscripcion',
	precio_total DECIMAL(5,2) COMMENT 'Precio de la suscripcion, se rellena con la tabla PLANES_DE_PAGO campo precio_plan',
	metodo_pago VARCHAR(50),
	estado_pago VARCHAR(50),
	CONSTRAINT fk_sucripcion_usuario FOREIGN KEY (suscripcion_usuario_fk) REFERENCES usuarios(id_usuario)
);


-- ######################### --
-- ### USUARIO_PLAN_PAGO ### --
-- ######################### --
CREATE TABLE usuario_plan_pago (
	id_usuario_plan_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
	usuario_plan_pago_suscripcion_fk BIGINT COMMENT 'ID de la suscripción asociada al usuario',
	plan_pago_fk BIGINT COMMENT 'ID del plan de pago asociado al usuario',
	reservas_restantes BIGINT COMMENT 'Número de reservas restantes para el usuario en su plan',
	fecha_renovacion DATE COMMENT 'Fecha en la que el plan del usuario se renueva',
	CONSTRAINT fk_usuario_plan_pago_suscripcion FOREIGN KEY (usuario_plan_pago_suscripcion_fk) REFERENCES suscripciones(id_sucripcion),
	CONSTRAINT fk_plan_pago FOREIGN KEY (plan_pago_fk) REFERENCES planes_de_pago(id_plan_pago)
);

-- ################################## --
-- ### AUTORIZACION_CONSENTIMIENTO ### --
-- ################################## --
CREATE TABLE autorizacion_consentimiento (
    id_cliente_registrado varchar(255) NOT NULL,
    nombre_principal varchar(255) NOT NULL,
    authorities varchar(1000) NOT NULL,
    PRIMARY KEY (id_cliente_registrado, nombre_principal)
);

-- ############## --
-- ### PAYPAL ### --
-- ############## --
CREATE TABLE paypal (
	id_paypal BIGINT AUTO_INCREMENT PRIMARY KEY,
	url_refund VARCHAR(255),
	total BIGINT COMMENT 'Precio que se ha abonado',
	moneda VARCHAR(100) COMMENT 'Nombre de la moneda con la que se hizo el pago `EUR`, `USD`, `LIB` ',
	reembolsado tinyint(1),
	fecha_reembolso DATE COMMENT 'Fecha en la que se realizao el reembolso',
	reserva_usuario_fk BIGINT COMMENT 'ID de la reservas del usuario',
	usuario_fk BIGINT COMMENT 'ID del usuario que hace la reserva dentro de la tabla usuarios',
	constraint fk_reserva_usuario FOREIGN KEY (reserva_usuario_fk) REFERENCES RESERVAS_USUARIO(id_reserva),
	constraint fk_usuario_ FOREIGN KEY (usuario_fk) REFERENCES usuarios(id_usuario)
);


-- ########################### --
-- ### Codigo_Verificacion ### --
-- ########################### --

CREATE TABLE codigo_verificacion (
	id_codigo_verificacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL,
    tiempo_expiracion TIMESTAMP NOT NULL,
    usuario_fk BIGINT COMMENT 'FK con el id del usuario',
    constraint fk_codigo_verificacion_usuario FOREIGN KEY (usuario_fk) REFERENCES usuarios(id_usuario)
);

-- ############## --
-- ### INSERT ### --
-- ############## --

-- Nota: todo los IDS que se están introduciendo a mano cuando se agregue en una nueva base de datos pueden ser alterados ya que ahora mismo empiezan por 3 por ejemplo en la tabla reservas

INSERT INTO `sportbuddies`.`usuarios`(`nombre_usuario`,`password`,`enabled`,`apellido`,`email`) VALUES ('Jose','$2a$10$zuhyIlUPCe6SZmcerMrzn.5W6U2eIzgipU50pN5clOC111zO9BV26',1,'Plasencia','pl@gmail.comm');
INSERT INTO `sportbuddies`.`usuarios`(`nombre_usuario`,`password`,`enabled`,`apellido`,`email`) VALUES ('Maria','$2a$10$Oab4M5yLiznerUFXD94PMO8.W5IjT6YFBzeJ1OlavCqgAKJnpm7wi',1,'Garcia','MA@gmail.comm');

INSERT INTO `sportbuddies`.`roles` (`nombre_rol`) VALUES ('ADMIN');
INSERT INTO `sportbuddies`.`roles` (`nombre_rol`) VALUES ('USER');
INSERT INTO `sportbuddies`.`roles` (`nombre_rol`) VALUES ('OIDC_USER');



INSERT INTO `sportbuddies`.`deportes` (`actividad`) VALUES ('Futbol Sala');
INSERT INTO `sportbuddies`.`deportes` (`actividad`) VALUES ('Futbol');

INSERT INTO `sportbuddies`.`usuarios_in_role` (`usuario_id`, `role_id`) VALUES(3, 4);


INSERT INTO reservas_actividad ( fecha_reserva, hora_inicio,  hora_fin,  requerimientos,  usuarios_max_requeridos,  actividad,  usuario_actividad_fk, 
direccion, provincia,  municipio,  codigo_postal, urgencia,abono_pista) VALUES ('2024-09-13','10:00:00', '12:00:00', JSON_ARRAY('Camiseta Roja equipo 1', 'Camiseta Verde Equipo B', 'Árbitro'), 
10,'Fútbol',3,'Calle Ejemplo 123','Araba/Álava','Amurrio',28001,'Alta',2.5);

INSERT INTO reservas_actividad ( fecha_reserva, hora_inicio,  hora_fin,  requerimientos,  usuarios_max_requeridos,  actividad,  usuario_actividad_fk, 
direccion, provincia,  municipio,  codigo_postal, urgencia,abono_pista) VALUES ('2024-12-14','17:00:31', '18:00:31', JSON_ARRAY('Equipo uno falta dos jugadores', 'Equipación Roja'), 
10,'Fútbol', 3, 'Avenida florida', 'Araba/Álava', 'Amurrio', 4200,'Alta',4.5);	


INSERT INTO `sportbuddies`.`reservas_usuario` (`fecha_reserva`, `hora_inicio_reserva`, `hora_fin_reserva`, `usuario_reserva_fk`,`deporte_reserva_fk`, `reserva_actividad_fk`, `abonado`)
VALUES ('2024-09-13', '20:00:31', '21:00:31',3,1,1,1);
INSERT INTO `sportbuddies`.`reservas_usuario` (`fecha_reserva`, `hora_inicio_reserva`, `hora_fin_reserva`, `usuario_reserva_fk`,`deporte_reserva_fk`, `reserva_actividad_fk`, `abonado`)
VALUES ('2024-09-14', '20:00:31', '21:00:31',3,1,1,0);
INSERT INTO `sportbuddies`.`reservas_usuario` (`fecha_reserva`, `hora_inicio_reserva`, `hora_fin_reserva`, `usuario_reserva_fk`,`deporte_reserva_fk`, `reserva_actividad_fk`, `abonado`)
VALUES ('2024-12-13', '20:00:31', '21:00:31',3,1,1,0);
INSERT INTO `sportbuddies`.`reservas_usuario` (`fecha_reserva`, `hora_inicio_reserva`, `hora_fin_reserva`, `usuario_reserva_fk`,`deporte_reserva_fk`, `reserva_actividad_fk`, `abonado`)
VALUES ('2024-12-14', '20:00:31', '21:00:31',3,1,1,0);


INSERT INTO provincias (id_provincia, sigla_provincia, nombre_provincia) VALUES
(1, 'VI', 'Araba/Álava'),
(2, 'AB', 'Albacete'),
(3, 'A', 'Alicante/Alacant'),
(4, 'AL', 'Almería'),
(5, 'O', 'Asturias'),
(6, 'AV', 'Ávila'),
(7, 'BA', 'Badajoz'),
(8, 'IB', 'Illes Balears'),
(9, 'B', 'Barcelona'),
(10, 'BU', 'Burgos'),
(11, 'CC', 'Cáceres'),
(12, 'CA', 'Cádiz'),
(13, 'CS', 'Castellón/Castelló'),
(14, 'CR', 'Ciudad Real'),
(15, 'CO', 'Córdoba'),
(16, 'C', 'A Coruña'),
(17, 'CU', 'Cuenca'),
(18, 'GI', 'Girona'),
(19, 'GR', 'Granada'),
(20, 'GU', 'Guadalajara'),
(21, 'SS', 'Gipuzkoa'),
(22, 'H', 'Huelva'),
(23, 'HU', 'Huesca'),
(24, 'J', 'Jaén'),
(25, 'LE', 'León'),
(26, 'L', 'Lleida'),
(27, 'LO', 'La Rioja'),
(28, 'LU', 'Lugo'),
(29, 'MA', 'Málaga'),
(30, 'MU', 'Murcia'),
(31, 'NA', 'Navarra'),
(32, 'OU', 'Ourense'),
(33, 'P', 'Palencia'),
(34, 'GC', 'Las Palmas'),
(35, 'PO', 'Pontevedra'),
(36, 'SA', 'Salamanca'),
(37, 'TF', 'Santa Cruz de Tenerife'),
(38, 'S', 'Cantabria'),
(39, 'SG', 'Segovia'),
(40, 'SE', 'Sevilla'),
(41, 'SO', 'Soria'),
(42, 'T', 'Tarragona'),
(43, 'TE', 'Teruel'),
(44, 'TO', 'Toledo'),
(45, 'V', 'Valencia/València'),
(46, 'VA', 'Valladolid'),
(47, 'BI', 'Bizkaia'),
(48, 'ZA', 'Zamora'),
(49, 'Z', 'Zaragoza'),
(50, 'CE', 'Ceuta'),
(51, 'ML', 'Melilla');

INSERT INTO municipios (id_municipio, municipio_provincia_fk, nombre_municipio) VALUES
(1,1, 'Alegría-Dulantzi'),
(2,1, 'Amurrio'),
(3, 1,'Aramaio'),
(4,1, 'Armiñón'),
(5, 1,'Arrazua-Ubarrundia'),
(6,1, 'Artziniega'),
(7, 1,'Asparrena'),
(8, 1,'Ayala/Aiara'),
(9, 1,'Baños de Ebro/Mañueta'),
(10,1, 'Barrundia'),
(11, 1,'Berantevilla'),
(12, 1,'Bernedo'),
(13, 1,'Campezo/Kanpezu'),
(14, 1,'Elburgo/Burgelu'),
(15, 1,'Elciego'),
(16, 1,'Elvillar/Bilar'),
(17, 1,'Erriberagoitia/Ribera Alta'),
(18, 1,'Harana/Valle de Arana'),
(19, 1,'Iruña Oka/Iruña de Oca'),
(20, 1,'Iruraiz-Gauna'),
(21, 1,'Kripan'),
(22, 1,'Kuartango'),
(23, 1,'Labastida/Bastida'),
(24, 1,'Lagrán'),
(25, 1,'Laguardia'),
(26, 1,'Lanciego/Lantziego'),
(27, 1,'Lantarón'),
(28, 1,'Lapuebla de Labarca'),
(29,1, 'Laudio/Llodio'),
(30, 1,'Legutio'),
(31, 1,'Leza'),
(32, 1,'Moreda de Álava'),
(33, 1,'Navaridas'),
(34,1, 'Okondo'),
(35,1, 'Oyón-Oion'),
(36,1, 'Peñacerrada-Urizaharra'),
(37,1, 'Samaniego'),
(38,1, 'San Millán/Donemiliaga'),
(39,1, 'Urkabustaiz'),
(40,1, 'Valdegovía/Gaubea'),
(41,1, 'Villabuena de Álava/Eskuernaga'),
(42, 1,'Vitoria-Gasteiz'),
(43,1, 'Yécora/Iekora'),
(44,1, 'Zalduondo'),
(45,1, 'Zambrana'),
(46,1, 'Zigoitia'),
(47,1, 'Zuia');


INSERT INTO planes_de_pago(id_plan_pago, nombre_plan, limite_reservas, precio_plan) VALUES (1,'free', 9999, 0), (2, 'student', 5, 2.99), (3, 'basic', 3, 4.99),
(4, 'premium', 5, 8.99),(5, 'unlimited', 10, 14.99)	


INSERT INTO suscripciones(id_sucripcion, suscripcion_usuario_fk,fecha_inicio,fecha_fin, precio_total, metodo_pago , estado_pago) 
VALUE (1,3,'2024-09-19','2024-10-19', 4.99, 'Paypal', 'Activo'); 

INSERT INTO tipo_estados (estado, descripcion) VALUES ('Activo', 'La suscripción está activa y el usuario puede usar todos los servicios.'),
('Pendiente', 'El pago está en proceso, pero no se ha confirmado aún. Puede ser por un intento de pago que no ha sido verificado'),
('Expirado', 'La suscripción ha caducado y el usuario no puede acceder a los servicios hasta que renueve su pago'),
('Cancelado', 'El usuario ha cancelado su suscripción y no puede acceder a los servicios'),
('Rechazado', 'El pago fue rechazado (por ejemplo, tarjeta de crédito denegada)'),
('Vencido', 'Similar a "expirado", pero puede usarse para indicar que se pasó la fecha de renovación sin pago'),
('Devuelto', 'El pago fue devuelto por alguna razón (por ejemplo, disputa o solicitud del usuario)');


INSERT INTO usuario_plan_pago(id_usuario_plan_pago, usuario_plan_pago_suscripcion_fk, plan_pago_fk, reservas_restantes, fecha_renovacion) 
VALUES (1,1,3,3,'2024-10-19') ;


INSERT INTO clientes_oauth (id_cliente_oauth,client_id,client_secret,nombre_cliente,metodos_autenticacion,tipos_autorizacion,redireccion_uris,redireccion_uris_logout,permisos,access_token,refresh_token)
VALUES
(4,'gateway-app','$2a$10$.Ud6UXjF6ScEieX7erFVU.dWPYyfnhYSjbSqVRpRR4k2r68GVMQeO','client-be','client_secret_basic','refresh_token,authorization_code','http://127.0.0.1:8090/authorized,http://127.0.0.1:8090/login/oauth2/code/client-be','http://127.0.0.1:8090/logout','openid,profile',12,1),
(5,'client-angular','$2a$10$3M0wg0e2LAKomzpTIjaJWOIKOjMFCRvpOinc6RG2WQhwNdIsMzjcu','client-angular','client_secret_basic','refresh_token,authorization_code','http://localhost:4200/authorize','http://127.0.0.1:8090/logout','openid,profile',12,1),
(6,'prueba_id','$2a$10$5WBAhfQ/3gCWhuNyBFFYJ.FUS6mrGFfWJUM13K7KNwEG1hBo9wz4m','pureba_cliente','http://localhost:8090/prueba/oauth','authorization_code,refresh_token','http://localhost:8090/prueba/oauth,http://localhost:8090/prueba/','http://localhost:8090/prueba/redorect','openid,profile',12,12);

INSERT INTO codigo_verificacion (codigo, tiempo_expiracion, usuario_fk)
VALUES ('ABC123', DATE_ADD(NOW(), INTERVAL 5 MINUTE),3);


